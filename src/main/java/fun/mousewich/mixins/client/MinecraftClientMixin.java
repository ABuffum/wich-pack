package fun.mousewich.mixins.client;

import fun.mousewich.ModBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

	@Redirect(method="initializeSearchableContainers", at=@At(value="INVOKE", target="Lnet/minecraft/item/Item;appendStacks(Lnet/minecraft/item/ItemGroup;Lnet/minecraft/util/collection/DefaultedList;)V"))
	private void CallOutNullBlockItemBlocks(Item instance, ItemGroup group, DefaultedList<ItemStack> stacks) {
		if (instance instanceof BlockItem blockItem) {
			if (blockItem.getBlock() == null) {
				ModBase.LOGGER.error(Registry.ITEM.getId(instance).toString());
			}
		}
		instance.appendStacks(group, stacks);
	}
}
