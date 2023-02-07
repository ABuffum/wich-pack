package fun.mousewich.gen.data.model;

import fun.mousewich.container.*;
import fun.mousewich.util.Util;
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
import static fun.mousewich.ModBase.WARPED_BOOKSHELF;

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
	public static void wallModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, TextureMap textures) {
		Block block = container.asBlock();
		Identifier identifier = Models.TEMPLATE_WALL_POST.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.TEMPLATE_WALL_SIDE.upload(block, textures, bsmg.modelCollector);
		Identifier identifier3 = Models.TEMPLATE_WALL_SIDE_TALL.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createWallBlockState(block, identifier, identifier2, identifier3));
		Identifier identifier4 = Models.WALL_INVENTORY.upload(block, textures, bsmg.modelCollector);
		bsmg.registerParentedItemModel(container.asItem(), identifier4);
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
	public static void buttonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer baseBlock) { buttonModel(bsmg, container, baseBlock.asBlock()); }
	public static void buttonModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		TextureMap textures = TextureMap.all(baseBlock);
		Identifier identifier = Models.BUTTON.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.BUTTON_PRESSED.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createButtonBlockState(block, identifier, identifier2));
		Identifier identifier3 = Models.BUTTON_INVENTORY.upload(block, textures, bsmg.modelCollector);
		bsmg.registerParentedItemModel(container.asItem(), identifier3);
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
	public static void weightedPressurePlateModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block baseBlock) {
		Block block = container.asBlock();
		TextureMap textures = TextureMap.all(baseBlock);
		Identifier identifier = Models.PRESSURE_PLATE_UP.upload(block, textures, bsmg.modelCollector);
		Identifier identifier2 = Models.PRESSURE_PLATE_DOWN.upload(block, textures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateModelGenerator.createValueFencedModelMap(Properties.POWER, 1, identifier2, identifier)));
		bsmg.registerParentedItemModel(container.asItem(), identifier);
	}
	public static void signModel(BlockStateModelGenerator bsmg, SignContainer container, IBlockItemContainer baseBlock) { signModel(bsmg, container, baseBlock.asBlock()); }
	public static void signModel(BlockStateModelGenerator bsmg, SignContainer container, Block baseBlock) {
		Block block = container.asBlock(), wallBlock = container.getWallBlock();
		Identifier identifier = Models.PARTICLE.upload(block, TextureMap.all(baseBlock), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier));
		bsmg.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(wallBlock, identifier));
		bsmg.excludeFromSimpleItemModelGeneration(wallBlock);
		generatedItemModel(bsmg, container.asItem());
	}

	private static void torchModelCommon(BlockStateModelGenerator bsmg, TorchContainer container, Identifier unlit, Identifier unlitWall, Model template, Model templateWall) {
		Block block = container.asBlock();
		TextureMap textures = TextureMap.torch(block), unlitTextures = new TextureMap().put(TextureKey.TORCH, getBlockModelId("unlit_", block));
		Identifier litModel = template.upload(block, textures, bsmg.modelCollector);
		Identifier unlitModel = unlit != null ? unlit : template.upload(getBlockModelId("unlit_", block), unlitTextures, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.LIT, litModel, unlitModel)));
		Block wallBlock = container.getWallBlock();
		litModel = templateWall.upload(wallBlock, textures, bsmg.modelCollector);
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
		generatedItemModel(bsmg, container.asItem(), block);
	}
	private static void torchModelCommon(BlockStateModelGenerator bsmg, TorchContainer container, Identifier unlit, Identifier unlitWall) {
		torchModelCommon(bsmg, container, unlit, unlitWall, Models.TEMPLATE_TORCH, Models.TEMPLATE_TORCH_WALL);
	}
	public static void torchModel(BlockStateModelGenerator bsmg, TorchContainer container) { torchModelCommon(bsmg, container, null, null); }
	public static void torchModel(BlockStateModelGenerator bsmg, TorchContainer container, UnlitTorchContainer unlit) {
		torchModelCommon(bsmg, container, getBlockModelId(unlit.asBlock()), getBlockModelId(unlit.getWallBlock()));
	}
	public static void torchModel(BlockStateModelGenerator bsmg, TorchContainer container, TorchContainer base) {
		torchModelCommon(bsmg, container, getBlockModelId("unlit_", base.asBlock()), getBlockModelId("unlit_", base.getWallBlock()));
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
		torchModelCommon(bsmg, container, unlit, unlitWall, ModModels.TEMPLATE_BONE_TORCH, ModModels.TEMPLATE_BONE_TORCH_WALL);
	}
	public static void boneTorchModel(BlockStateModelGenerator bsmg, TorchContainer container) { boneTorchModelCommon(bsmg, container, null, null); }
	public static void boneTorchModel(BlockStateModelGenerator bsmg, TorchContainer container, TorchContainer base) {
		boneTorchModelCommon(bsmg, container, getBlockModelId("unlit_", base.asBlock()), getBlockModelId("unlit_", base.getWallBlock()));
	}

	public static void lanternModelCommon(BlockStateModelGenerator bsmg, IBlockItemContainer container, Identifier unlit) {
		Block block = container.asBlock();
		Identifier litModel = TexturedModel.TEMPLATE_LANTERN.upload(block, bsmg.modelCollector);
		Identifier litHanging = TexturedModel.TEMPLATE_HANGING_LANTERN.upload(block, bsmg.modelCollector);
		Identifier unlitModel = unlit != null ? unlit : Models.TEMPLATE_LANTERN.upload(getBlockModelId("unlit_", block), TextureMap.lantern(block), bsmg.modelCollector);
		Identifier unlitHanging = unlit != null ? postfixPath(unlit, "_hanging") : Models.TEMPLATE_HANGING_LANTERN.upload(getBlockModelId("unlit_", block, "_hanging"), TextureMap.lantern(block), bsmg.modelCollector);
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
				.coordinate(BlockStateVariantMap.create(Properties.LIT, Properties.HANGING)
						.register(true, true, BlockStateVariant.create().put(VariantSettings.MODEL, litHanging))
						.register(true, false, BlockStateVariant.create().put(VariantSettings.MODEL, litModel))
						.register(false, true, BlockStateVariant.create().put(VariantSettings.MODEL, unlitHanging))
						.register(false, false, BlockStateVariant.create().put(VariantSettings.MODEL, unlitModel))));
		generatedItemModel(bsmg, container.asItem());
	}
	public static void lanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer lantern) { lanternModelCommon(bsmg, lantern, null); }
	public static void lanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, IBlockItemContainer base) { lanternModel(bsmg, container, base.asBlock()); }
	public static void lanternModel(BlockStateModelGenerator bsmg, IBlockItemContainer container, Block base) {
		Identifier id = getBlockModelId(base);
		if (!id.getPath().startsWith("block/unlit_")) id = getBlockModelId("unlit_", base);
		lanternModelCommon(bsmg, container, id);
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
	public static void barsModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		Identifier texture = TextureMap.getId(block);
		TextureMap textureMap = new TextureMap().put(TextureKey.PARTICLE, texture).put(ModTextureKeys.BARS, texture).put(TextureKey.EDGE, texture);
		Identifier identifier = ModModels.TEMPLATE_BARS_POST_ENDS.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier2 = ModModels.TEMPLATE_BARS_POST.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier3 = ModModels.TEMPLATE_BARS_CAP.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier4 = ModModels.TEMPLATE_BARS_CAP_ALT.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier5 = ModModels.TEMPLATE_BARS_SIDE.upload(block, textureMap, bsmg.modelCollector);
		Identifier identifier6 = ModModels.TEMPLATE_BARS_SIDE_ALT.upload(block, textureMap, bsmg.modelCollector);
		bsmg.blockStateCollector.accept(MultipartBlockStateSupplier.create(block)
				.with(BlockStateVariant.create().put(VariantSettings.MODEL, identifier))
				.with(When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, false),
						BlockStateVariant.create().put(VariantSettings.MODEL, identifier2))
				.with(When.create().set(Properties.NORTH, true).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, false),
						BlockStateVariant.create().put(VariantSettings.MODEL, identifier3))
				.with(When.create().set(Properties.NORTH, false).set(Properties.EAST, true).set(Properties.SOUTH, false).set(Properties.WEST, false),
						BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.with(When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, true).set(Properties.WEST, false),
						BlockStateVariant.create().put(VariantSettings.MODEL, identifier4))
				.with(When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, true),
						BlockStateVariant.create().put(VariantSettings.MODEL, identifier4).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5))
				.with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5).put(VariantSettings.Y, VariantSettings.Rotation.R90))
				.with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier6)).with(When.create().set(Properties.WEST, true),
						BlockStateVariant.create().put(VariantSettings.MODEL, identifier6).put(VariantSettings.Y, VariantSettings.Rotation.R90)));
		generatedItemModel(bsmg, container.asItem(), block);
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
	public static void chainModel(BlockStateModelGenerator bsmg, IBlockItemContainer container) {
		Block block = container.asBlock();
		bsmg.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create()
						.put(VariantSettings.MODEL, ModModels.TEMPLATE_CHAIN.upload(block, TextureMap.all(block), bsmg.modelCollector)))
				.coordinate(BlockStateModelGenerator.createAxisRotatedVariantMap()));
		generatedItemModel(bsmg, container.asItem());
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
		wallModel(bsmg, PURPUR_WALL, Blocks.PURPUR_BLOCK);
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
		//More Glass
		paneModel(bsmg, TINTED_GLASS_PANE, Blocks.TINTED_GLASS);
		glassSlabModel(bsmg, TINTED_GLASS_SLAB, Blocks.TINTED_GLASS);
		glassSlabModel(bsmg, GLASS_SLAB, Blocks.GLASS);
		for(DyeColor color : COLORS) glassSlabModel(bsmg, STAINED_GLASS_SLABS.get(color), Util.GetStainedGlassBlock(color));
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
		//Extended Iron
		buttonModel(bsmg, IRON_BUTTON, Blocks.IRON_BLOCK);
		//Extended Netherite
		torchModel(bsmg, NETHERITE_TORCH);
		torchModel(bsmg, NETHERITE_SOUL_TORCH, NETHERITE_TORCH);
		torchModel(bsmg, NETHERITE_ENDER_TORCH, NETHERITE_TORCH);
		torchModel(bsmg, UNDERWATER_NETHERITE_TORCH, NETHERITE_TORCH);
		lanternModel(bsmg, NETHERITE_LANTERN);
		lanternModel(bsmg, NETHERITE_SOUL_LANTERN, NETHERITE_LANTERN);
		lanternModel(bsmg, NETHERITE_ENDER_LANTERN, NETHERITE_LANTERN);
		barsModel(bsmg, NETHERITE_BARS);
		chainModel(bsmg, NETHERITE_CHAIN);
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
		cubeAllModel(bsmg, BLUE_SHROOMLIGHT);
		explicitModel(bsmg, HEDGE_BLOCK);
		cubeAllModel(bsmg, COCOA_BLOCK);
		cubeAllModel(bsmg, SEED_BLOCK);
		cubeAllModel(bsmg, WAX_BLOCK);
		//More Wool
		for (DyeColor color : COLORS) slabModel(bsmg, WOOL_SLABS.get(color), Util.GetWoolBlock(color));
		rainbowBlockModel(bsmg, RAINBOW_WOOL);
		rainbowSlabModel(bsmg, RAINBOW_WOOL_SLAB, RAINBOW_WOOL.asBlock());
		rainbowCarpetModel(bsmg, RAINBOW_CARPET, RAINBOW_WOOL.asBlock());
		//bedModel(bsmg, RAINBOW_BED, ID("block/rainbow_wool_particle"));
		//Extended Goat
		for (DyeColor color : COLORS) {
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
		cubeAllModel(bsmg, SCULK_STONE_BRICKS);
		slabModel(bsmg, SCULK_STONE_BRICK_SLAB, SCULK_STONE_BRICKS);
		stairsModel(bsmg, SCULK_STONE_BRICK_STAIRS, SCULK_STONE_BRICKS);
		wallModel(bsmg, SCULK_STONE_BRICK_WALL, SCULK_STONE_BRICKS);
		//Extended Sculk
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
		signModel(bsmg, CHARRED_SIGN, CHARRED_PLANKS);
		bookshelfModel(bsmg, CHARRED_BOOKSHELF, CHARRED_PLANKS);
		ladderModel(bsmg, CHARRED_LADDER);
		woodcutterModel(bsmg, CHARRED_WOODCUTTER, CHARRED_PLANKS);
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
		signModel(bsmg, MANGROVE_SIGN, MANGROVE_PLANKS);
		explicitModel(bsmg, MANGROVE_ROOTS);
		uprightPillarModel(bsmg, MUDDY_MANGROVE_ROOTS);
		bsmg.registerSimpleState(MANGROVE_PROPAGULE.getPottedBlock());
		generatedItemModel(bsmg, MANGROVE_PROPAGULE.asItem());
		//Extended Mangrove
		bookshelfModel(bsmg, MANGROVE_BOOKSHELF, MANGROVE_PLANKS);
		ladderModel(bsmg, MANGROVE_LADDER);
		woodcutterModel(bsmg, MANGROVE_WOODCUTTER, MANGROVE_PLANKS);
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
		signModel(bsmg, BAMBOO_SIGN, BAMBOO_PLANKS);
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
		//Frogs
		pillarModel(bsmg, OCHRE_FROGLIGHT);
		pillarModel(bsmg, VERDANT_FROGLIGHT);
		pillarModel(bsmg, PEARLESCENT_FROGLIGHT);
		frogspawnModel(bsmg, FROGSPAWN);
		//Ladders
		ladderModel(bsmg, ACACIA_LADDER);
		ladderModel(bsmg, BIRCH_LADDER);
		ladderModel(bsmg, DARK_OAK_LADDER);
		ladderModel(bsmg, JUNGLE_LADDER);
		ladderModel(bsmg, SPRUCE_LADDER);
		ladderModel(bsmg, CRIMSON_LADDER);
		ladderModel(bsmg, WARPED_LADDER);
		//Woodcutter
		bookshelfModel(bsmg, ACACIA_BOOKSHELF, Blocks.ACACIA_PLANKS);
		bookshelfModel(bsmg, BIRCH_BOOKSHELF, Blocks.BIRCH_PLANKS);
		bookshelfModel(bsmg, DARK_OAK_BOOKSHELF, Blocks.DARK_OAK_PLANKS);
		bookshelfModel(bsmg, JUNGLE_BOOKSHELF, Blocks.JUNGLE_PLANKS);
		bookshelfModel(bsmg, SPRUCE_BOOKSHELF, Blocks.SPRUCE_PLANKS);
		bookshelfModel(bsmg, CRIMSON_BOOKSHELF, Blocks.CRIMSON_PLANKS);
		bookshelfModel(bsmg, WARPED_BOOKSHELF, Blocks.WARPED_PLANKS);
		woodcutterModel(bsmg, ACACIA_WOODCUTTER, Blocks.ACACIA_PLANKS);
		woodcutterModel(bsmg, BIRCH_WOODCUTTER, Blocks.BIRCH_PLANKS);
		woodcutterModel(bsmg, DARK_OAK_WOODCUTTER, Blocks.DARK_OAK_PLANKS);
		woodcutterModel(bsmg, JUNGLE_WOODCUTTER, Blocks.JUNGLE_PLANKS);
		woodcutterModel(bsmg, WOODCUTTER, Blocks.OAK_PLANKS);
		woodcutterModel(bsmg, SPRUCE_WOODCUTTER, Blocks.SPRUCE_PLANKS);
		woodcutterModel(bsmg, CRIMSON_WOODCUTTER, Blocks.CRIMSON_PLANKS);
		woodcutterModel(bsmg, WARPED_WOODCUTTER, Blocks.WARPED_PLANKS);
	}

	private static void generatedItemModel(ItemModelGenerator img, ItemConvertible item) { img.register(item.asItem(), Models.GENERATED); }
	private static void generatedItemModel(ItemModelGenerator img, ItemConvertible... items) { for(ItemConvertible item : items) img.register(item.asItem(), Models.GENERATED); }

	@Override
	public void generateItemModels(ItemModelGenerator img) {
		//Extended Amethyst
		generatedItemModel(img, AMETHYST_AXE, AMETHYST_HOE, AMETHYST_PICKAXE, AMETHYST_SHOVEL, AMETHYST_SWORD, AMETHYST_KNIFE);
		generatedItemModel(img, AMETHYST_HELMET, AMETHYST_CHESTPLATE, AMETHYST_LEGGINGS, AMETHYST_BOOTS, AMETHYST_HORSE_ARMOR);
		generatedItemModel(img, TINTED_GOGGLES);
		//Extended Emerald
		generatedItemModel(img, EMERALD_AXE, EMERALD_HOE, EMERALD_PICKAXE, EMERALD_SHOVEL, EMERALD_SWORD, EMERALD_KNIFE);
		generatedItemModel(img, EMERALD_HELMET, EMERALD_CHESTPLATE, EMERALD_LEGGINGS, EMERALD_BOOTS, EMERALD_HORSE_ARMOR);
		//Extended Quartz
		generatedItemModel(img, QUARTZ_AXE, QUARTZ_HOE, QUARTZ_PICKAXE, QUARTZ_SHOVEL, QUARTZ_SWORD, QUARTZ_KNIFE);
		generatedItemModel(img, QUARTZ_HELMET, QUARTZ_CHESTPLATE, QUARTZ_LEGGINGS, QUARTZ_BOOTS, QUARTZ_HORSE_ARMOR);
		//Extended Netherite
		generatedItemModel(img, NETHERITE_NUGGET, NETHERITE_HORSE_ARMOR);
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
		generatedItemModel(img, CHEVON, COOKED_CHEVON);
		generatedItemModel(img, GOAT_HORN_SALVE);
		generatedItemModel(img, FLEECE_HELMET, FLEECE_CHESTPLATE, FLEECE_LEGGINGS, FLEECE_BOOTS, FLEECE_HORSE_ARMOR);
		generatedItemModel(img, STUDDED_LEATHER_HELMET, STUDDED_LEATHER_CHESTPLATE, STUDDED_LEATHER_LEGGINGS, STUDDED_LEATHER_BOOTS, STUDDED_LEATHER_HORSE_ARMOR);
		//Frog
		generatedItemModel(img, TADPOLE_BUCKET);
		//Echo
		generatedItemModel(img, ECHO_SHARD);
		//Extended Echo
		generatedItemModel(img, ECHO_AXE, ECHO_HOE, ECHO_PICKAXE, ECHO_SHOVEL, ECHO_SWORD, ECHO_KNIFE);
		//Music Discs
		generatedItemModel(img, MUSIC_DISC_5, DISC_FRAGMENT_5);
		//Charred Wood
		generatedItemModel(img, CHARRED_BOAT);
		//Mangrove
		generatedItemModel(img, MANGROVE_BOAT);
	}
}