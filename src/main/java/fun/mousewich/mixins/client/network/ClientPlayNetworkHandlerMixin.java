package fun.mousewich.mixins.client.network;

import fun.mousewich.entity.LastDeathPositionStoring;
import fun.mousewich.mixins.client.sound.GuardianAttackSoundInstanceAccessor;
import fun.mousewich.sound.ElderGuardianAttackSoundInstance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.sound.GuardianAttackSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.util.dynamic.GlobalPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin implements ClientPlayPacketListener {

	@Shadow @Final private MinecraftClient client;

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

	@Inject(method="onGameJoin", at=@At("TAIL"))
	public void StoreLastDeathOnPlayerJoin(GameJoinS2CPacket packet, CallbackInfo ci) {
		Optional<GlobalPos> pos = ((LastDeathPositionStoring)(Object)packet).getLastDeathPos();
		if (pos.isPresent()) ((LastDeathPositionStoring)this.client.player).setLastDeathPos(pos);
	}
	@Inject(method="onPlayerRespawn", at=@At("TAIL"))
	public void StoreLastDeathOnPlayerJoin(PlayerRespawnS2CPacket packet, CallbackInfo ci) {
		Optional<GlobalPos> pos = ((LastDeathPositionStoring)(Object)packet).getLastDeathPos();
		if (pos.isPresent()) ((LastDeathPositionStoring)this.client.player).setLastDeathPos(pos);
	}
}
