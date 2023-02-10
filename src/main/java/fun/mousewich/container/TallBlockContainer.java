package fun.mousewich.container;

import fun.mousewich.ModBase;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;

public class TallBlockContainer extends BlockContainer {
	public TallBlockContainer(Block block) { this(block, ModBase.ItemSettings()); }
	public TallBlockContainer(Block block, Item.Settings settings) { super(block, new TallBlockItem(block, settings)); }
	public TallBlockContainer flammable(int burn, int spread) {
		FlammableBlockRegistry.getDefaultInstance().add(this.asBlock(), burn, spread);
		return this;
	}
	public TallBlockContainer compostable(float chance) {
		CompostingChanceRegistry.INSTANCE.add(this.asItem(), chance);
		return this;
	}
}
