package fun.mousewich.util;

import fun.mousewich.ModBase;
import fun.mousewich.gen.data.tag.ModBlockTags;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class WaterBottleUtil {
	public static ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		PlayerEntity playerEntity = context.getPlayer();
		ItemStack itemStack = context.getStack();
		BlockState blockState = world.getBlockState(blockPos);
		if (PotionUtil.getPotion(itemStack) == Potions.WATER) {
			Block block = blockState.getBlock();
			BlockState outState = blockState;
			boolean bl = true, consume = false, first;
			SoundEvent sound = SoundEvents.ENTITY_GENERIC_SPLASH;
			if ((first = block == Blocks.TORCH) || block == Blocks.SOUL_TORCH) {
				outState = (first ? ModBase.UNLIT_TORCH : ModBase.UNLIT_SOUL_TORCH).asBlock().getDefaultState();
				sound = SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT;
				if (block instanceof Waterloggable) outState = outState.with(Properties.WATERLOGGED, blockState.get(Properties.WATERLOGGED));
			}
			else if ((first = block == Blocks.WALL_TORCH) || block == Blocks.SOUL_WALL_TORCH) {
				outState = (first ? ModBase.UNLIT_TORCH : ModBase.UNLIT_SOUL_TORCH).getWallBlock().getDefaultState()
						.with(WallTorchBlock.FACING, blockState.get(WallTorchBlock.FACING));
				sound = SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT;
				if (block instanceof Waterloggable) outState = outState.with(Properties.WATERLOGGED, blockState.get(Properties.WATERLOGGED));
			}
			else if ((first = block == Blocks.LANTERN) || block == Blocks.SOUL_LANTERN) {
				outState = (first ? ModBase.UNLIT_LANTERN : ModBase.UNLIT_SOUL_LANTERN).getDefaultState()
						.with(LanternBlock.HANGING, blockState.get(LanternBlock.HANGING)).with(LanternBlock.WATERLOGGED, blockState.get(LanternBlock.WATERLOGGED));
			}
			else if (block instanceof AbstractCandleBlock && blockState.get(AbstractCandleBlock.LIT)) {
				AbstractCandleBlock.extinguish(playerEntity, blockState, world, blockPos);
				return ActionResult.success(world.isClient);
			}
			else if (block instanceof CampfireBlock && blockState.get(CampfireBlock.LIT)) {
				CampfireBlock.extinguish(playerEntity, world, blockPos, blockState);
				outState = blockState.with(CampfireBlock.LIT, false);
				consume = true;
			}
			else if (context.getSide() != Direction.DOWN && blockState.isIn(ModBlockTags.CONVERTIBLE_TO_MUD)) {
				outState = ModBase.MUD.asBlock().getDefaultState();
				consume = true;
			}
			else bl = false;
			if (bl) {
				if (sound != null) world.playSound(null, blockPos, sound, SoundCategory.PLAYERS, 1.0f, 1.0f);
				if (playerEntity != null) {
					if (consume) {
						if (!playerEntity.getAbilities().creativeMode) context.getStack().decrement(1);
						playerEntity.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
					}
					playerEntity.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
				}
				if (!world.isClient) {
					ServerWorld serverWorld = (ServerWorld)world;
					for (int i = 0; i < 5; ++i) {
						serverWorld.spawnParticles(ParticleTypes.SPLASH, blockPos.getX() + world.random.nextDouble(), blockPos.getY() + 1, blockPos.getZ() + world.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
					}
				}
				if (consume) world.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, blockPos);
				world.setBlockState(blockPos, outState);
				return ActionResult.success(world.isClient);
			}
		}
		return ActionResult.PASS;
	}
}
