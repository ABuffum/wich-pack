package fun.mousewich.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagKey;

import java.util.List;

public class StructureUtil {
	public static boolean contains(List<BlockState> states, BlockState state) { return contains(states, state.getBlock()); }
	public static boolean contains(List<BlockState> states, Block block) { return states.stream().anyMatch((state) -> state.isOf(block)); }
}
