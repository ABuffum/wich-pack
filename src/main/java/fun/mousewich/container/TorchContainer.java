package fun.mousewich.container;

import fun.mousewich.ModDatagen;
import fun.mousewich.block.torch.*;
import fun.mousewich.util.OxidationScale;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.particle.ParticleEffect;

public class TorchContainer extends WallBlockContainer implements IContainer {
	public TorchContainer(Block.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		this(new LightableTorchBlock(blockSettings, particle), particle, itemSettings);
	}
	private TorchContainer(Block block, ParticleEffect particle, Item.Settings itemSettings) {
		this(block, new LightableWallTorchBlock(Block.Settings.copy(block), particle), itemSettings);
	}
	private TorchContainer(Block block, Block wallBlock, Item.Settings itemSettings) {
		super(block, wallBlock, new WallStandingBlockItem(block, wallBlock, itemSettings));
	}

	public static TorchContainer Oxidizable(Oxidizable.OxidationLevel level, OxidationScale.BlockSettingsSupplier settings, ParticleEffect particle, Item.Settings itemSettings) {
		return Oxidizable(level, settings.get(level), particle, itemSettings);
	}
	public static TorchContainer Oxidizable(Oxidizable.OxidationLevel level, Block.Settings settings, ParticleEffect particle, Item.Settings itemSettings) {
		Block block = new OxidizableTorchBlock(level, settings, particle);
		Block wallBlock = new OxidizableWallTorchBlock(level, Block.Settings.copy(block), particle);
		return new TorchContainer(block, wallBlock, itemSettings);
	}

	public static TorchContainer Waterloggable(Block.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		Block block = new WaterloggableTorchBlock(blockSettings, particle);
		Block wallBlock = new WaterloggableWallTorchBlock(Block.Settings.copy(block), particle);
		return new TorchContainer(block, wallBlock, itemSettings);
	}

	public TorchContainer drops(Item item) {
		ModDatagen.BlockLootGenerator.Drops.put(this.getBlock(), item);
		ModDatagen.BlockLootGenerator.Drops.put(this.getWallBlock(), item);
		return this;
	}
	public TorchContainer dropSelf() { return drops(this.getItem()); }
	public TorchContainer dropNothing() {
		ModDatagen.BlockLootGenerator.DropNothing.add(this.getBlock());
		ModDatagen.BlockLootGenerator.DropNothing.add(this.getWallBlock());
		return this;
	}
}
