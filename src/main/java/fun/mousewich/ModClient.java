package fun.mousewich;

import fun.mousewich.block.piglin.PiglinHeadEntityModel;
import fun.mousewich.block.piglin.PiglinHeadEntityRenderer;
import fun.mousewich.client.render.entity.model.*;
import fun.mousewich.client.render.entity.renderer.*;
import fun.mousewich.container.*;
import fun.mousewich.particle.*;
import fun.mousewich.client.render.gui.WoodcutterScreen;
import fun.mousewich.client.render.gui.WoodcutterScreenHandler;
import io.github.apace100.apoli.ApoliClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.SignType;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static fun.mousewich.ModBase.*;

@Environment(EnvType.CLIENT)
public class ModClient implements ClientModInitializer {
	public static final EntityModelLayer ALLAY_MODEL_LAYER = new EntityModelLayer(ID("allay"), "main");
	public static final EntityModelLayer CAMEL_MODEL_LAYER = new EntityModelLayer(ID("camel"), "main");
	public static final EntityModelLayer FROG_MODEL_LAYER = new EntityModelLayer(ID("frog"), "main");
	public static final EntityModelLayer PIGLIN_HEAD_LAYER = new EntityModelLayer(ID("piglin_head"), "main");
	public static final EntityModelLayer RAFT_ENTITY_MODEL_LAYER = new EntityModelLayer(ID("raft"), "main");
	public static final EntityModelLayer TADPOLE_MODEL_LAYER = new EntityModelLayer(ID("tadpole"), "main");
	public static final EntityModelLayer WARDEN_MODEL_LAYER = new EntityModelLayer(ID("warden"), "main");
	public static final EntityModelLayer BOAT_ENTITY_MODEL_LAYER = new EntityModelLayer(ID("boat"), "main");
	public static final EntityModelLayer CHEST_BOAT_ENTITY_MODEL_LAYER = new EntityModelLayer(ID("chest_boat"), "main");
	public static final EntityModelLayer CHEST_RAFT_ENTITY_MODEL_LAYER = new EntityModelLayer(ID("chest_raft"), "main");

	public static final ScreenHandlerType<WoodcutterScreenHandler> WOODCUTTER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(ID("woodcutter"), WoodcutterScreenHandler::new);

	private static void InitializeCutoutMipped() {
		RenderLayer layer = RenderLayer.getCutoutMipped();
		//Hedges
		setLayer(layer, HEDGE_BLOCK);
		//Backport
		setLayer(layer, MANGROVE_ROOTS, MANGROVE_TRAPDOOR);
		//Iron
		setLayer(layer, WHITE_IRON_CHAIN);
		//Gold
		setLayer(layer, GOLD_BARS, GOLD_CHAIN);
		//Netherite
		setLayer(layer, NETHERITE_BARS, NETHERITE_CHAIN);
	}
	private static void InitializeCutout() {
		RenderLayer layer = RenderLayer.getCutout();
		//Lanterns
		setLayer(layer, UNLIT_LANTERN, UNLIT_SOUL_LANTERN, ENDER_LANTERN.asBlock());
		//Unlit Torches
		setLayer(layer, UNLIT_TORCH, UNLIT_SOUL_TORCH);
		//Underwater Torch
		setLayer(layer, UNDERWATER_TORCH);
		//Ender Fire
		setLayer(layer, ENDER_TORCH);
		setLayer(layer, ENDER_CAMPFIRE);
		//Iron
		setLayer(layer, IRON_TORCH, IRON_SOUL_TORCH, IRON_ENDER_TORCH, UNDERWATER_IRON_TORCH);
		setLayer(layer, WHITE_IRON_LANTERN, WHITE_IRON_SOUL_LANTERN, WHITE_IRON_ENDER_LANTERN);
		//Gold
		setLayer(layer, GOLD_TORCH, GOLD_SOUL_TORCH, GOLD_ENDER_TORCH, UNDERWATER_GOLD_TORCH);
		setLayer(layer, GOLD_LANTERN, GOLD_SOUL_LANTERN, GOLD_ENDER_LANTERN);
		//Netherite
		setLayer(layer, NETHERITE_TORCH, NETHERITE_SOUL_TORCH, NETHERITE_ENDER_TORCH, UNDERWATER_NETHERITE_TORCH);
		setLayer(layer, NETHERITE_LANTERN, NETHERITE_SOUL_LANTERN, NETHERITE_ENDER_LANTERN);
		//Extended Bone
		setLayer(layer, BONE_TORCH, BONE_SOUL_TORCH, BONE_ENDER_TORCH, UNDERWATER_BONE_TORCH);
		//Mangrove
		setLayer(layer, MANGROVE_PROPAGULE.asBlock(), MANGROVE_PROPAGULE.getPottedBlock());
		//Extended Bamboo
		setLayer(layer, BAMBOO_TORCH, BAMBOO_SOUL_TORCH, BAMBOO_ENDER_TORCH, UNDERWATER_BAMBOO_TORCH);
		//Echo
		setLayer(layer, ECHO_CLUSTER, LARGE_ECHO_BUD, MEDIUM_ECHO_BUD, SMALL_ECHO_BUD);
		setLayer(layer, SCULK_VEIN, SCULK_SHRIEKER, SCULK_SENSOR);
		//Woodcutter
		setLayer(layer, MANGROVE_WOODCUTTER, BAMBOO_WOODCUTTER);
		setLayer(layer, CHARRED_WOODCUTTER);
		setLayer(layer, ACACIA_WOODCUTTER, BIRCH_WOODCUTTER, DARK_OAK_WOODCUTTER, JUNGLE_WOODCUTTER);
		setLayer(layer, WOODCUTTER, SPRUCE_WOODCUTTER, CRIMSON_WOODCUTTER, WARPED_WOODCUTTER);
		//Flowers
		setLayer(layer, AMARANTH, BLUE_ROSE_BUSH, TALL_ALLIUM, TALL_PINK_ALLIUM);
		setLayer(layer, BUTTERCUP, PINK_DAISY, ROSE, BLUE_ROSE, MAGENTA_TULIP, MARIGOLD, INDIGO_ORCHID, MAGENTA_ORCHID);
		setLayer(layer, ORANGE_ORCHID, PURPLE_ORCHID, RED_ORCHID, WHITE_ORCHID, YELLOW_ORCHID, PINK_ALLIUM, LAVENDER);
		setLayer(layer, HYDRANGEA, PAEONIA, ASTER);
	}
	private static void InitializeTranslucent() {
		RenderLayer Translucent = RenderLayer.getTranslucent();
		setLayer(Translucent, GLASS_SLAB, TINTED_GLASS_PANE, TINTED_GLASS_SLAB);
		for(BlockContainer container : STAINED_GLASS_SLABS.values()) setLayer(Translucent, container);
	}

	private static void setLayer(RenderLayer layer, Block block) { BlockRenderLayerMap.INSTANCE.putBlock(block, layer); }
	private static void setLayer(RenderLayer layer, Block... blocks) { BlockRenderLayerMap.INSTANCE.putBlocks(layer, blocks); }
	private static void setLayer(RenderLayer layer, BlockContainer container) { setLayer(layer, container.asBlock()); }
	private static void setLayer(RenderLayer layer, BlockContainer... containers) {
		setLayer(layer, Arrays.stream(containers).map(BlockContainer::asBlock).toArray(Block[]::new));
	}
	private static void setLayer(RenderLayer layer, PottedBlockContainer... containers) {
		setLayer(layer, Arrays.stream(containers).map(PottedBlockContainer::asBlock).toArray(Block[]::new));
		setLayer(layer, Arrays.stream(containers).map(PottedBlockContainer::getPottedBlock).toArray(Block[]::new));
	}
	private static void setLayer(RenderLayer layer, TorchContainer... containers) {
		for(TorchContainer container : containers) {
			BlockRenderLayerMap.INSTANCE.putBlocks(layer, container.asBlock(), container.getWallBlock());
		}
	}
	private static void setLayer(RenderLayer layer, UnlitTorchContainer... containers) {
		for(UnlitTorchContainer container : containers) {
			BlockRenderLayerMap.INSTANCE.putBlocks(layer, container.asBlock(), container.getWallBlock());
		}
	}


	@Override
	public void onInitializeClient() {
		//Render Layers
		InitializeCutoutMipped();
		InitializeCutout();
		InitializeTranslucent();
		//Mob Heads
		EntityModelLayerRegistry.registerModelLayer(PIGLIN_HEAD_LAYER, PiglinHeadEntityModel::getTexturedModelData);
		BlockEntityRendererRegistry.register(PIGLIN_HEAD_BLOCK_ENTITY, PiglinHeadEntityRenderer::new);
		//Allays
		EntityModelLayerRegistry.registerModelLayer(ALLAY_MODEL_LAYER, AllayEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(ALLAY_ENTITY, AllayEntityRenderer::new);
		//Frog & Tadpole
		EntityModelLayerRegistry.registerModelLayer(FROG_MODEL_LAYER, FrogEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(FROG_ENTITY, FrogEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(TADPOLE_MODEL_LAYER, TadpoleEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(TADPOLE_ENTITY, TadpoleEntityRenderer::new);
		//Warden
		EntityModelLayerRegistry.registerModelLayer(WARDEN_MODEL_LAYER, WardenEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(WARDEN_ENTITY, WardenEntityRenderer::new);
		//Camel
		EntityModelLayerRegistry.registerModelLayer(CAMEL_MODEL_LAYER, CamelEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(CAMEL_ENTITY, CamelEntityRenderer::new);
		//Hanging Signs
		TexturedModelData hangingSignModel = HangingSignModel.getTexturedModelData();
		Set<SignType> signTypes = new HashSet<>(List.of(SignType.OAK, SignType.SPRUCE, SignType.BIRCH, SignType.ACACIA, SignType.JUNGLE, SignType.DARK_OAK, SignType.CRIMSON, SignType.WARPED));
		signTypes.addAll(SIGN_TYPES);
		for (SignType type : signTypes) {
			EntityModelLayerRegistry.registerModelLayer(HangingSignModel.createSignLayer(type), () -> hangingSignModel);
		}
		//Custom Boats
		EntityModelLayerRegistry.registerModelLayer(BOAT_ENTITY_MODEL_LAYER, BoatEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(RAFT_ENTITY_MODEL_LAYER, RaftEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(MOD_BOAT_ENTITY, ModBoatEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(CHEST_BOAT_ENTITY_MODEL_LAYER, ChestBoatEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(CHEST_RAFT_ENTITY_MODEL_LAYER, ChestRaftEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(CHEST_BOAT_ENTITY, ChestBoatEntityRenderer::new);
		EntityRendererRegistry.register(MOD_CHEST_BOAT_ENTITY, ChestBoatEntityRenderer::new);
		//Particles
		ParticleFactoryRegistry PARTICLES = ParticleFactoryRegistry.getInstance();
		//Torches
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(ID("particle/glow_0"));
			registry.register(ID("particle/glow_1"));
			registry.register(ID("particle/glow_2"));
			registry.register(ID("particle/glow_3"));
			registry.register(ID("particle/glow_4"));
			registry.register(ID("particle/glow_5"));
			registry.register(ID("particle/glow_6"));
			registry.register(ID("particle/glow_7"));
			registry.register(ID("particle/copper_flame"));
			registry.register(ID("particle/gold_flame"));
			registry.register(ID("particle/iron_flame"));
			registry.register(ID("particle/netherite_flame"));
			registry.register(ID("particle/ender_fire_flame"));
		}));
		PARTICLES.register(UNDERWATER_TORCH_GLOW, FlameParticle.Factory::new);
		//PARTICLES.register(COPPER_FLAME_PARTICLE, FlameParticle.Factory::new);
		PARTICLES.register(GOLD_FLAME_PARTICLE, FlameParticle.Factory::new);
		PARTICLES.register(IRON_FLAME_PARTICLE, FlameParticle.Factory::new);
		PARTICLES.register(NETHERITE_FLAME_PARTICLE, FlameParticle.Factory::new);
		PARTICLES.register(ENDER_FIRE_FLAME_PARTICLE, FlameParticle.Factory::new);
		PARTICLES.register(SMALL_SOUL_FLAME_PARTICLE, FlameParticle.SmallFactory::new);
		PARTICLES.register(SMALL_ENDER_FLAME_PARTICLE, FlameParticle.SmallFactory::new);
		//Sculk
		PARTICLES.register(SCULK_SOUL_PARTICLE, ModSoulParticle.SculkSoulFactory::new);
		PARTICLES.register(SCULK_CHARGE_PARTICLE, SculkChargeParticle.Factory::new);
		PARTICLES.register(SCULK_CHARGE_POP_PARTICLE, SculkChargePopParticle.Factory::new);
		PARTICLES.register(SHRIEK_PARTICLE, ShriekParticle.Factory::new);
		PARTICLES.register(SONIC_BOOM_PARTICLE, SonicBoomParticle.Factory::new);
		//Custom Beds
		//ClientSpriteRegistryCallback.event(TexturedRenderLayers.BEDS_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
		//	for (BedContainer bed : BEDS) registry.register(bed.GetTexture());
		//}));
		//Woodcutter
		ScreenRegistry.register(WOODCUTTER_SCREEN_HANDLER, WoodcutterScreen::new);
		//Keybinds
		KeyBinding useTertiaryActivePowerKeybind = new KeyBinding("key." + NAMESPACE + ".tertiary_active", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "category." + NAMESPACE);
		ApoliClient.registerPowerKeybinding("key." + NAMESPACE + ".tertiary_active", useTertiaryActivePowerKeybind);
		ApoliClient.registerPowerKeybinding("tertiary", useTertiaryActivePowerKeybind);
		KeyBindingHelper.registerKeyBinding(useTertiaryActivePowerKeybind);
	}

	public static void RegisterBlockColors(BlockColors blockColors) {
		//Generic Foliage
		blockColors.registerColorProvider((state, world, pos, tintIndex) ->
						world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(),
				//Hedges
				HEDGE_BLOCK.asBlock(),
				//Mangrove Leaves
				MANGROVE_LEAVES.asBlock()
		);
	}

	public static void RegisterItemColors(ItemColors itemColors) {
		//Generic Foliage
		itemColors.register((itemStack, i) -> 9619016,
				//Hedges
				HEDGE_BLOCK,
				//Mangrove
				MANGROVE_LEAVES
		);
		//Fleece Armor
		itemColors.register((stack, tintIndex) -> {
			NbtCompound nbtCompound = stack.getSubNbt("display");
			return tintIndex > 0 ? -1 : nbtCompound != null && nbtCompound.contains("color", 99) ? nbtCompound.getInt("color") : 0xFFFFFF;
		}, FLEECE_HELMET, FLEECE_CHESTPLATE, FLEECE_LEGGINGS, FLEECE_BOOTS, FLEECE_HORSE_ARMOR);
	}
}
