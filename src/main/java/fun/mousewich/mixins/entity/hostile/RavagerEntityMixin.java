package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.event.ModGameEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RavagerEntity.class)
public abstract class RavagerEntityMixin extends RaiderEntity implements EntityWithBloodType {
	protected RavagerEntityMixin(EntityType<? extends RaiderEntity> entityType, World world) { super(entityType, world); }
	@Inject(method="roar", at = @At("TAIL"))
	private void roar(CallbackInfo ci) { if (this.isAlive()) this.emitGameEvent(ModGameEvent.ENTITY_ROAR); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.RAVAGER_BLOOD_TYPE; }
}
