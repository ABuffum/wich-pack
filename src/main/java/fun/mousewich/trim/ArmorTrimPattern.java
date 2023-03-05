package fun.mousewich.trim;

import fun.mousewich.ModBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;
import java.util.Optional;

public enum ArmorTrimPattern implements ItemConvertible {
	SENTRY("sentry", new String[] { ModBase.EN_US.SmithingTemplate("Sentry") }),
	DUNE("dune", new String[] { ModBase.EN_US.SmithingTemplate("Dune") }),
	COAST("coast", new String[] { ModBase.EN_US.SmithingTemplate("Coast") }),
	WILD("wild", new String[] { ModBase.EN_US.SmithingTemplate("Wild") }),
	WARD("ward", new String[] { ModBase.EN_US.SmithingTemplate("Ward") }),
	EYE("eye", new String[] { ModBase.EN_US.SmithingTemplate("Eye") }),
	VEX("vex", new String[] { ModBase.EN_US.SmithingTemplate("Vex") }),
	TIDE("tide", new String[] { ModBase.EN_US.SmithingTemplate("Tide") }),
	SNOUT("snout", new String[] { ModBase.EN_US.SmithingTemplate("Snout") }),
	RIB("rib", new String[] { ModBase.EN_US.SmithingTemplate("Rib") }),
	SPIRE("spire", new String[] { ModBase.EN_US.SmithingTemplate("Spire") });

	private final String name;
	private final String namespace;
	private final SmithingTemplateItem item;
	private final Text description;
	private final String[] translations;
	ArmorTrimPattern(String name, String[] translations) { this(name, "minecraft", translations); }
	ArmorTrimPattern(String name, String namespace, String[] translations) {
		this.name = name;
		this.namespace = namespace;
		this.item = SmithingTemplateItem.ofArmorTrimPattern(this.name);
		this.description = new TranslatableText("trim_pattern." + this.name);
		this.translations = translations;
	}
	public String getName() { return name; }
	public Item asItem() { return item; }
	public Text getDescription() { return description; }
	public Text getDescription(int color) { return description.copy().fillStyle(Style.EMPTY.withColor(color)); }
	public String getItemPath() { return namespace + ":" + name + "_armor_trim_smithing_template"; }
	public List<String> getTranslations() { return List.of(translations); }

	public static Optional<ArmorTrimPattern> get(ItemStack stack) {
		for (ArmorTrimPattern pattern : values()) {
			if (stack.isOf(pattern.asItem())) return Optional.of(pattern);
		}
		return Optional.empty();
	}
}
