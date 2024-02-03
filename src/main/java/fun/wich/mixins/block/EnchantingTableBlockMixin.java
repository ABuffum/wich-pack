package fun.wich.mixins.block;

import fun.wich.gen.data.tag.ModBlockTags;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {
	@Inject(method="canAccessBookshelf", at = @At("HEAD"), cancellable = true)
	private static void CanAccessBookshelf(World world, BlockPos tablePos, BlockPos bookshelfOffset, CallbackInfoReturnable<Boolean> cir) {
		if (world.getBlockState(tablePos.add(bookshelfOffset)).isIn(ModBlockTags.BOOKSHELVES)) {
			cir.setReturnValue(world.isAir(tablePos.add(bookshelfOffset.getX() / 2, bookshelfOffset.getY(), bookshelfOffset.getZ() / 2)));
		}
	}
}
