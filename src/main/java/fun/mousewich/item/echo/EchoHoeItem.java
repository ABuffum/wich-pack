package fun.mousewich.item.echo;

import fun.mousewich.item.basic.tool.ModHoeItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class EchoHoeItem extends ModHoeItem {
	private float knockback = 0;

	public EchoHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, float knockback, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
		this.knockback = knockback;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damage(1, attacker, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		if (knockback > 0) target.takeKnockback(0.5D + knockback, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
		return true;
	}
}
