package fun.mousewich.container;

import fun.mousewich.block.basic.ModFungusBlock;
import fun.mousewich.gen.feature.ModConfiguredFeature;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.gen.feature.HugeFungusFeatureConfig;

import java.util.function.Supplier;

public class FungusContainer extends PottedBlockContainer {
	public static Block.Settings Settings(MapColor mapColor) {
		return Block.Settings.of(Material.PLANT, mapColor).breakInstantly().noCollision().sounds(BlockSoundGroup.FUNGUS);
	}
	public FungusContainer(Supplier<ModConfiguredFeature<HugeFungusFeatureConfig, ?>> fungusGenerator, MapColor mapColor) {
		this(fungusGenerator, Settings(mapColor));
	}
	public FungusContainer(Supplier<ModConfiguredFeature<HugeFungusFeatureConfig, ?>> fungusGenerator, Block.Settings settings) {
		super(new ModFungusBlock(settings, fungusGenerator));
	}
}
