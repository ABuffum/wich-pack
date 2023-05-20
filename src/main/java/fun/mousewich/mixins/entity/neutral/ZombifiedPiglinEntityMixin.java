package fun.mousewich.mixins.entity.neutral;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombifiedPiglinEntity.class)
public abstract class ZombifiedPiglinEntityMixin extends ZombieEntity implements Angerable, EntityWithBloodType {
	public ZombifiedPiglinEntityMixin(World world) { super(world); }
	public ZombifiedPiglinEntityMixin(EntityType<? extends ZombieEntity> entityType, World world) { super(entityType, world); }

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(ModSoundEvents.ENTITY_ZOMBIFIED_PIGLIN_STEP, 0.15f, 1.0f);
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.ZOMBIFIED_PIGLIN_BLOOD_TYPE; }

	@Override
	public boolean shouldAngerAt(LivingEntity entity) {
		if (!this.canTarget(entity)) return false;
		if (entity.getType() == EntityType.PLAYER && this.isUniversallyAngry(entity.world)) {
			if (!ModBase.IS_ZOMBIFIED_PIGLIN.isActive(entity)) return true;
		}
		return entity.getUuid().equals(this.getAngryAt());
	}

	@Inject(method="getSkull", at=@At("RETURN"), cancellable=true)
	private void GetZombifiedPiglinSkull(CallbackInfoReturnable<ItemStack> cir) {
		ItemStack stack = cir.getReturnValue();
		if (stack == null || stack.isEmpty()) cir.setReturnValue(new ItemStack(ModBase.ZOMBIFIED_PIGLIN_HEAD));
	}
}
