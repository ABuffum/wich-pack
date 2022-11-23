package fun.mousewich.container;

import fun.mousewich.ModDatagen;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockContainer implements IBlockItemContainer {
	private final Block block;
	public Block getBlock() { return block; }
	private final Item item;
	public Item getItem() { return item; }

	public BlockContainer(Block block, Item.Settings settings) {
		this.block = block;
		this.item = new BlockItem(block, settings);
	}
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
		FuelRegistry.INSTANCE.add(getItem(), fuelTime);
		return this;
	}
	public BlockContainer compostable(float chance) {
		CompostingChanceRegistry.INSTANCE.add(getItem(), chance);
		return this;
	}
	public BlockContainer dispenser(DispenserBehavior behavior) {
		DispenserBlock.registerBehavior(getItem(), behavior);
		return this;
	}

	public BlockContainer drops(Item item) {
		ModDatagen.BlockLootGenerator.Drops.put(this.block, item);
		return this;
	}
	public BlockContainer dropSelf() { return drops(this.item); }
	public BlockContainer dropNothing() {
		ModDatagen.BlockLootGenerator.DropNothing.add(this.block);
		return this;
	}
	public BlockContainer dropSlabs() {
		ModDatagen.BlockLootGenerator.DropSlabs.add(this.block);
		return this;
	}
	public BlockContainer requireSilkTouch() {
		ModDatagen.BlockLootGenerator.RequireSilkTouch.put(this.block, this.item);
		return this;
	}
	public BlockContainer silkTouchOr(Item item) {
		ModDatagen.BlockLootGenerator.SilkTouchOr.put(this.block, this.item);
		return this;
	}
}
