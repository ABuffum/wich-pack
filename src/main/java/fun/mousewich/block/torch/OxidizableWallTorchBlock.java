package fun.mousewich.block.torch;

import fun.mousewich.util.OxidationScale;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

public class OxidizableWallTorchBlock extends LightableWallTorchBlock implements Oxidizable {
	private final Oxidizable.OxidationLevel oxidizationLevel;

	public OxidizableWallTorchBlock(Oxidizable.OxidationLevel oxidizationLevel, Settings settings, ParticleEffect particleEffect) {
		super(settings, particleEffect);
		this.oxidizationLevel = oxidizationLevel;
	}

	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.tickDegradation(state, world, pos, random);
	}

	public boolean hasRandomTicks(BlockState state) {
		return OxidationScale.getIncreasedOxidationBlock(state.getBlock()).isPresent();
	}

	public Oxidizable.OxidationLevel getDegradationLevel() { return this.oxidizationLevel; }

	@Override
	public Optional<BlockState> getDegradationResult(BlockState state) {
		return OxidationScale.getIncreasedOxidationBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}
}
