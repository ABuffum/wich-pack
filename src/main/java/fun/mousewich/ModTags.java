package fun.mousewich;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;

public class ModTags {
	public static class Blocks {
		public static final TagKey<Block> ANCIENT_CITY_REPLACEABLE = createMinecraftTag("ancient_city_replaceable");
		public static final TagKey<Block> CARVED_GOURDS = createTag("carved_gourds");
		public static final TagKey<Block> CONVERTIBLE_TO_MUD = createTag("convertible_to_mud");
		public static final TagKey<Block> DAMPENS_VIBRATIONS = createTag("dampens_vibrations");
		public static final TagKey<Block> FLEECE = createTag("fleece");
		public static final TagKey<Block> GOURDS = createTag("gourds");
		public static final TagKey<Block> GOURD_LANTERNS = createTag("gourd_lanterns");
		public static final TagKey<Block> MANGROVE_LOGS_CAN_GROW_THROUGH = createMinecraftTag("mangrove_logs_can_grow_through");
		public static final TagKey<Block> MANGROVE_ROOTS_CAN_GROW_THROUGH = createMinecraftTag("mangrove_roots_can_grow_through");
		public static final TagKey<Block> OCCLUDES_VIBRATION_SIGNALS = createMinecraftTag("occludes_vibration_signals");
		public static final TagKey<Block> PUMPKINS = createTag("pumpkins");
		public static final TagKey<Block> SNAPS_GOAT_HORN = createTag("snaps_goat_horn");

		public static final TagKey<Block> SCULK_REPLACEABLE = createMinecraftTag("sculk_replaceable");
		public static final TagKey<Block> SCULK_REPLACEABLE_WORLD_GEN = createMinecraftTag("sculk_replaceable");

		public static final TagKey<Block> SCULK_TURFS = createTag("sculk_turfs");

		private static TagKey<Block> createTag(String name) {
			return TagKey.of(Registry.BLOCK_KEY, ModBase.ID(name));
		}
		private static TagKey<Block> createCommonTag(String name) {
			return TagKey.of(Registry.BLOCK_KEY,new Identifier("c", name));
		}
		private static TagKey<Block> createMinecraftTag(String name) {
			return TagKey.of(Registry.BLOCK_KEY,new Identifier(name));
		}
	}
	public static class Fluids {
		public static final TagKey<Fluid> BLOOD = createTag("blood");
		public static final TagKey<Fluid> MUD = createTag("mud");

		private static TagKey<Fluid> createTag(String name) {
			return TagKey.of(Registry.FLUID_KEY, ModBase.ID(name));
		}
		private static TagKey<Fluid> createCommonTag(String name) {
			return TagKey.of(Registry.FLUID_KEY, new Identifier("c", name));
		}
		private static TagKey<Fluid> createMinecraftTag(String name) {
			return TagKey.of(Registry.FLUID_KEY, new Identifier(name));
		}
	}
	public static class Items {
		public static final TagKey<Item> BOOKSHELF_BOOKS = createMinecraftTag("bookshelf_books");
		public static final TagKey<Item> CARVED_PUMPKINS = createTag("carved_pumpkins");
		public static final TagKey<Item> HEAD_WEARABLE_BLOCKS = createTag("head_wearable_blocks");
		public static final TagKey<Item> FLAVORED_MILK = createTag("flavored_milk");
		public static final TagKey<Item> FLEECE = createTag("fleece");
		public static final TagKey<Item> SEEDS = createTag("seeds");

		public static final TagKey<Item> AXES = createCommonTag("tools/axes");
		public static final TagKey<Item> HOES = createCommonTag("tools/hoes");
		public static final TagKey<Item> PICKAXES = createCommonTag("tools/pickaxes");
		public static final TagKey<Item> SHOVELS = createCommonTag("tools/shovels");
		public static final TagKey<Item> SWORDS = createCommonTag("tools/swords");
		public static final TagKey<Item> KNIVES = createCommonTag("tools/knives");
		public static final TagKey<Item> SHEARS = createCommonTag("tools/shears");

		private static TagKey<Item> createTag(String name) {
			return TagKey.of(Registry.ITEM_KEY, ModBase.ID(name));
		}
		private static TagKey<Item> createCommonTag(String name) {
			return TagKey.of(Registry.ITEM_KEY, new Identifier("c", name));
		}
		private static TagKey<Item> createMinecraftTag(String name) {
			return TagKey.of(Registry.ITEM_KEY, new Identifier(name));
		}
	}
	public static class Events {
		public static final TagKey<GameEvent> WARDEN_CAN_LISTEN = createMinecraftTag("warden_can_listen");
		public static final TagKey<GameEvent> SHRIEKER_CAN_LISTEN = createMinecraftTag("shrieker_can_listen");

		private static TagKey<GameEvent> createTag(String name) {
			return TagKey.of(Registry.GAME_EVENT_KEY, ModBase.ID(name));
		}
		private static TagKey<GameEvent> createCommonTag(String name) {
			return TagKey.of(Registry.GAME_EVENT_KEY, new Identifier("c", name));
		}
		private static TagKey<GameEvent> createMinecraftTag(String name) {
			return TagKey.of(Registry.GAME_EVENT_KEY, new Identifier(name));
		}
	}
	public static class Biomes {
		public static final TagKey<Biome> ANCIENT_CITY_HAS_STRUCTURE = createMinecraftTag("has_structure/ancient_city");
		public static final TagKey<Biome> MINESHAFT_BLOCKING = createMinecraftTag("mineshaft_blocking");
		public static final TagKey<Biome> SPAWNS_COLD_VARIANT_FROGS = createMinecraftTag("spawns_cold_variant_frogs");
		public static final TagKey<Biome> SPAWNS_WARM_VARIANT_FROGS = createMinecraftTag("spawns_warm_variant_frogs");

		private static TagKey<Biome> createTag(String name) {
			return TagKey.of(Registry.BIOME_KEY, ModBase.ID(name));
		}
		private static TagKey<Biome> createCommonTag(String name) {
			return TagKey.of(Registry.BIOME_KEY, new Identifier("c", name));
		}
		private static TagKey<Biome> createMinecraftTag(String name) {
			return TagKey.of(Registry.BIOME_KEY, new Identifier(name));
		}
	}
}
