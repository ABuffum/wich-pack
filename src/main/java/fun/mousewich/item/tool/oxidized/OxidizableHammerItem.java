package fun.mousewich.item.tool.oxidized;

import fun.mousewich.item.OxidizableItem;
import fun.mousewich.item.tool.HammerItem;
import fun.mousewich.material.ModToolMaterials;
import fun.mousewich.util.OxidationScale;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

import java.util.Optional;

public class OxidizableHammerItem extends HammerItem implements OxidizableItem {
	private final Oxidizable.OxidationLevel level;
	public OxidizableHammerItem(Oxidizable.OxidationLevel level, ModToolMaterials material) { this(level, material, material.getHammerDamage(), material.getHammerSpeed()); }
	public OxidizableHammerItem(Oxidizable.OxidationLevel level, ToolMaterial material, float attackDamage, float attackSpeed) {
		super(material, attackDamage, attackSpeed);
		this.level = level;
	}
	@Override
	public Oxidizable.OxidationLevel getDegradationLevel() { return level; }
	@Override
	public Optional<Item> getDegradationResult() { return OxidationScale.getIncreasedItem(this); }
}
