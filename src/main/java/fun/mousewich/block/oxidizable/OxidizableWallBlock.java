package fun.mousewich.block.oxidizable;

import fun.mousewich.util.OxidationScale;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.WallBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

public class OxidizableWallBlock extends WallBlock implements Oxidizable {
	private final Oxidizable.OxidationLevel level;
	public OxidizableWallBlock(Oxidizable.OxidationLevel level, Block block) {
		this(level, Settings.copy(block));
	}
	public OxidizableWallBlock(Oxidizable.OxidationLevel level, Settings settings) {
		super(settings);
		this.level = level;
	}
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.tickDegradation(state, world, pos, random);
	}
	public boolean hasRandomTicks(BlockState state) {
		return OxidationScale.getIncreasedBlock(state.getBlock()).isPresent();
	}
	public Oxidizable.OxidationLevel getDegradationLevel() { return this.level; }
	@Override
	public Optional<BlockState> getDegradationResult(BlockState state) {
		return OxidationScale.getIncreasedBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}
}
