package fun.mousewich.gen.world;

import fun.mousewich.ModBase;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.OceanPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.jetbrains.annotations.Nullable;

public class ModBiomeCreator {
	private static void addBasicFeatures(GenerationSettings.Builder generationSettings) {
		DefaultBiomeFeatures.addLandCarvers(generationSettings);
		DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
		DefaultBiomeFeatures.addDungeons(generationSettings);
		DefaultBiomeFeatures.addMineables(generationSettings);
		DefaultBiomeFeatures.addSprings(generationSettings);
		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
	}

	protected static int getSkyColor(float temperature) {
		float f = MathHelper.clamp(temperature / 3.0f, -1.0f, 1.0f);
		return MathHelper.hsvToRgb(0.62222224f - f * 0.05f, 0.5f + f * 0.1f, 1.0f);
	}
	private static Biome createBiome(Biome.Precipitation precipitation, float temperature, float downfall, SpawnSettings.Builder spawnSettings, GenerationSettings.Builder generationSettings, @Nullable MusicSound music) {
		return createBiome(precipitation, temperature, downfall, 4159204, 329011, spawnSettings, generationSettings, music);
	}
	private static Biome createBiome(Biome.Precipitation precipitation, float temperature, float downfall, int waterColor, int waterFogColor, SpawnSettings.Builder spawnSettings, GenerationSettings.Builder generationSettings, @Nullable MusicSound music) {
		return new Biome.Builder()
				.precipitation(precipitation)
				.temperature(temperature)
				.downfall(downfall)
				.effects(new BiomeEffects.Builder()
						.waterColor(waterColor)
						.waterFogColor(waterFogColor)
						.fogColor(12638463)
						.skyColor(getSkyColor(temperature))
						.moodSound(BiomeMoodSound.CAVE)
						.music(music)
						.build())
				.spawnSettings(spawnSettings.build())
				.generationSettings(generationSettings.build()).build();
	}

	public static Biome createMangroveSwamp() {//RegistryEntry<PlacedFeature> grassDisk) {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		DefaultBiomeFeatures.addBatsAndMonsters(builder);
		builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SLIME, 1, 1, 1));
		builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModBase.FROG_ENTITY, 10, 2, 5));
		builder.spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TROPICAL_FISH, 25, 8, 8));
		GenerationSettings.Builder builder2 = new GenerationSettings.Builder();
		DefaultBiomeFeatures.addFossils(builder2);
		addBasicFeatures(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2);
		ModBiomeFeatures.addGrassAndClayDisks(builder2);
		ModBiomeFeatures.addMangroveSwampFeatures(builder2);
		builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, OceanPlacedFeatures.SEAGRASS_SWAMP);
		MusicSound musicSound = MusicType.createIngameMusic(ModSoundEvents.MUSIC_OVERWORLD_SWAMP);
		return new Biome.Builder()
				.precipitation(Biome.Precipitation.RAIN).temperature(0.8f).downfall(0.9f)
				.category(Biome.Category.SWAMP).effects(new BiomeEffects.Builder()
						.waterColor(3832426).waterFogColor(5077600).fogColor(12638463).skyColor(7972863).foliageColor(9285927)
						.grassColorModifier(BiomeEffects.GrassColorModifier.SWAMP)
						.moodSound(BiomeMoodSound.CAVE).music(musicSound).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}

	public static Biome createDeepDark() {
		SpawnSettings.Builder builder = new SpawnSettings.Builder();
		GenerationSettings.Builder builder2 = new GenerationSettings.Builder();
		builder2.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE);
		builder2.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE_EXTRA_UNDERGROUND);
		builder2.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CANYON);
		DefaultBiomeFeatures.addAmethystGeodes(builder2);
		DefaultBiomeFeatures.addDungeons(builder2);
		DefaultBiomeFeatures.addMineables(builder2);
		DefaultBiomeFeatures.addFrozenTopLayer(builder2);
		DefaultBiomeFeatures.addPlainsTallGrass(builder2);
		DefaultBiomeFeatures.addDefaultOres(builder2, true);
		DefaultBiomeFeatures.addDefaultDisks(builder2);
		DefaultBiomeFeatures.addPlainsFeatures(builder2);
		DefaultBiomeFeatures.addDefaultMushrooms(builder2);
		DefaultBiomeFeatures.addDefaultVegetation(builder2);
		ModBiomeFeatures.addSculk(builder2);
		MusicSound musicSound = MusicType.createIngameMusic(ModSoundEvents.MUSIC_OVERWORLD_DEEP_DARK);
		return new Biome.Builder()
				.precipitation(Biome.Precipitation.RAIN).temperature(0.8f).downfall(0.4f)
				.category(Biome.Category.UNDERGROUND).effects(new BiomeEffects.Builder()
						.waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(7972863)
						.moodSound(BiomeMoodSound.CAVE).music(musicSound).build())
				.spawnSettings(builder.build()).generationSettings(builder2.build()).build();
	}
}
