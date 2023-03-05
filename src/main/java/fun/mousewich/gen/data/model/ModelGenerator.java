package fun.mousewich.gen.data.model;

import fun.mousewich.container.*;
import fun.mousewich.trim.ArmorTrimPattern;
import fun.mousewich.util.ColorUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.SlabType;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import static fun.mousewich.ModBase.*;
import static fun.mousewich.ModBase.GOLD_SLAB;

public class ModelGenerator extends FabricModelProvider {
	public ModelGenerator(FabricDataGenerator dataGenerator) { super(dataGenerator); }
	public static Identifier prefixPath(Identifier id, String prefix) { return new Identifier(id.getNamespace(), prefix + id.getPath()); }
	public static Identifier postfixPath(Identifier id, String postfix) { return new Identifier(id.getNamespace(), id.getPath() + postfix); }
	public static Identifier getBlockModelId(Block block) { return getBlockModelId("", block); }
	public static Identifier getBlockModelId(String prefix, Block block) { return prefixPath(Registry.BLOCK.getId(block), "block/" + prefix); }
	public static Identifier getBlockModelId(String prefix, Block block, String postfix) { return postfixPath(getBlockModelId(prefix, block), postfix); }
	public static Identifier getItemModelId(Item item) { return prefixPath(Registry.ITEM.getId(item), "item/"); }
	public static void cubeAllModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) { parentedItem(bsmg, bsmg::registerSimpleCubeAll, container); }
	private static void explicitModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		bsmg.registerSimpleState(block);
	}
	public static void explicitModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		explicitModelCommon(bsmg, container);
		parentedItem(bsmg, container.asItem(), container.asBlock());
	}
	public static void singletonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, TexturedModel.Factory factory) {
		parentedItem(bsmg, (b) -> bsmg.registerSingleton(b, factory), container);
	}
	public static void parentedItem(BlockStateModelGenerator bsmg, IBlockItemContainer container) { parentedItem(bsmg, container.asItem(), container.asBlock()); }
	public static void parentedItem(BlockStateModelGenerator bsmg, Item item, Block block) { bsmg.registerParentedItemModel(item, getBlockModelId(block)); }
	public static void parentedItem(BlockStateModelGenerator bsmg, Item item, Identifier parent) { bsmg.registerParentedItemModel(item, parent); }
	public static void parentedItem(BlockStateModelGenerator bsmg, Item item, Item parent) { parentedItem(bsmg, item, getItemModelId(parent)); }
	public static void parentedItem(BlockStateModelGenerator bsmg, Item item, ItemConvertible parent) { parentedItem(bsmg, item, parent.asItem()); }
	public interface ModelFunc { void apply(Block block); }
	public static void parentedItem(BlockStateModelGenerator bsmg, ModelFunc blockModel, IBlockItemContainer container) { parentedItem(bsmg, blockModel, container.asBlock(), container.asItem()); }
	public static void parentedItem(BlockStateModelGenerator bsmg, ModelFunc blockModel, Block block, Item item) {
		blockModel.apply(block);
		parentedItem(bsmg, item, block);
	}
	public static void generatedItemModel(BlockStateModelGenerator bsmg, Item item, Block block) {
		Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(block), bsmg.modelCollector);
	}
	public static void generatedItemModel(BlockStateModelGenerator bsmg, Item item) {
		Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(item), bsmg.modelCollector);
	}
	public static void generatedItemModels(BlockStateModelGenerator bsmg, ItemConvertible... items) {
		for (ItemConvertible item : items) generatedItemModel(bsmg, item.asItem());
	}
	public static void logModel(BlockStateModelGenerator bsmg, IBlockItemContainer log, IBlockItemContainer wood) { makeLogModel(bsmg, log, null, wood); }
	public static void stemModel(BlockStateModelGenerator bsmg, IBlockItemContainer stem, IBlockItemContainer wood) { makeLogModel(bsmg, null, stem, wood); }
	public static void makeLogModel(BlockStateModelGenerator bsmg, IBlockItemContainer log, IBlockItemContainer stem, IBlockItemContainer wood) {
		TextureMap textures;
		if (log != null) {
			Block logBlock = log.asBlock();
			textures = TextureMap.sideAndEndForTop(logBlock);
			Identifier identifier = Models.CUBE_COLUMN.upload(logBlock, textures, bsmg.modelCollector);
			Identifier identifier2 = Models.CUBE_COLUMN_HORIZONTAL.upload(logBlock, textures, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(logBlock, identifier, identifier2));
			parentedItem(bsmg, log.asItem(), logBlock);
		}
		else {
			Block stemBlock = stem.asBlock();
			textures = TextureMap.sideAndEndForTop(stemBlock);
			Identifier identifier = Models.CUBE_COLUMN.upload(stemBlock, textures, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(stemBlock, identifier));
			parentedItem(bsmg, stem.asItem(), stemBlock);
		}
		Block woodBlock = wood.asBlock();
		TextureMap textureMap = textures.copyAndAdd(TextureKey.END, textures.getTexture(TextureKey.SIDE));
		Identifier identifier3 = Models.CUBE_COLUMN.upload(woodBlock, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(woodBlock, identifier3));
		parentedItem(bsmg, wood.asItem(), woodBlock);
	}
	public static void pillarModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		parentedItem(bsmg, (b) -> bsmg.registerAxisRotated(b, TexturedModel.CUBE_COLUMN, TexturedModel.CUBE_COLUMN_HORIZONTAL), container);
	}
	public static void frogspawnModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		bsmg.registerItemModel(block);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, ModelIds.getBlockModelId(block)));
	}
	public static void tallFlowerModel(BlockStateModelGenerator bsmg, TallBlockContainer flower) {
		Block doubleBlock = flower.asBlock();
		bsmg.registerItemModel(doubleBlock, "_top");
		Identifier identifier = bsmg.createSubModel(doubleBlock, "_top", Models.CROSS, TextureMap::cross);
		Identifier identifier2 = bsmg.createSubModel(doubleBlock, "_bottom", Models.CROSS, TextureMap::cross);
		bsmg.registerDoubleBlock(doubleBlock, identifier, identifier2);
	}
	public static void tallFlowerModel(BlockStateModelGenerator bsmg, TallBlockContainer flower, Block topModel, Identifier bottomModel) {
		generatedItemModel(bsmg, flower.asItem(), topModel);
		Block doubleBlock = flower.asBlock();
		Identifier identifier = Models.CROSS.upload(doubleBlock, "_top", TextureMap.cross(topModel), bsmg.modelCollector);
		Identifier identifier2 = Models.CROSS.upload(doubleBlock, "_bottom", TextureMap.cross(bottomModel), bsmg.modelCollector);
		bsmg.registerDoubleBlock(doubleBlock, identifier, identifier2);
	}
	public static void flowerPotPlantModel(BlockStateModelGenerator bsmg, PottedBlockContainer potted) {
		Block plantBlock = potted.asBlock(), flowerPotBlock = potted.getPottedBlock();
		bsmg.registerItemModel(plantBlock);
		Identifier identifier = Models.CROSS.upload(plantBlock, TextureMap.cross(plantBlock), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(plantBlock, identifier));
		Identifier identifier2 = Models.FLOWER_POT_CROSS.upload(flowerPotBlock, TextureMap.plant(plantBlock), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(flowerPotBlock, identifier2));
	}
	public static void paneModel(BlockStateModelGenerator bsmg, IBlockItemContainer pane, Block baseBlock) {
		Block paneBlock = pane.asBlock();
		TextureMap textureMap = TextureMap.paneAndTopForEdge(baseBlock, paneBlock);
		Identifier identifier = Models.TEMPLATE_GLASS_PANE_POST.upload(paneBlock, textureMap, bsmg.modelCollector);
		Identifier identifier2 = Models.TEMPLATE_GLASS_PANE_SIDE.upload(paneBlock, textureMap, bsmg.modelCollector);
		Identifier identifier3 = Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(paneBlock, textureMap, bsmg.modelCollector);
		Identifier identifier4 = Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(paneBlock, textureMap, bsmg.modelCollector);
		Identifier identifier5 = Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(paneBlock, textureMap, bsmg.modelCollector);
		Models.GENERATED.upload(getItemModelId(pane.asItem()), TextureMap.layer0(baseBlock), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(MultipartBlockStateSupplier.create(paneBlock).with(BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2)).with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3)).with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4)).with(When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5)).with(When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4).put(VariantSettings.Y, VariantSettings.Rotation.R270)));
	}
	public static void slabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock, TextureMap textures) {
		Block block = container.asBlock();
		Identifier identifier = Models.SLAB.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.SLAB_TOP.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(block, identifier, identifier2, getBlockModelId(baseBlock)));
		bsmg.registerParentedItemModel(container.asItem(), identifier);
	}
	public static void slabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { slabModel(bsmg, container, baseBlock.asBlock()); }
	public static void slabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) { slabModel(bsmg, container, baseBlock, TextureMap.all(baseBlock)); }
	private void glassSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		TextureMap textures = TextureMap.sideEnd(TextureMap.getSubId(block, "_side"), TextureMap.getId(baseBlock));
		Identifier identifier = Models.SLAB.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.SLAB_TOP.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(block, identifier, identifier2, getBlockModelId(baseBlock)));
		bsmg.registerParentedItemModel(container.asItem(), identifier);
	}
	public static void stairsModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { stairsModel(bsmg, container, baseBlock.asBlock()); }
	public static void stairsModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		TextureMap textures = TextureMap.all(baseBlock);
		Identifier identifier = Models.INNER_STAIRS.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.STAIRS.upload(block, textures, bsmg.modelCollector);
		Identifier identifier3 = Models.OUTER_STAIRS.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(block, identifier, identifier2, identifier3));
		bsmg.registerParentedItemModel(container.asItem(), identifier2);
	}
	public static void wallModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { wallModel(bsmg, container, baseBlock.asBlock()); }
	public static void wallModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) { wallModel(bsmg, container, TextureMap.all(baseBlock)); }
	public static void wallModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier texture) { wallModel(bsmg, container, TextureMap.all(texture)); }
	public static void wallModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier post, Identifier side, Identifier sideTall, Identifier inventory) {
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createWallBlockState(container.asBlock(), post, side, sideTall));
		bsmg.registerParentedItemModel(container.asItem(), inventory);
	}
	public static void wallModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, TextureMap textures) {
		Block block = container.asBlock();
		Identifier post = Models.TEMPLATE_WALL_POST.upload(block, textures, bsmg.modelCollector);
		Identifier side = Models.TEMPLATE_WALL_SIDE.upload(block, textures, bsmg.modelCollector);
		Identifier sideTall = Models.TEMPLATE_WALL_SIDE_TALL.upload(block, textures, bsmg.modelCollector);
		Identifier inventory = Models.WALL_INVENTORY.upload(block, textures, bsmg.modelCollector);
		wallModelCommon(bsmg, container, post, side, sideTall, inventory);
	}
	public static void copiedWallModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy) {
		Identifier id = getBlockModelId(copy.asBlock());
		Identifier post = postfixPath(id, "_post");
		Identifier side = postfixPath(id, "_side");
		Identifier sideTall = postfixPath(id, "_side_tall");
		Identifier inventory = postfixPath(id, "_inventory");
		wallModelCommon(bsmg, container, post, side, sideTall, inventory);
	}
	public static void doorModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		TextureMap textureMap = TextureMap.topBottom(block);
		Identifier identifier = Models.DOOR_BOTTOM.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier2 = Models.DOOR_BOTTOM_RH.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier3 = Models.DOOR_TOP.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier4 = Models.DOOR_TOP_RH.upload(block, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createDoorBlockState(block, identifier, identifier2, identifier3, identifier4));
		generatedItemModel(bsmg, container.asItem());
	}
	public static void trapdoorModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, boolean orientable) {
		Block block = container.asBlock();
		TextureMap textureMap = TextureMap.texture(block);
		Identifier identifier2;
		if (orientable) {
			Identifier identifier = Models.TEMPLATE_ORIENTABLE_TRAPDOOR_TOP.upload(block, textureMap, bsmg.modelCollector);
			identifier2 = Models.TEMPLATE_ORIENTABLE_TRAPDOOR_BOTTOM.upload(block, textureMap, bsmg.modelCollector);
			Identifier identifier3 = Models.TEMPLATE_ORIENTABLE_TRAPDOOR_OPEN.upload(block, textureMap, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createOrientableTrapdoorBlockState(block, identifier, identifier2, identifier3));
		}
		else {
			Identifier identifier = Models.TEMPLATE_TRAPDOOR_TOP.upload(block, textureMap, bsmg.modelCollector);
			identifier2 = Models.TEMPLATE_TRAPDOOR_BOTTOM.upload(block, textureMap, bsmg.modelCollector);
			Identifier identifier3 = Models.TEMPLATE_TRAPDOOR_OPEN.upload(block, textureMap, bsmg.modelCollector);
			bsmg.blockStateCollector.accept(BlockStateModelGenerator.createTrapdoorBlockState(block, identifier, identifier2, identifier3));
		}
		bsmg.registerParentedItemModel(container.asItem(), identifier2);
	}
	public static void glassTrapdoorModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block top, Block side) {
		Block block = container.asBlock();
		TextureMap textureMap = new TextureMap().put(TextureKey.TOP, TextureMap.getId(top)).put(TextureKey.SIDE, postfixPath(TextureMap.getId(side), "_top"));
		Identifier identifier = ModModels.TEMPLATE_GLASS_TRAPDOOR_TOP.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier2 = ModModels.TEMPLATE_GLASS_TRAPDOOR_BOTTOM.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier3 = ModModels.TEMPLATE_GLASS_TRAPDOOR_OPEN.upload(block, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createTrapdoorBlockState(block, identifier, identifier2, identifier3));
		bsmg.registerParentedItemModel(container.asItem(), identifier2);
	}
	public static void buttonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier unpressed, Identifier pressed, Identifier inventory) {
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createButtonBlockState(container.asBlock(), unpressed, pressed));
		bsmg.registerParentedItemModel(container.asItem(), inventory);
	}
	public static void buttonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { buttonModel(bsmg, container, baseBlock.asBlock()); }
	public static void buttonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		TextureMap textures = TextureMap.all(baseBlock);
		Identifier unpressed = Models.BUTTON.upload(block, textures, bsmg.modelCollector);
		Identifier pressed = Models.BUTTON_PRESSED.upload(block, textures, bsmg.modelCollector);
		Identifier inventory = Models.BUTTON_INVENTORY.upload(block, textures, bsmg.modelCollector);
		buttonModel(bsmg, container, unpressed, pressed, inventory);
	}
	public static void copiedButtonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy) {
		Identifier unpressed = getBlockModelId(copy.asBlock());
		Identifier pressed = postfixPath(unpressed, "_pressed");
		Identifier inventory = postfixPath(unpressed, "_inventory");
		buttonModel(bsmg, container, unpressed, pressed, inventory);
	}
	public static void fenceModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { fenceModel(bsmg, container, baseBlock.asBlock()); }
	public static void fenceModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		TextureMap textures = TextureMap.all(baseBlock);
		Identifier identifier = Models.FENCE_POST.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.FENCE_SIDE.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createFenceBlockState(block, identifier, identifier2));
		bsmg.registerParentedItemModel(container.asItem(), Models.FENCE_INVENTORY.upload(block, textures, bsmg.modelCollector));
	}
	public static void fenceGateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { fenceGateModel(bsmg, container, baseBlock.asBlock()); }
	public static void fenceGateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		TextureMap textures = TextureMap.all(baseBlock);
		Identifier identifier = Models.TEMPLATE_FENCE_GATE_OPEN.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.TEMPLATE_FENCE_GATE.upload(block, textures, bsmg.modelCollector);
		Identifier identifier3 = Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(block, textures, bsmg.modelCollector);
		Identifier identifier4 = Models.TEMPLATE_FENCE_GATE_WALL.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createFenceGateBlockState(block, identifier, identifier2, identifier3, identifier4));
		bsmg.registerParentedItemModel(container.asItem(), identifier2);
	}
	public static void pressurePlateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { pressurePlateModel(bsmg, container, baseBlock.asBlock()); }
	public static void pressurePlateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		TextureMap textures = TextureMap.all(baseBlock);
		Identifier identifier = Models.PRESSURE_PLATE_UP.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.PRESSURE_PLATE_DOWN.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createPressurePlateBlockState(block, identifier, identifier2));
		bsmg.registerParentedItemModel(container.asItem(), identifier);
	}
	public static void weightedPressurePlateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier up, Identifier down) {
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(container.asBlock()).coordinate(BlockStateModelGenerator.createValueFencedModelMap(Properties.POWER, 1, down, up)));
		bsmg.registerParentedItemModel(container.asItem(), up);
	}
	public static void weightedPressurePlateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		TextureMap textures = TextureMap.all(baseBlock);
		Identifier up = Models.PRESSURE_PLATE_UP.upload(block, textures, bsmg.modelCollector);
		Identifier down = Models.PRESSURE_PLATE_DOWN.upload(block, textures, bsmg.modelCollector);
		weightedPressurePlateModel(bsmg, container, up, down);
	}
	public static void copiedWeightedPressurePlateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer useModels) {
		Identifier up = ModelIds.getBlockModelId(useModels.asBlock());
		Identifier down = postfixPath(ModelIds.getBlockModelId(useModels.asBlock()), "_down");
		weightedPressurePlateModel(bsmg, container, up, down);
	}
	public static void signModel(BlockStateModelGenerator bsmg, SignContainer container, IBlockItemContainer baseBlock, Block hangingSignParticle) { signModel(bsmg, container, baseBlock.asBlock(), hangingSignParticle); }
	public static void signModel(BlockStateModelGenerator bsmg, SignContainer container, Block baseBlock, Block hangingSignParticle) {
		Block block = container.asBlock(), wallBlock = container.getWallBlock();
		Identifier identifier = Models.PARTICLE.upload(block, TextureMap.all(baseBlock), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier));
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(wallBlock, identifier));
		bsmg.excludeFromSimpleItemModelGeneration(wallBlock);
		generatedItemModel(bsmg, container.asItem());
		hangingSignModel(bsmg, hangingSignParticle, container.getHanging());
	}

	public static void hangingSignModel(BlockStateModelGenerator bsmg, Block particle, WallBlockContainer hangingSign) {
		TextureMap textureMap = TextureMap.particle(particle);
		Identifier identifier = Models.PARTICLE.upload(hangingSign.asBlock(), textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(hangingSign.asBlock(), identifier));
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(hangingSign.getWallBlock(), identifier));
		bsmg.excludeFromSimpleItemModelGeneration(hangingSign.getWallBlock());
		generatedItemModel(bsmg, hangingSign.asItem());
	}

	private static void torchModelCommon(BlockStateModelGenerator bsmg, TorchContainer container, Identifier lit, Identifier litWall, Identifier unlit, Identifier unlitWall, Model template, Model templateWall, ItemConvertible parentItem) {
		Block block = container.asBlock();
		TextureMap textures = TextureMap.torch(block), unlitTextures = new TextureMap().put(TextureKey.TORCH, getBlockModelId("unlit_", block));
		Identifier litModel = lit != null ? lit : template.upload(block, textures, bsmg.modelCollector);
		Identifier unlitModel = unlit != null ? unlit : template.upload(getBlockModelId("unlit_", block), unlitTextures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.LIT, litModel, unlitModel)));
		Block wallBlock = container.getWallBlock();
		litModel = litWall != null ? litWall : templateWall.upload(wallBlock, textures, bsmg.modelCollector);
		unlitModel = unlitWall != null ? unlitWall : templateWall.upload(getBlockModelId("unlit_", wallBlock), unlitTextures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(wallBlock)
				.coordinate(BlockStateVariantMap.create(Properties.LIT, Properties.HORIZONTAL_FACING)
						.register(true, Direction.EAST, BlockStateVariant.create()
								.put(VariantSettings.MODEL, litModel))
						.register(true, Direction.SOUTH, BlockStateVariant.create()
								.put(VariantSettings.MODEL, litModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
						.register(true, Direction.WEST, BlockStateVariant.create()
								.put(VariantSettings.MODEL, litModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
						.register(true, Direction.NORTH, BlockStateVariant.create()
								.put(VariantSettings.MODEL, litModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
						.register(false, Direction.EAST, BlockStateVariant.create()
								.put(VariantSettings.MODEL, unlitModel))
						.register(false, Direction.SOUTH, BlockStateVariant.create()
								.put(VariantSettings.MODEL, unlitModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
						.register(false, Direction.WEST, BlockStateVariant.create()
								.put(VariantSettings.MODEL, unlitModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
						.register(false, Direction.NORTH, BlockStateVariant.create()
								.put(VariantSettings.MODEL, unlitModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))));
		bsmg.excludeFromSimpleItemModelGeneration(wallBlock);
		if (parentItem == null) generatedItemModel(bsmg, container.asItem(), block);
		else parentedItem(bsmg, container.asItem(), parentItem);
	}
	private static void torchModelCommon(BlockStateModelGenerator bsmg, TorchContainer container, Identifier unlit, Identifier unlitWall, Model template, Model templateWall, ItemConvertible parentItem) {
		torchModelCommon(bsmg, container, null,  null, unlit, unlitWall, template, templateWall, parentItem);
	}
	private static void torchModelCommon(BlockStateModelGenerator bsmg, TorchContainer container, Identifier unlit, Identifier unlitWall) {
		torchModelCommon(bsmg, container, unlit, unlitWall, Models.TEMPLATE_TORCH, Models.TEMPLATE_TORCH_WALL, null);
	}
	public static void torchModel(BlockStateModelGenerator bsmg, TorchContainer container) { torchModelCommon(bsmg, container, null, null); }
	public static void torchModel(BlockStateModelGenerator bsmg, TorchContainer container, UnlitTorchContainer unlit) {
		torchModelCommon(bsmg, container, getBlockModelId(unlit.asBlock()), getBlockModelId(unlit.getWallBlock()));
	}
	public static void torchModel(BlockStateModelGenerator bsmg, TorchContainer container, TorchContainer base) {
		torchModelCommon(bsmg, container, getBlockModelId("unlit_", base.asBlock()), getBlockModelId("unlit_", base.getWallBlock()));
	}
	public static void copiedTorchModel(BlockStateModelGenerator bsmg, TorchContainer container, TorchContainer looksLike) {
		torchModelCommon(bsmg, container, getBlockModelId(looksLike.asBlock()), getBlockModelId(looksLike.getWallBlock()), getBlockModelId("unlit_", looksLike.asBlock()), getBlockModelId("unlit_", looksLike.getWallBlock()), Models.TEMPLATE_TORCH, Models.TEMPLATE_TORCH_WALL, looksLike);
	}
	private static void unlitTorchModelCommon(BlockStateModelGenerator bsmg, Block block, Block wallBlock, Identifier identifier, Identifier identifier2) {
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier));
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(wallBlock, BlockStateVariant.create().put(VariantSettings.MODEL, identifier2)).coordinate(BlockStateModelGenerator.createEastDefaultHorizontalRotationStates()));
	}
	private static void unlitTorchModel(BlockStateModelGenerator bsmg, UnlitTorchContainer container) {
		Block block = container.asBlock(), wallBlock = container.getWallBlock();
		TextureMap textures = TextureMap.torch(block);
		Identifier identifier = Models.TEMPLATE_TORCH.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.TEMPLATE_TORCH_WALL.upload(wallBlock, textures, bsmg.modelCollector);
		unlitTorchModelCommon(bsmg, block, wallBlock, identifier, identifier2);
	}

	private static void boneTorchModelCommon(BlockStateModelGenerator bsmg, TorchContainer container, Identifier unlit, Identifier unlitWall) {
		torchModelCommon(bsmg, container, unlit, unlitWall, ModModels.TEMPLATE_BONE_TORCH, ModModels.TEMPLATE_BONE_TORCH_WALL, null);
	}
	public static void boneTorchModel(BlockStateModelGenerator bsmg, TorchContainer container) { boneTorchModelCommon(bsmg, container, null, null); }
	public static void boneTorchModel(BlockStateModelGenerator bsmg, TorchContainer container, TorchContainer base) {
		boneTorchModelCommon(bsmg, container, getBlockModelId("unlit_", base.asBlock()), getBlockModelId("unlit_", base.getWallBlock()));
	}

	public static void lanternModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier lit, Identifier litHanging, Identifier unlit, Identifier unlitHanging) {
		Block block = container.asBlock();
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(Properties.LIT, Properties.HANGING)
						.register(true, true, BlockStateVariant.create().put(VariantSettings.MODEL, litHanging))
						.register(true, false, BlockStateVariant.create().put(VariantSettings.MODEL, lit))
						.register(false, true, BlockStateVariant.create().put(VariantSettings.MODEL, unlitHanging))
						.register(false, false, BlockStateVariant.create().put(VariantSettings.MODEL, unlit))));
	}
	public static TextureMap unlitLanternTextureMap(Block block) {
		return new TextureMap().put(TextureKey.LANTERN, getBlockModelId("unlit_", block));
	}
	public static void lanternModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier unlit) {
		Block block = container.asBlock();
		Identifier litModel = TexturedModel.TEMPLATE_LANTERN.upload(block, bsmg.modelCollector);
		Identifier litHanging = TexturedModel.TEMPLATE_HANGING_LANTERN.upload(block, bsmg.modelCollector);
		Identifier unlitModel = unlit != null ? unlit : Models.TEMPLATE_LANTERN.upload(getBlockModelId("unlit_", block), unlitLanternTextureMap(block), bsmg.modelCollector);
		Identifier unlitHanging = unlit != null ? postfixPath(unlit, "_hanging") : Models.TEMPLATE_HANGING_LANTERN.upload(getBlockModelId("unlit_", block, "_hanging"), unlitLanternTextureMap(block), bsmg.modelCollector);
		lanternModelCommon(bsmg, container, litModel, litHanging, unlitModel, unlitHanging);
		generatedItemModel(bsmg, container.asItem());
	}
	public static void lanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) { lanternModelCommon(bsmg, container, null); }
	public static void lanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer base) { lanternModel(bsmg, container, base.asBlock()); }
	public static void lanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block base) {
		Identifier id = getBlockModelId(base);
		if (!id.getPath().startsWith("block/unlit_")) id = getBlockModelId("unlit_", base);
		lanternModelCommon(bsmg, container, id);
	}
	public static void copiedLanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy) { copiedLanternModel(bsmg, container, copy, copy); }
	public static void copiedLanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy, IBlockItemContainer unlit) {
		Identifier litModel = getBlockModelId(copy.asBlock());
		Identifier litHanging = postfixPath(litModel, "_hanging");
		Identifier unlitModel = getBlockModelId("unlit_", unlit.asBlock());
		Identifier unlitHanging = postfixPath(unlitModel, "_hanging");
		lanternModelCommon(bsmg, container, litModel, litHanging, unlitModel, unlitHanging);
		parentedItem(bsmg, container.asItem(), copy);
	}
	private static void unlitLanternModelCommon(BlockStateModelGenerator bsmg, Block block, Identifier identifier, Identifier identifier2) {
		bsmg.excludeFromSimpleItemModelGeneration(block);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.HANGING, identifier2, identifier)));
	}
	public static void unlitLanternModel(BlockStateModelGenerator bsmg, Block block) {
		Identifier identifier = TexturedModel.TEMPLATE_LANTERN.upload(block, bsmg.modelCollector);
		Identifier identifier2 = TexturedModel.TEMPLATE_HANGING_LANTERN.upload(block, bsmg.modelCollector);
		unlitLanternModelCommon(bsmg, block, identifier, identifier2);
	}
	public static void unlitLanternModel(BlockStateModelGenerator bsmg, Block block, Block copyModel) {
		Identifier identifier = getBlockModelId(copyModel);
		Identifier identifier2 = postfixPath(identifier, "_hanging");
		unlitLanternModelCommon(bsmg, block, identifier, identifier2);
	}
	private static void campfireModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier off) {
		Block block = container.asBlock();
		Identifier identifier2 = Models.TEMPLATE_CAMPFIRE.upload(block, TextureMap.campfire(block), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.LIT, identifier2, off)).coordinate(BlockStateModelGenerator.createSouthDefaultHorizontalRotationStates()));
		generatedItemModel(bsmg, container.asItem());
	}
	public static void campfireModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		TextureMap textures = new TextureMap().put(ModTextureKeys.LOG, TextureMap.getSubId(block, "_log"));
		Identifier identifier = ModModels.TEMPLATE_CAMPFIRE_OFF.upload(block, textures, bsmg.modelCollector);
		campfireModelCommon(bsmg, container, identifier);
	}
	public static void campfireModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block base) { campfireModelCommon(bsmg, container, TextureMap.getSubId(base, "_off")); }
	public static void candleModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block cake) {
		Block candle = container.asBlock();
		generatedItemModel(bsmg, container.asItem());
		TextureMap textureMap = TextureMap.all(TextureMap.getId(candle));
		TextureMap textureMap2 = TextureMap.all(TextureMap.getSubId(candle, "_lit"));
		Identifier identifier = Models.TEMPLATE_CANDLE.upload(candle, "_one_candle", textureMap, bsmg.modelCollector);
		Identifier identifier2 = Models.TEMPLATE_TWO_CANDLES.upload(candle, "_two_candles", textureMap, bsmg.modelCollector);
		Identifier identifier3 = Models.TEMPLATE_THREE_CANDLES.upload(candle, "_three_candles", textureMap, bsmg.modelCollector);
		Identifier identifier4 = Models.TEMPLATE_FOUR_CANDLES.upload(candle, "_four_candles", textureMap, bsmg.modelCollector);
		Identifier identifier5 = Models.TEMPLATE_CANDLE.upload(candle, "_one_candle_lit", textureMap2, bsmg.modelCollector);
		Identifier identifier6 = Models.TEMPLATE_TWO_CANDLES.upload(candle, "_two_candles_lit", textureMap2, bsmg.modelCollector);
		Identifier identifier7 = Models.TEMPLATE_THREE_CANDLES.upload(candle, "_three_candles_lit", textureMap2, bsmg.modelCollector);
		Identifier identifier8 = Models.TEMPLATE_FOUR_CANDLES.upload(candle, "_four_candles_lit", textureMap2, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(candle)
				.coordinate(BlockStateVariantMap.create(Properties.CANDLES, Properties.LIT)
						.register(1, false, BlockStateVariant.create().put(VariantSettings.MODEL, identifier))
						.register(2, false, BlockStateVariant.create().put(VariantSettings.MODEL, identifier2))
						.register(3, false, BlockStateVariant.create().put(VariantSettings.MODEL, identifier3))
						.register(4, false, BlockStateVariant.create().put(VariantSettings.MODEL, identifier4))
						.register(1, true, BlockStateVariant.create().put(VariantSettings.MODEL, identifier5))
						.register(2, true, BlockStateVariant.create().put(VariantSettings.MODEL, identifier6))
						.register(3, true, BlockStateVariant.create().put(VariantSettings.MODEL, identifier7))
						.register(4, true, BlockStateVariant.create().put(VariantSettings.MODEL, identifier8))));
		Identifier identifier9 = Models.TEMPLATE_CAKE_WITH_CANDLE.upload(cake, TextureMap.candleCake(candle, false), bsmg.modelCollector);
		Identifier identifier10 = Models.TEMPLATE_CAKE_WITH_CANDLE.upload(cake, "_lit", TextureMap.candleCake(candle, true), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(cake).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.LIT, identifier10, identifier9)));
	}
	private static void barsModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier ends, Identifier post, Identifier cap, Identifier capAlt, Identifier side, Identifier sideAlt) {
		Block block = container.asBlock();
		bsmg.blockStateCollector.accept(MultipartBlockStateSupplier.create(block)
				.with(BlockStateVariant.create().put(VariantSettings.MODEL, ends))
				.with(When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, false),
						BlockStateVariant.create().put(VariantSettings.MODEL, post))
				.with(When.create().set(Properties.NORTH, true).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, false),
						BlockStateVariant.create().put(VariantSettings.MODEL, cap))
				.with(When.create().set(Properties.NORTH, false).set(Properties.EAST, true).set(Properties.SOUTH, false).set(Properties.WEST, false),
						BlockStateVariant.create().put(VariantSettings.MODEL, cap).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.with(When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, true).set(Properties.WEST, false),
						BlockStateVariant.create().put(VariantSettings.MODEL, capAlt))
				.with(When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, true),
						BlockStateVariant.create().put(VariantSettings.MODEL, capAlt).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, side))
				.with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, side).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, sideAlt)).with(When.create().set(Properties.WEST, true),
						BlockStateVariant.create().put(VariantSettings.MODEL, sideAlt).put(VariantSettings.Y, VariantSettings.Rotation.R90)));
	}
	public static void barsModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier texture = TextureMap.getId(block);
		TextureMap textureMap = new TextureMap().put(TextureKey.PARTICLE, texture).put(ModTextureKeys.BARS, texture).put(TextureKey.EDGE, texture);
		Identifier ends = ModModels.TEMPLATE_BARS_POST_ENDS.upload(block, textureMap, bsmg.modelCollector);
		Identifier post = ModModels.TEMPLATE_BARS_POST.upload(block, textureMap, bsmg.modelCollector);
		Identifier cap = ModModels.TEMPLATE_BARS_CAP.upload(block, textureMap, bsmg.modelCollector);
		Identifier capAlt = ModModels.TEMPLATE_BARS_CAP_ALT.upload(block, textureMap, bsmg.modelCollector);
		Identifier side = ModModels.TEMPLATE_BARS_SIDE.upload(block, textureMap, bsmg.modelCollector);
		Identifier sideAlt = ModModels.TEMPLATE_BARS_SIDE_ALT.upload(block, textureMap, bsmg.modelCollector);
		barsModelCommon(bsmg, container, ends, post, cap, capAlt, side, sideAlt);
		generatedItemModel(bsmg, container.asItem(), block);
	}
	public static void copiedBarsModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy) {
		Identifier copyId = getBlockModelId(copy.asBlock());
		Identifier ends = postfixPath(copyId, "_post_ends");
		Identifier post = postfixPath(copyId, "_post");
		Identifier cap = postfixPath(copyId, "_cap");
		Identifier capAlt = postfixPath(copyId, "_cap_alt");
		Identifier side = postfixPath(copyId, "_side");
		Identifier sideAlt = postfixPath(copyId, "_side_alt");
		barsModelCommon(bsmg, container, ends, post, cap, capAlt, side, sideAlt);
		parentedItem(bsmg, container.asItem(), copy);
	}
	public static void topBottomSidesModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block top, Block bottom) {
		Block block = container.asBlock();
		TextureMap textureMap = new TextureMap()
				.put(TextureKey.BOTTOM, TextureMap.getId(bottom))
				.put(TextureKey.TOP, TextureMap.getId(top))
				.put(TextureKey.SIDE, TextureMap.getSubId(block, "_side"));
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, Models.CUBE_BOTTOM_TOP.upload(block, textureMap, bsmg.modelCollector)));
		parentedItem(bsmg, container.asItem(), block);
	}
	public static TextureMap uniqueSidesTextureMap(Identifier base, String particle, String up, String down, String north, String south, String east, String west) {
		return new TextureMap()
				.put(TextureKey.PARTICLE, postfixPath(base, particle))
				.put(TextureKey.UP, postfixPath(base, up))
				.put(TextureKey.DOWN, postfixPath(base, down))
				.put(TextureKey.NORTH, postfixPath(base, north))
				.put(TextureKey.SOUTH, postfixPath(base, south))
				.put(TextureKey.EAST, postfixPath(base, east))
				.put(TextureKey.WEST, postfixPath(base, west));
	}
	public static void chainModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier model) {
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(container.asBlock(),
						BlockStateVariant.create().put(VariantSettings.MODEL, model))
				.coordinate(BlockStateModelGenerator.createAxisRotatedVariantMap()));
		generatedItemModel(bsmg, container.asItem());
	}
	public static void chainModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		chainModel(bsmg, container, ModModels.TEMPLATE_CHAIN.upload(block, TextureMap.all(block), bsmg.modelCollector));
	}
	public static void copiedChainModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer copy) {
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(container.asBlock(),
						BlockStateVariant.create().put(VariantSettings.MODEL, getBlockModelId(copy.asBlock())))
				.coordinate(BlockStateModelGenerator.createAxisRotatedVariantMap()));
		parentedItem(bsmg, container.asItem(), copy);
	}
	public static void metalPillarModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer nonPillar) {
		Block block = container.asBlock();
		Identifier end = TextureMap.getId(nonPillar.asBlock()), side = TextureMap.getId(block);
		Identifier identifier = Models.CUBE_COLUMN.upload(block, TextureMap.sideEnd(side, end), bsmg.modelCollector);
		Identifier identifier2 = Models.CUBE_COLUMN_HORIZONTAL.upload(block, TextureMap.sideEnd(postfixPath(side, "_horizontal"), end), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(block, identifier, identifier2));
		parentedItem(bsmg, container.asItem(), block);
	}
	public static void uprightPillarModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(block, Models.CUBE_COLUMN.upload(block, TextureMap.sideEnd(block), bsmg.modelCollector)));
		parentedItem(bsmg, container.asItem(), block);
	}
	public static void ladderModelCommon(BlockStateModelGenerator bsmg, Block block, Item item, Identifier model) {
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, model))
				.coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
		if (item != null) generatedItemModel(bsmg, item, block);
		else bsmg.excludeFromSimpleItemModelGeneration(block);
	}
	public static void ladderModelCommon(BlockStateModelGenerator bsmg, Block block, Item item, Model template) {
		ladderModelCommon(bsmg, block, item, template.upload(block, TextureMap.texture(block), bsmg.modelCollector));
	}
	public static void ladderModel(BlockStateModelGenerator bsmg, Block block, Item item) {
		ladderModelCommon(bsmg, block, item, ModModels.TEMPLATE_LADDER);
	}
	public static void ladderModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		ladderModelCommon(bsmg, container.asBlock(), container.asItem(), ModModels.TEMPLATE_LADDER);
	}
	public static void boneLadderModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		ladderModelCommon(bsmg, block, container.asItem(), getBlockModelId(block));
	}

	public final void glazedTerracottaModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier identifier = TexturedModel.TEMPLATE_GLAZED_TERRACOTTA.upload(block, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).coordinate(BlockStateModelGenerator.createSouthDefaultHorizontalRotationStates()));
		bsmg.registerParentedItemModel(container.asItem(), identifier);
	}

	public static void rainbowBlockModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier base = getBlockModelId(block);
		String particle = "_particle", p = "_p", r = "_r", hpr = "_horiz_p_r", hrp = "_horiz_r_p", vpr = "_vert_p_r", vrp = "_vert_r_p";
		Identifier up = Models.CUBE.upload(block, "_up", uniqueSidesTextureMap(base, particle, r, p, vrp, vrp, vrp, vrp), bsmg.modelCollector);
		Identifier down = Models.CUBE.upload(block, "_down", uniqueSidesTextureMap(base, particle, p, r, vpr, vpr, vpr, vpr), bsmg.modelCollector);
		Identifier north = Models.CUBE.upload(block, "_north", uniqueSidesTextureMap(base, particle, vpr, vrp, p, r, hrp, hpr), bsmg.modelCollector);
		Identifier south = Models.CUBE.upload(block, "_south", uniqueSidesTextureMap(base, particle, hrp, hrp, hpr, hrp, p, r), bsmg.modelCollector);
		Identifier east = Models.CUBE.upload(block, "_east", uniqueSidesTextureMap(base, particle, vrp, vpr, r, p, hpr, hrp), bsmg.modelCollector);
		Identifier west = Models.CUBE.upload(block, "_west", uniqueSidesTextureMap(base, particle, hpr, hpr, hrp, hpr, r, p), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(Properties.FACING)
						.register(Direction.DOWN, BlockStateVariant.create().put(VariantSettings.MODEL, up))
						.register(Direction.UP, BlockStateVariant.create().put(VariantSettings.MODEL, down))
						.register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, north))
						.register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, south))
						.register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, east))
						.register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, west))));
		bsmg.registerParentedItemModel(container.asItem(), north);
	}
	public static void rainbowSlabModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		Identifier base = getBlockModelId(baseBlock);
		String particle = "_particle", p = "_p", r = "_r", hpr = "_horiz_p_r", hrp = "_horiz_r_p", vpr = "_vert_p_r", vrp = "_vert_r_p";
		TextureMap n = uniqueSidesTextureMap(base, particle, vpr, vrp, p, r, hrp, hpr);
		Identifier north = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB.upload(block, "_north", n, bsmg.modelCollector);
		Identifier north_top = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB_TOP.upload(block, "_north", n, bsmg.modelCollector);
		TextureMap s = uniqueSidesTextureMap(base, particle, hrp, hrp, hpr, hrp, p, r);
		Identifier south = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB.upload(block, "_south", s, bsmg.modelCollector);
		Identifier south_top = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB_TOP.upload(block, "_south", s, bsmg.modelCollector);
		TextureMap e = uniqueSidesTextureMap(base, particle, vrp, vpr, r, p, hpr, hrp);
		Identifier east = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB.upload(block, "_east", e, bsmg.modelCollector);
		Identifier east_top = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB_TOP.upload(block, "_east", e, bsmg.modelCollector);
		TextureMap w = uniqueSidesTextureMap(base, particle, hpr, hpr, hrp, hpr, r, p);
		Identifier west = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB.upload(block, "_west", w, bsmg.modelCollector);
		Identifier west_top = ModModels.TEMPLATE_MULTI_TEXTURE_SLAB_TOP.upload(block, "_west", w, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.SLAB_TYPE)
						.register(Direction.NORTH, SlabType.BOTTOM, BlockStateVariant.create().put(VariantSettings.MODEL, north))
						.register(Direction.EAST, SlabType.BOTTOM, BlockStateVariant.create().put(VariantSettings.MODEL, south))
						.register(Direction.SOUTH, SlabType.BOTTOM, BlockStateVariant.create().put(VariantSettings.MODEL, east))
						.register(Direction.WEST, SlabType.BOTTOM, BlockStateVariant.create().put(VariantSettings.MODEL, west))
						.register(Direction.NORTH, SlabType.DOUBLE, BlockStateVariant.create().put(VariantSettings.MODEL, postfixPath(base, "_east")))
						.register(Direction.EAST, SlabType.DOUBLE, BlockStateVariant.create().put(VariantSettings.MODEL, postfixPath(base, "_south")))
						.register(Direction.SOUTH, SlabType.DOUBLE, BlockStateVariant.create().put(VariantSettings.MODEL, postfixPath(base, "_east")))
						.register(Direction.WEST, SlabType.DOUBLE, BlockStateVariant.create().put(VariantSettings.MODEL, postfixPath(base, "_west")))
						.register(Direction.NORTH, SlabType.TOP, BlockStateVariant.create().put(VariantSettings.MODEL, north_top))
						.register(Direction.EAST, SlabType.TOP, BlockStateVariant.create().put(VariantSettings.MODEL, south_top))
						.register(Direction.SOUTH, SlabType.TOP, BlockStateVariant.create().put(VariantSettings.MODEL, east_top))
						.register(Direction.WEST, SlabType.TOP, BlockStateVariant.create().put(VariantSettings.MODEL, west_top))
				));
		bsmg.registerParentedItemModel(container.asItem(), north);
	}
	public static void rainbowCarpetModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		Identifier base = getBlockModelId(baseBlock);
		String particle = "_particle", p = "_p", r = "_r", hpr = "_horiz_p_r", hrp = "_horiz_r_p", vpr = "_vert_p_r", vrp = "_vert_r_p";
		Identifier north = ModModels.TEMPLATE_MULTI_TEXTURE_CARPET.upload(block, "_north", uniqueSidesTextureMap(base, particle, vpr, vrp, p, r, hrp, hpr), bsmg.modelCollector);
		Identifier south = ModModels.TEMPLATE_MULTI_TEXTURE_CARPET.upload(block, "_south", uniqueSidesTextureMap(base, particle, hrp, hrp, hpr, hrp, p, r), bsmg.modelCollector);
		Identifier east = ModModels.TEMPLATE_MULTI_TEXTURE_CARPET.upload(block, "_east", uniqueSidesTextureMap(base, particle, vrp, vpr, r, p, hpr, hrp), bsmg.modelCollector);
		Identifier west = ModModels.TEMPLATE_MULTI_TEXTURE_CARPET.upload(block, "_west", uniqueSidesTextureMap(base, particle, hpr, hpr, hrp, hpr, r, p), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
						.register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, north))
						.register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, south))
						.register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, east))
						.register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, west))));
		bsmg.registerParentedItemModel(container.asItem(), north);
	}
	public static void bedModel(BlockStateModelGenerator bsmg, BedContainer bed, Block particle) {
		bedModel(bsmg, bed, TextureMap.getId(particle));
	}
	public static void bedModel(BlockStateModelGenerator bsmg, BedContainer bed, Identifier particle) {
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(bed.asBlock(), ID("minecraft:block/bed")));
		Models.TEMPLATE_BED.upload(getItemModelId(bed.asItem()), new TextureMap().put(TextureKey.PARTICLE, particle), bsmg.modelCollector);
	}
	public static void mudBricksModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		TextureMap map = TextureMap.all(block);
		Identifier identifier = Models.CUBE_ALL.upload(block, map, bsmg.modelCollector);
		Identifier identifier2 = ModModels.CUBE_NORTH_WEST_MIRRORED_ALL.upload(block, map, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier2));
		bsmg.registerParentedItemModel(container.asItem(), identifier);
	}
	public static void woodcutterModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer block) {
		woodcutterModel(bsmg, container, block.asBlock());
	}
	public static void woodcutterModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block planks) {
		Block block = container.asBlock();
		TextureMap textureMap = new TextureMap().put(ModTextureKeys.PLANKS, TextureMap.getId(planks));
		Identifier identifier = ModModels.TEMPLATE_WOODCUTTER.upload(block, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
		bsmg.registerParentedItemModel(container.asItem(), identifier);
	}
	private static void barrelModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier identifier = TextureMap.getSubId(block, "_top_open");
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(bsmg.createUpDefaultFacingVariantMap())
				.coordinate(BlockStateVariantMap.create(Properties.OPEN)
						.register(false, BlockStateVariant.create()
								.put(VariantSettings.MODEL, TexturedModel.CUBE_BOTTOM_TOP.upload(block, bsmg.modelCollector)))
						.register(true, BlockStateVariant.create()
								.put(VariantSettings.MODEL, TexturedModel.CUBE_BOTTOM_TOP.get(block)
										.textures(textureMap -> textureMap.put(TextureKey.TOP, identifier))
								.upload(block, "_open", bsmg.modelCollector)))));
		bsmg.registerParentedItemModel(container.asItem(), getBlockModelId(block));
	}
	private static void lecternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer planks) {
		lecternModel(bsmg, container, planks.asBlock());
	}
	private static void lecternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block planks) {
		Block block = container.asBlock();
		TextureMap map = TextureMap.of(TextureKey.BOTTOM, TextureMap.getId(planks))
				.put(ModTextureKeys.BASE, TextureMap.getSubId(block, "_base"))
				.put(TextureKey.FRONT, TextureMap.getSubId(block, "_front"))
				.put(ModTextureKeys.SIDES, TextureMap.getSubId(block, "_sides"))
				.put(TextureKey.TOP, TextureMap.getSubId(block, "_top"));
		Identifier identifier = ModModels.TEMPLATE_LECTERN.upload(block, map, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
				BlockStateVariant.create().put(VariantSettings.MODEL, identifier))
				.coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
		bsmg.registerParentedItemModel(container.asItem(), identifier);
	}
	public static void bookshelfModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer block) {
		bookshelfModel(bsmg, container, block.asBlock());
	}
	public static void bookshelfModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block planks) {
		Block block = container.asBlock();
		TextureMap textureMap = TextureMap.sideEnd(TextureMap.getId(block), TextureMap.getId(planks));
		Identifier identifier = Models.CUBE_COLUMN.upload(block, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier));
		bsmg.registerParentedItemModel(container.asItem(), identifier);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator bsmg) {
		//Overrides
		ladderModel(bsmg, Blocks.LADDER, null);
		//Light Sources
		unlitTorchModel(bsmg, UNLIT_TORCH);
		unlitTorchModelCommon(bsmg, UNLIT_SOUL_TORCH.asBlock(), UNLIT_SOUL_TORCH.getWallBlock(), getBlockModelId(UNLIT_TORCH.asBlock()), getBlockModelId(UNLIT_TORCH.getWallBlock()));
		torchModel(bsmg, UNDERWATER_TORCH, UNLIT_TORCH);
		//Ender Fire
		torchModel(bsmg, ENDER_TORCH, UNLIT_TORCH);
		unlitLanternModel(bsmg, UNLIT_LANTERN);
		unlitLanternModel(bsmg, UNLIT_SOUL_LANTERN, UNLIT_LANTERN);
		lanternModel(bsmg, ENDER_LANTERN, UNLIT_LANTERN);
		campfireModel(bsmg, ENDER_CAMPFIRE, Blocks.CAMPFIRE);
		//Candles & Cakes
		candleModel(bsmg, SOUL_CANDLE, SOUL_CANDLE_CAKE);
		candleModel(bsmg, ENDER_CANDLE, ENDER_CANDLE_CAKE);
		//Extended Stone
		wallModel(bsmg, POLISHED_ANDESITE_WALL, Blocks.POLISHED_ANDESITE);
		wallModel(bsmg, POLISHED_DIORITE_WALL, Blocks.POLISHED_DIORITE);
		wallModel(bsmg, POLISHED_GRANITE_WALL, Blocks.POLISHED_GRANITE);
		wallModel(bsmg, SMOOTH_SANDSTONE_WALL, TextureMap.getSubId(Blocks.SANDSTONE, "_top"));
		wallModel(bsmg, SMOOTH_RED_SANDSTONE_WALL, TextureMap.getSubId(Blocks.RED_SANDSTONE, "_top"));
		wallModel(bsmg, DARK_PRISMARINE_WALL, Blocks.DARK_PRISMARINE);
		//Obsidian
		stairsModel(bsmg, OBSIDIAN_STAIRS, Blocks.OBSIDIAN);
		slabModel(bsmg, OBSIDIAN_SLAB, Blocks.OBSIDIAN);
		wallModel(bsmg, OBSIDIAN_WALL, Blocks.OBSIDIAN);
		stairsModel(bsmg, CRYING_OBSIDIAN_STAIRS, Blocks.CRYING_OBSIDIAN);
		slabModel(bsmg, CRYING_OBSIDIAN_SLAB, Blocks.CRYING_OBSIDIAN);
		wallModel(bsmg, CRYING_OBSIDIAN_WALL, Blocks.CRYING_OBSIDIAN);
		cubeAllModel(bsmg, BLEEDING_OBSIDIAN);
		stairsModel(bsmg, BLEEDING_OBSIDIAN_STAIRS, BLEEDING_OBSIDIAN);
		slabModel(bsmg, BLEEDING_OBSIDIAN_SLAB, BLEEDING_OBSIDIAN);
		wallModel(bsmg, BLEEDING_OBSIDIAN_WALL, BLEEDING_OBSIDIAN);
		//Purpur
		wallModel(bsmg, PURPUR_WALL, Blocks.PURPUR_BLOCK);
		cubeAllModel(bsmg, SMOOTH_PURPUR);
		stairsModel(bsmg, SMOOTH_PURPUR_STAIRS, SMOOTH_PURPUR);
		slabModel(bsmg, SMOOTH_PURPUR_SLAB, SMOOTH_PURPUR);
		wallModel(bsmg, SMOOTH_PURPUR_WALL, SMOOTH_PURPUR);
		cubeAllModel(bsmg, PURPUR_BRICKS);
		stairsModel(bsmg, PURPUR_BRICK_STAIRS, PURPUR_BRICKS);
		slabModel(bsmg, PURPUR_BRICK_SLAB, PURPUR_BRICKS);
		wallModel(bsmg, PURPUR_BRICK_WALL, PURPUR_BRICKS);
		//Extended Calcite
		slabModel(bsmg, CALCITE_SLAB, Blocks.CALCITE);
		stairsModel(bsmg, CALCITE_STAIRS, Blocks.CALCITE);
		wallModel(bsmg, CALCITE_WALL, Blocks.CALCITE);
		cubeAllModel(bsmg, SMOOTH_CALCITE);
		slabModel(bsmg, SMOOTH_CALCITE_SLAB, SMOOTH_CALCITE);
		stairsModel(bsmg, SMOOTH_CALCITE_STAIRS, SMOOTH_CALCITE);
		wallModel(bsmg, SMOOTH_CALCITE_WALL, SMOOTH_CALCITE);
		cubeAllModel(bsmg, CALCITE_BRICKS);
		slabModel(bsmg, CALCITE_BRICK_SLAB, CALCITE_BRICKS);
		stairsModel(bsmg, CALCITE_BRICK_STAIRS, CALCITE_BRICKS);
		wallModel(bsmg, CALCITE_BRICK_WALL, CALCITE_BRICKS);
		//Extended Dripstone
		slabModel(bsmg, DRIPSTONE_SLAB, Blocks.DRIPSTONE_BLOCK);
		stairsModel(bsmg, DRIPSTONE_STAIRS, Blocks.DRIPSTONE_BLOCK);
		wallModel(bsmg, DRIPSTONE_WALL, Blocks.DRIPSTONE_BLOCK);
		cubeAllModel(bsmg, SMOOTH_DRIPSTONE);
		slabModel(bsmg, SMOOTH_DRIPSTONE_SLAB, SMOOTH_DRIPSTONE);
		stairsModel(bsmg, SMOOTH_DRIPSTONE_STAIRS, SMOOTH_DRIPSTONE);
		wallModel(bsmg, SMOOTH_DRIPSTONE_WALL, SMOOTH_DRIPSTONE);
		cubeAllModel(bsmg, DRIPSTONE_BRICKS);
		slabModel(bsmg, DRIPSTONE_BRICK_SLAB, DRIPSTONE_BRICKS);
		stairsModel(bsmg, DRIPSTONE_BRICK_STAIRS, DRIPSTONE_BRICKS);
		wallModel(bsmg, DRIPSTONE_BRICK_WALL, DRIPSTONE_BRICKS);
		//Extended Tuff
		slabModel(bsmg, TUFF_SLAB, Blocks.TUFF);
		stairsModel(bsmg, TUFF_STAIRS, Blocks.TUFF);
		wallModel(bsmg, TUFF_WALL, Blocks.TUFF);
		cubeAllModel(bsmg, SMOOTH_TUFF);
		slabModel(bsmg, SMOOTH_TUFF_SLAB, SMOOTH_TUFF);
		stairsModel(bsmg, SMOOTH_TUFF_STAIRS, SMOOTH_TUFF);
		wallModel(bsmg, SMOOTH_TUFF_WALL, SMOOTH_TUFF);
		cubeAllModel(bsmg, TUFF_BRICKS);
		slabModel(bsmg, TUFF_BRICK_SLAB, TUFF_BRICKS);
		stairsModel(bsmg, TUFF_BRICK_STAIRS, TUFF_BRICKS);
		wallModel(bsmg, TUFF_BRICK_WALL, TUFF_BRICKS);
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
		generatedItemModels(bsmg, AMETHYST_AXE, AMETHYST_HOE, AMETHYST_PICKAXE, AMETHYST_SHOVEL, AMETHYST_SWORD, AMETHYST_KNIFE);
		generatedItemModels(bsmg, AMETHYST_HELMET, AMETHYST_CHESTPLATE, AMETHYST_LEGGINGS, AMETHYST_BOOTS, AMETHYST_HORSE_ARMOR);
		//Arrows
		generatedItemModel(bsmg, AMETHYST_ARROW.asItem());
		generatedItemModel(bsmg, CHORUS_ARROW.asItem());
		//More Glass
		paneModel(bsmg, TINTED_GLASS_PANE, Blocks.TINTED_GLASS);
		glassSlabModel(bsmg, TINTED_GLASS_SLAB, Blocks.TINTED_GLASS);
		glassTrapdoorModel(bsmg, TINTED_GLASS_TRAPDOOR, Blocks.TINTED_GLASS, TINTED_GLASS_PANE.asBlock());
		glassSlabModel(bsmg, GLASS_SLAB, Blocks.GLASS);
		glassTrapdoorModel(bsmg, GLASS_TRAPDOOR, Blocks.GLASS, Blocks.GLASS_PANE);
		for(DyeColor color : DyeColor.values()){
			glassSlabModel(bsmg, STAINED_GLASS_SLABS.get(color), ColorUtil.GetStainedGlassBlock(color));
			glassTrapdoorModel(bsmg, STAINED_GLASS_TRAPDOORS.get(color), ColorUtil.GetStainedGlassBlock(color), ColorUtil.GetStainedGlassPaneBlock(color));
		}
		generatedItemModel(bsmg, TINTED_GOGGLES);
		//Extended Emerald
		cubeAllModel(bsmg, EMERALD_BRICKS);
		slabModel(bsmg, EMERALD_BRICK_SLAB, EMERALD_BRICKS);
		stairsModel(bsmg, EMERALD_BRICK_STAIRS, EMERALD_BRICKS);
		wallModel(bsmg, EMERALD_BRICK_WALL, EMERALD_BRICKS);
		cubeAllModel(bsmg, CUT_EMERALD);
		slabModel(bsmg, CUT_EMERALD_SLAB, CUT_EMERALD);
		stairsModel(bsmg, CUT_EMERALD_STAIRS, CUT_EMERALD);
		wallModel(bsmg, CUT_EMERALD_WALL, EMERALD_BRICKS);
		generatedItemModels(bsmg, EMERALD_AXE, EMERALD_HOE, EMERALD_PICKAXE, EMERALD_SHOVEL, EMERALD_SWORD, EMERALD_KNIFE);
		generatedItemModels(bsmg, EMERALD_HELMET, EMERALD_CHESTPLATE, EMERALD_LEGGINGS, EMERALD_BOOTS, EMERALD_HORSE_ARMOR);
		//Extended Diamond
		slabModel(bsmg, DIAMOND_SLAB, Blocks.DIAMOND_BLOCK);
		stairsModel(bsmg, DIAMOND_STAIRS, Blocks.DIAMOND_BLOCK);
		wallModel(bsmg, DIAMOND_WALL, Blocks.DIAMOND_BLOCK);
		cubeAllModel(bsmg, DIAMOND_BRICKS);
		slabModel(bsmg, DIAMOND_BRICK_SLAB, DIAMOND_BRICKS);
		stairsModel(bsmg, DIAMOND_BRICK_STAIRS, DIAMOND_BRICKS);
		wallModel(bsmg, DIAMOND_BRICK_WALL, DIAMOND_BRICKS);
		//Extended Quartz
		cubeAllModel(bsmg, QUARTZ_CRYSTAL_BLOCK);
		slabModel(bsmg, QUARTZ_CRYSTAL_SLAB, QUARTZ_CRYSTAL_BLOCK);
		stairsModel(bsmg, QUARTZ_CRYSTAL_STAIRS, QUARTZ_CRYSTAL_BLOCK);
		wallModel(bsmg, QUARTZ_CRYSTAL_WALL, QUARTZ_CRYSTAL_BLOCK);
		wallModel(bsmg, SMOOTH_QUARTZ_WALL, new Identifier("block/quartz_block_bottom"));
		wallModel(bsmg, QUARTZ_WALL, new Identifier("block/quartz_block_side"));
		slabModel(bsmg, QUARTZ_BRICK_SLAB, Blocks.QUARTZ_BRICKS);
		stairsModel(bsmg, QUARTZ_BRICK_STAIRS, Blocks.QUARTZ_BRICKS);
		wallModel(bsmg, QUARTZ_BRICK_WALL, Blocks.QUARTZ_BRICKS);
		generatedItemModels(bsmg, QUARTZ_AXE, QUARTZ_HOE, QUARTZ_PICKAXE, QUARTZ_SHOVEL, QUARTZ_SWORD, QUARTZ_KNIFE);
		generatedItemModels(bsmg, QUARTZ_HELMET, QUARTZ_CHESTPLATE, QUARTZ_LEGGINGS, QUARTZ_BOOTS, QUARTZ_HORSE_ARMOR);
		//Iron
		torchModel(bsmg, IRON_TORCH);
		torchModel(bsmg, IRON_SOUL_TORCH, IRON_TORCH);
		torchModel(bsmg, IRON_ENDER_TORCH, IRON_TORCH);
		torchModel(bsmg, UNDERWATER_IRON_TORCH, IRON_TORCH);
		lanternModel(bsmg, WHITE_IRON_LANTERN);
		lanternModel(bsmg, WHITE_IRON_SOUL_LANTERN, WHITE_IRON_LANTERN);
		lanternModel(bsmg, WHITE_IRON_ENDER_LANTERN, WHITE_IRON_LANTERN);
		chainModel(bsmg, WHITE_IRON_CHAIN);
		stairsModel(bsmg, IRON_STAIRS, Blocks.IRON_BLOCK);
		slabModel(bsmg, IRON_SLAB, Blocks.IRON_BLOCK);
		wallModel(bsmg, IRON_WALL, Blocks.IRON_BLOCK);
		cubeAllModel(bsmg, IRON_BRICKS);
		slabModel(bsmg, IRON_BRICK_SLAB, IRON_BRICKS);
		stairsModel(bsmg, IRON_BRICK_STAIRS, IRON_BRICKS);
		wallModel(bsmg, IRON_BRICK_WALL, IRON_BRICKS);
		cubeAllModel(bsmg, CUT_IRON);
		metalPillarModel(bsmg, CUT_IRON_PILLAR, CUT_IRON);
		slabModel(bsmg, CUT_IRON_SLAB, CUT_IRON);
		stairsModel(bsmg, CUT_IRON_STAIRS, CUT_IRON);
		wallModel(bsmg, CUT_IRON_WALL, IRON_BRICKS);
		buttonModel(bsmg, IRON_BUTTON, Blocks.IRON_BLOCK);
		//Gold
		torchModel(bsmg, GOLD_TORCH);
		torchModel(bsmg, GOLD_SOUL_TORCH, GOLD_TORCH);
		torchModel(bsmg, GOLD_ENDER_TORCH, GOLD_TORCH);
		torchModel(bsmg, UNDERWATER_GOLD_TORCH, GOLD_TORCH);
		lanternModel(bsmg, GOLD_LANTERN);
		lanternModel(bsmg, GOLD_SOUL_LANTERN, GOLD_LANTERN);
		lanternModel(bsmg, GOLD_ENDER_LANTERN, GOLD_LANTERN);
		barsModel(bsmg, GOLD_BARS);
		chainModel(bsmg, GOLD_CHAIN);
		stairsModel(bsmg, GOLD_STAIRS, Blocks.GOLD_BLOCK);
		slabModel(bsmg, GOLD_SLAB, Blocks.GOLD_BLOCK);
		wallModel(bsmg, GOLD_WALL, Blocks.GOLD_BLOCK);
		cubeAllModel(bsmg, GOLD_BRICKS);
		slabModel(bsmg, GOLD_BRICK_SLAB, GOLD_BRICKS);
		stairsModel(bsmg, GOLD_BRICK_STAIRS, GOLD_BRICKS);
		wallModel(bsmg, GOLD_BRICK_WALL, GOLD_BRICKS);
		cubeAllModel(bsmg, CUT_GOLD);
		metalPillarModel(bsmg, CUT_GOLD_PILLAR, CUT_GOLD);
		slabModel(bsmg, CUT_GOLD_SLAB, CUT_GOLD);
		stairsModel(bsmg, CUT_GOLD_STAIRS, CUT_GOLD);
		wallModel(bsmg, CUT_GOLD_WALL, GOLD_BRICKS);
		buttonModel(bsmg, GOLD_BUTTON, Blocks.GOLD_BLOCK);
		//Copper
		generatedItemModel(bsmg, COPPER_NUGGET);
		torchModel(bsmg, COPPER_TORCH);
		torchModel(bsmg, EXPOSED_COPPER_TORCH);
		torchModel(bsmg, WEATHERED_COPPER_TORCH);
		torchModel(bsmg, OXIDIZED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_COPPER_TORCH, COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_EXPOSED_COPPER_TORCH, EXPOSED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_WEATHERED_COPPER_TORCH, WEATHERED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_OXIDIZED_COPPER_TORCH, OXIDIZED_COPPER_TORCH);
		torchModel(bsmg, COPPER_SOUL_TORCH, COPPER_TORCH);
		torchModel(bsmg, EXPOSED_COPPER_SOUL_TORCH, EXPOSED_COPPER_TORCH);
		torchModel(bsmg, WEATHERED_COPPER_SOUL_TORCH, WEATHERED_COPPER_TORCH);
		torchModel(bsmg, OXIDIZED_COPPER_SOUL_TORCH, OXIDIZED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_COPPER_SOUL_TORCH, COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_EXPOSED_COPPER_SOUL_TORCH, EXPOSED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_WEATHERED_COPPER_SOUL_TORCH, WEATHERED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_OXIDIZED_COPPER_SOUL_TORCH, OXIDIZED_COPPER_TORCH);
		torchModel(bsmg, COPPER_ENDER_TORCH, COPPER_TORCH);
		torchModel(bsmg, EXPOSED_COPPER_ENDER_TORCH, EXPOSED_COPPER_TORCH);
		torchModel(bsmg, WEATHERED_COPPER_ENDER_TORCH, WEATHERED_COPPER_TORCH);
		torchModel(bsmg, OXIDIZED_COPPER_ENDER_TORCH, OXIDIZED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_COPPER_ENDER_TORCH, COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_EXPOSED_COPPER_ENDER_TORCH, EXPOSED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_WEATHERED_COPPER_ENDER_TORCH, WEATHERED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_OXIDIZED_COPPER_ENDER_TORCH, OXIDIZED_COPPER_TORCH);
		torchModel(bsmg, UNDERWATER_COPPER_TORCH, COPPER_TORCH);
		torchModel(bsmg, EXPOSED_UNDERWATER_COPPER_TORCH, EXPOSED_COPPER_TORCH);
		torchModel(bsmg, WEATHERED_UNDERWATER_COPPER_TORCH, WEATHERED_COPPER_TORCH);
		torchModel(bsmg, OXIDIZED_UNDERWATER_COPPER_TORCH, OXIDIZED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_UNDERWATER_COPPER_TORCH, COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_EXPOSED_UNDERWATER_COPPER_TORCH, EXPOSED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_WEATHERED_UNDERWATER_COPPER_TORCH, WEATHERED_COPPER_TORCH);
		copiedTorchModel(bsmg, WAXED_OXIDIZED_UNDERWATER_COPPER_TORCH, OXIDIZED_COPPER_TORCH);
		lanternModel(bsmg, COPPER_LANTERN);
		lanternModel(bsmg, EXPOSED_COPPER_LANTERN);
		lanternModel(bsmg, WEATHERED_COPPER_LANTERN);
		lanternModel(bsmg, OXIDIZED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_COPPER_LANTERN, COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_EXPOSED_COPPER_LANTERN, EXPOSED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_WEATHERED_COPPER_LANTERN, WEATHERED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_OXIDIZED_COPPER_LANTERN, OXIDIZED_COPPER_LANTERN);
		lanternModel(bsmg, COPPER_SOUL_LANTERN, COPPER_LANTERN);
		lanternModel(bsmg, EXPOSED_COPPER_SOUL_LANTERN, EXPOSED_COPPER_LANTERN);
		lanternModel(bsmg, WEATHERED_COPPER_SOUL_LANTERN, WEATHERED_COPPER_LANTERN);
		lanternModel(bsmg, OXIDIZED_COPPER_SOUL_LANTERN, OXIDIZED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_COPPER_SOUL_LANTERN, COPPER_SOUL_LANTERN, COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_EXPOSED_COPPER_SOUL_LANTERN, EXPOSED_COPPER_SOUL_LANTERN, EXPOSED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_WEATHERED_COPPER_SOUL_LANTERN, WEATHERED_COPPER_SOUL_LANTERN, WEATHERED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_OXIDIZED_COPPER_SOUL_LANTERN, OXIDIZED_COPPER_SOUL_LANTERN, OXIDIZED_COPPER_LANTERN);
		lanternModel(bsmg, COPPER_ENDER_LANTERN, COPPER_LANTERN);
		lanternModel(bsmg, EXPOSED_COPPER_ENDER_LANTERN, EXPOSED_COPPER_LANTERN);
		lanternModel(bsmg, WEATHERED_COPPER_ENDER_LANTERN, WEATHERED_COPPER_LANTERN);
		lanternModel(bsmg, OXIDIZED_COPPER_ENDER_LANTERN, OXIDIZED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_COPPER_ENDER_LANTERN, COPPER_ENDER_LANTERN, COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_EXPOSED_COPPER_ENDER_LANTERN, EXPOSED_COPPER_ENDER_LANTERN, EXPOSED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_WEATHERED_COPPER_ENDER_LANTERN, WEATHERED_COPPER_ENDER_LANTERN, WEATHERED_COPPER_LANTERN);
		copiedLanternModel(bsmg, WAXED_OXIDIZED_COPPER_ENDER_LANTERN, OXIDIZED_COPPER_ENDER_LANTERN, OXIDIZED_COPPER_LANTERN);
		buttonModel(bsmg, COPPER_BUTTON, Blocks.COPPER_BLOCK);
		buttonModel(bsmg, EXPOSED_COPPER_BUTTON, Blocks.EXPOSED_COPPER);
		buttonModel(bsmg, WEATHERED_COPPER_BUTTON, Blocks.WEATHERED_COPPER);
		buttonModel(bsmg, OXIDIZED_COPPER_BUTTON, Blocks.OXIDIZED_COPPER);
		copiedButtonModel(bsmg, WAXED_COPPER_BUTTON, COPPER_BUTTON);
		copiedButtonModel(bsmg, WAXED_EXPOSED_COPPER_BUTTON, EXPOSED_COPPER_BUTTON);
		copiedButtonModel(bsmg, WAXED_WEATHERED_COPPER_BUTTON, WEATHERED_COPPER_BUTTON);
		copiedButtonModel(bsmg, WAXED_OXIDIZED_COPPER_BUTTON, OXIDIZED_COPPER_BUTTON);
		chainModel(bsmg, COPPER_CHAIN);
		chainModel(bsmg, EXPOSED_COPPER_CHAIN);
		chainModel(bsmg, WEATHERED_COPPER_CHAIN);
		chainModel(bsmg, OXIDIZED_COPPER_CHAIN);
		copiedChainModel(bsmg, WAXED_COPPER_CHAIN, COPPER_CHAIN);
		copiedChainModel(bsmg, WAXED_EXPOSED_COPPER_CHAIN, EXPOSED_COPPER_CHAIN);
		copiedChainModel(bsmg, WAXED_WEATHERED_COPPER_CHAIN, WEATHERED_COPPER_CHAIN);
		copiedChainModel(bsmg, WAXED_OXIDIZED_COPPER_CHAIN, OXIDIZED_COPPER_CHAIN);
		barsModel(bsmg, COPPER_BARS);
		barsModel(bsmg, EXPOSED_COPPER_BARS);
		barsModel(bsmg, WEATHERED_COPPER_BARS);
		barsModel(bsmg, OXIDIZED_COPPER_BARS);
		copiedBarsModel(bsmg, WAXED_COPPER_BARS, COPPER_BARS);
		copiedBarsModel(bsmg, WAXED_EXPOSED_COPPER_BARS, EXPOSED_COPPER_BARS);
		copiedBarsModel(bsmg, WAXED_WEATHERED_COPPER_BARS, WEATHERED_COPPER_BARS);
		copiedBarsModel(bsmg, WAXED_OXIDIZED_COPPER_BARS, OXIDIZED_COPPER_BARS);
		wallModel(bsmg, COPPER_WALL, Blocks.COPPER_BLOCK);
		wallModel(bsmg, EXPOSED_COPPER_WALL, Blocks.EXPOSED_COPPER);
		wallModel(bsmg, WEATHERED_COPPER_WALL, Blocks.WEATHERED_COPPER);
		wallModel(bsmg, OXIDIZED_COPPER_WALL, Blocks.OXIDIZED_COPPER);
		copiedWallModel(bsmg, WAXED_COPPER_WALL, COPPER_WALL);
		copiedWallModel(bsmg, WAXED_EXPOSED_COPPER_WALL, EXPOSED_COPPER_WALL);
		copiedWallModel(bsmg, WAXED_WEATHERED_COPPER_WALL, WEATHERED_COPPER_WALL);
		copiedWallModel(bsmg, WAXED_OXIDIZED_COPPER_WALL, OXIDIZED_COPPER_WALL);
		weightedPressurePlateModel(bsmg, MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.COPPER_BLOCK);
		weightedPressurePlateModel(bsmg, EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.EXPOSED_COPPER);
		weightedPressurePlateModel(bsmg, WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.WEATHERED_COPPER);
		weightedPressurePlateModel(bsmg, OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, Blocks.OXIDIZED_COPPER);
		copiedWeightedPressurePlateModel(bsmg, WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE, MEDIUM_WEIGHTED_PRESSURE_PLATE);
		copiedWeightedPressurePlateModel(bsmg, WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		copiedWeightedPressurePlateModel(bsmg, WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		copiedWeightedPressurePlateModel(bsmg, WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		generatedItemModels(bsmg, COPPER_AXE, EXPOSED_COPPER_AXE, WEATHERED_COPPER_AXE, OXIDIZED_COPPER_AXE);
		parentedItem(bsmg, WAXED_COPPER_AXE, COPPER_AXE);
		parentedItem(bsmg, WAXED_EXPOSED_COPPER_AXE, EXPOSED_COPPER_AXE);
		parentedItem(bsmg, WAXED_WEATHERED_COPPER_AXE, WEATHERED_COPPER_AXE);
		parentedItem(bsmg, WAXED_OXIDIZED_COPPER_AXE, OXIDIZED_COPPER_AXE);
		generatedItemModels(bsmg, COPPER_HOE, EXPOSED_COPPER_HOE, WEATHERED_COPPER_HOE, OXIDIZED_COPPER_HOE);
		parentedItem(bsmg, WAXED_COPPER_HOE, COPPER_HOE);
		parentedItem(bsmg, WAXED_EXPOSED_COPPER_HOE, EXPOSED_COPPER_HOE);
		parentedItem(bsmg, WAXED_WEATHERED_COPPER_HOE, WEATHERED_COPPER_HOE);
		parentedItem(bsmg, WAXED_OXIDIZED_COPPER_HOE, OXIDIZED_COPPER_HOE);
		generatedItemModels(bsmg, COPPER_PICKAXE, EXPOSED_COPPER_PICKAXE, WEATHERED_COPPER_PICKAXE, OXIDIZED_COPPER_PICKAXE);
		parentedItem(bsmg, WAXED_COPPER_PICKAXE, COPPER_PICKAXE);
		parentedItem(bsmg, WAXED_EXPOSED_COPPER_PICKAXE, EXPOSED_COPPER_PICKAXE);
		parentedItem(bsmg, WAXED_WEATHERED_COPPER_PICKAXE, WEATHERED_COPPER_PICKAXE);
		parentedItem(bsmg, WAXED_OXIDIZED_COPPER_PICKAXE, OXIDIZED_COPPER_PICKAXE);
		generatedItemModels(bsmg, COPPER_SHOVEL, EXPOSED_COPPER_SHOVEL, WEATHERED_COPPER_SHOVEL, OXIDIZED_COPPER_SHOVEL);
		parentedItem(bsmg, WAXED_COPPER_SHOVEL, COPPER_SHOVEL);
		parentedItem(bsmg, WAXED_EXPOSED_COPPER_SHOVEL, EXPOSED_COPPER_SHOVEL);
		parentedItem(bsmg, WAXED_WEATHERED_COPPER_SHOVEL, WEATHERED_COPPER_SHOVEL);
		parentedItem(bsmg, WAXED_OXIDIZED_COPPER_SHOVEL, OXIDIZED_COPPER_SHOVEL);
		generatedItemModels(bsmg, COPPER_SWORD, EXPOSED_COPPER_SWORD, WEATHERED_COPPER_SWORD, OXIDIZED_COPPER_SWORD);
		parentedItem(bsmg, WAXED_COPPER_SWORD, COPPER_SWORD);
		parentedItem(bsmg, WAXED_EXPOSED_COPPER_SWORD, EXPOSED_COPPER_SWORD);
		parentedItem(bsmg, WAXED_WEATHERED_COPPER_SWORD, WEATHERED_COPPER_SWORD);
		parentedItem(bsmg, WAXED_OXIDIZED_COPPER_SWORD, OXIDIZED_COPPER_SWORD);
		generatedItemModels(bsmg, COPPER_KNIFE, EXPOSED_COPPER_KNIFE, WEATHERED_COPPER_KNIFE, OXIDIZED_COPPER_KNIFE);
		parentedItem(bsmg, WAXED_COPPER_KNIFE, COPPER_KNIFE);
		parentedItem(bsmg, WAXED_EXPOSED_COPPER_KNIFE, EXPOSED_COPPER_KNIFE);
		parentedItem(bsmg, WAXED_WEATHERED_COPPER_KNIFE, WEATHERED_COPPER_KNIFE);
		parentedItem(bsmg, WAXED_OXIDIZED_COPPER_KNIFE, OXIDIZED_COPPER_KNIFE);
		//Raw Metal
		slabModel(bsmg, RAW_COPPER_SLAB, Blocks.RAW_COPPER_BLOCK);
		slabModel(bsmg, RAW_GOLD_SLAB, Blocks.RAW_GOLD_BLOCK);
		slabModel(bsmg, RAW_IRON_SLAB, Blocks.RAW_IRON_BLOCK);
		//Netherite
		generatedItemModel(bsmg, NETHERITE_NUGGET);
		torchModel(bsmg, NETHERITE_TORCH);
		torchModel(bsmg, NETHERITE_SOUL_TORCH, NETHERITE_TORCH);
		torchModel(bsmg, NETHERITE_ENDER_TORCH, NETHERITE_TORCH);
		torchModel(bsmg, UNDERWATER_NETHERITE_TORCH, NETHERITE_TORCH);
		lanternModel(bsmg, NETHERITE_LANTERN);
		lanternModel(bsmg, NETHERITE_SOUL_LANTERN, NETHERITE_LANTERN);
		lanternModel(bsmg, NETHERITE_ENDER_LANTERN, NETHERITE_LANTERN);
		barsModel(bsmg, NETHERITE_BARS);
		chainModel(bsmg, NETHERITE_CHAIN);
		stairsModel(bsmg, NETHERITE_STAIRS, Blocks.NETHERITE_BLOCK);
		slabModel(bsmg, NETHERITE_SLAB, Blocks.NETHERITE_BLOCK);
		wallModel(bsmg, NETHERITE_WALL, Blocks.NETHERITE_BLOCK);
		cubeAllModel(bsmg, NETHERITE_BRICKS);
		slabModel(bsmg, NETHERITE_BRICK_SLAB, NETHERITE_BRICKS);
		stairsModel(bsmg, NETHERITE_BRICK_STAIRS, NETHERITE_BRICKS);
		wallModel(bsmg, NETHERITE_BRICK_WALL, NETHERITE_BRICKS);
		cubeAllModel(bsmg, CUT_NETHERITE);
		metalPillarModel(bsmg, CUT_NETHERITE_PILLAR, CUT_NETHERITE);
		slabModel(bsmg, CUT_NETHERITE_SLAB, CUT_NETHERITE);
		stairsModel(bsmg, CUT_NETHERITE_STAIRS, CUT_NETHERITE);
		wallModel(bsmg, CUT_NETHERITE_WALL, NETHERITE_BRICKS);
		buttonModel(bsmg, NETHERITE_BUTTON, Blocks.NETHERITE_BLOCK);
		weightedPressurePlateModel(bsmg, CRUSHING_WEIGHTED_PRESSURE_PLATE, Blocks.NETHERITE_BLOCK);
		generatedItemModel(bsmg, NETHERITE_HORSE_ARMOR);
		//Extended Bone
		boneTorchModel(bsmg, BONE_TORCH);
		boneTorchModel(bsmg, BONE_SOUL_TORCH, BONE_TORCH);
		boneTorchModel(bsmg, BONE_ENDER_TORCH, BONE_TORCH);
		boneTorchModel(bsmg, UNDERWATER_BONE_TORCH, BONE_TORCH);
		boneLadderModel(bsmg, BONE_LADDER);
		//Gilded Blackstone
		slabModel(bsmg, GILDED_BLACKSTONE_SLAB, Blocks.GILDED_BLACKSTONE);
		stairsModel(bsmg, GILDED_BLACKSTONE_STAIRS, Blocks.GILDED_BLACKSTONE);
		wallModel(bsmg, GILDED_BLACKSTONE_WALL, Blocks.GILDED_BLACKSTONE);
		cubeAllModel(bsmg, POLISHED_GILDED_BLACKSTONE);
		slabModel(bsmg, POLISHED_GILDED_BLACKSTONE_SLAB, POLISHED_GILDED_BLACKSTONE);
		stairsModel(bsmg, POLISHED_GILDED_BLACKSTONE_STAIRS, POLISHED_GILDED_BLACKSTONE);
		wallModel(bsmg, POLISHED_GILDED_BLACKSTONE_WALL, POLISHED_GILDED_BLACKSTONE);
		cubeAllModel(bsmg, POLISHED_GILDED_BLACKSTONE_BRICKS);
		slabModel(bsmg, POLISHED_GILDED_BLACKSTONE_BRICK_SLAB, POLISHED_GILDED_BLACKSTONE_BRICKS);
		stairsModel(bsmg, POLISHED_GILDED_BLACKSTONE_BRICK_STAIRS, POLISHED_GILDED_BLACKSTONE_BRICKS);
		wallModel(bsmg, POLISHED_GILDED_BLACKSTONE_BRICK_WALL, POLISHED_GILDED_BLACKSTONE_BRICKS);
		cubeAllModel(bsmg, CHISELED_POLISHED_GILDED_BLACKSTONE);
		cubeAllModel(bsmg, CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS);
		//Misc
		slabModel(bsmg, COAL_SLAB, Blocks.COAL_BLOCK);
		cubeAllModel(bsmg, CHARCOAL_BLOCK);
		slabModel(bsmg, CHARCOAL_SLAB, CHARCOAL_BLOCK);
		slabModel(bsmg, COARSE_DIRT_SLAB, Blocks.COARSE_DIRT);
		cubeAllModel(bsmg, BLUE_SHROOMLIGHT);
		cubeAllModel(bsmg, FLINT_BLOCK);
		slabModel(bsmg, FLINT_SLAB, FLINT_BLOCK);
		mudBricksModel(bsmg, FLINT_BRICKS);
		slabModel(bsmg, FLINT_BRICK_SLAB, FLINT_BRICKS);
		stairsModel(bsmg, FLINT_BRICK_STAIRS, FLINT_BRICKS);
		wallModel(bsmg, FLINT_BRICK_WALL, FLINT_BRICKS);
		explicitModel(bsmg, HEDGE_BLOCK);
		cubeAllModel(bsmg, COCOA_BLOCK);
		cubeAllModel(bsmg, SEED_BLOCK);
		glazedTerracottaModel(bsmg, GLAZED_TERRACOTTA);
		cubeAllModel(bsmg, WAX_BLOCK);
		//More Wool
		for (DyeColor color : DyeColor.values()) slabModel(bsmg, WOOL_SLABS.get(color), ColorUtil.GetWoolBlock(color));
		rainbowBlockModel(bsmg, RAINBOW_WOOL);
		rainbowSlabModel(bsmg, RAINBOW_WOOL_SLAB, RAINBOW_WOOL.asBlock());
		rainbowCarpetModel(bsmg, RAINBOW_CARPET, RAINBOW_WOOL.asBlock());
		//bedModel(bsmg, RAINBOW_BED, ID("block/rainbow_wool_particle"));
		//Extended Goat
		for (DyeColor color : DyeColor.values()) {
			BlockContainer fleece = FLEECE.get(color), slab = FLEECE_SLABS.get(color), carpet = FLEECE_CARPETS.get(color);
			Block fleeceBlock = fleece.asBlock(), carpetBlock = carpet.asBlock();
			bsmg.registerWoolAndCarpet(fleeceBlock, carpetBlock);
			parentedItem(bsmg, fleece.asItem(), fleeceBlock);
			parentedItem(bsmg, carpet.asItem(), carpetBlock);
			slabModel(bsmg, slab, fleece);
		}
		rainbowBlockModel(bsmg, RAINBOW_FLEECE);
		rainbowSlabModel(bsmg, RAINBOW_FLEECE_SLAB, RAINBOW_FLEECE.asBlock());
		rainbowCarpetModel(bsmg, RAINBOW_FLEECE_CARPET, RAINBOW_FLEECE.asBlock());
		generatedItemModels(bsmg, FLEECE_HELMET, FLEECE_CHESTPLATE, FLEECE_LEGGINGS, FLEECE_BOOTS, FLEECE_HORSE_ARMOR);
		//Moss
		slabModel(bsmg, MOSS_SLAB, Blocks.MOSS_BLOCK);
		//bedModel(bsmg, MOSS_BED, Blocks.MOSS_BLOCK);
		//Sculk & Deep Dark
		cubeAllModel(bsmg, SCULK);
		singletonModel(bsmg, SCULK_CATALYST, TexturedModel.CUBE_BOTTOM_TOP);
		parentedItem(bsmg, SCULK_SHRIEKER);
		generatedItemModel(bsmg, SCULK_VEIN.asItem(), SCULK_VEIN.asBlock());
		singletonModel(bsmg, REINFORCED_DEEPSLATE, TexturedModel.CUBE_BOTTOM_TOP);
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
		bsmg.registerAmethyst(ECHO_CLUSTER.asBlock());
		generatedItemModel(bsmg, ECHO_CLUSTER.asItem(), ECHO_CLUSTER.asBlock());
		bsmg.registerAmethyst(LARGE_ECHO_BUD.asBlock());
		generatedItemModel(bsmg, LARGE_ECHO_BUD.asItem(), LARGE_ECHO_BUD.asBlock());
		bsmg.registerAmethyst(MEDIUM_ECHO_BUD.asBlock());
		generatedItemModel(bsmg, MEDIUM_ECHO_BUD.asItem(), MEDIUM_ECHO_BUD.asBlock());
		bsmg.registerAmethyst(SMALL_ECHO_BUD.asBlock());
		generatedItemModel(bsmg, SMALL_ECHO_BUD.asItem(), SMALL_ECHO_BUD.asBlock());
		cubeAllModel(bsmg, SCULK_STONE);
		slabModel(bsmg, SCULK_STONE_SLAB, SCULK_STONE);
		stairsModel(bsmg, SCULK_STONE_STAIRS, SCULK_STONE);
		wallModel(bsmg, SCULK_STONE_WALL, SCULK_STONE);
		mudBricksModel(bsmg, SCULK_STONE_BRICKS);
		slabModel(bsmg, SCULK_STONE_BRICK_SLAB, SCULK_STONE_BRICKS);
		stairsModel(bsmg, SCULK_STONE_BRICK_STAIRS, SCULK_STONE_BRICKS);
		wallModel(bsmg, SCULK_STONE_BRICK_WALL, SCULK_STONE_BRICKS);
		//Extended Sculk
		generatedItemModel(bsmg, CHUM);
		topBottomSidesModel(bsmg, CALCITE_SCULK_TURF, SCULK.asBlock(), Blocks.CALCITE);
		topBottomSidesModel(bsmg, DEEPSLATE_SCULK_TURF, SCULK.asBlock(), Blocks.DEEPSLATE);
		topBottomSidesModel(bsmg, DRIPSTONE_SCULK_TURF, SCULK.asBlock(), Blocks.DRIPSTONE_BLOCK);
		topBottomSidesModel(bsmg, SMOOTH_BASALT_SCULK_TURF, SCULK.asBlock(), Blocks.SMOOTH_BASALT);
		topBottomSidesModel(bsmg, TUFF_SCULK_TURF, SCULK.asBlock(), Blocks.TUFF);
		//Charred Wood
		logModel(bsmg, CHARRED_LOG, CHARRED_WOOD);
		logModel(bsmg, STRIPPED_CHARRED_LOG, STRIPPED_CHARRED_WOOD);
		cubeAllModel(bsmg, CHARRED_PLANKS);
		slabModel(bsmg, CHARRED_SLAB, CHARRED_PLANKS);
		stairsModel(bsmg, CHARRED_STAIRS, CHARRED_PLANKS);
		doorModel(bsmg, CHARRED_DOOR);
		trapdoorModel(bsmg, CHARRED_TRAPDOOR, true);
		buttonModel(bsmg, CHARRED_BUTTON, CHARRED_PLANKS);
		fenceModel(bsmg, CHARRED_FENCE, CHARRED_PLANKS);
		fenceGateModel(bsmg, CHARRED_FENCE_GATE, CHARRED_PLANKS);
		pressurePlateModel(bsmg, CHARRED_PRESSURE_PLATE, CHARRED_PLANKS);
		signModel(bsmg, CHARRED_SIGN, CHARRED_PLANKS, STRIPPED_CHARRED_LOG.asBlock());
		bookshelfModel(bsmg, CHARRED_BOOKSHELF, CHARRED_PLANKS);
		ladderModel(bsmg, CHARRED_LADDER);
		woodcutterModel(bsmg, CHARRED_WOODCUTTER, CHARRED_PLANKS);
		barrelModel(bsmg, CHARRED_BARREL);
		lecternModel(bsmg, CHARRED_LECTERN, CHARRED_PLANKS);
		generatedItemModels(bsmg, CHARRED_BOAT, CHARRED_BOAT.getChestBoat());
		//Mud
		cubeAllModel(bsmg, MUD);
		cubeAllModel(bsmg, PACKED_MUD);
		mudBricksModel(bsmg, MUD_BRICKS);
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
		signModel(bsmg, MANGROVE_SIGN, MANGROVE_PLANKS, STRIPPED_MANGROVE_LOG.asBlock());
		explicitModel(bsmg, MANGROVE_ROOTS);
		uprightPillarModel(bsmg, MUDDY_MANGROVE_ROOTS);
		bsmg.registerSimpleState(MANGROVE_PROPAGULE.getPottedBlock());
		generatedItemModel(bsmg, MANGROVE_PROPAGULE.asItem());
		//Extended Mangrove
		bookshelfModel(bsmg, MANGROVE_BOOKSHELF, MANGROVE_PLANKS);
		ladderModel(bsmg, MANGROVE_LADDER);
		woodcutterModel(bsmg, MANGROVE_WOODCUTTER, MANGROVE_PLANKS);
		barrelModel(bsmg, MANGROVE_BARREL);
		lecternModel(bsmg, MANGROVE_LECTERN, MANGROVE_PLANKS);
		generatedItemModels(bsmg, MANGROVE_BOAT, MANGROVE_BOAT.getChestBoat());
		//Cherry
		logModel(bsmg, CHERRY_LOG, CHERRY_WOOD);
		logModel(bsmg, STRIPPED_CHERRY_LOG, STRIPPED_CHERRY_WOOD);
		singletonModel(bsmg, CHERRY_LEAVES, TexturedModel.LEAVES);
		cubeAllModel(bsmg, CHERRY_PLANKS);
		slabModel(bsmg, CHERRY_SLAB, CHERRY_PLANKS);
		stairsModel(bsmg, CHERRY_STAIRS, CHERRY_PLANKS);
		doorModel(bsmg, CHERRY_DOOR);
		trapdoorModel(bsmg, CHERRY_TRAPDOOR, true);
		buttonModel(bsmg, CHERRY_BUTTON, CHERRY_PLANKS);
		fenceModel(bsmg, CHERRY_FENCE, CHERRY_PLANKS);
		fenceGateModel(bsmg, CHERRY_FENCE_GATE, CHERRY_PLANKS);
		pressurePlateModel(bsmg, CHERRY_PRESSURE_PLATE, CHERRY_PLANKS);
		signModel(bsmg, CHERRY_SIGN, CHERRY_PLANKS, STRIPPED_CHERRY_LOG.asBlock());
		flowerPotPlantModel(bsmg, CHERRY_SAPLING);
		//Extended Cherry
		bookshelfModel(bsmg, CHERRY_BOOKSHELF, CHERRY_PLANKS);
		ladderModel(bsmg, CHERRY_LADDER);
		woodcutterModel(bsmg, CHERRY_WOODCUTTER, CHERRY_PLANKS);
		barrelModel(bsmg, CHERRY_BARREL);
		lecternModel(bsmg, CHERRY_LECTERN, CHERRY_PLANKS);
		generatedItemModels(bsmg, CHERRY_BOAT, CHERRY_BOAT.getChestBoat());
		//Pink Petals
		generatedItemModel(bsmg, PINK_PETALS.asItem(), PINK_PETALS.asBlock());
		//Torchflower
		flowerPotPlantModel(bsmg, TORCHFLOWER);
		generatedItemModel(bsmg, TORCHFLOWER_CROP.asItem());
		//Vanilla Bamboo
		parentedItem(bsmg, BAMBOO_BLOCK);
		parentedItem(bsmg, STRIPPED_BAMBOO_BLOCK);
		cubeAllModel(bsmg, BAMBOO_PLANKS);
		slabModel(bsmg, BAMBOO_SLAB, BAMBOO_PLANKS);
		stairsModel(bsmg, BAMBOO_STAIRS, BAMBOO_PLANKS);
		doorModel(bsmg, BAMBOO_DOOR);
		trapdoorModel(bsmg, BAMBOO_TRAPDOOR, true);
		buttonModel(bsmg, BAMBOO_BUTTON, BAMBOO_PLANKS);
		bsmg.registerParentedItemModel(BAMBOO_FENCE.asItem(), TextureMap.getSubId(BAMBOO_FENCE.asBlock(), "_inventory"));
		parentedItem(bsmg, BAMBOO_FENCE_GATE);
		pressurePlateModel(bsmg, BAMBOO_PRESSURE_PLATE, BAMBOO_PLANKS);
		signModel(bsmg, BAMBOO_SIGN, BAMBOO_PLANKS, STRIPPED_BAMBOO_BLOCK.asBlock());
		//Bamboo Mosaic
		cubeAllModel(bsmg, BAMBOO_MOSAIC);
		slabModel(bsmg, BAMBOO_MOSAIC_SLAB, BAMBOO_MOSAIC);
		stairsModel(bsmg, BAMBOO_MOSAIC_STAIRS, BAMBOO_MOSAIC);
		//Extended Bamboo
		torchModel(bsmg, BAMBOO_TORCH);
		torchModel(bsmg, BAMBOO_SOUL_TORCH, BAMBOO_TORCH);
		torchModel(bsmg, BAMBOO_ENDER_TORCH, BAMBOO_TORCH);
		torchModel(bsmg, UNDERWATER_BAMBOO_TORCH, BAMBOO_TORCH);
		bookshelfModel(bsmg, BAMBOO_BOOKSHELF, BAMBOO_PLANKS);
		ladderModel(bsmg, BAMBOO_LADDER);
		woodcutterModel(bsmg, BAMBOO_WOODCUTTER, BAMBOO_PLANKS);
		barrelModel(bsmg, BAMBOO_BARREL);
		lecternModel(bsmg, BAMBOO_LECTERN, BAMBOO_PLANKS);
		generatedItemModels(bsmg, BAMBOO_RAFT.asItem(), BAMBOO_RAFT.getChestBoat());
		//Frogs
		pillarModel(bsmg, OCHRE_FROGLIGHT);
		pillarModel(bsmg, VERDANT_FROGLIGHT);
		pillarModel(bsmg, PEARLESCENT_FROGLIGHT);
		frogspawnModel(bsmg, FROGSPAWN);
		//Tall Flowers
		tallFlowerModel(bsmg, AMARANTH);
		tallFlowerModel(bsmg, BLUE_ROSE_BUSH);
		tallFlowerModel(bsmg, TALL_ALLIUM, Blocks.ALLIUM, ID("block/allium_stalk"));
		tallFlowerModel(bsmg, TALL_PINK_ALLIUM, PINK_ALLIUM.asBlock(), ID("block/allium_stalk"));
		//Flowers
		flowerPotPlantModel(bsmg, BUTTERCUP);
		flowerPotPlantModel(bsmg, PINK_DAISY);
		flowerPotPlantModel(bsmg, ROSE);
		flowerPotPlantModel(bsmg, BLUE_ROSE);
		flowerPotPlantModel(bsmg, MAGENTA_TULIP);
		flowerPotPlantModel(bsmg, MARIGOLD);
		flowerPotPlantModel(bsmg, INDIGO_ORCHID);
		flowerPotPlantModel(bsmg, MAGENTA_ORCHID);
		flowerPotPlantModel(bsmg, ORANGE_ORCHID);
		flowerPotPlantModel(bsmg, PURPLE_ORCHID);
		flowerPotPlantModel(bsmg, RED_ORCHID);
		flowerPotPlantModel(bsmg, WHITE_ORCHID);
		flowerPotPlantModel(bsmg, YELLOW_ORCHID);
		flowerPotPlantModel(bsmg, PINK_ALLIUM);
		flowerPotPlantModel(bsmg, LAVENDER);
		flowerPotPlantModel(bsmg, HYDRANGEA);
		flowerPotPlantModel(bsmg, PAEONIA);
		flowerPotPlantModel(bsmg, ASTER);
		//Trimming
		generatedItemModel(bsmg, NETHERITE_UPGRADE_SMITHING_TEMPLATE);
		for (ArmorTrimPattern pattern : ArmorTrimPattern.values()) generatedItemModel(bsmg, pattern.asItem());
		//Acacia
		barrelModel(bsmg, ACACIA_BARREL);
		bookshelfModel(bsmg, ACACIA_BOOKSHELF, Blocks.ACACIA_PLANKS);
		hangingSignModel(bsmg, Blocks.STRIPPED_ACACIA_LOG, ACACIA_HANGING_SIGN);
		ladderModel(bsmg, ACACIA_LADDER);
		lecternModel(bsmg, ACACIA_LECTERN, Blocks.ACACIA_PLANKS);
		woodcutterModel(bsmg, ACACIA_WOODCUTTER, Blocks.ACACIA_PLANKS);
		generatedItemModel(bsmg, ACACIA_CHEST_BOAT);
		//Birch
		barrelModel(bsmg, BIRCH_BARREL);
		bookshelfModel(bsmg, BIRCH_BOOKSHELF, Blocks.BIRCH_PLANKS);
		hangingSignModel(bsmg, Blocks.STRIPPED_BIRCH_LOG, BIRCH_HANGING_SIGN);
		ladderModel(bsmg, BIRCH_LADDER);
		lecternModel(bsmg, BIRCH_LECTERN, Blocks.BIRCH_PLANKS);
		woodcutterModel(bsmg, BIRCH_WOODCUTTER, Blocks.BIRCH_PLANKS);
		generatedItemModel(bsmg, BIRCH_CHEST_BOAT);
		//Dark Oak
		barrelModel(bsmg, DARK_OAK_BARREL);
		bookshelfModel(bsmg, DARK_OAK_BOOKSHELF, Blocks.DARK_OAK_PLANKS);
		hangingSignModel(bsmg, Blocks.STRIPPED_DARK_OAK_LOG, DARK_OAK_HANGING_SIGN);
		ladderModel(bsmg, DARK_OAK_LADDER);
		lecternModel(bsmg, DARK_OAK_LECTERN, Blocks.DARK_OAK_PLANKS);
		woodcutterModel(bsmg, DARK_OAK_WOODCUTTER, Blocks.DARK_OAK_PLANKS);
		generatedItemModel(bsmg, DARK_OAK_CHEST_BOAT);
		//Jungle
		barrelModel(bsmg, JUNGLE_BARREL);
		bookshelfModel(bsmg, JUNGLE_BOOKSHELF, Blocks.JUNGLE_PLANKS);
		hangingSignModel(bsmg, Blocks.STRIPPED_JUNGLE_LOG, JUNGLE_HANGING_SIGN);
		ladderModel(bsmg, JUNGLE_LADDER);
		lecternModel(bsmg, JUNGLE_LECTERN, Blocks.JUNGLE_PLANKS);
		woodcutterModel(bsmg, JUNGLE_WOODCUTTER, Blocks.JUNGLE_PLANKS);
		generatedItemModel(bsmg, JUNGLE_CHEST_BOAT);
		//Oak
		barrelModel(bsmg, OAK_BARREL);
		hangingSignModel(bsmg, Blocks.STRIPPED_OAK_LOG, OAK_HANGING_SIGN);
		woodcutterModel(bsmg, WOODCUTTER, Blocks.OAK_PLANKS);
		generatedItemModel(bsmg, OAK_CHEST_BOAT);
		//Spruce
		bookshelfModel(bsmg, SPRUCE_BOOKSHELF, Blocks.SPRUCE_PLANKS);
		hangingSignModel(bsmg, Blocks.STRIPPED_SPRUCE_LOG, SPRUCE_HANGING_SIGN);
		ladderModel(bsmg, SPRUCE_LADDER);
		lecternModel(bsmg, SPRUCE_LECTERN, Blocks.SPRUCE_PLANKS);
		woodcutterModel(bsmg, SPRUCE_WOODCUTTER, Blocks.SPRUCE_PLANKS);
		generatedItemModel(bsmg, SPRUCE_CHEST_BOAT);
		//Crimson
		barrelModel(bsmg, CRIMSON_BARREL);
		bookshelfModel(bsmg, CRIMSON_BOOKSHELF, Blocks.CRIMSON_PLANKS);
		hangingSignModel(bsmg, Blocks.STRIPPED_CRIMSON_STEM, CRIMSON_HANGING_SIGN);
		ladderModel(bsmg, CRIMSON_LADDER);
		lecternModel(bsmg, CRIMSON_LECTERN, Blocks.CRIMSON_PLANKS);
		woodcutterModel(bsmg, CRIMSON_WOODCUTTER, Blocks.CRIMSON_PLANKS);
		//Warped
		barrelModel(bsmg, WARPED_BARREL);
		bookshelfModel(bsmg, WARPED_BOOKSHELF, Blocks.WARPED_PLANKS);
		hangingSignModel(bsmg, Blocks.STRIPPED_WARPED_STEM, WARPED_HANGING_SIGN);
		ladderModel(bsmg, WARPED_LADDER);
		lecternModel(bsmg, WARPED_LECTERN, Blocks.WARPED_PLANKS);
		woodcutterModel(bsmg, WARPED_WOODCUTTER, Blocks.WARPED_PLANKS);
		//Pouches & Pouchable Mobs
		generatedItemModel(bsmg, POUCH);
		generatedItemModel(bsmg, CHICKEN_POUCH);
		generatedItemModel(bsmg, RABBIT_POUCH);
		generatedItemModel(bsmg, HEDGEHOG_POUCH);
	}

	private static void generatedItemModel(ItemModelGenerator img, ItemConvertible item) { img.register(item.asItem(), Models.GENERATED); }
	private static void generatedItemModels(ItemModelGenerator img, ItemConvertible... items) { for(ItemConvertible item : items) img.register(item.asItem(), Models.GENERATED); }

	@Override
	public void generateItemModels(ItemModelGenerator img) {
		//Minecraft Earth
		generatedItemModel(img, HORN);
		//Books
		img.register(UNREADABLE_BOOK, Items.WRITTEN_BOOK, Models.GENERATED);
		generatedItemModel(img, RED_BOOK);
		generatedItemModel(img, ORANGE_BOOK);
		generatedItemModel(img, YELLOW_BOOK);
		generatedItemModel(img, GREEN_BOOK);
		generatedItemModel(img, BLUE_BOOK);
		generatedItemModel(img, PURPLE_BOOK);
		generatedItemModel(img, GRAY_BOOK);
		//Goat
		generatedItemModel(img, GOAT_HORN);
		//Misc Stuff
		generatedItemModel(img, LAVA_BOTTLE);
		//Extended Goat
		generatedItemModels(img, CHEVON, COOKED_CHEVON);
		generatedItemModel(img, GOAT_HORN_SALVE);
		//Studded Leather
		generatedItemModels(img, STUDDED_LEATHER_HELMET, STUDDED_LEATHER_CHESTPLATE, STUDDED_LEATHER_LEGGINGS, STUDDED_LEATHER_BOOTS, STUDDED_LEATHER_HORSE_ARMOR);
		//Frog
		generatedItemModel(img, TADPOLE_BUCKET);
		//Echo
		generatedItemModel(img, ECHO_SHARD);
		//Extended Echo
		generatedItemModels(img, ECHO_AXE, ECHO_HOE, ECHO_PICKAXE, ECHO_SHOVEL, ECHO_SWORD, ECHO_KNIFE);
		//Music Discs
		generatedItemModels(img, MUSIC_DISC_5, DISC_FRAGMENT_5);
	}
}