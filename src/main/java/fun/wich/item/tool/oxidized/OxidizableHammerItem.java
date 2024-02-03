package fun.wich.item.tool.oxidized;

import fun.wich.item.OxidizableItem;
import fun.wich.item.tool.HammerItem;
import fun.wich.material.ModToolMaterials;
import fun.wich.util.OxidationScale;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

import java.util.Optional;

import net.minecraft.block.Oxidizable.OxidationLevel;

public class OxidizableHammerItem extends HammerItem implements OxidizableItem {
	private final OxidationLevel level;
	public OxidizableHammerItem(OxidationLevel level, ModToolMaterials material) { this(level, material, material.getHammerDamage(), material.getHammerSpeed()); }
	public OxidizableHammerItem(OxidationLevel level, ToolMaterial material, float attackDamage, float attackSpeed) {
		super(material, attackDamage, attackSpeed);
		this.level = level;
	}
	@Override
	public OxidationLevel getDegradationLevel() { return level; }
	@Override
	public Optional<Item> getDegradationResult() { return OxidationScale.getIncreasedItem(this); }
}
