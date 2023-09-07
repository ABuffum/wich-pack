package fun.mousewich.entity;

import fun.mousewich.ModBase;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class SummonedAnvilEntity extends Entity {
	public int timeFalling;
	protected static final TrackedData<BlockPos> BLOCK_POS = DataTracker.registerData(SummonedAnvilEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
	public SummonedAnvilEntity(EntityType<? extends SummonedAnvilEntity> entityType, World world) { super(entityType, world); }
	public SummonedAnvilEntity(World world, double x, double y, double z) {
		this(ModBase.SUMMONED_ANVIL_ENTITY, world);
		this.intersectionChecked = true;
		this.setPosition(x, y, z);
		this.setVelocity(Vec3d.ZERO);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
		this.setFallingBlockPos(this.getBlockPos());
	}
	@Override
	public boolean isAttackable() { return false; }
	public void setFallingBlockPos(BlockPos pos) { this.dataTracker.set(BLOCK_POS, pos); }
	public BlockPos getFallingBlockPos() { return this.dataTracker.get(BLOCK_POS); }
	@Override
	protected Entity.MoveEffect getMoveEffect() { return Entity.MoveEffect.NONE; }
	@Override
	protected void initDataTracker() { this.dataTracker.startTracking(BLOCK_POS, BlockPos.ORIGIN); }
	@Override
	public boolean collides() { return !this.isRemoved(); }
	@Override
	public void tick() {
		++this.timeFalling;
		if (!this.hasNoGravity()) this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
		this.move(MovementType.SELF, this.getVelocity());
		if (!this.world.isClient) {
			BlockPos blockPos = this.getBlockPos();
			if (this.onGround) {
				BlockState blockState = this.world.getBlockState(blockPos);
				this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
				if (!blockState.isOf(Blocks.MOVING_PISTON)) {
					this.discard();
					if (!isSilent()) world.syncWorldEvent(WorldEvents.ANVIL_DESTROYED, blockPos, 0);
				}
			}
			else if (!(this.world.isClient || (this.timeFalling <= 100 || blockPos.getY() > this.world.getBottomY() && blockPos.getY() <= this.world.getTopY()) && this.timeFalling <= 600)) {
				this.discard();
			}
		}
		this.setVelocity(this.getVelocity().multiply(0.98));
	}
	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		int i = MathHelper.ceil(fallDistance - 1.0f);
		if (i < 0) return false;
		float f = Math.min(MathHelper.floor(i * 2), 40);
		this.world.getOtherEntities(this, this.getBoundingBox(), EntityPredicates.EXCEPT_SPECTATOR).forEach(entity -> entity.damage(DamageSource.ANVIL, f));
		return false;
	}
	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) { nbt.putInt("Time", this.timeFalling); }
	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) { this.timeFalling = nbt.getInt("Time"); }
	@Override
	public boolean doesRenderOnFire() { return false; }
	@Override
	public boolean entityDataRequiresOperator() { return true; }
	@Override
	public Packet<?> createSpawnPacket() { return new EntitySpawnS2CPacket(this); }
	@Override
	public void onSpawnPacket(EntitySpawnS2CPacket packet) {
		super.onSpawnPacket(packet);
		this.intersectionChecked = true;
		double d = packet.getX();
		double e = packet.getY();
		double f = packet.getZ();
		this.setPosition(d, e, f);
		this.setFallingBlockPos(this.getBlockPos());
	}
}
