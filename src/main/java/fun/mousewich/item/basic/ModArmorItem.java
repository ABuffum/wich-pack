package fun.mousewich.item.basic;

import fun.mousewich.ModFactory;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

public class ModArmorItem extends ArmorItem {
	public ModArmorItem(ArmorMaterial material, EquipmentSlot slot) { this(material, slot, ModFactory.ItemSettings()); }
	public ModArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) { super(material, slot, settings); }
}
