package fun.mousewich.block;

import fun.mousewich.block.basic.ModFallingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class SugarBlock extends ModFallingBlock {
	protected static final int COLOR = 0xFFFFFF;
	public SugarBlock(BlockConvertible block) { super(block, COLOR); }
	public SugarBlock(Block block) { super(block, COLOR); }
	public SugarBlock(Settings settings) { super(settings, COLOR); }
}
