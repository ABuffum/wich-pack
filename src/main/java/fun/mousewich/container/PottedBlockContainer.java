package fun.mousewich.container;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class PottedBlockContainer implements IBlockItemContainer {
	private final Block block;
	public Block asBlock() { return block; }
	private final Item item;
	public Item asItem() { return item; }
	private final Block potted;
	public Block getPottedBlock() { return potted; }

	public static final Block.Settings SETTINGS = AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque();

	public PottedBlockContainer(Block block, Item.Settings itemSettings) {
		this.block = block;
		item = new BlockItem(this.block, itemSettings);
		potted = new FlowerPotBlock(this.block, SETTINGS);
	}

	public boolean contains(Block block) { return block == this.block || block == this.potted; }
	public boolean contains(Item item) { return item == this.item; }
}
