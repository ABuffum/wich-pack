package fun.mousewich.mixins.client.render;

import fun.mousewich.ModBase;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {
	@Inject(method = "createSignTextureId", at = @At("HEAD"), cancellable = true)
	private static void RetextureSigns(SignType type, CallbackInfoReturnable<SpriteIdentifier> cir) {
		if (ModBase.SIGN_TYPES.contains(type)) {
			String name = type.getName();
			Identifier id = ModBase.ID(name.startsWith("minecraft:") ? "minecraft:entity/signs/" + name.substring("minecraft:".length()) : "entity/signs/" + name);
			cir.setReturnValue(new SpriteIdentifier(ModBase.ID("textures/atlas/signs.png"), id));
		}
	}
}
