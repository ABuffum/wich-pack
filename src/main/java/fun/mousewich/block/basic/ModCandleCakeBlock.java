package fun.mousewich.block.basic;

import com.google.common.collect.ImmutableList;
import fun.mousewich.gen.data.loot.DropTable;
import fun.mousewich.gen.data.loot.BlockLootGenerator;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class ModCandleCakeBlock extends AbstractCandleBlock {
	public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
	protected static final VoxelShape SHAPE = VoxelShapes.union(Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.createCuboidShape(7.0D, 8.0D, 7.0D, 9.0D, 14.0D, 9.0D));
	private static final Iterable<Vec3d> PARTICLE_OFFSETS = ImmutableList.of(new Vec3d(0.5D, 1.0D, 0.5D));

	public ModCandleCakeBlock(int candleLuminance) {
		this(Settings.copy(Blocks.CAKE).luminance((state) -> state.get(CandleBlock.LIT) ? candleLuminance : 0));
	}
	public ModCandleCakeBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false));
	}

	public ModCandleCakeBlock drops(Block candle) {
		BlockLootGenerator.Drops.put(this, DropTable.CandleCake(candle));
		return this;
	}

	protected Iterable<Vec3d> getParticleOffsets(BlockState state) { return PARTICLE_OFFSETS; }

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (!itemStack.isOf(Items.FLINT_AND_STEEL) && !itemStack.isOf(Items.FIRE_CHARGE)) {
			if (isHittingCandle(hit) && player.getStackInHand(hand).isEmpty() && state.get(LIT)) {
				extinguish(player, state, world, pos);
				return ActionResult.success(world.isClient);
			}
			else {
				ActionResult actionResult = tryEat(world, pos, Blocks.CAKE.getDefaultState(), player);
				if (actionResult.isAccepted()) dropStacks(state, world, pos);
				return actionResult;
			}
		}
		else return ActionResult.PASS;
	}

	protected static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!player.canConsume(false)) return ActionResult.PASS;
		else {
			player.incrementStat(Stats.EAT_CAKE_SLICE);
			player.getHungerManager().add(2, 0.1F);
			int i = state.get(CakeBlock.BITES);
			world.emitGameEvent(player, GameEvent.EAT, pos);
			if (i < 6) world.setBlockState(pos, state.with(CakeBlock.BITES, i + 1), Block.NOTIFY_ALL);
			else {
				world.removeBlock(pos, false);
				world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
			}
			return ActionResult.SUCCESS;
		}
	}

	private static boolean isHittingCandle(BlockHitResult hitResult) {
		return hitResult.getPos().y - (double)hitResult.getBlockPos().getY() > 0.5D;
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(LIT); }

	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) { return new ItemStack(Items.CAKE); }

	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) { return world.getBlockState(pos.down()).getMaterial().isSolid(); }

	public int getComparatorOutput(BlockState state, World world, BlockPos pos) { return CakeBlock.DEFAULT_COMPARATOR_OUTPUT; }

	public boolean hasComparatorOutput(BlockState state) { return true; }

	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }
}