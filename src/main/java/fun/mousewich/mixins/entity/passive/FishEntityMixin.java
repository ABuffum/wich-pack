package fun.mousewich.mixins.entity.passive;

import fun.mousewich.sound.IdentifiedSounds;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishEntity.class)
public abstract class FishEntityMixin extends WaterCreatureEntity implements Bucketable {
	protected FishEntityMixin(EntityType<? extends FishEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="getSwimSound", at = @At("HEAD"), cancellable = true)
	protected void GetSwimSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getSwimSound(this);
		if (sound != null) cir.setReturnValue(sound);
	}
}
