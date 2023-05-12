package fun.mousewich.item.consumable;

import fun.mousewich.util.MilkUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class BottledMilkItem extends BottledDrinkItem {
	public BottledMilkItem(Settings settings) { super(settings); }
	@Override public void OnDrink(ItemStack stack, LivingEntity user) { MilkUtil.ApplyMilk(user.world, user); }
}
