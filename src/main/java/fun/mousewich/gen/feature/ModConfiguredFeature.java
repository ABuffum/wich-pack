package fun.mousewich.gen.feature;

import fun.mousewich.ModId;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.function.Supplier;

public class ModConfiguredFeature<FC extends FeatureConfig, F extends Feature<FC>> {
	protected final Identifier identifier;
	protected ConfiguredFeature<FC, F> feature;
	protected Supplier<ConfiguredFeature<FC, F>> featureSupplier;
	protected RegistryEntry<ConfiguredFeature<FC, F>> registryEntry = null;
	public ModConfiguredFeature(String id, ConfiguredFeature<FC, F> feature) { this(ModId.ID(id), feature); }
	public ModConfiguredFeature(Identifier identifier, ConfiguredFeature<FC, F> feature) {
		this.identifier = identifier;
		this.feature = feature;
	}
	public ModConfiguredFeature(String id, Supplier<ConfiguredFeature<FC, F>> featureSupplier) { this(ModId.ID(id), featureSupplier); }
	public ModConfiguredFeature(Identifier identifier, Supplier<ConfiguredFeature<FC, F>> featureSupplier) {
		this.identifier = identifier;
		this.featureSupplier = featureSupplier;
	}
	public RegistryEntry<ConfiguredFeature<FC, F>> getRegistryEntry() {
		if (this.registryEntry == null) Initialize();
		return this.registryEntry;
	}
	public ConfiguredFeature<FC, F> getFeature() {
		if (this.registryEntry == null) Initialize();
		return this.feature;
	}
	public void Initialize() {
		if (this.registryEntry != null) return;
		if (this.feature == null) this.feature = this.featureSupplier.get();
		this.registryEntry = BuiltinRegistries.method_40360(BuiltinRegistries.CONFIGURED_FEATURE, this.identifier.toString(), this.feature);
	}
}
