package fun.mousewich.mixins.client.sound;

import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SoundManager.class)
public interface SoundManagerAccessor {
	@Accessor("soundSystem")
	public SoundSystem getSoundSystem();
}