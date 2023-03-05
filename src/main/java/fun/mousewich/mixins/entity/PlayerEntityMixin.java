package fun.mousewich.mixins.entity;

import fun.mousewich.ModBase;
import fun.mousewich.block.sculk.SculkShriekerWarningManager;
import fun.mousewich.entity.warden.WardenEntity;
import fun.mousewich.event.ModGameEvent;
import fun.mousewich.item.RecoveryCompassItem;
import fun.mousewich.sound.IdentifiedSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	@Shadow public abstract PlayerInventory getInventory();

	@Shadow @Final private PlayerInventory inventory;

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="tick", at = @At("HEAD"))
	public void Tick(CallbackInfo ci) {
		if (!this.world.isClient) SculkShriekerWarningManager.getSculkShriekerWarningManager((PlayerEntity)(Object)(this)).tick();
	}

	@Inject(method="onDeath", at = @At("TAIL"))
	public void RecoveryCompassDeathSet(DamageSource source, CallbackInfo ci) {
		RecoveryCompassItem.SetDeath((PlayerEntity)(Object)this);
	}

	@Inject(method="getFallSounds", at = @At("HEAD"), cancellable = true)
	public void getFallSounds(CallbackInfoReturnable<FallSounds> cir) {
		LivingEntity.FallSounds fallSounds = IdentifiedSounds.getFallSounds(this);
		if (fallSounds != null) cir.setReturnValue(fallSounds);
	}
	@Inject(method="getSwimSound", at = @At("HEAD"), cancellable = true)
	protected void GetSwimSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getSwimSound(this);
		if (sound != null) cir.setReturnValue(sound);
	}
	@Inject(method="getSplashSound", at = @At("HEAD"), cancellable = true)
	protected void GetSplashSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getSplashSound(this);
		if (sound != null) cir.setReturnValue(sound);
	}
	@Inject(method="getHighSpeedSplashSound", at = @At("HEAD"), cancellable = true)
	protected void GetHighSpeedSplashSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getHighSpeedSplashSound(this);
		if (sound != null) cir.setReturnValue(sound);
	}
	@Inject(method="getDeathSound", at = @At("HEAD"), cancellable = true)
	protected void GetDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getDeathSound(this);
		if (sound != null) cir.setReturnValue(sound);
	}
	@Inject(method="takeShieldHit", at = @At("HEAD"))
	protected void takeShieldHit(LivingEntity attacker, CallbackInfo ci) {
		super.takeShieldHit(attacker);
		if (attacker instanceof WardenEntity) ((PlayerEntity)(Object)this).disableShield(true);
	}
	@Inject(method="updateTurtleHelmet", at = @At("HEAD"))
	private void updateTintedGoggles(CallbackInfo ci) {
		ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);
		if (itemStack.isOf(ModBase.TINTED_GOGGLES)) {
			if (!this.hasStatusEffect(ModBase.DARKNESS_EFFECT) && !this.hasStatusEffect(StatusEffects.BLINDNESS)) {
				this.addStatusEffect(new StatusEffectInstance(ModBase.TINTED_GOGGLES_EFFECT, 20, 0, false, false, true));
			}
			if (!this.world.isClient && this.hasStatusEffect(ModBase.FLASHBANGED_EFFECT)) {
				this.removeStatusEffect(ModBase.FLASHBANGED_EFFECT);
			}
		}
		else if (!this.world.isClient && this.hasStatusEffect(ModBase.TINTED_GOGGLES_EFFECT)) {
			this.removeStatusEffect(ModBase.TINTED_GOGGLES_EFFECT);
		}
	}

	@Inject(method="interact", at = @At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/item/ItemStack;useOnEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"))
	public void InjectEntityInteractEvent(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		this.world.emitGameEvent(ModGameEvent.ENTITY_INTERACT, entity.getBlockPos());
	}
}
