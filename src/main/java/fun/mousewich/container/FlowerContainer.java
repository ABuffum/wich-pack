package fun.mousewich.container;

import fun.mousewich.ModBase;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.sound.BlockSoundGroup;

public class FlowerContainer extends PottedBlockContainer {
	public static AbstractBlock.Settings Settings() {
		return AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS);
	}
	public static AbstractBlock.Settings TallSettings() {
		return AbstractBlock.Settings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS);
	}

	public FlowerContainer(StatusEffect effect, int effectDuration) { this(effect, effectDuration, Settings()); }
	public FlowerContainer(StatusEffect effect, int effectDuration, AbstractBlock.Settings settings) {
		this(new FlowerBlock(effect, effectDuration, settings));
	}
	public FlowerContainer(FlowerBlock block) {
		super(block, ModBase.ItemSettings());
	}
	public FlowerContainer flammable(int burn, int spread) {
		FlammableBlockRegistry.getDefaultInstance().add(this.asBlock(), burn, spread);
		return this;
	}
	public FlowerContainer compostable(float chance) {
		CompostingChanceRegistry.INSTANCE.add(this.asItem(), chance);
		return this;
	}
}