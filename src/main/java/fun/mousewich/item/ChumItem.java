package fun.mousewich.item;

import fun.mousewich.ModBase;
import fun.mousewich.block.sculk.SculkBlock;
import fun.mousewich.block.sculk.SculkTurfBlock;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class ChumItem extends Item {
	public ChumItem(Settings settings) { super(settings); }

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState blockState = world.getBlockState(pos);
		Block block = blockState.getBlock();
		PlayerEntity player = context.getPlayer();
		if (block instanceof SculkBlock || block instanceof SculkTurfBlock) {
			if (block instanceof SculkTurfBlock) {
				if (context.getSide() != Direction.UP) return ActionResult.PASS;
			}
			Random random = world.getRandom();
			if (random.nextFloat() < 0.05f) {
				int selection = random.nextInt(3);
				ItemStack stack = new ItemStack(ModBase.SCULK_VEIN);
				if (selection == 0) stack = new ItemStack(ModBase.SCULK);
				world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack));
			}
			world.playSound(null, pos, ModSoundEvents.ITEM_CHUM_USED, SoundCategory.BLOCKS, 1, 1);
			if (!player.getAbilities().creativeMode) {
				context.getStack().decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}
}
