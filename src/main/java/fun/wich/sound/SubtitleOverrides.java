package fun.wich.sound;

import fun.wich.ModBase;
import fun.wich.gen.data.language.Words;
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

import static fun.wich.ModBase.*;

public class SubtitleOverrides {
	private static final Map<Identifier, String> subtitles = new HashMap<>();
	private static final Map<WeightedSoundSet, String> weightedSubtitles = new HashMap<>();

	public static void Register(SoundEvent soundEvent, String name, String... translations) { Register(soundEvent, name, List.of(translations)); }
	public static void Register(SoundEvent soundEvent, String name, List<String> translations) {
		if (soundEvent == null) throw new RuntimeException("SouneEvent null in registration: " + name);
		Register(soundEvent.getId(), name, translations);
	}
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
		Register(BlockSoundGroup.AMETHYST_BLOCK, "amethyst", Words.Amethyst);
		Register(BlockSoundGroup.AMETHYST_CLUSTER, "amethyst_cluster", EN_US.Cluster(Words.Amethyst));
		Register(BlockSoundGroup.ANCIENT_DEBRIS, "ancient_debris", EN_US.Debris(Words.Ancient));
		Register(BlockSoundGroup.ANVIL, "anvil", Words.Anvil);
		Register(BlockSoundGroup.AZALEA, "azalea", Words.Azalea);
		Register(BlockSoundGroup.AZALEA_LEAVES, "azalea_leaves", EN_US.Leaves(Words.Azalea));
		Register(BlockSoundGroup.BAMBOO, "bamboo", Words.Bamboo);
		Register(SoundEvents.BLOCK_BAMBOO_SAPLING_BREAK, "block.break.bamboo_sapling", EN_US.Subtitle_Block_Break(EN_US.Sapling(Words.Bamboo)));
		Register(SoundEvents.BLOCK_BAMBOO_SAPLING_HIT, "block.hit.bamboo_sapling", EN_US.Subtitle_Block_Hit(EN_US.Sapling(Words.Bamboo)));
		Register(SoundEvents.BLOCK_BAMBOO_SAPLING_PLACE, "block.place.bamboo_sapling", EN_US.Subtitle_Block_Place(EN_US.Sapling(Words.Bamboo)));
		Register(BlockSoundGroup.BASALT, "basalt", Words.Basalt);
		Register(BlockSoundGroup.BIG_DRIPLEAF, "big_dripleaf", EN_US.Dripleaf(Words.Big));
		Register(BlockSoundGroup.BONE, "bone", Words.Bone);
		Register(BlockSoundGroup.CALCITE, "calcite", Words.Calcite);
		Register(BlockSoundGroup.CANDLE, "candle", Words.Candle);
		Register(BlockSoundGroup.CAVE_VINES, "cave_vines", EN_US.Vines(Words.Cave));
		Register(BlockSoundGroup.CHAIN, "chain", Words.Chain);
		Register(BlockSoundGroup.COPPER, "copper", Words.Copper);
		Register(BlockSoundGroup.CORAL, "coral", Words.Coral);
		Register(SoundEvents.BLOCK_CROP_BREAK, "block.break.crop", EN_US.Subtitle_Block_Break(Words.Crop));
		Register(SoundEvents.ITEM_CROP_PLANT, "block.place.crop", EN_US.Subtitle_Block_Place(Words.Crop));
		Register(BlockSoundGroup.DEEPSLATE, "deepslate", Words.Deepslate);
		Register(BlockSoundGroup.DEEPSLATE_BRICKS, "deepslate_bricks", EN_US.Bricks(Words.Deepslate));
		Register(BlockSoundGroup.DEEPSLATE_TILES, "deepslate_tiles", EN_US.Tiles(Words.Deepslate));
		Register(BlockSoundGroup.DRIPSTONE_BLOCK, "dripstone", Words.Dripstone);
		Register(BlockSoundGroup.FLOWERING_AZALEA, "flowering_azalea", EN_US.Azalea(Words.Flowering));
		Register(BlockSoundGroup.FUNGUS, "fungus", Words.Fungus);
		Register(BlockSoundGroup.GILDED_BLACKSTONE, "gilded_blackstone", EN_US.Blackstone(Words.Gilded));
		Register(BlockSoundGroup.GLASS, "glass", Words.Glass);
		Register(BlockSoundGroup.GRASS, "grass", Words.Grass);
		Register(BlockSoundGroup.GRAVEL, "gravel", Words.Gravel);
		Register(BlockSoundGroup.HANGING_ROOTS, "hanging_roots", EN_US.Roots(Words.Hanging));
		Register(BlockSoundGroup.HONEY, "honey", Words.Honey);
		Register(BlockSoundGroup.LADDER, "ladder", Words.Ladder);
		Register(BlockSoundGroup.LANTERN, "lantern", Words.Lantern);
		Register(SoundEvents.BLOCK_LARGE_AMETHYST_BUD_BREAK, "block.break.large_amethyst_bud", EN_US.Subtitle_Block_Break(EN_US.Bud(EN_US.Amethyst(Words.Large))));
		Register(SoundEvents.BLOCK_LARGE_AMETHYST_BUD_PLACE, "block.place.large_amethyst_bud", EN_US.Subtitle_Block_Place(EN_US.Bud(EN_US.Amethyst(Words.Large))));
		Register(SoundEvents.BLOCK_LILY_PAD_PLACE, "block.place.lily_pad", EN_US.Subtitle_Block_Place(EN_US.Pad(Words.Lily)));
		Register(BlockSoundGroup.LODESTONE, "lodestone", Words.Lodestone);
		Register(SoundEvents.BLOCK_MEDIUM_AMETHYST_BUD_BREAK, "block.break.medium_amethyst_bud", EN_US.Subtitle_Block_Break(EN_US.Bud(EN_US.Amethyst(Words.Medium))));
		Register(SoundEvents.BLOCK_MEDIUM_AMETHYST_BUD_PLACE, "block.place.place.medium_amethyst_bud", EN_US.Subtitle_Block_Place(EN_US.Bud(EN_US.Amethyst(Words.Medium))));
		Register(BlockSoundGroup.METAL, "metal", Words.Metal);
		Register(BlockSoundGroup.MOSS_CARPET, "moss_carpet", EN_US.Carpet(Words.Moss));
		Register(BlockSoundGroup.MOSS_BLOCK, "moss", Words.Moss);
		Register(BlockSoundGroup.NETHER_BRICKS, "nether_bricks", EN_US.Bricks(Words.Nether));
		Register(BlockSoundGroup.NETHER_GOLD_ORE, "nether_gold_ore", EN_US.Ore(EN_US.Gold(Words.Nether)));
		Register(BlockSoundGroup.NETHER_ORE, "nether_ore", EN_US.Ore(Words.Nether));
		Register(BlockSoundGroup.NETHER_SPROUTS, "nether_sprouts", EN_US.Sprouts(Words.Nether));
		Register(BlockSoundGroup.NETHER_STEM, "nether_stem", EN_US.Stem(Words.Nether));
		Register(SoundEvents.BLOCK_NETHER_WART_BREAK, "block.break.nether_wart", EN_US.Subtitle_Block_Break(EN_US.Wart(Words.Nether)));
		Register(SoundEvents.ITEM_NETHER_WART_PLANT, "block.place.nether_wart", EN_US.Subtitle_Block_Place(EN_US.Wart(Words.Nether)));
		Register(BlockSoundGroup.NETHERITE, "netherite", Words.Netherite);
		Register(BlockSoundGroup.NETHERRACK, "netherrack", Words.Netherrack);
		Register(BlockSoundGroup.NYLIUM, "nylium", Words.Nylium);
		Register(BlockSoundGroup.POINTED_DRIPSTONE, "pointed_dripstone", EN_US.Dripstone(Words.Pointed));
		Register(SoundEvents.BLOCK_POINTED_DRIPSTONE_LAND, "block.land.pointed_dripstone", EN_US.fell(EN_US.Dripstone(Words.Pointed)));
		Register(BlockSoundGroup.POLISHED_DEEPSLATE, "polished_deepslate", EN_US.Deepslate(Words.Polished));
		Register(BlockSoundGroup.POWDER_SNOW, "powder_snow", EN_US.Snow(Words.Powder));
		Register(BlockSoundGroup.ROOTED_DIRT, "rooted_dirt", EN_US.Dirt(Words.Rooted));
		Register(BlockSoundGroup.ROOTS, "roots", Words.Roots);
		Register(BlockSoundGroup.SAND, "sand", Words.Sand);
		Register(BlockSoundGroup.SCAFFOLDING, "scaffolding", Words.Scaffolding);
		Register(BlockSoundGroup.SCULK_SENSOR, "sculk_sensor", EN_US.Sensor(Words.Sculk));
		Register(BlockSoundGroup.SHROOMLIGHT, "shroomlight", Words.Shroomlight);
		Register(BlockSoundGroup.SLIME, "slime", Words.Slime);
		Register(SoundEvents.BLOCK_SMALL_AMETHYST_BUD_BREAK, "block.break.small_amethyst_bud", EN_US.Subtitle_Block_Break(EN_US.Bud(EN_US.Amethyst(Words.Small))));
		Register(SoundEvents.BLOCK_SMALL_AMETHYST_BUD_PLACE, "block.place.small_amethyst_bud", EN_US.Subtitle_Block_Place(EN_US.Bud(EN_US.Amethyst(Words.Small))));
		Register(BlockSoundGroup.SMALL_DRIPLEAF, "small_dripleaf", EN_US.Dripleaf(Words.Small));
		Register(BlockSoundGroup.SNOW, "snow", Words.Snow);
		Register(BlockSoundGroup.SOUL_SAND, "soul_sand", EN_US.Sand(Words.Soul));
		Register(BlockSoundGroup.SOUL_SOIL, "soul_soil", EN_US.Soil(Words.Soul));
		Register(BlockSoundGroup.SPORE_BLOSSOM, "spore_blossom", EN_US.Blossom(Words.Spore));
		Register(BlockSoundGroup.STONE, "stone", Words.Stone);
		Register(SoundEvents.BLOCK_SWEET_BERRY_BUSH_BREAK, "block.break.sweet_berry_bush", EN_US.Subtitle_Block_Break(EN_US.Bush(EN_US.Berry(Words.Sweet))));
		Register(SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE, "block.place.sweet_berry_bush", EN_US.Subtitle_Block_Place(EN_US.Bush(EN_US.Berry(Words.Sweet))));
		Register(BlockSoundGroup.TUFF, "tuff", Words.Tuff);
		Register(BlockSoundGroup.VINE, "vine", Words.Vine);
		Register(BlockSoundGroup.WART_BLOCK, "wart_block", EN_US.Block(Words.Wart));
		Register(BlockSoundGroup.WEEPING_VINES, "weeping_vines", EN_US.Vines(Words.Weeping));
		Register(BlockSoundGroup.WET_GRASS, "wet_grass", EN_US.Grass(Words.Wet));
		Register(BlockSoundGroup.WOOD, "wood", Words.Wood);
		Register(BlockSoundGroup.WOOL, "wool", Words.Wool);
		//</editor-fold>
		Register(ModSoundEvents.ENTITY_GENERIC_ELYTRA_CRASH, "crash.generic", EN_US.crashes(Words.Something));
		Register("crash.player", EN_US.crashes(Words.Someone));
		Register(SoundEvents.ENTITY_GENERIC_BIG_FALL,"big_fall.generic", EN_US.fell(Words.Something));
		Register(SoundEvents.ENTITY_PLAYER_BIG_FALL,"big_fall.player", EN_US.fell(Words.Someone));
		Register("footsteps.generic", EN_US.steps(Words.Something));
		Register("footsteps.player", EN_US.steps(Words.Someone));
		Register(SoundEvents.ENTITY_GENERIC_SMALL_FALL,"small_fall.generic", EN_US.trips(Words.Something));
		Register(SoundEvents.ENTITY_PLAYER_SMALL_FALL,"small_fall.player", EN_US.trips(Words.Someone));
		//<editor-fold desc="Entity Sound Events">
		Register(SoundEvents.ENTITY_ARMOR_STAND_BREAK, "entity.armor_stand.break", EN_US.Subtitle_Block_Break(EN_US.Stand(Words.Armor)));
		Register(SoundEvents.ENTITY_ARMOR_STAND_FALL, "entity.armor_stand.fall", EN_US.fell(EN_US.Stand(Words.Armor)));
		Register(SoundEvents.ENTITY_ARMOR_STAND_HIT, "entity.armor_stand.hit", EN_US.Subtitle_Block_Hit(EN_US.Stand(Words.Armor)));
		Register(SoundEvents.ENTITY_ARMOR_STAND_PLACE, "entity.armor_stand.place", EN_US.Subtitle_Block_Place(EN_US.Stand(Words.Armor)));
		Register(SoundEvents.ENTITY_CHICKEN_STEP, "entity.chicken.step", EN_US.steps(Words.Chicken));
		Register(SoundEvents.ENTITY_COW_STEP, "entity.cow.step", EN_US.steps(Words.Cow));
		Register(SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, "entity.dragon.fireball_explode", EN_US.explodes(EN_US.Fireball(Words.Dragon)));
		Register(SoundEvents.ENTITY_ENDERMITE_STEP, "entity.endermite.step", EN_US.steps(Words.Endermite));
		Register(SoundEvents.ENTITY_GOAT_STEP, "entity.goat.step", EN_US.steps(Words.Goat));
		Register(SoundEvents.ENTITY_HORSE_LAND, "entity.horse.land", EN_US.fell(Words.Horse));
		Register(SoundEvents.ENTITY_HORSE_SADDLE, "entity.horse.saddle", EN_US.saddled(Words.Horse));
		Register(SoundEvents.ENTITY_HORSE_STEP, "entity.horse.step", EN_US.steps(Words.Horse));
		Register(SoundEvents.ENTITY_HORSE_STEP_WOOD, "entity.horse.step", EN_US.steps(Words.Horse));
		Register(SoundEvents.ENTITY_HUSK_STEP, "entity.husk.step", EN_US.steps(Words.Husk));
		Register(SoundEvents.ENTITY_IRON_GOLEM_STEP, "entity.iron_golem.step", EN_US.steps(EN_US.Golem(Words.Iron)));
		Register(SoundEvents.ENTITY_PARROT_STEP, "entity.parrot.step", EN_US.steps(Words.Parrot));
		Register(SoundEvents.ENTITY_PIG_SADDLE, "entity.pig.saddle", EN_US.saddled(Words.Pig));
		Register(SoundEvents.ENTITY_PIG_STEP, "entity.pig.step", EN_US.steps(Words.Pig));
		Register(SoundEvents.ENTITY_POLAR_BEAR_STEP, "entity.polar_bear.step", EN_US.steps(EN_US.Bear(Words.Polar)));
		Register(SoundEvents.ENTITY_SHEEP_STEP, "entity.sheep.step", EN_US.steps(Words.Sheep));
		Register(SoundEvents.ENTITY_SILVERFISH_STEP, "entity.silverfish.step", EN_US.steps(Words.Silverfish));
		Register(SoundEvents.ENTITY_SKELETON_STEP, "entity.skeleton.step", EN_US.steps(Words.Skeleton));
		Register(SoundEvents.ENTITY_SKELETON_HORSE_STEP_WATER, "entity.skeleton_horse.step_water", EN_US.Water(EN_US.in(EN_US.steps(Words.Skeleton))));
		Register(SoundEvents.ENTITY_SPIDER_STEP, "entity.spider.step", EN_US.steps(Words.Spider));
		Register(SoundEvents.ENTITY_STRAY_STEP, "entity.stray.step", EN_US.steps(Words.Stray));
		Register(SoundEvents.ENTITY_STRIDER_SADDLE, "entity.strider.saddle", EN_US.saddled(Words.Strider));
		Register(SoundEvents.ENTITY_STRIDER_STEP, "entity.strider.step", EN_US.steps(Words.Strider));
		Register(SoundEvents.ENTITY_STRIDER_STEP_LAVA, "entity.strider.step_lava", EN_US.Lava(EN_US.on(EN_US.steps(Words.Strider))));
		Register(SoundEvents.ENTITY_WITHER_SKELETON_STEP, "entity.wither_skeleton.step", EN_US.steps(EN_US.Skeleton(Words.Wither)));
		Register(SoundEvents.ENTITY_WOLF_HOWL, "entity.wolf.howl", EN_US.howls(Words.Wolf));
		Register(SoundEvents.ENTITY_WOLF_STEP, "entity.wolf.step", EN_US.steps(Words.Wolf));
		Register(SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, "entity.zombie.attack_iron_door", EN_US.clangs(Words.Door));
		Register(SoundEvents.ENTITY_ZOMBIE_STEP, "entity.zombie.step", EN_US.steps(Words.Zombie));
		Register(SoundEvents.ENTITY_ZOMBIE_VILLAGER_STEP, "entity.zombie_villager.step", EN_US.steps(EN_US.Villager(Words.Zombie)));
		//</editor-fold>
		//<editor-fold desc="Mod Blocks">
		Register(ModBlockSoundGroups.BAMBOO_WOOD, "bamboo_wood", EN_US.Wood(Words.Bamboo));
		Register(ModBlockSoundGroups.CHERRY_LEAVES, "cherry_leaves", EN_US.Leaves(Words.Cherry));
		Register(ModBlockSoundGroups.CHERRY_SAPLING, "cherry_sapling", EN_US.Sapling(Words.Cherry));
		Register(ModBlockSoundGroups.CHERRY_WOOD, "cherry_wood", EN_US.Wood(Words.Cherry));
		Register(ModBlockSoundGroups.CHERRY_WOOD_HANGING_SIGN, "cherry_wood_hanging_sign", EN_US.Sign(EN_US.Hanging(EN_US.Wood(Words.Cherry))));
		Register(ModBlockSoundGroups.COPPER_GRATE, "copper_grate", EN_US.Grate(Words.Copper));
		Register(ModSoundEvents.BLOCK_COPPER_TRAPDOOR_CLOSE, "block.close.copper_trapdoor", EN_US.closes(EN_US.Trapdoor(Words.Copper)));
		Register(ModSoundEvents.BLOCK_COPPER_TRAPDOOR_OPEN, "block.open.copper_trapdoor", EN_US.opens(EN_US.Trapdoor(Words.Copper)));
		Register(ModBlockSoundGroups.ECHO_BLOCK, "echo", Words.Echo);
		Register(ModBlockSoundGroups.ECHO_CLUSTER, "echo_cluster", EN_US.Cluster(Words.Echo));
		Register(ModBlockSoundGroups.FROGLIGHT, "froglight", Words.Froglight);
		Register(ModBlockSoundGroups.FROGSPAWN, "frogspawn", Words.Frogspawn);
		Register(ModBlockSoundGroups.GRAPE_VINES, "grape_vines", EN_US.Vines(Words.Grape));
		Register(ModSoundEvents.BLOCK_GLASS_TRAPDOOR_CLOSE, "block.close.glass_trapdoor", EN_US.closes(EN_US.Trapdoor(Words.Glass)));
		Register(ModSoundEvents.BLOCK_GLASS_TRAPDOOR_OPEN, "block.open.glass_trapdoor", EN_US.opens(EN_US.Trapdoor(Words.Glass)));
		Register(ModBlockSoundGroups.HANGING_SIGN, "hanging_sign", EN_US.Sign(Words.Hanging));
		Register(ModSoundEvents.BLOCK_LARGE_ECHO_BUD_BREAK, "block.break.large_echo_bud", EN_US.Subtitle_Block_Break(EN_US.Bud(EN_US.Echo(Words.Large))));
		Register(ModSoundEvents.BLOCK_LARGE_ECHO_BUD_PLACE, "block.place.large_echo_bud", EN_US.Subtitle_Block_Place(EN_US.Bud(EN_US.Echo(Words.Large))));
		Register(ModBlockSoundGroups.MANGROVE_ROOTS, "mangrove_roots", EN_US.Roots(Words.Mangrove));
		Register(ModSoundEvents.BLOCK_MEDIUM_ECHO_BUD_BREAK, "block.break.medium_echo_bud", EN_US.Subtitle_Block_Break(EN_US.Bud(EN_US.Echo(Words.Medium))));
		Register(ModSoundEvents.BLOCK_MEDIUM_ECHO_BUD_PLACE, "block.place.medium_echo_bud", EN_US.Subtitle_Block_Place(EN_US.Bud(EN_US.Echo(Words.Medium))));
		Register(ModBlockSoundGroups.MUD, "mud", Words.Mud);
		Register(ModBlockSoundGroups.MUD_BRICKS, "mud_bricks", EN_US.Bricks(Words.Mud));
		Register(ModBlockSoundGroups.MUDDY_MANGROVE_ROOTS, "muddy_mangrove_roots", EN_US.Roots(EN_US.Mangrove(Words.Muddy)));
		Register(ModBlockSoundGroups.NETHER_IRON_ORE, "nether_iron_ore", EN_US.Ore(EN_US.Iron(Words.Nether)));
		Register(ModBlockSoundGroups.NETHER_COPPER_ORE, "nether_copper_ore", EN_US.Ore(EN_US.Copper(Words.Nether)));
		Register(ModBlockSoundGroups.NETHER_WOOD, "nether_wood", EN_US.Wood(Words.Nether));
		Register(ModBlockSoundGroups.PACKED_MUD, "packed_mud", EN_US.Mud(Words.Packed));
		Register(ModBlockSoundGroups.PINK_PETALS, "pink_petals", EN_US.Petals(Words.Pink));
		Register(ModBlockSoundGroups.SCULK, "sculk", Words.Sculk);
		Register(ModBlockSoundGroups.SCULK_CATALYST, "sculk_catalyst", EN_US.Catalyst(Words.Sculk));
		Register(ModBlockSoundGroups.SCULK_SHRIEKER, "sculk_shrieker", EN_US.Shrieker(Words.Sculk));
		Register(ModBlockSoundGroups.SCULK_VEIN, "sculk_vein", EN_US.Vein(Words.Sculk));
		Register(ModBlockSoundGroups.SUSPICIOUS_GRAVEL, "suspicious_gravel", Words.Gravel);
		Register(ModBlockSoundGroups.SUSPICIOUS_SAND, "suspicious_sand", Words.Sand);
		Register(ModSoundEvents.BLOCK_SMALL_ECHO_BUD_BREAK, "block.break.small_echo_bud", EN_US.Subtitle_Block_Break(EN_US.Bud(EN_US.Echo(Words.Small))));
		Register(ModSoundEvents.BLOCK_SMALL_ECHO_BUD_PLACE, "block.place.small_echo_bud", EN_US.Subtitle_Block_Place(EN_US.Bud(EN_US.Echo(Words.Small))));
		//</editor-fold">
		//<editor-fold desc="Mod Entity Sound Events">
		Register(ModSoundEvents.ENTITY_BONE_SPIDER_AMBIENT, "entity.bone_spider.ambient", EN_US.hisses(EN_US.Spider(Words.Bone)));
		Register(ModSoundEvents.ENTITY_BONE_SPIDER_DEATH, "entity.bone_spider.death", EN_US.dies(EN_US.Spider(Words.Bone)));
		Register(ModSoundEvents.ENTITY_BONE_SPIDER_HURT, "entity.bone_spider.hurt", EN_US.hurts(EN_US.Spider(Words.Bone)));
		Register(ModSoundEvents.ENTITY_BONE_SPIDER_STEP, "entity.bone_spider.step", EN_US.steps(EN_US.Spider(Words.Bone)));
		Register(ModSoundEvents.ENTITY_CAVE_SPIDER_AMBIENT, "entity.cave_spider.ambient", EN_US.hisses(EN_US.Spider(Words.Cave)));
		Register(ModSoundEvents.ENTITY_CAVE_SPIDER_DEATH, "entity.cave_spider.death", EN_US.dies(EN_US.Spider(Words.Cave)));
		Register(ModSoundEvents.ENTITY_CAVE_SPIDER_HURT, "entity.cave_spider.hurt", EN_US.hurts(EN_US.Spider(Words.Cave)));
		Register(ModSoundEvents.ENTITY_CAVE_SPIDER_STEP, "entity.cave_spider.step", EN_US.steps(EN_US.Spider(Words.Cave)));
		Register(ModSoundEvents.ENTITY_CAMEL_LAND, "entity.camel.land", EN_US.fell(Words.Camel));
		Register(ModSoundEvents.ENTITY_CAMEL_SADDLE, "entity.camel.saddle", EN_US.saddled(Words.Camel));
		Register(ModSoundEvents.ENTITY_DONKEY_GALLOP, "entity.donkey.gallop", EN_US.gallops(Words.Donkey));
		Register(ModSoundEvents.ENTITY_DONKEY_JUMP, "entity.donkey.jump", EN_US.jumps(Words.Donkey));
		Register(ModSoundEvents.ENTITY_DONKEY_LAND, "entity.donkey.land", EN_US.fell(Words.Donkey));
		Register(ModSoundEvents.ENTITY_DONKEY_SADDLE, "entity.donkey.saddle", EN_US.saddled(Words.Donkey));
		Register(ModSoundEvents.ENTITY_DONKEY_STEP, "entity.donkey.step", EN_US.steps(Words.Donkey));
		Register(ModSoundEvents.ENTITY_DONKEY_STEP_WOOD, "entity.donkey.step", EN_US.steps(Words.Donkey));
		Register(ModSoundEvents.ENTITY_ELDER_GUARDIAN_ATTACK, "entity.edler_guardian.attack", EN_US.shoots(EN_US.Guardian(Words.Elder)));
		Register(ModSoundEvents.ENTITY_FROG_STEP, "entity.frog.step", EN_US.steps(Words.Frog));
		Register(ModSoundEvents.ENTITY_HEDGEHOG_STEP, "entity.hedgehog.step", EN_US.steps(Words.Hedgehog));
		Register(ModSoundEvents.ENTITY_JUMPING_SPIDER_AMBIENT, "entity.jumping_spider.ambient", EN_US.hisses(EN_US.Spider(Words.Jumping)));
		Register(ModSoundEvents.ENTITY_JUMPING_SPIDER_DEATH, "entity.jumping_spider.death", EN_US.dies(EN_US.Spider(Words.Jumping)));
		Register(ModSoundEvents.ENTITY_JUMPING_SPIDER_HURT, "entity.jumping_spider.hurt", EN_US.hurts(EN_US.Spider(Words.Jumping)));
		Register(ModSoundEvents.ENTITY_JUMPING_SPIDER_STEP, "entity.jumping_spider.step", EN_US.steps(EN_US.Spider(Words.Jumping)));
		Register(ModSoundEvents.ENTITY_MULE_JUMP, "entity.jumping_spider.jump", EN_US.jumps(EN_US.Spider(Words.Jumping)));
		Register(ModSoundEvents.ENTITY_MULE_GALLOP, "entity.mule.gallop", EN_US.gallops(Words.Mule));
		Register(ModSoundEvents.ENTITY_MULE_JUMP, "entity.mule.jump", EN_US.jumps(Words.Mule));
		Register(ModSoundEvents.ENTITY_MULE_LAND, "entity.mule.land", EN_US.fell(Words.Mule));
		Register(ModSoundEvents.ENTITY_MULE_SADDLE, "entity.mule.saddle", EN_US.saddled(Words.Mule));
		Register(ModSoundEvents.ENTITY_MULE_STEP, "entity.mule.step", EN_US.steps(Words.Mule));
		Register(ModSoundEvents.ENTITY_MULE_STEP_WOOD, "entity.mule.step", EN_US.steps(Words.Mule));
		Register(ModSoundEvents.ENTITY_MOOSHROOM_AMBIENT, "entity.mooshroom.ambient", EN_US.moos(Words.Mooshroom));
		Register(ModSoundEvents.ENTITY_MOOSHROOM_DEATH, "entity.mooshroom.death", EN_US.dies(Words.Mooshroom));
		Register(ModSoundEvents.ENTITY_MOOSHROOM_HURT, "entity.mooshroom.hurt", EN_US.hurts(Words.Mooshroom));
		Register(ModSoundEvents.ENTITY_MOOSHROOM_STEP, "entity.mooshroom.step", EN_US.steps(Words.Mooshroom));
		Register(ModSoundEvents.ENTITY_MOSSY_SKELETON_STEP, "entity.mossy_skeleton.step", EN_US.steps(EN_US.Skeleton(Words.Mossy)));
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_GALLOP, "entity.skeleton_horse.gallop", EN_US.gallops(EN_US.Horse(Words.Skeleton)));
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_JUMP, "entity.skeleton_horse.jump", EN_US.jumps(EN_US.Horse(Words.Skeleton)));
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_LAND, "entity.skeleton_horse.land", EN_US.fell(EN_US.Horse(Words.Skeleton)));
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_SADDLE, "entity.skeleton_horse.saddle", EN_US.saddled(EN_US.Horse(Words.Skeleton)));
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_STEP, "entity.skeleton_horse.step", EN_US.steps(EN_US.Horse(Words.Skeleton)));
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_STEP_WOOD, "entity.skeleton_horse.step", EN_US.steps(EN_US.Horse(Words.Skeleton)));
		Register(ModSoundEvents.ENTITY_SLIME_COW_DEATH, "entity.slime_cow.death", EN_US.dies(EN_US.Cow(Words.Slime)));
		Register(ModSoundEvents.ENTITY_SLIME_COW_HURT, "entity.slime_cow.hurt", EN_US.hurts(EN_US.Cow(Words.Slime)));
		Register(ModSoundEvents.ENTITY_SLIME_COW_STEP, "entity.slime_cow.step", EN_US.steps(EN_US.Cow(Words.Slime)));
		Register(ModSoundEvents.ENTITY_SUNKEN_SKELETON_STEP, "entity.sunken_skeleton.step", EN_US.steps(EN_US.Skeleton(Words.Sunken)));
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_GALLOP, "entity.zombie_horse.gallop", EN_US.gallops(EN_US.Horse(Words.Zombie)));
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_JUMP, "entity.zombie_horse.jump", EN_US.jumps(EN_US.Horse(Words.Zombie)));
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_LAND, "entity.zombie_horse.land", EN_US.fell(EN_US.Horse(Words.Zombie)));
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_SADDLE, "entity.zombie_horse.saddle", EN_US.saddled(EN_US.Horse(Words.Zombie)));
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_STEP, "entity.zombie_horse.step", EN_US.steps(EN_US.Horse(Words.Zombie)));
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_STEP_WOOD, "entity.zombie_horse.step", EN_US.steps(EN_US.Horse(Words.Zombie)));
		Register(ModSoundEvents.ENTITY_ZOMBIFIED_PIGLIN_STEP, "entity.zombified_piglin.step", EN_US.steps(EN_US.Piglin(Words.Zombified)));
		//</editor-fold">
		//<editor-fold desc="Mod Items">
		//</editor-fold>
		//<editor-fold desc="Swim">
		Register(SoundEvents.ENTITY_GENERIC_SWIM, "swim.generic", EN_US.swims(Words.Something));
		Register(SoundEvents.ENTITY_PLAYER_SWIM, "swim.player", EN_US.swims(Words.Someone));
		//</editor-fold">
		//<editor-fold desc="Splash">
		Register(SoundEvents.ENTITY_GENERIC_SPLASH, "splash.generic", EN_US.splashes(Words.Something));
		Register(SoundEvents.ENTITY_PLAYER_SPLASH, "splash.player", EN_US.splashes(Words.Someone));
		Register("splash.high_speed.generic", EN_US.hard(EN_US.splashes(Words.Something)));
		Register(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, "splash.high_speed.player", EN_US.hard(EN_US.splashes(Words.Someone)));
		//</editor-fold">
		Register(SoundEvents.ENTITY_BOAT_PADDLE_WATER, "paddle.water.generic", EN_US.Rowing(Words.Boat));
		Register(SoundEvents.ENTITY_BOAT_PADDLE_LAND, "paddle.land.generic", EN_US.Paddling(Words.Boat));

		Register(SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, "item.bucket_empty_powder_snow", EN_US.Subtitle_Block_Place(EN_US.Snow(Words.Powder)));
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