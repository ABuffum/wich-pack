package fun.mousewich.mixins.server;

import com.mojang.authlib.GameProfile;
import fun.mousewich.event.ModGameEvent;
import fun.mousewich.item.RecoveryCompassItem;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
	public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) { super(world, pos, yaw, profile); }

	@Inject(method="onDeath", at = @At("HEAD"))
	public void onDeath(DamageSource source, CallbackInfo ci) { this.emitGameEvent(ModGameEvent.ENTITY_DIE); }

	@Inject(method="onDeath", at = @At("TAIL"))
	public void RecoveryCompassDeathSet(DamageSource source, CallbackInfo ci) { RecoveryCompassItem.SetDeath(this); }
}
