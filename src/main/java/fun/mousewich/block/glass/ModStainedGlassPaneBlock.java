package fun.mousewich.block.glass;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.PaneBlock;

public class ModStainedGlassPaneBlock extends PaneBlock {
	public ModStainedGlassPaneBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
	}
}
