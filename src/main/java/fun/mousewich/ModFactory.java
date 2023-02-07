package fun.mousewich;

import fun.mousewich.block.BookshelfBlock;
import fun.mousewich.block.LightableLanternBlock;
import fun.mousewich.block.WoodcutterBlock;
import fun.mousewich.block.basic.*;
import fun.mousewich.container.*;
import fun.mousewich.gen.data.loot.DropTable;
import fun.mousewich.gen.data.loot.BlockLootGenerator;
import fun.mousewich.material.ModMaterials;
import fun.mousewich.sound.ModBlockSoundGroups;
import fun.mousewich.util.Util;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import java.util.function.ToIntFunction;

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

	public static Block.Settings LeafBlockSettings() {
		return Block.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(ModFactory::canSpawnOnLeaves).suffocates(ModFactory::never).blockVision(ModFactory::never);
	}

	public static BlockContainer MakeBookshelf(MapColor color) { return MakeBookshelf(color, BlockSoundGroup.WOOD); }
	public static BlockContainer MakeBookshelf(MapColor color, BlockSoundGroup sounds) {
		return new BlockContainer(new BookshelfBlock(Block.Settings.of(Material.WOOD, color).strength(1.5F).sounds(sounds))).drops(DropTable.BOOKSHELF);
	}

	public static final Material FROGSPAWN_MATERIAL = new Material(MapColor.WATER_BLUE, false, false, false, false, false, false, PistonBehavior.DESTROY);
	public static BlockContainer MakeFroglight(MapColor color) {
		return new BlockContainer(new PillarBlock(Block.Settings.of(ModMaterials.FROGLIGHT, color).strength(0.3f).luminance(Util.LUMINANCE_15).sounds(ModBlockSoundGroups.FROGLIGHT)), ItemGroup.DECORATIONS).dropSelf();
	}

	public static Block.Settings TorchSettings(int luminance, BlockSoundGroup sounds) {
		return FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(createLightLevelFromLitBlockState(luminance)).sounds(sounds);
	}
	public static TorchContainer MakeTorch(int luminance, BlockSoundGroup sounds, DefaultParticleType particle) {
		return new TorchContainer(TorchSettings(luminance, sounds), particle, ModBase.ItemSettings()).dropSelf();
	}
	public static TorchContainer MakeTorch(BlockSoundGroup sounds) {
		return MakeTorch(14, sounds, ParticleTypes.FLAME);
	}
	public static TorchContainer MakeEnderTorch(BlockSoundGroup sounds) {
		return MakeTorch(12, sounds, ModBase.ENDER_FIRE_FLAME_PARTICLE);
	}
	public static TorchContainer MakeSoulTorch(BlockSoundGroup sounds) {
		return MakeTorch(10, sounds, ParticleTypes.SOUL_FIRE_FLAME);
	}
	public static TorchContainer MakeUnderwaterTorch(BlockSoundGroup sounds) {
		return TorchContainer.Waterloggable(TorchSettings(14, sounds), ModBase.UNDERWATER_TORCH_GLOW, ModBase.ItemSettings()).dropSelf();
	}
	public static Block.Settings LanternSettings(int luminance) {
		return Block.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(createLightLevelFromLitBlockState(luminance)).nonOpaque();
	}
	public static BlockContainer MakeLantern(int luminance) {
		return new BlockContainer(new LightableLanternBlock(LanternSettings(luminance)), ModBase.ItemSettings()).dropSelf();
	}

	public static BlockContainer MakeCampfire(int luminance, int fireDamage, MapColor mapColor, BlockSoundGroup sounds, boolean emitsParticles) {
		return new BlockContainer(new CampfireBlock(emitsParticles, fireDamage, Block.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(sounds).luminance(createLightLevelFromLitBlockState(luminance)).nonOpaque()), ModBase.ItemSettings());
	}

	public static BedContainer MakeBed(String name) {
		BedContainer bed = new BedContainer(name);
		BlockLootGenerator.Drops.put(bed.asBlock(), DropTable.BED);
		return bed;
	}

	public static BoatContainer MakeBoat(String name, IBlockItemContainer base) { return MakeBoat(name, base, ModBase.ITEM_GROUP); }
	public static BoatContainer MakeBoat(String name, IBlockItemContainer base, ItemGroup itemGroup) {
		return new BoatContainer(name, base, false, ModBase.ItemSettings(itemGroup).maxCount(1)).dispensable();
	}

	public static BlockContainer MakeCandle(MapColor mapColor, double luminance) {
		return new BlockContainer(new CandleBlock(Block.Settings.of(Material.DECORATION, mapColor).nonOpaque().strength(0.1F).sounds(BlockSoundGroup.CANDLE)
				.luminance((state) -> (int)(state.get(CandleBlock.LIT) ? luminance * state.get(CandleBlock.CANDLES) : 0)))).drops(DropTable.CANDLE);
	}

	public static BlockContainer MakeLadder() {
		return MakeLadder(Block.Settings.of(Material.DECORATION).strength(0.4F).sounds(BlockSoundGroup.LADDER).nonOpaque());
	}
	public static BlockContainer MakeLadder(Block.Settings settings) {
		return new BlockContainer(new ModLadderBlock(settings)).dropSelf();
	}

	public static BlockContainer MakeWood(IBlockItemContainer log) { return MakeWood(log, ModBase.ITEM_GROUP); }
	public static BlockContainer MakeWood(IBlockItemContainer log, ItemGroup itemGroup) {
		return new BlockContainer(new PillarBlock(Block.Settings.copy(log.asBlock())), itemGroup).dropSelf();
	}

	public static Block.Settings WoodButtonSettings(BlockSoundGroup soundGroup) { return Block.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(soundGroup); }
	public static BlockContainer MakeWoodButton() { return MakeWoodButton(ModBase.ITEM_GROUP); }
	public static BlockContainer MakeWoodButton(ItemGroup itemGroup) {
		return new BlockContainer(new ModWoodenButtonBlock(WoodButtonSettings(BlockSoundGroup.WOOD)), itemGroup).dropSelf();
	}
	public static BlockContainer MakeWoodButton(ItemGroup itemGroup, BlockSoundGroup soundGroup, SoundEvent pressed, SoundEvent released) {
		return new BlockContainer(new ModWoodenButtonBlock(WoodButtonSettings(soundGroup), pressed, released), itemGroup).dropSelf();
	}

	public static Block.Settings WoodPressurePlateSettings(MapColor color, BlockSoundGroup soundGroup) { return Block.Settings.of(Material.WOOD, color).noCollision().strength(0.5F).sounds(soundGroup); }
	public static BlockContainer MakeWoodPressurePlate(IBlockItemContainer ingredient) { return MakeWoodPressurePlate(ingredient, ModBase.ITEM_GROUP); }
	public static BlockContainer MakeWoodPressurePlate(IBlockItemContainer ingredient, ItemGroup itemGroup) {
		return new BlockContainer(ModPressurePlateBlock.Wooden(PressurePlateBlock.ActivationRule.EVERYTHING, WoodPressurePlateSettings(ingredient.asBlock().getDefaultMapColor(), BlockSoundGroup.WOOD)), itemGroup).dropSelf();
	}
	public static BlockContainer MakeWoodPressurePlate(IBlockItemContainer ingredient, ItemGroup itemGroup, BlockSoundGroup soundGroup, SoundEvent pressed, SoundEvent released) {
		return new BlockContainer(new ModPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, WoodPressurePlateSettings(ingredient.asBlock().getDefaultMapColor(), soundGroup), pressed, released), itemGroup).dropSelf();
	}

	public static Block.Settings WoodDoorSettings(MapColor color, BlockSoundGroup soundGroup) { return Block.Settings.of(Material.WOOD, color).strength(3.0F).sounds(soundGroup).nonOpaque(); }
	public static BlockContainer MakeWoodDoor(IBlockItemContainer ingredient) { return MakeWoodDoor(ingredient, ModBase.ITEM_GROUP); }
	public static BlockContainer MakeWoodDoor(IBlockItemContainer ingredient, ItemGroup itemGroup) {
		return new BlockContainer(new ModDoorBlock(WoodDoorSettings(ingredient.asBlock().getDefaultMapColor(), BlockSoundGroup.WOOD)), itemGroup).drops(DropTable.DOOR);
	}
	public static BlockContainer MakeWoodDoor(IBlockItemContainer ingredient, ItemGroup itemGroup, BlockSoundGroup soundGroup, SoundEvent opened, SoundEvent closed) {
		return new BlockContainer(new ModDoorBlock(WoodDoorSettings(ingredient.asBlock().getDefaultMapColor(), soundGroup), opened, closed), itemGroup).drops(DropTable.DOOR);
	}

	public static SignContainer MakeWoodSign(String name) { return MakeWoodSign(name, ModBase.ITEM_GROUP); }
	public static SignContainer MakeWoodSign(String name, ItemGroup itemGroup) {
		return new SignContainer(name, Material.WOOD, BlockSoundGroup.WOOD, ModBase.ItemSettings(itemGroup).maxCount(16)).dropSelf();
	}

	public static Block.Settings PlanksSettings(MapColor mapColor) { return PlanksSettings(mapColor, BlockSoundGroup.WOOD); }
	public static Block.Settings PlanksSettings(MapColor mapColor, BlockSoundGroup soundGroup) {
		return Block.Settings.of(Material.WOOD, mapColor).strength(2.0F, 3.0F).sounds(soundGroup);
	}
	public static BlockContainer MakePlanks(MapColor mapColor) { return MakePlanks(mapColor, ModBase.ITEM_GROUP); }
	public static BlockContainer MakePlanks(MapColor mapColor, ItemGroup itemGroup) {
		return new BlockContainer(new Block(PlanksSettings(mapColor)), itemGroup).dropSelf();
	}
	public static BlockContainer MakePlanks(MapColor mapColor, BlockSoundGroup soundGroup, ItemGroup itemGroup) {
		return new BlockContainer(new Block(PlanksSettings(mapColor, soundGroup)), itemGroup).dropSelf();
	}

	public static BlockContainer MakeBambooBlock(MapColor mapColor, Item.Settings settings) {
		return new BlockContainer(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, (state) -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.YELLOW : mapColor).strength(2.0F).sounds(ModBlockSoundGroups.BAMBOO_WOOD)), settings).dropSelf();
	}

	public static BlockContainer MakeSlab(IBlockItemContainer base) { return MakeSlab(base, ModBase.ITEM_GROUP); }
	public static BlockContainer MakeSlab(IBlockItemContainer base, ItemGroup itemGroup) {
		return new BlockContainer(new ModSlabBlock(base.asBlock()), itemGroup).dropSlabs();
	}
	public static BlockContainer MakeSlab(Block base) { return MakeSlab(base, ModBase.ITEM_GROUP); }
	public static BlockContainer MakeSlab(Block base, ItemGroup itemGroup) {
		return new BlockContainer(new ModSlabBlock(base), itemGroup).dropSlabs();
	}

	public static BlockContainer MakeStairs(IBlockItemContainer base) { return MakeStairs(base, ModBase.ITEM_GROUP); }
	public static BlockContainer MakeStairs(IBlockItemContainer base, ItemGroup itemGroup) {
		return new BlockContainer(new ModStairsBlock(base.asBlock()), itemGroup).dropSelf();
	}
	public static BlockContainer MakeStairs(Block base) { return MakeStairs(base, ModBase.ITEM_GROUP); }
	public static BlockContainer MakeStairs(Block base, ItemGroup itemGroup) {
		return new BlockContainer(new ModStairsBlock(base), itemGroup).dropSelf();
	}

	public static BlockContainer MakeWoodFence(IBlockItemContainer base) { return MakeWoodFence(base, ModBase.ITEM_GROUP); }
	public static BlockContainer MakeWoodFence(IBlockItemContainer base, ItemGroup itemGroup) {
		return new BlockContainer(new ModFenceBlock(base.asBlock()), itemGroup).dropSelf();
	}

	public static BlockContainer MakeWoodFenceGate(IBlockItemContainer ingredient) { return MakeWoodFenceGate(ingredient, ModBase.ITEM_GROUP); }
	public static BlockContainer MakeWoodFenceGate(IBlockItemContainer ingredient, ItemGroup itemGroup) {
		return new BlockContainer(new FenceGateBlock(Block.Settings.copy(ingredient.asBlock())), itemGroup).dropSelf();
	}
	public static BlockContainer MakeWoodFenceGate(IBlockItemContainer ingredient, ItemGroup itemGroup, SoundEvent opened, SoundEvent closed) {
		return new BlockContainer(new ModFenceGateBlock(Block.Settings.copy(ingredient.asBlock()), opened, closed), itemGroup).dropSelf();
	}

	public static BlockContainer MakeWall(IBlockItemContainer base) { return MakeWall(base, ModBase.ITEM_GROUP); }
	public static BlockContainer MakeWall(IBlockItemContainer base, ItemGroup itemGroup) {
		return new BlockContainer(new ModWallBlock(base.asBlock()), itemGroup).dropSelf();
	}
	public static BlockContainer MakeWall(Block base) { return MakeWall(base, ModBase.ITEM_GROUP); }
	public static BlockContainer MakeWall(Block base, ItemGroup itemGroup) {
		return new BlockContainer(new ModWallBlock(base), itemGroup).dropSelf();
	}

	public static Block.Settings WoodTrapdoorSettings(MapColor color, BlockSoundGroup soundGroup) { return Block.Settings.of(Material.WOOD, color).strength(3.0F).sounds(soundGroup).nonOpaque().allowsSpawning(ModFactory::noSpawning); }
	public static BlockContainer MakeWoodTrapdoor(IBlockItemContainer ingredient) { return MakeWoodTrapdoor(ingredient, ModBase.ITEM_GROUP); }
	public static BlockContainer MakeWoodTrapdoor(IBlockItemContainer ingredient, ItemGroup itemGroup) {
		return new BlockContainer(new ModTrapdoorBlock(WoodTrapdoorSettings(ingredient.asBlock().getDefaultMapColor(), BlockSoundGroup.WOOD)), itemGroup).dropSelf();
	}
	public static BlockContainer MakeWoodTrapdoor(IBlockItemContainer ingredient, ItemGroup itemGroup, BlockSoundGroup soundGroup, SoundEvent opened, SoundEvent closed) {
		return new BlockContainer(new ModTrapdoorBlock(WoodTrapdoorSettings(ingredient.asBlock().getDefaultMapColor(), soundGroup), opened, closed), itemGroup).dropSelf();
	}

	public static BlockContainer MakeWoodcutter(MapColor color) {
		return new BlockContainer(new WoodcutterBlock(Block.Settings.of(Material.WOOD, color).strength(3.5F))).dropSelf();
	}
}
