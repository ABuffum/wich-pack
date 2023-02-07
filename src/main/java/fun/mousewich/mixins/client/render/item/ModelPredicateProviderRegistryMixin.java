package fun.mousewich.mixins.client.render.item;

import fun.mousewich.ModBase;
import fun.mousewich.item.RecoveryCompassItem;
import fun.mousewich.client.render.item.ModelPredicateAngleInterpolator;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ModelPredicateProviderRegistry.class)
public class ModelPredicateProviderRegistryMixin {
	@Inject(method="<clinit>", at = @At("TAIL"))
	private static void RegisterRecoveryCompass(CallbackInfo ci) {
		ModelPredicateProviderRegistry.register(ModBase.RECOVERY_COMPASS, new Identifier("angle"), new UnclampedModelPredicateProvider(){
			private final ModelPredicateAngleInterpolator aimedInterpolator = new ModelPredicateAngleInterpolator();
			private final ModelPredicateAngleInterpolator aimlessInterpolator = new ModelPredicateAngleInterpolator();

			@Override
			public float unclampedCall(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity, int i) {
				double g;
				Entity entity = livingEntity != null ? livingEntity : itemStack.getHolder();
				if (entity == null) return 0.0f;
				if (clientWorld == null && entity.world instanceof ClientWorld) clientWorld = (ClientWorld)entity.world;
				BlockPos blockPos = RecoveryCompassItem.hasPosition(itemStack) ? this.getPos(clientWorld, itemStack.getOrCreateNbt()) : null;//this.getSpawnPos(clientWorld);
				long l = clientWorld.getTime();
				if (blockPos == null || entity.getPos().squaredDistanceTo((double)blockPos.getX() + 0.5, entity.getPos().getY(), (double)blockPos.getZ() + 0.5) < (double)1.0E-5f) {
					if (this.aimlessInterpolator.shouldUpdate(l)) {
						this.aimlessInterpolator.update(l, Math.random());
					}
					double d = this.aimlessInterpolator.getValue() + (double)((float)this.scatter(i) / 2.14748365E9f);
					return MathHelper.floorMod((float)d, 1.0f);
				}
				boolean bl = livingEntity instanceof PlayerEntity playerEntity && playerEntity.isMainPlayer();
				double e = 0.0;
				if (bl) e = livingEntity.getYaw();
				else if (entity instanceof ItemFrameEntity itemFrameEntity) e = this.getItemFrameAngleOffset(itemFrameEntity);
				else if (entity instanceof ItemEntity itemEntity) e = 180.0f - itemEntity.getRotation(0.5f) / ((float)Math.PI * 2) * 360.0f;
				else if (livingEntity != null) e = livingEntity.bodyYaw;
				e = MathHelper.floorMod(e / 360.0, 1.0);
				double f = this.getAngleToPos(Vec3d.ofCenter(blockPos), entity) / 6.2831854820251465;
				if (bl) {
					if (this.aimedInterpolator.shouldUpdate(l)) this.aimedInterpolator.update(l, 0.5 - (e - 0.25));
					g = f + this.aimedInterpolator.getValue();
				}
				else g = 0.5 - (e - 0.25 - f);
				return MathHelper.floorMod((float)g, 1.0f);
			}

			/**
			 * Scatters a seed by integer overflow in multiplication onto the whole
			 * int domain.
			 */
			private int scatter(int seed) { return seed * 1327217883; }

			@Nullable
			private BlockPos getSpawnPos(ClientWorld world) {
				return world.getDimension().isNatural() ? world.getSpawnPos() : null;
			}

			@Nullable
			private BlockPos getPos(World world, NbtCompound nbt) {
				Optional<RegistryKey<World>> optional;
				boolean bl = nbt.contains(RecoveryCompassItem.POS_KEY);
				boolean bl2 = nbt.contains(RecoveryCompassItem.DIMENSION_KEY);
				if (bl) {
					if (!bl2 || ((optional = RecoveryCompassItem.getDimension(nbt)).isPresent() && world.getRegistryKey() == optional.get())) {
						return NbtHelper.toBlockPos(nbt.getCompound(RecoveryCompassItem.POS_KEY));
					}
				}
				return null;
			}

			private double getItemFrameAngleOffset(ItemFrameEntity itemFrame) {
				Direction direction = itemFrame.getHorizontalFacing();
				int i = direction.getAxis().isVertical() ? 90 * direction.getDirection().offset() : 0;
				return MathHelper.wrapDegrees(180 + direction.getHorizontal() * 90 + itemFrame.getRotation() * 45 + i);
			}

			private double getAngleToPos(Vec3d pos, Entity entity) {
				return Math.atan2(pos.getZ() - entity.getZ(), pos.getX() - entity.getX());
			}
		});
	}
}
