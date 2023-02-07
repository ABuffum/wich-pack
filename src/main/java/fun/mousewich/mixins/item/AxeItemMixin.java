package fun.mousewich.mixins.item;

import com.google.common.collect.BiMap;
import fun.mousewich.util.OxidationScale;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static fun.mousewich.ModBase.*;

@Mixin(AxeItem.class)
public class AxeItemMixin {
	@SuppressWarnings("ShadowModifiers")
	@Shadow
	protected static Map<Block, Block> STRIPPED_BLOCKS;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void AddStrippedBlocks(CallbackInfo ci) {
		STRIPPED_BLOCKS = new HashMap<>(STRIPPED_BLOCKS);
		//Add our own stripped blocks
		STRIPPED_BLOCKS.put(MANGROVE_LOG.asBlock(), STRIPPED_MANGROVE_LOG.asBlock());
		STRIPPED_BLOCKS.put(MANGROVE_WOOD.asBlock(), STRIPPED_MANGROVE_WOOD.asBlock());
		STRIPPED_BLOCKS.put(BAMBOO_BLOCK.asBlock(), STRIPPED_BAMBOO_BLOCK.asBlock());
	}

	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	private void UseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);
		PlayerEntity playerEntity = context.getPlayer();
		ItemStack itemStack = context.getStack();
		//Axe Scrape
		Optional<BlockState> optional2 = OxidationScale.getDecreasedOxidationState(blockState);
		//Wax Off
		Optional<BlockState> optional3 = Optional.ofNullable((Block)((BiMap<?, ?>)OxidationScale.WaxedToUnwaxedBlocks().get()).get(blockState.getBlock())).map((b) -> b.getStateWithProperties(blockState));
		Optional<BlockState> optional4 = Optional.empty();
		//Axe Scrape
		if (optional2.isPresent()) {
			world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.syncWorldEvent(playerEntity, WorldEvents.BLOCK_SCRAPED, blockPos, 0);
			optional4 = optional2;
		}
		//Wax Off
		else if (optional3.isPresent()) {
			world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.syncWorldEvent(playerEntity, WorldEvents.WAX_REMOVED, blockPos, 0);
			optional4 = optional3;
		}
		if (optional4.isPresent()) {
			if (playerEntity instanceof ServerPlayerEntity) Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
			world.setBlockState(blockPos, optional4.get(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
			if (playerEntity != null) itemStack.damage(1, playerEntity, (p -> p.sendToolBreakStatus(context.getHand())));
			cir.setReturnValue(ActionResult.success(world.isClient));
		}
	}

	@Shadow private Optional<BlockState> getStrippedState(BlockState state) { return null; }
}
