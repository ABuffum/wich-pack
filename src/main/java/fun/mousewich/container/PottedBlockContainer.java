package fun.mousewich.container;

import fun.mousewich.gen.data.loot.BlockLootGenerator;
import fun.mousewich.gen.data.loot.DropTable;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
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

	public PottedBlockContainer(Block block, Item.Settings itemSettings) {
		this.block = block;
		item = new BlockItem(this.block, itemSettings);
		potted = new FlowerPotBlock(this.block, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	}

	public boolean contains(Block block) { return block == this.block || block == this.potted; }
	public boolean contains(Item item) { return item == this.item; }
	public PottedBlockContainer flammable(int burn, int spread) {
		FlammableBlockRegistry.getDefaultInstance().add(this.asBlock(), burn, spread);
		return this;
	}
	public PottedBlockContainer compostable(float chance) {
		CompostingChanceRegistry.INSTANCE.add(this.asItem(), chance);
		return this;
	}
	public PottedBlockContainer dropSelf() {
		BlockLootGenerator.Drops.put(this.asBlock(), DropTable.Drops(this.asItem()));
		BlockLootGenerator.Drops.put(this.getPottedBlock(), DropTable.Potted(this.asItem()));
		return this;
	}
}
