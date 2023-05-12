package fun.mousewich.ryft;

import fun.mousewich.sound.IdentifiedSounds;

import java.util.List;

import static fun.mousewich.ModBase.*;

public class RyftMod {
	public static final String NAMESPACE = "ryft";

	public static final String KADEN = "Kaden";
	public static final String KIRHA = "Kirha";

	public static void RegisterAll() {
		IdentifiedSounds.RegisterPower(NAMESPACE, "angel", List.of(EN_US.clacking(EN_US.Heels())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.swims(EN_US.Someone())), List.of(EN_US.splashes(EN_US.Someone())), List.of(EN_US.splashes_hard(EN_US.Someone())), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "arsene", List.of(EN_US.footsteps(EN_US.Aggressive())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.swims(EN_US.Someone())), List.of(EN_US.splashes(EN_US.Someone())), List.of(EN_US.cannonballs(EN_US.Someone())), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "auryon", List.of(EN_US.marching(EN_US.Confident())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.sloshing(EN_US.Feathers())), List.of(EN_US.splashing(EN_US.Undignified())), List.of(EN_US.splashing(EN_US.undignified(EN_US.Loud()))), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "dj", List.of(EN_US.steps(EN_US.Light())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.swims(EN_US.Someone())), List.of(EN_US.splashes(EN_US.lightly(EN_US.Someone()))), List.of(EN_US.splashes_hard(EN_US.Someone())), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "faerie", List.of(EN_US.footsteps(EN_US.Clumsy())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.splashing(EN_US.Rhythmic())), List.of(EN_US.splashes(EN_US.awkwardly(EN_US.Someone()))), List.of(EN_US.splashing(EN_US.loudly(EN_US.Someone()))), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "gubby", List.of(EN_US.steps(EN_US.Warden())), List.of(EN_US.trips(EN_US.Warden())), List.of(EN_US.fell(EN_US.Warden())), List.of(EN_US.crashes(EN_US.Warden())), List.of(EN_US.swims(EN_US.Warden())), List.of(EN_US.splashes(EN_US.Warden())), List.of(EN_US.splashes_hard(EN_US.Warden())), List.of(EN_US.dies(EN_US.Warden())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "justin",
				List.of(EN_US.steps(EN_US.Someone())),
				List.of(EN_US.trips(EN_US.Someone())),
				List.of(EN_US.fell(EN_US.Someone())),
				List.of(EN_US.crashes(EN_US.Someone())),
				List.of(EN_US.swims(EN_US.Someone())),
				List.of(EN_US.splashes(EN_US.Someone())),
				List.of(EN_US.splashes_hard(EN_US.Someone())),
				List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "kaden", List.of(EN_US.softly(EN_US.steps(KADEN))), List.of(EN_US.trips(KADEN)), List.of(EN_US.softly(EN_US.lands(KADEN))), List.of(EN_US.crashes(KADEN)), List.of(EN_US.swims(KADEN)), List.of(EN_US.splashes(EN_US.quietly(KADEN))), List.of(EN_US.splashes(KADEN)), List.of(EN_US.dies(KADEN)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "kirha", List.of(EN_US.scuttles(KIRHA)), List.of(EN_US.trips(KIRHA)), List.of(EN_US.lands(KIRHA)), List.of(EN_US.splats(KIRHA)), List.of(EN_US.paddles(KIRHA)), List.of(EN_US.sploshes(KIRHA)), List.of(EN_US.hard(EN_US.sploshes(KIRHA))), List.of(EN_US.dies(KIRHA)));
		IdentifiedSounds.RegisterPower(NAMESPACE, "lavender", List.of(EN_US.tapping(EN_US.porcelain(EN_US.Excited()))), List.of(EN_US.trips(EN_US.small(EN_US.Someone()))), List.of(EN_US.crunches(EN_US.Porcelain())), List.of(EN_US.crashes(EN_US.Porcelain())), List.of(EN_US.glugs(EN_US.Porcelain())), List.of(EN_US.splashes(EN_US.small(EN_US.Something()))), List.of(EN_US.splashes_hard(EN_US.small(EN_US.Something()))), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "navn", List.of(EN_US.stomping(EN_US.Wet())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.swims(EN_US.Fish(EN_US.Large()))), List.of(EN_US.splashes(EN_US.Fish(EN_US.Large()))), List.of(EN_US.splashes_hard(EN_US.Fish(EN_US.Large()))), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "olm",
				List.of(EN_US.steps(EN_US.Someone())),
				List.of(EN_US.trips(EN_US.Someone())),
				List.of(EN_US.fell(EN_US.Someone())),
				List.of(EN_US.crashes(EN_US.Someone())),
				List.of(EN_US.swims(EN_US.Someone())),
				List.of(EN_US.splashes(EN_US.Someone())),
				List.of(EN_US.splashes_hard(EN_US.Someone())),
				List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "oracle", List.of(EN_US.footsteps(EN_US.Aloof())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.swims(EN_US.Someone())), List.of(EN_US.splashes(EN_US.Someone())), List.of(EN_US.splashes_hard(EN_US.Someone())), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "quincy", List.of(EN_US.steps(EN_US.boot(EN_US.Light()))), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.chivalrously(EN_US.crashes(EN_US.Someone()))), List.of(EN_US.swims(EN_US.Someone())), List.of(EN_US.splashes(EN_US.Someone())), List.of(EN_US.splashes_hard(EN_US.Someone())), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "rose", List.of(EN_US.tapping(EN_US.porcelain(EN_US.Paced()))), List.of(EN_US.trips(EN_US.medium(EN_US.Someone()))), List.of(EN_US.crunches(EN_US.Porcelain())), List.of(EN_US.crashes(EN_US.Porcelain())), List.of(EN_US.glugs(EN_US.Porcelain())), List.of(EN_US.splashes(EN_US.medium(EN_US.Something()))), List.of(EN_US.splashes_hard(EN_US.medium(EN_US.Something()))), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "simon",
				List.of(EN_US.steps(EN_US.Someone())),
				List.of(EN_US.trips(EN_US.Someone())),
				List.of(EN_US.fell(EN_US.Someone())),
				List.of(EN_US.crashes(EN_US.Someone())),
				List.of(EN_US.swims(EN_US.Someone())),
				List.of(EN_US.splashes(EN_US.Someone())),
				List.of(EN_US.splashes_hard(EN_US.Someone())),
				List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "tatiy",
				List.of(EN_US.steps(EN_US.Someone())),
				List.of(EN_US.trips(EN_US.Someone())),
				List.of(EN_US.fell(EN_US.Someone())),
				List.of(EN_US.crashes(EN_US.Someone())),
				List.of(EN_US.swims(EN_US.Someone())),
				List.of(EN_US.splashes(EN_US.Someone())),
				List.of(EN_US.splashes_hard(EN_US.Someone())),
				List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower(NAMESPACE, "zofia", List.of(EN_US.footsteps(EN_US.Cindering())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.burned(EN_US.and(EN_US.crashed(EN_US.Someone())))), List.of(EN_US.sizzles(EN_US.Water())), List.of(EN_US.splashes(EN_US.hot(EN_US.Something()))), List.of(EN_US.splashes_hard(EN_US.hot(EN_US.Something()))), List.of(EN_US.dies(EN_US.Someone())));
	}
}
