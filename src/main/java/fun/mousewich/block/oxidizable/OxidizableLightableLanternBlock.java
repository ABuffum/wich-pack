package fun.mousewich.block.oxidizable;

import fun.mousewich.block.LightableLanternBlock;
import fun.mousewich.util.OxidationScale;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

public class OxidizableLightableLanternBlock extends LightableLanternBlock implements Oxidizable {
	private final OxidationLevel level;
	public OxidizableLightableLanternBlock(OxidationLevel level, Settings settings) { super(settings); this.level = level; }
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
