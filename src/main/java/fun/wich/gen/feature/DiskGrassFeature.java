package fun.wich.gen.feature;

import com.mojang.serialization.Codec;
import fun.wich.ModBase;
import fun.wich.gen.provider.PredicatedStateProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;
import java.util.Random;

public class DiskGrassFeature extends Feature<DefaultFeatureConfig> {
	public DiskGrassFeature(Codec<DefaultFeatureConfig> codec) { super(codec); }
	@Override
	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		BlockPos blockPos = context.getOrigin();
		StructureWorldAccess world = context.getWorld();
		Random random = context.getRandom();
		boolean bl = false;
		int i = blockPos.getY();
		int j = i + 2;
		int k = i - 3;
		int l = UniformIntProvider.create(2, 6).get(random);
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add(-l, 0, -l), blockPos.add(l, 0, l))) {
			int n;
			int m = blockPos2.getX() - blockPos.getX();
			if (m * m + (n = blockPos2.getZ() - blockPos.getZ()) * n > l * l) continue;
			bl |= this.placeBlock(world, random, j, k, mutable.set(blockPos2));
		}
		return bl;
	}

	protected boolean placeBlock(StructureWorldAccess world, Random random, int topY, int bottomY, BlockPos.Mutable pos) {
		boolean bl = false;
		for (int i = topY; i > bottomY; --i) {
			pos.setY(i);
			BlockState state = world.getBlockState(pos);
			if (!state.isOf(Blocks.DIRT) && !state.isOf(ModBase.MUD.asBlock())) continue;
			PredicatedStateProvider predicated = new PredicatedStateProvider(
					BlockStateProvider.of(Blocks.DIRT),
					List.of(new PredicatedStateProvider.Rule(
							BlockPredicate.not(BlockPredicate.eitherOf(
									BlockPredicate.solid(Direction.UP.getVector()),
									BlockPredicate.matchingFluids(List.of(Fluids.WATER),
											Direction.UP.getVector()))),
							BlockStateProvider.of(Blocks.GRASS_BLOCK))));
			world.setBlockState(pos, predicated.getBlockState(world, random, pos), Block.NOTIFY_LISTENERS);
			this.markBlocksAboveForPostProcessing(world, pos);
			bl = true;
		}
		return bl;
	}
	public BlockState getBlockState(StructureWorldAccess world, Random random, BlockPos pos) {
		PredicatedStateProvider.Rule rule = new PredicatedStateProvider.Rule(
				BlockPredicate.not(
						BlockPredicate.eitherOf(
								BlockPredicate.solid(Direction.UP.getVector()),
								BlockPredicate.matchingFluids(List.of(Fluids.WATER),
										Direction.UP.getVector()
								)
						)
				),
				BlockStateProvider.of(Blocks.GRASS_BLOCK)
		);
		if (!rule.ifTrue().test(world, pos)) return BlockStateProvider.of(Blocks.DIRT).getBlockState(random, pos);
		return rule.then().getBlockState(random, pos);
	}
}
