package fun.mousewich.entity.passive.allay;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import fun.mousewich.ModBase;
import fun.mousewich.entity.ModNbtKeys;
import fun.mousewich.entity.ai.ModMemoryModules;
import fun.mousewich.event.ModEntityGameEventHandler;
import fun.mousewich.event.ModEntityPositionSource;
import fun.mousewich.event.ModGameEvent;
import fun.mousewich.event.ModVibrationListener;
import fun.mousewich.gen.data.tag.ModGameEventTags;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.*;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

public class AllayEntity extends PathAwareEntity implements InventoryOwner {
	private static final Logger LOGGER = LogUtils.getLogger();
	private static final Ingredient DUPLICATION_INGREDIENT = Ingredient.ofItems(Items.AMETHYST_SHARD);
	private static final int DUPLICATION_COOLDOWN = 6000;
	private static final TrackedData<Boolean> DANCING = DataTracker.registerData(AllayEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private static final TrackedData<Boolean> CAN_DUPLICATE = DataTracker.registerData(AllayEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	protected static final ImmutableList<SensorType<? extends Sensor<? super AllayEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, SensorType.NEAREST_ITEMS);
	@SuppressWarnings("ConstantConditions")
	protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.PATH, MemoryModuleType.LOOK_TARGET, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.HURT_BY, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, ModMemoryModules.LIKED_PLAYER, ModMemoryModules.LIKED_NOTEBLOCK, ModMemoryModules.LIKED_NOTEBLOCK_COOLDOWN_TICKS, ModMemoryModules.ITEM_PICKUP_COOLDOWN_TICKS, ModMemoryModules.IS_PANICKING);
	public static final ImmutableList<Float> THROW_SOUND_PITCHES = ImmutableList.of(0.5625f, 0.625f, 0.75f, 0.9375f, 1.0f, 1.0f, 1.125f, 1.25f, 1.5f, 1.875f, 2.0f, 2.25f, 2.5f, 3.0f, 3.75f, 4.0f);
	private final ModEntityGameEventHandler<ModVibrationListener> gameEventHandler;
	private final ModVibrationListener.Callback listenerCallback;
	private final ModEntityGameEventHandler<JukeboxEventListener> jukeboxEventHandler;
	private final SimpleInventory inventory = new SimpleInventory(1);
	@Nullable
	private BlockPos jukeboxPos;
	private long duplicationCooldown;
	private float field_38935;
	private float field_38936;
	private float field_39472;
	private float field_39473;
	private float field_39474;

	public AllayEntity(EntityType<? extends AllayEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new FlightMoveControl(this, 20, true);
		this.setCanPickUpLoot(this.canPickUpLoot());
		ModEntityPositionSource positionSource = new ModEntityPositionSource(this, this.getStandingEyeHeight());
		this.listenerCallback = new VibrationListenerCallback();
		this.gameEventHandler = new ModEntityGameEventHandler<>(new ModVibrationListener(positionSource, 16, this.listenerCallback));
		this.jukeboxEventHandler = new ModEntityGameEventHandler<>(new JukeboxEventListener(positionSource, ModGameEvent.JUKEBOX_PLAY.getRange()));
	}
	protected Brain.Profile<AllayEntity> createBrainProfile() { return Brain.createProfile(MEMORY_MODULES, SENSORS); }
	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) { return AllayBrain.create(this.createBrainProfile().deserialize(dynamic)); }
	@SuppressWarnings("unchecked")
	public Brain<AllayEntity> getBrain() { return (Brain<AllayEntity>)super.getBrain(); }
	public static DefaultAttributeContainer.Builder createAllayAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.1f).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0);
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		BirdNavigation birdNavigation = new BirdNavigation(this, world);
		birdNavigation.setCanPathThroughDoors(false);
		birdNavigation.setCanSwim(true);
		birdNavigation.setCanEnterOpenDoors(true);
		return birdNavigation;
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DANCING, false);
		this.dataTracker.startTracking(CAN_DUPLICATE, true);
	}

	@Override
	public void travel(Vec3d movementInput) {
		if (this.canMoveVoluntarily() || this.isLogicalSideForUpdatingMovement()) {
			if (this.isTouchingWater()) {
				this.updateVelocity(0.02f, movementInput);
				this.move(MovementType.SELF, this.getVelocity());
				this.setVelocity(this.getVelocity().multiply(0.8f));
			}
			else if (this.isInLava()) {
				this.updateVelocity(0.02f, movementInput);
				this.move(MovementType.SELF, this.getVelocity());
				this.setVelocity(this.getVelocity().multiply(0.5));
			}
			else {
				this.updateVelocity(this.getMovementSpeed(), movementInput);
				this.move(MovementType.SELF, this.getVelocity());
				this.setVelocity(this.getVelocity().multiply(0.91f));
			}
		}
		this.updateLimbs(this, false);
	}
	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) { return dimensions.height * 0.6f; }
	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) { return false; }
	@Override
	public boolean damage(DamageSource source, float amount) {
		Entity entity = source.getAttacker();
		if (entity instanceof PlayerEntity playerEntity) {
			Optional<UUID> optional = this.getBrain().getOptionalMemory(ModMemoryModules.LIKED_PLAYER);
			if (optional.isPresent() && playerEntity.getUuid().equals(optional.get())) return false;
		}
		return super.damage(source, amount);
	}
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) { }
	@Override
	protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) { }
	@Override
	protected SoundEvent getAmbientSound() {
		return this.hasStackEquipped(EquipmentSlot.MAINHAND) ? ModSoundEvents.ENTITY_ALLAY_AMBIENT_WITH_ITEM : ModSoundEvents.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_ALLAY_HURT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_ALLAY_DEATH; }
	@Override
	protected float getSoundVolume() { return 0.4f; }
	@Override
	protected void mobTick() {
		this.world.getProfiler().push("allayBrain");
		this.getBrain().tick((ServerWorld)this.world, this);
		this.world.getProfiler().pop();
		this.world.getProfiler().push("allayActivityUpdate");
		AllayBrain.updateActivities(this);
		this.world.getProfiler().pop();
		super.mobTick();
	}
	@Override
	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.age % 10 == 0) this.heal(1.0f);
		if (this.isDancing() && this.shouldStopDancing() && this.age % 20 == 0) {
			this.setDancing(false);
			this.jukeboxPos = null;
		}
		this.tickDuplicationCooldown();
	}

	@Override
	public void tick() {
		super.tick();
		if (this.world.isClient) {
			this.field_38936 = this.field_38935;
			this.field_38935 = this.isHoldingItem() ? MathHelper.clamp(this.field_38935 + 1.0f, 0.0f, 5.0f) : MathHelper.clamp(this.field_38935 - 1.0f, 0.0f, 5.0f);
			if (this.isDancing()) {
				this.field_39472 += 1.0f;
				this.field_39474 = this.field_39473;
				this.field_39473 += this.method_44360() ? 1.0f : -1.0f;
				this.field_39473 = MathHelper.clamp(this.field_39473, 0.0f, 15.0f);
			}
			else {
				this.field_39472 = 0.0f;
				this.field_39473 = 0.0f;
				this.field_39474 = 0.0f;
			}
		} else {
			this.gameEventHandler.getListener().tick(this.world);
			if (this.isPanicking()) {
				this.setDancing(false);
			}
		}
	}
	@Override
	public boolean canPickUpLoot() { return !this.isItemPickupCoolingDown() && this.isHoldingItem(); }
	public boolean isHoldingItem() { return !this.getStackInHand(Hand.MAIN_HAND).isEmpty(); }
	@Override
	public boolean canEquip(ItemStack stack) { return false; }
	private boolean isItemPickupCoolingDown() {
		return this.getBrain().isMemoryInState(ModMemoryModules.ITEM_PICKUP_COOLDOWN_TICKS, MemoryModuleState.VALUE_PRESENT);
	}
	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		ItemStack itemStack2 = this.getStackInHand(Hand.MAIN_HAND);
		if (this.isDancing() && this.matchesDuplicationIngredient(itemStack) && this.canDuplicate()) {
			this.duplicate();
			this.world.sendEntityStatus(this, EntityStatuses.ADD_BREEDING_PARTICLES);
			this.world.playSoundFromEntity(player, this, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.NEUTRAL, 2.0f, 1.0f);
			this.decrementStackUnlessInCreative(player, itemStack);
			return ActionResult.SUCCESS;
		}
		if (itemStack2.isEmpty() && !itemStack.isEmpty()) {
			ItemStack itemStack3 = itemStack.copy();
			itemStack3.setCount(1);
			this.setStackInHand(Hand.MAIN_HAND, itemStack3);
			this.decrementStackUnlessInCreative(player, itemStack);
			this.world.playSoundFromEntity(player, this, ModSoundEvents.ENTITY_ALLAY_ITEM_GIVEN, SoundCategory.NEUTRAL, 2.0f, 1.0f);
			this.getBrain().remember(ModMemoryModules.LIKED_PLAYER, player.getUuid());
			return ActionResult.SUCCESS;
		}
		if (!itemStack2.isEmpty() && hand == Hand.MAIN_HAND && itemStack.isEmpty()) {
			this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
			this.world.playSoundFromEntity(player, this, ModSoundEvents.ENTITY_ALLAY_ITEM_TAKEN, SoundCategory.NEUTRAL, 2.0f, 1.0f);
			this.swingHand(Hand.MAIN_HAND);
			for (ItemStack itemStack4 : this.getInventory().clearToList()) LookTargetUtil.give(this, itemStack4, this.getPos());
			this.getBrain().forget(ModMemoryModules.LIKED_PLAYER);
			player.giveItemStack(itemStack2);
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}
	public void updateJukeboxPos(BlockPos jukeboxPos, boolean playing) {
		if (playing) {
			if (!this.isDancing()) {
				this.jukeboxPos = jukeboxPos;
				this.setDancing(true);
			}
		}
		else if (jukeboxPos.equals(this.jukeboxPos) || this.jukeboxPos == null) {
			this.jukeboxPos = null;
			this.setDancing(false);
		}
	}

	@Override
	public SimpleInventory getInventory() { return this.inventory; }
	@Override
	public boolean canGather(ItemStack stack) {
		ItemStack itemStack = this.getStackInHand(Hand.MAIN_HAND);
		return !itemStack.isEmpty() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && this.inventory.canInsert(stack) && this.areItemsEqual(itemStack, stack);
	}
	private boolean areItemsEqual(ItemStack stack, ItemStack stack2) {
		return stack.isItemEqual(stack2) && !this.areDifferentPotions(stack, stack2);
	}
	private boolean areDifferentPotions(ItemStack stack, ItemStack stack2) {
		NbtCompound nbtCompound = stack.getNbt();
		if (nbtCompound == null || !nbtCompound.contains("Potion")) return false;
		NbtCompound nbtCompound2 = stack2.getNbt();
		if (nbtCompound2 == null || !nbtCompound2.contains("Potion")) return true;
		NbtElement nbtElement = nbtCompound.get("Potion");
		NbtElement nbtElement2 = nbtCompound2.get("Potion");
		return nbtElement != null && nbtElement2 != null && !nbtElement.equals(nbtElement2);
	}
	@Override
	protected void loot(ItemEntity item) {
		ItemStack itemStack = item.getStack();
		if (this.canGather(itemStack)) {
			SimpleInventory simpleInventory = this.getInventory();
			if (!simpleInventory.canInsert(itemStack)) return;
			this.triggerItemPickedUpByEntityCriteria(item);
			ItemStack itemStack2 = simpleInventory.addStack(itemStack);
			this.sendPickup(item, itemStack.getCount() - itemStack2.getCount());
			if (itemStack2.isEmpty()) item.discard();
			else itemStack.setCount(itemStack2.getCount());
		}
	}
	@Override
	protected void sendAiDebugData() {
		super.sendAiDebugData();
		DebugInfoSender.sendBrainDebugData(this);
	}
	@Override
	protected void addAirTravelEffects() {
		if (this.isFlappingWings()) {
			this.addFlapEffects();
			if (this.getMoveEffect().emitsGameEvents()) {
				this.emitGameEvent(GameEvent.FLAP);
			}
		}
	}
	public boolean isFlappingWings() { return !this.isOnGround(); }

	public void updateEventHandler(BiConsumer<ModEntityGameEventHandler<?>, ServerWorld> callback) {
		World world = this.world;
		if (world instanceof ServerWorld serverWorld) {
			callback.accept(this.gameEventHandler, serverWorld);
			callback.accept(this.jukeboxEventHandler, serverWorld);
		}
	}
	public boolean isDancing() { return this.dataTracker.get(DANCING); }
	public boolean isPanicking() { return this.brain.getOptionalMemory(ModMemoryModules.IS_PANICKING).isPresent(); }
	public void setDancing(boolean dancing) {
		if (this.world.isClient || !this.canMoveVoluntarily() || dancing && this.isPanicking()) return;
		this.dataTracker.set(DANCING, dancing);
	}
	private boolean shouldStopDancing() {
		return this.jukeboxPos == null || !this.jukeboxPos.isWithinDistance(this.getPos(), ModGameEvent.JUKEBOX_PLAY.getRange()) || !this.world.getBlockState(this.jukeboxPos).isOf(Blocks.JUKEBOX);
	}
	public float method_43397(float f) { return MathHelper.lerp(f, this.field_38936, this.field_38935) / 5.0f; }
	public boolean method_44360() {
		float f = this.field_39472 % 55.0f;
		return f < 15.0f;
	}
	public float method_44368(float f) { return MathHelper.lerp(f, this.field_39474, this.field_39473) / 15.0f; }
	@Override
	protected void dropInventory() {
		super.dropInventory();
		this.inventory.clearToList().forEach(this::dropStack);
		ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
		if (!itemStack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemStack)) {
			this.dropStack(itemStack);
			this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
		}
	}
	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) { return false; }
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.put(ModNbtKeys.INVENTORY, this.getInventory().toNbtList());
		ModVibrationListener.createCodec(this.listenerCallback).encodeStart(NbtOps.INSTANCE, this.gameEventHandler.getListener()).resultOrPartial(LOGGER::error).ifPresent(nbtElement -> nbt.put(ModNbtKeys.LISTENER, nbtElement));
		nbt.putLong(ModNbtKeys.DUPLICATION_COOLDOWN, this.duplicationCooldown);
		nbt.putBoolean(ModNbtKeys.CAN_DUPLICATE, this.canDuplicate());
	}
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains(ModNbtKeys.INVENTORY, NbtElement.LIST_TYPE)) {
			this.getInventory().readNbtList(nbt.getList(ModNbtKeys.INVENTORY, NbtElement.COMPOUND_TYPE));
		}
		if (nbt.contains(ModNbtKeys.LISTENER, NbtElement.COMPOUND_TYPE)) {
			ModVibrationListener.createCodec(this.listenerCallback).parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound(ModNbtKeys.LISTENER))).resultOrPartial(LOGGER::error).ifPresent(vibrationListener -> this.gameEventHandler.setListener(vibrationListener, this.world));
		}
		this.duplicationCooldown = nbt.getInt(ModNbtKeys.DUPLICATION_COOLDOWN);
		this.dataTracker.set(CAN_DUPLICATE, nbt.getBoolean(ModNbtKeys.CAN_DUPLICATE));
	}
	protected boolean shouldFollowLeash() { return false; }
	public Iterable<BlockPos> getPotentialEscapePositions() {
		Box box = this.getBoundingBox();
		int i = MathHelper.floor(box.minX - 0.5);
		int j = MathHelper.floor(box.maxX + 0.5);
		int k = MathHelper.floor(box.minZ - 0.5);
		int l = MathHelper.floor(box.maxZ + 0.5);
		int m = MathHelper.floor(box.minY - 0.5);
		int n = MathHelper.floor(box.maxY + 0.5);
		return BlockPos.iterate(i, m, k, j, n, l);
	}
	private void tickDuplicationCooldown() {
		if (this.duplicationCooldown > 0L) --this.duplicationCooldown;
		if (!this.world.isClient() && this.duplicationCooldown == 0L && !this.canDuplicate()) this.dataTracker.set(CAN_DUPLICATE, true);
	}
	private boolean matchesDuplicationIngredient(ItemStack stack) { return DUPLICATION_INGREDIENT.test(stack); }
	private void duplicate() {
		AllayEntity allayEntity = ModBase.ALLAY_ENTITY.create(this.world);
		if (allayEntity != null) {
			allayEntity.refreshPositionAfterTeleport(this.getPos());
			allayEntity.setPersistent();
			allayEntity.startDuplicationCooldown();
			this.startDuplicationCooldown();
			this.world.spawnEntity(allayEntity);
		}
	}
	private void startDuplicationCooldown() {
		this.duplicationCooldown = DUPLICATION_COOLDOWN;
		this.dataTracker.set(CAN_DUPLICATE, false);
	}
	private boolean canDuplicate() { return this.dataTracker.get(CAN_DUPLICATE); }
	private void decrementStackUnlessInCreative(PlayerEntity player, ItemStack stack) {
		if (!player.getAbilities().creativeMode) stack.decrement(1);
	}
	@Override
	public Vec3d getLeashOffset() { return new Vec3d(0.0, this.getStandingEyeHeight() * 0.6, this.getWidth() * 0.1); }
	@Override
	public double getHeightOffset() { return 0.4; }
	@Override
	public void handleStatus(byte status) {
		if (status == EntityStatuses.ADD_BREEDING_PARTICLES) {
			for (int i = 0; i < 3; ++i) this.addHeartParticle();
		}
		else super.handleStatus(status);
	}
	private void addHeartParticle() {
		double d = this.random.nextGaussian() * 0.02;
		double e = this.random.nextGaussian() * 0.02;
		double f = this.random.nextGaussian() * 0.02;
		this.world.addParticle(ParticleTypes.HEART, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
	}

	class VibrationListenerCallback implements ModVibrationListener.Callback {
		VibrationListenerCallback() { }
		@Override
		public boolean accepts(ServerWorld var1, GameEventListener var2, BlockPos var3, GameEvent var4, Entity entity, BlockPos pos) {
			if (AllayEntity.this.isAiDisabled()) return false;
			Optional<GlobalPos> optional = AllayEntity.this.getBrain().getOptionalMemory(ModMemoryModules.LIKED_NOTEBLOCK);
			if (optional.isEmpty()) return true;
			GlobalPos globalPos = optional.get();
			return globalPos.getDimension().equals(world.getRegistryKey()) && globalPos.getPos().equals(pos);
		}
		@Override
		public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float distance) {
			if (event == ModGameEvent.NOTE_BLOCK_PLAY) {
				AllayBrain.rememberNoteBlock(AllayEntity.this, new BlockPos(pos));
			}
		}
		@Override
		public ModVibrationListener getModEventListener() { return null; }
		@Override
		public TagKey<GameEvent> getTag() { return ModGameEventTags.ALLAY_CAN_LISTEN; }
	}

	class JukeboxEventListener implements GameEventListener {
		private final PositionSource positionSource;
		private final int range;
		public JukeboxEventListener(PositionSource positionSource, int range) {
			this.positionSource = positionSource;
			this.range = range;
		}
		@Override
		public PositionSource getPositionSource() { return this.positionSource; }
		@Override
		public int getRange() { return this.range; }

		@Override
		public boolean listen(World world, GameEvent event, @Nullable Entity entity, BlockPos pos) {
			if (event == ModGameEvent.JUKEBOX_PLAY) {
				AllayEntity.this.updateJukeboxPos(new BlockPos(pos), true);
				return true;
			}
			if (event == ModGameEvent.JUKEBOX_STOP_PLAY) {
				AllayEntity.this.updateJukeboxPos(new BlockPos(pos), false);
				return true;
			}
			return false;
		}
	}
}