package fun.mousewich.item.basic;

import fun.mousewich.ModBase;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.util.Identifier;

public class ModHorseArmorItem extends HorseArmorItem {
	private static final String ENTITY_TEXTURE_PREFIX = "textures/entity/horse/";
	private final String entityTexture;

	public ModHorseArmorItem(int bonus, String name, Settings settings) {
		super(bonus, name, settings);
		this.entityTexture = "textures/entity/horse/armor/horse_armor_" + name + ".png";
	}

	@Override
	public Identifier getEntityTexture() { return ModBase.ID(this.entityTexture); }
}
