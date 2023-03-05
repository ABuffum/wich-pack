package fun.mousewich.entity.projectile;

import fun.mousewich.ModBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AmethystArrowEntity extends ModArrowEntity {
	public AmethystArrowEntity(EntityType<? extends ModArrowEntity> entityType, World world) { super(entityType, world); }
	public AmethystArrowEntity(World world, LivingEntity owner) { super(ModBase.AMETHYST_ARROW.getEntityType(), world, owner); }
	public AmethystArrowEntity(World world, double x, double y, double z) { super(ModBase.AMETHYST_ARROW.getEntityType(), world, x, y, z); }

	@Override
	protected ItemStack asItemStack() { return new ItemStack(ModBase.AMETHYST_ARROW); }
}
