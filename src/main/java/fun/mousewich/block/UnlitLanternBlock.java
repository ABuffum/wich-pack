package fun.mousewich.block;

import fun.mousewich.ModBase;
import fun.mousewich.gen.data.loot.BlockLootGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class UnlitLanternBlock extends LanternBlock {
	protected Block lit;
	public Block getLitBlock() { return this.lit; }
	protected final ItemConvertible getPickStack;

	public UnlitLanternBlock(Block lit, ItemConvertible getPickStack, Settings settings) {
		super(settings);
		this.lit = lit;
		this.getPickStack = getPickStack;
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		boolean flint, lava = false;
		if ((flint = itemStack.isOf(Items.FLINT_AND_STEEL)) || (lava = itemStack.isOf(ModBase.LAVA_BOTTLE)) || itemStack.isOf(Items.FIRE_CHARGE)) {
			SoundEvent sound = flint ? SoundEvents.ITEM_FLINTANDSTEEL_USE : SoundEvents.ITEM_FIRECHARGE_USE;
			world.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
			world.setBlockState(pos, lit.getDefaultState().with(LanternBlock.HANGING, state.get(LanternBlock.HANGING)).with(LanternBlock.WATERLOGGED, state.get(LanternBlock.WATERLOGGED)));
			world.emitGameEvent(player, GameEvent.BLOCK_PLACE, pos);
			if (flint) itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
			else {
				itemStack.decrement(1);
				if (lava) player.giveItemStack(new ItemStack(ModBase.LAVA_BOTTLE.getRecipeRemainder()));
			}
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) { return getPickStack.asItem().getDefaultStack(); }


	public UnlitLanternBlock dropsLantern() {
		BlockLootGenerator.Drops.put(this, (block) -> BlockLootTableGenerator.drops(Items.LANTERN));
		return this;
	}
	public UnlitLanternBlock dropsSoulLantern() {
		BlockLootGenerator.Drops.put(this, (block) -> BlockLootTableGenerator.drops(Items.SOUL_LANTERN));
		return this;
	}
}
