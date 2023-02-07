package fun.mousewich.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class EchoUtils {
	public static boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker, float knockback) {
		stack.damage(1, attacker, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		if (knockback > 0) target.takeKnockback(0.5D + knockback, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
		return true;
	}
}
