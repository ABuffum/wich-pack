package fun.mousewich.item.tool.echo;

import fun.mousewich.item.tool.ModKnifeItem;
import fun.mousewich.util.EchoUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class EchoKnifeItem extends ModKnifeItem {
	private final float knockback;
	public EchoKnifeItem(ToolMaterial material, float knockback) {
		super(material);
		this.knockback = knockback;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) { return EchoUtils.postHit(stack, target, attacker, knockback); }
}
