package fun.mousewich.item.basic.tool;

import fun.mousewich.ModBase;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class ModPickaxeItem extends PickaxeItem {
	public ModPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed) {
		this(material, attackDamage, attackSpeed, ModBase.ItemSettings());
	}
	public ModPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}
}