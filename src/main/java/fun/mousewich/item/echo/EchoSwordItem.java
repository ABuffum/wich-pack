package fun.mousewich.item.echo;

import fun.mousewich.ModBase;
import fun.mousewich.item.basic.tool.ModSwordItem;
import fun.mousewich.util.EchoUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class EchoSwordItem extends ModSwordItem {
	private final float knockback;
	public EchoSwordItem(ToolMaterial material, int attackDamage, float attackSpeed, float knockback) {
		this(material, attackDamage, attackSpeed, knockback, ModBase.ItemSettings());
	}
	public EchoSwordItem(ToolMaterial material, int attackDamage, float attackSpeed, float knockback, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
		this.knockback = knockback;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) { return EchoUtils.postHit(stack, target, attacker, knockback); }
}
