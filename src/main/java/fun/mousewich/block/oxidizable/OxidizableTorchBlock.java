package fun.mousewich.block.oxidizable;

import fun.mousewich.block.torch.LightableTorchBlock;
import fun.mousewich.util.OxidationScale;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.Optional;
import java.util.Random;

public class OxidizableTorchBlock extends LightableTorchBlock implements Oxidizable {
	private final Oxidizable.OxidationLevel level;
	public OxidizableTorchBlock(Oxidizable.OxidationLevel level, Settings settings, ParticleEffect particle) {
		super(settings, particle);
		this.level = level;
	}
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.tickDegradation(state, world, pos, random);
	}
	@Override
	public boolean hasRandomTicks(BlockState state) {
		return OxidationScale.getIncreasedBlock(state.getBlock()).isPresent();
	}
	@Override
	public Oxidizable.OxidationLevel getDegradationLevel() { return this.level; }
	@Override
	public Optional<BlockState> getDegradationResult(BlockState state) {
		return OxidationScale.getIncreasedBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}
}
