package fun.mousewich.mixins.entity.vehicle;

import fun.mousewich.event.ModGameEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntityMixin extends Entity {
	public AbstractMinecartEntityMixin(EntityType<?> type, World world) { super(type, world); }

	@Inject(method="damage", at = @At("HEAD"))
	public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (this.world.isClient || this.isRemoved() || this.isInvulnerableTo(source)) return;
		this.emitGameEvent(ModGameEvent.ENTITY_DAMAGE, source.getAttacker());
	}
}
