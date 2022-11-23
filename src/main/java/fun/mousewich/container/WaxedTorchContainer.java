package fun.mousewich.container;

import net.minecraft.block.AbstractBlock;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;

public class WaxedTorchContainer extends TorchContainer implements IContainer {
	protected final TorchContainer unwaxed;
	public TorchContainer getUnwaxed() { return unwaxed; }

	public WaxedTorchContainer(TorchContainer unwaxed, AbstractBlock.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		super(blockSettings, particle, itemSettings);
		this.unwaxed = unwaxed;
	}
}

