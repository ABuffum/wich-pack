package fun.mousewich.gen.data.recipe;

import fun.mousewich.gen.data.tag.ModItemTags;
import fun.mousewich.trim.ArmorTrimPattern;
import fun.mousewich.util.ColorUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.Consumer;

import static fun.mousewich.ModBase.*;
import static fun.mousewich.ModBase.GOLD_SLAB;

public class RecipeGenerator extends FabricRecipeProvider {
	public RecipeGenerator(FabricDataGenerator dataGenerator) { super(dataGenerator); }

	@Override
	protected Identifier getRecipeIdentifier(Identifier identifier) { return identifier; }

	private static void OfferSmeltingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience) {
		offerSmelting(exporter, List.of(ingredient), output, (float)experience, cookingTime, null);
	}

	private static void OfferSmithingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, ItemConvertible input) {
		SmithingRecipeJsonBuilder.create(Ingredient.ofItems(ingredient), Ingredient.ofItems(input), output.asItem())
				.criterion("automatic", RecipeProvider.conditionsFromItem(Items.STICK))
				.offerTo(exporter, ID(RecipeProvider.getItemPath(output) + "_smithing"));
	}
	private static void OfferStonecuttingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient) {
		Recipes.MakeStonecutting(output, ingredient).offerTo(exporter, RecipeProvider.convertBetween(output, ingredient) + "_stonecutting");
	}
	private static void OfferStonecuttingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, int count) {
		Recipes.MakeStonecutting(output, ingredient, count).offerTo(exporter, RecipeProvider.convertBetween(output, ingredient) + "_stonecutting");
	}
	private static void OfferWoodcuttingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient) {
		Recipes.MakeWoodcutting(output, ingredient).offerTo(exporter, RecipeProvider.convertBetween(output, ingredient) + "_woodcutting");
	}
	private static void OfferWoodcuttingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible ingredient, int count) {
		Recipes.MakeWoodcutting(output, ingredient, count).offerTo(exporter, RecipeProvider.convertBetween(output, ingredient) + "_woodcutting");
	}

	private static String RecipeName(ItemConvertible item, String postfix) { return RecipeProvider.getItemPath(item) + postfix; }
	private static String _from_campfire(ItemConvertible item) { return RecipeName(item, "_from_campfire_cooking"); }
	private static String _from_smoking(ItemConvertible item) { return RecipeName(item, "_from_smoking"); }

	//TODO: Several irregular recipes (stonecutting, smelting, etc.) are going to the minecraft data folder instead
	@Override
	protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
		//Overrides
		Recipes.MakeShaped(Items.ARROW, Items.STICK, 4).input('X', Items.FLINT).input('Y', ModItemTags.FEATHERS)
				.pattern("X").pattern("#").pattern("Y").offerTo(exporter);
		for (DyeColor color : DyeColor.values()) { //Banners & Beds
			Ingredient ingredient = Ingredient.ofItems(ColorUtil.GetWoolItem(color), FLEECE.get(color).asItem());
			Recipes.MakeSign(ColorUtil.GetBannerItem(color), ingredient).offerTo(exporter);
			Recipes.MakeBed(ColorUtil.GetBedItem(color), ingredient).offerTo(exporter);
		}
		Recipes.MakeShapeless(Items.WRITABLE_BOOK, Items.INK_SAC).input(ModItemTags.BOOKS).input(ModItemTags.FEATHERS).offerTo(exporter);
		//Utility Recipes
		Recipes.MakeShapeless(Items.GLOW_INK_SAC, Items.INK_SAC).input(Items.GLOW_BERRIES).offerTo(exporter);
		Recipes.MakeShapeless(Items.NAME_TAG, Items.PAPER).input(Items.PAPER).input(Items.STRING).offerTo(exporter);
		Ingredient ironChain = Ingredient.ofItems(Items.CHAIN, WHITE_IRON_CHAIN);
		Recipes.Make3x1(Items.CHAINMAIL_HELMET, ironChain).pattern("# #").offerTo(exporter);
		Recipes.MakeShaped(Items.CHAINMAIL_CHESTPLATE, ironChain).pattern("# #").pattern("###").pattern("###").offerTo(exporter);
		Recipes.Make3x1(Items.CHAINMAIL_LEGGINGS, ironChain).pattern("# #").pattern("# #").offerTo(exporter);
		Recipes.MakeShaped(Items.CHAINMAIL_BOOTS, ironChain).pattern("# #").pattern("# #").offerTo(exporter);
		//Smithing Templates
		Recipes.MakeArmorTrim(NETHERITE_UPGRADE_SMITHING_TEMPLATE, Items.NETHERRACK).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.COAST, Items.COBBLESTONE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.DUNE, Items.SANDSTONE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.EYE, Items.END_STONE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.RIB, Items.NETHERRACK).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.SENTRY, Items.COBBLESTONE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.SNOUT, Items.BLACKSTONE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.SPIRE, Items.PURPUR_BLOCK).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.TIDE, Items.PRISMARINE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.VEX, Items.COBBLESTONE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.WARD, Items.COBBLED_DEEPSLATE).offerTo(exporter);
		Recipes.MakeArmorTrim(ArmorTrimPattern.WILD, Items.MOSSY_COBBLESTONE).offerTo(exporter);
		//Jack O' Lanterns & Lanterns
		Recipes.MakeShaped(Items.JACK_O_LANTERN, Items.CARVED_PUMPKIN).input('T', ModItemTags.TORCHES).pattern("#").pattern("T").offerTo(exporter);
		Recipes.MakeLantern(Items.LANTERN, Items.IRON_NUGGET, ModItemTags.TORCHES).offerTo(exporter);
		Recipes.MakeLantern(Items.SOUL_LANTERN, Items.IRON_NUGGET, ModItemTags.SOUL_TORCHES).offerTo(exporter);
		//Woodcutting Recipes
		OfferWoodcuttingRecipe(exporter, Items.ACACIA_SLAB, Items.ACACIA_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.ACACIA_STAIRS, Items.ACACIA_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.ACACIA_FENCE, Items.ACACIA_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.BIRCH_SLAB, Items.BIRCH_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.BIRCH_STAIRS, Items.BIRCH_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.BIRCH_FENCE, Items.BIRCH_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.DARK_OAK_SLAB, Items.DARK_OAK_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.DARK_OAK_STAIRS, Items.DARK_OAK_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.DARK_OAK_FENCE, Items.DARK_OAK_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.JUNGLE_SLAB, Items.JUNGLE_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.JUNGLE_STAIRS, Items.JUNGLE_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.JUNGLE_FENCE, Items.JUNGLE_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.SPRUCE_SLAB, Items.SPRUCE_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.SPRUCE_STAIRS, Items.SPRUCE_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.SPRUCE_FENCE, Items.SPRUCE_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.CRIMSON_SLAB, Items.CRIMSON_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.CRIMSON_STAIRS, Items.CRIMSON_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.CRIMSON_FENCE, Items.CRIMSON_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.WARPED_SLAB, Items.WARPED_PLANKS, 2);
		OfferWoodcuttingRecipe(exporter, Items.WARPED_STAIRS, Items.WARPED_PLANKS);
		OfferWoodcuttingRecipe(exporter, Items.WARPED_FENCE, Items.WARPED_PLANKS);

		//<editor-fold desc="Extended Wood">
		Recipes.MakeBookshelf(ACACIA_BOOKSHELF, Items.ACACIA_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(ACACIA_LADDER, Items.ACACIA_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(ACACIA_WOODCUTTER, Items.ACACIA_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(ACACIA_BARREL, Items.ACACIA_PLANKS, Items.ACACIA_SLAB).offerTo(exporter);
		Recipes.MakeLectern(ACACIA_LECTERN, Items.ACACIA_SLAB).offerTo(exporter);
		Recipes.MakeHangingSign(ACACIA_HANGING_SIGN, Items.STRIPPED_ACACIA_LOG).offerTo(exporter);
		Recipes.MakeShapeless(ACACIA_CHEST_BOAT, Items.ACACIA_BOAT).input(Items.CHEST).offerTo(exporter);
		Recipes.MakeBookshelf(BIRCH_BOOKSHELF, Items.BIRCH_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(BIRCH_LADDER, Items.BIRCH_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(BIRCH_WOODCUTTER, Items.BIRCH_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(BIRCH_BARREL, Items.BIRCH_PLANKS, Items.BIRCH_SLAB).offerTo(exporter);
		Recipes.MakeLectern(BIRCH_LECTERN, Items.BIRCH_SLAB).offerTo(exporter);
		Recipes.MakeHangingSign(BIRCH_HANGING_SIGN, Items.STRIPPED_BIRCH_LOG).offerTo(exporter);
		Recipes.MakeShapeless(BIRCH_CHEST_BOAT, Items.BIRCH_BOAT).input(Items.CHEST).offerTo(exporter);
		Recipes.MakeBookshelf(DARK_OAK_BOOKSHELF, Items.DARK_OAK_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(DARK_OAK_LADDER, Items.DARK_OAK_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(DARK_OAK_WOODCUTTER, Items.DARK_OAK_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(DARK_OAK_BARREL, Items.DARK_OAK_PLANKS, Items.DARK_OAK_SLAB).offerTo(exporter);
		Recipes.MakeLectern(DARK_OAK_LECTERN, Items.DARK_OAK_SLAB).offerTo(exporter);
		Recipes.MakeHangingSign(DARK_OAK_HANGING_SIGN, Items.STRIPPED_DARK_OAK_LOG).offerTo(exporter);
		Recipes.MakeShapeless(DARK_OAK_CHEST_BOAT, Items.DARK_OAK_BOAT).input(Items.CHEST).offerTo(exporter);
		Recipes.MakeBookshelf(JUNGLE_BOOKSHELF, Items.JUNGLE_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(JUNGLE_LADDER, Items.JUNGLE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(JUNGLE_WOODCUTTER, Items.JUNGLE_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(JUNGLE_BARREL, Items.JUNGLE_PLANKS, Items.JUNGLE_SLAB).offerTo(exporter);
		Recipes.MakeLectern(JUNGLE_LECTERN, Items.JUNGLE_SLAB).offerTo(exporter);
		Recipes.MakeHangingSign(JUNGLE_HANGING_SIGN, Items.STRIPPED_JUNGLE_LOG).offerTo(exporter);
		Recipes.MakeShapeless(JUNGLE_CHEST_BOAT, Items.JUNGLE_BOAT).input(Items.CHEST).offerTo(exporter);
		Recipes.MakeBookshelf(Blocks.BOOKSHELF, Items.OAK_PLANKS).offerTo(exporter); //Override
		Recipes.MakeWoodcutter(WOODCUTTER, Items.OAK_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(OAK_BARREL, Items.OAK_PLANKS, Items.OAK_SLAB).offerTo(exporter);
		Recipes.MakeLectern(Items.LECTERN, Items.OAK_SLAB).offerTo(exporter); //Override
		Recipes.MakeHangingSign(OAK_HANGING_SIGN, Items.STRIPPED_OAK_LOG).offerTo(exporter);
		Recipes.MakeShapeless(OAK_CHEST_BOAT, Items.OAK_BOAT).input(Items.CHEST).offerTo(exporter);
		Recipes.MakeBookshelf(SPRUCE_BOOKSHELF, Items.SPRUCE_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(SPRUCE_LADDER, Items.SPRUCE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(SPRUCE_WOODCUTTER, Items.SPRUCE_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(Items.BARREL, Items.SPRUCE_PLANKS, Items.SPRUCE_SLAB).offerTo(exporter); //Override
		Recipes.MakeLectern(SPRUCE_LECTERN, Items.SPRUCE_SLAB).offerTo(exporter);
		Recipes.MakeHangingSign(SPRUCE_HANGING_SIGN, Items.STRIPPED_SPRUCE_LOG).offerTo(exporter);
		Recipes.MakeShapeless(SPRUCE_CHEST_BOAT, Items.SPRUCE_BOAT).input(Items.CHEST).offerTo(exporter);
		Recipes.MakeBookshelf(CRIMSON_BOOKSHELF, Items.CRIMSON_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(CRIMSON_LADDER, Items.CRIMSON_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(CRIMSON_WOODCUTTER, Items.CRIMSON_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(CRIMSON_BARREL, Items.CRIMSON_PLANKS, Items.CRIMSON_SLAB).offerTo(exporter);
		Recipes.MakeLectern(CRIMSON_LECTERN, Items.CRIMSON_SLAB).offerTo(exporter);
		Recipes.MakeHangingSign(CRIMSON_HANGING_SIGN, Items.STRIPPED_CRIMSON_STEM).offerTo(exporter);
		Recipes.MakeBookshelf(WARPED_BOOKSHELF, Items.WARPED_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(WARPED_LADDER, Items.WARPED_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(WARPED_WOODCUTTER, Items.WARPED_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(WARPED_BARREL, Items.WARPED_PLANKS, Items.WARPED_SLAB).offerTo(exporter);
		Recipes.MakeLectern(WARPED_LECTERN, Items.WARPED_SLAB).offerTo(exporter);
		Recipes.MakeHangingSign(WARPED_HANGING_SIGN, Items.STRIPPED_WARPED_STEM).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Light Sources">
		Recipes.MakeShaped(UNDERWATER_TORCH, Items.STICK, 4).input('*', Items.GLOW_INK_SAC).pattern("*").pattern("#").offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Soul & Ender Fire">
		Recipes.MakeShaped(SOUL_CANDLE, Items.STRING).input('S', ItemTags.SOUL_FIRE_BASE_BLOCKS).pattern("#").pattern("S").offerTo(exporter);
		Recipes.MakeShaped(ENDER_CANDLE, Items.STRING).input('S', Items.POPPED_CHORUS_FRUIT).pattern("#").pattern("S").offerTo(exporter);
		Recipes.MakeShaped(ENDER_TORCH, Items.STICK, 4).input('*', ItemTags.COALS).input('S', Items.POPPED_CHORUS_FRUIT).pattern("*").pattern("#").pattern("S").offerTo(exporter);
		Recipes.MakeLantern(ENDER_LANTERN, Items.IRON_NUGGET, ModItemTags.ENDER_TORCHES).offerTo(exporter);
		Recipes.MakeEnderCampfire(ENDER_CAMPFIRE, ItemTags.LOGS).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Extended Stone">
		Recipes.MakeWall(POLISHED_ANDESITE_WALL, Items.POLISHED_ANDESITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_ANDESITE_WALL, Items.POLISHED_ANDESITE);
		Recipes.MakeWall(POLISHED_DIORITE_WALL, Items.POLISHED_DIORITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_DIORITE_WALL, Items.POLISHED_DIORITE);
		Recipes.MakeWall(POLISHED_GRANITE_WALL, Items.POLISHED_GRANITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GRANITE_WALL, Items.POLISHED_GRANITE);
		Recipes.MakeWall(SMOOTH_SANDSTONE_WALL, Items.SMOOTH_SANDSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_SANDSTONE_WALL, Items.SMOOTH_SANDSTONE);
		Recipes.MakeWall(SMOOTH_RED_SANDSTONE_WALL, Items.SMOOTH_RED_SANDSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_RED_SANDSTONE_WALL, Items.SMOOTH_RED_SANDSTONE);
		Recipes.MakeWall(DARK_PRISMARINE_WALL, Items.DARK_PRISMARINE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DARK_PRISMARINE_WALL, Items.DARK_PRISMARINE);
		Recipes.MakeWall(PURPUR_WALL, Items.PURPUR_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_WALL, Items.PURPUR_BLOCK);
		Recipes.Make2x2(PURPUR_BRICKS, Items.PURPUR_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_BRICKS, Items.PURPUR_BLOCK);
		Recipes.MakeStairs(PURPUR_BRICK_STAIRS, PURPUR_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_BRICK_STAIRS, PURPUR_BRICKS);
		Recipes.MakeSlab(PURPUR_BRICK_SLAB, PURPUR_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_BRICK_SLAB, PURPUR_BRICKS, 2);
		Recipes.MakeWall(PURPUR_BRICK_WALL, PURPUR_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, PURPUR_BRICK_WALL, PURPUR_BRICKS);
		OfferSmeltingRecipe(exporter, SMOOTH_PURPUR, Items.PURPUR_BLOCK, 200, 0.1);
		OfferStonecuttingRecipe(exporter, SMOOTH_PURPUR, Items.PURPUR_BLOCK);
		OfferSmeltingRecipe(exporter, SMOOTH_PURPUR_STAIRS, Items.PURPUR_STAIRS, 200, 0.1);
		OfferStonecuttingRecipe(exporter, SMOOTH_PURPUR_STAIRS, Items.PURPUR_BLOCK);
		OfferStonecuttingRecipe(exporter, SMOOTH_PURPUR_STAIRS, SMOOTH_PURPUR);
		OfferSmeltingRecipe(exporter, SMOOTH_PURPUR_SLAB, Items.PURPUR_SLAB, 200, 0.1);
		OfferStonecuttingRecipe(exporter, SMOOTH_PURPUR_SLAB, Items.PURPUR_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, SMOOTH_PURPUR_SLAB, SMOOTH_PURPUR, 2);
		OfferSmeltingRecipe(exporter, SMOOTH_PURPUR_WALL, PURPUR_WALL, 200, 0.1);
		OfferStonecuttingRecipe(exporter, SMOOTH_PURPUR_WALL, Items.PURPUR_BLOCK);
		OfferStonecuttingRecipe(exporter, SMOOTH_PURPUR_WALL, SMOOTH_PURPUR);
		//</editor-fold>
		//<editor-fold desc="Extended Calcite">
		Recipes.MakeSlab(CALCITE_SLAB, Items.CALCITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_SLAB, Items.CALCITE, 2);
		Recipes.MakeStairs(CALCITE_STAIRS, Items.CALCITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_STAIRS, Items.CALCITE);
		Recipes.MakeWall(CALCITE_WALL, Items.CALCITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_WALL, Items.CALCITE);
		//Smooth
		OfferSmeltingRecipe(exporter, SMOOTH_CALCITE, Items.CALCITE, 200, 0.1);
		OfferStonecuttingRecipe(exporter, SMOOTH_CALCITE, Items.CALCITE);
		Recipes.MakeSlab(SMOOTH_CALCITE_SLAB, SMOOTH_CALCITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_CALCITE_SLAB, SMOOTH_CALCITE, 2);
		Recipes.MakeStairs(SMOOTH_CALCITE_STAIRS, SMOOTH_CALCITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_CALCITE_STAIRS, SMOOTH_CALCITE);
		Recipes.MakeWall(SMOOTH_CALCITE_WALL, SMOOTH_CALCITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_CALCITE_WALL, SMOOTH_CALCITE);
		//Bricks
		Recipes.Make2x2(CALCITE_BRICKS, Items.CALCITE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_BRICKS, Items.CALCITE);
		Recipes.MakeSlab(CALCITE_BRICK_SLAB, CALCITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_BRICK_SLAB, Items.CALCITE, 2);
		OfferStonecuttingRecipe(exporter, CALCITE_BRICK_SLAB, CALCITE_BRICKS, 2);
		Recipes.MakeStairs(CALCITE_BRICK_STAIRS, CALCITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_BRICK_STAIRS, Items.CALCITE);
		OfferStonecuttingRecipe(exporter, CALCITE_BRICK_STAIRS, CALCITE_BRICKS);
		Recipes.MakeWall(CALCITE_BRICK_WALL, CALCITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CALCITE_BRICK_WALL, Items.CALCITE);
		OfferStonecuttingRecipe(exporter, CALCITE_BRICK_WALL, CALCITE_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Extended Dripstone">
		Recipes.MakeSlab(DRIPSTONE_SLAB, Items.DRIPSTONE_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_SLAB, Items.DRIPSTONE_BLOCK, 2);
		Recipes.MakeStairs(DRIPSTONE_STAIRS, Items.DRIPSTONE_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_STAIRS, Items.DRIPSTONE_BLOCK);
		Recipes.MakeWall(DRIPSTONE_WALL, Items.DRIPSTONE_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_WALL, Items.DRIPSTONE_BLOCK);
		//Smooth
		OfferSmeltingRecipe(exporter, SMOOTH_DRIPSTONE, Items.DRIPSTONE_BLOCK, 200, 0.1);
		OfferStonecuttingRecipe(exporter, SMOOTH_DRIPSTONE, Items.DRIPSTONE_BLOCK);
		Recipes.MakeSlab(SMOOTH_DRIPSTONE_SLAB, SMOOTH_DRIPSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_DRIPSTONE_SLAB, SMOOTH_DRIPSTONE, 2);
		Recipes.MakeStairs(SMOOTH_DRIPSTONE_STAIRS, SMOOTH_DRIPSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_DRIPSTONE_STAIRS, SMOOTH_DRIPSTONE);
		Recipes.MakeWall(SMOOTH_DRIPSTONE_WALL, SMOOTH_DRIPSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_DRIPSTONE_WALL, SMOOTH_DRIPSTONE);
		//Bricks
		Recipes.Make2x2(DRIPSTONE_BRICKS, Items.DRIPSTONE_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_BRICKS, Items.DRIPSTONE_BLOCK);
		Recipes.MakeSlab(DRIPSTONE_BRICK_SLAB, DRIPSTONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_BRICK_SLAB, Items.DRIPSTONE_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_BRICK_SLAB, DRIPSTONE_BRICKS, 2);
		Recipes.MakeStairs(DRIPSTONE_BRICK_STAIRS, DRIPSTONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_BRICK_STAIRS, Items.DRIPSTONE_BLOCK);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_BRICK_STAIRS, DRIPSTONE_BRICKS);
		Recipes.MakeWall(DRIPSTONE_BRICK_WALL, DRIPSTONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_BRICK_WALL, Items.DRIPSTONE_BLOCK);
		OfferStonecuttingRecipe(exporter, DRIPSTONE_BRICK_WALL, DRIPSTONE_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Extended Tuff">
		Recipes.MakeSlab(TUFF_SLAB, Items.TUFF).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_SLAB, Items.TUFF, 2);
		Recipes.MakeStairs(TUFF_STAIRS, Items.TUFF).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_STAIRS, Items.TUFF);
		Recipes.MakeWall(TUFF_WALL, Items.TUFF).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_WALL, Items.TUFF);
		//Smooth
		OfferSmeltingRecipe(exporter, SMOOTH_TUFF, Items.TUFF, 200, 0.1);
		OfferStonecuttingRecipe(exporter, SMOOTH_TUFF, Items.TUFF);
		Recipes.MakeSlab(SMOOTH_TUFF_SLAB, SMOOTH_TUFF).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_TUFF_SLAB, SMOOTH_TUFF, 2);
		Recipes.MakeStairs(SMOOTH_TUFF_STAIRS, SMOOTH_TUFF).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_TUFF_STAIRS, SMOOTH_TUFF);
		Recipes.MakeWall(SMOOTH_TUFF_WALL, SMOOTH_TUFF).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_TUFF_WALL, SMOOTH_TUFF);
		//Bricks
		Recipes.Make2x2(TUFF_BRICKS, Items.TUFF, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_BRICKS, Items.TUFF);
		Recipes.MakeSlab(TUFF_BRICK_SLAB, TUFF_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_BRICK_SLAB, Items.TUFF, 2);
		OfferStonecuttingRecipe(exporter, TUFF_BRICK_SLAB, TUFF_BRICKS, 2);
		Recipes.MakeStairs(TUFF_BRICK_STAIRS, TUFF_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_BRICK_STAIRS, Items.TUFF);
		OfferStonecuttingRecipe(exporter, TUFF_BRICK_STAIRS, TUFF_BRICKS);
		Recipes.MakeWall(TUFF_BRICK_WALL, TUFF_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, TUFF_BRICK_WALL, Items.TUFF);
		OfferStonecuttingRecipe(exporter, TUFF_BRICK_WALL, TUFF_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Extended Amethyst">
		Recipes.MakeShapeless(Items.AMETHYST_SHARD, Items.AMETHYST_BLOCK, 4).offerTo(exporter, ID("amethyst_shard_from_amethyst_block"));
		Recipes.MakeSlab(AMETHYST_SLAB, Items.AMETHYST_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_SLAB, Items.AMETHYST_BLOCK, 2);
		Recipes.MakeStairs(AMETHYST_STAIRS, Items.AMETHYST_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_STAIRS, Items.AMETHYST_BLOCK);
		Recipes.MakeWall(AMETHYST_WALL, Items.AMETHYST_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_WALL, Items.AMETHYST_BLOCK);
		//Crystal Block
		Recipes.MakeShapeless(Items.AMETHYST_SHARD, AMETHYST_CRYSTAL_BLOCK, 9).offerTo(exporter, ID("amethyst_shard_from_amethyst_crystal_block"));
		Recipes.Make3x3(AMETHYST_CRYSTAL_BLOCK, Items.AMETHYST_SHARD).offerTo(exporter);
		Recipes.MakeSlab(AMETHYST_CRYSTAL_SLAB, AMETHYST_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_CRYSTAL_SLAB, AMETHYST_CRYSTAL_BLOCK, 2);
		Recipes.MakeStairs(AMETHYST_CRYSTAL_STAIRS, AMETHYST_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_CRYSTAL_STAIRS, AMETHYST_CRYSTAL_BLOCK);
		Recipes.MakeWall(AMETHYST_CRYSTAL_WALL, AMETHYST_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_CRYSTAL_WALL, AMETHYST_CRYSTAL_BLOCK);
		//Bricks
		Recipes.Make2x2(AMETHYST_BRICKS, Items.AMETHYST_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_BRICKS, Items.AMETHYST_BLOCK);
		Recipes.MakeSlab(AMETHYST_BRICK_SLAB, AMETHYST_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_BRICK_SLAB, Items.AMETHYST_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, AMETHYST_BRICK_SLAB, AMETHYST_BRICKS, 2);
		Recipes.MakeStairs(AMETHYST_BRICK_STAIRS, AMETHYST_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_BRICK_STAIRS, Items.AMETHYST_BLOCK);
		OfferStonecuttingRecipe(exporter, AMETHYST_BRICK_STAIRS, AMETHYST_BRICKS);
		Recipes.MakeWall(AMETHYST_BRICK_WALL, AMETHYST_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, AMETHYST_BRICK_WALL, Items.AMETHYST_BLOCK);
		OfferStonecuttingRecipe(exporter, AMETHYST_BRICK_WALL, AMETHYST_BRICKS);
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
		//<editor-fold desc="Arrows">
		Recipes.MakeShaped(AMETHYST_ARROW, Items.AMETHYST_SHARD).input('|', Items.STICK).input('F', ModItemTags.FEATHERS).pattern("  #").pattern(" | ").pattern("F  ").offerTo(exporter);
		Recipes.MakeShapeless(CHORUS_ARROW, Items.CHORUS_FRUIT).input(ItemTags.ARROWS).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Obsidian">
		Recipes.MakeSlab(OBSIDIAN_SLAB, Items.OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, OBSIDIAN_SLAB, Items.OBSIDIAN, 2);
		Recipes.MakeStairs(OBSIDIAN_STAIRS, Items.OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, OBSIDIAN_STAIRS, Items.OBSIDIAN);
		Recipes.MakeWall(OBSIDIAN_WALL, Items.OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, OBSIDIAN_WALL, Items.OBSIDIAN);
		//Crying
		Recipes.MakeSlab(CRYING_OBSIDIAN_SLAB, Items.CRYING_OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CRYING_OBSIDIAN_SLAB, Items.CRYING_OBSIDIAN, 2);
		Recipes.MakeStairs(CRYING_OBSIDIAN_STAIRS, Items.CRYING_OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CRYING_OBSIDIAN_STAIRS, Items.CRYING_OBSIDIAN);
		Recipes.MakeWall(CRYING_OBSIDIAN_WALL, Items.CRYING_OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CRYING_OBSIDIAN_WALL, Items.CRYING_OBSIDIAN);
		//Bleeding
		Recipes.MakeSlab(BLEEDING_OBSIDIAN_SLAB, BLEEDING_OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, BLEEDING_OBSIDIAN_SLAB, BLEEDING_OBSIDIAN, 2);
		Recipes.MakeStairs(BLEEDING_OBSIDIAN_STAIRS, BLEEDING_OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, BLEEDING_OBSIDIAN_STAIRS, BLEEDING_OBSIDIAN);
		Recipes.MakeWall(BLEEDING_OBSIDIAN_WALL, BLEEDING_OBSIDIAN).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, BLEEDING_OBSIDIAN_WALL, BLEEDING_OBSIDIAN);
		//</editor-fold>
		//<editor-fold desc="More Glass">
		Recipes.Make3x2(TINTED_GLASS_PANE, Items.TINTED_GLASS).offerTo(exporter);
		Recipes.MakeSlab(TINTED_GLASS_SLAB, Items.TINTED_GLASS).offerTo(exporter);
		Recipes.Make3x2(TINTED_GLASS_TRAPDOOR, TINTED_GLASS_PANE).offerTo(exporter);
		Recipes.MakeShaped(TINTED_GOGGLES, Items.TINTED_GLASS).input('X', Items.LEATHER).pattern("#X#").offerTo(exporter);
		Recipes.MakeSlab(GLASS_SLAB, Items.GLASS).offerTo(exporter);
		Recipes.Make3x2(GLASS_TRAPDOOR, Items.GLASS_PANE).offerTo(exporter);
		for (DyeColor color : DyeColor.values()){
			Recipes.MakeSlab(STAINED_GLASS_SLABS.get(color), ColorUtil.GetStainedGlassItem(color));
			Recipes.Make2x2(STAINED_GLASS_TRAPDOORS.get(color), ColorUtil.GetStainedGlassPaneItem(color));
		}
		//</editor-fold>
		//<editor-fold desc="Extended Emerald">
		Recipes.Make2x2(EMERALD_BRICKS, Items.EMERALD_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EMERALD_BRICKS, Items.EMERALD_BLOCK);
		Recipes.MakeSlab(EMERALD_BRICK_SLAB, EMERALD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EMERALD_BRICK_SLAB, Items.EMERALD_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, EMERALD_BRICK_SLAB, EMERALD_BRICKS, 2);
		Recipes.MakeStairs(EMERALD_BRICK_STAIRS, EMERALD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EMERALD_BRICK_STAIRS, Items.EMERALD_BLOCK);
		OfferStonecuttingRecipe(exporter, EMERALD_BRICK_STAIRS, EMERALD_BRICKS);
		Recipes.MakeWall(EMERALD_BRICK_WALL, EMERALD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EMERALD_BRICK_WALL, Items.EMERALD_BLOCK);
		OfferStonecuttingRecipe(exporter, EMERALD_BRICK_WALL, EMERALD_BRICKS);
		//Cut
		OfferStonecuttingRecipe(exporter, CUT_EMERALD, Items.EMERALD_BLOCK);
		Recipes.MakeSlab(CUT_EMERALD_SLAB, CUT_EMERALD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_EMERALD_SLAB, Items.EMERALD_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, CUT_EMERALD_SLAB, CUT_EMERALD, 2);
		Recipes.MakeStairs(CUT_EMERALD_STAIRS, CUT_EMERALD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_EMERALD_STAIRS, Items.EMERALD_BLOCK);
		OfferStonecuttingRecipe(exporter, CUT_EMERALD_STAIRS, CUT_EMERALD);
		Recipes.MakeWall(CUT_EMERALD_WALL, CUT_EMERALD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_EMERALD_WALL, Items.EMERALD_BLOCK);
		OfferStonecuttingRecipe(exporter, CUT_EMERALD_WALL, CUT_EMERALD);
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
		OfferStonecuttingRecipe(exporter, DIAMOND_SLAB, Items.DIAMOND_BLOCK, 2);
		Recipes.MakeStairs(DIAMOND_STAIRS, Items.DIAMOND_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIAMOND_STAIRS, Items.DIAMOND_BLOCK);
		Recipes.MakeWall(DIAMOND_WALL, Items.DIAMOND_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIAMOND_WALL, Items.DIAMOND_BLOCK);
		//Bricks
		Recipes.Make2x2(DIAMOND_BRICKS, Items.DIAMOND_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIAMOND_BRICKS, Items.DIAMOND_BLOCK);
		Recipes.MakeSlab(DIAMOND_BRICK_SLAB, DIAMOND_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIAMOND_BRICK_SLAB, Items.DIAMOND_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, DIAMOND_BRICK_SLAB, DIAMOND_BRICKS, 2);
		Recipes.MakeStairs(DIAMOND_BRICK_STAIRS, DIAMOND_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIAMOND_BRICK_STAIRS, Items.DIAMOND_BLOCK);
		OfferStonecuttingRecipe(exporter, DIAMOND_BRICK_STAIRS, DIAMOND_BRICKS);
		Recipes.MakeWall(DIAMOND_BRICK_WALL, DIAMOND_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, DIAMOND_BRICK_WALL, Items.DIAMOND_BLOCK);
		OfferStonecuttingRecipe(exporter, DIAMOND_BRICK_WALL, DIAMOND_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Quartz">
		Recipes.MakeShapeless(Items.QUARTZ, Items.QUARTZ_BLOCK, 4).offerTo(exporter, ID("quartz_from_quartz_block"));
		Recipes.MakeWall(SMOOTH_QUARTZ_WALL, Items.SMOOTH_QUARTZ).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SMOOTH_QUARTZ_WALL, Items.SMOOTH_QUARTZ);
		Recipes.MakeWall(QUARTZ_WALL, Items.QUARTZ_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, QUARTZ_WALL, Items.QUARTZ_BLOCK);
		//Crystal Block
		Recipes.MakeShapeless(Items.QUARTZ, QUARTZ_CRYSTAL_BLOCK, 9).offerTo(exporter, ID("quartz_from_quartz_crystal_block"));
		Recipes.Make3x3(QUARTZ_CRYSTAL_BLOCK, Items.QUARTZ).offerTo(exporter);
		Recipes.MakeSlab(QUARTZ_CRYSTAL_SLAB, QUARTZ_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, QUARTZ_CRYSTAL_SLAB, QUARTZ_CRYSTAL_BLOCK, 2);
		Recipes.MakeStairs(QUARTZ_CRYSTAL_STAIRS, QUARTZ_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, QUARTZ_CRYSTAL_STAIRS, QUARTZ_CRYSTAL_BLOCK);
		Recipes.MakeWall(QUARTZ_CRYSTAL_WALL, QUARTZ_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, QUARTZ_CRYSTAL_WALL, QUARTZ_CRYSTAL_BLOCK);
		//Bricks
		Recipes.MakeSlab(QUARTZ_BRICK_SLAB, Items.QUARTZ_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, QUARTZ_BRICK_SLAB, Items.QUARTZ_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, QUARTZ_BRICK_SLAB, Items.QUARTZ_BRICKS, 2);
		Recipes.MakeStairs(QUARTZ_BRICK_STAIRS, Items.QUARTZ_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, QUARTZ_BRICK_STAIRS, Items.QUARTZ_BLOCK);
		OfferStonecuttingRecipe(exporter, QUARTZ_BRICK_STAIRS, Items.QUARTZ_BRICKS);
		Recipes.MakeWall(QUARTZ_BRICK_WALL, Items.QUARTZ_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, QUARTZ_BRICK_WALL, Items.QUARTZ_BLOCK);
		OfferStonecuttingRecipe(exporter, QUARTZ_BRICK_WALL, Items.QUARTZ_BRICKS);
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
		//<editor-fold desc="Iron">
		Recipes.MakeShaped(IRON_TORCH, Items.STICK, 4).input('*', ItemTags.COALS).input('I', Items.IRON_NUGGET).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(IRON_SOUL_TORCH, Ingredient.fromTag(ModItemTags.SOUL_TORCHES), 4).input('I', Items.IRON_NUGGET).pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(IRON_ENDER_TORCH, Ingredient.fromTag(ModItemTags.ENDER_TORCHES), 4).input('I', Items.IRON_NUGGET).pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(UNDERWATER_IRON_TORCH, Items.STICK, 4).input('*', Items.GLOW_INK_SAC).input('I', Items.IRON_NUGGET).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShapeless(WHITE_IRON_LANTERN, Items.LANTERN).input(Items.WHITE_DYE).offerTo(exporter, ID("white_iron_lantern_from_lantern"));
		Recipes.MakeShapeless(Items.LANTERN, WHITE_IRON_LANTERN).input(Items.BLACK_DYE).offerTo(exporter, ID("lantern_from_white_iron_lantern"));
		Recipes.MakeShapeless(WHITE_IRON_SOUL_LANTERN, Items.SOUL_LANTERN).input(Items.WHITE_DYE).offerTo(exporter, ID("white_iron_soul_lantern_from_soul_lantern"));
		Recipes.MakeShapeless(Items.SOUL_LANTERN, WHITE_IRON_SOUL_LANTERN).input(Items.BLACK_DYE).offerTo(exporter, ID("soul_lantern_from_white_iron_soul_lantern"));
		Recipes.MakeShapeless(WHITE_IRON_ENDER_LANTERN, ENDER_LANTERN).input(Items.WHITE_DYE).offerTo(exporter, ID("white_iron_ender_lantern_from_ender_lantern"));
		Recipes.MakeShapeless(ENDER_LANTERN, WHITE_IRON_ENDER_LANTERN).input(Items.BLACK_DYE).offerTo(exporter, ID("ender_lantern_from_white_iron_ender_lantern"));
		Recipes.Make2x2(IRON_BUTTON, Items.IRON_NUGGET).offerTo(exporter);
		Recipes.MakeShapeless(WHITE_IRON_CHAIN, Items.CHAIN).input(Items.WHITE_DYE).offerTo(exporter, ID("white_iron_chain_from_chain"));
		Recipes.MakeShapeless(Items.CHAIN, WHITE_IRON_CHAIN).input(Items.BLACK_DYE).offerTo(exporter, ID("chain_from_white_iron_chain"));
		Recipes.MakeStairs(IRON_STAIRS, Items.IRON_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, IRON_STAIRS, Items.IRON_BLOCK);
		Recipes.MakeSlab(IRON_SLAB, Items.IRON_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, IRON_SLAB, Items.IRON_BLOCK, 2);
		Recipes.MakeWall(IRON_WALL, Items.IRON_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, IRON_WALL, Items.IRON_BLOCK);
		//Bricks
		Recipes.Make2x2(IRON_BRICKS, Items.IRON_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, IRON_BRICKS, Items.IRON_BLOCK);
		Recipes.MakeSlab(IRON_BRICK_SLAB, IRON_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, IRON_BRICK_SLAB, Items.IRON_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, IRON_BRICK_SLAB, IRON_BRICKS, 2);
		Recipes.MakeStairs(IRON_BRICK_STAIRS, IRON_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, IRON_BRICK_STAIRS, Items.IRON_BLOCK);
		OfferStonecuttingRecipe(exporter, IRON_BRICK_STAIRS, IRON_BRICKS);
		Recipes.MakeWall(IRON_BRICK_WALL, IRON_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, IRON_BRICK_WALL, Items.IRON_BLOCK);
		OfferStonecuttingRecipe(exporter, IRON_BRICK_WALL, IRON_BRICKS);
		Recipes.Make2x2(CUT_IRON, Items.IRON_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_IRON, Items.IRON_BLOCK, 4);
		Recipes.Make2x2(CUT_IRON_PILLAR, CUT_IRON, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_IRON_PILLAR, CUT_IRON);
		Recipes.MakeSlab(CUT_IRON_SLAB, CUT_IRON).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_IRON_SLAB, CUT_IRON, 2);
		OfferStonecuttingRecipe(exporter, CUT_IRON_SLAB, Items.IRON_BLOCK, 8);
		Recipes.MakeStairs(CUT_IRON_STAIRS, CUT_IRON).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_IRON_STAIRS, CUT_IRON);
		OfferStonecuttingRecipe(exporter, CUT_IRON_STAIRS, Items.IRON_BLOCK, 4);
		Recipes.MakeWall(CUT_IRON_WALL, CUT_IRON).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_IRON_WALL, CUT_IRON);
		OfferStonecuttingRecipe(exporter, CUT_IRON_WALL, Items.IRON_BLOCK, 4);
		//</editor-fold>
		//<editor-fold desc="Gold">
		Recipes.MakeShaped(GOLD_TORCH, Items.STICK, 4).input('*', ItemTags.COALS).input('I', Items.GOLD_NUGGET).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(GOLD_SOUL_TORCH, Ingredient.fromTag(ModItemTags.SOUL_TORCHES), 4).input('I', Items.GOLD_NUGGET).pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(GOLD_ENDER_TORCH, Ingredient.fromTag(ModItemTags.ENDER_TORCHES), 4).input('I', Items.GOLD_NUGGET).pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(UNDERWATER_GOLD_TORCH, Items.STICK, 4).input('*', Items.GLOW_INK_SAC).input('I', Items.GOLD_NUGGET).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeLantern(GOLD_LANTERN, Items.GOLD_NUGGET, ModItemTags.TORCHES).offerTo(exporter);
		Recipes.MakeLantern(GOLD_SOUL_LANTERN, Items.GOLD_NUGGET, ModItemTags.SOUL_TORCHES).offerTo(exporter);
		Recipes.MakeLantern(GOLD_ENDER_LANTERN, Items.GOLD_NUGGET, ModItemTags.ENDER_TORCHES).offerTo(exporter);
		Recipes.MakeShapeless(GOLD_BUTTON, Items.GOLD_NUGGET).offerTo(exporter);
		Recipes.MakeChain(GOLD_CHAIN, Items.GOLD_INGOT, Items.GOLD_NUGGET).offerTo(exporter);
		Recipes.Make3x2(GOLD_BARS, Items.GOLD_INGOT).offerTo(exporter);
		Recipes.MakeStairs(GOLD_STAIRS, Items.GOLD_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GOLD_STAIRS, Items.GOLD_BLOCK);
		Recipes.MakeSlab(GOLD_SLAB, Items.GOLD_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GOLD_SLAB, Items.GOLD_BLOCK, 2);
		Recipes.MakeWall(GOLD_WALL, Items.GOLD_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GOLD_WALL, Items.GOLD_BLOCK);
		//Bricks
		Recipes.Make2x2(GOLD_BRICKS, Items.GOLD_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GOLD_BRICKS, Items.GOLD_BLOCK);
		Recipes.MakeSlab(GOLD_BRICK_SLAB, GOLD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GOLD_BRICK_SLAB, Items.GOLD_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, GOLD_BRICK_SLAB, GOLD_BRICKS, 2);
		Recipes.MakeStairs(GOLD_BRICK_STAIRS, GOLD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GOLD_BRICK_STAIRS, Items.GOLD_BLOCK);
		OfferStonecuttingRecipe(exporter, GOLD_BRICK_STAIRS, GOLD_BRICKS);
		Recipes.MakeWall(GOLD_BRICK_WALL, GOLD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GOLD_BRICK_WALL, Items.GOLD_BLOCK);
		OfferStonecuttingRecipe(exporter, GOLD_BRICK_WALL, GOLD_BRICKS);
		Recipes.Make2x2(CUT_GOLD, Items.GOLD_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_GOLD, Items.GOLD_BLOCK, 4);
		Recipes.Make2x2(CUT_GOLD_PILLAR, CUT_GOLD, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_GOLD_PILLAR, CUT_GOLD);
		Recipes.MakeSlab(CUT_GOLD_SLAB, CUT_GOLD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_GOLD_SLAB, CUT_GOLD, 2);
		OfferStonecuttingRecipe(exporter, CUT_GOLD_SLAB, Items.GOLD_BLOCK, 8);
		Recipes.MakeStairs(CUT_GOLD_STAIRS, CUT_GOLD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_GOLD_STAIRS, CUT_GOLD);
		OfferStonecuttingRecipe(exporter, CUT_GOLD_STAIRS, Items.GOLD_BLOCK, 4);
		Recipes.MakeWall(CUT_GOLD_WALL, CUT_GOLD).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_GOLD_WALL, CUT_GOLD);
		OfferStonecuttingRecipe(exporter, CUT_GOLD_WALL, Items.GOLD_BLOCK, 4);
		//</editor-fold>
		//<editor-fold desc="Copper">
		Recipes.MakeShaped(COPPER_TORCH, Items.STICK, 4).input('*', ItemTags.COALS).input('I', COPPER_NUGGET).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(COPPER_SOUL_TORCH, Ingredient.fromTag(ModItemTags.SOUL_TORCHES), 4).input('I', COPPER_NUGGET).pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(COPPER_ENDER_TORCH, Ingredient.fromTag(ModItemTags.ENDER_TORCHES), 4).input('I', COPPER_NUGGET).pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(UNDERWATER_COPPER_TORCH, Items.STICK, 4).input('*', Items.GLOW_INK_SAC).input('I', COPPER_NUGGET).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShapeless(COPPER_NUGGET, Items.COPPER_INGOT, 9).offerTo(exporter, ID("copper_nugget_from_copper_ingot"));
		Recipes.Make3x3(Items.COPPER_INGOT, COPPER_NUGGET).offerTo(exporter);
		Recipes.MakeLantern(COPPER_LANTERN, COPPER_NUGGET, ModItemTags.TORCHES).offerTo(exporter);
		Recipes.MakeLantern(COPPER_SOUL_LANTERN, COPPER_NUGGET, ModItemTags.SOUL_TORCHES).offerTo(exporter);
		Recipes.MakeLantern(COPPER_ENDER_LANTERN, COPPER_NUGGET, ModItemTags.ENDER_TORCHES).offerTo(exporter);
		Recipes.MakeShapeless(COPPER_BUTTON, COPPER_NUGGET).offerTo(exporter);
		Recipes.MakeChain(COPPER_CHAIN, Items.COPPER_INGOT, COPPER_NUGGET).offerTo(exporter);
		Recipes.Make3x2(COPPER_BARS, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeWall(COPPER_WALL, Items.COPPER_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, COPPER_WALL, Items.COPPER_BLOCK);
		Recipes.MakeWall(EXPOSED_COPPER_WALL, Items.EXPOSED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, EXPOSED_COPPER_WALL, Items.EXPOSED_COPPER);
		Recipes.MakeWall(WEATHERED_COPPER_WALL, Items.WEATHERED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WEATHERED_COPPER_WALL, Items.WEATHERED_COPPER);
		Recipes.MakeWall(OXIDIZED_COPPER_WALL, Items.OXIDIZED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, OXIDIZED_COPPER_WALL, Items.OXIDIZED_COPPER);
		Recipes.MakeWall(WAXED_COPPER_WALL, Items.WAXED_COPPER_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_COPPER_WALL, Items.WAXED_COPPER_BLOCK);
		Recipes.MakeWall(WAXED_EXPOSED_COPPER_WALL, Items.WAXED_EXPOSED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_EXPOSED_COPPER_WALL, Items.WAXED_EXPOSED_COPPER);
		Recipes.MakeWall(WAXED_WEATHERED_COPPER_WALL, Items.WAXED_WEATHERED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_WEATHERED_COPPER_WALL, Items.WAXED_WEATHERED_COPPER);
		Recipes.MakeWall(WAXED_OXIDIZED_COPPER_WALL, Items.WAXED_OXIDIZED_COPPER).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, WAXED_OXIDIZED_COPPER_WALL, Items.WAXED_OXIDIZED_COPPER);
		Recipes.MakePressurePlate(MEDIUM_WEIGHTED_PRESSURE_PLATE, Items.COPPER_INGOT).offerTo(exporter);
		//Tools
		Recipes.MakeAxe(COPPER_AXE, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeKnife(COPPER_KNIFE, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeHoe(COPPER_HOE, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakePickaxe(COPPER_PICKAXE, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeShovel(COPPER_SHOVEL, Items.COPPER_INGOT).offerTo(exporter);
		Recipes.MakeSword(COPPER_SWORD, Items.COPPER_INGOT).offerTo(exporter);
		//Recipes.MakeHelmet(COPPER_HELMET, Items.COPPER_INGOT).offerTo(exporter);
		//Recipes.MakeChestplate(COPPER_CHESTPLATE, Items.COPPER_INGOT).offerTo(exporter);
		//Recipes.MakeLeggings(COPPER_LEGGINGS, Items.COPPER_INGOT).offerTo(exporter);
		//Recipes.MakeBoots(COPPER_BOOTS, Items.COPPER_INGOT).offerTo(exporter);
		//Recipes.MakeHorseArmor(COPPER_HORSE_ARMOR, Items.COPPER_INGOT).offerTo(exporter);
		//TODO: Waxing recipes
		//</editor-fold>
		//<editor-fold desc="Raw Metal">
		Recipes.MakeSlab(RAW_COPPER_SLAB, Items.RAW_COPPER_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, RAW_COPPER_SLAB, Items.COPPER_BLOCK, 2);
		Recipes.Make1x2(Items.RAW_COPPER_BLOCK, RAW_COPPER_SLAB).offerTo(exporter, ID("raw_copper_block_from_raw_copper_slabs"));
		Recipes.MakeSlab(RAW_GOLD_SLAB, Items.RAW_GOLD_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, RAW_GOLD_SLAB, Items.GOLD_BLOCK, 2);
		Recipes.Make1x2(Items.RAW_GOLD_BLOCK, RAW_GOLD_SLAB).offerTo(exporter, ID("raw_gold_block_from_raw_gold_slabs"));
		Recipes.MakeSlab(RAW_IRON_SLAB, Items.RAW_IRON_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, RAW_IRON_SLAB, Items.IRON_BLOCK, 2);
		Recipes.Make1x2(Items.RAW_IRON_BLOCK, RAW_IRON_SLAB).offerTo(exporter, ID("raw_iron_block_from_raw_iron_slabs"));
		//</editor-fold>
		//<editor-fold desc="Netherite">
		Recipes.MakeShaped(NETHERITE_TORCH, Items.STICK, 4).input('*', ItemTags.COALS).input('I', NETHERITE_NUGGET).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(NETHERITE_SOUL_TORCH, Ingredient.fromTag(ModItemTags.SOUL_TORCHES), 4).input('I', NETHERITE_NUGGET).pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(NETHERITE_ENDER_TORCH, Ingredient.fromTag(ModItemTags.ENDER_TORCHES), 4).input('I', NETHERITE_NUGGET).pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(UNDERWATER_NETHERITE_TORCH, Items.STICK, 4).input('*', Items.GLOW_INK_SAC).input('I', NETHERITE_NUGGET).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeLantern(NETHERITE_LANTERN, NETHERITE_NUGGET, ModItemTags.TORCHES).offerTo(exporter);
		Recipes.MakeLantern(NETHERITE_SOUL_LANTERN, NETHERITE_NUGGET, ModItemTags.SOUL_TORCHES).offerTo(exporter);
		Recipes.MakeLantern(NETHERITE_ENDER_LANTERN, NETHERITE_NUGGET, ModItemTags.ENDER_TORCHES).offerTo(exporter);
		Recipes.MakeShapeless(NETHERITE_NUGGET, Items.NETHERITE_INGOT, 9).offerTo(exporter, ID("netherite_nugget_from_netherite_ingot"));
		Recipes.Make3x3(Items.NETHERITE_INGOT, NETHERITE_NUGGET).offerTo(exporter);
		Recipes.Make2x2(NETHERITE_BUTTON, NETHERITE_NUGGET).offerTo(exporter);
		Recipes.MakeShaped(NETHERITE_CHAIN, Items.NETHERITE_INGOT).input('N', NETHERITE_NUGGET).pattern("N").pattern("#").pattern("N").offerTo(exporter);
		Recipes.Make3x2(NETHERITE_BARS, Items.NETHERITE_INGOT).offerTo(exporter);
		Recipes.MakeStairs(NETHERITE_STAIRS, Items.NETHERITE_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, NETHERITE_STAIRS, Items.NETHERITE_BLOCK);
		Recipes.MakeSlab(NETHERITE_SLAB, Items.NETHERITE_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, NETHERITE_SLAB, Items.NETHERITE_BLOCK, 2);
		Recipes.MakeWall(NETHERITE_WALL, Items.NETHERITE_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, NETHERITE_WALL, Items.NETHERITE_BLOCK);
		//Bricks
		Recipes.Make2x2(NETHERITE_BRICKS, Items.NETHERITE_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, NETHERITE_BRICKS, Items.NETHERITE_BLOCK);
		Recipes.MakeSlab(NETHERITE_BRICK_SLAB, NETHERITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, NETHERITE_BRICK_SLAB, Items.NETHERITE_BLOCK, 2);
		OfferStonecuttingRecipe(exporter, NETHERITE_BRICK_SLAB, NETHERITE_BRICKS, 2);
		Recipes.MakeStairs(NETHERITE_BRICK_STAIRS, NETHERITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, NETHERITE_BRICK_STAIRS, Items.NETHERITE_BLOCK);
		OfferStonecuttingRecipe(exporter, NETHERITE_BRICK_STAIRS, NETHERITE_BRICKS);
		Recipes.MakeWall(NETHERITE_BRICK_WALL, NETHERITE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, NETHERITE_BRICK_WALL, Items.NETHERITE_BLOCK);
		OfferStonecuttingRecipe(exporter, NETHERITE_BRICK_WALL, NETHERITE_BRICKS);
		Recipes.Make2x2(CUT_NETHERITE, Items.NETHERITE_BLOCK, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE, Items.NETHERITE_BLOCK, 4);
		Recipes.Make2x2(CUT_NETHERITE_PILLAR, CUT_NETHERITE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE_PILLAR, CUT_NETHERITE);
		Recipes.MakeSlab(CUT_NETHERITE_SLAB, CUT_NETHERITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE_SLAB, CUT_NETHERITE, 2);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE_SLAB, Items.NETHERITE_BLOCK, 8);
		Recipes.MakeStairs(CUT_NETHERITE_STAIRS, CUT_NETHERITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE_STAIRS, CUT_NETHERITE);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE_STAIRS, Items.NETHERITE_BLOCK, 4);
		Recipes.MakeWall(CUT_NETHERITE_WALL, CUT_NETHERITE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE_WALL, CUT_NETHERITE);
		OfferStonecuttingRecipe(exporter, CUT_NETHERITE_WALL, Items.NETHERITE_BLOCK, 4);
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
		OfferSmithingRecipe(exporter, Items.GILDED_BLACKSTONE, Items.BLACKSTONE, Items.GOLD_INGOT);
		Recipes.MakeSlab(GILDED_BLACKSTONE_SLAB, Items.GILDED_BLACKSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GILDED_BLACKSTONE_SLAB, Items.GILDED_BLACKSTONE, 2);
		OfferSmithingRecipe(exporter, GILDED_BLACKSTONE_SLAB, Items.BLACKSTONE_SLAB, Items.GOLD_INGOT);
		Recipes.MakeStairs(GILDED_BLACKSTONE_STAIRS, Items.GILDED_BLACKSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GILDED_BLACKSTONE_STAIRS, Items.GILDED_BLACKSTONE);
		OfferSmithingRecipe(exporter, GILDED_BLACKSTONE_STAIRS, Items.BLACKSTONE_STAIRS, Items.GOLD_INGOT);
		Recipes.MakeWall(GILDED_BLACKSTONE_WALL, Items.GILDED_BLACKSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, GILDED_BLACKSTONE_WALL, Items.GILDED_BLACKSTONE);
		OfferSmithingRecipe(exporter, GILDED_BLACKSTONE_WALL, Items.BLACKSTONE_WALL, Items.GOLD_INGOT);
		//Polished
		Recipes.Make2x2(POLISHED_GILDED_BLACKSTONE, Items.GILDED_BLACKSTONE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE, Items.GILDED_BLACKSTONE);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE, Items.POLISHED_BLACKSTONE, Items.GOLD_INGOT);
		Recipes.MakeSlab(POLISHED_GILDED_BLACKSTONE_SLAB, POLISHED_GILDED_BLACKSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_SLAB, POLISHED_GILDED_BLACKSTONE, 2);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_SLAB, Items.POLISHED_BLACKSTONE_SLAB, Items.GOLD_INGOT);
		Recipes.MakeStairs(POLISHED_GILDED_BLACKSTONE_STAIRS, POLISHED_GILDED_BLACKSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_STAIRS, POLISHED_GILDED_BLACKSTONE);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_STAIRS, Items.POLISHED_BLACKSTONE_STAIRS, Items.GOLD_INGOT);
		Recipes.MakeWall(POLISHED_GILDED_BLACKSTONE_WALL, POLISHED_GILDED_BLACKSTONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_WALL, POLISHED_GILDED_BLACKSTONE);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_WALL, Items.POLISHED_BLACKSTONE_WALL, Items.GOLD_INGOT);
		//Bricks
		Recipes.Make2x2(POLISHED_GILDED_BLACKSTONE_BRICKS, POLISHED_GILDED_BLACKSTONE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICKS, POLISHED_GILDED_BLACKSTONE);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICKS, Items.POLISHED_BLACKSTONE_BRICKS, Items.GOLD_INGOT);
		Recipes.MakeSlab(POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, POLISHED_GILDED_BLACKSTONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, POLISHED_GILDED_BLACKSTONE, 2);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, POLISHED_GILDED_BLACKSTONE_BRICKS, 2);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, Items.POLISHED_BLACKSTONE_BRICK_SLAB, Items.GOLD_INGOT);
		Recipes.MakeStairs(POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, POLISHED_GILDED_BLACKSTONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, POLISHED_GILDED_BLACKSTONE);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, POLISHED_GILDED_BLACKSTONE_BRICKS);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, Items.POLISHED_BLACKSTONE_BRICK_STAIRS, Items.GOLD_INGOT);
		Recipes.MakeWall(POLISHED_GILDED_BLACKSTONE_BRICK_WALL, POLISHED_GILDED_BLACKSTONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_WALL, POLISHED_GILDED_BLACKSTONE);
		OfferStonecuttingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_WALL, POLISHED_GILDED_BLACKSTONE_BRICKS);
		OfferSmithingRecipe(exporter, POLISHED_GILDED_BLACKSTONE_BRICK_WALL, Items.POLISHED_BLACKSTONE_BRICK_WALL, Items.GOLD_INGOT);
		//</editor-fold>
		//<editor-fold desc="Misc Stuff">
		Recipes.MakeSlab(COAL_SLAB, Items.COAL_BLOCK).offerTo(exporter);
		Recipes.Make1x2(Items.COAL_BLOCK, COAL_SLAB).offerTo(exporter, ID("coal_block_from_coal_slabs"));
		Recipes.MakeShapeless(Items.CHARCOAL, CHARCOAL_BLOCK, 9).offerTo(exporter, ID("charcoal_from_charcoal_block"));
		Recipes.Make3x3(CHARCOAL_BLOCK, Items.CHARCOAL).offerTo(exporter);
		Recipes.MakeSlab(CHARCOAL_SLAB, CHARCOAL_BLOCK).offerTo(exporter);
		Recipes.Make1x2(CHARCOAL_BLOCK, CHARCOAL_SLAB).offerTo(exporter, ID("charcoal_block_from_charcoal_slabs"));
		Recipes.MakeSlab(COARSE_DIRT_SLAB, Items.COARSE_DIRT).offerTo(exporter);
		Recipes.MakeShapeless(Items.COCOA_BEANS, COCOA_BLOCK, 9).offerTo(exporter, ID("cocoa_beans_from_cocoa_block"));
		Recipes.Make3x3(COCOA_BLOCK, Items.COCOA_BEANS).offerTo(exporter);
		Recipes.MakeShapeless(Items.FLINT, FLINT_BLOCK, 9).offerTo(exporter, ID("flint_from_flint_block"));
		Recipes.Make3x3(FLINT_BLOCK, Items.FLINT).offerTo(exporter);
		Recipes.MakeSlab(FLINT_SLAB, FLINT_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, FLINT_SLAB, FLINT_BLOCK, 2);
		Recipes.Make1x2(FLINT_BLOCK, FLINT_SLAB).offerTo(exporter, ID("flint_block_from_flint_slabs"));
		Recipes.Make2x2(FLINT_BRICKS, FLINT_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, FLINT_BRICKS, FLINT_BLOCK);
		Recipes.MakeStairs(FLINT_BRICK_STAIRS, FLINT_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, FLINT_BRICK_STAIRS, FLINT_BLOCK);
		Recipes.MakeSlab(FLINT_BRICK_SLAB, FLINT_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, FLINT_BRICK_SLAB, FLINT_BLOCK, 2);
		Recipes.MakeWall(FLINT_BRICK_WALL, FLINT_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, FLINT_BRICK_WALL, FLINT_BLOCK);
		Recipes.MakeShapeless(Items.HONEYCOMB, WAX_BLOCK, 9).offerTo(exporter, ID("honeycomb_from_wax_block"));
		Recipes.Make3x3(WAX_BLOCK, Items.HONEYCOMB).offerTo(exporter);
		OfferSmeltingRecipe(exporter, GLAZED_TERRACOTTA, Items.TERRACOTTA, 200, 0.1);
		ShapelessRecipeJsonBuilder.create(SEED_BLOCK).input(ModItemTags.SEEDS).criterion("automatic", RecipeProvider.conditionsFromItem(Items.STICK)).offerTo(exporter);
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
		for (DyeColor color : DyeColor.values()) Recipes.MakeSlab(WOOL_SLABS.get(color), ColorUtil.GetWoolItem(color));
		Recipes.MakeSlab(RAINBOW_WOOL_SLAB, RAINBOW_WOOL).offerTo(exporter);
		Recipes.MakeCarpet(RAINBOW_CARPET, RAINBOW_WOOL).offerTo(exporter);
		//Recipes.MakeBed(RAINBOW_BED, Ingredient.ofItems(RAINBOW_WOOL, RAINBOW_FLEECE)).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Glow Lichen">
		Recipes.Make3x3(GLOW_LICHEN_BLOCK, Items.GLOW_LICHEN).offerTo(exporter);
		Recipes.MakeShapeless(Items.GLOW_LICHEN, GLOW_LICHEN_BLOCK, 9).offerTo(exporter, ID("glow_lichen_from_glow_lichen_block"));
		Recipes.MakeSlab(GLOW_LICHEN_SLAB, GLOW_LICHEN_BLOCK).offerTo(exporter);
		Recipes.MakeCarpet(GLOW_LICHEN_CARPET, GLOW_LICHEN_BLOCK).offerTo(exporter);
		//Recipes.MakeBed(GLOW_LICHEN_BED, GLOW_LICHEN_BLOCK).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Moss">
		Recipes.MakeSlab(MOSS_SLAB, Items.MOSS_BLOCK).offerTo(exporter);
		//Recipes.MakeBed(MOSS_BED, Items.MOSS_BLOCK).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Extended Goat">
		OfferSmeltingRecipe(exporter, COOKED_CHEVON, CHEVON, 200, 0.35);
		Recipes.MakeSmoking(COOKED_CHEVON, CHEVON, 100, 0.35).offerTo(exporter, _from_smoking(COOKED_CHEVON));
		Recipes.MakeCampfireRecipe(COOKED_CHEVON, CHEVON, 600, 0.35).offerTo(exporter, _from_campfire(COOKED_CHEVON));
		for (DyeColor color : DyeColor.values()) Recipes.Make2x2(FLEECE.get(color), ColorUtil.GetWoolItem(color)).offerTo(exporter);
		for (DyeColor color : DyeColor.values()) Recipes.MakeSlab(FLEECE_SLABS.get(color), FLEECE.get(color)).offerTo(exporter);
		for (DyeColor color : DyeColor.values()) Recipes.MakeCarpet(FLEECE_CARPETS.get(color), FLEECE.get(color)).offerTo(exporter);
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
		Recipes.MakeCampfireRecipe(CHARRED_LOG, Ingredient.fromTag(ModItemTags.CHARRABLE_LOGS), 600, 0.35f).offerTo(exporter, RecipeName(CHARRED_LOG, "_from_charrable_log"));
		Recipes.MakeCampfireRecipe(STRIPPED_CHARRED_LOG, Ingredient.fromTag(ModItemTags.CHARRABLE_STRIPPED_LOGS), 600, 0.35f).offerTo(exporter, RecipeName(STRIPPED_CHARRED_LOG, "_from_charrable_stripped_log"));
		Recipes.Make2x2(CHARRED_WOOD, CHARRED_LOG, 3).offerTo(exporter);
		Recipes.MakeCampfireRecipe(CHARRED_WOOD, Ingredient.fromTag(ModItemTags.CHARRABLE_WOODS), 600, 0.35f).offerTo(exporter, RecipeName(CHARRED_WOOD, "_from_charrable_wood"));
		Recipes.Make2x2(STRIPPED_CHARRED_WOOD, STRIPPED_CHARRED_LOG, 3).offerTo(exporter);
		Recipes.MakeCampfireRecipe(STRIPPED_CHARRED_WOOD, Ingredient.fromTag(ModItemTags.CHARRABLE_STRIPPED_WOODS), 600, 0.35f).offerTo(exporter, RecipeName(STRIPPED_CHARRED_WOOD, "_from_charrable_stripped_wood"));
		Recipes.MakeShapeless(CHARRED_PLANKS, Ingredient.fromTag(ModItemTags.CHARRED_LOGS), 4).offerTo(exporter);
		Recipes.MakeSlab(CHARRED_SLAB, CHARRED_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CHARRED_SLAB, CHARRED_PLANKS, 2);
		Recipes.MakeStairs(CHARRED_STAIRS, CHARRED_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CHARRED_STAIRS, CHARRED_PLANKS);
		Recipes.MakeWoodenFence(CHARRED_FENCE, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenFenceGate(CHARRED_FENCE_GATE, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeDoor(CHARRED_DOOR, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenTrapdoor(CHARRED_TRAPDOOR, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakePressurePlate(CHARRED_PRESSURE_PLATE, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(CHARRED_BUTTON, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeSign(CHARRED_SIGN, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeHangingSign(CHARRED_SIGN.getHanging(), STRIPPED_CHARRED_LOG).offerTo(exporter);
		Recipes.MakeBoat(CHARRED_BOAT, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(CHARRED_BOAT.getChestBoat(), CHARRED_BOAT).input(Items.CHEST).offerTo(exporter);
		Recipes.MakeBookshelf(CHARRED_BOOKSHELF, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(CHARRED_LADDER, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(CHARRED_WOODCUTTER, CHARRED_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(CHARRED_BARREL, CHARRED_PLANKS, CHARRED_SLAB).offerTo(exporter);
		Recipes.MakeLectern(CHARRED_LECTERN, CHARRED_SLAB).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Music Discs">
		Recipes.Make3x3(MUSIC_DISC_5, DISC_FRAGMENT_5).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Mud">
		Recipes.MakeShapeless(PACKED_MUD, MUD).input(Items.WHEAT).offerTo(exporter);
		Recipes.Make2x2(MUD_BRICKS, PACKED_MUD, 4).offerTo(exporter);
		Recipes.MakeSlab(MUD_BRICK_SLAB, MUD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, MUD_BRICK_SLAB, MUD_BRICKS, 2);
		Recipes.MakeStairs(MUD_BRICK_STAIRS, MUD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, MUD_BRICK_STAIRS, MUD_BRICKS);
		Recipes.MakeWall(MUD_BRICK_WALL, MUD_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, MUD_BRICK_WALL, MUD_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Mangrove">
		Recipes.Make2x2(MANGROVE_WOOD, MANGROVE_LOG, 3).offerTo(exporter);
		Recipes.Make2x2(STRIPPED_MANGROVE_WOOD, STRIPPED_MANGROVE_LOG, 3).offerTo(exporter);
		Recipes.MakeShapeless(MANGROVE_PLANKS, Ingredient.fromTag(ModItemTags.MANGROVE_LOGS), 4).offerTo(exporter);
		Recipes.MakeSlab(MANGROVE_SLAB, MANGROVE_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, MANGROVE_SLAB, MANGROVE_PLANKS, 2);
		Recipes.MakeStairs(MANGROVE_STAIRS, MANGROVE_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, MANGROVE_STAIRS, MANGROVE_PLANKS);
		Recipes.MakeWoodenFence(MANGROVE_FENCE, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenFenceGate(MANGROVE_FENCE_GATE, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeDoor(MANGROVE_DOOR, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenTrapdoor(MANGROVE_TRAPDOOR, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakePressurePlate(MANGROVE_PRESSURE_PLATE, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(MANGROVE_BUTTON, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeSign(MANGROVE_SIGN.getHanging(), MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeHangingSign(MANGROVE_SIGN, STRIPPED_MANGROVE_LOG).offerTo(exporter);
		Recipes.MakeBoat(MANGROVE_BOAT, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(MANGROVE_BOAT.getChestBoat(), MANGROVE_BOAT).input(Items.CHEST).offerTo(exporter);
		Recipes.MakeShapeless(MUDDY_MANGROVE_ROOTS, MUD).input(MANGROVE_ROOTS).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Extended Mangrove">
		Recipes.MakeBookshelf(MANGROVE_BOOKSHELF, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(MANGROVE_LADDER, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(MANGROVE_WOODCUTTER, MANGROVE_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(MANGROVE_BARREL, MANGROVE_PLANKS, MANGROVE_SLAB).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Cherry">
		Recipes.Make2x2(CHERRY_WOOD, CHERRY_LOG, 3).offerTo(exporter);
		Recipes.Make2x2(STRIPPED_CHERRY_WOOD, STRIPPED_CHERRY_LOG, 3).offerTo(exporter);
		Recipes.MakeShapeless(CHERRY_PLANKS, Ingredient.fromTag(ModItemTags.CHERRY_LOGS), 4).offerTo(exporter);
		Recipes.MakeSlab(CHERRY_SLAB, CHERRY_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CHERRY_SLAB, CHERRY_PLANKS, 2);
		Recipes.MakeStairs(CHERRY_STAIRS, CHERRY_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, CHERRY_STAIRS, CHERRY_PLANKS);
		Recipes.MakeWoodenFence(CHERRY_FENCE, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenFenceGate(CHERRY_FENCE_GATE, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeDoor(CHERRY_DOOR, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenTrapdoor(CHERRY_TRAPDOOR, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakePressurePlate(CHERRY_PRESSURE_PLATE, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(CHERRY_BUTTON, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeSign(CHERRY_SIGN.getHanging(), CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeHangingSign(CHERRY_SIGN, STRIPPED_CHERRY_LOG).offerTo(exporter);
		Recipes.MakeBoat(CHERRY_BOAT, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(CHERRY_BOAT.getChestBoat(), CHERRY_BOAT).input(Items.CHEST).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Extended Cherry">
		Recipes.MakeBookshelf(CHERRY_BOOKSHELF, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakePlanksLadder(CHERRY_LADDER, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeWoodcutter(CHERRY_WOODCUTTER, CHERRY_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(CHERRY_BARREL, CHERRY_PLANKS, CHERRY_SLAB).offerTo(exporter);
		Recipes.MakeLectern(CHERRY_LECTERN, CHERRY_SLAB).offerTo(exporter);
		//</editor-fold>
		Recipes.MakeShapeless(Items.PINK_DYE, PINK_PETALS, 1).offerTo(exporter, ID("pink_dye_from_pink_petals"));
		Recipes.MakeShapeless(Items.ORANGE_DYE, TORCHFLOWER, 1).offerTo(exporter, ID("orange_dye_from_torchflower"));
		//<editor-fold desc="Bamboo Wood">
		Recipes.Make3x3(BAMBOO_BLOCK, Items.BAMBOO).offerTo(exporter);
		Recipes.MakeShapeless(BAMBOO_PLANKS, Ingredient.ofItems(BAMBOO_BLOCK, STRIPPED_BAMBOO_BLOCK)).offerTo(exporter, ID("bamboo_planks_from_bamboo_block"));
		Recipes.Make2x2(BAMBOO_PLANKS, Items.BAMBOO).offerTo(exporter);
		Recipes.MakeSlab(BAMBOO_SLAB, BAMBOO_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, BAMBOO_SLAB, BAMBOO_PLANKS, 2);
		Recipes.MakeStairs(BAMBOO_STAIRS, BAMBOO_PLANKS).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, BAMBOO_STAIRS, BAMBOO_PLANKS);
		Recipes.MakeWoodenFence(BAMBOO_FENCE, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenFenceGate(BAMBOO_FENCE_GATE, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeDoor(BAMBOO_DOOR, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeWoodenTrapdoor(BAMBOO_TRAPDOOR, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakePressurePlate(BAMBOO_PRESSURE_PLATE, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(BAMBOO_BUTTON, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeSign(BAMBOO_SIGN.getHanging(), BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeHangingSign(BAMBOO_SIGN, STRIPPED_BAMBOO_BLOCK).offerTo(exporter);
		Recipes.MakeBoat(BAMBOO_RAFT, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeShapeless(BAMBOO_RAFT.getChestBoat(), BAMBOO_RAFT).input(Items.CHEST).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Bamboo Mosaic">
		Recipes.Make2x1(BAMBOO_MOSAIC, BAMBOO_SLAB, 1).offerTo(exporter);
		Recipes.MakeSlab(BAMBOO_MOSAIC_SLAB, BAMBOO_MOSAIC).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, BAMBOO_MOSAIC_SLAB, BAMBOO_MOSAIC, 2);
		Recipes.MakeStairs(BAMBOO_MOSAIC_STAIRS, BAMBOO_MOSAIC).offerTo(exporter);
		OfferWoodcuttingRecipe(exporter, BAMBOO_MOSAIC_STAIRS, BAMBOO_MOSAIC);
		//</editor-fold>
		//<editor-fold desc="Extended Bamboo">
		Recipes.MakeShaped(BAMBOO_TORCH, Items.BAMBOO, 4).input('*', ItemTags.COALS).pattern("*").pattern("#").offerTo(exporter);
		Recipes.MakeShaped(BAMBOO_SOUL_TORCH, Items.BAMBOO, 4).input('*', ItemTags.COALS).input('I', ItemTags.SOUL_FIRE_BASE_BLOCKS).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(BAMBOO_ENDER_TORCH, Items.BAMBOO, 4).input('*', ItemTags.COALS).input('I', Items.POPPED_CHORUS_FRUIT).pattern("*").pattern("#").pattern("I").offerTo(exporter);
		Recipes.MakeShaped(UNDERWATER_BAMBOO_TORCH, Items.BAMBOO, 4).input('*', Items.GLOW_INK_SAC).pattern("*").pattern("#").offerTo(exporter);
		Recipes.MakeBookshelf(BAMBOO_BOOKSHELF, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeLadder(BAMBOO_LADDER, Items.BAMBOO).offerTo(exporter);
		Recipes.MakeWoodcutter(BAMBOO_WOODCUTTER, BAMBOO_PLANKS).offerTo(exporter);
		Recipes.MakeBarrel(BAMBOO_BARREL, BAMBOO_PLANKS, BAMBOO_SLAB).offerTo(exporter);
		Recipes.MakeLectern(BAMBOO_LECTERN, BAMBOO_SLAB).offerTo(exporter);
		//</editor-fold>
		//<editor-fold desc="Backport 1.19">
		Recipes.Make3x1(RECOVERY_COMPASS, ECHO_SHARD).input('C', Items.COMPASS).pattern("#C#").pattern("###").offerTo(exporter);
		Recipes.MakeShapeless(RECOVERY_COMPASS, RECOVERY_COMPASS).offerTo(exporter, ID("recovery_compass_clear_nbt"));
		//</editor-fold>
		//<editor-fold desc="Extended Echo">
		Recipes.MakeShapeless(ECHO_SHARD, ECHO_BLOCK, 4).offerTo(exporter, ID("echo_shard_from_echo_block"));
		Recipes.MakeSlab(ECHO_SLAB, ECHO_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_SLAB, ECHO_BLOCK, 2);
		Recipes.MakeStairs(ECHO_STAIRS, ECHO_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_STAIRS, ECHO_BLOCK);
		Recipes.MakeWall(ECHO_WALL, ECHO_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_WALL, ECHO_BLOCK);
		//Crystal Block
		Recipes.MakeShapeless(ECHO_SHARD, ECHO_CRYSTAL_BLOCK, 9).offerTo(exporter, ID("echo_shard_from_echo_crystal_block"));
		Recipes.Make3x3(ECHO_CRYSTAL_BLOCK, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeSlab(ECHO_CRYSTAL_SLAB, ECHO_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_CRYSTAL_SLAB, ECHO_CRYSTAL_BLOCK, 2);
		Recipes.MakeStairs(ECHO_CRYSTAL_STAIRS, ECHO_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_CRYSTAL_STAIRS, ECHO_CRYSTAL_BLOCK);
		Recipes.MakeWall(ECHO_CRYSTAL_WALL, ECHO_CRYSTAL_BLOCK).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, ECHO_CRYSTAL_WALL, ECHO_CRYSTAL_BLOCK);
		//Armor & Tools
		Recipes.MakeAxe(ECHO_AXE, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeKnife(ECHO_KNIFE, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeHoe(ECHO_HOE, ECHO_SHARD).offerTo(exporter);
		Recipes.MakePickaxe(ECHO_PICKAXE, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeShovel(ECHO_SHOVEL, ECHO_SHARD).offerTo(exporter);
		Recipes.MakeSword(ECHO_SWORD, ECHO_SHARD).offerTo(exporter);
		//</editor-fold>
		Recipes.MakeShaped(CHUM, Items.ROTTEN_FLESH, 3).input('B', Items.BONE_MEAL).pattern("###").pattern("#B#").pattern("###").offerTo(exporter);
		//<editor-fold desc="Sculk Stone">
		Recipes.MakeSlab(SCULK_STONE_SLAB, SCULK_STONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_SLAB, SCULK_STONE, 2);
		Recipes.MakeStairs(SCULK_STONE_STAIRS, SCULK_STONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_STAIRS, SCULK_STONE);
		Recipes.MakeWall(SCULK_STONE_WALL, SCULK_STONE).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_WALL, SCULK_STONE);
		//Bricks
		Recipes.Make2x2(SCULK_STONE_BRICKS, SCULK_STONE, 4).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_BRICKS, SCULK_STONE);
		Recipes.MakeSlab(SCULK_STONE_BRICK_SLAB, SCULK_STONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_BRICK_SLAB, SCULK_STONE, 2);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_BRICK_SLAB, SCULK_STONE_BRICKS, 2);
		Recipes.MakeStairs(SCULK_STONE_BRICK_STAIRS, SCULK_STONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_BRICK_STAIRS, SCULK_STONE);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_BRICK_STAIRS, SCULK_STONE_BRICKS);
		Recipes.MakeWall(SCULK_STONE_BRICK_WALL, SCULK_STONE_BRICKS).offerTo(exporter);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_BRICK_WALL, SCULK_STONE);
		OfferStonecuttingRecipe(exporter, SCULK_STONE_BRICK_WALL, SCULK_STONE_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Flowers">
		Recipes.MakeShapeless(Items.MAGENTA_DYE, AMARANTH, 2).offerTo(exporter, ID("amaranth"));
		Recipes.MakeShapeless(Items.LIGHT_BLUE_DYE, BLUE_ROSE_BUSH, 2).offerTo(exporter, ID("blue_rose_bush"));
		Recipes.MakeShapeless(Items.MAGENTA_DYE, TALL_ALLIUM, 2).offerTo(exporter, ID("tall_allium"));
		Recipes.MakeShapeless(Items.PINK_DYE, TALL_PINK_ALLIUM, 2).offerTo(exporter, ID("tall_pink_allium"));
		Recipes.MakeShapeless(Items.YELLOW_DYE, BUTTERCUP).offerTo(exporter, ID("buttercup"));
		Recipes.MakeShapeless(Items.PINK_DYE, PINK_DAISY).offerTo(exporter, ID("pink_daisy"));
		Recipes.MakeShapeless(Items.RED_DYE, ROSE).offerTo(exporter, ID("rose"));
		Recipes.MakeShapeless(Items.LIGHT_BLUE_DYE, BLUE_ROSE).offerTo(exporter, ID("blue_rose"));
		Recipes.MakeShapeless(Items.MAGENTA_DYE, MAGENTA_TULIP).offerTo(exporter, ID("magenta_tulip"));
		Recipes.MakeShapeless(Items.ORANGE_DYE, MARIGOLD).offerTo(exporter, ID("marigold"));
		Recipes.MakeShapeless(Items.MAGENTA_DYE, INDIGO_ORCHID).offerTo(exporter, ID("indigo_orchid"));
		Recipes.MakeShapeless(Items.MAGENTA_DYE, MAGENTA_ORCHID).offerTo(exporter, ID("magenta_orchid"));
		Recipes.MakeShapeless(Items.ORANGE_DYE, ORANGE_ORCHID).offerTo(exporter, ID("orange_orchid"));
		Recipes.MakeShapeless(Items.PURPLE_DYE, PURPLE_ORCHID).offerTo(exporter, ID("purple_orchid"));
		Recipes.MakeShapeless(Items.WHITE_DYE, WHITE_ORCHID).offerTo(exporter, ID("white_orchid"));
		Recipes.MakeShapeless(Items.YELLOW_DYE, YELLOW_ORCHID).offerTo(exporter, ID("yellow_orchid"));
		Recipes.MakeShapeless(Items.PINK_DYE, PINK_ALLIUM).offerTo(exporter, ID("pink_allium"));
		Recipes.MakeShapeless(Items.MAGENTA_DYE, HYDRANGEA).offerTo(exporter, ID("hydrangea"));
		Recipes.MakeShapeless(Items.PURPLE_DYE, LAVENDER).offerTo(exporter, ID("lavender"));
		Recipes.MakeShapeless(Items.PINK_DYE, PAEONIA).offerTo(exporter, ID("paeonia"));
		Recipes.MakeShapeless(Items.PINK_DYE, ASTER).offerTo(exporter, ID("aster"));
		//</editor-fold>
	}
}
