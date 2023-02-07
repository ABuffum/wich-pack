package fun.mousewich.item.basic.tool;

import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import fun.mousewich.ModBase;
import net.minecraft.item.ToolMaterial;

public class ModKnifeItem extends KnifeItem {
	public ModKnifeItem(ToolMaterial material) { this(material, ModBase.ItemSettings()); }
	public ModKnifeItem(ToolMaterial material, Settings settings) { super(material, settings); }
}
