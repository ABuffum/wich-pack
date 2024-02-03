package fun.wich.block.oxidizable;

import fun.wich.block.torch.LightableWallTorchBlock;
import fun.wich.util.OxidationScale;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

public class OxidizableWallTorchBlock extends LightableWallTorchBlock implements Oxidizable {
	private final OxidationLevel level;
	public OxidizableWallTorchBlock(OxidationLevel level, Settings settings, ParticleEffect particleEffect) {
		super(settings, particleEffect);
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
	public OxidationLevel getDegradationLevel() { return this.level; }
	@Override
	public Optional<BlockState> getDegradationResult(BlockState state) {
		return OxidationScale.getIncreasedBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}
}
