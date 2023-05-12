package fun.mousewich.gen.data.tag;

import fun.mousewich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockTags {
	public static final TagKey<Block> ALL_SIGNS = createMinecraftTag("all_signs");
	public static final TagKey<Block> ALL_HANGING_SIGNS = createMinecraftTag("all_hanging_signs");
	public static final TagKey<Block> ANCIENT_CITY_REPLACEABLE = createMinecraftTag("ancient_city_replaceable");
	public static final TagKey<Block> CEILING_HANGING_SIGNS = createMinecraftTag("ceiling_hanging_signs");
	public static final TagKey<Block> CHERRY_LOGS = createMinecraftTag("cherry_logs");
	public static final TagKey<Block> COMBINATION_STEP_SOUND_BLOCKS = createMinecraftTag("combination_step_sound_blocks");
	public static final TagKey<Block> CONVERTIBLE_TO_MUD = createMinecraftTag("convertible_to_mud");
	public static final TagKey<Block> DAMPENS_VIBRATIONS = createMinecraftTag("dampens_vibrations");
	public static final TagKey<Block> FROG_PREFER_JUMP_TO = createMinecraftTag("frog_prefer_jump_to");
	public static final TagKey<Block> FROGS_SPAWNABLE_ON = createMinecraftTag("frogs_spawnable_on");
	public static final TagKey<Block> MANGROVE_LOGS = createMinecraftTag("mangrove_logs");
	public static final TagKey<Block> MANGROVE_LOGS_CAN_GROW_THROUGH = createMinecraftTag("mangrove_logs_can_grow_through");
	public static final TagKey<Block> MANGROVE_ROOTS_CAN_GROW_THROUGH = createMinecraftTag("mangrove_roots_can_grow_through");
	public static final TagKey<Block> OCCLUDES_VIBRATION_SIGNALS = createMinecraftTag("occludes_vibration_signals");
	public static final TagKey<Block> OVERWORLD_NATURAL_LOGS = createMinecraftTag("overworld_natural_logs");
	public static final TagKey<Block> SCULK_REPLACEABLE = createMinecraftTag("sculk_replaceable");
	public static final TagKey<Block> SCULK_REPLACEABLE_WORLD_GEN = createMinecraftTag("sculk_replaceable_world_gen");
	public static final TagKey<Block> SNAPS_GOAT_HORN = createMinecraftTag("snaps_goat_horn");
	public static final TagKey<Block> SNIFFER_DIGGABLE_BLOCK = createMinecraftTag("sniffer_diggable_block");
	public static final TagKey<Block> SNIFFER_EGG_HATCH_BOOST = createMinecraftTag("sniffer_egg_hatch_boost");
	public static final TagKey<Block> TRAIL_RUINS_REPLACEABLE = createMinecraftTag("trail_ruins_replaceable");
	public static final TagKey<Block> VIBRATION_RESONATORS = createMinecraftTag("vibration_resonators");
	public static final TagKey<Block> WALL_HANGING_SIGNS = createMinecraftTag("wall_hanging_signs");
	public static final TagKey<Block> WOOL_CARPETS = createMinecraftTag("wool_carpets");

	public static final TagKey<Block> BARRELS = createTag("barrels");
	public static final TagKey<Block> BOOKSHELVES = createTag("bookshelves");
	public static final TagKey<Block> BROOM_SWEEPS = createTag("broom_sweeps");
	public static final TagKey<Block> CARVED_GOURDS = createTag("carved_gourds");
	public static final TagKey<Block> CARVED_MELONS = createTag("carved_melons");
	public static final TagKey<Block> CARVED_PUMPKINS = createTag("carved_pumpkins");
	public static final TagKey<Block> CASSIA_LOGS = createTag("cassia_logs");
	public static final TagKey<Block> CHARRED_LOGS = createTag("charred_logs");
	public static final TagKey<Block> CHISELED_BOOKSHELVES = createTag("chiseled_bookshelves");
	public static final TagKey<Block> COLD_BLOCKS = createTag("cold_blocks");
	public static final TagKey<Block> CRAFTING_TABLES = createTag("crafting_tables");
	public static final TagKey<Block> DOGWOOD_LOGS = createTag("dogwood_logs");
	public static final TagKey<Block> ECHO_SOUND_BLOCKS = createTag("echo_sound_blocks");
	public static final TagKey<Block> FLEECE = createTag("fleece");
	public static final TagKey<Block> FLEECE_CARPETS = createTag("fleece_carpets");
	public static final TagKey<Block> FLEECE_SLABS = createTag("fleece_slabs");
	public static final TagKey<Block> GOURDS = createTag("gourds");
	public static final TagKey<Block> GOURD_LANTERNS = createTag("gourd_lanterns");
	public static final TagKey<Block> HAMMER_MINEABLE = createTag("mineable/hammer");
	public static final TagKey<Block> INFLICTS_FIRE_DAMAGE = createTag("inflicts_fire_damage");
	public static final TagKey<Block> LECTERNS = createTag("lecterns");
	public static final TagKey<Block> LOG_SLABS = createTag("log_slabs");
	public static final TagKey<Block> POWDER_KEGS = createTag("powder_kegs");
	public static final TagKey<Block> PUMPKINS = createTag("pumpkins");
	public static final TagKey<Block> ROWS = createTag("rows");
	public static final TagKey<Block> RUBY_ORES = createTag("ruby_ores");
	public static final TagKey<Block> SCULK_TURFS = createTag("sculk_turfs");
	public static final TagKey<Block> SCULK_VEIN_CAN_PLACE_ON = createTag("sculk_vein_can_place_on");
	public static final TagKey<Block> SIZZLE_RAIN_BLOCKS = createTag("sizzle_rain_blocks");
	public static final TagKey<Block> SLIME_BLOCKS = createTag("slime_blocks");
	public static final TagKey<Block> STICKY = createTag("sticky");
	public static final TagKey<Block> VIBRATION_RESONATORS_ECHO = createTag("vibration_resonators_echo");
	public static final TagKey<Block> WOODEN_BEEHIVES = createTag("wooden_beehives");
	public static final TagKey<Block> WOOL_SLABS = createTag("wool_slabs");

	private static TagKey<Block> createTag(String name) { return TagKey.of(Registry.BLOCK_KEY, ModBase.ID(name)); }
	private static TagKey<Block> createTag(String namespace, String path) { return TagKey.of(Registry.BLOCK_KEY, new Identifier(namespace, path)); }
	private static TagKey<Block> createCommonTag(String name) { return TagKey.of(Registry.BLOCK_KEY,new Identifier("c", name)); }
	private static TagKey<Block> createMinecraftTag(String name) { return TagKey.of(Registry.BLOCK_KEY,new Identifier(name)); }

}
