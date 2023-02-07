package fun.mousewich.gen.world;

import fun.mousewich.ModBase;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.MiscPlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class ModBiomeFeatures {
	public static void addGrassAndClayDisks(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, ModBase.DISK_GRASS_PLACED.getRegistryEntry());
		builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, MiscPlacedFeatures.DISK_CLAY);
	}

	public static void addMangroveSwampFeatures(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModBase.TREES_MANGROVE_PLACED.getRegistryEntry());
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_GRASS_NORMAL);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_DEAD_BUSH);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_WATERLILY);
	}

	public static void addSculk(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ModBase.SCULK_VEIN_PLACED.getRegistryEntry());
		builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ModBase.SCULK_PATCH_DEEP_DARK_PLACED.getRegistryEntry());
	}
}
