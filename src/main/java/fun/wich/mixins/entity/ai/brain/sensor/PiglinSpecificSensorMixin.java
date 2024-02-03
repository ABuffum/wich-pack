package fun.wich.mixins.entity.ai.brain.sensor;

import fun.wich.ModBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.PiglinSpecificSensor;
import net.minecraft.entity.mob.*;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(PiglinSpecificSensor.class)
public class PiglinSpecificSensorMixin {

	@Inject(method="sense", at=@At("TAIL"))
	protected void OverrideNearestVisible(ServerWorld world, LivingEntity entity, CallbackInfo ci) {
		Brain<?> brain = entity.getBrain();
		Optional<LivingEntity> optional5 = Optional.empty();
		LivingTargetCache livingTargetCache = brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).orElse(LivingTargetCache.empty());
		for (LivingEntity livingEntity : livingTargetCache.iterate(livingEntity -> true)) {
			if (optional5.isPresent() || !(PiglinBrain.isZombified(livingEntity.getType()) || ModBase.IS_ZOMBIFIED_PIGLIN.isActive(livingEntity))) continue;
			optional5 = Optional.of(livingEntity);
		}
		brain.remember(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, optional5);
	}
}
