package fun.wich.client.render.entity.renderer.feature.spider;

import fun.wich.ModId;
import fun.wich.client.render.entity.model.spider.BoneSpiderEntityModel;
import fun.wich.entity.hostile.spider.BoneSpiderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;

@Environment(value= EnvType.CLIENT)
public class BoneSpiderEyesFeatureRenderer extends EyesFeatureRenderer<BoneSpiderEntity, BoneSpiderEntityModel> {
	private static final RenderLayer SKIN = RenderLayer.getEyes(ModId.ID("textures/entity/spider/bone_spider_eyes.png"));
	public BoneSpiderEyesFeatureRenderer(FeatureRendererContext<BoneSpiderEntity, BoneSpiderEntityModel> featureRendererContext) {
		super(featureRendererContext);
	}
	@Override
	public RenderLayer getEyesTexture() { return SKIN; }
}