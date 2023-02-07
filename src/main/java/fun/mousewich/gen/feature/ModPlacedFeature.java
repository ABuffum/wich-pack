package fun.mousewich.gen.feature;

import fun.mousewich.ModBase;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

import static fun.mousewich.ModBase.ID;

public abstract class ModPlacedFeature {
	protected final Identifier identifier;
	protected final ModConfiguredFeature<?,?> configuredFeature;
	@SuppressWarnings("FieldMayBeFinal")
	protected RegistryEntry<PlacedFeature> placedFeature = null;
	public ModPlacedFeature(String id, ModConfiguredFeature<?,?> configuredFeature) { this(ModBase.ID(id), configuredFeature); }
	public ModPlacedFeature(Identifier identifier, ModConfiguredFeature<?,?> configuredFeature) {
		this.identifier = identifier;
		this.configuredFeature = configuredFeature;
	}

	public RegistryEntry<PlacedFeature> getRegistryEntry() {
		if (placedFeature == null) Initialize();
		return placedFeature;
	}
	public void Initialize() {
		if (placedFeature != null) return;
		this.placedFeature = PlacedFeatures.register(identifier.toString(), configuredFeature.getRegistryEntry(), getModifiers());
	}
	public abstract List<PlacementModifier> getModifiers();
}
