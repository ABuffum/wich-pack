package fun.mousewich.gen.data.loot;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.enums.BedPart;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

import static fun.mousewich.ModBase.ENDER_CAMPFIRE;
import static fun.mousewich.ModBase.ENDER_CANDLE;

public interface DropTable {
	LootTable.Builder get(Block block);

	static DropTable Drops(ItemConvertible item) {
		return (block) -> BlockLootTableGenerator.drops(item);
	}
	static DropTable CandleCake(Block candle) {
		return (block) -> BlockLootTableGenerator.candleCakeDrops(candle);
	}
	static DropTable WithSilkTouch(ItemConvertible item) {
		return (block) -> BlockLootTableGenerator.dropsWithSilkTouch(item);
	}
	static DropTable SilkTouchOrElse(ItemConvertible item) {
		return (block) -> BlockLootTableGenerator.drops(block, item);
	}

	DropTable BED = (block) -> BlockLootTableGenerator.dropsWithProperty(block, BedBlock.PART, BedPart.HEAD);
	DropTable BOOKSHELF = (block) -> BlockLootTableGenerator.drops(block, Items.BOOK, ConstantLootNumberProvider.create(3.0f));
	DropTable CAMPFIRE = (block) -> BlockLootTableGenerator.dropsWithSilkTouch(block, BlockLootTableGenerator.addSurvivesExplosionCondition(block,
			ItemEntry.builder(Items.CHARCOAL).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)))));
	DropTable CANDLE = BlockLootTableGenerator::candleDrops;
	DropTable DOOR = BlockLootTableGenerator::addDoorDrop;
	DropTable ENDER_CAMPFIRE = (block) -> BlockLootTableGenerator.dropsWithSilkTouch(block, BlockLootTableGenerator.addSurvivesExplosionCondition(block,
			ItemEntry.builder(Items.POPPED_CHORUS_FRUIT).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)))));
	DropTable NOTHING = (block) -> BlockLootTableGenerator.dropsNothing();
	DropTable SLAB = BlockLootTableGenerator::slabDrops;
	DropTable SOUL_CAMPFIRE = (block) -> BlockLootTableGenerator.dropsWithSilkTouch(block, BlockLootTableGenerator.addSurvivesExplosionCondition(block,
			ItemEntry.builder(Items.SOUL_SOIL).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)))));
}
