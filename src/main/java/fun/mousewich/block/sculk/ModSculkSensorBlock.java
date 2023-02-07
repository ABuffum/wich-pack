package fun.mousewich.block.sculk;

import fun.mousewich.ModBase;
import fun.mousewich.util.SculkUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ModSculkSensorBlock extends BlockWithEntity implements Waterloggable {
	public static final EnumProperty<SculkSensorPhase> SCULK_SENSOR_PHASE = Properties.SCULK_SENSOR_PHASE;
	public static final IntProperty POWER = Properties.POWER;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	protected static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
	private final int range;

	public ModSculkSensorBlock(Settings settings, int range) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(SCULK_SENSOR_PHASE, SculkSensorPhase.INACTIVE).with(POWER, 0).with(WATERLOGGED, false));
		this.range = range;
	}

	public int getRange() { return this.range; }

	@Override
	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockPos blockPos = ctx.getBlockPos();
		FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
		return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		if (state.get(WATERLOGGED)) return Fluids.WATER.getStill(false);
		return super.getFluidState(state);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (SculkUtils.getPhase(state) != SculkSensorPhase.ACTIVE) {
			if (SculkUtils.getPhase(state) == SculkSensorPhase.COOLDOWN) {
				world.setBlockState(pos, state.with(SCULK_SENSOR_PHASE, SculkSensorPhase.INACTIVE), Block.NOTIFY_ALL);
			}
			return;
		}
		setCooldown(world, pos, state);
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (!world.isClient() && SculkUtils.isInactive(state) && entity.getType() != ModBase.WARDEN_ENTITY) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof ModSculkSensorBlockEntity sculk) sculk.setLastVibrationFrequency(SculkUtils.FREQUENCIES.get(GameEvent.STEP));
			SculkUtils.setActive(entity, world, pos, state, 15);
		}
		super.onSteppedOn(world, pos, state, entity);
	}

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (world.isClient() || state.isOf(oldState.getBlock())) return;
		if (state.get(POWER) > 0 && !world.getBlockTickScheduler().isQueued(pos, this)) {
			world.setBlockState(pos, state.with(POWER, 0), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
		}
		world.createAndScheduleBlockTick(new BlockPos(pos), state.getBlock(), 1);
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.isOf(newState.getBlock())) return;
		if (SculkUtils.getPhase(state) == SculkSensorPhase.ACTIVE) SculkUtils.updateNeighbors(world, pos);
		super.onStateReplaced(state, world, pos, newState, moved);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	@Nullable
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ModSculkSensorBlockEntity(pos, state);
	}

	@Nullable
	public <T extends BlockEntity> GameEventListener getGameEventListener(World world, T blockEntity) {
		if (blockEntity instanceof ModSculkSensorBlockEntity) return ((ModSculkSensorBlockEntity)blockEntity).getEventListener();
		return null;
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world2, BlockState state2, BlockEntityType<T> type) {
		if (!world2.isClient) {
			return checkType(type, ModBase.SCULK_SENSOR_ENTITY, (world, pos, state, blockEntity) -> blockEntity.getEventListener().tick(world));
		}
		return null;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return OUTLINE_SHAPE; }

	@Override
	public boolean emitsRedstonePower(BlockState state) { return true; }

	@Override
	public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) { return state.get(POWER); }

	public static void setCooldown(World world, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state.with(SCULK_SENSOR_PHASE, SculkSensorPhase.COOLDOWN).with(POWER, 0), Block.NOTIFY_ALL);
		world.createAndScheduleBlockTick(pos, state.getBlock(), 1);
		if (!state.get(WATERLOGGED)) {
			world.playSound(null, pos, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING_STOP, SoundCategory.BLOCKS, 1.0f, world.random.nextFloat() * 0.2f + 0.8f);
		}
		SculkUtils.updateNeighbors(world, pos);
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (SculkUtils.getPhase(state) != SculkSensorPhase.ACTIVE) return;
		Direction direction = Direction.random(random);
		if (direction == Direction.UP || direction == Direction.DOWN) return;
		double d = pos.getX() + 0.5 + (direction.getOffsetX() == 0 ? 0.5 - random.nextDouble() : direction.getOffsetX() * 0.6);
		double f = pos.getZ() + 0.5 + (direction.getOffsetZ() == 0 ? 0.5 - random.nextDouble() : direction.getOffsetZ() * 0.6);
		world.addParticle(DustColorTransitionParticleEffect.DEFAULT, d, pos.getY() + 0.25, f, 0.0, random.nextFloat() * 0.04, 0.0);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(SCULK_SENSOR_PHASE, POWER, WATERLOGGED);
	}

	@Override
	public boolean hasComparatorOutput(BlockState state) { return true; }

	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof ModSculkSensorBlockEntity sculk) {
			return SculkUtils.getPhase(state) == SculkSensorPhase.ACTIVE ? sculk.getLastVibrationFrequency() : 0;
		}
		return 0;
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }

	@Override
	public boolean hasSidedTransparency(BlockState state) { return true; }

	@Override
	public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
		super.onStacksDropped(state, world, pos, stack);
		if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) this.dropExperience(world, pos, 5);
	}
}
