package fun.mousewich.mixins.client.render.model;

import com.google.common.collect.ImmutableList;
import fun.mousewich.client.render.entity.model.ExpandedModelPart;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.client.model.ModelCuboidData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mixin(ModelPartData.class)
public abstract class ModelPartDataMixin implements ExpandedModelPart {

    @Shadow
    @Final
    private List<ModelCuboidData> cuboidData;
    @Shadow
    @Final
    private Map<String, ModelPartData> children;
    @Shadow
    @Final
    private ModelTransform rotationData;

    /**
     * @author FrozenBlock
     * @reason modelparts
     */
	/*@Overwrite
	public ModelPart createPart(int textureWidth, int textureHeight) {
		Object2ObjectArrayMap<String, ModelPart> object2ObjectArrayMap = this.children.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, (entry) -> {
			return (entry.getValue()).createPart(textureWidth, textureHeight);
		}, (modelPartx, modelPart2) -> {
			return modelPartx;
		}, Object2ObjectArrayMap::new));
		List<ModelPart.Cuboid> list = this.cuboidData.stream().map((modelCuboidData) -> {
			return modelCuboidData.createCuboid(textureWidth, textureHeight);
		}).collect(ImmutableList.toImmutableList());
		ModelPart modelPart = new ModelPart(list, object2ObjectArrayMap);
		modelPart.traverse().forEach(modelPart1 -> ((ExpandedModelPart)modelPart1).resetTransform());
		return modelPart;
	}*/
    /**
     * @author mousewich
     * @reason I'm fighting for my life out here
     *//*
	@Overwrite
    public ModelPart createPart(int textureWidth, int textureHeight) {
        Object2ObjectArrayMap<String, ModelPart> object2ObjectArrayMap = this.children.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().createPart(textureWidth, textureHeight), (modelPart, modelPart2) -> modelPart, Object2ObjectArrayMap::new));
        List<ModelPart.Cuboid> list = this.cuboidData.stream().map(modelCuboidData -> modelCuboidData.createCuboid(textureWidth, textureHeight)).collect(ImmutableList.toImmutableList());
        ModelPart modelPart3 = new ModelPart(list, object2ObjectArrayMap);
        modelPart3.traverse().forEach(modelPart1 -> ((ExpandedModelPart)modelPart1).resetTransform());
        ((ExpandedModelPart)modelPart3).setDefaultTransform(this.rotationData);
        modelPart3.setTransform(this.rotationData);
        return modelPart3;
    }*/
    public abstract ModelPart traverse();
}
