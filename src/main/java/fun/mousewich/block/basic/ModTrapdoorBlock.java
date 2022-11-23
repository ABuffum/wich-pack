package fun.mousewich.block.basic;

import net.minecraft.block.TrapdoorBlock;
import net.minecraft.sound.SoundEvent;

public class ModTrapdoorBlock extends TrapdoorBlock {
	public final SoundEvent openSound;
	public final SoundEvent closeSound;
	public ModTrapdoorBlock(Settings settings, SoundEvent openSound, SoundEvent closeSound) {
		super(settings);
		this.openSound = openSound;
		this.closeSound = closeSound;
	}
}
