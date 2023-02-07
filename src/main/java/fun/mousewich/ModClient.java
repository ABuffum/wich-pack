package fun.mousewich;

import fun.mousewich.block.piglin.PiglinHeadEntityModel;
import fun.mousewich.block.piglin.PiglinHeadEntityRenderer;
import fun.mousewich.client.render.entity.model.FrogEntityModel;
import fun.mousewich.client.render.entity.renderer.FrogEntityRenderer;
import fun.mousewich.client.render.entity.model.TadpoleEntityModel;
import fun.mousewich.client.render.entity.model.WardenEntityModel;
import fun.mousewich.client.render.entity.renderer.ModBoatEntityRenderer;
import fun.mousewich.client.render.entity.renderer.TadpoleEntityRenderer;
import fun.mousewich.client.render.entity.renderer.WardenEntityRenderer;
import fun.mousewich.container.BedContainer;
import fun.mousewich.container.BlockContainer;
import fun.mousewich.container.TorchContainer;
import fun.mousewich.container.UnlitTorchContainer;
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
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

import static fun.mousewich.ModBase.*;

@Environment(EnvType.CLIENT)
public class ModClient implements ClientModInitializer {
	public static final EntityModelLayer BOAT_ENTITY_MODEL_LAYER = new EntityModelLayer(ID("boat"), "main");
	public static final EntityModelLayer FROG_MODEL_LAYER = new EntityModelLayer(ID("frog"), "main");
	public static final EntityModelLayer PIGLIN_HEAD_LAYER = new EntityModelLayer(ID("piglin_head"), "main");
	public static final EntityModelLayer TADPOLE_MODEL_LAYER = new EntityModelLayer(ID("tadpole"), "main");
	public static final EntityModelLayer WARDEN_MODEL_LAYER = new EntityModelLayer(ID("warden"), "main");

	public static final ScreenHandlerType<WoodcutterScreenHandler> WOODCUTTER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(ID("woodcutter"), WoodcutterScreenHandler::new);

	private static void InitializeCutoutMipped() {
		RenderLayer layer = RenderLayer.getCutoutMipped();
		//Hedges
		setLayer(layer, HEDGE_BLOCK);
		//Backport
		setLayer(layer, MANGROVE_ROOTS, MANGROVE_TRAPDOOR);
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
		//Frog & Tadpole
		EntityModelLayerRegistry.registerModelLayer(FROG_MODEL_LAYER, FrogEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(FROG_ENTITY, FrogEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(TADPOLE_MODEL_LAYER, TadpoleEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(TADPOLE_ENTITY, TadpoleEntityRenderer::new);
		//Warden
		EntityModelLayerRegistry.registerModelLayer(WARDEN_MODEL_LAYER, WardenEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(WARDEN_ENTITY, WardenEntityRenderer::new);
		//Custom Boats
		EntityModelLayerRegistry.registerModelLayer(BOAT_ENTITY_MODEL_LAYER, BoatEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(BOAT_ENTITY, ModBoatEntityRenderer::new);
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
		//PARTICLES.register(GOLD_FLAME_PARTICLE, FlameParticle.Factory::new);
		//PARTICLES.register(IRON_FLAME_PARTICLE, FlameParticle.Factory::new);
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
		ClientSpriteRegistryCallback.event(TexturedRenderLayers.BEDS_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			for (BedContainer bed : BEDS) registry.register(bed.GetTexture());
		}));
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
