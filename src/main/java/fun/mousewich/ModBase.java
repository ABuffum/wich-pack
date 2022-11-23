package fun.mousewich;

import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import fun.mousewich.block.*;
import fun.mousewich.block.basic.*;
import fun.mousewich.block.mangrove.MangroveRootsBlock;
import fun.mousewich.block.mangrove.PropaguleBlock;
import fun.mousewich.block.sculk.SculkTurfBlock;
import fun.mousewich.block.torch.UnlitTorchBlock;
import fun.mousewich.block.torch.UnlitWallTorchBlock;
import fun.mousewich.dispenser.HorseArmorDispenserBehavior;
import fun.mousewich.entity.ModBoatEntity;
import fun.mousewich.container.*;
import fun.mousewich.gen.features.MangroveTreeFeature;
import fun.mousewich.gen.features.MangroveTreeFeatureConfig;
import fun.mousewich.item.DiscFragmentItem;
import fun.mousewich.item.basic.ModHorseArmorItem;
import fun.mousewich.item.basic.ModMusicDiscItem;
import fun.mousewich.item.basic.tool.*;
import fun.mousewich.item.echo.*;
import fun.mousewich.item.goat.GoatHornItem;
import fun.mousewich.item.goat.GoatHornSalveItem;
import fun.mousewich.material.BaseMaterial;
import fun.mousewich.material.ModArmorMaterials;
import fun.mousewich.material.ModToolMaterials;
import fun.mousewich.sounds.ModBlockSoundGroups;
import fun.mousewich.sounds.ModSoundEvents;
import fun.mousewich.util.Util;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.*;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static fun.mousewich.ModRegistry.*;

public class ModBase implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Wich Pack");

	public static final String NAMESPACE = "wich";
	public static Identifier ID(String path) {
		if (path.startsWith("minecraft:")) return new Identifier(path);
		else return new Identifier(NAMESPACE, path);
	}

	private static ItemStack GetItemGroupIcon() { return new ItemStack(ECHO_SHARD); }
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(ID("wichpack"), ModBase::GetItemGroupIcon);
	public static Item.Settings ItemSettings() { return new Item.Settings().group(ITEM_GROUP); }

	public static final DyeColor[] COLORS = DyeColor.values();
	public interface DyeMapFunc<T> { public T op(DyeColor c); }
	public static <T> Map<DyeColor, T> MapDyeColor(DyeMapFunc<T> op) {
		Map<DyeColor, T> output = new HashMap<>();
		for(DyeColor c : COLORS) output.put(c, op.op(c));
		return output;
	}

	//Boats
	public static final EntityType<ModBoatEntity> BOAT_ENTITY = Register("mod_boat", FabricEntityTypeBuilder.<ModBoatEntity>create(SpawnGroup.MISC, ModBoatEntity::new).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).trackRangeBlocks(10).build());

	//Light Sources
	public static final DefaultParticleType UNDERWATER_TORCH_GLOW = Register("glow_flame", FabricParticleTypes.simple(false));
	public static final TorchContainer UNDERWATER_TORCH = Register("underwater_torch", "underwater_wall_torch", TorchContainer.Waterloggable(BaseMaterial.TorchSettings(14, BlockSoundGroup.WOOD), UNDERWATER_TORCH_GLOW, ItemSettings())).dropSelf();

	public static final UnlitTorchContainer UNLIT_TORCH = Register("unlit_torch", "unlit_wall_torch", new UnlitTorchContainer(new UnlitTorchBlock((TorchBlock)Blocks.TORCH), new UnlitWallTorchBlock((WallTorchBlock)Blocks.WALL_TORCH))).drops(Items.TORCH);
	public static final UnlitTorchContainer UNLIT_SOUL_TORCH = Register("unlit_soul_torch", "unlit_wall_soul_torch", new UnlitTorchContainer(new UnlitTorchBlock((TorchBlock)Blocks.SOUL_TORCH), new UnlitWallTorchBlock((WallTorchBlock)Blocks.SOUL_WALL_TORCH))).drops(Items.SOUL_TORCH);

	public static final Block UNLIT_LANTERN = Register("unlit_lantern", new UnlitLanternBlock(() -> Items.LANTERN, UnlitLanternBlock.BlockSettings()));
	public static final Block UNLIT_SOUL_LANTERN = Register("unlit_soul_lantern", new UnlitLanternBlock(() -> Items.SOUL_LANTERN, UnlitLanternBlock.BlockSettings()));
	
	public static final DefaultParticleType ENDER_FIRE_FLAME_PARTICLE = Register("ender_fire_flame", FabricParticleTypes.simple(false));
	public static final TorchContainer ENDER_TORCH = Register("ender_torch", "ender_wall_torch", BaseMaterial.MakeTorch(12, BlockSoundGroup.WOOD, ENDER_FIRE_FLAME_PARTICLE, ItemSettings())).dropSelf();
	public static final BlockContainer ENDER_LANTERN = Register("ender_lantern", BaseMaterial.MakeLantern(13, ItemSettings())).dropSelf();
	public static final BlockContainer ENDER_CAMPFIRE = Register("ender_campfire", BaseMaterial.MakeCampfire(13, 3, MapColor.SPRUCE_BROWN, BlockSoundGroup.WOOD, false, ItemSettings()));

	//Soul Candles
	public static final DefaultParticleType SMALL_SOUL_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final BlockContainer SOUL_CANDLE = Register("soul_candle", new BlockContainer(new CandleBlock(Block.Settings.of(Material.DECORATION, MapColor.BROWN).nonOpaque().strength(0.1F).sounds(BlockSoundGroup.CANDLE).luminance((state) -> (int)(state.get(CandleBlock.LIT) ? 2.5 * state.get(CandleBlock.CANDLES) : 0))), ItemSettings())); //TODO: Loot Table, Models
	//Ender Candles
	public static final DefaultParticleType SMALL_ENDER_FLAME_PARTICLE = FabricParticleTypes.simple(false);
	public static final BlockContainer ENDER_CANDLE = Register("ender_candle", new BlockContainer(new CandleBlock(Block.Settings.of(Material.DECORATION, MapColor.PALE_YELLOW).nonOpaque().strength(0.1F).sounds(BlockSoundGroup.CANDLE).luminance((state) -> (int)(state.get(CandleBlock.LIT) ? 2.75 * state.get(CandleBlock.CANDLES) : 0))), ItemSettings())); //TODO: Loot Table, Models

	//Extended Amethyst
	//<editor-fold desc="Extended Amethyst">
	public static final BlockContainer AMETHYST_SLAB = Register("amethyst_slab", new BlockContainer(new ModSlabBlock(Blocks.AMETHYST_BLOCK), ItemSettings())).dropSlabs();
	public static final BlockContainer AMETHYST_STAIRS = Register("amethyst_stairs", new BlockContainer(new ModStairsBlock(Blocks.AMETHYST_BLOCK), ItemSettings())).dropSelf();
	public static final BlockContainer AMETHYST_WALL = Register("amethyst_wall", new BlockContainer(new ModSlabBlock(Blocks.AMETHYST_BLOCK), ItemSettings())).dropSelf();
	public static final BlockContainer AMETHYST_CRYSTAL_BLOCK = Register("amethyst_crystal_block", new BlockContainer(new Block(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f, 1.5f).requiresTool().luminance(Util.LUMINANCE_5)), ItemSettings())).dropSelf();
	public static final BlockContainer AMETHYST_CRYSTAL_SLAB = Register("amethyst_crystal_slab", new BlockContainer(new ModSlabBlock(AMETHYST_CRYSTAL_BLOCK.getBlock()), ItemSettings())).dropSlabs();
	public static final BlockContainer AMETHYST_CRYSTAL_STAIRS = Register("amethyst_crystal_stairs", new BlockContainer(new ModStairsBlock(AMETHYST_CRYSTAL_BLOCK.getBlock()), ItemSettings())).dropSelf();
	public static final BlockContainer AMETHYST_CRYSTAL_WALL = Register("amethyst_crystal_wall", new BlockContainer(new ModSlabBlock(AMETHYST_CRYSTAL_BLOCK.getBlock()), ItemSettings())).dropSelf();
	public static final BlockContainer AMETHYST_BRICKS = Register("amethyst_bricks", new BlockContainer(new Block(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f, 1.5f).requiresTool()), ItemSettings())).dropSelf();
	public static final BlockContainer AMETHYST_BRICK_SLAB = Register("amethyst_brick_slab", new BlockContainer(new ModSlabBlock(AMETHYST_BRICKS.getBlock()), ItemSettings())).dropSlabs();
	public static final BlockContainer AMETHYST_BRICK_STAIRS = Register("amethyst_brick_stairs", new BlockContainer(new ModStairsBlock(AMETHYST_BRICKS.getBlock()), ItemSettings())).dropSelf();
	public static final BlockContainer AMETHYST_BRICK_WALL = Register("amethyst_brick_wall", new BlockContainer(new ModSlabBlock(AMETHYST_BRICKS.getBlock()), ItemSettings())).dropSelf();
	public static final AxeItem AMETHYST_AXE = (AxeItem)Register("amethyst_axe", new ModAxeItem(ModToolMaterials.AMETHYST, 5, -3, ItemSettings()));
	public static final HoeItem AMETHYST_HOE = (HoeItem)Register("amethyst_hoe", new ModHoeItem(ModToolMaterials.AMETHYST, -3, 0, ItemSettings()));
	public static final PickaxeItem AMETHYST_PICKAXE = (PickaxeItem)Register("amethyst_pickaxe", new ModPickaxeItem(ModToolMaterials.AMETHYST, 1, -2.8F, ItemSettings()));
	public static final ShovelItem AMETHYST_SHOVEL = (ShovelItem)Register("amethyst_shovel", new ModShovelItem(ModToolMaterials.AMETHYST, 1.5F, -3, ItemSettings()));
	public static final SwordItem AMETHYST_SWORD = (SwordItem)Register("amethyst_sword", new ModSwordItem(ModToolMaterials.AMETHYST, 3, -2.4F, ItemSettings()));
	public static final KnifeItem AMETHYST_KNIFE = (KnifeItem)Register("amethyst_knife", new ModKnifeItem(ModToolMaterials.AMETHYST, ItemSettings()));
	public static final ArmorItem AMETHYST_HELMET = (ArmorItem)Register("amethyst_helmet", new ArmorItem(ModArmorMaterials.AMETHYST, EquipmentSlot.HEAD, ItemSettings()));
	public static final ArmorItem AMETHYST_CHESTPLATE = (ArmorItem)Register("amethyst_chestplate", new ArmorItem(ModArmorMaterials.AMETHYST, EquipmentSlot.CHEST, ItemSettings()));
	public static final ArmorItem AMETHYST_LEGGINGS = (ArmorItem)Register("amethyst_leggings", new ArmorItem(ModArmorMaterials.AMETHYST, EquipmentSlot.LEGS, ItemSettings()));
	public static final ArmorItem AMETHYST_BOOTS = (ArmorItem)Register("amethyst_boots", new ArmorItem(ModArmorMaterials.AMETHYST, EquipmentSlot.FEET, ItemSettings()));
	public static final HorseArmorItem AMETHYST_HORSE_ARMOR = (HorseArmorItem)Register("amethyst_horse_armor", new ModHorseArmorItem(10, "amethyst", ItemSettings().maxCount(1)));
	public static final BlockContainer TINTED_GLASS_PANE = Register("tinted_glass_pane", new BlockContainer(new TintedGlassPaneBlock(Block.Settings.of(Material.GLASS).mapColor(MapColor.GRAY).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque()), ItemSettings())).dropSelf();
	public static final ArmorItem TINTED_GOGGLES = (ArmorItem)Register("tinted_goggles", new ArmorItem(ModArmorMaterials.TINTED_GOGGLES, EquipmentSlot.HEAD, ItemSettings()));
	//</editor-fold>
	//Extended Emerald
	//<editor-fold desc="Extended Emerald">
	public static final BlockContainer EMERALD_BRICKS = Register("emerald_bricks", new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.EMERALD_BLOCK)), ItemSettings())).dropSelf();
	public static final BlockContainer EMERALD_BRICK_SLAB = Register("emerald_brick_slab", new BlockContainer(new ModSlabBlock(EMERALD_BRICKS.getBlock()), ItemSettings())).dropSlabs();
	public static final BlockContainer EMERALD_BRICK_STAIRS = Register("emerald_brick_stairs", new BlockContainer(new ModStairsBlock(EMERALD_BRICKS.getBlock()), ItemSettings())).dropSelf();
	public static final BlockContainer EMERALD_BRICK_WALL = Register("emerald_brick_wall", new BlockContainer(new ModSlabBlock(EMERALD_BRICKS.getBlock()), ItemSettings())).dropSelf();
	public static final BlockContainer CUT_EMERALD = Register("cut_emerald", new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.EMERALD_BLOCK)), ItemSettings())).dropSelf();
	public static final BlockContainer CUT_EMERALD_SLAB = Register("cut_emerald_slab", new BlockContainer(new ModSlabBlock(CUT_EMERALD.getBlock()), ItemSettings())).dropSlabs();
	public static final BlockContainer CUT_EMERALD_STAIRS = Register("cut_emerald_stairs", new BlockContainer(new ModStairsBlock(CUT_EMERALD.getBlock()), ItemSettings())).dropSelf();
	public static final BlockContainer CUT_EMERALD_WALL = Register("cut_emerald_wall", new BlockContainer(new ModSlabBlock(CUT_EMERALD.getBlock()), ItemSettings())).dropSelf();
	public static final AxeItem EMERALD_AXE = (AxeItem)Register("emerald_axe", new ModAxeItem(ModToolMaterials.EMERALD, 5, -3, ItemSettings()));
	public static final HoeItem EMERALD_HOE = (HoeItem)Register("emerald_hoe", new ModHoeItem(ModToolMaterials.EMERALD, -2, 0, ItemSettings()));
	public static final PickaxeItem EMERALD_PICKAXE = (PickaxeItem)Register("emerald_pickaxe", new ModPickaxeItem(ModToolMaterials.EMERALD, 1, -2.8F, ItemSettings()));
	public static final ShovelItem EMERALD_SHOVEL = (ShovelItem)Register("emerald_shovel", new ModShovelItem(ModToolMaterials.EMERALD, 1.5F, -3, ItemSettings()));
	public static final SwordItem EMERALD_SWORD = (SwordItem)Register("emerald_sword", new ModSwordItem(ModToolMaterials.EMERALD, 3, -2.4F, ItemSettings()));
	public static final KnifeItem EMERALD_KNIFE = (KnifeItem)Register("emerald_knife", new ModKnifeItem(ModToolMaterials.EMERALD, ItemSettings()));
	public static final ArmorItem EMERALD_HELMET = (ArmorItem)Register("emerald_helmet", new ArmorItem(ModArmorMaterials.EMERALD, EquipmentSlot.HEAD, ItemSettings()));
	public static final ArmorItem EMERALD_CHESTPLATE = (ArmorItem)Register("emerald_chestplate", new ArmorItem(ModArmorMaterials.EMERALD, EquipmentSlot.CHEST, ItemSettings()));
	public static final ArmorItem EMERALD_LEGGINGS = (ArmorItem)Register("emerald_leggings", new ArmorItem(ModArmorMaterials.EMERALD, EquipmentSlot.LEGS, ItemSettings()));
	public static final ArmorItem EMERALD_BOOTS = (ArmorItem)Register("emerald_boots", new ArmorItem(ModArmorMaterials.EMERALD, EquipmentSlot.FEET, ItemSettings()));
	public static final HorseArmorItem EMERALD_HORSE_ARMOR = (HorseArmorItem)Register("emerald_horse_armor", new ModHorseArmorItem(9, "amethyst", ItemSettings().maxCount(1)));
	//</editor-fold>
	//Extended Diamond
	//<editor-fold desc="Extended Diamond">
	public static final BlockContainer DIAMOND_SLAB = Register("diamond_slab", new BlockContainer(new ModSlabBlock(Blocks.DIAMOND_BLOCK), ItemSettings())).dropSlabs();
	public static final BlockContainer DIAMOND_STAIRS = Register("diamond_stairs", new BlockContainer(new ModStairsBlock(Blocks.DIAMOND_BLOCK), ItemSettings())).dropSelf();
	public static final BlockContainer DIAMOND_WALL = Register("diamond_wall", new BlockContainer(new ModSlabBlock(Blocks.DIAMOND_BLOCK), ItemSettings())).dropSelf();
	public static final BlockContainer DIAMOND_BRICKS = Register("diamond_bricks", new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_BLOCK)), ItemSettings())).dropSelf();
	public static final BlockContainer DIAMOND_BRICK_SLAB = Register("diamond_brick_slab", new BlockContainer(new ModSlabBlock(DIAMOND_BRICKS.getBlock()), ItemSettings())).dropSlabs();
	public static final BlockContainer DIAMOND_BRICK_STAIRS = Register("diamond_brick_stairs", new BlockContainer(new ModStairsBlock(DIAMOND_BRICKS.getBlock()), ItemSettings())).dropSelf();
	public static final BlockContainer DIAMOND_BRICK_WALL = Register("diamond_brick_wall", new BlockContainer(new ModSlabBlock(DIAMOND_BRICKS.getBlock()), ItemSettings())).dropSelf();
	//</editor-fold>
	//Extended Quartz
	//Misc Stuff
	public static final BlockContainer CHARCOAL_BLOCK = Register("charcoal_block", new BlockContainer(new Block(Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(5.0F, 6.0F)), ItemSettings()).flammable(5, 5).fuel(16000)).dropSelf();
	public static final BlockContainer BLUE_SHROOMLIGHT = Register("blue_shroomlight", new BlockContainer(new Block(Block.Settings.of(Material.SOLID_ORGANIC, MapColor.CYAN).strength(1.0f).sounds(BlockSoundGroup.SHROOMLIGHT).luminance(Util.LUMINANCE_15)), ItemSettings())).dropSelf();
	public static final BlockContainer HEDGE_BLOCK = Register("hedge_block", new BlockContainer(new Block(Block.Settings.of(Material.LEAVES).strength(0.2F).sounds(BlockSoundGroup.GRASS).nonOpaque()), ItemSettings()).flammable(5, 20)).dropSelf();
	public static final BlockContainer SEED_BLOCK = Register("seed_block", new BlockContainer(new Block(Block.Settings.of(Material.SOLID_ORGANIC, MapColor.DARK_GREEN).strength(0.8F).sounds(BlockSoundGroup.GRASS)), ItemSettings()).compostable(1f)).dropSelf();
	public static final BlockContainer WAX_BLOCK = Register("wax_block", new BlockContainer(new Block(Block.Settings.copy(Blocks.HONEYCOMB_BLOCK)), ItemSettings()));
	//Minecraft Earth
	public static final Item HORN = Register("horn", new Item(ItemSettings()));

	//Goat
	public static final Item GOAT_HORN = Register("minecraft:goat_horn", new GoatHornItem(ItemSettings().maxCount(1).group(ItemGroup.MISC)));
	//Extended Goat
	//<editor-fold desc="Extended Goat">
	public static final Item CHEVON = Register("chevon", new Item(ItemSettings().food(FoodComponents.MUTTON)));
	public static final Item COOKED_CHEVON = Register("cooked_chevon", new Item(ItemSettings().food(FoodComponents.COOKED_MUTTON)));
	public static final Map<DyeColor, BlockContainer> FLEECE = MapDyeColor((color) -> {
		Block block = new Block(Block.Settings.of(Material.WOOL, color.getMapColor()).strength(0.8F).sounds(BlockSoundGroup.WOOL));
		return Register(color.getName() + "_fleece", new BlockContainer(block, ItemSettings()).flammable(30, 60).fuel(100).dropSelf());
	});
	public static final Map<DyeColor, BlockContainer> FLEECE_CARPETS = MapDyeColor((color) -> {
		Block block = new ModDyedCarpetBlock(color, Block.Settings.of(Material.CARPET, color.getMapColor()).strength(0.1F).sounds(BlockSoundGroup.WOOL));
		return Register(color.getName() + "_fleece_carpet", new BlockContainer(block, ItemSettings()).flammable(60, 20).fuel(67).dispenser(new HorseArmorDispenserBehavior()::dispenseSilently).dropSelf());
	});
	public static final BlockContainer RAINBOW_FLEECE = Register("rainbow_fleece", new BlockContainer(new ModFacingBlock(Blocks.WHITE_WOOL), ItemSettings()).flammable(30, 60).fuel(100).dropSelf());
	public static final BlockContainer RAINBOW_FLEECE_CARPET = Register("rainbow_fleece_carpet", new BlockContainer(new HorziontalFacingCarpetBlock(Block.Settings.copy(Blocks.WHITE_CARPET)), ItemSettings()).flammable(60, 20).fuel(67).dropSelf());
	public static final ArmorItem FLEECE_HELMET = (ArmorItem)Register("fleece_helmet", new DyeableArmorItem(ModArmorMaterials.FLEECE, EquipmentSlot.HEAD, ItemSettings()));
	public static final ArmorItem FLEECE_CHESTPLATE = (ArmorItem)Register("fleece_chestplate", new DyeableArmorItem(ModArmorMaterials.FLEECE, EquipmentSlot.CHEST, ItemSettings()));
	public static final ArmorItem FLEECE_LEGGINGS = (ArmorItem)Register("fleece_leggings", new DyeableArmorItem(ModArmorMaterials.FLEECE, EquipmentSlot.LEGS, ItemSettings()));
	public static final ArmorItem FLEECE_BOOTS = (ArmorItem)Register("fleece_boots", new DyeableArmorItem(ModArmorMaterials.FLEECE, EquipmentSlot.FEET, ItemSettings()));
	public static final HorseArmorItem FLEECE_HORSE_ARMOR = (HorseArmorItem)Register("fleece_horse_armor", HorseArmorDispenserBehavior.Dispensible(new DyeableHorseArmorItem(3, "fleece", ItemSettings().maxCount(1))));
	public static final Item GOAT_HORN_SALVE = Register("got_horn_salve", new GoatHornSalveItem(ItemSettings()));
	//</editor-fold>
	//Echo
	public static final Item ECHO_SHARD = Register("minecraft:echo_shard", new Item(new Item.Settings().group(ItemGroup.MISC)));
	//Extended Echo
	//<editor-fold desc="Extended Echo">
	public static final BlockContainer ECHO_BLOCK = Register("echo_block", new BlockContainer(new Block(Block.Settings.of(Material.SCULK).strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool()), ItemSettings())).dropSelf();
	public static final BlockContainer ECHO_SLAB = Register("echo_slab", new BlockContainer(new ModSlabBlock(ECHO_BLOCK.getBlock()), ItemSettings())).dropSlabs();
	public static final BlockContainer ECHO_STAIRS = Register("echo_stairs", new BlockContainer(new ModStairsBlock(ECHO_BLOCK.getBlock()), ItemSettings())).dropSelf();
	public static final BlockContainer ECHO_WALL = Register("echo_wall", new BlockContainer(new ModWallBlock(ECHO_BLOCK.getBlock()), ItemSettings())).dropSelf();
	public static final BlockContainer ECHO_CRYSTAL_BLOCK = Register("echo_crystal_block", new BlockContainer(new Block(FabricBlockSettings.of(Material.SCULK).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f, 1.5f).requiresTool().luminance(Util.LUMINANCE_5)), ItemSettings())).dropSelf();
	public static final BlockContainer ECHO_CRYSTAL_SLAB = Register("echo_crystal_slab", new BlockContainer(new ModSlabBlock(ECHO_CRYSTAL_BLOCK.getBlock()), ItemSettings())).dropSlabs();
	public static final BlockContainer ECHO_CRYSTAL_STAIRS = Register("echo_crystal_stairs", new BlockContainer(new ModStairsBlock(ECHO_CRYSTAL_BLOCK.getBlock()), ItemSettings())).dropSelf();
	public static final BlockContainer ECHO_CRYSTAL_WALL = Register("echo_crystal_wall", new BlockContainer(new ModSlabBlock(ECHO_CRYSTAL_BLOCK.getBlock()), ItemSettings())).dropSelf();
	public static final BlockContainer BUDDING_ECHO = Register("budding_echo", new BlockContainer(new BuddingEchoBlock(Block.Settings.copy(ECHO_BLOCK.getBlock()).ticksRandomly()), ItemSettings())).dropNothing();
	public static final BlockContainer ECHO_CLUSTER = Register("echo_cluster", new BlockContainer(new EchoClusterBlock(7, 3, Block.Settings.of(Material.SCULK).nonOpaque().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5F).luminance(Util.LUMINANCE_3)), ItemSettings()));
	public static final BlockContainer LARGE_ECHO_BUD = Register("large_echo_bud", new BlockContainer(new EchoClusterBlock(5, 3, Block.Settings.copy(ECHO_CLUSTER.getBlock()).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD).luminance(Util.LUMINANCE_2)), ItemSettings())).requireSilkTouch();
	public static final BlockContainer MEDIUM_ECHO_BUD = Register("medium_echo_bud", new BlockContainer(new EchoClusterBlock(4, 3, Block.Settings.copy(ECHO_CLUSTER.getBlock()).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD).luminance(Util.LUMINANCE_1)), ItemSettings())).requireSilkTouch();
	public static final BlockContainer SMALL_ECHO_BUD = Register("small_echo_bud", new BlockContainer(new EchoClusterBlock(3, 4, Block.Settings.copy(ECHO_CLUSTER.getBlock()).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD)), ItemSettings())).requireSilkTouch();
	public static final AxeItem ECHO_AXE = (AxeItem)Register("echo_axe", new EchoAxeItem(ModToolMaterials.ECHO, 5, -3, 1, ItemSettings()));
	public static final HoeItem ECHO_HOE = (HoeItem)Register("echo_hoe", new EchoHoeItem(ModToolMaterials.ECHO, -4, 0, 1, ItemSettings()));
	public static final PickaxeItem ECHO_PICKAXE = (PickaxeItem)Register("echo_pickaxe", new EchoPickaxeItem(ModToolMaterials.ECHO, 1, -2.8F, 1, ItemSettings()));
	public static final ShovelItem ECHO_SHOVEL = (ShovelItem)Register("echo_shovel", new EchoShovelItem(ModToolMaterials.ECHO, 1.5F, -3, 1, ItemSettings()));
	public static final SwordItem ECHO_SWORD = (SwordItem)Register("echo_sword", new EchoSwordItem(ModToolMaterials.ECHO, 3, -2.4F, 1, ItemSettings()));
	public static final KnifeItem ECHO_KNIFE = (KnifeItem)Register("echo_knife", new EchoKnifeItem(ModToolMaterials.ECHO, 1, ItemSettings()));
	//</editor-fold>
	//Music Discs
	//<editor-fold desc="Music Discs">
	public static final Item MUSIC_DISC_5 = Register("minecraft:music_disc_5", new ModMusicDiscItem(15, ModSoundEvents.MUSIC_DISC_5, ItemSettings().maxCount(1).rarity(Rarity.RARE).group(ItemGroup.MISC)));
	public static final Item DISC_FRAGMENT_5 = Register("minecraft:disc_fragment_5", new DiscFragmentItem(ItemSettings().group(ItemGroup.MISC)));
	//</editor-fold>
	//Mud
	//<editor-fold desc="Mud">
	public static final BlockContainer MUD = Register("minecraft:mud", new BlockContainer(new MudBlock(Block.Settings.copy(Blocks.DIRT).mapColor(MapColor.TERRACOTTA_CYAN).allowsSpawning(BaseMaterial::always).solidBlock(BaseMaterial::always).blockVision(BaseMaterial::always).suffocates(BaseMaterial::always).sounds(ModBlockSoundGroups.MUD)), ItemSettings().group(ItemGroup.BUILDING_BLOCKS))).dropSelf();
	public static final BlockContainer PACKED_MUD = Register("minecraft:packed_mud", new BlockContainer(new Block(Block.Settings.copy(Blocks.DIRT).strength(1.0f, 3.0f).sounds(ModBlockSoundGroups.PACKED_MUD)), ItemSettings().group(ItemGroup.BUILDING_BLOCKS))).dropSelf();
	public static final BlockContainer MUD_BRICKS = Register("minecraft:mud_bricks", new BlockContainer(new Block(Block.Settings.of(Material.STONE, MapColor.TERRACOTTA_LIGHT_GRAY).requiresTool().strength(1.5f, 3.0f).sounds(ModBlockSoundGroups.MUD_BRICKS)), ItemSettings().group(ItemGroup.BUILDING_BLOCKS))); //Custom Model
	public static final BlockContainer MUD_BRICK_SLAB = Register("minecraft:mud_brick_slab", new BlockContainer(new ModSlabBlock(MUD_BRICKS.getBlock()), ItemSettings().group(ItemGroup.BUILDING_BLOCKS))).dropSlabs();
	public static final BlockContainer MUD_BRICK_STAIRS = Register("minecraft:mud_brick_stairs", new BlockContainer(new ModStairsBlock(MUD_BRICKS.getBlock()), ItemSettings().group(ItemGroup.BUILDING_BLOCKS))).dropSelf();
	public static final BlockContainer MUD_BRICK_WALL = Register("minecraft:mud_brick_wall", new BlockContainer(new ModWallBlock(MUD_BRICKS.getBlock()), ItemSettings().group(ItemGroup.BUILDING_BLOCKS))).dropSelf();
	//</editor-fold>
	//Mangrove
	//<editor-fold desc="Mangrove">
	public static final BlockContainer MANGROVE_LOG = Register("minecraft:mangrove_log", new BlockContainer(new PillarBlock(Block.Settings.of(Material.WOOD, MapColor.RED).strength(2.0F).sounds(BlockSoundGroup.WOOD)), ItemSettings().group(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300)).dropSelf();
	public static final BlockContainer STRIPPED_MANGROVE_LOG = Register("minecraft:stripped_mangrove_log", new BlockContainer(new PillarBlock(Block.Settings.copy(MANGROVE_LOG.getBlock())), ItemSettings().group(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300)).dropSelf();
	public static final BlockContainer MANGROVE_WOOD = Register("minecraft:mangrove_wood", new BlockContainer(new PillarBlock(Block.Settings.copy(MANGROVE_LOG.getBlock())), ItemSettings().group(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300)).dropSelf();
	public static final BlockContainer STRIPPED_MANGROVE_WOOD = Register("minecraft:stripped_mangrove_wood", new BlockContainer(new PillarBlock(Block.Settings.copy(MANGROVE_LOG.getBlock())), ItemSettings().group(ItemGroup.BUILDING_BLOCKS)).flammable(5, 5).fuel(300)).dropSelf();
	public static final BlockContainer MANGROVE_LEAVES = Register("minecraft:mangrove_leaves", new BlockContainer(new ModLeavesBlock(Block.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(BaseMaterial::canSpawnOnLeaves).suffocates(BaseMaterial::never).blockVision(BaseMaterial::never)), ItemSettings().group(ItemGroup.DECORATIONS)).flammable(30, 60).compostable(0.3f));
	public static final BlockContainer MANGROVE_PLANKS = Register("minecraft:mangrove_planks", new BlockContainer(new Block(Block.Settings.of(Material.WOOD, MapColor.RED).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), ItemSettings().group(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20)).dropSelf();
	public static final BlockContainer MANGROVE_SLAB = Register("minecraft:mangrove_slab", new BlockContainer(new SlabBlock(Block.Settings.copy(MANGROVE_PLANKS.getBlock())), ItemSettings().group(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(150)).dropSlabs();
	public static final BlockContainer MANGROVE_STAIRS = Register("minecraft:mangrove_stairs", new BlockContainer(new ModStairsBlock(MANGROVE_PLANKS.getBlock()), ItemSettings().group(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300)).dropSelf();
	public static final BlockContainer MANGROVE_FENCE = Register("minecraft:mangrove_fence", new BlockContainer(new FenceBlock(Block.Settings.copy(MANGROVE_PLANKS.getBlock())), ItemSettings().group(ItemGroup.DECORATIONS)).flammable(5, 20).fuel(300)).dropSelf();
	public static final BlockContainer MANGROVE_FENCE_GATE = Register("minecraft:mangrove_fence_gate", new BlockContainer(new FenceGateBlock(Block.Settings.copy(MANGROVE_PLANKS.getBlock())), ItemSettings().group(ItemGroup.REDSTONE)).flammable(5, 20).fuel(300)).dropSelf();
	public static final BlockContainer MANGROVE_DOOR = Register("minecraft:mangrove_door", new BlockContainer(new ModDoorBlock(Block.Settings.of(Material.WOOD, MANGROVE_PLANKS.getBlock().getDefaultMapColor()).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque(), SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE), ItemSettings().group(ItemGroup.REDSTONE)).fuel(200));
	public static final BlockContainer MANGROVE_TRAPDOOR = Register("minecraft:mangrove_trapdoor", new BlockContainer(new ModTrapdoorBlock(Block.Settings.of(Material.WOOD, MapColor.RED).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning(BaseMaterial::noSpawning), SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE), ItemSettings().group(ItemGroup.REDSTONE)).fuel(300)).dropSelf();
	public static final BlockContainer MANGROVE_PRESSURE_PLATE = Register("minecraft:mangrove_pressure_plate", new BlockContainer(ModPressurePlateBlock.Wooden(PressurePlateBlock.ActivationRule.EVERYTHING, Block.Settings.of(Material.WOOD, MANGROVE_PLANKS.getBlock().getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)), ItemSettings().group(ItemGroup.REDSTONE)).fuel(300)).dropSelf();
	public static final BlockContainer MANGROVE_BUTTON = Register("minecraft:mangrove_button", new BlockContainer(new ModWoodenButtonBlock(Block.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)), ItemSettings().group(ItemGroup.REDSTONE)).fuel(100)).dropSelf();
	public static final SignContainer MANGROVE_SIGN = Register("minecraft:mangrove_sign", "minecraft:mangrove_wall_sign", new SignContainer("mangrove", Material.WOOD, BlockSoundGroup.WOOD, SignContainer.SignSettings().group(ItemGroup.DECORATIONS)).fuel(200)).dropSelf();
	public static final BoatContainer MANGROVE_BOAT = Register("minecraft:mangrove_boat", new BoatContainer("minecraft:mangrove", MANGROVE_PLANKS.getBlock(), false, ItemSettings().group(ItemGroup.TRANSPORTATION)).fuel(1200));
	public static final PottedBlockContainer MANGROVE_PROPAGULE = Register("minecraft:mangrove_propagule", "minecraft:potted_mangrove_propagule", new PottedBlockContainer(new PropaguleBlock(Block.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)), ItemSettings().group(ItemGroup.DECORATIONS)));
	public static final BlockContainer MANGROVE_ROOTS = Register("minecraft:mangrove_roots", new BlockContainer(new MangroveRootsBlock(Block.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(0.7f).ticksRandomly().sounds(ModBlockSoundGroups.MANGROVE_ROOTS).nonOpaque().suffocates(BaseMaterial::never).blockVision(BaseMaterial::never).nonOpaque()), ItemSettings().group(ItemGroup.BUILDING_BLOCKS)).flammable(5, 20).fuel(300)).dropSelf();
	public static final BlockContainer MUDDY_MANGROVE_ROOTS = Register("minecraft:muddy_mangrove_roots", new BlockContainer(new PillarBlock(Block.Settings.of(Material.SOIL, MapColor.SPRUCE_BROWN).strength(0.7f).sounds(ModBlockSoundGroups.MUDDY_MANGROVE_ROOTS)), ItemSettings().group(ItemGroup.BUILDING_BLOCKS))).dropSelf();
	public static final Feature<MangroveTreeFeatureConfig> MANGROVE_TREE_FEATURE = Registry.register(Registry.FEATURE, ID("minecraft:mangrove_tree"),
			new MangroveTreeFeature(MangroveTreeFeatureConfig.CODEC));
	public static final ConfiguredFeature<MangroveTreeFeatureConfig, ?> MANGROVE_FEATURE = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("minecraft:mangrove"),
			new ConfiguredFeature<>(MANGROVE_TREE_FEATURE, new MangroveTreeFeatureConfig(false)));
	public static final ConfiguredFeature<MangroveTreeFeatureConfig, ?> TALL_MANGROVE_FEATURE = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("minecraft:tall_mangrove"),
			new ConfiguredFeature<>(MANGROVE_TREE_FEATURE, new MangroveTreeFeatureConfig(true)));
	//</editor-fold>
	//Extended Mangrove
	//<editor-fold desc="Extended Mangrove">

	//</editor-fold>
	//Frogs
	//<editor-fold desc="Frogs">
	public static final Material FROGLIGHT_MATERIAL = new Material.Builder(MapColor.CLEAR).build();
	public static final BlockContainer OCHRE_FROGLIGHT = Register("minecraft:ochre_froglight", new BlockContainer(new PillarBlock(Block.Settings.of(FROGLIGHT_MATERIAL, MapColor.PALE_YELLOW).strength(0.3f).luminance(Util.LUMINANCE_15).sounds(ModBlockSoundGroups.FROGLIGHT)), ItemSettings().group(ItemGroup.DECORATIONS)).dropSelf());
	public static final BlockContainer VERDANT_FROGLIGHT = Register("minecraft:verdant_froglight", new BlockContainer(new PillarBlock(Block.Settings.of(FROGLIGHT_MATERIAL, MapColor.LICHEN_GREEN).strength(0.3f).luminance(Util.LUMINANCE_15).sounds(ModBlockSoundGroups.FROGLIGHT)), ItemSettings().group(ItemGroup.DECORATIONS)).dropSelf());
	public static final BlockContainer PEARLESCENT_FROGLIGHT = Register("minecraft:pearlescent_froglight", new BlockContainer(new PillarBlock(Block.Settings.of(FROGLIGHT_MATERIAL, MapColor.PINK).strength(0.3f).luminance(Util.LUMINANCE_15).sounds(ModBlockSoundGroups.FROGLIGHT)), ItemSettings().group(ItemGroup.DECORATIONS)).dropSelf());
	//</editor-fold>
	//Sculk & Deep Dark
	//<editor-fold desc="Sculk & Deep Dark">

	//</editor-fold>
	//Extended Sculk
	//<editor-fold desc="Extended Sculk">
	public static final BlockContainer CALCITE_SCULK_TURF = Register("calcite_sculk_turf", new BlockContainer(new SculkTurfBlock(Blocks.CALCITE, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(0.75F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly()), ItemSettings()).silkTouchOr(Items.CALCITE));
	public static final BlockContainer DEEPSLATE_SCULK_TURF = Register("deepslate_sculk_turf", new BlockContainer(new SculkTurfBlock(Blocks.DEEPSLATE, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(3.0F, 6.0F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly()), ItemSettings()).silkTouchOr(Items.DEEPSLATE));
	public static final BlockContainer DRIPSTONE_SCULK_TURF = Register("dripstone_sculk_turf", new BlockContainer(new SculkTurfBlock(Blocks.DRIPSTONE_BLOCK, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(1.5F, 1.0F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly()), ItemSettings()).silkTouchOr(Items.DRIPSTONE_BLOCK));
	public static final BlockContainer SMOOTH_BASALT_SCULK_TURF = Register("smooth_basalt_sculk_turf", new BlockContainer(new SculkTurfBlock(Blocks.SMOOTH_BASALT, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(1.25F, 4.2F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly()), ItemSettings()).silkTouchOr(Items.SMOOTH_BASALT));
	public static final BlockContainer TUFF_SCULK_TURF = Register("tuff_sculk_turf", new BlockContainer(new SculkTurfBlock(Blocks.TUFF, Block.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(1.5F, 6.0F).sounds(ModBlockSoundGroups.SCULK).ticksRandomly()), ItemSettings()).silkTouchOr(Items.TUFF));
	//</editor-fold>


	@Override
	public void onInitialize() {
		LOGGER.info("Wich Pack Initializing");
	}
}
