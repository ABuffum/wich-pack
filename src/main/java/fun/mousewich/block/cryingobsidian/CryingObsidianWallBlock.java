package fun.mousewich.block.cryingobsidian;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class CryingObsidianWallBlock extends WallBlock {
	private final DefaultParticleType particle;
	public CryingObsidianWallBlock(Block block, DefaultParticleType particle) {
		this(Settings.copy(block), particle);
	}
	public CryingObsidianWallBlock(Settings settings, DefaultParticleType particle) {
		super(settings);
		this.particle = particle;
	}
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (random.nextInt(5) == 0) {
			Direction direction = Direction.random(random);
			if (direction != Direction.UP) {
				BlockPos blockPos = pos.offset(direction);
				BlockState blockState = world.getBlockState(blockPos);
				if (!state.isOpaque() || !blockState.isSideSolidFullSquare(world, blockPos, direction.getOpposite())) {
					double d = direction.getOffsetX() == 0 ? random.nextDouble() : 0.5D + (double)direction.getOffsetX() * 0.6D;
					double e = direction.getOffsetY() == 0 ? random.nextDouble() : 0.5D + (double)direction.getOffsetY() * 0.6D;
					double f = direction.getOffsetZ() == 0 ? random.nextDouble() : 0.5D + (double)direction.getOffsetZ() * 0.6D;
					world.addParticle(particle, (double)pos.getX() + d, (double)pos.getY() + e, (double)pos.getZ() + f, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
}
