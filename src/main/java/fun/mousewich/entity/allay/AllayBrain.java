package fun.mousewich.entity.allay;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import fun.mousewich.entity.ai.ModMemoryModules;
import fun.mousewich.entity.ai.task.GiveInventoryToLookTargetTask;
import fun.mousewich.entity.ai.task.ModStrollTask;
import fun.mousewich.entity.ai.task.WalkTowardsLookTargetTask;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;

public class AllayBrain {
	protected static Brain<?> create(Brain<AllayEntity> brain) {
		AllayBrain.addCoreActivities(brain);
		AllayBrain.addIdleActivities(brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();
		return brain;
	}
	private static void addCoreActivities(Brain<AllayEntity> brain) {
		brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
				new StayAboveWaterTask(0.8f),
				new WalkTask(2.5f),
				new LookAroundTask(45, 90),
				new WanderAroundTask(),
				new TemptationCooldownTask(ModMemoryModules.LIKED_NOTEBLOCK_COOLDOWN_TICKS),
				new TemptationCooldownTask(ModMemoryModules.ITEM_PICKUP_COOLDOWN_TICKS)));
	}
	private static void addIdleActivities(Brain<AllayEntity> brain) {
		brain.setTaskList(Activity.IDLE, ImmutableList.of(
				Pair.of(0, new WalkToNearestVisibleWantedItemTask<>(allay -> true, 1.75f, true, 32)),
				Pair.of(1, new GiveInventoryToLookTargetTask<>(AllayBrain::getLookTarget, 2.25f, 20)),
				Pair.of(2, new WalkTowardsLookTargetTask<>(AllayBrain::getLookTarget, 4, 16, 2.25f)),
				Pair.of(3, new TimeLimitedTask<>(new FollowMobTask(allay -> true, 6.0f), UniformIntProvider.create(30, 60))),
				Pair.of(4, new RandomTask<>(ImmutableList.of(
						Pair.of(new ModStrollTask(1.0f), 2),
						Pair.of(new GoTowardsLookTarget(1.0f, 3), 2),
						Pair.of(new WaitTask(30, 60), 1))))),
				ImmutableSet.of());
	}

	public static void updateActivities(AllayEntity allay) {
		allay.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
	}

	public static void rememberNoteBlock(LivingEntity allay, BlockPos pos) {
		Brain<?> brain = allay.getBrain();
		GlobalPos globalPos = GlobalPos.create(allay.getWorld().getRegistryKey(), pos);
		Optional<GlobalPos> optional = brain.getOptionalMemory(ModMemoryModules.LIKED_NOTEBLOCK);
		if (optional.isEmpty()) {
			brain.remember(ModMemoryModules.LIKED_NOTEBLOCK, globalPos);
			brain.remember(ModMemoryModules.LIKED_NOTEBLOCK_COOLDOWN_TICKS, 600);
		}
		else if (optional.get().equals(globalPos)) {
			brain.remember(ModMemoryModules.LIKED_NOTEBLOCK_COOLDOWN_TICKS, 600);
		}
	}

	private static Optional<LookTarget> getLookTarget(LivingEntity allay) {
		Brain<?> brain = allay.getBrain();
		Optional<GlobalPos> optional = brain.getOptionalMemory(ModMemoryModules.LIKED_NOTEBLOCK);
		if (optional.isPresent()) {
			GlobalPos globalPos = optional.get();
			if (AllayBrain.shouldGoTowardsNoteBlock(allay, brain, globalPos)) {
				return Optional.of(new BlockPosLookTarget(globalPos.getPos().up()));
			}
			brain.forget(ModMemoryModules.LIKED_NOTEBLOCK);
		}
		return AllayBrain.getLikedLookTarget(allay);
	}

	private static boolean shouldGoTowardsNoteBlock(LivingEntity allay, Brain<?> brain, GlobalPos pos) {
		Optional<Integer> optional = brain.getOptionalMemory(ModMemoryModules.LIKED_NOTEBLOCK_COOLDOWN_TICKS);
		World world = allay.getWorld();
		return world.getRegistryKey() == pos.getDimension() && world.getBlockState(pos.getPos()).isOf(Blocks.NOTE_BLOCK) && optional.isPresent();
	}
	private static Optional<LookTarget> getLikedLookTarget(LivingEntity allay) {
		return AllayBrain.getLikedPlayer(allay).map(player -> new EntityLookTarget(player, true));
	}
	public static Optional<ServerPlayerEntity> getLikedPlayer(LivingEntity allay) {
		World world = allay.getWorld();
		if (!world.isClient() && world instanceof ServerWorld serverWorld) {
			Optional<UUID> optional = allay.getBrain().getOptionalMemory(ModMemoryModules.LIKED_PLAYER);
			if (optional.isPresent()) {
				Entity entity = serverWorld.getEntity(optional.get());
				if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
					if ((serverPlayerEntity.interactionManager.isSurvivalLike() || serverPlayerEntity.interactionManager.isCreative()) && serverPlayerEntity.isInRange(allay, 64.0)) {
						return Optional.of(serverPlayerEntity);
					}
				}
				return Optional.empty();
			}
		}
		return Optional.empty();
	}
}
