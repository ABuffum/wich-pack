package fun.mousewich.client.render.entity.renderer.zombie;

import fun.mousewich.ModBase;
import fun.mousewich.entity.hostile.zombie.JungleZombieEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public class JungleZombieEntityRenderer extends LayeredZombieEntityRenderer<JungleZombieEntity> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/zombie/jungle.png");
	private static final Identifier SKIN = ModBase.ID("textures/entity/zombie/jungle_outer_layer.png");
	public JungleZombieEntityRenderer(EntityRendererFactory.Context context) { super(context, SKIN); }
	@Override
	public Identifier getTexture(ZombieEntity zombieEntity) { return TEXTURE; }
}