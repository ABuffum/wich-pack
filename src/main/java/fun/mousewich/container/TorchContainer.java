package fun.mousewich.container;

import fun.mousewich.ModBase;
import fun.mousewich.block.torch.*;
import fun.mousewich.gen.data.loot.DropTable;
import fun.mousewich.gen.data.loot.BlockLootGenerator;
import fun.mousewich.util.OxidationScale;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.particle.ParticleEffect;

public class TorchContainer implements IBlockItemContainer {
	protected final LightableTorchBlock block;
	public Block asBlock() { return block; }
	protected final LightableWallTorchBlock wallBlock;
	public Block getWallBlock() { return wallBlock; }
	protected final Item item;
	public Item asItem() { return item; }

	public TorchContainer(Block.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		this(new LightableTorchBlock(blockSettings, particle), particle, itemSettings);
	}
	private TorchContainer(LightableTorchBlock block, ParticleEffect particle, Item.Settings itemSettings) {
		this(block, new LightableWallTorchBlock(Block.Settings.copy(block), particle), itemSettings);
	}
	private TorchContainer(LightableTorchBlock block, LightableWallTorchBlock wallBlock, Item.Settings itemSettings) {
		this.block = block;
		this.item = new WallStandingBlockItem(block, wallBlock, itemSettings);
		this.wallBlock = wallBlock;
	}

	public boolean contains(Block block) { return block == this.block || block == this.wallBlock; }
	public boolean contains(Item item) { return item == this.item; }

	public static TorchContainer Oxidizable(Oxidizable.OxidationLevel level, OxidationScale.BlockSettingsSupplier settings, ParticleEffect particle, Item.Settings itemSettings) {
		return Oxidizable(level, settings.get(level), particle, itemSettings);
	}
	public static TorchContainer Oxidizable(Oxidizable.OxidationLevel level, Block.Settings settings, ParticleEffect particle, Item.Settings itemSettings) {
		OxidizableTorchBlock block = new OxidizableTorchBlock(level, settings, particle);
		OxidizableWallTorchBlock wallBlock = new OxidizableWallTorchBlock(level, Block.Settings.copy(block), particle);
		return new TorchContainer(block, wallBlock, itemSettings);
	}

	public static TorchContainer Waterloggable(Block.Settings blockSettings, ParticleEffect particle) {
		return Waterloggable(blockSettings, particle, ModBase.ItemSettings());
	}
	public static TorchContainer Waterloggable(Block.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		WaterloggableTorchBlock block = new WaterloggableTorchBlock(blockSettings, particle);
		WaterloggableWallTorchBlock wallBlock = new WaterloggableWallTorchBlock(Block.Settings.copy(block), particle);
		return new TorchContainer(block, wallBlock, itemSettings).dropSelf();
	}

	public TorchContainer dropSelf() {
		BlockLootGenerator.Drops.put(this.block, DropTable.Drops(this.item));
		BlockLootGenerator.Drops.put(this.wallBlock, DropTable.Drops(this.item));
		return this;
	}
}
