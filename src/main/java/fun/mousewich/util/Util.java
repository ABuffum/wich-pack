package fun.mousewich.util;

import net.minecraft.block.BlockState;

import java.util.function.ToIntFunction;

public class Util {
	//Block Luminance Functions
	public static final ToIntFunction<BlockState> LUMINANCE_15 = state -> 15, LUMINANCE_14 = state -> 14, LUMINANCE_13 = state -> 13, LUMINANCE_12 = state -> 12;
	public static final ToIntFunction<BlockState> LUMINANCE_11 = state -> 11, LUMINANCE_10 = state -> 10, LUMINANCE_9 = state -> 9, LUMINANCE_8 = state -> 8;
	public static final ToIntFunction<BlockState> LUMINANCE_7 = state -> 7, LUMINANCE_6 = state -> 6, LUMINANCE_5 = state -> 5, LUMINANCE_4 = state -> 4;
	public static final ToIntFunction<BlockState> LUMINANCE_3 = state -> 3, LUMINANCE_2 = state -> 2, LUMINANCE_1 = state -> 1, LUMINANCE_0 = state -> 0;
}
