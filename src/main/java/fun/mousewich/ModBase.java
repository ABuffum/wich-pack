package fun.mousewich;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import fun.mousewich.advancement.ModCriteria;
import fun.mousewich.block.*;
import fun.mousewich.block.basic.*;
import fun.mousewich.block.mangrove.*;
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
import fun.mousewich.entity.warden.WardenEntity;
import fun.mousewich.event.*;
import fun.mousewich.gen.data.loot.DropTable;
import fun.mousewich.gen.data.tag.ModBiomeTags;
import fun.mousewich.gen.data.tag.ModBlockTags;
import fun.mousewich.gen.feature.*;
import fun.mousewich.gen.feature.config.MangroveTreeFeatureConfig;
import fun.mousewich.gen.structure.*;
import fun.mousewich.gen.world.ModBiomeCreator;
import fun.mousewich.item.*;
import fun.mousewich.item.basic.*;
import fun.mousewich.item.basic.tool.*;
import fun.mousewich.item.echo.*;
import fun.mousewich.item.goat.*;
import fun.mousewich.material.*;
import fun.mousewich.mixins.world.BuiltinBiomesInvoker;
import fun.mousewich.origins.powers.*;
import fun.mousewich.particle.*;
import fun.mousewich.recipe.*;
import fun.mousewich.sound.*;
import fun.mousewich.util.SculkUtils;
import fun.mousewich.util.Util;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.mixin.gamerule.GameRulesAccessor;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static fun.mousewich.ModRegistry.*;

public class ModBase implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Wich Pack");

	public static final String NAMESPACE = "wich";
	public static Identifier ID(String path) {
		if (path.contains(":")) return new Identifier(path);
		else return new Identifier(NAMESPACE, path);
	}

	private static ItemStack GetItemGroupIcon() { return new ItemStack(ECHO_SHARD); }
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(ID("wichpack"), ModBase::GetItemGroupIcon);
	public static Item.Settings ItemSettings() { return new Item.Settings().group(ITEM_GROUP); }
	public static Item.Settings ItemSettings(ItemGroup itemGroup) { return ItemSettings().group(itemGroup); }

	public static final DyeColor[] COLORS = DyeColor.values();
	public interface DyeMapFunc<T> { T op(DyeColor c); }
	public static <T> Map<DyeColor, T> MapDyeColor(DyeMapFunc<T> op) {
		Map<DyeColor, T> output = new HashMap<>();
		for(DyeColor c : COLORS) output.put(c, op.op(c));
		return output;
	}

	//<editor-fold desc="Boats">
	public static final EntityType<ModBoatEntity> MOD_BOAT_ENTITY = FabricEntityTypeBuilder.<ModBoatEntity>create(SpawnGroup.MISC, ModBoatEntity::new).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).trackRangeBlocks(10).build();

	public static final EntityType<ChestBoatEntity> CHEST_BOAT_ENTITY = FabricEntityTypeBuilder.<ChestBoatEntity>create(SpawnGroup.MISC, ChestBoatEntity::new).dimensions(EntityDimensions.fixed(1.375f, 0.5625f)).trackRangeBlocks(10).build();
	public static final EntityType<ModChestBoatEntity> MOD_CHEST_BOAT_ENTITY = FabricEntityTypeBuilder.<ModChestBoatEntity>create(SpawnGroup.MISC, ModChestBoatEntity::new).dimensions(EntityDimensions.fixed(1.375f, 0.5625f)).trackRangeBlocks(10).build();

	public static final Item OAK_CHEST_BOAT = ModFactory.MakeChestBoat(BoatEntity.Type.OAK, ItemGroup.TRANSPORTATION);
	public static final Item SPRUCE_CHEST_BOAT = ModFactory.MakeChestBoat(BoatEntity.Type.SPRUCE, ItemGroup.TRANSPORTATION);
	public static final Item BIRCH_CHEST_BOAT = ModFactory.MakeChestBoat(BoatEntity.Type.BIRCH, ItemGroup.TRANSPORTATION);
	public static final Item JUNGLE_CHEST_BOAT = ModFactory.MakeChestBoat(BoatEntity.Type.JUNGLE, ItemGroup.TRANSPORTATION);
	public static final Item ACACIA_CHEST_BOAT = ModFactory.MakeChestBoat(BoatEntity.Type.ACACIA, ItemGroup.TRANSPORTATION);
	public static final Item DARK_OAK_CHEST_BOAT = ModFactory.MakeChestBoat(BoatEntity.Type.DARK_OAK, ItemGroup.TRANSPORTATION);
	//</editor-fold>
	//<editor-fold desc="Light Sources">
	public static final DefaultParticleType UNDERWATER_TORCH_GLOW = FabricParticleTypes.simple(false);
	public static final TorchContainer UNDERWATER_TORCH = TorchContainer.Waterloggable(ModFactory.TorchSettings(14, BlockSoundGroup.WOOD), UNDERWATER_TORCH_GLOW);
	public static final UnlitTorchContainer UNLIT_TORCH = new UnlitTorchContainer(new UnlitTorchBlock((TorchBlock)Blocks.TORCH), new UnlitWallTorchBlock((WallTorchBlock)Blocks.WALL_TORCH)).drops(Items.TORCH);
	public static final UnlitTorchContainer UNLIT_SOUL_TORCH = new UnlitTorchContainer(new UnlitTorchBlock((TorchBlock)Blocks.SOUL_TORCH), new UnlitWallTorchBlock((WallTorchBlock)Blocks.SOUL_WALL_TORCH)).drops(Items.SOUL_TORCH);
	public static final Block UNLIT_LANTERN = new UnlitLanternBlock(Blocks.LANTERN, Items.LANTERN).dropsLantern();
	public static final Block UNLIT_SOUL_LANTERN = new UnlitLanternBlock(Blocks.SOUL_LANTERN, Items.SOUL_LANTERN).dropsLantern();
	//</editor-fold>
	//<editor-fold desc="Soul & Ender Fire">
	public static final DefaultParticleType SMALL_SOUL_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final DefaultParticleType ENDER_FIRE_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final DefaultParticleType SMALL_ENDER_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final BlockContainer SOUL_CANDLE = ModFactory.MakeCandle(MapColor.BROWN, 2.5);
	public static final BlockContainer ENDER_CANDLE = ModFactory.MakeCandle(MapColor.PALE_YELLOW, 2.75);
	public static final Block SOUL_CANDLE_CAKE = new ModCandleCakeBlock(2).drops(SOUL_CANDLE.asBlock());
	public static final Block ENDER_CANDLE_CAKE = new ModCandleCakeBlock(3).drops(ENDER_CANDLE.asBlock());
	public static final TorchContainer ENDER_TORCH = ModFactory.MakeTorch(12, BlockSoundGroup.WOOD, ENDER_FIRE_FLAME_PARTICLE);
	public static final BlockContainer ENDER_LANTERN = ModFactory.MakeLantern(13);
	public static final BlockContainer ENDER_CAMPFIRE = ModFactory.MakeCampfire(13, 3, MapColor.SPRUCE_BROWN, BlockSoundGroup.WOOD, false).drops(DropTable.ENDER_CAMPFIRE);
	//</editor-fold>
	//<editor-fold desc="Extended Stone">
	public static final BlockContainer POLISHED_ANDESITE_WALL = ModFactory.MakeWall(Blocks.POLISHED_ANDESITE);
	public static final BlockContainer POLISHED_DIORITE_WALL = ModFactory.MakeWall(Blocks.POLISHED_DIORITE);
	public static final BlockContainer POLISHED_GRANITE_WALL = ModFactory.MakeWall(Blocks.POLISHED_GRANITE);
	public static final BlockContainer SMOOTH_SANDSTONE_WALL = ModFactory.MakeWall(Blocks.SMOOTH_SANDSTONE);
	public static final BlockContainer SMOOTH_RED_SANDSTONE_WALL = ModFactory.MakeWall(Blocks.SMOOTH_RED_SANDSTONE);
	public static final BlockContainer DARK_PRISMARINE_WALL = ModFactory.MakeWall(Blocks.DARK_PRISMARINE);
	public static final BlockContainer PURPUR_WALL = ModFactory.MakeWall(Blocks.PURPUR_BLOCK);
	//</editor-fold>
	//<editor-fold desc="Extended Calcite">
	public static final BlockContainer CALCITE_SLAB = ModFactory.MakeSlab(Blocks.CALCITE);
	public static final BlockContainer CALCITE_STAIRS = ModFactory.MakeStairs(Blocks.CALCITE);
	public static final BlockContainer CALCITE_WALL = ModFactory.MakeWall(Blocks.CALCITE);
	public static final BlockContainer SMOOTH_CALCITE = new BlockContainer(new Block(Block.Settings.copy(Blocks.CALCITE))).dropSelf();
	public static final BlockContainer SMOOTH_CALCITE_SLAB = ModFactory.MakeSlab(SMOOTH_CALCITE);
	public static final BlockContainer SMOOTH_CALCITE_STAIRS = ModFactory.MakeStairs(SMOOTH_CALCITE);
	public static final BlockContainer SMOOTH_CALCITE_WALL = ModFactory.MakeWall(SMOOTH_CALCITE);
	public static final BlockContainer CALCITE_BRICKS = new BlockContainer(new Block(Block.Settings.copy(Blocks.CALCITE))).dropSelf();
	public static final BlockContainer CALCITE_BRICK_SLAB = ModFactory.MakeSlab(CALCITE_BRICKS);
	public static final BlockContainer CALCITE_BRICK_STAIRS = ModFactory.MakeStairs(CALCITE_BRICKS);
	public static final BlockContainer CALCITE_BRICK_WALL = ModFactory.MakeWall(CALCITE_BRICKS);
	//</editor-fold>
	//<editor-fold desc="Extended Dripstone">
	public static final BlockContainer DRIPSTONE_SLAB = ModFactory.MakeSlab(Blocks.DRIPSTONE_BLOCK);
	public static final BlockContainer DRIPSTONE_STAIRS = ModFactory.MakeStairs(Blocks.DRIPSTONE_BLOCK);
	public static final BlockContainer DRIPSTONE_WALL = ModFactory.MakeWall(Blocks.DRIPSTONE_BLOCK);
	public static final BlockContainer SMOOTH_DRIPSTONE = new BlockContainer(new Block(Block.Settings.copy(Blocks.DRIPSTONE_BLOCK))).dropSelf().dropSelf();
	public static final BlockContainer SMOOTH_DRIPSTONE_SLAB = ModFactory.MakeSlab(SMOOTH_DRIPSTONE);
	public static final BlockContainer SMOOTH_DRIPSTONE_STAIRS = ModFactory.MakeStairs(SMOOTH_DRIPSTONE);
	public static final BlockContainer SMOOTH_DRIPSTONE_WALL = ModFactory.MakeWall(SMOOTH_DRIPSTONE);
	public static final BlockContainer DRIPSTONE_BRICKS = new BlockContainer(new Block(Block.Settings.copy(Blocks.DRIPSTONE_BLOCK))).dropSelf();
	public static final BlockContainer DRIPSTONE_BRICK_SLAB = ModFactory.MakeSlab(DRIPSTONE_BRICKS);
	public static final BlockContainer DRIPSTONE_BRICK_STAIRS = ModFactory.MakeStairs(DRIPSTONE_BRICKS);
	public static final BlockContainer DRIPSTONE_BRICK_WALL = ModFactory.MakeWall(DRIPSTONE_BRICKS);
	//</editor-fold>
	//<editor-fold desc="Extended Tuff">
	public static final BlockContainer TUFF_SLAB = ModFactory.MakeSlab(Blocks.TUFF);
	public static final BlockContainer TUFF_STAIRS = ModFactory.MakeStairs(Blocks.TUFF);
	public static final BlockContainer TUFF_WALL = ModFactory.MakeWall(Blocks.TUFF);
	public static final BlockContainer SMOOTH_TUFF = new BlockContainer(new Block(Block.Settings.copy(Blocks.TUFF))).dropSelf().dropSelf();
	public static final BlockContainer SMOOTH_TUFF_SLAB = ModFactory.MakeSlab(SMOOTH_TUFF);
	public static final BlockContainer SMOOTH_TUFF_STAIRS = ModFactory.MakeStairs(SMOOTH_TUFF);
	public static final BlockContainer SMOOTH_TUFF_WALL = ModFactory.MakeWall(SMOOTH_TUFF);
	public static final BlockContainer TUFF_BRICKS = new BlockContainer(new Block(Block.Settings.copy(Blocks.TUFF))).dropSelf();
	public static final BlockContainer TUFF_BRICK_SLAB = ModFactory.MakeSlab(TUFF_BRICKS);
	public static final BlockContainer TUFF_BRICK_STAIRS = ModFactory.MakeStairs(TUFF_BRICKS);
	public static final BlockContainer TUFF_BRICK_WALL = ModFactory.MakeWall(TUFF_BRICKS);
	//</editor-fold>
	//<editor-fold desc="Extended Amethyst">
	public static final BlockContainer AMETHYST_SLAB = ModFactory.MakeSlab(Blocks.AMETHYST_BLOCK);
	public static final BlockContainer AMETHYST_STAIRS = ModFactory.MakeStairs(Blocks.AMETHYST_BLOCK);
	public static final BlockContainer AMETHYST_WALL = ModFactory.MakeWall(Blocks.AMETHYST_BLOCK);
	public static final BlockContainer AMETHYST_CRYSTAL_BLOCK = new BlockContainer(new Block(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f, 1.5f).requiresTool().luminance(Util.LUMINANCE_5))).dropSelf();
	public static final BlockContainer AMETHYST_CRYSTAL_SLAB = ModFactory.MakeSlab(AMETHYST_CRYSTAL_BLOCK);
	public static final BlockContainer AMETHYST_CRYSTAL_STAIRS = ModFactory.MakeStairs(AMETHYST_CRYSTAL_BLOCK);
	public static final BlockContainer AMETHYST_CRYSTAL_WALL = ModFactory.MakeWall(AMETHYST_CRYSTAL_BLOCK);
	public static final BlockContainer AMETHYST_BRICKS = new BlockContainer(new Block(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f, 1.5f).requiresTool())).dropSelf();
	public static final BlockContainer AMETHYST_BRICK_SLAB = ModFactory.MakeSlab(AMETHYST_BRICKS);
	public static final BlockContainer AMETHYST_BRICK_STAIRS = ModFactory.MakeStairs(AMETHYST_BRICKS);
	public static final BlockContainer AMETHYST_BRICK_WALL = ModFactory.MakeWall(AMETHYST_BRICKS);
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
	public static final Item AMETHYST_HORSE_ARMOR = new ModHorseArmorItem(10, "amethyst", ItemSettings().maxCount(1)).dispenseSilently();
	//</editor-fold>
	//<editor-fold desc="More Glass">
	public static final BlockContainer TINTED_GLASS_PANE = new BlockContainer(new TintedGlassPaneBlock(Block.Settings.of(Material.GLASS).mapColor(MapColor.GRAY).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque())).dropSelf();
	public static final BlockContainer TINTED_GLASS_SLAB = new BlockContainer(new TintedGlassSlabBlock(Blocks.TINTED_GLASS)).dropSlabs();
	public static final Item TINTED_GOGGLES = new ModArmorItem(ModArmorMaterials.TINTED_GOGGLES, EquipmentSlot.HEAD);
	public static final BlockContainer GLASS_SLAB = new BlockContainer(new GlassSlabBlock(Blocks.GLASS)).dropSlabs();
	public static final Map<DyeColor, BlockContainer> STAINED_GLASS_SLABS = MapDyeColor((color) -> new BlockContainer(new StainedGlassSlabBlock(color, Util.GetStainedGlassBlock(color))));
	//</editor-fold>
	//<editor-fold desc="Extended Emerald">
	public static final BlockContainer EMERALD_BRICKS = new BlockContainer(new Block(Block.Settings.copy(Blocks.EMERALD_BLOCK))).dropSelf();
	public static final BlockContainer EMERALD_BRICK_SLAB = ModFactory.MakeSlab(EMERALD_BRICKS);
	public static final BlockContainer EMERALD_BRICK_STAIRS = ModFactory.MakeStairs(EMERALD_BRICKS);
	public static final BlockContainer EMERALD_BRICK_WALL = ModFactory.MakeWall(EMERALD_BRICKS);
	public static final BlockContainer CUT_EMERALD = new BlockContainer(new Block(Block.Settings.copy(Blocks.EMERALD_BLOCK))).dropSelf();
	public static final BlockContainer CUT_EMERALD_SLAB = ModFactory.MakeSlab(CUT_EMERALD);
	public static final BlockContainer CUT_EMERALD_STAIRS = ModFactory.MakeStairs(CUT_EMERALD);
	public static final BlockContainer CUT_EMERALD_WALL = ModFactory.MakeWall(CUT_EMERALD);
	public static final Item EMERALD_AXE = new ModAxeItem(ModToolMaterials.EMERALD, 5, -3);
	public static final Item EMERALD_HOE = new ModHoeItem(ModToolMaterials.EMERALD, -2, 0);
	public static final Item EMERALD_PICKAXE = new ModPickaxeItem(ModToolMaterials.EMERALD, 1, -2.8F);
	public static final Item EMERALD_SHOVEL = new ModShovelItem(ModToolMaterials.EMERALD, 1.5F, -3);
	public static final Item EMERALD_SWORD = new ModSwordItem(ModToolMaterials.EMERALD, 3, -2.4F);
	public static final Item EMERALD_KNIFE = new ModKnifeItem(ModToolMaterials.EMERALD);
	public static final Item EMERALD_HELMET = new ModArmorItem(ModArmorMaterials.EMERALD, EquipmentSlot.HEAD);
	public static final Item EMERALD_CHESTPLATE = new ModArmorItem(ModArmorMaterials.EMERALD, EquipmentSlot.CHEST);
	public static final Item EMERALD_LEGGINGS = new ModArmorItem(ModArmorMaterials.EMERALD, EquipmentSlot.LEGS);
	public static final Item EMERALD_BOOTS = new ModArmorItem(ModArmorMaterials.EMERALD, EquipmentSlot.FEET);
	public static final Item EMERALD_HORSE_ARMOR = new ModHorseArmorItem(9, "emerald", ItemSettings().maxCount(1)).dispenseSilently();
	//</editor-fold>
	//<editor-fold desc="Extended Diamond">
	public static final BlockContainer DIAMOND_SLAB = ModFactory.MakeSlab(Blocks.DIAMOND_BLOCK);
	public static final BlockContainer DIAMOND_STAIRS = ModFactory.MakeStairs(Blocks.DIAMOND_BLOCK);
	public static final BlockContainer DIAMOND_WALL = ModFactory.MakeWall(Blocks.DIAMOND_BLOCK);
	public static final BlockContainer DIAMOND_BRICKS = new BlockContainer(new Block(Block.Settings.copy(Blocks.DIAMOND_BLOCK))).dropSelf().dropSelf();
	public static final BlockContainer DIAMOND_BRICK_SLAB = ModFactory.MakeSlab(DIAMOND_BRICKS);
	public static final BlockContainer DIAMOND_BRICK_STAIRS = ModFactory.MakeStairs(DIAMOND_BRICKS);
	public static final BlockContainer DIAMOND_BRICK_WALL = ModFactory.MakeWall(DIAMOND_BRICKS);
	//</editor-fold>
	//<editor-fold desc="Extended Quartz">
	public static final BlockContainer QUARTZ_CRYSTAL_BLOCK = new BlockContainer(new Block(Block.Settings.copy(Blocks.QUARTZ_BLOCK))).dropSelf();
	public static final BlockContainer QUARTZ_CRYSTAL_SLAB = ModFactory.MakeSlab(QUARTZ_CRYSTAL_BLOCK);
	public static final BlockContainer QUARTZ_CRYSTAL_STAIRS = ModFactory.MakeStairs(QUARTZ_CRYSTAL_BLOCK);
	public static final BlockContainer QUARTZ_CRYSTAL_WALL = ModFactory.MakeWall(QUARTZ_CRYSTAL_BLOCK);
	public static final BlockContainer SMOOTH_QUARTZ_WALL = ModFactory.MakeWall(Blocks.SMOOTH_QUARTZ);
	public static final BlockContainer QUARTZ_WALL = ModFactory.MakeWall(Blocks.QUARTZ_BLOCK);
	public static final BlockContainer QUARTZ_BRICK_SLAB = ModFactory.MakeSlab(Blocks.QUARTZ_BRICKS);
	public static final BlockContainer QUARTZ_BRICK_STAIRS = ModFactory.MakeStairs(Blocks.QUARTZ_BRICKS);
	public static final BlockContainer QUARTZ_BRICK_WALL = ModFactory.MakeWall(Blocks.QUARTZ_BRICKS);
	public static final Item QUARTZ_AXE = new ModAxeItem(ModToolMaterials.QUARTZ, 5, -3);
	public static final Item QUARTZ_HOE = new ModHoeItem(ModToolMaterials.QUARTZ, -1, 0);
	public static final Item QUARTZ_PICKAXE = new ModPickaxeItem(ModToolMaterials.QUARTZ, 1, -2.8F);
	public static final Item QUARTZ_SHOVEL = new ModShovelItem(ModToolMaterials.QUARTZ, 1.5F, -3);
	public static final Item QUARTZ_SWORD = new ModSwordItem(ModToolMaterials.QUARTZ, 3, -2.4F);
	public static final Item QUARTZ_KNIFE = new ModKnifeItem(ModToolMaterials.QUARTZ);
	public static final Item QUARTZ_HELMET = new ModArmorItem(ModArmorMaterials.QUARTZ, EquipmentSlot.HEAD);
	public static final Item QUARTZ_CHESTPLATE = new ModArmorItem(ModArmorMaterials.QUARTZ, EquipmentSlot.CHEST);
	public static final Item QUARTZ_LEGGINGS = new ModArmorItem(ModArmorMaterials.QUARTZ, EquipmentSlot.LEGS);
	public static final Item QUARTZ_BOOTS = new ModArmorItem(ModArmorMaterials.QUARTZ, EquipmentSlot.FEET);
	public static final Item QUARTZ_HORSE_ARMOR = new ModHorseArmorItem(8, "quartz", ItemSettings().maxCount(1)).dispenseSilently();
	//</editor-fold>
	//<editor-fold desc="Extended Iron">
	public static final DefaultParticleType IRON_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final TorchContainer IRON_TORCH = ModFactory.MakeTorch(14, BlockSoundGroup.METAL, IRON_FLAME_PARTICLE);
	public static final TorchContainer IRON_SOUL_TORCH = ModFactory.MakeSoulTorch(BlockSoundGroup.METAL);
	public static final TorchContainer IRON_ENDER_TORCH = ModFactory.MakeEnderTorch(BlockSoundGroup.METAL);
	public static final TorchContainer UNDERWATER_IRON_TORCH = ModFactory.MakeUnderwaterTorch(BlockSoundGroup.METAL);
	public static final BlockContainer WHITE_IRON_LANTERN = ModFactory.MakeLantern(15);
	public static final BlockContainer WHITE_IRON_SOUL_LANTERN = ModFactory.MakeLantern(10);
	public static final BlockContainer WHITE_IRON_ENDER_LANTERN = ModFactory.MakeLantern(10);
	public static final BlockContainer IRON_BUTTON = new BlockContainer(new MetalButtonBlock(Block.Settings.of(Material.DECORATION).noCollision().strength(10.0F).sounds(BlockSoundGroup.METAL))).dropSelf();
	public static final BlockContainer WHITE_IRON_CHAIN = ModFactory.MakeChain();
	public static final BlockContainer IRON_WALL = ModFactory.MakeWall(Blocks.IRON_BLOCK);
	public static final BlockContainer IRON_BRICKS = new BlockContainer(new Block(Block.Settings.copy(Blocks.IRON_BLOCK))).dropSelf();
	public static final BlockContainer IRON_BRICK_SLAB = ModFactory.MakeSlab(IRON_BRICKS);
	public static final BlockContainer IRON_BRICK_STAIRS = ModFactory.MakeStairs(IRON_BRICKS);
	public static final BlockContainer IRON_BRICK_WALL = ModFactory.MakeWall(IRON_BRICKS);
	public static final BlockContainer CUT_IRON = new BlockContainer(new Block(Block.Settings.copy(Blocks.IRON_BLOCK))).dropSelf();
	public static final BlockContainer CUT_IRON_PILLAR = new BlockContainer(new PillarBlock(Block.Settings.copy(CUT_IRON.asBlock()))).dropSelf();
	public static final BlockContainer CUT_IRON_SLAB = ModFactory.MakeSlab(CUT_IRON);
	public static final BlockContainer CUT_IRON_STAIRS = ModFactory.MakeStairs(CUT_IRON);
	public static final BlockContainer CUT_IRON_WALL = ModFactory.MakeWall(CUT_IRON);
	//</editor-fold>
	//<editor-fold desc="Extended Gold">
	public static final DefaultParticleType GOLD_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final TorchContainer GOLD_TORCH = ModFactory.MakeTorch(14, BlockSoundGroup.METAL, GOLD_FLAME_PARTICLE);
	public static final TorchContainer GOLD_SOUL_TORCH = ModFactory.MakeSoulTorch(BlockSoundGroup.METAL);
	public static final TorchContainer GOLD_ENDER_TORCH = ModFactory.MakeEnderTorch(BlockSoundGroup.METAL);
	public static final TorchContainer UNDERWATER_GOLD_TORCH = ModFactory.MakeUnderwaterTorch(BlockSoundGroup.METAL);
	public static final BlockContainer GOLD_LANTERN = ModFactory.MakeLantern(15);
	public static final BlockContainer GOLD_SOUL_LANTERN = ModFactory.MakeLantern(10);
	public static final BlockContainer GOLD_ENDER_LANTERN = ModFactory.MakeLantern(10);
	public static final BlockContainer GOLD_BUTTON = new BlockContainer(new MetalButtonBlock(Block.Settings.of(Material.DECORATION).noCollision().strength(10.0F).sounds(BlockSoundGroup.METAL))).dropSelf();
	public static final BlockContainer GOLD_CHAIN = ModFactory.MakeChain();
	public static final BlockContainer GOLD_BARS = new BlockContainer(new ModPaneBlock(Block.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL).nonOpaque())).dropSelf();
	public static final BlockContainer GOLD_WALL = ModFactory.MakeWall(Blocks.GOLD_BLOCK);
	public static final BlockContainer GOLD_BRICKS = new BlockContainer(new Block(Block.Settings.copy(Blocks.GOLD_BLOCK))).dropSelf();
	public static final BlockContainer GOLD_BRICK_SLAB = ModFactory.MakeSlab(GOLD_BRICKS);
	public static final BlockContainer GOLD_BRICK_STAIRS = ModFactory.MakeStairs(GOLD_BRICKS);
	public static final BlockContainer GOLD_BRICK_WALL = ModFactory.MakeWall(GOLD_BRICKS);
	public static final BlockContainer CUT_GOLD = new BlockContainer(new Block(Block.Settings.copy(Blocks.GOLD_BLOCK))).dropSelf();
	public static final BlockContainer CUT_GOLD_PILLAR = new BlockContainer(new PillarBlock(Block.Settings.copy(CUT_GOLD.asBlock()))).dropSelf();
	public static final BlockContainer CUT_GOLD_SLAB = ModFactory.MakeSlab(CUT_GOLD);
	public static final BlockContainer CUT_GOLD_STAIRS = ModFactory.MakeStairs(CUT_GOLD);
	public static final BlockContainer CUT_GOLD_WALL = ModFactory.MakeWall(CUT_GOLD);
	//</editor-fold>
	//<editor-fold desc="Extended Netherite">
	public static final DefaultParticleType NETHERITE_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final Item NETHERITE_NUGGET = new Item(ItemSettings());
	public static final TorchContainer NETHERITE_TORCH = ModFactory.MakeTorch(14, BlockSoundGroup.NETHERITE, NETHERITE_FLAME_PARTICLE);
	public static final TorchContainer NETHERITE_SOUL_TORCH = ModFactory.MakeSoulTorch(BlockSoundGroup.NETHERITE);
	public static final TorchContainer NETHERITE_ENDER_TORCH = ModFactory.MakeEnderTorch(BlockSoundGroup.NETHERITE);
	public static final TorchContainer UNDERWATER_NETHERITE_TORCH = ModFactory.MakeUnderwaterTorch(BlockSoundGroup.NETHERITE);
	public static final BlockContainer NETHERITE_LANTERN = ModFactory.MakeLantern(15);
	public static final BlockContainer NETHERITE_SOUL_LANTERN = ModFactory.MakeLantern(10);
	public static final BlockContainer NETHERITE_ENDER_LANTERN = ModFactory.MakeLantern(10);
	public static final BlockContainer NETHERITE_BUTTON = new BlockContainer(new MetalButtonBlock(Block.Settings.of(Material.DECORATION).noCollision().strength(10.0F).sounds(BlockSoundGroup.NETHERITE))).dropSelf();
	public static final BlockContainer NETHERITE_CHAIN = ModFactory.MakeChain();
	public static final BlockContainer NETHERITE_BARS = new BlockContainer(new ModPaneBlock(Block.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.NETHERITE).nonOpaque())).dropSelf();
	public static final BlockContainer NETHERITE_WALL = ModFactory.MakeWall(Blocks.NETHERITE_BLOCK);
	public static final BlockContainer NETHERITE_BRICKS = new BlockContainer(new Block(Block.Settings.copy(Blocks.NETHERITE_BLOCK))).dropSelf();
	public static final BlockContainer NETHERITE_BRICK_SLAB = ModFactory.MakeSlab(NETHERITE_BRICKS);
	public static final BlockContainer NETHERITE_BRICK_STAIRS = ModFactory.MakeStairs(NETHERITE_BRICKS);
	public static final BlockContainer NETHERITE_BRICK_WALL = ModFactory.MakeWall(NETHERITE_BRICKS);
	public static final BlockContainer CUT_NETHERITE = new BlockContainer(new Block(Block.Settings.copy(Blocks.NETHERITE_BLOCK))).dropSelf();
	public static final BlockContainer CUT_NETHERITE_PILLAR = new BlockContainer(new PillarBlock(Block.Settings.copy(CUT_NETHERITE.asBlock()))).dropSelf();
	public static final BlockContainer CUT_NETHERITE_SLAB = ModFactory.MakeSlab(CUT_NETHERITE);
	public static final BlockContainer CUT_NETHERITE_STAIRS = ModFactory.MakeStairs(CUT_NETHERITE);
	public static final BlockContainer CUT_NETHERITE_WALL = ModFactory.MakeWall(CUT_NETHERITE);
	public static final Item NETHERITE_HORSE_ARMOR = new ModHorseArmorItem(15, "netherite", ItemSettings().maxCount(1)).dispenseSilently();
	public static final BlockContainer CRUSHING_WEIGHTED_PRESSURE_PLATE = new BlockContainer(new ModWeightedPressurePlateBlock(300, Block.Settings.of(Material.METAL).requiresTool().noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)), ItemSettings().fireproof()).dropSelf();
	//</editor-fold>
	public static final Item HORN = new Item(ItemSettings());
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
	//<editor-fold desc="More Wool">
	public static final Map<DyeColor, BlockContainer> WOOL_SLABS = MapDyeColor((color) -> new BlockContainer(new ModSlabBlock(Util.GetWoolBlock(color))).flammable(40, 40).fuel(50).dropSlabs());
	public static final BlockContainer RAINBOW_WOOL = new BlockContainer(new ModFacingBlock(Blocks.WHITE_WOOL)).flammable(30, 60).fuel(100).dropSelf();
	public static final BlockContainer RAINBOW_WOOL_SLAB = new BlockContainer(new HorizontalFacingSlabBlock(RAINBOW_WOOL.asBlock())).flammable(40, 40).fuel(50).dropSlabs();
	public static final BlockContainer RAINBOW_CARPET = new BlockContainer(new HorziontalFacingCarpetBlock(RAINBOW_WOOL.asBlock())).flammable(60, 20).fuel(67).dropSelf();
	//</editor-fold>
	//<editor-fold desc="More Moss">
	public static final BlockContainer MOSS_SLAB = new BlockContainer(new MossSlabBlock(Blocks.MOSS_BLOCK)).dropSlabs();
	//public static final BedContainer MOSS_BED = ModFactory.MakeBed("moss");
	//</editor-fold>
	//<editor-fold desc="Goat">
	public static final Item GOAT_HORN = new GoatHornItem(ItemSettings(ItemGroup.MISC).maxCount(1));
	//Extended
	public static final Item CHEVON = new Item(ItemSettings().food(FoodComponents.MUTTON));
	public static final Item COOKED_CHEVON = new Item(ItemSettings().food(FoodComponents.COOKED_MUTTON));
	public static final Map<DyeColor, BlockContainer> FLEECE = MapDyeColor((color) -> new BlockContainer(new Block(Block.Settings.copy(Util.GetWoolBlock(color)))).flammable(30, 60).fuel(100).dropSelf());
	public static final Map<DyeColor, BlockContainer> FLEECE_SLABS = MapDyeColor((color) -> new BlockContainer(new ModSlabBlock(FLEECE.get(color).asBlock())).flammable(40, 40).fuel(50).dropSlabs());
	public static final Map<DyeColor, BlockContainer> FLEECE_CARPETS = MapDyeColor((color) -> new BlockContainer(new ModDyedCarpetBlock(color, Block.Settings.copy(Util.GetWoolCarpetBlock(color)))).flammable(60, 20).fuel(67).dispenser(new HorseArmorDispenserBehavior()::dispenseSilently).dropSelf());
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
	//<editor-fold desc="Studded Leather">
	public static final Item STUDDED_LEATHER_HELMET = new ModArmorItem(ModArmorMaterials.STUDDED_LEATHER, EquipmentSlot.HEAD);
	public static final Item STUDDED_LEATHER_CHESTPLATE = new ModArmorItem(ModArmorMaterials.STUDDED_LEATHER, EquipmentSlot.CHEST);
	public static final Item STUDDED_LEATHER_LEGGINGS = new ModArmorItem(ModArmorMaterials.STUDDED_LEATHER, EquipmentSlot.LEGS);
	public static final Item STUDDED_LEATHER_BOOTS = new ModArmorItem(ModArmorMaterials.STUDDED_LEATHER, EquipmentSlot.FEET);
	public static final Item STUDDED_LEATHER_HORSE_ARMOR = new ModHorseArmorItem(4, "studded_leather", ItemSettings().maxCount(1)).dispenseSilently();
	//</editor-fold>
	//<editor-fold desc="Music Discs">
	public static final Item MUSIC_DISC_5 = new ModMusicDiscItem(15, ModSoundEvents.MUSIC_DISC_5, ItemSettings(ItemGroup.MISC).maxCount(1).rarity(Rarity.RARE));
	public static final Item DISC_FRAGMENT_5 = new DiscFragmentItem(ItemSettings(ItemGroup.MISC));
	//</editor-fold>
	//<editor-fold desc="Mud">
	public static final BlockContainer MUD = new BlockContainer(new MudBlock(Block.Settings.copy(Blocks.DIRT).mapColor(MapColor.TERRACOTTA_CYAN).allowsSpawning(ModFactory::always).solidBlock(ModFactory::always).blockVision(ModFactory::always).suffocates(ModFactory::always).sounds(ModBlockSoundGroups.MUD)), ItemGroup.BUILDING_BLOCKS).dropSelf();
	public static final BlockContainer PACKED_MUD = new BlockContainer(new Block(Block.Settings.copy(Blocks.DIRT).strength(1.0f, 3.0f).sounds(ModBlockSoundGroups.PACKED_MUD)), ItemGroup.BUILDING_BLOCKS).dropSelf();
	public static final BlockContainer MUD_BRICKS = new BlockContainer(new Block(Block.Settings.of(Material.STONE, MapColor.TERRACOTTA_LIGHT_GRAY).requiresTool().strength(1.5f, 3.0f).sounds(ModBlockSoundGroups.MUD_BRICKS)), ItemGroup.BUILDING_BLOCKS).dropSelf();
	public static final BlockContainer MUD_BRICK_SLAB = ModFactory.MakeSlab(MUD_BRICKS, ItemGroup.BUILDING_BLOCKS);
	public static final BlockContainer MUD_BRICK_STAIRS = ModFactory.MakeStairs(MUD_BRICKS, ItemGroup.BUILDING_BLOCKS);
	public static final BlockContainer MUD_BRICK_WALL = ModFactory.MakeWall(MUD_BRICKS, ItemGroup.BUILDING_BLOCKS);
	//</editor-fold>
	//<editor-fold desc="Charred Wood">
	public static final BlockContainer CHARRED_LOG = new BlockContainer(new PillarBlock(Block.Settings.of(Material.WOOD, MapColor.BLACK).strength(2.0F).sounds(BlockSoundGroup.WOOD))).dropSelf();
	public static final BlockContainer STRIPPED_CHARRED_LOG = new BlockContainer(new PillarBlock(Block.Settings.copy(CHARRED_LOG.asBlock()))).dropSelf();
	public static final BlockContainer CHARRED_WOOD = ModFactory.MakeWood(CHARRED_LOG);
	public static final BlockContainer STRIPPED_CHARRED_WOOD = ModFactory.MakeWood(STRIPPED_CHARRED_LOG);
	public static final BlockContainer CHARRED_PLANKS = ModFactory.MakePlanks(MapColor.BLACK);
	public static final BlockContainer CHARRED_SLAB = ModFactory.MakeSlab(CHARRED_PLANKS);
	public static final BlockContainer CHARRED_STAIRS = ModFactory.MakeStairs(CHARRED_PLANKS);
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
	//</editor-fold>
	//<editor-fold desc="Mangrove">
	public static final BlockContainer MANGROVE_LOG = new BlockContainer(new PillarBlock(Block.Settings.of(Material.WOOD, MapColor.RED).strength(2.0F).sounds(BlockSoundGroup.WOOD)), ItemGroup.BUILDING_BLOCKS).flammable(5, 5).fuel(300).dropSelf();
	public static final BlockContainer STRIPPED_MANGROVE_LOG = new BlockContainer(new PillarBlock(Block.Settings.copy(MANGROVE_LOG.asBlock())), ItemGroup.BUILDING_BLOCKS).flammable(5, 5).fuel(300).dropSelf();
	public static final BlockContainer MANGROVE_WOOD = ModFactory.MakeWood(MANGROVE_LOG, ItemGroup.BUILDING_BLOCKS).flammable(5, 5).fuel(300);
	public static final BlockContainer STRIPPED_MANGROVE_WOOD = ModFactory.MakeWood(STRIPPED_MANGROVE_LOG, ItemGroup.BUILDING_BLOCKS).flammable(5, 5).fuel(300);
	public static final BlockContainer MANGROVE_LEAVES = new BlockContainer(new MangroveLeavesBlock(ModFactory.LeafBlockSettings()), ItemGroup.DECORATIONS).flammable(30, 60).compostable(0.3f);
	public static final BlockContainer MANGROVE_PLANKS = ModFactory.MakePlanks(MapColor.RED, ItemGroup.BUILDING_BLOCKS).flammable(5, 20).fuel(300);
	public static final BlockContainer MANGROVE_SLAB = ModFactory.MakeSlab(MANGROVE_PLANKS, ItemGroup.BUILDING_BLOCKS).flammable(5, 20).fuel(150);
	public static final BlockContainer MANGROVE_STAIRS = ModFactory.MakeStairs(MANGROVE_PLANKS, ItemGroup.BUILDING_BLOCKS).flammable(5, 20).fuel(300);
	public static final BlockContainer MANGROVE_FENCE = ModFactory.MakeWoodFence(MANGROVE_PLANKS, ItemGroup.DECORATIONS).flammable(5, 20).fuel(300);
	public static final BlockContainer MANGROVE_FENCE_GATE = ModFactory.MakeWoodFenceGate(MANGROVE_PLANKS, ItemGroup.REDSTONE).flammable(5, 20).fuel(300);
	public static final BlockContainer MANGROVE_DOOR = ModFactory.MakeWoodDoor(MANGROVE_PLANKS, ItemGroup.REDSTONE).fuel(200);
	public static final BlockContainer MANGROVE_TRAPDOOR = ModFactory.MakeWoodTrapdoor(MANGROVE_PLANKS, ItemGroup.REDSTONE).fuel(300);
	public static final BlockContainer MANGROVE_PRESSURE_PLATE = ModFactory.MakeWoodPressurePlate(MANGROVE_PLANKS, ItemGroup.REDSTONE).fuel(300);
	public static final BlockContainer MANGROVE_BUTTON = ModFactory.MakeWoodButton(ItemGroup.REDSTONE).fuel(100);
	public static final BlockContainer MANGROVE_ROOTS = new BlockContainer(new MangroveRootsBlock(Block.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(0.7f).ticksRandomly().sounds(ModBlockSoundGroups.MANGROVE_ROOTS).nonOpaque().suffocates(ModFactory::never).blockVision(ModFactory::never).nonOpaque()), ItemGroup.BUILDING_BLOCKS).flammable(5, 20).fuel(300).dropSelf();
	public static final BlockContainer MUDDY_MANGROVE_ROOTS = new BlockContainer(new PillarBlock(Block.Settings.of(Material.SOIL, MapColor.SPRUCE_BROWN).strength(0.7f).sounds(ModBlockSoundGroups.MUDDY_MANGROVE_ROOTS)), ItemGroup.BUILDING_BLOCKS).dropSelf();
	public static final SignContainer MANGROVE_SIGN = ModFactory.MakeWoodSign("minecraft:mangrove", ItemGroup.DECORATIONS, MapColor.RED).fuel(200);
	public static final BoatContainer MANGROVE_BOAT = ModFactory.MakeBoat("minecraft:mangrove", MANGROVE_PLANKS, ItemGroup.TRANSPORTATION).fuel(1200);
	public static final PottedBlockContainer MANGROVE_PROPAGULE = new PottedBlockContainer(new PropaguleBlock(Block.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)), ItemSettings(ItemGroup.DECORATIONS));
	//Extended
	public static final BlockContainer MANGROVE_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.RED).flammable(30, 20).fuel(300);
	public static final BlockContainer MANGROVE_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer MANGROVE_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.RED);
	//</editor-fold>
	//<editor-fold desc="Bamboo Wood">
	public static final BlockContainer BAMBOO_BLOCK = ModFactory.MakeBambooBlock(MapColor.DARK_GREEN, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300);
	public static final BlockContainer STRIPPED_BAMBOO_BLOCK = ModFactory.MakeBambooBlock(MapColor.YELLOW, ItemSettings(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300);
	public static final BlockContainer BAMBOO_PLANKS = ModFactory.MakePlanks(MapColor.YELLOW, ModBlockSoundGroups.BAMBOO_WOOD, ItemGroup.BUILDING_BLOCKS).flammable(5, 20).fuel(300);
	public static final BlockContainer BAMBOO_SLAB = ModFactory.MakeSlab(BAMBOO_PLANKS, ItemGroup.BUILDING_BLOCKS).flammable(5, 20).fuel(150);
	public static final BlockContainer BAMBOO_STAIRS = ModFactory.MakeStairs(BAMBOO_PLANKS, ItemGroup.BUILDING_BLOCKS).flammable(5, 20).fuel(300);
	public static final BlockContainer BAMBOO_FENCE = ModFactory.MakeWoodFence(BAMBOO_PLANKS, ItemGroup.DECORATIONS).flammable(5, 20).fuel(300);
	public static final BlockContainer BAMBOO_FENCE_GATE = ModFactory.MakeWoodFenceGate(BAMBOO_PLANKS, ItemGroup.REDSTONE, ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN, ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE).flammable(5, 20).fuel(300);
	public static final BlockContainer BAMBOO_DOOR = ModFactory.MakeWoodDoor(BAMBOO_PLANKS, ItemGroup.REDSTONE, ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_OPEN).fuel(200);
	public static final BlockContainer BAMBOO_TRAPDOOR = ModFactory.MakeWoodTrapdoor(BAMBOO_PLANKS, ItemGroup.REDSTONE, ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_OPEN).fuel(300);
	public static final BlockContainer BAMBOO_PRESSURE_PLATE = ModFactory.MakeWoodPressurePlate(BAMBOO_PLANKS, ItemGroup.REDSTONE, ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON, ModSoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF).fuel(300);
	public static final BlockContainer BAMBOO_BUTTON = ModFactory.MakeWoodButton(ItemGroup.REDSTONE, ModBlockSoundGroups.BAMBOO_WOOD, ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON, ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_OFF).fuel(100);
	public static final SignContainer BAMBOO_SIGN = ModFactory.MakeWoodSign("minecraft:bamboo", ItemGroup.DECORATIONS, MapColor.YELLOW).fuel(200);
	public static final BoatContainer BAMBOO_RAFT = ModFactory.MakeBoat("minecraft:bamboo", BAMBOO_PLANKS, ItemGroup.TRANSPORTATION).fuel(1200);
	//Mosaic
	public static final BlockContainer BAMBOO_MOSAIC = new BlockContainer(new Block(Block.Settings.copy(BAMBOO_PLANKS.asBlock())), ItemGroup.BUILDING_BLOCKS).flammable(5, 20).dropSelf();
	public static final BlockContainer BAMBOO_MOSAIC_SLAB = ModFactory.MakeSlab(BAMBOO_MOSAIC, ItemGroup.BUILDING_BLOCKS).flammable(5, 20).fuel(150);
	public static final BlockContainer BAMBOO_MOSAIC_STAIRS = ModFactory.MakeStairs(BAMBOO_MOSAIC, ItemGroup.BUILDING_BLOCKS).flammable(5, 20).fuel(300);
	//Extended Bamboo
	public static final TorchContainer BAMBOO_TORCH = ModFactory.MakeTorch(BlockSoundGroup.BAMBOO);
	public static final TorchContainer BAMBOO_SOUL_TORCH = ModFactory.MakeSoulTorch(BlockSoundGroup.BAMBOO);
	public static final TorchContainer BAMBOO_ENDER_TORCH = ModFactory.MakeEnderTorch(BlockSoundGroup.BAMBOO);
	public static final TorchContainer UNDERWATER_BAMBOO_TORCH = ModFactory.MakeUnderwaterTorch(BlockSoundGroup.BAMBOO);
	public static final BlockContainer BAMBOO_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.YELLOW, ModBlockSoundGroups.BAMBOO_WOOD).flammable(30, 20).fuel(300);
	public static final BlockContainer BAMBOO_LADDER = ModFactory.MakeLadder(Block.Settings.of(Material.DECORATION).strength(0.4F).sounds(BlockSoundGroup.BAMBOO).nonOpaque());
	public static final BlockContainer BAMBOO_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.YELLOW);
	//</editor-fold>
	//<editor-fold desc="Frogs">
	//TODO: Frogs throw up jazz hands after jumping. They don't croak. What that tongue [not] do.
	public static final BlockContainer OCHRE_FROGLIGHT = ModFactory.MakeFroglight(MapColor.PALE_YELLOW);
	public static final BlockContainer VERDANT_FROGLIGHT = ModFactory.MakeFroglight(MapColor.LICHEN_GREEN);
	public static final BlockContainer PEARLESCENT_FROGLIGHT = ModFactory.MakeFroglight(MapColor.PINK);

	public static final ModSensorType<TemptationsSensor> FROG_TEMPTATIONS_SENSOR = new ModSensorType<>("frog_temptations", () -> new TemptationsSensor(FrogBrain.getTemptItems()));
	public static final ModSensorType<FrogAttackablesSensor> FROG_ATTACKABLES_SENSOR = new ModSensorType<>("frog_attackables", FrogAttackablesSensor::new);
	public static final ModSensorType<IsInWaterSensor> IS_IN_WATER_SENSOR = new ModSensorType<>("is_in_water", IsInWaterSensor::new);
	public static final EntityType<FrogEntity> FROG_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FrogEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeBlocks(10).build();
	public static final Item FROG_SPAWN_EGG = new SpawnEggItem(FROG_ENTITY, 13661252, 0xFFC77C, ItemSettings(ItemGroup.MISC));
	private static final Block FROGSPAWN_BLOCK = new FrogspawnBlock(Block.Settings.of(ModFactory.FROGSPAWN_MATERIAL).breakInstantly().nonOpaque().noCollision().sounds(ModBlockSoundGroups.FROGSPAWN));
	public static final BlockContainer FROGSPAWN = new BlockContainer(FROGSPAWN_BLOCK, new LilyPadItem(FROGSPAWN_BLOCK, ItemSettings(ItemGroup.MISC)));
	public static final EntityType<TadpoleEntity> TADPOLE_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TadpoleEntity::new).dimensions(EntityDimensions.fixed(TadpoleEntity.WIDTH, TadpoleEntity.HEIGHT)).trackRangeBlocks(10).build();
	public static final Item TADPOLE_BUCKET = new EntityBucketItem(TADPOLE_ENTITY, Fluids.WATER, ModSoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, ItemSettings(ItemGroup.MISC).maxCount(1));
	public static final Item TADPOLE_SPAWN_EGG = new SpawnEggItem(TADPOLE_ENTITY, 7164733, 1444352, ItemSettings(ItemGroup.MISC));
	//</editor-fold>
	//TODO: Chiseled Bookshelves (1.20)
	//TODO: Armor Trims (1.20)
	//TODO: Recovery Compass
	public static final WallBlockContainer ACACIA_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.ACACIA, MapColor.STONE_GRAY, ItemGroup.DECORATIONS);
	public static final WallBlockContainer BIRCH_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.BIRCH, MapColor.OFF_WHITE, ItemGroup.DECORATIONS);
	public static final WallBlockContainer DARK_OAK_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.DARK_OAK, MapColor.BROWN, ItemGroup.DECORATIONS);
	public static final WallBlockContainer JUNGLE_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.JUNGLE, MapColor.SPRUCE_BROWN, ItemGroup.DECORATIONS);
	public static final WallBlockContainer OAK_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.OAK, MapColor.SPRUCE_BROWN, ItemGroup.DECORATIONS);
	public static final WallBlockContainer SPRUCE_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.SPRUCE, MapColor.BROWN, ItemGroup.DECORATIONS);
	public static final WallBlockContainer CRIMSON_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.CRIMSON, MapColor.DULL_PINK, ItemGroup.DECORATIONS);
	public static final WallBlockContainer WARPED_HANGING_SIGN = ModFactory.MakeHangingSign(SignType.WARPED, MapColor.DARK_AQUA, ItemGroup.DECORATIONS);

	public static final ModSensorType<TemptationsSensor> CAMEL_TEMPTATIONS_SENSOR = new ModSensorType<>("camel_temptations", () -> new TemptationsSensor(CamelBrain.getBreedingIngredient()));
	public static final EntityType<CamelEntity> CAMEL_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CamelEntity::new).dimensions(EntityDimensions.fixed(1.7f, 2.375f)).trackRangeBlocks(10).build();
	public static final Item CAMEL_SPAWN_EGG = new SpawnEggItem(CAMEL_ENTITY, 16565097, 13341495, ItemSettings(ItemGroup.MISC));


	private static final Block PIGLIN_HEAD_BLOCK = new PiglinHeadBlock(Block.Settings.of(Material.DECORATION).strength(1.0f));
	private static final Block PIGLIN_WALL_HEAD = new WallPiglinHeadBlock(Block.Settings.of(Material.DECORATION).strength(1.0f));
	public static final WallBlockContainer PIGLIN_HEAD = new WallBlockContainer(PIGLIN_HEAD_BLOCK, PIGLIN_WALL_HEAD, new VerticallyAttachableBlockItem(PIGLIN_HEAD_BLOCK, PIGLIN_WALL_HEAD, ItemSettings(ItemGroup.DECORATIONS).rarity(Rarity.UNCOMMON), Direction.DOWN)).dropSelf();
	public static final BlockEntityType<PiglinHeadEntity> PIGLIN_HEAD_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(PiglinHeadEntity::new, PIGLIN_HEAD.asBlock(), PIGLIN_HEAD.getWallBlock()).build();



	public static final EntityType<AllayEntity> ALLAY_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AllayEntity::new).dimensions(EntityDimensions.fixed(0.35f, 0.6f)).trackRangeBlocks(8).trackedUpdateRate(2).build();
	public static final Item ALLAY_SPAWN_EGG = new SpawnEggItem(ALLAY_ENTITY, 56063, 44543, ItemSettings(ItemGroup.MISC));



	//<editor-fold desc="Sculk">
	public static final BlockContainer SCULK_SENSOR = new BlockContainer(new ModSculkSensorBlock(Block.Settings.of(Material.SCULK, MapColor.CYAN).strength(1.5f).sounds(BlockSoundGroup.SCULK_SENSOR).luminance(state -> 1).emissiveLighting((state, world, pos) -> SculkUtils.getPhase(state) == SculkSensorPhase.ACTIVE), 8), ItemGroup.REDSTONE).requireSilkTouch();
	public static final BlockEntityType<ModSculkSensorBlockEntity> SCULK_SENSOR_ENTITY = FabricBlockEntityTypeBuilder.create(ModSculkSensorBlockEntity::new, SCULK_SENSOR.asBlock()).build();
	public static final BlockContainer SCULK = new BlockContainer(new SculkBlock(Block.Settings.of(Material.SCULK).strength(0.2f).sounds(ModBlockSoundGroups.SCULK)), ItemGroup.DECORATIONS).requireSilkTouch();
	public static final BlockContainer SCULK_VEIN = new BlockContainer(new SculkVeinBlock(Block.Settings.of(Material.SCULK).noCollision().strength(0.2f).sounds(ModBlockSoundGroups.SCULK_VEIN)), ItemGroup.DECORATIONS).requireSilkTouch();
	public static final DefaultParticleType SCULK_SOUL_PARTICLE = FabricParticleTypes.simple(false);
	public static final ParticleType<SculkChargeParticleEffect> SCULK_CHARGE_PARTICLE = new ParticleType<>(true, SculkChargeParticleEffect.FACTORY) {
		@Override
		public Codec<SculkChargeParticleEffect> getCodec() { return SculkChargeParticleEffect.CODEC; }
	};
	public static final DefaultParticleType SCULK_CHARGE_POP_PARTICLE = FabricParticleTypes.simple(true);
	public static final ParticleType<ShriekParticleEffect> SHRIEK_PARTICLE = new ParticleType<>(false, ShriekParticleEffect.FACTORY) {
		@Override
		public Codec<ShriekParticleEffect> getCodec() { return ShriekParticleEffect.CODEC; }
	};
	public static final BlockContainer SCULK_CATALYST = new BlockContainer(new SculkCatalystBlock(Block.Settings.of(Material.SCULK).strength(3.0f, 3.0f).sounds(ModBlockSoundGroups.SCULK_CATALYST).luminance(Util.LUMINANCE_6)), ItemGroup.DECORATIONS).requireSilkTouch();
	public static final BlockEntityType<SculkCatalystBlockEntity> SCULK_CATALYST_ENTITY = FabricBlockEntityTypeBuilder.create(SculkCatalystBlockEntity::new, SCULK_CATALYST.asBlock()).build();
	public static final BlockContainer SCULK_SHRIEKER = new BlockContainer(new SculkShriekerBlock(Block.Settings.of(Material.SCULK, MapColor.BLACK).strength(3.0f, 3.0f).sounds(ModBlockSoundGroups.SCULK_SHRIEKER)), ItemGroup.DECORATIONS).requireSilkTouch();
	public static final BlockEntityType<SculkShriekerBlockEntity> SCULK_SHRIEKER_ENTITY = FabricBlockEntityTypeBuilder.create(SculkShriekerBlockEntity::new, SCULK_SHRIEKER.asBlock()).build();
	//Extended
	public static final BlockContainer SCULK_STONE = new BlockContainer(new Block(Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(2.5F, 6.0F))).dropSelf();
	public static final BlockContainer SCULK_STONE_SLAB = ModFactory.MakeSlab(SCULK_STONE);
	public static final BlockContainer SCULK_STONE_STAIRS = ModFactory.MakeStairs(SCULK_STONE);
	public static final BlockContainer SCULK_STONE_WALL = ModFactory.MakeWall(SCULK_STONE);
	public static final BlockContainer SCULK_STONE_BRICKS = new BlockContainer(new Block(Block.Settings.copy(SCULK_STONE.asBlock()))).dropSelf();
	public static final BlockContainer SCULK_STONE_BRICK_SLAB = ModFactory.MakeSlab(SCULK_STONE_BRICKS);
	public static final BlockContainer SCULK_STONE_BRICK_STAIRS = ModFactory.MakeStairs(SCULK_STONE_BRICKS);
	public static final BlockContainer SCULK_STONE_BRICK_WALL = ModFactory.MakeWall(SCULK_STONE_BRICKS);
	//Turf
	public static final BlockContainer CALCITE_SCULK_TURF = new BlockContainer(new SculkTurfBlock(Blocks.CALCITE, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(0.75F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly())).requireSilkTouchOrDrop(Items.CALCITE);
	public static final BlockContainer DEEPSLATE_SCULK_TURF = new BlockContainer(new SculkTurfBlock(Blocks.DEEPSLATE, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(3.0F, 6.0F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly())).requireSilkTouchOrDrop(Items.DEEPSLATE);
	public static final BlockContainer DRIPSTONE_SCULK_TURF = new BlockContainer(new SculkTurfBlock(Blocks.DRIPSTONE_BLOCK, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(1.5F, 1.0F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly())).requireSilkTouchOrDrop(Items.DRIPSTONE_BLOCK);
	public static final BlockContainer SMOOTH_BASALT_SCULK_TURF = new BlockContainer(new SculkTurfBlock(Blocks.SMOOTH_BASALT, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(1.25F, 4.2F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly())).requireSilkTouchOrDrop(Items.SMOOTH_BASALT);
	public static final BlockContainer TUFF_SCULK_TURF = new BlockContainer(new SculkTurfBlock(Blocks.TUFF, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(1.5F, 6.0F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly())).requireSilkTouchOrDrop(Items.TUFF);
	//</editor-fold>
	public static final BlockContainer REINFORCED_DEEPSLATE = new BlockContainer(new Block(Block.Settings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).sounds(BlockSoundGroup.DEEPSLATE).strength(55.0f, 1200.0f))).drops(DropTable.NOTHING);
	//<editor-fold desc="Warden">
	public static final DefaultParticleType SONIC_BOOM_PARTICLE = FabricParticleTypes.simple(true);
	public static final ModSensorType<WardenAttackablesSensor> WARDEN_ENTITY_SENSOR = new ModSensorType<>("minecraft:warden_entity_sensor", WardenAttackablesSensor::new);
	public static final EntityType<WardenEntity> WARDEN_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WardenEntity::new).dimensions(EntityDimensions.fixed(0.9f, 2.9f)).trackRangeBlocks(16).fireImmune().build();
	public static final Item WARDEN_SPAWN_EGG = new SpawnEggItem(WARDEN_ENTITY, 1001033, 3790560, ItemSettings(ItemGroup.MISC));
	public static final GameRules.Key<GameRules.BooleanRule> DO_WARDEN_SPAWNING = GameRulesAccessor.callRegister("doWardenSpawning", GameRules.Category.SPAWNING, GameRuleFactory.createBooleanRule(true));
	//</editor-fold>
	//<editor-fold desc="Echo">
	public static final Item ECHO_SHARD = new Item(ItemSettings(ItemGroup.MISC));
	//Extended
	public static final BlockContainer ECHO_BLOCK = new BlockContainer(new EchoBlock(Block.Settings.of(Material.SCULK).strength(1.5F).sounds(ModBlockSoundGroups.ECHO_BLOCK).requiresTool())).dropSelf();
	public static final BlockContainer ECHO_SLAB = ModFactory.MakeSlab(ECHO_BLOCK);
	public static final BlockContainer ECHO_STAIRS = ModFactory.MakeStairs(ECHO_BLOCK);
	public static final BlockContainer ECHO_WALL = ModFactory.MakeWall(ECHO_BLOCK);
	public static final BlockContainer ECHO_CRYSTAL_BLOCK = new BlockContainer(new Block(FabricBlockSettings.of(Material.SCULK).sounds(ModBlockSoundGroups.ECHO_CLUSTER).strength(1.5f, 1.5f).requiresTool().luminance(Util.LUMINANCE_5))).dropSelf();
	public static final BlockContainer ECHO_CRYSTAL_SLAB = ModFactory.MakeSlab(ECHO_CRYSTAL_BLOCK);
	public static final BlockContainer ECHO_CRYSTAL_STAIRS = ModFactory.MakeStairs(ECHO_CRYSTAL_BLOCK);
	public static final BlockContainer ECHO_CRYSTAL_WALL = ModFactory.MakeWall(ECHO_CRYSTAL_BLOCK);
	public static final BlockContainer BUDDING_ECHO = new BlockContainer(new BuddingEchoBlock(Block.Settings.copy(ECHO_BLOCK.asBlock()).ticksRandomly())).drops(DropTable.NOTHING);
	public static final BlockContainer ECHO_CLUSTER = new BlockContainer(new EchoClusterBlock(7, 3, Block.Settings.of(Material.SCULK).nonOpaque().ticksRandomly().sounds(ModBlockSoundGroups.ECHO_CLUSTER).strength(1.5F).luminance(Util.LUMINANCE_3)));
	public static final BlockContainer LARGE_ECHO_BUD = new BlockContainer(new EchoClusterBlock(5, 3, Block.Settings.copy(ECHO_CLUSTER.asBlock()).sounds(ModBlockSoundGroups.MEDIUM_ECHO_BUD).luminance(Util.LUMINANCE_2))).requireSilkTouch();
	public static final BlockContainer MEDIUM_ECHO_BUD = new BlockContainer(new EchoClusterBlock(4, 3, Block.Settings.copy(ECHO_CLUSTER.asBlock()).sounds(ModBlockSoundGroups.LARGE_ECHO_BUD).luminance(Util.LUMINANCE_1))).requireSilkTouch();
	public static final BlockContainer SMALL_ECHO_BUD = new BlockContainer(new EchoClusterBlock(3, 4, Block.Settings.copy(ECHO_CLUSTER.asBlock()).sounds(ModBlockSoundGroups.SMALL_ECHO_BUD))).requireSilkTouch();
	public static final Item ECHO_AXE = new EchoAxeItem(ModToolMaterials.ECHO, 5, -3, 1);
	public static final Item ECHO_HOE = new EchoHoeItem(ModToolMaterials.ECHO, -4, 0, 1);
	public static final Item ECHO_PICKAXE = new EchoPickaxeItem(ModToolMaterials.ECHO, 1, -2.8F, 1);
	public static final Item ECHO_SHOVEL = new EchoShovelItem(ModToolMaterials.ECHO, 1.5F, -3, 1);
	public static final Item ECHO_SWORD = new EchoSwordItem(ModToolMaterials.ECHO, 3, -2.4F, 1);
	public static final Item ECHO_KNIFE = new EchoKnifeItem(ModToolMaterials.ECHO, 1);
	//</editor-fold>
	//<editor-fold desc="Backport 1.19">
	public static final StatusEffect DARKNESS_EFFECT = new DarknessEffect();
	public static final Enchantment SWIFT_SNEAK_ENCHANTMENT = new SwiftSneakEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.LEGS);
	public static final Item RECOVERY_COMPASS = new RecoveryCompassItem(ItemSettings(ItemGroup.TOOLS));
	//</editor-fold>
	//<editor-fold desc="Effects">
	public static final StatusEffect FLASHBANGED_EFFECT = new FlashbangedEffect();
	public static final StatusEffect BLEEDING_EFFECT = new BleedingEffect();
	public static final StatusEffect TINTED_GOGGLES_EFFECT = new TintedGogglesEffect();
	//</editor-fold>
	//<editor-fold desc="Extended Bone">
	public static final TorchContainer BONE_TORCH = ModFactory.MakeTorch(BlockSoundGroup.BONE);
	public static final TorchContainer BONE_SOUL_TORCH = ModFactory.MakeSoulTorch(BlockSoundGroup.BONE);
	public static final TorchContainer BONE_ENDER_TORCH = ModFactory.MakeEnderTorch(BlockSoundGroup.BONE);
	public static final TorchContainer UNDERWATER_BONE_TORCH = ModFactory.MakeUnderwaterTorch(BlockSoundGroup.BONE);
	public static final BlockContainer BONE_LADDER = ModFactory.MakeLadder(Block.Settings.of(Material.DECORATION).strength(0.4F).sounds(BlockSoundGroup.BONE).nonOpaque());
	//</editor-fold>
	//<editor-fold desc="Extended Blackstone">
	public static final BlockContainer GILDED_BLACKSTONE_SLAB = ModFactory.MakeSlab(Blocks.GILDED_BLACKSTONE);
	public static final BlockContainer GILDED_BLACKSTONE_STAIRS = ModFactory.MakeStairs(Blocks.GILDED_BLACKSTONE);
	public static final BlockContainer GILDED_BLACKSTONE_WALL = ModFactory.MakeWall(Blocks.GILDED_BLACKSTONE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE = new BlockContainer(new Block(Block.Settings.copy(Blocks.GILDED_BLACKSTONE))).dropSelf();
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_SLAB = ModFactory.MakeSlab(POLISHED_GILDED_BLACKSTONE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_STAIRS = ModFactory.MakeStairs(POLISHED_GILDED_BLACKSTONE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_WALL = ModFactory.MakeWall(POLISHED_GILDED_BLACKSTONE);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_BRICKS = new BlockContainer(new Block(Block.Settings.copy(Blocks.GILDED_BLACKSTONE))).dropSelf();
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_BRICK_SLAB = ModFactory.MakeSlab(POLISHED_GILDED_BLACKSTONE_BRICKS);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS = ModFactory.MakeStairs(POLISHED_GILDED_BLACKSTONE_BRICKS);
	public static final BlockContainer POLISHED_GILDED_BLACKSTONE_BRICK_WALL = ModFactory.MakeWall(POLISHED_GILDED_BLACKSTONE_BRICKS);
	public static final BlockContainer CHISELED_POLISHED_GILDED_BLACKSTONE = new BlockContainer(new Block(Block.Settings.copy(Blocks.GILDED_BLACKSTONE)));
	public static final BlockContainer CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS = new BlockContainer(new Block(Block.Settings.copy(Blocks.GILDED_BLACKSTONE)));
	//</editor-fold>
	//<editor-fold desc="Misc Stuff">
	public static final BlockContainer COAL_SLAB = ModFactory.MakeSlab(Blocks.COAL_BLOCK).flammable(5, 20).fuel(8000).dropSlabs();
	public static final BlockContainer CHARCOAL_BLOCK = new BlockContainer(new Block(Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(5.0F, 6.0F))).flammable(5, 5).fuel(16000).dropSelf();
	public static final BlockContainer CHARCOAL_SLAB = ModFactory.MakeSlab(CHARCOAL_BLOCK).flammable(5, 20).fuel(8000).dropSlabs();
	public static final BlockContainer BLUE_SHROOMLIGHT = new BlockContainer(new Block(Block.Settings.copy(Blocks.SHROOMLIGHT).mapColor(MapColor.CYAN).strength(1.0f))).dropSelf();
	public static final BlockContainer HEDGE_BLOCK = new BlockContainer(new Block(Block.Settings.of(Material.LEAVES).strength(0.2F).sounds(BlockSoundGroup.GRASS).nonOpaque())).flammable(5, 20).dropSelf();
	public static final BlockContainer COCOA_BLOCK = new BlockContainer(new Block(Block.Settings.of(Material.SOLID_ORGANIC, MapColor.BROWN).strength(0.8F).sounds(BlockSoundGroup.WOOD))).compostable(0.65f).dropSelf();
	public static final BlockContainer SEED_BLOCK = new BlockContainer(new Block(Block.Settings.of(Material.SOLID_ORGANIC, MapColor.DARK_GREEN).strength(0.8F).sounds(BlockSoundGroup.GRASS))).compostable(1f).dropSelf();
	public static final BlockContainer WAX_BLOCK = new BlockContainer(new Block(Block.Settings.copy(Blocks.HONEYCOMB_BLOCK))).dropSelf();
	public static final Item LAVA_BOTTLE = new LavaBottleItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
	//</editor-fold>
	//<editor-fold desc="Woodcutting">
	public static final RecipeType<WoodcuttingRecipe> WOODCUTTING_RECIPE_TYPE = RegisterRecipeType("woodcutting");
	public static final RecipeSerializer<WoodcuttingRecipe> WOODCUTTING_RECIPE_SERIALIZER = new WoodcuttingRecipeSerializer<>(WoodcuttingRecipe::new);
	public static final BlockContainer WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.OAK_TAN);
	//</editor-fold>
	//<editor-fold desc="Extended Wood">
	public static final BlockContainer ACACIA_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.ORANGE).flammable(30, 20).fuel(300);
	public static final BlockContainer ACACIA_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer ACACIA_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.ORANGE);
	public static final BlockContainer BIRCH_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.PALE_YELLOW).flammable(30, 20).fuel(300);
	public static final BlockContainer BIRCH_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer BIRCH_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.PALE_YELLOW);
	public static final BlockContainer DARK_OAK_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.BROWN).flammable(30, 20).fuel(300);
	public static final BlockContainer DARK_OAK_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer DARK_OAK_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.BROWN);
	public static final BlockContainer JUNGLE_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.DIRT_BROWN).flammable(30, 20).fuel(300);
	public static final BlockContainer JUNGLE_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer JUNGLE_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.DIRT_BROWN);
	public static final BlockContainer SPRUCE_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.SPRUCE_BROWN).flammable(30, 20).fuel(300);
	public static final BlockContainer SPRUCE_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer SPRUCE_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.SPRUCE_BROWN);
	public static final BlockContainer CRIMSON_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.DULL_PINK, ModBlockSoundGroups.NETHER_WOOD).flammable(30, 20).fuel(300);
	public static final BlockContainer CRIMSON_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer CRIMSON_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.DULL_PINK);
	public static final BlockContainer WARPED_BOOKSHELF = ModFactory.MakeBookshelf(MapColor.DARK_AQUA, ModBlockSoundGroups.NETHER_WOOD).flammable(30, 20).fuel(300);
	public static final BlockContainer WARPED_LADDER = ModFactory.MakeLadder();
	public static final BlockContainer WARPED_WOODCUTTER = ModFactory.MakeWoodcutter(MapColor.DARK_AQUA);
	//</editor-fold>
	//<editor-fold desc="Flowers">
	//Minecraft Earth Flowers
	public static final FlowerContainer BUTTERCUP = ModFactory.MakeFlower(StatusEffects.BLINDNESS, 11);
	public static final FlowerContainer PINK_DAISY = ModFactory.MakeFlower(StatusEffects.REGENERATION, 8);
	//Other Flowers
	public static final FlowerContainer ROSE = new FlowerContainer(new GrowableFlowerBlock(StatusEffects.WEAKNESS, 9, FlowerContainer.Settings(), () -> (TallFlowerBlock)Blocks.ROSE_BUSH));
	private static TallFlowerBlock getBlueRoseBush() { return (TallFlowerBlock) BLUE_ROSE_BUSH.asBlock(); }
	public static final FlowerContainer BLUE_ROSE = new FlowerContainer(new GrowableFlowerBlock(StatusEffects.WEAKNESS, 9, FlowerContainer.Settings(), ModBase::getBlueRoseBush));
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
	public static final FlowerContainer PINK_ALLIUM = new FlowerContainer(new GrowableFlowerBlock(StatusEffects.FIRE_RESISTANCE, 4, FlowerContainer.Settings(), ModBase::getTallPinkAlliumBlock));
	public static final FlowerContainer LAVENDER = ModFactory.MakeFlower(StatusEffects.INVISIBILITY, 8);
	public static final FlowerContainer HYDRANGEA = ModFactory.MakeFlower(StatusEffects.JUMP_BOOST, 7);
	public static final FlowerContainer PAEONIA = ModFactory.MakeFlower(StatusEffects.STRENGTH, 6);
	public static final FlowerContainer ASTER = ModFactory.MakeFlower(StatusEffects.INSTANT_DAMAGE, 1);
	public static final TallBlockContainer AMARANTH = ModFactory.MakeTallFlower();
	public static final TallBlockContainer BLUE_ROSE_BUSH = ModFactory.MakeCuttableFlower(() -> (FlowerBlock)BLUE_ROSE.asBlock());
	public static final TallBlockContainer TALL_ALLIUM = ModFactory.MakeCuttableFlower(() -> (FlowerBlock)Blocks.ALLIUM);
	public static final TallBlockContainer TALL_PINK_ALLIUM = ModFactory.MakeCuttableFlower(() -> (FlowerBlock) PINK_ALLIUM.asBlock());
	//</editor-fold>
	//<editor-fold desc="Biomes">
	public static final Feature<DefaultFeatureConfig> DISK_GRASS_FEATURE = new DiskGrassFeature(DefaultFeatureConfig.CODEC);
	public static final ModConfiguredFeature<DefaultFeatureConfig, ?> DISK_GRASS_CONFIGURED = new ModConfiguredFeature<>("disk_grass", new ConfiguredFeature<>(DISK_GRASS_FEATURE, FeatureConfig.DEFAULT));
	public static final ModPlacedFeature DISK_GRASS_PLACED = new ModPlacedFeature("disk_grass", DISK_GRASS_CONFIGURED) {
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
	public static final ModConfiguredFeature<MangroveTreeFeatureConfig, ?> MANGROVE_CONFIGURED = new ModConfiguredFeature<>("mangrove", new ConfiguredFeature<>(MANGROVE_TREE_FEATURE, new MangroveTreeFeatureConfig(false)));
	public static final ModPlacedFeature MANGROVE_CHECKED_PLACED = new ModPlacedFeature("mangrove_checked", MANGROVE_CONFIGURED) {
		@Override public List<PlacementModifier> getModifiers() { return List.of(PlacedFeatures.wouldSurvive(ModBase.MANGROVE_PROPAGULE.asBlock())); }
	};
	public static final ModConfiguredFeature<MangroveTreeFeatureConfig, ?> TALL_MANGROVE_CONFIGURED = new ModConfiguredFeature<>("tall_mangrove", new ConfiguredFeature<>(MANGROVE_TREE_FEATURE, new MangroveTreeFeatureConfig(true)));
	public static final ModPlacedFeature TALL_MANGROVE_CHECKED_PLACED = new ModPlacedFeature("tall_mangrove_checked", TALL_MANGROVE_CONFIGURED) {
		@Override public List<PlacementModifier> getModifiers() { return List.of(PlacedFeatures.wouldSurvive(ModBase.MANGROVE_PROPAGULE.asBlock())); }
	};
	public static final ModConfiguredFeature<RandomFeatureConfig, ?> MANGROVE_VEGETATION_CONFIGURED = new ModConfiguredFeature<>("mangrove_vegetation", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(TALL_MANGROVE_CHECKED_PLACED.getRegistryEntry(), 0.85f)), MANGROVE_CHECKED_PLACED.getRegistryEntry())));
	public static final ModPlacedFeature TREES_MANGROVE_PLACED = new ModPlacedFeature("trees_mangrove", MANGROVE_VEGETATION_CONFIGURED) {
		@Override
		public List<PlacementModifier> getModifiers() {
			return List.of(
					CountPlacementModifier.of(25),
					SquarePlacementModifier.of(),
					SurfaceWaterDepthFilterPlacementModifier.of(5),
					PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of(),
					BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(ModBase.MANGROVE_PROPAGULE.asBlock().getDefaultState(), BlockPos.ORIGIN))
			);
		}
	};

	public static final Feature<SculkPatchFeatureConfig> SCULK_PATCH_FEATURE = new SculkPatchFeature(SculkPatchFeatureConfig.CODEC);
	public static final ModConfiguredFeature<SculkPatchFeatureConfig, ?> SCULK_PATCH_DEEP_DARK_CONFIGURED = new ModConfiguredFeature<>("sculk_patch_deep_dark", new ConfiguredFeature<>(SCULK_PATCH_FEATURE, new SculkPatchFeatureConfig(10, 32, 64, 0, 1, ConstantIntProvider.create(0), 0.5f)));
	public static final ModPlacedFeature SCULK_PATCH_DEEP_DARK_PLACED = new ModPlacedFeature("sculk_patch_deep_dark", SCULK_PATCH_DEEP_DARK_CONFIGURED) {
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
	public static final ModConfiguredFeature<SculkPatchFeatureConfig, ?> SCULK_PATCH_ANCIENT_CITY_CONFIGURED = new ModConfiguredFeature<>("sculk_patch_ancient_city", new ConfiguredFeature<>(SCULK_PATCH_FEATURE, new SculkPatchFeatureConfig(10, 32, 64, 0, 1, UniformIntProvider.create(1, 3), 0.5F)));
	public static final ModPlacedFeature SCULK_PATCH_ANCIENT_CITY_PLACED = new ModPlacedFeature("sculk_patch_ancient_city", SCULK_PATCH_ANCIENT_CITY_CONFIGURED) { @Override public List<PlacementModifier> getModifiers() { return List.of(); } };
	public static final Feature<DefaultFeatureConfig> SCULK_VEIN_FEATURE = new SculkVeinFeature(DefaultFeatureConfig.CODEC);
	public static final ModConfiguredFeature<DefaultFeatureConfig, ?> SCULK_VEIN_CONFIGURED = new ModConfiguredFeature<>("sculk_vein", new ConfiguredFeature<>(SCULK_VEIN_FEATURE, FeatureConfig.DEFAULT));
	public static final ModPlacedFeature SCULK_VEIN_PLACED = new ModPlacedFeature("sculk_vein", SCULK_VEIN_CONFIGURED) {
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

	public static final ModStructureProcessorList ANCIENT_CITY_START_DEGRADATION = new ModStructureProcessorList("ancient_city_start_degradation", new StructureProcessorList(ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(
			new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_BRICKS, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState()),
			new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_TILES, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.getDefaultState()),
			new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.SOUL_LANTERN, 0.05f), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()))),
			new ProtectedBlocksStructureProcessor(BlockTags.FEATURES_CANNOT_REPLACE))));
	public static final ModStructureProcessorList ANCIENT_CITY_GENERIC_DEGRADATION = new ModStructureProcessorList("ancient_city_generic_degradation", new StructureProcessorList(ImmutableList.of(
			new ModBlockRotStructureProcessor(ModBlockTags.ANCIENT_CITY_REPLACEABLE, 0.95f),
			new RuleStructureProcessor(ImmutableList.of(
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_BRICKS, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_TILES, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.SOUL_LANTERN, 0.05f), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()))),
			new ProtectedBlocksStructureProcessor(BlockTags.FEATURES_CANNOT_REPLACE))));
	public static final ModStructureProcessorList ANCIENT_CITY_WALLS_DEGRADATION = new ModStructureProcessorList("ancient_city_walls_degradation", new StructureProcessorList(ImmutableList.of(
			new ModBlockRotStructureProcessor(ModBlockTags.ANCIENT_CITY_REPLACEABLE, 0.95f),
			new RuleStructureProcessor(ImmutableList.of(
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_BRICKS, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_TILES, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.DEEPSLATE_TILE_SLAB, 0.3f), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()),
					new StructureProcessorRule(new RandomBlockMatchRuleTest(Blocks.SOUL_LANTERN, 0.05f), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()))),
			new ProtectedBlocksStructureProcessor(BlockTags.FEATURES_CANNOT_REPLACE))));

	public static ModStructurePool CITY_CENTER = new ModStructurePool("ancient_city/city_center", ImmutableList.of(
			new ModPoolPair("ancient_city/city_center/city_center_1", ANCIENT_CITY_START_DEGRADATION, 1),
			new ModPoolPair("ancient_city/city_center/city_center_2", ANCIENT_CITY_START_DEGRADATION, 1),
			new ModPoolPair("ancient_city/city_center/city_center_3", ANCIENT_CITY_START_DEGRADATION, 1)
	));
	public static final ModStructureFeature ANCIENT_CITY_POOL = new ModStructureFeature("ancient_city", new JigsawFeature(StructurePoolFeatureConfig.CODEC, -51, true, false, context -> true));
	public static final RegistryKey<ConfiguredStructureFeature<?, ?>> ANCIENT_CITY_CONFIGURED_KEY = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, ID("ancient_city"));
	public static final ModConfiguredStructure ANCIENT_CITY_CONFIGURED = new ModConfiguredStructure(ANCIENT_CITY_CONFIGURED_KEY, ANCIENT_CITY_POOL, new ModStructurePoolConfig(CITY_CENTER, 7), ModBiomeTags.ANCIENT_CITY_HAS_STRUCTURE);
	public static final RegistryKey<StructureSet> ANCIENT_CITIES_KEY = RegistryKey.of(Registry.STRUCTURE_SET_KEY, ID("ancient_cities"));
	public static final ModStructureSet ANCIENT_CITIES = new ModStructureSet(ANCIENT_CITIES_KEY, ANCIENT_CITY_CONFIGURED, new RandomSpreadStructurePlacement(24, 8, SpreadType.LINEAR, 20083232));

	public static final RegistryKey<Biome> MANGROVE_SWAMP = RegistryKey.of(Registry.BIOME_KEY, ID("mangrove_swamp"));
	public static final RegistryKey<Biome> DEEP_DARK = RegistryKey.of(Registry.BIOME_KEY, ID("deep_dark"));
	public static final Set<RegistryKey<Biome>> CAVE_BIOMES = Set.of(BiomeKeys.LUSH_CAVES, BiomeKeys.DRIPSTONE_CAVES, DEEP_DARK);
	//</editor-fold>

	//<editor-fold desc="Origins Powers">
	public static final PowerType<?> DRAGON_WONT_HARM_POWER = new PowerTypeReference<>(ID("dragon_wont_harm"));
	public static final PowerType<?> DRAGON_WONT_LAUNCH_POWER = new PowerTypeReference<>(ID("dragon_wont_launch"));
	public static final PowerType<?> CHORUS_IMMUNE_POWER = new PowerTypeReference<>(ID("chorus_immune"));
	public static final PowerType<?> IDENTIFIED_SOUNDS_POWER = new PowerTypeReference<>(ID("identified_sounds"));
	public static final PowerType<?> TAKE_HONEY_POWER = new PowerTypeReference<>(ID("take_honey"));
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
		//<editor-fold desc="Boats">
		Register("mod_boat", MOD_BOAT_ENTITY);
		Register("minecraft:chest_boat", CHEST_BOAT_ENTITY);
		Register("mod_chest_boat", MOD_CHEST_BOAT_ENTITY);
		Register("minecraft:oak_chest_boat", OAK_CHEST_BOAT);
		Register("minecraft:spruce_chest_boat", SPRUCE_CHEST_BOAT);
		Register("minecraft:birch_chest_boat", BIRCH_CHEST_BOAT);
		Register("minecraft:jungle_chest_boat", JUNGLE_CHEST_BOAT);
		Register("minecraft:acacia_chest_boat", ACACIA_CHEST_BOAT);
		Register("minecraft:dark_oak_chest_boat", DARK_OAK_CHEST_BOAT);
		//</editor-fold>
		Register("woodcutting", WOODCUTTING_RECIPE_SERIALIZER);
		//<editor-fold desc="Extended Wood">
		Register("acacia_bookshelf", ACACIA_BOOKSHELF);
		Register("acacia_ladder", ACACIA_LADDER);
		Register("acacia_woodcutter", ACACIA_WOODCUTTER);
		Register("minecraft:acacia_hanging_sign", "minecraft:acacia_wall_hanging_sign", ACACIA_HANGING_SIGN);
		Register("birch_bookshelf", BIRCH_BOOKSHELF);
		Register("birch_ladder", BIRCH_LADDER);
		Register("birch_woodcutter", BIRCH_WOODCUTTER);
		Register("minecraft:birch_hanging_sign", "minecraft:birch_wall_hanging_sign", BIRCH_HANGING_SIGN);
		Register("dark_oak_bookshelf", DARK_OAK_BOOKSHELF);
		Register("dark_oak_ladder", DARK_OAK_LADDER);
		Register("dark_oak_woodcutter", DARK_OAK_WOODCUTTER);
		Register("minecraft:dark_oak_hanging_sign", "minecraft:dark_oak_wall_hanging_sign", DARK_OAK_HANGING_SIGN);
		Register("jungle_bookshelf", JUNGLE_BOOKSHELF);
		Register("jungle_ladder", JUNGLE_LADDER);
		Register("jungle_woodcutter", JUNGLE_WOODCUTTER);
		Register("minecraft:jungle_hanging_sign", "minecraft:jungle_wall_hanging_sign", JUNGLE_HANGING_SIGN);
		Register("woodcutter", WOODCUTTER);
		Register("minecraft:oak_hanging_sign", "minecraft:oak_wall_hanging_sign", OAK_HANGING_SIGN);
		Register("spruce_bookshelf", SPRUCE_BOOKSHELF);
		Register("spruce_ladder", SPRUCE_LADDER);
		Register("spruce_woodcutter", SPRUCE_WOODCUTTER);
		Register("minecraft:spruce_hanging_sign", "minecraft:spruce_wall_hanging_sign", SPRUCE_HANGING_SIGN);
		Register("crimson_bookshelf", CRIMSON_BOOKSHELF);
		Register("crimson_ladder", CRIMSON_LADDER);
		Register("crimson_woodcutter", CRIMSON_WOODCUTTER);
		Register("minecraft:crimson_hanging_sign", "minecraft:crimson_wall_hanging_sign", CRIMSON_HANGING_SIGN);
		Register("warped_bookshelf", WARPED_BOOKSHELF);
		Register("warped_ladder", WARPED_LADDER);
		Register("warped_woodcutter", WARPED_WOODCUTTER);
		Register("minecraft:warped_hanging_sign", "minecraft:warped_wall_hanging_sign", WARPED_HANGING_SIGN);
		//</editor-fold>
		//<editor-fold desc="Light Sources">
		Register("glow_flame", UNDERWATER_TORCH_GLOW);
		Register("underwater_torch", "underwater_wall_torch", UNDERWATER_TORCH);
		Register("unlit_torch", "unlit_wall_torch", UNLIT_TORCH);
		Register("unlit_soul_torch", "unlit_wall_soul_torch", UNLIT_SOUL_TORCH);
		Register("unlit_lantern", UNLIT_LANTERN);
		Register("unlit_soul_lantern", UNLIT_SOUL_LANTERN);
		//</editor-fold>
		//<editor-fold desc="Soul & Ender Fire">
		Register("small_soul_flame", SMALL_SOUL_FLAME_PARTICLE);
		Register("ender_fire_flame", ENDER_FIRE_FLAME_PARTICLE);
		Register("small_ender_flame", SMALL_ENDER_FLAME_PARTICLE);
		Register("soul_candle", SOUL_CANDLE);
		Register("ender_candle", ENDER_CANDLE);
		Register("soul_candle_cake", SOUL_CANDLE_CAKE);
		Register("ender_candle_cake", ENDER_CANDLE_CAKE);
		Register("ender_torch", "ender_wall_torch", ENDER_TORCH);
		Register("ender_lantern", ENDER_LANTERN);
		Register("ender_campfire", ENDER_CAMPFIRE);
		//</editor-fold>
		//<editor-fold desc="Extended Stone">
		Register("polished_andesite_wall", POLISHED_ANDESITE_WALL);
		Register("polished_diorite_wall", POLISHED_DIORITE_WALL);
		Register("polished_granite_wall", POLISHED_GRANITE_WALL);
		Register("smooth_sandstone_wall", SMOOTH_SANDSTONE_WALL);
		Register("smooth_red_sandstone_wall", SMOOTH_RED_SANDSTONE_WALL);
		Register("dark_prismarine_wall", DARK_PRISMARINE_WALL);
		Register("purpur_wall", PURPUR_WALL);
		//</editor-fold>
		//<editor-fold desc="Extended Calcite">
		Register("calcite_stairs", CALCITE_STAIRS);
		Register("calcite_slab", CALCITE_SLAB);
		Register("calcite_wall", CALCITE_WALL);
		Register("smooth_calcite", SMOOTH_CALCITE);
		Register("smooth_calcite_stairs", SMOOTH_CALCITE_STAIRS);
		Register("smooth_calcite_slab", SMOOTH_CALCITE_SLAB);
		Register("smooth_calcite_wall", SMOOTH_CALCITE_WALL);
		Register("calcite_bricks", CALCITE_BRICKS);
		Register("calcite_brick_stairs", CALCITE_BRICK_STAIRS);
		Register("calcite_brick_slab", CALCITE_BRICK_SLAB);
		Register("calcite_brick_wall", CALCITE_BRICK_WALL);
		//</editor-fold>
		//<editor-fold desc="Extended Dripstone">
		Register("dripstone_stairs", DRIPSTONE_STAIRS);
		Register("dripstone_slab", DRIPSTONE_SLAB);
		Register("dripstone_wall", DRIPSTONE_WALL);
		Register("smooth_dripstone", SMOOTH_DRIPSTONE);
		Register("smooth_dripstone_stairs", SMOOTH_DRIPSTONE_STAIRS);
		Register("smooth_dripstone_slab", SMOOTH_DRIPSTONE_SLAB);
		Register("smooth_dripstone_wall", SMOOTH_DRIPSTONE_WALL);
		Register("dripstone_bricks", DRIPSTONE_BRICKS);
		Register("dripstone_brick_stairs", DRIPSTONE_BRICK_STAIRS);
		Register("dripstone_brick_slab", DRIPSTONE_BRICK_SLAB);
		Register("dripstone_brick_wall", DRIPSTONE_BRICK_WALL);
		//</editor-fold>
		//<editor-fold desc="Extended Tuff">
		Register("tuff_stairs", TUFF_STAIRS);
		Register("tuff_slab", TUFF_SLAB);
		Register("tuff_wall", TUFF_WALL);
		Register("smooth_tuff", SMOOTH_TUFF);
		Register("smooth_tuff_stairs", SMOOTH_TUFF_STAIRS);
		Register("smooth_tuff_slab", SMOOTH_TUFF_SLAB);
		Register("smooth_tuff_wall", SMOOTH_TUFF_WALL);
		Register("tuff_bricks", TUFF_BRICKS);
		Register("tuff_brick_stairs", TUFF_BRICK_STAIRS);
		Register("tuff_brick_slab", TUFF_BRICK_SLAB);
		Register("tuff_brick_wall", TUFF_BRICK_WALL);
		//</editor-fold>
		//<editor-fold desc="Extended Amethyst">
		Register("amethyst_stairs", AMETHYST_STAIRS);
		Register("amethyst_slab", AMETHYST_SLAB);
		Register("amethyst_wall", AMETHYST_WALL);
		Register("amethyst_crystal_block", AMETHYST_CRYSTAL_BLOCK);
		Register("amethyst_crystal_stairs", AMETHYST_CRYSTAL_STAIRS);
		Register("amethyst_crystal_slab", AMETHYST_CRYSTAL_SLAB);
		Register("amethyst_crystal_wall", AMETHYST_CRYSTAL_WALL);
		Register("amethyst_bricks", AMETHYST_BRICKS);
		Register("amethyst_brick_stairs", AMETHYST_BRICK_STAIRS);
		Register("amethyst_brick_slab", AMETHYST_BRICK_SLAB);
		Register("amethyst_brick_wall", AMETHYST_BRICK_WALL);
		Register("amethyst_axe", AMETHYST_AXE);
		Register("amethyst_hoe", AMETHYST_HOE);
		Register("amethyst_pickaxe", AMETHYST_PICKAXE);
		Register("amethyst_shovel", AMETHYST_SHOVEL);
		Register("amethyst_sword", AMETHYST_SWORD);
		Register("amethyst_knife", AMETHYST_KNIFE);
		Register("amethyst_helmet", AMETHYST_HELMET);
		Register("amethyst_chestplate", AMETHYST_CHESTPLATE);
		Register("amethyst_leggings", AMETHYST_LEGGINGS);
		Register("amethyst_boots", AMETHYST_BOOTS);
		Register("amethyst_horse_armor", AMETHYST_HORSE_ARMOR);
		//</editor-fold>
		//<editor-fold desc="More Glass">
		Register("tinted_glass_pane", TINTED_GLASS_PANE);
		Register("tinted_glass_slab", TINTED_GLASS_SLAB);
		Register("tinted_goggles", TINTED_GOGGLES);
		Register("glass_slab", GLASS_SLAB);
		for (DyeColor color : COLORS) Register(color.getName() + "_stained_glass_slab", STAINED_GLASS_SLABS.get(color));
		//</editor-fold>
		//<editor-fold desc="Extended Emerald">
		Register("emerald_bricks", EMERALD_BRICKS);
		Register("emerald_brick_stairs", EMERALD_BRICK_STAIRS);
		Register("emerald_brick_slab", EMERALD_BRICK_SLAB);
		Register("emerald_brick_wall", EMERALD_BRICK_WALL);
		Register("cut_emerald", CUT_EMERALD);
		Register("cut_emerald_stairs", CUT_EMERALD_STAIRS);
		Register("cut_emerald_slab", CUT_EMERALD_SLAB);
		Register("cut_emerald_wall", CUT_EMERALD_WALL);
		Register("emerald_axe", EMERALD_AXE);
		Register("emerald_hoe", EMERALD_HOE);
		Register("emerald_pickaxe", EMERALD_PICKAXE);
		Register("emerald_shovel", EMERALD_SHOVEL);
		Register("emerald_sword", EMERALD_SWORD);
		Register("emerald_knife", EMERALD_KNIFE);
		Register("emerald_helmet", EMERALD_HELMET);
		Register("emerald_chestplate", EMERALD_CHESTPLATE);
		Register("emerald_leggings", EMERALD_LEGGINGS);
		Register("emerald_boots", EMERALD_BOOTS);
		Register("emerald_horse_armor", EMERALD_HORSE_ARMOR);
		//</editor-fold>
		//<editor-fold desc="Extended Diamond">
		Register("diamond_stairs", DIAMOND_STAIRS);
		Register("diamond_slab", DIAMOND_SLAB);
		Register("diamond_wall", DIAMOND_WALL);
		Register("diamond_bricks", DIAMOND_BRICKS);
		Register("diamond_brick_stairs", DIAMOND_BRICK_STAIRS);
		Register("diamond_brick_slab", DIAMOND_BRICK_SLAB);
		Register("diamond_brick_wall", DIAMOND_BRICK_WALL);
		//</editor-fold>
		//<editor-fold desc="Quartz">
		Register("quartz_crystal_block", QUARTZ_CRYSTAL_BLOCK);
		Register("quartz_crystal_stairs", QUARTZ_CRYSTAL_STAIRS);
		Register("quartz_crystal_slab", QUARTZ_CRYSTAL_SLAB);
		Register("quartz_crystal_wall", QUARTZ_CRYSTAL_WALL);
		Register("smooth_quartz_wall", SMOOTH_QUARTZ_WALL);
		Register("quartz_wall", QUARTZ_WALL);
		Register("quartz_brick_stairs", QUARTZ_BRICK_STAIRS);
		Register("quartz_brick_slab", QUARTZ_BRICK_SLAB);
		Register("quartz_brick_wall", QUARTZ_BRICK_WALL);
		Register("quartz_axe", QUARTZ_AXE);
		Register("quartz_hoe", QUARTZ_HOE);
		Register("quartz_pickaxe", QUARTZ_PICKAXE);
		Register("quartz_shovel", QUARTZ_SHOVEL);
		Register("quartz_sword", QUARTZ_SWORD);
		Register("quartz_knife", QUARTZ_KNIFE);
		Register("quartz_helmet", QUARTZ_HELMET);
		Register("quartz_chestplate", QUARTZ_CHESTPLATE);
		Register("quartz_leggings", QUARTZ_LEGGINGS);
		Register("quartz_boots", QUARTZ_BOOTS);
		Register("quartz_horse_armor", QUARTZ_HORSE_ARMOR);
		//</editor-fold>
		//<editor-fold desc="Extended Iron">
		Register("iron_flame", IRON_FLAME_PARTICLE);
		Register("iron_torch", "iron_wall_torch", IRON_TORCH);
		Register("iron_soul_torch", "iron_soul_wall_torch", IRON_SOUL_TORCH);
		Register("iron_ender_torch", "iron_ender_wall_torch", IRON_ENDER_TORCH);
		Register("underwater_iron_torch", "underwater_iron_wall_torch", UNDERWATER_IRON_TORCH);
		Register("white_iron_lantern", WHITE_IRON_LANTERN);
		Register("white_iron_soul_lantern", WHITE_IRON_SOUL_LANTERN);
		Register("white_iron_ender_lantern", WHITE_IRON_ENDER_LANTERN);
		Register("iron_button", IRON_BUTTON);
		Register("white_iron_chain", WHITE_IRON_CHAIN);
		Register("iron_wall", IRON_WALL);
		Register("iron_bricks", IRON_BRICKS);
		Register("iron_brick_stairs", IRON_BRICK_STAIRS);
		Register("iron_brick_slab", IRON_BRICK_SLAB);
		Register("iron_brick_wall", IRON_BRICK_WALL);
		Register("cut_iron", CUT_IRON);
		Register("cut_iron_pillar", CUT_IRON_PILLAR);
		Register("cut_iron_stairs", CUT_IRON_STAIRS);
		Register("cut_iron_slab", CUT_IRON_SLAB);
		Register("cut_iron_wall", CUT_IRON_WALL);
		//</editor-fold>
		//<editor-fold desc="Extended Gold">
		Register("gold_flame", GOLD_FLAME_PARTICLE);
		Register("gold_torch", "gold_wall_torch", GOLD_TORCH);
		Register("gold_soul_torch", "gold_soul_wall_torch", GOLD_SOUL_TORCH);
		Register("gold_ender_torch", "gold_ender_wall_torch", GOLD_ENDER_TORCH);
		Register("underwater_gold_torch", "underwater_gold_wall_torch", UNDERWATER_GOLD_TORCH);
		Register("gold_lantern", GOLD_LANTERN);
		Register("gold_soul_lantern", GOLD_SOUL_LANTERN);
		Register("gold_ender_lantern", GOLD_ENDER_LANTERN);
		Register("gold_button", GOLD_BUTTON);
		Register("gold_chain", GOLD_CHAIN);
		Register("gold_bars", GOLD_BARS);
		Register("gold_wall", GOLD_WALL);
		Register("gold_bricks", GOLD_BRICKS);
		Register("gold_brick_stairs", GOLD_BRICK_STAIRS);
		Register("gold_brick_slab", GOLD_BRICK_SLAB);
		Register("gold_brick_wall", GOLD_BRICK_WALL);
		Register("cut_gold", CUT_GOLD);
		Register("cut_gold_pillar", CUT_GOLD_PILLAR);
		Register("cut_gold_stairs", CUT_GOLD_STAIRS);
		Register("cut_gold_slab", CUT_GOLD_SLAB);
		Register("cut_gold_wall", CUT_GOLD_WALL);
		//</editor-fold>
		//<editor-fold desc="Extended Netherite">
		Register("netherite_flame", NETHERITE_FLAME_PARTICLE);
		Register("netherite_torch", "netherite_wall_torch", NETHERITE_TORCH);
		Register("netherite_soul_torch", "netherite_soul_wall_torch", NETHERITE_SOUL_TORCH);
		Register("netherite_ender_torch", "netherite_ender_wall_torch", NETHERITE_ENDER_TORCH);
		Register("underwater_netherite_torch", "underwater_netherite_wall_torch", UNDERWATER_NETHERITE_TORCH);
		Register("netherite_lantern", NETHERITE_LANTERN);
		Register("netherite_soul_lantern", NETHERITE_SOUL_LANTERN);
		Register("netherite_ender_lantern", NETHERITE_ENDER_LANTERN);
		Register("netherite_nugget", NETHERITE_NUGGET);
		Register("netherite_button", NETHERITE_BUTTON);
		Register("netherite_chain", NETHERITE_CHAIN);
		Register("netherite_bars", NETHERITE_BARS);
		Register("netherite_wall", NETHERITE_WALL);
		Register("netherite_bricks", NETHERITE_BRICKS);
		Register("netherite_brick_stairs", NETHERITE_BRICK_STAIRS);
		Register("netherite_brick_slab", NETHERITE_BRICK_SLAB);
		Register("netherite_brick_wall", NETHERITE_BRICK_WALL);
		Register("cut_netherite", CUT_NETHERITE);
		Register("cut_netherite_pillar", CUT_NETHERITE_PILLAR);
		Register("cut_netherite_stairs", CUT_NETHERITE_STAIRS);
		Register("cut_netherite_slab", CUT_NETHERITE_SLAB);
		Register("cut_netherite_wall", CUT_NETHERITE_WALL);
		Register("netherite_horse_armor", NETHERITE_HORSE_ARMOR);
		Register("crushing_weighted_pressure_plate", CRUSHING_WEIGHTED_PRESSURE_PLATE);
		//</editor-fold>
		//<editor-fold desc="Extended Bone">
		Register("bone_torch", "bone_wall_torch", BONE_TORCH);
		Register("bone_soul_torch", "bone_soul_wall_torch", BONE_SOUL_TORCH);
		Register("bone_ender_torch", "bone_ender_wall_torch", BONE_ENDER_TORCH);
		Register("underwater_bone_torch", "underwater_bone_wall_torch", UNDERWATER_BONE_TORCH);
		Register("bone_ladder", BONE_LADDER);
		//</editor-fold>
		//<editor-fold desc="Gilded Blackstone">
		Register("gilded_blackstone_stairs", GILDED_BLACKSTONE_STAIRS);
		Register("gilded_blackstone_slab", GILDED_BLACKSTONE_SLAB);
		Register("gilded_blackstone_wall", GILDED_BLACKSTONE_WALL);
		Register("polished_gilded_blackstone", POLISHED_GILDED_BLACKSTONE);
		Register("polished_gilded_blackstone_stairs", POLISHED_GILDED_BLACKSTONE_STAIRS);
		Register("polished_gilded_blackstone_slab", POLISHED_GILDED_BLACKSTONE_SLAB);
		Register("polished_gilded_blackstone_wall", POLISHED_GILDED_BLACKSTONE_WALL);
		Register("polished_gilded_blackstone_bricks", POLISHED_GILDED_BLACKSTONE_BRICKS);
		Register("polished_gilded_blackstone_brick_stairs", POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS);
		Register("polished_gilded_blackstone_brick_slab", POLISHED_GILDED_BLACKSTONE_BRICK_SLAB);
		Register("polished_gilded_blackstone_brick_wall", POLISHED_GILDED_BLACKSTONE_BRICK_WALL);
		Register("chiseled_polished_gilded_blackstone", CHISELED_POLISHED_GILDED_BLACKSTONE);
		Register("cracked_polished_gilded_blackstone_bricks", CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS);
		//</editor-fold>
		//<editor-fold desc="Misc Stuff">
		Register("coal_slab", COAL_SLAB);
		Register("charcoal_block", CHARCOAL_BLOCK);
		Register("charcoal_slab", CHARCOAL_SLAB);
		Register("blue_shroomlight", BLUE_SHROOMLIGHT);
		Register("hedge_block", HEDGE_BLOCK);
		Register("cocoa_block", COCOA_BLOCK);
		Register("seed_block", SEED_BLOCK);
		Register("wax_block", WAX_BLOCK);
		Register("lava_bottle", LAVA_BOTTLE);
		//</editor-fold>
		Register("horn", HORN);
		//<editor-fold desc="Books">
		Register("unreadable_book", UNREADABLE_BOOK);
		Register("red_book", RED_BOOK);
		Register("orange_book", ORANGE_BOOK);
		Register("yellow_book", YELLOW_BOOK);
		Register("green_book", GREEN_BOOK);
		Register("blue_book", BLUE_BOOK);
		Register("purple_book", PURPLE_BOOK);
		Register("gray_book", GRAY_BOOK);
		//</editor-fold>
		//<editor-fold desc="More Wool">
		for (DyeColor color : COLORS) Register(color.getName() + "_wool_slab", WOOL_SLABS.get(color));
		Register("rainbow_wool", RAINBOW_WOOL);
		Register("rainbow_wool_slab", RAINBOW_WOOL_SLAB);
		Register("rainbow_carpet", RAINBOW_CARPET);
		//Register("rainbow_bed", RAINBOW_BED);
		//</editor-fold>
		Register("moss_slab", MOSS_SLAB);
		//Register("moss_bed", MOSS_BED);
		//<editor-fold desc="Goat">
		Register("minecraft:goat_horn", GOAT_HORN);
		//Extended
		Register("chevon", CHEVON);
		Register("cooked_chevon", COOKED_CHEVON);
		for (DyeColor color : COLORS) Register(color.getName() + "_fleece", FLEECE.get(color));
		for (DyeColor color : COLORS) Register(color.getName() + "_fleece_slab", FLEECE_SLABS.get(color));
		for (DyeColor color : COLORS) Register(color.getName() + "_fleece_carpet", FLEECE_CARPETS.get(color));
		Register("rainbow_fleece", RAINBOW_FLEECE);
		Register("rainbow_fleece_slab", RAINBOW_FLEECE_SLAB);
		Register("rainbow_fleece_carpet", RAINBOW_FLEECE_CARPET);
		Register("fleece_helmet", FLEECE_HELMET);
		Register("fleece_chestplate", FLEECE_CHESTPLATE);
		Register("fleece_leggings", FLEECE_LEGGINGS);
		Register("fleece_boots", FLEECE_BOOTS);
		Register("fleece_horse_armor", FLEECE_HORSE_ARMOR);
		Register("goat_horn_salve", GOAT_HORN_SALVE);
		//</editor-fold>
		//<editor-fold desc="Studded Leather">
		Register("studded_leather_helmet", STUDDED_LEATHER_HELMET);
		Register("studded_leather_chestplate", STUDDED_LEATHER_CHESTPLATE);
		Register("studded_leather_leggings", STUDDED_LEATHER_LEGGINGS);
		Register("studded_leather_boots", STUDDED_LEATHER_BOOTS);
		Register("studded_leather_horse_armor", STUDDED_LEATHER_HORSE_ARMOR);
		//</editor-fold>
		//<editor-fold desc="Charred Wood">
		Register("charred_log", CHARRED_LOG);
		Register("stripped_charred_log", STRIPPED_CHARRED_LOG);
		Register("charred_wood", CHARRED_WOOD);
		Register("stripped_charred_wood", STRIPPED_CHARRED_WOOD);
		Register("charred_planks", CHARRED_PLANKS);
		Register("charred_stairs", CHARRED_STAIRS);
		Register("charred_slab", CHARRED_SLAB);
		Register("charred_fence", CHARRED_FENCE);
		Register("charred_fence_gate", CHARRED_FENCE_GATE);
		Register("charred_door", CHARRED_DOOR);
		Register("charred_trapdoor", CHARRED_TRAPDOOR);
		Register("charred_pressure_plate", CHARRED_PRESSURE_PLATE);
		Register("charred_button", CHARRED_BUTTON);
		Register("charred", CHARRED_SIGN);
		Register("charred_boat", "charred_chest_boat", CHARRED_BOAT);
		Register("charred_bookshelf", CHARRED_BOOKSHELF);
		Register("charred_ladder", CHARRED_LADDER);
		Register("charred_woodcutter", CHARRED_WOODCUTTER);
		//</editor-fold>
		Register("minecraft:music_disc_5", MUSIC_DISC_5);
		Register("minecraft:disc_fragment_5", DISC_FRAGMENT_5);
		//<editor-fold desc="Mud">
		Register("minecraft:mud", MUD);
		Register("minecraft:packed_mud", PACKED_MUD);
		Register("minecraft:mud_bricks", MUD_BRICKS);
		Register("minecraft:mud_brick_stairs", MUD_BRICK_STAIRS);
		Register("minecraft:mud_brick_slab", MUD_BRICK_SLAB);
		Register("minecraft:mud_brick_wall", MUD_BRICK_WALL);
		//</editor-fold>
		//<editor-fold desc="Mangrove">
		Register("minecraft:mangrove_log", MANGROVE_LOG);
		Register("minecraft:stripped_mangrove_log", STRIPPED_MANGROVE_LOG);
		Register("minecraft:mangrove_wood", MANGROVE_WOOD);
		Register("minecraft:stripped_mangrove_wood", STRIPPED_MANGROVE_WOOD);
		Register("minecraft:mangrove_leaves", MANGROVE_LEAVES);
		Register("minecraft:mangrove_planks", MANGROVE_PLANKS);
		Register("minecraft:mangrove_stairs", MANGROVE_STAIRS);
		Register("minecraft:mangrove_slab", MANGROVE_SLAB);
		Register("minecraft:mangrove_fence", MANGROVE_FENCE);
		Register("minecraft:mangrove_fence_gate", MANGROVE_FENCE_GATE);
		Register("minecraft:mangrove_door", MANGROVE_DOOR);
		Register("minecraft:mangrove_trapdoor", MANGROVE_TRAPDOOR);
		Register("minecraft:mangrove_pressure_plate", MANGROVE_PRESSURE_PLATE);
		Register("minecraft:mangrove_button", MANGROVE_BUTTON);
		Register("minecraft:mangrove_roots", MANGROVE_ROOTS);
		Register("minecraft:muddy_mangrove_roots", MUDDY_MANGROVE_ROOTS);
		Register("minecraft:mangrove", MANGROVE_SIGN);
		Register("minecraft:mangrove_boat", "minecraft:mangrove_chest_boat", MANGROVE_BOAT);
		Register("minecraft:mangrove_propagule", "minecraft:potted_mangrove_propagule", MANGROVE_PROPAGULE);
		//Extended
		Register("mangrove_bookshelf", MANGROVE_BOOKSHELF);
		Register("mangrove_ladder", MANGROVE_LADDER);
		Register("mangrove_woodcutter", MANGROVE_WOODCUTTER);
		//</editor-fold>
		//<editor-fold desc="Bamboo Wood">
		Register("minecraft:bamboo_block", BAMBOO_BLOCK);
		Register("minecraft:stripped_bamboo_block", STRIPPED_BAMBOO_BLOCK);
		Register("minecraft:bamboo_planks", BAMBOO_PLANKS);
		Register("minecraft:bamboo_stairs", BAMBOO_STAIRS);
		Register("minecraft:bamboo_slab", BAMBOO_SLAB);
		Register("minecraft:bamboo_fence", BAMBOO_FENCE);
		Register("minecraft:bamboo_fence_gate", BAMBOO_FENCE_GATE);
		Register("minecraft:bamboo_door", BAMBOO_DOOR);
		Register("minecraft:bamboo_trapdoor", BAMBOO_TRAPDOOR);
		Register("minecraft:bamboo_pressure_plate", BAMBOO_PRESSURE_PLATE);
		Register("minecraft:bamboo_button", BAMBOO_BUTTON);
		Register("minecraft:bamboo", BAMBOO_SIGN);
		Register("minecraft:bamboo_raft", "minecraft:bamboo_chest_raft", BAMBOO_RAFT);
		//Bamboo Mosaic
		Register("minecraft:bamboo_mosaic", BAMBOO_MOSAIC);
		Register("minecraft:bamboo_mosaic_stairs", BAMBOO_MOSAIC_STAIRS);
		Register("minecraft:bamboo_mosaic_slab", BAMBOO_MOSAIC_SLAB);
		//Extended
		Register("bamboo_torch", "bamboo_wall_torch", BAMBOO_TORCH);
		Register("bamboo_soul_torch", "bamboo_soul_wall_torch", BAMBOO_SOUL_TORCH);
		Register("bamboo_ender_torch", "bamboo_ender_wall_torch", BAMBOO_ENDER_TORCH);
		Register("underwater_bamboo_torch", "underwater_bamboo_wall_torch", UNDERWATER_BAMBOO_TORCH);
		Register("bamboo_bookshelf", BAMBOO_BOOKSHELF);
		Register("bamboo_ladder", BAMBOO_LADDER);
		Register("bamboo_woodcutter", BAMBOO_WOODCUTTER);
		//</editor-fold>
		Register("minecraft:piglin_head", "minecraft:piglin_wall_head", PIGLIN_HEAD);
		Register("piglin_head", PIGLIN_HEAD_BLOCK_ENTITY);
		DispenserBlock.registerBehavior(PIGLIN_HEAD, new WearableDispenserBehavior());
		//Mobs
		ModActivities.Initialize();
		ModDataHandlers.Initialize();
		//<editor-fold desc="Allays">
		Register("minecraft:allay", ALLAY_ENTITY);
		FabricDefaultAttributeRegistry.register(ALLAY_ENTITY, AllayEntity.createAllayAttributes());
		Register("minecraft:allay_spawn_egg", ALLAY_SPAWN_EGG);
		//</editor-fold>
		//<editor-fold desc="Frogs">
		Register("minecraft:ochre_froglight", OCHRE_FROGLIGHT);
		Register("minecraft:verdant_froglight", VERDANT_FROGLIGHT);
		Register("minecraft:pearlescent_froglight", PEARLESCENT_FROGLIGHT);
		FROG_TEMPTATIONS_SENSOR.Initialize();
		FROG_ATTACKABLES_SENSOR.Initialize();
		IS_IN_WATER_SENSOR.Initialize();
		Register("minecraft:frog", FROG_ENTITY);
		FabricDefaultAttributeRegistry.register(FROG_ENTITY, FrogEntity.createFrogAttributes());
		SpawnRestrictionAccessor.callRegister(FROG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FrogEntity::canMobSpawn);
		Register("minecraft:frogspawn", FROGSPAWN);
		Register("minecraft:frog_spawn_egg", FROG_SPAWN_EGG);
		Register("minecraft:tadpole", TADPOLE_ENTITY);
		FabricDefaultAttributeRegistry.register(TADPOLE_ENTITY, TadpoleEntity.createTadpoleAttributes());
		Register("minecraft:tadpole_bucket", TADPOLE_BUCKET);
		Register("minecraft:tadpole_spawn_egg", TADPOLE_SPAWN_EGG);
		//</editor-fold>
		//<editor-fold desc="Sculk">
		Register("sculk_sensor", SCULK_SENSOR, SCULK_SENSOR_ENTITY);
		Register("minecraft:sculk", SCULK);
		Register("minecraft:sculk_vein",SCULK_VEIN);
		Register("minecraft:sculk_catalyst", SCULK_CATALYST, SCULK_CATALYST_ENTITY);
		Register("minecraft:sculk_shrieker", SCULK_SHRIEKER, SCULK_SHRIEKER_ENTITY);
		Register("minecraft:sculk_soul", SCULK_SOUL_PARTICLE);
		Register("minecraft:sculk_charge", SCULK_CHARGE_PARTICLE);
		Register("minecraft:sculk_charge_pop", SCULK_CHARGE_POP_PARTICLE);
		Register("minecraft:shriek", SHRIEK_PARTICLE);
		//</editor-fold>
		Register("minecraft:reinforced_deepslate", REINFORCED_DEEPSLATE);
		//<editor-fold desc="Wardens">
		WARDEN_ENTITY_SENSOR.Initialize();
		Register("minecraft:warden", WARDEN_ENTITY);
		Register("minecraft:sonic_boom", SONIC_BOOM_PARTICLE);
		FabricDefaultAttributeRegistry.register(WARDEN_ENTITY, WardenEntity.addAttributes());
		SpawnRestrictionAccessor.callRegister(WARDEN_ENTITY, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WardenEntity::canMobSpawn);
		Register("minecraft:warden_spawn_egg", WARDEN_SPAWN_EGG);
		
		Register("minecraft:darkness", DARKNESS_EFFECT);
		Register("minecraft:swift_sneak", SWIFT_SNEAK_ENCHANTMENT);
		Register("recovery_compass", RECOVERY_COMPASS);
		//</editor-fold>
		//<editor-fold desc="Echo">
		Register("minecraft:echo_shard", ECHO_SHARD);
		//Extended
		Register("echo_block", ECHO_BLOCK);
		Register("echo_stairs", ECHO_STAIRS);
		Register("echo_slab", ECHO_SLAB);
		Register("echo_wall", ECHO_WALL);
		Register("echo_crystal_block", ECHO_CRYSTAL_BLOCK);
		Register("echo_crystal_stairs", ECHO_CRYSTAL_STAIRS);
		Register("echo_crystal_slab", ECHO_CRYSTAL_SLAB);
		Register("echo_crystal_wall", ECHO_CRYSTAL_WALL);
		Register("budding_echo", BUDDING_ECHO);
		Register("echo_cluster", ECHO_CLUSTER);
		Register("large_echo_bud", LARGE_ECHO_BUD);
		Register("medium_echo_bud", MEDIUM_ECHO_BUD);
		Register("small_echo_bud", SMALL_ECHO_BUD);
		Register("echo_axe", ECHO_AXE);
		Register("echo_hoe", ECHO_HOE);
		Register("echo_pickaxe", ECHO_PICKAXE);
		Register("echo_shovel", ECHO_SHOVEL);
		Register("echo_sword", ECHO_SWORD);
		Register("echo_knife", ECHO_KNIFE);
		//</editor-fold>
		//<editor-fold desc="Sculk (Extended)">
		Register("sculk_stone", SCULK_STONE);
		Register("sculk_stone_stairs", SCULK_STONE_STAIRS);
		Register("sculk_stone_slab", SCULK_STONE_SLAB);
		Register("sculk_stone_wall", SCULK_STONE_WALL);
		Register("sculk_stone_bricks", SCULK_STONE_BRICKS);
		Register("sculk_stone_brick_stairs", SCULK_STONE_BRICK_STAIRS);
		Register("sculk_stone_brick_slab", SCULK_STONE_BRICK_SLAB);
		Register("sculk_stone_brick_wall", SCULK_STONE_BRICK_WALL);
		//Turf
		Register("calcite_sculk_turf", CALCITE_SCULK_TURF);
		Register("deepslate_sculk_turf", DEEPSLATE_SCULK_TURF);
		Register("dripstone_sculk_turf", DRIPSTONE_SCULK_TURF);
		Register("smooth_basalt_sculk_turf", SMOOTH_BASALT_SCULK_TURF);
		Register("tuff_sculk_turf", TUFF_SCULK_TURF);
		//</editor-fold>
		//<editor-fold desc="Camels">
		CAMEL_TEMPTATIONS_SENSOR.Initialize();
		Register("minecraft:camel", CAMEL_ENTITY);
		FabricDefaultAttributeRegistry.register(CAMEL_ENTITY, CamelEntity.createCamelAttributes());
		Register("minecraft:camel_spawn_egg", CAMEL_SPAWN_EGG);
		//</editor-fold>
		//<editor-fold desc="Flowers">
		//Minecraft Earth Flowers
		Register("buttercup", BUTTERCUP);
		Register("pink_daisy", PINK_DAISY);
		//Other Flowers
		Register("rose", ROSE);
		Register("blue_rose", BLUE_ROSE);
		Register("magenta_tulip", MAGENTA_TULIP);
		Register("marigold", MARIGOLD);
		Register("pink_allium", PINK_ALLIUM);
		Register("lavender", LAVENDER);
		Register("hydrangea", HYDRANGEA);
		Register("indigo_orchid", INDIGO_ORCHID);
		Register("magenta_orchid", MAGENTA_ORCHID);
		Register("orange_orchid", ORANGE_ORCHID);
		Register("purple_orchid", PURPLE_ORCHID);
		Register("red_orchid", RED_ORCHID);
		Register("white_orchid", WHITE_ORCHID);
		Register("yellow_orchid", YELLOW_ORCHID);
		Register("paeonia", PAEONIA);
		Register("aster", ASTER);
		Register("amaranth", AMARANTH);
		Register("blue_rose_bush", BLUE_ROSE_BUSH);
		Register("tall_allium", TALL_ALLIUM);
		Register("tall_pink_allium", TALL_PINK_ALLIUM);
		//</editor-fold>
		//<editor-fold desc="Biomes">
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
		//</editor-fold>
		//<editor-fold desc="Effects">
		Register("flashbanged", FLASHBANGED_EFFECT);
		Register("bleeding", BLEEDING_EFFECT);
		Register("tinted_goggles", TINTED_GOGGLES_EFFECT);
		//</editor-fold>
		RegisterOriginsPowers();
		RegisterCommands();
		ModGameEvent.RegisterAll();
		ModMemoryModules.Initialize();
		ModPositionSourceTypes.Initialize();
		IdentifiedSounds.RegisterAll();
		ModCriteria.Register();
		//Ryft Modpack
		IdentifiedSounds.RegisterPowers("ryft", "angel", "arsene", "auryon", "dj", "faerie", "gubby",
				"kaden", "kirha", "lavender", "navn", "oracle", "quincy", "rose", "zofia");
	}
	//TODO: Soul & Ender Fire: Jack O' Lantern
	//TODO: Carved Melons (and fire, soul, ender lanterns)

	//public static final Set<BedContainer> BEDS = new HashSet<>(Set.of( MOSS_BED, RAINBOW_BED ));
	public static final List<SignType> SIGN_TYPES = new ArrayList<>(List.of(
			CHARRED_SIGN.getType(), BAMBOO_SIGN.getType(), MANGROVE_SIGN.getType()
	));
	public static final Map<Block, Block> UNLIT_TO_LIT_TORCHES = Map.ofEntries(
			Map.entry(UNLIT_TORCH.asBlock(), Blocks.TORCH),
			Map.entry(UNLIT_SOUL_TORCH.asBlock(), Blocks.SOUL_TORCH)
	);
	public static final Map<Block, Block> UNLIT_TO_LIT_WALL_TORCHES = Map.ofEntries(
			Map.entry(UNLIT_TORCH.getWallBlock(), Blocks.WALL_TORCH),
			Map.entry(UNLIT_SOUL_TORCH.getWallBlock(), Blocks.SOUL_WALL_TORCH)
	);
	public static final Map<Block, Block> UNLIT_TO_LIT_LANTERNS = Map.ofEntries(
			Map.entry(UNLIT_LANTERN, Blocks.LANTERN),
			Map.entry(UNLIT_SOUL_LANTERN, Blocks.SOUL_LANTERN)
	);
	public static final Set<StatusEffect> MILK_IMMUNE_EFFECTS = new HashSet<>(Set.of( BLEEDING_EFFECT, TINTED_GOGGLES_EFFECT ));

	@Override
	public void onInitialize() {
		LOGGER.info("Wich Pack Initializing");
		RegisterAll();
	}
}
