package fun.mousewich.mixins.item;

import fun.mousewich.item.OxidizableItem;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Shadow public abstract int getMaxDamage();

	@Shadow public abstract int getDamage();

	@Shadow public abstract Item getItem();

	@Shadow @Final @Deprecated private Item item;

	@Inject(method="isOf", at = @At("HEAD"), cancellable = true)
	public void isOf(Item item, CallbackInfoReturnable<Boolean> cir) {
		if (item == Items.FISHING_ROD) {
			if (((ItemStack)(Object)this).getItem() instanceof FishingRodItem) cir.setReturnValue(true);
		}
		else if (item == Items.SHEARS) {
			if (((ItemStack)(Object)this).getItem() instanceof ShearsItem) cir.setReturnValue(true);
		}
	}
}
