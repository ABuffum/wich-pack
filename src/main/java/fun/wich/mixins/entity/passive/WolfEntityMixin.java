package fun.wich.mixins.entity.passive;

import fun.wich.ModBase;
import fun.wich.entity.ModNbtKeys;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.entity.variants.WolfVariant;
import fun.wich.event.ModGameEvent;
import fun.wich.item.basic.ModDyeItem;
import fun.wich.util.dye.ModDyeColor;
import fun.wich.util.dye.ModDyedCollar;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin extends TameableEntity implements Angerable, ModDyedCollar, EntityWithBloodType {

	protected WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) { super(entityType, world); }

	@Shadow private boolean furWet;
	@Shadow private boolean canShakeWaterOff;
	@Shadow private float shakeProgress;
	@Shadow @Final private static TrackedData<Integer> COLLAR_COLOR;

	@Inject(method="tick", at = @At("HEAD"))
	public void tick(CallbackInfo ci) {
		if (this.isAlive() && !this.isWet()) {
			if ((this.furWet || this.canShakeWaterOff) && this.canShakeWaterOff) {
				if (this.shakeProgress == 0.0f) this.emitGameEvent(ModGameEvent.ENTITY_SHAKE);
			}
		}
	}

	@Inject(method="initDataTracker", at = @At("TAIL"))
	protected void AddVariantToDataTracker(CallbackInfo ci) { this.dataTracker.startTracking(WolfVariant.VARIANT, 0); }

	@Inject(at=@At(value="RETURN"), method="createChild(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/PassiveEntity;)Lnet/minecraft/entity/passive/WolfEntity;")
	public void CreateChild(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<WolfEntity> cir) {
		WolfEntity child = cir.getReturnValue();
		if (child != null) {
			int variant = WolfVariant.getVariant((WolfEntity)(Object)this);
			int otherVariant = WolfVariant.getVariant((WolfEntity)passiveEntity);
			if (variant == otherVariant) {
				boolean darker = random.nextInt(10) == 0;
				if (variant == WolfVariant.DEFAULT.ordinal()) WolfVariant.setVariant(child, darker ? WolfVariant.GRAY.ordinal() : variant);
				else if (variant == WolfVariant.GRAY.ordinal()) WolfVariant.setVariant(child, darker ? WolfVariant.BLACK.ordinal() : variant);
				else WolfVariant.setVariant(child, variant);
			}
			else WolfVariant.setVariant(child, random.nextBoolean() ? variant : otherVariant);
		}
	}

	public ModDyeColor GetModCollarColor() { return ModDyeColor.byId(this.dataTracker.get(COLLAR_COLOR)); }
	public void SetModCollarColor(ModDyeColor color) { this.dataTracker.set(COLLAR_COLOR, color.getId()); }
	
	@Inject(method="writeCustomDataToNbt", at = @At("TAIL"))
	public void WriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		nbt.putInt(ModNbtKeys.VARIANT, this.getDataTracker().get(WolfVariant.VARIANT));
		nbt.putByte(ModNbtKeys.COLLAR_COLOR, (byte)this.GetModCollarColor().getId());
	}
	@Inject(method="readCustomDataFromNbt", at = @At("TAIL"))
	public void ReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		if (nbt.contains(ModNbtKeys.VARIANT)) this.dataTracker.set(WolfVariant.VARIANT, nbt.getInt(ModNbtKeys.VARIANT));
		if (nbt.contains(ModNbtKeys.COLLAR_COLOR, 99)) this.SetModCollarColor(ModDyeColor.byId(nbt.getInt(ModNbtKeys.COLLAR_COLOR)));
	}

	@Inject(method="interactMob", at=@At("HEAD"), cancellable=true)
	public void DyeCollarWithModDye(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (this.world.isClient) return;
		if (this.isTamed()) {
			if (this.isOwner(player)) {
				if (itemStack.getItem() instanceof ModDyeItem dye) {
					ModDyeColor dyeColor = dye.getColor();
					if (dyeColor != this.GetModCollarColor()) {
						this.SetModCollarColor(dyeColor);
						if (!player.getAbilities().creativeMode) itemStack.decrement(1);
						this.setPersistent();
						cir.setReturnValue(ActionResult.CONSUME);
					}
				}
			}
		}
	}

	@Inject(method="createChild(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/PassiveEntity;)Lnet/minecraft/entity/passive/WolfEntity;", at=@At("TAIL"))
	private void SetChildCollarColor(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<WolfEntity> cir) {
		WolfEntity child = cir.getReturnValue();
		if (passiveEntity instanceof WolfEntity && child instanceof ModDyedCollar dyed) {
			if (this.isTamed()) {
				if (this.random.nextBoolean() || !(passiveEntity instanceof ModDyedCollar)) {
					dyed.SetModCollarColor(this.GetModCollarColor());
				}
				else dyed.SetModCollarColor(((ModDyedCollar)passiveEntity).GetModCollarColor());
			}
		}
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.WOLF_BLOOD_TYPE; }
}
