package fun.mousewich.sound;

import fun.mousewich.ModBase;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {
	//Echo
	public static final SoundEvent BLOCK_ECHO_BLOCK_CHIME = register("block.echo_block.chime");

	protected static SoundEvent register(String name) {
		Identifier id = ModBase.ID(name);
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
	}
}
