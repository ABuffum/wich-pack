package fun.mousewich;

import fun.mousewich.client.rendering.entity.ModBoatEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.StemBlock;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PlayerScreenHandler;

import static fun.mousewich.ModBase.*;

@Environment(EnvType.CLIENT)
public class ModClient implements ClientModInitializer {
	public static final EntityModelLayer BOAT_ENTITY_MODEL_LAYER = new EntityModelLayer(ID("boat"), "main");

	@Override
	public void onInitializeClient() {
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
		//PARTICLES.register(NETHERITE_FLAME_PARTICLE, FlameParticle.Factory::new);
		PARTICLES.register(ENDER_FIRE_FLAME_PARTICLE, FlameParticle.Factory::new);
		PARTICLES.register(SMALL_SOUL_FLAME_PARTICLE, FlameParticle.SmallFactory::new);
		PARTICLES.register(SMALL_ENDER_FLAME_PARTICLE, FlameParticle.SmallFactory::new);
	}

	public static void RegisterBlockColors(BlockColors blockColors) {
		//Generic Foliage
		blockColors.registerColorProvider((state, world, pos, tintIndex) ->
						world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(),
				//Hedges
				HEDGE_BLOCK.getBlock(),
				//Mangrove Leaves
				MANGROVE_LEAVES.getBlock()
		);
	}

	public static void RegisterItemColors(ItemColors itemColors) {
		//Generic Foliage
		itemColors.register((itemStack, i) -> 9619016,
				//Hedges
				HEDGE_BLOCK.getItem(),
				//Mangrove
				MANGROVE_LEAVES.getItem()
		);
		//Fleece Armor
		itemColors.register((stack, tintIndex) -> {
			NbtCompound nbtCompound = stack.getSubNbt("display");
			return tintIndex > 0 ? -1 : nbtCompound != null && nbtCompound.contains("color", 99) ? nbtCompound.getInt("color") : 0xFFFFFF;
		}, FLEECE_HELMET, FLEECE_CHESTPLATE, FLEECE_LEGGINGS, FLEECE_BOOTS, FLEECE_HORSE_ARMOR);
	}
}
