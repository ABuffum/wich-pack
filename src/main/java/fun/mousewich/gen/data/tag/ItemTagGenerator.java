package fun.mousewich.gen.data.tag;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.nhoryzon.mc.farmersdelight.tag.Tags;
import fun.mousewich.container.BlockContainer;
import fun.mousewich.trim.ArmorTrimPattern;
import fun.mousewich.util.ColorUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;

import static fun.mousewich.ModBase.*;

public class ItemTagGenerator extends FabricTagProvider<Item> {
	public ItemTagGenerator(FabricDataGenerator dataGenerator) {
		super(dataGenerator, Registry.ITEM, "items", NAMESPACE + ":item_tag_generator");
	}

	private static Identifier betterend(String path) { return new Identifier("betterend", path); }
	private static Identifier betternether(String path) { return new Identifier("betternether", path); }
	private static Identifier croptopia(String path) { return new Identifier("croptopia", path); }

	@Override
	protected void generateTags() {
		getOrCreateTagBuilder(ItemTags.ARROWS).add(AMETHYST_ARROW.asItem(), CHORUS_ARROW.asItem());
		//getOrCreateTagBuilder(ItemTags.BEDS).add(MOSS_BED.asItem(), RAINBOW_BED.asItem());
		getOrCreateTagBuilder(ItemTags.BOATS)
				.add(CHARRED_BOAT.asItem())
				.add(MANGROVE_BOAT.asItem(), CHERRY_BOAT.asItem())
				.add(BAMBOO_RAFT.asItem());
		getOrCreateTagBuilder(ItemTags.BUTTONS)
				.add(NETHERITE_BUTTON.asItem());
		getOrCreateTagBuilder(ItemTags.CANDLES).add(SOUL_CANDLE.asItem(), ENDER_CANDLE.asItem());
		getOrCreateTagBuilder(ItemTags.CARPETS).addTag(ModItemTags.WOOL_CARPETS).addTag(ModItemTags.FLEECE_CARPETS);
		getOrCreateTagBuilder(ItemTags.CLUSTER_MAX_HARVESTABLES).addTag(ModItemTags.PICKAXES);
		getOrCreateTagBuilder(ItemTags.FISHES).addTag(ModItemTags.RAW_FISH).addTag(ModItemTags.COOKED_FISH);
		getOrCreateTagBuilder(ItemTags.LEAVES).add(MANGROVE_LEAVES.asItem(), CHERRY_LEAVES.asItem());
		getOrCreateTagBuilder(ItemTags.LOGS).addTag(ModItemTags.CHARRED_LOGS);
		getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN).addTag(ModItemTags.MANGROVE_LOGS).addTag(ModItemTags.CHERRY_LOGS);
		getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(MUSIC_DISC_5);
		getOrCreateTagBuilder(ItemTags.NON_FLAMMABLE_WOOD).add(CRIMSON_HANGING_SIGN.asItem(), WARPED_HANGING_SIGN.asItem());
		getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED)
				.add(POLISHED_GILDED_BLACKSTONE.asItem(), POLISHED_GILDED_BLACKSTONE_BRICKS.asItem())
				.add(CHISELED_POLISHED_GILDED_BLACKSTONE.asItem(), CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS.asItem())
				.add(GILDED_BLACKSTONE_SLAB.asItem(), POLISHED_GILDED_BLACKSTONE_SLAB.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_SLAB.asItem())
				.add(GILDED_BLACKSTONE_STAIRS.asItem(), POLISHED_GILDED_BLACKSTONE_STAIRS.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS.asItem())
				.add(GILDED_BLACKSTONE_WALL.asItem(), POLISHED_GILDED_BLACKSTONE_WALL.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_WALL.asItem())
				.add(GOLD_BARS.asItem(), GOLD_CHAIN.asItem())
				.add(GOLD_LANTERN.asItem(), GOLD_SOUL_LANTERN.asItem(), GOLD_ENDER_LANTERN.asItem())
				.add(GOLD_BUTTON.asItem())
				.add(GOLD_WALL.asItem(), GOLD_BRICKS.asItem(), GOLD_BRICK_SLAB.asItem())
				.add(GOLD_BRICK_STAIRS.asItem(), GOLD_BRICK_WALL.asItem(), CUT_GOLD.asItem())
				.add(CUT_GOLD_PILLAR.asItem(), CUT_GOLD_SLAB.asItem(), CUT_GOLD_STAIRS.asItem())
				.add(CUT_GOLD_WALL.asItem());
		getOrCreateTagBuilder(ItemTags.PIGLIN_REPELLENTS).addTag(ModItemTags.SOUL_TORCHES)
				.add(WHITE_IRON_SOUL_LANTERN.asItem(), GOLD_SOUL_LANTERN.asItem(), NETHERITE_SOUL_LANTERN.asItem())
				.add(COPPER_SOUL_LANTERN.asItem(), EXPOSED_COPPER_SOUL_LANTERN.asItem())
				.add(WEATHERED_COPPER_SOUL_LANTERN.asItem(), OXIDIZED_COPPER_SOUL_LANTERN.asItem())
				.add(WAXED_COPPER_SOUL_LANTERN.asItem(), WAXED_EXPOSED_COPPER_SOUL_LANTERN.asItem())
				.add(WAXED_WEATHERED_COPPER_SOUL_LANTERN.asItem(), WAXED_OXIDIZED_COPPER_SOUL_LANTERN.asItem());
		getOrCreateTagBuilder(ItemTags.PLANKS)
				.add(CHARRED_PLANKS.asItem())
				.add(MANGROVE_PLANKS.asItem(), BAMBOO_PLANKS.asItem(), CHERRY_PLANKS.asItem());
		getOrCreateTagBuilder(ItemTags.SIGNS)
				.add(CHARRED_SIGN.asItem())
				.add(MANGROVE_SIGN.asItem(), BAMBOO_SIGN.asItem(), CHERRY_SIGN.asItem());
		getOrCreateTagBuilder(ItemTags.SLABS)
				.add(SMOOTH_PURPUR_SLAB.asItem(), PURPUR_BRICK_SLAB.asItem())
				.add(CALCITE_SLAB.asItem(), SMOOTH_CALCITE_SLAB.asItem(), CALCITE_BRICK_SLAB.asItem())
				.add(DRIPSTONE_SLAB.asItem(), SMOOTH_DRIPSTONE_SLAB.asItem(), DRIPSTONE_BRICK_SLAB.asItem())
				.add(TUFF_SLAB.asItem(), SMOOTH_TUFF_SLAB.asItem(), TUFF_BRICK_SLAB.asItem())
				.add(AMETHYST_SLAB.asItem(), AMETHYST_BRICK_SLAB.asItem(), AMETHYST_CRYSTAL_SLAB.asItem())
				.add(TINTED_GLASS_SLAB.asItem())
				.add(GLASS_SLAB.asItem()).add(COAL_SLAB.asItem(), CHARCOAL_SLAB.asItem(), COARSE_DIRT_SLAB.asItem())
				.add(STAINED_GLASS_SLABS.values().stream().map(BlockContainer::asItem).toArray(Item[]::new))
				.add(EMERALD_BRICK_SLAB.asItem(), CUT_EMERALD_SLAB.asItem())
				.add(DIAMOND_SLAB.asItem(), DIAMOND_BRICK_SLAB.asItem())
				.add(QUARTZ_BRICK_SLAB.asItem(), QUARTZ_CRYSTAL_SLAB.asItem())
				.add(FLINT_SLAB.asItem(), FLINT_BRICK_SLAB.asItem())
				.add(RAW_COPPER_SLAB.asItem(), RAW_GOLD_SLAB.asItem(), RAW_IRON_SLAB.asItem())
				.add(IRON_SLAB.asItem(), IRON_BRICK_SLAB.asItem(), CUT_IRON_SLAB.asItem())
				.add(GOLD_SLAB.asItem(), GOLD_BRICK_SLAB.asItem(), CUT_GOLD_SLAB.asItem())
				.add(NETHERITE_SLAB.asItem(), NETHERITE_BRICK_SLAB.asItem(), CUT_NETHERITE_SLAB.asItem())
				.add(OBSIDIAN_SLAB.asItem(), CRYING_OBSIDIAN_SLAB.asItem(), BLEEDING_OBSIDIAN_SLAB.asItem())
				.add(ECHO_SLAB.asItem(), ECHO_CRYSTAL_SLAB.asItem())
				.add(GILDED_BLACKSTONE_SLAB.asItem(), POLISHED_GILDED_BLACKSTONE_SLAB.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_SLAB.asItem())
				.add(SCULK_STONE_SLAB.asItem(), SCULK_STONE_BRICK_SLAB.asItem())
				.addTag(ModItemTags.WOOL_SLABS).addTag(ModItemTags.FLEECE_SLABS)
				.add(MOSS_SLAB.asItem())
				.add(MUD_BRICK_SLAB.asItem());
		getOrCreateTagBuilder(ItemTags.SMALL_FLOWERS)
				.add(BUTTERCUP.asItem(), PINK_DAISY.asItem(), ROSE.asItem(), BLUE_ROSE.asItem())
				.add(MAGENTA_TULIP.asItem(), MARIGOLD.asItem(), INDIGO_ORCHID.asItem(), MAGENTA_ORCHID.asItem())
				.add(ORANGE_ORCHID.asItem(), PURPLE_ORCHID.asItem(), RED_ORCHID.asItem(), WHITE_ORCHID.asItem())
				.add(YELLOW_ORCHID.asItem(), PINK_ALLIUM.asItem(), LAVENDER.asItem(), HYDRANGEA.asItem())
				.add(PAEONIA.asItem(), ASTER.asItem());
		getOrCreateTagBuilder(ItemTags.STAIRS)
				.add(SMOOTH_PURPUR_STAIRS.asItem(), PURPUR_BRICK_STAIRS.asItem())
				.add(CALCITE_STAIRS.asItem(), SMOOTH_CALCITE_STAIRS.asItem(), CALCITE_BRICK_STAIRS.asItem())
				.add(DRIPSTONE_STAIRS.asItem(), SMOOTH_DRIPSTONE_STAIRS.asItem(), DRIPSTONE_BRICK_STAIRS.asItem())
				.add(TUFF_STAIRS.asItem(), SMOOTH_TUFF_STAIRS.asItem(), TUFF_BRICK_STAIRS.asItem())
				.add(AMETHYST_STAIRS.asItem(), AMETHYST_BRICK_STAIRS.asItem(), AMETHYST_CRYSTAL_STAIRS.asItem())
				.add(EMERALD_BRICK_STAIRS.asItem(), CUT_EMERALD_STAIRS.asItem())
				.add(DIAMOND_STAIRS.asItem(), DIAMOND_BRICK_STAIRS.asItem())
				.add(QUARTZ_BRICK_STAIRS.asItem(), QUARTZ_CRYSTAL_STAIRS.asItem())
				.add(FLINT_BRICK_STAIRS.asItem())
				.add(IRON_STAIRS.asItem(), IRON_BRICK_STAIRS.asItem(), CUT_IRON_STAIRS.asItem())
				.add(GOLD_STAIRS.asItem(), GOLD_BRICK_STAIRS.asItem(), CUT_GOLD_STAIRS.asItem())
				.add(NETHERITE_STAIRS.asItem(), NETHERITE_BRICK_STAIRS.asItem(), CUT_NETHERITE_STAIRS.asItem())
				.add(OBSIDIAN_STAIRS.asItem(), CRYING_OBSIDIAN_STAIRS.asItem(), BLEEDING_OBSIDIAN_STAIRS.asItem())
				.add(ECHO_STAIRS.asItem(), ECHO_CRYSTAL_STAIRS.asItem())
				.add(GILDED_BLACKSTONE_STAIRS.asItem(), POLISHED_GILDED_BLACKSTONE_STAIRS.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS.asItem())
				.add(SCULK_STONE_STAIRS.asItem(), SCULK_STONE_BRICK_STAIRS.asItem())
				.add(MUD_BRICK_STAIRS.asItem());
		getOrCreateTagBuilder(ItemTags.TALL_FLOWERS)
				.add(AMARANTH.asItem(), BLUE_ROSE_BUSH.asItem(), TALL_ALLIUM.asItem(), TALL_PINK_ALLIUM.asItem());
		getOrCreateTagBuilder(ItemTags.WALLS)
				.add(POLISHED_ANDESITE_WALL.asItem(), POLISHED_DIORITE_WALL.asItem(), POLISHED_GRANITE_WALL.asItem())
				.add(SMOOTH_PURPUR_WALL.asItem(), PURPUR_BRICK_WALL.asItem())
				.add(SMOOTH_SANDSTONE_WALL.asItem(), SMOOTH_RED_SANDSTONE_WALL.asItem())
				.add(DARK_PRISMARINE_WALL.asItem(), PURPUR_WALL.asItem())
				.add(CALCITE_WALL.asItem(), SMOOTH_CALCITE_WALL.asItem(), CALCITE_BRICK_WALL.asItem())
				.add(DRIPSTONE_WALL.asItem(), SMOOTH_DRIPSTONE_WALL.asItem(), DRIPSTONE_BRICK_WALL.asItem())
				.add(TUFF_WALL.asItem(), SMOOTH_TUFF_WALL.asItem(), TUFF_BRICK_WALL.asItem())
				.add(AMETHYST_WALL.asItem(), AMETHYST_BRICK_WALL.asItem(), AMETHYST_CRYSTAL_WALL.asItem())
				.add(EMERALD_BRICK_WALL.asItem(), CUT_EMERALD_WALL.asItem())
				.add(DIAMOND_WALL.asItem(), DIAMOND_BRICK_WALL.asItem())
				.add(SMOOTH_QUARTZ_WALL.asItem(), QUARTZ_BRICK_WALL.asItem(), QUARTZ_WALL.asItem(), QUARTZ_CRYSTAL_WALL.asItem())
				.add(FLINT_BRICK_WALL.asItem())
				.add(IRON_WALL.asItem(), IRON_BRICK_WALL.asItem(), CUT_IRON_WALL.asItem())
				.add(GOLD_WALL.asItem(), GOLD_BRICK_WALL.asItem(), CUT_GOLD_WALL.asItem())
				.add(NETHERITE_WALL.asItem(), NETHERITE_BRICK_WALL.asItem(), CUT_NETHERITE_WALL.asItem())
				.add(OBSIDIAN_WALL.asItem(), CRYING_OBSIDIAN_WALL.asItem(), BLEEDING_OBSIDIAN_WALL.asItem())
				.add(COPPER_WALL.asItem(), EXPOSED_COPPER_WALL.asItem())
				.add(WEATHERED_COPPER_WALL.asItem(), OXIDIZED_COPPER_WALL.asItem())
				.add(WAXED_COPPER_WALL.asItem(), WAXED_EXPOSED_COPPER_WALL.asItem())
				.add(WAXED_WEATHERED_COPPER_WALL.asItem(), WAXED_OXIDIZED_COPPER_WALL.asItem())
				.add(ECHO_WALL.asItem(), ECHO_CRYSTAL_WALL.asItem())
				.add(GILDED_BLACKSTONE_WALL.asItem(), POLISHED_GILDED_BLACKSTONE_WALL.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_WALL.asItem())
				.add(SCULK_STONE_WALL.asItem(), SCULK_STONE_BRICK_WALL.asItem())
				.add(MUD_BRICK_WALL.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS)
				.add(CHARRED_BUTTON.asItem())
				.add(MANGROVE_BUTTON.asItem(), BAMBOO_BUTTON.asItem(), CHERRY_BUTTON.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_DOORS)
				.add(CHARRED_DOOR.asItem())
				.add(MANGROVE_DOOR.asItem(), BAMBOO_DOOR.asItem(), CHERRY_DOOR.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_FENCES)
				.add(CHARRED_FENCE.asItem())
				.add(MANGROVE_FENCE.asItem(), BAMBOO_FENCE.asItem(), CHERRY_FENCE.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES)
				.add(CHARRED_PRESSURE_PLATE.asItem())
				.add(MANGROVE_PRESSURE_PLATE.asItem(), BAMBOO_PRESSURE_PLATE.asItem(), CHERRY_PRESSURE_PLATE.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_SLABS)
				.add(CHARRED_SLAB.asItem())
				.add(MANGROVE_SLAB.asItem(), BAMBOO_SLAB.asItem(), BAMBOO_MOSAIC_SLAB.asItem(), CHERRY_SLAB.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_STAIRS)
				.add(CHARRED_STAIRS.asItem())
				.add(MANGROVE_STAIRS.asItem(), BAMBOO_STAIRS.asItem(), BAMBOO_MOSAIC_STAIRS.asItem(), CHERRY_STAIRS.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_TRAPDOORS)
				.add(CHARRED_TRAPDOOR.asItem())
				.add(MANGROVE_TRAPDOOR.asItem(), BAMBOO_TRAPDOOR.asItem(), CHERRY_TRAPDOOR.asItem());
		getOrCreateTagBuilder(ItemTags.WOOL).add(RAINBOW_WOOL.asItem());

		getOrCreateTagBuilder(Tags.KNIVES).addTag(ModItemTags.KNIVES);

		getOrCreateTagBuilder(ModItemTags.AXES)
				.add(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE)
				.add(AMETHYST_AXE, ECHO_AXE, EMERALD_AXE, QUARTZ_AXE)
				.add(COPPER_AXE, EXPOSED_COPPER_AXE, WEATHERED_COPPER_AXE, OXIDIZED_COPPER_AXE)
				.add(WAXED_COPPER_AXE, WAXED_EXPOSED_COPPER_AXE, WAXED_WEATHERED_COPPER_AXE, WAXED_OXIDIZED_COPPER_AXE);
		getOrCreateTagBuilder(ModItemTags.BOOKS)
				.add(Items.BOOK, RED_BOOK, ORANGE_BOOK, YELLOW_BOOK, GREEN_BOOK, BLUE_BOOK, GRAY_BOOK);
		getOrCreateTagBuilder(ModItemTags.BOOKSHELF_BOOKS)
				.addTag(ModItemTags.BOOKS).add(UNREADABLE_BOOK)
				.add(Items.ENCHANTED_BOOK, Items.WRITABLE_BOOK, Items.WRITTEN_BOOK);
		getOrCreateTagBuilder(ModItemTags.BOOKSHELVES).add(Items.BOOKSHELF)
				.add(ACACIA_BOOKSHELF.asItem(), BIRCH_BOOKSHELF.asItem(), CRIMSON_BOOKSHELF.asItem(), DARK_OAK_BOOKSHELF.asItem(),
						JUNGLE_BOOKSHELF.asItem(), SPRUCE_BOOKSHELF.asItem(), WARPED_BOOKSHELF.asItem(),
						BAMBOO_BOOKSHELF.asItem(), MANGROVE_BOOKSHELF.asItem(), CHERRY_BOOKSHELF.asItem(), CHARRED_BOOKSHELF.asItem());
		getOrCreateTagBuilder(ModItemTags.BOOTS)
				.add(Items.LEATHER_BOOTS, Items.CHAINMAIL_BOOTS, Items.IRON_BOOTS, Items.GOLDEN_BOOTS, Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS)
				.add(AMETHYST_BOOTS, EMERALD_BOOTS, FLEECE_BOOTS, QUARTZ_BOOTS, STUDDED_LEATHER_BOOTS);
		getOrCreateTagBuilder(ModItemTags.CAMPFIRES).add(Items.CAMPFIRE);
		getOrCreateTagBuilder(ModItemTags.CARVED_MELONS);
		getOrCreateTagBuilder(ModItemTags.CARVED_PUMPKINS).add(Items.CARVED_PUMPKIN);
		getOrCreateTagBuilder(ModItemTags.CHARRED_LOGS).add(CHARRED_LOG.asItem(), CHARRED_WOOD.asItem(), STRIPPED_CHARRED_LOG.asItem(), STRIPPED_CHARRED_WOOD.asItem());
		getOrCreateTagBuilder(ModItemTags.CHERRY_LOGS).add(CHERRY_LOG.asItem(), CHERRY_WOOD.asItem(), STRIPPED_CHERRY_LOG.asItem(), STRIPPED_CHERRY_WOOD.asItem());
		getOrCreateTagBuilder(ModItemTags.CHESTPLATES)
				.add(Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.IRON_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.NETHERITE_CHESTPLATE)
				.add(AMETHYST_CHESTPLATE, EMERALD_CHESTPLATE, FLEECE_CHESTPLATE, QUARTZ_CHESTPLATE, STUDDED_LEATHER_CHESTPLATE);
		getOrCreateTagBuilder(ModItemTags.COMPASSES).add(Items.COMPASS).add(RECOVERY_COMPASS);
		getOrCreateTagBuilder(ModItemTags.COOKED_BEEF)
				.add(Items.COOKED_BEEF)
				.add(ItemsRegistry.BEEF_PATTY.get(), ItemsRegistry.HAMBURGER.get(), ItemsRegistry.BEEF_STEW.get(), ItemsRegistry.STEAK_AND_POTATOES.get())
				.addOptional(croptopia("beef_jerky")).addOptional(croptopia("beef_stew"))
				.addOptional(croptopia("beef_stir_fry")).addOptional(croptopia("beef_wellington"))
				.addOptional(croptopia("cheeseburger")).addOptional(croptopia("hamburger"));
		getOrCreateTagBuilder(ModItemTags.COOKED_BIRD)
				.add(Items.COOKED_CHICKEN)
				.add(ItemsRegistry.COOKED_CHICKEN_CUTS.get(), ItemsRegistry.ROAST_CHICKEN.get(), ItemsRegistry.CHICKEN_SOUP.get())
				.addOptional(croptopia("lemon_chicken")).addOptional(croptopia("cashew_chicken"))
				.addOptional(croptopia("chicken_and_dumplings")).addOptional(croptopia("chicken_and_noodles"))
				.addOptional(croptopia("chicken_and_rice")).addOptional(croptopia("fried_chicken"));
		getOrCreateTagBuilder(ModItemTags.COOKED_FISH)
				.add(Items.COOKED_COD, Items.COOKED_SALMON)
				.add(ItemsRegistry.COOKED_COD_SLICE.get(), ItemsRegistry.COOKED_SALMON_SLICE.get())
				.add(ItemsRegistry.FISH_STEW.get(), ItemsRegistry.BAKED_COD_STEW.get(), ItemsRegistry.GRILLED_SALMON.get())
				.addOptional(croptopia("cooked_anchovy")).addOptional(croptopia("cooked_tuna"))
				.addOptional(croptopia("fish_and_chips")).addOptional(betterend("end_fish_cooked"));
		getOrCreateTagBuilder(ModItemTags.COOKED_MEAT)
				.addTag(ModItemTags.COOKED_BEEF).addTag(ModItemTags.COOKED_BIRD).addTag(ModItemTags.COOKED_FISH)
				.addTag(ModItemTags.COOKED_PORK).addTag(ModItemTags.COOKED_RABBIT).addTag(ModItemTags.COOKED_SEAFOOD)
				.addTag(ModItemTags.COOKED_SHEEP);
		getOrCreateTagBuilder(ModItemTags.COOKED_PORK)
				.add(Items.COOKED_PORKCHOP)
				.add(ItemsRegistry.COOKED_BACON.get(), ItemsRegistry.COOKED_BACON.get(), ItemsRegistry.SMOKED_HAM.get())
				.addOptional(croptopia("blt")).addOptional(croptopia("cooked_bacon"))
				.addOptional(croptopia("ham_sandwich")).addOptional(croptopia("pork_jerky"))
				.addOptional(croptopia("carnitas")).addOptional(croptopia("pork_and_beans"));
		getOrCreateTagBuilder(ModItemTags.COOKED_RABBIT).add(Items.COOKED_RABBIT, Items.RABBIT_STEW);
		getOrCreateTagBuilder(ModItemTags.COOKED_SEAFOOD)
				.addTag(ModItemTags.COOKED_FISH).add(ItemsRegistry.SQUID_INK_PASTA.get())
				.addOptional(croptopia("cooked_calamari")).addOptional(croptopia("cooked_shrimp"))
				.addOptional(croptopia("crab_legs")).addOptional(croptopia("deep_fried_shrimp"))
				.addOptional(croptopia("grilled_oysters")).addOptional(croptopia("steamed_clams"))
				.addOptional(croptopia("steamed_crab")).addOptional(croptopia("tuna_roll"))
				.addOptional(croptopia("tuna_sandwich")).addOptional(croptopia("anchovy_pizza"))
				.addOptional(croptopia("fried_calamari"));
		getOrCreateTagBuilder(ModItemTags.COOKED_SHEEP)
				.add(Items.COOKED_MUTTON).add(COOKED_CHEVON)
				.add(ItemsRegistry.COOKED_MUTTON_CHOPS.get(), ItemsRegistry.ROASTED_MUTTON_CHOPS.get())
				.add(ItemsRegistry.MUTTON_WRAP.get(), ItemsRegistry.PASTA_WITH_MUTTON_CHOP.get());
		getOrCreateTagBuilder(ModItemTags.EDIBLE_BEEF).addTag(ModItemTags.RAW_BEEF).addTag(ModItemTags.COOKED_BEEF);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_BIRD).addTag(ModItemTags.RAW_BIRD).addTag(ModItemTags.COOKED_BIRD);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_EGG)
				.add(ItemsRegistry.EGG_SANDWICH.get(), ItemsRegistry.FRIED_EGG.get(), ItemsRegistry.FRIED_RICE.get())
				.addOptional(croptopia("egg_roll"));
		getOrCreateTagBuilder(ModItemTags.EDIBLE_FISH).addTag(ModItemTags.RAW_FISH).addTag(ModItemTags.COOKED_FISH);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_MEAT)
				.addTag(ModItemTags.EDIBLE_BEEF).addTag(ModItemTags.EDIBLE_BIRD).addTag(ModItemTags.EDIBLE_FISH)
				.addTag(ModItemTags.EDIBLE_PORK).addTag(ModItemTags.EDIBLE_RABBIT).addTag(ModItemTags.EDIBLE_SEAFOOD)
				.addTag(ModItemTags.EDIBLE_SHEEP);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_MUSHROOMS).add(Items.MUSHROOM_STEW)
				.add(ItemsRegistry.NETHER_SALAD.get())
				.addOptional(betternether("stalagnate_bowl_wart")).addOptional(betternether("stalagnate_bowl_mushroom"))
				.addOptional(betterend("chorus_mushroom_raw")).addOptional(betterend("chorus_mushroom_cooked"))
				.addOptional(betterend("bolux_mushroom_cooked"));
		getOrCreateTagBuilder(ModItemTags.EDIBLE_PORK).addTag(ModItemTags.RAW_PORK).addTag(ModItemTags.COOKED_PORK);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_RABBIT).addTag(ModItemTags.RAW_RABBIT).addTag(ModItemTags.COOKED_RABBIT);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_SEAFOOD).addTag(ModItemTags.RAW_SEAFOOD).addTag(ModItemTags.COOKED_SEAFOOD);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_SHEEP).addTag(ModItemTags.RAW_SHEEP).addTag(ModItemTags.COOKED_SHEEP);
		getOrCreateTagBuilder(ModItemTags.ELYTRA).add(Items.ELYTRA);
		getOrCreateTagBuilder(ModItemTags.ENDER_TORCHES).add(ENDER_TORCH.asItem())
				.add(COPPER_ENDER_TORCH.asItem(), EXPOSED_COPPER_ENDER_TORCH.asItem(),
						WEATHERED_COPPER_ENDER_TORCH.asItem(), OXIDIZED_COPPER_ENDER_TORCH.asItem(),
						WAXED_COPPER_ENDER_TORCH.asItem(), WAXED_EXPOSED_COPPER_ENDER_TORCH.asItem(),
						WAXED_WEATHERED_COPPER_ENDER_TORCH.asItem(), WAXED_OXIDIZED_COPPER_ENDER_TORCH.asItem())
				.add(BAMBOO_ENDER_TORCH.asItem(), BONE_ENDER_TORCH.asItem(), IRON_ENDER_TORCH.asItem(), GOLD_ENDER_TORCH.asItem(), NETHERITE_ENDER_TORCH.asItem());
		getOrCreateTagBuilder(ModItemTags.FEATHERS).add(Items.FEATHER, FANCY_FEATHER).add(BLACK_FEATHER, RED_FEATHER);
		getOrCreateTagBuilder(ModItemTags.FLEECE)
				.add(FLEECE.values().stream().map(BlockContainer::asItem).toArray(Item[]::new))
				.add(RAINBOW_FLEECE.asItem());
		getOrCreateTagBuilder(ModItemTags.FLEECE_SLABS)
				.add(FLEECE_SLABS.values().stream().map(BlockContainer::asItem).toArray(Item[]::new))
				.add(RAINBOW_FLEECE_SLAB.asItem());
		getOrCreateTagBuilder(ModItemTags.FLEECE_CARPETS)
				.add(FLEECE_CARPETS.values().stream().map(BlockContainer::asItem).toArray(Item[]::new))
				.add(RAINBOW_FLEECE_CARPET.asItem());
		getOrCreateTagBuilder(ModItemTags.FRUITS).addTag(ModItemTags.GOLDEN_FRUIT)
				.add(Items.APPLE).add(Items.MELON_SLICE, Items.GLISTERING_MELON_SLICE, Items.SWEET_BERRIES, Items.GLOW_BERRIES)
				.add(Items.PUMPKIN_PIE, Items.CHORUS_FRUIT, Items.POPPED_CHORUS_FRUIT)
				.add(ItemsRegistry.TOMATO.get(), ItemsRegistry.TOMATO_SAUCE.get(), ItemsRegistry.PUMPKIN_SLICE.get())
				.add(ItemsRegistry.APPLE_PIE.get(), ItemsRegistry.APPLE_PIE_SLICE.get(), ItemsRegistry.SWEET_BERRY_COOKIE.get())
				.add(ItemsRegistry.SWEET_BERRY_CHEESECAKE.get(), ItemsRegistry.SWEET_BERRY_CHEESECAKE_SLICE.get())
				.add(ItemsRegistry.MELON_POPSICLE.get(), ItemsRegistry.FRUIT_SALAD.get(), ItemsRegistry.PUMPKIN_SOUP.get())
				.add(ItemsRegistry.RATATOUILLE.get())
				.addOptional(betternether("black_apple")).addOptional(betternether("stalagnate_bowl_apple"))
				.addOptional(betterend("shadow_berry_raw")).addOptional(betterend("shadow_berry_cooked"))
				.addOptional(betterend("cave_pumpkin_pie")).addOptional(betterend("blossom_berry"))
				.addOptional(croptopia("chile_pepper"))
				.addOptionalTag(ModItemTags.NOURISH_FRUIT);
		getOrCreateTagBuilder(ModItemTags.GOLDEN_FOOD).addTag(ModItemTags.GOLDEN_FRUIT);
		getOrCreateTagBuilder(ModItemTags.GOLDEN_FRUIT).add(Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE);
		getOrCreateTagBuilder(ModItemTags.GOLDEN_VEGETABLES).add(Items.GOLDEN_CARROT);
		getOrCreateTagBuilder(ModItemTags.GRAINS).add(Items.BREAD)
				.add(ItemsRegistry.RICE.get(), ItemsRegistry.WHEAT_DOUGH.get(), ItemsRegistry.RAW_PASTA.get())
				.add(ItemsRegistry.COOKED_RICE.get(), ItemsRegistry.FRIED_RICE.get())
				.addOptional(croptopia("oats")).addOptional(croptopia("rice")).addOptional(croptopia("steamed_rice"))
				.addOptional(croptopia("tortilla")).addOptional(croptopia("buttered_toast")).addOptional(croptopia("grilled_cheese"))
				.addOptional(croptopia("nougat")).addOptional(croptopia("pizza")).addOptional(croptopia("cheese_pizza"))
				.addOptional(croptopia("toast_sandwich")).addOptional(croptopia("corn_bread"));
		getOrCreateTagBuilder(ModItemTags.HEAD_WEARABLE_BLOCKS)
				.add(PIGLIN_HEAD.asItem()).addTag(ModItemTags.CARVED_MELONS).addTag(ModItemTags.CARVED_PUMPKINS);
		getOrCreateTagBuilder(ModItemTags.HELMETS)
				.add(Items.LEATHER_HELMET, Items.CHAINMAIL_HELMET, Items.IRON_HELMET, Items.GOLDEN_HELMET, Items.DIAMOND_HELMET, Items.NETHERITE_HELMET)
				.add(AMETHYST_HELMET, EMERALD_HELMET, FLEECE_HELMET, QUARTZ_HELMET, STUDDED_LEATHER_HELMET);
		getOrCreateTagBuilder(ModItemTags.HOES)
				.add(Items.WOODEN_HOE, Items.STONE_HOE, Items.IRON_HOE, Items.GOLDEN_HOE, Items.DIAMOND_HOE, Items.NETHERITE_HOE)
				.add(AMETHYST_HOE, ECHO_HOE, EMERALD_HOE, QUARTZ_HOE)
				.add(COPPER_HOE, EXPOSED_COPPER_HOE, WEATHERED_COPPER_HOE, OXIDIZED_COPPER_HOE)
				.add(WAXED_COPPER_HOE, WAXED_EXPOSED_COPPER_HOE, WAXED_WEATHERED_COPPER_HOE, WAXED_OXIDIZED_COPPER_HOE);
		getOrCreateTagBuilder(ModItemTags.KNIVES)
				.add(ItemsRegistry.FLINT_KNIFE.get(), ItemsRegistry.IRON_KNIFE.get(), ItemsRegistry.GOLDEN_KNIFE.get(), ItemsRegistry.DIAMOND_KNIFE.get(), ItemsRegistry.NETHERITE_KNIFE.get())
				.add(AMETHYST_KNIFE, ECHO_KNIFE, EMERALD_KNIFE, QUARTZ_KNIFE)
				.add(COPPER_KNIFE, EXPOSED_COPPER_KNIFE, WEATHERED_COPPER_KNIFE, OXIDIZED_COPPER_KNIFE)
				.add(WAXED_COPPER_KNIFE, WAXED_EXPOSED_COPPER_KNIFE, WAXED_WEATHERED_COPPER_KNIFE, WAXED_OXIDIZED_COPPER_KNIFE);
		getOrCreateTagBuilder(ModItemTags.LEGGINGS)
				.add(Items.LEATHER_LEGGINGS, Items.CHAINMAIL_LEGGINGS, Items.IRON_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS)
				.add(AMETHYST_LEGGINGS, EMERALD_LEGGINGS, FLEECE_LEGGINGS, QUARTZ_LEGGINGS, STUDDED_LEATHER_LEGGINGS);
		getOrCreateTagBuilder(ModItemTags.LIGHTS_FLINT).add(Items.IRON_SWORD, ItemsRegistry.IRON_KNIFE.get());
		getOrCreateTagBuilder(ModItemTags.MANGROVE_LOGS).add(MANGROVE_LOG.asItem(), MANGROVE_WOOD.asItem(), STRIPPED_MANGROVE_LOG.asItem(), STRIPPED_MANGROVE_WOOD.asItem());
		getOrCreateTagBuilder(ModItemTags.PICKAXES)
				.add(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE)
				.add(AMETHYST_PICKAXE, ECHO_PICKAXE, EMERALD_PICKAXE, QUARTZ_PICKAXE)
				.add(COPPER_PICKAXE, EXPOSED_COPPER_PICKAXE, WEATHERED_COPPER_PICKAXE, OXIDIZED_COPPER_PICKAXE)
				.add(WAXED_COPPER_PICKAXE, WAXED_EXPOSED_COPPER_PICKAXE, WAXED_WEATHERED_COPPER_PICKAXE, WAXED_OXIDIZED_COPPER_PICKAXE);
		getOrCreateTagBuilder(ModItemTags.RAW_BEEF).add(Items.BEEF)
				.add(ItemsRegistry.MINCED_BEEF.get());
		getOrCreateTagBuilder(ModItemTags.RAW_BIRD).add(Items.CHICKEN)
				.add(ItemsRegistry.CHICKEN_CUTS.get());
		getOrCreateTagBuilder(ModItemTags.RAW_FISH).add(Items.COD, Items.PUFFERFISH, Items.SALMON, Items.TROPICAL_FISH)
				.add(ItemsRegistry.COD_SLICE.get(), ItemsRegistry.SALMON_SLICE.get())
				.addOptional(croptopia("anchovy")).addOptional(croptopia("tuna"))
				.addOptional(betterend("end_fish_raw"));
		getOrCreateTagBuilder(ModItemTags.RAW_MEAT)
				.addTag(ModItemTags.RAW_BEEF).addTag(ModItemTags.RAW_BIRD).addTag(ModItemTags.RAW_FISH)
				.addTag(ModItemTags.RAW_PORK).addTag(ModItemTags.RAW_RABBIT).addTag(ModItemTags.RAW_SEAFOOD)
				.addTag(ModItemTags.RAW_SHEEP);
		getOrCreateTagBuilder(ModItemTags.RAW_PORK).add(Items.PORKCHOP)
				.add(ItemsRegistry.BACON.get(), ItemsRegistry.HAM.get())
				.addOptional(croptopia("bacon"));
		getOrCreateTagBuilder(ModItemTags.RAW_RABBIT).add(Items.RABBIT);
		getOrCreateTagBuilder(ModItemTags.RAW_SEAFOOD)
				.addOptional(croptopia("clam")).addOptional(croptopia("calamari"))
				.addOptional(croptopia("crab")).addOptional(croptopia("glowing_calamari"))
				.addOptional(croptopia("oyster")).addOptional(croptopia("shrimp"));
		getOrCreateTagBuilder(ModItemTags.RAW_SHEEP).add(Items.MUTTON).add(CHEVON)
				.add(ItemsRegistry.MUTTON_CHOPS.get());
		getOrCreateTagBuilder(ModItemTags.SEEDS)
				.add(Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.WHEAT_SEEDS)
				.add(TORCHFLOWER_CROP.asItem())
				.add(ItemsRegistry.CABBAGE_SEEDS.get(), ItemsRegistry.TOMATO_SEED.get())
				.addOptional(croptopia("artichoke_seed")).addOptional(croptopia("asparagus_seed"))
				.addOptional(croptopia("barley_seed")).addOptional(croptopia("basil_seed"))
				.addOptional(croptopia("bellpepper_seed")).addOptional(croptopia("blackbean_seed"))
				.addOptional(croptopia("blackberry_seed")).addOptional(croptopia("blueberry_seed"))
				.addOptional(croptopia("broccoli_seed")).addOptional(croptopia("cabbage_seed"))
				.addOptional(croptopia("cantaloupe_seed")).addOptional(croptopia("cauliflower_seed"))
				.addOptional(croptopia("celery_seed")).addOptional(croptopia("chilepepper_seed"))
				.addOptional(croptopia("coffee_beans_seed")).addOptional(croptopia("corn_seed"))
				.addOptional(croptopia("cranberry_seed")).addOptional(croptopia("cucumber_seed"))
				.addOptional(croptopia("currant_seed")).addOptional(croptopia("eggplant_seed"))
				.addOptional(croptopia("elderberry_seed")).addOptional(croptopia("garlic_seed"))
				.addOptional(croptopia("ginger_seed")).addOptional(croptopia("grape_seed"))
				.addOptional(croptopia("greenbean_seed")).addOptional(croptopia("greenonion_seed"))
				.addOptional(croptopia("honeydew_seed")).addOptional(croptopia("hops_seed"))
				.addOptional(croptopia("kale_seed")).addOptional(croptopia("kiwi_seed"))
				.addOptional(croptopia("leek_seed")).addOptional(croptopia("lettuce_seed"))
				.addOptional(croptopia("mustard_seed")).addOptional(croptopia("oat_seed"))
				.addOptional(croptopia("olive_seed")).addOptional(croptopia("onion_seed"))
				.addOptional(croptopia("peanut_seed")).addOptional(croptopia("pepper_seed"))
				.addOptional(croptopia("pineapple_seed")).addOptional(croptopia("radish_seed"))
				.addOptional(croptopia("raspberry_seed")).addOptional(croptopia("rhubarb_seed"))
				.addOptional(croptopia("rice_seed")).addOptional(croptopia("rutabaga_seed"))
				.addOptional(croptopia("saguaro_seed")).addOptional(croptopia("soybean_seed"))
				.addOptional(croptopia("spinach_seed")).addOptional(croptopia("squash_seed"))
				.addOptional(croptopia("strawberry_seed")).addOptional(croptopia("sweetpotato_seed"))
				.addOptional(croptopia("tea_leaves_seed")).addOptional(croptopia("tomatillo_seed"))
				.addOptional(croptopia("tomato_seed")).addOptional(croptopia("turmeric_seed"))
				.addOptional(croptopia("turnip_seed")).addOptional(croptopia("vanilla_seed"))
				.addOptional(croptopia("yam_seed")).addOptional(croptopia("zucchini"));
		getOrCreateTagBuilder(ModItemTags.SHEARS).add(Items.SHEARS);
		getOrCreateTagBuilder(ModItemTags.SHOVELS)
				.add(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.GOLDEN_SHOVEL, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL)
				.add(AMETHYST_SHOVEL, ECHO_SHOVEL, EMERALD_SHOVEL, QUARTZ_SHOVEL)
				.add(COPPER_SHOVEL, EXPOSED_COPPER_SHOVEL, WEATHERED_COPPER_SHOVEL, OXIDIZED_COPPER_SHOVEL)
				.add(WAXED_COPPER_SHOVEL, WAXED_EXPOSED_COPPER_SHOVEL, WAXED_WEATHERED_COPPER_SHOVEL, WAXED_OXIDIZED_COPPER_SHOVEL);
		getOrCreateTagBuilder(ModItemTags.SNIFFER_FOOD).add(TORCHFLOWER_CROP.asItem());
		getOrCreateTagBuilder(ModItemTags.SOUL_TORCHES).add(Items.SOUL_TORCH)
				.add(COPPER_SOUL_TORCH.asItem(), EXPOSED_COPPER_SOUL_TORCH.asItem(),
						WEATHERED_COPPER_SOUL_TORCH.asItem(), OXIDIZED_COPPER_SOUL_TORCH.asItem(),
						WAXED_COPPER_SOUL_TORCH.asItem(), WAXED_EXPOSED_COPPER_SOUL_TORCH.asItem(),
						WAXED_WEATHERED_COPPER_SOUL_TORCH.asItem(), WAXED_OXIDIZED_COPPER_SOUL_TORCH.asItem())
				.add(BAMBOO_SOUL_TORCH.asItem(), BONE_SOUL_TORCH.asItem(), IRON_SOUL_TORCH.asItem(), GOLD_SOUL_TORCH.asItem(), NETHERITE_SOUL_TORCH.asItem());
		getOrCreateTagBuilder(ModItemTags.SWEETS).add(Items.CAKE, Items.COOKIE, Items.PUMPKIN_PIE)
				.add(ItemsRegistry.CHOCOLATE_PIE.get(), ItemsRegistry.CHOCOLATE_PIE_SLICE.get(), ItemsRegistry.HONEY_COOKIE.get())
				.add(ItemsRegistry.PIE_CRUST.get(), ItemsRegistry.CAKE_SLICE.get(), ItemsRegistry.MELON_POPSICLE.get())
				.add(ItemsRegistry.APPLE_PIE.get(), ItemsRegistry.APPLE_PIE_SLICE.get(), ItemsRegistry.SWEET_BERRY_COOKIE.get())
				.add(ItemsRegistry.SWEET_BERRY_CHEESECAKE.get(), ItemsRegistry.SWEET_BERRY_CHEESECAKE_SLICE.get())
				.addOptional(betterend("cave_pumpkin_pie")).addOptional(croptopia("pecan_pie")).addOptional(croptopia("rhubarb_pie"))
				.addOptional(croptopia("donut")).addOptional(croptopia("candied_nuts")).addOptional(croptopia("almond_brittle"))
				.addOptional(croptopia("raisin_oatmeal_cookie")).addOptional(croptopia("nutty_cookie")).addOptional(croptopia("whipping_cream"))
				.addOptional(croptopia("pumpkin_bars")).addOptional(croptopia("meringue")).addOptional(croptopia("cinnamon_roll"))
				.addOptional(croptopia("baked_crepes")).addOptional(croptopia("macaron")).addOptional(croptopia("quiche"))
				.addOptional(croptopia("sweet_crepes")).addOptional(croptopia("trail_mix")).addOptional(croptopia("protein_bar"))
				.addOptional(croptopia("tofu_and_dumplings"))
				.addTag(ModItemTags.NOURISH_SWEETS);
		getOrCreateTagBuilder(ModItemTags.SWORDS)
				.add(Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD)
				.add(AMETHYST_SWORD, ECHO_SWORD, EMERALD_SWORD, QUARTZ_SWORD)
				.add(COPPER_SWORD, EXPOSED_COPPER_SWORD, WEATHERED_COPPER_SWORD, OXIDIZED_COPPER_SWORD)
				.add(WAXED_COPPER_SWORD, WAXED_EXPOSED_COPPER_SWORD, WAXED_WEATHERED_COPPER_SWORD, WAXED_OXIDIZED_COPPER_SWORD);
		getOrCreateTagBuilder(ModItemTags.TORCHES).add(Items.TORCH)
				.add(COPPER_TORCH.asItem(), EXPOSED_COPPER_TORCH.asItem(),
						WEATHERED_COPPER_TORCH.asItem(), OXIDIZED_COPPER_TORCH.asItem(),
						WAXED_COPPER_TORCH.asItem(), WAXED_EXPOSED_COPPER_TORCH.asItem(),
						WAXED_WEATHERED_COPPER_TORCH.asItem(), WAXED_OXIDIZED_COPPER_TORCH.asItem())
				.add(BAMBOO_TORCH.asItem(), BONE_TORCH.asItem(), IRON_TORCH.asItem(), GOLD_TORCH.asItem(), NETHERITE_TORCH.asItem());
		getOrCreateTagBuilder(ModItemTags.UNDERWATER_TORCHES).add(UNDERWATER_TORCH.asItem())
				.add(UNDERWATER_COPPER_TORCH.asItem(), EXPOSED_UNDERWATER_COPPER_TORCH.asItem(),
						WEATHERED_UNDERWATER_COPPER_TORCH.asItem(), OXIDIZED_UNDERWATER_COPPER_TORCH.asItem(),
						WAXED_UNDERWATER_COPPER_TORCH.asItem(), WAXED_EXPOSED_UNDERWATER_COPPER_TORCH.asItem(),
						WAXED_WEATHERED_UNDERWATER_COPPER_TORCH.asItem(), WAXED_OXIDIZED_UNDERWATER_COPPER_TORCH.asItem())
				.add(UNDERWATER_BAMBOO_TORCH.asItem(), UNDERWATER_BONE_TORCH.asItem(), UNDERWATER_IRON_TORCH.asItem())
				.add(UNDERWATER_GOLD_TORCH.asItem(), UNDERWATER_NETHERITE_TORCH.asItem());
		getOrCreateTagBuilder(ModItemTags.TRIMMABLE_ARMOR);
		getOrCreateTagBuilder(ModItemTags.TRIM_MATERIALS);
		getOrCreateTagBuilder(ModItemTags.TRIM_TEMPLATES);
		getOrCreateTagBuilder(ModItemTags.TRIMMABLE_ARMOR)
				.add(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS)
				.add(Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS)
				.add(Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS)
				.add(Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS)
				.add(Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS)
				.add(Items.TURTLE_HELMET)
				.add(Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS);
		getOrCreateTagBuilder(ModItemTags.TRIM_MATERIALS)
				.add(Items.QUARTZ, Items.IRON_INGOT, Items.NETHERITE_INGOT, Items.REDSTONE, Items.COPPER_INGOT)
				.add(Items.GOLD_INGOT, Items.EMERALD, Items.DIAMOND, Items.LAPIS_LAZULI, Items.AMETHYST_SHARD);
		FabricTagBuilder<Item> TRIM_TEMPLATES = getOrCreateTagBuilder(ModItemTags.TRIM_TEMPLATES);
		for (ArmorTrimPattern pattern : ArmorTrimPattern.values()) TRIM_TEMPLATES.add(pattern.asItem());
		getOrCreateTagBuilder(ModItemTags.VEGETABLES).addTag(ModItemTags.GOLDEN_VEGETABLES)
				.add(Items.CARROT, Items.POTATO, Items.BAKED_POTATO, Items.POISONOUS_POTATO, Items.BEETROOT, Items.DRIED_KELP)
				.add(ItemsRegistry.CABBAGE.get(), ItemsRegistry.CABBAGE_LEAF.get(), ItemsRegistry.ONION.get(), ItemsRegistry.RICE.get())
				.add(Items.BEETROOT_SOUP)
				.add(ItemsRegistry.VEGETABLE_SOUP.get(), ItemsRegistry.VEGETABLE_NOODLES.get(), ItemsRegistry.RATATOUILLE.get())
				.add(ItemsRegistry.HORSE_FEED.get())
				.addOptional(betterend("amber_root_raw"))
				.addTag(ModItemTags.FORGE_VEGETABLES)
				.addTag(ModItemTags.NOURISH_VEGETABLES)
				.addOptional(croptopia("barley")).addOptional(croptopia("basil")).addOptional(croptopia("blackbean"))
				.addOptional(croptopia("coffee_beans")).addOptional(croptopia("ginger")).addOptional(croptopia("hops"))
				.addOptional(croptopia("mustard")).addOptional(croptopia("potato_soup")).addOptional(croptopia("beetroot_salad"))
				.addOptional(croptopia("candied_kumquats")).addOptional(croptopia("sea_lettuce")).addOptional(croptopia("dauphine_potatoes"))
				.addOptional(croptopia("mashed_potatoes")).addOptional(croptopia("hashed_brown")).addOptional(croptopia("saucy_chips"))
				.addOptional(croptopia("roasted_nuts")).addOptional(croptopia("pumpkin_soup"));
		getOrCreateTagBuilder(ModItemTags.WOOL_SLABS)
				.add(WOOL_SLABS.values().stream().map(BlockContainer::asItem).toArray(Item[]::new))
				.add(RAINBOW_WOOL_SLAB.asItem());
		getOrCreateTagBuilder(ModItemTags.WOOL_CARPETS)
				.add(Arrays.stream(DyeColor.values()).map(ColorUtil::GetWoolCarpetItem).toArray(Item[]::new))
				.add(RAINBOW_CARPET.asItem());

		getOrCreateTagBuilder(ModItemTags.CROPTOPIA_NUTS);
		getOrCreateTagBuilder(ModItemTags.FORGE_VEGETABLES);
		getOrCreateTagBuilder(ModItemTags.NOURISH_FRUIT);
		getOrCreateTagBuilder(ModItemTags.NOURISH_SWEETS);
		getOrCreateTagBuilder(ModItemTags.NOURISH_VEGETABLES);
		getOrCreateTagBuilder(ModItemTags.ORIGINS_MEAT).addTag(ModItemTags.EDIBLE_MEAT);
	}
}