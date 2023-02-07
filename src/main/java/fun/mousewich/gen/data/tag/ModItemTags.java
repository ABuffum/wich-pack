package fun.mousewich.gen.data.tag;

import fun.mousewich.ModBase;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItemTags {
	public static final TagKey<Item> AXES = createCommonTag("tools/axes");
	public static final TagKey<Item> BOOKS = createTag("books");
	public static final TagKey<Item> BOOKSHELF_BOOKS = createMinecraftTag("bookshelf_books");
	public static final TagKey<Item> BOOKSHELVES = createTag("bookshelves");
	public static final TagKey<Item> BOOTS = createTag("boots");
	public static final TagKey<Item> CARVED_MELONS = createTag("carved_melons");
	public static final TagKey<Item> CARVED_PUMPKINS = createTag("carved_pumpkins");
	public static final TagKey<Item> CHARRED_LOGS = createTag("charred_logs");
	public static final TagKey<Item> CHESTPLATES = createTag("chestplates");
	public static final TagKey<Item> COMPASSES = createMinecraftTag("compasses");
	public static final TagKey<Item> COOKED_BEEF = createTag("cooked_beef");
	public static final TagKey<Item> COOKED_BIRD = createTag("cooked_bird");
	public static final TagKey<Item> COOKED_FISH = createTag("cooked_fish");
	public static final TagKey<Item> COOKED_MEAT = createTag("cooked_meat");
	public static final TagKey<Item> COOKED_PORK = createTag("cooked_pork");
	public static final TagKey<Item> COOKED_RABBIT = createTag("cooked_rabbit");
	public static final TagKey<Item> COOKED_SEAFOOD = createTag("cooked_seafood");
	public static final TagKey<Item> COOKED_SHEEP = createTag("cooked_sheep");
	public static final TagKey<Item> EDIBLE_BEEF = createTag("edible_beef");
	public static final TagKey<Item> EDIBLE_BIRD = createTag("edible_bird");
	public static final TagKey<Item> EDIBLE_EGG = createTag("edible_egg");
	public static final TagKey<Item> EDIBLE_FISH = createTag("edible_fish");
	public static final TagKey<Item> EDIBLE_MEAT = createTag("edible_meat");
	public static final TagKey<Item> EDIBLE_MUSHROOMS = createTag("edible_mushrooms");
	public static final TagKey<Item> EDIBLE_PORK = createTag("edible_pork");
	public static final TagKey<Item> EDIBLE_RABBIT = createTag("edible_rabbit");
	public static final TagKey<Item> EDIBLE_SEAFOOD = createTag("edible_seafood");
	public static final TagKey<Item> EDIBLE_SHEEP = createTag("edible_sheep");
	public static final TagKey<Item> ELYTRA = createTag("elytra");
	public static final TagKey<Item> ENDER_TORCHES = createTag("ender_torches");
	public static final TagKey<Item> FEATHERS = createTag("feathers");
	public static final TagKey<Item> CAMPFIRES = createTag("campfires");
	public static final TagKey<Item> FLEECE = createTag("fleece");
	public static final TagKey<Item> FLEECE_SLABS = createTag("fleece_slabs");
	public static final TagKey<Item> FLEECE_CARPETS = createTag("fleece_carpets");
	public static final TagKey<Item> FRUITS = createTag("fruits");
	public static final TagKey<Item> GOLDEN_FOOD = createTag("golden_food");
	public static final TagKey<Item> GOLDEN_FRUIT = createTag("golden_fruit");
	public static final TagKey<Item> GOLDEN_VEGETABLES = createTag("golden_vegetables");
	public static final TagKey<Item> GRAINS = createTag("grains");
	public static final TagKey<Item> HEAD_WEARABLE_BLOCKS = createTag("head_wearable_blocks");
	public static final TagKey<Item> HELMETS = createTag("helmets");
	public static final TagKey<Item> HOES = createCommonTag("tools/hoes");
	public static final TagKey<Item> KNIVES = createCommonTag("tools/knives");
	public static final TagKey<Item> LEGGINGS = createTag("leggings");
	public static final TagKey<Item> MANGROVE_LOGS = createMinecraftTag("mangrove_logs");
	public static final TagKey<Item> PICKAXES = createCommonTag("tools/pickaxes");
	public static final TagKey<Item> RAW_BEEF = createTag("raw_beef");
	public static final TagKey<Item> RAW_BIRD = createTag("raw_bird");
	public static final TagKey<Item> RAW_FISH = createTag("raw_fish");
	public static final TagKey<Item> RAW_MEAT = createTag("raw_meat");
	public static final TagKey<Item> RAW_PORK = createTag("raw_pork");
	public static final TagKey<Item> RAW_RABBIT = createTag("raw_rabbit");
	public static final TagKey<Item> RAW_SEAFOOD = createTag("raw_seafood");
	public static final TagKey<Item> RAW_SHEEP = createTag("raw_sheep");
	public static final TagKey<Item> SEEDS = createTag("seeds");
	public static final TagKey<Item> SHEARS = createCommonTag("tools/shears");
	public static final TagKey<Item> SHOVELS = createCommonTag("tools/shovels");
	public static final TagKey<Item> SOUL_TORCHES = createTag("soul_torches");
	public static final TagKey<Item> SWEETS = createTag("sweets");
	public static final TagKey<Item> SWORDS = createCommonTag("tools/swords");
	public static final TagKey<Item> TORCHES = createTag("torches");
	public static final TagKey<Item> UNDERWATER_TORCHES = createTag("underwater_torches");
	public static final TagKey<Item> VEGETABLES = createTag("vegetables");
	public static final TagKey<Item> WEAPON = createTag("weapons");
	public static final TagKey<Item> WOOL_SLABS = createTag("wool_slabs");
	public static final TagKey<Item> WOOL_CARPETS = createTag("wool_carpets");

	public static final TagKey<Item> CROPTOPIA_NUTS = createTag("croptopia", "nuts");
	public static final TagKey<Item> FORGE_VEGETABLES = createTag("forge", "vegetables");
	public static final TagKey<Item> NOURISH_FRUIT = createTag("nourish", "fruit");
	public static final TagKey<Item> NOURISH_SWEETS = createTag("nourish", "sweets");
	public static final TagKey<Item> NOURISH_VEGETABLES = createTag("nourish", "vegetables");
	public static final TagKey<Item> ORIGINS_MEAT = createTag("origins", "meat");

	private static TagKey<Item> createTag(String name) { return TagKey.of(Registry.ITEM_KEY, ModBase.ID(name)); }
	private static TagKey<Item> createTag(String namespace, String path) { return TagKey.of(Registry.ITEM_KEY, new Identifier(namespace, path)); }
	private static TagKey<Item> createCommonTag(String name) { return TagKey.of(Registry.ITEM_KEY, new Identifier("c", name)); }
	private static TagKey<Item> createMinecraftTag(String name) { return TagKey.of(Registry.ITEM_KEY, new Identifier(name)); }
}
