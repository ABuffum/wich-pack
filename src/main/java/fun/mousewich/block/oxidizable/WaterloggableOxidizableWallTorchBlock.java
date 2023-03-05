package fun.mousewich.block.oxidizable;

import net.minecraft.block.Waterloggable;
import net.minecraft.particle.ParticleEffect;

public class WaterloggableOxidizableWallTorchBlock extends OxidizableWallTorchBlock implements Waterloggable {
	public WaterloggableOxidizableWallTorchBlock(OxidationLevel level, Settings settings, ParticleEffect particleEffect) {
		super(level, settings, particleEffect);
	}
}
