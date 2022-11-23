package fun.mousewich.block.basic;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;

public class ModWallBlock extends WallBlock {
	public ModWallBlock(Block block) { this(Settings.copy(block)); }
	public ModWallBlock(Settings settings) { super(settings); }
}
