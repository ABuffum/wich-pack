package fun.mousewich.entity;

import fun.mousewich.ModBase;
import fun.mousewich.entity.vehicle.ModBoatType;
import fun.mousewich.mixins.entity.BoatEntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class ModBoatEntity extends BoatEntity {
	private static final TrackedData<Integer> BOAT_TYPE;

	public ModBoatEntity(EntityType<? extends BoatEntity> type, World world) {
		super(type, world);
	}

	public ModBoatEntity(World worldIn, double x, double y, double z) {
		this(ModBase.BOAT_ENTITY, worldIn);
		this.setPosition(x, y, z);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(BOAT_TYPE, 0);
	}
	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putString("HavenType", this.getHavenBoatType().getName());
	}
	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("HavenType", 8)) {
			this.setHavenBoatType(ModBoatType.getType(nbt.getString("HavenType")));
		}
	}

	public void setHavenBoatType(ModBoatType type) {
		this.dataTracker.set(BOAT_TYPE, type.ordinal());
	}

	public ModBoatType getHavenBoatType() {
		return ModBoatType.getType(this.dataTracker.get(BOAT_TYPE));
	}

	@Override
	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
		((BoatEntityAccessor)this).setFallVelocity(this.getVelocity().y);
		if (!this.hasVehicle()) {
			if (onGround) {
				if (this.fallDistance > 3.0F) {
					if (((BoatEntityAccessor)this).getLocation() != Location.ON_LAND) {
						this.fallDistance = 0.0F;
						return;
					}

					this.handleFallDamage(this.fallDistance, 1.0F, DamageSource.FALL);
					if (!this.world.isClient && !this.isRemoved()) {
						this.kill();
						if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
							int j;
							for(j = 0; j < 3; ++j) {
								this.dropItem(this.getHavenBoatType().getBaseBlock());
							}

							for(j = 0; j < 2; ++j) {
								this.dropItem(Items.STICK);
							}
						}
					}
				}

				this.fallDistance = 0.0F;
			} else if (!this.world.getFluidState(this.getBlockPos().down()).isIn(FluidTags.WATER) && heightDifference < 0.0D) {
				if (!this.world.getFluidState(this.getBlockPos().down()).isIn(FluidTags.LAVA)) {
					this.fallDistance = (float)((double)this.fallDistance - heightDifference);
				}
			}

		}
	}

	public boolean floatsOnLava() { return getHavenBoatType().floatsOnLava; }

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (floatsOnLava()) {
			//TODO: Nonflammable boats should float on lava but until that's working, they should be destroyed
			//if (damageSource.isFire() || damageSource == DamageSource.LAVA) return true;
		}
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean doesRenderOnFire() {
		return !floatsOnLava() && super.doesRenderOnFire();
	}

	@Override
	public Item asItem() {
		return getHavenBoatType().GetItem();
	}

	@Override
	protected boolean canAddPassenger(Entity passenger) {
		return this.getPassengerList().size() < 2 && !this.isSubmergedIn(FluidTags.WATER) && !this.isSubmergedIn(FluidTags.LAVA);
	}

	static {
		BOAT_TYPE = DataTracker.registerData(ModBoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}
}
