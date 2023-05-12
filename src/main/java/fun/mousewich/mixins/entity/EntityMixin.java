package fun.mousewich.mixins.entity;

import fun.mousewich.event.ModGameEvent;
import fun.mousewich.gen.data.tag.ModBlockTags;
import fun.mousewich.origins.power.FireImmunePower;
import fun.mousewich.origins.power.PowersUtil;
import fun.mousewich.sound.IdentifiedSounds;
import fun.mousewich.sound.ModSoundEvents;
import fun.mousewich.sound.SoundUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput {

	@Shadow @Final protected Random random;
	@Shadow public int age;
	@Shadow public World world;
	@Shadow private int lastChimeAge;
	@Shadow private float lastChimeIntensity;

	@Shadow public abstract void emitGameEvent(GameEvent event, @Nullable Entity entity, BlockPos pos);
	@Shadow public abstract void emitGameEvent(GameEvent event, @Nullable Entity entity);
	@Shadow public abstract void emitGameEvent(GameEvent event, BlockPos pos);
	@Shadow public abstract void emitGameEvent(GameEvent event);
	@Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);
	@Shadow public abstract BlockPos getBlockPos();
	@Shadow public abstract BlockState getBlockStateAtPos();

	@Inject(method="kill", at = @At("TAIL"))
	public void Kill(CallbackInfo ci) { this.emitGameEvent(ModGameEvent.ENTITY_DIE); }

	@Inject(method="playStepSound", at = @At("HEAD"), cancellable = true)
	protected void PlayStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
		if (state.getMaterial().isLiquid()) return;
		BlockState blockState = this.world.getBlockState(pos.up());
		blockState = blockState.isIn(BlockTags.INSIDE_STEP_SOUND_BLOCKS) || blockState.isIn(BlockTags.CARPETS) ? blockState : state;
		if (blockState.isOf(Blocks.BARRIER) || blockState.isOf(Blocks.STRUCTURE_VOID)) { ci.cancel(); return; }
		SoundUtil.playIdentifiedStepSound((Entity)(Object)this);
		SoundEvent stepSound = IdentifiedSounds.getStepSound(blockState);
		if (stepSound != null) {
			this.playSound(stepSound, 0.15f, 1);
			ci.cancel();
		}
	}
	@Inject(method="getSwimSound", at = @At("HEAD"), cancellable = true)
	protected void GetSwimSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent swimSound = IdentifiedSounds.getSwimSound((Entity)(Object)this);
		if (swimSound != null) cir.setReturnValue(swimSound);
	}
	@Inject(method="getSplashSound", at = @At("HEAD"), cancellable = true)
	protected void GetSplashSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getSplashSound((Entity)(Object)this);
		if (sound != null) cir.setReturnValue(sound);
	}
	@Inject(method="getHighSpeedSplashSound", at = @At("HEAD"), cancellable = true)
	protected void GetHighSpeedSplashSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getHighSpeedSplashSound((Entity)(Object)this);
		if (sound != null) cir.setReturnValue(sound);
	}
	@Inject(method="addPassenger", at = @At("TAIL"))
	protected void AddPassenger(Entity passenger, CallbackInfo ci) {
		this.emitGameEvent(ModGameEvent.ENTITY_MOUNT, passenger);
	}
	@Inject(method="removePassenger", at = @At("TAIL"))
	protected void RemovePassenger(Entity passenger, CallbackInfo ci) {
		this.emitGameEvent(ModGameEvent.ENTITY_DISMOUNT, passenger);
	}

	@Inject(method="playAmethystChimeSound", at = @At("HEAD"))
	private void playEchoChimeSound(BlockState state, CallbackInfo ci) {
		if (state.isIn(ModBlockTags.ECHO_SOUND_BLOCKS) && this.age >= this.lastChimeAge + 20) {
			this.lastChimeIntensity *= (float)Math.pow(0.997, this.age - this.lastChimeAge);
			this.lastChimeIntensity = Math.min(1.0f, this.lastChimeIntensity + 0.07f);
			float f = 0.5f + this.lastChimeIntensity * this.random.nextFloat() * 1.2f;
			float g = 0.1f + this.lastChimeIntensity * 1.2f;
			this.playSound(ModSoundEvents.BLOCK_ECHO_BLOCK_CHIME, g, f);
			this.lastChimeAge = this.age;
		}
	}

	@Inject(method="isFireImmune", at=@At("HEAD"), cancellable=true)
	public void IsFireImmune(CallbackInfoReturnable<Boolean> cir) {
		if (PowersUtil.Active((Entity)(Object)this, FireImmunePower.class)) cir.setReturnValue(true);
	}
}
