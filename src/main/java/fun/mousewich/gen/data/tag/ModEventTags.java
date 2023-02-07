package fun.mousewich.gen.data.tag;

import fun.mousewich.ModBase;
import net.minecraft.tag.GameEventTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;

public class ModEventTags {
	public static final TagKey<GameEvent> ALLAY_CAN_LISTEN = createMinecraftTag("allay_can_listen");
	public static final TagKey<GameEvent> SHRIEKER_CAN_LISTEN = createMinecraftTag("shrieker_can_listen");
	public static final TagKey<GameEvent> WARDEN_CAN_LISTEN = createMinecraftTag("warden_can_listen");

	private static TagKey<GameEvent> createTag(String name) { return TagKey.of(Registry.GAME_EVENT_KEY, ModBase.ID(name)); }
	private static TagKey<GameEvent> createCommonTag(String name) { return TagKey.of(Registry.GAME_EVENT_KEY, new Identifier("c", name)); }
	private static TagKey<GameEvent> createMinecraftTag(String name) { return TagKey.of(Registry.GAME_EVENT_KEY, new Identifier(name)); }
}
