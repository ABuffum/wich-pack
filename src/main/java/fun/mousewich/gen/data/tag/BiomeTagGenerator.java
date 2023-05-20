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
		for (Map.Entry<TagKey<Biome>, Set<Biome>> entry : ModDatagen.Cache.Tags.BIOME_TAGS.entrySet()) {
			getOrCreateTagBuilder(entry.getKey()).add(entry.getValue().toArray(Biome[]::new));
			entry.getValue().clear();
		}
		ModDatagen.Cache.Tags.BIOME_TAGS.clear();

		getOrCreateTagBuilder(BiomeTags.IS_BADLANDS).add(BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS);
		getOrCreateTagBuilder(BiomeTags.IS_JUNGLE).add(BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE);
		getOrCreateTagBuilder(BiomeTags.IS_NETHER).add(BiomeKeys.NETHER_WASTES, BiomeKeys.BASALT_DELTAS, BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.CRIMSON_FOREST, BiomeKeys.WARPED_FOREST);
		getOrCreateTagBuilder(BiomeTags.IS_MOUNTAIN).add(ModBase.CHERRY_GROVE);
		getOrCreateTagBuilder(BiomeTags.IS_DEEP_OCEAN).add(BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN);
		getOrCreateTagBuilder(BiomeTags.IS_OCEAN).addTag(BiomeTags.IS_DEEP_OCEAN).add(BiomeKeys.FROZEN_OCEAN, BiomeKeys.OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN);
		getOrCreateTagBuilder(BiomeTags.IS_RIVER).add(BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER);
		getOrCreateTagBuilder(BiomeTags.MINESHAFT_HAS_STRUCTURE).add(ModBase.MANGROVE_SWAMP);
		getOrCreateTagBuilder(BiomeTags.RUINED_PORTAL_SWAMP_HAS_STRUCTURE).add(ModBase.MANGROVE_SWAMP);

		getOrCreateTagBuilder(ModBiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS).add(BiomeKeys.SWAMP, ModBase.MANGROVE_SWAMP);
		getOrCreateTagBuilder(ModBiomeTags.ANCIENT_CITY_HAS_STRUCTURE).add(ModBase.DEEP_DARK);
		getOrCreateTagBuilder(ModBiomeTags.HAS_CLOSER_WATER_FOG).add(BiomeKeys.SWAMP, ModBase.MANGROVE_SWAMP);
		getOrCreateTagBuilder(ModBiomeTags.INCREASED_FIRE_BURNOUT)
				.add(BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.MUSHROOM_FIELDS, ModBase.MANGROVE_SWAMP, BiomeKeys.SNOWY_SLOPES)
				.add(BiomeKeys.FROZEN_PEAKS, BiomeKeys.JAGGED_PEAKS, BiomeKeys.SWAMP, BiomeKeys.JUNGLE);
		getOrCreateTagBuilder(ModBiomeTags.IS_END)
				.add(BiomeKeys.THE_END, BiomeKeys.END_HIGHLANDS, BiomeKeys.END_MIDLANDS)
				.add(BiomeKeys.SMALL_END_ISLANDS, BiomeKeys.END_BARRENS);
		getOrCreateTagBuilder(ModBiomeTags.IS_OVERWORLD)
				.add(BiomeKeys.MUSHROOM_FIELDS, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_COLD_OCEAN)
				.add(BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN)
				.add(BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN, BiomeKeys.STONY_SHORE, BiomeKeys.SWAMP)
				.add(BiomeKeys.SNOWY_SLOPES, BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_BEACH, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS)
				.add(BiomeKeys.GROVE, BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.SNOWY_TAIGA, BiomeKeys.WINDSWEPT_FOREST)
				.add(BiomeKeys.TAIGA, BiomeKeys.PLAINS, BiomeKeys.MEADOW, BiomeKeys.BEACH, BiomeKeys.FOREST)
				.add(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeKeys.FLOWER_FOREST, BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST)
				.add(BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.SAVANNA, BiomeKeys.JUNGLE, BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS)
				.add(BiomeKeys.DESERT, BiomeKeys.WOODED_BADLANDS, BiomeKeys.JAGGED_PEAKS, BiomeKeys.STONY_PEAKS)
				.add(BiomeKeys.FROZEN_RIVER, BiomeKeys.RIVER, BiomeKeys.ICE_SPIKES, BiomeKeys.OLD_GROWTH_PINE_TAIGA)
				.add(BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE)
				.add(BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.FROZEN_PEAKS, BiomeKeys.DRIPSTONE_CAVES, BiomeKeys.LUSH_CAVES)
				.add(ModBase.DEEP_DARK, ModBase.MANGROVE_SWAMP, ModBase.CHERRY_GROVE);
		getOrCreateTagBuilder(ModBiomeTags.IS_SAVANNA).add(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA);
		getOrCreateTagBuilder(ModBiomeTags.MINESHAFT_BLOCKING).add(ModBase.DEEP_DARK);
		getOrCreateTagBuilder(ModBiomeTags.SPAWNS_COLD_VARIANT_FROGS)
				.add(BiomeKeys.SNOWY_PLAINS, BiomeKeys.ICE_SPIKES, BiomeKeys.FROZEN_PEAKS, BiomeKeys.JAGGED_PEAKS)
				.add(BiomeKeys.SNOWY_SLOPES, BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.GROVE)
				.add(BiomeKeys.FROZEN_RIVER, BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_BEACH)
				.add(ModBase.DEEP_DARK)
				.addTag(ModBiomeTags.IS_END);
		getOrCreateTagBuilder(ModBiomeTags.SPAWNS_WARM_VARIANT_FROGS)
				.add(BiomeKeys.DESERT, BiomeKeys.WARM_OCEAN).addTag(BiomeTags.IS_JUNGLE)
				.add(ModBase.MANGROVE_SWAMP)
				.addTag(ModBiomeTags.IS_SAVANNA).addTag(BiomeTags.IS_NETHER).addTag(BiomeTags.IS_BADLANDS);
		getOrCreateTagBuilder(ModBiomeTags.TRAIL_RUINS_HAS_STRUCTURE)
				.add(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA, BiomeKeys.OLD_GROWTH_PINE_TAIGA)
				.add(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.JUNGLE);
		getOrCreateTagBuilder(ModBiomeTags.WARM_OCEANS).add(BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN);
		getOrCreateTagBuilder(ModBiomeTags.WATER_ON_MAP_OUTLINES)
				.addTag(BiomeTags.IS_OCEAN).addTag(BiomeTags.IS_RIVER)
				.add(BiomeKeys.SWAMP, ModBase.MANGROVE_SWAMP);
	}
}