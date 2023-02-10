package fun.mousewich.sound;


import fun.mousewich.ModBase;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

@Environment(value= EnvType.CLIENT)
public class SubtitleOverrides {
	private static final Map<Identifier, String> subtitles = new HashMap<>();
	private static final Map<WeightedSoundSet, String> weightedSubtitles = new HashMap<>();

	public static void Register(SoundEvent soundEvent, String name) { Register(soundEvent.getId(), name); }
	public static void Register(Identifier id, String name) {
		if (!subtitles.containsKey(id)) subtitles.put(id, IdentifiedSounds.IDENTIFY + name);
	}
	private static void Register(BlockSoundGroup soundGroup, String block) {
		Register(soundGroup.getBreakSound(), "block.break." + block);
		Register(soundGroup.getHitSound(), "block.hit." + block);
		Register(soundGroup.getPlaceSound(), "block.place." + block);
		Register(soundGroup.getStepSound(), "block.step." + block);
	}

	static {
		//<editor-fold desc="Blocks">
		Register(BlockSoundGroup.AMETHYST_BLOCK, "amethyst");
		Register(BlockSoundGroup.AMETHYST_CLUSTER, "amethyst_cluster");
		Register(BlockSoundGroup.ANCIENT_DEBRIS, "ancient_debris");
		Register(BlockSoundGroup.ANVIL, "anvil");
		Register(BlockSoundGroup.AZALEA, "azalea");
		Register(BlockSoundGroup.AZALEA_LEAVES, "azalea_leaves");
		Register(BlockSoundGroup.BAMBOO, "bamboo");
		Register(SoundEvents.BLOCK_BAMBOO_SAPLING_BREAK, "block.break.bamboo_sapling");
		Register(SoundEvents.BLOCK_BAMBOO_SAPLING_HIT, "block.hit.bamboo_sapling");
		Register(SoundEvents.BLOCK_BAMBOO_SAPLING_PLACE, "block.place.bamboo_sapling");
		Register(BlockSoundGroup.BASALT, "basalt");
		Register(BlockSoundGroup.BIG_DRIPLEAF, "big_dripleaf");
		Register(BlockSoundGroup.BONE, "bone");
		Register(BlockSoundGroup.CALCITE, "calcite");
		Register(BlockSoundGroup.CANDLE, "candle");
		Register(BlockSoundGroup.CAVE_VINES, "cave_vines");
		Register(BlockSoundGroup.CHAIN, "chain");
		Register(BlockSoundGroup.COPPER, "copper");
		Register(BlockSoundGroup.CORAL, "coral");
		Register(SoundEvents.BLOCK_CROP_BREAK, "block.break.crop");
		Register(SoundEvents.ITEM_CROP_PLANT, "block.place.crop");
		Register(BlockSoundGroup.DEEPSLATE, "deepslate");
		Register(BlockSoundGroup.DEEPSLATE_BRICKS, "deepslate_bricks");
		Register(BlockSoundGroup.DEEPSLATE_TILES, "deepslate_tiles");
		Register(BlockSoundGroup.DRIPSTONE_BLOCK, "dripstone");
		Register(BlockSoundGroup.FLOWERING_AZALEA, "flowering_azalea");
		Register(BlockSoundGroup.FUNGUS, "fungus");
		Register(BlockSoundGroup.GILDED_BLACKSTONE, "gilded_blackstone");
		Register(BlockSoundGroup.GLASS, "glass");
		Register(BlockSoundGroup.GRASS, "grass");
		Register(BlockSoundGroup.GRAVEL, "gravel");
		Register(BlockSoundGroup.HANGING_ROOTS, "hanging_roots");
		Register(BlockSoundGroup.HONEY, "honey");
		Register(BlockSoundGroup.LADDER, "ladder");
		Register(BlockSoundGroup.LANTERN, "lantern");
		Register(SoundEvents.BLOCK_LARGE_AMETHYST_BUD_BREAK, "block.break.large_amethyst_bud");
		Register(SoundEvents.BLOCK_LARGE_AMETHYST_BUD_PLACE, "block.place.large_amethyst_bud");
		Register(SoundEvents.BLOCK_LILY_PAD_PLACE, "block.place.lily_pad");
		Register(BlockSoundGroup.LODESTONE, "lodestone");
		Register(SoundEvents.BLOCK_MEDIUM_AMETHYST_BUD_BREAK, "block.break.medium_amethyst_bud");
		Register(SoundEvents.BLOCK_MEDIUM_AMETHYST_BUD_PLACE, "block.place.place.medium_amethyst_bud");
		Register(BlockSoundGroup.METAL, "metal");
		Register(BlockSoundGroup.MOSS_CARPET, "moss_carpet");
		Register(BlockSoundGroup.MOSS_BLOCK, "moss");
		Register(BlockSoundGroup.NETHER_BRICKS, "nether_bricks");
		Register(BlockSoundGroup.NETHER_GOLD_ORE, "nether_gold_ore");
		Register(BlockSoundGroup.NETHER_ORE, "nether_ore");
		Register(BlockSoundGroup.NETHER_SPROUTS, "nether_sprouts");
		Register(BlockSoundGroup.NETHER_STEM, "nether_stem");
		Register(SoundEvents.BLOCK_NETHER_WART_BREAK, "block.break.nether_wart");
		Register(SoundEvents.ITEM_NETHER_WART_PLANT, "block.place.nether_wart");
		Register(BlockSoundGroup.NETHERITE, "netherite");
		Register(BlockSoundGroup.NETHERRACK, "netherrack");
		Register(BlockSoundGroup.NYLIUM, "nylium");
		Register(BlockSoundGroup.POINTED_DRIPSTONE, "pointed_dripstone");
		Register(SoundEvents.BLOCK_POINTED_DRIPSTONE_LAND, "block.land.pointed_dripstone");
		Register(BlockSoundGroup.POLISHED_DEEPSLATE, "polished_deepslate");
		Register(BlockSoundGroup.POWDER_SNOW, "powder_snow");
		Register(BlockSoundGroup.ROOTED_DIRT, "rooted_dirt");
		Register(BlockSoundGroup.ROOTS, "roots");
		Register(BlockSoundGroup.SAND, "sand");
		Register(BlockSoundGroup.SCAFFOLDING, "scaffolding");
		Register(BlockSoundGroup.SCULK_SENSOR, "sculk_sensor");
		Register(BlockSoundGroup.SHROOMLIGHT, "shroomlight");
		Register(BlockSoundGroup.SLIME, "slime");
		Register(SoundEvents.BLOCK_SMALL_AMETHYST_BUD_BREAK, "block.break.small_amethyst_bud");
		Register(SoundEvents.BLOCK_SMALL_AMETHYST_BUD_PLACE, "block.place.small_amethyst_bud");
		Register(BlockSoundGroup.SMALL_DRIPLEAF, "small_dripleaf");
		Register(BlockSoundGroup.SNOW, "snow");
		Register(BlockSoundGroup.SOUL_SAND, "soul_sand");
		Register(BlockSoundGroup.SOUL_SOIL, "soul_soil");
		Register(BlockSoundGroup.SPORE_BLOSSOM, "spore_blossom");
		Register(BlockSoundGroup.STONE, "stone");
		Register(SoundEvents.BLOCK_SWEET_BERRY_BUSH_BREAK, "block.break.sweet_berry_bush");
		Register(SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE, "block.place.sweet_berry_bush");
		Register(BlockSoundGroup.TUFF, "tuff");
		Register(BlockSoundGroup.VINE, "vine");
		Register(BlockSoundGroup.WART_BLOCK, "wart_block");
		Register(BlockSoundGroup.WET_GRASS, "wet_grass");
		Register(BlockSoundGroup.WEEPING_VINES, "weeping_vines");
		Register(BlockSoundGroup.WOOD, "wood");
		Register(BlockSoundGroup.WOOL, "wool");
		//</editor-fold>
		//<editor-fold desc="Entities">
		Register(SoundEvents.ENTITY_ARMOR_STAND_BREAK, "entity.armor_stand.break");
		Register(SoundEvents.ENTITY_ARMOR_STAND_FALL, "entity.armor_stand.fall");
		Register(SoundEvents.ENTITY_ARMOR_STAND_HIT, "entity.armor_stand.hit");
		Register(SoundEvents.ENTITY_ARMOR_STAND_PLACE, "entity.armor_stand.place");
		Register(SoundEvents.ENTITY_CHICKEN_STEP, "entity.chicken.step");
		Register(SoundEvents.ENTITY_COW_STEP, "entity.cow.step");
		Register(SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, "entity.dragon.fireball_explode");
		Register(SoundEvents.ENTITY_ENDERMITE_STEP, "entity.endermite.step");
		Register(SoundEvents.ENTITY_GOAT_STEP, "entity.goat.step");
		Register(SoundEvents.ENTITY_HORSE_LAND, "entity.horse.land");
		Register(SoundEvents.ENTITY_HORSE_SADDLE, "entity.horse.saddle");
		Register(SoundEvents.ENTITY_HORSE_STEP, "entity.horse.step");
		Register(SoundEvents.ENTITY_HORSE_STEP_WOOD, "entity.horse.step");
		Register(SoundEvents.ENTITY_HUSK_STEP, "entity.husk.step");
		Register(SoundEvents.ENTITY_IRON_GOLEM_STEP, "entity.iron_golem.step");
		Register(SoundEvents.ENTITY_PARROT_STEP, "entity.parrot.step");
		Register(SoundEvents.ENTITY_PIG_SADDLE, "entity.pig.saddle");
		Register(SoundEvents.ENTITY_PIG_STEP, "entity.pig.step");
		Register(SoundEvents.ENTITY_POLAR_BEAR_STEP, "entity.polar_bear.step");
		Register(SoundEvents.ENTITY_SHEEP_STEP, "entity.sheep.step");
		Register(SoundEvents.ENTITY_SILVERFISH_STEP, "entity.silverfish.step");
		Register(SoundEvents.ENTITY_SKELETON_STEP, "entity.skeleton.step");
		Register(SoundEvents.ENTITY_SKELETON_HORSE_STEP_WATER, "entity.skeleton_horse.step_water");
		Register(SoundEvents.ENTITY_SPIDER_STEP, "entity.spider.step");
		Register(SoundEvents.ENTITY_STRAY_STEP, "entity.stray.step");
		Register(SoundEvents.ENTITY_STRIDER_SADDLE, "entity.strider.saddle");
		Register(SoundEvents.ENTITY_STRIDER_STEP, "entity.strider.step");
		Register(SoundEvents.ENTITY_STRIDER_STEP_LAVA, "entity.strider.step_lava");
		Register(SoundEvents.ENTITY_WITHER_SKELETON_STEP, "entity.wolf.step");
		Register(SoundEvents.ENTITY_WOLF_HOWL, "entity.wolf.howl");
		Register(SoundEvents.ENTITY_WOLF_STEP, "entity.wolf.step");
		Register(SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, "entity.zombie.attack_iron_door");
		Register(SoundEvents.ENTITY_ZOMBIE_STEP, "entity.zombie.step");
		Register(SoundEvents.ENTITY_ZOMBIE_VILLAGER_STEP, "entity.zombie_villager.step");
		//</editor-fold>
		//<editor-fold desc="Mod Blocks">
		Register(ModBlockSoundGroups.BAMBOO_WOOD, "bamboo_wood");
		Register(ModBlockSoundGroups.ECHO_BLOCK, "echo");
		Register(ModBlockSoundGroups.ECHO_CLUSTER, "echo_cluster");
		Register(ModBlockSoundGroups.FROGLIGHT, "froglight");
		Register(ModBlockSoundGroups.FROGSPAWN, "frogspawn");
		Register(ModBlockSoundGroups.HANGING_SIGN, "hanging_sign");
		Register(ModSoundEvents.BLOCK_LARGE_ECHO_BUD_BREAK, "block.break.large_echo_bud");
		Register(ModSoundEvents.BLOCK_LARGE_ECHO_BUD_PLACE, "block.place.large_echo_bud");
		Register(ModBlockSoundGroups.MANGROVE_ROOTS, "mangrove_roots");
		Register(ModSoundEvents.BLOCK_MEDIUM_ECHO_BUD_BREAK, "block.break.medium_echo_bud");
		Register(ModSoundEvents.BLOCK_MEDIUM_ECHO_BUD_PLACE, "block.place.medium_echo_bud");
		Register(ModBlockSoundGroups.MUD, "mud");
		Register(ModBlockSoundGroups.MUD_BRICKS, "mud_bricks");
		Register(ModBlockSoundGroups.MUDDY_MANGROVE_ROOTS, "muddy_mangrove_roots");
		Register(ModBlockSoundGroups.NETHER_WOOD, "nether_wood");
		Register(ModBlockSoundGroups.PACKED_MUD, "packed_mud");
		Register(ModBlockSoundGroups.SCULK, "sculk");
		Register(ModBlockSoundGroups.SCULK_CATALYST, "sculk_catalyst");
		Register(ModBlockSoundGroups.SCULK_SHRIEKER, "sculk_shrieker");
		Register(ModBlockSoundGroups.SCULK_VEIN, "sculk_vein");
		Register(ModSoundEvents.BLOCK_SMALL_ECHO_BUD_BREAK, "block.break.small_echo_bud");
		Register(ModSoundEvents.BLOCK_SMALL_ECHO_BUD_PLACE, "block.place.small_echo_bud");
		//</editor-fold">
		//<editor-fold desc="Mod Entities">
		Register(ModSoundEvents.ENTITY_CAVE_SPIDER_AMBIENT, "entity.cave_spider.ambient");
		Register(ModSoundEvents.ENTITY_CAVE_SPIDER_DEATH, "entity.cave_spider.death");
		Register(ModSoundEvents.ENTITY_CAVE_SPIDER_HURT, "entity.cave_spider.hurt");
		Register(ModSoundEvents.ENTITY_CAVE_SPIDER_STEP, "entity.cave_spider.step");
		Register(ModSoundEvents.ENTITY_CAMEL_LAND, "entity.camel.land");
		Register(ModSoundEvents.ENTITY_CAMEL_SADDLE, "entity.camel.saddle");
		Register(ModSoundEvents.ENTITY_DONKEY_GALLOP, "entity.donkey.gallop");
		Register(ModSoundEvents.ENTITY_DONKEY_JUMP, "entity.donkey.jump");
		Register(ModSoundEvents.ENTITY_DONKEY_LAND, "entity.donkey.land");
		Register(ModSoundEvents.ENTITY_DONKEY_SADDLE, "entity.donkey.saddle");
		Register(ModSoundEvents.ENTITY_DONKEY_STEP, "entity.donkey.step");
		Register(ModSoundEvents.ENTITY_DONKEY_STEP_WOOD, "entity.donkey.step");
		Register(ModSoundEvents.ENTITY_ELDER_GUARDIAN_ATTACK, "entity.edler_guardian.attack");
		Register(ModSoundEvents.ENTITY_FROG_STEP, "entity.frog.step");
		Register(ModSoundEvents.ENTITY_MULE_GALLOP, "entity.mule.gallop");
		Register(ModSoundEvents.ENTITY_MULE_JUMP, "entity.mule.jump");
		Register(ModSoundEvents.ENTITY_MULE_LAND, "entity.mule.land");
		Register(ModSoundEvents.ENTITY_MULE_SADDLE, "entity.mule.saddle");
		Register(ModSoundEvents.ENTITY_MULE_STEP, "entity.mule.step");
		Register(ModSoundEvents.ENTITY_MULE_STEP_WOOD, "entity.mule.step");
		Register(ModSoundEvents.ENTITY_MOOSHROOM_AMBIENT, "entity.mooshroom.ambient");
		Register(ModSoundEvents.ENTITY_MOOSHROOM_DEATH, "entity.mooshroom.death");
		Register(ModSoundEvents.ENTITY_MOOSHROOM_HURT, "entity.mooshroom.hurt");
		Register(ModSoundEvents.ENTITY_MOOSHROOM_STEP, "entity.mooshroom.step");
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_GALLOP, "entity.skeleton_horse.gallop");
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_JUMP, "entity.skeleton_horse.jump");
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_LAND, "entity.skeleton_horse.land");
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_SADDLE, "entity.skeleton_horse.saddle");
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_STEP, "entity.skeleton_horse.step");
		Register(ModSoundEvents.ENTITY_SKELETON_HORSE_STEP_WOOD, "entity.skeleton_horse.step");
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_GALLOP, "entity.zombie_horse.gallop");
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_JUMP, "entity.zombie_horse.jump");
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_LAND, "entity.zombie_horse.land");
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_SADDLE, "entity.zombie_horse.saddle");
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_STEP, "entity.zombie_horse.step");
		Register(ModSoundEvents.ENTITY_ZOMBIE_HORSE_STEP_WOOD, "entity.zombie_horse.step");
		Register(ModSoundEvents.ENTITY_ZOMBIFIED_PIGLIN_STEP, "entity.zombified_piglin.step");
		//</editor-fold">
		//<editor-fold desc="Swim">
		Register(SoundEvents.ENTITY_GENERIC_SWIM, "swim.generic");
		Register(SoundEvents.ENTITY_PLAYER_SWIM, "swim.player");
		//</editor-fold">
		//<editor-fold desc="Splash">
		Register(SoundEvents.ENTITY_GENERIC_SPLASH, "splash.generic");
		Register(SoundEvents.ENTITY_PLAYER_SPLASH, "splash.player");
		Register(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, "splash.high_speed.player");
		//</editor-fold">
		Register(SoundEvents.ENTITY_BOAT_PADDLE_WATER, "paddle.water.generic");
		Register(SoundEvents.ENTITY_BOAT_PADDLE_LAND, "paddle.land.generic");

		Register(SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, "item.bucket_empty_powder_snow");
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