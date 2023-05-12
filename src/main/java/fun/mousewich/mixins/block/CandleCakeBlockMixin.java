package fun.mousewich.mixins.block;

import fun.mousewich.ModBase;
import fun.mousewich.block.basic.ModCandleCakeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CandleCakeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CandleCakeBlock.class)
public class CandleCakeBlockMixin {
	@Inject(method="getCandleCakeFromCandle", at = @At("HEAD"), cancellable = true)
	private static void GetCandleCakeFromCandle(Block candle, CallbackInfoReturnable<BlockState> cir) {
		if (candle == ModBase.SOUL_CANDLE.asBlock()) cir.setReturnValue(ModBase.SOUL_CANDLE_CAKE.getDefaultState());
		else if (candle == ModBase.ENDER_CANDLE.asBlock()) cir.setReturnValue(ModBase.ENDER_CANDLE_CAKE.getDefaultState());
		else if (candle == ModBase.NETHERRACK_CANDLE.asBlock()) cir.setReturnValue(ModBase.NETHERRACK_CANDLE_CAKE.getDefaultState());
	}

	@Inject(method="canBeLit", at = @At("HEAD"), cancellable = true)
	private static void canBeLit(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		Block block = state.getBlock();
		if (block instanceof ModCandleCakeBlock) {
			cir.setReturnValue(state.contains(ModCandleCakeBlock.LIT) && !state.get(ModCandleCakeBlock.LIT));
		}
	}
}
