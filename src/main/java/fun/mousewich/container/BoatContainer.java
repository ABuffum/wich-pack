package fun.mousewich.container;

import fun.mousewich.ModBase;
import fun.mousewich.item.basic.ModBoatItem;
import fun.mousewich.entity.vehicle.ModBoatType;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.Item;

public class BoatContainer implements IContainer {
	protected final ModBoatType type;
	public ModBoatType getType() { return type; }
	protected final ModBoatItem item;
	public ModBoatItem getItem() { return item; }

	public BoatContainer(String name, Block baseBlock, boolean floatsOnLava) {
		this(name, baseBlock, floatsOnLava, BoatSettings());
	}
	public BoatContainer(String name, Block baseBlock, boolean floatsOnLava, Item.Settings itemSettings) {
		type = ModBoatType.Register(new ModBoatType(baseBlock, name, this::getItem, floatsOnLava));
		item = new ModBoatItem(type, itemSettings);
	}
	public static Item.Settings BoatSettings() {
		return ModBase.ItemSettings().maxCount(1);
	}

	@Override
	public boolean contains(Block block) { return false; }

	@Override
	public boolean contains(Item item) { return item == this.item; }

	public BoatContainer fuel(int fuelTime) {
		FuelRegistry.INSTANCE.add(getItem(), fuelTime);
		return this;
	}
	public BoatContainer compostable(float chance) {
		CompostingChanceRegistry.INSTANCE.add(getItem(), chance);
		return this;
	}
	public BoatContainer dispenser(DispenserBehavior behavior) {
		DispenserBlock.registerBehavior(getItem(), behavior);
		return this;
	}
}
