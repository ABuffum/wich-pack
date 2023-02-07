package fun.mousewich.gen.data.tag;

import fun.mousewich.container.BlockContainer;
import fun.mousewich.util.Util;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;

import static fun.mousewich.ModBase.*;
import static fun.mousewich.ModBase.RAINBOW_CARPET;

public class BlockTagGenerator extends FabricTagProvider<Block> {
	public BlockTagGenerator(FabricDataGenerator dataGenerator) {
		super(dataGenerator, Registry.BLOCK, "blocks", NAMESPACE + ":block_tag_generator");
	}

	@Override
	protected void generateTags() {
		getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
				.add(BAMBOO_BLOCK.asBlock(), STRIPPED_BAMBOO_BLOCK.asBlock())
				.add(MANGROVE_ROOTS.asBlock())
				.add(ENDER_CAMPFIRE.asBlock());
		getOrCreateTagBuilder(BlockTags.BASE_STONE_NETHER).add(Blocks.NETHERRACK, Blocks.BASALT, Blocks.BLACKSTONE);
		getOrCreateTagBuilder(BlockTags.BASE_STONE_OVERWORLD)
				.add(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.TUFF, Blocks.DEEPSLATE);
		getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS)
				.add(NETHERITE_BRICKS.asBlock(), CUT_NETHERITE.asBlock(), CUT_NETHERITE_PILLAR.asBlock())
				.add(CUT_EMERALD.asBlock(), EMERALD_BRICKS.asBlock());
		getOrCreateTagBuilder(BlockTags.BUTTONS)
				.add(NETHERITE_BUTTON.asBlock());
		getOrCreateTagBuilder(BlockTags.CAMPFIRES).add(ENDER_CAMPFIRE.asBlock());
		getOrCreateTagBuilder(BlockTags.CANDLE_CAKES).add(SOUL_CANDLE_CAKE, ENDER_CANDLE_CAKE);
		getOrCreateTagBuilder(BlockTags.CANDLES).add(SOUL_CANDLE.asBlock(), ENDER_CANDLE.asBlock());
		getOrCreateTagBuilder(BlockTags.CARPETS)
				.add(RAINBOW_CARPET.asBlock())
				.add(FLEECE_CARPETS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new))
				.add(RAINBOW_FLEECE_CARPET.asBlock());
		getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(BONE_LADDER.asBlock());
		getOrCreateTagBuilder(BlockTags.CRYSTAL_SOUND_BLOCKS)
				.add(AMETHYST_SLAB.asBlock(), AMETHYST_STAIRS.asBlock(), AMETHYST_WALL.asBlock())
				.add(AMETHYST_CRYSTAL_BLOCK.asBlock(), AMETHYST_CRYSTAL_SLAB.asBlock())
				.add(AMETHYST_CRYSTAL_STAIRS.asBlock(), AMETHYST_CRYSTAL_WALL.asBlock())
				.add(AMETHYST_BRICKS.asBlock(), AMETHYST_BRICK_SLAB.asBlock())
				.add(AMETHYST_BRICK_STAIRS.asBlock(), AMETHYST_BRICK_WALL.asBlock());
		getOrCreateTagBuilder(BlockTags.DIRT).add(MUD.asBlock(), MUDDY_MANGROVE_ROOTS.asBlock());
		getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE).add(REINFORCED_DEEPSLATE.asBlock());
		getOrCreateTagBuilder(BlockTags.FEATURES_CANNOT_REPLACE).add(REINFORCED_DEEPSLATE.asBlock());
		getOrCreateTagBuilder(BlockTags.FENCE_GATES)
				.add(CHARRED_FENCE_GATE.asBlock())
				.add(MANGROVE_FENCE_GATE.asBlock(), BAMBOO_FENCE_GATE.asBlock());
		getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(MANGROVE_PROPAGULE.getPottedBlock());
		getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
				.add(MANGROVE_LEAVES.asBlock())
				.add(SCULK.asBlock(), SCULK_SHRIEKER.asBlock(), SCULK_VEIN.asBlock())
				.add(HEDGE_BLOCK.asBlock(), BLUE_SHROOMLIGHT.asBlock());
		getOrCreateTagBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(SCULK_VEIN.asBlock());
		getOrCreateTagBuilder(BlockTags.LEAVES).add(MANGROVE_LEAVES.asBlock());
		getOrCreateTagBuilder(BlockTags.LOGS).addTag(ModBlockTags.CHARRED_LOGS);
		getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(ModBlockTags.MANGROVE_LOGS);
		getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
				.add(NETHERITE_BARS.asBlock(), NETHERITE_CHAIN.asBlock())
				.add(NETHERITE_LANTERN.asBlock(), NETHERITE_SOUL_LANTERN.asBlock(), NETHERITE_ENDER_LANTERN.asBlock())
				.add(NETHERITE_BUTTON.asBlock(), CRUSHING_WEIGHTED_PRESSURE_PLATE.asBlock())
				.add(NETHERITE_WALL.asBlock(), NETHERITE_BRICKS.asBlock(), NETHERITE_BRICK_SLAB.asBlock())
				.add(NETHERITE_BRICK_STAIRS.asBlock(), NETHERITE_BRICK_WALL.asBlock(), CUT_NETHERITE.asBlock())
				.add(CUT_NETHERITE_PILLAR.asBlock(), CUT_NETHERITE_SLAB.asBlock(), CUT_NETHERITE_STAIRS.asBlock())
				.add(CUT_NETHERITE_WALL.asBlock());
		getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
				.add(DIAMOND_BRICKS.asBlock(), DIAMOND_BRICK_SLAB.asBlock(), DIAMOND_BRICK_STAIRS.asBlock())
				.add(DIAMOND_BRICK_WALL.asBlock(), DIAMOND_SLAB.asBlock(), DIAMOND_STAIRS.asBlock())
				.add(DIAMOND_WALL.asBlock())
				.add(EMERALD_BRICKS.asBlock(), EMERALD_BRICK_SLAB.asBlock(), EMERALD_BRICK_STAIRS.asBlock())
				.add(EMERALD_BRICK_WALL.asBlock(), CUT_EMERALD.asBlock(), CUT_EMERALD_SLAB.asBlock())
				.add(CUT_EMERALD_STAIRS.asBlock(), CUT_EMERALD_WALL.asBlock());
		getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
				.add(ENDER_LANTERN.asBlock(), UNLIT_LANTERN, UNLIT_SOUL_LANTERN);
		getOrCreateTagBuilder(BlockTags.NYLIUM).add(Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM);
		getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
				.add(MUD_BRICKS.asBlock(), MUD_BRICK_SLAB.asBlock(), MUD_BRICK_STAIRS.asBlock(), MUD_BRICK_WALL.asBlock(), PACKED_MUD.asBlock())
				.add(COAL_SLAB.asBlock(), CHARCOAL_BLOCK.asBlock(), CHARCOAL_SLAB.asBlock())
				.add(POLISHED_ANDESITE_WALL.asBlock(), POLISHED_DIORITE_WALL.asBlock(), POLISHED_GRANITE_WALL.asBlock())
				.add(SMOOTH_SANDSTONE_WALL.asBlock(), SMOOTH_RED_SANDSTONE_WALL.asBlock())
				.add(DARK_PRISMARINE_WALL.asBlock(), PURPUR_WALL.asBlock())
				.add(CALCITE_SLAB.asBlock(), CALCITE_STAIRS.asBlock(), CALCITE_WALL.asBlock())
				.add(SMOOTH_CALCITE.asBlock(), SMOOTH_CALCITE_SLAB.asBlock(), SMOOTH_CALCITE_STAIRS.asBlock(), SMOOTH_CALCITE_WALL.asBlock())
				.add(CALCITE_BRICKS.asBlock(), CALCITE_BRICK_SLAB.asBlock(), CALCITE_BRICK_STAIRS.asBlock(), CALCITE_BRICK_WALL.asBlock())
				.add(DRIPSTONE_SLAB.asBlock(), DRIPSTONE_STAIRS.asBlock(), DRIPSTONE_WALL.asBlock())
				.add(SMOOTH_DRIPSTONE.asBlock(), SMOOTH_DRIPSTONE_SLAB.asBlock(), SMOOTH_DRIPSTONE_STAIRS.asBlock(), SMOOTH_DRIPSTONE_WALL.asBlock())
				.add(DRIPSTONE_BRICKS.asBlock(), DRIPSTONE_BRICK_SLAB.asBlock(), DRIPSTONE_BRICK_STAIRS.asBlock(), DRIPSTONE_BRICK_WALL.asBlock())
				.add(TUFF_SLAB.asBlock(), TUFF_STAIRS.asBlock(), TUFF_WALL.asBlock())
				.add(SMOOTH_TUFF.asBlock(), SMOOTH_TUFF_SLAB.asBlock(), SMOOTH_TUFF_STAIRS.asBlock(), SMOOTH_TUFF_WALL.asBlock())
				.add(TUFF_BRICKS.asBlock(), TUFF_BRICK_SLAB.asBlock(), TUFF_BRICK_STAIRS.asBlock(), TUFF_BRICK_WALL.asBlock())
				.add(AMETHYST_CRYSTAL_BLOCK.asBlock(), AMETHYST_CRYSTAL_SLAB.asBlock(), AMETHYST_CRYSTAL_STAIRS.asBlock())
				.add(AMETHYST_CRYSTAL_WALL.asBlock(), AMETHYST_BRICKS.asBlock(), AMETHYST_BRICK_SLAB.asBlock())
				.add(AMETHYST_BRICK_STAIRS.asBlock(), AMETHYST_BRICK_WALL.asBlock(), AMETHYST_SLAB.asBlock())
				.add(AMETHYST_STAIRS.asBlock(), AMETHYST_WALL.asBlock(), ECHO_BLOCK.asBlock(), ECHO_SLAB.asBlock())
				.add(ECHO_STAIRS.asBlock(), ECHO_WALL.asBlock(), ECHO_CRYSTAL_BLOCK.asBlock(), ECHO_CRYSTAL_SLAB.asBlock())
				.add(ECHO_CRYSTAL_STAIRS.asBlock(), ECHO_CRYSTAL_WALL.asBlock(), BUDDING_ECHO.asBlock())
				.add(SMALL_ECHO_BUD.asBlock(), MEDIUM_ECHO_BUD.asBlock(), LARGE_ECHO_BUD.asBlock(), ECHO_CLUSTER.asBlock())
				.add(EMERALD_BRICKS.asBlock(), EMERALD_BRICK_SLAB.asBlock(), EMERALD_BRICK_STAIRS.asBlock())
				.add(EMERALD_BRICK_WALL.asBlock(), CUT_EMERALD.asBlock(), CUT_EMERALD_SLAB.asBlock())
				.add(CUT_EMERALD_STAIRS.asBlock(), CUT_EMERALD_WALL.asBlock(), DIAMOND_BRICKS.asBlock())
				.add(DIAMOND_BRICK_SLAB.asBlock(), DIAMOND_BRICK_STAIRS.asBlock(), DIAMOND_BRICK_WALL.asBlock())
				.add(DIAMOND_SLAB.asBlock(), DIAMOND_STAIRS.asBlock(), DIAMOND_WALL.asBlock())
				.add(UNLIT_LANTERN, UNLIT_SOUL_LANTERN)
				.add(QUARTZ_BRICK_SLAB.asBlock(), QUARTZ_BRICK_STAIRS.asBlock(), QUARTZ_BRICK_WALL.asBlock())
				.add(SMOOTH_QUARTZ_WALL.asBlock(), QUARTZ_CRYSTAL_BLOCK.asBlock(), QUARTZ_CRYSTAL_SLAB.asBlock())
				.add(QUARTZ_CRYSTAL_STAIRS.asBlock(), QUARTZ_CRYSTAL_WALL.asBlock(), QUARTZ_WALL.asBlock())
				.add(NETHERITE_LANTERN.asBlock(), NETHERITE_SOUL_LANTERN.asBlock(), NETHERITE_ENDER_LANTERN.asBlock())
				.add(CRUSHING_WEIGHTED_PRESSURE_PLATE.asBlock(), NETHERITE_BUTTON.asBlock(), NETHERITE_BARS.asBlock())
				.add(NETHERITE_CHAIN.asBlock(), NETHERITE_WALL.asBlock(), NETHERITE_BRICKS.asBlock())
				.add(NETHERITE_BRICK_SLAB.asBlock(), NETHERITE_BRICK_STAIRS.asBlock(), NETHERITE_BRICK_WALL.asBlock())
				.add(CUT_NETHERITE.asBlock(), CUT_NETHERITE_PILLAR.asBlock(), CUT_NETHERITE_SLAB.asBlock())
				.add(CUT_NETHERITE_STAIRS.asBlock(), CUT_NETHERITE_WALL.asBlock())
				.add(SCULK_STONE.asBlock(), SCULK_STONE_SLAB.asBlock(), SCULK_STONE_STAIRS.asBlock())
				.add(SCULK_STONE_WALL.asBlock(), SCULK_STONE_BRICKS.asBlock(), SCULK_STONE_BRICK_SLAB.asBlock())
				.add(SCULK_STONE_STAIRS.asBlock(), SCULK_STONE_WALL.asBlock())
				.add(CALCITE_SCULK_TURF.asBlock(), DEEPSLATE_SCULK_TURF.asBlock())
				.add(DRIPSTONE_SCULK_TURF.asBlock(), SMOOTH_BASALT_SCULK_TURF.asBlock())
				.add(TUFF_SCULK_TURF.asBlock())
				.add(POLISHED_GILDED_BLACKSTONE.asBlock(), POLISHED_GILDED_BLACKSTONE_BRICKS.asBlock())
				.add(CHISELED_POLISHED_GILDED_BLACKSTONE.asBlock(), CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS.asBlock())
				.add(GILDED_BLACKSTONE_SLAB.asBlock(), POLISHED_GILDED_BLACKSTONE_SLAB.asBlock(), POLISHED_GILDED_BLACKSTONE_BRICK_SLAB.asBlock())
				.add(GILDED_BLACKSTONE_STAIRS.asBlock(), POLISHED_GILDED_BLACKSTONE_STAIRS.asBlock(), POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS.asBlock())
				.add(GILDED_BLACKSTONE_WALL.asBlock(), POLISHED_GILDED_BLACKSTONE_WALL.asBlock(), POLISHED_GILDED_BLACKSTONE_BRICK_WALL.asBlock());;
		getOrCreateTagBuilder(BlockTags.GUARDED_BY_PIGLINS)
				.add(POLISHED_GILDED_BLACKSTONE.asBlock(), POLISHED_GILDED_BLACKSTONE_BRICKS.asBlock())
				.add(CHISELED_POLISHED_GILDED_BLACKSTONE.asBlock(), CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS.asBlock())
				.add(GILDED_BLACKSTONE_SLAB.asBlock(), POLISHED_GILDED_BLACKSTONE_SLAB.asBlock(), POLISHED_GILDED_BLACKSTONE_BRICK_SLAB.asBlock())
				.add(GILDED_BLACKSTONE_STAIRS.asBlock(), POLISHED_GILDED_BLACKSTONE_STAIRS.asBlock(), POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS.asBlock())
				.add(GILDED_BLACKSTONE_WALL.asBlock(), POLISHED_GILDED_BLACKSTONE_WALL.asBlock(), POLISHED_GILDED_BLACKSTONE_BRICK_WALL.asBlock());
		getOrCreateTagBuilder(BlockTags.PIGLIN_REPELLENTS)
				.add(NETHERITE_SOUL_LANTERN.asBlock(), NETHERITE_SOUL_TORCH.asBlock())
				.add(BONE_SOUL_TORCH.asBlock(), BAMBOO_SOUL_TORCH.asBlock());
		getOrCreateTagBuilder(BlockTags.PLANKS)
				.add(CHARRED_PLANKS.asBlock())
				.add(MANGROVE_PLANKS.asBlock(), BAMBOO_PLANKS.asBlock());
		getOrCreateTagBuilder(BlockTags.PRESSURE_PLATES).add(CRUSHING_WEIGHTED_PRESSURE_PLATE.asBlock());
		getOrCreateTagBuilder(BlockTags.SAND).add(Blocks.SAND, Blocks.RED_SAND);
		getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(MUD.asBlock(), MUDDY_MANGROVE_ROOTS.asBlock());
		getOrCreateTagBuilder(BlockTags.STANDING_SIGNS)
				.add(CHARRED_SIGN.asBlock())
				.add(MANGROVE_SIGN.asBlock(), BAMBOO_SIGN.asBlock());
		getOrCreateTagBuilder(BlockTags.SLABS)
				.add(CALCITE_SLAB.asBlock(), SMOOTH_CALCITE_SLAB.asBlock(), CALCITE_BRICK_SLAB.asBlock())
				.add(DRIPSTONE_SLAB.asBlock(), SMOOTH_DRIPSTONE_SLAB.asBlock(), DRIPSTONE_BRICK_SLAB.asBlock())
				.add(TUFF_SLAB.asBlock(), SMOOTH_TUFF_SLAB.asBlock(), TUFF_BRICK_SLAB.asBlock())
				.add(AMETHYST_SLAB.asBlock(), AMETHYST_BRICK_SLAB.asBlock(), AMETHYST_CRYSTAL_SLAB.asBlock())
				.add(TINTED_GLASS_SLAB.asBlock())
				.add(GLASS_SLAB.asBlock()).add(COAL_SLAB.asBlock(), CHARCOAL_SLAB.asBlock())
				.add(STAINED_GLASS_SLABS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new))
				.add(EMERALD_BRICK_SLAB.asBlock(), CUT_EMERALD_SLAB.asBlock())
				.add(DIAMOND_SLAB.asBlock(), DIAMOND_BRICK_SLAB.asBlock())
				.add(QUARTZ_BRICK_SLAB.asBlock(), QUARTZ_CRYSTAL_SLAB.asBlock())
				.add(NETHERITE_BRICK_SLAB.asBlock(), CUT_NETHERITE_SLAB.asBlock())
				.add(ECHO_SLAB.asBlock(), ECHO_CRYSTAL_SLAB.asBlock())
				.add(GILDED_BLACKSTONE_SLAB.asBlock(), POLISHED_GILDED_BLACKSTONE_SLAB.asBlock(), POLISHED_GILDED_BLACKSTONE_BRICK_SLAB.asBlock())
				.add(SCULK_STONE_SLAB.asBlock(), SCULK_STONE_BRICK_SLAB.asBlock())
				.add(WOOL_SLABS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new))
				.addTag(ModBlockTags.WOOL_SLABS).addTag(ModBlockTags.FLEECE_SLABS)
				.add(MOSS_SLAB.asBlock())
				.add(MUD_BRICK_SLAB.asBlock());
		getOrCreateTagBuilder(BlockTags.STAIRS)
				.add(CALCITE_STAIRS.asBlock(), SMOOTH_CALCITE_STAIRS.asBlock(), CALCITE_BRICK_STAIRS.asBlock())
				.add(DRIPSTONE_STAIRS.asBlock(), SMOOTH_DRIPSTONE_STAIRS.asBlock(), DRIPSTONE_BRICK_STAIRS.asBlock())
				.add(TUFF_STAIRS.asBlock(), SMOOTH_TUFF_STAIRS.asBlock(), TUFF_BRICK_STAIRS.asBlock())
				.add(AMETHYST_STAIRS.asBlock(), AMETHYST_BRICK_STAIRS.asBlock(), AMETHYST_CRYSTAL_STAIRS.asBlock())
				.add(EMERALD_BRICK_STAIRS.asBlock(), CUT_EMERALD_STAIRS.asBlock())
				.add(DIAMOND_STAIRS.asBlock(), DIAMOND_BRICK_STAIRS.asBlock())
				.add(QUARTZ_BRICK_STAIRS.asBlock(), QUARTZ_CRYSTAL_STAIRS.asBlock())
				.add(NETHERITE_BRICK_STAIRS.asBlock(), CUT_NETHERITE_STAIRS.asBlock())
				.add(ECHO_STAIRS.asBlock(), ECHO_CRYSTAL_STAIRS.asBlock())
				.add(GILDED_BLACKSTONE_STAIRS.asBlock(), POLISHED_GILDED_BLACKSTONE_STAIRS.asBlock(), POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS.asBlock())
				.add(SCULK_STONE_STAIRS.asBlock(), SCULK_STONE_BRICK_STAIRS.asBlock())
				.add(MUD_BRICK_STAIRS.asBlock());
		getOrCreateTagBuilder(BlockTags.TERRACOTTA)
				.add(Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA)
				.add(Blocks.MAGENTA_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA)
				.add(Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA)
				.add(Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA)
				.add(Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.GREEN_TERRACOTTA)
				.add(Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA);
		getOrCreateTagBuilder(BlockTags.WALL_SIGNS)
				.add(CHARRED_SIGN.getWallBlock())
				.add(MANGROVE_SIGN.getWallBlock(), BAMBOO_SIGN.getWallBlock());
		getOrCreateTagBuilder(BlockTags.WALLS)
				.add(POLISHED_ANDESITE_WALL.asBlock(), POLISHED_DIORITE_WALL.asBlock(), POLISHED_GRANITE_WALL.asBlock())
				.add(SMOOTH_SANDSTONE_WALL.asBlock(), SMOOTH_RED_SANDSTONE_WALL.asBlock())
				.add(DARK_PRISMARINE_WALL.asBlock(), PURPUR_WALL.asBlock())
				.add(CALCITE_WALL.asBlock(), SMOOTH_CALCITE_WALL.asBlock(), CALCITE_BRICK_WALL.asBlock())
				.add(DRIPSTONE_WALL.asBlock(), SMOOTH_DRIPSTONE_WALL.asBlock(), DRIPSTONE_BRICK_WALL.asBlock())
				.add(TUFF_WALL.asBlock(), SMOOTH_TUFF_WALL.asBlock(), TUFF_BRICK_WALL.asBlock())
				.add(AMETHYST_WALL.asBlock(), AMETHYST_BRICK_WALL.asBlock(), AMETHYST_CRYSTAL_WALL.asBlock())
				.add(EMERALD_BRICK_WALL.asBlock(), CUT_EMERALD_WALL.asBlock())
				.add(DIAMOND_WALL.asBlock(), DIAMOND_BRICK_WALL.asBlock())
				.add(SMOOTH_QUARTZ_WALL.asBlock(), QUARTZ_BRICK_WALL.asBlock(), QUARTZ_WALL.asBlock(), QUARTZ_CRYSTAL_WALL.asBlock())
				.add(NETHERITE_WALL.asBlock(), NETHERITE_BRICK_WALL.asBlock(), CUT_NETHERITE_WALL.asBlock())
				.add(ECHO_WALL.asBlock(), ECHO_CRYSTAL_WALL.asBlock())
				.add(GILDED_BLACKSTONE_WALL.asBlock(), POLISHED_GILDED_BLACKSTONE_WALL.asBlock(), POLISHED_GILDED_BLACKSTONE_BRICK_WALL.asBlock())
				.add(SCULK_STONE_WALL.asBlock(), SCULK_STONE_BRICK_WALL.asBlock())
				.add(MUD_BRICK_WALL.asBlock());
		getOrCreateTagBuilder(BlockTags.WITHER_IMMUNE).add(REINFORCED_DEEPSLATE.asBlock());
		getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS)
				.add(CHARRED_BUTTON.asBlock())
				.add(MANGROVE_BUTTON.asBlock(), BAMBOO_BUTTON.asBlock());
		getOrCreateTagBuilder(BlockTags.WOODEN_DOORS)
				.add(CHARRED_DOOR.asBlock())
				.add(MANGROVE_DOOR.asBlock(), BAMBOO_DOOR.asBlock());
		getOrCreateTagBuilder(BlockTags.WOODEN_FENCES)
				.add(CHARRED_FENCE.asBlock())
				.add(MANGROVE_FENCE.asBlock(), BAMBOO_FENCE.asBlock());
		getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES)
				.add(CHARRED_PRESSURE_PLATE.asBlock())
				.add(MANGROVE_PRESSURE_PLATE.asBlock(), BAMBOO_PRESSURE_PLATE.asBlock());
		getOrCreateTagBuilder(BlockTags.WOODEN_SLABS)
				.add(CHARRED_SLAB.asBlock())
				.add(MANGROVE_SLAB.asBlock(), BAMBOO_SLAB.asBlock(), BAMBOO_MOSAIC_SLAB.asBlock());
		getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS)
				.add(CHARRED_STAIRS.asBlock())
				.add(MANGROVE_STAIRS.asBlock(), BAMBOO_STAIRS.asBlock(), BAMBOO_MOSAIC_STAIRS.asBlock());
		getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS)
				.add(CHARRED_TRAPDOOR.asBlock())
				.add(MANGROVE_TRAPDOOR.asBlock(), BAMBOO_TRAPDOOR.asBlock());
		getOrCreateTagBuilder(BlockTags.WOOL).add(RAINBOW_WOOL.asBlock());

		getOrCreateTagBuilder(ModBlockTags.ANCIENT_CITY_REPLACEABLE)
				.add(Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE_BRICK_SLAB,
						Blocks.DEEPSLATE_TILE_SLAB, Blocks.DEEPSLATE_BRICK_STAIRS, Blocks.DEEPSLATE_TILE_WALL,
						Blocks.DEEPSLATE_BRICK_WALL, Blocks.COBBLED_DEEPSLATE, Blocks.CRACKED_DEEPSLATE_BRICKS,
						Blocks.CRACKED_DEEPSLATE_TILES)
				.addTag(ModBlockTags.SCULK_TURFS);
		getOrCreateTagBuilder(ModBlockTags.BOOKSHELVES).add(Blocks.BOOKSHELF)
				.add(ACACIA_BOOKSHELF.asBlock(), BIRCH_BOOKSHELF.asBlock(), CRIMSON_BOOKSHELF.asBlock(), DARK_OAK_BOOKSHELF.asBlock(),
						JUNGLE_BOOKSHELF.asBlock(), SPRUCE_BOOKSHELF.asBlock(), WARPED_BOOKSHELF.asBlock(),
						BAMBOO_BOOKSHELF.asBlock(), MANGROVE_BOOKSHELF.asBlock(), CHARRED_BOOKSHELF.asBlock());
		getOrCreateTagBuilder(ModBlockTags.CARVED_GOURDS).add(Blocks.CARVED_PUMPKIN);
		getOrCreateTagBuilder(ModBlockTags.CHARRED_LOGS).add(CHARRED_LOG.asBlock(), CHARRED_WOOD.asBlock(), STRIPPED_CHARRED_LOG.asBlock(), STRIPPED_CHARRED_WOOD.asBlock());
		getOrCreateTagBuilder(ModBlockTags.CONVERTIBLE_TO_MUD).add(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.ROOTED_DIRT);
		getOrCreateTagBuilder(ModBlockTags.DAMPENS_VIBRATIONS)
				.addTag(BlockTags.WOOL).addTag(ModBlockTags.WOOL_SLABS).addTag(ModBlockTags.WOOL_CARPETS)
				.addTag(ModBlockTags.FLEECE).addTag(ModBlockTags.FLEECE_SLABS).addTag(ModBlockTags.FLEECE_CARPETS);
		getOrCreateTagBuilder(ModBlockTags.ECHO_SOUND_BLOCKS)
				.add(BUDDING_ECHO.asBlock())
				.add(ECHO_BLOCK.asBlock(), ECHO_SLAB.asBlock(), ECHO_STAIRS.asBlock(), ECHO_WALL.asBlock())
				.add(ECHO_CRYSTAL_BLOCK.asBlock(), ECHO_CRYSTAL_SLAB.asBlock())
				.add(ECHO_CRYSTAL_STAIRS.asBlock(), ECHO_CRYSTAL_WALL.asBlock());
		getOrCreateTagBuilder(ModBlockTags.FLEECE)
				.add(FLEECE.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new))
				.add(RAINBOW_FLEECE.asBlock());
		getOrCreateTagBuilder(ModBlockTags.FLEECE_SLABS)
				.add(FLEECE_SLABS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new))
				.add(RAINBOW_FLEECE_SLAB.asBlock());
		getOrCreateTagBuilder(ModBlockTags.FLEECE_CARPETS)
				.add(FLEECE_CARPETS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new))
				.add(RAINBOW_FLEECE_CARPET.asBlock());
		getOrCreateTagBuilder(ModBlockTags.FROG_PREFER_JUMP_TO).add(Blocks.LILY_PAD, Blocks.BIG_DRIPLEAF);
		getOrCreateTagBuilder(ModBlockTags.FROGS_SPAWNABLE_ON).add(Blocks.GRASS_BLOCK)
				.add(MUD.asBlock(), MANGROVE_ROOTS.asBlock(), MUDDY_MANGROVE_ROOTS.asBlock());
		getOrCreateTagBuilder(ModBlockTags.GOURD_LANTERNS).add(Blocks.JACK_O_LANTERN);
		getOrCreateTagBuilder(ModBlockTags.GOURDS).add(Blocks.PUMPKIN, Blocks.MELON);
		getOrCreateTagBuilder(ModBlockTags.MANGROVE_LOGS).add(MANGROVE_LOG.asBlock(), MANGROVE_WOOD.asBlock(),STRIPPED_MANGROVE_LOG.asBlock(), STRIPPED_MANGROVE_WOOD.asBlock());
		getOrCreateTagBuilder(ModBlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)
				.add(MUD.asBlock(), MUDDY_MANGROVE_ROOTS.asBlock(), MANGROVE_ROOTS.asBlock())
				.add(MANGROVE_LEAVES.asBlock(), MANGROVE_PROPAGULE.asBlock())
				.add(Blocks.MOSS_CARPET, Blocks.VINE);
		getOrCreateTagBuilder(ModBlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH)
				.add(MUD.asBlock(), MUDDY_MANGROVE_ROOTS.asBlock(), MANGROVE_ROOTS.asBlock())
				.add(MANGROVE_PROPAGULE.asBlock())
				.add(Blocks.MOSS_CARPET, Blocks.VINE, Blocks.SNOW);
		getOrCreateTagBuilder(ModBlockTags.OCCLUDES_VIBRATION_SIGNALS)
				.addTag(BlockTags.WOOL).addTag(ModBlockTags.FLEECE);
		getOrCreateTagBuilder(ModBlockTags.PUMPKINS).add(Blocks.PUMPKIN);
		getOrCreateTagBuilder(ModBlockTags.SCULK_REPLACEABLE)
				.addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.DIRT).addTag(BlockTags.TERRACOTTA)
				.addTag(BlockTags.NYLIUM).addTag(BlockTags.BASE_STONE_NETHER).addTag(BlockTags.SAND)
				.add(Blocks.GRAVEL, Blocks.SOUL_SAND, Blocks.SOUL_SOIL, Blocks.CALCITE, Blocks.SMOOTH_BASALT)
				.add(Blocks.CLAY, Blocks.DRIPSTONE_BLOCK, Blocks.END_STONE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE)
				.addTag(ModBlockTags.SCULK_TURFS);
		getOrCreateTagBuilder(ModBlockTags.SCULK_REPLACEABLE_WORLD_GEN)
				.addTag(ModBlockTags.SCULK_REPLACEABLE)
				.add(Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_TILES, Blocks.COBBLED_DEEPSLATE)
				.add(Blocks.CRACKED_DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_TILES, Blocks.POLISHED_DEEPSLATE);
		getOrCreateTagBuilder(ModBlockTags.SCULK_TURFS)
				.add(CALCITE_SCULK_TURF.asBlock(), DEEPSLATE_SCULK_TURF.asBlock())
				.add(DRIPSTONE_SCULK_TURF.asBlock(), SMOOTH_BASALT_SCULK_TURF.asBlock())
				.add(TUFF_SCULK_TURF.asBlock());
		getOrCreateTagBuilder(ModBlockTags.SCULK_VEIN_CAN_PLACE_ON)
				.add(Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK)
				.add(Blocks.CALCITE, Blocks.TUFF, Blocks.DEEPSLATE);
		getOrCreateTagBuilder(ModBlockTags.WOOL_SLABS)
				.add(WOOL_SLABS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new))
				.add(RAINBOW_WOOL_SLAB.asBlock());
		getOrCreateTagBuilder(ModBlockTags.WOOL_CARPETS)
				.add(Arrays.stream(COLORS).map(Util::GetWoolCarpetBlock).toArray(Block[]::new))
				.add(RAINBOW_CARPET.asBlock());
	}
}