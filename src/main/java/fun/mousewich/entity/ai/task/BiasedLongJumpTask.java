package fun.mousewich.entity.ai.task;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class BiasedLongJumpTask<E extends MobEntity> extends ModLongJumpTask<E> {
	private final TagKey<Block> favoredBlocks;
	private final float biasChance;
	private final List<Target> unfavoredTargets = new ArrayList<>();
	private boolean useBias;
	public BiasedLongJumpTask(UniformIntProvider cooldownRange, int verticalRange, int horizontalRange, float maxRange, Function<E, SoundEvent> entityToSound, TagKey<Block> favoredBlocks, float biasChance, Predicate<BlockState> jumpToPredicate) {
		super(cooldownRange, verticalRange, horizontalRange, maxRange, entityToSound, jumpToPredicate);
		this.favoredBlocks = favoredBlocks;
		this.biasChance = biasChance;
	}
	@Override
	protected void run(ServerWorld serverWorld, E mobEntity, long l) {
		super.run(serverWorld, mobEntity, l);
		this.unfavoredTargets.clear();
		this.useBias = mobEntity.getRandom().nextFloat() < this.biasChance;
	}
	@Override
	protected Optional<Target> getTarget(ServerWorld world) {
		if (!this.useBias) return super.getTarget(world);
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		while (!this.targets.isEmpty()) {
			Optional<ModLongJumpTask.Target> optional = super.getTarget(world);
			if (optional.isEmpty()) continue;
			ModLongJumpTask.Target target = optional.get();
			if (world.getBlockState(mutable.set(target.getPos(), Direction.DOWN)).isIn(this.favoredBlocks)) return optional;
			this.unfavoredTargets.add(target);
		}
		if (!this.unfavoredTargets.isEmpty()) return Optional.of(this.unfavoredTargets.remove(0));
		return Optional.empty();
	}
	@Override
	protected boolean canJumpTo(ServerWorld world, E entity, BlockPos pos) {
		return super.canJumpTo(world, entity, pos) && this.isFluidStateAndBelowEmpty(world, pos);
	}
	private boolean isFluidStateAndBelowEmpty(ServerWorld world, BlockPos pos) {
		return world.getFluidState(pos).isEmpty() && world.getFluidState(pos.down()).isEmpty();
	}
}
