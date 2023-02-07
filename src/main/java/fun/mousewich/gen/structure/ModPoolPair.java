package fun.mousewich.gen.structure;

import com.mojang.datafixers.util.Pair;
import fun.mousewich.ModBase;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModPoolPair {
	public final Identifier identifier;
	public final ModStructureProcessorList processor;
	public final int count;
	private Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer> pair;
	public ModPoolPair(String id, ModStructureProcessorList processor, int count) {
		this.identifier = ModBase.ID(id);
		this.processor = processor;
		this.count = count;
	}
	public Pair<Function<StructurePool.Projection, ? extends StructurePoolElement>, Integer> get() {
		if (pair == null) Initialize();
		return pair;
	}
	public void Initialize() {
		if (pair != null) Initialize();
		pair = Pair.of(StructurePoolElement.ofProcessedSingle(identifier.toString(), processor.get()), count);
	}
}
