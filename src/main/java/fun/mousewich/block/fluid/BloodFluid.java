package fun.mousewich.block.fluid;

import fun.mousewich.ModBase;
import fun.mousewich.particle.ModParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class BloodFluid extends FlowableFluid {
	@Override
	public Fluid getStill() { return ModBase.STILL_BLOOD_FLUID; }
	@Override
	public Fluid getFlowing() { return ModBase.FLOWING_BLOOD_FLUID; }
	@Override
	public Item getBucketItem() { return ModBase.BLOOD_BUCKET; }
	@Override
	public boolean isInfinite() { return false; }
	@Override
	protected int getFlowSpeed(WorldView worldView) { return 2; }
	@Override
	protected float getBlastResistance() { return 100.0F; }
	@Override
	protected boolean canBeReplacedWith(FluidState fluidState, BlockView blockView, BlockPos blockPos, Fluid fluid, Direction direction) { return false; }
	@Override
	public int getTickRate(WorldView worldView) { return 5; }
	@Override
	protected int getLevelDecreasePerBlock(WorldView worldView) { return 1; }
	@Override
	protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
		final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
		Block.dropStacks(state, world, pos, blockEntity);
	}
	@Override
	public boolean matchesType(Fluid fluid) { return fluid == getStill() || fluid == getFlowing(); }
	@Override
	protected BlockState toBlockState(FluidState fluidState) {
		return ModBase.BLOOD_FLUID_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
	}
	@Override
	public Optional<SoundEvent> getBucketFillSound() { return Optional.of(SoundEvents.ITEM_BUCKET_FILL); }

	@Nullable
	public ParticleEffect getParticle() { return ModParticleTypes.DRIPPING_BLOOD; }

	public static class Flowing extends BloodFluid {
		@Override
		protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
			super.appendProperties(builder);
			builder.add(LEVEL);
		}
		@Override
		public int getLevel(FluidState fluidState) { return fluidState.get(LEVEL); }
		@Override
		public boolean isStill(FluidState fluidState) { return false; }
	}

	public static class Still extends BloodFluid {
		@Override
		public int getLevel(FluidState fluidState) { return 8; }
		@Override
		public boolean isStill(FluidState fluidState) { return true; }
	}
}

