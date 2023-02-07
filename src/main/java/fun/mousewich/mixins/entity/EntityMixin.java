package fun.mousewich.mixins.entity;

import fun.mousewich.event.ModGameEvent;
import fun.mousewich.gen.data.tag.ModBlockTags;
import fun.mousewich.sound.IdentifiedSounds;
import fun.mousewich.sound.ModSoundEvents;
import fun.mousewich.sound.SoundUtil;
import fun.mousewich.sound.SubtitleOverrides;
import io.github.apace100.origins.power.OriginsPowerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
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
	@Inject(method="kill", at = @At("TAIL"))
	public void Kill(CallbackInfo ci) { ((Entity)(Object)this).emitGameEvent(ModGameEvent.ENTITY_DIE); }

	@Inject(method="playStepSound", at = @At("HEAD"), cancellable = true)
	protected void PlayStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
		if (state.getMaterial().isLiquid()) return;
		BlockState blockState = ((Entity)(Object)this).world.getBlockState(pos.up());
		blockState = blockState.isIn(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? blockState : state;
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

	@Shadow public int age;
	@Shadow private float lastChimeIntensity;
	@Shadow private int lastChimeAge;
	@Shadow @Final protected Random random;
	@Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

	@Shadow public abstract SoundCategory getSoundCategory();

	@Shadow public abstract BlockPos getBlockPos();

	@Shadow public abstract World getEntityWorld();

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

	@Inject(method="isSubmergedIn", at = @At("HEAD"), cancellable = true)
	public void IsSubmergedIn(TagKey<Fluid> fluidTag, CallbackInfoReturnable<Boolean> cir) {
		if (fluidTag == FluidTags.WATER) {
			if (OriginsPowerTypes.WATER_BREATHING.isActive((Entity)(Object)this)) {
				BlockState state = this.getEntityWorld().getBlockState(this.getBlockPos());
				if (state.isOf(Blocks.WATER_CAULDRON)) cir.setReturnValue(true);
			}
		}
	}
}
