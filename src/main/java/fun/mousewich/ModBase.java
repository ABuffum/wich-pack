package fun.mousewich;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import fun.mousewich.advancement.ModCriteria;
import fun.mousewich.block.*;
import fun.mousewich.block.basic.*;
import fun.mousewich.block.cryingobsidian.BleedingObsidianBlock;
import fun.mousewich.block.cryingobsidian.CryingObsidianSlabBlock;
import fun.mousewich.block.cryingobsidian.CryingObsidianStairsBlock;
import fun.mousewich.block.cryingobsidian.CryingObsidianWallBlock;
import fun.mousewich.block.mangrove.*;
import fun.mousewich.block.oxidizable.OxidizableWeightedPressurePlateBlock;
import fun.mousewich.block.piglin.PiglinHeadBlock;
import fun.mousewich.block.piglin.PiglinHeadEntity;
import fun.mousewich.block.piglin.WallPiglinHeadBlock;
import fun.mousewich.block.sculk.*;
import fun.mousewich.block.torch.*;
import fun.mousewich.command.*;
import fun.mousewich.dispenser.HorseArmorDispenserBehavior;
import fun.mousewich.dispenser.WearableDispenserBehavior;
import fun.mousewich.effect.*;
import fun.mousewich.enchantment.*;
import fun.mousewich.entity.*;
import fun.mousewich.container.*;
import fun.mousewich.entity.ai.ModMemoryModules;
import fun.mousewich.entity.ai.sensor.FrogAttackablesSensor;
import fun.mousewich.entity.ai.sensor.IsInWaterSensor;
import fun.mousewich.entity.ai.sensor.ModSensorType;
import fun.mousewich.entity.ai.sensor.WardenAttackablesSensor;
import fun.mousewich.entity.allay.AllayEntity;
import fun.mousewich.entity.camel.CamelBrain;
import fun.mousewich.entity.camel.CamelEntity;
import fun.mousewich.entity.frog.FrogBrain;
import fun.mousewich.entity.frog.FrogEntity;
import fun.mousewich.entity.frog.TadpoleEntity;
import fun.mousewich.entity.hedgehog.HedgehogEntity;
import fun.mousewich.entity.projectile.AmethystArrowEntity;
import fun.mousewich.entity.projectile.ChorusArrowEntity;
import fun.mousewich.entity.sniffer.SnifferEntity;
import fun.mousewich.entity.warden.WardenEntity;
import fun.mousewich.event.*;
import fun.mousewich.gen.data.language.ModLanguageCache;
import fun.mousewich.gen.data.loot.DropTable;
import fun.mousewich.gen.data.tag.ModBiomeTags;
import fun.mousewich.gen.data.tag.ModBlockTags;
import fun.mousewich.gen.feature.*;
import fun.mousewich.gen.feature.config.CherryTreeFeatureConfig;
import fun.mousewich.gen.feature.config.MangroveTreeFeatureConfig;
import fun.mousewich.gen.loot.SetInstrumentLootFunction;
import fun.mousewich.gen.stateprovider.ModSimpleBlockStateProvider;
import fun.mousewich.gen.structure.*;
import fun.mousewich.gen.world.ModBiomeCreator;
import fun.mousewich.item.*;
import fun.mousewich.item.basic.*;
import fun.mousewich.item.tool.*;
import fun.mousewich.item.tool.echo.*;
import fun.mousewich.item.goat.*;
import fun.mousewich.material.*;
import fun.mousewich.item.EntityPouchItem;
import fun.mousewich.item.PouchItem;
import fun.mousewich.mixins.world.BuiltinBiomesInvoker;
import fun.mousewich.origins.powers.*;
import fun.mousewich.particle.*;
import fun.mousewich.recipe.*;
import fun.mousewich.sound.*;
import fun.mousewich.trim.ArmorTrimPattern;
import fun.mousewich.trim.TrimmingRecipe;
import fun.mousewich.trim.SmithingTemplateItem;
import fun.mousewich.trim.TrimmingTableBlock;
import fun.mousewich.util.ColorUtil;
import fun.mousewich.util.OxidationScale;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.particle.*;
import net.minecraft.recipe.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.processor.*;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.SignType;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static fun.mousewich.ModRegistry.*;
import static fun.mousewich.ModFactory.ItemSettings;
import static fun.mousewich.ModFactory.NetheriteItemSettings;

public class ModBase implements ModInitializer {
	public static final ModLanguageCache.en_us EN_US = new ModLanguageCache.en_us();
	public static final ModLanguageCache[] LANGUAGE_CACHES = new ModLanguageCache[] {
		EN_US
	};

	public static final Logger LOGGER = LoggerFactory.getLogger("Wich Pack");
	public static final String NAMESPACE = "wich";
	public static Identifier ID(String path) { return path.contains(":") ? new Identifier(path) : new Identifier(NAMESPACE, path); }

	private static ItemStack GetItemGroupIcon() { return new ItemStack(ECHO_SHARD); }
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(ID("wichpack"), ModBase::GetItemGroupIcon);

	//<editor-fold desc="Acacia">
	public static final Item ACACIA_CHEST_BOAT = ModFactory.MakeChestBoat(BoatEntity.Type.ACACIA, ModFactory.BoatSettings(ItemGroup.TRANSPORTATION));
	public static final WallBlockContainer ACACIA_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.ACACIA, MapColor.STONE_GRAY, ModFactory.SignItemSettings(ItemGroup.DECORATIONS));
	public static final BlockContainer ACACIA_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.ORANGE).flammable(30, 20).fuel(300);
	public static final BlockContainer ACACIA_BARREL = ModFactory.MakeBarrel(MapColor.ORANGE).fuel(300);
	public static final BlockContainer ACACIA_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer ACACIA_LECTERN = ModFactory.MakeLectern(MapColor.ORANGE).flammable(30, 20).fuel(300);
	public static final BlockContainer ACACIA_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.ORANGE);
	//</editor-fold>
	//<editor-fold desc="Allay">
	public static final EntityType<AllayEntity> ALLAY_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AllayEntity::new).dimensions(EntityDimensions.fixed(0.35f, 0.6f)).trackRangeChunks(8).trackedUpdateRate(2).build();
	public static final Item ALLAY_SPAWN_EGG = new SpawnEggItem(ALLAY_ENTITY, 56063, 44543, ItemSettings(ItemGroup.MISC));
	//</editor-fold>
	//<editor-fold desc="Amethyst">
	public static final BlockContainer AMETHYST_STAIRS = ModFactory.BuildStairs(new AmethystStairsBlock(Blocks.AMETHYST_BLOCK));
	public static final BlockContainer AMETHYST_SLAB = ModFactory.BuildSlab(new AmethystSlabBlock(Blocks.AMETHYST_BLOCK));
	public static final BlockContainer AMETHYST_WALL = ModFactory.BuildWall(new AmethystWallBlock(Blocks.AMETHYST_BLOCK));
	public static final BlockContainer AMETHYST_CRYSTAL_BLOCK = ModFactory.BuildBlock(new AmethystBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f, 1.5f).requiresTool().luminance(ModFactory.LUMINANCE_5)));
	public static final BlockContainer AMETHYST_CRYSTAL_STAIRS = ModFactory.BuildStairs(new AmethystStairsBlock(AMETHYST_CRYSTAL_BLOCK));
	public static final BlockContainer AMETHYST_CRYSTAL_SLAB = ModFactory.BuildSlab(new AmethystSlabBlock(AMETHYST_CRYSTAL_BLOCK));
	public static final BlockContainer AMETHYST_CRYSTAL_WALL = ModFactory.BuildWall(new AmethystWallBlock(AMETHYST_CRYSTAL_BLOCK));
	public static final BlockContainer AMETHYST_BRICKS = ModFactory.BuildBlock(new AmethystBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f, 1.5f).requiresTool()));
	public static final BlockContainer AMETHYST_BRICK_STAIRS = ModFactory.BuildStairs(new AmethystStairsBlock(AMETHYST_BRICKS));
	public static final BlockContainer AMETHYST_BRICK_SLAB = ModFactory.BuildSlab(new AmethystSlabBlock(AMETHYST_BRICKS));
	public static final BlockContainer AMETHYST_BRICK_WALL = ModFactory.BuildWall(new AmethystWallBlock(AMETHYST_BRICKS));
	public static final Item AMETHYST_AXE = new ModAxeItem(ModToolMaterials.AMETHYST, 5, -3);
	public static final Item AMETHYST_HOE = new ModHoeItem(ModToolMaterials.AMETHYST, -3, 0);
	public static final Item AMETHYST_PICKAXE = new ModPickaxeItem(ModToolMaterials.AMETHYST, 1, -2.8F);
	public static final Item AMETHYST_SHOVEL = new ModShovelItem(ModToolMaterials.AMETHYST, 1.5F, -3);
	public static final Item AMETHYST_SWORD = new ModSwordItem(ModToolMaterials.AMETHYST, 3, -2.4F);
	public static final Item AMETHYST_KNIFE = new ModKnifeItem(ModToolMaterials.AMETHYST);
	public static final Item AMETHYST_HELMET = new ModArmorItem(ModArmorMaterials.AMETHYST, EquipmentSlot.HEAD);
	public static final Item AMETHYST_CHESTPLATE = new ModArmorItem(ModArmorMaterials.AMETHYST, EquipmentSlot.CHEST);
	public static final Item AMETHYST_LEGGINGS = new ModArmorItem(ModArmorMaterials.AMETHYST, EquipmentSlot.LEGS);
	public static final Item AMETHYST_BOOTS = new ModArmorItem(ModArmorMaterials.AMETHYST, EquipmentSlot.FEET);
	public static final Item AMETHYST_HORSE_ARMOR = ModFactory.MakeHorseArmor(ModArmorMaterials.AMETHYST);
	//</editor-fold>
	//<editor-fold desc="Andesite">
	public static final BlockContainer POLISHED_ANDESITE_WALL = ModFactory.MakeWall(Blocks.POLISHED_ANDESITE);
	//</editor-fold>
	//<editor-fold desc="Arrows">
	public static final ArrowContainer AMETHYST_ARROW = new ArrowContainer(AmethystArrowEntity::new, AmethystArrowEntity::new, AmethystArrowEntity::new);
	public static final ArrowContainer CHORUS_ARROW = new ArrowContainer(ChorusArrowEntity::new, ChorusArrowEntity::new, ChorusArrowEntity::new);
	//</editor-fold>
	//<editor-fold desc="Backport 1.19">
	public static final StatusEffect DARKNESS_EFFECT = new DarknessEffect();
	public static final Enchantment SWIFT_SNEAK_ENCHANTMENT = new SwiftSneakEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.LEGS);
	public static final Item RECOVERY_COMPASS = new RecoveryCompassItem(ItemSettings(ItemGroup.TOOLS));
	//</editor-fold>
	//<editor-fold desc="Backport 1.20">
	public static final BlockContainer PINK_PETALS = new BlockContainer(new FlowerbedBlock(Block.Settings.of(Material.REPLACEABLE_PLANT, MapColor.PINK).strength(0.1f).noCollision().sounds(ModBlockSoundGroups.PINK_PETALS)), ItemSettings(ItemGroup.DECORATIONS)).compostable(0.3f);
	//</editor-fold>
	//<editor-fold desc="Bamboo">
	public static final BlockContainer BAMBOO_BLOCK = ModFactory.MakeLog(MapColor.YELLOW, MapColor.DARK_GREEN, ModBlockSoundGroups.BAMBOO_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300);
	public static final BlockContainer STRIPPED_BAMBOO_BLOCK = ModFactory.MakeLog(MapColor.YELLOW, MapColor.YELLOW, ModBlockSoundGroups.BAMBOO_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300);
	public static final BlockContainer BAMBOO_PLANKS = ModFactory.MakePlanks(MapColor.YELLOW, ModBlockSoundGroups.BAMBOO_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300);
	public static final BlockContainer BAMBOO_STAIRS = ModFactory.MakeStairs(BAMBOO_PLANKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300);
	public static final BlockContainer BAMBOO_SLAB = ModFactory.MakeSlab(BAMBOO_PLANKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(150);
	public static final BlockContainer BAMBOO_FENCE = ModFactory.MakeWoodFence(BAMBOO_PLANKS, ItemSettings(ItemGroup.DECORATIONS)).flammable(5, 20).fuel(300);
	public static final BlockContainer BAMBOO_FENCE_GATE = ModFactory.MakeWoodFenceGate(BAMBOO_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN, ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE).flammable(5, 20).fuel(300);
	public static final BlockContainer BAMBOO_DOOR = ModFactory.MakeWoodDoor(BAMBOO_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_OPEN).fuel(200);
	public static final BlockContainer BAMBOO_TRAPDOOR = ModFactory.MakeWoodTrapdoor(BAMBOO_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_OPEN).fuel(300);
	public static final BlockContainer BAMBOO_PRESSURE_PLATE = ModFactory.MakeWoodPressurePlate(BAMBOO_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON, ModSoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF).fuel(300);
	public static final BlockContainer BAMBOO_BUTTON = ModFactory.MakeWoodButton(ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON, ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_OFF).fuel(100);
	public static final SignContainer BAMBOO_SIGN = ModFactory.MakeWoodSign("minecraft:bamboo", ModFactory.SignItemSettings(ItemGroup.DECORATIONS), MapColor.YELLOW).fuel(200);
	public static final BoatContainer BAMBOO_RAFT = ModFactory.MakeBoat("minecraft:bamboo", BAMBOO_PLANKS, ModFactory.BoatSettings(ItemGroup.TRANSPORTATION)).fuel(1200);
	//Mosaic
	public static final BlockContainer BAMBOO_MOSAIC = ModFactory.MakeBlock(BAMBOO_PLANKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).dropSelf();
	public static final BlockContainer BAMBOO_MOSAIC_STAIRS = ModFactory.MakeStairs(BAMBOO_MOSAIC, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300);
	public static final BlockContainer BAMBOO_MOSAIC_SLAB = ModFactory.MakeSlab(BAMBOO_MOSAIC, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(150);
	//Extended Bamboo
	public static final TorchContainer BAMBOO_TORCH = ModFactory.MakeTorch(BlockSoundGroup.BAMBOO);
	public static final TorchContainer BAMBOO_SOUL_TORCH = ModFactory.MakeSoulTorch(BlockSoundGroup.BAMBOO);
	public static final TorchContainer BAMBOO_ENDER_TORCH = ModFactory.MakeEnderTorch(BlockSoundGroup.BAMBOO);
	public static final TorchContainer UNDERWATER_BAMBOO_TORCH = ModFactory.MakeUnderwaterTorch(BlockSoundGroup.BAMBOO);
	public static final BlockContainer BAMBOO_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.YELLOW, ModBlockSoundGroups.BAMBOO_WOOD).flammable(30, 20).fuel(300);
	public static final BlockContainer BAMBOO_LADDER = ModFactory.MakeLadder(Block.Settings.of(Material.DECORATION).strength(0.4F).sounds(BlockSoundGroup.BAMBOO).nonOpaque());
	public static final BlockContainer BAMBOO_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.YELLOW);
	public static final BlockContainer BAMBOO_BARREL = ModFactory.MakeBarrel(MapColor.YELLOW, ModBlockSoundGroups.BAMBOO_WOOD).fuel(300);
	public static final BlockContainer BAMBOO_LECTERN = ModFactory.MakeLectern(MapColor.YELLOW, ModBlockSoundGroups.BAMBOO_WOOD).flammable(30, 20).fuel(300);
	//</editor-fold>
	//<editor-fold desc="Birch">
	public static final Item BIRCH_CHEST_BOAT = ModFactory.MakeChestBoat(BoatEntity.Type.BIRCH, ModFactory.BoatSettings(ItemGroup.TRANSPORTATION));
	public static final WallBlockContainer BIRCH_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.BIRCH, MapColor.OFF_WHITE, ModFactory.SignItemSettings(ItemGroup.DECORATIONS));
	public static final BlockContainer BIRCH_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.PALE_YELLOW).flammable(30, 20).fuel(300);
	public static final BlockContainer BIRCH_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer BIRCH_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.PALE_YELLOW);
	public static final BlockContainer BIRCH_BARREL = ModFactory.MakeBarrel(MapColor.PALE_YELLOW).fuel(300);
	public static final BlockContainer BIRCH_LECTERN = ModFactory.MakeLectern(MapColor.PALE_YELLOW).flammable(30, 20).fuel(300);
	//</editor-fold>
	//<editor-fold desc="Blue Mushroom">
	private static ModConfiguredFeature<?, ?> getHugeBlueMushroomConfigured() { return HUGE_BLUE_MUSHROOM_CONFIGURED; }
	public static final PottedBlockContainer BLUE_MUSHROOM = new PottedBlockContainer(new MushroomPlantBlock(Block.Settings.of(Material.PLANT, MapColor.LIGHT_BLUE).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).postProcess(ModFactory::always), () -> getHugeBlueMushroomConfigured().getRegistryEntry())).compostable(0.65f).dropSelf();
	public static final BlockContainer BLUE_MUSHROOM_BLOCK = new BlockContainer(new MushroomBlock(Block.Settings.of(Material.WOOD, MapColor.LIGHT_BLUE).strength(0.2F).sounds(BlockSoundGroup.WOOD))).compostable(0.85f).drops(DropTable.Mushroom(BLUE_MUSHROOM));
	public static final Feature<HugeMushroomFeatureConfig> HUGE_BLUE_MUSHROOM_FEATURE = new HugeBlueMushroomFeature(HugeMushroomFeatureConfig.CODEC);
	public static final ModConfiguredFeature<?, ?> HUGE_BLUE_MUSHROOM_CONFIGURED = new ModConfiguredFeature<>("huge_blue_mushroom", new ConfiguredFeature<>(HUGE_BLUE_MUSHROOM_FEATURE, new HugeMushroomFeatureConfig(new ModSimpleBlockStateProvider(BLUE_MUSHROOM_BLOCK.asBlock().getDefaultState().with(MushroomBlock.DOWN, false)), new ModSimpleBlockStateProvider(Blocks.MUSHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false).with(MushroomBlock.DOWN, false)), 3)));
	//</editor-fold>
	//<editor-fold desc="Boats">
	public static final EntityType<ModBoatEntity> MOD_BOAT_ENTITY = FabricEntityTypeBuilder.<ModBoatEntity>create(SpawnGroup.MISC, ModBoatEntity::new).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).trackRangeChunks(10).build();
	public static final EntityType<ChestBoatEntity> CHEST_BOAT_ENTITY = FabricEntityTypeBuilder.<ChestBoatEntity>create(SpawnGroup.MISC, ChestBoatEntity::new).dimensions(EntityDimensions.fixed(1.375f, 0.5625f)).trackRangeChunks(10).build();
	public static final EntityType<ModChestBoatEntity> MOD_CHEST_BOAT_ENTITY = FabricEntityTypeBuilder.<ModChestBoatEntity>create(SpawnGroup.MISC, ModChestBoatEntity::new).dimensions(EntityDimensions.fixed(1.375f, 0.5625f)).trackRangeChunks(10).build();
	//</editor-fold>
	//<editor-fold desc="Bone">
	public static final TorchContainer BONE_TORCH = ModFactory.MakeTorch(BlockSoundGroup.BONE);
	public static final TorchContainer BONE_SOUL_TORCH = ModFactory.MakeSoulTorch(BlockSoundGroup.BONE);
	public static final TorchContainer BONE_ENDER_TORCH = ModFactory.MakeEnderTorch(BlockSoundGroup.BONE);
	public static final TorchContainer UNDERWATER_BONE_TORCH = ModFactory.MakeUnderwaterTorch(BlockSoundGroup.BONE);
	public static final BlockContainer BONE_LADDER = ModFactory.MakeLadder(BlockSoundGroup.BONE);
	//</editor-fold>
	//<editor-fold desc="Books">
	public static final Item UNREADABLE_BOOK = new Item(ItemSettings());
	public static final Item RED_BOOK = new Item(ItemSettings());
	public static final Item ORANGE_BOOK = new Item(ItemSettings());
	public static final Item YELLOW_BOOK = new Item(ItemSettings());
	public static final Item GREEN_BOOK = new Item(ItemSettings());
	public static final Item BLUE_BOOK = new Item(ItemSettings());
	public static final Item PURPLE_BOOK = new Item(ItemSettings());
	public static final Item GRAY_BOOK = new Item(ItemSettings());
	//</editor-fold>
	//<editor-fold desc="Calcite">
	public static final BlockContainer CALCITE_STAIRS = ModFactory.MakeStairs(Blocks.CALCITE);
	public static final BlockContainer CALCITE_SLAB = ModFactory.MakeSlab(Blocks.CALCITE);
	public static final BlockContainer CALCITE_WALL = ModFactory.MakeWall(Blocks.CALCITE);
	public static final BlockContainer SMOOTH_CALCITE = ModFactory.MakeBlock(Blocks.CALCITE);
	public static final BlockContainer SMOOTH_CALCITE_STAIRS = ModFactory.MakeStairs(SMOOTH_CALCITE);
	public static final BlockContainer SMOOTH_CALCITE_SLAB = ModFactory.MakeSlab(SMOOTH_CALCITE);
	public static final BlockContainer SMOOTH_CALCITE_WALL = ModFactory.MakeWall(SMOOTH_CALCITE);
	public static final BlockContainer CALCITE_BRICKS = ModFactory.MakeBlock(Blocks.CALCITE);
	public static final BlockContainer CALCITE_BRICK_STAIRS = ModFactory.MakeStairs(CALCITE_BRICKS);
	public static final BlockContainer CALCITE_BRICK_SLAB = ModFactory.MakeSlab(CALCITE_BRICKS);
	public static final BlockContainer CALCITE_BRICK_WALL = ModFactory.MakeWall(CALCITE_BRICKS);
	//</editor-fold>
	//<editor-fold desc="Camel">
	public static final ModSensorType<TemptationsSensor> CAMEL_TEMPTATIONS_SENSOR = new ModSensorType<>("camel_temptations", () -> new TemptationsSensor(CamelBrain.getBreedingIngredient()));
	public static final EntityType<CamelEntity> CAMEL_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CamelEntity::new).dimensions(EntityDimensions.fixed(1.7f, 2.375f)).trackRangeChunks(10).build();
	public static final Item CAMEL_SPAWN_EGG = new SpawnEggItem(CAMEL_ENTITY, 16565097, 13341495, ItemSettings(ItemGroup.MISC));
	//</editor-fold>
	//<editor-fold desc="Charred">
	public static final BlockContainer CHARRED_LOG = ModFactory.MakeLog(MapColor.BLACK);
	public static final BlockContainer STRIPPED_CHARRED_LOG = ModFactory.MakeLog(MapColor.BLACK);
	public static final BlockContainer CHARRED_WOOD = ModFactory.MakeWood(MapColor.BLACK);
	public static final BlockContainer STRIPPED_CHARRED_WOOD = ModFactory.MakeWood(MapColor.BLACK);
	public static final BlockContainer CHARRED_PLANKS = ModFactory.MakePlanks(MapColor.BLACK);
	public static final BlockContainer CHARRED_STAIRS = ModFactory.MakeStairs(CHARRED_PLANKS);
	public static final BlockContainer CHARRED_SLAB = ModFactory.MakeSlab(CHARRED_PLANKS);
	public static final BlockContainer CHARRED_FENCE = ModFactory.MakeWoodFence(CHARRED_PLANKS);
	public static final BlockContainer CHARRED_FENCE_GATE = ModFactory.MakeWoodFenceGate(CHARRED_PLANKS);
	public static final BlockContainer CHARRED_DOOR = ModFactory.MakeWoodDoor(CHARRED_PLANKS);
	public static final BlockContainer CHARRED_TRAPDOOR = ModFactory.MakeWoodTrapdoor(CHARRED_PLANKS);
	public static final BlockContainer CHARRED_PRESSURE_PLATE = ModFactory.MakeWoodPressurePlate(CHARRED_PLANKS);
	public static final BlockContainer CHARRED_BUTTON = ModFactory.MakeWoodButton();
	public static final SignContainer CHARRED_SIGN = ModFactory.MakeWoodSign("charred", MapColor.BLACK);
	public static final BoatContainer CHARRED_BOAT = ModFactory.MakeBoat("charred", CHARRED_PLANKS);
	public static final BlockContainer CHARRED_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.BLACK);
	public static final BlockContainer CHARRED_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer CHARRED_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.BLACK);
	public static final BlockContainer CHARRED_BARREL = ModFactory.MakeBarrel(MapColor.BLACK);
	public static final BlockContainer CHARRED_LECTERN = ModFactory.MakeLectern(MapColor.BLACK);
	//</editor-fold>
	//<editor-fold desc="Cherry">
	public static final BlockContainer CHERRY_LOG = ModFactory.MakeLog(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_GRAY, ModBlockSoundGroups.CHERRY_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300);
	public static final BlockContainer STRIPPED_CHERRY_LOG = ModFactory.MakeLog(MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_PINK, ModBlockSoundGroups.CHERRY_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300);
	public static final BlockContainer CHERRY_WOOD = ModFactory.MakeWood(MapColor.TERRACOTTA_GRAY, ModBlockSoundGroups.CHERRY_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300);
	public static final BlockContainer STRIPPED_CHERRY_WOOD = ModFactory.MakeWood(MapColor.TERRACOTTA_PINK, ModBlockSoundGroups.CHERRY_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300);
	public static final BlockContainer CHERRY_LEAVES = new BlockContainer(new CherryLeavesBlock(AbstractBlock.Settings.of(Material.LEAVES, MapColor.PINK).strength(0.2f).ticksRandomly().sounds(ModBlockSoundGroups.CHERRY_LEAVES).nonOpaque().allowsSpawning(ModFactory::canSpawnOnLeaves).suffocates(ModFactory::never).blockVision(ModFactory::never)), ItemSettings(ItemGroup.DECORATIONS)).flammable(30, 60).compostable(0.3f);
	public static final DefaultParticleType DRIPPING_CHERRY_LEAVES_PARTICLE = FabricParticleTypes.simple(false);
	public static final DefaultParticleType FALLING_CHERRY_LEAVES_PARTICLE = FabricParticleTypes.simple(false);
	public static final DefaultParticleType LANDING_CHERRY_LEAVES_PARTICLE = FabricParticleTypes.simple(false);
	public static final BlockContainer CHERRY_PLANKS = ModFactory.MakePlanks(MapColor.TERRACOTTA_WHITE, ModBlockSoundGroups.CHERRY_WOOD, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300);
	public static final BlockContainer CHERRY_STAIRS = ModFactory.MakeStairs(CHERRY_PLANKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300);
	public static final BlockContainer CHERRY_SLAB = ModFactory.MakeSlab(CHERRY_PLANKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(150);
	public static final BlockContainer CHERRY_FENCE = ModFactory.MakeWoodFence(CHERRY_PLANKS, ItemSettings(ItemGroup.DECORATIONS)).flammable(5, 20).fuel(300);
	public static final BlockContainer CHERRY_FENCE_GATE = ModFactory.MakeWoodFenceGate(CHERRY_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModSoundEvents.BLOCK_CHERRY_WOOD_FENCE_GATE_OPEN, ModSoundEvents.BLOCK_CHERRY_WOOD_FENCE_GATE_CLOSE).flammable(5, 20).fuel(300);
	public static final BlockContainer CHERRY_DOOR = ModFactory.MakeWoodDoor(CHERRY_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.CHERRY_WOOD, ModSoundEvents.BLOCK_CHERRY_WOOD_DOOR_CLOSE, ModSoundEvents.BLOCK_CHERRY_WOOD_DOOR_OPEN).fuel(200);
	public static final BlockContainer CHERRY_TRAPDOOR = ModFactory.MakeWoodTrapdoor(CHERRY_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.CHERRY_WOOD, ModSoundEvents.BLOCK_CHERRY_WOOD_TRAPDOOR_CLOSE, ModSoundEvents.BLOCK_CHERRY_WOOD_TRAPDOOR_OPEN).fuel(300);
	public static final BlockContainer CHERRY_PRESSURE_PLATE = ModFactory.MakeWoodPressurePlate(CHERRY_PLANKS, ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.CHERRY_WOOD, ModSoundEvents.BLOCK_CHERRY_WOOD_PRESSURE_PLATE_CLICK_ON, ModSoundEvents.BLOCK_CHERRY_WOOD_PRESSURE_PLATE_CLICK_OFF).fuel(300);
	public static final BlockContainer CHERRY_BUTTON = ModFactory.MakeWoodButton(ItemSettings(ItemGroup.REDSTONE), ModBlockSoundGroups.CHERRY_WOOD, ModSoundEvents.BLOCK_CHERRY_WOOD_BUTTON_CLICK_ON, ModSoundEvents.BLOCK_CHERRY_WOOD_BUTTON_CLICK_OFF).fuel(100);
	public static final SignContainer CHERRY_SIGN = ModFactory.MakeWoodSign("minecraft:cherry", ModFactory.SignItemSettings(ItemGroup.DECORATIONS), MapColor.TERRACOTTA_WHITE, ModBlockSoundGroups.CHERRY_WOOD_HANGING_SIGN).fuel(200);
	public static final BoatContainer CHERRY_BOAT = ModFactory.MakeBoat("minecraft:cherry", CHERRY_PLANKS, ModFactory.BoatSettings(ItemGroup.TRANSPORTATION)).fuel(1200);
	public static final PottedBlockContainer CHERRY_SAPLING = new PottedBlockContainer(new SaplingBlock(new CherrySaplingGenerator(), AbstractBlock.Settings.of(Material.PLANT, MapColor.PINK).noCollision().ticksRandomly().breakInstantly().sounds(ModBlockSoundGroups.CHERRY_SAPLING)), ItemSettings(ItemGroup.DECORATIONS)).compostable(0.3f).dropSelf();
	//Extended
	public static final BlockContainer CHERRY_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.TERRACOTTA_WHITE).flammable(30, 20).fuel(300);
	public static final BlockContainer CHERRY_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer CHERRY_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.TERRACOTTA_WHITE);
	public static final BlockContainer CHERRY_BARREL = ModFactory.MakeBarrel(MapColor.TERRACOTTA_WHITE, ModBlockSoundGroups.CHERRY_WOOD).fuel(300);
	public static final BlockContainer CHERRY_LECTERN = ModFactory.MakeLectern(MapColor.TERRACOTTA_WHITE, ModBlockSoundGroups.CHERRY_WOOD).flammable(30, 20).fuel(300);
	//</editor-fold>
	//<editor-fold desc="Copper">
	public static final DefaultParticleType COPPER_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final Item COPPER_NUGGET = new Item(ItemSettings());
	public static final TorchContainer COPPER_TORCH = ModFactory.MakeOxidizableTorch(Oxidizable.OxidationLevel.UNAFFECTED, BlockSoundGroup.COPPER, COPPER_FLAME_PARTICLE);
	public static final TorchContainer EXPOSED_COPPER_TORCH = ModFactory.MakeOxidizableTorch(Oxidizable.OxidationLevel.EXPOSED, BlockSoundGroup.COPPER, COPPER_FLAME_PARTICLE);
	public static final TorchContainer WEATHERED_COPPER_TORCH = ModFactory.MakeOxidizableTorch(Oxidizable.OxidationLevel.WEATHERED, BlockSoundGroup.COPPER, COPPER_FLAME_PARTICLE);
	public static final TorchContainer OXIDIZED_COPPER_TORCH = ModFactory.MakeOxidizableTorch(Oxidizable.OxidationLevel.OXIDIZED, BlockSoundGroup.COPPER, COPPER_FLAME_PARTICLE);
	public static final TorchContainer WAXED_COPPER_TORCH = ModFactory.MakeTorch(BlockSoundGroup.COPPER, COPPER_FLAME_PARTICLE);
	public static final TorchContainer WAXED_EXPOSED_COPPER_TORCH = ModFactory.MakeTorch(BlockSoundGroup.COPPER, COPPER_FLAME_PARTICLE);
	public static final TorchContainer WAXED_WEATHERED_COPPER_TORCH = ModFactory.MakeTorch(BlockSoundGroup.COPPER, COPPER_FLAME_PARTICLE);
	public static final TorchContainer WAXED_OXIDIZED_COPPER_TORCH = ModFactory.MakeTorch(BlockSoundGroup.COPPER, COPPER_FLAME_PARTICLE);

	public static final TorchContainer COPPER_SOUL_TORCH = ModFactory.MakeOxidizableSoulTorch(Oxidizable.OxidationLevel.UNAFFECTED, BlockSoundGroup.COPPER);
	public static final TorchContainer EXPOSED_COPPER_SOUL_TORCH = ModFactory.MakeOxidizableSoulTorch(Oxidizable.OxidationLevel.EXPOSED, BlockSoundGroup.COPPER);
	public static final TorchContainer WEATHERED_COPPER_SOUL_TORCH = ModFactory.MakeOxidizableSoulTorch(Oxidizable.OxidationLevel.WEATHERED, BlockSoundGroup.COPPER);
	public static final TorchContainer OXIDIZED_COPPER_SOUL_TORCH = ModFactory.MakeOxidizableSoulTorch(Oxidizable.OxidationLevel.OXIDIZED, BlockSoundGroup.COPPER);
	public static final TorchContainer WAXED_COPPER_SOUL_TORCH = ModFactory.MakeSoulTorch(BlockSoundGroup.COPPER);
	public static final TorchContainer WAXED_EXPOSED_COPPER_SOUL_TORCH = ModFactory.MakeSoulTorch(BlockSoundGroup.COPPER);
	public static final TorchContainer WAXED_WEATHERED_COPPER_SOUL_TORCH = ModFactory.MakeSoulTorch(BlockSoundGroup.COPPER);
	public static final TorchContainer WAXED_OXIDIZED_COPPER_SOUL_TORCH = ModFactory.MakeSoulTorch(BlockSoundGroup.COPPER);

	public static final TorchContainer COPPER_ENDER_TORCH = ModFactory.MakeOxidizableEnderTorch(Oxidizable.OxidationLevel.UNAFFECTED, BlockSoundGroup.COPPER);
	public static final TorchContainer EXPOSED_COPPER_ENDER_TORCH = ModFactory.MakeOxidizableEnderTorch(Oxidizable.OxidationLevel.EXPOSED, BlockSoundGroup.COPPER);
	public static final TorchContainer WEATHERED_COPPER_ENDER_TORCH = ModFactory.MakeOxidizableEnderTorch(Oxidizable.OxidationLevel.WEATHERED, BlockSoundGroup.COPPER);
	public static final TorchContainer OXIDIZED_COPPER_ENDER_TORCH = ModFactory.MakeOxidizableEnderTorch(Oxidizable.OxidationLevel.OXIDIZED, BlockSoundGroup.COPPER);
	public static final TorchContainer WAXED_COPPER_ENDER_TORCH = ModFactory.MakeEnderTorch(BlockSoundGroup.COPPER);
	public static final TorchContainer WAXED_EXPOSED_COPPER_ENDER_TORCH = ModFactory.MakeEnderTorch(BlockSoundGroup.COPPER);
	public static final TorchContainer WAXED_WEATHERED_COPPER_ENDER_TORCH = ModFactory.MakeEnderTorch(BlockSoundGroup.COPPER);
	public static final TorchContainer WAXED_OXIDIZED_COPPER_ENDER_TORCH = ModFactory.MakeEnderTorch(BlockSoundGroup.COPPER);

	public static final TorchContainer UNDERWATER_COPPER_TORCH = ModFactory.MakeOxidizableUnderwaterTorch(Oxidizable.OxidationLevel.UNAFFECTED, BlockSoundGroup.COPPER);
	public static final TorchContainer EXPOSED_UNDERWATER_COPPER_TORCH = ModFactory.MakeOxidizableUnderwaterTorch(Oxidizable.OxidationLevel.EXPOSED, BlockSoundGroup.COPPER);
	public static final TorchContainer WEATHERED_UNDERWATER_COPPER_TORCH = ModFactory.MakeOxidizableUnderwaterTorch(Oxidizable.OxidationLevel.WEATHERED, BlockSoundGroup.COPPER);
	public static final TorchContainer OXIDIZED_UNDERWATER_COPPER_TORCH = ModFactory.MakeOxidizableUnderwaterTorch(Oxidizable.OxidationLevel.OXIDIZED, BlockSoundGroup.COPPER);
	public static final TorchContainer WAXED_UNDERWATER_COPPER_TORCH = ModFactory.MakeUnderwaterTorch(BlockSoundGroup.COPPER);
	public static final TorchContainer WAXED_EXPOSED_UNDERWATER_COPPER_TORCH = ModFactory.MakeUnderwaterTorch(BlockSoundGroup.COPPER);
	public static final TorchContainer WAXED_WEATHERED_UNDERWATER_COPPER_TORCH = ModFactory.MakeUnderwaterTorch(BlockSoundGroup.COPPER);
	public static final TorchContainer WAXED_OXIDIZED_UNDERWATER_COPPER_TORCH = ModFactory.MakeUnderwaterTorch(BlockSoundGroup.COPPER);

	public static final BlockContainer COPPER_LANTERN = ModFactory.MakeOxidizableLantern(15, Oxidizable.OxidationLevel.UNAFFECTED);
	public static final BlockContainer EXPOSED_COPPER_LANTERN = ModFactory.MakeOxidizableLantern(15, Oxidizable.OxidationLevel.EXPOSED);
	public static final BlockContainer WEATHERED_COPPER_LANTERN = ModFactory.MakeOxidizableLantern(15, Oxidizable.OxidationLevel.WEATHERED);
	public static final BlockContainer OXIDIZED_COPPER_LANTERN = ModFactory.MakeOxidizableLantern(15, Oxidizable.OxidationLevel.OXIDIZED);
	public static final BlockContainer WAXED_COPPER_LANTERN = ModFactory.MakeLantern(15);
	public static final BlockContainer WAXED_EXPOSED_COPPER_LANTERN = ModFactory.MakeLantern(15);
	public static final BlockContainer WAXED_WEATHERED_COPPER_LANTERN = ModFactory.MakeLantern(15);
	public static final BlockContainer WAXED_OXIDIZED_COPPER_LANTERN = ModFactory.MakeLantern(15);

	public static final BlockContainer COPPER_SOUL_LANTERN = ModFactory.MakeOxidizableLantern(10, Oxidizable.OxidationLevel.UNAFFECTED);
	public static final BlockContainer EXPOSED_COPPER_SOUL_LANTERN = ModFactory.MakeOxidizableLantern(10, Oxidizable.OxidationLevel.EXPOSED);
	public static final BlockContainer WEATHERED_COPPER_SOUL_LANTERN = ModFactory.MakeOxidizableLantern(10, Oxidizable.OxidationLevel.WEATHERED);
	public static final BlockContainer OXIDIZED_COPPER_SOUL_LANTERN = ModFactory.MakeOxidizableLantern(10, Oxidizable.OxidationLevel.OXIDIZED);
	public static final BlockContainer WAXED_COPPER_SOUL_LANTERN = ModFactory.MakeLantern(10);
	public static final BlockContainer WAXED_EXPOSED_COPPER_SOUL_LANTERN = ModFactory.MakeLantern(10);
	public static final BlockContainer WAXED_WEATHERED_COPPER_SOUL_LANTERN = ModFactory.MakeLantern(10);
	public static final BlockContainer WAXED_OXIDIZED_COPPER_SOUL_LANTERN = ModFactory.MakeLantern(10);

	public static final BlockContainer COPPER_ENDER_LANTERN = ModFactory.MakeOxidizableLantern(13, Oxidizable.OxidationLevel.UNAFFECTED);
	public static final BlockContainer EXPOSED_COPPER_ENDER_LANTERN = ModFactory.MakeOxidizableLantern(13, Oxidizable.OxidationLevel.EXPOSED);
	public static final BlockContainer WEATHERED_COPPER_ENDER_LANTERN = ModFactory.MakeOxidizableLantern(13, Oxidizable.OxidationLevel.WEATHERED);
	public static final BlockContainer OXIDIZED_COPPER_ENDER_LANTERN = ModFactory.MakeOxidizableLantern(13, Oxidizable.OxidationLevel.OXIDIZED);
	public static final BlockContainer WAXED_COPPER_ENDER_LANTERN = ModFactory.MakeLantern(13);
	public static final BlockContainer WAXED_EXPOSED_COPPER_ENDER_LANTERN = ModFactory.MakeLantern(13);
	public static final BlockContainer WAXED_WEATHERED_COPPER_ENDER_LANTERN = ModFactory.MakeLantern(13);
	public static final BlockContainer WAXED_OXIDIZED_COPPER_ENDER_LANTERN = ModFactory.MakeLantern(13);

	public static final BlockContainer COPPER_BUTTON = ModFactory.MakeOxidizableButton(BlockSoundGroup.COPPER, Oxidizable.OxidationLevel.UNAFFECTED);
	public static final BlockContainer EXPOSED_COPPER_BUTTON = ModFactory.MakeOxidizableButton(BlockSoundGroup.COPPER, Oxidizable.OxidationLevel.EXPOSED);
	public static final BlockContainer WEATHERED_COPPER_BUTTON = ModFactory.MakeOxidizableButton(BlockSoundGroup.COPPER, Oxidizable.OxidationLevel.WEATHERED);
	public static final BlockContainer OXIDIZED_COPPER_BUTTON = ModFactory.MakeOxidizableButton(BlockSoundGroup.COPPER, Oxidizable.OxidationLevel.OXIDIZED);
	public static final BlockContainer WAXED_COPPER_BUTTON = ModFactory.MakeMetalButton(BlockSoundGroup.COPPER);
	public static final BlockContainer WAXED_EXPOSED_COPPER_BUTTON = ModFactory.MakeMetalButton(BlockSoundGroup.COPPER);
	public static final BlockContainer WAXED_WEATHERED_COPPER_BUTTON = ModFactory.MakeMetalButton(BlockSoundGroup.COPPER);
	public static final BlockContainer WAXED_OXIDIZED_COPPER_BUTTON = ModFactory.MakeMetalButton(BlockSoundGroup.COPPER);

	public static final BlockContainer COPPER_CHAIN = ModFactory.MakeOxidizableChain(Oxidizable.OxidationLevel.UNAFFECTED);
	public static final BlockContainer EXPOSED_COPPER_CHAIN = ModFactory.MakeOxidizableChain(Oxidizable.OxidationLevel.EXPOSED);
	public static final BlockContainer WEATHERED_COPPER_CHAIN = ModFactory.MakeOxidizableChain(Oxidizable.OxidationLevel.WEATHERED);
	public static final BlockContainer OXIDIZED_COPPER_CHAIN = ModFactory.MakeOxidizableChain(Oxidizable.OxidationLevel.OXIDIZED);
	public static final BlockContainer WAXED_COPPER_CHAIN = ModFactory.MakeChain();
	public static final BlockContainer WAXED_EXPOSED_COPPER_CHAIN = ModFactory.MakeChain();
	public static final BlockContainer WAXED_WEATHERED_COPPER_CHAIN = ModFactory.MakeChain();
	public static final BlockContainer WAXED_OXIDIZED_COPPER_CHAIN = ModFactory.MakeChain();
	
	public static final BlockContainer COPPER_BARS = ModFactory.MakeOxidizableBars(BlockSoundGroup.COPPER, Oxidizable.OxidationLevel.UNAFFECTED);
	public static final BlockContainer EXPOSED_COPPER_BARS = ModFactory.MakeOxidizableBars(BlockSoundGroup.COPPER, Oxidizable.OxidationLevel.EXPOSED);
	public static final BlockContainer WEATHERED_COPPER_BARS = ModFactory.MakeOxidizableBars(BlockSoundGroup.COPPER, Oxidizable.OxidationLevel.WEATHERED);
	public static final BlockContainer OXIDIZED_COPPER_BARS = ModFactory.MakeOxidizableBars(BlockSoundGroup.COPPER, Oxidizable.OxidationLevel.OXIDIZED);
	public static final BlockContainer WAXED_COPPER_BARS = ModFactory.MakeBars(BlockSoundGroup.COPPER);
	public static final BlockContainer WAXED_EXPOSED_COPPER_BARS = ModFactory.MakeBars(BlockSoundGroup.COPPER);
	public static final BlockContainer WAXED_WEATHERED_COPPER_BARS = ModFactory.MakeBars(BlockSoundGroup.COPPER);
	public static final BlockContainer WAXED_OXIDIZED_COPPER_BARS = ModFactory.MakeBars(BlockSoundGroup.COPPER);

	public static final BlockContainer COPPER_WALL = ModFactory.MakeOxidizableWall(Blocks.COPPER_BLOCK, Oxidizable.OxidationLevel.UNAFFECTED);
	public static final BlockContainer EXPOSED_COPPER_WALL = ModFactory.MakeOxidizableWall(Blocks.EXPOSED_COPPER, Oxidizable.OxidationLevel.EXPOSED);
	public static final BlockContainer WEATHERED_COPPER_WALL = ModFactory.MakeOxidizableWall(Blocks.WEATHERED_COPPER, Oxidizable.OxidationLevel.WEATHERED);
	public static final BlockContainer OXIDIZED_COPPER_WALL = ModFactory.MakeOxidizableWall(Blocks.OXIDIZED_COPPER, Oxidizable.OxidationLevel.OXIDIZED);
	public static final BlockContainer WAXED_COPPER_WALL = ModFactory.MakeWall(Blocks.WAXED_COPPER_BLOCK);
	public static final BlockContainer WAXED_EXPOSED_COPPER_WALL = ModFactory.MakeWall(Blocks.WAXED_EXPOSED_COPPER);
	public static final BlockContainer WAXED_WEATHERED_COPPER_WALL = ModFactory.MakeWall(Blocks.WAXED_WEATHERED_COPPER);
	public static final BlockContainer WAXED_OXIDIZED_COPPER_WALL = ModFactory.MakeWall(Blocks.WAXED_OXIDIZED_COPPER);
	
	public static final BlockContainer MEDIUM_WEIGHTED_PRESSURE_PLATE = new BlockContainer(new OxidizableWeightedPressurePlateBlock(Oxidizable.OxidationLevel.UNAFFECTED, 75)).dropSelf();
	public static final BlockContainer EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new BlockContainer(new OxidizableWeightedPressurePlateBlock(Oxidizable.OxidationLevel.EXPOSED, 75)).dropSelf();
	public static final BlockContainer WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new BlockContainer(new OxidizableWeightedPressurePlateBlock(Oxidizable.OxidationLevel.WEATHERED, 75)).dropSelf();
	public static final BlockContainer OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new BlockContainer(new OxidizableWeightedPressurePlateBlock(Oxidizable.OxidationLevel.OXIDIZED, 75)).dropSelf();
	public static final BlockContainer WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new BlockContainer(new ModWeightedPressurePlateBlock(75, ModFactory.OxidizablePressurePlateSettings(Oxidizable.OxidationLevel.OXIDIZED))).dropSelf();
	public static final BlockContainer WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new BlockContainer(new ModWeightedPressurePlateBlock(75, ModFactory.OxidizablePressurePlateSettings(Oxidizable.OxidationLevel.EXPOSED))).dropSelf();
	public static final BlockContainer WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new BlockContainer(new ModWeightedPressurePlateBlock(75, ModFactory.OxidizablePressurePlateSettings(Oxidizable.OxidationLevel.WEATHERED))).dropSelf();
	public static final BlockContainer WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = new BlockContainer(new ModWeightedPressurePlateBlock(75, ModFactory.OxidizablePressurePlateSettings(Oxidizable.OxidationLevel.OXIDIZED))).dropSelf();

	public static final Item COPPER_AXE = ModFactory.MakeOxidizableAxe(Oxidizable.OxidationLevel.UNAFFECTED, ModToolMaterials.COPPER);
	public static final Item EXPOSED_COPPER_AXE = ModFactory.MakeOxidizableAxe(Oxidizable.OxidationLevel.EXPOSED, ModToolMaterials.COPPER);
	public static final Item WEATHERED_COPPER_AXE = ModFactory.MakeOxidizableAxe(Oxidizable.OxidationLevel.WEATHERED, ModToolMaterials.COPPER);
	public static final Item OXIDIZED_COPPER_AXE = ModFactory.MakeOxidizableAxe(Oxidizable.OxidationLevel.OXIDIZED, ModToolMaterials.COPPER);
	public static final Item WAXED_COPPER_AXE = ModFactory.MakeAxe(ModToolMaterials.COPPER);
	public static final Item WAXED_EXPOSED_COPPER_AXE = ModFactory.MakeAxe(ModToolMaterials.COPPER);
	public static final Item WAXED_WEATHERED_COPPER_AXE = ModFactory.MakeAxe(ModToolMaterials.COPPER);
	public static final Item WAXED_OXIDIZED_COPPER_AXE = ModFactory.MakeAxe(ModToolMaterials.COPPER);
	
	public static final Item COPPER_HOE = ModFactory.MakeOxidizableHoe(Oxidizable.OxidationLevel.UNAFFECTED, ModToolMaterials.COPPER);
	public static final Item EXPOSED_COPPER_HOE = ModFactory.MakeOxidizableHoe(Oxidizable.OxidationLevel.EXPOSED, ModToolMaterials.COPPER);
	public static final Item WEATHERED_COPPER_HOE = ModFactory.MakeOxidizableHoe(Oxidizable.OxidationLevel.WEATHERED, ModToolMaterials.COPPER);
	public static final Item OXIDIZED_COPPER_HOE = ModFactory.MakeOxidizableHoe(Oxidizable.OxidationLevel.OXIDIZED, ModToolMaterials.COPPER);
	public static final Item WAXED_COPPER_HOE = ModFactory.MakeHoe(ModToolMaterials.COPPER);
	public static final Item WAXED_EXPOSED_COPPER_HOE = ModFactory.MakeHoe(ModToolMaterials.COPPER);
	public static final Item WAXED_WEATHERED_COPPER_HOE = ModFactory.MakeHoe(ModToolMaterials.COPPER);
	public static final Item WAXED_OXIDIZED_COPPER_HOE = ModFactory.MakeHoe(ModToolMaterials.COPPER);

	public static final Item COPPER_PICKAXE = ModFactory.MakeOxidizablePickaxe(Oxidizable.OxidationLevel.UNAFFECTED, ModToolMaterials.COPPER);
	public static final Item EXPOSED_COPPER_PICKAXE = ModFactory.MakeOxidizablePickaxe(Oxidizable.OxidationLevel.EXPOSED, ModToolMaterials.COPPER);
	public static final Item WEATHERED_COPPER_PICKAXE = ModFactory.MakeOxidizablePickaxe(Oxidizable.OxidationLevel.WEATHERED, ModToolMaterials.COPPER);
	public static final Item OXIDIZED_COPPER_PICKAXE = ModFactory.MakeOxidizablePickaxe(Oxidizable.OxidationLevel.OXIDIZED, ModToolMaterials.COPPER);
	public static final Item WAXED_COPPER_PICKAXE = ModFactory.MakePickaxe(ModToolMaterials.COPPER);
	public static final Item WAXED_EXPOSED_COPPER_PICKAXE = ModFactory.MakePickaxe(ModToolMaterials.COPPER);
	public static final Item WAXED_WEATHERED_COPPER_PICKAXE = ModFactory.MakePickaxe(ModToolMaterials.COPPER);
	public static final Item WAXED_OXIDIZED_COPPER_PICKAXE = ModFactory.MakePickaxe(ModToolMaterials.COPPER);

	public static final Item COPPER_SHOVEL = ModFactory.MakeOxidizableShovel(Oxidizable.OxidationLevel.UNAFFECTED, ModToolMaterials.COPPER);
	public static final Item EXPOSED_COPPER_SHOVEL = ModFactory.MakeOxidizableShovel(Oxidizable.OxidationLevel.EXPOSED, ModToolMaterials.COPPER);
	public static final Item WEATHERED_COPPER_SHOVEL = ModFactory.MakeOxidizableShovel(Oxidizable.OxidationLevel.WEATHERED, ModToolMaterials.COPPER);
	public static final Item OXIDIZED_COPPER_SHOVEL = ModFactory.MakeOxidizableShovel(Oxidizable.OxidationLevel.OXIDIZED, ModToolMaterials.COPPER);
	public static final Item WAXED_COPPER_SHOVEL = ModFactory.MakeShovel(ModToolMaterials.COPPER);
	public static final Item WAXED_EXPOSED_COPPER_SHOVEL = ModFactory.MakeShovel(ModToolMaterials.COPPER);
	public static final Item WAXED_WEATHERED_COPPER_SHOVEL = ModFactory.MakeShovel(ModToolMaterials.COPPER);
	public static final Item WAXED_OXIDIZED_COPPER_SHOVEL = ModFactory.MakeShovel(ModToolMaterials.COPPER);

	public static final Item COPPER_SWORD = ModFactory.MakeOxidizableSword(Oxidizable.OxidationLevel.UNAFFECTED, ModToolMaterials.COPPER);
	public static final Item EXPOSED_COPPER_SWORD = ModFactory.MakeOxidizableSword(Oxidizable.OxidationLevel.EXPOSED, ModToolMaterials.COPPER);
	public static final Item WEATHERED_COPPER_SWORD = ModFactory.MakeOxidizableSword(Oxidizable.OxidationLevel.WEATHERED, ModToolMaterials.COPPER);
	public static final Item OXIDIZED_COPPER_SWORD = ModFactory.MakeOxidizableSword(Oxidizable.OxidationLevel.OXIDIZED, ModToolMaterials.COPPER);
	public static final Item WAXED_COPPER_SWORD = ModFactory.MakeSword(ModToolMaterials.COPPER);
	public static final Item WAXED_EXPOSED_COPPER_SWORD = ModFactory.MakeSword(ModToolMaterials.COPPER);
	public static final Item WAXED_WEATHERED_COPPER_SWORD = ModFactory.MakeSword(ModToolMaterials.COPPER);
	public static final Item WAXED_OXIDIZED_COPPER_SWORD = ModFactory.MakeSword(ModToolMaterials.COPPER);

	public static final Item COPPER_KNIFE = ModFactory.MakeOxidizableKnife(Oxidizable.OxidationLevel.UNAFFECTED, ModToolMaterials.COPPER);
	public static final Item EXPOSED_COPPER_KNIFE = ModFactory.MakeOxidizableKnife(Oxidizable.OxidationLevel.EXPOSED, ModToolMaterials.COPPER);
	public static final Item WEATHERED_COPPER_KNIFE = ModFactory.MakeOxidizableKnife(Oxidizable.OxidationLevel.WEATHERED, ModToolMaterials.COPPER);
	public static final Item OXIDIZED_COPPER_KNIFE = ModFactory.MakeOxidizableKnife(Oxidizable.OxidationLevel.OXIDIZED, ModToolMaterials.COPPER);
	public static final Item WAXED_COPPER_KNIFE = ModFactory.MakeKnife(ModToolMaterials.COPPER);
	public static final Item WAXED_EXPOSED_COPPER_KNIFE = ModFactory.MakeKnife(ModToolMaterials.COPPER);
	public static final Item WAXED_WEATHERED_COPPER_KNIFE = ModFactory.MakeKnife(ModToolMaterials.COPPER);
	public static final Item WAXED_OXIDIZED_COPPER_KNIFE = ModFactory.MakeKnife(ModToolMaterials.COPPER);

	//</editor-fold>
	//<editor-fold desc="Crimson">
	public static final BlockContainer CRIMSON_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.DULL_PINK, ModBlockSoundGroups.NETHER_WOOD).flammable(30, 20).fuel(300);
	public static final BlockContainer CRIMSON_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer CRIMSON_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.DULL_PINK);
	public static final BlockContainer CRIMSON_BARREL = ModFactory.MakeBarrel(MapColor.DULL_PINK, ModBlockSoundGroups.NETHER_WOOD).fuel(300);
	public static final BlockContainer CRIMSON_LECTERN = ModFactory.MakeLectern(MapColor.DULL_PINK, ModBlockSoundGroups.NETHER_WOOD).flammable(30, 20).fuel(300);
	public static final WallBlockContainer CRIMSON_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.CRIMSON, MapColor.DULL_PINK, ModFactory.SignItemSettings(ItemGroup.DECORATIONS));
	//</editor-fold>
	//<editor-fold desc="Dark Oak">
	public static final Item DARK_OAK_CHEST_BOAT = ModFactory.MakeChestBoat(BoatEntity.Type.DARK_OAK, ModFactory.BoatSettings(ItemGroup.TRANSPORTATION));
	public static final WallBlockContainer DARK_OAK_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.DARK_OAK, MapColor.BROWN, ModFactory.SignItemSettings(ItemGroup.DECORATIONS));
	public static final BlockContainer DARK_OAK_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.BROWN).flammable(30, 20).fuel(300);
	public static final BlockContainer DARK_OAK_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer DARK_OAK_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.BROWN).fuel(300);
	public static final BlockContainer DARK_OAK_BARREL = ModFactory.MakeBarrel(MapColor.BROWN).fuel(300);
	public static final BlockContainer DARK_OAK_LECTERN = ModFactory.MakeLectern(MapColor.BROWN).flammable(30, 20).fuel(300);
	//</editor-fold>
	//<editor-fold desc="Deepslate">
	public static final BlockContainer REINFORCED_DEEPSLATE = new BlockContainer(new Block(Block.Settings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).sounds(BlockSoundGroup.DEEPSLATE).strength(55.0f, 1200.0f)), ItemSettings(ItemGroup.BUILDING_BLOCKS)).drops(DropTable.NOTHING);
	//</editor-fold>
	//<editor-fold desc="Diamond">
	public static final BlockContainer DIAMOND_STAIRS = ModFactory.MakeStairs(Blocks.DIAMOND_BLOCK);
	public static final BlockContainer DIAMOND_SLAB = ModFactory.MakeSlab(Blocks.DIAMOND_BLOCK);
	public static final BlockContainer DIAMOND_WALL = ModFactory.MakeWall(Blocks.DIAMOND_BLOCK);
	public static final BlockContainer DIAMOND_BRICKS = ModFactory.MakeBlock(Blocks.DIAMOND_BLOCK);
	public static final BlockContainer DIAMOND_BRICK_STAIRS = ModFactory.MakeStairs(DIAMOND_BRICKS);
	public static final BlockContainer DIAMOND_BRICK_SLAB = ModFactory.MakeSlab(DIAMOND_BRICKS);
	public static final BlockContainer DIAMOND_BRICK_WALL = ModFactory.MakeWall(DIAMOND_BRICKS);
	//</editor-fold>
	//<editor-fold desc="Diorite">
	public static final BlockContainer POLISHED_DIORITE_WALL = ModFactory.MakeWall(Blocks.POLISHED_DIORITE);
	//</editor-fold>
	//<editor-fold desc="Dripstone">
	public static final BlockContainer DRIPSTONE_STAIRS = ModFactory.MakeStairs(Blocks.DRIPSTONE_BLOCK);
	public static final BlockContainer DRIPSTONE_SLAB = ModFactory.MakeSlab(Blocks.DRIPSTONE_BLOCK);
	public static final BlockContainer DRIPSTONE_WALL = ModFactory.MakeWall(Blocks.DRIPSTONE_BLOCK);
	public static final BlockContainer SMOOTH_DRIPSTONE = ModFactory.MakeBlock(Blocks.DRIPSTONE_BLOCK);
	public static final BlockContainer SMOOTH_DRIPSTONE_STAIRS = ModFactory.MakeStairs(SMOOTH_DRIPSTONE);
	public static final BlockContainer SMOOTH_DRIPSTONE_SLAB = ModFactory.MakeSlab(SMOOTH_DRIPSTONE);
	public static final BlockContainer SMOOTH_DRIPSTONE_WALL = ModFactory.MakeWall(SMOOTH_DRIPSTONE);
	public static final BlockContainer DRIPSTONE_BRICKS = ModFactory.MakeBlock(Blocks.DRIPSTONE_BLOCK);
	public static final BlockContainer DRIPSTONE_BRICK_STAIRS = ModFactory.MakeStairs(DRIPSTONE_BRICKS);
	public static final BlockContainer DRIPSTONE_BRICK_SLAB = ModFactory.MakeSlab(DRIPSTONE_BRICKS);
	public static final BlockContainer DRIPSTONE_BRICK_WALL = ModFactory.MakeWall(DRIPSTONE_BRICKS);
	//</editor-fold>
	//<editor-fold desc="Echo">
	public static final Item ECHO_SHARD = new Item(ItemSettings(ItemGroup.MISC));
	//Extended
	public static final BlockContainer ECHO_BLOCK = new BlockContainer(new EchoBlock(Block.Settings.of(Material.SCULK).strength(1.5F).sounds(ModBlockSoundGroups.ECHO_BLOCK).requiresTool())).dropSelf();
	public static final BlockContainer ECHO_STAIRS = ModFactory.BuildStairs(new EchoStairsBlock(ECHO_BLOCK));
	public static final BlockContainer ECHO_SLAB = ModFactory.BuildSlab(new EchoSlabBlock(ECHO_BLOCK));
	public static final BlockContainer ECHO_WALL = ModFactory.BuildWall(new EchoWallBlock(ECHO_BLOCK));
	public static final BlockContainer ECHO_CRYSTAL_BLOCK = new BlockContainer(new Block(FabricBlockSettings.of(Material.SCULK).sounds(ModBlockSoundGroups.ECHO_CLUSTER).strength(1.5f, 1.5f).requiresTool().luminance(ModFactory.LUMINANCE_5))).dropSelf();
	public static final BlockContainer ECHO_CRYSTAL_STAIRS = ModFactory.BuildStairs(new EchoStairsBlock(ECHO_CRYSTAL_BLOCK));
	public static final BlockContainer ECHO_CRYSTAL_SLAB = ModFactory.BuildSlab(new EchoSlabBlock(ECHO_CRYSTAL_BLOCK));
	public static final BlockContainer ECHO_CRYSTAL_WALL = ModFactory.BuildWall(new EchoWallBlock(ECHO_CRYSTAL_BLOCK));
	public static final BlockContainer BUDDING_ECHO = new BlockContainer(new BuddingEchoBlock(Block.Settings.copy(ECHO_BLOCK.asBlock()).ticksRandomly())).drops(DropTable.NOTHING);
	public static final BlockContainer ECHO_CLUSTER = new BlockContainer(new EchoClusterBlock(7, 3, Block.Settings.of(Material.SCULK).nonOpaque().ticksRandomly().sounds(ModBlockSoundGroups.ECHO_CLUSTER).strength(1.5F).luminance(ModFactory.LUMINANCE_3)));
	public static final BlockContainer LARGE_ECHO_BUD = new BlockContainer(new EchoClusterBlock(5, 3, Block.Settings.copy(ECHO_CLUSTER.asBlock()).sounds(ModBlockSoundGroups.MEDIUM_ECHO_BUD).luminance(ModFactory.LUMINANCE_2))).requireSilkTouch();
	public static final BlockContainer MEDIUM_ECHO_BUD = new BlockContainer(new EchoClusterBlock(4, 3, Block.Settings.copy(ECHO_CLUSTER.asBlock()).sounds(ModBlockSoundGroups.LARGE_ECHO_BUD).luminance(ModFactory.LUMINANCE_1))).requireSilkTouch();
	public static final BlockContainer SMALL_ECHO_BUD = new BlockContainer(new EchoClusterBlock(3, 4, Block.Settings.copy(ECHO_CLUSTER.asBlock()).sounds(ModBlockSoundGroups.SMALL_ECHO_BUD))).requireSilkTouch();
	public static final Item ECHO_AXE = new EchoAxeItem(ModToolMaterials.ECHO, 5, -3, 1);
	public static final Item ECHO_HOE = new EchoHoeItem(ModToolMaterials.ECHO, -4, 0, 1);
	public static final Item ECHO_PICKAXE = new EchoPickaxeItem(ModToolMaterials.ECHO, 1, -2.8F, 1);
	public static final Item ECHO_SHOVEL = new EchoShovelItem(ModToolMaterials.ECHO, 1.5F, -3, 1);
	public static final Item ECHO_SWORD = new EchoSwordItem(ModToolMaterials.ECHO, 3, -2.4F, 1);
	public static final Item ECHO_KNIFE = new EchoKnifeItem(ModToolMaterials.ECHO, 1);
	//</editor-fold>
	//<editor-fold desc="Effects">
	public static final StatusEffect FLASHBANGED_EFFECT = new FlashbangedEffect();
	public static final StatusEffect BLEEDING_EFFECT = new BleedingEffect();
	public static final Enchantment SERRATED_ENCHANTMENT = new SerratedEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND);
	public static final StatusEffect TINTED_GOGGLES_EFFECT = new TintedGogglesEffect();
	//</editor-fold>
	//<editor-fold desc="Emerald">
	public static final BlockContainer EMERALD_BRICKS = ModFactory.MakeBlock(Blocks.EMERALD_BLOCK);
	public static final BlockContainer EMERALD_BRICK_STAIRS = ModFactory.MakeStairs(EMERALD_BRICKS);
	public static final BlockContainer EMERALD_BRICK_SLAB = ModFactory.MakeSlab(EMERALD_BRICKS);
	public static final BlockContainer EMERALD_BRICK_WALL = ModFactory.MakeWall(EMERALD_BRICKS);
	public static final BlockContainer CUT_EMERALD = ModFactory.MakeBlock(Blocks.EMERALD_BLOCK);
	public static final BlockContainer CUT_EMERALD_STAIRS = ModFactory.MakeStairs(CUT_EMERALD);
	public static final BlockContainer CUT_EMERALD_SLAB = ModFactory.MakeSlab(CUT_EMERALD);
	public static final BlockContainer CUT_EMERALD_WALL = ModFactory.MakeWall(CUT_EMERALD);
	public static final Item EMERALD_AXE = ModFactory.MakeAxe(ModToolMaterials.EMERALD);
	public static final Item EMERALD_HOE = ModFactory.MakeHoe(ModToolMaterials.EMERALD);
	public static final Item EMERALD_PICKAXE = ModFactory.MakePickaxe(ModToolMaterials.EMERALD);
	public static final Item EMERALD_SHOVEL = ModFactory.MakeShovel(ModToolMaterials.EMERALD);
	public static final Item EMERALD_SWORD = ModFactory.MakeSword(ModToolMaterials.EMERALD);
	public static final Item EMERALD_KNIFE = ModFactory.MakeKnife(ModToolMaterials.EMERALD);
	public static final Item EMERALD_HELMET = ModFactory.MakeHelmet(ModArmorMaterials.EMERALD);
	public static final Item EMERALD_CHESTPLATE = ModFactory.MakeChestplate(ModArmorMaterials.EMERALD);
	public static final Item EMERALD_LEGGINGS = ModFactory.MakeLeggings(ModArmorMaterials.EMERALD);
	public static final Item EMERALD_BOOTS = ModFactory.MakeBoots(ModArmorMaterials.EMERALD);
	public static final Item EMERALD_HORSE_ARMOR = ModFactory.MakeHorseArmor(ModArmorMaterials.EMERALD);
	//</editor-fold>
	//<editor-fold desc="Ender Fire">
	public static final DefaultParticleType ENDER_FIRE_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final DefaultParticleType SMALL_ENDER_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final BlockContainer ENDER_CANDLE = ModFactory.MakeCandle(MapColor.PALE_YELLOW, 2.75);
	public static final Block ENDER_CANDLE_CAKE = new ModCandleCakeBlock(3).drops(ENDER_CANDLE.asBlock());
	public static final TorchContainer ENDER_TORCH = ModFactory.MakeTorch(12, BlockSoundGroup.WOOD, ENDER_FIRE_FLAME_PARTICLE);
	public static final BlockContainer ENDER_LANTERN = ModFactory.MakeLantern(13);
	public static final BlockContainer ENDER_CAMPFIRE = ModFactory.MakeCampfire(13, 3, MapColor.SPRUCE_BROWN, BlockSoundGroup.WOOD, false).drops(DropTable.ENDER_CAMPFIRE);
	//</editor-fold>
	//<editor-fold desc="Feathers">
	public static final Item FANCY_FEATHER = new Item(ItemSettings());
	public static final Item BLACK_FEATHER = new Item(ItemSettings());
	public static final Item RED_FEATHER = new Item(ItemSettings());
	//</editor-fold>
	//<editor-fold desc="Flowers">
	//Minecraft Earth Flowers
	public static final FlowerContainer BUTTERCUP = ModFactory.MakeFlower(StatusEffects.BLINDNESS, 11);
	public static final FlowerContainer PINK_DAISY = ModFactory.MakeFlower(StatusEffects.REGENERATION, 8);
	//Other Flowers
	public static final FlowerContainer ROSE = new FlowerContainer(new GrowableFlowerBlock(StatusEffects.WEAKNESS, 9, FlowerContainer.Settings(), () -> (TallFlowerBlock)Blocks.ROSE_BUSH)).flammable(60, 100).compostable(0.65f).dropSelf();
	private static TallFlowerBlock getBlueRoseBush() { return (TallFlowerBlock) BLUE_ROSE_BUSH.asBlock(); }
	public static final FlowerContainer BLUE_ROSE = new FlowerContainer(new GrowableFlowerBlock(StatusEffects.WEAKNESS, 9, FlowerContainer.Settings(), ModBase::getBlueRoseBush)).flammable(60, 100).compostable(0.65f).dropSelf();
	public static final FlowerContainer MAGENTA_TULIP = ModFactory.MakeFlower(StatusEffects.FIRE_RESISTANCE, 4);
	public static final FlowerContainer MARIGOLD = ModFactory.MakeFlower(StatusEffects.WITHER, 5);
	public static final FlowerContainer INDIGO_ORCHID = ModFactory.MakeFlower(StatusEffects.SATURATION, 7);
	public static final FlowerContainer MAGENTA_ORCHID = ModFactory.MakeFlower(StatusEffects.SATURATION, 7);
	public static final FlowerContainer ORANGE_ORCHID = ModFactory.MakeFlower(StatusEffects.SATURATION, 7);
	public static final FlowerContainer PURPLE_ORCHID = ModFactory.MakeFlower(StatusEffects.SATURATION, 7);
	public static final FlowerContainer RED_ORCHID = ModFactory.MakeFlower(StatusEffects.SATURATION, 7);
	public static final FlowerContainer WHITE_ORCHID = ModFactory.MakeFlower(StatusEffects.SATURATION, 7);
	public static final FlowerContainer YELLOW_ORCHID = ModFactory.MakeFlower(StatusEffects.SATURATION, 7);
	private static TallFlowerBlock getTallPinkAlliumBlock() { return (TallFlowerBlock) TALL_PINK_ALLIUM.asBlock(); }
	public static final FlowerContainer PINK_ALLIUM = new FlowerContainer(new GrowableFlowerBlock(StatusEffects.FIRE_RESISTANCE, 4, FlowerContainer.Settings(), ModBase::getTallPinkAlliumBlock)).flammable(60, 100).compostable(0.65f).dropSelf();
	public static final FlowerContainer LAVENDER = ModFactory.MakeFlower(StatusEffects.INVISIBILITY, 8);
	public static final FlowerContainer HYDRANGEA = ModFactory.MakeFlower(StatusEffects.JUMP_BOOST, 7);
	public static final FlowerContainer PAEONIA = ModFactory.MakeFlower(StatusEffects.STRENGTH, 6);
	public static final FlowerContainer ASTER = ModFactory.MakeFlower(StatusEffects.INSTANT_DAMAGE, 1);
	public static final TallBlockContainer AMARANTH = ModFactory.MakeTallFlower();
	public static final TallBlockContainer BLUE_ROSE_BUSH = ModFactory.MakeCuttableFlower(() -> (FlowerBlock)BLUE_ROSE.asBlock());
	public static final TallBlockContainer TALL_ALLIUM = ModFactory.MakeCuttableFlower(() -> (FlowerBlock)Blocks.ALLIUM);
	public static final TallBlockContainer TALL_PINK_ALLIUM = ModFactory.MakeCuttableFlower(() -> (FlowerBlock) PINK_ALLIUM.asBlock());
	//</editor-fold>
	//<editor-fold desc="Frogs">
	//TODO: Frogs throw up jazz hands after jumping. They don't croak. What that tongue [not] do.
	public static final BlockContainer OCHRE_FROGLIGHT = ModFactory.MakeFroglight(MapColor.PALE_YELLOW, ItemSettings(ItemGroup.DECORATIONS));
	public static final BlockContainer VERDANT_FROGLIGHT = ModFactory.MakeFroglight(MapColor.LICHEN_GREEN, ItemSettings(ItemGroup.DECORATIONS));
	public static final BlockContainer PEARLESCENT_FROGLIGHT = ModFactory.MakeFroglight(MapColor.PINK, ItemSettings(ItemGroup.DECORATIONS));

	public static final ModSensorType<TemptationsSensor> FROG_TEMPTATIONS_SENSOR = new ModSensorType<>("frog_temptations", () -> new TemptationsSensor(FrogBrain.getTemptItems()));
	public static final ModSensorType<FrogAttackablesSensor> FROG_ATTACKABLES_SENSOR = new ModSensorType<>("frog_attackables", FrogAttackablesSensor::new);
	public static final ModSensorType<IsInWaterSensor> IS_IN_WATER_SENSOR = new ModSensorType<>("is_in_water", IsInWaterSensor::new);
	public static final EntityType<FrogEntity> FROG_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FrogEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(10).build();
	public static final Item FROG_SPAWN_EGG = new SpawnEggItem(FROG_ENTITY, 13661252, 0xFFC77C, ItemSettings(ItemGroup.MISC));
	private static final Block FROGSPAWN_BLOCK = new FrogspawnBlock(Block.Settings.of(ModFactory.FROGSPAWN_MATERIAL).breakInstantly().nonOpaque().noCollision().sounds(ModBlockSoundGroups.FROGSPAWN));
	public static final BlockContainer FROGSPAWN = new BlockContainer(FROGSPAWN_BLOCK, new LilyPadItem(FROGSPAWN_BLOCK, ItemSettings(ItemGroup.MISC)));
	public static final EntityType<TadpoleEntity> TADPOLE_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TadpoleEntity::new).dimensions(EntityDimensions.fixed(TadpoleEntity.WIDTH, TadpoleEntity.HEIGHT)).trackRangeChunks(10).build();
	public static final Item TADPOLE_BUCKET = new EntityBucketItem(TADPOLE_ENTITY, Fluids.WATER, ModSoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, ItemSettings(ItemGroup.MISC).maxCount(1));
	public static final Item TADPOLE_SPAWN_EGG = new SpawnEggItem(TADPOLE_ENTITY, 7164733, 1444352, ItemSettings(ItemGroup.MISC));
	//</editor-fold>
	//<editor-fold desc="Gilded Blackstone">
	public static final BlockContainer GILDED_BLACKSTONE_STAIRS = ModFactory.MakeStairs(Blocks.GILDED_BLACKSTONE);
	public static final BlockContainer GILDED_BLACKSTONE_SLAB = ModFactory.MakeSlab(Blocks.GILDED_BLACKSTONE);
	public static final BlockContainer GILDED_BLACKSTONE_WALL = ModFactory.MakeWall(Blocks.GILDED_BLACKSTONE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE = ModFactory.MakeBlock(Blocks.GILDED_BLACKSTONE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_STAIRS = ModFactory.MakeStairs(POLISHED_GILDED_BLACKSTONE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_SLAB = ModFactory.MakeSlab(POLISHED_GILDED_BLACKSTONE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_WALL = ModFactory.MakeWall(POLISHED_GILDED_BLACKSTONE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_BRICKS = ModFactory.MakeBlock(Blocks.GILDED_BLACKSTONE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS = ModFactory.MakeStairs(POLISHED_GILDED_BLACKSTONE_BRICKS);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_BRICK_SLAB = ModFactory.MakeSlab(POLISHED_GILDED_BLACKSTONE_BRICKS);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_BRICK_WALL = ModFactory.MakeWall(POLISHED_GILDED_BLACKSTONE_BRICKS);
	public static final BlockContainer CHISELED_POLISHED_GILDED_BLACKSTONE = ModFactory.MakeBlock(Blocks.GILDED_BLACKSTONE);
	public static final BlockContainer CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS = ModFactory.MakeBlock(Blocks.GILDED_BLACKSTONE);
	//</editor-fold>
	//<editor-fold desc="Glass">
	public static final BlockContainer TINTED_GLASS_PANE = new BlockContainer(new TintedGlassPaneBlock(Block.Settings.of(Material.GLASS).mapColor(MapColor.GRAY).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque())).dropSelf();
	public static final BlockContainer TINTED_GLASS_SLAB = new BlockContainer(new TintedGlassSlabBlock(Blocks.TINTED_GLASS)).dropSlabs();
	public static final BlockContainer TINTED_GLASS_TRAPDOOR = new BlockContainer(new TintedGlassTrapdoorBlock(Blocks.TINTED_GLASS, TINTED_GLASS_SLAB.asBlock())).dropSelf();
	public static final Item TINTED_GOGGLES = new ModArmorItem(ModArmorMaterials.TINTED_GOGGLES, EquipmentSlot.HEAD);
	public static final BlockContainer GLASS_SLAB = new BlockContainer(new GlassSlabBlock(Blocks.GLASS));
	public static final BlockContainer GLASS_TRAPDOOR = new BlockContainer(new GlassTrapdoorBlock(Blocks.GLASS, GLASS_SLAB.asBlock()));
	public static final Map<DyeColor, BlockContainer> STAINED_GLASS_SLABS = ColorUtil.Map(color -> new BlockContainer(new StainedGlassSlabBlock(color, ColorUtil.GetStainedGlassBlock(color))));
	public static final Map<DyeColor, BlockContainer> STAINED_GLASS_TRAPDOORS = ColorUtil.Map(color -> new BlockContainer(new StainedGlassTrapdoorBlock(color, STAINED_GLASS_SLABS.get(color).asBlock(), ColorUtil.GetStainedGlassBlock(color))));
	//</editor-fold>
	//<editor-fold desc="Glow Lichen">
	public static final BlockContainer GLOW_LICHEN_BLOCK = ModFactory.BuildBlock(new GlowLichenFullBlock(Block.Settings.of(Material.MOSS_BLOCK, MapColor.LICHEN_GREEN).strength(0.1f).sounds(BlockSoundGroup.GLOW_LICHEN).luminance(ModFactory.LUMINANCE_7))).compostable(0.65f);
	public static final BlockContainer GLOW_LICHEN_SLAB = ModFactory.BuildSlab(new GlowLichenSlabBlock(GLOW_LICHEN_BLOCK)).compostable(0.325f);
	public static final BlockContainer GLOW_LICHEN_CARPET = ModFactory.BuildBlock(new CarpetBlock(Block.Settings.copy(Blocks.MOSS_CARPET).mapColor(MapColor.LICHEN_GREEN).luminance(ModFactory.LUMINANCE_7))).compostable(0.3f);
	//public static final BedContainer GLOW_LICHEN_BED = ModFactory.MakeBed("glow_lichen", MapColor.LICHEN_GREEN, BlockSoundGroup.GOW_LICHEN, ModFactory.LUMINANCE_7);
	//</editor-fold>
	//<editor-fold desc="Goat">
	public static final LootFunctionType SET_INSTRUMENT_LOOT_FUNCTION = new LootFunctionType(new SetInstrumentLootFunction.Serializer());
	public static final Item GOAT_HORN = new GoatHornItem(ItemSettings(ItemGroup.MISC).maxCount(1));
	//Extended
	public static final Item CHEVON = new Item(ItemSettings().food(FoodComponents.MUTTON));
	public static final Item COOKED_CHEVON = new Item(ItemSettings().food(FoodComponents.COOKED_MUTTON));
	public static final Map<DyeColor, BlockContainer> FLEECE = ColorUtil.Map((color) -> ModFactory.MakeBlock(ColorUtil.GetWoolBlock(color)).flammable(30, 60).fuel(100).dropSelf());
	public static final Map<DyeColor, BlockContainer> FLEECE_SLABS = ColorUtil.Map((color) -> ModFactory.MakeSlab(FLEECE.get(color)).flammable(40, 40).fuel(50).dropSlabs());
	public static final Map<DyeColor, BlockContainer> FLEECE_CARPETS = ColorUtil.Map((color) -> new BlockContainer(new ModDyedCarpetBlock(color, Block.Settings.copy(ColorUtil.GetWoolCarpetBlock(color)))).flammable(60, 20).fuel(67).dispenser(new HorseArmorDispenserBehavior()::dispenseSilently).dropSelf());
	public static final BlockContainer RAINBOW_FLEECE = new BlockContainer(new ModFacingBlock(Blocks.WHITE_WOOL)).flammable(30, 60).fuel(100).dropSelf();
	public static final BlockContainer RAINBOW_FLEECE_SLAB = new BlockContainer(new HorizontalFacingSlabBlock(RAINBOW_FLEECE.asBlock())).flammable(40, 40).fuel(50).dropSlabs();
	public static final BlockContainer RAINBOW_FLEECE_CARPET = new BlockContainer(new HorziontalFacingCarpetBlock(RAINBOW_FLEECE.asBlock())).flammable(60, 20).fuel(67).dropSelf();
	//public static final BedContainer RAINBOW_BED = ModFactory.MakeBed("rainbow");
	public static final Item FLEECE_HELMET = new ModDyeableArmorItem(ModArmorMaterials.FLEECE, EquipmentSlot.HEAD);
	public static final Item FLEECE_CHESTPLATE = new ModDyeableArmorItem(ModArmorMaterials.FLEECE, EquipmentSlot.CHEST);
	public static final Item FLEECE_LEGGINGS = new ModDyeableArmorItem(ModArmorMaterials.FLEECE, EquipmentSlot.LEGS);
	public static final Item FLEECE_BOOTS = new ModDyeableArmorItem(ModArmorMaterials.FLEECE, EquipmentSlot.FEET);
	public static final Item FLEECE_HORSE_ARMOR = HorseArmorDispenserBehavior.Dispensible(new DyeableHorseArmorItem(3, "fleece", ItemSettings().maxCount(1)));
	public static final Item GOAT_HORN_SALVE = new GoatHornSalveItem(ItemSettings());
	//</editor-fold>
	//<editor-fold desc="Gold">
	public static final DefaultParticleType GOLD_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final TorchContainer GOLD_TORCH = ModFactory.MakeTorch(14, BlockSoundGroup.METAL, GOLD_FLAME_PARTICLE);
	public static final TorchContainer GOLD_SOUL_TORCH = ModFactory.MakeSoulTorch(BlockSoundGroup.METAL);
	public static final TorchContainer GOLD_ENDER_TORCH = ModFactory.MakeEnderTorch(BlockSoundGroup.METAL);
	public static final TorchContainer UNDERWATER_GOLD_TORCH = ModFactory.MakeUnderwaterTorch(BlockSoundGroup.METAL);
	public static final BlockContainer GOLD_LANTERN = ModFactory.MakeLantern(15);
	public static final BlockContainer GOLD_SOUL_LANTERN = ModFactory.MakeLantern(10);
	public static final BlockContainer GOLD_ENDER_LANTERN = ModFactory.MakeLantern(13);
	public static final BlockContainer GOLD_BUTTON = ModFactory.MakeMetalButton();
	public static final BlockContainer GOLD_CHAIN = ModFactory.MakeChain();
	public static final BlockContainer GOLD_BARS = ModFactory.MakeBars();
	public static final BlockContainer GOLD_STAIRS = ModFactory.MakeStairs(Blocks.GOLD_BLOCK);
	public static final BlockContainer GOLD_SLAB = ModFactory.MakeSlab(Blocks.GOLD_BLOCK);
	public static final BlockContainer GOLD_WALL = ModFactory.MakeWall(Blocks.GOLD_BLOCK);
	public static final BlockContainer GOLD_BRICKS = new BlockContainer(new Block(Block.Settings.copy(Blocks.GOLD_BLOCK))).dropSelf();
	public static final BlockContainer GOLD_BRICK_STAIRS = ModFactory.MakeStairs(GOLD_BRICKS);
	public static final BlockContainer GOLD_BRICK_SLAB = ModFactory.MakeSlab(GOLD_BRICKS);
	public static final BlockContainer GOLD_BRICK_WALL = ModFactory.MakeWall(GOLD_BRICKS);
	public static final BlockContainer CUT_GOLD = new BlockContainer(new Block(Block.Settings.copy(Blocks.GOLD_BLOCK))).dropSelf();
	public static final BlockContainer CUT_GOLD_PILLAR = new BlockContainer(new ModPillarBlock(CUT_GOLD)).dropSelf();
	public static final BlockContainer CUT_GOLD_STAIRS = ModFactory.MakeStairs(CUT_GOLD);
	public static final BlockContainer CUT_GOLD_SLAB = ModFactory.MakeSlab(CUT_GOLD);
	public static final BlockContainer CUT_GOLD_WALL = ModFactory.MakeWall(CUT_GOLD);
	//</editor-fold>
	//<editor-fold desc="Granite">
	public static final BlockContainer POLISHED_GRANITE_WALL = ModFactory.MakeWall(Blocks.POLISHED_GRANITE);
	//</editor-fold>
	//<editor-fold desc="Hedgehog">
	public static final EntityType<HedgehogEntity> HEDGEHOG_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HedgehogEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.45F)).trackRangeChunks(8).build();
	public static final Item HEDGEHOG_SPAWN_EGG = new SpawnEggItem(HEDGEHOG_ENTITY, 11440263, 7558239, ItemSettings());
	//</editor-fold>
	//<editor-fold desc="Iron">
	public static final DefaultParticleType IRON_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final TorchContainer IRON_TORCH = ModFactory.MakeTorch(14, BlockSoundGroup.METAL, IRON_FLAME_PARTICLE);
	public static final TorchContainer IRON_SOUL_TORCH = ModFactory.MakeSoulTorch(BlockSoundGroup.METAL);
	public static final TorchContainer IRON_ENDER_TORCH = ModFactory.MakeEnderTorch(BlockSoundGroup.METAL);
	public static final TorchContainer UNDERWATER_IRON_TORCH = ModFactory.MakeUnderwaterTorch(BlockSoundGroup.METAL);
	public static final BlockContainer WHITE_IRON_LANTERN = ModFactory.MakeLantern(15);
	public static final BlockContainer WHITE_IRON_SOUL_LANTERN = ModFactory.MakeLantern(10);
	public static final BlockContainer WHITE_IRON_ENDER_LANTERN = ModFactory.MakeLantern(13);
	public static final BlockContainer IRON_BUTTON = ModFactory.MakeMetalButton();
	public static final BlockContainer WHITE_IRON_CHAIN = ModFactory.MakeChain();
	public static final BlockContainer IRON_STAIRS = ModFactory.MakeStairs(Blocks.IRON_BLOCK);
	public static final BlockContainer IRON_SLAB = ModFactory.MakeSlab(Blocks.IRON_BLOCK);
	public static final BlockContainer IRON_WALL = ModFactory.MakeWall(Blocks.IRON_BLOCK);
	public static final BlockContainer IRON_BRICKS = new BlockContainer(new Block(Block.Settings.copy(Blocks.IRON_BLOCK))).dropSelf();
	public static final BlockContainer IRON_BRICK_STAIRS = ModFactory.MakeStairs(IRON_BRICKS);
	public static final BlockContainer IRON_BRICK_SLAB = ModFactory.MakeSlab(IRON_BRICKS);
	public static final BlockContainer IRON_BRICK_WALL = ModFactory.MakeWall(IRON_BRICKS);
	public static final BlockContainer CUT_IRON = new BlockContainer(new Block(Block.Settings.copy(Blocks.IRON_BLOCK))).dropSelf();
	public static final BlockContainer CUT_IRON_PILLAR = new BlockContainer(new ModPillarBlock(CUT_IRON)).dropSelf();
	public static final BlockContainer CUT_IRON_STAIRS = ModFactory.MakeStairs(CUT_IRON);
	public static final BlockContainer CUT_IRON_SLAB = ModFactory.MakeSlab(CUT_IRON);
	public static final BlockContainer CUT_IRON_WALL = ModFactory.MakeWall(CUT_IRON);
	//</editor-fold>
	//<editor-fold desc="Jumping Spider">
	public static final EntityType<JumpingSpiderEntity> JUMPING_SPIDER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, JumpingSpiderEntity::new).dimensions(EntityDimensions.fixed(0.7f, 0.5f)).trackRangeChunks(8).build();
	public static final Item JUMPING_SPIDER_SPAWN_EGG = new SpawnEggItem(JUMPING_SPIDER_ENTITY, 0x281206, 0x3C0202, ItemSettings());
	//</editor-fold>
	//<editor-fold desc="Jungle">
	public static final Item JUNGLE_CHEST_BOAT = ModFactory.MakeChestBoat(BoatEntity.Type.JUNGLE, ModFactory.BoatSettings(ItemGroup.TRANSPORTATION));
	public static final WallBlockContainer JUNGLE_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.JUNGLE, MapColor.SPRUCE_BROWN, ModFactory.SignItemSettings(ItemGroup.DECORATIONS));
	public static final BlockContainer JUNGLE_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.DIRT_BROWN).flammable(30, 20).fuel(300);
	public static final BlockContainer JUNGLE_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer JUNGLE_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.DIRT_BROWN);
	public static final BlockContainer JUNGLE_BARREL = ModFactory.MakeBarrel(MapColor.DIRT_BROWN).fuel(300);
	public static final BlockContainer JUNGLE_LECTERN = ModFactory.MakeLectern(MapColor.DIRT_BROWN).flammable(30, 20).fuel(300);
	//</editor-fold>
	//<editor-fold desc="Mangrove">
	public static final BlockContainer MANGROVE_LOG = ModFactory.MakeLog(MapColor.RED, MapColor.SPRUCE_BROWN, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300);
	public static final BlockContainer STRIPPED_MANGROVE_LOG = ModFactory.MakeLog(MapColor.RED, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300);
	public static final BlockContainer MANGROVE_WOOD = ModFactory.MakeWood(MapColor.SPRUCE_BROWN, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300);
	public static final BlockContainer STRIPPED_MANGROVE_WOOD = ModFactory.MakeWood(MapColor.RED, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300);
	public static final BlockContainer MANGROVE_LEAVES = new BlockContainer(new MangroveLeavesBlock(ModFactory.LeafBlockSettings()), ItemSettings(ItemGroup.DECORATIONS)).flammable(30, 60).compostable(0.3f);
	public static final BlockContainer MANGROVE_PLANKS = ModFactory.MakePlanks(MapColor.RED, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300);
	public static final BlockContainer MANGROVE_STAIRS = ModFactory.MakeStairs(MANGROVE_PLANKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300);
	public static final BlockContainer MANGROVE_SLAB = ModFactory.MakeSlab(MANGROVE_PLANKS, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(150);
	public static final BlockContainer MANGROVE_FENCE = ModFactory.MakeWoodFence(MANGROVE_PLANKS, ItemSettings(ItemGroup.DECORATIONS)).flammable(5, 20).fuel(300);
	public static final BlockContainer MANGROVE_FENCE_GATE = ModFactory.MakeWoodFenceGate(MANGROVE_PLANKS, ItemSettings(ItemGroup.REDSTONE)).flammable(5, 20).fuel(300);
	public static final BlockContainer MANGROVE_DOOR = ModFactory.MakeWoodDoor(MANGROVE_PLANKS, ItemSettings(ItemGroup.REDSTONE)).fuel(200);
	public static final BlockContainer MANGROVE_TRAPDOOR = ModFactory.MakeWoodTrapdoor(MANGROVE_PLANKS, ItemSettings(ItemGroup.REDSTONE)).fuel(300);
	public static final BlockContainer MANGROVE_PRESSURE_PLATE = ModFactory.MakeWoodPressurePlate(MANGROVE_PLANKS, ItemSettings(ItemGroup.REDSTONE)).fuel(300);
	public static final BlockContainer MANGROVE_BUTTON = ModFactory.MakeWoodButton(ItemSettings(ItemGroup.REDSTONE)).fuel(100);
	public static final BlockContainer MANGROVE_ROOTS = new BlockContainer(new MangroveRootsBlock(Block.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(0.7f).ticksRandomly().sounds(ModBlockSoundGroups.MANGROVE_ROOTS).nonOpaque().suffocates(ModFactory::never).blockVision(ModFactory::never).nonOpaque()), ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300).dropSelf();
	public static final BlockContainer MUDDY_MANGROVE_ROOTS = new BlockContainer(new ModPillarBlock(Block.Settings.of(Material.SOIL, MapColor.SPRUCE_BROWN).strength(0.7f).sounds(ModBlockSoundGroups.MUDDY_MANGROVE_ROOTS)), ItemSettings(ItemGroup.BUILDING_BLOCKS)).dropSelf();
	public static final SignContainer MANGROVE_SIGN = ModFactory.MakeWoodSign("minecraft:mangrove", ModFactory.SignItemSettings(ItemGroup.DECORATIONS), MapColor.RED).fuel(200);
	public static final BoatContainer MANGROVE_BOAT = ModFactory.MakeBoat("minecraft:mangrove", MANGROVE_PLANKS, ModFactory.BoatSettings(ItemGroup.TRANSPORTATION)).fuel(1200);
	public static final PottedBlockContainer MANGROVE_PROPAGULE = new PottedBlockContainer(new PropaguleBlock(Block.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)), ItemSettings(ItemGroup.DECORATIONS)).compostable(0.3f); //Drop table omitted intentionally
	//Extended
	public static final BlockContainer MANGROVE_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.RED).flammable(30, 20).fuel(300);
	public static final BlockContainer MANGROVE_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer MANGROVE_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.RED);
	public static final BlockContainer MANGROVE_BARREL = ModFactory.MakeBarrel(MapColor.RED).fuel(300);
	public static final BlockContainer MANGROVE_LECTERN = ModFactory.MakeLectern(MapColor.RED).flammable(30, 20).fuel(300);
	//</editor-fold>
	//<editor-fold desc="Moss">
	public static final BlockContainer MOSS_SLAB = ModFactory.BuildSlab(new MossSlabBlock(Blocks.MOSS_BLOCK)).compostable(0.325f);
	//public static final BedContainer MOSS_BED = ModFactory.MakeBed("moss", MapColor.GREEN, BlockSoundGroup.MOSS);
	//</editor-fold>
	//<editor-fold desc="Mud">
	public static final BlockContainer MUD = new BlockContainer(new MudBlock(Block.Settings.copy(Blocks.DIRT).mapColor(MapColor.TERRACOTTA_CYAN).allowsSpawning(ModFactory::always).solidBlock(ModFactory::always).blockVision(ModFactory::always).suffocates(ModFactory::always).sounds(ModBlockSoundGroups.MUD)), ItemSettings(ItemGroup.BUILDING_BLOCKS)).dropSelf();
	public static final BlockContainer PACKED_MUD = ModFactory.MakeBlock(Block.Settings.copy(Blocks.DIRT).strength(1.0f, 3.0f).sounds(ModBlockSoundGroups.PACKED_MUD), ItemSettings(ItemGroup.BUILDING_BLOCKS));
	public static final BlockContainer MUD_BRICKS = ModFactory.MakeBlock(Block.Settings.of(Material.STONE, MapColor.TERRACOTTA_LIGHT_GRAY).requiresTool().strength(1.5f, 3.0f).sounds(ModBlockSoundGroups.MUD_BRICKS), ItemSettings(ItemGroup.BUILDING_BLOCKS));
	public static final BlockContainer MUD_BRICK_STAIRS = ModFactory.MakeStairs(MUD_BRICKS, ItemSettings(ItemGroup.BUILDING_BLOCKS));
	public static final BlockContainer MUD_BRICK_SLAB = ModFactory.MakeSlab(MUD_BRICKS, ItemSettings(ItemGroup.BUILDING_BLOCKS));
	public static final BlockContainer MUD_BRICK_WALL = ModFactory.MakeWall(MUD_BRICKS, ItemSettings(ItemGroup.BUILDING_BLOCKS));
	//</editor-fold>
	//<editor-fold desc="Music Discs">
	public static final Item MUSIC_DISC_5 = new ModMusicDiscItem(15, ModSoundEvents.MUSIC_DISC_5, ItemSettings(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
	public static final Item DISC_FRAGMENT_5 = new DiscFragmentItem(ItemSettings(ItemGroup.MISC));
	//</editor-fold>
	//<editor-fold desc="Netherite">
	public static final DefaultParticleType NETHERITE_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final Item NETHERITE_NUGGET = new Item(NetheriteItemSettings());
	public static final TorchContainer NETHERITE_TORCH = ModFactory.MakeTorch(14, BlockSoundGroup.NETHERITE, NETHERITE_FLAME_PARTICLE, NetheriteItemSettings());
	public static final TorchContainer NETHERITE_SOUL_TORCH = ModFactory.MakeSoulTorch(BlockSoundGroup.NETHERITE, NetheriteItemSettings());
	public static final TorchContainer NETHERITE_ENDER_TORCH = ModFactory.MakeEnderTorch(BlockSoundGroup.NETHERITE, NetheriteItemSettings());
	public static final TorchContainer UNDERWATER_NETHERITE_TORCH = ModFactory.MakeUnderwaterTorch(BlockSoundGroup.NETHERITE, NetheriteItemSettings());
	public static final BlockContainer NETHERITE_LANTERN = ModFactory.MakeLantern(15, NetheriteItemSettings());
	public static final BlockContainer NETHERITE_SOUL_LANTERN = ModFactory.MakeLantern(10, NetheriteItemSettings());
	public static final BlockContainer NETHERITE_ENDER_LANTERN = ModFactory.MakeLantern(13, NetheriteItemSettings());
	public static final BlockContainer NETHERITE_BUTTON = ModFactory.MakeMetalButton(BlockSoundGroup.NETHERITE, NetheriteItemSettings());
	public static final BlockContainer NETHERITE_CHAIN = ModFactory.MakeChain(NetheriteItemSettings());
	public static final BlockContainer NETHERITE_BARS = ModFactory.MakeBars(BlockSoundGroup.NETHERITE, NetheriteItemSettings());
	public static final BlockContainer NETHERITE_STAIRS = ModFactory.MakeStairs(Blocks.NETHERITE_BLOCK, NetheriteItemSettings());
	public static final BlockContainer NETHERITE_SLAB = ModFactory.MakeSlab(Blocks.NETHERITE_BLOCK, NetheriteItemSettings());
	public static final BlockContainer NETHERITE_WALL = ModFactory.MakeWall(Blocks.NETHERITE_BLOCK, NetheriteItemSettings());
	public static final BlockContainer NETHERITE_BRICKS = ModFactory.MakeBlock(Blocks.NETHERITE_BLOCK, NetheriteItemSettings());
	public static final BlockContainer NETHERITE_BRICK_STAIRS = ModFactory.MakeStairs(NETHERITE_BRICKS, NetheriteItemSettings());
	public static final BlockContainer NETHERITE_BRICK_SLAB = ModFactory.MakeSlab(NETHERITE_BRICKS, NetheriteItemSettings());
	public static final BlockContainer NETHERITE_BRICK_WALL = ModFactory.MakeWall(NETHERITE_BRICKS, NetheriteItemSettings());
	public static final BlockContainer CUT_NETHERITE = ModFactory.MakeBlock(Blocks.NETHERITE_BLOCK, NetheriteItemSettings());
	public static final BlockContainer CUT_NETHERITE_PILLAR = ModFactory.BuildBlock(new ModPillarBlock(Blocks.NETHERITE_BLOCK), NetheriteItemSettings());
	public static final BlockContainer CUT_NETHERITE_STAIRS = ModFactory.MakeStairs(CUT_NETHERITE, NetheriteItemSettings());
	public static final BlockContainer CUT_NETHERITE_SLAB = ModFactory.MakeSlab(CUT_NETHERITE, NetheriteItemSettings());
	public static final BlockContainer CUT_NETHERITE_WALL = ModFactory.MakeWall(CUT_NETHERITE, NetheriteItemSettings());
	//TODO: Make netherite fireproof. Oops lmao
	public static final Item NETHERITE_HORSE_ARMOR = new ModHorseArmorItem(15, "netherite", NetheriteItemSettings().maxCount(1)).dispenseSilently();
	public static final BlockContainer CRUSHING_WEIGHTED_PRESSURE_PLATE = new BlockContainer(new ModWeightedPressurePlateBlock(300, Block.Settings.of(Material.METAL).requiresTool().noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)), NetheriteItemSettings()).dropSelf();
	//</editor-fold>
	//<editor-fold desc="Oak">
	public static final Item OAK_CHEST_BOAT = ModFactory.MakeChestBoat(BoatEntity.Type.OAK, ModFactory.BoatSettings(ItemGroup.TRANSPORTATION));
	public static final WallBlockContainer OAK_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.OAK, MapColor.SPRUCE_BROWN, ModFactory.SignItemSettings(ItemGroup.DECORATIONS));
	public static final BlockContainer WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.OAK_TAN);
	public static final BlockContainer OAK_BARREL = ModFactory.MakeBarrel(MapColor.OAK_TAN).fuel(300);
	//</editor-fold>
	//<editor-fold desc="Obsidian">
	public static final BlockContainer OBSIDIAN_STAIRS = ModFactory.MakeStairs(Blocks.OBSIDIAN);
	public static final BlockContainer OBSIDIAN_SLAB = ModFactory.MakeSlab(Blocks.OBSIDIAN);
	public static final BlockContainer OBSIDIAN_WALL = ModFactory.MakeWall(Blocks.OBSIDIAN);
	/*
	public static final Item OBSIDIAN_AXE = ModFactory.MakeAxe(ModToolMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_HOE = ModFactory.MakeHoe(ModToolMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_PICKAXE = ModFactory.MakePickaxe(ModToolMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_SHOVEL = ModFactory.MakeShovel(ModToolMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_SWORD = ModFactory.MakeSword(ModToolMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_KNIFE = ModFactory.MakeKnife(ModToolMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_HELMET = ModFactory.MakeHelmet(ModArmorMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_CHESTPLATE = ModFactory.MakeChestplate(ModArmorMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_LEGGINGS = ModFactory.MakeLeggings(ModArmorMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_BOOTS = ModFactory.MakeBoots(ModArmorMaterials.OBSIDIAN);
	public static final Item OBSIDIAN_HORSE_ARMOR = ModFactory.MakeHorseArmor(ModArmorMaterials.OBSIDIAN);
	*/
	public static final BlockContainer CRYING_OBSIDIAN_STAIRS = new BlockContainer(new CryingObsidianStairsBlock(Blocks.OBSIDIAN, ParticleTypes.DRIPPING_OBSIDIAN_TEAR)).dropSelf();
	public static final BlockContainer CRYING_OBSIDIAN_SLAB = new BlockContainer(new CryingObsidianSlabBlock(Blocks.OBSIDIAN, ParticleTypes.DRIPPING_OBSIDIAN_TEAR)).dropSlabs();
	public static final BlockContainer CRYING_OBSIDIAN_WALL = new BlockContainer(new CryingObsidianWallBlock(Blocks.OBSIDIAN, ParticleTypes.DRIPPING_OBSIDIAN_TEAR)).dropSelf();
	public static final DefaultParticleType LANDING_OBSIDIAN_BLOOD = FabricParticleTypes.simple(false);
	public static final DefaultParticleType FALLING_OBSIDIAN_BLOOD = FabricParticleTypes.simple(false);
	public static final DefaultParticleType DRIPPING_OBSIDIAN_BLOOD = FabricParticleTypes.simple(false);
	public static final BlockContainer BLEEDING_OBSIDIAN = ModFactory.BuildBlock(new BleedingObsidianBlock(Blocks.CRYING_OBSIDIAN));
	public static final BlockContainer BLEEDING_OBSIDIAN_STAIRS = new BlockContainer(new CryingObsidianStairsBlock(Blocks.OBSIDIAN, DRIPPING_OBSIDIAN_BLOOD)).dropSelf();
	public static final BlockContainer BLEEDING_OBSIDIAN_SLAB = new BlockContainer(new CryingObsidianSlabBlock(Blocks.OBSIDIAN, DRIPPING_OBSIDIAN_BLOOD)).dropSlabs();
	public static final BlockContainer BLEEDING_OBSIDIAN_WALL = new BlockContainer(new CryingObsidianWallBlock(Blocks.OBSIDIAN, DRIPPING_OBSIDIAN_BLOOD)).dropSelf();
	//</editor-fold>
	//<editor-fold desc="Piglin Head">
	private static final Block PIGLIN_HEAD_BLOCK = new PiglinHeadBlock(Block.Settings.of(Material.DECORATION).strength(1.0f));
	private static final Block PIGLIN_WALL_HEAD = new WallPiglinHeadBlock(Block.Settings.of(Material.DECORATION).strength(1.0f));
	public static final WallBlockContainer PIGLIN_HEAD = new WallBlockContainer(PIGLIN_HEAD_BLOCK, PIGLIN_WALL_HEAD, new VerticallyAttachableBlockItem(PIGLIN_HEAD_BLOCK, PIGLIN_WALL_HEAD, ItemSettings(ItemGroup.DECORATIONS).rarity(Rarity.UNCOMMON), Direction.DOWN)).dropSelf();
	public static final BlockEntityType<PiglinHeadEntity> PIGLIN_HEAD_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(PiglinHeadEntity::new, PIGLIN_HEAD.asBlock(), PIGLIN_HEAD.getWallBlock()).build();
	//</editor-fold>
	//<editor-fold desc="Pottery Shards (1.20)>
	public static final Item POTTERY_SHARD_ARCHER = new Item(ItemSettings());
	public static final Item POTTERY_SHARD_PRIZE = new Item(ItemSettings());
	public static final Item POTTERY_SHARD_ARMS_UP = new Item(ItemSettings());
	public static final Item POTTERY_SHARD_SKULL = new Item(ItemSettings());
	//</editor-fold>
	//<editor-fold desc="Prismarine">
	public static final BlockContainer DARK_PRISMARINE_WALL = ModFactory.MakeWall(Blocks.DARK_PRISMARINE);
	//</editor-fold>
	//<editor-fold desc="Purpur">
	public static final BlockContainer PURPUR_WALL = ModFactory.MakeWall(Blocks.PURPUR_BLOCK);
	public static final BlockContainer SMOOTH_PURPUR = ModFactory.MakeBlock(Blocks.PURPUR_BLOCK);
	public static final BlockContainer SMOOTH_PURPUR_STAIRS = ModFactory.MakeStairs(SMOOTH_PURPUR);
	public static final BlockContainer SMOOTH_PURPUR_SLAB = ModFactory.MakeSlab(SMOOTH_PURPUR);
	public static final BlockContainer SMOOTH_PURPUR_WALL = ModFactory.MakeWall(SMOOTH_PURPUR);
	public static final BlockContainer PURPUR_BRICKS = ModFactory.MakeBlock(Blocks.PURPUR_BLOCK);
	public static final BlockContainer PURPUR_BRICK_STAIRS = ModFactory.MakeStairs(PURPUR_BRICKS);
	public static final BlockContainer PURPUR_BRICK_SLAB = ModFactory.MakeSlab(PURPUR_BRICKS);
	public static final BlockContainer PURPUR_BRICK_WALL = ModFactory.MakeWall(PURPUR_BRICKS);
	//</editor-fold>
	//<editor-fold desc="Quartz">
	public static final BlockContainer QUARTZ_CRYSTAL_BLOCK = ModFactory.MakeBlock(Blocks.QUARTZ_BLOCK);
	public static final BlockContainer QUARTZ_CRYSTAL_STAIRS = ModFactory.MakeStairs(QUARTZ_CRYSTAL_BLOCK);
	public static final BlockContainer QUARTZ_CRYSTAL_SLAB = ModFactory.MakeSlab(QUARTZ_CRYSTAL_BLOCK);
	public static final BlockContainer QUARTZ_CRYSTAL_WALL = ModFactory.MakeWall(QUARTZ_CRYSTAL_BLOCK);
	public static final BlockContainer SMOOTH_QUARTZ_WALL = ModFactory.MakeWall(Blocks.SMOOTH_QUARTZ);
	public static final BlockContainer QUARTZ_WALL = ModFactory.MakeWall(Blocks.QUARTZ_BLOCK);
	public static final BlockContainer QUARTZ_BRICK_STAIRS = ModFactory.MakeStairs(Blocks.QUARTZ_BRICKS);
	public static final BlockContainer QUARTZ_BRICK_SLAB = ModFactory.MakeSlab(Blocks.QUARTZ_BRICKS);
	public static final BlockContainer QUARTZ_BRICK_WALL = ModFactory.MakeWall(Blocks.QUARTZ_BRICKS);
	public static final Item QUARTZ_AXE = ModFactory.MakeAxe(ModToolMaterials.QUARTZ);
	public static final Item QUARTZ_HOE = ModFactory.MakeHoe(ModToolMaterials.QUARTZ);
	public static final Item QUARTZ_PICKAXE = ModFactory.MakePickaxe(ModToolMaterials.QUARTZ);
	public static final Item QUARTZ_SHOVEL = ModFactory.MakeShovel(ModToolMaterials.QUARTZ);
	public static final Item QUARTZ_SWORD = ModFactory.MakeSword(ModToolMaterials.QUARTZ);
	public static final Item QUARTZ_KNIFE = ModFactory.MakeKnife(ModToolMaterials.QUARTZ);
	public static final Item QUARTZ_HELMET = ModFactory.MakeHelmet(ModArmorMaterials.QUARTZ);
	public static final Item QUARTZ_CHESTPLATE = ModFactory.MakeChestplate(ModArmorMaterials.QUARTZ);
	public static final Item QUARTZ_LEGGINGS = ModFactory.MakeLeggings(ModArmorMaterials.QUARTZ);
	public static final Item QUARTZ_BOOTS = ModFactory.MakeBoots(ModArmorMaterials.QUARTZ);
	public static final Item QUARTZ_HORSE_ARMOR = ModFactory.MakeHorseArmor(ModArmorMaterials.QUARTZ);
	//</editor-fold>
	//<editor-fold desc="Raw Metal">
	public static final BlockContainer RAW_COPPER_SLAB = ModFactory.MakeSlab(Blocks.RAW_COPPER_BLOCK);
	public static final BlockContainer RAW_GOLD_SLAB = ModFactory.MakeSlab(Blocks.RAW_GOLD_BLOCK);
	public static final BlockContainer RAW_IRON_SLAB = ModFactory.MakeSlab(Blocks.RAW_IRON_BLOCK);
	//</editor-fold>
	//<editor-fold desc="Sandstone">
	public static final BlockContainer SMOOTH_SANDSTONE_WALL = ModFactory.MakeWall(Blocks.SMOOTH_SANDSTONE);
	public static final BlockContainer SMOOTH_RED_SANDSTONE_WALL = ModFactory.MakeWall(Blocks.SMOOTH_RED_SANDSTONE);
	//</editor-fold>
	//<editor-fold desc="Sculk">
	public static final BlockContainer SCULK = new BlockContainer(new SculkBlock(Block.Settings.of(Material.SCULK).strength(0.2f).sounds(ModBlockSoundGroups.SCULK)), ItemSettings(ItemGroup.DECORATIONS)).requireSilkTouch();
	public static final BlockContainer SCULK_VEIN = new BlockContainer(new SculkVeinBlock(Block.Settings.of(Material.SCULK).noCollision().strength(0.2f).sounds(ModBlockSoundGroups.SCULK_VEIN)), ItemSettings(ItemGroup.DECORATIONS)).requireSilkTouch();
	public static final DefaultParticleType SCULK_SOUL_PARTICLE = FabricParticleTypes.simple(false);
	public static final ParticleType<SculkChargeParticleEffect> SCULK_CHARGE_PARTICLE = new ParticleType<>(true, SculkChargeParticleEffect.FACTORY) { @Override public Codec<SculkChargeParticleEffect> getCodec() { return SculkChargeParticleEffect.CODEC; } };
	public static final DefaultParticleType SCULK_CHARGE_POP_PARTICLE = FabricParticleTypes.simple(true);
	public static final ParticleType<ShriekParticleEffect> SHRIEK_PARTICLE = new ParticleType<>(false, ShriekParticleEffect.FACTORY) { @Override public Codec<ShriekParticleEffect> getCodec() { return ShriekParticleEffect.CODEC; } };
	public static final BlockContainer SCULK_CATALYST = new BlockContainer(new SculkCatalystBlock(Block.Settings.of(Material.SCULK).strength(3.0f, 3.0f).sounds(ModBlockSoundGroups.SCULK_CATALYST).luminance(ModFactory.LUMINANCE_6)), ItemSettings(ItemGroup.DECORATIONS)).requireSilkTouch();
	public static final BlockEntityType<SculkCatalystBlockEntity> SCULK_CATALYST_ENTITY = FabricBlockEntityTypeBuilder.create(SculkCatalystBlockEntity::new, SCULK_CATALYST.asBlock()).build();
	public static final BlockContainer SCULK_SHRIEKER = new BlockContainer(new SculkShriekerBlock(Block.Settings.of(Material.SCULK, MapColor.BLACK).strength(3.0f, 3.0f).sounds(ModBlockSoundGroups.SCULK_SHRIEKER)), ItemSettings(ItemGroup.DECORATIONS)).requireSilkTouch();
	public static final BlockEntityType<SculkShriekerBlockEntity> SCULK_SHRIEKER_ENTITY = FabricBlockEntityTypeBuilder.create(SculkShriekerBlockEntity::new, SCULK_SHRIEKER.asBlock()).build();
	//Extended
	public static final BlockContainer SCULK_STONE = new BlockContainer(new Block(Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(2.5F, 6.0F))).dropSelf();
	public static final BlockContainer SCULK_STONE_STAIRS = ModFactory.MakeStairs(SCULK_STONE);
	public static final BlockContainer SCULK_STONE_SLAB = ModFactory.MakeSlab(SCULK_STONE);
	public static final BlockContainer SCULK_STONE_WALL = ModFactory.MakeWall(SCULK_STONE);
	public static final BlockContainer SCULK_STONE_BRICKS = ModFactory.MakeBlock(SCULK_STONE);
	public static final BlockContainer SCULK_STONE_BRICK_STAIRS = ModFactory.MakeStairs(SCULK_STONE_BRICKS);
	public static final BlockContainer SCULK_STONE_BRICK_SLAB = ModFactory.MakeSlab(SCULK_STONE_BRICKS);
	public static final BlockContainer SCULK_STONE_BRICK_WALL = ModFactory.MakeWall(SCULK_STONE_BRICKS);
	//Turf
	public static final Item CHUM = new ChumItem(ItemSettings().food(FoodComponents.ROTTEN_FLESH));
	public static final BlockContainer CALCITE_SCULK_TURF = new BlockContainer(new SculkTurfBlock(Blocks.CALCITE, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(0.75F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly())).requireSilkTouchOrDrop(Items.CALCITE);
	public static final BlockContainer DEEPSLATE_SCULK_TURF = new BlockContainer(new SculkTurfBlock(Blocks.DEEPSLATE, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(3.0F, 6.0F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly())).requireSilkTouchOrDrop(Items.DEEPSLATE);
	public static final BlockContainer DRIPSTONE_SCULK_TURF = new BlockContainer(new SculkTurfBlock(Blocks.DRIPSTONE_BLOCK, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(1.5F, 1.0F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly())).requireSilkTouchOrDrop(Items.DRIPSTONE_BLOCK);
	public static final BlockContainer SMOOTH_BASALT_SCULK_TURF = new BlockContainer(new SculkTurfBlock(Blocks.SMOOTH_BASALT, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(1.25F, 4.2F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly())).requireSilkTouchOrDrop(Items.SMOOTH_BASALT);
	public static final BlockContainer TUFF_SCULK_TURF = new BlockContainer(new SculkTurfBlock(Blocks.TUFF, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(1.5F, 6.0F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly())).requireSilkTouchOrDrop(Items.TUFF);
	//</editor-fold>
	//<editor-fold desc="Sniffer">
	public static final EntityType<SnifferEntity> SNIFFER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SnifferEntity::new).dimensions(EntityDimensions.fixed(1.9f, 1.75f)).trackRangeChunks(10).build();
	public static final Item SNIFFER_SPAWN_EGG = new SpawnEggItem(SNIFFER_ENTITY, 9840944, 5085536, ItemSettings(ItemGroup.MISC));
	//</editor-fold>
	//<editor-fold desc="Soul Fire">
	public static final DefaultParticleType SMALL_SOUL_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final BlockContainer SOUL_CANDLE = ModFactory.MakeCandle(MapColor.BROWN, 2.5);
	public static final Block SOUL_CANDLE_CAKE = new ModCandleCakeBlock(2).drops(SOUL_CANDLE.asBlock());
	//</editor-fold>
	//<editor-fold desc="Spruce">
	public static final Item SPRUCE_CHEST_BOAT = ModFactory.MakeChestBoat(BoatEntity.Type.SPRUCE, ModFactory.BoatSettings(ItemGroup.TRANSPORTATION));
	public static final WallBlockContainer SPRUCE_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.SPRUCE, MapColor.BROWN, ModFactory.SignItemSettings(ItemGroup.DECORATIONS));
	public static final BlockContainer SPRUCE_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.SPRUCE_BROWN).flammable(30, 20).fuel(300);
	public static final BlockContainer SPRUCE_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer SPRUCE_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.SPRUCE_BROWN);
	public static final BlockContainer SPRUCE_LECTERN = ModFactory.MakeLectern(MapColor.SPRUCE_BROWN).flammable(30, 20).fuel(300);
	//</editor-fold>
	//<editor-fold desc="Studded Leather">
	public static final Item STUDDED_LEATHER_HELMET = ModFactory.MakeHelmet(ModArmorMaterials.STUDDED_LEATHER);
	public static final Item STUDDED_LEATHER_CHESTPLATE = ModFactory.MakeChestplate(ModArmorMaterials.STUDDED_LEATHER);
	public static final Item STUDDED_LEATHER_LEGGINGS = ModFactory.MakeLeggings(ModArmorMaterials.STUDDED_LEATHER);
	public static final Item STUDDED_LEATHER_BOOTS = ModFactory.MakeBoots(ModArmorMaterials.STUDDED_LEATHER);
	public static final Item STUDDED_LEATHER_HORSE_ARMOR = ModFactory.MakeHorseArmor(ModArmorMaterials.STUDDED_LEATHER);
	//</editor-fold>
	//<editor-fold desc="Torchflower">
	private static final Block TORCHFLOWER_CROP_BLOCK = new TorchflowerBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
	public static final BlockContainer TORCHFLOWER_CROP = new BlockContainer(TORCHFLOWER_CROP_BLOCK, new AliasedBlockItem(TORCHFLOWER_CROP_BLOCK, ItemSettings(ItemGroup.DECORATIONS))).compostable(0.3f);
	public static final FlowerContainer TORCHFLOWER = ModFactory.MakeFlower(StatusEffects.NIGHT_VISION, 5, ItemSettings(ItemGroup.DECORATIONS));
	//</editor-fold>
	//<editor-fold desc="Tuff">
	public static final BlockContainer TUFF_STAIRS = ModFactory.MakeStairs(Blocks.TUFF);
	public static final BlockContainer TUFF_SLAB = ModFactory.MakeSlab(Blocks.TUFF);
	public static final BlockContainer TUFF_WALL = ModFactory.MakeWall(Blocks.TUFF);
	public static final BlockContainer SMOOTH_TUFF = ModFactory.MakeBlock(Blocks.TUFF);
	public static final BlockContainer SMOOTH_TUFF_STAIRS = ModFactory.MakeStairs(SMOOTH_TUFF);
	public static final BlockContainer SMOOTH_TUFF_SLAB = ModFactory.MakeSlab(SMOOTH_TUFF);
	public static final BlockContainer SMOOTH_TUFF_WALL = ModFactory.MakeWall(SMOOTH_TUFF);
	public static final BlockContainer TUFF_BRICKS = ModFactory.MakeBlock(Blocks.TUFF);
	public static final BlockContainer TUFF_BRICK_STAIRS = ModFactory.MakeStairs(TUFF_BRICKS);
	public static final BlockContainer TUFF_BRICK_SLAB = ModFactory.MakeSlab(TUFF_BRICKS);
	public static final BlockContainer TUFF_BRICK_WALL = ModFactory.MakeWall(TUFF_BRICKS);
	//</editor-fold>
	//<editor-fold desc="Underwater Torch">
	public static final DefaultParticleType UNDERWATER_TORCH_GLOW = FabricParticleTypes.simple(false);
	public static final TorchContainer UNDERWATER_TORCH = TorchContainer.Waterloggable(ModFactory.TorchSettings(14, BlockSoundGroup.WOOD), UNDERWATER_TORCH_GLOW);
	//</editor-fold>
	//<editor-fold desc="Unlit Light Sources">
	public static final UnlitTorchContainer UNLIT_TORCH = new UnlitTorchContainer(new UnlitTorchBlock((TorchBlock)Blocks.TORCH), new UnlitWallTorchBlock((WallTorchBlock)Blocks.WALL_TORCH)).drops(Items.TORCH);
	public static final UnlitTorchContainer UNLIT_SOUL_TORCH = new UnlitTorchContainer(new UnlitTorchBlock((TorchBlock)Blocks.SOUL_TORCH), new UnlitWallTorchBlock((WallTorchBlock)Blocks.SOUL_WALL_TORCH)).drops(Items.SOUL_TORCH);
	public static final Block UNLIT_LANTERN = ModFactory.MakeUnlitLantern(Blocks.LANTERN, Items.LANTERN).dropsLantern();
	public static final Block UNLIT_SOUL_LANTERN = ModFactory.MakeUnlitLantern(Blocks.SOUL_LANTERN, Items.SOUL_LANTERN).dropsSoulLantern();
	//</editor-fold>
	//<editor-fold desc="Warden">
	public static final DefaultParticleType SONIC_BOOM_PARTICLE = FabricParticleTypes.simple(true);
	public static final ModSensorType<WardenAttackablesSensor> WARDEN_ENTITY_SENSOR = new ModSensorType<>("minecraft:warden_entity_sensor", WardenAttackablesSensor::new);
	public static final EntityType<WardenEntity> WARDEN_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WardenEntity::new).dimensions(EntityDimensions.fixed(0.9f, 2.9f)).trackRangeChunks(16).fireImmune().build();
	public static final Item WARDEN_SPAWN_EGG = new SpawnEggItem(WARDEN_ENTITY, 1001033, 3790560, ItemSettings(ItemGroup.MISC));
	//</editor-fold>
	//<editor-fold desc="Warped">
	public static final BlockContainer WARPED_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.DARK_AQUA, ModBlockSoundGroups.NETHER_WOOD).flammable(30, 20).fuel(300);
	public static final BlockContainer WARPED_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer WARPED_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.DARK_AQUA);
	public static final BlockContainer WARPED_BARREL = ModFactory.MakeBarrel(MapColor.DARK_AQUA, ModBlockSoundGroups.NETHER_WOOD).fuel(300);
	public static final BlockContainer WARPED_LECTERN = ModFactory.MakeLectern(MapColor.DARK_AQUA, ModBlockSoundGroups.NETHER_WOOD).flammable(30, 20).fuel(300);
	public static final WallBlockContainer WARPED_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.WARPED, MapColor.DARK_AQUA, ModFactory.SignItemSettings(ItemGroup.DECORATIONS));
	//</editor-fold>
	//<editor-fold desc="Woodcutting">
	public static final RecipeType<WoodcuttingRecipe> WOODCUTTING_RECIPE_TYPE = RegisterRecipeType("woodcutting");
	public static final RecipeSerializer<WoodcuttingRecipe> WOODCUTTING_RECIPE_SERIALIZER = new WoodcuttingRecipeSerializer<>(WoodcuttingRecipe::new);
	//</editor-fold>
	//<editor-fold desc="Wool">
	public static final Map<DyeColor, BlockContainer> WOOL_SLABS = ColorUtil.Map((color) -> ModFactory.MakeSlab(ColorUtil.GetWoolBlock(color)).flammable(40, 40).fuel(50));
	public static final BlockContainer RAINBOW_WOOL = ModFactory.BuildBlock(new ModFacingBlock(Blocks.WHITE_WOOL)).flammable(30, 60).fuel(100);
	public static final BlockContainer RAINBOW_WOOL_SLAB = ModFactory.BuildSlab(new HorizontalFacingSlabBlock(RAINBOW_WOOL)).flammable(40, 40).fuel(50);
	public static final BlockContainer RAINBOW_CARPET = ModFactory.BuildBlock(new HorziontalFacingCarpetBlock(RAINBOW_WOOL)).flammable(60, 20).fuel(67);
	//</editor-fold>

	public static final Item HORN = new Item(ItemSettings());


	//TODO: Chiseled Bookshelves (1.20)
	//TODO: Armor Trims (1.20)
	//TODO: Recovery Compass
	public static final Item NETHERITE_UPGRADE_SMITHING_TEMPLATE = SmithingTemplateItem.createNetheriteUpgrade();
	public static final RecipeType<TrimmingRecipe> TRIMMING_RECIPE_TYPE = RegisterRecipeType("trimming");
	public static final RecipeSerializer<TrimmingRecipe> TRIMMING_RECIPE_SERIALIZER = new TrimmingRecipe.Serializer();
	public static final BlockContainer TRIMMING_TABLE = ModFactory.BuildBlock(new TrimmingTableBlock(Blocks.SMITHING_TABLE));

	public static final Item POUCH = new PouchItem(ItemSettings().maxCount(16));
	public static final Item CHICKEN_POUCH = new ChickenPouchItem(EntityType.CHICKEN, ItemSettings().maxCount(1)).dispensible();
	public static final Item RABBIT_POUCH = new EntityPouchItem(EntityType.RABBIT, ModSoundEvents.ITEM_POUCH_EMPTY_RABBIT, ItemSettings().maxCount(1)).dispensible();
	public static final Item HEDGEHOG_POUCH = new EntityPouchItem(HEDGEHOG_ENTITY, ModSoundEvents.ITEM_POUCH_EMPTY_HEDGEHOG, ItemSettings().maxCount(1)).dispensible();

	//<editor-fold desc="Misc Stuff">
	public static final BlockContainer COAL_SLAB = ModFactory.MakeSlab(Blocks.COAL_BLOCK).flammable(5, 20).fuel(8000);
	public static final BlockContainer CHARCOAL_BLOCK = ModFactory.MakeBlock(Blocks.COAL_BLOCK).flammable(5, 5).fuel(16000);
	public static final BlockContainer CHARCOAL_SLAB = ModFactory.MakeSlab(CHARCOAL_BLOCK).flammable(5, 20).fuel(8000);
	public static final BlockContainer COARSE_DIRT_SLAB = ModFactory.MakeSlab(Blocks.COARSE_DIRT);
	public static final BlockContainer BLUE_SHROOMLIGHT = ModFactory.MakeBlock(Block.Settings.copy(Blocks.SHROOMLIGHT).mapColor(MapColor.CYAN));
	//TODO: Recipe for Blue Targets
	public static final BlockContainer BLUE_TARGET = ModFactory.BuildBlock(new TargetBlock(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, MapColor.OFF_WHITE).strength(0.5f).sounds(BlockSoundGroup.GRASS))).flammable(15, 20);
	public static final BlockContainer COCOA_BLOCK = ModFactory.MakeBlock(Block.Settings.of(Material.SOLID_ORGANIC, MapColor.BROWN).strength(0.8F).sounds(BlockSoundGroup.WOOD)).compostable(0.65f);
	public static final BlockContainer FLINT_BLOCK = new BlockContainer(new FlintBlock(Block.Settings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).strength(1.5f, 3.0f).sounds(BlockSoundGroup.METAL))).drops(DropTable.FLINT);
	public static final BlockContainer FLINT_SLAB = ModFactory.BuildSlab(new FlintSlabBlock(FLINT_BLOCK), DropTable.FLINT_SLAB);
	public static final BlockContainer FLINT_BRICKS = ModFactory.BuildBlock(new FlintBlock(FLINT_BLOCK), DropTable.FLINT);
	public static final BlockContainer FLINT_BRICK_STAIRS = ModFactory.BuildStairs(new FlintStairsBlock(FLINT_BRICKS), DropTable.FLINT);
	public static final BlockContainer FLINT_BRICK_SLAB = ModFactory.BuildSlab(new FlintSlabBlock(FLINT_BRICKS), DropTable.FLINT_SLAB);
	public static final BlockContainer FLINT_BRICK_WALL = ModFactory.BuildWall(new FlintWallBlock(FLINT_BRICKS), DropTable.FLINT);
	public static final BlockContainer HEDGE_BLOCK = ModFactory.MakeBlock(Block.Settings.of(Material.LEAVES).strength(0.2F).sounds(BlockSoundGroup.GRASS).nonOpaque()).flammable(5, 20);

	public static final PottedBlockContainer MYCELIUM_ROOTS = new PottedBlockContainer(new MyceliumRootsBlock(Block.Settings.of(Material.REPLACEABLE_PLANT, MapColor.PURPLE).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS))).requireSilkTouch();
	public static final ModConfiguredFeature<SimpleBlockFeatureConfig, ?> SINGLE_MYCELIUM_ROOTS = new ModConfiguredFeature<>("single_mycelium_roots", new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(MYCELIUM_ROOTS.asBlock().getDefaultState()))));
	public static final ModConfiguredFeature<SimpleBlockFeatureConfig, ?> SINGLE_BROWN_MUSHROOM = new ModConfiguredFeature<>("single_brown_mushroom", new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.BROWN_MUSHROOM.getDefaultState()))));
	public static final ModConfiguredFeature<SimpleBlockFeatureConfig, ?> SINGLE_BLUE_MUSHROOM = new ModConfiguredFeature<>("single_blue_mushroom", new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(BLUE_MUSHROOM.asBlock().getDefaultState()))));
	public static final ModConfiguredFeature<SimpleBlockFeatureConfig, ?> SINGLE_RED_MUSHROOM = new ModConfiguredFeature<>("single_red_mushroom", new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.RED_MUSHROOM.getDefaultState()))));
	public static final ModPlacedFeature MYCELIUM_BONEMEAL_MYCELIUM_ROOTS = new ModPlacedFeature("mycelium_bonemeal_roots", SINGLE_MYCELIUM_ROOTS) { @Override public List<PlacementModifier> getModifiers() { return List.of(PlacedFeatures.isAir()); } };
	public static final ModPlacedFeature MYCELIUM_BONEMEAL_BROWN_MUSHROOM = new ModPlacedFeature("mycelium_bonemeal_brown", SINGLE_BROWN_MUSHROOM) { @Override public List<PlacementModifier> getModifiers() { return List.of(PlacedFeatures.isAir()); } };
	public static final ModPlacedFeature MYCELIUM_BONEMEAL_BLUE_MUSHROOM = new ModPlacedFeature("mycelium_bonemeal_blue", SINGLE_BLUE_MUSHROOM) { @Override public List<PlacementModifier> getModifiers() { return List.of(PlacedFeatures.isAir()); } };
	public static final ModPlacedFeature MYCELIUM_BONEMEAL_RED_MUSHROOM = new ModPlacedFeature("mycelium_bonemeal_red", SINGLE_RED_MUSHROOM) { @Override public List<PlacementModifier> getModifiers() { return List.of(PlacedFeatures.isAir()); } };

	public static final BlockContainer SEED_BLOCK = ModFactory.MakeBlock(Block.Settings.of(Material.SOLID_ORGANIC, MapColor.DARK_GREEN).strength(0.8F).sounds(BlockSoundGroup.GRASS)).compostable(1f);
	public static final BlockContainer GLAZED_TERRACOTTA = new BlockContainer(new GlazedTerracottaBlock(Block.Settings.of(Material.STONE, MapColor.ORANGE).requiresTool().strength(1.4f))).dropSelf();
	public static final BlockContainer WAX_BLOCK = ModFactory.BuildBlock(new WaxBlock(Blocks.HONEYCOMB_BLOCK));
	public static final Item LAVA_BOTTLE = new LavaBottleItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
	//</editor-fold>

	//<editor-fold desc="Biomes">
	public static final Feature<DefaultFeatureConfig> DISK_GRASS_FEATURE = new DiskGrassFeature(DefaultFeatureConfig.CODEC);
	public static final ModConfiguredFeature<DefaultFeatureConfig, ?> DISK_GRASS_CONFIGURED = new ModConfiguredFeature<>("minecraft:disk_grass", new ConfiguredFeature<>(DISK_GRASS_FEATURE, FeatureConfig.DEFAULT));
	public static final ModPlacedFeature DISK_GRASS_PLACED = new ModPlacedFeature("minecraft:disk_grass", DISK_GRASS_CONFIGURED) {
		@Override
		public List<PlacementModifier> getModifiers() {
			return List.of(
					CountPlacementModifier.of(1),
					SquarePlacementModifier.of(),
					PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP,
					RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)),
					BlockFilterPlacementModifier.of(BlockPredicate.matchingBlocks(List.of(ModBase.MUD.asBlock()))),
					BiomePlacementModifier.of()
			);
		}
	};
	public static final Feature<MangroveTreeFeatureConfig> MANGROVE_TREE_FEATURE = new MangroveTreeFeature(MangroveTreeFeatureConfig.CODEC);
	public static final ModConfiguredFeature<MangroveTreeFeatureConfig, ?> MANGROVE_CONFIGURED = new ModConfiguredFeature<>("minecraft:mangrove", new ConfiguredFeature<>(MANGROVE_TREE_FEATURE, new MangroveTreeFeatureConfig(false)));
	public static final ModPlacedFeature MANGROVE_CHECKED_PLACED = new ModPlacedFeature("minecraft:mangrove_checked", MANGROVE_CONFIGURED) {
		@Override public List<PlacementModifier> getModifiers() { return List.of(PlacedFeatures.wouldSurvive(ModBase.MANGROVE_PROPAGULE.asBlock())); }
	};
	public static final ModConfiguredFeature<MangroveTreeFeatureConfig, ?> TALL_MANGROVE_CONFIGURED = new ModConfiguredFeature<>("minecraft:tall_mangrove", new ConfiguredFeature<>(MANGROVE_TREE_FEATURE, new MangroveTreeFeatureConfig(true)));
	public static final ModPlacedFeature TALL_MANGROVE_CHECKED_PLACED = new ModPlacedFeature("minecraft:tall_mangrove_checked", TALL_MANGROVE_CONFIGURED) {
		@Override public List<PlacementModifier> getModifiers() { return List.of(PlacedFeatures.wouldSurvive(ModBase.MANGROVE_PROPAGULE.asBlock())); }
	};
	public static final ModConfiguredFeature<RandomFeatureConfig, ?> MANGROVE_VEGETATION_CONFIGURED = new ModConfiguredFeature<>("minecraft:mangrove_vegetation",
			() -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(
					new RandomFeatureEntry(TALL_MANGROVE_CHECKED_PLACED.getRegistryEntry(), 0.85f)),
					MANGROVE_CHECKED_PLACED.getRegistryEntry())));
	public static final ModPlacedFeature TREES_MANGROVE_PLACED = new ModPlacedFeature("minecraft:trees_mangrove", MANGROVE_VEGETATION_CONFIGURED) {
		@Override
		public List<PlacementModifier> getModifiers() {
			return List.of(
					CountPlacementModifier.of(25),
					SquarePlacementModifier.of(),
					SurfaceWaterDepthFilterPlacementModifier.of(5),
					PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of(),
					BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(MANGROVE_PROPAGULE.asBlock().getDefaultState(), BlockPos.ORIGIN))
			);
		}
	};

	public static final Feature<CherryTreeFeatureConfig> CHERRY_TREE_FEATURE = new CherryTreeFeature(CherryTreeFeatureConfig.CODEC);
	public static final ModConfiguredFeature<CherryTreeFeatureConfig, ?> CHERRY_CONFIGURED = new ModConfiguredFeature<>("minecraft:cherry", new ConfiguredFeature<>(CHERRY_TREE_FEATURE, new CherryTreeFeatureConfig(false)));
	public static final ModPlacedFeature CHERRY_CHECKED_PLACED = new ModPlacedFeature("minecraft:cherry_checked", CHERRY_CONFIGURED) {
		@Override public List<PlacementModifier> getModifiers() { return List.of(PlacedFeatures.wouldSurvive(ModBase.CHERRY_SAPLING.asBlock())); }
	};
	public static final ModConfiguredFeature<CherryTreeFeatureConfig, ?> CHERRY_BEES_005_CONFIGURED = new ModConfiguredFeature<>("minecraft:cherry_bees_005", new ConfiguredFeature<>(CHERRY_TREE_FEATURE, new CherryTreeFeatureConfig(true)));
	public static final ModPlacedFeature CHERRY_BEES_PLACED = new ModPlacedFeature("minecraft:cherry_bees_005", CHERRY_BEES_005_CONFIGURED) {
		@Override public List<PlacementModifier> getModifiers() { return List.of(PlacedFeatures.wouldSurvive(ModBase.CHERRY_SAPLING.asBlock())); }
	};
	public static final ModConfiguredFeature<RandomFeatureConfig, ?> CHERRY_VEGETATION_CONFIGURED = new ModConfiguredFeature<>("minecraft:cherry_vegetation",
			() -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(
					new RandomFeatureEntry(CHERRY_CHECKED_PLACED.getRegistryEntry(), 0.85f)),
					CHERRY_BEES_PLACED.getRegistryEntry())));
	public static final ModPlacedFeature TREES_CHERRY_PLACED = new ModPlacedFeature("minecraft:trees_cherry", CHERRY_VEGETATION_CONFIGURED) {
		@Override
		public List<PlacementModifier> getModifiers() {
			return List.of(
					PlacedFeatures.createCountExtraModifier(10, 0.1f, 1),
					PlacedFeatures.wouldSurvive(CHERRY_SAPLING.asBlock())
			);
		}
	};
	public static final ModConfiguredFeature<RandomPatchFeatureConfig, ?> FLOWER_CHERRY_CONFIGURED = new ModConfiguredFeature<>("minecraft:flower_cherry", () -> {
		DataPool.Builder<BlockState> builder = DataPool.builder();
		for (int i = 1; i <= 4; ++i) {
			for (Direction direction : Direction.Type.HORIZONTAL) {
				builder.add(PINK_PETALS.asBlock().getDefaultState().with(FlowerbedBlock.FLOWER_AMOUNT, i).with(FlowerbedBlock.FACING, direction), 1);
			}
		}
		return new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchFeatureConfig(96, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(builder)))));
	});
	public static final ModPlacedFeature FLOWER_CHERRY_PLACED = new ModPlacedFeature("minecraft:flower_cherry", FLOWER_CHERRY_CONFIGURED) {
		@Override
		public List<PlacementModifier> getModifiers() {
			return List.of(
					NoiseThresholdCountPlacementModifier.of(-0.8, 5, 10),
					SquarePlacementModifier.of(),
					PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
					BiomePlacementModifier.of()
			);
		}
	};

	public static final Feature<SculkPatchFeatureConfig> SCULK_PATCH_FEATURE = new SculkPatchFeature(SculkPatchFeatureConfig.CODEC);
	public static final ModConfiguredFeature<SculkPatchFeatureConfig, ?> SCULK_PATCH_DEEP_DARK_CONFIGURED = new ModConfiguredFeature<>("minecraft:sculk_patch_deep_dark", new ConfiguredFeature<>(SCULK_PATCH_FEATURE, new SculkPatchFeatureConfig(10, 32, 64, 0, 1, ConstantIntProvider.create(0), 0.5f)));
	public static final ModPlacedFeature SCULK_PATCH_DEEP_DARK_PLACED = new ModPlacedFeature("minecraft:sculk_patch_deep_dark", SCULK_PATCH_DEEP_DARK_CONFIGURED) {
		@Override
		public List<PlacementModifier> getModifiers() {
			return List.of(
					CountPlacementModifier.of(ConstantIntProvider.create(256)),
					SquarePlacementModifier.of(),
					PlacedFeatures.BOTTOM_TO_120_RANGE,
					BiomePlacementModifier.of()
			);
		}
	};
	public static final ModConfiguredFeature<SculkPatchFeatureConfig, ?> SCULK_PATCH_ANCIENT_CITY_CONFIGURED = new ModConfiguredFeature<>("minecraft:sculk_patch_ancient_city", new ConfiguredFeature<>(SCULK_PATCH_FEATURE, new SculkPatchFeatureConfig(10, 32, 64, 0, 1, UniformIntProvider.create(1, 3), 0.5F)));
	public static final ModPlacedFeature SCULK_PATCH_ANCIENT_CITY_PLACED = new ModPlacedFeature("minecraft:sculk_patch_ancient_city", SCULK_PATCH_ANCIENT_CITY_CONFIGURED) { @Override public List<PlacementModifier> getModifiers() { return List.of(); } };
	public static final Feature<DefaultFeatureConfig> SCULK_VEIN_FEATURE = new SculkVeinFeature(DefaultFeatureConfig.CODEC);
	public static final ModConfiguredFeature<DefaultFeatureConfig, ?> SCULK_VEIN_CONFIGURED = new ModConfiguredFeature<>("minecraft:sculk_vein", new ConfiguredFeature<>(SCULK_VEIN_FEATURE, FeatureConfig.DEFAULT));
	public static final ModPlacedFeature SCULK_VEIN_PLACED = new ModPlacedFeature("minecraft:sculk_vein", SCULK_VEIN_CONFIGURED) {
		@Override
		public List<PlacementModifier> getModifiers() {
			return List.of(
					CountPlacementModifier.of(UniformIntProvider.create(204, 250)),
					SquarePlacementModifier.of(),
					PlacedFeatures.BOTTOM_TO_120_RANGE,
					BiomePlacementModifier.of()
			);
		}
	};

	public static final ModStructureProcessorList ANCIENT_CITY_START_DEGRADATION = new ModStructureProcessorList("minecraft:ancient_city_start_degradation", new StructureProcessorList(ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(
			new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_BRICKS, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState()),
			new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_TILES, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.getDefaultState()),
			new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.SOUL_LANTERN, 0.05f), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()))),
			new ProtectedBlocksStructureProcessor(BlockTags.FEATURES_CANNOT_REPLACE))));
	public static final ModStructureProcessorList ANCIENT_CITY_GENERIC_DEGRADATION = new ModStructureProcessorList("minecraft:ancient_city_generic_degradation", new StructureProcessorList(ImmutableList.of(
			new ModBlockRotStructureProcessor(ModBlockTags.ANCIENT_CITY_REPLACEABLE, 0.95f),
			new RuleStructureProcessor(ImmutableList.of(
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_BRICKS, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_TILES, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.SOUL_LANTERN, 0.05f), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()))),
			new ProtectedBlocksStructureProcessor(BlockTags.FEATURES_CANNOT_REPLACE))));
	public static final ModStructureProcessorList ANCIENT_CITY_WALLS_DEGRADATION = new ModStructureProcessorList("minecraft:ancient_city_walls_degradation", new StructureProcessorList(ImmutableList.of(
			new ModBlockRotStructureProcessor(ModBlockTags.ANCIENT_CITY_REPLACEABLE, 0.95f),
			new RuleStructureProcessor(ImmutableList.of(
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_BRICKS, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_TILES, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_TILE_SLAB, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.SOUL_LANTERN, 0.05f), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()))),
			new ProtectedBlocksStructureProcessor(BlockTags.FEATURES_CANNOT_REPLACE))));

	public static ModStructurePool CITY_CENTER = new ModStructurePool("minecraft:ancient_city/city_center", ImmutableList.of(
			new ModPoolPair("minecraft:ancient_city/city_center/city_center_1", ANCIENT_CITY_START_DEGRADATION, 1),
			new ModPoolPair("minecraft:ancient_city/city_center/city_center_2", ANCIENT_CITY_START_DEGRADATION, 1),
			new ModPoolPair("minecraft:ancient_city/city_center/city_center_3", ANCIENT_CITY_START_DEGRADATION, 1)
	));
	public static final ModStructureFeature ANCIENT_CITY_POOL = new ModStructureFeature("minecraft:ancient_city", new JigsawFeature(StructurePoolFeatureConfig.CODEC, -51, true, false, context -> true));
	public static final RegistryKey<ConfiguredStructureFeature<?, ?>> ANCIENT_CITY_CONFIGURED_KEY = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, new Identifier("ancient_city"));
	public static final ModConfiguredStructure ANCIENT_CITY_CONFIGURED = new ModConfiguredStructure(ANCIENT_CITY_CONFIGURED_KEY, ANCIENT_CITY_POOL, new ModStructurePoolConfig(CITY_CENTER, 7), ModBiomeTags.ANCIENT_CITY_HAS_STRUCTURE);
	public static final RegistryKey<StructureSet> ANCIENT_CITIES_KEY = RegistryKey.of(Registry.STRUCTURE_SET_KEY, new Identifier("ancient_cities"));
	public static final ModStructureSet ANCIENT_CITIES = new ModStructureSet(ANCIENT_CITIES_KEY, ANCIENT_CITY_CONFIGURED, new RandomSpreadStructurePlacement(24, 8, SpreadType.LINEAR, 20083232));

	public static final RegistryKey<Biome> MANGROVE_SWAMP = RegistryKey.of(Registry.BIOME_KEY, ID("minecraft:mangrove_swamp"));
	public static final RegistryKey<Biome> DEEP_DARK = RegistryKey.of(Registry.BIOME_KEY, ID("minecraft:deep_dark"));
	public static final RegistryKey<Biome> CHERRY_GROVE = RegistryKey.of(Registry.BIOME_KEY, ID("minecraft:cherry_grove"));
	public static final Set<RegistryKey<Biome>> CAVE_BIOMES = Set.of(BiomeKeys.LUSH_CAVES, BiomeKeys.DRIPSTONE_CAVES, DEEP_DARK);
	//</editor-fold>

	//<editor-fold desc="Origins Powers">
	public static final PowerType<?> DRAGON_WONT_HARM_POWER = new PowerTypeReference<>(ID("dragon_wont_harm"));
	public static final PowerType<?> DRAGON_WONT_LAUNCH_POWER = new PowerTypeReference<>(ID("dragon_wont_launch"));
	public static final PowerType<?> CHORUS_IMMUNE_POWER = new PowerTypeReference<>(ID("chorus_immune"));
	public static final PowerType<?> IDENTIFIED_SOUNDS_POWER = new PowerTypeReference<>(ID("identified_sounds"));
	public static final PowerType<?> WARDEN_NEUTRAL_POWER = new PowerTypeReference<>(ID("warden_neutral"));
	private static void RegisterOriginsPowers() {
		Register(BiggerLungsPower::createFactory);
		Register(BreatheInWaterPower::createFactory);
		Register(CannotFreezePower::createFactory);
		Register(ChorusTeleportPower::createFactory);
		Register(ElytraTexturePower::createFactory);
		Register(HuntedByAxolotlsPower::createFactory);
		Register(LactoseIntolerantPower::createFactory);
		Register(MobHostilityPower::createFactory);
		Register(PreventShiveringPower::createFactory);
		Register(PulsingSkinGlowPower::createFactory);
		Register(ScareFoxesPower::createFactory);
		Register(SkinGlowPower::createFactory);
		Register(SneakSpeedPower::createFactory);
		Register(SoftStepsPower::createFactory);
		Register(VillagersFleePower::createFactory);
		Register(WalkOnPowderSnowPower::createFactory);
		Register(WardenHeartbeatPower::createFactory);
	}
	//</editor-fold>
	private static void RegisterCommands() {
		Event<CommandRegistrationCallback> COMMAND = CommandRegistrationCallback.EVENT;
		COMMAND.register(CleanseCommand::register);
		COMMAND.register(ChorusCommand::register);
	}

	private static boolean registered = false;
	private static void RegisterAll() {
		if (registered) return;
		registered = true;
		OxidationScale.Initialize();
		//<editor-fold desc="Boats">
		Register("mod_boat", MOD_BOAT_ENTITY, List.of(EN_US.Boat("Mod")));
		Register("minecraft:chest_boat", CHEST_BOAT_ENTITY, List.of(EN_US.ChestBoat()));
		Register("mod_chest_boat", MOD_CHEST_BOAT_ENTITY, List.of(EN_US.ChestBoat("Mod")));
		Register("minecraft:oak_chest_boat", OAK_CHEST_BOAT, List.of(EN_US.ChestBoat(EN_US.Oak())));
		Register("minecraft:spruce_chest_boat", SPRUCE_CHEST_BOAT, List.of(EN_US.ChestBoat(EN_US.Spruce())));
		Register("minecraft:birch_chest_boat", BIRCH_CHEST_BOAT, List.of(EN_US.ChestBoat(EN_US.Birch())));
		Register("minecraft:jungle_chest_boat", JUNGLE_CHEST_BOAT, List.of(EN_US.ChestBoat(EN_US.Jungle())));
		Register("minecraft:acacia_chest_boat", ACACIA_CHEST_BOAT, List.of(EN_US.ChestBoat(EN_US.Acacia())));
		Register("minecraft:dark_oak_chest_boat", DARK_OAK_CHEST_BOAT, List.of(EN_US.ChestBoat(EN_US.DarkOak())));
		//</editor-fold>
		Register("woodcutting", WOODCUTTING_RECIPE_SERIALIZER);
		//<editor-fold desc="Acacia">
		Register("acacia_bookshelf", ACACIA_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Acacia())));
		Register("acacia_ladder", ACACIA_LADDER, List.of(EN_US.Ladder(EN_US.Acacia())));
		Register("acacia_woodcutter", ACACIA_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Acacia())));
		Register("acacia_barrel", ACACIA_BARREL, List.of(EN_US.Barrel(EN_US.Acacia())));
		Register("acacia_lectern", ACACIA_LECTERN, List.of(EN_US.Lectern(EN_US.Acacia())));
		Register("minecraft:acacia_hanging_sign", "minecraft:acacia_wall_hanging_sign", ACACIA_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Acacia())));
		//</editor-fold>
		//<editor-fold desc="Birch">
		Register("birch_bookshelf", BIRCH_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Birch())));
		Register("birch_ladder", BIRCH_LADDER, List.of(EN_US.Ladder(EN_US.Birch())));
		Register("birch_woodcutter", BIRCH_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Birch())));
		Register("birch_barrel", BIRCH_BARREL, List.of(EN_US.Barrel(EN_US.Birch())));
		Register("birch_lectern", BIRCH_LECTERN, List.of(EN_US.Lectern(EN_US.Birch())));
		Register("minecraft:birch_hanging_sign", "minecraft:birch_wall_hanging_sign", BIRCH_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Birch())));
		//</editor-fold>
		//<editor-fold desc="Dark Oak">
		Register("dark_oak_bookshelf", DARK_OAK_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.DarkOak())));
		Register("dark_oak_ladder", DARK_OAK_LADDER, List.of(EN_US.Ladder(EN_US.DarkOak())));
		Register("dark_oak_woodcutter", DARK_OAK_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.DarkOak())));
		Register("dark_oak_barrel", DARK_OAK_BARREL, List.of(EN_US.Barrel(EN_US.DarkOak())));
		Register("dark_oak_lectern", DARK_OAK_LECTERN, List.of(EN_US.Lectern(EN_US.DarkOak())));
		Register("minecraft:dark_oak_hanging_sign", "minecraft:dark_oak_wall_hanging_sign", DARK_OAK_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.DarkOak())));
		//</editor-fold>
		//<editor-fold desc="Jungle">
		Register("jungle_bookshelf", JUNGLE_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Jungle())));
		Register("jungle_ladder", JUNGLE_LADDER, List.of(EN_US.Ladder(EN_US.Jungle())));
		Register("jungle_woodcutter", JUNGLE_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Jungle())));
		Register("jungle_barrel", JUNGLE_BARREL, List.of(EN_US.Barrel(EN_US.Jungle())));
		Register("jungle_lectern", JUNGLE_LECTERN, List.of(EN_US.Lectern(EN_US.Jungle())));
		Register("minecraft:jungle_hanging_sign", "minecraft:jungle_wall_hanging_sign", JUNGLE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Jungle())));
		//</editor-fold>
		//<editor-fold desc="Oak">
		Register("woodcutter", WOODCUTTER, List.of(EN_US.Woodcutter()));
		Register("oak_barrel", OAK_BARREL, List.of(EN_US.Barrel(EN_US.Oak())));
		Register("minecraft:oak_hanging_sign", "minecraft:oak_wall_hanging_sign", OAK_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Oak())));
		//</editor-fold>
		//<editor-fold desc="Spruce">
		Register("spruce_bookshelf", SPRUCE_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Spruce())));
		Register("spruce_ladder", SPRUCE_LADDER, List.of(EN_US.Ladder(EN_US.Spruce())));
		Register("spruce_woodcutter", SPRUCE_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Spruce())));
		Register("spruce_lectern", SPRUCE_LECTERN, List.of(EN_US.Lectern(EN_US.Spruce())));
		Register("minecraft:spruce_hanging_sign", "minecraft:spruce_wall_hanging_sign", SPRUCE_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Spruce())));
		//</editor-fold>
		//<editor-fold desc="Crimson">
		Register("crimson_bookshelf", CRIMSON_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Crimson())));
		Register("crimson_ladder", CRIMSON_LADDER, List.of(EN_US.Ladder(EN_US.Crimson())));
		Register("crimson_woodcutter", CRIMSON_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Crimson())));
		Register("crimson_barrel", CRIMSON_BARREL, List.of(EN_US.Barrel(EN_US.Crimson())));
		Register("crimson_lectern", CRIMSON_LECTERN, List.of(EN_US.Lectern(EN_US.Crimson())));
		Register("minecraft:crimson_hanging_sign", "minecraft:crimson_wall_hanging_sign", CRIMSON_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Crimson())));
		//</editor-fold>
		//<editor-fold desc="Warped">
		Register("warped_bookshelf", WARPED_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Warped())));
		Register("warped_ladder", WARPED_LADDER, List.of(EN_US.Ladder(EN_US.Warped())));
		Register("warped_woodcutter", WARPED_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Warped())));
		Register("warped_barrel", WARPED_BARREL, List.of(EN_US.Barrel(EN_US.Warped())));
		Register("warped_lectern", WARPED_LECTERN, List.of(EN_US.Lectern(EN_US.Warped())));
		Register("minecraft:warped_hanging_sign", "minecraft:warped_wall_hanging_sign", WARPED_HANGING_SIGN, List.of(EN_US._HangingSign(EN_US.Warped())));
		//</editor-fold>
		//<editor-fold desc="Light Sources">
		Register("glow_flame", UNDERWATER_TORCH_GLOW);
		Register("underwater_torch", "underwater_wall_torch", UNDERWATER_TORCH, List.of(EN_US.Underwater_Torch()));
		Register("unlit_torch", "unlit_wall_torch", UNLIT_TORCH, List.of(EN_US._Torch(EN_US.Unlit())));
		Register("unlit_soul_torch", "unlit_soul_wall_torch", UNLIT_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Unlit())));
		Register("unlit_lantern", UNLIT_LANTERN, List.of(EN_US.Lantern(EN_US.Unlit())));
		Register("unlit_soul_lantern", UNLIT_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.Unlit())));
		//</editor-fold>
		//<editor-fold desc="Soul & Ender Fire">
		Register("small_soul_flame", SMALL_SOUL_FLAME_PARTICLE);
		Register("ender_fire_flame", ENDER_FIRE_FLAME_PARTICLE);
		Register("small_ender_flame", SMALL_ENDER_FLAME_PARTICLE);
		Register("soul_candle", SOUL_CANDLE, List.of(EN_US.Candle(EN_US.Soul())));
		Register("ender_candle", ENDER_CANDLE, List.of(EN_US.Candle(EN_US.Ender())));
		Register("soul_candle_cake", SOUL_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Soul(EN_US.with(EN_US.Cake())))));
		Register("ender_candle_cake", ENDER_CANDLE_CAKE, List.of(EN_US.Candle(EN_US.Ender(EN_US.with(EN_US.Cake())))));
		Register("ender_torch", "ender_wall_torch", ENDER_TORCH, List.of(EN_US.EnderTorch()));
		Register("ender_lantern", ENDER_LANTERN, List.of(EN_US.EnderLantern()));
		Register("ender_campfire", ENDER_CAMPFIRE, List.of(EN_US.Campfire(EN_US.Ender())));
		//</editor-fold>
		//<editor-fold desc="Extended Stone">
		Register("polished_andesite_wall", POLISHED_ANDESITE_WALL, List.of(EN_US.Wall(EN_US.PolishedAndesite())));
		Register("polished_diorite_wall", POLISHED_DIORITE_WALL, List.of(EN_US.Wall(EN_US.PolishedDiorite())));
		Register("polished_granite_wall", POLISHED_GRANITE_WALL, List.of(EN_US.Wall(EN_US.PolishedGranite())));
		Register("smooth_sandstone_wall", SMOOTH_SANDSTONE_WALL, List.of(EN_US.Wall(EN_US.SmoothSandstone())));
		Register("smooth_red_sandstone_wall", SMOOTH_RED_SANDSTONE_WALL, List.of(EN_US.Wall(EN_US.SmoothRedSandstone())));
		Register("dark_prismarine_wall", DARK_PRISMARINE_WALL, List.of(EN_US.Wall(EN_US.DarkPrismarine())));
		//</editor-fold>
		//<editor-fold desc="Calcite">
		Register("calcite_stairs", CALCITE_STAIRS, List.of(EN_US.Stairs(EN_US.Calcite())));
		Register("calcite_slab", CALCITE_SLAB, List.of(EN_US.Slab(EN_US.Calcite())));
		Register("calcite_wall", CALCITE_WALL, List.of(EN_US.Wall(EN_US.Calcite())));
		Register("smooth_calcite", SMOOTH_CALCITE, List.of(EN_US.Calcite(EN_US.Smooth())));
		Register("smooth_calcite_stairs", SMOOTH_CALCITE_STAIRS, List.of(EN_US.Stairs(EN_US.Calcite(EN_US.Smooth()))));
		Register("smooth_calcite_slab", SMOOTH_CALCITE_SLAB, List.of(EN_US.Slab(EN_US.Calcite(EN_US.Smooth()))));
		Register("smooth_calcite_wall", SMOOTH_CALCITE_WALL, List.of(EN_US.Wall(EN_US.Calcite(EN_US.Smooth()))));
		Register("calcite_bricks", CALCITE_BRICKS, List.of(EN_US.Bricks(EN_US.Calcite())));
		Register("calcite_brick_stairs", CALCITE_BRICK_STAIRS, List.of(EN_US.BrickStairs(EN_US.Calcite())));
		Register("calcite_brick_slab", CALCITE_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.Calcite())));
		Register("calcite_brick_wall", CALCITE_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.Calcite())));
		//</editor-fold>
		//<editor-fold desc="Dripstone">
		Register("dripstone_stairs", DRIPSTONE_STAIRS, List.of(EN_US.Stairs(EN_US.Dripstone())));
		Register("dripstone_slab", DRIPSTONE_SLAB, List.of(EN_US.Slab(EN_US.Dripstone())));
		Register("dripstone_wall", DRIPSTONE_WALL, List.of(EN_US.Wall(EN_US.Dripstone())));
		Register("smooth_dripstone", SMOOTH_DRIPSTONE, List.of(EN_US.Dripstone(EN_US.Smooth())));
		Register("smooth_dripstone_stairs", SMOOTH_DRIPSTONE_STAIRS, List.of(EN_US.Stairs(EN_US.Dripstone(EN_US.Smooth()))));
		Register("smooth_dripstone_slab", SMOOTH_DRIPSTONE_SLAB, List.of(EN_US.Slab(EN_US.Dripstone(EN_US.Smooth()))));
		Register("smooth_dripstone_wall", SMOOTH_DRIPSTONE_WALL, List.of(EN_US.Wall(EN_US.Dripstone(EN_US.Smooth()))));
		Register("dripstone_bricks", DRIPSTONE_BRICKS, List.of(EN_US.Bricks(EN_US.Dripstone())));
		Register("dripstone_brick_stairs", DRIPSTONE_BRICK_STAIRS, List.of(EN_US.BrickStairs(EN_US.Dripstone())));
		Register("dripstone_brick_slab", DRIPSTONE_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.Dripstone())));
		Register("dripstone_brick_wall", DRIPSTONE_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.Dripstone())));
		//</editor-fold>
		//<editor-fold desc="Tuff">
		Register("tuff_stairs", TUFF_STAIRS, List.of(EN_US.Stairs(EN_US.Tuff())));
		Register("tuff_slab", TUFF_SLAB, List.of(EN_US.Slab(EN_US.Tuff())));
		Register("tuff_wall", TUFF_WALL, List.of(EN_US.Wall(EN_US.Tuff())));
		Register("smooth_tuff", SMOOTH_TUFF, List.of(EN_US.Tuff(EN_US.Smooth())));
		Register("smooth_tuff_stairs", SMOOTH_TUFF_STAIRS, List.of(EN_US.Stairs(EN_US.Tuff(EN_US.Smooth()))));
		Register("smooth_tuff_slab", SMOOTH_TUFF_SLAB, List.of(EN_US.Slab(EN_US.Tuff(EN_US.Smooth()))));
		Register("smooth_tuff_wall", SMOOTH_TUFF_WALL, List.of(EN_US.Wall(EN_US.Tuff(EN_US.Smooth()))));
		Register("tuff_bricks", TUFF_BRICKS, List.of(EN_US.Bricks(EN_US.Tuff())));
		Register("tuff_brick_stairs", TUFF_BRICK_STAIRS, List.of(EN_US.BrickStairs(EN_US.Tuff())));
		Register("tuff_brick_slab", TUFF_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.Tuff())));
		Register("tuff_brick_wall", TUFF_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.Tuff())));
		//</editor-fold>
		//<editor-fold desc="Purpur">
		Register("purpur_wall", PURPUR_WALL, List.of(EN_US.Wall(EN_US.Purpur())));
		Register("smooth_purpur", SMOOTH_PURPUR, List.of(EN_US.Purpur(EN_US.Smooth())));
		Register("smooth_purpur_stairs", SMOOTH_PURPUR_STAIRS, List.of(EN_US.Stairs(EN_US.Purpur(EN_US.Smooth()))));
		Register("smooth_purpur_slab", SMOOTH_PURPUR_SLAB, List.of(EN_US.Slab(EN_US.Purpur(EN_US.Smooth()))));
		Register("smooth_purpur_wall", SMOOTH_PURPUR_WALL, List.of(EN_US.Wall(EN_US.Purpur(EN_US.Smooth()))));
		Register("purpur_bricks", PURPUR_BRICKS, List.of(EN_US.Bricks(EN_US.Purpur())));
		Register("purpur_brick_stairs", PURPUR_BRICK_STAIRS, List.of(EN_US.BrickStairs(EN_US.Purpur())));
		Register("purpur_brick_slab", PURPUR_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.Purpur())));
		Register("purpur_brick_wall", PURPUR_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.Purpur())));
		//</editor-fold>
		//<editor-fold desc="Obsidian">
		Register("obsidian_stairs", OBSIDIAN_STAIRS, List.of(EN_US.Stairs(EN_US.Obsidian())));
		Register("obsidian_slab", OBSIDIAN_SLAB, List.of(EN_US.Slab(EN_US.Obsidian())));
		Register("obsidian_wall", OBSIDIAN_WALL, List.of(EN_US.Wall(EN_US.Obsidian())));
		Register("crying_obsidian_stairs", CRYING_OBSIDIAN_STAIRS, List.of(EN_US.Stairs(EN_US.CryingObsidian())));
		Register("crying_obsidian_slab", CRYING_OBSIDIAN_SLAB, List.of(EN_US.Slab(EN_US.CryingObsidian())));
		Register("crying_obsidian_wall", CRYING_OBSIDIAN_WALL, List.of(EN_US.Wall(EN_US.CryingObsidian())));
		Register("landing_obsidian_blood", LANDING_OBSIDIAN_BLOOD);
		Register("falling_obsidian_blood", FALLING_OBSIDIAN_BLOOD);
		Register("dripping_obsidian_blood", DRIPPING_OBSIDIAN_BLOOD);
		Register("bleeding_obsidian", BLEEDING_OBSIDIAN, List.of(EN_US.BleedingObsidian()));
		Register("bleeding_obsidian_stairs", BLEEDING_OBSIDIAN_STAIRS, List.of(EN_US.Stairs(EN_US.BleedingObsidian())));
		Register("bleeding_obsidian_slab", BLEEDING_OBSIDIAN_SLAB, List.of(EN_US.Slab(EN_US.BleedingObsidian())));
		Register("bleeding_obsidian_wall", BLEEDING_OBSIDIAN_WALL, List.of(EN_US.Wall(EN_US.BleedingObsidian())));
		//</editor-fold>
		//<editor-fold desc="Amethyst">
		Register("amethyst_stairs", AMETHYST_STAIRS, List.of(EN_US.AmethystStairs()));
		Register("amethyst_slab", AMETHYST_SLAB, List.of(EN_US.AmethystSlab()));
		Register("amethyst_wall", AMETHYST_WALL, List.of(EN_US.AmethystWall()));
		Register("amethyst_crystal_block", AMETHYST_CRYSTAL_BLOCK, List.of(EN_US.CrystalBlock(EN_US.Amethyst())));
		Register("amethyst_crystal_stairs", AMETHYST_CRYSTAL_STAIRS, List.of(EN_US.CrystalSlab(EN_US.Amethyst())));
		Register("amethyst_crystal_slab", AMETHYST_CRYSTAL_SLAB, List.of(EN_US.CrystalStairs(EN_US.Amethyst())));
		Register("amethyst_crystal_wall", AMETHYST_CRYSTAL_WALL, List.of(EN_US.CrystalWall(EN_US.Amethyst())));
		Register("amethyst_bricks", AMETHYST_BRICKS, List.of(EN_US.AmethystBricks()));
		Register("amethyst_brick_stairs", AMETHYST_BRICK_STAIRS, List.of(EN_US.BrickStairs(EN_US.Amethyst())));
		Register("amethyst_brick_slab", AMETHYST_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.Amethyst())));
		Register("amethyst_brick_wall", AMETHYST_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.Amethyst())));
		Register("amethyst_shovel", AMETHYST_SHOVEL, List.of(EN_US.Shovel(EN_US.Amethyst())));
		Register("amethyst_pickaxe", AMETHYST_PICKAXE, List.of(EN_US.Pickaxe(EN_US.Amethyst())));
		Register("amethyst_axe", AMETHYST_AXE, List.of(EN_US.Axe(EN_US.Amethyst())));
		Register("amethyst_hoe", AMETHYST_HOE, List.of(EN_US.Hoe(EN_US.Amethyst())));
		Register("amethyst_sword", AMETHYST_SWORD, List.of(EN_US.Sword(EN_US.Amethyst())));
		Register("amethyst_knife", AMETHYST_KNIFE, List.of(EN_US.Knife(EN_US.Amethyst())));
		Register("amethyst_helmet", AMETHYST_HELMET, List.of(EN_US.Helmet(EN_US.Amethyst())));
		Register("amethyst_chestplate", AMETHYST_CHESTPLATE, List.of(EN_US.Chestplate(EN_US.Amethyst())));
		Register("amethyst_leggings", AMETHYST_LEGGINGS, List.of(EN_US.Leggings(EN_US.Amethyst())));
		Register("amethyst_boots", AMETHYST_BOOTS, List.of(EN_US.Boots(EN_US.Amethyst())));
		Register("amethyst_horse_armor", AMETHYST_HORSE_ARMOR, List.of(EN_US.HorseArmor(EN_US.Amethyst())));
		//</editor-fold>
		//<editor-fold desc="Glass">
		Register("tinted_glass_pane", TINTED_GLASS_PANE, List.of(EN_US.GlassPane(EN_US.TintedGlass())));
		Register("tinted_glass_slab", TINTED_GLASS_SLAB, List.of(EN_US.GlassSlab(EN_US.TintedGlass())));
		Register("tinted_glass_trapdoor", TINTED_GLASS_TRAPDOOR, List.of(EN_US.GlassTrapdoor(EN_US.TintedGlass())));
		Register("tinted_goggles", TINTED_GOGGLES, List.of(EN_US.TintedGoggles()));
		Register("tinted_goggles", TINTED_GOGGLES_EFFECT, List.of(EN_US.TintedGoggles()));
		Register("glass_slab", GLASS_SLAB, List.of(EN_US.Slab(EN_US.Glass())));
		Register("glass_trapdoor", GLASS_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Glass())));
		for (DyeColor color : DyeColor.values()) {
			Register(color.getName() + "_stained_glass_slab", STAINED_GLASS_SLABS.get(color), List.of(EN_US.GlassSlab(EN_US.Color(color))));
		}
		for (DyeColor color : DyeColor.values()) {
			Register(color.getName() + "_stained_glass_trapdoor", STAINED_GLASS_TRAPDOORS.get(color), List.of(EN_US.GlassTrapdoor(EN_US.Color(color))));
		}
		//</editor-fold>
		//<editor-fold desc="Emerald">
		Register("emerald_bricks", EMERALD_BRICKS, List.of(EN_US.Bricks(EN_US.Emerald())));
		Register("emerald_brick_stairs", EMERALD_BRICK_STAIRS, List.of(EN_US.BrickStairs(EN_US.Emerald())));
		Register("emerald_brick_slab", EMERALD_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.Emerald())));
		Register("emerald_brick_wall", EMERALD_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.Emerald())));
		Register("cut_emerald", CUT_EMERALD, List.of(EN_US.CutEmerald()));
		Register("cut_emerald_stairs", CUT_EMERALD_STAIRS, List.of(EN_US.Stairs(EN_US.CutEmerald())));
		Register("cut_emerald_slab", CUT_EMERALD_SLAB, List.of(EN_US.Slab(EN_US.CutEmerald())));
		Register("cut_emerald_wall", CUT_EMERALD_WALL, List.of(EN_US.Wall(EN_US.CutEmerald())));
		Register("emerald_shovel", EMERALD_SHOVEL, List.of(EN_US.Shovel(EN_US.Emerald())));
		Register("emerald_pickaxe", EMERALD_PICKAXE, List.of(EN_US.Pickaxe(EN_US.Emerald())));
		Register("emerald_axe", EMERALD_AXE, List.of(EN_US.Axe(EN_US.Emerald())));
		Register("emerald_hoe", EMERALD_HOE, List.of(EN_US.Hoe(EN_US.Emerald())));
		Register("emerald_sword", EMERALD_SWORD, List.of(EN_US.Sword(EN_US.Emerald())));
		Register("emerald_knife", EMERALD_KNIFE, List.of(EN_US.Knife(EN_US.Emerald())));
		Register("emerald_helmet", EMERALD_HELMET, List.of(EN_US.Helmet(EN_US.Emerald())));
		Register("emerald_chestplate", EMERALD_CHESTPLATE, List.of(EN_US.Chestplate(EN_US.Emerald())));
		Register("emerald_leggings", EMERALD_LEGGINGS, List.of(EN_US.Leggings(EN_US.Emerald())));
		Register("emerald_boots", EMERALD_BOOTS, List.of(EN_US.Boots(EN_US.Emerald())));
		Register("emerald_horse_armor", EMERALD_HORSE_ARMOR, List.of(EN_US.HorseArmor(EN_US.Emerald())));
		//</editor-fold>
		//<editor-fold desc="Diamond">
		Register("diamond_stairs", DIAMOND_STAIRS, List.of(EN_US.Stairs(EN_US.Diamond())));
		Register("diamond_slab", DIAMOND_SLAB, List.of(EN_US.Slab(EN_US.Diamond())));
		Register("diamond_wall", DIAMOND_WALL, List.of(EN_US.Wall(EN_US.Diamond())));
		Register("diamond_bricks", DIAMOND_BRICKS, List.of(EN_US.Bricks(EN_US.Diamond())));
		Register("diamond_brick_stairs", DIAMOND_BRICK_STAIRS, List.of(EN_US.BrickStairs(EN_US.Diamond())));
		Register("diamond_brick_slab", DIAMOND_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.Diamond())));
		Register("diamond_brick_wall", DIAMOND_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.Diamond())));
		//</editor-fold>
		//<editor-fold desc="Quartz">
		Register("quartz_crystal_block", QUARTZ_CRYSTAL_BLOCK, List.of(EN_US.CrystalBlock(EN_US.Quartz())));
		Register("quartz_crystal_stairs", QUARTZ_CRYSTAL_STAIRS, List.of(EN_US.CrystalStairs(EN_US.Quartz())));
		Register("quartz_crystal_slab", QUARTZ_CRYSTAL_SLAB, List.of(EN_US.CrystalSlab(EN_US.Quartz())));
		Register("quartz_crystal_wall", QUARTZ_CRYSTAL_WALL, List.of(EN_US.CrystalWall(EN_US.Quartz())));
		Register("smooth_quartz_wall", SMOOTH_QUARTZ_WALL, List.of(EN_US.Wall(EN_US.Quartz(EN_US.Smooth()))));
		Register("quartz_wall", QUARTZ_WALL, List.of(EN_US.Wall(EN_US.Quartz())));
		Register("quartz_brick_stairs", QUARTZ_BRICK_STAIRS, List.of(EN_US.BrickStairs(EN_US.Quartz())));
		Register("quartz_brick_slab", QUARTZ_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.Quartz())));
		Register("quartz_brick_wall", QUARTZ_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.Quartz())));
		Register("quartz_shovel", QUARTZ_SHOVEL, List.of(EN_US.Shovel(EN_US.Quartz())));
		Register("quartz_pickaxe", QUARTZ_PICKAXE, List.of(EN_US.Pickaxe(EN_US.Quartz())));
		Register("quartz_axe", QUARTZ_AXE, List.of(EN_US.Axe(EN_US.Quartz())));
		Register("quartz_hoe", QUARTZ_HOE, List.of(EN_US.Hoe(EN_US.Quartz())));
		Register("quartz_sword", QUARTZ_SWORD, List.of(EN_US.Sword(EN_US.Quartz())));
		Register("quartz_knife", QUARTZ_KNIFE, List.of(EN_US.Knife(EN_US.Quartz())));
		Register("quartz_helmet", QUARTZ_HELMET, List.of(EN_US.Helmet(EN_US.Quartz())));
		Register("quartz_chestplate", QUARTZ_CHESTPLATE, List.of(EN_US.Chest(EN_US.Quartz())));
		Register("quartz_leggings", QUARTZ_LEGGINGS, List.of(EN_US.Leggings(EN_US.Quartz())));
		Register("quartz_boots", QUARTZ_BOOTS, List.of(EN_US.Boots(EN_US.Quartz())));
		Register("quartz_horse_armor", QUARTZ_HORSE_ARMOR, List.of(EN_US.HorseArmor(EN_US.Quartz())));
		//</editor-fold>
		//<editor-fold desc="Iron">
		Register("iron_flame", IRON_FLAME_PARTICLE);
		Register("iron_torch", "iron_wall_torch", IRON_TORCH, List.of(EN_US._Torch(EN_US.Iron())));
		Register("iron_soul_torch", "iron_soul_wall_torch", IRON_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Iron())));
		Register("iron_ender_torch", "iron_ender_wall_torch", IRON_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Iron())));
		Register("underwater_iron_torch", "underwater_iron_wall_torch", UNDERWATER_IRON_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Iron())));
		Register("white_iron_lantern", WHITE_IRON_LANTERN, List.of(EN_US.Lantern(EN_US.Iron())));
		Register("white_iron_soul_lantern", WHITE_IRON_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.Iron())));
		Register("white_iron_ender_lantern", WHITE_IRON_ENDER_LANTERN, List.of(EN_US.EnderLantern(EN_US.Iron())));
		Register("iron_button", IRON_BUTTON, List.of(EN_US.Button(EN_US.Iron())));
		Register("white_iron_chain", WHITE_IRON_CHAIN, List.of(EN_US.Chain(EN_US.Iron())));
		Register("iron_stairs", IRON_STAIRS, List.of(EN_US.Stairs(EN_US.Iron())));
		Register("iron_slab", IRON_SLAB, List.of(EN_US.Slab(EN_US.Iron())));
		Register("iron_wall", IRON_WALL, List.of(EN_US.Wall(EN_US.Iron())));
		Register("iron_bricks", IRON_BRICKS, List.of(EN_US.Bricks(EN_US.Iron())));
		Register("iron_brick_stairs", IRON_BRICK_STAIRS, List.of(EN_US.BrickStairs(EN_US.Iron())));
		Register("iron_brick_slab", IRON_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.Iron())));
		Register("iron_brick_wall", IRON_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.Iron())));
		Register("cut_iron", CUT_IRON, List.of(EN_US.CutIron()));
		Register("cut_iron_pillar", CUT_IRON_PILLAR, List.of(EN_US.Pillar(EN_US.CutIron())));
		Register("cut_iron_stairs", CUT_IRON_STAIRS, List.of(EN_US.Stairs(EN_US.CutIron())));
		Register("cut_iron_slab", CUT_IRON_SLAB, List.of(EN_US.Slab(EN_US.CutIron())));
		Register("cut_iron_wall", CUT_IRON_WALL, List.of(EN_US.Wall(EN_US.CutIron())));
		//</editor-fold>
		//<editor-fold desc="Gold">
		Register("gold_flame", GOLD_FLAME_PARTICLE);
		Register("gold_torch", "gold_wall_torch", GOLD_TORCH, List.of(EN_US._Torch(EN_US.Gold())));
		Register("gold_soul_torch", "gold_soul_wall_torch", GOLD_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Gold())));
		Register("gold_ender_torch", "gold_ender_wall_torch", GOLD_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Gold())));
		Register("underwater_gold_torch", "underwater_gold_wall_torch", UNDERWATER_GOLD_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Gold())));
		Register("gold_lantern", GOLD_LANTERN, List.of(EN_US.Lantern(EN_US.Gold())));
		Register("gold_soul_lantern", GOLD_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.Gold())));
		Register("gold_ender_lantern", GOLD_ENDER_LANTERN, List.of(EN_US.EnderLantern(EN_US.Gold())));
		Register("gold_button", GOLD_BUTTON, List.of(EN_US.Button(EN_US.Gold())));
		Register("gold_chain", GOLD_CHAIN, List.of(EN_US.Chain(EN_US.Gold())));
		Register("gold_bars", GOLD_BARS, List.of(EN_US.Bars(EN_US.Gold())));
		Register("gold_stairs", GOLD_STAIRS, List.of(EN_US.Stairs(EN_US.Gold())));
		Register("gold_slab", GOLD_SLAB, List.of(EN_US.Slab(EN_US.Gold())));
		Register("gold_wall", GOLD_WALL, List.of(EN_US.Wall(EN_US.Gold())));
		Register("gold_bricks", GOLD_BRICKS, List.of(EN_US.Bricks(EN_US.Gold())));
		Register("gold_brick_stairs", GOLD_BRICK_STAIRS, List.of(EN_US.BrickStairs(EN_US.Gold())));
		Register("gold_brick_slab", GOLD_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.Gold())));
		Register("gold_brick_wall", GOLD_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.Gold())));
		Register("cut_gold", CUT_GOLD, List.of(EN_US.CutGold()));
		Register("cut_gold_pillar", CUT_GOLD_PILLAR, List.of(EN_US.Pillar(EN_US.CutGold())));
		Register("cut_gold_stairs", CUT_GOLD_STAIRS, List.of(EN_US.Stairs(EN_US.CutGold())));
		Register("cut_gold_slab", CUT_GOLD_SLAB, List.of(EN_US.Slab(EN_US.CutGold())));
		Register("cut_gold_wall", CUT_GOLD_WALL, List.of(EN_US.Wall(EN_US.CutGold())));
		//</editor-fold>
		//<editor-fold desc="Copper">
		Register("copper_nugget", COPPER_NUGGET, List.of(EN_US.Nugget(EN_US.Copper())));
		Register("copper_flame", COPPER_FLAME_PARTICLE);
		Register("copper_torch", "copper_wall_torch", COPPER_TORCH, List.of(EN_US._Torch(EN_US.Copper())));
		Register("exposed_copper_torch", "exposed_copper_wall_torch", EXPOSED_COPPER_TORCH, List.of(EN_US._Torch(EN_US.ExposedCopper())));
		Register("weathered_copper_torch", "weathered_copper_wall_torch", WEATHERED_COPPER_TORCH, List.of(EN_US._Torch(EN_US.WeatheredCopper())));
		Register("oxidized_copper_torch", "oxidized_copper_wall_torch", OXIDIZED_COPPER_TORCH, List.of(EN_US._Torch(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_TORCH, EXPOSED_COPPER_TORCH, WEATHERED_COPPER_TORCH, OXIDIZED_COPPER_TORCH);
		Register("waxed_copper_torch", "waxed_copper_wall_torch", WAXED_COPPER_TORCH, List.of(EN_US._Torch(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_torch", "waxed_exposed_copper_wall_torch", WAXED_EXPOSED_COPPER_TORCH, List.of(EN_US._Torch(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_torch", "waxed_weathered_copper_wall_torch", WAXED_WEATHERED_COPPER_TORCH, List.of(EN_US._Torch(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_torch", "waxed_oxidized_copper_wall_torch", WAXED_OXIDIZED_COPPER_TORCH, List.of(EN_US._Torch(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_TORCH, WAXED_COPPER_TORCH);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_TORCH, WAXED_EXPOSED_COPPER_TORCH);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_TORCH, WAXED_WEATHERED_COPPER_TORCH);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_TORCH, WAXED_OXIDIZED_COPPER_TORCH);
		Register("copper_soul_torch", "copper_soul_wall_torch", COPPER_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Copper())));
		Register("exposed_copper_soul_torch", "exposed_copper_soul_wall_torch", EXPOSED_COPPER_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.ExposedCopper())));
		Register("weathered_copper_soul_torch", "weathered_copper_soul_wall_torch", WEATHERED_COPPER_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.WeatheredCopper())));
		Register("oxidized_copper_soul_torch", "oxidized_copper_soul_wall_torch", OXIDIZED_COPPER_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_SOUL_TORCH, EXPOSED_COPPER_SOUL_TORCH, WEATHERED_COPPER_SOUL_TORCH, OXIDIZED_COPPER_SOUL_TORCH);
		Register("waxed_copper_soul_torch", "waxed_copper_soul_wall_torch", WAXED_COPPER_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_soul_torch", "waxed_exposed_copper_soul_wall_torch", WAXED_EXPOSED_COPPER_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_soul_torch", "waxed_weathered_copper_soul_wall_torch", WAXED_WEATHERED_COPPER_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_soul_torch", "waxed_oxidized_copper_soul_wall_torch", WAXED_OXIDIZED_COPPER_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_SOUL_TORCH, WAXED_COPPER_SOUL_TORCH);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_SOUL_TORCH, WAXED_EXPOSED_COPPER_SOUL_TORCH);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_SOUL_TORCH, WAXED_WEATHERED_COPPER_SOUL_TORCH);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_SOUL_TORCH, WAXED_OXIDIZED_COPPER_SOUL_TORCH);
		Register("copper_ender_torch", "copper_ender_wall_torch", COPPER_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Copper())));
		Register("exposed_copper_ender_torch", "exposed_copper_ender_wall_torch", EXPOSED_COPPER_ENDER_TORCH, List.of(EN_US.SoulTorch(EN_US.ExposedCopper())));
		Register("weathered_copper_ender_torch", "weathered_copper_ender_wall_torch", WEATHERED_COPPER_ENDER_TORCH, List.of(EN_US.SoulTorch(EN_US.WeatheredCopper())));
		Register("oxidized_copper_ender_torch", "oxidized_copper_ender_wall_torch", OXIDIZED_COPPER_ENDER_TORCH, List.of(EN_US.SoulTorch(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_ENDER_TORCH, EXPOSED_COPPER_ENDER_TORCH, WEATHERED_COPPER_ENDER_TORCH, OXIDIZED_COPPER_ENDER_TORCH);
		Register("waxed_copper_ender_torch", "waxed_copper_ender_wall_torch", WAXED_COPPER_ENDER_TORCH, List.of(EN_US.SoulTorch(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_ender_torch", "waxed_exposed_copper_ender_wall_torch", WAXED_EXPOSED_COPPER_ENDER_TORCH, List.of(EN_US.SoulTorch(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_ender_torch", "waxed_weathered_copper_ender_wall_torch", WAXED_WEATHERED_COPPER_ENDER_TORCH, List.of(EN_US.SoulTorch(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_ender_torch", "waxed_oxidized_copper_ender_wall_torch", WAXED_OXIDIZED_COPPER_ENDER_TORCH, List.of(EN_US.SoulTorch(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_ENDER_TORCH, WAXED_COPPER_ENDER_TORCH);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_ENDER_TORCH, WAXED_EXPOSED_COPPER_ENDER_TORCH);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_ENDER_TORCH, WAXED_WEATHERED_COPPER_ENDER_TORCH);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_ENDER_TORCH, WAXED_OXIDIZED_COPPER_ENDER_TORCH);
		Register("underwater_copper_torch", "underwater_underwater_copper_wall_torch", UNDERWATER_COPPER_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Copper())));
		Register("exposed_underwater_copper_torch", "exposed_underwater_copper_wall_torch", EXPOSED_UNDERWATER_COPPER_TORCH, List.of(EN_US._Torch(EN_US.ExposedCopper())));
		Register("weathered_underwater_copper_torch", "weathered_underwater_copper_wall_torch", WEATHERED_UNDERWATER_COPPER_TORCH, List.of(EN_US._Torch(EN_US.WeatheredCopper())));
		Register("oxidized_underwater_copper_torch", "oxidized_underwater_copper_wall_torch", OXIDIZED_UNDERWATER_COPPER_TORCH, List.of(EN_US._Torch(EN_US.OxidizedCopper())));
		OxidationScale.Register(UNDERWATER_COPPER_TORCH, EXPOSED_UNDERWATER_COPPER_TORCH, WEATHERED_UNDERWATER_COPPER_TORCH, OXIDIZED_UNDERWATER_COPPER_TORCH);
		Register("waxed_underwater_copper_torch", "waxed_underwater_copper_wall_torch", WAXED_UNDERWATER_COPPER_TORCH, List.of(EN_US._Torch(EN_US.WaxedCopper())));
		Register("waxed_exposed_underwater_copper_torch", "waxed_exposed_underwater_copper_wall_torch", WAXED_EXPOSED_UNDERWATER_COPPER_TORCH, List.of(EN_US._Torch(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_underwater_copper_torch", "waxed_weathered_underwater_copper_wall_torch", WAXED_WEATHERED_UNDERWATER_COPPER_TORCH, List.of(EN_US._Torch(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_underwater_copper_torch", "waxed_oxidized_underwater_copper_wall_torch", WAXED_OXIDIZED_UNDERWATER_COPPER_TORCH, List.of(EN_US._Torch(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(UNDERWATER_COPPER_TORCH, WAXED_UNDERWATER_COPPER_TORCH);
		OxidationScale.RegisterWaxed(EXPOSED_UNDERWATER_COPPER_TORCH, WAXED_EXPOSED_UNDERWATER_COPPER_TORCH);
		OxidationScale.RegisterWaxed(WEATHERED_UNDERWATER_COPPER_TORCH, WAXED_WEATHERED_UNDERWATER_COPPER_TORCH);
		OxidationScale.RegisterWaxed(OXIDIZED_UNDERWATER_COPPER_TORCH, WAXED_OXIDIZED_UNDERWATER_COPPER_TORCH);
		Register("copper_lantern", COPPER_LANTERN, List.of(EN_US.Lantern(EN_US.Copper())));
		Register("exposed_copper_lantern", EXPOSED_COPPER_LANTERN, List.of(EN_US.Lantern(EN_US.ExposedCopper())));
		Register("weathered_copper_lantern", WEATHERED_COPPER_LANTERN, List.of(EN_US.Lantern(EN_US.WeatheredCopper())));
		Register("oxidized_copper_lantern", OXIDIZED_COPPER_LANTERN, List.of(EN_US.Lantern(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_LANTERN, EXPOSED_COPPER_LANTERN, WEATHERED_COPPER_LANTERN, OXIDIZED_COPPER_LANTERN);
		Register("waxed_copper_lantern", WAXED_COPPER_LANTERN, List.of(EN_US.Lantern(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_lantern", WAXED_EXPOSED_COPPER_LANTERN, List.of(EN_US.Lantern(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_lantern", WAXED_WEATHERED_COPPER_LANTERN, List.of(EN_US.Lantern(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_lantern", WAXED_OXIDIZED_COPPER_LANTERN, List.of(EN_US.Lantern(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_LANTERN, WAXED_COPPER_LANTERN);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_LANTERN, WAXED_EXPOSED_COPPER_LANTERN);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_LANTERN, WAXED_WEATHERED_COPPER_LANTERN);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_LANTERN, WAXED_OXIDIZED_COPPER_LANTERN);
		Register("copper_soul_lantern", COPPER_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.Copper())));
		Register("exposed_copper_soul_lantern", EXPOSED_COPPER_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.ExposedCopper())));
		Register("weathered_copper_soul_lantern", WEATHERED_COPPER_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.WeatheredCopper())));
		Register("oxidized_copper_soul_lantern", OXIDIZED_COPPER_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_SOUL_LANTERN, EXPOSED_COPPER_SOUL_LANTERN, WEATHERED_COPPER_SOUL_LANTERN, OXIDIZED_COPPER_SOUL_LANTERN);
		Register("waxed_copper_soul_lantern", WAXED_COPPER_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_soul_lantern", WAXED_EXPOSED_COPPER_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_soul_lantern", WAXED_WEATHERED_COPPER_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_soul_lantern", WAXED_OXIDIZED_COPPER_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_SOUL_LANTERN, WAXED_COPPER_SOUL_LANTERN);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_SOUL_LANTERN, WAXED_EXPOSED_COPPER_SOUL_LANTERN);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_SOUL_LANTERN, WAXED_WEATHERED_COPPER_SOUL_LANTERN);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_SOUL_LANTERN, WAXED_OXIDIZED_COPPER_SOUL_LANTERN);
		Register("copper_ender_lantern", COPPER_ENDER_LANTERN, List.of(EN_US.EnderLantern(EN_US.Copper())));
		Register("exposed_copper_ender_lantern", EXPOSED_COPPER_ENDER_LANTERN, List.of(EN_US.SoulLantern(EN_US.ExposedCopper())));
		Register("weathered_copper_ender_lantern", WEATHERED_COPPER_ENDER_LANTERN, List.of(EN_US.SoulLantern(EN_US.WeatheredCopper())));
		Register("oxidized_copper_ender_lantern", OXIDIZED_COPPER_ENDER_LANTERN, List.of(EN_US.SoulLantern(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_ENDER_LANTERN, EXPOSED_COPPER_ENDER_LANTERN, WEATHERED_COPPER_ENDER_LANTERN, OXIDIZED_COPPER_ENDER_LANTERN);
		Register("waxed_copper_ender_lantern", WAXED_COPPER_ENDER_LANTERN, List.of(EN_US.SoulLantern(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_ender_lantern", WAXED_EXPOSED_COPPER_ENDER_LANTERN, List.of(EN_US.SoulLantern(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_ender_lantern", WAXED_WEATHERED_COPPER_ENDER_LANTERN, List.of(EN_US.SoulLantern(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_ender_lantern", WAXED_OXIDIZED_COPPER_ENDER_LANTERN, List.of(EN_US.SoulLantern(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_ENDER_LANTERN, WAXED_COPPER_ENDER_LANTERN);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_ENDER_LANTERN, WAXED_EXPOSED_COPPER_ENDER_LANTERN);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_ENDER_LANTERN, WAXED_WEATHERED_COPPER_ENDER_LANTERN);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_ENDER_LANTERN, WAXED_OXIDIZED_COPPER_ENDER_LANTERN);
		Register("copper_button", COPPER_BUTTON, List.of(EN_US.Button(EN_US.Copper())));
		Register("exposed_copper_button", EXPOSED_COPPER_BUTTON, List.of(EN_US.Button(EN_US.ExposedCopper())));
		Register("weathered_copper_button", WEATHERED_COPPER_BUTTON, List.of(EN_US.Button(EN_US.WeatheredCopper())));
		Register("oxidized_copper_button", OXIDIZED_COPPER_BUTTON, List.of(EN_US.Button(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_BUTTON, EXPOSED_COPPER_BUTTON, WEATHERED_COPPER_BUTTON, OXIDIZED_COPPER_BUTTON);
		Register("waxed_copper_button", WAXED_COPPER_BUTTON, List.of(EN_US.Button(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_button", WAXED_EXPOSED_COPPER_BUTTON, List.of(EN_US.Button(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_button", WAXED_WEATHERED_COPPER_BUTTON, List.of(EN_US.Button(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_button", WAXED_OXIDIZED_COPPER_BUTTON, List.of(EN_US.Button(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_BUTTON, WAXED_COPPER_BUTTON);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_BUTTON, WAXED_EXPOSED_COPPER_BUTTON);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_BUTTON, WAXED_WEATHERED_COPPER_BUTTON);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_BUTTON, WAXED_OXIDIZED_COPPER_BUTTON);
		Register("copper_chain", COPPER_CHAIN, List.of(EN_US.Chain(EN_US.Copper())));
		Register("exposed_copper_chain", EXPOSED_COPPER_CHAIN, List.of(EN_US.Chain(EN_US.ExposedCopper())));
		Register("weathered_copper_chain", WEATHERED_COPPER_CHAIN, List.of(EN_US.Chain(EN_US.WeatheredCopper())));
		Register("oxidized_copper_chain", OXIDIZED_COPPER_CHAIN, List.of(EN_US.Chain(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_CHAIN, EXPOSED_COPPER_CHAIN, WEATHERED_COPPER_CHAIN, OXIDIZED_COPPER_CHAIN);
		Register("waxed_copper_chain", WAXED_COPPER_CHAIN, List.of(EN_US.Chain(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_chain", WAXED_EXPOSED_COPPER_CHAIN, List.of(EN_US.Chain(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_chain", WAXED_WEATHERED_COPPER_CHAIN, List.of(EN_US.Chain(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_chain", WAXED_OXIDIZED_COPPER_CHAIN, List.of(EN_US.Chain(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_CHAIN, WAXED_COPPER_CHAIN);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_CHAIN, WAXED_EXPOSED_COPPER_CHAIN);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_CHAIN, WAXED_WEATHERED_COPPER_CHAIN);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_CHAIN, WAXED_OXIDIZED_COPPER_CHAIN);
		Register("copper_bars", COPPER_BARS, List.of(EN_US.Bars(EN_US.Copper())));
		Register("exposed_copper_bars", EXPOSED_COPPER_BARS, List.of(EN_US.Bars(EN_US.ExposedCopper())));
		Register("weathered_copper_bars", WEATHERED_COPPER_BARS, List.of(EN_US.Bars(EN_US.WeatheredCopper())));
		Register("oxidized_copper_bars", OXIDIZED_COPPER_BARS, List.of(EN_US.Bars(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_BARS, EXPOSED_COPPER_BARS, WEATHERED_COPPER_BARS, OXIDIZED_COPPER_BARS);
		Register("waxed_copper_bars", WAXED_COPPER_BARS, List.of(EN_US.Bars(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_bars", WAXED_EXPOSED_COPPER_BARS, List.of(EN_US.Bars(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_bars", WAXED_WEATHERED_COPPER_BARS, List.of(EN_US.Bars(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_bars", WAXED_OXIDIZED_COPPER_BARS, List.of(EN_US.Bars(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_BARS, WAXED_COPPER_BARS);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_BARS, WAXED_EXPOSED_COPPER_BARS);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_BARS, WAXED_WEATHERED_COPPER_BARS);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_BARS, WAXED_OXIDIZED_COPPER_BARS);
		Register("copper_wall", COPPER_WALL, List.of(EN_US.Wall(EN_US.Copper())));
		Register("exposed_copper_wall", EXPOSED_COPPER_WALL, List.of(EN_US.Wall(EN_US.ExposedCopper())));
		Register("weathered_copper_wall", WEATHERED_COPPER_WALL, List.of(EN_US.Wall(EN_US.WeatheredCopper())));
		Register("oxidized_copper_wall", OXIDIZED_COPPER_WALL, List.of(EN_US.Wall(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_WALL, EXPOSED_COPPER_WALL, WEATHERED_COPPER_WALL, OXIDIZED_COPPER_WALL);
		Register("waxed_copper_wall", WAXED_COPPER_WALL, List.of(EN_US.Wall(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_wall", WAXED_EXPOSED_COPPER_WALL, List.of(EN_US.Wall(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_wall", WAXED_WEATHERED_COPPER_WALL, List.of(EN_US.Wall(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_wall", WAXED_OXIDIZED_COPPER_WALL, List.of(EN_US.Wall(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_WALL, WAXED_COPPER_WALL);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_WALL, WAXED_EXPOSED_COPPER_WALL);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_WALL, WAXED_WEATHERED_COPPER_WALL);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_WALL, WAXED_OXIDIZED_COPPER_WALL);
		Register("medium_weighted_pressure_plate", MEDIUM_WEIGHTED_PRESSURE_PLATE, List.of(EN_US.WeightedPressurePlate(EN_US.Medium())));
		Register("exposed_medium_weighted_pressure_plate", EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, List.of(EN_US.WeightedPressurePlate(EN_US.Medium(EN_US.Exposed()))));
		Register("weathered_medium_weighted_pressure_plate", WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, List.of(EN_US.WeightedPressurePlate(EN_US.Medium(EN_US.Weathered()))));
		Register("oxidized_medium_weighted_pressure_plate", OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, List.of(EN_US.WeightedPressurePlate(EN_US.Medium(EN_US.Oxidized()))));
		OxidationScale.Register(MEDIUM_WEIGHTED_PRESSURE_PLATE, EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		Register("waxed_medium_weighted_pressure_plate", WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE, List.of(EN_US.WeightedPressurePlate(EN_US.Medium(EN_US.Waxed()))));
		Register("waxed_exposed_medium_weighted_pressure_plate", WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, List.of(EN_US.WeightedPressurePlate(EN_US.Medium(EN_US.Exposed(EN_US.Waxed())))));
		Register("waxed_weathered_medium_weighted_pressure_plate", WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, List.of(EN_US.WeightedPressurePlate(EN_US.Medium(EN_US.Weathered(EN_US.Waxed())))));
		Register("waxed_oxidized_medium_weighted_pressure_plate", WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, List.of(EN_US.WeightedPressurePlate(EN_US.Medium(EN_US.Oxidized(EN_US.Waxed())))));
		OxidationScale.RegisterWaxed(MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		OxidationScale.RegisterWaxed(EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		OxidationScale.RegisterWaxed(WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		OxidationScale.RegisterWaxed(OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);

		Register("copper_shovel", COPPER_SHOVEL, List.of(EN_US.Shovel(EN_US.Copper())));
		Register("exposed_copper_shovel", EXPOSED_COPPER_SHOVEL, List.of(EN_US.Shovel(EN_US.ExposedCopper())));
		Register("weathered_copper_shovel", WEATHERED_COPPER_SHOVEL, List.of(EN_US.Shovel(EN_US.WeatheredCopper())));
		Register("oxidized_copper_shovel", OXIDIZED_COPPER_SHOVEL, List.of(EN_US.Shovel(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_SHOVEL, EXPOSED_COPPER_SHOVEL, WEATHERED_COPPER_SHOVEL, OXIDIZED_COPPER_SHOVEL);
		Register("waxed_copper_shovel", WAXED_COPPER_SHOVEL, List.of(EN_US.Shovel(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_shovel", WAXED_EXPOSED_COPPER_SHOVEL, List.of(EN_US.Shovel(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_shovel", WAXED_WEATHERED_COPPER_SHOVEL, List.of(EN_US.Shovel(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_shovel", WAXED_OXIDIZED_COPPER_SHOVEL, List.of(EN_US.Shovel(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_SHOVEL, WAXED_COPPER_SHOVEL);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_SHOVEL, WAXED_EXPOSED_COPPER_SHOVEL);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_SHOVEL, WAXED_WEATHERED_COPPER_SHOVEL);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_SHOVEL, WAXED_OXIDIZED_COPPER_SHOVEL);
		Register("copper_pickaxe", COPPER_PICKAXE, List.of(EN_US.Pickaxe(EN_US.Copper())));
		Register("exposed_copper_pickaxe", EXPOSED_COPPER_PICKAXE, List.of(EN_US.Pickaxe(EN_US.ExposedCopper())));
		Register("weathered_copper_pickaxe", WEATHERED_COPPER_PICKAXE, List.of(EN_US.Pickaxe(EN_US.WeatheredCopper())));
		Register("oxidized_copper_pickaxe", OXIDIZED_COPPER_PICKAXE, List.of(EN_US.Pickaxe(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_PICKAXE, EXPOSED_COPPER_PICKAXE, WEATHERED_COPPER_PICKAXE, OXIDIZED_COPPER_PICKAXE);
		Register("waxed_copper_pickaxe", WAXED_COPPER_PICKAXE, List.of(EN_US.Pickaxe(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_pickaxe", WAXED_EXPOSED_COPPER_PICKAXE, List.of(EN_US.Pickaxe(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_pickaxe", WAXED_WEATHERED_COPPER_PICKAXE, List.of(EN_US.Pickaxe(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_pickaxe", WAXED_OXIDIZED_COPPER_PICKAXE, List.of(EN_US.Pickaxe(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_PICKAXE, WAXED_COPPER_PICKAXE);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_PICKAXE, WAXED_EXPOSED_COPPER_PICKAXE);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_PICKAXE, WAXED_WEATHERED_COPPER_PICKAXE);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_PICKAXE, WAXED_OXIDIZED_COPPER_PICKAXE);
		Register("copper_axe", COPPER_AXE, List.of(EN_US.Axe(EN_US.Copper())));
		Register("exposed_copper_axe", EXPOSED_COPPER_AXE, List.of(EN_US.Axe(EN_US.ExposedCopper())));
		Register("weathered_copper_axe", WEATHERED_COPPER_AXE, List.of(EN_US.Axe(EN_US.WeatheredCopper())));
		Register("oxidized_copper_axe", OXIDIZED_COPPER_AXE, List.of(EN_US.Axe(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_AXE, EXPOSED_COPPER_AXE, WEATHERED_COPPER_AXE, OXIDIZED_COPPER_AXE);
		Register("waxed_copper_axe", WAXED_COPPER_AXE, List.of(EN_US.Axe(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_axe", WAXED_EXPOSED_COPPER_AXE, List.of(EN_US.Axe(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_axe", WAXED_WEATHERED_COPPER_AXE, List.of(EN_US.Axe(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_axe", WAXED_OXIDIZED_COPPER_AXE, List.of(EN_US.Axe(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_AXE, WAXED_COPPER_AXE);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_AXE, WAXED_EXPOSED_COPPER_AXE);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_AXE, WAXED_WEATHERED_COPPER_AXE);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_AXE, WAXED_OXIDIZED_COPPER_AXE);
		Register("copper_hoe", COPPER_HOE, List.of(EN_US.Hoe(EN_US.Copper())));
		Register("exposed_copper_hoe", EXPOSED_COPPER_HOE, List.of(EN_US.Hoe(EN_US.ExposedCopper())));
		Register("weathered_copper_hoe", WEATHERED_COPPER_HOE, List.of(EN_US.Hoe(EN_US.WeatheredCopper())));
		Register("oxidized_copper_hoe", OXIDIZED_COPPER_HOE, List.of(EN_US.Hoe(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_HOE, EXPOSED_COPPER_HOE, WEATHERED_COPPER_HOE, OXIDIZED_COPPER_HOE);
		Register("waxed_copper_hoe", WAXED_COPPER_HOE, List.of(EN_US.Hoe(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_hoe", WAXED_EXPOSED_COPPER_HOE, List.of(EN_US.Hoe(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_hoe", WAXED_WEATHERED_COPPER_HOE, List.of(EN_US.Hoe(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_hoe", WAXED_OXIDIZED_COPPER_HOE, List.of(EN_US.Hoe(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_HOE, WAXED_COPPER_HOE);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_HOE, WAXED_EXPOSED_COPPER_HOE);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_HOE, WAXED_WEATHERED_COPPER_HOE);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_HOE, WAXED_OXIDIZED_COPPER_HOE);
		Register("copper_sword", COPPER_SWORD, List.of(EN_US.Sword(EN_US.Copper())));
		Register("exposed_copper_sword", EXPOSED_COPPER_SWORD, List.of(EN_US.Sword(EN_US.ExposedCopper())));
		Register("weathered_copper_sword", WEATHERED_COPPER_SWORD, List.of(EN_US.Sword(EN_US.WeatheredCopper())));
		Register("oxidized_copper_sword", OXIDIZED_COPPER_SWORD, List.of(EN_US.Sword(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_SWORD, EXPOSED_COPPER_SWORD, WEATHERED_COPPER_SWORD, OXIDIZED_COPPER_SWORD);
		Register("waxed_copper_sword", WAXED_COPPER_SWORD, List.of(EN_US.Sword(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_sword", WAXED_EXPOSED_COPPER_SWORD, List.of(EN_US.Sword(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_sword", WAXED_WEATHERED_COPPER_SWORD, List.of(EN_US.Sword(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_sword", WAXED_OXIDIZED_COPPER_SWORD, List.of(EN_US.Sword(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_SWORD, WAXED_COPPER_SWORD);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_SWORD, WAXED_EXPOSED_COPPER_SWORD);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_SWORD, WAXED_WEATHERED_COPPER_SWORD);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_SWORD, WAXED_OXIDIZED_COPPER_SWORD);
		Register("copper_knife", COPPER_KNIFE, List.of(EN_US.Knife(EN_US.Copper())));
		Register("exposed_copper_knife", EXPOSED_COPPER_KNIFE, List.of(EN_US.Knife(EN_US.ExposedCopper())));
		Register("weathered_copper_knife", WEATHERED_COPPER_KNIFE, List.of(EN_US.Knife(EN_US.WeatheredCopper())));
		Register("oxidized_copper_knife", OXIDIZED_COPPER_KNIFE, List.of(EN_US.Knife(EN_US.OxidizedCopper())));
		OxidationScale.Register(COPPER_KNIFE, EXPOSED_COPPER_KNIFE, WEATHERED_COPPER_KNIFE, OXIDIZED_COPPER_KNIFE);
		Register("waxed_copper_knife", WAXED_COPPER_KNIFE, List.of(EN_US.Knife(EN_US.WaxedCopper())));
		Register("waxed_exposed_copper_knife", WAXED_EXPOSED_COPPER_KNIFE, List.of(EN_US.Knife(EN_US.WaxedExposedCopper())));
		Register("waxed_weathered_copper_knife", WAXED_WEATHERED_COPPER_KNIFE, List.of(EN_US.Knife(EN_US.WaxedWeatheredCopper())));
		Register("waxed_oxidized_copper_knife", WAXED_OXIDIZED_COPPER_KNIFE, List.of(EN_US.Knife(EN_US.WaxedOxidizedCopper())));
		OxidationScale.RegisterWaxed(COPPER_KNIFE, WAXED_COPPER_KNIFE);
		OxidationScale.RegisterWaxed(EXPOSED_COPPER_KNIFE, WAXED_EXPOSED_COPPER_KNIFE);
		OxidationScale.RegisterWaxed(WEATHERED_COPPER_KNIFE, WAXED_WEATHERED_COPPER_KNIFE);
		OxidationScale.RegisterWaxed(OXIDIZED_COPPER_KNIFE, WAXED_OXIDIZED_COPPER_KNIFE);
		//</editor-fold>
		//<editor-fold desc="Raw Metal">
		Register("raw_copper_slab", RAW_COPPER_SLAB, List.of(EN_US.Slab(EN_US.RawCopper())));
		Register("raw_gold_slab", RAW_GOLD_SLAB, List.of(EN_US.Slab(EN_US.RawGold())));
		Register("raw_iron_slab", RAW_IRON_SLAB, List.of(EN_US.Slab(EN_US.RawIron())));
		//</editor-fold>
		//<editor-fold desc="Netherite">
		Register("netherite_nugget", NETHERITE_NUGGET, List.of(EN_US.Nugget(EN_US.Netherite())));
		Register("netherite_flame", NETHERITE_FLAME_PARTICLE);
		Register("netherite_torch", "netherite_wall_torch", NETHERITE_TORCH, List.of(EN_US._Torch(EN_US.Netherite())));
		Register("netherite_soul_torch", "netherite_soul_wall_torch", NETHERITE_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Netherite())));
		Register("netherite_ender_torch", "netherite_ender_wall_torch", NETHERITE_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Netherite())));
		Register("underwater_netherite_torch", "underwater_netherite_wall_torch", UNDERWATER_NETHERITE_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Netherite())));
		Register("netherite_lantern", NETHERITE_LANTERN, List.of(EN_US.Lantern(EN_US.Netherite())));
		Register("netherite_soul_lantern", NETHERITE_SOUL_LANTERN, List.of(EN_US.SoulLantern(EN_US.Netherite())));
		Register("netherite_ender_lantern", NETHERITE_ENDER_LANTERN, List.of(EN_US.EnderLantern(EN_US.Netherite())));
		Register("netherite_button", NETHERITE_BUTTON, List.of(EN_US.Button(EN_US.Netherite())));
		Register("netherite_chain", NETHERITE_CHAIN, List.of(EN_US.Chain(EN_US.Netherite())));
		Register("netherite_bars", NETHERITE_BARS, List.of(EN_US.Bars(EN_US.Netherite())));
		Register("netherite_stairs", NETHERITE_STAIRS, List.of(EN_US.Stairs(EN_US.Netherite())));
		Register("netherite_slab", NETHERITE_SLAB, List.of(EN_US.Slab(EN_US.Netherite())));
		Register("netherite_wall", NETHERITE_WALL, List.of(EN_US.Wall(EN_US.Netherite())));
		Register("netherite_bricks", NETHERITE_BRICKS, List.of(EN_US.Bricks(EN_US.Netherite())));
		Register("netherite_brick_stairs", NETHERITE_BRICK_STAIRS, List.of(EN_US.BrickStairs(EN_US.Netherite())));
		Register("netherite_brick_slab", NETHERITE_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.Netherite())));
		Register("netherite_brick_wall", NETHERITE_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.Netherite())));
		Register("cut_netherite", CUT_NETHERITE, List.of(EN_US.CutNetherite()));
		Register("cut_netherite_pillar", CUT_NETHERITE_PILLAR, List.of(EN_US.Pillar(EN_US.CutNetherite())));
		Register("cut_netherite_stairs", CUT_NETHERITE_STAIRS, List.of(EN_US.Stairs(EN_US.CutNetherite())));
		Register("cut_netherite_slab", CUT_NETHERITE_SLAB, List.of(EN_US.Slab(EN_US.CutNetherite())));
		Register("cut_netherite_wall", CUT_NETHERITE_WALL, List.of(EN_US.Wall(EN_US.CutNetherite())));
		Register("netherite_horse_armor", NETHERITE_HORSE_ARMOR, List.of(EN_US.HorseArmor(EN_US.Netherite())));
		Register("crushing_weighted_pressure_plate", CRUSHING_WEIGHTED_PRESSURE_PLATE, List.of(EN_US.WeightedPressurePlate("Crushing")));
		//</editor-fold>
		//<editor-fold desc="Bone">
		Register("bone_torch", "bone_wall_torch", BONE_TORCH, List.of(EN_US._Torch(EN_US.Bone())));
		Register("bone_soul_torch", "bone_soul_wall_torch", BONE_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Bone())));
		Register("bone_ender_torch", "bone_ender_wall_torch", BONE_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Bone())));
		Register("underwater_bone_torch", "underwater_bone_wall_torch", UNDERWATER_BONE_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Bone())));
		Register("bone_ladder", BONE_LADDER, List.of(EN_US.BoneLadder()));
		//</editor-fold>
		//<editor-fold desc="Gilded Blackstone">
		Register("gilded_blackstone_stairs", GILDED_BLACKSTONE_STAIRS, List.of(EN_US.Stairs(EN_US.GildedBlackstone())));
		Register("gilded_blackstone_slab", GILDED_BLACKSTONE_SLAB, List.of(EN_US.Slab(EN_US.GildedBlackstone())));
		Register("gilded_blackstone_wall", GILDED_BLACKSTONE_WALL, List.of(EN_US.Wall(EN_US.GildedBlackstone())));
		Register("polished_gilded_blackstone", POLISHED_GILDED_BLACKSTONE, List.of(EN_US.PolishedGildedBlackstone()));
		Register("polished_gilded_blackstone_stairs", POLISHED_GILDED_BLACKSTONE_STAIRS, List.of(EN_US.Stairs(EN_US.PolishedGildedBlackstone())));
		Register("polished_gilded_blackstone_slab", POLISHED_GILDED_BLACKSTONE_SLAB, List.of(EN_US.Slab(EN_US.PolishedGildedBlackstone())));
		Register("polished_gilded_blackstone_wall", POLISHED_GILDED_BLACKSTONE_WALL, List.of(EN_US.Wall(EN_US.PolishedGildedBlackstone())));
		Register("polished_gilded_blackstone_bricks", POLISHED_GILDED_BLACKSTONE_BRICKS, List.of(EN_US.Bricks(EN_US.PolishedGildedBlackstone())));
		Register("polished_gilded_blackstone_brick_stairs", POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, List.of(EN_US.BrickStairs(EN_US.PolishedGildedBlackstone())));
		Register("polished_gilded_blackstone_brick_slab", POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.PolishedGildedBlackstone())));
		Register("polished_gilded_blackstone_brick_wall", POLISHED_GILDED_BLACKSTONE_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.PolishedGildedBlackstone())));
		Register("chiseled_polished_gilded_blackstone", CHISELED_POLISHED_GILDED_BLACKSTONE, List.of(EN_US.PolishedGildedBlackstone(EN_US.Chiseled())));
		Register("cracked_polished_gilded_blackstone_bricks", CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS, List.of(EN_US.Bricks(EN_US.PolishedGildedBlackstone(EN_US.Cracked()))));
		//</editor-fold>
		//<editor-fold desc="Arrows">
		Register("amethyst_arrow", AMETHYST_ARROW, List.of(EN_US.AmethystArrow()));
		Register("chorus_arrow", CHORUS_ARROW, List.of(EN_US.ChorusArrow()));
		//</editor-fold>
		//<editor-fold desc="Misc Stuff">
		Register("coal_slab", COAL_SLAB, List.of(EN_US.Slab(EN_US.Coal())));
		Register("charcoal_block", CHARCOAL_BLOCK, List.of(EN_US.Block(EN_US.Charcoal())));
		Register("charcoal_slab", CHARCOAL_SLAB, List.of(EN_US.Slab(EN_US.Charcoal())));
		Register("coarse_dirt_slab", COARSE_DIRT_SLAB, List.of(EN_US.Slab(EN_US.CoarseDirt())));
		Register("cocoa_block", COCOA_BLOCK, List.of(EN_US.Block(EN_US.Cocoa())));
		Register("blue_mushroom", BLUE_MUSHROOM, List.of(EN_US._Potted(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_mushroom_block", BLUE_MUSHROOM_BLOCK, List.of(EN_US.Block(EN_US.Mushroom(EN_US.Blue()))));
		Register("blue_shroomlight", BLUE_SHROOMLIGHT, List.of(EN_US.BlueShroomlight()));
		Register("blue_target", BLUE_TARGET, List.of(EN_US.Target(EN_US.Blue())));
		Register("flint_block", FLINT_BLOCK, List.of(EN_US.Block(EN_US.Flint())));
		Register("flint_slab", FLINT_SLAB, List.of(EN_US.Slab(EN_US.Flint())));
		Register("flint_bricks", FLINT_BRICKS, List.of(EN_US.Bricks(EN_US.Flint())));
		Register("flint_brick_stairs", FLINT_BRICK_STAIRS, List.of(EN_US.BrickStairs(EN_US.Flint())));
		Register("flint_brick_slab", FLINT_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.Flint())));
		Register("flint_brick_wall", FLINT_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.Flint())));
		Register("hedge_block", HEDGE_BLOCK, List.of(EN_US.Block(EN_US.Hedge())));
		Register("mycelium_roots", MYCELIUM_ROOTS, List.of(EN_US._Potted(EN_US.Roots(EN_US.Mycelium()))));
		Register("seed_block", SEED_BLOCK, List.of(EN_US.Block(EN_US.Seed())));
		Register("wax_block", WAX_BLOCK, List.of(EN_US.Block(EN_US.Wax())));
		Register("glazed_terracotta", GLAZED_TERRACOTTA, List.of(EN_US.GlazedTerracotta()));
		Register("lava_bottle", LAVA_BOTTLE, List.of(EN_US.Bottle(EN_US.Lava())));
		//</editor-fold>
		Register("horn", HORN, List.of(EN_US.Horn()));
		//<editor-fold desc="Feathers">
		Register("fancy_feather", FANCY_FEATHER, List.of(EN_US.Feather(EN_US.Fancy())));
		Register("black_feather", BLACK_FEATHER, List.of(EN_US.Feather(EN_US.Black())));
		Register("red_feather", RED_FEATHER, List.of(EN_US.Feather(EN_US.Red())));
		//</editor-fold>
		//<editor-fold desc="Books">
		Register("unreadable_book", UNREADABLE_BOOK, List.of(EN_US.Book(EN_US.Unreadable())));
		Register("red_book", RED_BOOK, List.of(EN_US.Book(EN_US.Red())));
		Register("orange_book", ORANGE_BOOK, List.of(EN_US.Book(EN_US.Orange())));
		Register("yellow_book", YELLOW_BOOK, List.of(EN_US.Book(EN_US.Yellow())));
		Register("green_book", GREEN_BOOK, List.of(EN_US.Book(EN_US.Green())));
		Register("blue_book", BLUE_BOOK, List.of(EN_US.Book(EN_US.Blue())));
		Register("purple_book", PURPLE_BOOK, List.of(EN_US.Book(EN_US.Purple())));
		Register("gray_book", GRAY_BOOK, List.of(EN_US.Book(EN_US.Gray())));
		//</editor-fold>
		//TODO: Rainbow Bed
		//<editor-fold desc="Wool">
		for (DyeColor color : DyeColor.values()) Register(color.getName() + "_wool_slab", WOOL_SLABS.get(color), List.of(EN_US.Slab(EN_US.Wool(EN_US.Color(color)))));
		Register("rainbow_wool", RAINBOW_WOOL, List.of(EN_US.Wool(EN_US.Rainbow())));
		Register("rainbow_wool_slab", RAINBOW_WOOL_SLAB, List.of(EN_US.Slab(EN_US.Wool(EN_US.Rainbow()))));
		Register("rainbow_carpet", RAINBOW_CARPET, List.of(EN_US.RainbowCarpet()));
		//Register("rainbow_bed", RAINBOW_BED, List.of(EN_US.Bed(EN_US.Rainbow())));
		//</editor-fold>
		//TODO: Moss & Glow Lichen Beds
		Register("moss_slab", MOSS_SLAB, List.of(EN_US.Slab(EN_US.Moss())));
		//Register("moss_bed", MOSS_BED, List.of(EN_US.MossBed()));
		Register("glow_lichen_block", GLOW_LICHEN_BLOCK, List.of(EN_US.Block(EN_US.GlowLichen())));
		Register("glow_lichen_slab", GLOW_LICHEN_SLAB, List.of(EN_US.Slab(EN_US.GlowLichen())));
		Register("glow_lichen_carpet", GLOW_LICHEN_CARPET, List.of(EN_US.Carpet(EN_US.GlowLichen())));
		//Register("glow_lichen_bed", GLOW_LICHEN_BED, List.of(EN_US.GlowLichenBed()));
		//<editor-fold desc="Goat">
		Register("minecraft:set_instrument", SET_INSTRUMENT_LOOT_FUNCTION);
		Register("minecraft:goat_horn", GOAT_HORN, List.of(EN_US.GoatHorn()));
		//Extended
		Register("chevon", CHEVON, List.of(EN_US.Chevon()));
		Register("cooked_chevon", COOKED_CHEVON, List.of(EN_US.Chevon(EN_US.Cooked())));
		for (DyeColor color : DyeColor.values()) Register(color.getName() + "_fleece", FLEECE.get(color), List.of(EN_US.Fleece(EN_US.Color(color))));
		for (DyeColor color : DyeColor.values()) Register(color.getName() + "_fleece_slab", FLEECE_SLABS.get(color), List.of(EN_US.Slab(EN_US.Fleece(EN_US.Color(color)))));
		for (DyeColor color : DyeColor.values()) Register(color.getName() + "_fleece_carpet", FLEECE_CARPETS.get(color), List.of(EN_US.Carpet(EN_US.Fleece(EN_US.Color(color)))));
		Register("rainbow_fleece", RAINBOW_FLEECE, List.of(EN_US.Fleece(EN_US.Rainbow())));
		Register("rainbow_fleece_slab", RAINBOW_FLEECE_SLAB, List.of(EN_US.Slab(EN_US.Fleece(EN_US.Rainbow()))));
		Register("rainbow_fleece_carpet", RAINBOW_FLEECE_CARPET, List.of(EN_US.Carpet(EN_US.Fleece(EN_US.Rainbow()))));
		Register("fleece_helmet", FLEECE_HELMET, List.of(EN_US.Helmet(EN_US.Fleece())));
		Register("fleece_chestplate", FLEECE_CHESTPLATE, List.of(EN_US.Chestplate(EN_US.Fleece())));
		Register("fleece_leggings", FLEECE_LEGGINGS, List.of(EN_US.Leggings(EN_US.Fleece())));
		Register("fleece_boots", FLEECE_BOOTS, List.of(EN_US.Boots(EN_US.Fleece())));
		Register("fleece_horse_armor", FLEECE_HORSE_ARMOR, List.of(EN_US.HorseArmor(EN_US.Fleece())));
		Register("goat_horn_salve", GOAT_HORN_SALVE, List.of(EN_US.Salve(EN_US.GoatHorn())));
		//</editor-fold>
		//<editor-fold desc="Studded Leather">
		Register("studded_leather_helmet", STUDDED_LEATHER_HELMET, List.of(EN_US.Helmet(EN_US.StuddedLeather())));
		Register("studded_leather_chestplate", STUDDED_LEATHER_CHESTPLATE, List.of(EN_US.Chestplate(EN_US.StuddedLeather())));
		Register("studded_leather_leggings", STUDDED_LEATHER_LEGGINGS, List.of(EN_US.Leggings(EN_US.StuddedLeather())));
		Register("studded_leather_boots", STUDDED_LEATHER_BOOTS, List.of(EN_US.Boots(EN_US.StuddedLeather())));
		Register("studded_leather_horse_armor", STUDDED_LEATHER_HORSE_ARMOR, List.of(EN_US.HorseArmor(EN_US.StuddedLeather())));
		//</editor-fold>
		//<editor-fold desc="Charred Wood">
		Register("charred_log", CHARRED_LOG, List.of(EN_US.Log(EN_US.Charred())));
		Register("stripped_charred_log", STRIPPED_CHARRED_LOG, List.of(EN_US.Log(EN_US.Charred(EN_US.Stripped()))));
		Register("charred_wood", CHARRED_WOOD, List.of(EN_US.Wood(EN_US.Charred())));
		Register("stripped_charred_wood", STRIPPED_CHARRED_WOOD, List.of(EN_US.Wood(EN_US.Charred(EN_US.Stripped()))));
		Register("charred_planks", CHARRED_PLANKS, List.of(EN_US.Planks(EN_US.Charred())));
		Register("charred_stairs", CHARRED_STAIRS, List.of(EN_US.Stairs(EN_US.Charred())));
		Register("charred_slab", CHARRED_SLAB, List.of(EN_US.Slab(EN_US.Charred())));
		Register("charred_fence", CHARRED_FENCE, List.of(EN_US.Fence(EN_US.Charred())));
		Register("charred_fence_gate", CHARRED_FENCE_GATE, List.of(EN_US.FenceGate(EN_US.Charred())));
		Register("charred_door", CHARRED_DOOR, List.of(EN_US.Door(EN_US.Charred())));
		Register("charred_trapdoor", CHARRED_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Charred())));
		Register("charred_pressure_plate", CHARRED_PRESSURE_PLATE, List.of(EN_US.PressurePlate(EN_US.Charred())));
		Register("charred_button", CHARRED_BUTTON, List.of(EN_US.Button(EN_US.Charred())));
		Register("charred", CHARRED_SIGN, List.of(EN_US._Sign(EN_US.Charred())));
		ModRegistry.Register("charred", CHARRED_BOAT, List.of(EN_US._Boat(EN_US.Charred())));
		Register("charred_bookshelf", CHARRED_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Charred())));
		Register("charred_ladder", CHARRED_LADDER, List.of(EN_US.Ladder(EN_US.Charred())));
		Register("charred_woodcutter", CHARRED_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Charred())));
		Register("charred_barrel", CHARRED_BARREL, List.of(EN_US.Barrel(EN_US.Charred())));
		Register("charred_lectern", CHARRED_LECTERN, List.of(EN_US.Lectern(EN_US.Charred())));
		//</editor-fold>
		Register("minecraft:music_disc_5", MUSIC_DISC_5, List.of(EN_US.MusicDisc()));
		Register("minecraft:disc_fragment_5", DISC_FRAGMENT_5, List.of(EN_US.Fragment(EN_US.Disc())));
		//<editor-fold desc="Mud">
		Register("minecraft:mud", MUD, List.of(EN_US.Mud()));
		Register("minecraft:packed_mud", PACKED_MUD, List.of(EN_US.PackedMud()));
		Register("minecraft:mud_bricks", MUD_BRICKS, List.of(EN_US.MudBricks()));
		Register("minecraft:mud_brick_stairs", MUD_BRICK_STAIRS, List.of(EN_US.BrickStairs(EN_US.Mud())));
		Register("minecraft:mud_brick_slab", MUD_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.Mud())));
		Register("minecraft:mud_brick_wall", MUD_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.Mud())));
		//</editor-fold>
		//<editor-fold desc="Mangrove">
		Register("minecraft:mangrove_log", MANGROVE_LOG, List.of(EN_US.Log(EN_US.Mangrove())));
		Register("minecraft:stripped_mangrove_log", STRIPPED_MANGROVE_LOG, List.of(EN_US.Log(EN_US.Mangrove(EN_US.Stripped()))));
		Register("minecraft:mangrove_wood", MANGROVE_WOOD, List.of(EN_US.Wood(EN_US.Mangrove())));
		Register("minecraft:stripped_mangrove_wood", STRIPPED_MANGROVE_WOOD, List.of(EN_US.Wood(EN_US.Mangrove(EN_US.Stripped()))));
		Register("minecraft:mangrove_leaves", MANGROVE_LEAVES, List.of(EN_US.Leaves(EN_US.Mangrove())));
		Register("minecraft:mangrove_planks", MANGROVE_PLANKS, List.of(EN_US.Planks(EN_US.Mangrove())));
		Register("minecraft:mangrove_stairs", MANGROVE_STAIRS, List.of(EN_US.Stairs(EN_US.Mangrove())));
		Register("minecraft:mangrove_slab", MANGROVE_SLAB, List.of(EN_US.Slab(EN_US.Mangrove())));
		Register("minecraft:mangrove_fence", MANGROVE_FENCE, List.of(EN_US.Fence(EN_US.Mangrove())));
		Register("minecraft:mangrove_fence_gate", MANGROVE_FENCE_GATE, List.of(EN_US.FenceGate(EN_US.Mangrove())));
		Register("minecraft:mangrove_door", MANGROVE_DOOR, List.of(EN_US.Door(EN_US.Mangrove())));
		Register("minecraft:mangrove_trapdoor", MANGROVE_TRAPDOOR, List.of(EN_US.Trapdoor(EN_US.Mangrove())));
		Register("minecraft:mangrove_pressure_plate", MANGROVE_PRESSURE_PLATE, List.of(EN_US.PressurePlate(EN_US.Mangrove())));
		Register("minecraft:mangrove_button", MANGROVE_BUTTON, List.of(EN_US.Button(EN_US.Mangrove())));
		Register("minecraft:mangrove_roots", MANGROVE_ROOTS, List.of(EN_US.MangroveRoots()));
		Register("minecraft:muddy_mangrove_roots", MUDDY_MANGROVE_ROOTS, List.of(EN_US.MuddyMangroveRoots()));
		Register("minecraft:mangrove", MANGROVE_SIGN, List.of(EN_US._Sign(EN_US.Mangrove())));
		ModRegistry.Register("minecraft:mangrove", MANGROVE_BOAT, List.of(EN_US._Boat(EN_US.Mangrove())));
		Register("minecraft:mangrove_propagule", MANGROVE_PROPAGULE, List.of(EN_US.PottedPropagule(EN_US.Mangrove())));
		//Extended
		Register("mangrove_bookshelf", MANGROVE_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Mangrove())));
		Register("mangrove_ladder", MANGROVE_LADDER, List.of(EN_US.Ladder(EN_US.Mangrove())));
		Register("mangrove_woodcutter", MANGROVE_WOODCUTTER, List.of(EN_US.Woodcutter(EN_US.Mangrove())));
		Register("mangrove_barrel", MANGROVE_BARREL, List.of(EN_US.Barrel(EN_US.Mangrove())));
		Register("mangrove_lectern", MANGROVE_LECTERN, List.of(EN_US.Lectern(EN_US.Mangrove())));
		//</editor-fold>
		//<editor-fold desc="Cherry">
		Register("minecraft:cherry_log", CHERRY_LOG, List.of(EN_US.Log(EN_US.Cherry())));
		Register("minecraft:stripped_cherry_log", STRIPPED_CHERRY_LOG, List.of(EN_US.Log(EN_US.Cherry(EN_US.Stripped()))));
		Register("minecraft:cherry_wood", CHERRY_WOOD, List.of(EN_US.CherryWood()));
		Register("minecraft:stripped_cherry_wood", STRIPPED_CHERRY_WOOD, List.of(EN_US.CherryWood(EN_US.Stripped())));
		Register("minecraft:cherry_leaves", CHERRY_LEAVES, List.of(EN_US.Leaves(EN_US.Cherry())));
		Register("minecraft:dripping_cherry_leaves", DRIPPING_CHERRY_LEAVES_PARTICLE);
		Register("minecraft:falling_cherry_leaves", FALLING_CHERRY_LEAVES_PARTICLE);
		Register("minecraft:landing_cherry_leaves", LANDING_CHERRY_LEAVES_PARTICLE);
		Register("minecraft:cherry_planks", CHERRY_PLANKS, List.of(EN_US.Planks(EN_US.Cherry())));
		Register("minecraft:cherry_stairs", CHERRY_STAIRS, List.of(EN_US.Stairs(EN_US.Cherry())));
		Register("minecraft:cherry_slab", CHERRY_SLAB, List.of(EN_US.Slab(EN_US.Cherry())));
		Register("minecraft:cherry_fence", CHERRY_FENCE, List.of(EN_US.CherryFence()));
		Register("minecraft:cherry_fence_gate", CHERRY_FENCE_GATE, List.of(EN_US.CherryFenceGate()));
		Register("minecraft:cherry_door", CHERRY_DOOR, List.of(EN_US.CherryDoor()));
		Register("minecraft:cherry_trapdoor", CHERRY_TRAPDOOR, List.of(EN_US.CherryTrapdoor()));
		Register("minecraft:cherry_pressure_plate", CHERRY_PRESSURE_PLATE, List.of(EN_US.PressurePlate(EN_US.Cherry())));
		Register("minecraft:cherry_button", CHERRY_BUTTON, List.of(EN_US.Button(EN_US.Cherry())));
		Register("minecraft:cherry", CHERRY_SIGN, List.of(EN_US._Sign(EN_US.Cherry())));
		ModRegistry.Register("minecraft:cherry", CHERRY_BOAT, List.of(EN_US._Boat(EN_US.Cherry())));
		Register("minecraft:cherry_sapling", CHERRY_SAPLING, List.of(EN_US.PottedSapling(EN_US.Cherry())));
		//Extended
		Register("cherry_bookshelf", CHERRY_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Cherry())));
		Register("cherry_ladder", CHERRY_LADDER, List.of(EN_US.Ladder(EN_US.Cherry())));
		Register("cherry_woodcutter", CHERRY_WOODCUTTER, List.of(EN_US.CherryWoodcutter()));
		Register("cherry_barrel", CHERRY_BARREL, List.of(EN_US.Barrel(EN_US.Cherry())));
		Register("cherry_lectern", CHERRY_LECTERN, List.of(EN_US.CherryLectern()));
		//</editor-fold>
		//<editor-fold desc="Backport 1.20">
		Register("minecraft:pink_petals", PINK_PETALS, List.of(EN_US.PinkPetals()));
		Register("minecraft:torchflower_crop", TORCHFLOWER_CROP.asBlock(), List.of(EN_US.Crop(EN_US.Torchflower())));
		Register("minecraft:torchflower_seeds", TORCHFLOWER_CROP.asItem(), List.of(EN_US.Seeds(EN_US.Torchflower())));
		Register("minecraft:torchflower", TORCHFLOWER, List.of(EN_US._Potted(EN_US.Torchflower())));
		//</editor-fold>
		//<editor-fold desc="Bamboo">
		Register("minecraft:bamboo_block", BAMBOO_BLOCK, List.of(EN_US.Block(EN_US.Bamboo())));
		Register("minecraft:stripped_bamboo_block", STRIPPED_BAMBOO_BLOCK, List.of(EN_US.Block(EN_US.Bamboo(EN_US.Stripped()))));
		Register("minecraft:bamboo_planks", BAMBOO_PLANKS, List.of(EN_US.Planks(EN_US.Bamboo())));
		Register("minecraft:bamboo_stairs", BAMBOO_STAIRS, List.of(EN_US.Stairs(EN_US.Bamboo())));
		Register("minecraft:bamboo_slab", BAMBOO_SLAB, List.of(EN_US.Slab(EN_US.Bamboo())));
		Register("minecraft:bamboo_fence", BAMBOO_FENCE, List.of(EN_US.Fence(EN_US.BambooFence())));
		Register("minecraft:bamboo_fence_gate", BAMBOO_FENCE_GATE, List.of(EN_US.BambooFenceGate()));
		Register("minecraft:bamboo_door", BAMBOO_DOOR, List.of(EN_US.BambooDoor()));
		Register("minecraft:bamboo_trapdoor", BAMBOO_TRAPDOOR, List.of(EN_US.BambooTrapdoor()));
		Register("minecraft:bamboo_pressure_plate", BAMBOO_PRESSURE_PLATE, List.of(EN_US.PressurePlate(EN_US.Bamboo())));
		Register("minecraft:bamboo_button", BAMBOO_BUTTON, List.of(EN_US.Button(EN_US.Bamboo())));
		Register("minecraft:bamboo", BAMBOO_SIGN, List.of(EN_US._Sign(EN_US.Bamboo())));
		ModRegistry.Register("minecraft:bamboo", "raft", BAMBOO_RAFT, List.of(EN_US._Raft(EN_US.Bamboo())));
		//Bamboo Mosaic
		Register("minecraft:bamboo_mosaic", BAMBOO_MOSAIC, List.of(EN_US.BambooMosaic()));
		Register("minecraft:bamboo_mosaic_stairs", BAMBOO_MOSAIC_STAIRS, List.of(EN_US.Stairs(EN_US.BambooMosaic())));
		Register("minecraft:bamboo_mosaic_slab", BAMBOO_MOSAIC_SLAB, List.of(EN_US.Slab(EN_US.BambooMosaic())));
		//Extended
		Register("bamboo_torch", "bamboo_wall_torch", BAMBOO_TORCH, List.of(EN_US._Torch(EN_US.Bamboo())));
		Register("bamboo_soul_torch", "bamboo_soul_wall_torch", BAMBOO_SOUL_TORCH, List.of(EN_US.SoulTorch(EN_US.Bamboo())));
		Register("bamboo_ender_torch", "bamboo_ender_wall_torch", BAMBOO_ENDER_TORCH, List.of(EN_US.EnderTorch(EN_US.Bamboo())));
		Register("underwater_bamboo_torch", "underwater_bamboo_wall_torch", UNDERWATER_BAMBOO_TORCH, List.of(EN_US.Underwater_Torch(EN_US.Bamboo())));
		Register("bamboo_bookshelf", BAMBOO_BOOKSHELF, List.of(EN_US.Bookshelf(EN_US.Bamboo())));
		Register("bamboo_ladder", BAMBOO_LADDER, List.of(EN_US.BambooLadder()));
		Register("bamboo_woodcutter", BAMBOO_WOODCUTTER, List.of(EN_US.BambooWoodcutter()));
		Register("bamboo_barrel", BAMBOO_BARREL, List.of(EN_US.Barrel(EN_US.Bamboo())));
		Register("bamboo_lectern", BAMBOO_LECTERN, List.of(EN_US.BambooLectern()));
		//</editor-fold>
		Register("minecraft:piglin_head", "minecraft:piglin_wall_head", PIGLIN_HEAD, List.of(EN_US._Head(EN_US.Piglin())));
		Register("minecraft:piglin_head", PIGLIN_HEAD_BLOCK_ENTITY);
		DispenserBlock.registerBehavior(PIGLIN_HEAD, new WearableDispenserBehavior());
		//Mobs
		ModActivities.Initialize();
		ModDataHandlers.Initialize();
		//<editor-fold desc="Allays">
		Register("minecraft:allay", ALLAY_ENTITY, List.of(EN_US.Allay()));
		//noinspection ConstantConditions
		FabricDefaultAttributeRegistry.register(ALLAY_ENTITY, AllayEntity.createAllayAttributes());
		//</editor-fold>
		//<editor-fold desc="Frogs">
		Register("minecraft:ochre_froglight", OCHRE_FROGLIGHT, List.of(EN_US.Froglight(EN_US.Ochre())));
		Register("minecraft:verdant_froglight", VERDANT_FROGLIGHT, List.of(EN_US.Froglight(EN_US.Verdant())));
		Register("minecraft:pearlescent_froglight", PEARLESCENT_FROGLIGHT, List.of(EN_US.Froglight(EN_US.Pearlescent())));
		FROG_TEMPTATIONS_SENSOR.Initialize();
		FROG_ATTACKABLES_SENSOR.Initialize();
		IS_IN_WATER_SENSOR.Initialize();
		Register("minecraft:frog", FROG_ENTITY, List.of(EN_US.Frog()));
		FabricDefaultAttributeRegistry.register(FROG_ENTITY, FrogEntity.createFrogAttributes());
		SpawnRestrictionAccessor.callRegister(FROG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FrogEntity::canSpawn);
		Register("minecraft:frogspawn", FROGSPAWN, List.of(EN_US.Frogspawn()));
		Register("minecraft:tadpole", TADPOLE_ENTITY, List.of(EN_US.Tadpole()));
		FabricDefaultAttributeRegistry.register(TADPOLE_ENTITY, TadpoleEntity.createTadpoleAttributes());
		Register("minecraft:tadpole_bucket", TADPOLE_BUCKET, List.of(EN_US.Tadpole(EN_US.BucketOf())));
		//</editor-fold>
		//<editor-fold desc="Sculk">
		Register("minecraft:sculk", SCULK, List.of(EN_US.Sculk()));
		Register("minecraft:sculk_vein",SCULK_VEIN, List.of(EN_US.SculkVein()));
		Register("minecraft:sculk_catalyst", SCULK_CATALYST, SCULK_CATALYST_ENTITY, List.of(EN_US.SculkCatalyst()));
		Register("minecraft:sculk_shrieker", SCULK_SHRIEKER, SCULK_SHRIEKER_ENTITY, List.of(EN_US.SculkShrieker()));
		Register("minecraft:sculk_soul", SCULK_SOUL_PARTICLE);
		Register("minecraft:sculk_charge", SCULK_CHARGE_PARTICLE);
		Register("minecraft:sculk_charge_pop", SCULK_CHARGE_POP_PARTICLE);
		Register("minecraft:shriek", SHRIEK_PARTICLE);
		//</editor-fold>
		Register("minecraft:reinforced_deepslate", REINFORCED_DEEPSLATE, List.of(EN_US.ReinforcedDeepslate()));
		//<editor-fold desc="Wardens">
		WARDEN_ENTITY_SENSOR.Initialize();
		Register("minecraft:warden", WARDEN_ENTITY, List.of(EN_US.Warden()));
		Register("minecraft:sonic_boom", SONIC_BOOM_PARTICLE);
		FabricDefaultAttributeRegistry.register(WARDEN_ENTITY, WardenEntity.addAttributes());
		SpawnRestrictionAccessor.callRegister(WARDEN_ENTITY, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WardenEntity::canMobSpawn);
		//</editor-fold>
		//<editor-fold desc="1.19 Backport">
		Register("minecraft:darkness", DARKNESS_EFFECT, List.of(EN_US.Darkness()));
		Register("minecraft:swift_sneak", SWIFT_SNEAK_ENCHANTMENT, List.of(EN_US.SwiftSneak()));
		Register("recovery_compass", RECOVERY_COMPASS, List.of(EN_US.RecoveryCompass()));
		EN_US.TranslationKeys.put("item." + NAMESPACE + "active_recovery_compass", EN_US.RecoveryCompass(EN_US.Active()));
		//</editor-fold>
		//<editor-fold desc="Echo">
		Register("minecraft:echo_shard", ECHO_SHARD, List.of(EN_US.Shard(EN_US.Echo())));
		//Extended
		Register("echo_block", ECHO_BLOCK, List.of(EN_US.Block(EN_US.Echo())));
		Register("echo_stairs", ECHO_STAIRS, List.of(EN_US.Stairs(EN_US.Echo())));
		Register("echo_slab", ECHO_SLAB, List.of(EN_US.Slab(EN_US.Echo())));
		Register("echo_wall", ECHO_WALL, List.of(EN_US.Wall(EN_US.Echo())));
		Register("echo_crystal_block", ECHO_CRYSTAL_BLOCK, List.of(EN_US.CrystalBlock(EN_US.Echo())));
		Register("echo_crystal_stairs", ECHO_CRYSTAL_STAIRS, List.of(EN_US.CrystalStairs(EN_US.Echo())));
		Register("echo_crystal_slab", ECHO_CRYSTAL_SLAB, List.of(EN_US.CrystalSlab(EN_US.Echo())));
		Register("echo_crystal_wall", ECHO_CRYSTAL_WALL, List.of(EN_US.CrystalWall(EN_US.Echo())));
		Register("budding_echo", BUDDING_ECHO, List.of(EN_US.BuddingEcho()));
		Register("echo_cluster", ECHO_CLUSTER, List.of(EN_US.EchoCluster()));
		Register("large_echo_bud", LARGE_ECHO_BUD, List.of(EN_US.EchoBud(EN_US.Large())));
		Register("medium_echo_bud", MEDIUM_ECHO_BUD, List.of(EN_US.EchoBud(EN_US.Medium())));
		Register("small_echo_bud", SMALL_ECHO_BUD, List.of(EN_US.EchoBud(EN_US.Small())));
		Register("echo_axe", ECHO_AXE, List.of(EN_US.Axe(EN_US.Echo())));
		Register("echo_hoe", ECHO_HOE, List.of(EN_US.Hoe(EN_US.Echo())));
		Register("echo_pickaxe", ECHO_PICKAXE, List.of(EN_US.Pickaxe(EN_US.Echo())));
		Register("echo_shovel", ECHO_SHOVEL, List.of(EN_US.Shovel(EN_US.Echo())));
		Register("echo_sword", ECHO_SWORD, List.of(EN_US.Sword(EN_US.Echo())));
		Register("echo_knife", ECHO_KNIFE, List.of(EN_US.Knife(EN_US.Echo())));
		//</editor-fold>
		//<editor-fold desc="Sculk (Extended)">
		Register("sculk_stone", SCULK_STONE, List.of(EN_US.SculkStone()));
		Register("sculk_stone_stairs", SCULK_STONE_STAIRS, List.of(EN_US.Stairs(EN_US.SculkStone())));
		Register("sculk_stone_slab", SCULK_STONE_SLAB, List.of(EN_US.Slab(EN_US.SculkStone())));
		Register("sculk_stone_wall", SCULK_STONE_WALL, List.of(EN_US.Wall(EN_US.SculkStone())));
		Register("sculk_stone_bricks", SCULK_STONE_BRICKS, List.of(EN_US.Bricks(EN_US.SculkStone())));
		Register("sculk_stone_brick_stairs", SCULK_STONE_BRICK_STAIRS, List.of(EN_US.Stairs(EN_US.SculkStone())));
		Register("sculk_stone_brick_slab", SCULK_STONE_BRICK_SLAB, List.of(EN_US.BrickSlab(EN_US.SculkStone())));
		Register("sculk_stone_brick_wall", SCULK_STONE_BRICK_WALL, List.of(EN_US.BrickWall(EN_US.SculkStone())));
		//Turf
		Register("chum", CHUM, List.of(EN_US.Chum()));
		CompostingChanceRegistry.INSTANCE.add(CHUM, 0.75f);
		Register("calcite_sculk_turf", CALCITE_SCULK_TURF, List.of(EN_US.SculkTurf(EN_US.Calcite())));
		Register("deepslate_sculk_turf", DEEPSLATE_SCULK_TURF, List.of(EN_US.SculkTurf(EN_US.Deepslate())));
		Register("dripstone_sculk_turf", DRIPSTONE_SCULK_TURF, List.of(EN_US.SculkTurf(EN_US.Dripstone())));
		Register("smooth_basalt_sculk_turf", SMOOTH_BASALT_SCULK_TURF, List.of(EN_US.SculkTurf(EN_US.SmoothBasalt())));
		Register("tuff_sculk_turf", TUFF_SCULK_TURF, List.of(EN_US.SculkTurf(EN_US.Tuff())));
		//</editor-fold>
		//<editor-fold desc="Camel">
		CAMEL_TEMPTATIONS_SENSOR.Initialize();
		Register("minecraft:camel", CAMEL_ENTITY, List.of(EN_US.Camel()));
		FabricDefaultAttributeRegistry.register(CAMEL_ENTITY, CamelEntity.createCamelAttributes());
		//</editor-fold>
		//<editor-fold desc="Sniffer">
		Register("minecraft:sniffer", SNIFFER_ENTITY, List.of(EN_US.Sniffer()));
		FabricDefaultAttributeRegistry.register(SNIFFER_ENTITY, SnifferEntity.createSnifferAttributes());
		//</editor-fold>
		//<editor-fold desc="Pottery Shards>
		Register("minecraft:pottery_shard_archer", POTTERY_SHARD_ARCHER, List.of(EN_US.PotteryShard(EN_US.Archer())));
		Register("minecraft:pottery_shard_prize", POTTERY_SHARD_PRIZE, List.of(EN_US.PotteryShard(EN_US.Prize())));
		Register("minecraft:pottery_shard_arms_up", POTTERY_SHARD_ARMS_UP, List.of(EN_US.PotteryShard(EN_US.Up(EN_US.Arms()))));
		Register("minecraft:pottery_shard_skull", POTTERY_SHARD_SKULL, List.of(EN_US.PotteryShard(EN_US.Skull())));
		//</editor-fold>
		//<editor-fold desc="Jumping Spider">
		Register("jumping_spider", JUMPING_SPIDER_ENTITY, List.of(EN_US.JumpingSpider()));
		FabricDefaultAttributeRegistry.register(JUMPING_SPIDER_ENTITY, JumpingSpiderEntity.createJumpingSpiderAttributes());
		//</editor-fold>
		//<editor-fold desc="Hedgehog">
		Register("hedgehog", HEDGEHOG_ENTITY, List.of(EN_US.Hedgehog()));
		FabricDefaultAttributeRegistry.register(HEDGEHOG_ENTITY, HedgehogEntity.createHedgehogAttributes());
		//</editor-fold>
		//<editor-fold desc="Flowers">
		//Minecraft Earth Flowers
		Register("buttercup", BUTTERCUP, List.of(EN_US._Potted(EN_US.Buttercup())));
		Register("pink_daisy", PINK_DAISY, List.of(EN_US._Potted(EN_US.PinkDaisy())));
		//Other Flowers
		Register("rose", ROSE, List.of(EN_US._Potted(EN_US.Rose())));
		Register("blue_rose", BLUE_ROSE, List.of(EN_US._Potted(EN_US.BlueRose())));
		Register("magenta_tulip", MAGENTA_TULIP, List.of(EN_US._Potted(EN_US.Tulip(EN_US.Magenta()))));
		Register("marigold", MARIGOLD, List.of(EN_US._Potted(EN_US.Marigold())));
		Register("pink_allium", PINK_ALLIUM, List.of(EN_US._Potted(EN_US.PinkAllium())));
		Register("lavender", LAVENDER, List.of(EN_US._Potted(EN_US.Lavender())));
		Register("hydrangea", HYDRANGEA, List.of(EN_US._Potted(EN_US.Hydrangea())));
		Register("indigo_orchid", INDIGO_ORCHID, List.of(EN_US._Potted(EN_US.Orchid(EN_US.Indigo()))));
		Register("magenta_orchid", MAGENTA_ORCHID, List.of(EN_US._Potted(EN_US.Orchid(EN_US.Magenta()))));
		Register("orange_orchid", ORANGE_ORCHID, List.of(EN_US._Potted(EN_US.Orchid(EN_US.Orange()))));
		Register("purple_orchid", PURPLE_ORCHID, List.of(EN_US._Potted(EN_US.Orchid(EN_US.Purple()))));
		Register("red_orchid", RED_ORCHID, List.of(EN_US._Potted(EN_US.Orchid(EN_US.Red()))));
		Register("white_orchid", WHITE_ORCHID, List.of(EN_US._Potted(EN_US.Orchid(EN_US.White()))));
		Register("yellow_orchid", YELLOW_ORCHID, List.of(EN_US._Potted(EN_US.Orchid(EN_US.Yellow()))));
		Register("paeonia", PAEONIA, List.of(EN_US._Potted(EN_US.Paeonia())));
		Register("aster", ASTER, List.of(EN_US._Potted(EN_US.Aster())));
		Register("amaranth", AMARANTH, List.of(EN_US.Amaranth()));
		Register("blue_rose_bush", BLUE_ROSE_BUSH, List.of(EN_US.BlueRose()));
		Register("tall_allium", TALL_ALLIUM, List.of(EN_US.Allium(EN_US.Allium())));
		Register("tall_pink_allium", TALL_PINK_ALLIUM, List.of(EN_US.PinkAllium(EN_US.Tall())));
		//</editor-fold>
		Register("minecraft:netherite_upgrade_smithing_template", NETHERITE_UPGRADE_SMITHING_TEMPLATE, List.of(EN_US.SmithingTemplate(EN_US.Upgrade(EN_US.Netherite()))));
		Register("trimming", TRIMMING_RECIPE_SERIALIZER);
		Register("trimming_table", TRIMMING_TABLE, List.of(EN_US.TrimmingTable()));
		for (ArmorTrimPattern pattern : ArmorTrimPattern.values()) Register(pattern.getItemPath(), pattern.asItem(), pattern.getTranslations());
		//<editor-fold desc="Pouches">
		Register("pouch", POUCH, List.of(EN_US.Pouch()));
		Register("chicken_pouch", CHICKEN_POUCH, List.of(EN_US.Chicken(EN_US.PouchOf())));
		Register("rabbit_pouch", RABBIT_POUCH, List.of(EN_US.Rabbit(EN_US.PouchOf())));
		Register("hedgehog_pouch", HEDGEHOG_POUCH, List.of(EN_US.Hedgehog(EN_US.PouchOf())));
		//</editor-fold>
		//<editor-fold desc="Spawn Eggs">
		Register("minecraft:allay_spawn_egg", ALLAY_SPAWN_EGG, List.of(EN_US.SpawnEgg(EN_US.Allay())));
		Register("minecraft:frog_spawn_egg", FROG_SPAWN_EGG, List.of(EN_US.SpawnEgg(EN_US.Frog())));
		Register("minecraft:tadpole_spawn_egg", TADPOLE_SPAWN_EGG, List.of(EN_US.SpawnEgg(EN_US.Tadpole())));
		Register("minecraft:warden_spawn_egg", WARDEN_SPAWN_EGG, List.of(EN_US.SpawnEgg(EN_US.Warden())));
		Register("minecraft:camel_spawn_egg", CAMEL_SPAWN_EGG, List.of(EN_US.SpawnEgg(EN_US.Camel())));
		Register("minecraft:sniffer_spawn_egg", SNIFFER_SPAWN_EGG, List.of(EN_US.SpawnEgg(EN_US.Sniffer())));
		Register("jumping_spider_spawn_egg", JUMPING_SPIDER_SPAWN_EGG, List.of(EN_US.SpawnEgg(EN_US.JumpingSpider())));
		Register("hedgehog_spawn_egg", HEDGEHOG_SPAWN_EGG, List.of(EN_US.SpawnEgg(EN_US.Hedgehog())));
		//</editor-fold>
		//<editor-fold desc="Biomes and generated features">
		SINGLE_MYCELIUM_ROOTS.Initialize();
		SINGLE_BROWN_MUSHROOM.Initialize();
		SINGLE_BLUE_MUSHROOM.Initialize();
		SINGLE_RED_MUSHROOM.Initialize();
		MYCELIUM_BONEMEAL_MYCELIUM_ROOTS.Initialize();
		MYCELIUM_BONEMEAL_BROWN_MUSHROOM.Initialize();
		MYCELIUM_BONEMEAL_BLUE_MUSHROOM.Initialize();
		MYCELIUM_BONEMEAL_RED_MUSHROOM.Initialize();
		Register("huge_blue_mushroom", HUGE_BLUE_MUSHROOM_FEATURE);
		HUGE_BLUE_MUSHROOM_CONFIGURED.Initialize();
		Register("disk_grass", DISK_GRASS_FEATURE);
		DISK_GRASS_CONFIGURED.Initialize();
		DISK_GRASS_PLACED.Initialize();
		Register("mangrove_tree", MANGROVE_TREE_FEATURE);
		MANGROVE_CONFIGURED.Initialize();
		MANGROVE_CHECKED_PLACED.Initialize();
		TALL_MANGROVE_CONFIGURED.Initialize();
		TALL_MANGROVE_CHECKED_PLACED.Initialize();
		MANGROVE_VEGETATION_CONFIGURED.Initialize();
		TREES_MANGROVE_PLACED.Initialize();
		Register("cherry_tree", CHERRY_TREE_FEATURE);
		CHERRY_CONFIGURED.Initialize();
		CHERRY_CHECKED_PLACED.Initialize();
		CHERRY_BEES_005_CONFIGURED.Initialize();
		CHERRY_BEES_PLACED.Initialize();
		CHERRY_VEGETATION_CONFIGURED.Initialize();
		TREES_CHERRY_PLACED.Initialize();
		FLOWER_CHERRY_CONFIGURED.Initialize();
		FLOWER_CHERRY_PLACED.Initialize();
		Register("sculk_patch", SCULK_PATCH_FEATURE);
		SCULK_PATCH_DEEP_DARK_CONFIGURED.Initialize();
		SCULK_PATCH_DEEP_DARK_PLACED.Initialize();
		SCULK_PATCH_ANCIENT_CITY_CONFIGURED.Initialize();
		SCULK_PATCH_ANCIENT_CITY_PLACED.Initialize();
		Register("sculk_vein", SCULK_VEIN_FEATURE);
		SCULK_VEIN_CONFIGURED.Initialize();
		SCULK_VEIN_PLACED.Initialize();
		//Ancient Cities
		//TODO: Ancient Cities aren't generating anywhere ever
		ANCIENT_CITY_START_DEGRADATION.Initialize();
		ANCIENT_CITY_GENERIC_DEGRADATION.Initialize();
		ANCIENT_CITY_WALLS_DEGRADATION.Initialize();
		ANCIENT_CITY_POOL.Initialize();
		ANCIENT_CITY_CONFIGURED.Initialize();
		ANCIENT_CITIES.Initialize();
		AncientCityOutskirtsGenerator.Initialize();
		/*MANGROVE_SWAMP*/ BuiltinBiomesInvoker.Register(MANGROVE_SWAMP, ModBiomeCreator.createMangroveSwamp());
		/*DEEP_DARK*/ BuiltinBiomesInvoker.Register(DEEP_DARK, ModBiomeCreator.createDeepDark());
		/*CHERRY GROVE*/ BuiltinBiomesInvoker.Register(CHERRY_GROVE, ModBiomeCreator.createCherryGrove());
		//</editor-fold>
		//<editor-fold desc="Effects">
		Register("flashbanged", FLASHBANGED_EFFECT, List.of(EN_US.Flashbanged()));
		Register("bleeding", BLEEDING_EFFECT, List.of(EN_US.Bleeding()));
		Register("serrated", SERRATED_ENCHANTMENT, List.of(EN_US.Serrated()));
		//</editor-fold>
		RegisterOriginsPowers();
		//TODO: Give Faerie (and others) wing texture
		//TODO: Cherry Blossom Trees, Wood, Forest Biome (1.20)
		//TODO: Sniffer (1.20)
		//TODO: Archaeology (1.20)
		//TODO: Mechanism to craft Rotten Flesh -> Leather
		//TODO: Armor Trimming (1.20)
		//TODO: Weapon Customization / Forging
		RegisterCommands();
		ModGameEvent.RegisterAll();
		ModMemoryModules.Initialize();
		ModPositionSourceTypes.Initialize();
		IdentifiedSounds.RegisterAll();
		ModCriteria.Register();
		ModGameRules.Initialize();
		//Ryft Modpack
		IdentifiedSounds.RegisterPower("ryft", "angel", List.of(EN_US.clacking(EN_US.Heels())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.swims(EN_US.Someone())), List.of(EN_US.splashes(EN_US.Someone())), List.of(EN_US.splashes_hard(EN_US.Someone())), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower("ryft", "arsene", List.of(EN_US.footsteps(EN_US.Aggressive())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.swims(EN_US.Someone())), List.of(EN_US.splashes(EN_US.Someone())), List.of(EN_US.cannonballs(EN_US.Someone())), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower("ryft", "auryon", List.of(EN_US.marching(EN_US.Confident())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.sloshing(EN_US.Feathers())), List.of(EN_US.splashing(EN_US.Undignified())), List.of(EN_US.splashing(EN_US.undignified(EN_US.Loud()))), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower("ryft", "dj", List.of(EN_US.steps(EN_US.Light())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.swims(EN_US.Someone())), List.of(EN_US.splashes(EN_US.lightly(EN_US.Someone()))), List.of(EN_US.splashes_hard(EN_US.Someone())), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower("ryft", "faerie", List.of(EN_US.footsteps(EN_US.Clumsy())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.splashing(EN_US.Rhythmic())), List.of(EN_US.splashes(EN_US.awkwardly(EN_US.Someone()))), List.of(EN_US.splashing(EN_US.loudly(EN_US.Someone()))), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower("ryft", "gubby", List.of(EN_US.steps(EN_US.Warden())), List.of(EN_US.trips(EN_US.Warden())), List.of(EN_US.fell(EN_US.Warden())), List.of(EN_US.crashes(EN_US.Warden())), List.of(EN_US.swims(EN_US.Warden())), List.of(EN_US.splashes(EN_US.Warden())), List.of(EN_US.splashes_hard(EN_US.Warden())), List.of(EN_US.dies(EN_US.Warden())));
		IdentifiedSounds.RegisterPower("ryft", "kaden", List.of(EN_US.steps(EN_US.Soft())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.softly(EN_US.lands(EN_US.Someone()))), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.swims(EN_US.Someone())), List.of(EN_US.splashes(EN_US.quietly(EN_US.Someone()))), List.of(EN_US.splashes(EN_US.Someone())), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower("ryft", "kirha", List.of(EN_US.scuttles("Kirha")), List.of(EN_US.trips("Kirha")), List.of(EN_US.lands("Kirha")), List.of(EN_US.splats("Kirha")), List.of(EN_US.paddles("Kirha")), List.of(EN_US.sploshes("Kirha")), List.of(EN_US.hard(EN_US.sploshes("Kirha"))), List.of(EN_US.dies("Kirha")));
		IdentifiedSounds.RegisterPower("ryft", "lavender", List.of(EN_US.tapping(EN_US.porcelain(EN_US.Excited()))), List.of(EN_US.trips(EN_US.small(EN_US.Someone()))), List.of(EN_US.crunches(EN_US.Porcelain())), List.of(EN_US.crashes(EN_US.Porcelain())), List.of(EN_US.glugs(EN_US.Porcelain())), List.of(EN_US.splashes(EN_US.small(EN_US.Something()))), List.of(EN_US.splashes_hard(EN_US.small(EN_US.Something()))), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower("ryft", "navn", List.of(EN_US.stomping(EN_US.Wet())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.swims(EN_US.Fish(EN_US.Large()))), List.of(EN_US.splashes(EN_US.Fish(EN_US.Large()))), List.of(EN_US.splashes_hard(EN_US.Fish(EN_US.Large()))), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower("ryft", "oracle", List.of(EN_US.footsteps(EN_US.Aloof())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.crashes(EN_US.Someone())), List.of(EN_US.swims(EN_US.Someone())), List.of(EN_US.splashes(EN_US.Someone())), List.of(EN_US.splashes_hard(EN_US.Someone())), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower("ryft", "quincy", List.of(EN_US.steps(EN_US.boot(EN_US.Light()))), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.chivalrously(EN_US.crashes(EN_US.Someone()))), List.of(EN_US.swims(EN_US.Someone())), List.of(EN_US.splashes(EN_US.Someone())), List.of(EN_US.splashes_hard(EN_US.Someone())), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower("ryft", "rose", List.of(EN_US.tapping(EN_US.porcelain(EN_US.Paced()))), List.of(EN_US.trips(EN_US.medium(EN_US.Someone()))), List.of(EN_US.crunches(EN_US.Porcelain())), List.of(EN_US.crashes(EN_US.Porcelain())), List.of(EN_US.glugs(EN_US.Porcelain())), List.of(EN_US.splashes(EN_US.medium(EN_US.Something()))), List.of(EN_US.splashes_hard(EN_US.medium(EN_US.Something()))), List.of(EN_US.dies(EN_US.Someone())));
		IdentifiedSounds.RegisterPower("ryft", "zofia", List.of(EN_US.footsteps(EN_US.Cindering())), List.of(EN_US.trips(EN_US.Someone())), List.of(EN_US.fell(EN_US.Someone())), List.of(EN_US.burned(EN_US.and(EN_US.crashed(EN_US.Someone())))), List.of(EN_US.sizzles(EN_US.Water())), List.of(EN_US.splashes(EN_US.hot(EN_US.Something()))), List.of(EN_US.splashes_hard(EN_US.hot(EN_US.Something()))), List.of(EN_US.dies(EN_US.Someone())));
	}
	//TODO: Soul & Ender Fire: Jack O' Lantern
	//TODO: Carved Melons (and fire, soul, ender lanterns)

	//public static final Set<BedContainer> BEDS = new HashSet<>(Set.of( MOSS_BED, RAINBOW_BED ));
	public static final List<SignType> SIGN_TYPES = new ArrayList<>(List.of(
			CHARRED_SIGN.getType(), BAMBOO_SIGN.getType(), MANGROVE_SIGN.getType(), CHERRY_SIGN.getType()
	));
	public static final Set<StatusEffect> MILK_IMMUNE_EFFECTS = new HashSet<>(Set.of( BLEEDING_EFFECT, TINTED_GOGGLES_EFFECT ));

	@Override
	public void onInitialize() {
		LOGGER.info("Wich Pack Initializing");
		RegisterAll();
	}
}
