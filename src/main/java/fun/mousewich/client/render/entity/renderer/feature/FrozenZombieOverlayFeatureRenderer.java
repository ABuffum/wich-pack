package fun.mousewich.client.render.entity.renderer.feature;

import fun.mousewich.ModBase;
import fun.mousewich.ModClient;
import fun.mousewich.client.render.entity.model.FrozenZombieEntityModel;
import fun.mousewich.entity.hostile.zombie.FrozenZombieEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class FrozenZombieOverlayFeatureRenderer extends FeatureRenderer<FrozenZombieEntity, FrozenZombieEntityModel> {
	private static final Identifier SKIN = ModBase.ID("textures/entity/zombie/frozen_outer_layer.png");
	private final FrozenZombieEntityModel model;

	public FrozenZombieOverlayFeatureRenderer(FeatureRendererContext<FrozenZombieEntity, FrozenZombieEntityModel> context, EntityModelLoader loader) {
		super(context);
		this.model = new FrozenZombieEntityModel(loader.getModelPart(ModClient.FROZEN_ZOMBIE_OUTER_LAYER));
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, FrozenZombieEntity entity, float f, float g, float h, float j, float k, float l) {
		render(this.getContextModel(), this.model, SKIN, matrixStack, vertexConsumerProvider, i, entity, f, g, j, k, l, h, 1.0f, 1.0f, 1.0f);
	}
}