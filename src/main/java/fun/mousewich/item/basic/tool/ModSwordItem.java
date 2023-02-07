package fun.mousewich.item.basic.tool;

import fun.mousewich.ModBase;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class ModSwordItem extends SwordItem {
	public ModSwordItem(ToolMaterial material, int attackDamage, float attackSpeed) {
		this(material, attackDamage, attackSpeed, ModBase.ItemSettings());
	}
	public ModSwordItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}
}
