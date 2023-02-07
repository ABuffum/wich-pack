package fun.mousewich.mixins.entity;

import fun.mousewich.ModBase;
import fun.mousewich.event.ModGameEvent;
import fun.mousewich.gen.data.tag.ModItemTags;
import fun.mousewich.origins.powers.*;
import fun.mousewich.sound.IdentifiedSounds;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) { super(type, world); }

	@Inject(method="canFreeze", at = @At("HEAD"), cancellable = true)
	private void CannotFreeze(CallbackInfoReturnable<Boolean> cir) {
		if (PowersUtil.Active(this, CannotFreezePower.class)) cir.setReturnValue(false);
	}

	@Inject(method="getPreferredEquipmentSlot", at=@At("HEAD"), cancellable = true)
	private static void GetPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
		if (stack.isIn(ModItemTags.HEAD_WEARABLE_BLOCKS)) cir.setReturnValue(EquipmentSlot.HEAD);
	}

	@Inject(method="getStackInHand", at = @At("HEAD"), cancellable = true)
	public void GetStackInHand(Hand hand, CallbackInfoReturnable<ItemStack> cir) {
		if (hand == null) cir.setReturnValue(this.getEquippedStack(EquipmentSlot.MAINHAND));
	}
	@Shadow
	protected boolean dead;

	@Inject(method="onDeath", at = @At("HEAD"))
	public void OnDeath(DamageSource source, CallbackInfo ci) {
		if (!this.isRemoved() && !this.dead && this.world instanceof ServerWorld) {
			this.emitGameEvent(ModGameEvent.ENTITY_DIE);
			this.emitGameEvent(GameEvent.ENTITY_KILLED);
		}
	}
	@Inject(method="getDeathSound", at = @At("HEAD"), cancellable = true)
	protected void GetDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getDeathSound(this);
		if (sound != null) cir.setReturnValue(sound);
	}

	@Inject(method="applyDamage", at = @At(value = "INVOKE", target="Lnet/minecraft/entity/LivingEntity;setAbsorptionAmount(F)V", ordinal = 1))
	protected void ApplyDamage(DamageSource source, float amount, CallbackInfo ci) {
		this.emitGameEvent(ModGameEvent.ENTITY_DAMAGE);
	}

	@Shadow
	public abstract boolean hasStatusEffect(StatusEffect effect);
	@Shadow
	protected int roll;

	@Inject(method="tickFallFlying", at = @At("TAIL"))
	private void TickFallFlying(CallbackInfo ci) {
		boolean bl = this.getFlag(Entity.FALL_FLYING_FLAG_INDEX);
		if (bl && !this.onGround && !this.hasVehicle() && !this.hasStatusEffect(StatusEffects.LEVITATION)) {
			ItemStack itemStack = this.getEquippedStack(EquipmentSlot.CHEST);
			if (itemStack.isOf(Items.ELYTRA) && ElytraItem.isUsable(itemStack)) {
				if (!this.world.isClient && this.roll + 1 % 10 == 0) this.emitGameEvent(ModGameEvent.ELYTRA_GLIDE);
			}
		}
	}
	@Shadow
	public abstract boolean isUsingItem();
	@Inject(method="clearActiveItem", at = @At("HEAD"))
	public void ClearActiveItem(CallbackInfo ci) {
		if (!this.world.isClient && (this.isUsingItem())) this.emitGameEvent(ModGameEvent.ITEM_INTERACT_FINISH);
	}
	@Shadow
	public abstract ItemStack getStackInHand(Hand hand);
	@Inject(method="setCurrentHand", at = @At("HEAD"))
	public void SetCurrentHand(Hand hand, CallbackInfo ci) {
		if (this.getStackInHand(hand).isEmpty() || this.isUsingItem()) return;
		if (!this.world.isClient) this.emitGameEvent(ModGameEvent.ITEM_INTERACT_START);
	}

	@Inject(method="canBreatheInWater", at = @At("HEAD"), cancellable = true)
	public void CanBreatheInWater(CallbackInfoReturnable<Boolean> cir) {
		if (PowersUtil.Active(this, BreatheInWaterPower.class)) cir.setReturnValue(true);
	}

	@Inject(method="getNextAirUnderwater", at = @At("HEAD"), cancellable = true)
	public void GetNextAirUnderwater(int air, CallbackInfoReturnable<Integer> cir) {
		int respiration = BiggerLungsPower.getRespiration(this);
		if (respiration > 0 && this.random.nextInt(respiration + 1) > 0) {
			cir.setReturnValue(air);
		}
	}

	@ModifyArg(method="travel", at = @At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"))
	private SoundEvent CrashSound(SoundEvent par1) {
		SoundEvent crashSound = IdentifiedSounds.getCrashSound(this);
		if (crashSound != null) return crashSound;
		return ModSoundEvents.ENTITY_GENERIC_ELYTRA_CRASH;
	}

	@Inject(method="canEnterTrapdoor", at = @At("HEAD"), cancellable = true)
	private void CanEnterTrapdoor(BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.get(TrapdoorBlock.OPEN)) {
			BlockState blockState = this.world.getBlockState(pos.down());
			if (blockState.getBlock() instanceof LadderBlock) {
				cir.setReturnValue(blockState.get(LadderBlock.FACING) == state.get(TrapdoorBlock.FACING));
			}
		}
	}

	@Shadow
	public abstract ItemStack getEquippedStack(EquipmentSlot var1);
	@Shadow
	public abstract float getArmorVisibility();

	@Inject(method="getAttackDistanceScalingFactor", at = @At("HEAD"), cancellable = true)
	public void GetAttackDistanceScalingFactor(Entity entity, CallbackInfoReturnable<Double> cir) {
		if (entity != null) {
			ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);
			if (itemStack.isOf(ModBase.PIGLIN_HEAD.asItem())) {
				double d = 1.0;
				if (this.isSneaky()) d *= 0.8;
				if (this.isInvisible()) d *= 0.7 * Math.max(0.1, this.getArmorVisibility());
				EntityType<?> entityType = entity.getType();
				if (entityType == EntityType.PIGLIN || entityType == EntityType.PIGLIN_BRUTE) cir.setReturnValue(d * 0.5);
			}
		}
	}
}
