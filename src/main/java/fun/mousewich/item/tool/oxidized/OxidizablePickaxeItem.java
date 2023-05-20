package fun.mousewich.item.tool.oxidized;

import fun.mousewich.item.OxidizableItem;
import fun.mousewich.item.tool.ModPickaxeItem;
import fun.mousewich.material.ModToolMaterials;
import fun.mousewich.util.OxidationScale;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

import java.util.Optional;

import net.minecraft.block.Oxidizable.OxidationLevel;

public class OxidizablePickaxeItem extends ModPickaxeItem implements OxidizableItem {
	private final OxidationLevel level;
	public OxidizablePickaxeItem(OxidationLevel level, ModToolMaterials material) { this(level, material, material.getPickaxeDamage(), material.getPickaxeSpeed()); }
	public OxidizablePickaxeItem(OxidationLevel level, ToolMaterial material, int attackDamage, float attackSpeed) {
		super(material, attackDamage, attackSpeed);
		this.level = level;
	}
	@Override
	public OxidationLevel getDegradationLevel() { return level; }
	@Override
	public Optional<Item> getDegradationResult() { return OxidationScale.getIncreasedItem(this); }
}
