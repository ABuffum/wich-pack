package fun.mousewich.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import fun.mousewich.entity.ai.ModMemoryModules;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.IntProvider;

import java.util.Random;

public class RandomLookAroundTask extends Task<MobEntity> {
	private final IntProvider cooldown;
	private final float maxYaw;
	private final float minPitch;
	private final float pitchRange;

	public RandomLookAroundTask(IntProvider cooldown, float maxYaw, float minPitch, float maxPitch) {
		super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_ABSENT, ModMemoryModules.GAZE_COOLDOWN_TICKS, MemoryModuleState.VALUE_ABSENT));
		if (minPitch > maxPitch) throw new IllegalArgumentException("Minimum pitch is larger than maximum pitch! " + minPitch + " > " + maxPitch);
		this.cooldown = cooldown;
		this.maxYaw = maxYaw;
		this.minPitch = minPitch;
		this.pitchRange = maxPitch - minPitch;
	}

	@Override
	protected void run(ServerWorld serverWorld, MobEntity mobEntity, long l) {
		Random random = mobEntity.getRandom();
		float f = MathHelper.clamp(random.nextFloat() * this.pitchRange + this.minPitch, -90.0f, 90.0f);
		float g = MathHelper.wrapDegrees(mobEntity.getYaw() + 2.0f * random.nextFloat() * this.maxYaw - this.maxYaw);
		Vec3d vec3d = Vec3d.fromPolar(f, g);
		mobEntity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(new BlockPos(mobEntity.getEyePos().add(vec3d))));
		mobEntity.getBrain().remember(ModMemoryModules.GAZE_COOLDOWN_TICKS, this.cooldown.get(random));
	}
}
