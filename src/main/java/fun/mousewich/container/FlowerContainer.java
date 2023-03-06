package fun.mousewich.container;

import fun.mousewich.ModFactory;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class FlowerContainer extends PottedBlockContainer {
	public static AbstractBlock.Settings Settings() {
		return AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS);
	}
	public static AbstractBlock.Settings TallSettings() {
		return AbstractBlock.Settings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS);
	}

	public FlowerContainer(StatusEffect effect, int effectDuration, Item.Settings itemSettings) { this(effect, effectDuration, Settings(), itemSettings); }
	public FlowerContainer(StatusEffect effect, int effectDuration, AbstractBlock.Settings settings, Item.Settings itemSettings) {
		this(new FlowerBlock(effect, effectDuration, settings), itemSettings);
	}
	public FlowerContainer(FlowerBlock block) { super(block); }
	public FlowerContainer(FlowerBlock block, Item.Settings settings) { super(block, settings); }
	public FlowerContainer flammable(int burn, int spread) { return (FlowerContainer)super.flammable(burn, spread); }
	public FlowerContainer compostable(float chance) { return (FlowerContainer)super.compostable(chance); }
	public FlowerContainer dropSelf() { return (FlowerContainer)super.dropSelf(); }
}