package fun.mousewich.entity.projectile;

import fun.mousewich.ModBase;
import fun.mousewich.command.ChorusCommand;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ChorusArrowEntity extends ModArrowEntity {
	public ChorusArrowEntity(EntityType<? extends ModArrowEntity> entityType, World world) { super(entityType, world); }
	public ChorusArrowEntity(World world, LivingEntity owner) { super(ModBase.CHORUS_ARROW.getEntityType(), world, owner); }
	public ChorusArrowEntity(World world, double x, double y, double z) { super(ModBase.CHORUS_ARROW.getEntityType(), world, x, y, z); }
	@Override
	protected ItemStack asItemStack() { return new ItemStack(ModBase.CHORUS_ARROW); }
	@Override
	protected void onHit(LivingEntity target) {
		super.onHit(target);
		ChorusCommand.TeleportEntity(target);
	}
}
