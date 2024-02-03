package fun.wich.item.armor;

import fun.wich.dispenser.WearableDispenserBehavior;
import fun.wich.item.OxidizableItem;
import fun.wich.util.OxidationScale;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;

import java.util.Optional;

public class OxidizableArmoritem extends ModArmorItem implements OxidizableItem {
	private final Oxidizable.OxidationLevel level;
	public OxidizableArmoritem(Oxidizable.OxidationLevel level, ArmorMaterial material, EquipmentSlot slot) {
		super(material, slot);
		this.level = level;
	}
	@Override
	public Oxidizable.OxidationLevel getDegradationLevel() { return level; }
	@Override
	public Optional<Item> getDegradationResult() { return OxidationScale.getIncreasedItem(this); }

	public OxidizableArmoritem dispensible() {
		DispenserBlock.registerBehavior(this, new WearableDispenserBehavior());
		return (OxidizableArmoritem)super.dispensible();
	}
}
