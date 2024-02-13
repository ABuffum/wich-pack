package fun.wich.entity.ai.goal;

import fun.wich.entity.passive.RedPandaEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;

import java.util.EnumSet;

public class StandGroundGoal extends Goal {
	protected final RedPandaEntity panda;
	private final boolean pauseWhenMobIdle;

	protected float distance;
	protected float sqrDistance;

	public StandGroundGoal(RedPandaEntity panda, float distance, boolean pauseWhenMobIdle) {
		this.panda = panda;
		this.distance = distance;
		this.sqrDistance = distance * distance;
		this.pauseWhenMobIdle = pauseWhenMobIdle;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean canStart() {
		long l = panda.world.getTime();
		LivingEntity livingEntity = panda.getTarget();
		if (livingEntity == null) return false;
		if (!livingEntity.isAlive()) return false;
		return panda.squaredDistanceTo(livingEntity) <= this.sqrDistance;
	}

	@Override
	public boolean shouldContinue() {
		LivingEntity livingEntity = panda.getTarget();
		if (livingEntity == null) return false;
		if (!livingEntity.isAlive()) return false;
		if (!this.pauseWhenMobIdle) return !panda.getNavigation().isIdle();
		if (panda.squaredDistanceTo(livingEntity) > this.sqrDistance) return false;
		return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity)livingEntity).isCreative();
	}

	@Override
	public void start() {
		panda.getNavigation().stop();
		panda.SetStanding(true);
	}

	@Override
	public void stop() {
		LivingEntity livingEntity = panda.getTarget();
		if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
			panda.setTarget(null);
		}
		panda.SetStanding(false);
	}

	@Override
	public boolean shouldRunEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		LivingEntity livingEntity = panda.getTarget();
		if (livingEntity == null) return;
		panda.getLookControl().lookAt(livingEntity, 30.0f, 30.0f);
	}
}