package fun.wich.mixins.client;

import java.util.Collections;
import java.util.List;

import fun.wich.entity.ModNbtKeys;
import fun.wich.util.dye.ModDyeColor;
import fun.wich.util.banners.ModBannerPatternContainer;
import fun.wich.util.banners.ModBannerPatternConversions;
import fun.wich.util.banners.ModBannerPatternData;
import fun.wich.util.dye.ModDyedBanner;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

@Mixin(BannerBlockEntity.class)
public abstract class BannerBlockEntityClientMixin extends BlockEntity implements ModBannerPatternContainer, ModDyedBanner {
	@Shadow private List<?> patterns;

	public BannerBlockEntityClientMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) { super(type, pos, state); }

	@Shadow public abstract void readFrom(ItemStack stack);

	@Unique private List<ModBannerPatternData> modBannerPatterns = Collections.emptyList();
	private ModDyeColor modDyeBaseColor;



	@Override
	public List<ModBannerPatternData> getModBannerPatterns() {
		if (this.patterns == null) {
			NbtList nbt = ((ModBannerPatternContainer.MixinHelper)this).getModBannerPatternsNbt();
			modBannerPatterns = ModBannerPatternConversions.makeModBannerPatternData(nbt);
		}
		return Collections.unmodifiableList(modBannerPatterns);
	}

	@Inject(method = "readFrom(Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DyeColor;)V", at = @At("RETURN"))
	private void ReadPatternFromItemStack(ItemStack stack, DyeColor color, CallbackInfo info) {
		((MixinHelper)this).setModBannerPatternsNbt(ModBannerPatternConversions.getModBannerPatternNbt(stack));
	}

	@Override
	public void ReadFrom(ItemStack stack, ModDyeColor baseColor) {
		this.modDyeBaseColor = baseColor;
		this.readFrom(stack);
		((MixinHelper)this).setModBannerPatternsNbt(ModBannerPatternConversions.getModBannerPatternNbt(stack));
	}

	@Inject(method = "getPickStack", at = @At("RETURN"))
	private void PutPatternsInPickStack(CallbackInfoReturnable<ItemStack> info) {
		NbtList nbt = ((MixinHelper)this).getModBannerPatternsNbt();
		if (nbt != null) info.getReturnValue().getOrCreateSubNbt(ModNbtKeys.BLOCK_ENTITY_TAG).put(ModNbtKeys.MOD_BANNER_PATTERNS, nbt);
	}
}
