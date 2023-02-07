package fun.mousewich.mixins.entity.ai.brain;

import fun.mousewich.origins.powers.MobHostilityPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
	@Inject(method="wearsGoldArmor", at = @At("HEAD"), cancellable = true)
	private static void ignoreGoldArmor(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
		if (MobHostilityPower.shouldBeHostile(target, EntityType.PIGLIN)) cir.setReturnValue(false);
	}
}
