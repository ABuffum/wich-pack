package fun.mousewich.gen.feature;

import fun.mousewich.ModBase;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class DogwoodSaplingGenerator extends SaplingGenerator {
	@Override
	protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
		return (switch (random.nextInt(6)) {
			case 0 -> ModBase.PINK_DOGWOOD_TREE_CONFIGURED;
			case 1, 2 -> ModBase.PALE_DOGWOOD_TREE_CONFIGURED;
			default -> ModBase.WHITE_DOGWOOD_TREE_CONFIGURED;
		}).getRegistryEntry();
	}
}