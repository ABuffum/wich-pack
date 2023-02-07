package fun.mousewich.item.basic.tool;

import fun.mousewich.ModBase;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

public class ModHoeItem extends HoeItem {
	public ModHoeItem(ToolMaterial material, int attackDamage, float attackSpeed) {
		this(material, attackDamage, attackSpeed, ModBase.ItemSettings());
	}
	public ModHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}
}
