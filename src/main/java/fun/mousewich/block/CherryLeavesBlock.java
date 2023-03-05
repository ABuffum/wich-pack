package fun.mousewich.block;

import fun.mousewich.ModBase;
import fun.mousewich.block.basic.ModLeavesBlock;
import fun.mousewich.util.ParticleUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class CherryLeavesBlock extends ModLeavesBlock {
	public static final int MAX_DISTANCE = 9;
	public CherryLeavesBlock(Settings settings) { super(settings); }

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		super.randomDisplayTick(state, world, pos, random);
		if (random.nextInt(15) != 0) return;
		BlockPos blockPos = pos.down();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.isOpaque() && blockState.isSideSolidFullSquare(world, blockPos, Direction.UP)) return;
		ParticleUtils.spawnParticle(world, pos, random, ModBase.DRIPPING_CHERRY_LEAVES_PARTICLE);
	}
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		BlockState blockState = this.getDefaultState().with(PERSISTENT, true).with(Properties.WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
		return updateDistanceFromLogs(blockState, ctx.getWorld(), ctx.getBlockPos());
	}
	private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
		return state.with(DISTANCE, Math.max(7, distanceFromLogs(state, world, pos)));
	}
	private static int distanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
		int i = MAX_DISTANCE;
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (Direction direction : Direction.values()) {
			mutable.set(pos, direction);
			i = Math.min(i, (state.isIn(BlockTags.LOGS) ? 0 : state.getBlock() instanceof LeavesBlock ? state.get(DISTANCE) : MAX_DISTANCE) + 1);
			if (i == 1) break;
		}
		return i;
	}
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!state.get(PERSISTENT) && distanceFromLogs(state, world, pos) >= MAX_DISTANCE) {
			LeavesBlock.dropStacks(state, world, pos);
			world.removeBlock(pos, false);
		}
	}
}
