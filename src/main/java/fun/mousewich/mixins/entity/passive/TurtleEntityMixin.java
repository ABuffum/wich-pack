package fun.mousewich.mixins.entity.passive;

import fun.mousewich.sound.IdentifiedSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TurtleEntity.class)
public abstract class TurtleEntityMixin extends AnimalEntity {
	protected TurtleEntityMixin(EntityType<? extends TurtleEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method="getSwimSound", at = @At("HEAD"), cancellable = true)
	protected void GetSwimSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getSwimSound(this);
		if (sound != null) cir.setReturnValue(sound);
	}
}
