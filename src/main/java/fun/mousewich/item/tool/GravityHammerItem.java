package fun.mousewich.item.tool;

import fun.mousewich.enchantment.GravityEnchantment;
import fun.mousewich.material.ModToolMaterials;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

import java.util.List;

public class GravityHammerItem extends HammerItem implements ExtraKnockbackItem {
	protected final int factor;
	public GravityHammerItem(int factor, ModToolMaterials material) { this(factor, material, material.getHammerDamage(), material.getHammerSpeed()); }
	public GravityHammerItem(int factor, ToolMaterial material, float attackDamage, float attackSpeed) {
		super(material, attackDamage, attackSpeed);
		this.factor = factor;
	}
	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		GravityEnchantment.pullInNearbyEntities(attacker.getWorld(), target.getPos(), factor, List.of(attacker, target));
		return super.postHit(stack, target, attacker);
	}
	@Override
	public int getExtraKnockback() { return factor < 0 ? -factor : 0; }
}
