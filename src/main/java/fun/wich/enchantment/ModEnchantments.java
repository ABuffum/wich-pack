package fun.wich.enchantment;

import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import fun.wich.gen.data.language.Words;
import fun.wich.item.tool.HammerItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

import java.util.List;

import static fun.wich.ModBase.EN_US;
import static fun.wich.registry.ModRegistry.Register;

public class ModEnchantments {
	public static final Enchantment COMMITTED = new CommittedEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND); //Dungeons
	public static final Enchantment CRUSHING = new ShieldBreakingEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND) {
		@Override public boolean isAcceptableItem(ItemStack stack) { return stack.getItem() instanceof HammerItem; }
	};
	public static final Enchantment EXPERIENCE_SIPHON = new ExperienceSiphonEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND);
	public static final Enchantment FRENZY = new FrenzyEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.CHEST); //Dungeons
	public static final Enchantment GRAVITY = new GravityEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND); //Dungeons
	public static final Enchantment LEECHING = new LeechingEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND); //Dungeons
	public static final Enchantment RECYLING = new RecyclingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND); //Dungeons
	public static final Enchantment RICOCHET = new RicochetEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND); //Dungeons
	public static final Enchantment RUSH = new RushEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.FEET); //Dungeons
	public static final Enchantment SERRATED = new SerratedEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND);
	public static final Enchantment THUNDERING = new ThunderingEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND); //Dungeons
	public static final Enchantment WATER_SHOT = new WaterShotEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND); //Dungeons
	public static final Enchantment WEAKENING = new WeakeningEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND); //Dungeons
	//Minecraft
	public static final Enchantment CLEAVING = new ShieldBreakingEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND) {
		@Override public boolean isAcceptableItem(ItemStack stack) { return stack.getItem() instanceof AxeItem; }
	}; //Combat Test
	public static final Enchantment SWIFT_SNEAK = new SwiftSneakEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.LEGS); //1.19

	public static void RegisterEnchantments() {
		Register("committed", COMMITTED, List.of(Words.Committed));
		Register("crushing", CRUSHING, List.of(Words.Crushing));
		Register("experience_siphon", EXPERIENCE_SIPHON, List.of(EN_US.Siphon(Words.Experience)));
		Register("frenzy", FRENZY, List.of(Words.Frenzy));
		Register("gravity", GRAVITY, List.of(Words.Gravity));
		Register("leeching", LEECHING, List.of(Words.Leeching));
		Register("recyling", RECYLING, List.of(Words.Recycling));
		Register("ricochet", RICOCHET, List.of(Words.Ricochet));
		Register("rush", RUSH, List.of(Words.Rush));
		Register("serrated", SERRATED, List.of(Words.Serrated));
		Register("thundering", THUNDERING, List.of(Words.Thundering));
		Register("water_shot", WATER_SHOT, List.of(EN_US.Shot(Words.Water)));
		Register("weakening", WEAKENING, List.of(Words.Weakening));
		//Minecraft
		Register("minecraft:cleaving", CLEAVING, List.of(Words.Cleaving)); //Combat Test
		Register("minecraft:swift_sneak", SWIFT_SNEAK, List.of(EN_US.Sneak(Words.Swift))); //1.19
	}
}
