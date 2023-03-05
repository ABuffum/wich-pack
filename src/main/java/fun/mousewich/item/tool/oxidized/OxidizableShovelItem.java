package fun.mousewich.item.tool.oxidized;

import fun.mousewich.item.OxidizableItem;
import fun.mousewich.item.tool.ModShovelItem;
import fun.mousewich.util.OxidationScale;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

import java.util.Optional;

public class OxidizableShovelItem extends ModShovelItem implements OxidizableItem {
	private final Oxidizable.OxidationLevel level;
	public OxidizableShovelItem(Oxidizable.OxidationLevel level, ToolMaterial material, float attackDamage, float attackSpeed) {
		super(material, attackDamage, attackSpeed);
		this.level = level;
	}
	@Override
	public Oxidizable.OxidationLevel getDegradationLevel() { return level; }
	@Override
	public Optional<Item> getDegradationResult() { return OxidationScale.getIncreasedItem(this); }
}
