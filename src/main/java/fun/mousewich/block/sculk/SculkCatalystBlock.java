package fun.mousewich.block.sculk;

import fun.mousewich.ModBase;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SculkCatalystBlock extends BlockWithEntity {
	public static final int BLOOM_DURATION = 8;
	public static final BooleanProperty BLOOM = BooleanProperty.of("bloom");
	private final IntProvider experience = ConstantIntProvider.create(5);

	public SculkCatalystBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(BLOOM, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(BLOOM); }

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (state.get(BLOOM)) world.setBlockState(pos, state.with(BLOOM, false), Block.NOTIFY_ALL);
	}

	public static void bloom(ServerWorld world, BlockPos pos, BlockState state, Random random) {
		world.setBlockState(pos, state.with(BLOOM, true), Block.NOTIFY_ALL);
		world.createAndScheduleBlockTick(pos, state.getBlock(), BLOOM_DURATION);
		world.spawnParticles(ModBase.SCULK_SOUL_PARTICLE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.15, (double)pos.getZ() + 0.5, 2, 0.2, 0.0, 0.2, 0.0);
		world.playSound(null, pos, ModSoundEvents.BLOCK_SCULK_CATALYST_BLOOM, SoundCategory.BLOCKS, 2.0f, 0.6f + random.nextFloat() * 0.4f);
	}

	@Override
	@Nullable
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new SculkCatalystBlockEntity(pos, state);
	}

	@Nullable
	public <T extends BlockEntity> GameEventListener getGameEventListener(World world, T blockEntity) {
		return blockEntity instanceof SculkCatalystBlockEntity sculk ? sculk : null;
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		if (world.isClient) return null;
		return SculkCatalystBlock.checkType(type, ModBase.SCULK_CATALYST_ENTITY, SculkCatalystBlockEntity::tick);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }

	@Override
	public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
		super.onStacksDropped(state, world, pos, stack);
		this.dropExperience(world, pos, this.experience.get(world.random));
	}
}