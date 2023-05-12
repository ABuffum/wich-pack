package fun.mousewich.mixins.item;

import fun.mousewich.item.tool.ModShearsItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Shadow public abstract Item getItem();

	@Inject(method="isOf", at = @At("HEAD"), cancellable = true)
	public void isOf(Item item, CallbackInfoReturnable<Boolean> cir) {
		if (item == Items.FISHING_ROD) {
			if (this.getItem() instanceof FishingRodItem) cir.setReturnValue(true);
		}
		else if (item == Items.SHEARS) {
			if (this.getItem() instanceof ModShearsItem) cir.setReturnValue(true);
		}
	}
}
