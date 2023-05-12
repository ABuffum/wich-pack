package fun.mousewich.entity.ai.goal.slime;

import fun.mousewich.entity.ai.SlimeMoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SlimeEntity;

import java.util.EnumSet;

public class RandomLookGoal extends Goal {
	private final SlimeEntity slime;
	private float targetYaw;
	private int timer;

	public RandomLookGoal(SlimeEntity slime) {
		this.slime = slime;
		this.setControls(EnumSet.of(Goal.Control.LOOK));
	}

	@Override
	public boolean canStart() {
		return this.slime.getTarget() == null && (this.slime.isOnGround() || this.slime.isTouchingWater() || this.slime.isInLava() || this.slime.hasStatusEffect(StatusEffects.LEVITATION)) && this.slime.getMoveControl() instanceof SlimeMoveControl;
	}

	@Override
	public void tick() {
		if (--this.timer <= 0) {
			this.timer = this.getTickCount(40 + this.slime.getRandom().nextInt(60));
			this.targetYaw = this.slime.getRandom().nextInt(360);
		}
		((SlimeMoveControl)this.slime.getMoveControl()).look(this.targetYaw, false);
	}
}
