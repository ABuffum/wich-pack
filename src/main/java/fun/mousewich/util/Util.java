package fun.mousewich.util;

import fun.mousewich.container.BlockContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.DyeColor;

import java.util.function.ToIntFunction;

public class Util {
	//Block Luminance Functions
	public static final ToIntFunction<BlockState> LUMINANCE_15 = state -> 15, LUMINANCE_14 = state -> 14, LUMINANCE_13 = state -> 13, LUMINANCE_12 = state -> 12;
	public static final ToIntFunction<BlockState> LUMINANCE_11 = state -> 11, LUMINANCE_10 = state -> 10, LUMINANCE_9 = state -> 9, LUMINANCE_8 = state -> 8;
	public static final ToIntFunction<BlockState> LUMINANCE_7 = state -> 7, LUMINANCE_6 = state -> 6, LUMINANCE_5 = state -> 5, LUMINANCE_4 = state -> 4;
	public static final ToIntFunction<BlockState> LUMINANCE_3 = state -> 3, LUMINANCE_2 = state -> 2, LUMINANCE_1 = state -> 1, LUMINANCE_0 = state -> 0;

	public static DyeColor GetCandleColor(Block block) {
		if (block == Blocks.BLACK_CANDLE) return DyeColor.BLACK;
		else if (block == Blocks.BLUE_CANDLE) return DyeColor.BLUE;
		else if (block == Blocks.BROWN_CANDLE) return DyeColor.BROWN;
		else if (block == Blocks.CYAN_CANDLE) return DyeColor.CYAN;
		else if (block == Blocks.GRAY_CANDLE) return DyeColor.GRAY;
		else if (block == Blocks.GREEN_CANDLE) return DyeColor.GREEN;
		else if (block == Blocks.LIGHT_BLUE_CANDLE) return DyeColor.LIGHT_BLUE;
		else if (block == Blocks.LIGHT_GRAY_CANDLE) return DyeColor.LIGHT_GRAY;
		else if (block == Blocks.LIME_CANDLE) return DyeColor.LIME;
		else if (block == Blocks.MAGENTA_CANDLE) return DyeColor.MAGENTA;
		else if (block == Blocks.ORANGE_CANDLE) return DyeColor.ORANGE;
		else if (block == Blocks.PINK_CANDLE) return DyeColor.PINK;
		else if (block == Blocks.PURPLE_CANDLE) return DyeColor.PURPLE;
		else if (block == Blocks.RED_CANDLE) return DyeColor.RED;
		else if (block == Blocks.WHITE_CANDLE) return DyeColor.WHITE;
		else if (block == Blocks.YELLOW_CANDLE) return DyeColor.YELLOW;
		else return DyeColor.WHITE;
	}
	public static Block GetWoolBlock(DyeColor color) {
		return switch (color) {
			case ORANGE -> Blocks.ORANGE_WOOL;
			case MAGENTA -> Blocks.MAGENTA_WOOL;
			case LIGHT_BLUE -> Blocks.LIGHT_BLUE_WOOL;
			case YELLOW -> Blocks.YELLOW_WOOL;
			case LIME -> Blocks.LIME_WOOL;
			case PINK -> Blocks.PINK_WOOL;
			case GRAY -> Blocks.GRAY_WOOL;
			case LIGHT_GRAY -> Blocks.LIGHT_GRAY_WOOL;
			case CYAN -> Blocks.CYAN_WOOL;
			case PURPLE -> Blocks.PURPLE_WOOL;
			case BLUE -> Blocks.BLUE_WOOL;
			case BROWN -> Blocks.BROWN_WOOL;
			case GREEN -> Blocks.GREEN_WOOL;
			case RED -> Blocks.RED_WOOL;
			case BLACK -> Blocks.BLACK_WOOL;
			case WHITE -> Blocks.WHITE_WOOL;
		};
	}
	public static Item GetWoolItem(DyeColor color) {
		return switch (color) {
			case ORANGE -> Items.ORANGE_WOOL;
			case MAGENTA -> Items.MAGENTA_WOOL;
			case LIGHT_BLUE -> Items.LIGHT_BLUE_WOOL;
			case YELLOW -> Items.YELLOW_WOOL;
			case LIME -> Items.LIME_WOOL;
			case PINK -> Items.PINK_WOOL;
			case GRAY -> Items.GRAY_WOOL;
			case LIGHT_GRAY -> Items.LIGHT_GRAY_WOOL;
			case CYAN -> Items.CYAN_WOOL;
			case PURPLE -> Items.PURPLE_WOOL;
			case BLUE -> Items.BLUE_WOOL;
			case BROWN -> Items.BROWN_WOOL;
			case GREEN -> Items.GREEN_WOOL;
			case RED -> Items.RED_WOOL;
			case BLACK -> Items.BLACK_WOOL;
			case WHITE -> Items.WHITE_WOOL;
		};
	}
	public static Block GetWoolCarpetBlock(DyeColor color) {
		return switch (color) {
			case ORANGE -> Blocks.ORANGE_CARPET;
			case MAGENTA -> Blocks.MAGENTA_CARPET;
			case LIGHT_BLUE -> Blocks.LIGHT_BLUE_CARPET;
			case YELLOW -> Blocks.YELLOW_CARPET;
			case LIME -> Blocks.LIME_CARPET;
			case PINK -> Blocks.PINK_CARPET;
			case GRAY -> Blocks.GRAY_CARPET;
			case LIGHT_GRAY -> Blocks.LIGHT_GRAY_CARPET;
			case CYAN -> Blocks.CYAN_CARPET;
			case PURPLE -> Blocks.PURPLE_CARPET;
			case BLUE -> Blocks.BLUE_CARPET;
			case BROWN -> Blocks.BROWN_CARPET;
			case GREEN -> Blocks.GREEN_CARPET;
			case RED -> Blocks.RED_CARPET;
			case BLACK -> Blocks.BLACK_CARPET;
			case WHITE -> Blocks.WHITE_CARPET;
		};
	}
	public static Item GetWoolCarpetItem(DyeColor color) {
		return switch (color) {
			case ORANGE -> Items.ORANGE_CARPET;
			case MAGENTA -> Items.MAGENTA_CARPET;
			case LIGHT_BLUE -> Items.LIGHT_BLUE_CARPET;
			case YELLOW -> Items.YELLOW_CARPET;
			case LIME -> Items.LIME_CARPET;
			case PINK -> Items.PINK_CARPET;
			case GRAY -> Items.GRAY_CARPET;
			case LIGHT_GRAY -> Items.LIGHT_GRAY_CARPET;
			case CYAN -> Items.CYAN_CARPET;
			case PURPLE -> Items.PURPLE_CARPET;
			case BLUE -> Items.BLUE_CARPET;
			case BROWN -> Items.BROWN_CARPET;
			case GREEN -> Items.GREEN_CARPET;
			case RED -> Items.RED_CARPET;
			case BLACK -> Items.BLACK_CARPET;
			case WHITE -> Items.WHITE_CARPET;
		};
	}
	public static Block GetStainedGlassBlock(DyeColor color) {
		return switch (color) {
			case ORANGE -> Blocks.ORANGE_STAINED_GLASS;
			case MAGENTA -> Blocks.MAGENTA_STAINED_GLASS;
			case LIGHT_BLUE -> Blocks.LIGHT_BLUE_STAINED_GLASS;
			case YELLOW -> Blocks.YELLOW_STAINED_GLASS;
			case LIME -> Blocks.LIME_STAINED_GLASS;
			case PINK -> Blocks.PINK_STAINED_GLASS;
			case GRAY -> Blocks.GRAY_STAINED_GLASS;
			case LIGHT_GRAY -> Blocks.LIGHT_GRAY_STAINED_GLASS;
			case CYAN -> Blocks.CYAN_STAINED_GLASS;
			case PURPLE -> Blocks.PURPLE_STAINED_GLASS;
			case BLUE -> Blocks.BLUE_STAINED_GLASS;
			case BROWN -> Blocks.BROWN_STAINED_GLASS;
			case GREEN -> Blocks.GREEN_STAINED_GLASS;
			case RED -> Blocks.RED_STAINED_GLASS;
			case BLACK -> Blocks.BLACK_STAINED_GLASS;
			case WHITE -> Blocks.WHITE_STAINED_GLASS;
		};
	}
	public static Item GetStainedGlassItem(DyeColor color) {
		return switch (color) {
			case ORANGE -> Items.ORANGE_STAINED_GLASS;
			case MAGENTA -> Items.MAGENTA_STAINED_GLASS;
			case LIGHT_BLUE -> Items.LIGHT_BLUE_STAINED_GLASS;
			case YELLOW -> Items.YELLOW_STAINED_GLASS;
			case LIME -> Items.LIME_STAINED_GLASS;
			case PINK -> Items.PINK_STAINED_GLASS;
			case GRAY -> Items.GRAY_STAINED_GLASS;
			case LIGHT_GRAY -> Items.LIGHT_GRAY_STAINED_GLASS;
			case CYAN -> Items.CYAN_STAINED_GLASS;
			case PURPLE -> Items.PURPLE_STAINED_GLASS;
			case BLUE -> Items.BLUE_STAINED_GLASS;
			case BROWN -> Items.BROWN_STAINED_GLASS;
			case GREEN -> Items.GREEN_STAINED_GLASS;
			case RED -> Items.RED_STAINED_GLASS;
			case BLACK -> Items.BLACK_STAINED_GLASS;
			case WHITE -> Items.WHITE_STAINED_GLASS;
		};
	}
	public static Block GetBannerBlock(DyeColor color) {
		return switch (color) {
			case ORANGE -> Blocks.ORANGE_BANNER;
			case MAGENTA -> Blocks.MAGENTA_BANNER;
			case LIGHT_BLUE -> Blocks.LIGHT_BLUE_BANNER;
			case YELLOW -> Blocks.YELLOW_BANNER;
			case LIME -> Blocks.LIME_BANNER;
			case PINK -> Blocks.PINK_BANNER;
			case GRAY -> Blocks.GRAY_BANNER;
			case LIGHT_GRAY -> Blocks.LIGHT_GRAY_BANNER;
			case CYAN -> Blocks.CYAN_BANNER;
			case PURPLE -> Blocks.PURPLE_BANNER;
			case BLUE -> Blocks.BLUE_BANNER;
			case BROWN -> Blocks.BROWN_BANNER;
			case GREEN -> Blocks.GREEN_BANNER;
			case RED -> Blocks.RED_BANNER;
			case BLACK -> Blocks.BLACK_BANNER;
			case WHITE -> Blocks.WHITE_BANNER;
		};
	}
	public static Item GetBannerItem(DyeColor color) {
		return switch (color) {
			case ORANGE -> Items.ORANGE_BANNER;
			case MAGENTA -> Items.MAGENTA_BANNER;
			case LIGHT_BLUE -> Items.LIGHT_BLUE_BANNER;
			case YELLOW -> Items.YELLOW_BANNER;
			case LIME -> Items.LIME_BANNER;
			case PINK -> Items.PINK_BANNER;
			case GRAY -> Items.GRAY_BANNER;
			case LIGHT_GRAY -> Items.LIGHT_GRAY_BANNER;
			case CYAN -> Items.CYAN_BANNER;
			case PURPLE -> Items.PURPLE_BANNER;
			case BLUE -> Items.BLUE_BANNER;
			case BROWN -> Items.BROWN_BANNER;
			case GREEN -> Items.GREEN_BANNER;
			case RED -> Items.RED_BANNER;
			case BLACK -> Items.BLACK_BANNER;
			case WHITE -> Items.WHITE_BANNER;
		};
	}
	public static Item GetBedItem(DyeColor color) {
		return switch (color) {
			case ORANGE -> Items.ORANGE_BED;
			case MAGENTA -> Items.MAGENTA_BED;
			case LIGHT_BLUE -> Items.LIGHT_BLUE_BED;
			case YELLOW -> Items.YELLOW_BED;
			case LIME -> Items.LIME_BED;
			case PINK -> Items.PINK_BED;
			case GRAY -> Items.GRAY_BED;
			case LIGHT_GRAY -> Items.LIGHT_GRAY_BED;
			case CYAN -> Items.CYAN_BED;
			case PURPLE -> Items.PURPLE_BED;
			case BLUE -> Items.BLUE_BED;
			case BROWN -> Items.BROWN_BED;
			case GREEN -> Items.GREEN_BED;
			case RED -> Items.RED_BED;
			case BLACK -> Items.BLACK_BED;
			case WHITE -> Items.WHITE_BED;
		};
	}
}
