package fun.mousewich.sound;

import fun.mousewich.ModBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fun.mousewich.ModBase.*;

public class SubtitleOverrides {
	private static final Map<Identifier, String> subtitles = new HashMap<>();
	private static final Map<WeightedSoundSet, String> weightedSubtitles = new HashMap<>();

	public static void Register(SoundEvent soundEvent, String name, String... translations) { Register(soundEvent, name, List.of(translations)); }
	public static void Register(SoundEvent soundEvent, String name, List<String> translations) { Register(soundEvent.getId(), name, translations); }
	public static void Register(Identifier id, String name, String... translations) { Register(id, name, List.of(translations)); }
	public static void Register(Identifier id, String name, List<String> translations) {
		if (!subtitles.containsKey(id)) {
			Register(name, translations);
			subtitles.put(id, IdentifiedSounds.IDENTIFY + name);
		}
	}
	private static void Register(BlockSoundGroup soundGroup, String block, String... translations) { Register(soundGroup, block, List.of(translations)); }
	private static void Register(BlockSoundGroup soundGroup, String block, List<String> translations) {
		int length = translations.size();
		List<String> breakTranslations = new ArrayList<>(), hitTranslations = new ArrayList<>();
		List<String> placeTranslations = new ArrayList<>(), stepTranslations = new ArrayList<>();
		for (int i = 0; i < ModBase.LANGUAGE_CACHES.length; i++) {
			if (length <= i) throw new RuntimeException("Missing translation for Language: " + LANGUAGE_CACHES[i].getLanguageCode() + " & BlockSoundGroup");
			String translation = translations.get(i);
			breakTranslations.add(LANGUAGE_CACHES[i].Subtitle_Block_Break(translation));
			hitTranslations.add(LANGUAGE_CACHES[i].Subtitle_Block_Hit(translation));
			placeTranslations.add(LANGUAGE_CACHES[i].Subtitle_Block_Place(translation));
			stepTranslations.add(LANGUAGE_CACHES[i].Subtitle_Block_Step(translation));
		}
		Register(soundGroup.getBreakSound(), "block.break." + block, breakTranslations);
		Register(soundGroup.getHitSound(), "block.hit." + block, hitTranslations);
		Register(soundGroup.getPlaceSound(), "block.place." + block, placeTranslations);
		Register(soundGroup.getStepSound(), "block.step." + block, stepTranslations);
	}
	public static void Register(String name, String... translations) { Register(name, List.of(translations)); }
	public static void Register(String name, List<String> translations) {
		int length = translations.size();
		String subtitle = IdentifiedSounds.IDENTIFY + name;
		for (int i = 0; i < LANGUAGE_CACHES.length; i++) {
			if (length <= i) throw new RuntimeException("Missing translation for Language: " + LANGUAGE_CACHES[i].getLanguageCode() + " & Subtitle: " + subtitle);
			LANGUAGE_CACHES[i].TranslationKeys.put(subtitle, translations.get(i));
		}
	}

	static {
		Register("block.break.generic", EN_US.Subtitle_Block_Break(EN_US.Block()));
		Register("block.step.generic", EN_US.Subtitle_Block_Step(EN_US.Block()));
		Register("block.hit.generic", EN_US.Subtitle_Block_Hit(EN_US.Block()));
		Register("block.place.generic", EN_US.Subtitle_Block_Place(EN_US.Block()));
		//<editor-fold desc="Blocks">
		Register(BlockSoundGroup.AMETHYST_BLOCK, "amethyst", EN_US.Amethyst());
		Register(BlockSoundGroup.AMETHYST_CLUSTER, "amethyst_cluster", EN_US.AmethystCluster());
		Register(BlockSoundGroup.ANCIENT_DEBRIS, "ancient_debris", EN_US.AncientDebris());
		Register(BlockSoundGroup.ANVIL, "anvil", EN_US.Anvil());
		Register(BlockSoundGroup.AZALEA, "azalea", EN_US.Azalea());
		Register(BlockSoundGroup.AZALEA_LEAVES, "azalea_leaves", EN_US.AzaleaLeaves());
		Register(BlockSoundGroup.BAMBOO, "bamboo", EN_US.Bamboo());
		Register(SoundEvents.BLOCK_BAMBOO_SAPLING_BREAK, "block.break.bamboo_sapling", EN_US.Subtitle_Block_Break(EN_US.BambooSapling()));
		Register(SoundEvents.BLOCK_BAMBOO_SAPLING_HIT, "block.hit.bamboo_sapling", EN_US.Subtitle_Block_Hit(EN_US.BambooSapling()));
		Register(SoundEvents.BLOCK_BAMBOO_SAPLING_PLACE, "block.place.bamboo_sapling", EN_US.Subtitle_Block_Place(EN_US.BambooSapling()));
		Register(BlockSoundGroup.BASALT, "basalt", EN_US.Basalt());
		Register(BlockSoundGroup.BIG_DRIPLEAF, "big_dripleaf", EN_US.BigDripleaf());
		Register(BlockSoundGroup.BONE, "bone", EN_US.Bone());
		Register(BlockSoundGroup.CALCITE, "calcite", EN_US.Calcite());
		Register(BlockSoundGroup.CANDLE, "candle", EN_US.Candle());
		Register(BlockSoundGroup.CAVE_VINES, "cave_vines", EN_US.CaveVines());
		Register(BlockSoundGroup.CHAIN, "chain", EN_US.Chain());
		Register(BlockSoundGroup.COPPER, "copper", EN_US.Copper());
		Register(BlockSoundGroup.CORAL, "coral", EN_US.Coral());
		Register(SoundEvents.BLOCK_CROP_BREAK, "block.break.crop", EN_US.Subtitle_Block_Break(EN_US.Crop()));
		Register(SoundEvents.ITEM_CROP_PLANT, "block.place.crop", EN_US.Subtitle_Block_Place(EN_US.Crop()));
		Register(BlockSoundGroup.DEEPSLATE, "deepslate", EN_US.Deepslate());
		Register(BlockSoundGroup.DEEPSLATE_BRICKS, "deepslate_bricks", EN_US.Bricks(EN_US.Deepslate()));
		Register(BlockSoundGroup.DEEPSLATE_TILES, "deepslate_tiles", EN_US.Tiles(EN_US.Deepslate()));
		Register(BlockSoundGroup.DRIPSTONE_BLOCK, "dripstone", EN_US.Dripstone());
		Register(BlockSoundGroup.FLOWERING_AZALEA, "flowering_azalea", EN_US.FloweringAzalea());
		Register(BlockSoundGroup.FUNGUS, "fungus", EN_US.Fungus());
		Register(BlockSoundGroup.GILDED_BLACKSTONE, "gilded_blackstone", EN_US.GildedBlackstone());
		Register(BlockSoundGroup.GLASS, "glass", EN_US.Glass());
		Register(BlockSoundGroup.GRASS, "grass", EN_US.Grass());
		Register(BlockSoundGroup.GRAVEL, "gravel", EN_US.Gravel());
		Register(BlockSoundGroup.HANGING_ROOTS, "hanging_roots", EN_US.HangingRoots());
		Register(BlockSoundGroup.HONEY, "honey", EN_US.Honey());
		Register(BlockSoundGroup.LADDER, "ladder", EN_US.Ladder());
		Register(BlockSoundGroup.LANTERN, "lantern", EN_US.Lantern());
		Register(SoundEvents.BLOCK_LARGE_AMETHYST_BUD_BREAK, "block.break.large_amethyst_bud", EN_US.Subtitle_Block_Break(EN_US.AmethystBud(EN_US.Large())));
		Register(SoundEvents.BLOCK_LARGE_AMETHYST_BUD_PLACE, "block.place.large_amethyst_bud", EN_US.Subtitle_Block_Place(EN_US.AmethystBud(EN_US.Large())));
		Register(SoundEvents.BLOCK_LILY_PAD_PLACE, "block.place.lily_pad", EN_US.Subtitle_Block_Place(EN_US.LilyPad()));
		Register(BlockSoundGroup.LODESTONE, "lodestone", EN_US.Lodestone());
		Register(SoundEvents.BLOCK_MEDIUM_AMETHYST_BUD_BREAK, "block.break.medium_amethyst_bud", EN_US.Subtitle_Block_Break(EN_US.AmethystBud(EN_US.Medium())));
		Register(SoundEvents.BLOCK_MEDIUM_AMETHYST_BUD_PLACE, "block.place.place.medium_amethyst_bud", EN_US.Subtitle_Block_Place(EN_US.AmethystBud(EN_US.Medium())));
		Register(BlockSoundGroup.METAL, "metal", EN_US.Metal());
		Register(BlockSoundGroup.MOSS_CARPET, "moss_carpet", EN_US.MossCarpet());
		Register(BlockSoundGroup.MOSS_BLOCK, "moss", EN_US.Moss());
		Register(BlockSoundGroup.NETHER_BRICKS, "nether_bricks", EN_US.NetherBricks());
		Register(BlockSoundGroup.NETHER_GOLD_ORE, "nether_gold_ore", EN_US.NetherGoldOre());
		Register(BlockSoundGroup.NETHER_ORE, "nether_ore", EN_US.NetherOre());
		Register(BlockSoundGroup.NETHER_SPROUTS, "nether_sprouts", EN_US.NetherSprouts());
		Register(BlockSoundGroup.NETHER_STEM, "nether_stem", EN_US.NetherStem());
		Register(SoundEvents.BLOCK_NETHER_WART_BREAK, "block.break.nether_wart", EN_US.Subtitle_Block_Break(EN_US.NetherWart()));
		Register(SoundEvents.ITEM_NETHER_WART_PLANT, "block.place.nether_wart", EN_US.Subtitle_Block_Place(EN_US.NetherWart()));
		Register(BlockSoundGroup.NETHERITE, "netherite", EN_US.Netherite());
		Register(BlockSoundGroup.NETHERRACK, "netherrack", EN_US.Netherrack());
		Register(BlockSoundGroup.NYLIUM, "nylium", EN_US.Nylium());
		Register(BlockSoundGroup.POINTED_DRIPSTONE, "pointed_dripstone", EN_US.PointedDripstone());
		Register(SoundEvents.BLOCK_POINTED_DRIPSTONE_LAND, "block.land.pointed_dripstone", EN_US.fell(EN_US.PointedDripstone()));
		Register(BlockSoundGroup.POLISHED_DEEPSLATE, "polished_deepslate", EN_US.PolishedDeepslate());
		Register(BlockSoundGroup.POWDER_SNOW, "powder_snow", EN_US.PowderSnow());
		Register(BlockSoundGroup.ROOTED_DIRT, "rooted_dirt", EN_US.RootedDirt());
		Register(BlockSoundGroup.ROOTS, "roots", EN_US.Roots());
		Register(BlockSoundGroup.SAND, "sand", EN_US.Sand());
		Register(BlockSoundGroup.SCAFFOLDING, "scaffolding", EN_US.Scaffolding());
		Register(BlockSoundGroup.SCULK_SENSOR, "sculk_sensor", EN_US.SculkSensor());
		Register(BlockSoundGroup.SHROOMLIGHT, "shroomlight", EN_US.Shroomlight());
		Register(BlockSoundGroup.SLIME, "slime", EN_US.Slime());
		Register(SoundEvents.BLOCK_SMALL_AMETHYST_BUD_BREAK, "block.break.small_amethyst_bud", EN_US.Subtitle_Block_Break(EN_US.AmethystBud(EN_US.Small())));
		Register(SoundEvents.BLOCK_SMALL_AMETHYST_BUD_PLACE, "block.place.small_amethyst_bud", EN_US.Subtitle_Block_Place(EN_US.AmethystBud(EN_US.Small())));
		Register(BlockSoundGroup.SMALL_DRIPLEAF, "small_dripleaf", EN_US.SmallDripleaf());
		Register(BlockSoundGroup.SNOW, "snow", EN_US.Snow());
		Register(BlockSoundGroup.SOUL_SAND, "soul_sand", EN_US.SoulSand());
		Register(BlockSoundGroup.SOUL_SOIL, "soul_soil", EN_US.SoulSoil());
		Register(BlockSoundGroup.SPORE_BLOSSOM, "spore_blossom", EN_US.SporeBlossom());
		Register(BlockSoundGroup.STONE, "stone", EN_US.Stone());
		Register(SoundEvents.BLOCK_SWEET_BERRY_BUSH_BREAK, "block.break.sweet_berry_bush", EN_US.Subtitle_Block_Break(EN_US.SweetBerryBush()));
		Register(SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE, "block.place.sweet_berry_bush", EN_US.Subtitle_Block_Place(EN_US.SweetBerryBush()));
		Register(BlockSoundGroup.TUFF, "tuff", EN_US.Tuff());
		Register(BlockSoundGroup.VINE, "vine", EN_US.Vine());
		Register(BlockSoundGroup.WART_BLOCK, "wart_block", EN_US.WartBlock());
		Register(BlockSoundGroup.WEEPING_VINES, "weeping_vines", EN_US.WeepingVines());
		Register(BlockSoundGroup.WET_GRASS, "wet_grass", EN_US.WetGrass());
		Register(BlockSoundGroup.WOOD, "wood", EN_US.Wood());
		Register(BlockSoundGroup.WOOL, "wool", EN_US.Wool());
		//</editor-fold>
		Register(ModSoundEvents.ENTITY_GENERIC_ELYTRA_CRASH, "crash.generic", EN_US.crashes(EN_US.Something()));
		Register("crash.player", EN_US.crashes(EN_US.Someone()));
		Register(SoundEvents.ENTITY_GENERIC_BIG_FALL,"big_fall.generic", EN_US.fell(EN_US.Something()));
		Register(SoundEvents.ENTITY_PLAYER_BIG_FALL,"big_fall.player", EN_US.fell(EN_US.Someone()));
		Register("footsteps.generic", EN_US.steps(EN_US.Something()));
		Register("footsteps.player", EN_US.steps(EN_US.Someone()));
		Register(SoundEvents.ENTITY_GENERIC_SMALL_FALL,"small_fall.generic", EN_US.trips(EN_US.Something()));
		Register(SoundEvents.ENTITY_PLAYER_SMALL_FALL,"small_fall.player", EN_US.trips(EN_US.Someone()));
		//<editor-fold desc="Entity Sound Events">
		Register(SoundEvents.ENTITY_ARMOR_STAND_BREAK, "entity.armor_stand.break", EN_US.Subtitle_Block_Break(EN_US.ArmorStand()));
		Register(SoundEvents.ENTITY_ARMOR_STAND_FALL, "entity.armor_stand.fall", EN_US.fell(EN_US.ArmorStand()));
		Register(SoundEvents.ENTITY_ARMOR_STAND_HIT, "entity.armor_stand.hit", EN_US.Subtitle_Block_Hit(EN_US.ArmorStand()));
		Register(SoundEvents.ENTITY_ARMOR_STAND_PLACE, "entity.armor_stand.place", EN_US.Subtitle_Block_Place(EN_US.ArmorStand()));
		Register(SoundEvents.ENTITY_CHICKEN_STEP, "entity.chicken.step", EN_US.steps(EN_US.Chicken()));
		Register(SoundEvents.ENTITY_COW_STEP, "entity.cow.step", EN_US.steps(EN_US.Cow()));
		Register(SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, "entity.dragon.fireball_explode", EN_US.explodes(EN_US.DragonFireball()));
		Register(SoundEvents.ENTITY_ENDERMITE_STEP, "entity.endermite.step", EN_US.steps(EN_US.Endermite()));
		Register(SoundEvents.ENTITY_GOAT_STEP, "entity.goat.step", EN_US.steps(EN_US.Goat()));
		Register(SoundEvents.ENTITY_HORSE_LAND, "entity.horse.land", EN_US.fell(EN_US.Horse()));
		Register(SoundEvents.ENTITY_HORSE_SADDLE, "entity.horse.saddle", EN_US.saddled(EN_US.Horse()));
		Register(SoundEvents.ENTITY_HORSE_STEP, "entity.horse.step", EN_US.steps(EN_US.Horse()));
		Register(SoundEvents.ENTITY_HORSE_STEP_WOOD, "entity.horse.step", EN_US.steps(EN_US.Horse()));
		Register(SoundEvents.ENTITY_HUSK_STEP, "entity.husk.step", EN_US.steps(EN_US.Husk()));
		Register(SoundEvents.ENTITY_IRON_GOLEM_STEP, "entity.iron_golem.step", EN_US.steps(EN_US.IronGolem()));
		Register(SoundEvents.ENTITY_PARROT_STEP, "entity.parrot.step", EN_US.saddled(EN_US.Parrot()));
		Register(SoundEvents.ENTITY_PIG_SADDLE, "entity.pig.saddle", EN_US.saddled(EN_US.Pig()));
		Register(SoundEvents.ENTITY_PIG_STEP, "entity.pig.step", EN_US.saddled(EN_US.Pig()));
		Register(SoundEvents.ENTITY_POLAR_BEAR_STEP, "entity.polar_bear.step", EN_US.saddled(EN_US.PolarBear()));
		Register(SoundEvents.ENTITY_SHEEP_STEP, "entity.sheep.step", EN_US.saddled(EN_US.Sheep()));
		Register(SoundEvents.ENTITY_SILVERFISH_STEP, "entity.silverfish.step", EN_US.saddled(EN_US.Silverfish()));
		Register(SoundEvents.ENTITY_SKELETON_STEP, "entity.skeleton.step", EN_US.saddled(EN_US.Skeleton()));
		Register(SoundEvents.ENTITY_SKELETON_HORSE_STEP_WATER, "entity.skeleton_horse.step_water", EN_US.Water(EN_US.in(EN_US.steps(EN_US.Skeleton()))));
		Register(SoundEvents.ENTITY_SPIDER_STEP, "entity.spider.step", EN_US.saddled(EN_US.Spider()));
		Register(SoundEvents.ENTITY_STRAY_STEP, "entity.stray.step", EN_US.saddled(EN_US.Stray()));
		Register(SoundEvents.ENTITY_STRIDER_SADDLE, "entity.strider.saddle", EN_US.saddled(EN_US.Strider()));
		Register(SoundEvents.ENTITY_STRIDER_STEP, "entity.strider.step", EN_US.steps(EN_US.Strider()));
		Register(SoundEvents.ENTITY_STRIDER_STEP_LAVA, "entity.strider.step_lava", EN_US.Lava(EN_US.on(EN_US.steps(EN_US.Strider()))));
		Register(SoundEvents.ENTITY_WITHER_SKELETON_STEP, "entity.wither_skeleton.step", EN_US.steps(EN_US.WitherSkeleton()));
		Register(SoundEvents.ENTITY_WOLF_HOWL, "entity.wolf.howl", EN_US.howls(EN_US.Wolf()));
		Register(SoundEvents.ENTITY_WOLF_STEP, "entity.wolf.step", EN_US.steps(EN_US.Wolf()));
		Register(SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, "entity.zombie.attack_iron_door", EN_US.clangs(EN_US.Door()));
		Register(SoundEvents.ENTITY_ZOMBIE_STEP, "entity.zombie.step", EN_US.steps(EN_US.Zombie()));
		Register(SoundEvents.ENTITY_ZOMBIE_VILLAGER_STEP, "entity.zombie_villager.step", EN_US.steps(EN_US.ZombieVillager()));
		//</editor-fold>
		//<editor-fold desc="Mod Blocks">
		Register(ModBlockSoundGroups.BAMBOO_WOOD, "bamboo_wood", EN_US.BambooWood());
		Register(ModBlockSoundGroups.CHERRY_LEAVES, "cherry_leaves", EN_US.CherryLeaves());
		Register(ModBlockSoundGroups.CHERRY_SAPLING, "cherry_sapling", EN_US.CherrySapling());
		Register(ModBlockSoundGroups.CHERRY_WOOD, "cherry_wood", EN_US.CherryWood());
		Register(ModBlockSoundGroups.CHERRY_WOOD_HANGING_SIGN, "cherry_wood_hanging_sign", EN_US.CherryWoodHangingSign());
		Register(ModBlockSoundGroups.ECHO_BLOCK, "echo", EN_US.Echo());
		Register(ModBlockSoundGroups.ECHO_CLUSTER, "echo_cluster", EN_US.EchoCluster());
		Register(ModBlockSoundGroups.FROGLIGHT, "froglight", EN_US.Froglight());
		Register(ModBlockSoundGroups.FROGSPAWN, "frogspawn", EN_US.Frogspawn());
		Register(ModBlockSoundGroups.HANGING_SIGN, "hanging_sign", EN_US.HangingSign());
		Register(ModSoundEvents.BLOCK_LARGE_ECHO_BUD_BREAK, "block.break.large_echo_bud", EN_US.Subtitle_Block_Break(EN_US.EchoBud(EN_US.Large())));
		Register(ModSoundEvents.BLOCK_LARGE_ECHO_BUD_PLACE, "block.place.large_echo_bud", EN_US.Subtitle_Block_Place(EN_US.EchoBud(EN_US.Large())));
		Register(ModBlockSoundGroups.MANGROVE_ROOTS, "mangrove_roots", EN_US.MangroveRoots());
		Register(ModSoundEvents.BLOCK_MEDIUM_ECHO_BUD_BREAK, "block.break.medium_echo_bud", EN_US.Subtitle_Block_Break(EN_US.EchoBud(EN_US.Medium())));
		Register(ModSoundEvents.BLOCK_MEDIUM_ECHO_BUD_PLACE, "block.place.medium_echo_bud", EN_US.Subtitle_Block_Place(EN_US.EchoBud(EN_US.Medium())));
		Register(ModBlockSoundGroups.MUD, "mud", EN_US.Mud());
		Register(ModBlockSoundGroups.MUD_BRICKS, "mud_bricks", EN_US.MudBricks());
		Register(ModBlockSoundGroups.MUDDY_MANGROVE_ROOTS, "muddy_mangrove_roots", EN_US.MuddyMangroveRoots());
		Register(ModBlockSoundGroups.NETHER_WOOD, "nether_wood", EN_US.NetherWood());
		Register(ModBlockSoundGroups.PACKED_MUD, "packed_mud", EN_US.PackedMud());
		Register(ModBlockSoundGroups.PINK_PETALS, "pink_petals", EN_US.PinkPetals());
		Register(ModBlockSoundGroups.SCULK, "sculk", EN_US.Sculk());
		Register(ModBlockSoundGroups.SCULK_CATALYST, "sculk_catalyst", EN_US.SculkCatalyst());
		Register(ModBlockSoundGroups.SCULK_SHRIEKER, "sculk_shrieker", EN_US.SculkShrieker());
		Register(ModBlockSoundGroups.SCULK_VEIN, "sculk_vein", EN_US.SculkVein());
		Register(ModSoundEvents.BLOCK_SMALL_ECHO_BUD_BREAK, "block.break.small_echo_bud", EN_US.Subtitle_Block_Break(EN_US.EchoBud(EN_US.Small())));
		Register(ModSoundEvents.BLOCK_SMALL_ECHO_BUD_PLACE, "block.place.small_echo_bud", EN_US.Subtitle_Block_Place(EN_US.EchoBud(EN_US.Small())));
		//</editor-fold">
		//<editor-fold desc="Mod Entity Sound Events">
		Register(ModSoundEvents.ENTITY_CAVE_SPIDER_AMBIENT, "entity.cave_spider.ambient", EN_US.hisses(EN_US.CaveSpider()));
		Register(ModSoundEvents.ENTITY_CAVE_SPIDER_DEATH, "entity.cave_spider.death", EN_US.dies(EN_US.CaveSpider()));
		Register(ModSoundEvents.ENTITY_CAVE_SPIDER_HURT, "entity.cave_spider.hurt", EN_US.hurts(EN_US.CaveSpider()));
		Register(ModSoundEvents.ENTITY_CAVE_SPIDER_STEP, "entity.cave_spider.step", EN_US.steps(EN_US.CaveSpider()));
		Register(ModSoundEvents.ENTITY_CAMEL_LAND, "entity.camel.land", EN_US.fell(EN_US.Camel()));
		Register(ModSoundEvents.ENTITY_CAMEL_SADDLE, "entity.camel.saddle", EN_US.saddled(EN_US.Camel()));
		Register(ModSoundEvents.ENTITY_DONKEY_GALLOP, "entity.donkey.gallop", EN_US.gallops(EN_US.Donkey()));
		Register(ModSoundEvents.ENTITY_DONKEY_JUMP, "entity.donkey.jump", EN_US.jumps(EN_US.Donkey()));
		Register(ModSoundEvents.ENTITY_DONKEY_LAND, "entity.donkey.land", EN_US.fell(EN_US.Donkey()));
		Register(ModSoundEvents.ENTITY_DONKEY_SADDLE, "entity.donkey.saddle", EN_US.saddled(EN_US.Donkey()));
		Register(ModSoundEvents.ENTITY_DONKEY_STEP, "entity.donkey.step", EN_US.steps(EN_US.Donkey()));
		Register(ModSoundEvents.ENTITY_DONKEY_STEP_WOOD, "entity.donkey.step", EN_US.steps(EN_US.Donkey()));
		Register(ModSoundEvents.ENTITY_ELDER_GUARDIAN_ATTACK, "entity.edler_guardian.attack", EN_US.shoots(EN_US.ElderGuardian()));
		Register(ModSoundEvents.ENTITY_FROG_STEP, "entity.frog.step", EN_US.steps(EN_US.Frog()));
		Register(ModSoundEvents.ENTITY_HEDGEHOG_STEP, "entity.hedgehog.step", EN_US.steps(EN_US.Hedgehog()));
		Register(ModSoundEvents.ENTITY_JUMPING_SPIDER_AMBIENT, "entity.jumping_spider.ambient", EN_US.hisses(EN_US.JumpingSpider()));
		Register(ModSoundEvents.ENTITY_JUMPING_SPIDER_DEATH, "entity.jumping_spider.death", EN_US.dies(EN_US.JumpingSpider()));
		Register(ModSoundEvents.ENTITY_JUMPING_SPIDER_HURT, "entity.jumping_spider.hurt", EN_US.hurts(EN_US.JumpingSpider()));
		Register(ModSoundEvents.ENTITY_JUMPING_SPIDER_STEP, "entity.jumping_spider.step", EN_US.steps(EN_US.JumpingSpider()));
		Register(ModSoundEvents.ENTITY_MULE_JUMP, "entity.jumping_spider.jump", EN_US.jumps(EN_US.JumpingSpider()));
		Register(ModSoundEvents.ENTITY_MULE_GALLOP, "entity.mule.gallop", EN_US.gallops(EN_US.Mule()));
		Register(ModSoundEvents.ENTITY_MULE_JUMP, "entity.mule.jump", EN_US.jumps(EN_US.Mule()));
		Register(ModSoundEvents.ENTITY_MULE_LAND, "entity.mule.land", EN_US.fell(EN_US.Mule()));
		Register(ModSoundEvents.ENTITY_MULE_SADDLE, "entity.mule.saddle", EN_US.saddled(EN_US.Mule()));
		Register(ModSoundEvents.ENTITY_MULE_STEP, "entity.mule.step", EN_US.steps(EN_US.Mule()));
		Register(ModSoundEvents.ENTITY_MULE_STEP_WOOD, "entity.mule.step", EN_US.steps(EN_US.Mule()));
		Register(ModSoundEvents.ENTITY_MOOSHROOM_AMBIENT, "entity.mooshroom.ambient", EN_US.moos(EN_US.Mooshroom()));
		Register(ModSoundEvents.ENTITY_MOOSHROOM_DEATH, "entity.mooshroom.death", EN_US.dies(EN_US.Mooshroom()));
		Register(ModSoundEvents.ENTITY_MOOSHROOM_HURT, "entity.mooshroom.hurt", EN_US.hurts(EN_US.Mooshroom()));
		Register(ModSoundEvents.ENTITY_MOOSHROOM_STEP, "entity.mooshroom.step", EN_US.steps(EN_US.Mooshroom()));
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_GALLOP, "entity.skeleton_horse.gallop", EN_US.gallops(EN_US.SkeletonHorse()));
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_JUMP, "entity.skeleton_horse.jump", EN_US.jumps(EN_US.SkeletonHorse()));
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_LAND, "entity.skeleton_horse.land", EN_US.fell(EN_US.SkeletonHorse()));
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_SADDLE, "entity.skeleton_horse.saddle", EN_US.saddled(EN_US.SkeletonHorse()));
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_STEP, "entity.skeleton_horse.step", EN_US.steps(EN_US.SkeletonHorse()));
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_STEP_WOOD, "entity.skeleton_horse.step", EN_US.steps(EN_US.SkeletonHorse()));
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_GALLOP, "entity.zombie_horse.gallop", EN_US.gallops(EN_US.ZombieHorse()));
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_JUMP, "entity.zombie_horse.jump", EN_US.jumps(EN_US.ZombieHorse()));
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_LAND, "entity.zombie_horse.land", EN_US.fell(EN_US.ZombieHorse()));
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_SADDLE, "entity.zombie_horse.saddle", EN_US.saddled(EN_US.ZombieHorse()));
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_STEP, "entity.zombie_horse.step", EN_US.steps(EN_US.ZombieHorse()));
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_STEP_WOOD, "entity.zombie_horse.step", EN_US.steps(EN_US.ZombieHorse()));
		Register(ModSoundEvents.ENTITY_ZOMBIFIED_PIGLIN_STEP, "entity.zombified_piglin.step", EN_US.steps(EN_US.ZombifiedPigling()));
		//</editor-fold">
		//<editor-fold desc="Mod Items">
		//</editor-fold>
		//<editor-fold desc="Swim">
		Register(SoundEvents.ENTITY_GENERIC_SWIM, "swim.generic", EN_US.swims(EN_US.Something()));
		Register(SoundEvents.ENTITY_PLAYER_SWIM, "swim.player", EN_US.swims(EN_US.Someone()));
		//</editor-fold">
		//<editor-fold desc="Splash">
		Register(SoundEvents.ENTITY_GENERIC_SPLASH, "splash.generic", EN_US.splashes(EN_US.Something()));
		Register(SoundEvents.ENTITY_PLAYER_SPLASH, "splash.player", EN_US.splashes(EN_US.Someone()));
		Register("splash.high_speed.generic", EN_US.hard(EN_US.splashes(EN_US.Something())));
		Register(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, "splash.high_speed.player", EN_US.hard(EN_US.splashes(EN_US.Someone())));
		//</editor-fold">
		Register(SoundEvents.ENTITY_BOAT_PADDLE_WATER, "paddle.water.generic", EN_US.Rowing(EN_US.Boat()));
		Register(SoundEvents.ENTITY_BOAT_PADDLE_LAND, "paddle.land.generic", EN_US.Paddling(EN_US.Boat()));

		Register(SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, "item.bucket_empty_powder_snow", EN_US.Subtitle_Block_Place(EN_US.PowderSnow()));
	}

	public static String getSubtitle(WeightedSoundSet soundSet) {
		if (soundSet == null) return null;
		String subtitle = weightedSubtitles.getOrDefault(soundSet, null);
		if (subtitle != null) return subtitle;
		if (!subtitles.isEmpty()) {
			SoundManager manager = MinecraftClient.getInstance().getSoundManager();
			if (manager != null) {
				for (Map.Entry<Identifier, String> entry : subtitles.entrySet()) {
					weightedSubtitles.put(manager.get(entry.getKey()), entry.getValue());
				}
				subtitles.clear();
			}
		}
		return weightedSubtitles.getOrDefault(soundSet, null);
	}
}