package fun.wich.container;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;

public class FlowerContainer extends PottedBlockContainer {
	public FlowerContainer(StatusEffect effect, int effectDuration, Block.Settings blockSettings, Item.Settings itemSettings) {
		this(new FlowerBlock(effect, effectDuration, blockSettings), itemSettings);
	}
	public FlowerContainer(FlowerBlock block, Item.Settings settings) { super(block, settings); }
	public FlowerContainer flammable(int burn, int spread) { return (FlowerContainer)super.flammable(burn, spread); }
	public FlowerContainer compostable(float chance) { return (FlowerContainer)super.compostable(chance); }
	public FlowerContainer dropSelf() { return (FlowerContainer)super.dropSelf(); }
	public FlowerContainer pottedModel() { return (FlowerContainer)super.pottedModel(); }
	//Tags
	public FlowerContainer blockTag(TagKey<Block> tag) { return (FlowerContainer)super.blockTag(tag); }
	public FlowerContainer itemTag(TagKey<Item> tag) { return (FlowerContainer)super.itemTag(tag); }
}