package fun.mousewich.gen.data.tag;

import fun.mousewich.event.ModGameEvent;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.GameEventTags;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;

import static fun.mousewich.ModBase.NAMESPACE;

public class GameEventTagGenerator extends FabricTagProvider<GameEvent> {
	public GameEventTagGenerator(FabricDataGenerator dataGenerator) {
		super(dataGenerator, Registry.GAME_EVENT, "game_events", NAMESPACE + ":game_event_tag_generator");
	}
	@Override
	protected void generateTags() {
		getOrCreateTagBuilder(ModEventTags.ALLAY_CAN_LISTEN).add(ModGameEvent.NOTE_BLOCK_PLAY);
		getOrCreateTagBuilder(GameEventTags.IGNORE_VIBRATIONS_SNEAKING)
				.add(GameEvent.HIT_GROUND, GameEvent.PROJECTILE_SHOOT, GameEvent.STEP, GameEvent.SWIM)
				.add(ModGameEvent.ITEM_INTERACT_START, ModGameEvent.ITEM_INTERACT_FINISH);
		getOrCreateTagBuilder(ModEventTags.SHRIEKER_CAN_LISTEN).add(ModGameEvent.SCULK_SENSOR_TENDRILS_CLICKING);
		getOrCreateTagBuilder(GameEventTags.VIBRATIONS)
				.add(GameEvent.BLOCK_ATTACH, GameEvent.BLOCK_CHANGE, GameEvent.BLOCK_CLOSE, GameEvent.BLOCK_DESTROY)
				.add(GameEvent.BLOCK_DETACH, GameEvent.BLOCK_OPEN, GameEvent.BLOCK_PLACE, ModGameEvent.BLOCK_ACTIVATE)
				.add(ModGameEvent.BLOCK_DEACTIVATE, GameEvent.CONTAINER_CLOSE, GameEvent.CONTAINER_OPEN)
				.add(GameEvent.DISPENSE_FAIL, ModGameEvent.DRINK, GameEvent.EAT, ModGameEvent.ELYTRA_GLIDE)
				.add(GameEvent.ENTITY_DAMAGED, ModGameEvent.ENTITY_DIE, GameEvent.ENTITY_KILLED)
				.add(ModGameEvent.ENTITY_INTERACT, GameEvent.ENTITY_PLACE, ModGameEvent.ENTITY_ROAR)
				.add(ModGameEvent.ENTITY_SHAKE, GameEvent.EQUIP, GameEvent.FLUID_PICKUP, GameEvent.FLUID_PLACE)
				.add(GameEvent.HIT_GROUND, ModGameEvent.INSTRUMENT_PLAY, ModGameEvent.ITEM_INTERACT_FINISH)
				.add(GameEvent.LIGHTNING_STRIKE, ModGameEvent.NOTE_BLOCK_PLAY, GameEvent.PISTON_CONTRACT)
				.add(GameEvent.PISTON_EXTEND, GameEvent.PRIME_FUSE, GameEvent.PROJECTILE_LAND)
				.add(GameEvent.PROJECTILE_SHOOT, GameEvent.SHEAR, GameEvent.SPLASH, GameEvent.STEP, GameEvent.SWIM)
				.add(ModGameEvent.TELEPORT, GameEvent.FLAP);
		getOrCreateTagBuilder(ModEventTags.WARDEN_CAN_LISTEN)
				.add(GameEvent.BLOCK_ATTACH, GameEvent.BLOCK_CHANGE, GameEvent.BLOCK_CLOSE, GameEvent.BLOCK_DESTROY)
				.add(GameEvent.BLOCK_DETACH, GameEvent.BLOCK_OPEN, GameEvent.BLOCK_PLACE, ModGameEvent.BLOCK_ACTIVATE)
				.add(ModGameEvent.BLOCK_DEACTIVATE, GameEvent.CONTAINER_CLOSE, GameEvent.CONTAINER_OPEN)
				.add(GameEvent.DISPENSE_FAIL, ModGameEvent.DRINK, GameEvent.EAT, ModGameEvent.ELYTRA_GLIDE)
				.add(GameEvent.ENTITY_DAMAGED, ModGameEvent.ENTITY_DIE, GameEvent.ENTITY_KILLED)
				.add(ModGameEvent.ENTITY_INTERACT, GameEvent.ENTITY_PLACE, ModGameEvent.ENTITY_ROAR)
				.add(ModGameEvent.ENTITY_SHAKE, GameEvent.EQUIP, GameEvent.EXPLODE, GameEvent.FLUID_PICKUP, GameEvent.FLUID_PLACE)
				.add(GameEvent.HIT_GROUND, ModGameEvent.INSTRUMENT_PLAY, ModGameEvent.ITEM_INTERACT_FINISH)
				.add(GameEvent.LIGHTNING_STRIKE, ModGameEvent.NOTE_BLOCK_PLAY, GameEvent.PISTON_CONTRACT)
				.add(GameEvent.PISTON_EXTEND, GameEvent.PRIME_FUSE, GameEvent.PROJECTILE_LAND)
				.add(GameEvent.PROJECTILE_SHOOT, GameEvent.SHEAR, GameEvent.SPLASH, GameEvent.STEP, GameEvent.SWIM)
				.add(ModGameEvent.TELEPORT, ModGameEvent.SHRIEK)
				.addTag(ModEventTags.SHRIEKER_CAN_LISTEN);
	}
}
