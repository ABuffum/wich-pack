package fun.mousewich.mixins.client.network;

import fun.mousewich.mixins.client.sound.GuardianAttackSoundInstanceAccessor;
import fun.mousewich.sound.ElderGuardianAttackSoundInstance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.sound.GuardianAttackSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin implements ClientPlayPacketListener {

	@ModifyArg(method="onEntityStatus", at = @At(value="INVOKE", target="Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;)V"))
	public SoundInstance HandleGuardianAttackSound(SoundInstance sound) {
		if (sound instanceof GuardianAttackSoundInstance instance) {
			GuardianEntity guardian = ((GuardianAttackSoundInstanceAccessor)instance).getGuardian();
			if (guardian instanceof ElderGuardianEntity elderGuardianEntity) {
				return new ElderGuardianAttackSoundInstance(elderGuardianEntity);
			}
		}
		return sound;
	}
}
