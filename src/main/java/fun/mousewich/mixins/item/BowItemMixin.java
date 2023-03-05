package fun.mousewich.mixins.item;

import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BowItem.class)
public abstract class BowItemMixin {

	@Redirect(method = "onStoppedUsing", at = @At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
	private boolean AllowNonstandardArrows(ItemStack instance, Item item) {
		if (item == Items.SPECTRAL_ARROW) return instance.getItem() instanceof ArrowItem || instance.isOf(item);
		return instance.isOf(item);
	}
}
