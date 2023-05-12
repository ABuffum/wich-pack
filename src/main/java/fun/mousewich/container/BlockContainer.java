package fun.mousewich.container;

import fun.mousewich.ModFactory;
import fun.mousewich.block.BlockConvertible;
import fun.mousewich.gen.data.ModDatagen;
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
import net.minecraft.tag.TagKey;
import net.minecraft.util.Pair;

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

	//Tags
	public BlockContainer blockTag(TagKey<Block> tag) { ModDatagen.Cache.Tag.Register(tag, this.block); return this; }
	public BlockContainer itemTag(TagKey<Item> tag) { ModDatagen.Cache.Tag.Register(tag, this.item); return this; }

	//<editor-fold desc="Models">
	public BlockContainer generatedItemModel() { ModDatagen.Cache.Model.GENERATED.add(this.item); return this; }

	public BlockContainer cubeAllModel() { ModDatagen.Cache.Model.CUBE_ALL.add(this); return this; }

	public BlockContainer buttonModel(BlockConvertible base) { return buttonModel(base.asBlock()); }
	public BlockContainer buttonModel(Block base) { ModDatagen.Cache.Model.BUTTON.add(new Pair<>(this, base)); return this; }

	public BlockContainer fenceModel(BlockConvertible base) { return fenceModel(base.asBlock()); }
	public BlockContainer fenceModel(Block base) { ModDatagen.Cache.Model.FENCE.add(new Pair<>(this, base)); return this; }

	public BlockContainer fenceGateModel(BlockConvertible base) { return fenceGateModel(base.asBlock()); }
	public BlockContainer fenceGateModel(Block base) { ModDatagen.Cache.Model.FENCE_GATE.add(new Pair<>(this, base)); return this; }

	public BlockContainer paneModel(BlockConvertible base) { return paneModel(base.asBlock()); }
	public BlockContainer paneModel(Block base) { ModDatagen.Cache.Model.PANE.add(new Pair<>(this, base)); return this; }

	public BlockContainer slabModel(BlockConvertible base) { return slabModel(base.asBlock()); }
	public BlockContainer slabModel(Block base) { ModDatagen.Cache.Model.SLAB.add(new Pair<>(this, base)); return this; }

	public BlockContainer stairsModel(BlockConvertible base) { return stairsModel(base.asBlock()); }
	public BlockContainer stairsModel(Block base) { ModDatagen.Cache.Model.STAIRS.add(new Pair<>(this, base)); return this; }

	public BlockContainer trapdoorModel(boolean orientable) { ModDatagen.Cache.Model.TRAPDOOR.add(new Pair<>(this, orientable)); return this; }

	public BlockContainer wallModel(BlockConvertible base) { return wallModel(base.asBlock()); }
	public BlockContainer wallModel(Block base) { ModDatagen.Cache.Model.WALL.add(new Pair<>(this, base)); return this; }

	public BlockContainer weightedPressurePlateModel(BlockConvertible base) { return weightedPressurePlateModel(base.asBlock()); }
	public BlockContainer weightedPressurePlateModel(Block base) { ModDatagen.Cache.Model.WEIGHTED_PRESSURE_PLATE.add(new Pair<>(this, base)); return this; }

	//Niche
	public BlockContainer bambooFenceModel() { ModDatagen.Cache.Model.BAMBOO_FENCE.add(this); return this; }
	public BlockContainer bambooFenceGateModel() { ModDatagen.Cache.Model.BAMBOO_FENCE_GATE.add(this); return this; }
	//</editor-fold>

	public BlockContainer drops(DropTable dropTable) {
		if (dropTable == null) return dropSelf();
		BlockLootGenerator.Drops.put(this.block, dropTable);
		return this;
	}
	public BlockContainer drops(Item item) { return drops(DropTable.Drops(item)); }
	public BlockContainer dropSelf() { return drops(this.item); }
	public BlockContainer dropSlabs() { return drops(DropTable.SLAB); }
	public BlockContainer requireSilkTouch() { return drops(DropTable.WithSilkTouch(this.item)); }
	public BlockContainer requireSilkTouchOrDrop(Item item) { return drops(DropTable.SilkTouchOrElse(item)); }
}
