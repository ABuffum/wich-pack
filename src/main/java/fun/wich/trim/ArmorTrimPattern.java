package fun.wich.trim;

import fun.wich.ModFactory;
import fun.wich.gen.data.language.Words;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

import static fun.wich.ModBase.EN_US;

public enum ArmorTrimPattern implements ItemConvertible {
	SENTRY("sentry", new String[] { EN_US.Template(EN_US.Smithing(Words.Sentry)) }),
	DUNE("dune", new String[] { EN_US.Template(EN_US.Smithing(Words.Dune)) }),
	COAST("coast", new String[] { EN_US.Template(EN_US.Smithing(Words.Coast)) }),
	WILD("wild", new String[] { EN_US.Template(EN_US.Smithing(Words.Wild)) }),
	WARD("ward", new String[] { EN_US.Template(EN_US.Smithing(Words.Ward)) }),
	EYE("eye", new String[] { EN_US.Template(EN_US.Smithing(Words.Eye)) }),
	VEX("vex", new String[] { EN_US.Template(EN_US.Smithing(Words.Vex)) }),
	TIDE("tide", new String[] { EN_US.Template(EN_US.Smithing(Words.Tide)) }),
	SNOUT("snout", new String[] { EN_US.Template(EN_US.Smithing(Words.Snout)) }),
	RIB("rib", new String[] { EN_US.Template(EN_US.Smithing(Words.Rib)) }),
	SPIRE("spire", new String[] { EN_US.Template(EN_US.Smithing(Words.Spire)) }),
	WAYFINDER("wayfinder", new String[] { EN_US.Template(EN_US.Smithing(Words.Wayfinder)) }),
	SHAPER("shaper", new String[] { EN_US.Template(EN_US.Smithing(Words.Shaper)) }),
	SILENCE("silence", new String[] { EN_US.Template(EN_US.Smithing(Words.Silence)) }),
	RAISER("raiser", new String[] { EN_US.Template(EN_US.Smithing(Words.Raider)) }),
	HOST("host", new String[] { EN_US.Template(EN_US.Smithing(Words.Host)) });

	private final String name;
	private final String namespace;
	private final SmithingTemplateItem item;
	private final Text description;
	private final String[] translations;
	ArmorTrimPattern(String name, String[] translations) { this(name, Identifier.DEFAULT_NAMESPACE, translations); }
	ArmorTrimPattern(String name, String namespace, String[] translations) {
		this.name = name;
		this.namespace = namespace;
		this.item = ModFactory.GeneratedItem(SmithingTemplateItem.ofArmorTrimPattern(namespace + "." + this.name));
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
