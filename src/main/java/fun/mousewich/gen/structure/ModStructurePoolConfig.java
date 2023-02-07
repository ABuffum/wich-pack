package fun.mousewich.gen.structure;

import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class ModStructurePoolConfig {
	public final ModStructurePool pool;
	public final int size;
	private StructurePoolFeatureConfig config;
	public ModStructurePoolConfig(ModStructurePool pool, int size) {
		this.pool = pool;
		this.size = size;
	}
	public StructurePoolFeatureConfig get() {
		if (config == null) Initialize();
		return config;
	}
	public void Initialize() {
		if (config != null) return;
		config = new StructurePoolFeatureConfig(pool.get(), size);
	}
}
