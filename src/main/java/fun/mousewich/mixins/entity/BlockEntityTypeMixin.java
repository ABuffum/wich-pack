package fun.mousewich.mixins.entity;

import fun.mousewich.ModBase;
import fun.mousewich.block.basic.ModBedBlock;
import fun.mousewich.container.BedContainer;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
	@Inject(method="supports", at = @At("HEAD"), cancellable = true)
	private void supports(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		Block block = state.getBlock();
		if (BlockEntityType.SIGN.equals(this)) {
			if (block instanceof SignBlock sign) {
				if (ModBase.SIGN_TYPES.contains(sign.getSignType())) cir.setReturnValue(true);
			}
			else if (block instanceof WallSignBlock sign) {
				if (ModBase.SIGN_TYPES.contains(sign.getSignType())) cir.setReturnValue(true);
			}
		}
		else if (BlockEntityType.BED.equals(this)) {
			if (block instanceof ModBedBlock) cir.setReturnValue(true);
		}
		else if (BlockEntityType.CAMPFIRE.equals(this)) {
			if (block instanceof CampfireBlock) cir.setReturnValue(true);
		}
	}
}
