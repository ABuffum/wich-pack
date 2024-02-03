package fun.wich.mixins.block;

import fun.wich.ModBase;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(MyceliumBlock.class)
public class MyceliumBlockMixin extends SpreadableBlock implements Fertilizable {
	protected MyceliumBlockMixin(Settings settings) { super(settings); }

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return world.getBlockState(pos.up()).isAir();
	}
	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return true; }
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		//Generate Mycelium Roots
		BlockPos blockPos = pos.up();
		block0: for (int i = 0; i < 128; ++i) {
			RegistryEntry<PlacedFeature> registryEntry = ModBase.MYCELIUM_BONEMEAL_MYCELIUM_ROOTS.getRegistryEntry();
			BlockPos blockPos2 = blockPos;
			for (int j = 0; j < i / 16; ++j) {
				if (!world.getBlockState((blockPos2 = blockPos2.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1)).down()).isOf(this) || world.getBlockState(blockPos2).isFullCube(world, blockPos2)) continue block0;
			}
			BlockState blockState2 = world.getBlockState(blockPos2);
			if (blockState2.isOf(Blocks.MYCELIUM) && random.nextInt(10) == 0) {
				((Fertilizable)Blocks.MYCELIUM).grow(world, random, blockPos2, blockState2);
			}
			if (!blockState2.isAir()) continue;
			if (random.nextInt(20) == 0) {
				switch (random.nextInt(3)) {
					case 0 -> registryEntry = ModBase.MYCELIUM_BONEMEAL_BROWN_MUSHROOM.getRegistryEntry();
					case 1 -> registryEntry = ModBase.MYCELIUM_BONEMEAL_BLUE_MUSHROOM.getRegistryEntry();
					case 2 -> registryEntry = ModBase.MYCELIUM_BONEMEAL_RED_MUSHROOM.getRegistryEntry();
				}
			}
			registryEntry.value().generateUnregistered(world, world.getChunkManager().getChunkGenerator(), random, blockPos2);
		}
	}
}
