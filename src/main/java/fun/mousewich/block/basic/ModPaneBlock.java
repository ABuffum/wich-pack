package fun.mousewich.block.basic;

import net.minecraft.block.Block;
import net.minecraft.block.PaneBlock;

public class ModPaneBlock extends PaneBlock {
	public ModPaneBlock(Block block) { this(Settings.copy(block)); }
	public ModPaneBlock(Settings settings) { super(settings); }
}
