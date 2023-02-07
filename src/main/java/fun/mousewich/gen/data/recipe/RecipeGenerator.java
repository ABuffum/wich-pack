package fun.mousewich.gen.data.recipe;

import fun.mousewich.gen.data.tag.ModItemTags;
import fun.mousewich.util.Util;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.Consumer;

import static fun.mousewich.ModBase.*;

public class RecipeGenerator extends FabricRecipeProvider {
	public RecipeGenerator(FabricDataGenerator dataGenerator) { super(dataGenerator); }

	@Override
	protected Identifier getRecipeIdentifier(Identifier identifier) { return identifier; }

	public static void offerWoodcuttingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient) {
		offerWoodcuttingRecipe(exporter, output, ingredient, 1);
	}
	public static void offerWoodcuttingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, int count) {
		new SingleItemRecipeJsonBuilder(WOODCUTTING_RECIPE_SERIALIZER, Ingredient.ofItems(ingredient), output, count)
				.criterion("automatic", RecipeProvider.conditionsFromItem(Items.STICK))
				.offerTo(exporter, ID(convertBetween(output, ingredient) + "_woodcutting"));
	}
	private static void offerSmeltingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience) {
		offerSmelting(exporter, List.of(ingredient), output, (float)experience, cookingTime, null);
	}
	private static void offerSmokingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience) {
		offerCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING, cookingTime, ingredient, output, (float)experience);
	}
	private static void offerCampfireRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience) {
		offerCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, cookingTime, ingredient, output, (float)experience);
	}
	private static void offerSmithingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, ItemConvertible input) {
		SmithingRecipeJsonBuilder.create(Ingredient.ofItems(ingredient), Ingredient.ofItems(input), output.asItem())
				.criterion("automatic", RecipeProvider.conditionsFromItem(Items.STICK))
				.offerTo(exporter, ID(RecipeProvider.getItemPath(output) + "_smithing"));
	}
	//TODO: Several irregular recipes (stonecutting, smelting, etc.) are going to the minecraft data folder instead
	@Override
	protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
		//Overrides
		Recipes.MakeShaped(Items.ARROW, Items.STICK, 4).input('X', Items.FLINT).input('Y', ModItemTags.FEATHERS)
				.pattern("X").pattern("#").pattern("Y").offerTo(exporter);
		for (DyeColor color : COLORS) { //Banners & Beds
			Ingredient ingredient = Ingredient.ofItems(Util.GetWoolItem(color), FLEECE.get(color).asItem());
			Recipes.MakeSign(Util.GetBannerItem(color), ingredient).offerTo(exporter);
			Recipes.MakeBed(Util.GetBedItem(color), ingredient).offerTo(exporter);
		}
		Recipes.MakeShapeless(Items.WRITABLE_BOOK, Items.INK_SAC).input(ModItemTags.BOOKS).input(ModItemTags.FEATHERS).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(Blocks.LECTERN).input('S', ItemTags.WOODEN_SLABS).input('B', ModItemTags.BOOKSHELVES)
				.pattern("SSS").pattern(" B ").pattern(" S ")
				.criterion("has_book", RecipeProvider.conditionsFromItem(Items.BOOK))
				.offerTo(exporter);
		//Utility Recipes
		Recipes.MakeShapeless(Items.GLOW_INK_SAC, Items.INK_SAC).input(Items.GLOW_BERRIES).offerTo(exporter);
		Recipes.MakeShapeless(Items.NAME_TAG, Items.PAPER).input(Items.PAPER).input(Items.STRING).offerTo(exporter);
		//Jack O' Lanterns & Lanterns
		Recipes.MakeShaped(Items.JACK_O_LANTERN, Items.CARVED_PUMPKIN).input('T', ModItemTags.TORCHES).pattern("#").pattern("T").offerTo(exporter);
		Recipes.MakeLantern(Items.LANTERN, Items.IRON_NUGGET, ModItemTags.TORCHES).offerTo(exporter);
		Recipes.MakeLantern(Items.SOUL_LANTERN, Items.IRON_NUGGET, ModItemTags.SOUL_TORCHES).offerTo(exporter);
		//Woodcutting Recipes
		offerWoodcuttingRecipe(exporter, Items.ACACIA_SLAB, Items.ACACIA_PLANKS, 2);
		offerWoodcuttingRecipe(exporter, Items.ACACIA_STAIRS, Items.ACACIA_PLANKS);
		offerWoodcuttingRecipe(exporter, Items.ACACIA_FENCE, Items.ACACIA_PLANKS);
		offerWoodcuttingRecipe(exporter, Items.BIRCH_SLAB, Items.BIRCH_PLANKS, 2);
		offerWoodcuttingRecipe(exporter, Items.BIRCH_STAIRS, Items.BIRCH_PLANKS);
		offerWoodcuttingRecipe(exporter, Items.BIRCH_FENCE, Items.BIRCH_PLANKS);
		offerWoodcuttingRecipe(exporter, Items.DARK_OAK_SLAB, Items.DARK_OAK_PLANKS, 2);
		offerWoodcuttingRecipe(exporter, Items.DARK_OAK_STAIRS, Items.DARK_OAK_PLANKS);
		offerWoodcuttingRecipe(exporter, Items.DARK_OAK_FENCE, Items.DARK_OAK_PLANKS);
		offerWoodcuttingRecipe(exporter, Items.JUNGLE_SLAB, Items.JUNGLE_PLANKS, 2);
		offerWoodcuttingRecipe(exporter, Items.JUNGLE_STAIRS, Items.JUNGLE_PLANKS);
		offerWoodcuttingRecipe(exporter, Items.JUNGLE_FENCE, Items.JUNGLE_PLANKS);
		offerWoodcuttingRecipe(exporter, Items.SPRUCE_SLAB, Items.SPRUCE_PLANKS, 2);
		offerWoodcuttingRecipe(exporter, Items.SPRUCE_STAIRS, Items.SPRUCE_PLANKS);
		offerWoodcuttingRecipe(exporter, Items.SPRUCE_FENCE, Items.SPRUCE_PLANKS);
		offerWoodcuttingRecipe(exporter, Items.CRIMSON_SLAB, Items.CRIMSON_PLANKS, 2);
		offerWoodcuttingRecipe(exporter, Items.CRIMSON_STAIRS, Items.CRIMSON_PLANKS);
		offerWoodcuttingRecipe(exporter, Items.CRIMSON_FENCE, Items.CRIMSON_PLANKS);
		offerWoodcuttingRecipe(exporter, Items.WARPED_SLAB, Items.WARPED_PLANKS, 2);
		offerWoodcuttingRecipe(exporter, Items.WARPED_STAIRS, Items.WARPED_PLANKS);
		offerWoodcuttingRecipe(exporter, Items.WARPED_FENCE, Items.WARPED_PLANKS);

		//<editor-fold desc="Extended Wood">
		Recipes.MakeBookshelf(ACACIA_BOOKSHELF, Items.ACACIA_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(ACACIA_LADDER, Items.ACACIA_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(ACACIA_WOODCUTTER, Items.ACACIA_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(BIRCH_BOOKSHELF, Items.BIRCH_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(BIRCH_LADDER, Items.BIRCH_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(BIRCH_WOODCUTTER, Items.BIRCH_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(DARK_OAK_BOOKSHELF, Items.DARK_OAK_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(DARK_OAK_LADDER, Items.DARK_OAK_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(DARK_OAK_WOODCUTTER, Items.DARK_OAK_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(JUNGLE_BOOKSHELF, Items.JUNGLE_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(JUNGLE_LADDER, Items.JUNGLE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(JUNGLE_WOODCUTTER, Items.JUNGLE_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(Blocks.BOOKSHELF, Items.OAK_PLANKS).offerTo(exporter); //Override
		Recipes.MakeWoodcutter(WOODCUTTER, Items.OAK_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(SPRUCE_BOOKSHELF, Items.SPRUCE_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(SPRUCE_LADDER, Items.SPRUCE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(SPRUCE_WOODCUTTER, Items.SPRUCE_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(CRIMSON_BOOKSHELF, Items.CRIMSON_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(CRIMSON_LADDER, Items.CRIMSON_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(CRIMSON_WOODCUTTER, Items.CRIMSON_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(WARPED_BOOKSHELF, Items.WARPED_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(WARPED_LADDER, Items.WARPED_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(WARPED_WOODCUTTER, Items.WARPED_PLANKS).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Light Sources">
		Recipes.MakeShaped(UNDERWATER_TORCH, Items.STICK, 4).input('*', Items.GLOW_INK_SAC).pattern("*").pattern("#").offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Soul & Ender Fire">
		Recipes.MakeShaped(SOUL_CANDLE, Items.STRING).input('S', ItemTags.SOUL_FIRE_BASE_BLOCKS).pattern("#").pattern("S").offerTo(exporter);
		Recipes.MakeShaped(ENDER_CANDLE, Items.STRING).input('S', Items.POPPED_CHORUS_FRUIT).pattern("#").pattern("S").offerTo(exporter);
		Recipes.MakeShaped(ENDER_TORCH, Items.STICK, 4).input('*', ItemTags.COALS).input('S', Items.POPPED_CHORUS_FRUIT).pattern("*").pattern("#").pattern("S").offerTo(exporter);
		Recipes.MakeLantern(ENDER_LANTERN, Items.IRON_NUGGET, ModItemTags.ENDER_TORCHES).offerTo(exporter);
		Recipes.MakeCampfire(ENDER_CAMPFIRE, ItemTags.LOGS).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Extended Stone">
		Recipes.MakeWall(POLISHED_ANDESITE_WALL, Items.POLISHED_ANDESITE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, POLISHED_ANDESITE_WALL, Items.POLISHED_ANDESITE);
		Recipes.MakeWall(POLISHED_DIORITE_WALL, Items.POLISHED_DIORITE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, POLISHED_DIORITE_WALL, Items.POLISHED_DIORITE);
		Recipes.MakeWall(POLISHED_GRANITE_WALL, Items.POLISHED_GRANITE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, POLISHED_GRANITE_WALL, Items.POLISHED_GRANITE);
		Recipes.MakeWall(SMOOTH_SANDSTONE_WALL, Items.SMOOTH_SANDSTONE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SMOOTH_SANDSTONE_WALL, Items.SMOOTH_SANDSTONE);
		Recipes.MakeWall(SMOOTH_RED_SANDSTONE_WALL, Items.SMOOTH_RED_SANDSTONE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SMOOTH_RED_SANDSTONE_WALL, Items.SMOOTH_RED_SANDSTONE);
		Recipes.MakeWall(DARK_PRISMARINE_WALL, Items.DARK_PRISMARINE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DARK_PRISMARINE_WALL, Items.DARK_PRISMARINE);
		Recipes.MakeWall(PURPUR_WALL, Items.PURPUR_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, PURPUR_WALL, Items.PURPUR_BLOCK);
		//</editor-fold>
		//<editor-fold desc="Extended Calcite">
		Recipes.MakeSlab(CALCITE_SLAB, Items.CALCITE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CALCITE_SLAB, Items.CALCITE, 2);
		Recipes.MakeStairs(CALCITE_STAIRS, Items.CALCITE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CALCITE_STAIRS, Items.CALCITE);
		Recipes.MakeWall(CALCITE_WALL, Items.CALCITE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CALCITE_WALL, Items.CALCITE);
		//Smooth
		offerSmeltingRecipe(exporter, SMOOTH_CALCITE, Items.CALCITE, 200, 0.1);
		offerStonecuttingRecipe(exporter, SMOOTH_CALCITE, Items.CALCITE);
		Recipes.MakeSlab(SMOOTH_CALCITE_SLAB, SMOOTH_CALCITE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SMOOTH_CALCITE_SLAB, SMOOTH_CALCITE, 2);
		Recipes.MakeStairs(SMOOTH_CALCITE_STAIRS, SMOOTH_CALCITE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SMOOTH_CALCITE_STAIRS, SMOOTH_CALCITE);
		Recipes.MakeWall(SMOOTH_CALCITE_WALL, SMOOTH_CALCITE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SMOOTH_CALCITE_WALL, SMOOTH_CALCITE);
		//Bricks
		Recipes.Make2x2(CALCITE_BRICKS, Items.CALCITE, 4).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CALCITE_BRICKS, Items.CALCITE);
		Recipes.MakeSlab(CALCITE_BRICK_SLAB, CALCITE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CALCITE_BRICK_SLAB, Items.CALCITE, 2);
		offerStonecuttingRecipe(exporter, CALCITE_BRICK_SLAB, CALCITE_BRICKS, 2);
		Recipes.MakeStairs(CALCITE_BRICK_STAIRS, CALCITE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CALCITE_BRICK_STAIRS, Items.CALCITE);
		offerStonecuttingRecipe(exporter, CALCITE_BRICK_STAIRS, CALCITE_BRICKS);
		Recipes.MakeWall(CALCITE_BRICK_WALL, CALCITE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CALCITE_BRICK_WALL, Items.CALCITE);
		offerStonecuttingRecipe(exporter, CALCITE_BRICK_WALL, CALCITE_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Extended Dripstone">
		Recipes.MakeSlab(DRIPSTONE_SLAB, Items.DRIPSTONE_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DRIPSTONE_SLAB, Items.DRIPSTONE_BLOCK, 2);
		Recipes.MakeStairs(DRIPSTONE_STAIRS, Items.DRIPSTONE_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DRIPSTONE_STAIRS, Items.DRIPSTONE_BLOCK);
		Recipes.MakeWall(DRIPSTONE_WALL, Items.DRIPSTONE_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DRIPSTONE_WALL, Items.DRIPSTONE_BLOCK);
		//Smooth
		offerSmeltingRecipe(exporter, SMOOTH_DRIPSTONE, Items.DRIPSTONE_BLOCK, 200, 0.1);
		offerStonecuttingRecipe(exporter, SMOOTH_DRIPSTONE, Items.DRIPSTONE_BLOCK);
		Recipes.MakeSlab(SMOOTH_DRIPSTONE_SLAB, SMOOTH_DRIPSTONE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SMOOTH_DRIPSTONE_SLAB, SMOOTH_DRIPSTONE, 2);
		Recipes.MakeStairs(SMOOTH_DRIPSTONE_STAIRS, SMOOTH_DRIPSTONE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SMOOTH_DRIPSTONE_STAIRS, SMOOTH_DRIPSTONE);
		Recipes.MakeWall(SMOOTH_DRIPSTONE_WALL, SMOOTH_DRIPSTONE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SMOOTH_DRIPSTONE_WALL, SMOOTH_DRIPSTONE);
		//Bricks
		Recipes.Make2x2(DRIPSTONE_BRICKS, Items.DRIPSTONE_BLOCK, 4).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DRIPSTONE_BRICKS, Items.DRIPSTONE_BLOCK);
		Recipes.MakeSlab(DRIPSTONE_BRICK_SLAB, DRIPSTONE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DRIPSTONE_BRICK_SLAB, Items.DRIPSTONE_BLOCK, 2);
		offerStonecuttingRecipe(exporter, DRIPSTONE_BRICK_SLAB, DRIPSTONE_BRICKS, 2);
		Recipes.MakeStairs(DRIPSTONE_BRICK_STAIRS, DRIPSTONE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DRIPSTONE_BRICK_STAIRS, Items.DRIPSTONE_BLOCK);
		offerStonecuttingRecipe(exporter, DRIPSTONE_BRICK_STAIRS, DRIPSTONE_BRICKS);
		Recipes.MakeWall(DRIPSTONE_BRICK_WALL, DRIPSTONE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DRIPSTONE_BRICK_WALL, Items.DRIPSTONE_BLOCK);
		offerStonecuttingRecipe(exporter, DRIPSTONE_BRICK_WALL, DRIPSTONE_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Extended Tuff">
		Recipes.MakeSlab(TUFF_SLAB, Items.TUFF).offerTo(exporter);
		offerStonecuttingRecipe(exporter, TUFF_SLAB, Items.TUFF, 2);
		Recipes.MakeStairs(TUFF_STAIRS, Items.TUFF).offerTo(exporter);
		offerStonecuttingRecipe(exporter, TUFF_STAIRS, Items.TUFF);
		Recipes.MakeWall(TUFF_WALL, Items.TUFF).offerTo(exporter);
		offerStonecuttingRecipe(exporter, TUFF_WALL, Items.TUFF);
		//Smooth
		offerSmeltingRecipe(exporter, SMOOTH_TUFF, Items.TUFF, 200, 0.1);
		offerStonecuttingRecipe(exporter, SMOOTH_TUFF, Items.TUFF);
		Recipes.MakeSlab(SMOOTH_TUFF_SLAB, SMOOTH_TUFF).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SMOOTH_TUFF_SLAB, SMOOTH_TUFF, 2);
		Recipes.MakeStairs(SMOOTH_TUFF_STAIRS, SMOOTH_TUFF).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SMOOTH_TUFF_STAIRS, SMOOTH_TUFF);
		Recipes.MakeWall(SMOOTH_TUFF_WALL, SMOOTH_TUFF).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SMOOTH_TUFF_WALL, SMOOTH_TUFF);
		//Bricks
		Recipes.Make2x2(TUFF_BRICKS, Items.TUFF, 4).offerTo(exporter);
		offerStonecuttingRecipe(exporter, TUFF_BRICKS, Items.TUFF);
		Recipes.MakeSlab(TUFF_BRICK_SLAB, TUFF_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, TUFF_BRICK_SLAB, Items.TUFF, 2);
		offerStonecuttingRecipe(exporter, TUFF_BRICK_SLAB, TUFF_BRICKS, 2);
		Recipes.MakeStairs(TUFF_BRICK_STAIRS, TUFF_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, TUFF_BRICK_STAIRS, Items.TUFF);
		offerStonecuttingRecipe(exporter, TUFF_BRICK_STAIRS, TUFF_BRICKS);
		Recipes.MakeWall(TUFF_BRICK_WALL, TUFF_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, TUFF_BRICK_WALL, Items.TUFF);
		offerStonecuttingRecipe(exporter, TUFF_BRICK_WALL, TUFF_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Extended Amethyst">
		Recipes.MakeShapeless(Items.AMETHYST_SHARD, Items.AMETHYST_BLOCK, 4).offerTo(exporter, "amethyst_shard_from_amethyst_block");
		Recipes.MakeSlab(AMETHYST_SLAB, Items.AMETHYST_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, AMETHYST_SLAB, Items.AMETHYST_BLOCK, 2);
		Recipes.MakeStairs(AMETHYST_STAIRS, Items.AMETHYST_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, AMETHYST_STAIRS, Items.AMETHYST_BLOCK);
		Recipes.MakeWall(AMETHYST_WALL, Items.AMETHYST_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, AMETHYST_WALL, Items.AMETHYST_BLOCK);
		//Crystal Block
		Recipes.MakeShapeless(Items.AMETHYST_SHARD, AMETHYST_CRYSTAL_BLOCK, 9).offerTo(exporter, "amethyst_shard_from_amethyst_crystal_block");
		Recipes.Make3x3(AMETHYST_CRYSTAL_BLOCK, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeSlab(AMETHYST_CRYSTAL_SLAB, AMETHYST_CRYSTAL_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, AMETHYST_CRYSTAL_SLAB, AMETHYST_CRYSTAL_BLOCK, 2);
		Recipes.MakeStairs(AMETHYST_CRYSTAL_STAIRS, AMETHYST_CRYSTAL_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, AMETHYST_CRYSTAL_STAIRS, AMETHYST_CRYSTAL_BLOCK);
		Recipes.MakeWall(AMETHYST_CRYSTAL_WALL, AMETHYST_CRYSTAL_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, AMETHYST_CRYSTAL_WALL, AMETHYST_CRYSTAL_BLOCK);
		//Bricks
		Recipes.Make2x2(AMETHYST_BRICKS, Items.AMETHYST_BLOCK, 4).offerTo(exporter);
		offerStonecuttingRecipe(exporter, AMETHYST_BRICKS, Items.AMETHYST_BLOCK);
		Recipes.MakeSlab(AMETHYST_BRICK_SLAB, AMETHYST_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, AMETHYST_BRICK_SLAB, Items.AMETHYST_BLOCK, 2);
		offerStonecuttingRecipe(exporter, AMETHYST_BRICK_SLAB, AMETHYST_BRICKS, 2);
		Recipes.MakeStairs(AMETHYST_BRICK_STAIRS, AMETHYST_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, AMETHYST_BRICK_STAIRS, Items.AMETHYST_BLOCK);
		offerStonecuttingRecipe(exporter, AMETHYST_BRICK_STAIRS, AMETHYST_BRICKS);
		Recipes.MakeWall(AMETHYST_BRICK_WALL, AMETHYST_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, AMETHYST_BRICK_WALL, Items.AMETHYST_BLOCK);
		offerStonecuttingRecipe(exporter, AMETHYST_BRICK_WALL, AMETHYST_BRICKS);
		//Armor & Tools
		Recipes.MakeAxe(AMETHYST_AXE, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeKnife(AMETHYST_KNIFE, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeHoe(AMETHYST_HOE, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakePickaxe(AMETHYST_PICKAXE, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeShovel(AMETHYST_SHOVEL, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeSword(AMETHYST_SWORD, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeHelmet(AMETHYST_HELMET, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeChestplate(AMETHYST_CHESTPLATE, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeLeggings(AMETHYST_LEGGINGS, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeBoots(AMETHYST_BOOTS, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeHorseArmor(AMETHYST_HORSE_ARMOR, Items.AMETHYST_SHARD).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="More Glass">
		Recipes.Make3x2(TINTED_GLASS_PANE, Items.TINTED_GLASS).offerTo(exporter);
		Recipes.MakeSlab(TINTED_GLASS_SLAB, Items.TINTED_GLASS).offerTo(exporter);
		Recipes.MakeShaped(TINTED_GOGGLES, Items.TINTED_GLASS).input('X', Items.LEATHER).pattern("#X#").offerTo(exporter);
		Recipes.MakeSlab(GLASS_SLAB, Items.GLASS).offerTo(exporter);
		for (DyeColor color : COLORS) Recipes.MakeSlab(STAINED_GLASS_SLABS.get(color), Util.GetStainedGlassItem(color));
		//</editor-fold>
		//<editor-fold desc="Extended Emerald">
		Recipes.Make2x2(EMERALD_BRICKS, Items.EMERALD_BLOCK, 4).offerTo(exporter);
		offerStonecuttingRecipe(exporter, EMERALD_BRICKS, Items.EMERALD_BLOCK);
		Recipes.MakeSlab(EMERALD_BRICK_SLAB, EMERALD_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, EMERALD_BRICK_SLAB, Items.EMERALD_BLOCK, 2);
		offerStonecuttingRecipe(exporter, EMERALD_BRICK_SLAB, EMERALD_BRICKS, 2);
		Recipes.MakeStairs(EMERALD_BRICK_STAIRS, EMERALD_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, EMERALD_BRICK_STAIRS, Items.EMERALD_BLOCK);
		offerStonecuttingRecipe(exporter, EMERALD_BRICK_STAIRS, EMERALD_BRICKS);
		Recipes.MakeWall(EMERALD_BRICK_WALL, EMERALD_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, EMERALD_BRICK_WALL, Items.EMERALD_BLOCK);
		offerStonecuttingRecipe(exporter, EMERALD_BRICK_WALL, EMERALD_BRICKS);
		//Cut
		offerStonecuttingRecipe(exporter, CUT_EMERALD, Items.EMERALD_BLOCK);
		Recipes.MakeSlab(CUT_EMERALD_SLAB, CUT_EMERALD).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CUT_EMERALD_SLAB, Items.EMERALD_BLOCK, 2);
		offerStonecuttingRecipe(exporter, CUT_EMERALD_SLAB, CUT_EMERALD, 2);
		Recipes.MakeStairs(CUT_EMERALD_STAIRS, CUT_EMERALD).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CUT_EMERALD_STAIRS, Items.EMERALD_BLOCK);
		offerStonecuttingRecipe(exporter, CUT_EMERALD_STAIRS, CUT_EMERALD);
		Recipes.MakeWall(CUT_EMERALD_WALL, CUT_EMERALD).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CUT_EMERALD_WALL, Items.EMERALD_BLOCK);
		offerStonecuttingRecipe(exporter, CUT_EMERALD_WALL, CUT_EMERALD);
		//Armor & Tools
		Recipes.MakeAxe(EMERALD_AXE, Items.EMERALD).offerTo(exporter);
		Recipes.MakeKnife(EMERALD_KNIFE, Items.EMERALD).offerTo(exporter);
		Recipes.MakeHoe(EMERALD_HOE, Items.EMERALD).offerTo(exporter);
		Recipes.MakePickaxe(EMERALD_PICKAXE, Items.EMERALD).offerTo(exporter);
		Recipes.MakeShovel(EMERALD_SHOVEL, Items.EMERALD).offerTo(exporter);
		Recipes.MakeSword(EMERALD_SWORD, Items.EMERALD).offerTo(exporter);
		Recipes.MakeHelmet(EMERALD_HELMET, Items.EMERALD).offerTo(exporter);
		Recipes.MakeChestplate(EMERALD_CHESTPLATE, Items.EMERALD).offerTo(exporter);
		Recipes.MakeLeggings(EMERALD_LEGGINGS, Items.EMERALD).offerTo(exporter);
		Recipes.MakeBoots(EMERALD_BOOTS, Items.EMERALD).offerTo(exporter);
		Recipes.MakeHorseArmor(EMERALD_HORSE_ARMOR, Items.EMERALD).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Extended Diamond">
		Recipes.MakeSlab(DIAMOND_SLAB, Items.DIAMOND_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DIAMOND_SLAB, Items.DIAMOND_BLOCK, 2);
		Recipes.MakeStairs(DIAMOND_STAIRS, Items.DIAMOND_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DIAMOND_STAIRS, Items.DIAMOND_BLOCK);
		Recipes.MakeWall(DIAMOND_WALL, Items.DIAMOND_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DIAMOND_WALL, Items.DIAMOND_BLOCK);
		//Bricks
		Recipes.Make2x2(DIAMOND_BRICKS, Items.DIAMOND_BLOCK, 4).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DIAMOND_BRICKS, Items.DIAMOND_BLOCK);
		Recipes.MakeSlab(DIAMOND_BRICK_SLAB, DIAMOND_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DIAMOND_BRICK_SLAB, Items.DIAMOND_BLOCK, 2);
		offerStonecuttingRecipe(exporter, DIAMOND_BRICK_SLAB, DIAMOND_BRICKS, 2);
		Recipes.MakeStairs(DIAMOND_BRICK_STAIRS, DIAMOND_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DIAMOND_BRICK_STAIRS, Items.DIAMOND_BLOCK);
		offerStonecuttingRecipe(exporter, DIAMOND_BRICK_STAIRS, DIAMOND_BRICKS);
		Recipes.MakeWall(DIAMOND_BRICK_WALL, DIAMOND_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, DIAMOND_BRICK_WALL, Items.DIAMOND_BLOCK);
		offerStonecuttingRecipe(exporter, DIAMOND_BRICK_WALL, DIAMOND_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Quartz">
		Recipes.MakeShapeless(Items.QUARTZ, Items.QUARTZ_BLOCK, 4).offerTo(exporter, "quartz_from_quartz_block");
		Recipes.MakeWall(SMOOTH_QUARTZ_WALL, Items.SMOOTH_QUARTZ).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SMOOTH_QUARTZ_WALL, Items.SMOOTH_QUARTZ);
		Recipes.MakeWall(QUARTZ_WALL, Items.QUARTZ_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, QUARTZ_WALL, Items.QUARTZ_BLOCK);
		//Crystal Block
		Recipes.MakeShapeless(Items.QUARTZ, QUARTZ_CRYSTAL_BLOCK, 9).offerTo(exporter, "quartz_from_quartz_crystal_block");
		Recipes.Make3x3(QUARTZ_CRYSTAL_BLOCK, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeSlab(QUARTZ_CRYSTAL_SLAB, QUARTZ_CRYSTAL_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, QUARTZ_CRYSTAL_SLAB, QUARTZ_CRYSTAL_BLOCK, 2);
		Recipes.MakeStairs(QUARTZ_CRYSTAL_STAIRS, QUARTZ_CRYSTAL_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, QUARTZ_CRYSTAL_STAIRS, QUARTZ_CRYSTAL_BLOCK);
		Recipes.MakeWall(QUARTZ_CRYSTAL_WALL, QUARTZ_CRYSTAL_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, QUARTZ_CRYSTAL_WALL, QUARTZ_CRYSTAL_BLOCK);
		//Bricks
		Recipes.MakeSlab(QUARTZ_BRICK_SLAB, Items.QUARTZ_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, QUARTZ_BRICK_SLAB, Items.QUARTZ_BLOCK, 2);
		offerStonecuttingRecipe(exporter, QUARTZ_BRICK_SLAB, Items.QUARTZ_BRICKS, 2);
		Recipes.MakeStairs(QUARTZ_BRICK_STAIRS, Items.QUARTZ_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, QUARTZ_BRICK_STAIRS, Items.QUARTZ_BLOCK);
		offerStonecuttingRecipe(exporter, QUARTZ_BRICK_STAIRS, Items.QUARTZ_BRICKS);
		Recipes.MakeWall(QUARTZ_BRICK_WALL, Items.QUARTZ_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, QUARTZ_BRICK_WALL, Items.QUARTZ_BLOCK);
		offerStonecuttingRecipe(exporter, QUARTZ_BRICK_WALL, Items.QUARTZ_BRICKS);
		//Armor & Tools
		Recipes.MakeAxe(QUARTZ_AXE, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeKnife(QUARTZ_KNIFE, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeHoe(QUARTZ_HOE, Items.QUARTZ).offerTo(exporter);
		Recipes.MakePickaxe(QUARTZ_PICKAXE, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeShovel(QUARTZ_SHOVEL, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeSword(QUARTZ_SWORD, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeHelmet(QUARTZ_HELMET, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeChestplate(QUARTZ_CHESTPLATE, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeLeggings(QUARTZ_LEGGINGS, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeBoots(QUARTZ_BOOTS, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeHorseArmor(QUARTZ_HORSE_ARMOR, Items.QUARTZ).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Extended Iron">
		Recipes.MakeShapeless(IRON_BUTTON, Items.IRON_INGOT).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Extended Netherite">
		Recipes.MakeShaped(NETHERITE_TORCH, Items.STICK, 4).input('*', ItemTags.COALS).input('I', NETHERITE_NUGGET).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(NETHERITE_SOUL_TORCH, Ingredient.fromTag(ModItemTags.SOUL_TORCHES), 4).input('I', NETHERITE_NUGGET).pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(NETHERITE_ENDER_TORCH, Ingredient.fromTag(ModItemTags.ENDER_TORCHES), 4).input('I', NETHERITE_NUGGET).pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(UNDERWATER_NETHERITE_TORCH, Items.STICK, 4).input('*', Items.GLOW_INK_SAC).input('I', NETHERITE_NUGGET).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeLantern(NETHERITE_LANTERN, NETHERITE_NUGGET, ModItemTags.TORCHES).offerTo(exporter);
		Recipes.MakeLantern(NETHERITE_SOUL_LANTERN, NETHERITE_NUGGET, ModItemTags.SOUL_TORCHES).offerTo(exporter);
		Recipes.MakeLantern(NETHERITE_ENDER_LANTERN, NETHERITE_NUGGET, ModItemTags.ENDER_TORCHES).offerTo(exporter);
		Recipes.MakeShapeless(Items.NETHERITE_INGOT, NETHERITE_NUGGET, 9).offerTo(exporter, "netherite_nugget_from_netherite_ingot");
		Recipes.Make3x3(NETHERITE_NUGGET, Items.NETHERITE_INGOT).offerTo(exporter);
		Recipes.MakeShapeless(NETHERITE_BUTTON, Items.NETHERITE_INGOT).offerTo(exporter);
		Recipes.MakeShaped(NETHERITE_CHAIN, Items.NETHERITE_INGOT).input('N', NETHERITE_NUGGET).pattern("N").pattern("#").pattern("N").offerTo(exporter);
		Recipes.Make3x2(NETHERITE_BARS, Items.NETHERITE_INGOT).offerTo(exporter);
		Recipes.MakeWall(NETHERITE_WALL, Items.NETHERITE_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, NETHERITE_WALL, Items.NETHERITE_BLOCK);
		//Bricks
		Recipes.Make2x2(NETHERITE_BRICKS, Items.NETHERITE_BLOCK, 4).offerTo(exporter);
		offerStonecuttingRecipe(exporter, NETHERITE_BRICKS, Items.NETHERITE_BLOCK);
		Recipes.MakeSlab(NETHERITE_BRICK_SLAB, NETHERITE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, NETHERITE_BRICK_SLAB, Items.NETHERITE_BLOCK, 2);
		offerStonecuttingRecipe(exporter, NETHERITE_BRICK_SLAB, NETHERITE_BRICKS, 2);
		Recipes.MakeStairs(NETHERITE_BRICK_STAIRS, NETHERITE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, NETHERITE_BRICK_STAIRS, Items.NETHERITE_BLOCK);
		offerStonecuttingRecipe(exporter, NETHERITE_BRICK_STAIRS, NETHERITE_BRICKS);
		Recipes.MakeWall(NETHERITE_BRICK_WALL, NETHERITE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, NETHERITE_BRICK_WALL, Items.NETHERITE_BLOCK);
		offerStonecuttingRecipe(exporter, NETHERITE_BRICK_WALL, NETHERITE_BRICKS);
		Recipes.Make2x2(CUT_NETHERITE, Items.NETHERITE_BLOCK, 4).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CUT_NETHERITE, Items.NETHERITE_BLOCK, 4);
		Recipes.Make2x2(CUT_NETHERITE_PILLAR, CUT_NETHERITE, 4).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CUT_NETHERITE_PILLAR, CUT_NETHERITE);
		Recipes.MakeSlab(CUT_NETHERITE_SLAB, CUT_NETHERITE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CUT_NETHERITE_SLAB, CUT_NETHERITE, 2);
		offerStonecuttingRecipe(exporter, CUT_NETHERITE_SLAB, Items.NETHERITE_BLOCK, 8);
		Recipes.MakeStairs(CUT_NETHERITE_STAIRS, CUT_NETHERITE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CUT_NETHERITE_STAIRS, CUT_NETHERITE);
		offerStonecuttingRecipe(exporter, CUT_NETHERITE_STAIRS, Items.NETHERITE_BLOCK, 4);
		Recipes.MakeWall(CUT_NETHERITE_WALL, CUT_NETHERITE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, CUT_NETHERITE_WALL, CUT_NETHERITE);
		offerStonecuttingRecipe(exporter, CUT_NETHERITE_WALL, Items.NETHERITE_BLOCK, 4);
		Recipes.MakeHorseArmor(NETHERITE_HORSE_ARMOR, Items.NETHERITE_INGOT).offerTo(exporter);
		Recipes.MakePressurePlate(CRUSHING_WEIGHTED_PRESSURE_PLATE, Items.NETHERITE_INGOT).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Extended Bone">
		Recipes.MakeShaped(BONE_TORCH, Items.BONE, 4).input('*', ItemTags.COALS).pattern("*").pattern("#").offerTo(exporter);
		Recipes.MakeShaped(BONE_SOUL_TORCH, Items.BONE, 4).input('*', ItemTags.COALS).input('I', ItemTags.SOUL_FIRE_BASE_BLOCKS).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(BONE_ENDER_TORCH, Items.BONE, 4).input('*', ItemTags.COALS).input('I', Items.POPPED_CHORUS_FRUIT).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(UNDERWATER_BONE_TORCH, Items.BONE, 4).input('*', Items.GLOW_INK_SAC).pattern("*").pattern("#").offerTo(exporter);
		Recipes.MakeLadder(BONE_LADDER, Items.BONE).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Gilded Blackstone">
		offerSmithingRecipe(exporter, Items.GILDED_BLACKSTONE, Items.BLACKSTONE, Items.GOLD_INGOT);
		Recipes.MakeSlab(GILDED_BLACKSTONE_SLAB, Items.GILDED_BLACKSTONE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, GILDED_BLACKSTONE_SLAB, Items.GILDED_BLACKSTONE, 2);
		offerSmithingRecipe(exporter, GILDED_BLACKSTONE_SLAB, Items.BLACKSTONE_SLAB, Items.GOLD_INGOT);
		Recipes.MakeStairs(GILDED_BLACKSTONE_STAIRS, Items.GILDED_BLACKSTONE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, GILDED_BLACKSTONE_STAIRS, Items.GILDED_BLACKSTONE);
		offerSmithingRecipe(exporter, GILDED_BLACKSTONE_STAIRS, Items.BLACKSTONE_STAIRS, Items.GOLD_INGOT);
		Recipes.MakeWall(GILDED_BLACKSTONE_WALL, Items.GILDED_BLACKSTONE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, GILDED_BLACKSTONE_WALL, Items.GILDED_BLACKSTONE);
		offerSmithingRecipe(exporter, GILDED_BLACKSTONE_WALL, Items.BLACKSTONE_WALL, Items.GOLD_INGOT);
		//Polished
		Recipes.Make2x2(POLISHED_GILDED_BLACKSTONE, Items.GILDED_BLACKSTONE, 4).offerTo(exporter);
		offerStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE, Items.GILDED_BLACKSTONE);
		offerSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE, Items.POLISHED_BLACKSTONE, Items.GOLD_INGOT);
		Recipes.MakeSlab(POLISHED_GILDED_BLACKSTONE_SLAB, POLISHED_GILDED_BLACKSTONE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_SLAB, POLISHED_GILDED_BLACKSTONE, 2);
		offerSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_SLAB, Items.POLISHED_BLACKSTONE_SLAB, Items.GOLD_INGOT);
		Recipes.MakeStairs(POLISHED_GILDED_BLACKSTONE_STAIRS, POLISHED_GILDED_BLACKSTONE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_STAIRS, POLISHED_GILDED_BLACKSTONE);
		offerSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_STAIRS, Items.POLISHED_BLACKSTONE_STAIRS, Items.GOLD_INGOT);
		Recipes.MakeWall(POLISHED_GILDED_BLACKSTONE_WALL, POLISHED_GILDED_BLACKSTONE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_WALL, POLISHED_GILDED_BLACKSTONE);
		offerSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_WALL, Items.POLISHED_BLACKSTONE_WALL, Items.GOLD_INGOT);
		//Bricks
		Recipes.Make2x2(POLISHED_GILDED_BLACKSTONE_BRICKS, POLISHED_GILDED_BLACKSTONE, 4).offerTo(exporter);
		offerStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICKS, POLISHED_GILDED_BLACKSTONE);
		offerSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICKS, Items.POLISHED_BLACKSTONE_BRICKS, Items.GOLD_INGOT);
		Recipes.MakeSlab(POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, POLISHED_GILDED_BLACKSTONE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, POLISHED_GILDED_BLACKSTONE, 2);
		offerStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, POLISHED_GILDED_BLACKSTONE_BRICKS, 2);
		offerSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, Items.POLISHED_BLACKSTONE_BRICK_SLAB, Items.GOLD_INGOT);
		Recipes.MakeStairs(POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, POLISHED_GILDED_BLACKSTONE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, POLISHED_GILDED_BLACKSTONE);
		offerStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, POLISHED_GILDED_BLACKSTONE_BRICKS);
		offerSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, Items.POLISHED_BLACKSTONE_BRICK_STAIRS, Items.GOLD_INGOT);
		Recipes.MakeWall(POLISHED_GILDED_BLACKSTONE_BRICK_WALL, POLISHED_GILDED_BLACKSTONE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_WALL, POLISHED_GILDED_BLACKSTONE);
		offerStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_WALL, POLISHED_GILDED_BLACKSTONE_BRICKS);
		offerSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_WALL, Items.POLISHED_BLACKSTONE_BRICK_WALL, Items.GOLD_INGOT);
		//</editor-fold>
		//<editor-fold desc="Misc Stuff">
		Recipes.MakeSlab(COAL_SLAB, Items.COAL_BLOCK).offerTo(exporter);
		Recipes.MakeShapeless(Items.CHARCOAL, CHARCOAL_BLOCK, 9).offerTo(exporter, ID("charcoal_from_charcoal_block"));
		Recipes.Make3x3(CHARCOAL_BLOCK, Items.CHARCOAL).offerTo(exporter);
		Recipes.MakeSlab(CHARCOAL_SLAB, CHARCOAL_BLOCK).offerTo(exporter);
		Recipes.MakeShapeless(Items.COCOA_BEANS, COCOA_BLOCK, 9).offerTo(exporter, ID("cocoa_beans_from_cocoa_block"));
		Recipes.Make3x3(COCOA_BLOCK, Items.COCOA_BEANS).offerTo(exporter);
		ShapelessRecipeJsonBuilder.create(SEED_BLOCK).input(ModItemTags.SEEDS).criterion("automatic", RecipeProvider.conditionsFromItem(Items.STICK)).offerTo(exporter);
		Recipes.MakeShapeless(Items.HONEYCOMB, WAX_BLOCK, 9).offerTo(exporter, ID("honeycomb_from_wax_block"));
		Recipes.Make3x3(WAX_BLOCK, Items.HONEYCOMB).offerTo(exporter);
		Recipes.MakeShapeless(LAVA_BOTTLE, Items.GLASS_BOTTLE, 3).input(Items.LAVA_BUCKET).offerTo(exporter);
		Recipes.MakeShaped(Items.LAVA_BUCKET, LAVA_BOTTLE).input('B', Items.BUCKET).pattern("##").pattern("#B").offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Books">
		Recipes.MakeShapeless(UNREADABLE_BOOK, Items.BOOK).offerTo(exporter);
		Recipes.MakeShapeless(RED_BOOK, Items.BOOK).input(Items.RED_DYE).offerTo(exporter);
		Recipes.MakeShapeless(ORANGE_BOOK, Items.BOOK).input(Items.ORANGE_DYE).offerTo(exporter);
		Recipes.MakeShapeless(YELLOW_BOOK, Items.BOOK).input(Items.YELLOW_DYE).offerTo(exporter);
		Recipes.MakeShapeless(GREEN_BOOK, Items.BOOK).input(Items.GREEN_DYE).offerTo(exporter);
		Recipes.MakeShapeless(BLUE_BOOK, Items.BOOK).input(Items.BLUE_DYE).offerTo(exporter);
		Recipes.MakeShapeless(PURPLE_BOOK, Items.BOOK).input(Items.PURPLE_DYE).offerTo(exporter);
		Recipes.MakeShapeless(GRAY_BOOK, Items.BOOK).input(Items.GRAY_DYE).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="More Wool">
		for (DyeColor color : COLORS) Recipes.MakeSlab(WOOL_SLABS.get(color), Util.GetWoolItem(color));
		Recipes.MakeSlab(RAINBOW_WOOL_SLAB, RAINBOW_WOOL).offerTo(exporter);
		Recipes.MakeCarpet(RAINBOW_CARPET, RAINBOW_WOOL).offerTo(exporter);
		//Recipes.MakeBed(RAINBOW_BED, Ingredient.ofItems(RAINBOW_WOOL, RAINBOW_FLEECE)).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="More Moss">
		Recipes.MakeSlab(MOSS_SLAB, Items.MOSS_BLOCK).offerTo(exporter);
		//Recipes.MakeBed(MOSS_BED, Items.MOSS_BLOCK).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Extended Goat">
		offerSmeltingRecipe(exporter, COOKED_CHEVON, CHEVON, 200, 0.35);
		offerSmokingRecipe(exporter, COOKED_CHEVON, CHEVON, 100, 0.35);
		offerCampfireRecipe(exporter, COOKED_CHEVON, CHEVON, 600, 0.35);
		for (DyeColor color : COLORS) Recipes.Make2x2(FLEECE.get(color), Util.GetWoolItem(color)).offerTo(exporter);
		for (DyeColor color : COLORS) Recipes.MakeSlab(FLEECE_SLABS.get(color), FLEECE.get(color)).offerTo(exporter);
		for (DyeColor color : COLORS) Recipes.MakeCarpet(FLEECE_CARPETS.get(color), FLEECE.get(color)).offerTo(exporter);
		Recipes.MakeSlab(RAINBOW_FLEECE_SLAB, RAINBOW_FLEECE).offerTo(exporter);
		Recipes.MakeCarpet(RAINBOW_FLEECE_CARPET, RAINBOW_FLEECE).offerTo(exporter);
		Recipes.MakeHelmet(FLEECE_HELMET, Ingredient.fromTag(ModItemTags.FLEECE));
		Recipes.MakeChestplate(FLEECE_CHESTPLATE, Ingredient.fromTag(ModItemTags.FLEECE));
		Recipes.MakeLeggings(FLEECE_LEGGINGS, Ingredient.fromTag(ModItemTags.FLEECE));
		Recipes.MakeBoots(FLEECE_BOOTS, Ingredient.fromTag(ModItemTags.FLEECE));
		Recipes.MakeHorseArmor(FLEECE_HORSE_ARMOR, Ingredient.fromTag(ModItemTags.FLEECE));
		//</editor-fold>
		//<editor-fold desc="Studded Leather">
		Recipes.MakeShaped(STUDDED_LEATHER_HELMET, Items.LEATHER).input('C', Items.CHAIN).pattern("C#C").pattern("# #").offerTo(exporter);
		Recipes.MakeShaped(STUDDED_LEATHER_CHESTPLATE, Items.LEATHER).input('C', Items.CHAIN).pattern("C C").pattern("C#C").pattern("###").offerTo(exporter);
		Recipes.MakeShaped(STUDDED_LEATHER_LEGGINGS, Items.LEATHER).input('C', Items.CHAIN).pattern("C#C").pattern("C C").pattern("# #").offerTo(exporter);
		Recipes.MakeShaped(STUDDED_LEATHER_BOOTS, Items.LEATHER).input('C', Items.CHAIN).pattern("C C").pattern("# #").offerTo(exporter);
		Recipes.MakeShaped(STUDDED_LEATHER_HORSE_ARMOR, Items.LEATHER).input('C', Items.CHAIN).pattern("# #").pattern("C#C").pattern("# #").offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Charred Wood">
		Recipes.Make2x2(CHARRED_WOOD, CHARRED_LOG, 3).offerTo(exporter);
		Recipes.Make2x2(STRIPPED_CHARRED_WOOD, STRIPPED_CHARRED_LOG, 3).offerTo(exporter);
		Recipes.MakeShapeless(CHARRED_PLANKS, Ingredient.fromTag(ModItemTags.CHARRED_LOGS), 4).offerTo(exporter);
		Recipes.MakeSlab(CHARRED_SLAB, CHARRED_PLANKS).offerTo(exporter);
		offerWoodcuttingRecipe(exporter, CHARRED_SLAB, CHARRED_PLANKS, 2);
		Recipes.MakeSlab(CHARRED_STAIRS, CHARRED_PLANKS).offerTo(exporter);
		offerWoodcuttingRecipe(exporter, CHARRED_STAIRS, CHARRED_PLANKS);
		Recipes.MakeWoodenFence(CHARRED_FENCE, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenFenceGate(CHARRED_FENCE_GATE, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeDoor(CHARRED_DOOR, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenTrapdoor(CHARRED_TRAPDOOR, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakePressurePlate(CHARRED_PRESSURE_PLATE, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(CHARRED_BUTTON, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeSign(CHARRED_SIGN, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeBoat(CHARRED_BOAT, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeBookshelf(CHARRED_BOOKSHELF, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(CHARRED_LADDER, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(CHARRED_WOODCUTTER, CHARRED_PLANKS).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Music Discs">
		Recipes.Make3x3(MUSIC_DISC_5, DISC_FRAGMENT_5).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Mud">
		Recipes.MakeShapeless(PACKED_MUD, MUD).input(Items.WHEAT).offerTo(exporter);
		Recipes.Make2x2(MUD_BRICKS, PACKED_MUD, 4).offerTo(exporter);
		Recipes.MakeSlab(MUD_BRICK_SLAB, MUD_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, MUD_BRICK_SLAB, MUD_BRICKS, 2);
		Recipes.MakeStairs(MUD_BRICK_STAIRS, MUD_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, MUD_BRICK_STAIRS, MUD_BRICKS);
		Recipes.MakeWall(MUD_BRICK_WALL, MUD_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, MUD_BRICK_WALL, MUD_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Mangrove">
		Recipes.Make2x2(MANGROVE_WOOD, MANGROVE_LOG, 3).offerTo(exporter);
		Recipes.Make2x2(STRIPPED_MANGROVE_WOOD, STRIPPED_MANGROVE_LOG, 3).offerTo(exporter);
		Recipes.MakeShapeless(MANGROVE_PLANKS, Ingredient.fromTag(ModItemTags.MANGROVE_LOGS), 4).offerTo(exporter);
		Recipes.MakeSlab(MANGROVE_SLAB, MANGROVE_PLANKS).offerTo(exporter);
		offerWoodcuttingRecipe(exporter, MANGROVE_SLAB, MANGROVE_PLANKS, 2);
		Recipes.MakeSlab(MANGROVE_STAIRS, MANGROVE_PLANKS).offerTo(exporter);
		offerWoodcuttingRecipe(exporter, MANGROVE_STAIRS, MANGROVE_PLANKS);
		Recipes.MakeWoodenFence(MANGROVE_FENCE, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenFenceGate(MANGROVE_FENCE_GATE, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeDoor(MANGROVE_DOOR, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenTrapdoor(MANGROVE_TRAPDOOR, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakePressurePlate(MANGROVE_PRESSURE_PLATE, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(MANGROVE_BUTTON, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeSign(MANGROVE_SIGN, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeBoat(MANGROVE_BOAT, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(MUDDY_MANGROVE_ROOTS, MUD).input(MANGROVE_ROOTS).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Extended Mangrove">
		Recipes.MakeBookshelf(MANGROVE_BOOKSHELF, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(MANGROVE_LADDER, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(MANGROVE_WOODCUTTER, MANGROVE_PLANKS).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Bamboo Wood">
		Recipes.Make3x3(BAMBOO_BLOCK, Items.BAMBOO).offerTo(exporter);
		Recipes.MakeShapeless(BAMBOO_PLANKS, Ingredient.ofItems(BAMBOO_BLOCK, STRIPPED_BAMBOO_BLOCK)).offerTo(exporter, ID("bamboo_planks_from_bamboo_block"));
		Recipes.Make2x2(BAMBOO_PLANKS, Items.BAMBOO).offerTo(exporter);
		Recipes.MakeSlab(BAMBOO_SLAB, BAMBOO_PLANKS).offerTo(exporter);
		offerWoodcuttingRecipe(exporter, BAMBOO_SLAB, BAMBOO_PLANKS, 2);
		Recipes.MakeStairs(BAMBOO_STAIRS, BAMBOO_PLANKS).offerTo(exporter);
		offerWoodcuttingRecipe(exporter, BAMBOO_STAIRS, BAMBOO_PLANKS);
		Recipes.MakeWoodenFence(BAMBOO_FENCE, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenFenceGate(BAMBOO_FENCE_GATE, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeDoor(BAMBOO_DOOR, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenTrapdoor(BAMBOO_TRAPDOOR, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakePressurePlate(BAMBOO_PRESSURE_PLATE, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(BAMBOO_BUTTON, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeSign(BAMBOO_SIGN, BAMBOO_PLANKS).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Bamboo Mosaic">
		Recipes.Make2x1(BAMBOO_MOSAIC, BAMBOO_SLAB, 1).offerTo(exporter);
		Recipes.MakeSlab(BAMBOO_MOSAIC_SLAB, BAMBOO_MOSAIC).offerTo(exporter);
		offerWoodcuttingRecipe(exporter, BAMBOO_MOSAIC_SLAB, BAMBOO_MOSAIC, 2);
		Recipes.MakeStairs(BAMBOO_MOSAIC_STAIRS, BAMBOO_MOSAIC).offerTo(exporter);
		offerWoodcuttingRecipe(exporter, BAMBOO_MOSAIC_STAIRS, BAMBOO_MOSAIC);
		//</editor-fold>
		//<editor-fold desc="Extended Bamboo">
		Recipes.MakeShaped(BAMBOO_TORCH, Items.BAMBOO, 4).input('*', ItemTags.COALS).pattern("*").pattern("#").offerTo(exporter);
		Recipes.MakeShaped(BAMBOO_SOUL_TORCH, Items.BAMBOO, 4).input('*', ItemTags.COALS).input('I', ItemTags.SOUL_FIRE_BASE_BLOCKS).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(BAMBOO_ENDER_TORCH, Items.BAMBOO, 4).input('*', ItemTags.COALS).input('I', Items.POPPED_CHORUS_FRUIT).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(UNDERWATER_BAMBOO_TORCH, Items.BAMBOO, 4).input('*', Items.GLOW_INK_SAC).pattern("*").pattern("#").offerTo(exporter);
		Recipes.MakeBookshelf(BAMBOO_BOOKSHELF, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeLadder(BAMBOO_LADDER, Items.BAMBOO).offerTo(exporter);
		Recipes.MakeWoodcutter(BAMBOO_WOODCUTTER, BAMBOO_PLANKS).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Backport 1.19">
		Recipes.Make3x1(RECOVERY_COMPASS, ECHO_SHARD).input('C', Items.COMPASS).pattern("#C#").pattern("###").offerTo(exporter);
		Recipes.MakeShapeless(RECOVERY_COMPASS, RECOVERY_COMPASS).offerTo(exporter, "recovery_compass_clear_nbt");
		//</editor-fold>
		//<editor-fold desc="Extended Echo">
		Recipes.MakeShapeless(ECHO_SHARD, ECHO_BLOCK, 4).offerTo(exporter, "echo_shard_from_echo_block");
		Recipes.MakeSlab(ECHO_SLAB, ECHO_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, ECHO_SLAB, ECHO_BLOCK, 2);
		Recipes.MakeStairs(ECHO_STAIRS, ECHO_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, ECHO_STAIRS, ECHO_BLOCK);
		Recipes.MakeWall(ECHO_WALL, ECHO_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, ECHO_WALL, ECHO_BLOCK);
		//Crystal Block
		Recipes.MakeShapeless(ECHO_SHARD, ECHO_CRYSTAL_BLOCK, 9).offerTo(exporter, "echo_shard_from_echo_crystal_block");
		Recipes.Make3x3(ECHO_CRYSTAL_BLOCK, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeSlab(ECHO_CRYSTAL_SLAB, ECHO_CRYSTAL_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, ECHO_CRYSTAL_SLAB, ECHO_CRYSTAL_BLOCK, 2);
		Recipes.MakeStairs(ECHO_CRYSTAL_STAIRS, ECHO_CRYSTAL_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, ECHO_CRYSTAL_STAIRS, ECHO_CRYSTAL_BLOCK);
		Recipes.MakeWall(ECHO_CRYSTAL_WALL, ECHO_CRYSTAL_BLOCK).offerTo(exporter);
		offerStonecuttingRecipe(exporter, ECHO_CRYSTAL_WALL, ECHO_CRYSTAL_BLOCK);
		//Armor & Tools
		Recipes.MakeAxe(ECHO_AXE, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeKnife(ECHO_KNIFE, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeHoe(ECHO_HOE, ECHO_SHARD).offerTo(exporter);
		Recipes.MakePickaxe(ECHO_PICKAXE, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeShovel(ECHO_SHOVEL, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeSword(ECHO_SWORD, ECHO_SHARD).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Sculk Stone">
		Recipes.MakeSlab(SCULK_STONE_SLAB, SCULK_STONE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SCULK_STONE_SLAB, SCULK_STONE, 2);
		Recipes.MakeStairs(SCULK_STONE_STAIRS, SCULK_STONE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SCULK_STONE_STAIRS, SCULK_STONE);
		Recipes.MakeWall(SCULK_STONE_WALL, SCULK_STONE).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SCULK_STONE_WALL, SCULK_STONE);
		//Bricks
		Recipes.Make2x2(SCULK_STONE_BRICKS, SCULK_STONE, 4).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SCULK_STONE_BRICKS, SCULK_STONE);
		Recipes.MakeSlab(SCULK_STONE_BRICK_SLAB, SCULK_STONE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SCULK_STONE_BRICK_SLAB, SCULK_STONE, 2);
		offerStonecuttingRecipe(exporter, SCULK_STONE_BRICK_SLAB, SCULK_STONE_BRICKS, 2);
		Recipes.MakeStairs(SCULK_STONE_BRICK_STAIRS, SCULK_STONE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SCULK_STONE_BRICK_STAIRS, SCULK_STONE);
		offerStonecuttingRecipe(exporter, SCULK_STONE_BRICK_STAIRS, SCULK_STONE_BRICKS);
		Recipes.MakeWall(SCULK_STONE_BRICK_WALL, SCULK_STONE_BRICKS).offerTo(exporter);
		offerStonecuttingRecipe(exporter, SCULK_STONE_BRICK_WALL, SCULK_STONE);
		offerStonecuttingRecipe(exporter, SCULK_STONE_BRICK_WALL, SCULK_STONE_BRICKS);
		//</editor-fold>
	}
}
