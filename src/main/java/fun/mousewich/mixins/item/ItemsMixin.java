package fun.mousewich.mixins.item;

import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Items.class)
public class ItemsMixin {
	@Inject(method="createEmptyOptional", at = @At("HEAD"), cancellable = true)
	private static <T> void CreateEmptyOptional(T of, CallbackInfoReturnable<Optional<T>> cir) {
		//Placeholder to force Sculk Sensors into the creative menu
		if (of != null && of.equals(ItemGroup.REDSTONE)) cir.setReturnValue(Optional.of(of));
	}
}
