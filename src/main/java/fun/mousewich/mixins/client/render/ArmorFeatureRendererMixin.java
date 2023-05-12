package fun.mousewich.mixins.client.render;

import com.google.common.collect.Maps;
import fun.mousewich.ModBase;
import fun.mousewich.item.armor.ModDyeableArmorItem;
import fun.mousewich.material.ModArmorMaterials;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>>
		extends FeatureRenderer<T, M> {
	private static final Map<String, Identifier> MOD_ARMOR_TEXTURE_CACHE = Maps.newHashMap();

	public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context) { super(context); }

	@Inject(method="getArmorTexture", at = @At("HEAD"), cancellable = true)
	private void GetArmorTexture(ArmorItem item, boolean legs, String overlay, CallbackInfoReturnable<Identifier> cir) {
		ArmorMaterial material = item.getMaterial();
		if (material instanceof ModArmorMaterials) {
			String var10000 = item.getMaterial().getName();
			String string = "textures/models/armor/" + var10000 + "_layer_" + (legs ? 2 : 1) + (overlay == null ? "" : "_" + overlay) + ".png";
			cir.setReturnValue(MOD_ARMOR_TEXTURE_CACHE.computeIfAbsent(string, ModBase::ID));
		}
	}
}
