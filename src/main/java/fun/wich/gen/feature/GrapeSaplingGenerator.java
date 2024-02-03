package fun.wich.gen.feature;

import fun.wich.ModBase;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class GrapeSaplingGenerator extends SaplingGenerator {
	@Override
	protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
		return (bees ? ModBase.GRAPE_TREE_BEES_CONFIGURED : ModBase.GRAPE_TREE_CONFIGURED).getRegistryEntry();
	}
}
