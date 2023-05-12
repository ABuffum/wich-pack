package fun.mousewich.gen.data.loot;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static fun.mousewich.ModBase.*;

public class BlockLootGenerator extends SimpleFabricLootTableProvider {
	public static Map<Block, DropTable> Drops = new HashMap<>();

	public BlockLootGenerator(FabricDataGenerator dataGenerator) { super(dataGenerator, LootContextTypes.BLOCK); }

	@Override
	public void accept(BiConsumer<Identifier, LootTable.Builder> ibbc) {
		for (Map.Entry<Block, DropTable> drop : Drops.entrySet()) {
			Block block = drop.getKey();
			ibbc.accept(block.getLootTableId(), drop.getValue().get(block));
		}
		Drops.clear();
		//Extended Echo
		ibbc.accept(ECHO_CLUSTER.asBlock().getLootTableId(), BlockLootTableGenerator.dropsWithSilkTouch(ECHO_CLUSTER.asBlock(),
				ItemEntry.builder(ECHO_SHARD)
						.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4.0f)))
						.apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
						.conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ItemTags.CLUSTER_MAX_HARVESTABLES)))
						.alternatively(BlockLootTableGenerator.applyExplosionDecay(ECHO_CLUSTER.asBlock(), ItemEntry.builder(ECHO_SHARD).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)))))));
	}
}
