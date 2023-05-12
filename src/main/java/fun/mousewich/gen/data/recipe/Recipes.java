package fun.mousewich.gen.data.recipe;

import fun.mousewich.ModBase;
import fun.mousewich.container.ArrowContainer;
import fun.mousewich.gen.data.tag.ModItemTags;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;

import java.util.Arrays;

import static fun.mousewich.ModBase.WOODCUTTING_RECIPE_SERIALIZER;

public class Recipes {
	private static CraftingRecipeJsonBuilder make(CraftingRecipeJsonBuilder recipe) {
		return recipe.criterion("automatic", RecipeProvider.conditionsFromItem(Items.STICK));
	}

	private static CookingRecipeJsonBuilder makeCooking(ItemConvertible output, Ingredient ingredient, int cookingTime, double experience, CookingRecipeSerializer<?> serializer) {
		return CookingRecipeJsonBuilder.create(ingredient, output, (float)experience, cookingTime, serializer)
				.criterion("automatic", RecipeProvider.conditionsFromItem(Items.STICK));
	}
	private static CookingRecipeJsonBuilder makeCooking(ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience, CookingRecipeSerializer<?> serializer) {
		return CookingRecipeJsonBuilder.create(Ingredient.ofItems(ingredient), output, (float)experience, cookingTime, serializer)
				.criterion(RecipeProvider.hasItem(ingredient), RecipeProvider.conditionsFromItem(ingredient));
	}
	public static CookingRecipeJsonBuilder MakeSmelting(ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.SMELTING);
	}
	public static CookingRecipeJsonBuilder MakeSmelting(ItemConvertible output, Ingredient ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.SMELTING);
	}
	public static CookingRecipeJsonBuilder MakeBlasting(ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.BLASTING);
	}
	public static CookingRecipeJsonBuilder MakeBlasting(ItemConvertible output, Ingredient ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.BLASTING);
	}
	public static CookingRecipeJsonBuilder MakeSmoking(ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.SMOKING);
	}
	public static CookingRecipeJsonBuilder MakeSmoking(ItemConvertible output, Ingredient ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.SMOKING);
	}
	public static CookingRecipeJsonBuilder MakeCampfireRecipe(ItemConvertible output, ItemConvertible ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.CAMPFIRE_COOKING);
	}
	public static CookingRecipeJsonBuilder MakeCampfireRecipe(ItemConvertible output, TagKey<Item> ingredient, int cookingTime, double experience) {
		return MakeCampfireRecipe(output, Ingredient.fromTag(ingredient), cookingTime, experience);
	}
	public static CookingRecipeJsonBuilder MakeCampfireRecipe(ItemConvertible output, Ingredient ingredient, int cookingTime, double experience) {
		return makeCooking(output, ingredient, cookingTime, experience, RecipeSerializer.CAMPFIRE_COOKING);
	}

	public static SingleItemRecipeJsonBuilder MakeStonecutting(ItemConvertible output, ItemConvertible ingredient) { return MakeStonecutting(output, ingredient, 1); }
	public static SingleItemRecipeJsonBuilder MakeStonecutting(ItemConvertible output, ItemConvertible ingredient, int count) {
		return SingleItemRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ingredient), output, count)
				.criterion(RecipeProvider.hasItem(ingredient), RecipeProvider.conditionsFromItem(ingredient));
	}
	public static SingleItemRecipeJsonBuilder MakeWoodcutting(ItemConvertible output, ItemConvertible ingredient) { return MakeWoodcutting(output, ingredient, 1); }
	public static SingleItemRecipeJsonBuilder MakeWoodcutting(ItemConvertible output, ItemConvertible ingredient, int count) {
		return new SingleItemRecipeJsonBuilder(WOODCUTTING_RECIPE_SERIALIZER, Ingredient.ofItems(ingredient), output, count)
				.criterion(RecipeProvider.hasItem(ingredient), RecipeProvider.conditionsFromItem(ingredient));
	}

	public static ShapelessRecipeJsonBuilder MakeShapeless(ItemConvertible output, ItemConvertible ingredient) { return MakeShapeless(output, ingredient, 1); }
	public static ShapelessRecipeJsonBuilder MakeShapeless(ItemConvertible output, ItemConvertible ingredient, int count) {
		return MakeShapeless(output, Ingredient.ofItems(ingredient), count);
	}
	public static ShapelessRecipeJsonBuilder MakeShapeless(ItemConvertible output, Ingredient ingredient) { return MakeShapeless(output, ingredient, 1); }
	public static ShapelessRecipeJsonBuilder MakeShapeless(ItemConvertible output, Ingredient ingredient, int count) {
		return ShapelessRecipeJsonBuilder.create(output, count).input(ingredient)
				.criterion("automatic", RecipeProvider.conditionsFromItem(Items.STICK));
	}

	public static ShapedRecipeJsonBuilder MakeShaped(ItemConvertible output, ItemConvertible ingredient) { return MakeShaped(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder MakeShaped(ItemConvertible output, ItemConvertible ingredient, int count) {
		return MakeShaped(output, Ingredient.ofItems(ingredient), count);
	}
	public static ShapedRecipeJsonBuilder MakeShaped(ItemConvertible output, TagKey<Item> ingredient) { return MakeShaped(output, Ingredient.fromTag(ingredient)); }
	public static ShapedRecipeJsonBuilder MakeShaped(ItemConvertible output, TagKey<Item> ingredient, int count) { return MakeShaped(output, Ingredient.fromTag(ingredient), count); }
	public static ShapedRecipeJsonBuilder MakeShaped(ItemConvertible output, Ingredient ingredient) { return MakeShaped(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder MakeShaped(ItemConvertible output, Ingredient ingredient, int count) {
		return ShapedRecipeJsonBuilder.create(output, count).input('#', ingredient)
				.criterion("automatic", RecipeProvider.conditionsFromItem(Items.STICK));
	}
	public static ShapedRecipeJsonBuilder Make1x2(ItemConvertible output, ItemConvertible ingredient) { return Make1x2(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder Make1x2(ItemConvertible output, ItemConvertible ingredient, int count) { return MakeShaped(output, ingredient, count).pattern("#").pattern("#"); }
	public static ShapedRecipeJsonBuilder Make1x2(ItemConvertible output, Ingredient ingredient, int count) { return MakeShaped(output, ingredient, count).pattern("#").pattern("#"); }
	public static ShapedRecipeJsonBuilder Make1x3(ItemConvertible output, ItemConvertible ingredient) { return Make1x3(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder Make1x3(ItemConvertible output, ItemConvertible ingredient, int count) { return Make1x2(output, ingredient, count).pattern("#"); }
	public static ShapedRecipeJsonBuilder Make1x3(ItemConvertible output, Ingredient ingredient, int count) { return Make1x2(output, ingredient, count).pattern("#"); }
	public static ShapedRecipeJsonBuilder Make2x1(ItemConvertible output, ItemConvertible ingredient) { return Make2x1(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder Make2x1(ItemConvertible output, ItemConvertible ingredient, int count) { return MakeShaped(output, ingredient, count).pattern("##"); }
	public static ShapedRecipeJsonBuilder Make2x1(ItemConvertible output, Ingredient ingredient) { return Make2x1(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder Make2x1(ItemConvertible output, Ingredient ingredient, int count) { return MakeShaped(output, ingredient, count).pattern("##"); }
	public static ShapedRecipeJsonBuilder Make2x2(ItemConvertible output, Ingredient ingredient) { return Make2x2(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder Make2x2(ItemConvertible output, Ingredient ingredient, int count) { return Make2x1(output, ingredient, count).pattern("##"); }
	public static ShapedRecipeJsonBuilder Make2x2(ItemConvertible output, ItemConvertible ingredient) { return Make2x2(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder Make2x2(ItemConvertible output, ItemConvertible ingredient, int count) { return Make2x1(output, ingredient, count).pattern("##"); }
	public static ShapedRecipeJsonBuilder Make2x3(ItemConvertible output, ItemConvertible ingredient) { return Make2x3(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder Make2x3(ItemConvertible output, ItemConvertible ingredient, int count) { return Make2x2(output, ingredient, count).pattern("##"); }
	public static ShapedRecipeJsonBuilder Make3x1(ItemConvertible output, ItemConvertible ingredient) { return Make3x1(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder Make3x1(ItemConvertible output, Ingredient ingredient) { return Make3x1(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder Make3x1(ItemConvertible output, ItemConvertible ingredient, int count) { return MakeShaped(output, ingredient, count).pattern("###"); }
	public static ShapedRecipeJsonBuilder Make3x1(ItemConvertible output, Ingredient ingredient, int count) { return MakeShaped(output, ingredient, count).pattern("###"); }
	public static ShapedRecipeJsonBuilder Make3x2(ItemConvertible output, ItemConvertible ingredient) { return Make3x2(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder Make3x2(ItemConvertible output, ItemConvertible ingredient, int count) { return Make3x2(output, Ingredient.ofItems(ingredient), count); }
	public static ShapedRecipeJsonBuilder Make3x2(ItemConvertible output, Ingredient ingredient) { return Make3x2(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder Make3x2(ItemConvertible output, Ingredient ingredient, int count) { return Make3x1(output, ingredient, count).pattern("###"); }
	public static ShapedRecipeJsonBuilder Make3x3(ItemConvertible output, ItemConvertible ingredient) { return Make3x3(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder Make3x3(ItemConvertible output, ItemConvertible ingredient, int count) { return Make3x2(output, ingredient, count).pattern("###"); }

	public static CraftingRecipeJsonBuilder MakeCuttingBoard(ItemConvertible input, ItemConvertible... output) { return MakeCuttingBoard(ModItemTags.KNIVES, input, output); }
	public static CraftingRecipeJsonBuilder MakeCuttingBoard(ItemConvertible tool, ItemConvertible input, ItemConvertible... output) { return MakeCuttingBoard(Ingredient.ofItems(tool), input, output); }
	public static CraftingRecipeJsonBuilder MakeCuttingBoard(TagKey<Item> tool, ItemConvertible input, ItemConvertible... output) { return MakeCuttingBoard(Ingredient.fromTag(tool), input, output); }
	public static CraftingRecipeJsonBuilder MakeCuttingBoard(Ingredient tool, ItemConvertible input, ItemConvertible... output) {
		return new CuttingBoardRecipeJsonBuilder(tool, Arrays.stream(output).map(ItemConvertible::asItem).toArray(Item[]::new)).input(input)
				.criterion(RecipeProvider.hasItem(input), RecipeProvider.conditionsFromItem(input));
	}

	public static ShapedRecipeJsonBuilder MakeArmorTrim(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, Items.DIAMOND, 2).input('S', output).input('C', ingredient).pattern("#S#").pattern("#C#").pattern("###");
	}
	public static CraftingRecipeJsonBuilder MakeAxe(ItemConvertible output, ItemConvertible ingredient) { return MakeAxe(output, ingredient, Items.STICK); }
	public static CraftingRecipeJsonBuilder MakeAxe(ItemConvertible output, ItemConvertible ingredient, ItemConvertible stick) {
		return MakeShaped(output, ingredient).input('|', stick).pattern("##").pattern("#|").pattern(" |");
	}
	public static CraftingRecipeJsonBuilder MakeBarrel(ItemConvertible output, ItemConvertible planks, ItemConvertible slabs) {
		return MakeShaped(output, planks).input('S', slabs).pattern("#S#").pattern("# #").pattern("#S#");
	}
	public static CraftingRecipeJsonBuilder MakeBeehive(ItemConvertible output, ItemConvertible planks) {
		return Make3x1(output, Items.HONEYCOMB).input('P', planks).pattern("PPP").pattern("###");
	}
	public static CraftingRecipeJsonBuilder MakeBed(ItemConvertible output, ItemConvertible ingredient) {
		return Make3x1(output, ingredient).input('X', ItemTags.PLANKS).pattern("XXX");
	}
	public static CraftingRecipeJsonBuilder MakeBed(ItemConvertible output, Ingredient ingredient) {
		return Make3x1(output, ingredient).input('X', ItemTags.PLANKS).pattern("XXX");
	}
	public static CraftingRecipeJsonBuilder MakeBoat(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).pattern("# #").pattern("###");
	}
	public static CraftingRecipeJsonBuilder MakeChain(ItemConvertible output, ItemConvertible ingot, ItemConvertible nugget) {
		return MakeShaped(output, ingot).input('N', nugget).pattern("N").pattern("#").pattern("N");
	}
	public static CraftingRecipeJsonBuilder MakeBookshelf(ItemConvertible output, ItemConvertible ingredient) {
		return Make3x1(output, ingredient).input('X', ModItemTags.BOOKS).pattern("XXX").pattern("###");
	}
	public static CraftingRecipeJsonBuilder MakeChiseledBookshelf(ItemConvertible output, ItemConvertible ingredient) {
		return Make3x1(output, ingredient).input('X', ItemTags.WOODEN_SLABS).pattern("XXX").pattern("###");
	}
	public static CraftingRecipeJsonBuilder MakeBoots(ItemConvertible output, ItemConvertible ingredient) {
		return MakeBoots(output, Ingredient.ofItems(ingredient));
	}
	public static CraftingRecipeJsonBuilder MakeBoots(ItemConvertible output, Ingredient ingredient) {
		return MakeShaped(output, ingredient).pattern("# #").pattern("# #");
	}
	public static CraftingRecipeJsonBuilder MakeCampfire(ItemConvertible output, TagKey<Item> logs) { return MakeCampfire(output, logs, Ingredient.fromTag(ItemTags.COALS)); }
	public static CraftingRecipeJsonBuilder MakeSoulCampfire(ItemConvertible output, TagKey<Item> logs) { return MakeCampfire(output, logs, Ingredient.fromTag(ItemTags.SOUL_FIRE_BASE_BLOCKS)); }
	public static CraftingRecipeJsonBuilder MakeEnderCampfire(ItemConvertible output, TagKey<Item> logs) { return MakeCampfire(output, logs, Ingredient.ofItems(Items.POPPED_CHORUS_FRUIT)); }
	public static CraftingRecipeJsonBuilder MakeCampfire(ItemConvertible output, TagKey<Item> logs, Ingredient coal) {
		return make(ShapedRecipeJsonBuilder.create(output).input('C', coal).input('L', logs).input('S', Items.STICK)
				.pattern(" S ").pattern("SCS").pattern("LLL"));
	}
	public static CraftingRecipeJsonBuilder MakeCarpet(ItemConvertible output, ItemConvertible ingredient) { return Make2x1(output, ingredient, 3); }
	public static CraftingRecipeJsonBuilder MakeChestplate(ItemConvertible output, ItemConvertible ingredient) {
		return MakeChestplate(output, Ingredient.ofItems(ingredient));
	}
	public static CraftingRecipeJsonBuilder MakeChestplate(ItemConvertible output, Ingredient ingredient) {
		return MakeShaped(output, ingredient).pattern("# #").pattern("###").pattern("###");
	}
	public static CraftingRecipeJsonBuilder MakeDoor(ItemConvertible output, ItemConvertible ingredient) {
		return Make2x3(output, ingredient, 3);
	}
	public static CraftingRecipeJsonBuilder MakeGoldenFood(ItemConvertible output, ItemConvertible ingredient) {
		return Make3x1(output, Items.GOLD_NUGGET).input('*', ingredient).pattern("#*#").pattern("###");
	}
	public static CraftingRecipeJsonBuilder MakeGourdLantern(ItemConvertible output, ItemConvertible gourd, TagKey<Item> torches) {
		return MakeShaped(output, gourd).input('B', torches).pattern("#").pattern("B");
	}
	public static CraftingRecipeJsonBuilder MakeGourdLantern(ItemConvertible output, ItemConvertible gourd) { return MakeGourdLantern(output, gourd, ModItemTags.TORCHES); }
	public static CraftingRecipeJsonBuilder MakeSoulGourdLantern(ItemConvertible output, ItemConvertible gourd) { return MakeGourdLantern(output, gourd, ModItemTags.SOUL_TORCHES); }
	public static CraftingRecipeJsonBuilder MakeEnderGourdLantern(ItemConvertible output, ItemConvertible gourd) { return MakeGourdLantern(output, gourd, ModItemTags.ENDER_TORCHES); }
	public static CraftingRecipeJsonBuilder MakeHammer(ItemConvertible output, ItemConvertible ingredient) { return MakeHammer(output, ingredient, Items.STICK); }
	public static CraftingRecipeJsonBuilder MakeHammer(ItemConvertible output, ItemConvertible ingredient, ItemConvertible stick) {
		return MakeShaped(output, ingredient).input('|', Items.STICK).pattern("#|#").pattern(" | ").pattern(" | ");
	}
	public static CraftingRecipeJsonBuilder MakeHelmet(ItemConvertible output, ItemConvertible ingredient) {
		return Make3x1(output, ingredient).pattern("# #");
	}
	public static CraftingRecipeJsonBuilder MakeHelmet(ItemConvertible output, Ingredient ingredient) {
		return Make3x1(output, ingredient).pattern("# #");
	}
	public static CraftingRecipeJsonBuilder MakeHoe(ItemConvertible output, ItemConvertible ingredient) { return MakeHoe(output, ingredient, Items.STICK); }
	public static CraftingRecipeJsonBuilder MakeHoe(ItemConvertible output, ItemConvertible ingredient, ItemConvertible stick) {
		return MakeShaped(output, ingredient).input('|', stick).pattern("##").pattern(" |").pattern(" |");
	}
	public static CraftingRecipeJsonBuilder MakeHorseArmor(ItemConvertible output, ItemConvertible ingredient) {
		return MakeHorseArmor(output, Ingredient.ofItems(ingredient));
	}
	public static CraftingRecipeJsonBuilder MakeHorseArmor(ItemConvertible output, Ingredient ingredient) {
		return MakeShaped(output, ingredient).pattern("# #").pattern("###").pattern("# #");
	}
	public static CraftingRecipeJsonBuilder MakeKnife(ItemConvertible output, ItemConvertible ingredient) { return MakeKnife(output, ingredient, Items.STICK); }
	public static CraftingRecipeJsonBuilder MakeKnife(ItemConvertible output, ItemConvertible ingredient, ItemConvertible stick) {
		return MakeShaped(output, ingredient).input('|', stick).pattern(" #").pattern("| ");
	}
	public static CraftingRecipeJsonBuilder MakeLantern(ItemConvertible output, ItemConvertible ingredient, TagKey<Item> torch) {
		return Make3x1(output, ingredient).input('T', torch).pattern("#T#").pattern("###");
	}
	public static CraftingRecipeJsonBuilder MakeLadder(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient, 3).pattern("# #").pattern("###").pattern("# #");
	}
	public static CraftingRecipeJsonBuilder MakeLeggings(ItemConvertible output, ItemConvertible ingredient) {
		return MakeLeggings(output, Ingredient.ofItems(ingredient));
	}
	public static CraftingRecipeJsonBuilder MakeLeggings(ItemConvertible output, Ingredient ingredient) {
		return Make3x1(output, ingredient).pattern("# #").pattern("# #");
	}
	public static CraftingRecipeJsonBuilder MakePickaxe(ItemConvertible output, ItemConvertible ingredient) { return MakePickaxe(output, ingredient, Items.STICK); }
	public static CraftingRecipeJsonBuilder MakePickaxe(ItemConvertible output, ItemConvertible ingredient, ItemConvertible stick) {
		return MakeShaped(output, ingredient).input('|', stick).pattern("###").pattern(" | ").pattern(" | ");
	}
	public static CraftingRecipeJsonBuilder MakePlanksLadder(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient, 3).input('|', Items.STICK).pattern("| |").pattern("|#|").pattern("| |");
	}
	public static CraftingRecipeJsonBuilder MakePressurePlate(ItemConvertible output, ItemConvertible ingredient) {
		return Make2x1(output, ingredient);
	}
	public static CraftingRecipeJsonBuilder MakeSandy(ItemConvertible output, ItemConvertible input) { return MakeShapeless(output, Items.SAND).input(input); }
	public static CraftingRecipeJsonBuilder MakeShears(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).pattern(" #").pattern("# ");
	}
	public static CraftingRecipeJsonBuilder MakeShovel(ItemConvertible output, ItemConvertible ingredient) { return MakeShovel(output, ingredient, Items.STICK); }
	public static CraftingRecipeJsonBuilder MakeShovel(ItemConvertible output, ItemConvertible ingredient, ItemConvertible stick) {
		return MakeShaped(output, ingredient).input('|', stick).pattern("#").pattern("|").pattern("|");
	}
	public static CraftingRecipeJsonBuilder MakeSign(ItemConvertible output, ItemConvertible ingredient) { return MakeSign(output, Ingredient.ofItems(ingredient)); }
	public static CraftingRecipeJsonBuilder MakeSign(ItemConvertible output, Ingredient ingredient) {
		return Make3x2(output, ingredient).input('|', Items.STICK).pattern(" | ");
	}
	public static CraftingRecipeJsonBuilder MakeHangingSign(ItemConvertible output, ItemConvertible ingredient) { return MakeHangingSign(output, Ingredient.ofItems(ingredient)); }
	public static CraftingRecipeJsonBuilder MakeHangingSign(ItemConvertible output, Ingredient ingredient) {
		return MakeShaped(output, ingredient).input('|', Items.CHAIN).pattern("| |").pattern("###").pattern("###");
	}
	public static CraftingRecipeJsonBuilder MakeLectern(ItemConvertible output, ItemConvertible slabs) {
		return MakeShaped(output, slabs).input('B', ModItemTags.BOOKSHELVES).pattern("###").pattern(" B ").pattern(" # ");
	}
	public static CraftingRecipeJsonBuilder MakePowderKeg(ItemConvertible output, ItemConvertible barrel) {
		return MakeShapeless(output, barrel).input(ModBase.GUNPOWDER_BLOCK);
	}
	public static CraftingRecipeJsonBuilder MakeSlab(ItemConvertible output, ItemConvertible ingredient) { return Make3x1(output, ingredient, 6); }
	public static CraftingRecipeJsonBuilder MakeStairs(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient, 4).pattern("#  ").pattern("## ").pattern("###");
	}
	public static CraftingRecipeJsonBuilder MakeSword(ItemConvertible output, ItemConvertible ingredient) { return MakeSword(output, ingredient, Items.STICK); }
	public static CraftingRecipeJsonBuilder MakeSword(ItemConvertible output, ItemConvertible ingredient, ItemConvertible stick) {
		return MakeShaped(output, ingredient).input('|', stick).pattern("#").pattern("#").pattern("|");
	}
	public static CraftingRecipeJsonBuilder MakeSummoningArrow(ArrowContainer container, Item spawnEgg) {
		return MakeShapeless(container.asItem(), spawnEgg).input(ItemTags.ARROWS);
	}

	public static ShapedRecipeJsonBuilder MakeTorch(ItemConvertible output, ItemConvertible stick) { return MakeTorch(output, ItemTags.COALS, stick); }
	public static ShapedRecipeJsonBuilder MakeTorch(ItemConvertible output, ItemConvertible coal, ItemConvertible stick) { return MakeTorch(output, Ingredient.ofItems(coal), stick); }
	public static ShapedRecipeJsonBuilder MakeTorch(ItemConvertible output, TagKey<Item> coal, ItemConvertible stick) { return MakeTorch(output, Ingredient.fromTag(coal), stick); }
	public static ShapedRecipeJsonBuilder MakeTorch(ItemConvertible output, Ingredient coal, ItemConvertible stick) { return MakeTorch(output, coal, stick, 1); }
	public static ShapedRecipeJsonBuilder MakeTorch(ItemConvertible output, ItemConvertible stick, int count) { return MakeTorch(output, ItemTags.COALS, stick, count); }
	public static ShapedRecipeJsonBuilder MakeTorch(ItemConvertible output, ItemConvertible coal, ItemConvertible stick, int count) { return MakeTorch(output, Ingredient.ofItems(coal), stick, count); }
	public static ShapedRecipeJsonBuilder MakeTorch(ItemConvertible output, TagKey<Item> coal, ItemConvertible stick, int count) { return MakeTorch(output, Ingredient.fromTag(coal), stick, count); }
	public static ShapedRecipeJsonBuilder MakeTorch(ItemConvertible output, Ingredient coal, ItemConvertible stick, int count) {
		return MakeShaped(output, coal, count).input('|', stick).pattern("#").pattern("|");
	}

	public static ShapedRecipeJsonBuilder MakeEnderTorch(ItemConvertible output, ItemConvertible stick) { return MakeEnderTorch(output, ItemTags.COALS, stick); }
	public static ShapedRecipeJsonBuilder MakeEnderTorch(ItemConvertible output, ItemConvertible coal, ItemConvertible stick) { return MakeEnderTorch(output, Ingredient.ofItems(coal), stick); }
	public static ShapedRecipeJsonBuilder MakeEnderTorch(ItemConvertible output, TagKey<Item> coal, ItemConvertible stick) { return MakeEnderTorch(output, Ingredient.fromTag(coal), stick); }
	public static ShapedRecipeJsonBuilder MakeEnderTorch(ItemConvertible output, Ingredient coal, ItemConvertible stick) { return MakeEnderTorch(output, coal, stick, 1); }
	public static ShapedRecipeJsonBuilder MakeEnderTorch(ItemConvertible output, ItemConvertible stick, int count) { return MakeEnderTorch(output, ItemTags.COALS, stick, count); }
	public static ShapedRecipeJsonBuilder MakeEnderTorch(ItemConvertible output, ItemConvertible coal, ItemConvertible stick, int count) { return MakeEnderTorch(output, Ingredient.ofItems(coal), stick, count); }
	public static ShapedRecipeJsonBuilder MakeEnderTorch(ItemConvertible output, TagKey<Item> coal, ItemConvertible stick, int count) { return MakeEnderTorch(output, Ingredient.fromTag(coal), stick, count); }
	public static ShapedRecipeJsonBuilder MakeEnderTorch(ItemConvertible output, Ingredient coal, ItemConvertible stick, int count) {
		return MakeTorch(output, coal, stick, count).input('*', Items.POPPED_CHORUS_FRUIT).pattern("*");
	}

	public static ShapedRecipeJsonBuilder MakeSoulTorch(ItemConvertible output, ItemConvertible stick) { return MakeSoulTorch(output, ItemTags.COALS, stick); }
	public static ShapedRecipeJsonBuilder MakeSoulTorch(ItemConvertible output, ItemConvertible coal, ItemConvertible stick) { return MakeSoulTorch(output, Ingredient.ofItems(coal), stick); }
	public static ShapedRecipeJsonBuilder MakeSoulTorch(ItemConvertible output, TagKey<Item> coal, ItemConvertible stick) { return MakeSoulTorch(output, Ingredient.fromTag(coal), stick); }
	public static ShapedRecipeJsonBuilder MakeSoulTorch(ItemConvertible output, Ingredient coal, ItemConvertible stick) { return MakeSoulTorch(output, coal, stick, 1); }
	public static ShapedRecipeJsonBuilder MakeSoulTorch(ItemConvertible output, ItemConvertible stick, int count) { return MakeSoulTorch(output, ItemTags.COALS, stick, count); }
	public static ShapedRecipeJsonBuilder MakeSoulTorch(ItemConvertible output, ItemConvertible coal, ItemConvertible stick, int count) { return MakeSoulTorch(output, Ingredient.ofItems(coal), stick, count); }
	public static ShapedRecipeJsonBuilder MakeSoulTorch(ItemConvertible output, TagKey<Item> coal, ItemConvertible stick, int count) { return MakeSoulTorch(output, Ingredient.fromTag(coal), stick, count); }
	public static ShapedRecipeJsonBuilder MakeSoulTorch(ItemConvertible output, Ingredient coal, ItemConvertible stick, int count) {
		return MakeTorch(output, coal, stick, count).input('*', ItemTags.SOUL_FIRE_BASE_BLOCKS).pattern("*");
	}

	public static ShapedRecipeJsonBuilder MakeUnderwaterTorch(ItemConvertible output, ItemConvertible stick) { return MakeTorch(output, stick, 1); }
	public static ShapedRecipeJsonBuilder MakeUnderwaterTorch(ItemConvertible output, ItemConvertible stick, int count) {
		return MakeShaped(output, Items.INK_SAC, count).input('|', stick).pattern("#").pattern("|");
	}

	public static CraftingRecipeJsonBuilder MakeWall(ItemConvertible output, ItemConvertible ingredient) { return Make3x2(output, ingredient, 6); }
	public static CraftingRecipeJsonBuilder MakeWoodcutter(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).input('I', Items.IRON_INGOT).pattern(" I ").pattern("###");
	}
	public static CraftingRecipeJsonBuilder MakeWoodenFence(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).input('|', Items.STICK).pattern("#|#").pattern("#|#");
	}
	public static CraftingRecipeJsonBuilder MakeWoodenFenceGate(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).input('|', Items.STICK).pattern("|#|").pattern("|#|");
	}
	public static CraftingRecipeJsonBuilder MakeWoodenTrapdoor(ItemConvertible output, ItemConvertible ingredient) {
		return Make3x2(output, ingredient, 3);
	}


}
