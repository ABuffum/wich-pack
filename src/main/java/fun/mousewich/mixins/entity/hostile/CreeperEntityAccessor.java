package fun.mousewich.mixins.entity.hostile;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Collection;

@Mixin(CreeperEntity.class)
public interface CreeperEntityAccessor {
	@Accessor("lastFuseTime")
	int getLastFuseTime();
	@Accessor("lastFuseTime")
	void setLastFuseTime(int value);
	@Accessor("currentFuseTime")
	int getCurrentFuseTime();
	@Accessor("currentFuseTime")
	void setCurrentFuseTime(int value);
	@Accessor("fuseTime")
	int getFuseTime();
	@Accessor("explosionRadius")
	int getExplosionRadius();
	@Accessor("headsDropped")
	int getHeadsDropped();

	@Invoker("spawnEffectsCloud")
	void invokeSpawnEffectsCloud();
}
