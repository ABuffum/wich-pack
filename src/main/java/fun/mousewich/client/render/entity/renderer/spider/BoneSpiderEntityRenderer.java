package fun.mousewich.client.render.entity.renderer.spider;

import fun.mousewich.ModBase;
import fun.mousewich.ModClient;
import fun.mousewich.client.render.entity.model.BoneSpiderEntityModel;
import fun.mousewich.client.render.entity.renderer.feature.BoneSpiderEyesFeatureRenderer;
import fun.mousewich.entity.hostile.spider.BoneSpiderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class BoneSpiderEntityRenderer extends MobEntityRenderer<BoneSpiderEntity, BoneSpiderEntityModel> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/spider/bone_spider.png");
	public BoneSpiderEntityRenderer(EntityRendererFactory.Context context) { this(context, ModClient.BONE_SPIDER_MODEL_LAYER); }
	public BoneSpiderEntityRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer) {
		super(ctx, new BoneSpiderEntityModel(ctx.getPart(layer)), 0.8f);
		this.addFeature(new BoneSpiderEyesFeatureRenderer(this));
	}
	@Override
	protected float getLyingAngle(BoneSpiderEntity entity) { return 180.0f; }
	@Override
	public Identifier getTexture(BoneSpiderEntity entity) { return TEXTURE; }
}