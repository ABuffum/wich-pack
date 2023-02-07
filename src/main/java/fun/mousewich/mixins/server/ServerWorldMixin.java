package fun.mousewich.mixins.server;

import fun.mousewich.origins.powers.PulsingSkinGlowPower;
import fun.mousewich.util.ServerEntityHandler;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.entity.EntityHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements StructureWorldAccess {
	protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> registryEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
		super(properties, registryRef, registryEntry, profiler, isClient, debugWorld, seed);
	}

	@ModifyArg(method="<init>", at = @At(value="INVOKE", target="Lnet/minecraft/server/world/ServerEntityManager;<init>(Ljava/lang/Class;Lnet/minecraft/world/entity/EntityHandler;Lnet/minecraft/world/storage/ChunkDataAccess;)V"), index = 1)
	private EntityHandler<Entity> InjectServerEntityHandler(EntityHandler<Entity> handler) {
		return new ServerEntityHandler((ServerWorld)(Object)this);
	}

	@Inject(method="tick", at = @At(value="INVOKE", target="Lnet/minecraft/server/world/ServerWorld;tickBlockEntities()V"))
	public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
		ServerWorld serverWorld = (ServerWorld)(Object)this;
		PulsingSkinGlowPower.tick(serverWorld);
	}
}
