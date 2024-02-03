package fun.wich.item.tool.oxidized;

import fun.wich.item.OxidizableItem;
import fun.wich.item.tool.ModKnifeItem;
import fun.wich.material.ModToolMaterials;
import fun.wich.util.OxidationScale;
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
