package fun.wich.gen.structure;

import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;

public class ModConfiguredStructure {
	public final RegistryKey<ConfiguredStructureFeature<?, ?>> key;
	public final ModStructureFeature feature;
	public final ModStructurePoolConfig config;
	public final TagKey<Biome> biomeTag;
	private RegistryEntry<ConfiguredStructureFeature<?, ?>> registryEntry;
	public ModConfiguredStructure(RegistryKey<ConfiguredStructureFeature<?, ?>> key, ModStructureFeature feature, ModStructurePoolConfig config, TagKey<Biome> biomeTag) {
		this.key = key;
		this.feature = feature;
		this.config = config;
		this.biomeTag = biomeTag;
	}
	public RegistryEntry<ConfiguredStructureFeature<?, ?>> get() {
		if (registryEntry == null) Initialize();
		return registryEntry;
	}
	public void Initialize() {
		if (registryEntry != null) return;
		ConfiguredStructureFeature<?,?> structure = feature.get().configure(config.get(), biomeTag);
		registryEntry = BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, key, structure);
	}
}
