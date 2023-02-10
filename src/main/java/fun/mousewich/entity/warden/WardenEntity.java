package fun.mousewich.entity.warden;

import com.mojang.serialization.Dynamic;
import fun.mousewich.ModBase;
import fun.mousewich.entity.ModDataHandlers;
import fun.mousewich.entity.ModEntityStatuses;
import fun.mousewich.entity.ModEntityPose;
import fun.mousewich.entity.ai.ModMemoryModules;
import fun.mousewich.event.ModEntityGameEventHandler;
import fun.mousewich.event.ModEntityPositionSource;
import fun.mousewich.event.ModVibrationListener;
import fun.mousewich.gen.data.tag.ModEventTags;
import fun.mousewich.client.render.entity.animation.AnimationState;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;

public class WardenEntity extends HostileEntity implements ModVibrationListener.Callback {
	private static final TrackedData<Integer> ANGER = DataTracker.registerData(WardenEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private int tendrilPitch;
	private int lastTendrilPitch;
	private int heartbeatCooldown;
	private int lastHeartbeatCooldown;
	public AnimationState roaringAnimationState = new AnimationState();
	public AnimationState sniffingAnimationState = new AnimationState();
	public AnimationState emergingAnimationState = new AnimationState();
	public AnimationState diggingAnimationState = new AnimationState();
	public AnimationState attackingAnimationState = new AnimationState();
	public AnimationState chargingSonicBoomAnimationState = new AnimationState();
	private final ModEntityGameEventHandler<ModVibrationListener> gameEventHandler;
	private WardenAngerManager angerManager = new WardenAngerManager(this::isValidTarget, Collections.emptyList());

	public WardenEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.gameEventHandler = new ModEntityGameEventHandler<>(new ModVibrationListener(new ModEntityPositionSource(this, this.getStandingEyeHeight()), 16, this, null, 0.0f, 0));
		this.experiencePoints = 5;
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0f);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0f);
		this.setPathfindingPenalty(PathNodeType.LAVA, 8.0f);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0f);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0f);
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return new EntitySpawnS2CPacket(this, this.IsInPose(ModEntityPose.EMERGING) ? 1 : 0);
	}

	@Override
	public void onSpawnPacket(EntitySpawnS2CPacket packet) {
		super.onSpawnPacket(packet);
		if (packet.getEntityData() == 1) this.SetPose(ModEntityPose.EMERGING);
	}
	protected static final TrackedData<ModEntityPose> NEW_POSE = DataTracker.registerData(WardenEntity.class, ModDataHandlers.NEW_ENTITY_POSE);
	@Override
	public void setPose(EntityPose pose) {
		this.dataTracker.set(NEW_POSE, ModEntityPose.valueOf(pose.name()));
		super.setPose(pose);
	}
	public void SetPose(ModEntityPose pose) {
		this.dataTracker.set(NEW_POSE, pose);
		for(EntityPose p : EntityPose.values()) {
			if (p.name().equals(pose.name())) {
				super.setPose(p);
				return;
			}
		}
		super.setPose(EntityPose.STANDING);
	}
	public ModEntityPose GetPose() { return this.dataTracker.get(NEW_POSE); }
	public boolean IsInPose(ModEntityPose pose) { return GetPose() == pose; }
	@Override
	public boolean canSpawn(WorldView world) {
		return super.canSpawn(world) && world.isSpaceEmpty(this, this.getType().getDimensions().getBoxAt(this.getPos()));
	}
	@Override
	public float getPathfindingFavor(BlockPos pos, WorldView world) { return 0.0f; }
	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (this.isDiggingOrEmerging() && !damageSource.isOutOfWorld()) return true;
		return super.isInvulnerableTo(damageSource);
	}
	private boolean isDiggingOrEmerging() {
		return this.IsInPose(ModEntityPose.DIGGING) || this.IsInPose(ModEntityPose.EMERGING);
	}
	@Override
	protected boolean canStartRiding(Entity entity) { return false; }
	@Override
	protected float calculateNextStepSoundDistance() { return this.distanceTraveled + 0.55f; }
	public static DefaultAttributeContainer.Builder addAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 500.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.5).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 30.0);
	}
	@Override
	public boolean shouldRender(double distance) {
		return true;
	}
	@Override
	public boolean occludeVibrationSignals() { return true; }
	@Override
	protected float getSoundVolume() { return 4.0f; }
	@Override
	@Nullable
	protected SoundEvent getAmbientSound() {
		if (this.IsInPose(ModEntityPose.ROARING) || this.isDiggingOrEmerging()) return null;
		return this.getAngriness().getSound();
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_WARDEN_HURT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_WARDEN_DEATH; }
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(ModSoundEvents.ENTITY_WARDEN_STEP, 10.0f, 1.0f);
	}
	@Override
	public boolean tryAttack(Entity target) {
		this.world.sendEntityStatus(this, EntityStatuses.PLAY_ATTACK_SOUND);
		this.playSound(ModSoundEvents.ENTITY_WARDEN_ATTACK_IMPACT, 10.0f, this.getSoundPitch());
		SonicBoomTask.cooldown(this, 40);
		return super.tryAttack(target);
	}
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ANGER, 0);
		this.dataTracker.startTracking(NEW_POSE, ModEntityPose.STANDING);
	}
	public int getAnger() { return this.dataTracker.get(ANGER); }
	private void updateAnger() { this.dataTracker.set(ANGER, this.getAngerAtTarget()); }
	@Override
	public void tick() {
		World world = this.world;
		if (world instanceof ServerWorld serverWorld) {
			this.gameEventHandler.getListener().tick(serverWorld);
			if (this.isPersistent() || this.cannotDespawn()) {
				WardenBrain.resetDigCooldown(this);
			}
		}
		super.tick();
		if (this.world.isClient()) {
			if (this.age % this.getHeartRate() == 0) {
				this.heartbeatCooldown = 10;
				if (!this.isSilent()) {
					this.world.playSound(this.getX(), this.getY(), this.getZ(), ModSoundEvents.ENTITY_WARDEN_HEARTBEAT, this.getSoundCategory(), 5.0f, this.getSoundPitch(), false);
				}
			}
			this.lastTendrilPitch = this.tendrilPitch;
			if (this.tendrilPitch > 0) {
				--this.tendrilPitch;
			}
			this.lastHeartbeatCooldown = this.heartbeatCooldown;
			if (this.heartbeatCooldown > 0) {
				--this.heartbeatCooldown;
			}
			switch (this.GetPose()) {
				case EMERGING -> this.addDigParticles(this.emergingAnimationState);
				case DIGGING -> {
					this.SetPose(ModEntityPose.DIGGING);
					if (!this.diggingAnimationState.isRunning()) this.diggingAnimationState.start(this.age);
					this.addDigParticles(this.diggingAnimationState);
				}
			}
		}
	}

	@Override
	protected void mobTick() {
		ServerWorld serverWorld = (ServerWorld)this.world;
		serverWorld.getProfiler().push("wardenBrain");
		this.getBrain().tick(serverWorld, this);
		this.world.getProfiler().pop();
		super.mobTick();
		if ((this.age + this.getId()) % 120 == 0) addDarknessToClosePlayers(serverWorld, this.getPos(), this, 20);
		if (this.age % 20 == 0) {
			this.angerManager.tick(serverWorld, this::isValidTarget);
			this.updateAnger();
		}
		WardenBrain.updateActivities(this);
	}
	@Override
	public void handleStatus(byte status) {
		if (status == EntityStatuses.PLAY_ATTACK_SOUND) {
			this.roaringAnimationState.stop();
			this.attackingAnimationState.start(this.age);
		}
		else if (status == ModEntityStatuses.EARS_TWITCH) this.tendrilPitch = 10;
		else if (status == ModEntityStatuses.SONIC_BOOM) this.chargingSonicBoomAnimationState.start(this.age);
		else super.handleStatus(status);
	}
	private int getHeartRate() {
		float f = (float)this.getAnger() / (float)Angriness.ANGRY.getThreshold();
		return 40 - MathHelper.floor(MathHelper.clamp(f, 0.0f, 1.0f) * 30.0f);
	}
	public float getTendrilPitch(float tickDelta) {
		return MathHelper.lerp(tickDelta, this.lastTendrilPitch, this.tendrilPitch) / 10.0f;
	}
	public float getHeartPitch(float tickDelta) {
		return MathHelper.lerp(tickDelta, this.lastHeartbeatCooldown, this.heartbeatCooldown) / 10.0f;
	}
	private void addDigParticles(AnimationState animationState) {
		if ((float)animationState.getTimeRunning() < 4500.0f) {
			Random random = this.getRandom();
			BlockState blockState = this.getSteppingBlockState();
			if (blockState.getRenderType() != BlockRenderType.INVISIBLE) {
				for (int i = 0; i < 30; ++i) {
					double d = this.getX() + (double)MathHelper.nextBetween(random, -0.7f, 0.7f);
					double e = this.getY();
					double f = this.getZ() + (double)MathHelper.nextBetween(random, -0.7f, 0.7f);
					this.world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), d, e, f, 0.0, 0.0, 0.0);
				}
			}
		}
	}
	public BlockState getSteppingBlockState() {
		return this.world.getBlockState(this.getPosWithYOffset());
	}
	private BlockPos getPosWithYOffset() {
		BlockPos blockPos2;
		BlockState blockState;
		Vec3d pos = getPos();
		int i = MathHelper.floor(pos.x);
		BlockPos blockPos = new BlockPos(i, MathHelper.floor(pos.y - (double) (float) 1.0E-5), MathHelper.floor(pos.z));
		if (this.world.getBlockState(blockPos).isAir() && ((blockState = this.world.getBlockState(blockPos2 = blockPos.down())).isIn(BlockTags.FENCES) || blockState.isIn(BlockTags.WALLS) || blockState.getBlock() instanceof FenceGateBlock)) {
			return blockPos2;
		}
		return blockPos;
	}

	@Override
	public void onTrackedDataSet(TrackedData<?> data) {
		if (POSE.equals(data)) {
			switch (this.GetPose()) {
				case ROARING -> this.roaringAnimationState.start(this.age);
				case SNIFFING -> this.sniffingAnimationState.start(this.age);
				case EMERGING -> this.emergingAnimationState.start(this.age);
				case DIGGING -> this.diggingAnimationState.start(this.age);
			}
		}
		super.onTrackedDataSet(data);
	}
	@Override
	public boolean isImmuneToExplosion() { return this.isDiggingOrEmerging(); }
	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) { return WardenBrain.create(this, dynamic); }
	public Brain<WardenEntity> getBrain() { return (Brain<WardenEntity>)super.getBrain(); }
	@Override
	protected void sendAiDebugData() {
		super.sendAiDebugData();
		DebugInfoSender.sendBrainDebugData(this);
	}
	public void updateEventHandler(BiConsumer<ModEntityGameEventHandler<?>, ServerWorld> callback) {
		World world = this.world;
		if (world instanceof ServerWorld serverWorld) {
			callback.accept(this.gameEventHandler, serverWorld);
		}
	}
	@Override
	public TagKey<GameEvent> getTag() { return ModEventTags.WARDEN_CAN_LISTEN; }
	@Override
	public boolean triggersAvoidCriterion() { return true; }
	@Contract(value="null->false")
	public boolean isValidTarget(@Nullable Entity entity) {
		if (!(entity instanceof LivingEntity livingEntity)) return false;
		if (this.world != entity.world) return false;
		if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(entity)) return false;
		if (this.isTeammate(entity)) return false;
		if (livingEntity.getType() == EntityType.ARMOR_STAND) return false;
		if (livingEntity.getType() == ModBase.WARDEN_ENTITY) return false;
		if (ModBase.WARDEN_NEUTRAL_POWER.isActive(entity)) return false;
		if (livingEntity.isInvulnerable()) return false;
		if (livingEntity.isDead()) return false;
		return this.world.getWorldBorder().contains(livingEntity.getBoundingBox());
	}

	public static void addDarknessToClosePlayers(ServerWorld world, Vec3d pos, @Nullable Entity entity, int range) {
		//StatusEffectInstance statusEffectInstance = new StatusEffectInstance(ModBase.DARKNESS_EFFECT, 260, 0, false, false);
		//TODO: Iris disables fog effects which darkness and flashbanged rely on
		StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.BLINDNESS, 260, 0, false, false);
		addEffectToPlayersWithinDistance(world, entity, pos, range, statusEffectInstance, 200);
	}
	private static List<ServerPlayerEntity> addEffectToPlayersWithinDistance(ServerWorld world, @Nullable Entity entity, Vec3d origin, double range, StatusEffectInstance statusEffectInstance, int duration) {
		StatusEffect statusEffect = statusEffectInstance.getEffectType();
		List<ServerPlayerEntity> list = world.getPlayers(player ->
						!ModBase.WARDEN_NEUTRAL_POWER.isActive(player) &&
						!(!player.interactionManager.isSurvivalLike()
								|| entity != null && entity.isTeammate(player)
								|| !origin.isInRange(player.getPos(), range)
								|| player.hasStatusEffect(statusEffect) && player.getStatusEffect(statusEffect).getAmplifier()
								>= statusEffectInstance.getAmplifier() && player.getStatusEffect(statusEffect).getDuration() >= duration));
		list.forEach(player -> player.addStatusEffect(new StatusEffectInstance(statusEffectInstance), entity));
		return list;
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		WardenAngerManager.createCodec(this::isValidTarget).encodeStart(NbtOps.INSTANCE, this.angerManager).resultOrPartial(ModBase.LOGGER::error).ifPresent(angerNbt -> nbt.put("anger", angerNbt));
		ModVibrationListener.createCodec(this).encodeStart(NbtOps.INSTANCE, this.gameEventHandler.getListener()).resultOrPartial(ModBase.LOGGER::error).ifPresent(nbtElement -> nbt.put("listener", nbtElement));
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("anger")) {
			WardenAngerManager.createCodec(this::isValidTarget).parse(new Dynamic<>(NbtOps.INSTANCE, nbt.get("anger"))).resultOrPartial(ModBase.LOGGER::error).ifPresent(angerManager -> this.angerManager = angerManager);
			this.updateAnger();
		}
		if (nbt.contains("listener", NbtElement.COMPOUND_TYPE)) {
			ModVibrationListener.createCodec(this).parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("listener"))).resultOrPartial(ModBase.LOGGER::error).ifPresent(vibrationListener -> this.gameEventHandler.setListener(vibrationListener, this.world));
		}
	}

	private void playListeningSound() {
		if (!this.IsInPose(ModEntityPose.ROARING)) {
			this.playSound(this.getAngriness().getListeningSound(), 10.0f, this.getSoundPitch());
		}
	}
	public Angriness getAngriness() { return Angriness.getForAnger(this.getAngerAtTarget()); }
	private int getAngerAtTarget() { return this.angerManager.getAngerFor(this.getTarget()); }
	public void removeSuspect(Entity entity) { this.angerManager.removeSuspect(entity); }

	public void increaseAngerAt(@Nullable Entity entity) { this.increaseAngerAt(entity, 35, true); }
	public void increaseAngerAt(@Nullable Entity entity, int amount, boolean listening) {
		if (!this.isAiDisabled() && this.isValidTarget(entity)) {
			WardenBrain.resetDigCooldown(this);
			boolean bl = !(this.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).orElse(null) instanceof PlayerEntity);
			int i = this.angerManager.increaseAngerAt(entity, amount);
			if (entity instanceof PlayerEntity && bl && Angriness.getForAnger(i).isAngry()) this.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
			if (listening) this.playListeningSound();
		}
	}
	public Optional<LivingEntity> getPrimeSuspect() {
		if (this.getAngriness().isAngry()) return this.angerManager.getPrimeSuspect();
		return Optional.empty();
	}
	@Override
	@Nullable
	public LivingEntity getTarget() {
		return this.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
	}
	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) { return false; }

	@Override
	@Nullable
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		this.getBrain().remember(ModMemoryModules.DIG_COOLDOWN, Unit.INSTANCE, 1200L);
		if (spawnReason == SpawnReason.TRIGGERED) {
			this.SetPose(ModEntityPose.EMERGING);
			this.getBrain().remember(ModMemoryModules.IS_EMERGING, Unit.INSTANCE, WardenBrain.EMERGE_DURATION);
			this.playSound(ModSoundEvents.ENTITY_WARDEN_AGITATED, 5.0f, 1.0f);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	public boolean isInRange(Entity entity, double horizontalRadius, double verticalRadius) {
		double d = entity.getX() - this.getX();
		double e = entity.getY() - this.getY();
		double f = entity.getZ() - this.getZ();
		return MathHelper.squaredHypot(d, f) < MathHelper.square(horizontalRadius) && MathHelper.square(e) < MathHelper.square(verticalRadius);
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		boolean bl = super.damage(source, amount);
		if (!(this.world.isClient || this.isAiDisabled() || this.isDiggingOrEmerging())) {
			Entity entity = source.getAttacker();
			this.increaseAngerAt(entity, Angriness.ANGRY.getThreshold() + 20, false);
			if (this.brain.getOptionalMemory(MemoryModuleType.ATTACK_TARGET).isEmpty() && entity instanceof LivingEntity livingEntity) {
				if (!(source instanceof ProjectileDamageSource) || this.isInRange(livingEntity, 5.0)) {
					this.updateAttackTarget(livingEntity);
				}
			}
		}
		return bl;
	}

	public void updateAttackTarget(LivingEntity target) {
		this.getBrain().forget(ModMemoryModules.ROAR_TARGET);
		WardenUpdateAttackTargetTask.updateAttackTarget(this, target);
		SonicBoomTask.cooldown(this, 200);
	}

	@Override
	public EntityDimensions getDimensions(EntityPose pose) {
		EntityDimensions entityDimensions = super.getDimensions(pose);
		if (this.isDiggingOrEmerging()) return EntityDimensions.fixed(entityDimensions.width, 1.0f);
		return entityDimensions;
	}
	@Override
	public boolean isPushable() { return !this.isDiggingOrEmerging() && super.isPushable(); }
	@Override
	protected void pushAway(Entity entity) {
		if (!this.isAiDisabled() && !this.getBrain().hasMemoryModule(ModMemoryModules.TOUCH_COOLDOWN)) {
			this.getBrain().remember(ModMemoryModules.TOUCH_COOLDOWN, Unit.INSTANCE, 20L);
			this.increaseAngerAt(entity);
			WardenBrain.lookAtDisturbance(this, entity.getBlockPos());
		}
		super.pushAway(entity);
	}
	@Override
	public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, BlockPos pos2) {
		if (this.isAiDisabled() || this.isDead() || this.getBrain().hasMemoryModule(ModMemoryModules.VIBRATION_COOLDOWN) || this.isDiggingOrEmerging() || !world.getWorldBorder().contains(pos) || this.isRemoved() || this.world != world) {
			return false;
		}
		return !(entity instanceof LivingEntity livingEntity) || this.isValidTarget(livingEntity);
	}
	@Override
	public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float distance) {
		if (this.isDead()) return;
		this.brain.remember(ModMemoryModules.VIBRATION_COOLDOWN, Unit.INSTANCE, 40L);
		world.sendEntityStatus(this, ModEntityStatuses.EARS_TWITCH);
		this.playSound(ModSoundEvents.ENTITY_WARDEN_TENDRIL_CLICKS, 5.0f, this.getSoundPitch());
		BlockPos blockPos = pos;
		if (sourceEntity != null) {
			if (this.isInRange(sourceEntity, 30.0)) {
				if (this.getBrain().hasMemoryModule(ModMemoryModules.RECENT_PROJECTILE)) {
					if (this.isValidTarget(sourceEntity)) blockPos = sourceEntity.getBlockPos();
					this.increaseAngerAt(sourceEntity);
				}
				else this.increaseAngerAt(sourceEntity, 10, true);
			}
			this.getBrain().remember(ModMemoryModules.RECENT_PROJECTILE, Unit.INSTANCE, 100L);
		}
		else this.increaseAngerAt(entity);
		if (!this.getAngriness().isAngry()) {
			Optional<LivingEntity> optional = this.angerManager.getPrimeSuspect();
			if (sourceEntity != null || optional.isEmpty() || optional.get() == entity) WardenBrain.lookAtDisturbance(this, blockPos);
		}
	}
	public ModVibrationListener getEventListener() { return gameEventHandler.getListener(); }
	public WardenAngerManager getAngerManager() { return this.angerManager; }
	@Override
	protected EntityNavigation createNavigation(World world) {
		return new MobNavigation(this, world){
			@Override
			protected PathNodeNavigator createPathNodeNavigator(int range) {
				this.nodeMaker = new LandPathNodeMaker();
				this.nodeMaker.setCanEnterOpenDoors(true);
				return new WardenPathNodeNagivator(this.nodeMaker, range);
			}
		};
	}
}