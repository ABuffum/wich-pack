package fun.mousewich.gen.structure;

import com.mojang.datafixers.util.Pair;
import fun.mousewich.ModId;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;

import java.util.List;
import java.util.function.Function;

public class ModStructurePool {
	public final Identifier identifier;
	public final List<ModPoolPair> elementCounts;
	public final StructurePool.Projection projection;
	private RegistryEntry<StructurePool> registryEntry;
	public ModStructurePool(String id, List<ModPoolPair> elementCounts) {
		this(id, elementCounts, StructurePool.Projection.RIGID);
	}
	public ModStructurePool(String id, List<ModPoolPair> elementCounts, StructurePool.Projection projection) {
		this.identifier = ModId.ID(id);
		this.elementCounts = elementCounts;
		this.projection = projection;
	}
	public RegistryEntry<StructurePool> get() {
		if (registryEntry == null) Initialize();
		return registryEntry;
	}
	public void Initialize() {
		if (registryEntry != null) return;
		List<Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer>> elements =
				elementCounts.stream().map(ModPoolPair::get).toList();
		registryEntry = StructurePools.register(new StructurePool(identifier, new Identifier("empty"), elements, projection));
	}
}
