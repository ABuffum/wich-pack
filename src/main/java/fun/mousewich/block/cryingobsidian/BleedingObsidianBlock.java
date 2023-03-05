package fun.mousewich.block.cryingobsidian;

import fun.mousewich.ModBase;
import fun.mousewich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class BleedingObsidianBlock extends Block {
	public BleedingObsidianBlock(BlockConvertible block) { this(block.asBlock()); }
	public BleedingObsidianBlock(Block block) { this(Settings.copy(block)); }
	public BleedingObsidianBlock(Settings settings) { super(settings); }

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
					world.addParticle(ModBase.DRIPPING_OBSIDIAN_BLOOD, (double)pos.getX() + d, (double)pos.getY() + e, (double)pos.getZ() + f, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
}
