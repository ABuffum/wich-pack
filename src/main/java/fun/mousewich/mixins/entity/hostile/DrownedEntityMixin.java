package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.sound.IdentifiedSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DrownedEntity.class)
public abstract class DrownedEntityMixin extends ZombieEntity implements RangedAttackMob {
	public DrownedEntityMixin(EntityType<? extends DrownedEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="getSwimSound", at = @At("HEAD"), cancellable = true)
	protected void GetSwimSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getSwimSound(this);
		if (sound != null) cir.setReturnValue(sound);
	}
}
