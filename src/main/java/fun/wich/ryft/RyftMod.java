package fun.wich.ryft;

import fun.wich.gen.data.language.Words;
import fun.wich.registry.ModRegistry;
import fun.wich.damage.ModDamageSource;
import fun.wich.entity.blood.BloodType;
import fun.wich.item.syringe.BloodSyringeItem;
import fun.wich.sound.IdentifiedSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

import static fun.wich.ModBase.*;
import static fun.wich.ModFactory.HandheldItem;
import static fun.wich.ModFactory.MakeGeneratedItem;

public class RyftMod {
	public static final String NAMESPACE = "ryft";

	public static final String AURYONS = "Auryon's";
	public static final String KADEN = "Kaden";
	public static final String KIRHA = "Kirha";
	public static final String NAVN = "Navn";
	public static final String SIMON = "Simon";
	public static final String VICTORIAS = "Victoria's";

	//<editor-fold desc="Blood Types">
	public static final BloodType DRACONIC_BLOOD_TYPE = BloodType.Register(NAMESPACE, "draconic", List.of(EN_US.Blood(Words.Draconic)));
	public static final BloodType FIREBIRD_BLOOD_TYPE = BloodType.Register(NAMESPACE, "firebird", List.of(EN_US.Blood(Words.Firebird)));
	//</editor-fold>

	//<editor-fold desc="Auryon">
	public static final Item AURYON_FEATHER = MakeGeneratedItem();
	public static final Item VICTORIA_FEATHER = MakeGeneratedItem();
	//</editor-fold>
	//<editor-fold desc="Kaden">
	public static final Item BROKEN_PTEROR = MakeGeneratedItem();
	//</editor-fold>
	//<editor-fold desc="Zofia">
	public static final Item FIREBIRD_BLOOD_SYRINGE = HandheldItem(new BloodSyringeItem((PlayerEntity user, ItemStack stack, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == FIREBIRD_BLOOD_TYPE) BloodSyringeItem.heal(entity, 1);
		else {
			if (bloodType == AVIAN_BLOOD_TYPE)  BloodSyringeItem.heal(entity, 1);
			else entity.damage(ModDamageSource.Injected(user, FIREBIRD_BLOOD_TYPE), 1);
			entity.setOnFireFor(15);
		}
	}));
	//</editor-fold>

	public static void RegisterAll() {
		ModRegistry.Register("auryon_feather", AURYON_FEATHER, List.of(EN_US.Feather(AURYONS)));
		ModRegistry.Register("victoria_feather", VICTORIA_FEATHER, List.of(EN_US.Feather(VICTORIAS)));

		ModRegistry.Register("broken_pteror", BROKEN_PTEROR, List.of("Broken Pteror"));

		ModRegistry.Register("firebird_blood_syringe", FIREBIRD_BLOOD_SYRINGE, List.of(EN_US.Syringe(EN_US.Blood(Words.Firebird))));
		BloodType.RegisterBloodType(FIREBIRD_BLOOD_TYPE, FIREBIRD_BLOOD_SYRINGE);

		IdentifiedSounds.RegisterPower(NAMESPACE, "angel", List.of(EN_US.clacking(Words.Heels)), List.of(EN_US.trips(Words.Someone)), List.of(EN_US.fell(Words.Someone)), List.of(EN_US.crashes(Words.Someone)), List.of(EN_US.swims(Words.Someone)), List.of(EN_US.splashes(Words.Someone)), List.of(EN_US.hard(EN_US.splashes(Words.Someone))), List.of(EN_US.dies(Words.Someone)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "arsene", List.of(EN_US.footsteps(Words.Aggressive)), List.of(EN_US.trips(Words.Someone)), List.of(EN_US.fell(Words.Someone)), List.of(EN_US.crashes(Words.Someone)), List.of(EN_US.swims(Words.Someone)), List.of(EN_US.splashes(Words.Someone)), List.of(EN_US.cannonballs(Words.Someone)), List.of(EN_US.dies(Words.Someone)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "auryon", List.of(EN_US.marching(Words.Confident)), List.of(EN_US.trips(Words.Someone)), List.of(EN_US.fell(Words.Someone)), List.of(EN_US.crashes(Words.Someone)), List.of(EN_US.sloshing(EN_US.Feathers())), List.of(EN_US.splashing(Words.Undignified)), List.of(EN_US.splashing(EN_US.undignified(Words.Loud))), List.of(EN_US.dies(Words.Someone)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "dj", List.of(EN_US.steps(Words.Light)), List.of(EN_US.trips(Words.Someone)), List.of(EN_US.fell(Words.Someone)), List.of(EN_US.crashes(Words.Someone)), List.of(EN_US.swims(Words.Someone)), List.of(EN_US.splashes(EN_US.lightly(Words.Someone))), List.of(EN_US.hard(EN_US.splashes(Words.Someone))), List.of(EN_US.dies(Words.Someone)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "eldora", List.of(EN_US.wetly(EN_US.stomps(EN_US.Guardian(Words.Ancient)))), List.of(EN_US.trips(EN_US.Guardian(Words.Ancient))), List.of(EN_US.fell(EN_US.Guardian(Words.Ancient))), List.of(EN_US.crashes(EN_US.Guardian(Words.Ancient))), List.of(EN_US.swims(EN_US.Guardian(Words.Ancient))), List.of(EN_US.splashes(EN_US.Guardian(Words.Ancient))), List.of(EN_US.hard(EN_US.splashes(EN_US.Guardian(Words.Ancient)))), List.of(EN_US.dies(EN_US.Guardian(Words.Ancient))));
		IdentifiedSounds.RegisterPower(NAMESPACE, "faerie", List.of(EN_US.footsteps(Words.Clumsy)), List.of(EN_US.trips(Words.Someone)), List.of(EN_US.fell(Words.Someone)), List.of(EN_US.crashes(Words.Someone)), List.of(EN_US.splashing(Words.Rhythmic)), List.of(EN_US.splashes(EN_US.awkwardly(Words.Someone))), List.of(EN_US.splashing(EN_US.loudly(Words.Someone))), List.of(EN_US.dies(Words.Someone)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "gubby", List.of(EN_US.steps(Words.Warden)), List.of(EN_US.trips(Words.Warden)), List.of(EN_US.fell(Words.Warden)), List.of(EN_US.crashes(Words.Warden)), List.of(EN_US.swims(Words.Warden)), List.of(EN_US.splashes(Words.Warden)), List.of(EN_US.hard(EN_US.splashes(Words.Warden))), List.of(EN_US.dies(Words.Warden)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "ivy",
				List.of(EN_US.steps(Words.Spiteful)),
				List.of(EN_US.trips(Words.Someone)),
				List.of(EN_US.fell(Words.Someone)),
				List.of(EN_US.crashes(Words.Someone)),
				List.of(EN_US.swims(Words.Someone)),
				List.of(EN_US.splashes(Words.Someone)),
				List.of(EN_US.hard(EN_US.splashes(Words.Someone))),
				List.of(EN_US.dies(Words.Someone)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "justin",
				List.of(EN_US.steps(Words.Someone)),
				List.of(EN_US.trips(Words.Someone)),
				List.of(EN_US.fell(Words.Someone)),
				List.of(EN_US.crashes(Words.Someone)),
				List.of(EN_US.swims(Words.Someone)),
				List.of(EN_US.splashes(Words.Someone)),
				List.of(EN_US.hard(EN_US.splashes(Words.Someone))),
				List.of(EN_US.dies(Words.Someone)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "kaden", List.of(EN_US.softly(EN_US.steps(KADEN))), List.of(EN_US.trips(KADEN)), List.of(EN_US.softly(EN_US.lands(KADEN))), List.of(EN_US.crashes(KADEN)), List.of(EN_US.swims(KADEN)), List.of(EN_US.splashes(EN_US.quietly(KADEN))), List.of(EN_US.splashes(KADEN)), List.of(EN_US.dies(KADEN)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "kirha", List.of(EN_US.scuttles(KIRHA)), List.of(EN_US.trips(KIRHA)), List.of(EN_US.lands(KIRHA)), List.of(EN_US.splats(KIRHA)), List.of(EN_US.paddles(KIRHA)), List.of(EN_US.sploshes(KIRHA)), List.of(EN_US.hard(EN_US.sploshes(KIRHA))), List.of(EN_US.dies(KIRHA)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "lavender", List.of(EN_US.tapping(EN_US.porcelain(Words.Excited))), List.of(EN_US.trips(EN_US.small(Words.Someone))), List.of(EN_US.crunches(Words.Porcelain)), List.of(EN_US.crashes(Words.Porcelain)), List.of(EN_US.glugs(Words.Porcelain)), List.of(EN_US.splashes(EN_US.small(Words.Something))), List.of(EN_US.hard(EN_US.splashes(EN_US.small(Words.Something)))), List.of(EN_US.dies(Words.Someone)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "navn", List.of(EN_US.wetly(EN_US.stomps(NAVN))), List.of(EN_US.trips(NAVN)), List.of(EN_US.fell(NAVN)), List.of(EN_US.crashes(NAVN)), List.of(EN_US.swims(NAVN)), List.of(EN_US.splashes(NAVN)), List.of(EN_US.hard(EN_US.splashes(NAVN))), List.of(EN_US.dies(NAVN)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "olm",
				List.of(EN_US.steps(Words.Someone)),
				List.of(EN_US.trips(Words.Someone)),
				List.of(EN_US.fell(Words.Someone)),
				List.of(EN_US.crashes(Words.Someone)),
				List.of(EN_US.swims(Words.Someone)),
				List.of(EN_US.splashes(Words.Someone)),
				List.of(EN_US.hard(EN_US.splashes(Words.Someone))),
				List.of(EN_US.dies(Words.Someone)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "oracle", List.of(EN_US.footsteps(Words.Aloof)), List.of(EN_US.trips(Words.Someone)), List.of(EN_US.fell(Words.Someone)), List.of(EN_US.crashes(Words.Someone)), List.of(EN_US.swims(Words.Someone)), List.of(EN_US.splashes(Words.Someone)), List.of(EN_US.hard(EN_US.splashes(Words.Someone))), List.of(EN_US.dies(Words.Someone)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "penny",
				List.of(EN_US.steps(Words.Someone)),
				List.of(EN_US.trips(Words.Someone)),
				List.of(EN_US.fell(Words.Someone)),
				List.of(EN_US.crashes(Words.Someone)),
				List.of(EN_US.swims(Words.Someone)),
				List.of(EN_US.splashes(Words.Someone)),
				List.of(EN_US.hard(EN_US.splashes(Words.Someone))),
				List.of(EN_US.dies(Words.Someone)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "quincy", List.of(EN_US.steps(EN_US.boot(Words.Light))), List.of(EN_US.trips(Words.Someone)), List.of(EN_US.fell(Words.Someone)), List.of(EN_US.chivalrously(EN_US.crashes(Words.Someone))), List.of(EN_US.swims(Words.Someone)), List.of(EN_US.splashes(Words.Someone)), List.of(EN_US.hard(EN_US.splashes(Words.Someone))), List.of(EN_US.dies(Words.Someone)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "rose", List.of(EN_US.tapping(EN_US.porcelain(Words.Paced))), List.of(EN_US.trips(EN_US.medium(Words.Someone))), List.of(EN_US.crunches(Words.Porcelain)), List.of(EN_US.crashes(Words.Porcelain)), List.of(EN_US.glugs(Words.Porcelain)), List.of(EN_US.splashes(EN_US.medium(Words.Something))), List.of(EN_US.hard(EN_US.splashes(EN_US.medium(Words.Something)))), List.of(EN_US.dies(Words.Someone)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "simon",
				List.of(EN_US.steps(SIMON)),
				List.of(EN_US.trips(SIMON)),
				List.of(EN_US.fell(SIMON)),
				List.of(EN_US.crashes(SIMON)),
				List.of(EN_US.swims(SIMON)),
				List.of(EN_US.splashes(SIMON)),
				List.of(EN_US.hard(EN_US.splashes(SIMON))),
				List.of(EN_US.dies(SIMON)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "tatiy",
				List.of(EN_US.steps(Words.Someone)),
				List.of(EN_US.trips(Words.Someone)),
				List.of(EN_US.fell(Words.Someone)),
				List.of(EN_US.crashes(Words.Someone)),
				List.of(EN_US.swims(Words.Someone)),
				List.of(EN_US.splashes(Words.Someone)),
				List.of(EN_US.hard(EN_US.splashes(Words.Someone))),
				List.of(EN_US.dies(Words.Someone)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "zofia", List.of(EN_US.footsteps(Words.Cindering)), List.of(EN_US.trips(Words.Someone)), List.of(EN_US.fell(Words.Someone)), List.of(EN_US.burned(EN_US.and(EN_US.crashed(Words.Someone)))), List.of(EN_US.sizzles(EN_US.Water())), List.of(EN_US.splashes(EN_US.hot(Words.Something))), List.of(EN_US.hard(EN_US.splashes(EN_US.hot(Words.Something)))), List.of(EN_US.dies(Words.Someone)));
	}
}
