package fun.mousewich.mixins.block.enums;

import fun.mousewich.ModBase;
import fun.mousewich.gen.data.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.Instrument;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Instrument.class)
public class InstrumentMixin {
	@Inject(method="fromBlockState", at = @At("HEAD"), cancellable = true)
	private static void fromBlockState(BlockState state, CallbackInfoReturnable<Instrument> cir) {
		if (state.isIn(ModBlockTags.FLEECE)) cir.setReturnValue(Instrument.GUITAR);
		else if (state.isIn(ModBlockTags.PUMPKINS)) cir.setReturnValue(Instrument.DIDGERIDOO);
		else if (state.isOf(ModBase.BAMBOO_BLOCK.asBlock()) || state.isOf(ModBase.STRIPPED_BAMBOO_BLOCK.asBlock())) cir.setReturnValue(Instrument.BASS);
	}
}