package fun.mousewich.container;

import fun.mousewich.ModFactory;
import fun.mousewich.gen.data.loot.DropTable;
import fun.mousewich.gen.data.loot.BlockLootGenerator;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockContainer implements IBlockItemContainer {
	protected final Block block;
	public Block asBlock() { return block; }
	protected final Item item;
	public Item asItem() { return item; }

	public BlockContainer(Block block) { this(block, ModFactory.ItemSettings()); }
	public BlockContainer(Block block, Item.Settings settings) { this(block, new BlockItem(block,settings)); }
	public BlockContainer(Block block, Item item) {
		this.block = block;
		this.item = item;
	}

	public boolean contains(Block block) { return block == this.block; }
	public boolean contains(Item item) { return item == this.item; }

	public BlockContainer flammable(int burn, int spread) {
		FlammableBlockRegistry.getDefaultInstance().add(this.block, burn, spread);
		return this;
	}
	public BlockContainer fuel(int fuelTime) {
		FuelRegistry.INSTANCE.add(this.item, fuelTime);
		return this;
	}
	public BlockContainer compostable(float chance) {
		CompostingChanceRegistry.INSTANCE.add(this.item, chance);
		return this;
	}
	public BlockContainer dispenser(DispenserBehavior behavior) {
		DispenserBlock.registerBehavior(this.item, behavior);
		return this;
	}

	public BlockContainer drops(DropTable dropTable) {
		BlockLootGenerator.Drops.put(this.block, dropTable);
		return this;
	}
	public BlockContainer drops(Item item) { return drops(DropTable.Drops(item)); }
	public BlockContainer dropSelf() { return drops(this.item); }
	public BlockContainer dropSlabs() { return drops(DropTable.SLAB); }
	public BlockContainer requireSilkTouch() { return drops(DropTable.WithSilkTouch(this.item)); }
	public BlockContainer requireSilkTouchOrDrop(Item item) { return drops(DropTable.SilkTouchOrElse(item)); }
}
