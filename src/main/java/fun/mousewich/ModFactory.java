package fun.mousewich;

import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import fun.mousewich.block.*;
import fun.mousewich.block.basic.*;
import fun.mousewich.block.oxidizable.*;
import fun.mousewich.block.sign.HangingSignBlock;
import fun.mousewich.block.sign.WallHangingSignBlock;
import fun.mousewich.container.*;
import fun.mousewich.dispenser.HorseArmorDispenserBehavior;
import fun.mousewich.entity.projectile.ModArrowEntity;
import fun.mousewich.gen.data.loot.DropTable;
import fun.mousewich.gen.data.loot.BlockLootGenerator;
import fun.mousewich.item.HangingSignItem;
import fun.mousewich.item.basic.ChestBoatItem;
import fun.mousewich.item.basic.ModArmorItem;
import fun.mousewich.item.basic.ModHorseArmorItem;
import fun.mousewich.item.tool.*;
import fun.mousewich.item.tool.oxidized.*;
import fun.mousewich.material.ModArmorMaterials;
import fun.mousewich.material.ModMaterials;
import fun.mousewich.material.ModToolMaterials;
import fun.mousewich.sound.ModBlockSoundGroups;
import fun.mousewich.util.OxidationScale;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.BoatDispenserBehavior;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.Properties;
import net.minecraft.util.SignType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import static fun.mousewich.ModBase.ID;

public class ModFactory {
	//Helpful BlockState Delegates
	public static boolean never(BlockState state, BlockView world, BlockPos pos) { return false; }
	public static boolean noSpawning(BlockState var1, BlockView var2, BlockPos var3, EntityType<?> var) { return false; }
	public static boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
		return type == EntityType.OCELOT || type == EntityType.PARROT;
	}
	public static Boolean always(BlockState state, BlockView world, BlockPos pos) { return true; }
	public static Boolean always(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) { return true; }
	//Helpful Block Functions
	public static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
		return (state) -> state.get(Properties.LIT) ? litLevel : 0;
	}
	public static final ToIntFunction<BlockState> LUMINANCE_15 = state -> 15, LUMINANCE_14 = state -> 14, LUMINANCE_13 = state -> 13, LUMINANCE_12 = state -> 12;
	public static final ToIntFunction<BlockState> LUMINANCE_11 = state -> 11, LUMINANCE_10 = state -> 10, LUMINANCE_9 = state -> 9, LUMINANCE_8 = state -> 8;
	public static final ToIntFunction<BlockState> LUMINANCE_7 = state -> 7, LUMINANCE_6 = state -> 6, LUMINANCE_5 = state -> 5, LUMINANCE_4 = state -> 4;
	public static final ToIntFunction<BlockState> LUMINANCE_3 = state -> 3, LUMINANCE_2 = state -> 2, LUMINANCE_1 = state -> 1, LUMINANCE_0 = state -> 0;

	public static Item.Settings ItemSettings() { return new Item.Settings().group(ModBase.ITEM_GROUP); }
	public static Item.Settings ItemSettings(ItemGroup itemGroup) { return ItemSettings().group(itemGroup); }
	public static Item.Settings NetheriteItemSettings() { return ItemSettings().fireproof(); }
	//Items
	public static AxeItem MakeAxe(ModToolMaterials material) { return new ModAxeItem(material, material.getAxeDamage(), material.getAxeSpeed()); }
	public static AxeItem MakeOxidizableAxe(Oxidizable.OxidationLevel level, ModToolMaterials material) {
		return new OxidizableAxeItem(level, material, material.getAxeDamage(), material.getAxeSpeed());
	}
	public static HoeItem MakeHoe(ModToolMaterials material) { return new ModHoeItem(material, material.getHoeDamage(), material.getHoeSpeed()); }
	public static HoeItem MakeOxidizableHoe(Oxidizable.OxidationLevel level, ModToolMaterials material) {
		return new OxidizableHoeItem(level, material, material.getHoeDamage(), material.getHoeSpeed());
	}
	public static PickaxeItem MakePickaxe(ModToolMaterials material) { return new ModPickaxeItem(material, material.getPickaxeDamage(), material.getPickaxeSpeed()); }
	public static PickaxeItem MakeOxidizablePickaxe(Oxidizable.OxidationLevel level, ModToolMaterials material) {
		return new OxidizablePickaxeItem(level, material, material.getPickaxeDamage(), material.getPickaxeSpeed());
	}
	public static ShovelItem MakeShovel(ModToolMaterials material) { return new ModShovelItem(material, material.getShovelDamage(), material.getShovelSpeed()); }
	public static ShovelItem MakeOxidizableShovel(Oxidizable.OxidationLevel level, ModToolMaterials material) {
		return new OxidizableShovelItem(level, material, material.getShovelDamage(), material.getShovelSpeed());
	}
	public static SwordItem MakeSword(ModToolMaterials material) { return new ModSwordItem(material, material.getSwordDamage(), material.getSwordSpeed()); }
	public static SwordItem MakeOxidizableSword(Oxidizable.OxidationLevel level, ModToolMaterials material) {
		return new OxidizableSwordItem(level, material, material.getSwordDamage(), material.getSwordSpeed());
	}
	public static KnifeItem MakeKnife(ModToolMaterials material) { return new ModKnifeItem(material); }
	public static KnifeItem MakeOxidizableKnife(Oxidizable.OxidationLevel level, ModToolMaterials material) {
		return new OxidizableKnifeItem(level, material);
	}
	public static ArmorItem MakeHelmet(ArmorMaterial material) { return new ModArmorItem(material, EquipmentSlot.HEAD); }
	public static ArmorItem MakeChestplate(ArmorMaterial material) { return new ModArmorItem(material, EquipmentSlot.CHEST); }
	public static ArmorItem MakeLeggings(ArmorMaterial material) { return new ModArmorItem(material, EquipmentSlot.LEGS); }
	public static ArmorItem MakeBoots(ArmorMaterial material) { return new ModArmorItem(material, EquipmentSlot.FEET); }
	public static Item MakeHorseArmor(ModArmorMaterials material) { return MakeHorseArmor(material.getHorseProtectionAmount(), material); }
	public static Item MakeHorseArmor(int bonus, ArmorMaterial material) { return MakeHorseArmor(bonus, material.getName()); }
	public static Item MakeHorseArmor(int bonus, String name) {
		ModHorseArmorItem armor = new ModHorseArmorItem(bonus, name, ItemSettings().maxCount(1));
		DispenserBlock.registerBehavior(armor, new HorseArmorDispenserBehavior()::dispenseSilently);
		return armor;
	}

	public static EntityModelLayer MakeModelLayer(String path) { return MakeModelLayer(path, "main"); }
	public static EntityModelLayer MakeModelLayer(String path, String name) { return new EntityModelLayer(ID(path), name); }

	public static EntityType<ModArrowEntity> MakeArrowEntity(EntityType.EntityFactory<ModArrowEntity> factory) {
		return FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(4).trackedUpdateRate(20).build();
	}

	public static Block.Settings LeafBlockSettings() {
		return Block.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(ModFactory::canSpawnOnLeaves).suffocates(ModFactory::never).blockVision(ModFactory::never);
	}

	public static BlockContainer MakeBookshelf(MapColor color) { return MakeBookshelf(color, BlockSoundGroup.WOOD); }
	public static BlockContainer MakeBookshelf(MapColor color, BlockSoundGroup sounds) {
		return new BlockContainer(new BookshelfBlock(Block.Settings.of(Material.WOOD, color).strength(1.5F).sounds(sounds))).drops(DropTable.BOOKSHELF);
	}

	public static final Material FROGSPAWN_MATERIAL = new Material(MapColor.WATER_BLUE, false, false, false, false, false, false, PistonBehavior.DESTROY);
	public static BlockContainer MakeFroglight(MapColor color) { return MakeFroglight(color, ItemSettings()); }
	public static BlockContainer MakeFroglight(MapColor color, Item.Settings settings) {
		return new BlockContainer(new PillarBlock(Block.Settings.of(ModMaterials.FROGLIGHT, color).strength(0.3f).luminance(LUMINANCE_15).sounds(ModBlockSoundGroups.FROGLIGHT)), settings).dropSelf();
	}

	public static Block.Settings TorchSettings(int luminance, BlockSoundGroup sounds) {
		return FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(createLightLevelFromLitBlockState(luminance)).sounds(sounds);
	}
	public static TorchContainer MakeTorch(int luminance, BlockSoundGroup sounds, DefaultParticleType particle) { return MakeTorch(luminance, sounds, particle, ItemSettings()); }
	public static TorchContainer MakeTorch(int luminance, BlockSoundGroup sounds, DefaultParticleType particle, Item.Settings settings) {
		return new TorchContainer(TorchSettings(luminance, sounds), particle, settings).dropSelf();
	}
	public static TorchContainer MakeTorch(BlockSoundGroup sounds, DefaultParticleType particle) { return MakeTorch(14, sounds, particle); }
	public static TorchContainer MakeTorch(BlockSoundGroup sounds, DefaultParticleType particle, Item.Settings settings) { return MakeTorch(14, sounds, particle, settings); }
	public static TorchContainer MakeTorch(BlockSoundGroup sounds) { return MakeTorch(sounds, ParticleTypes.FLAME); }
	public static TorchContainer MakeTorch(BlockSoundGroup sounds, Item.Settings settings) { return MakeTorch(sounds, ParticleTypes.FLAME, settings); }
	public static TorchContainer MakeEnderTorch(BlockSoundGroup sounds) { return MakeTorch(12, sounds, ModBase.ENDER_FIRE_FLAME_PARTICLE); }
	public static TorchContainer MakeEnderTorch(BlockSoundGroup sounds, Item.Settings settings) { return MakeTorch(12, sounds, ModBase.ENDER_FIRE_FLAME_PARTICLE, settings); }
	public static TorchContainer MakeSoulTorch(BlockSoundGroup sounds) { return MakeTorch(10, sounds, ParticleTypes.SOUL_FIRE_FLAME); }
	public static TorchContainer MakeSoulTorch(BlockSoundGroup sounds, Item.Settings settings) { return MakeTorch(10, sounds, ParticleTypes.SOUL_FIRE_FLAME, settings); }
	public static TorchContainer MakeUnderwaterTorch(BlockSoundGroup sounds) { return MakeUnderwaterTorch(sounds, ItemSettings()); }
	public static TorchContainer MakeUnderwaterTorch(BlockSoundGroup sounds, Item.Settings settings) {
		return TorchContainer.Waterloggable(TorchSettings(14, sounds), ModBase.UNDERWATER_TORCH_GLOW, settings).dropSelf();
	}

	public static TorchContainer MakeOxidizableTorch(Oxidizable.OxidationLevel level, int luminance, BlockSoundGroup sounds, DefaultParticleType particle) {
		return TorchContainer.Oxidizable(level, TorchSettings(luminance, sounds).ticksRandomly(), particle).dropSelf();
	}
	public static TorchContainer MakeOxidizableTorch(Oxidizable.OxidationLevel level, BlockSoundGroup sounds, DefaultParticleType particle) {
		return MakeOxidizableTorch(level, 14, sounds, particle);
	}
	public static TorchContainer MakeOxidizableEnderTorch(Oxidizable.OxidationLevel level, BlockSoundGroup sounds) {
		return MakeOxidizableTorch(level, 12, sounds, ModBase.ENDER_FIRE_FLAME_PARTICLE);
	}
	public static TorchContainer MakeOxidizableSoulTorch(Oxidizable.OxidationLevel level, BlockSoundGroup sounds) {
		return MakeOxidizableTorch(level, 10, sounds, ParticleTypes.SOUL_FIRE_FLAME);
	}
	public static TorchContainer MakeOxidizableUnderwaterTorch(Oxidizable.OxidationLevel level, BlockSoundGroup sounds) {
		return TorchContainer.WaterloggableOxidizable(level, TorchSettings(14, sounds).ticksRandomly(), ModBase.UNDERWATER_TORCH_GLOW).dropSelf();
	}

	public static Block.Settings OxidizablePressurePlateSettings(Oxidizable.OxidationLevel level) {
		return OxidizablePressurePlateSettings(level, BlockSoundGroup.WOOD);
	}
	public static Block.Settings OxidizablePressurePlateSettings(Oxidizable.OxidationLevel level, BlockSoundGroup sounds) {
		return Block.Settings.of(Material.METAL, OxidationScale.getMapColor(level)).requiresTool().noCollision().strength(0.5F).sounds(sounds);
	}

	public static Block.Settings LanternSettings(int luminance) {
		return Block.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(createLightLevelFromLitBlockState(luminance)).nonOpaque();
	}
	public static BlockContainer MakeLantern(int luminance) { return MakeLantern(luminance, ItemSettings()); }
	public static BlockContainer MakeLantern(int luminance, Item.Settings settings) {
		return new BlockContainer(new LightableLanternBlock(LanternSettings(luminance)), settings).dropSelf();
	}
	public static Block.Settings UnlitLanternSettings() {
		return Block.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).nonOpaque();
	}
	public static UnlitLanternBlock MakeUnlitLantern(Block lit, ItemConvertible getPickStack) {
		return new UnlitLanternBlock(lit, getPickStack, UnlitLanternSettings());
	}
	public static BlockContainer MakeOxidizableLantern(int luminance, Oxidizable.OxidationLevel level) {
		return new BlockContainer(new OxidizableLightableLanternBlock(level, LanternSettings(luminance))).dropSelf();
	}

	public static BlockContainer MakeCampfire(int luminance, int fireDamage, MapColor mapColor, BlockSoundGroup sounds, boolean emitsParticles) {
		return new BlockContainer(new ModCampfireBlock(emitsParticles, fireDamage, Block.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(sounds).luminance(createLightLevelFromLitBlockState(luminance)).nonOpaque()));
	}

	public static Block.Settings BarsSettings(BlockSoundGroup sounds) {
		return Block.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(sounds).nonOpaque();
	}
	public static BlockContainer MakeBars() { return MakeBars(BlockSoundGroup.METAL); }
	public static BlockContainer MakeBars(BlockSoundGroup sounds) { return MakeBars(sounds, ItemSettings()); }
	public static BlockContainer MakeBars(BlockSoundGroup sounds, Item.Settings settings) {
		return new BlockContainer(new ModPaneBlock(BarsSettings(sounds)), settings).dropSelf();
	}
	public static BlockContainer MakeOxidizableBars(BlockSoundGroup sounds, Oxidizable.OxidationLevel level) {
		return new BlockContainer(new OxidizablePaneBlock(level, BarsSettings(sounds))).dropSelf();
	}

	public static Block.Settings ChainSettings() {
		return Block.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.CHAIN).nonOpaque();
	}
	public static BlockContainer MakeChain() { return MakeChain(ItemSettings()); }
	public static BlockContainer MakeChain(Item.Settings settings) {
		return new BlockContainer(new ChainBlock(ChainSettings()), settings).dropSelf();
	}
	public static BlockContainer MakeOxidizableChain(Oxidizable.OxidationLevel level) {
		return new BlockContainer(new OxidizableChainBlock(level, ChainSettings())).dropSelf();
	}

	public static Block.Settings BedSettings(MapColor color, BlockSoundGroup sounds, ToIntFunction<BlockState> luminance) {
		Block.Settings output = AbstractBlock.Settings.copy(Blocks.WHITE_BED).mapColor(color).sounds(sounds);
		return luminance == null ? output : output.luminance(luminance);
	}

	public static BedContainer MakeBed(String name) { return MakeBed(name, MapColor.WHITE); }
	public static BedContainer MakeBed(String name, MapColor color) { return MakeBed(name, color, BlockSoundGroup.WOOD); }
	public static BedContainer MakeBed(String name, MapColor color, BlockSoundGroup sounds) { return MakeBed(name, color, sounds, null); }
	public static BedContainer MakeBed(String name, MapColor color, BlockSoundGroup sounds, ToIntFunction<BlockState> luminance) {
		BedContainer bed = new BedContainer(name, BedSettings(color, sounds, luminance), ItemSettings().maxCount(1));
		BlockLootGenerator.Drops.put(bed.asBlock(), DropTable.BED);
		return bed;
	}

	public static Item.Settings BoatSettings() { return ItemSettings().maxCount(1); }
	public static Item.Settings BoatSettings(ItemGroup group) { return ItemSettings(group).maxCount(1); }
	public static ChestBoatItem MakeChestBoat(BoatEntity.Type type, Item.Settings settings) {
		ChestBoatItem boat = new ChestBoatItem(type, settings.maxCount(1));
		DispenserBlock.registerBehavior(boat, new BoatDispenserBehavior(type));
		return boat;
	}

	public static BoatContainer MakeBoat(String name, IBlockItemContainer base) { return MakeBoat(name, base, BoatSettings()); }
	public static BoatContainer MakeBoat(String name, IBlockItemContainer base, Item.Settings settings) {
		return new BoatContainer(name, base, false, settings).dispensable();
	}

	public static BlockContainer MakeCandle(MapColor mapColor, double luminance) {
		return new BlockContainer(new CandleBlock(Block.Settings.of(Material.DECORATION, mapColor).nonOpaque().strength(0.1F).sounds(BlockSoundGroup.CANDLE)
				.luminance((state) -> (int)(state.get(CandleBlock.LIT) ? luminance * state.get(CandleBlock.CANDLES) : 0)))).drops(DropTable.CANDLE);
	}

	public static Block.Settings LadderSettings() { return LadderSettings(BlockSoundGroup.LADDER); }
	public static Block.Settings LadderSettings(BlockSoundGroup sounds) {
		return Block.Settings.of(Material.DECORATION).strength(0.4F).sounds(sounds).nonOpaque();
	}
	public static BlockContainer MakeLadder() { return MakeLadder(LadderSettings()); }
	public static BlockContainer MakeLadder(BlockSoundGroup sounds) { return MakeLadder(LadderSettings(sounds)); }
	public static BlockContainer MakeLadder(Block.Settings settings) {
		return new BlockContainer(new ModLadderBlock(settings)).dropSelf();
	}

	public static BlockContainer MakeWood(IBlockItemContainer log, Item.Settings settings) {
		return new BlockContainer(new PillarBlock(Block.Settings.copy(log.asBlock())), settings).dropSelf();
	}
	public static BlockContainer MakeWood(MapColor color) { return MakeWood(color, ItemSettings()); }
	public static BlockContainer MakeWood(MapColor color, Item.Settings settings) { return MakeWood(color, BlockSoundGroup.WOOD, settings); }
	public static BlockContainer MakeWood(MapColor color, BlockSoundGroup soundGroup) { return MakeWood(color, soundGroup, ItemSettings()); }
	public static BlockContainer MakeWood(MapColor color, BlockSoundGroup soundGroup, Item.Settings settings) {
		return new BlockContainer(new ModPillarBlock(LogSettings(color, soundGroup)), settings).dropSelf();
	}

	public static Block.Settings ButtonSettings(float strength, BlockSoundGroup sounds) { return Block.Settings.of(Material.DECORATION).noCollision().strength(strength).sounds(sounds); }
	public static BlockContainer MakeWoodButton() { return MakeWoodButton(ItemSettings()); }
	public static BlockContainer MakeWoodButton(Item.Settings settings) {
		return new BlockContainer(new ModWoodenButtonBlock(ButtonSettings(0.5F, BlockSoundGroup.WOOD)), settings).dropSelf();
	}
	public static BlockContainer MakeWoodButton(Item.Settings settings, BlockSoundGroup soundGroup, SoundEvent pressed, SoundEvent released) {
		return new BlockContainer(new ModWoodenButtonBlock(ButtonSettings(0.5F, soundGroup), pressed, released), settings).dropSelf();
	}

	public static BlockContainer MakeMetalButton() { return MakeMetalButton(BlockSoundGroup.METAL); }
	public static BlockContainer MakeMetalButton(BlockSoundGroup sounds) { return MakeMetalButton(sounds, ItemSettings()); }
	public static BlockContainer MakeMetalButton(BlockSoundGroup sounds, Item.Settings settings) {
		return new BlockContainer(new MetalButtonBlock(ButtonSettings(10, sounds)), settings).dropSelf();
	}
	public static BlockContainer MakeOxidizableButton(BlockSoundGroup sounds, Oxidizable.OxidationLevel level) {
		return new BlockContainer(new OxidizableButtonBlock(level, ButtonSettings(10, sounds))).dropSelf();
	}

	public static Block.Settings WoodPressurePlateSettings(MapColor color, BlockSoundGroup soundGroup) { return Block.Settings.of(Material.WOOD, color).noCollision().strength(0.5F).sounds(soundGroup); }
	public static BlockContainer MakeWoodPressurePlate(IBlockItemContainer ingredient) { return MakeWoodPressurePlate(ingredient, ItemSettings()); }
	public static BlockContainer MakeWoodPressurePlate(IBlockItemContainer ingredient, Item.Settings settings) {
		return new BlockContainer(ModPressurePlateBlock.Wooden(PressurePlateBlock.ActivationRule.EVERYTHING, WoodPressurePlateSettings(ingredient.asBlock().getDefaultMapColor(), BlockSoundGroup.WOOD)), settings).dropSelf();
	}
	public static BlockContainer MakeWoodPressurePlate(IBlockItemContainer ingredient, Item.Settings settings, BlockSoundGroup soundGroup, SoundEvent pressed, SoundEvent released) {
		return new BlockContainer(new ModPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, WoodPressurePlateSettings(ingredient.asBlock().getDefaultMapColor(), soundGroup), pressed, released), settings).dropSelf();
	}

	public static Block.Settings WoodDoorSettings(MapColor color, BlockSoundGroup soundGroup) { return Block.Settings.of(Material.WOOD, color).strength(3.0F).sounds(soundGroup).nonOpaque(); }
	public static BlockContainer MakeWoodDoor(IBlockItemContainer ingredient) { return MakeWoodDoor(ingredient, ItemSettings()); }
	public static BlockContainer MakeWoodDoor(IBlockItemContainer ingredient, Item.Settings settings) {
		return new BlockContainer(new ModDoorBlock(WoodDoorSettings(ingredient.asBlock().getDefaultMapColor(), BlockSoundGroup.WOOD)), settings).drops(DropTable.DOOR);
	}
	public static BlockContainer MakeWoodDoor(IBlockItemContainer ingredient, Item.Settings settings, BlockSoundGroup soundGroup, SoundEvent opened, SoundEvent closed) {
		return new BlockContainer(new ModDoorBlock(WoodDoorSettings(ingredient.asBlock().getDefaultMapColor(), soundGroup), opened, closed), settings).drops(DropTable.DOOR);
	}

	public static SignContainer MakeWoodSign(String name, MapColor color) { return MakeWoodSign(name, ItemSettings(), color); }
	public static SignContainer MakeWoodSign(String name, Item.Settings settings, MapColor color) {
		return MakeWoodSign(name, settings, color, ModBlockSoundGroups.HANGING_SIGN);
	}
	public static SignContainer MakeWoodSign(String name, Item.Settings settings, MapColor color, BlockSoundGroup hangingSounds) {
		return new SignContainer(name, Material.WOOD, BlockSoundGroup.WOOD, settings.maxCount(16), color, hangingSounds).dropSelf();
	}

	public static Block.Settings HangingSignSettings(MapColor color) {
		return HangingSignSettings(color, ModBlockSoundGroups.HANGING_SIGN);
	}
	public static Block.Settings HangingSignSettings(MapColor color, BlockSoundGroup sounds) {
		return Block.Settings.of(Material.WOOD, color).noCollision().strength(1.0f).sounds(sounds);
	}
	public static Item.Settings SignItemSettings() { return ItemSettings().maxCount(16); }
	public static Item.Settings SignItemSettings(ItemGroup group) { return ItemSettings(group).maxCount(16); }
	public static WallBlockContainer MakeHangingSign(SignType type, MapColor color) { return MakeHangingSign(type, color, ItemSettings()); }
	public static WallBlockContainer MakeHangingSign(SignType type, MapColor color, Item.Settings itemSettings) { return MakeHangingSign(type, HangingSignSettings(color), itemSettings); }
	public static WallBlockContainer MakeHangingSign(SignType type, MapColor color, Item.Settings itemSettings, BlockSoundGroup sounds) { return MakeHangingSign(type, HangingSignSettings(color, sounds), itemSettings); }
	public static WallBlockContainer MakeHangingSign(SignType type, Block.Settings settings, Item.Settings itemSettings) {
		HangingSignBlock hanging = new HangingSignBlock(settings, type);
		WallHangingSignBlock wall = new WallHangingSignBlock(settings, type);
		HangingSignItem item = new HangingSignItem(hanging, wall, itemSettings);
		return new WallBlockContainer(hanging, wall, item).dropSelf();
	}

	public static Block.Settings PlanksSettings(MapColor mapColor) { return PlanksSettings(mapColor, BlockSoundGroup.WOOD); }
	public static Block.Settings PlanksSettings(MapColor mapColor, BlockSoundGroup soundGroup) {
		return Block.Settings.of(Material.WOOD, mapColor).strength(2.0F, 3.0F).sounds(soundGroup);
	}
	public static BlockContainer MakePlanks(MapColor mapColor) { return MakePlanks(mapColor, ItemSettings()); }
	public static BlockContainer MakePlanks(MapColor mapColor, Item.Settings settings) {
		return new BlockContainer(new Block(PlanksSettings(mapColor)), settings).dropSelf();
	}
	public static BlockContainer MakePlanks(MapColor mapColor, BlockSoundGroup soundGroup, Item.Settings settings) {
		return new BlockContainer(new Block(PlanksSettings(mapColor, soundGroup)), settings).dropSelf();
	}

	public static Block.Settings LogSettings(MapColor color, BlockSoundGroup soundGroup) { return logSettings(Block.Settings.of(Material.WOOD, color), soundGroup); }
	public static Block.Settings LogSettings(MapColor top, MapColor side, BlockSoundGroup soundGroup) { return logSettings(Block.Settings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? top : side), soundGroup); }
	private static Block.Settings logSettings(Block.Settings settings, BlockSoundGroup soundGroup) { return settings.strength(2.0f).sounds(soundGroup); }

	public static BlockContainer MakeLog(MapColor color) { return MakeLog(color,ItemSettings()); }
	public static BlockContainer MakeLog(MapColor color, Item.Settings settings) { return MakeLog(color, BlockSoundGroup.WOOD, settings); }
	public static BlockContainer MakeLog(MapColor color, BlockSoundGroup soundGroup) { return MakeLog(color, soundGroup, ItemSettings()); }
	public static BlockContainer MakeLog(MapColor color, BlockSoundGroup soundGroup, Item.Settings settings) { return makeLog(LogSettings(color, soundGroup), settings); }
	public static BlockContainer MakeLog(MapColor top, MapColor side) { return MakeLog(top, side, ItemSettings()); }
	public static BlockContainer MakeLog(MapColor top, MapColor side, Item.Settings settings) { return MakeLog(top, side, BlockSoundGroup.WOOD, settings); }
	public static BlockContainer MakeLog(MapColor top, MapColor side, BlockSoundGroup soundGroup) { return MakeLog(top, side, soundGroup, ItemSettings()); }
	public static BlockContainer MakeLog(MapColor top, MapColor side, BlockSoundGroup soundGroup, Item.Settings settings) { return makeLog(LogSettings(top, side, soundGroup), settings); }
	private static BlockContainer makeLog(Block.Settings blockSettings, Item.Settings settings) {
		return new BlockContainer(new ModPillarBlock(blockSettings), settings).dropSelf();
	}

	public static BlockContainer MakeBlock(Block.Settings blockSettings) { return MakeBlock(blockSettings, ItemSettings()); }
	public static BlockContainer MakeBlock(Block.Settings blockSettings, Item.Settings itemSettings) { return BuildBlock(new Block(blockSettings), itemSettings); }
	public static BlockContainer MakeBlock(BlockConvertible base) { return MakeBlock(base, ItemSettings()); }
	public static BlockContainer MakeBlock(BlockConvertible base, Item.Settings settings) { return BuildBlock(new Block(Block.Settings.copy(base.asBlock())), settings); }
	public static BlockContainer MakeBlock(Block base) { return MakeBlock(base, ItemSettings()); }
	public static BlockContainer MakeBlock(Block base, Item.Settings settings) { return BuildBlock(new Block(Block.Settings.copy(base)), settings); }
	public static BlockContainer BuildBlock(Block block) { return BuildBlock(block, ItemSettings()); }
	public static BlockContainer BuildBlock(Block block, Item.Settings settings) { return BuildBlock(block, null, settings); }
	public static BlockContainer BuildBlock(Block block, DropTable dropTable) { return BuildBlock(block, dropTable, ItemSettings()); }
	public static BlockContainer BuildBlock(Block block, DropTable dropTable, Item.Settings settings) {
		BlockContainer container = new BlockContainer(block, settings);
		return dropTable == null ? container.dropSelf() : container.drops(dropTable);
	}

	public static BlockContainer MakeSlab(BlockConvertible base) { return MakeSlab(base, ItemSettings()); }
	public static BlockContainer MakeSlab(BlockConvertible base, Item.Settings settings) { return BuildSlab(new ModSlabBlock(base), settings); }
	public static BlockContainer MakeSlab(Block base) { return MakeSlab(base, ItemSettings()); }
	public static BlockContainer MakeSlab(Block base, Item.Settings settings) { return BuildSlab(new ModSlabBlock(base), settings); }
	public static BlockContainer BuildSlab(Block slab) { return BuildSlab(slab, ItemSettings()); }
	public static BlockContainer BuildSlab(Block slab, Item.Settings settings) { return BuildSlab(slab, null, settings); }
	public static BlockContainer BuildSlab(Block slab, DropTable dropTable) { return BuildSlab(slab, dropTable, ItemSettings()); }
	public static BlockContainer BuildSlab(Block slab, DropTable dropTable, Item.Settings settings) {
		BlockContainer container = new BlockContainer(slab, settings);
		return dropTable == null ? container.dropSlabs() : container.drops(dropTable);
	}

	public static BlockContainer MakeStairs(BlockConvertible base) { return MakeStairs(base, ItemSettings()); }
	public static BlockContainer MakeStairs(BlockConvertible base, Item.Settings settings) { return BuildStairs(new ModStairsBlock(base), settings); }
	public static BlockContainer MakeStairs(Block base) { return MakeStairs(base, ItemSettings()); }
	public static BlockContainer MakeStairs(Block base, Item.Settings settings) { return BuildStairs(new ModStairsBlock(base), settings); }
	public static BlockContainer BuildStairs(Block stairs) { return BuildStairs(stairs, ItemSettings()); }
	public static BlockContainer BuildStairs(Block stairs, Item.Settings settings) { return BuildStairs(stairs, null, settings); }
	public static BlockContainer BuildStairs(Block stairs, DropTable dropTable) { return BuildStairs(stairs, dropTable, ItemSettings()); }
	public static BlockContainer BuildStairs(Block stairs, DropTable dropTable, Item.Settings settings) {
		BlockContainer container = new BlockContainer(stairs, settings);
		return dropTable == null ? container.dropSelf() : container.drops(dropTable);
	}

	public static BlockContainer MakeWall(BlockConvertible base) { return MakeWall(base, ItemSettings()); }
	public static BlockContainer MakeWall(BlockConvertible base, Item.Settings settings) { return BuildWall(new ModWallBlock(base), settings); }
	public static BlockContainer MakeWall(Block base) { return MakeWall(base, ItemSettings()); }
	public static BlockContainer MakeWall(Block base, Item.Settings settings) { return BuildWall(new ModWallBlock(base), settings); }
	public static BlockContainer BuildWall(Block wall) { return BuildWall(wall, ItemSettings()); }
	public static BlockContainer BuildWall(Block wall, Item.Settings settings) { return BuildWall(wall, null, settings); }
	public static BlockContainer BuildWall(Block wall, DropTable dropTable) { return BuildWall(wall, dropTable, ItemSettings()); }
	public static BlockContainer BuildWall(Block wall, DropTable dropTable, Item.Settings settings) {
		BlockContainer container = new BlockContainer(wall, settings);
		return dropTable == null ? container.dropSelf() : container.drops(dropTable);
	}

	public static BlockContainer MakeOxidizableWall(Block base, Oxidizable.OxidationLevel level) {
		return new BlockContainer(new OxidizableWallBlock(level, base)).dropSelf();
	}

	public static BlockContainer MakeWoodFence(IBlockItemContainer base) { return MakeWoodFence(base, ItemSettings()); }
	public static BlockContainer MakeWoodFence(IBlockItemContainer base, Item.Settings settings) {
		return new BlockContainer(new ModFenceBlock(base.asBlock()), settings).dropSelf();
	}

	public static BlockContainer MakeWoodFenceGate(IBlockItemContainer ingredient) { return MakeWoodFenceGate(ingredient, ItemSettings()); }
	public static BlockContainer MakeWoodFenceGate(IBlockItemContainer ingredient, Item.Settings settings) {
		return new BlockContainer(new FenceGateBlock(Block.Settings.copy(ingredient.asBlock())), settings).dropSelf();
	}
	public static BlockContainer MakeWoodFenceGate(IBlockItemContainer ingredient, Item.Settings settings, SoundEvent opened, SoundEvent closed) {
		return new BlockContainer(new ModFenceGateBlock(Block.Settings.copy(ingredient.asBlock()), opened, closed), settings).dropSelf();
	}

	public static Block.Settings WoodTrapdoorSettings(MapColor color, BlockSoundGroup soundGroup) { return Block.Settings.of(Material.WOOD, color).strength(3.0F).sounds(soundGroup).nonOpaque().allowsSpawning(ModFactory::noSpawning); }
	public static BlockContainer MakeWoodTrapdoor(IBlockItemContainer ingredient) { return MakeWoodTrapdoor(ingredient, ItemSettings()); }
	public static BlockContainer MakeWoodTrapdoor(IBlockItemContainer ingredient, Item.Settings settings) {
		return new BlockContainer(new ModTrapdoorBlock(WoodTrapdoorSettings(ingredient.asBlock().getDefaultMapColor(), BlockSoundGroup.WOOD)), settings).dropSelf();
	}
	public static BlockContainer MakeWoodTrapdoor(IBlockItemContainer ingredient, Item.Settings settings, BlockSoundGroup soundGroup, SoundEvent opened, SoundEvent closed) {
		return new BlockContainer(new ModTrapdoorBlock(WoodTrapdoorSettings(ingredient.asBlock().getDefaultMapColor(), soundGroup), opened, closed), settings).dropSelf();
	}

	public static Block.Settings GlassTrapdoorSettings(Block block) {
		return Block.Settings.copy(block).strength(3.0F).nonOpaque().allowsSpawning(ModFactory::noSpawning);
	}

	public static BlockContainer MakeWoodcutter(MapColor color) {
		return new BlockContainer(new WoodcutterBlock(Block.Settings.of(Material.WOOD, color).strength(3.5F)))
				.dropSelf();
	}

	public static BlockContainer MakeBarrel(MapColor color) { return MakeBarrel(color, BlockSoundGroup.WOOD); }
	public static BlockContainer MakeBarrel(MapColor color, BlockSoundGroup soundGroup) {
		return new BlockContainer(new ModBarrelBlock(Block.Settings.of(Material.WOOD).mapColor(color).strength(2.5f).sounds(soundGroup)))
				.drops(BlockLootTableGenerator::nameableContainerDrops);
	}

	public static BlockContainer MakeLectern(MapColor color) { return MakeLectern(color, BlockSoundGroup.WOOD); }
	public static BlockContainer MakeLectern(MapColor color, BlockSoundGroup soundGroup) {
		return new BlockContainer(new ModLecternBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(color).strength(2.5f).sounds(soundGroup)));
	}

	public static FlowerContainer MakeFlower(StatusEffect effect, int effectDuration) { return MakeFlower(effect, effectDuration, ItemSettings()); }
	public static FlowerContainer MakeFlower(StatusEffect effect, int effectDuration, Item.Settings settings) {
		return new FlowerContainer(effect, effectDuration, settings).flammable(60, 100).compostable(0.65f).dropSelf();
	}
	public static TallBlockContainer MakeTallFlower() {
		return new TallBlockContainer(new TallFlowerBlock(FlowerContainer.TallSettings())).flammable(60, 100).compostable(0.65f).dropSelf();
	}
	public static TallBlockContainer MakeCuttableFlower(Supplier<FlowerBlock> shortBlock) {
		return MakeCuttableFlower(shortBlock, null);
	}
	public static TallBlockContainer MakeCuttableFlower(Supplier<FlowerBlock> shortBlock, Function<World, ItemStack> alsoDrop) {
		return MakeCuttableFlower(FlowerContainer.TallSettings(), shortBlock, alsoDrop);
	}
	public static TallBlockContainer MakeCuttableFlower(Block.Settings settings, Supplier<FlowerBlock> shortBlock, Function<World, ItemStack> alsoDrop) {
		return new TallBlockContainer(new CuttableFlowerBlock(settings, shortBlock, alsoDrop)).flammable(60, 100).compostable(0.65f).dropSelf();
	}
}
