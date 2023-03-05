package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.ModBase;
import fun.mousewich.entity.projectile.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class ModArrowEntityRenderer extends ProjectileEntityRenderer<ModArrowEntity> {
	public static final Identifier AMETHYST_TEXTURE = ModBase.ID("textures/entity/projectiles/amethyst_arrow.png");
	public static final Identifier CHORUS_TEXTURE = ModBase.ID("textures/entity/projectiles/chorus_arrow.png");
	public ModArrowEntityRenderer(EntityRendererFactory.Context context) { super(context); }
	@Override
	public Identifier getTexture(ModArrowEntity entity) {
		if (entity instanceof AmethystArrowEntity) return AMETHYST_TEXTURE;
		else if (entity instanceof ChorusArrowEntity) return CHORUS_TEXTURE;
		else return ArrowEntityRenderer.TEXTURE;
	}
}