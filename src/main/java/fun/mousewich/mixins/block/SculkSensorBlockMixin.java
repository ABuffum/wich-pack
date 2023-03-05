package fun.mousewich.mixins.block;

import fun.mousewich.ModBase;
import fun.mousewich.block.sculk.ExtendedSculkEntity;
import fun.mousewich.event.ModVibrationListener;
import fun.mousewich.util.SculkUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SculkSensorBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkSensorBlock.class)
public abstract class SculkSensorBlockMixin extends BlockWithEntity implements Waterloggable {
	protected SculkSensorBlockMixin(Settings settings) { super(settings); }

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (!world.isClient() && SculkSensorBlock.isInactive(state) && entity.getType() != ModBase.WARDEN_ENTITY) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof ExtendedSculkEntity sculk) {
				sculk.setLastVibrationFrequency(1);
			}
			SculkSensorBlock.setActive(world, pos, state, 15);
		}
		super.onSteppedOn(world, pos, state, entity);
	}


	@Inject(method="getGameEventListener", at = @At("HEAD"), cancellable = true)
	public <T extends BlockEntity> void GetGameEventListener(World world, T blockEntity, CallbackInfoReturnable<@Nullable GameEventListener> cir) {
		if (blockEntity instanceof SculkSensorBlockEntity sculk) {
			ModVibrationListener.Callback mix = (ModVibrationListener.Callback)sculk;
			cir.setReturnValue(mix.getModEventListener());
		}
	}

	@Inject(method="getTicker", at = @At("HEAD"), cancellable = true)
	public <T extends BlockEntity> void GetTicker(World world2, BlockState state2, BlockEntityType<T> type, CallbackInfoReturnable<@Nullable BlockEntityTicker<T>> cir) {
		if (!world2.isClient) {
			if (type == BlockEntityType.SCULK_SENSOR) {
				BlockEntityTicker<T> ticker = (world, pos, state, blockEntity) -> {
					if (blockEntity instanceof ModVibrationListener.Callback mix) {
						mix.getModEventListener().tick(world);
					}
					else if (blockEntity instanceof SculkSensorBlockEntity sculk){
						sculk.getEventListener().tick(world);
					}
				};
				cir.setReturnValue(ticker);
				return;
			}
		}
		cir.setReturnValue(null);
	}


	@Inject(method="setActive", at = @At("HEAD"), cancellable = true)
	private static void SetActive(World world, BlockPos pos, BlockState state, int power, CallbackInfo ci) {
		SculkUtils.setActive(null, world, pos, state, power);
		ci.cancel();
	}

	@Override
	public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
		super.onStacksDropped(state, world, pos, stack);
		if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) this.dropExperience(world, pos, 5);
	}
}
