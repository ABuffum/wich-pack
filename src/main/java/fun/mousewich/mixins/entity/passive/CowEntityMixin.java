package fun.mousewich.mixins.entity.passive;

import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntity {
	public CowEntityMixin(EntityType<? extends CowEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="getAmbientSound", at = @At("HEAD"), cancellable = true)
	protected void getAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
		if (getType() == EntityType.MOOSHROOM) cir.setReturnValue(ModSoundEvents.ENTITY_MOOSHROOM_AMBIENT);
	}
	@Inject(method="getHurtSound", at = @At("HEAD"), cancellable = true)
	protected void getHurtSound(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
		if (getType() == EntityType.MOOSHROOM) cir.setReturnValue(ModSoundEvents.ENTITY_MOOSHROOM_HURT);
	}
	@Inject(method="getDeathSound", at = @At("HEAD"), cancellable = true)
	protected void getDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
		if (getType() == EntityType.MOOSHROOM) cir.setReturnValue(ModSoundEvents.ENTITY_MOOSHROOM_DEATH);
	}

	@Inject(method="playStepSound", at = @At("HEAD"), cancellable = true)
	protected void playStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
		if (getType() == EntityType.MOOSHROOM) {
			this.playSound(ModSoundEvents.ENTITY_MOOSHROOM_STEP, 0.15f, 1.0f);
			ci.cancel();
		}
	}
}
