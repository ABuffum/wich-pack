package fun.mousewich.gen.data.tag;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.nhoryzon.mc.farmersdelight.tag.Tags;
import fun.mousewich.ModBase;
import fun.mousewich.container.BlockContainer;
import fun.mousewich.util.Util;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;

import static fun.mousewich.ModBase.*;
import static fun.mousewich.ModBase.RAINBOW_CARPET;

public class ItemTagGenerator extends FabricTagProvider<Item> {
	public ItemTagGenerator(FabricDataGenerator dataGenerator) {
		super(dataGenerator, Registry.ITEM, "items", NAMESPACE + ":item_tag_generator");
	}

	@Override
	protected void generateTags() {
		//getOrCreateTagBuilder(ItemTags.BEDS).add(MOSS_BED.asItem(), RAINBOW_BED.asItem());
		getOrCreateTagBuilder(ItemTags.BOATS)
				.add(CHARRED_BOAT.asItem())
				.add(MANGROVE_BOAT.asItem());
		getOrCreateTagBuilder(ItemTags.BUTTONS)
				.add(NETHERITE_BUTTON.asItem());
		getOrCreateTagBuilder(ItemTags.CANDLES).add(SOUL_CANDLE.asItem(), ENDER_CANDLE.asItem());
		getOrCreateTagBuilder(ItemTags.CARPETS).addTag(ModItemTags.WOOL_CARPETS).addTag(ModItemTags.FLEECE_CARPETS);
		getOrCreateTagBuilder(ItemTags.CLUSTER_MAX_HARVESTABLES).addTag(ModItemTags.PICKAXES);
		getOrCreateTagBuilder(ItemTags.FISHES).addTag(ModItemTags.RAW_FISH).addTag(ModItemTags.COOKED_FISH);
		getOrCreateTagBuilder(ItemTags.LEAVES).add(MANGROVE_LEAVES.asItem());
		getOrCreateTagBuilder(ItemTags.LOGS).addTag(ModItemTags.CHARRED_LOGS);
		getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN).addTag(ModItemTags.MANGROVE_LOGS);
		getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(MUSIC_DISC_5);
		getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED)
				.add(POLISHED_GILDED_BLACKSTONE.asItem(), POLISHED_GILDED_BLACKSTONE_BRICKS.asItem())
				.add(CHISELED_POLISHED_GILDED_BLACKSTONE.asItem(), CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS.asItem())
				.add(GILDED_BLACKSTONE_SLAB.asItem(), POLISHED_GILDED_BLACKSTONE_SLAB.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_SLAB.asItem())
				.add(GILDED_BLACKSTONE_STAIRS.asItem(), POLISHED_GILDED_BLACKSTONE_STAIRS.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS.asItem())
				.add(GILDED_BLACKSTONE_WALL.asItem(), POLISHED_GILDED_BLACKSTONE_WALL.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_WALL.asItem());
		getOrCreateTagBuilder(ItemTags.PIGLIN_REPELLENTS).addTag(ModItemTags.SOUL_TORCHES)
				.add(NETHERITE_SOUL_LANTERN.asItem());
		getOrCreateTagBuilder(ItemTags.PLANKS)
				.add(CHARRED_PLANKS.asItem())
				.add(MANGROVE_PLANKS.asItem(), BAMBOO_PLANKS.asItem());
		getOrCreateTagBuilder(ItemTags.SIGNS)
				.add(CHARRED_SIGN.asItem())
				.add(MANGROVE_SIGN.asItem(), BAMBOO_SIGN.asItem());
		getOrCreateTagBuilder(ItemTags.SLABS)
				.add(CALCITE_SLAB.asItem(), SMOOTH_CALCITE_SLAB.asItem(), CALCITE_BRICK_SLAB.asItem())
				.add(DRIPSTONE_SLAB.asItem(), SMOOTH_DRIPSTONE_SLAB.asItem(), DRIPSTONE_BRICK_SLAB.asItem())
				.add(TUFF_SLAB.asItem(), SMOOTH_TUFF_SLAB.asItem(), TUFF_BRICK_SLAB.asItem())
				.add(AMETHYST_SLAB.asItem(), AMETHYST_BRICK_SLAB.asItem(), AMETHYST_CRYSTAL_SLAB.asItem())
				.add(TINTED_GLASS_SLAB.asItem())
				.add(GLASS_SLAB.asItem()).add(COAL_SLAB.asItem(), CHARCOAL_SLAB.asItem())
				.add(STAINED_GLASS_SLABS.values().stream().map(BlockContainer::asItem).toArray(Item[]::new))
				.add(EMERALD_BRICK_SLAB.asItem(), CUT_EMERALD_SLAB.asItem())
				.add(DIAMOND_SLAB.asItem(), DIAMOND_BRICK_SLAB.asItem())
				.add(QUARTZ_BRICK_SLAB.asItem(), QUARTZ_CRYSTAL_SLAB.asItem())
				.add(NETHERITE_BRICK_SLAB.asItem(), CUT_NETHERITE_SLAB.asItem())
				.add(ECHO_SLAB.asItem(), ECHO_CRYSTAL_SLAB.asItem())
				.add(GILDED_BLACKSTONE_SLAB.asItem(), POLISHED_GILDED_BLACKSTONE_SLAB.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_SLAB.asItem())
				.add(SCULK_STONE_SLAB.asItem(), SCULK_STONE_BRICK_SLAB.asItem())
				.addTag(ModItemTags.WOOL_SLABS).addTag(ModItemTags.FLEECE_SLABS)
				.add(MOSS_SLAB.asItem())
				.add(MUD_BRICK_SLAB.asItem());
		getOrCreateTagBuilder(ItemTags.STAIRS)
				.add(CALCITE_STAIRS.asItem(), SMOOTH_CALCITE_STAIRS.asItem(), CALCITE_BRICK_STAIRS.asItem())
				.add(DRIPSTONE_STAIRS.asItem(), SMOOTH_DRIPSTONE_STAIRS.asItem(), DRIPSTONE_BRICK_STAIRS.asItem())
				.add(TUFF_STAIRS.asItem(), SMOOTH_TUFF_STAIRS.asItem(), TUFF_BRICK_STAIRS.asItem())
				.add(AMETHYST_STAIRS.asItem(), AMETHYST_BRICK_STAIRS.asItem(), AMETHYST_CRYSTAL_STAIRS.asItem())
				.add(EMERALD_BRICK_STAIRS.asItem(), CUT_EMERALD_STAIRS.asItem())
				.add(DIAMOND_STAIRS.asItem(), DIAMOND_BRICK_STAIRS.asItem())
				.add(QUARTZ_BRICK_STAIRS.asItem(), QUARTZ_CRYSTAL_STAIRS.asItem())
				.add(NETHERITE_BRICK_STAIRS.asItem(), CUT_NETHERITE_STAIRS.asItem())
				.add(ECHO_STAIRS.asItem(), ECHO_CRYSTAL_STAIRS.asItem())
				.add(GILDED_BLACKSTONE_STAIRS.asItem(), POLISHED_GILDED_BLACKSTONE_STAIRS.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS.asItem())
				.add(SCULK_STONE_STAIRS.asItem(), SCULK_STONE_BRICK_STAIRS.asItem())
				.add(MUD_BRICK_STAIRS.asItem());
		getOrCreateTagBuilder(ItemTags.WALLS)
				.add(POLISHED_ANDESITE_WALL.asItem(), POLISHED_DIORITE_WALL.asItem(), POLISHED_GRANITE_WALL.asItem())
				.add(SMOOTH_SANDSTONE_WALL.asItem(), SMOOTH_RED_SANDSTONE_WALL.asItem())
				.add(DARK_PRISMARINE_WALL.asItem(), PURPUR_WALL.asItem())
				.add(CALCITE_WALL.asItem(), SMOOTH_CALCITE_WALL.asItem(), CALCITE_BRICK_WALL.asItem())
				.add(DRIPSTONE_WALL.asItem(), SMOOTH_DRIPSTONE_WALL.asItem(), DRIPSTONE_BRICK_WALL.asItem())
				.add(TUFF_WALL.asItem(), SMOOTH_TUFF_WALL.asItem(), TUFF_BRICK_WALL.asItem())
				.add(AMETHYST_WALL.asItem(), AMETHYST_BRICK_WALL.asItem(), AMETHYST_CRYSTAL_WALL.asItem())
				.add(EMERALD_BRICK_WALL.asItem(), CUT_EMERALD_WALL.asItem())
				.add(DIAMOND_WALL.asItem(), DIAMOND_BRICK_WALL.asItem())
				.add(SMOOTH_QUARTZ_WALL.asItem(), QUARTZ_BRICK_WALL.asItem(), QUARTZ_WALL.asItem(), QUARTZ_CRYSTAL_WALL.asItem())
				.add(NETHERITE_WALL.asItem(), NETHERITE_BRICK_WALL.asItem(), CUT_NETHERITE_WALL.asItem())
				.add(ECHO_WALL.asItem(), ECHO_CRYSTAL_WALL.asItem())
				.add(GILDED_BLACKSTONE_WALL.asItem(), POLISHED_GILDED_BLACKSTONE_WALL.asItem(), POLISHED_GILDED_BLACKSTONE_BRICK_WALL.asItem())
				.add(SCULK_STONE_WALL.asItem(), SCULK_STONE_BRICK_WALL.asItem())
				.add(MUD_BRICK_WALL.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS)
				.add(CHARRED_BUTTON.asItem())
				.add(MANGROVE_BUTTON.asItem(), BAMBOO_BUTTON.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_DOORS)
				.add(CHARRED_DOOR.asItem())
				.add(MANGROVE_DOOR.asItem(), BAMBOO_DOOR.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_FENCES)
				.add(CHARRED_FENCE.asItem())
				.add(MANGROVE_FENCE.asItem(), BAMBOO_FENCE.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES)
				.add(CHARRED_PRESSURE_PLATE.asItem())
				.add(MANGROVE_PRESSURE_PLATE.asItem(), BAMBOO_PRESSURE_PLATE.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_SLABS)
				.add(CHARRED_SLAB.asItem())
				.add(MANGROVE_SLAB.asItem(), BAMBOO_SLAB.asItem(), BAMBOO_MOSAIC_SLAB.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_STAIRS)
				.add(CHARRED_STAIRS.asItem())
				.add(MANGROVE_STAIRS.asItem(), BAMBOO_STAIRS.asItem(), BAMBOO_MOSAIC_STAIRS.asItem());
		getOrCreateTagBuilder(ItemTags.WOODEN_TRAPDOORS)
				.add(CHARRED_TRAPDOOR.asItem())
				.add(MANGROVE_TRAPDOOR.asItem(), BAMBOO_TRAPDOOR.asItem());
		getOrCreateTagBuilder(ItemTags.WOOL).add(RAINBOW_WOOL.asItem());

		getOrCreateTagBuilder(Tags.KNIVES).addTag(ModItemTags.KNIVES);

		getOrCreateTagBuilder(ModItemTags.AXES)
				.add(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE)
				.add(AMETHYST_AXE, ECHO_AXE, EMERALD_AXE, QUARTZ_AXE);
		getOrCreateTagBuilder(ModItemTags.BOOKS)
				.add(Items.BOOK, RED_BOOK, ORANGE_BOOK, YELLOW_BOOK, GREEN_BOOK, BLUE_BOOK, GRAY_BOOK);
		getOrCreateTagBuilder(ModItemTags.BOOKSHELF_BOOKS)
				.addTag(ModItemTags.BOOKS).add(UNREADABLE_BOOK)
				.add(Items.ENCHANTED_BOOK, Items.WRITABLE_BOOK, Items.WRITTEN_BOOK);
		getOrCreateTagBuilder(ModItemTags.BOOKSHELVES).add(Items.BOOKSHELF)
				.add(ACACIA_BOOKSHELF.asItem(), BIRCH_BOOKSHELF.asItem(), CRIMSON_BOOKSHELF.asItem(), DARK_OAK_BOOKSHELF.asItem(),
						JUNGLE_BOOKSHELF.asItem(), SPRUCE_BOOKSHELF.asItem(), WARPED_BOOKSHELF.asItem(),
						BAMBOO_BOOKSHELF.asItem(), MANGROVE_BOOKSHELF.asItem(), CHARRED_BOOKSHELF.asItem());
		getOrCreateTagBuilder(ModItemTags.BOOTS)
				.add(Items.LEATHER_BOOTS, Items.CHAINMAIL_BOOTS, Items.IRON_BOOTS, Items.GOLDEN_BOOTS, Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS)
				.add(AMETHYST_BOOTS, EMERALD_BOOTS, FLEECE_BOOTS, QUARTZ_BOOTS, STUDDED_LEATHER_BOOTS);
		getOrCreateTagBuilder(ModItemTags.CAMPFIRES).add(Items.CAMPFIRE);
		getOrCreateTagBuilder(ModItemTags.CARVED_MELONS);
		getOrCreateTagBuilder(ModItemTags.CARVED_PUMPKINS).add(Items.CARVED_PUMPKIN);
		getOrCreateTagBuilder(ModItemTags.CHARRED_LOGS).add(CHARRED_LOG.asItem(), CHARRED_WOOD.asItem(), STRIPPED_CHARRED_LOG.asItem(), STRIPPED_CHARRED_WOOD.asItem());
		getOrCreateTagBuilder(ModItemTags.CHESTPLATES)
				.add(Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.IRON_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.NETHERITE_CHESTPLATE)
				.add(AMETHYST_CHESTPLATE, EMERALD_CHESTPLATE, FLEECE_CHESTPLATE, QUARTZ_CHESTPLATE, STUDDED_LEATHER_CHESTPLATE);
		getOrCreateTagBuilder(ModItemTags.COMPASSES).add(Items.COMPASS).add(RECOVERY_COMPASS);
		getOrCreateTagBuilder(ModItemTags.COOKED_BEEF)
				.add(Items.COOKED_BEEF)
				.add(ItemsRegistry.BEEF_PATTY.get(), ItemsRegistry.HAMBURGER.get(), ItemsRegistry.BEEF_STEW.get(), ItemsRegistry.STEAK_AND_POTATOES.get())
				.addOptional(ID("croptopia:beef_jerky")).addOptional(ID("croptopia:beef_stew"))
				.addOptional(ID("croptopia:beef_stir_fry")).addOptional(ID("croptopia:beef_wellington"))
				.addOptional(ID("croptopia:cheeseburger")).addOptional(ID("croptopia:hamburger"));
		getOrCreateTagBuilder(ModItemTags.COOKED_BIRD)
				.add(Items.COOKED_CHICKEN)
				.add(ItemsRegistry.COOKED_CHICKEN_CUTS.get(), ItemsRegistry.ROAST_CHICKEN.get(), ItemsRegistry.CHICKEN_SOUP.get())
				.addOptional(ID("croptopia:lemon_chicken")).addOptional(ID("croptopia:cashew_chicken"))
				.addOptional(ID("croptopia:chicken_and_dumplings")).addOptional(ID("croptopia:chicken_and_noodles"))
				.addOptional(ID("croptopia:chicken_and_rice")).addOptional(ID("croptopia:fried_chicken"));
		getOrCreateTagBuilder(ModItemTags.COOKED_FISH)
				.add(Items.COOKED_COD, Items.COOKED_SALMON)
				.add(ItemsRegistry.COOKED_COD_SLICE.get(), ItemsRegistry.COOKED_SALMON_SLICE.get())
				.add(ItemsRegistry.FISH_STEW.get(), ItemsRegistry.BAKED_COD_STEW.get(), ItemsRegistry.GRILLED_SALMON.get())
				.addOptional(ID("croptopia:cooked_anchovy")).addOptional(ID("croptopia:cooked_tuna"))
				.addOptional(ID("croptopia:fish_and_chips")).addOptional(ID("betterend:end_fish_cooked"));
		getOrCreateTagBuilder(ModItemTags.COOKED_MEAT)
				.addTag(ModItemTags.COOKED_BEEF).addTag(ModItemTags.COOKED_BIRD).addTag(ModItemTags.COOKED_FISH)
				.addTag(ModItemTags.COOKED_PORK).addTag(ModItemTags.COOKED_RABBIT).addTag(ModItemTags.COOKED_SEAFOOD)
				.addTag(ModItemTags.COOKED_SHEEP);
		getOrCreateTagBuilder(ModItemTags.COOKED_PORK)
				.add(Items.COOKED_PORKCHOP)
				.add(ItemsRegistry.COOKED_BACON.get(), ItemsRegistry.COOKED_BACON.get(), ItemsRegistry.SMOKED_HAM.get())
				.addOptional(ID("croptopia:blt")).addOptional(ID("croptopia:cooked_bacon"))
				.addOptional(ID("croptopia:ham_sandwich")).addOptional(ID("croptopia:pork_jerky"))
				.addOptional(ID("croptopia:carnitas")).addOptional(ID("croptopia:pork_and_beans"));
		getOrCreateTagBuilder(ModItemTags.COOKED_RABBIT).add(Items.COOKED_RABBIT, Items.RABBIT_STEW);
		getOrCreateTagBuilder(ModItemTags.COOKED_SEAFOOD)
				.addTag(ModItemTags.COOKED_FISH).add(ItemsRegistry.SQUID_INK_PASTA.get())
				.addOptional(ID("croptopia:cooked_calamari")).addOptional(ID("croptopia:cooked_shrimp"))
				.addOptional(ID("croptopia:crab_legs")).addOptional(ID("croptopia:deep_fried_shrimp"))
				.addOptional(ID("croptopia:grilled_oysters")).addOptional(ID("croptopia:steamed_clams"))
				.addOptional(ID("croptopia:steamed_crab")).addOptional(ID("croptopia:tuna_roll"))
				.addOptional(ID("croptopia:tuna_sandwich")).addOptional(ID("croptopia:anchovy_pizza"))
				.addOptional(ID("croptopia:fried_calamari"));
		getOrCreateTagBuilder(ModItemTags.COOKED_SHEEP)
				.add(Items.COOKED_MUTTON).add(COOKED_CHEVON)
				.add(ItemsRegistry.COOKED_MUTTON_CHOPS.get(), ItemsRegistry.ROASTED_MUTTON_CHOPS.get())
				.add(ItemsRegistry.MUTTON_WRAP.get(), ItemsRegistry.PASTA_WITH_MUTTON_CHOP.get());
		getOrCreateTagBuilder(ModItemTags.EDIBLE_BEEF).addTag(ModItemTags.RAW_BEEF).addTag(ModItemTags.COOKED_BEEF);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_BIRD).addTag(ModItemTags.RAW_BIRD).addTag(ModItemTags.COOKED_BIRD);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_EGG)
				.add(ItemsRegistry.EGG_SANDWICH.get(), ItemsRegistry.FRIED_EGG.get(), ItemsRegistry.FRIED_RICE.get())
				.addOptional(ID("croptopia:egg_roll"));
		getOrCreateTagBuilder(ModItemTags.EDIBLE_FISH).addTag(ModItemTags.RAW_FISH).addTag(ModItemTags.COOKED_FISH);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_MEAT)
				.addTag(ModItemTags.EDIBLE_BEEF).addTag(ModItemTags.EDIBLE_BIRD).addTag(ModItemTags.EDIBLE_FISH)
				.addTag(ModItemTags.EDIBLE_PORK).addTag(ModItemTags.EDIBLE_RABBIT).addTag(ModItemTags.EDIBLE_SEAFOOD)
				.addTag(ModItemTags.EDIBLE_SHEEP);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_MUSHROOMS).add(Items.MUSHROOM_STEW)
				.add(ItemsRegistry.NETHER_SALAD.get())
				.addOptional(ID("betternether:stalagnate_bowl_wart")).addOptional(ID("betternether:stalagnate_bowl_mushroom"))
				.addOptional(ID("betterend:chorus_mushroom_raw")).addOptional(ID("betterend:chorus_mushroom_cooked"))
				.addOptional(ID("betterend:bolux_mushroom_cooked"));
		getOrCreateTagBuilder(ModItemTags.EDIBLE_PORK).addTag(ModItemTags.RAW_PORK).addTag(ModItemTags.COOKED_PORK);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_RABBIT).addTag(ModItemTags.RAW_RABBIT).addTag(ModItemTags.COOKED_RABBIT);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_SEAFOOD).addTag(ModItemTags.RAW_SEAFOOD).addTag(ModItemTags.COOKED_SEAFOOD);
		getOrCreateTagBuilder(ModItemTags.EDIBLE_SHEEP).addTag(ModItemTags.RAW_SHEEP).addTag(ModItemTags.COOKED_SHEEP);
		getOrCreateTagBuilder(ModItemTags.ELYTRA).add(Items.ELYTRA);
		getOrCreateTagBuilder(ModItemTags.ENDER_TORCHES).add(ENDER_TORCH.asItem())
				.add(BAMBOO_ENDER_TORCH.asItem(), BONE_ENDER_TORCH.asItem(), NETHERITE_ENDER_TORCH.asItem());
		getOrCreateTagBuilder(ModItemTags.FEATHERS).add(Items.FEATHER);
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
				.add(ItemsRegistry.TOMATO.get(), ItemsRegistry.PUMPKIN_SLICE.get())
				.add(ItemsRegistry.APPLE_PIE.get(), ItemsRegistry.APPLE_PIE_SLICE.get(), ItemsRegistry.SWEET_BERRY_COOKIE.get())
				.add(ItemsRegistry.SWEET_BERRY_CHEESECAKE.get(), ItemsRegistry.SWEET_BERRY_CHEESECAKE_SLICE.get())
				.add(ItemsRegistry.MELON_POPSICLE.get(), ItemsRegistry.FRUIT_SALAD.get(), ItemsRegistry.PUMPKIN_SOUP.get())
				.add(ItemsRegistry.RATATOUILLE.get())
				.addOptional(ID("betternether:black_apple")).addOptional(ID("betternether:stalagnate_bowl_apple"))
				.addOptional(ID("betterend:shadow_berry_raw")).addOptional(ID("betterend:shadow_berry_cooked"))
				.addOptional(ID("betterend:cave_pumpkin_pie")).addOptional(ID("betterend:blossom_berry"))
				.addOptionalTag(ModItemTags.NOURISH_FRUIT);
		getOrCreateTagBuilder(ModItemTags.GOLDEN_FOOD).addTag(ModItemTags.GOLDEN_FRUIT);
		getOrCreateTagBuilder(ModItemTags.GOLDEN_FRUIT).add(Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE);
		getOrCreateTagBuilder(ModItemTags.GOLDEN_VEGETABLES).add(Items.GOLDEN_CARROT);
		getOrCreateTagBuilder(ModItemTags.GRAINS).add(Items.BREAD)
				.add(ItemsRegistry.RICE.get(), ItemsRegistry.WHEAT_DOUGH.get(), ItemsRegistry.RAW_PASTA.get())
				.add(ItemsRegistry.COOKED_RICE.get(), ItemsRegistry.FRIED_RICE.get());
		getOrCreateTagBuilder(ModItemTags.HEAD_WEARABLE_BLOCKS)
				.add(PIGLIN_HEAD.asItem()).addTag(ModItemTags.CARVED_MELONS).addTag(ModItemTags.CARVED_PUMPKINS);
		getOrCreateTagBuilder(ModItemTags.HELMETS)
				.add(Items.LEATHER_HELMET, Items.CHAINMAIL_HELMET, Items.IRON_HELMET, Items.GOLDEN_HELMET, Items.DIAMOND_HELMET, Items.NETHERITE_HELMET)
				.add(AMETHYST_HELMET, EMERALD_HELMET, FLEECE_HELMET, QUARTZ_HELMET, STUDDED_LEATHER_HELMET);
		getOrCreateTagBuilder(ModItemTags.HOES)
				.add(Items.WOODEN_HOE, Items.STONE_HOE, Items.IRON_HOE, Items.GOLDEN_HOE, Items.DIAMOND_HOE, Items.NETHERITE_HOE)
				.add(AMETHYST_HOE, ECHO_HOE, EMERALD_HOE, QUARTZ_HOE);
		getOrCreateTagBuilder(ModItemTags.KNIVES)
				.add(ItemsRegistry.FLINT_KNIFE.get(), ItemsRegistry.IRON_KNIFE.get(), ItemsRegistry.GOLDEN_KNIFE.get(), ItemsRegistry.DIAMOND_KNIFE.get(), ItemsRegistry.NETHERITE_KNIFE.get())
				.add(AMETHYST_KNIFE, ECHO_KNIFE, EMERALD_KNIFE, QUARTZ_KNIFE);
		getOrCreateTagBuilder(ModItemTags.LEGGINGS)
				.add(Items.LEATHER_LEGGINGS, Items.CHAINMAIL_LEGGINGS, Items.IRON_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS)
				.add(AMETHYST_LEGGINGS, EMERALD_LEGGINGS, FLEECE_LEGGINGS, QUARTZ_LEGGINGS, STUDDED_LEATHER_LEGGINGS);
		getOrCreateTagBuilder(ModItemTags.MANGROVE_LOGS).add(MANGROVE_LOG.asItem(), MANGROVE_WOOD.asItem(), STRIPPED_MANGROVE_LOG.asItem(), STRIPPED_MANGROVE_WOOD.asItem());
		getOrCreateTagBuilder(ModItemTags.PICKAXES)
				.add(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE)
				.add(AMETHYST_PICKAXE, ECHO_PICKAXE, EMERALD_PICKAXE, QUARTZ_PICKAXE);
		getOrCreateTagBuilder(ModItemTags.SEEDS)
				.add(Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.WHEAT_SEEDS);
		getOrCreateTagBuilder(ModItemTags.RAW_BEEF).add(Items.BEEF)
				.add(ItemsRegistry.MINCED_BEEF.get());
		getOrCreateTagBuilder(ModItemTags.RAW_BIRD).add(Items.CHICKEN)
				.add(ItemsRegistry.CHICKEN_CUTS.get());
		getOrCreateTagBuilder(ModItemTags.RAW_FISH).add(Items.COD, Items.PUFFERFISH, Items.SALMON, Items.TROPICAL_FISH)
				.add(ItemsRegistry.COD_SLICE.get(), ItemsRegistry.SALMON_SLICE.get())
				.addOptional(ID("croptopia:anchovy")).addOptional(ID("croptopia:tuna"))
				.addOptional(ID("betterend:end_fish_raw"));
		getOrCreateTagBuilder(ModItemTags.RAW_MEAT)
				.addTag(ModItemTags.RAW_BEEF).addTag(ModItemTags.RAW_BIRD).addTag(ModItemTags.RAW_FISH)
				.addTag(ModItemTags.RAW_PORK).addTag(ModItemTags.RAW_RABBIT).addTag(ModItemTags.RAW_SEAFOOD)
				.addTag(ModItemTags.RAW_SHEEP);
		getOrCreateTagBuilder(ModItemTags.RAW_PORK).add(Items.PORKCHOP)
				.add(ItemsRegistry.BACON.get(), ItemsRegistry.HAM.get())
				.addOptional(ID("croptopia:bacon"));
		getOrCreateTagBuilder(ModItemTags.RAW_RABBIT).add(Items.RABBIT);
		getOrCreateTagBuilder(ModItemTags.RAW_SEAFOOD)
				.addOptional(ID("croptopia:clam")).addOptional(ID("croptopia:calamari"))
				.addOptional(ID("croptopia:crab")).addOptional(ID("croptopia:glowing_calamari"))
				.addOptional(ID("croptopia:oyster")).addOptional(ID("croptopia:shrimp"));
		getOrCreateTagBuilder(ModItemTags.RAW_SHEEP).add(Items.MUTTON).add(CHEVON)
				.add(ItemsRegistry.MUTTON_CHOPS.get());
		getOrCreateTagBuilder(ModItemTags.SHEARS).add(Items.SHEARS);
		getOrCreateTagBuilder(ModItemTags.SHOVELS)
				.add(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.GOLDEN_SHOVEL, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL)
				.add(AMETHYST_SHOVEL, ECHO_SHOVEL, EMERALD_SHOVEL, QUARTZ_SHOVEL);
		getOrCreateTagBuilder(ModItemTags.SOUL_TORCHES).add(Items.SOUL_TORCH)
				.add(BAMBOO_SOUL_TORCH.asItem(), BONE_SOUL_TORCH.asItem(), NETHERITE_SOUL_TORCH.asItem());
		getOrCreateTagBuilder(ModItemTags.SWEETS).add(Items.CAKE, Items.COOKIE, Items.PUMPKIN_PIE)
				.add(ItemsRegistry.CHOCOLATE_PIE.get(), ItemsRegistry.CHOCOLATE_PIE_SLICE.get(), ItemsRegistry.HONEY_COOKIE.get())
				.add(ItemsRegistry.PIE_CRUST.get(), ItemsRegistry.CAKE_SLICE.get(), ItemsRegistry.MELON_POPSICLE.get())
				.add(ItemsRegistry.APPLE_PIE.get(), ItemsRegistry.APPLE_PIE_SLICE.get(), ItemsRegistry.SWEET_BERRY_COOKIE.get())
				.add(ItemsRegistry.SWEET_BERRY_CHEESECAKE.get(), ItemsRegistry.SWEET_BERRY_CHEESECAKE_SLICE.get())
				.addOptional(ID("betterend:cave_pumpkin_pie"))
				.addTag(ModItemTags.NOURISH_SWEETS);
		getOrCreateTagBuilder(ModItemTags.SWORDS)
				.add(Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD)
				.add(AMETHYST_SWORD, ECHO_SWORD, EMERALD_SWORD, QUARTZ_SWORD);
		getOrCreateTagBuilder(ModItemTags.TORCHES).add(Items.TORCH)
				.add(BAMBOO_TORCH.asItem(), BONE_TORCH.asItem(), NETHERITE_TORCH.asItem());
		getOrCreateTagBuilder(ModItemTags.UNDERWATER_TORCHES).add(UNDERWATER_TORCH.asItem())
				.add(UNDERWATER_BAMBOO_TORCH.asItem(), UNDERWATER_BONE_TORCH.asItem(), UNDERWATER_NETHERITE_TORCH.asItem());
		getOrCreateTagBuilder(ModItemTags.VEGETABLES).addTag(ModItemTags.GOLDEN_VEGETABLES)
				.add(Items.CARROT, Items.POTATO, Items.BAKED_POTATO, Items.POISONOUS_POTATO, Items.BEETROOT, Items.DRIED_KELP)
				.add(ItemsRegistry.CABBAGE.get(), ItemsRegistry.CABBAGE_LEAF.get(), ItemsRegistry.ONION.get(), ItemsRegistry.RICE.get())
				.add(Items.BEETROOT_SOUP)
				.add(ItemsRegistry.VEGETABLE_SOUP.get(), ItemsRegistry.VEGETABLE_NOODLES.get(), ItemsRegistry.RATATOUILLE.get())
				.add(ItemsRegistry.HORSE_FEED.get())
				.addOptional(ID("betterend:amber_root_raw"))
				.addTag(ModItemTags.FORGE_VEGETABLES)
				.addTag(ModItemTags.NOURISH_VEGETABLES);
		getOrCreateTagBuilder(ModItemTags.WOOL_SLABS)
				.add(WOOL_SLABS.values().stream().map(BlockContainer::asItem).toArray(Item[]::new))
				.add(RAINBOW_WOOL_SLAB.asItem());
		getOrCreateTagBuilder(ModItemTags.WOOL_CARPETS)
				.add(Arrays.stream(COLORS).map(Util::GetWoolCarpetItem).toArray(Item[]::new))
				.add(RAINBOW_CARPET.asItem());

		getOrCreateTagBuilder(ModItemTags.CROPTOPIA_NUTS);
		getOrCreateTagBuilder(ModItemTags.FORGE_VEGETABLES);
		getOrCreateTagBuilder(ModItemTags.NOURISH_FRUIT);
		getOrCreateTagBuilder(ModItemTags.NOURISH_SWEETS);
		getOrCreateTagBuilder(ModItemTags.NOURISH_VEGETABLES);
		getOrCreateTagBuilder(ModItemTags.ORIGINS_MEAT).addTag(ModItemTags.EDIBLE_MEAT);
	}
}