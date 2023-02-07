package fun.mousewich.mixins.entity.passive;

import fun.mousewich.origins.powers.MobHostilityPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowGolemEntity.class)
public abstract class SnowGolemEntityMixin extends GolemEntity implements Shearable, RangedAttackMob {
	protected SnowGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="initGoals", at = @At("TAIL"))
	protected void initGoals(CallbackInfo ci) {
		this.targetSelector.add(1, MobHostilityPower.makeHostilityGoal(this, 10, true, false, EntityType.SNOW_GOLEM));
	}
}
