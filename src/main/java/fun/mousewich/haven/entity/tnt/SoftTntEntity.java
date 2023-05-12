package fun.mousewich.haven.entity.tnt;

import fun.mousewich.entity.tnt.ModTntEntity;
import fun.mousewich.haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class SoftTntEntity extends ModTntEntity {
	public SoftTntEntity(EntityType<? extends Entity> entityType, World world) { super(entityType, world, HavenMod.SOFT_TNT.asBlock()); }
	public SoftTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
		this(world, x, y, z, igniter, HavenMod.SOFT_TNT.asBlock());
	}
	public SoftTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter, Block block) {
		super(HavenMod.SOFT_TNT_ENTITY, world, x, y, z, igniter, block);
	}

	@Override
	protected void explode() {
		Explosion explosion = this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), -3F, Explosion.DestructionType.NONE);
		propagateExplosion(3);
	}
	@Override
	public boolean shouldDestroyBlocks() { return false; }
	@Override
	public float damageMultiplier() { return 0; }
	@Override
	public float knockbackMultiplier() { return 1.5F; }
}
