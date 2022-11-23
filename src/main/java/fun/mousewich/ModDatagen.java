package fun.mousewich;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.nhoryzon.mc.farmersdelight.tag.Tags;
import fun.mousewich.container.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import static fun.mousewich.ModBase.*;
import static fun.mousewich.ModBase.EMERALD_BRICK_WALL;

public class ModDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		fabricDataGenerator.addProvider(ModelGenerator::new);
		fabricDataGenerator.addProvider(BlockLootGenerator::new);
		fabricDataGenerator.addProvider(BlockTagGenerator::new);
		fabricDataGenerator.addProvider(ItemTagGenerator::new);
	}

	private static class ModelGenerator extends FabricModelProvider {
		public ModelGenerator(FabricDataGenerator dataGenerator) { super(dataGenerator); }
		public static Identifier prefixPath(Identifier id, String prefix) { return new Identifier(id.getNamespace(), prefix + id.getPath()); }
		public static Identifier getBlockModelId(Block block) { return prefixPath(Registry.BLOCK.getId(block), "block/"); }
		public static Identifier getItemModelId(Item item) { return prefixPath(Registry.ITEM.getId(item), "item/"); }
		public static void cubeAllModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
			parentedItem(bsmg, bsmg::registerSimpleCubeAll, container);
		}
		public static void singletonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, TexturedModel.Factory factory) {
			parentedItem(bsmg, (b) -> bsmg.registerSingleton(b, factory), container);
		}
		public static void parentedItem(BlockStateModelGenerator bsmg, Item item, Block block) {
			bsmg.registerParentedItemModel(item, getBlockModelId(block));
		}
		public interface ModelFunc { void apply(Block block); }
		public static void parentedItem(BlockStateModelGenerator bsmg, ModelFunc blockModel, IBlockItemContainer container) {
			parentedItem(bsmg, blockModel, container.getBlock(), container.getItem());
		}
		public static void parentedItem(BlockStateModelGenerator bsmg, ModelFunc blockModel, Block block, Item item) {
			blockModel.apply(block);
			bsmg.registerParentedItemModel(item, getBlockModelId(block));
		}
		public static void logModel(BlockStateModelGenerator bsmg, IBlockItemContainer log, IBlockItemContainer wood) { makeLogModel(bsmg, log, null, wood); }
		public static void stemModel(BlockStateModelGenerator bsmg, IBlockItemContainer stem, IBlockItemContainer wood) { makeLogModel(bsmg, null, stem, wood); }
		public static void makeLogModel(BlockStateModelGenerator bsmg, IBlockItemContainer log, IBlockItemContainer stem, IBlockItemContainer wood) {
			TextureMap textures;
			if (log != null) {
				Block logBlock = log.getBlock();
				textures = TextureMap.sideAndEndForTop(logBlock);
				Identifier identifier = Models.CUBE_COLUMN.upload(logBlock, textures, bsmg.modelCollector);
				Identifier identifier2 = Models.CUBE_COLUMN_HORIZONTAL.upload(logBlock, textures, bsmg.modelCollector);
				bsmg.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(logBlock, identifier, identifier2));
				parentedItem(bsmg, log.getItem(), logBlock);
			}
			else {
				Block stemBlock = stem.getBlock();
				textures = TextureMap.sideAndEndForTop(stemBlock);
				Identifier identifier = Models.CUBE_COLUMN.upload(stemBlock, textures, bsmg.modelCollector);
				bsmg.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(stemBlock, identifier));
				parentedItem(bsmg, stem.getItem(), stemBlock);
			}
			Block woodBlock = wood.getBlock();
			TextureMap textureMap = textures.copyAndAdd(TextureKey.END, textures.getTexture(TextureKey.SIDE));
			Identifier identifier3 = Models.CUBE_COLUMN.upload(woodBlock, textureMap, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(woodBlock, identifier3));
			parentedItem(bsmg, wood.getItem(), woodBlock);
		}
		public static void pillarModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
			parentedItem(bsmg, (b) -> bsmg.registerAxisRotated(b, TexturedModel.CUBE_COLUMN, TexturedModel.CUBE_COLUMN_HORIZONTAL), container);
		}
		public static void paneModel(BlockStateModelGenerator bsmg, IBlockItemContainer pane, Block baseBlock) {
			Block paneBlock = pane.getBlock();
			TextureMap textureMap = TextureMap.paneAndTopForEdge(baseBlock, paneBlock);
			Identifier identifier = Models.TEMPLATE_GLASS_PANE_POST.upload(paneBlock, textureMap, bsmg.modelCollector);
			Identifier identifier2 = Models.TEMPLATE_GLASS_PANE_SIDE.upload(paneBlock, textureMap, bsmg.modelCollector);
			Identifier identifier3 = Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(paneBlock, textureMap, bsmg.modelCollector);
			Identifier identifier4 = Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(paneBlock, textureMap, bsmg.modelCollector);
			Identifier identifier5 = Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(paneBlock, textureMap, bsmg.modelCollector);
			Models.GENERATED.upload(ModelIds.getItemModelId(pane.getItem()), TextureMap.layer0(baseBlock), bsmg.modelCollector);
			bsmg.blockStateCollector.accept(MultipartBlockStateSupplier.create(paneBlock).with(BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2)).with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3)).with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4)).with(When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5)).with(When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4).put(VariantSettings.Y, VariantSettings.Rotation.R270)));
		}
		public static void slabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { slabModel(bsmg, container, baseBlock.getBlock()); }
		public static void slabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
			Block block = container.getBlock();
			TextureMap textures = TextureMap.all(baseBlock);
			Identifier identifier = Models.SLAB.upload(block, textures, bsmg.modelCollector);
			Identifier identifier2 = Models.SLAB_TOP.upload(block, textures, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(block, identifier, identifier2, getBlockModelId(baseBlock)));
			bsmg.registerParentedItemModel(block, identifier);
		}
		public static void stairsModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { stairsModel(bsmg, container, baseBlock.getBlock()); }
		public static void stairsModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
			Block block = container.getBlock();
			TextureMap textures = TextureMap.all(baseBlock);
			Identifier identifier = Models.INNER_STAIRS.upload(block, textures, bsmg.modelCollector);
			Identifier identifier2 = Models.STAIRS.upload(block, textures, bsmg.modelCollector);
			Identifier identifier3 = Models.OUTER_STAIRS.upload(block, textures, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(block, identifier, identifier2, identifier3));
			bsmg.registerParentedItemModel(block, identifier2);
		}
		public static void wallModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { wallModel(bsmg, container, baseBlock.getBlock()); }
		public static void wallModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
			Block block = container.getBlock();
			TextureMap textures = TextureMap.all(baseBlock);
			Identifier identifier = Models.TEMPLATE_WALL_POST.upload(block, textures, bsmg.modelCollector);
			Identifier identifier2 = Models.TEMPLATE_WALL_SIDE.upload(block, textures, bsmg.modelCollector);
			Identifier identifier3 = Models.TEMPLATE_WALL_SIDE_TALL.upload(block, textures, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createWallBlockState(block, identifier, identifier2, identifier3));
			Identifier identifier4 = Models.WALL_INVENTORY.upload(block, textures, bsmg.modelCollector);
			bsmg.registerParentedItemModel(block, identifier4);
		}
		public static void doorModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
			Block block = container.getBlock();
			TextureMap textureMap = TextureMap.topBottom(block);
			Identifier identifier = Models.DOOR_BOTTOM.upload(block, textureMap, bsmg.modelCollector);
			Identifier identifier2 = Models.DOOR_BOTTOM_RH.upload(block, textureMap, bsmg.modelCollector);
			Identifier identifier3 = Models.DOOR_TOP.upload(block, textureMap, bsmg.modelCollector);
			Identifier identifier4 = Models.DOOR_TOP_RH.upload(block, textureMap, bsmg.modelCollector);
			bsmg.registerItemModel(container.getItem());
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createDoorBlockState(block, identifier, identifier2, identifier3, identifier4));
		}
		public static void trapdoorModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, boolean orientable) {
			if (orientable) bsmg.registerOrientableTrapdoor(container.getBlock());
			else bsmg.registerTrapdoor(container.getBlock());
		}
		public static void buttonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { buttonModel(bsmg, container, baseBlock.getBlock()); }
		public static void buttonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
			Block block = container.getBlock();
			TextureMap textures = TextureMap.all(baseBlock);
			Identifier identifier = Models.BUTTON.upload(block, textures, bsmg.modelCollector);
			Identifier identifier2 = Models.BUTTON_PRESSED.upload(block, textures, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createButtonBlockState(block, identifier, identifier2));
			Identifier identifier3 = Models.BUTTON_INVENTORY.upload(block, textures, bsmg.modelCollector);
			bsmg.registerParentedItemModel(block, identifier3);
		}
		public static void fenceModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { fenceModel(bsmg, container, baseBlock.getBlock()); }
		public static void fenceModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
			Block block = container.getBlock();
			TextureMap textures = TextureMap.all(baseBlock);
			Identifier identifier = Models.FENCE_POST.upload(block, textures, bsmg.modelCollector);
			Identifier identifier2 = Models.FENCE_SIDE.upload(block, textures, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createFenceBlockState(block, identifier, identifier2));
			Identifier identifier3 = Models.FENCE_INVENTORY.upload(block, textures, bsmg.modelCollector);
			bsmg.registerParentedItemModel(block, identifier3);
		}
		public static void fenceGateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { fenceGateModel(bsmg, container, baseBlock.getBlock()); }
		public static void fenceGateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
			Block block = container.getBlock();
			TextureMap textures = TextureMap.all(baseBlock);
			Identifier identifier = Models.TEMPLATE_FENCE_GATE_OPEN.upload(block, textures, bsmg.modelCollector);
			Identifier identifier2 = Models.TEMPLATE_FENCE_GATE.upload(block, textures, bsmg.modelCollector);
			Identifier identifier3 = Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(block, textures, bsmg.modelCollector);
			Identifier identifier4 = Models.TEMPLATE_FENCE_GATE_WALL.upload(block, textures, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createFenceGateBlockState(block, identifier, identifier2, identifier3, identifier4));
		}
		public static void pressurePlateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { pressurePlateModel(bsmg, container, baseBlock.getBlock()); }
		public static void pressurePlateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
			Block block = container.getBlock();
			TextureMap textures = TextureMap.all(baseBlock);
			Identifier identifier = Models.PRESSURE_PLATE_UP.upload(block, textures, bsmg.modelCollector);
			Identifier identifier2 = Models.PRESSURE_PLATE_DOWN.upload(block, textures, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createPressurePlateBlockState(block, identifier, identifier2));
		}
		public static void signModel(BlockStateModelGenerator bsmg, SignContainer container, IBlockItemContainer baseBlock) { signModel(bsmg, container, baseBlock.getBlock()); }
		public static void signModel(BlockStateModelGenerator bsmg, SignContainer container, Block baseBlock) {
			Block block = container.getBlock(), wallBlock = container.getWallBlock();
			Identifier identifier = Models.PARTICLE.upload(block, TextureMap.all(baseBlock), bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier));
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(wallBlock, identifier));
			bsmg.registerItemModel(container.getItem());
			bsmg.excludeFromSimpleItemModelGeneration(wallBlock);
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator bsmg) {
			//Light Sources
			//TODO: Torches
			bsmg.registerItemModel(UNDERWATER_TORCH.getBlock());
			bsmg.registerItemModel(ENDER_TORCH.getBlock());
			//Extended Amethyst
			slabModel(bsmg, AMETHYST_SLAB, Blocks.AMETHYST_BLOCK);
			stairsModel(bsmg, AMETHYST_STAIRS, Blocks.AMETHYST_BLOCK);
			wallModel(bsmg, AMETHYST_WALL, Blocks.AMETHYST_BLOCK);
			cubeAllModel(bsmg, AMETHYST_CRYSTAL_BLOCK);
			slabModel(bsmg, AMETHYST_CRYSTAL_SLAB, AMETHYST_CRYSTAL_BLOCK);
			stairsModel(bsmg, AMETHYST_CRYSTAL_STAIRS, AMETHYST_CRYSTAL_BLOCK);
			wallModel(bsmg, AMETHYST_CRYSTAL_WALL, AMETHYST_CRYSTAL_BLOCK);
			cubeAllModel(bsmg, AMETHYST_BRICKS);
			slabModel(bsmg, AMETHYST_BRICK_SLAB, AMETHYST_BRICKS);
			stairsModel(bsmg, AMETHYST_BRICK_STAIRS, AMETHYST_BRICKS);
			wallModel(bsmg, AMETHYST_BRICK_WALL, AMETHYST_BRICKS);
			paneModel(bsmg, TINTED_GLASS_PANE, Blocks.TINTED_GLASS);
			//Extended Emerald
			cubeAllModel(bsmg, EMERALD_BRICKS);
			slabModel(bsmg, EMERALD_BRICK_SLAB, EMERALD_BRICKS);
			stairsModel(bsmg, EMERALD_BRICK_STAIRS, EMERALD_BRICKS);
			wallModel(bsmg, EMERALD_BRICK_WALL, EMERALD_BRICKS);
			cubeAllModel(bsmg, CUT_EMERALD);
			slabModel(bsmg, CUT_EMERALD_SLAB, CUT_EMERALD);
			stairsModel(bsmg, CUT_EMERALD_STAIRS, CUT_EMERALD);
			wallModel(bsmg, CUT_EMERALD_WALL, EMERALD_BRICKS);
			//Extended Diamond
			slabModel(bsmg, DIAMOND_SLAB, Blocks.DIAMOND_BLOCK);
			stairsModel(bsmg, DIAMOND_STAIRS, Blocks.DIAMOND_BLOCK);
			wallModel(bsmg, DIAMOND_WALL, Blocks.DIAMOND_BLOCK);
			cubeAllModel(bsmg, DIAMOND_BRICKS);
			slabModel(bsmg, DIAMOND_BRICK_SLAB, DIAMOND_BRICKS);
			stairsModel(bsmg, DIAMOND_BRICK_STAIRS, DIAMOND_BRICKS);
			wallModel(bsmg, DIAMOND_BRICK_WALL, DIAMOND_BRICKS);
			//Misc
			cubeAllModel(bsmg, CHARCOAL_BLOCK);
			cubeAllModel(bsmg, BLUE_SHROOMLIGHT);
			//TODO: HEDGE_BLOCK
			parentedItem(bsmg, HEDGE_BLOCK.getItem(), HEDGE_BLOCK.getBlock());
			cubeAllModel(bsmg, SEED_BLOCK);
			cubeAllModel(bsmg, WAX_BLOCK);
			//Extended Goat
			for (DyeColor color : COLORS) {
				BlockContainer fleece = FLEECE.get(color), carpet = FLEECE_CARPETS.get(color);
				bsmg.registerWoolAndCarpet(fleece.getBlock(), carpet.getBlock());
				parentedItem(bsmg, fleece.getItem(), fleece.getBlock());
				parentedItem(bsmg, carpet.getItem(), carpet.getBlock());
			}
			bsmg.registerWoolAndCarpet(RAINBOW_FLEECE.getBlock(), RAINBOW_FLEECE_CARPET.getBlock());
			//Extended Echo
			cubeAllModel(bsmg, ECHO_BLOCK);
			slabModel(bsmg, ECHO_SLAB, ECHO_BLOCK);
			stairsModel(bsmg, ECHO_STAIRS, ECHO_BLOCK);
			wallModel(bsmg, ECHO_WALL, ECHO_BLOCK);
			cubeAllModel(bsmg, ECHO_CRYSTAL_BLOCK);
			slabModel(bsmg, ECHO_CRYSTAL_SLAB, ECHO_CRYSTAL_BLOCK);
			stairsModel(bsmg, ECHO_CRYSTAL_STAIRS, ECHO_CRYSTAL_BLOCK);
			wallModel(bsmg, ECHO_CRYSTAL_WALL, ECHO_CRYSTAL_BLOCK);
			cubeAllModel(bsmg, BUDDING_ECHO);
			bsmg.registerAmethyst(ECHO_CLUSTER.getBlock());
			bsmg.registerItemModel(ECHO_CLUSTER.getBlock());
			bsmg.registerAmethyst(LARGE_ECHO_BUD.getBlock());
			bsmg.registerItemModel(LARGE_ECHO_BUD.getBlock());
			bsmg.registerAmethyst(MEDIUM_ECHO_BUD.getBlock());
			bsmg.registerItemModel(MEDIUM_ECHO_BUD.getBlock());
			bsmg.registerAmethyst(SMALL_ECHO_BUD.getBlock());
			bsmg.registerItemModel(SMALL_ECHO_BUD.getBlock());
			//Mud
			cubeAllModel(bsmg, MUD);
			cubeAllModel(bsmg, PACKED_MUD);
			//TODO: MUD_BRICKS (using north_west_mirrored_all parent model)
			parentedItem(bsmg, MUD_BRICKS.getItem(), MUD_BRICKS.getBlock());
			slabModel(bsmg, MUD_BRICK_SLAB, MUD_BRICKS);
			stairsModel(bsmg, MUD_BRICK_STAIRS, MUD_BRICKS);
			wallModel(bsmg, MUD_BRICK_WALL, MUD_BRICKS);
			//Mangrove
			logModel(bsmg, MANGROVE_LOG, MANGROVE_WOOD);
			logModel(bsmg, STRIPPED_MANGROVE_LOG, STRIPPED_MANGROVE_WOOD);
			singletonModel(bsmg, MANGROVE_LEAVES, TexturedModel.LEAVES);
			cubeAllModel(bsmg, MANGROVE_PLANKS);
			slabModel(bsmg, MANGROVE_SLAB, MANGROVE_PLANKS);
			stairsModel(bsmg, MANGROVE_STAIRS, MANGROVE_PLANKS);
			doorModel(bsmg, MANGROVE_DOOR);
			trapdoorModel(bsmg, MANGROVE_TRAPDOOR, true);
			buttonModel(bsmg, MANGROVE_BUTTON, MANGROVE_PLANKS);
			fenceModel(bsmg, MANGROVE_FENCE, MANGROVE_PLANKS);
			fenceGateModel(bsmg, MANGROVE_FENCE_GATE, MANGROVE_PLANKS);
			pressurePlateModel(bsmg, MANGROVE_PRESSURE_PLATE, MANGROVE_PLANKS);
			signModel(bsmg, MANGROVE_SIGN, MANGROVE_PLANKS);
			//TODO: hanging_sign, propagule, roots
			//Frogs
			pillarModel(bsmg, OCHRE_FROGLIGHT);
			pillarModel(bsmg, VERDANT_FROGLIGHT);
			pillarModel(bsmg, PEARLESCENT_FROGLIGHT);
		}

		private static void generated(ItemModelGenerator img, Item item) { img.register(item, Models.GENERATED); }
		private static void generated(ItemModelGenerator img, Item... items) { for(Item item : items) img.register(item, Models.GENERATED); }

		@Override
		public void generateItemModels(ItemModelGenerator img) {
			//Light Sources
			generated(img, ENDER_LANTERN.getItem(), ENDER_CAMPFIRE.getItem());
			//Extended Amethyst
			generated(img, AMETHYST_AXE, AMETHYST_HOE, AMETHYST_PICKAXE, AMETHYST_SHOVEL, AMETHYST_SWORD, AMETHYST_KNIFE);
			generated(img, AMETHYST_HELMET, AMETHYST_CHESTPLATE, AMETHYST_LEGGINGS, AMETHYST_BOOTS, AMETHYST_HORSE_ARMOR);
			generated(img, TINTED_GOGGLES);
			//Extended Emerald
			generated(img, EMERALD_AXE, EMERALD_HOE, EMERALD_PICKAXE, EMERALD_SHOVEL, EMERALD_SWORD, EMERALD_KNIFE);
			generated(img, EMERALD_HELMET, EMERALD_CHESTPLATE, EMERALD_LEGGINGS, EMERALD_BOOTS, EMERALD_HORSE_ARMOR);
			//Minecraft Earth
			generated(img, HORN);
			//Goat
			generated(img, GOAT_HORN);
			//Extended Goat
			generated(img, CHEVON, COOKED_CHEVON);
			generated(img, GOAT_HORN_SALVE);
			generated(img, FLEECE_HELMET, FLEECE_CHESTPLATE, FLEECE_LEGGINGS, FLEECE_BOOTS, FLEECE_HORSE_ARMOR);
			//Echo
			generated(img, ECHO_SHARD);
			//Extended Echo
			generated(img, ECHO_AXE, ECHO_HOE, ECHO_PICKAXE, ECHO_SHOVEL, ECHO_SWORD, ECHO_KNIFE);
			//Music Discs
			generated(img, MUSIC_DISC_5, DISC_FRAGMENT_5);
			//Mangrove
			generated(img, MANGROVE_BOAT.getItem(), MANGROVE_PROPAGULE.getItem());
		}
	}

	public static class BlockLootGenerator extends SimpleFabricLootTableProvider {
		public static Map<Block, Item> Drops = new HashMap<>();
		public static Set<Block> DropNothing = new HashSet<>();
		public static Set<Block> DropSlabs = new HashSet<>();
		public static Map<Block, Item> RequireSilkTouch = new HashMap<>();
		public static Map<Block, Item> SilkTouchOr = new HashMap<>();

		public BlockLootGenerator(FabricDataGenerator dataGenerator) {
			super(dataGenerator, LootContextTypes.BLOCK);
		}

		private void drops(BiConsumer<Identifier, LootTable.Builder> ibbc, Block block, Item drop) {
			ibbc.accept(block.getLootTableId(), BlockLootTableGenerator.drops(drop));
		}
		private void dropDoor(BiConsumer<Identifier, LootTable.Builder> ibbc, Block block) {
			ibbc.accept(block.getLootTableId(), BlockLootTableGenerator.addDoorDrop(block));
		}
		private void dropNothing(BiConsumer<Identifier, LootTable.Builder> ibbc, Block block) {
			ibbc.accept(block.getLootTableId(), BlockLootTableGenerator.dropsNothing());
		}
		private void requireSilkTouch(BiConsumer<Identifier, LootTable.Builder> ibbc, Block block, Item item) {
			ibbc.accept(block.getLootTableId(), BlockLootTableGenerator.dropsWithSilkTouch(item));
		}
		private void silkTouchOr(BiConsumer<Identifier, LootTable.Builder> ibbc, Block block, Item withoutSilkTouch) {
			ibbc.accept(block.getLootTableId(), BlockLootTableGenerator.drops(block, withoutSilkTouch));
		}
		public void dropSlabs(BiConsumer<Identifier, LootTable.Builder> ibbc, Block block) {
			ibbc.accept(block.getLootTableId(), BlockLootTableGenerator.slabDrops(block));
		}

		@Override
		public void accept(BiConsumer<Identifier, LootTable.Builder> ibbc) {
			for (Map.Entry<Block, Item> drop : Drops.entrySet()) drops(ibbc, drop.getKey(), drop.getValue());
			Drops.clear();
			for (Block block : DropNothing) dropNothing(ibbc, block);
			DropNothing.clear();
			for (Block block : DropSlabs) dropSlabs(ibbc, block);
			DropSlabs.clear();
			for (Map.Entry<Block, Item> drop : RequireSilkTouch.entrySet()) requireSilkTouch(ibbc, drop.getKey(), drop.getValue());
			RequireSilkTouch.clear();
			for (Map.Entry<Block, Item> drop : Drops.entrySet()) silkTouchOr(ibbc, drop.getKey(), drop.getValue());
			//Light Sources
			drops(ibbc, UNLIT_LANTERN, Items.LANTERN);
			drops(ibbc, UNLIT_SOUL_LANTERN, Items.LANTERN);
			ibbc.accept(ENDER_CAMPFIRE.getBlock().getLootTableId(), BlockLootTableGenerator.dropsWithSilkTouch(ENDER_CAMPFIRE.getBlock(),
					BlockLootTableGenerator.addSurvivesExplosionCondition(ENDER_CAMPFIRE.getBlock(), ItemEntry.builder(Items.POPPED_CHORUS_FRUIT).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f))))));
			//Extended Echo
			ibbc.accept(ECHO_CLUSTER.getBlock().getLootTableId(), BlockLootTableGenerator.dropsWithSilkTouch(ECHO_CLUSTER.getBlock(),
					ItemEntry.builder(ECHO_SHARD)
							.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4.0f)))
							.apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
							.conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ItemTags.CLUSTER_MAX_HARVESTABLES)))
							.alternatively(BlockLootTableGenerator.applyExplosionDecay(ECHO_CLUSTER.getBlock(), ItemEntry.builder(ECHO_SHARD).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)))))));
			//Mangrove
			//TODO: MANGROVE_HANGING_SIGN, MANGROVE_LEAVES
			//TODO: MANGROVE_PROPAGULE, POTTED_MANGROVE_PROPAGULE
			dropDoor(ibbc, MANGROVE_DOOR.getBlock());
		}
	}

	private static class BlockTagGenerator extends FabricTagProvider<Block> {
		public BlockTagGenerator(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.BLOCK, "blocks", NAMESPACE + ":block_tag_generator");
		}

		@Override
		protected void generateTags() {
			getOrCreateTagBuilder(ModTags.Blocks.ANCIENT_CITY_REPLACEABLE)
					.add(Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE_BRICK_SLAB,
							Blocks.DEEPSLATE_TILE_SLAB, Blocks.DEEPSLATE_BRICK_STAIRS, Blocks.DEEPSLATE_TILE_WALL,
							Blocks.DEEPSLATE_BRICK_WALL, Blocks.COBBLED_DEEPSLATE, Blocks.CRACKED_DEEPSLATE_BRICKS,
							Blocks.CRACKED_DEEPSLATE_TILES)
					.addTag(ModTags.Blocks.SCULK_TURFS);

			getOrCreateTagBuilder(ModTags.Blocks.SCULK_TURFS)
					.add(CALCITE_SCULK_TURF.getBlock(), DEEPSLATE_SCULK_TURF.getBlock(), DRIPSTONE_SCULK_TURF.getBlock(),
							SMOOTH_BASALT_SCULK_TURF.getBlock(), TUFF_SCULK_TURF.getBlock());
		}
	}

	private static class ItemTagGenerator extends FabricTagProvider<Item> {
		public ItemTagGenerator(FabricDataGenerator dataGenerator) {
			super(dataGenerator, Registry.ITEM, "items", NAMESPACE + ":item_tag_generator");
		}

		@Override
		protected void generateTags() {
			getOrCreateTagBuilder(ModTags.Items.BOOKSHELF_BOOKS)
					.add(Items.BOOK, Items.ENCHANTED_BOOK, Items.WRITABLE_BOOK, Items.WRITTEN_BOOK);
			getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(MUSIC_DISC_5);

			getOrCreateTagBuilder(ModTags.Items.AXES)
					.add(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE)
					.add(ECHO_AXE);
			getOrCreateTagBuilder(ModTags.Items.HOES)
					.add(Items.WOODEN_HOE, Items.STONE_HOE, Items.IRON_HOE, Items.GOLDEN_HOE, Items.DIAMOND_HOE, Items.NETHERITE_HOE)
					.add(ECHO_HOE);
			getOrCreateTagBuilder(ModTags.Items.PICKAXES)
					.add(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE)
					.add(ECHO_PICKAXE);
			getOrCreateTagBuilder(ItemTags.CLUSTER_MAX_HARVESTABLES).addTag(ModTags.Items.PICKAXES);
			getOrCreateTagBuilder(ModTags.Items.SHOVELS)
					.add(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.GOLDEN_SHOVEL, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL)
					.add(ECHO_SHOVEL);
			getOrCreateTagBuilder(ModTags.Items.SWORDS)
					.add(Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD)
					.add(ECHO_SWORD);
			getOrCreateTagBuilder(ModTags.Items.KNIVES)
					.add(ItemsRegistry.FLINT_KNIFE.get(), ItemsRegistry.IRON_KNIFE.get(), ItemsRegistry.GOLDEN_KNIFE.get(), ItemsRegistry.DIAMOND_KNIFE.get(), ItemsRegistry.NETHERITE_KNIFE.get())
					.add(ECHO_KNIFE);
			getOrCreateTagBuilder(Tags.KNIVES).addTag(ModTags.Items.KNIVES);
			getOrCreateTagBuilder(ModTags.Items.SHEARS)
					.add(Items.SHEARS);
		}
	}
}
