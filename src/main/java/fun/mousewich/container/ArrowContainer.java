package fun.mousewich.container;

import fun.mousewich.ModFactory;
import fun.mousewich.entity.projectile.ModArrowEntity;
import fun.mousewich.item.projectile.ModArrowItem;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

import java.awt.*;

public class ArrowContainer extends Container implements ItemConvertible {
	private final ModArrowItem item;
	@Override
	public Item asItem() { return item; }
	private final EntityType<ModArrowEntity> entityType;
	public EntityType<ModArrowEntity> getEntityType() { return entityType; }

	public ArrowContainer(ModArrowItem.ShotFactory shot, ModArrowItem.DispensedFactory dispensed, EntityType.EntityFactory<ModArrowEntity> entityFactory) {
		this.item = new ModArrowItem(shot, dispensed);
		this.entityType = ModFactory.MakeArrowEntity(entityFactory);
	}

}
