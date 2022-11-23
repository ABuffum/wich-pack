package fun.mousewich.block.torch;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class LightableWallTorchBlock extends WallTorchBlock {
	public LightableWallTorchBlock(Settings settings, ParticleEffect particleEffect) {
		super(settings, particleEffect);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(Properties.LIT, true));
	}

	@Override
	public boolean hasRandomTicks(BlockState state) { return state.get(Properties.LIT); }

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING))).with(Properties.LIT, state.get(Properties.LIT));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(FACING, Properties.LIT); }

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.FLINT_AND_STEEL)) {
			if (!state.get(Properties.LIT)) {
				world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
				world.setBlockState(pos, state.with(Properties.LIT, true));
				world.emitGameEvent(player, GameEvent.BLOCK_PLACE, pos);
				itemStack.damage(1, (LivingEntity)player, (p) -> p.sendToolBreakStatus(hand));
				return ActionResult.SUCCESS;
			}
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
