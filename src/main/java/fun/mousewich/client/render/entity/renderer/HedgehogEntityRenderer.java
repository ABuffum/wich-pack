package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.ModId;
import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.entity.passive.HedgehogEntity;
import fun.mousewich.client.render.entity.model.HedgehogEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class HedgehogEntityRenderer extends MobEntityRenderer<HedgehogEntity, HedgehogEntityModel> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/hedgehog/hedgehog.png");
	private static final Identifier SONIC_TEXTURE = ModId.ID("textures/entity/hedgehog/sonic.png");
	private static final Identifier SHADOW_TEXTURE = ModId.ID("textures/entity/hedgehog/shadow.png");

	public HedgehogEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new HedgehogEntityModel(context.getPart(ModEntityModelLayers.HEDGEHOG)), 0.3F);
	}

	public Identifier getTexture(HedgehogEntity entity) {
		String name = entity.getName().getString();
		if (name.equals("Sonic")) return SONIC_TEXTURE;
		else if (name.equals("Shadow")) return SHADOW_TEXTURE;
		return TEXTURE;
	}
}
