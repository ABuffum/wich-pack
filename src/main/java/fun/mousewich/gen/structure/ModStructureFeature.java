package fun.mousewich.gen.structure;

import fun.mousewich.ModBase;
import fun.mousewich.mixins.world.StructureFeatureInvoker;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class ModStructureFeature {
	public final Identifier identifier;
	public final JigsawFeature jigsawFeature;
	private StructureFeature<StructurePoolFeatureConfig> feature;
	public ModStructureFeature(String id, JigsawFeature jigsawFeature) {
		this.identifier = ModBase.ID(id);
		this.jigsawFeature = jigsawFeature;
	}

	public StructureFeature<StructurePoolFeatureConfig> get() {
		if (feature == null) Initialize();
		return feature;
	}

	public void Initialize() {
		if (feature != null) return;
		feature = StructureFeatureInvoker.Register(identifier.toString(), jigsawFeature, GenerationStep.Feature.SURFACE_STRUCTURES);
	}
}
