package fun.mousewich.item.basic;

import fun.mousewich.ModBase;
import fun.mousewich.dispenser.HorseArmorDispenserBehavior;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.util.Identifier;

public class ModHorseArmorItem extends HorseArmorItem {
	private final String entityTexture;

	public ModHorseArmorItem(int bonus, String name, Settings settings) {
		super(bonus, name, settings);
		this.entityTexture = "textures/entity/horse/armor/horse_armor_" + name + ".png";
	}
	public ModHorseArmorItem dispenseSilently() {
		DispenserBlock.registerBehavior(this, new HorseArmorDispenserBehavior()::dispenseSilently);
		return this;
	}

	@Override
	public Identifier getEntityTexture() { return ModBase.ID(this.entityTexture); }
}
