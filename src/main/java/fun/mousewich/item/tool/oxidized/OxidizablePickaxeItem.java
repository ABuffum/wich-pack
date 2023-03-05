package fun.mousewich.item.tool.oxidized;

import fun.mousewich.item.OxidizableItem;
import fun.mousewich.item.tool.ModPickaxeItem;
import fun.mousewich.util.OxidationScale;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

import java.util.Optional;

public class OxidizablePickaxeItem extends ModPickaxeItem implements OxidizableItem {
	private final Oxidizable.OxidationLevel level;
	public OxidizablePickaxeItem(Oxidizable.OxidationLevel level, ToolMaterial material, int attackDamage, float attackSpeed) {
		super(material, attackDamage, attackSpeed);
		this.level = level;
	}
	@Override
	public Oxidizable.OxidationLevel getDegradationLevel() { return level; }
	@Override
	public Optional<Item> getDegradationResult() { return OxidationScale.getIncreasedItem(this); }
}