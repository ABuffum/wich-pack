package fun.mousewich.mixins.block.entity;

import fun.mousewich.event.ModGameEvent;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxBlock.class)
public class JukeboxBlockMixin {
	@Inject(method="removeRecord", at = @At("HEAD"))
	private void RemoveRecord(World world, BlockPos pos, CallbackInfo ci) {
		if (world.isClient) return;
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (!(blockEntity instanceof JukeboxBlockEntity jukeboxBlockEntity)) return;
		ItemStack itemStack = jukeboxBlockEntity.getRecord();
		if (itemStack.isEmpty()) return;
		world.emitGameEvent(ModGameEvent.JUKEBOX_STOP_PLAY, pos);
	}
}
