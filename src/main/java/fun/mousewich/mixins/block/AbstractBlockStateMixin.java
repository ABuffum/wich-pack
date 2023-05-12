package fun.mousewich.mixins.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import fun.mousewich.ModBase;
import fun.mousewich.block.decoration.*;
import net.minecraft.block.*;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin extends State<Block, BlockState> {
	protected AbstractBlockStateMixin(Block owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<BlockState> codec) { super(owner, entries, codec); }
	@Shadow
	public abstract Block getBlock();
	@Inject(method="isOf", at = @At("HEAD"), cancellable = true)
	public void isOf(Block block, CallbackInfoReturnable<Boolean> cir) {
		//TODO: Decorative Blocks
		/*
		if (block == Blocks.ACACIA_SAPLING) {
			if (ModBase.DECORATIVE_ACACIA_SAPLING.contains(getBlock())) cir.setReturnValue(true);
		}
		else if (block == Blocks.BEETROOTS) {
			if (ModBase.DECORATIVE_BEETROOTS.contains(getBlock())) cir.setReturnValue(true);
		}
		else if (block == Blocks.BIRCH_SAPLING) {
			if (ModBase.DECORATIVE_BIRCH_SAPLING.contains(getBlock())) cir.setReturnValue(true);
		}
		else if (block == BlocksRegistry.CABBAGE_CROP.get()) {
			if (ModBase.DECORATIVE_CABBAGES.contains(getBlock())) cir.setReturnValue(true);
		}
		else if (block == Blocks.CACTUS) {
			if (ModBase.DECORATIVE_CACTUS.contains(getBlock())) cir.setReturnValue(true);
		}
		else if (block == Blocks.CARROTS) {
			if (ModBase.DECORATIVE_CARROTS.contains(getBlock())) cir.setReturnValue(true);
		}
		else if (block == Blocks.DARK_OAK_SAPLING) {
			if (ModBase.DECORATIVE_DARK_OAK_SAPLING.contains(getBlock())) cir.setReturnValue(true);
		}
		else if (block == Blocks.JUNGLE_SAPLING) {
			if (ModBase.DECORATIVE_JUNGLE_SAPLING.contains(getBlock())) cir.setReturnValue(true);
		}
		else if (block == Blocks.OAK_SAPLING) {
			if (ModBase.DECORATIVE_OAK_SAPLING.contains(getBlock())) cir.setReturnValue(true);
		}
		else if (block == BlocksRegistry.ONION_CROP.get()) {
			if (ModBase.DECORATIVE_ONIONS.contains(getBlock())) cir.setReturnValue(true);
		}
		else if (block == Blocks.POTATOES) {
			if (ModBase.DECORATIVE_POTATOES.contains(getBlock())) cir.setReturnValue(true);
		}
		else if (block == Blocks.SPRUCE_SAPLING) {
			if (ModBase.DECORATIVE_SPRUCE_SAPLING.contains(getBlock())) cir.setReturnValue(true);
		}
		else if (block == Blocks.SUGAR_CANE) {
			if (ModBase.DECORATIVE_SUGAR_CANE.contains(getBlock())) cir.setReturnValue(true);
		}
		else if (block == Blocks.VINE) {
			if (ModBase.DECORATIVE_VINE.contains(getBlock())) cir.setReturnValue(true);
		}
		else if (block == Blocks.WHEAT) {
			if (ModBase.DECORATIVE_WHEAT.contains(getBlock())) cir.setReturnValue(true);
		}
		*/
	}
}
