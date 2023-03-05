package fun.mousewich.item;

import fun.mousewich.util.ItemUtils;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Optional;

public interface OxidizableItem {
	default ItemStack degrade(ItemStack stack) {
		Optional<Item> degraded = this.getDegradationResult();
		if (degraded.isPresent()) return ItemUtils.swapItem(stack, degraded.get());
		else return ItemStack.EMPTY;
	}

	Oxidizable.OxidationLevel getDegradationLevel();
	Optional<Item> getDegradationResult();
}
