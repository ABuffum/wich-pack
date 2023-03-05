package fun.mousewich.item.tool.echo;

import fun.mousewich.item.tool.ModHoeItem;
import fun.mousewich.util.EchoUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class EchoHoeItem extends ModHoeItem {
	private final float knockback;
	public EchoHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, float knockback) {
		super(material, attackDamage, attackSpeed);
		this.knockback = knockback;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) { return EchoUtils.postHit(stack, target, attacker, knockback); }
}
