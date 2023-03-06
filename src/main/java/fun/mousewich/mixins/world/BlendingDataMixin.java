package fun.mousewich.mixins.world;

import fun.mousewich.ModBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.BlendingData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlendingData.class)
public class BlendingDataMixin {
	@Inject(method="isCollidableAndNotTreeAt", at=@At("HEAD"), cancellable = true)
	private static void BlueMushroomIsCollidableOrTreeAt(Chunk chunk, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (chunk.getBlockState(pos).isOf(ModBase.BLUE_MUSHROOM_BLOCK.asBlock())) cir.setReturnValue(false);
	}
}
