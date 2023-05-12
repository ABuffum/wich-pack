package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.ModBase;
import fun.mousewich.ModClient;
import fun.mousewich.client.render.entity.model.FrozenZombieEntityModel;
import fun.mousewich.client.render.entity.renderer.feature.FrozenZombieOverlayFeatureRenderer;
import fun.mousewich.entity.hostile.zombie.FrozenZombieEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class FrozenZombieEntityRenderer extends ZombieBaseEntityRenderer<FrozenZombieEntity, FrozenZombieEntityModel> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/zombie/frozen.png");

	public FrozenZombieEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new FrozenZombieEntityModel(context.getPart(ModClient.FROZEN_ZOMBIE_LAYER)),
				new FrozenZombieEntityModel(context.getPart(ModClient.FROZEN_ZOMBIE_INNER_ARMOR_LAYER)),
				new FrozenZombieEntityModel(context.getPart(ModClient.FROZEN_ZOMBIE_OUTER_ARMOR_LAYER)));
		this.addFeature(new FrozenZombieOverlayFeatureRenderer(this, context.getModelLoader()));
	}

	@Override
	public Identifier getTexture(ZombieEntity zombieEntity) { return TEXTURE; }

	@Override
	protected void setupTransforms(FrozenZombieEntity entity, MatrixStack matrixStack, float f, float g, float h) {
		super.setupTransforms(entity, matrixStack, f, g, h);
		float i = entity.getLeaningPitch(h);
		if (i > 0) matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.lerp(i, entity.getPitch(), -10 - entity.getPitch())));
	}
}
