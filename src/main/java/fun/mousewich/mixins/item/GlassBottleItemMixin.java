package fun.mousewich.mixins.item;

import fun.mousewich.ModBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlassBottleItem.class)
public abstract class GlassBottleItemMixin extends Item {
	private GlassBottleItemMixin(Settings settings) { super(settings); }

	@Shadow
	protected abstract ItemStack fill(ItemStack itemStack, PlayerEntity playerEntity, ItemStack itemStack2);

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		//Fill bottle with Blood
		BlockHitResult hitResult = Item.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
		BlockPos blockPos = hitResult.getBlockPos();
		FluidState state = world.getFluidState(blockPos);
		ItemStack stack = user.getStackInHand(hand);
		ItemStack newStack = null;
		//Fill bottle with Lava
		if (state.getFluid() == Fluids.LAVA || state.getFluid() == Fluids.FLOWING_LAVA) {
			newStack = new ItemStack(ModBase.LAVA_BOTTLE);
		}
		if (newStack != null) {
			PlayerInventory inventory = user.getInventory();
			if (!user.getAbilities().creativeMode) stack.decrement(1);
			if (stack.isEmpty()) user.setStackInHand(hand, newStack);
			else if (inventory.getEmptySlot() > 0) inventory.insertStack(newStack);
			else user.dropItem(newStack, false);
			world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			cir.setReturnValue(TypedActionResult.success(user.getStackInHand(hand)));
		}
	}
}
