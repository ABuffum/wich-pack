package fun.mousewich.mixins.block;

import fun.mousewich.origins.powers.PowersUtil;
import fun.mousewich.origins.powers.TakeHoneyPower;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BeehiveBlock.class)
public abstract class BeehiveBlockMixin extends BlockWithEntity {
	protected BeehiveBlockMixin(Settings settings) { super(settings); }

	@ModifyVariable(method = "angerNearbyBees", at = @At("STORE"), index = 4)
	public List<PlayerEntity> angerNearbyBees$MobOrigins(List<PlayerEntity> list) {
		list.removeIf(entity -> PowersUtil.Active(entity, TakeHoneyPower.class));
		return list;
	}

	@Inject(method="onUse", at = @At("HEAD"), cancellable = true)
	private void OnUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		int i = state.get(BeehiveBlock.HONEY_LEVEL);
		if (i >= 5) {
			if (itemStack.getItem() instanceof ShearsItem shears) {
				world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.NEUTRAL, 1.0F, 1.0F);
				dropStack(world, pos, new ItemStack(Items.HONEYCOMB, 3));
				itemStack.damage(1, (LivingEntity)player, (p -> p.sendToolBreakStatus(hand)));
				world.emitGameEvent(player, GameEvent.SHEAR, pos);
				if (!world.isClient()) player.incrementStat(Stats.USED.getOrCreateStat(shears));
				BeehiveBlock bhb = (BeehiveBlock)(Object)this;
				BeehiveBlockInvoker bhbi = (BeehiveBlockInvoker)bhb;
				if (!CampfireBlock.isLitCampfireInRange(world, pos)) {
					if (bhbi.InvokeHasBees(world, pos)) bhbi.InvokeAngerNearbyBees(world, pos);
					bhb.takeHoney(world, state, pos, player, BeehiveBlockEntity.BeeState.EMERGENCY);
				}
				else bhb.takeHoney(world, state, pos);
				cir.setReturnValue(ActionResult.success(world.isClient));
			}
		}
	}
}
