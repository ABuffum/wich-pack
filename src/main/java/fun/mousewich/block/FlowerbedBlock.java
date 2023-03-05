package fun.mousewich.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class FlowerbedBlock extends PlantBlock implements Fertilizable {
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	public static final IntProperty FLOWER_AMOUNT = IntProperty.of("flower_amount", 1, 4);
	public FlowerbedBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(FLOWER_AMOUNT, 1));
	}
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) { return state.with(FACING, rotation.rotate(state.get(FACING))); }
	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) { return state.rotate(mirror.getRotation(state.get(FACING))); }
	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		if (!context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(FLOWER_AMOUNT) < 4) return true;
		return super.canReplace(state, context);
	}
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
	}
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
		if (blockState.isOf(this)) return blockState.with(FLOWER_AMOUNT, Math.min(4, blockState.get(FLOWER_AMOUNT) + 1));
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(FACING, FLOWER_AMOUNT); }
	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) { return true; }
	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return true; }
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		int i = state.get(FLOWER_AMOUNT);
		if (i < 4) world.setBlockState(pos, state.with(FLOWER_AMOUNT, i + 1), Block.NOTIFY_LISTENERS);
		else dropStack(world, pos, new ItemStack(this));
	}
}
