package fun.mousewich.entity.warden;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import fun.mousewich.ModBase;
import fun.mousewich.entity.ModActivities;
import fun.mousewich.entity.ai.task.DismountVehicleTask;
import fun.mousewich.entity.ai.ModMemoryModules;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class WardenBrain {
	private static final int DIG_DURATION = MathHelper.ceil(100.0f);
	public static final int EMERGE_DURATION = MathHelper.ceil(133.59999f);
	public static final int ROAR_DURATION = MathHelper.ceil(84.0f);
	private static final int SNIFF_DURATION = MathHelper.ceil(83.2f);
	public static final int DIG_COOLDOWN = 1200;
	private static final List<SensorType<? extends Sensor<? super WardenEntity>>> SENSORS = List.of(SensorType.NEAREST_PLAYERS, ModBase.WARDEN_ENTITY_SENSOR.get());
	private static final List<MemoryModuleType<?>> MEMORY_MODULES = List.of(MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_ATTACKABLE, ModMemoryModules.ROAR_TARGET, ModMemoryModules.DISTURBANCE_LOCATION, ModMemoryModules.RECENT_PROJECTILE, ModMemoryModules.IS_SNIFFING, ModMemoryModules.IS_EMERGING, ModMemoryModules.ROAR_SOUND_DELAY, ModMemoryModules.DIG_COOLDOWN, ModMemoryModules.ROAR_SOUND_COOLDOWN, ModMemoryModules.SNIFF_COOLDOWN, ModMemoryModules.TOUCH_COOLDOWN, ModMemoryModules.VIBRATION_COOLDOWN, ModMemoryModules.SONIC_BOOM_COOLDOWN, ModMemoryModules.SONIC_BOOM_SOUND_COOLDOWN, ModMemoryModules.SONIC_BOOM_SOUND_DELAY);
	private static final Task<WardenEntity> RESET_DIG_COOLDOWN_TASK = new Task<WardenEntity>(ImmutableMap.of(ModMemoryModules.DIG_COOLDOWN, MemoryModuleState.REGISTERED)){
		@Override
		protected void run(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
			WardenBrain.resetDigCooldown(wardenEntity);
		}
	};
	public static void updateActivities(WardenEntity warden) {
		warden.getBrain().resetPossibleActivities(ImmutableList.of(ModActivities.EMERGE, ModActivities.DIG, ModActivities.ROAR, Activity.FIGHT, ModActivities.INVESTIGATE, ModActivities.SNIFF, Activity.IDLE));
	}
	protected static Brain<?> create(WardenEntity warden, Dynamic<?> dynamic) {
		Brain.Profile<WardenEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
		Brain<WardenEntity> brain = profile.deserialize(dynamic);
		WardenBrain.addCoreActivities(brain);
		WardenBrain.addEmergeActivities(brain);
		WardenBrain.addDigActivities(brain);
		WardenBrain.addIdleActivities(brain);
		WardenBrain.addRoarActivities(brain);
		WardenBrain.addFightActivities(warden, brain);
		WardenBrain.addInvestigateActivities(brain);
		WardenBrain.addSniffActivities(brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();
		return brain;
	}
	private static void addCoreActivities(Brain<WardenEntity> brain) {
		brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new StayAboveWaterTask(0.8f), new LookAtDisturbanceTask(), new LookAroundTask(45, 90), new WanderAroundTask()));
	}
	private static void addEmergeActivities(Brain<WardenEntity> brain) {
		brain.setTaskList(ModActivities.EMERGE, 5, ImmutableList.of(new EmergeTask<>(EMERGE_DURATION)), ModMemoryModules.IS_EMERGING);
	}
	private static void addDigActivities(Brain<WardenEntity> brain) {
		brain.setTaskList(ModActivities.DIG, ImmutableList.of(
				Pair.of(0, new DismountVehicleTask()),
				Pair.of(1, new DigTask<>(DIG_DURATION))),
				ImmutableSet.of(
						Pair.of(ModMemoryModules.ROAR_TARGET, MemoryModuleState.VALUE_ABSENT),
						Pair.of(ModMemoryModules.DIG_COOLDOWN, MemoryModuleState.VALUE_ABSENT)));
	}
	private static void addIdleActivities(Brain<WardenEntity> brain) {
		brain.setTaskList(Activity.IDLE, 10, ImmutableList.of(
				new FindRoarTargetTask<>(WardenEntity::getPrimeSuspect),
				new StartSniffingTask(),
				new RandomTask<>(ImmutableMap.of(ModMemoryModules.IS_SNIFFING, MemoryModuleState.VALUE_ABSENT),
						ImmutableList.of(Pair.of(new StrollTask(0.5f), 2), Pair.of(new WaitTask(30, 60), 1)))));
	}
	private static void addInvestigateActivities(Brain<WardenEntity> brain) {
		brain.setTaskList(ModActivities.INVESTIGATE, 5, ImmutableList.of(
				new FindRoarTargetTask<>(WardenEntity::getPrimeSuspect),
				new WardenGoToCelebrateTask<>(ModMemoryModules.DISTURBANCE_LOCATION, 2, 0.7f)), ModMemoryModules.DISTURBANCE_LOCATION);
	}
	private static void addSniffActivities(Brain<WardenEntity> brain) {
		brain.setTaskList(ModActivities.SNIFF, 5, ImmutableList.of(new FindRoarTargetTask<>(WardenEntity::getPrimeSuspect), new SniffTask<>(SNIFF_DURATION)), ModMemoryModules.IS_SNIFFING);
	}
	private static void addRoarActivities(Brain<WardenEntity> brain) {
		brain.setTaskList(ModActivities.ROAR, 10, ImmutableList.of(new RoarTask()), ModMemoryModules.ROAR_TARGET);
	}
	private static void addFightActivities(WardenEntity warden, Brain<WardenEntity> brain) {
		brain.setTaskList(Activity.FIGHT, 10, ImmutableList.of(RESET_DIG_COOLDOWN_TASK,
				new WardenForgetAttackTargetTask<WardenEntity>(
						entity -> !warden.getAngriness().isAngry() || !warden.isValidTarget(entity),
						WardenBrain::removeDeadSuspect, false),
				new FollowMobTask(entity -> WardenBrain.isTargeting(warden, entity), (float)warden.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)), new RangedApproachTask(1.2f), new SonicBoomTask(), new MeleeAttackTask(18)), MemoryModuleType.ATTACK_TARGET);
	}
	private static boolean isTargeting(WardenEntity warden, LivingEntity entity2) {
		return warden.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter(entity -> entity == entity2).isPresent();
	}
	private static void removeDeadSuspect(WardenEntity warden, LivingEntity suspect) {
		if (!warden.isValidTarget(suspect)) warden.removeSuspect(suspect);
		WardenBrain.resetDigCooldown(warden);
	}
	public static void resetDigCooldown(LivingEntity warden) {
		if (warden.getBrain().hasMemoryModule(ModMemoryModules.DIG_COOLDOWN)) {
			warden.getBrain().remember(ModMemoryModules.DIG_COOLDOWN, Unit.INSTANCE, DIG_COOLDOWN);
		}
	}
	public static void lookAtDisturbance(WardenEntity warden, BlockPos pos) {
		if (!warden.world.getWorldBorder().contains(pos) || warden.getPrimeSuspect().isPresent() || warden.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).isPresent()) {
			return;
		}
		WardenBrain.resetDigCooldown(warden);
		warden.getBrain().remember(ModMemoryModules.SNIFF_COOLDOWN, Unit.INSTANCE, 100L);
		warden.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(pos), 100L);
		warden.getBrain().remember(ModMemoryModules.DISTURBANCE_LOCATION, pos, 100L);
		warden.getBrain().forget(MemoryModuleType.WALK_TARGET);
	}
}
