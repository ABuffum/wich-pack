package fun.wich.entity.passive.camel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import fun.wich.entity.ModEntityType;
import fun.wich.entity.ai.ModMemoryModules;
import fun.wich.entity.ai.sensor.ModSensorTypes;
import fun.wich.entity.ai.task.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.function.Predicate;

public class CamelBrain {
	private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(ModMemoryModules.IS_PANICKING, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, ModMemoryModules.GAZE_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.BREED_TARGET, MemoryModuleType.NEAREST_VISIBLE_ADULT);
	public static Brain.Profile<CamelEntity> createProfile() {
		ImmutableList<SensorType<? extends Sensor<? super CamelEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, ModSensorTypes.CAMEL_TEMPTATIONS_SENSOR.get(), SensorType.NEAREST_ADULT);
		return Brain.createProfile(MEMORY_MODULES, SENSORS);
	}
	protected static Brain<?> create(Brain<CamelEntity> brain) {
		CamelBrain.addCoreActivities(brain);
		CamelBrain.addIdleActivities(brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();
		return brain;
	}
	private static void addCoreActivities(Brain<CamelEntity> brain) {
		brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
				new StayAboveWaterTask(0.8f),
				new CamelWalkTask(4.0f),
				new LookAroundTask(45, 90),
				new WanderAroundTask(),
				new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
				new TemptationCooldownTask(ModMemoryModules.GAZE_COOLDOWN_TICKS)));
	}
	private static void addIdleActivities(Brain<CamelEntity> brain) {
		brain.setTaskList(Activity.IDLE, ImmutableList.of(
				Pair.of(0, new FollowMobWithIntervalTask(EntityType.PLAYER, UniformIntProvider.create(30, 60), 6.0f)),
				Pair.of(1, new BreedTask(ModEntityType.CAMEL_ENTITY, 1.0f)), Pair.of(2, new TemptTask(entity -> 2.5f)),
				Pair.of(3, new WalkTowardsClosestAdultTask<>(5, 16, 2.5f)),
				Pair.of(4, new RandomLookAroundTask(UniformIntProvider.create(150, 250), 30.0f, 0.0f, 0.0f)),
				Pair.of(5, new RandomTask<>(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), ImmutableList.of(
						Pair.of(new StrollTask(2.0f), 1),
						Pair.of(new GoTowardsLookTargetTask<>(Predicate.not(CamelEntity::isStationary), 3, 2.0f), 1),
						Pair.of(new SitOrStandTask(20), 1),
						Pair.of(new WaitTask(30, 60), 1))))));
	}
	public static void updateActivities(CamelEntity camel) {
		camel.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
	}
	public static Ingredient getBreedingIngredient() { return CamelEntity.BREEDING_INGREDIENT; }
	public static class CamelWalkTask extends WalkTask {
		public CamelWalkTask(float f) { super(f); }
		@Override
		protected void run(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
			if (pathAwareEntity instanceof CamelEntity camelEntity) camelEntity.setStanding();
			super.run(serverWorld, pathAwareEntity, l);
		}
	}
	public static class SitOrStandTask extends Task<CamelEntity> {
		private final int lastPoseTickDelta;
		public SitOrStandTask(int lastPoseSecondsDelta) {
			super(ImmutableMap.of());
			this.lastPoseTickDelta = lastPoseSecondsDelta * 20;
		}
		@Override
		protected boolean shouldRun(ServerWorld serverWorld, CamelEntity camelEntity) {
			return !camelEntity.isTouchingWater() && camelEntity.getLastPoseTickDelta() >= (long)this.lastPoseTickDelta && !camelEntity.isLeashed() && camelEntity.isOnGround() && !camelEntity.hasPrimaryPassenger();
		}
		@Override
		protected void run(ServerWorld serverWorld, CamelEntity camelEntity, long l) {
			if (camelEntity.isSitting()) camelEntity.startStanding();
			else if (!camelEntity.isPanicking()) camelEntity.startSitting();
		}
	}
}
