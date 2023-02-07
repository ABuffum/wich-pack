package fun.mousewich.block.basic;

import fun.mousewich.container.IBlockItemContainer;
import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;

public class ModWallBlock extends WallBlock {
	public ModWallBlock(IBlockItemContainer block) { this(block.asBlock()); }
	public ModWallBlock(Block block) { this(Settings.copy(block)); }
	public ModWallBlock(Settings settings) { super(settings); }
}