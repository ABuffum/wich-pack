package fun.mousewich.item.basic;

import fun.mousewich.ModBase;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableArmorItem;

public class ModDyeableArmorItem extends DyeableArmorItem {
	public ModDyeableArmorItem(ArmorMaterial material, EquipmentSlot slot) { this(material, slot, ModBase.ItemSettings()); }
	public ModDyeableArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) { super(material, slot, settings); }
}
