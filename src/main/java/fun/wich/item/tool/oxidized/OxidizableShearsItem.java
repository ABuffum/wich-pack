package fun.wich.item.tool.oxidized;

import fun.wich.ModFactory;
import fun.wich.item.OxidizableItem;
import fun.wich.item.tool.ModShearsItem;
import fun.wich.material.ModToolMaterials;
import fun.wich.util.OxidationScale;
import net.minecraft.item.Item;

import java.util.Optional;

import net.minecraft.block.Oxidizable.OxidationLevel;

public class OxidizableShearsItem extends ModShearsItem implements OxidizableItem {
	private final OxidationLevel level;
	public OxidizableShearsItem(OxidationLevel level, ModToolMaterials material) { this(level, material, ModFactory.ItemSettings()); }
	public OxidizableShearsItem(OxidationLevel level, ModToolMaterials material, Settings settings) {
		super(material, settings);
		this.level = level;
	}
	@Override
	public OxidationLevel getDegradationLevel() { return level; }
	@Override
	public Optional<Item> getDegradationResult() { return OxidationScale.getIncreasedItem(this); }
}
