package fun.mousewich.mixins.item;

import fun.mousewich.ModBase;
import fun.mousewich.block.sculk.SculkBlock;
import fun.mousewich.block.sculk.SculkTurfBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
	@Inject(method="useOnBlock", at = @At("HEAD"), cancellable = true)
	public void UseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		//Get more spore blossoms by bone mealing a spore blossom
		if (state.isOf(Blocks.SPORE_BLOSSOM)) {
			world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new ItemStack(Items.SPORE_BLOSSOM, 1)));
			world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);
			if (!context.getPlayer().getAbilities().creativeMode) context.getStack().decrement(1);
			cir.setReturnValue(ActionResult.SUCCESS);
		}
		//Try spreading sculk turf
		else if (world.getBlockState(pos.up()).isTranslucent(world, pos)) {
			SculkTurfBlock turf = SculkTurfBlock.getSculkTurf(block);
			if (turf != null) {
				for (BlockPos blockPos : BlockPos.iterate(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
					Block b = world.getBlockState(blockPos).getBlock();
					if (b instanceof SculkTurfBlock || b instanceof SculkBlock) {
						world.setBlockState(pos, turf.getDefaultState(), Block.NOTIFY_ALL);
						break;
					}
				}
			}
		}
	}
}