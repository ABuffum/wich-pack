package fun.mousewich.block.torch;

import fun.mousewich.util.WaterBottleUtil;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class LightableTorchBlock extends TorchBlock {
	public LightableTorchBlock(Settings settings, ParticleEffect particle) {
		super(settings, particle);
		this.setDefaultState(this.stateManager.getDefaultState().with(Properties.LIT, true));
	}

	@Override
	public boolean hasRandomTicks(BlockState state) { return state.get(Properties.LIT); }

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(Properties.LIT); }

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(Properties.LIT)) super.randomDisplayTick(state, world, pos, random);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.FLINT_AND_STEEL)) {
			if (!state.get(Properties.LIT)) {
				world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
				world.setBlockState(pos, state.with(Properties.LIT, true));
				world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
				itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
				return ActionResult.SUCCESS;
			}
		}
		else if (itemStack.isOf(Items.POTION) && PotionUtil.getPotion(itemStack) == Potions.WATER) {
			if (state.get(Properties.LIT)) {
				WaterBottleUtil.useOnLightable(state, world, pos, player, itemStack, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT);
				return ActionResult.SUCCESS;
			}
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
