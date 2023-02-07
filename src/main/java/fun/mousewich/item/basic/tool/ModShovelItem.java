package fun.mousewich.item.basic.tool;

import fun.mousewich.ModBase;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

public class ModShovelItem extends ShovelItem {
	public ModShovelItem(ToolMaterial material, float attackDamage, float attackSpeed) {
		this(material, attackDamage, attackSpeed, ModBase.ItemSettings());
	}
	public ModShovelItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}
}
