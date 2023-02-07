package fun.mousewich.gen.structure;

import fun.mousewich.ModBase;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;

public class ModStructureProcessorList {
	public final Identifier identifier;
	public final StructureProcessorList structure;
	private RegistryEntry<StructureProcessorList> registryEntry;
	public ModStructureProcessorList(String id, StructureProcessorList structure) {
		this.identifier = ModBase.ID(id);
		this.structure = structure;
	}
	public RegistryEntry<StructureProcessorList> get() {
		if (registryEntry == null) Initialize();
		return registryEntry;
	}
	public void Initialize() {
		if (registryEntry != null) return;
		registryEntry = BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_PROCESSOR_LIST, identifier, structure);
	}
}