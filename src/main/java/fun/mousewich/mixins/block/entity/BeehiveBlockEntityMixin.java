package fun.mousewich.mixins.block.entity;

import fun.mousewich.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeehiveBlockEntity.class)
public class BeehiveBlockEntityMixin {
	@Inject(method = "angerBees", at = @At("HEAD"), cancellable = true)
	public void angerBees$MobOrigins(@Nullable PlayerEntity player, BlockState state, BeehiveBlockEntity.BeeState beeState, CallbackInfo ci) {
		if (ModBase.TAKE_HONEY_POWER.isActive(player)) ci.cancel();
	}
}
