package fun.wich.mixins.block;

import fun.wich.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.NyliumBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(NyliumBlock.class)
public class NyliumBlockMixin {
	@Inject(method="grow", at = @At("TAIL"))
	private void Grow(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo ci) {
		BlockState blockState = world.getBlockState(pos);
		if (blockState.isOf(ModBase.GILDED_NYLIUM.asBlock())) {
			BlockPos blockPos = pos.up();
			ChunkGenerator chunkGenerator = world.getChunkManager().getChunkGenerator();
			ModBase.GILDED_FOREST_VEGETATION_BONEMEAL.getFeature().generate(world, chunkGenerator, random, blockPos);
		}
	}
}
