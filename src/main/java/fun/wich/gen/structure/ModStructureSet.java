package fun.wich.gen.structure;

import net.minecraft.structure.StructureSet;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;

public class ModStructureSet {
	public final RegistryKey<StructureSet> key;
	public final ModConfiguredStructure structure;
	public final StructurePlacement placement;
	private RegistryEntry<StructureSet> registryEntry;
	public ModStructureSet(RegistryKey<StructureSet> key, ModConfiguredStructure structure, StructurePlacement placement) {
		this.key = key;
		this.structure = structure;
		this.placement = placement;
	}
	public RegistryEntry<StructureSet> get() {
		if (registryEntry == null) Initialize();
		return registryEntry;
	}
	public void Initialize() {
		if (registryEntry != null) return;
		registryEntry = BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_SET, key, new StructureSet(structure.get(), placement));
	}
}
