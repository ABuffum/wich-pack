package fun.mousewich.mixins.client.render.entity;

import fun.mousewich.entity.variants.GoatVariant;
import net.minecraft.client.render.entity.GoatEntityRenderer;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GoatEntityRenderer.class)
public class GoatEntityRendererMixin {
	@Inject(method="getTexture(Lnet/minecraft/entity/passive/GoatEntity;)Lnet/minecraft/util/Identifier;", at=@At("HEAD"), cancellable=true)
	public void getTexture(GoatEntity entity, CallbackInfoReturnable<Identifier> cir) {
		GoatVariant variant = GoatVariant.get(entity);
		if (variant != null) cir.setReturnValue(variant.texture);
	}
}
