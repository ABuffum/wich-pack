package fun.mousewich.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class LightableLanternBlock extends LanternBlock {
	public LightableLanternBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(HANGING, false).with(WATERLOGGED, false).with(Properties.LIT, true));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(HANGING, WATERLOGGED, Properties.LIT); }

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.FLINT_AND_STEEL)) {
			if (!state.get(Properties.LIT)) {
				world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
				world.setBlockState(pos, state.with(Properties.LIT, true));
				world.emitGameEvent(player, GameEvent.BLOCK_PLACE, pos);
				itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
				return ActionResult.SUCCESS;
			}
		}
		else if (itemStack.isOf(Items.POTION)) {
			if (state.get(Properties.LIT)) {
				world.playSound(player, pos, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
				world.setBlockState(pos, state.with(Properties.LIT, false));
				world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
				if (!world.isClient) {
					ServerWorld serverWorld = (ServerWorld)world;
					for (int i = 0; i < 5; ++i) {
						serverWorld.spawnParticles(ParticleTypes.SPLASH, pos.getX() + world.random.nextDouble(), pos.getY() + 1, pos.getZ() + world.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
					}
				}
				return ActionResult.SUCCESS;
			}
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
