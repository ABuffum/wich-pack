package fun.mousewich.gen.data.tag;

import fun.mousewich.ModBase;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class ModBiomeTags {
	public static final TagKey<Biome> ALLOWS_SURFACE_SLIME_SPAWNS = createMinecraftTag("allows_surface_slime_spawns");
	public static final TagKey<Biome> ANCIENT_CITY_HAS_STRUCTURE = createMinecraftTag("has_structure/ancient_city");
	public static final TagKey<Biome> HAS_CLOSER_WATER_FOG = createMinecraftTag("has_closer_water_fog");
	public static final TagKey<Biome> IS_END = createMinecraftTag("is_end");
	public static final TagKey<Biome> IS_SAVANNA = createMinecraftTag("is_savanna");
	public static final TagKey<Biome> MINESHAFT_BLOCKING = createMinecraftTag("mineshaft_blocking");
	public static final TagKey<Biome> SPAWNS_COLD_VARIANT_FROGS = createMinecraftTag("spawns_cold_variant_frogs");
	public static final TagKey<Biome> SPAWNS_WARM_VARIANT_FROGS = createMinecraftTag("spawns_warm_variant_frogs");
	public static final TagKey<Biome> WATER_ON_MAP_OUTLINES = createMinecraftTag("water_on_map_outlines");

	private static TagKey<Biome> createTag(String name) { return TagKey.of(Registry.BIOME_KEY, ModBase.ID(name)); }
	private static TagKey<Biome> createCommonTag(String name) {
		return TagKey.of(Registry.BIOME_KEY, new Identifier("c", name));
	}
	private static TagKey<Biome> createMinecraftTag(String name) {
		return TagKey.of(Registry.BIOME_KEY, new Identifier(name));
	}
}
