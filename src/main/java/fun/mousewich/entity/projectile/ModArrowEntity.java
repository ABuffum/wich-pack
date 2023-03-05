package fun.mousewich.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public abstract class ModArrowEntity extends PersistentProjectileEntity {
	public ModArrowEntity(EntityType<? extends ModArrowEntity> entityType, World world) { super(entityType, world); }
	public ModArrowEntity(EntityType<? extends ModArrowEntity> entityType, World world, LivingEntity owner) { super(entityType, owner, world); }
	public ModArrowEntity(EntityType<? extends ModArrowEntity> entityType, World world, double x, double y, double z) { super(entityType, x, y, z, world); }
	@Override
	public void tick() { super.tick(); }
	@Override
	protected void onHit(LivingEntity target) { super.onHit(target); }
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) { super.readCustomDataFromNbt(nbt); }
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) { super.writeCustomDataToNbt(nbt); }
}
