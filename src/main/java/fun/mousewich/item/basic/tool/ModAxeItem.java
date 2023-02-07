package fun.mousewich.item.basic.tool;

import fun.mousewich.ModBase;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ToolMaterial;

public class ModAxeItem extends AxeItem {
	public ModAxeItem(ToolMaterial material, float attackDamage, float attackSpeed) {
		this(material, attackDamage, attackSpeed, ModBase.ItemSettings());
	}
	public ModAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}
}
