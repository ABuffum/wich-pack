package fun.wich.mixins.block.entity;

import fun.wich.ModBase;
import fun.wich.block.basic.*;
import fun.wich.block.container.ChiseledBookshelfBlock;
import fun.wich.block.dust.Brushable;
import fun.wich.block.dust.BrushableEntity;
import fun.wich.block.sign.HangingSignBlock;
import fun.wich.block.sign.WallHangingSignBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.SignType;
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
				SignType type = sign.getSignType();
				if (ModBase.SIGN_TYPES.contains(type) || ModBase.HANGING_SIGN_SUBTYPES.containsKey(type)) cir.setReturnValue(true);
				if (block instanceof HangingSignBlock) cir.setReturnValue(true);
				if (block instanceof WallHangingSignBlock) cir.setReturnValue(true);
			}
		}
		else if (BlockEntityType.BEEHIVE.equals(this)) {
			if (block instanceof ModBeehiveBlock) cir.setReturnValue(true);
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
		else if (ModBase.CHISELED_BOOKSHELF_ENTITY.equals(this)) {
			if (block instanceof ChiseledBookshelfBlock) cir.setReturnValue(true);
		}
		else if (this instanceof BrushableEntity) {
			if (block instanceof Brushable) cir.setReturnValue(true);
		}
	}
}
