package fun.mousewich.gen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fun.mousewich.gen.placer.MangroveRootPlacer;
import fun.mousewich.gen.placer.MangroveTrunkPlacer;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.size.FeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;

public class CherryTreeFeatureConfig implements FeatureConfig {
	public static final Codec<CherryTreeFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.BOOL.fieldOf("bees").forGetter(config -> config.bees))
			.apply(instance, CherryTreeFeatureConfig::new));
	public final boolean bees;
	public CherryTreeFeatureConfig(boolean bees) { this.bees = bees; }
}