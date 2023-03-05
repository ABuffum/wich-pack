package fun.mousewich.mixins.block.entity;

import fun.mousewich.ModBase;
import fun.mousewich.block.basic.ModBarrelBlock;
import fun.mousewich.block.basic.ModBedBlock;
import fun.mousewich.block.basic.ModCampfireBlock;
import fun.mousewich.block.basic.ModLecternBlock;
import fun.mousewich.block.sign.HangingSignBlock;
import fun.mousewich.block.sign.WallHangingSignBlock;
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
			if (block instanceof AbstractSignBlock sign) {
				if (ModBase.SIGN_TYPES.contains(sign.getSignType())) cir.setReturnValue(true);
				if (block instanceof HangingSignBlock) cir.setReturnValue(true);
				if (block instanceof WallHangingSignBlock) cir.setReturnValue(true);
			}
		}
		else if (BlockEntityType.BED.equals(this)) {
			if (block instanceof ModBedBlock) cir.setReturnValue(true);
		}
		else if (BlockEntityType.CAMPFIRE.equals(this)) {
			if (block instanceof ModCampfireBlock) cir.setReturnValue(true);
		}
		else if (BlockEntityType.BARREL.equals(this)) {
			if (block instanceof ModBarrelBlock) cir.setReturnValue(true);
		}
		else if (BlockEntityType.LECTERN.equals(this)) {
			if (block instanceof ModLecternBlock) cir.setReturnValue(true);
		}
	}
}
