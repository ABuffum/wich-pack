package fun.mousewich.item.tool.oxidized;

import fun.mousewich.item.OxidizableItem;
import fun.mousewich.item.tool.ModKnifeItem;
import fun.mousewich.material.ModToolMaterials;
import fun.mousewich.util.OxidationScale;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

import java.util.Optional;

import net.minecraft.block.Oxidizable.OxidationLevel;

public class OxidizableKnifeItem extends ModKnifeItem implements OxidizableItem {
	private final OxidationLevel level;
	public OxidizableKnifeItem(OxidationLevel level, ModToolMaterials material) { this(level, (ToolMaterial)material); }
	public OxidizableKnifeItem(OxidationLevel level, ToolMaterial material) {
		super(material);
		this.level = level;
	}
	@Override
	public OxidationLevel getDegradationLevel() { return level; }
	@Override
	public Optional<Item> getDegradationResult() { return OxidationScale.getIncreasedItem(this); }
}
