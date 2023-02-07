package fun.mousewich.mixins.entity.passive;

import com.google.common.collect.Lists;
import fun.mousewich.ModBase;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

@Mixin(ParrotEntity.class)
public class ParrotEntityMixin {
	@Shadow @Final static Map<EntityType<?>, SoundEvent> MOB_SOUNDS;

	@Inject(method="getRandomSound", at = @At("HEAD"), cancellable = true)
	private static void GetRandomSound(World world, Random random, CallbackInfoReturnable<SoundEvent> cir) {
		if (world.getDifficulty() != Difficulty.PEACEFUL && random.nextInt(1000) == 0) {
			ArrayList<EntityType<?>> list = Lists.newArrayList(MOB_SOUNDS.keySet());
			list.add(ModBase.WARDEN_ENTITY);
			EntityType<?> type = list.get(random.nextInt(list.size()));
			cir.setReturnValue(GetSound(type));
		}
	}

	@Inject(method="getSound", at = @At("HEAD"), cancellable = true)
	private static void GetSound(EntityType<?> imitate, CallbackInfoReturnable<SoundEvent> cir) {
		cir.setReturnValue(GetSound(imitate));
	}

	private static SoundEvent GetSound(EntityType<?> type) {
		if (type == ModBase.WARDEN_ENTITY) return ModSoundEvents.ENTITY_PARROT_IMITATE_WARDEN;
		return MOB_SOUNDS.getOrDefault(type, SoundEvents.ENTITY_PARROT_AMBIENT);
	}
}
