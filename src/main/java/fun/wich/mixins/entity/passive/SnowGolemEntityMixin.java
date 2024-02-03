package fun.wich.mixins.entity.passive;

import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.entity.variants.SnowGolemVariant;
import fun.wich.origins.power.MobHostilityPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowGolemEntity.class)
public abstract class SnowGolemEntityMixin extends GolemEntity implements Shearable, RangedAttackMob, EntityWithBloodType {
	protected SnowGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="initGoals", at = @At("TAIL"))
	protected void InitGoals(CallbackInfo ci) {
		this.targetSelector.add(1, MobHostilityPower.makeHostilityGoal(this, EntityType.SNOW_GOLEM));
	}
	@Inject(method="initDataTracker", at=@At("TAIL"))
	protected void AddVariantToDataTracker(CallbackInfo ci) { this.dataTracker.startTracking(SnowGolemVariant.VARIANT, 0); }
	@Inject(method="writeCustomDataToNbt", at=@At("TAIL"))
	public void AddVariantToNbt(NbtCompound nbt, CallbackInfo ci) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Variant", SnowGolemVariant.getVariant((SnowGolemEntity)(Object)this));
	}

	@Inject(method="readCustomDataFromNbt", at=@At("TAIL"))
	public void ReadVariantFromNbt(NbtCompound nbt, CallbackInfo ci) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("Variant")) SnowGolemVariant.setVariant((SnowGolemEntity)(Object)this, nbt.getInt("Variant"));
	}

	@Inject(method="sheared", at=@At(value="INVOKE", target="Lnet/minecraft/entity/passive/SnowGolemEntity;dropStack(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/ItemEntity;"), cancellable=true)
	protected void DropPumpkin(SoundCategory shearedSoundCategory, CallbackInfo ci) {
		SnowGolemVariant variant = SnowGolemVariant.get((SnowGolemEntity)(Object)this);
		if (variant != SnowGolemVariant.DEFAULT) {
			this.dropStack(new ItemStack(variant.item), 1.7f);
			ci.cancel();
		}
	}

	@Override public BloodType GetDefaultBloodType() { return BloodType.WATER; }
}
