package fun.wich.container;

import fun.wich.block.basic.ModFungusBlock;
import fun.wich.gen.feature.ModConfiguredFeature;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.TagKey;
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
	//Tags
	public FungusContainer blockTag(TagKey<Block> tag) { return (FungusContainer)super.blockTag(tag); }
	public FungusContainer itemTag(TagKey<Item> tag) { return (FungusContainer)super.itemTag(tag); }
}
