package fun.mousewich.container;

import fun.mousewich.ModDatagen;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.Item;

public class WallBlockContainer implements IBlockItemContainer {
	private final Block block;
	public Block getBlock() { return block; }
	private final Block wallBlock;
	public Block getWallBlock() { return wallBlock; }
	private final Item item;
	public Item getItem() { return item; }

	public WallBlockContainer(Block block, Block wallBlock, Item item) {
		this.block = block;
		this.item = item;
		this.wallBlock = wallBlock;
	}

	public boolean contains(Block block) { return block == this.block || block == this.wallBlock; }
	public boolean contains(Item item) { return item == this.item; }

	public WallBlockContainer flammable(int burn, int spread) {
		FlammableBlockRegistry.getDefaultInstance().add(this.block, burn, spread);
		FlammableBlockRegistry.getDefaultInstance().add(this.wallBlock, burn, spread);
		return this;
	}
	public WallBlockContainer fuel(int fuelTime) {
		FuelRegistry.INSTANCE.add(getItem(), fuelTime);
		return this;
	}
	public WallBlockContainer compostable(float chance) {
		CompostingChanceRegistry.INSTANCE.add(getItem(), chance);
		return this;
	}
	public WallBlockContainer dispenser(DispenserBehavior behavior) {
		DispenserBlock.registerBehavior(getItem(), behavior);
		return this;
	}

	public WallBlockContainer drops(Item item) {
		ModDatagen.BlockLootGenerator.Drops.put(this.block, item);
		ModDatagen.BlockLootGenerator.Drops.put(this.wallBlock, item);
		return this;
	}
	public WallBlockContainer dropSelf() { return drops(this.item); }
	public WallBlockContainer dropNothing() {
		ModDatagen.BlockLootGenerator.DropNothing.add(this.block);
		ModDatagen.BlockLootGenerator.DropNothing.add(this.wallBlock);
		return this;
	}
}
