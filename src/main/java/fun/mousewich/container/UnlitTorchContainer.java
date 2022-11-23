package fun.mousewich.container;

import fun.mousewich.ModDatagen;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class UnlitTorchContainer implements IContainer {
	private final Block block;
	public Block getBlock() { return block; }
	private final Block wallBlock;
	public Block getWallBlock() { return wallBlock; }

	public UnlitTorchContainer(Block block, Block wallBlock) {
		this.block = block;
		this.wallBlock = wallBlock;
	}

	public boolean contains(Block block) { return block == this.block || block == this.wallBlock; }
	public boolean contains(Item item) { return false; }

	public static Map<Block, Block> UNLIT_TORCHES = new HashMap<Block, Block>();
	public static Map<Block, Block> UNLIT_WALL_TORCHES = new HashMap<Block, Block>();

	public UnlitTorchContainer drops(Item item) {
		ModDatagen.BlockLootGenerator.Drops.put(this.block, item);
		ModDatagen.BlockLootGenerator.Drops.put(this.wallBlock, item);
		return this;
	}
	public UnlitTorchContainer dropNothing() {
		ModDatagen.BlockLootGenerator.DropNothing.add(this.block);
		ModDatagen.BlockLootGenerator.DropNothing.add(this.wallBlock);
		return this;
	}
}
