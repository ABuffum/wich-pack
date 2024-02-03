package fun.wich.mixins.entity.ai;

import fun.wich.gen.data.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LandPathNodeMaker.class)
public class LandPathNodeMakerMixin {
	@Inject(method="inflictsFireDamage", at=@At("HEAD"), cancellable=true)
	private static void InflictsFireDamage(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.isIn(ModBlockTags.INFLICTS_FIRE_DAMAGE)) cir.setReturnValue(true);
	}
}
