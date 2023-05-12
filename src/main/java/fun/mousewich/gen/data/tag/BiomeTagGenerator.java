package fun.mousewich.gen.data.tag;

import fun.mousewich.ModBase;
import fun.mousewich.gen.data.ModDatagen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.Map;
import java.util.Set;

public class BiomeTagGenerator extends FabricTagProvider.DynamicRegistryTagProvider<Biome> {
	public BiomeTagGenerator(FabricDataGenerator dataGenerator) {
		super(dataGenerator, Registry.BIOME_KEY, "worldgen/biomes", ModBase.NAMESPACE + ":biome_tag_generator");
	}

	@Override
	protected void generateTags() {
		for (Map.Entry<TagKey<Biome>, Set<Biome>> entry : ModDatagen.Cache.Tag.BIOME_TAGS.entrySet()) {
			getOrCreateTagBuilder(entry.getKey()).add(entry.getValue().toArray(Biome[]::new));
			entry.getValue().clear();
		}
		ModDatagen.Cache.Tag.BIOME_TAGS.clear();

		getOrCreateTagBuilder(BiomeTags.IS_BADLANDS).add(BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS);
		getOrCreateTagBuilder(BiomeTags.IS_JUNGLE).add(BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE);
		getOrCreateTagBuilder(BiomeTags.IS_NETHER)
				.add(BiomeKeys.NETHER_WASTES, BiomeKeys.BASALT_DELTAS, BiomeKeys.SOUL_SAND_VALLEY)
				.add(BiomeKeys.CRIMSON_FOREST, BiomeKeys.WARPED_FOREST);
		getOrCreateTagBuilder(BiomeTags.IS_MOUNTAIN).add(ModBase.CHERRY_GROVE);
		getOrCreateTagBuilder(BiomeTags.MINESHAFT_HAS_STRUCTURE).add(ModBase.MANGROVE_SWAMP);
		getOrCreateTagBuilder(BiomeTags.RUINED_PORTAL_SWAMP_HAS_STRUCTURE).add(ModBase.MANGROVE_SWAMP);

		getOrCreateTagBuilder(ModBiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS).add(ModBase.MANGROVE_SWAMP);
		getOrCreateTagBuilder(ModBiomeTags.ANCIENT_CITY_HAS_STRUCTURE).add(ModBase.DEEP_DARK);
		getOrCreateTagBuilder(ModBiomeTags.HAS_CLOSER_WATER_FOG).add(ModBase.MANGROVE_SWAMP);
		getOrCreateTagBuilder(ModBiomeTags.IS_END)
				.add(BiomeKeys.THE_END, BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS)
				.add(BiomeKeys.SMALL_END_ISLANDS, BiomeKeys.END_BARRENS);
		getOrCreateTagBuilder(ModBiomeTags.IS_SAVANNA).add(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA);
		getOrCreateTagBuilder(ModBiomeTags.MINESHAFT_BLOCKING).add(ModBase.DEEP_DARK);
		getOrCreateTagBuilder(ModBiomeTags.SPAWNS_COLD_VARIANT_FROGS)
				.add(BiomeKeys.SNOWY_PLAINS, BiomeKeys.ICE_SPIKES, BiomeKeys.FROZEN_PEAKS, BiomeKeys.JAGGED_PEAKS)
				.add(BiomeKeys.SNOWY_SLOPES, BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.GROVE)
				.add(ModBase.DEEP_DARK)
				.add(BiomeKeys.FROZEN_RIVER, BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_BEACH)
				.addTag(ModBiomeTags.IS_END);
		getOrCreateTagBuilder(ModBiomeTags.SPAWNS_WARM_VARIANT_FROGS)
				.add(BiomeKeys.DESERT, BiomeKeys.WARM_OCEAN).addTag(BiomeTags.IS_JUNGLE)
				.add(ModBase.MANGROVE_SWAMP)
				.addTag(ModBiomeTags.IS_SAVANNA).addTag(BiomeTags.IS_NETHER).addTag(BiomeTags.IS_BADLANDS);
		getOrCreateTagBuilder(ModBiomeTags.TRAIL_RUINS_HAS_STRUCTURE)
				.add(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA, BiomeKeys.OLD_GROWTH_PINE_TAIGA)
				.add(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.JUNGLE);
		getOrCreateTagBuilder(ModBiomeTags.WARM_OCEANS).add(BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN);
		getOrCreateTagBuilder(ModBiomeTags.WATER_ON_MAP_OUTLINES).add(ModBase.MANGROVE_SWAMP);
	}
}