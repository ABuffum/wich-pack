package fun.mousewich.sound;

import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import fun.mousewich.ModBase;
import fun.mousewich.container.BlockContainer;
import fun.mousewich.entity.ModBoatEntity;
import fun.mousewich.gen.data.tag.ModBlockTags;
import fun.mousewich.mixins.entity.BoatEntityAccessor;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import net.minecraft.block.*;
import net.minecraft.block.enums.PistonType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.*;

import static fun.mousewich.ModBase.*;

public class IdentifiedSounds {
	public static final String IDENTIFY = "identified_sound.";
	public static boolean Active(Entity entity) { return ModBase.IDENTIFIED_SOUNDS_POWER.isActive(entity); }
	private static SoundEvent makeSound(String name) {
		SoundEvent sound = ModSoundEvents.registerSoundEvent(IDENTIFY + name);
		SubtitleOverrides.Register(sound, name);
		return sound;
	}

	private static class EntitySounds {
		public final String name;
		public final SoundEvent stepSound, crashSound, swimSound, splashSound, highSpeedSplashSound, deathSound;
		public final LivingEntity.FallSounds fallSounds;
		public EntitySounds(String name, SoundEvent step, SoundEvent trip, SoundEvent fall, SoundEvent crash, SoundEvent swim, SoundEvent splash, SoundEvent fastSplash, SoundEvent death) {
			this.name = name;
			this.stepSound = step;
			this.fallSounds = new LivingEntity.FallSounds(trip, fall);
			this.crashSound = crash;
			this.swimSound = swim;
			this.splashSound = splash;
			this.highSpeedSplashSound = fastSplash;
			this.deathSound = death;
		}
	}
	private static EntitySounds makeEntitySounds(String name, boolean steps, boolean trip, boolean fall, boolean crash, boolean swim, boolean splash, boolean fastSplash, boolean death) {
		SoundEvent stepSound = null, tripSound = null, fallSound = null, crashSound = null, swimSound = null, splashSound = null, highSpeedSplashSound = null, deathSound = null;
		if (steps) stepSound = makeSound("footsteps." + name);
		if (trip) tripSound = makeSound("small_fall." + name);
		if (fall) fallSound = makeSound("big_fall." + name);
		if (crash) crashSound = makeSound("crash." + name);
		if (swim) swimSound = makeSound("swim." + name);
		if (splash) splashSound = makeSound("splash." + name);
		if (fastSplash) highSpeedSplashSound = makeSound("splash.high_speed." + name);
		if (death) deathSound = makeSound("death." + name);
		return new EntitySounds(name, stepSound, tripSound, fallSound, crashSound, swimSound, splashSound, highSpeedSplashSound, deathSound);
	}
	private static final EntitySounds AXOLOTL_ENTITY = makeEntitySounds("axolotl", true, false, false, false, false, false, false, false);
	private static final EntitySounds BAT_ENTITY = makeEntitySounds("bat", false, false, false, false, false, true, false, false);
	private static final EntitySounds CAT_ENTITY = makeEntitySounds("cat", true, false, false, false, false, true, false, false);
	private static final EntitySounds CHICKEN_ENTITY = makeEntitySounds("chicken", false, false, false, false, false, true, false, false);
	private static final EntitySounds COD_ENTITY = makeEntitySounds("cod", false, false, false, false, true, true, false, false);
	private static final EntitySounds CREEPER_ENTITY = makeEntitySounds("creeper", true, false, false, false, false, false, false, false);
	private static final EntitySounds FOX_ENTITY = makeEntitySounds("fox", true, false, false, false, false, false, false, false);
	private static final EntitySounds GLOW_SQUID_ENTITY = makeEntitySounds("glow_squid", false, false, false, false, false, true, false, false);
	private static final EntitySounds OCELOT_ENTITY = makeEntitySounds("ocelot", true, false, false, false, false, false, false, false);
	private static final EntitySounds PUFFERFISH_ENTITY = makeEntitySounds("pufferfish", false, false, false, false, true, true, false, false);
	private static final EntitySounds RABBIT_ENTITY = makeEntitySounds("rabbit", true, false, false, false, false, false, false, false);
	private static final EntitySounds SALMON_ENTITY = makeEntitySounds("salmon", false, false, false, false, true, true, false, false);
	private static final EntitySounds SQUID_ENTITY = makeEntitySounds("squid", false, false, false, false, false, true, false, false);
	private static final EntitySounds TROPICAL_FISH_ENTITY = makeEntitySounds("tropical_fish", false, false, false, false, true, true, false, false);
	private static final EntitySounds VILLAGER_ENTITY = makeEntitySounds("villager", true, false, false, false, false, false, false, false);
	private static final EntitySounds WANDERING_TRADER_ENTITY = makeEntitySounds("wandering_trader", true, false, false, false, false, false, false, false);

	private static final Map<PowerType<?>, EntitySounds> powerSounds = new HashMap<>();
	public static void RegisterPowers(String namespace, String... names) {
		for (String name : names) {
			PowerType<?> power = new PowerTypeReference<>(ModBase.ID(namespace + ":" + name + "_identified_sounds"));
			powerSounds.put(power, makeEntitySounds(namespace + "." + name, true, true, true, true, true, true, true, true));
		}
	}
	public static EntitySounds getEntitySounds(Entity entity) {
		if (entity == null) return null;
		for (Map.Entry<PowerType<?>, EntitySounds> entry : powerSounds.entrySet()) {
			if (entry.getKey().isActive(entity)) return entry.getValue();
		}
		if (entity instanceof AxolotlEntity) return AXOLOTL_ENTITY;
		if (entity instanceof BatEntity) return BAT_ENTITY;
		if (entity instanceof CatEntity) return CAT_ENTITY;
		if (entity instanceof ChickenEntity) return CHICKEN_ENTITY;
		if (entity instanceof CodEntity) return COD_ENTITY;
		if (entity instanceof CreeperEntity) return CREEPER_ENTITY;
		if (entity instanceof FoxEntity) return FOX_ENTITY;
		if (entity instanceof GlowSquidEntity) return GLOW_SQUID_ENTITY;
		if (entity instanceof OcelotEntity) return OCELOT_ENTITY;
		if (entity instanceof PufferfishEntity) return PUFFERFISH_ENTITY;
		if (entity instanceof RabbitEntity) return RABBIT_ENTITY;
		if (entity instanceof SalmonEntity) return SALMON_ENTITY;
		if (entity instanceof SquidEntity) return SQUID_ENTITY;
		if (entity instanceof TropicalFishEntity) return TROPICAL_FISH_ENTITY;
		if (entity instanceof VillagerEntity) return VILLAGER_ENTITY;
		if (entity instanceof WanderingTraderEntity) return WANDERING_TRADER_ENTITY;
		return null;
	}
	public static SoundEvent getStepSound(Entity entity) {
		EntitySounds sounds = getEntitySounds(entity);
		return sounds != null ? sounds.stepSound : null;
	}
	public static LivingEntity.FallSounds getFallSounds(Entity entity) {
		EntitySounds sounds = getEntitySounds(entity);
		return sounds != null ? sounds.fallSounds : null;
	}
	public static SoundEvent getCrashSound(Entity entity) {
		EntitySounds sounds = getEntitySounds(entity);
		return sounds != null ? sounds.crashSound : null;
	}
	public static SoundEvent getSwimSound(Entity entity) {
		EntitySounds sounds = getEntitySounds(entity);
		return sounds != null ? sounds.swimSound : null;
	}
	public static SoundEvent getSplashSound(Entity entity) {
		EntitySounds sounds = getEntitySounds(entity);
		return sounds != null ? sounds.splashSound : null;
	}
	public static SoundEvent getHighSpeedSplashSound(Entity entity) {
		EntitySounds sounds = getEntitySounds(entity);
		if (sounds != null && sounds.highSpeedSplashSound != null) return sounds.highSpeedSplashSound;
		return getSplashSound(entity);
	}
	public static SoundEvent getDeathSound(Entity entity) {
		EntitySounds sounds = getEntitySounds(entity);
		return sounds != null ? sounds.deathSound : null;
	}

	private static class BoatSounds {
		public final SoundEvent land, water;
		public BoatSounds(String name) {
			this.land = makeSound("paddle.land." + name);
			this.water = makeSound("paddle.water." + name);
		}
	}
	private static final Map<String, BoatSounds> boatSounds = new HashMap<>();
	public static SoundEvent getPaddleSound(BoatEntity boat) {
		String name;
		if (boat instanceof ModBoatEntity modBoat) name = modBoat.getModBoatType().getName();
		else name = boat.getBoatType().getName();
		if (boatSounds.containsKey(name)) {
			switch (((BoatEntityAccessor) boat).getLocation()) {
				case IN_WATER, UNDER_WATER, UNDER_FLOWING_WATER -> { return boatSounds.get(name).water; }
				case ON_LAND -> { return boatSounds.get(name).land; }
			}
		}
		return null;
	}

	public static void RegisterAll() {
		//Boats
		for (BoatEntity.Type type : BoatEntity.Type.values()) {
			String name = type.getName();
			boatSounds.put(name, new BoatSounds(name));
		}
		boatSounds.put(MANGROVE_BOAT.getType().getName(), new BoatSounds("mangrove"));
		boatSounds.put(CHARRED_BOAT.getType().getName(), new BoatSounds("charred"));
		//Blocks
		RegisterBlockSounds();
	}

	private static class BlockSounds {
		public final SoundEvent breakSound, stepSound, placeSound, hitSound;
		public BlockSounds(String name) { this(makeBreak(name), makeStep(name), makePlace(name), makeHit(name)); }
		public BlockSounds(SoundEvent breakSound, SoundEvent stepSound, SoundEvent placeSound, SoundEvent hitSound) {
			this.breakSound = breakSound;
			this.stepSound = stepSound;
			this.placeSound = placeSound;
			this.hitSound = hitSound;
		}
		public BlockSounds(BlockSoundGroup soundGroup) {
			this.breakSound = soundGroup.getBreakSound();
			this.stepSound = soundGroup.getStepSound();
			this.placeSound = soundGroup.getPlaceSound();
			this.hitSound = soundGroup.getHitSound();
		}
		public static BlockSounds BreakPlace(String name, SoundEvent breakSound, SoundEvent placeSound) {
			return new BlockSounds(breakSound, makeStep(name), placeSound, makeHit(name));
		}
		public static BlockSounds StepOnly(SoundEvent stepSound)  {
			return new BlockSounds(null, stepSound, null, null);
		}
		public static BlockSounds Place(String name, SoundEvent placeSound)  {
			return new BlockSounds(makeBreak(name), makeStep(name), placeSound, makeHit(name));
		}
		public static SoundEvent makeBreak(String name) { return makeSound("block.break." + name); }
		public static SoundEvent makeStep(String name) { return makeSound("block.step." + name); }
		public static SoundEvent makePlace(String name) { return makeSound("block.place." + name); }
		public static SoundEvent makeHit(String name) { return makeSound("block.hit." + name); }
	}

	private static final Map<Block, BlockSounds> blockSounds = new HashMap<>();
	private static final Map<Identifier, Block> copyBlockSounds = new HashMap<>();
	private static void Register(String name, Block... blocks) { for (Block block : blocks) blockSounds.put(block, new BlockSounds(name)); }
	private static void Register(BlockSounds sounds, Block... blocks) { for (Block block : blocks) blockSounds.put(block, sounds); }
	private static void RegisterBlockSounds() {
		Register("andesite", Blocks.ANDESITE, Blocks.ANDESITE_STAIRS, Blocks.ANDESITE_SLAB, Blocks.ANDESITE_WALL,
				Blocks.POLISHED_ANDESITE, Blocks.POLISHED_ANDESITE_STAIRS, Blocks.POLISHED_ANDESITE_SLAB,
				/* Mod */ POLISHED_ANDESITE_WALL.asBlock());
		Register("bamboo_door", /* Mod */ BAMBOO_DOOR.asBlock());
		Register("bamboo_fence", /* Mod */ BAMBOO_FENCE.asBlock());
		Register("bamboo_fence_gate", /* Mod */ BAMBOO_FENCE_GATE.asBlock());
		Register("bamboo_ladder", /* Mod */ BAMBOO_LADDER.asBlock());
		Register("bamboo_trapdoor", /* Mod */ BAMBOO_TRAPDOOR.asBlock());
		Register("banner", Blocks.BLACK_BANNER, Blocks.BLUE_BANNER, Blocks.BROWN_BANNER, Blocks.CYAN_BANNER,
				Blocks.GRAY_BANNER, Blocks.GREEN_BANNER, Blocks.LIGHT_BLUE_BANNER, Blocks.LIGHT_GRAY_BANNER,
				Blocks.LIME_BANNER, Blocks.MAGENTA_BANNER, Blocks.ORANGE_BANNER, Blocks.PINK_BANNER,
				Blocks.PURPLE_BANNER, Blocks.RED_BANNER, Blocks.WHITE_BANNER, Blocks.YELLOW_BANNER,
				Blocks.BLACK_WALL_BANNER, Blocks.BLUE_WALL_BANNER, Blocks.BROWN_WALL_BANNER, Blocks.CYAN_WALL_BANNER,
				Blocks.GRAY_WALL_BANNER, Blocks.GREEN_WALL_BANNER, Blocks.LIGHT_BLUE_WALL_BANNER, Blocks.LIGHT_GRAY_WALL_BANNER,
				Blocks.LIME_WALL_BANNER, Blocks.MAGENTA_WALL_BANNER, Blocks.ORANGE_WALL_BANNER, Blocks.PINK_WALL_BANNER,
				Blocks.PURPLE_WALL_BANNER, Blocks.RED_WALL_BANNER, Blocks.WHITE_WALL_BANNER, Blocks.YELLOW_WALL_BANNER);
		Register(BARK_BLOCK, Blocks.CRIMSON_HYPHAE, Blocks.WARPED_HYPHAE);
		Register("barrel", Blocks.BARREL);
		Register("beacon", Blocks.BEACON);
		Register("bed", Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED,
				Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED,
				Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, Blocks.PINK_BED,
				Blocks.PURPLE_BED, Blocks.RED_BED, Blocks.WHITE_BED, Blocks.YELLOW_BED);//,
				///* Mod */ RAINBOW_BED.asBlock());
		Register("bedrock", Blocks.BEDROCK);
		Register("bee_nest", Blocks.BEE_NEST);
		Register("beehive", Blocks.BEEHIVE);
		Register("bell", Blocks.BELL);
		Register("blackstone", Blocks.BLACKSTONE, Blocks.BLACKSTONE_STAIRS, Blocks.BLACKSTONE_SLAB, Blocks.BLACKSTONE_WALL,
				Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_STAIRS, Blocks.POLISHED_BLACKSTONE_SLAB, Blocks.POLISHED_BLACKSTONE_WALL,
				Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, Blocks.POLISHED_BLACKSTONE_BRICK_WALL,
				Blocks.CHISELED_POLISHED_BLACKSTONE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BUTTON, Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE);
		Register("bone_ladder", /* Mod */ BONE_LADDER.asBlock());
		Register("brewing_stand", Blocks.BREWING_STAND);
		Register("bricks", Blocks.BRICKS);
		Register("cactus", Blocks.CACTUS); //TODO: Decorative
		Register("cake", Blocks.CAKE, Blocks.CANDLE_CAKE,
				Blocks.BLACK_CANDLE_CAKE, Blocks.BLUE_CANDLE_CAKE, Blocks.BROWN_CANDLE_CAKE, Blocks.CYAN_CANDLE_CAKE,
				Blocks.GRAY_CANDLE_CAKE, Blocks.GREEN_CANDLE_CAKE, Blocks.LIGHT_BLUE_CANDLE_CAKE, Blocks.LIGHT_GRAY_CANDLE_CAKE,
				Blocks.LIME_CANDLE_CAKE, Blocks.MAGENTA_CANDLE_CAKE, Blocks.ORANGE_CANDLE_CAKE, Blocks.PINK_CANDLE_CAKE,
				Blocks.PURPLE_CANDLE_CAKE, Blocks.RED_CANDLE_CAKE, Blocks.WHITE_CANDLE_CAKE, Blocks.YELLOW_CANDLE_CAKE,
				/* Mod */ SOUL_CANDLE_CAKE, ENDER_CANDLE_CAKE);
		BlockSounds carpet = new BlockSounds("carpet");
		Register(carpet, Blocks.BLACK_CARPET, Blocks.BLUE_CARPET, Blocks.BROWN_CARPET, Blocks.CYAN_CARPET,
				Blocks.GRAY_CARPET, Blocks.GREEN_CARPET, Blocks.LIGHT_BLUE_CARPET, Blocks.LIGHT_GRAY_CARPET,
				Blocks.LIME_CARPET, Blocks.MAGENTA_CARPET, Blocks.ORANGE_CARPET, Blocks.PINK_CARPET,
				Blocks.PURPLE_CARPET, Blocks.RED_CARPET, Blocks.WHITE_CARPET, Blocks.YELLOW_CARPET,
				/* Mod */ RAINBOW_CARPET.asBlock(), RAINBOW_FLEECE_CARPET.asBlock());
		Register(carpet, FLEECE_CARPETS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new));
		Register("cartography_table", Blocks.CARTOGRAPHY_TABLE);
		Register("cauldron", Blocks.CAULDRON, Blocks.WATER_CAULDRON, Blocks.LAVA_CAULDRON, Blocks.POWDER_SNOW_CAULDRON);
		Register("chest", Blocks.CHEST, Blocks.TRAPPED_CHEST);
		Register("chorus_plant", Blocks.CHORUS_PLANT, Blocks.CHORUS_FLOWER);
		Register("clay", Blocks.CLAY);
		Register("coal", Blocks.COAL_BLOCK, /* Mod */ COAL_SLAB.asBlock(), CHARCOAL_BLOCK.asBlock(), CHARCOAL_SLAB.asBlock());
		Register("coarse_dirt", Blocks.COARSE_DIRT);
		Register("cobbled_deepslate", Blocks.COBBLED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE_STAIRS, Blocks.COBBLED_DEEPSLATE_SLAB, Blocks.COBBLED_DEEPSLATE_WALL);
		Register("cobblestone", Blocks.COBBLESTONE, Blocks.COBBLESTONE_STAIRS, Blocks.COBBLESTONE_SLAB, Blocks.COBBLESTONE_WALL,
				Blocks.MOSSY_COBBLESTONE, Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_WALL);
		Register("cobweb", Blocks.COBWEB);
		Register("cocoa", Blocks.COCOA, /* Mod */ COCOA_BLOCK.asBlock());
		Register("composter", Blocks.COMPOSTER);
		Register("concrete", Blocks.BLACK_CONCRETE, Blocks.BLUE_CONCRETE, Blocks.BROWN_CONCRETE, Blocks.CYAN_CONCRETE,
				Blocks.GRAY_CONCRETE, Blocks.GREEN_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE, Blocks.LIGHT_GRAY_CONCRETE,
				Blocks.LIME_CONCRETE, Blocks.MAGENTA_CONCRETE, Blocks.ORANGE_CONCRETE, Blocks.PINK_CONCRETE,
				Blocks.PURPLE_CONCRETE, Blocks.RED_CONCRETE, Blocks.WHITE_CONCRETE, Blocks.YELLOW_CONCRETE);
		Register("concrete_powder", Blocks.BLACK_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER,
				Blocks.GRAY_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER,
				Blocks.LIME_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER,
				Blocks.PURPLE_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER);
		Register("conduit", Blocks.CONDUIT);
		Register("coral_fan", Blocks.BRAIN_CORAL, Blocks.BUBBLE_CORAL, Blocks.FIRE_CORAL, Blocks.HORN_CORAL, Blocks.TUBE_CORAL,
				Blocks.BRAIN_CORAL_FAN, Blocks.BUBBLE_CORAL_FAN, Blocks.FIRE_CORAL_FAN, Blocks.HORN_CORAL_FAN, Blocks.TUBE_CORAL_FAN,
				Blocks.BRAIN_CORAL_WALL_FAN, Blocks.BUBBLE_CORAL_WALL_FAN, Blocks.FIRE_CORAL_WALL_FAN, Blocks.HORN_CORAL_WALL_FAN, Blocks.TUBE_CORAL_WALL_FAN);
		Register("crafting_table", Blocks.CRAFTING_TABLE);
		Register(BlockSounds.BreakPlace("crop", SoundEvents.BLOCK_CROP_BREAK, SoundEvents.ITEM_CROP_PLANT),
				Blocks.BEETROOTS, Blocks.CARROTS, Blocks.POTATOES, Blocks.WHEAT,
				/* Farmer's Delight */ BlocksRegistry.CABBAGE_CROP.get(), BlocksRegistry.ONION_CROP.get(), BlocksRegistry.TOMATO_CROP.get()); //TODO: Decorative
		Register("cutter", Blocks.STONECUTTER);
		Register("daylight_detector", Blocks.DAYLIGHT_DETECTOR);
		Register("dead_coral", Blocks.DEAD_BRAIN_CORAL, Blocks.DEAD_BUBBLE_CORAL, Blocks.DEAD_FIRE_CORAL, Blocks.DEAD_HORN_CORAL, Blocks.DEAD_TUBE_CORAL,
				Blocks.DEAD_BRAIN_CORAL_FAN, Blocks.DEAD_BUBBLE_CORAL_FAN, Blocks.DEAD_FIRE_CORAL_FAN, Blocks.DEAD_HORN_CORAL_FAN, Blocks.DEAD_TUBE_CORAL_FAN,
				Blocks.DEAD_BRAIN_CORAL_WALL_FAN, Blocks.DEAD_BUBBLE_CORAL_WALL_FAN, Blocks.DEAD_FIRE_CORAL_WALL_FAN, Blocks.DEAD_HORN_CORAL_WALL_FAN, Blocks.DEAD_TUBE_CORAL_WALL_FAN,
				Blocks.DEAD_BRAIN_CORAL_BLOCK, Blocks.DEAD_BUBBLE_CORAL_BLOCK, Blocks.DEAD_FIRE_CORAL_BLOCK, Blocks.DEAD_HORN_CORAL_BLOCK, Blocks.DEAD_TUBE_CORAL_BLOCK);
		Register("deepslate_coal_ore", Blocks.DEEPSLATE_COAL_ORE);
		Register("deepslate_copper_ore", Blocks.DEEPSLATE_COPPER_ORE);
		Register("deepslate_diamond_ore", Blocks.DEEPSLATE_DIAMOND_ORE);
		Register("deepslate_emerald_ore", Blocks.DEEPSLATE_EMERALD_ORE);
		Register("deepslate_gold_ore", Blocks.DEEPSLATE_GOLD_ORE);
		Register("deepslate_iron_ore", Blocks.DEEPSLATE_IRON_ORE);
		Register("deepslate_lapis_ore", Blocks.DEEPSLATE_LAPIS_ORE);
		Register("deepslate_redstone_ore", Blocks.DEEPSLATE_REDSTONE_ORE);
		Register("diamond", Blocks.DIAMOND_BLOCK,
				/* Mod */ DIAMOND_STAIRS.asBlock(), DIAMOND_SLAB.asBlock(), DIAMOND_WALL.asBlock(),
				DIAMOND_BRICKS.asBlock(), DIAMOND_BRICK_STAIRS.asBlock(), DIAMOND_BRICK_SLAB.asBlock(), DIAMOND_BRICK_WALL.asBlock());
		Register("diorite", Blocks.DIORITE, Blocks.DIORITE_STAIRS, Blocks.DIORITE_SLAB, Blocks.DIORITE_WALL,
				Blocks.POLISHED_DIORITE, Blocks.POLISHED_DIORITE_STAIRS, Blocks.POLISHED_DIORITE_SLAB,
				/* Mod */ POLISHED_DIORITE_WALL.asBlock());
		Register("dirt", Blocks.DIRT);
		Register("dirt_path", Blocks.DIRT_PATH);
		Register("dispenser", Blocks.DISPENSER);
		Register("dragon_egg", Blocks.DRAGON_EGG);
		Register("dragon_head", Blocks.DRAGON_HEAD, Blocks.DRAGON_WALL_HEAD);
		Register("dried_kelp", Blocks.DRIED_KELP_BLOCK);
		Register("dropper", Blocks.DROPPER);
		Register("emerald", Blocks.EMERALD_BLOCK,
				/* Mod */ CUT_EMERALD.asBlock(), CUT_EMERALD_STAIRS.asBlock(), CUT_EMERALD_SLAB.asBlock(), CUT_EMERALD_WALL.asBlock(),
				EMERALD_BRICKS.asBlock(), EMERALD_BRICK_STAIRS.asBlock(), EMERALD_BRICK_SLAB.asBlock(), EMERALD_BRICK_WALL.asBlock());
		Register("enchanting_table", Blocks.ENCHANTING_TABLE);
		Register("end_portal_frame", Blocks.END_PORTAL_FRAME);
		Register("end_rod", Blocks.END_ROD);
		Register("ender_chest", Blocks.ENDER_CHEST);
		Register("farmland", Blocks.FARMLAND);
		Register("fire", Blocks.FIRE, Blocks.SOUL_FIRE);
		Register("fletching_table", Blocks.FLETCHING_TABLE);
		Register("flower", Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.CORNFLOWER, Blocks.DANDELION,
				Blocks.LILAC, Blocks.LILY_OF_THE_VALLEY, Blocks.ORANGE_TULIP, Blocks.OXEYE_DAISY, Blocks.PEONY,
				Blocks.PINK_TULIP, Blocks.POPPY, Blocks.RED_TULIP, Blocks.SUNFLOWER, Blocks.WHITE_TULIP, Blocks.WITHER_ROSE);
		Register("furnace", Blocks.FURNACE, Blocks.BLAST_FURNACE, Blocks.SMOKER);
		Register("glass_pane", Blocks.BLACK_STAINED_GLASS_PANE, Blocks.BLUE_STAINED_GLASS_PANE, Blocks.BROWN_STAINED_GLASS_PANE, Blocks.CYAN_STAINED_GLASS_PANE,
				Blocks.GRAY_STAINED_GLASS_PANE, Blocks.GREEN_STAINED_GLASS_PANE, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE,
				Blocks.LIME_STAINED_GLASS_PANE, Blocks.MAGENTA_STAINED_GLASS_PANE, Blocks.ORANGE_STAINED_GLASS_PANE, Blocks.PINK_STAINED_GLASS_PANE,
				Blocks.PURPLE_STAINED_GLASS_PANE, Blocks.RED_STAINED_GLASS_PANE, Blocks.WHITE_STAINED_GLASS_PANE, Blocks.YELLOW_STAINED_GLASS_PANE,
				Blocks.GLASS_PANE, /* Mod */ TINTED_GLASS_PANE.asBlock());
		Register("glazed_terracotta", Blocks.BLACK_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA, Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.CYAN_GLAZED_TERRACOTTA,
				Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA,
				Blocks.LIME_GLAZED_TERRACOTTA, Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA, Blocks.PINK_GLAZED_TERRACOTTA,
				Blocks.PURPLE_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA, Blocks.YELLOW_GLAZED_TERRACOTTA);
		Register("glow_lichen", Blocks.GLOW_LICHEN);
		Register("glowstone", Blocks.GLOWSTONE);
		Register("gold", Blocks.GOLD_BLOCK, Blocks.RAW_GOLD_BLOCK, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
		Register("granite", Blocks.GRANITE, Blocks.GRANITE_STAIRS, Blocks.GRANITE_SLAB, Blocks.GRANITE_WALL,
				Blocks.POLISHED_GRANITE, Blocks.POLISHED_GRANITE_STAIRS, Blocks.POLISHED_GRANITE_SLAB,
				/* Mod */ POLISHED_GRANITE_WALL.asBlock());
		Register("grindstone", Blocks.GRINDSTONE);
		Register("hay", Blocks.HAY_BLOCK);
		Register("head", Blocks.PLAYER_HEAD, Blocks.PLAYER_WALL_HEAD, Blocks.ZOMBIE_HEAD, Blocks.ZOMBIE_WALL_HEAD,
				Blocks.CREEPER_HEAD, Blocks.CREEPER_WALL_HEAD, /* Mob */ PIGLIN_HEAD.asBlock(), PIGLIN_HEAD.getWallBlock());
		Register("honeycomb", Blocks.HONEYCOMB_BLOCK);
		Register("hopper", Blocks.HOPPER);
		Register("ice", Blocks.ICE, Blocks.BLUE_ICE, Blocks.FROSTED_ICE, Blocks.PACKED_ICE);
		Register("infested_cobblestone", Blocks.INFESTED_COBBLESTONE, Blocks.INFESTED_CHISELED_STONE_BRICKS,
				Blocks.INFESTED_CRACKED_STONE_BRICKS, Blocks.INFESTED_DEEPSLATE, Blocks.INFESTED_MOSSY_STONE_BRICKS,
				Blocks.INFESTED_STONE, Blocks.INFESTED_STONE_BRICKS);
		Register("infested_deepslate", Blocks.INFESTED_DEEPSLATE);
		Register("infested_stone", Blocks.INFESTED_CHISELED_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS,
				Blocks.INFESTED_MOSSY_STONE_BRICKS, Blocks.INFESTED_STONE, Blocks.INFESTED_STONE_BRICKS);
		Register("iron", Blocks.IRON_BLOCK, Blocks.RAW_IRON_BLOCK, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
				/* Mod */ IRON_BUTTON.asBlock());
		Register("iron_bars", Blocks.IRON_BARS);
		Register("iron_door", Blocks.IRON_DOOR);
		Register("iron_trapdoor", Blocks.IRON_TRAPDOOR);
		Register("jukebox", Blocks.JUKEBOX);
		Register("kelp", Blocks.KELP, Blocks.KELP_PLANT);
		Register("lapis", Blocks.LAPIS_BLOCK);
		Register("leaves", Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LEAVES,
				Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES,
				/* Mod */ MANGROVE_LEAVES.asBlock(), HEDGE_BLOCK.asBlock());
		Register("lectern", Blocks.LECTERN);
		Register(BlockSounds.Place("lily_pad", SoundEvents.BLOCK_LILY_PAD_PLACE), Blocks.LILY_PAD);
		Register("loom", Blocks.LOOM);
		Register("magma", Blocks.MAGMA_BLOCK);
		Register("melon", Blocks.MELON);
		//Register("moss_bed", /* Mod */ MOSS_BED.asBlock());
		Register("mushroom", Blocks.MUSHROOM_STEM, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM_BLOCK);
		Register("mushroom_plant", Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM);
		Register("mycelium", Blocks.MYCELIUM);
		Register(NETHER_BARK_BLOCK, Blocks.CRIMSON_HYPHAE, Blocks.WARPED_HYPHAE);
		Register("nether_brick_fence", Blocks.NETHER_BRICK_FENCE);
		Register(BlockSounds.BreakPlace("nether_wart", SoundEvents.BLOCK_NETHER_WART_BREAK, SoundEvents.ITEM_NETHER_WART_PLANT), Blocks.NETHER_WART);
		Register(NETHER_WOOD_BLOCK, Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_STAIRS, Blocks.CRIMSON_SLAB,
				Blocks.CRIMSON_BUTTON, Blocks.CRIMSON_PRESSURE_PLATE, Blocks.CRIMSON_SIGN, Blocks.CRIMSON_WALL_SIGN,
				Blocks.WARPED_PLANKS, Blocks.WARPED_STAIRS, Blocks.WARPED_SLAB, Blocks.WARPED_BUTTON,
				Blocks.WARPED_PRESSURE_PLATE, Blocks.WARPED_SIGN, Blocks.WARPED_WALL_SIGN,
				/* Mod */ CRIMSON_BOOKSHELF.asBlock(), WARPED_BOOKSHELF.asBlock());
		Register("nether_wood_door", Blocks.CRIMSON_DOOR, Blocks.WARPED_DOOR);
		Register("nether_wood_fence", Blocks.CRIMSON_FENCE, Blocks.WARPED_FENCE);
		Register("nether_wood_fence_gate", Blocks.CRIMSON_FENCE_GATE, Blocks.WARPED_FENCE_GATE);
		Register("nether_wood_ladder", /* Mod */ CRIMSON_LADDER.asBlock(), WARPED_LADDER.asBlock());
		Register("nether_wood_trapdoor", Blocks.CRIMSON_TRAPDOOR, Blocks.WARPED_TRAPDOOR);
		Register("netherite_bars", /* Mod */ NETHERITE_BARS.asBlock());
		Register("netherite_chain", /* Mod */ NETHERITE_CHAIN.asBlock());
		Register("netherrack_gold_ore", Blocks.NETHER_GOLD_ORE);
		Register("netherrack_quartz_ore", Blocks.NETHER_QUARTZ_ORE);
		Register("note_block", Blocks.NOTE_BLOCK);
		Register("observer", Blocks.OBSERVER);
		Register("obsidian", Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN);
		Register("podzol", Blocks.PODZOL);
		Register("pot", Blocks.FLOWER_POT);
		Register("potted_plant", Blocks.POTTED_ACACIA_SAPLING, Blocks.POTTED_BIRCH_SAPLING, Blocks.POTTED_DARK_OAK_SAPLING,
				Blocks.POTTED_JUNGLE_SAPLING, Blocks.POTTED_OAK_SAPLING, Blocks.POTTED_SPRUCE_SAPLING,
				Blocks.POTTED_FERN, Blocks.POTTED_DEAD_BUSH, Blocks.POTTED_BAMBOO, Blocks.POTTED_CACTUS,
				Blocks.POTTED_ALLIUM, Blocks.POTTED_AZURE_BLUET, Blocks.POTTED_BLUE_ORCHID, Blocks.POTTED_CORNFLOWER,
				Blocks.POTTED_DANDELION, Blocks.POTTED_LILY_OF_THE_VALLEY,  Blocks.POTTED_ORANGE_TULIP,
				Blocks.POTTED_OXEYE_DAISY, Blocks.POTTED_PINK_TULIP, Blocks.POTTED_POPPY, Blocks.POTTED_RED_TULIP,
				Blocks.POTTED_WHITE_TULIP, Blocks.POTTED_WITHER_ROSE, Blocks.POTTED_AZALEA_BUSH, Blocks.POTTED_FLOWERING_AZALEA_BUSH,
				Blocks.POTTED_CRIMSON_FUNGUS, Blocks.POTTED_CRIMSON_ROOTS, Blocks.POTTED_WARPED_FUNGUS, Blocks.POTTED_WARPED_ROOTS,
				Blocks.POTTED_RED_MUSHROOM, Blocks.POTTED_BROWN_MUSHROOM);
		Register("prismarine", Blocks.PRISMARINE, Blocks.PRISMARINE_STAIRS, Blocks.PRISMARINE_SLAB, Blocks.PRISMARINE_WALL,
				Blocks.PRISMARINE_BRICKS, Blocks.PRISMARINE_BRICK_STAIRS, Blocks.PRISMARINE_BRICK_SLAB,
				Blocks.DARK_PRISMARINE, Blocks.DARK_PRISMARINE_STAIRS, Blocks.DARK_PRISMARINE_SLAB,
				/* Mod */DARK_PRISMARINE_WALL.asBlock());
		Register("pumpkin", Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN);
		Register("purpur", Blocks.PURPUR_BLOCK, Blocks.PURPUR_STAIRS, Blocks.PURPUR_SLAB, Blocks.PURPUR_PILLAR,
				/* Mod */ PURPUR_WALL.asBlock());
		Register("quartz", Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_STAIRS, Blocks.QUARTZ_SLAB, Blocks.QUARTZ_PILLAR,
				Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_QUARTZ_STAIRS, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.CHISELED_QUARTZ_BLOCK,
				Blocks.QUARTZ_BRICKS, /* Mod */ QUARTZ_BRICK_STAIRS.asBlock(), QUARTZ_BRICK_SLAB.asBlock(), QUARTZ_BRICK_WALL.asBlock(),
				QUARTZ_CRYSTAL_BLOCK.asBlock(), QUARTZ_CRYSTAL_STAIRS.asBlock(), QUARTZ_CRYSTAL_SLAB.asBlock(), QUARTZ_CRYSTAL_WALL.asBlock(),
				QUARTZ_WALL.asBlock(), SMOOTH_QUARTZ_WALL.asBlock());
		Register("rail", Blocks.RAIL, Blocks.ACTIVATOR_RAIL, Blocks.DETECTOR_RAIL, Blocks.POWERED_RAIL);
		Register("redstone", Blocks.REDSTONE_BLOCK);
		Register("redstone_components", Blocks.REPEATER, Blocks.COMPARATOR);
		Register("redstone_lamp", Blocks.REDSTONE_LAMP);
		Register("redstone_wire", Blocks.REDSTONE_WIRE);
		Register("reinforced_deepslate", REINFORCED_DEEPSLATE.asBlock());
		Register("respawn_anchor", Blocks.RESPAWN_ANCHOR);
		Register("sandstone", Blocks.SANDSTONE, Blocks.SANDSTONE_STAIRS, Blocks.SANDSTONE_SLAB, Blocks.SANDSTONE_WALL,
				Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_SANDSTONE_STAIRS, Blocks.SMOOTH_SANDSTONE_SLAB,
				Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE_STAIRS, Blocks.RED_SANDSTONE_SLAB, Blocks.RED_SANDSTONE_WALL,
				Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE_STAIRS, Blocks.SMOOTH_RED_SANDSTONE_SLAB,
				Blocks.CUT_SANDSTONE, Blocks.CUT_SANDSTONE_SLAB, Blocks.CUT_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE_SLAB,
				Blocks.CHISELED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE,
				/* Mod */ SMOOTH_SANDSTONE_WALL.asBlock(), SMOOTH_RED_SANDSTONE_WALL.asBlock());
		Register("sapling", Blocks.ACACIA_SAPLING, Blocks.BIRCH_SAPLING, Blocks.DARK_OAK_SAPLING,
				Blocks.JUNGLE_SAPLING, Blocks.OAK_SAPLING, Blocks.SPRUCE_SAPLING); //TODO: Decorative
		Register(BlockSounds.StepOnly(ModSoundEvents.BLOCK_SCULK_STEP),
				/* Mod */ CALCITE_SCULK_TURF.asBlock(), DEEPSLATE_SCULK_TURF.asBlock(), DRIPSTONE_SCULK_TURF.asBlock(),
				SMOOTH_BASALT_SCULK_TURF.asBlock(), TUFF_SCULK_TURF.asBlock());
		Register("sea_lantern", Blocks.SEA_LANTERN);
		Register("sea_pickle", Blocks.SEA_PICKLE);
		Register("seagrass", Blocks.SEAGRASS, Blocks.TALL_SEAGRASS);
		Register("seeds", /* Mod */ SEED_BLOCK.asBlock());
		Register("shulker_box", Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX,
				Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX,
				Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX,
				Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX,
				Blocks.SHULKER_BOX);
		Register("skull", Blocks.SKELETON_SKULL, Blocks.SKELETON_WALL_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.WITHER_SKELETON_WALL_SKULL);
		Register("smithing_table", Blocks.SMITHING_TABLE);
		Register("spawner", Blocks.SPAWNER);
		Register("sponge", Blocks.SPONGE);
		Register("stem", Blocks.MELON_STEM, Blocks.PUMPKIN_STEM);
		Register("stem_attached", Blocks.ATTACHED_MELON_STEM, Blocks.ATTACHED_PUMPKIN_STEM);
		Register("stone_coal_ore", Blocks.COAL_ORE);
		Register("stone_copper_ore", Blocks.COPPER_ORE);
		Register("stone_diamond_ore", Blocks.DIAMOND_ORE);
		Register("stone_emerald_ore", Blocks.EMERALD_ORE);
		Register("stone_gold_ore", Blocks.GOLD_ORE);
		Register("stone_iron_ore", Blocks.IRON_ORE);
		Register("stone_lapis_ore", Blocks.LAPIS_ORE);
		Register("stone_redstone_ore", Blocks.REDSTONE_ORE);
		Register("sugar_cane", Blocks.SUGAR_CANE); //TODO: Decorative
		Register(BlockSounds.BreakPlace("sweet_berry_bush", SoundEvents.BLOCK_SWEET_BERRY_BUSH_BREAK, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE), Blocks.SWEET_BERRY_BUSH);
		Register("target", Blocks.TARGET);
		Register("terracotta", Blocks.BLACK_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.CYAN_TERRACOTTA,
				Blocks.GRAY_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA,
				Blocks.LIME_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.PINK_TERRACOTTA,
				Blocks.PURPLE_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA);
		Register("tnt", Blocks.TNT);
		Register("tripwire", Blocks.TRIPWIRE, Blocks.TRIPWIRE_HOOK);
		Register("turtle_egg", Blocks.TURTLE_EGG);
		Register("wet_sponge", Blocks.WET_SPONGE);
		Register("wood_door", Blocks.ACACIA_DOOR, Blocks.BIRCH_DOOR, Blocks.DARK_OAK_DOOR, Blocks.JUNGLE_DOOR,
				Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR, /* Mod */ MANGROVE_DOOR.asBlock(), CHARRED_DOOR.asBlock());
		Register("wood_fence", Blocks.ACACIA_FENCE, Blocks.BIRCH_FENCE, Blocks.DARK_OAK_FENCE, Blocks.JUNGLE_FENCE,
				Blocks.OAK_FENCE, Blocks.SPRUCE_FENCE, /* Mod */ MANGROVE_FENCE.asBlock(), CHARRED_FENCE.asBlock());
		Register("wood_fence_gate", Blocks.ACACIA_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE,
				Blocks.JUNGLE_FENCE_GATE, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE,
				/* Mod */ MANGROVE_FENCE_GATE.asBlock(), CHARRED_FENCE_GATE.asBlock());
		Register("wood_trapdoor", Blocks.ACACIA_TRAPDOOR, Blocks.BIRCH_TRAPDOOR, Blocks.DARK_OAK_TRAPDOOR,
				Blocks.JUNGLE_TRAPDOOR, Blocks.OAK_TRAPDOOR, Blocks.SPRUCE_TRAPDOOR,
				/* Mod */ MANGROVE_TRAPDOOR.asBlock(), CHARRED_TRAPDOOR.asBlock());
		Register("woodcutter", /* Mod */ ACACIA_WOODCUTTER.asBlock(), BIRCH_WOODCUTTER.asBlock(), CRIMSON_WOODCUTTER.asBlock(), DARK_OAK_WOODCUTTER.asBlock(),
				JUNGLE_WOODCUTTER.asBlock(), MANGROVE_WOODCUTTER.asBlock(), WOODCUTTER.asBlock(), SPRUCE_WOODCUTTER.asBlock(), WARPED_WOODCUTTER.asBlock(),
				CHARRED_WOODCUTTER.asBlock(), BAMBOO_WOODCUTTER.asBlock());
	}
	private static final BlockSounds BARK_BLOCK = new BlockSounds("bark");
	private static final BlockSounds NETHER_BARK_BLOCK = new BlockSounds("nether_bark");
	private static final BlockSounds NETHER_WOOD_BLOCK = new BlockSounds(ModBlockSoundGroups.NETHER_WOOD);
	private static BlockSounds getSounds(Block block) {
		if (blockSounds.containsKey(block)) return blockSounds.get(block);
		Identifier id = Registry.BLOCK.getId(block);
		if (copyBlockSounds.containsKey(id)) return getSounds(copyBlockSounds.get(id));
		return null;
	}
	public static SoundEvent getBreakSound(BlockState state) {
		BlockSounds sounds = getSounds(state.getBlock());
		if (sounds != null && sounds.breakSound != null) return sounds.breakSound;
		return null;
	}
	private static final BlockSounds PISTON_BLOCK = new BlockSounds("piston");
	private static final SoundEvent STICKY_PISTON_TOP_STEP = BlockSounds.makeStep("sticky_piston_top");
	public static SoundEvent getStepSound(BlockState state) {
		Block block = state.getBlock();
		if (block == Blocks.STICKY_PISTON) return state.get(PistonBlock.FACING) == Direction.UP ? STICKY_PISTON_TOP_STEP : PISTON_BLOCK.stepSound;
		if (block == Blocks.PISTON) return state.get(PistonBlock.FACING) == Direction.UP ? SoundEvents.BLOCK_WOOD_STEP : PISTON_BLOCK.stepSound;
		if (block == Blocks.PISTON_HEAD) {
			if (state.get(PistonBlock.FACING) == Direction.UP) {
				return state.get(PistonHeadBlock.TYPE) == PistonType.STICKY ? STICKY_PISTON_TOP_STEP : SoundEvents.BLOCK_WOOD_STEP;
			}
			return PISTON_BLOCK.stepSound;
		}
		if (block == Blocks.CRIMSON_STEM || block == Blocks.WARPED_STEM) {
			return state.get(PillarBlock.AXIS) != Direction.Axis.Y ? NETHER_BARK_BLOCK.stepSound : ModSoundEvents.BLOCK_NETHER_WOOD_STEP;
		}
		if (block == Blocks.ACACIA_LOG || block == Blocks.BIRCH_LOG || block == Blocks.DARK_OAK_LOG
				|| block == Blocks.JUNGLE_LOG || block == Blocks.OAK_LOG || block == Blocks.SPRUCE_LOG
				|| MANGROVE_LOG.contains(block) || CHARRED_LOG.contains(block)) {
			return state.get(PillarBlock.AXIS) != Direction.Axis.Y ? BARK_BLOCK.stepSound : SoundEvents.BLOCK_WOOD_STEP;
		}
		if (state.isIn(ModBlockTags.SCULK_TURFS)) return ModBlockSoundGroups.SCULK.getStepSound();
		BlockSounds sounds = getSounds(block);
		if (sounds != null && sounds.stepSound != null) return sounds.stepSound;
		return null;
	}
	public static SoundEvent getPlaceSound(BlockState state) {
		BlockSounds sounds = getSounds(state.getBlock());
		if (sounds != null && sounds.placeSound != null) return sounds.placeSound;
		return null;
	}
	public static SoundEvent getHitSound(BlockState state) {
		BlockSounds sounds = getSounds(state.getBlock());
		if (sounds != null && sounds.hitSound != null) return sounds.hitSound;
		return null;
	}
}
