package fun.mousewich.block;

import fun.mousewich.block.basic.ModTrapdoorBlock;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public abstract class AbstractGlassTrapdoorBlock extends TransparentTrapdoorBlock {
	protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 16.0, 16.0);
	protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 2.0);
	protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 14.0, 16.0, 16.0, 16.0);
	protected static final VoxelShape OPEN_BOTTOM_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
	protected static final VoxelShape OPEN_TOP_SHAPE = Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);

	protected final Block baseSlab;
	public AbstractGlassTrapdoorBlock(Block block, Block slab) {
		super(block, ModSoundEvents.BLOCK_GLASS_TRAPDOOR_OPEN, ModSoundEvents.BLOCK_GLASS_TRAPDOOR_CLOSE);
		this.baseSlab = slab;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (!state.get(OPEN)) return state.get(HALF) == BlockHalf.TOP ? OPEN_TOP_SHAPE : OPEN_BOTTOM_SHAPE;
		return switch (state.get(FACING)) {
			case SOUTH -> SOUTH_SHAPE;
			case WEST -> WEST_SHAPE;
			case EAST -> EAST_SHAPE;
			default -> NORTH_SHAPE;
		};
	}
	@Override
	public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.empty();
	}
	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) { return 1.0f; }
	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) { return true; }
	@Override
	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
		if (stateFrom.isOf(baseSlab)) {
			SlabType type = stateFrom.get(Properties.SLAB_TYPE);
			if (type == SlabType.DOUBLE) return true;
			else if (type == SlabType.TOP) {
				if (!state.get(OPEN)) {
					if (state.get(HALF) == BlockHalf.TOP && direction.getAxis().isHorizontal()) return true;
				}
			}
			else if (!state.get(OPEN)) {
				if (state.get(HALF) == BlockHalf.BOTTOM && direction.getAxis().isHorizontal()) return true;
			}
		}
		return super.isSideInvisible(state, stateFrom, direction);
	}
}
