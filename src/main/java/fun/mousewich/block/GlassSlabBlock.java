package fun.mousewich.block;

import fun.mousewich.block.basic.ModSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class GlassSlabBlock extends AbstractGlassSlabBlock {
	public GlassSlabBlock(Block block) { super(block); }
}
