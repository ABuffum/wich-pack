package fun.mousewich.gen.data.recipe;

import fun.mousewich.gen.data.tag.ModItemTags;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;

public class Recipes {
	private static CraftingRecipeJsonBuilder make(CraftingRecipeJsonBuilder recipe) {
		return recipe.criterion("automatic", RecipeProvider.conditionsFromItem(Items.STICK));
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
	public static ShapedRecipeJsonBuilder MakeShaped(ItemConvertible output, Ingredient ingredient) { return MakeShaped(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder MakeShaped(ItemConvertible output, Ingredient ingredient, int count) {
		return ShapedRecipeJsonBuilder.create(output, count).input('#', ingredient)
				.criterion("automatic", RecipeProvider.conditionsFromItem(Items.STICK));
	}
	public static ShapedRecipeJsonBuilder Make1x2(ItemConvertible output, ItemConvertible ingredient, int count) { return MakeShaped(output, ingredient, count).pattern("#").pattern("#"); }
	public static ShapedRecipeJsonBuilder Make2x1(ItemConvertible output, ItemConvertible ingredient) { return Make2x1(output, ingredient, 1); }
	public static ShapedRecipeJsonBuilder Make2x1(ItemConvertible output, ItemConvertible ingredient, int count) { return MakeShaped(output, ingredient, count).pattern("##"); }
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


	public static CraftingRecipeJsonBuilder MakeAxe(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).input('|', Items.STICK).pattern("##").pattern("#|").pattern(" |");
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
	public static CraftingRecipeJsonBuilder MakeBookshelf(ItemConvertible output, ItemConvertible ingredient) {
		return Make3x1(output, ingredient).input('X', ModItemTags.BOOKS).pattern("XXX").pattern("###");
	}
	public static CraftingRecipeJsonBuilder MakeBoots(ItemConvertible output, ItemConvertible ingredient) {
		return MakeBoots(output, Ingredient.ofItems(ingredient));
	}
	public static CraftingRecipeJsonBuilder MakeBoots(ItemConvertible output, Ingredient ingredient) {
		return MakeShaped(output, ingredient).pattern("# #").pattern("# #");
	}
	public static CraftingRecipeJsonBuilder MakeCampfire(ItemConvertible output, TagKey<Item> logs) {
		return make(ShapedRecipeJsonBuilder.create(output).input('C', ItemTags.COALS).input('L', logs).input('S', Items.STICK)
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
	public static CraftingRecipeJsonBuilder MakeHelmet(ItemConvertible output, ItemConvertible ingredient) {
		return MakeHelmet(output, Ingredient.ofItems(ingredient));
	}
	public static CraftingRecipeJsonBuilder MakeHelmet(ItemConvertible output, Ingredient ingredient) {
		return Make3x1(output, ingredient).pattern("# #");
	}
	public static CraftingRecipeJsonBuilder MakeHoe(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).input('|', Items.STICK).pattern("##").pattern(" |").pattern(" |");
	}
	public static CraftingRecipeJsonBuilder MakeHorseArmor(ItemConvertible output, ItemConvertible ingredient) {
		return MakeHorseArmor(output, Ingredient.ofItems(ingredient));
	}
	public static CraftingRecipeJsonBuilder MakeHorseArmor(ItemConvertible output, Ingredient ingredient) {
		return MakeShaped(output, ingredient).pattern("# #").pattern("###").pattern("# #");
	}
	public static CraftingRecipeJsonBuilder MakeKnife(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).input('|', Items.STICK).pattern(" #").pattern("| ");
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
	public static CraftingRecipeJsonBuilder MakePickaxe(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).input('|', Items.STICK).pattern("###").pattern(" | ").pattern(" | ");
	}
	public static CraftingRecipeJsonBuilder MakePlanksLadder(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient, 3).input('|', Items.STICK).pattern("| |").pattern("|#|").pattern("| |");
	}
	public static CraftingRecipeJsonBuilder MakePressurePlate(ItemConvertible output, ItemConvertible ingredient) {
		return Make2x1(output, ingredient);
	}
	public static CraftingRecipeJsonBuilder MakeShovel(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).input('|', Items.STICK).pattern("#").pattern("|").pattern("|");
	}
	public static CraftingRecipeJsonBuilder MakeSign(ItemConvertible output, ItemConvertible ingredient) { return MakeSign(output, Ingredient.ofItems(ingredient)); }
	public static CraftingRecipeJsonBuilder MakeSign(ItemConvertible output, Ingredient ingredient) {
		return Make3x2(output, ingredient).input('|', Items.STICK).pattern(" | ");
	}
	public static CraftingRecipeJsonBuilder MakeHangingSign(ItemConvertible output, ItemConvertible ingredient) { return MakeHangingSign(output, Ingredient.ofItems(ingredient)); }
	public static CraftingRecipeJsonBuilder MakeHangingSign(ItemConvertible output, Ingredient ingredient) {
		return MakeShaped(output, ingredient).input('|', Items.CHAIN).pattern("| |").pattern("###").pattern("###");
	}
	public static CraftingRecipeJsonBuilder MakeSlab(ItemConvertible output, ItemConvertible ingredient) { return Make3x1(output, ingredient, 6); }
	public static CraftingRecipeJsonBuilder MakeStairs(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient, 4).pattern("#  ").pattern("## ").pattern("###");
	}
	public static CraftingRecipeJsonBuilder MakeSword(ItemConvertible output, ItemConvertible ingredient) {
		return MakeShaped(output, ingredient).input('|', Items.STICK).pattern("#").pattern("#").pattern("|");
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
