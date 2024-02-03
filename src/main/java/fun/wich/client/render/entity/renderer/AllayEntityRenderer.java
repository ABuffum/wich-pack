package fun.wich.client.render.entity.renderer;

import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.AllayEntityModel;
import fun.wich.client.render.entity.renderer.feature.HeldItemFeatureRenderer;
import fun.wich.entity.passive.allay.AllayEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(value= EnvType.CLIENT)
public class AllayEntityRenderer extends MobEntityRenderer<AllayEntity, AllayEntityModel> {
	private static final Identifier TEXTURE = new Identifier("textures/entity/allay/allay.png");
	public AllayEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new AllayEntityModel(context.getPart(ModEntityModelLayers.ALLAY)), 0.4f);
		//this.addFeature(new HeldItemFeatureRenderer<AllayEntity, AllayEntityModel>(this, context.getHeldItemRenderer()));
		this.addFeature(new HeldItemFeatureRenderer<>(this, MinecraftClient.getInstance().getHeldItemRenderer()));
		//this.addFeature(new AllayFeatureRenderer(this));
	}
	@Override
	public Identifier getTexture(AllayEntity allayEntity) { return TEXTURE; }
	@Override
	protected int getBlockLight(AllayEntity allayEntity, BlockPos blockPos) { return 15; }
}
