package fun.mousewich.block;

import fun.mousewich.block.basic.ModSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.enums.SlabType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.UndergroundConfiguredFeatures;

import java.util.Random;

public class MossSlabBlock extends ModSlabBlock implements Fertilizable {
	public MossSlabBlock(BlockConvertible block) { super(block); }
	public MossSlabBlock(Block block) { super(block); }
	public MossSlabBlock(Settings settings) { super(settings); }

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return world.getBlockState(pos.up()).isAir() && state.get(TYPE) == SlabType.DOUBLE;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return state.get(TYPE) == SlabType.DOUBLE; }

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		UndergroundConfiguredFeatures.MOSS_PATCH_BONEMEAL.value().generate(world, world.getChunkManager().getChunkGenerator(), random, pos.up());
	}
}
