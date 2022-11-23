package fun.mousewich.material;

import fun.mousewich.block.LightableLanternBlock;
import fun.mousewich.container.BlockContainer;
import fun.mousewich.container.TorchContainer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.ToIntFunction;

public abstract class BaseMaterial {
	protected final boolean flammable;
	public boolean isFlammable() { return flammable; }

	protected BaseMaterial(boolean flammable) {
		this.flammable = flammable;
	}

	public abstract Item.Settings ItemSettings();

	public boolean contains(Block block) { return false; }
	public boolean contains(Item item) { return false; }

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

	public static Block.Settings TorchSettings(int luminance, BlockSoundGroup sounds) {
		return FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(createLightLevelFromLitBlockState(luminance)).sounds(sounds);
	}

	protected TorchContainer MakeTorch(int luminance, BlockSoundGroup sounds, DefaultParticleType particle) {
		return MakeTorch(luminance, sounds, particle, ItemSettings());
	}
	public static TorchContainer MakeTorch(int luminance, BlockSoundGroup sounds, DefaultParticleType particle, Item.Settings settings) {
		return new TorchContainer(TorchSettings(luminance, sounds), particle, settings);
	}
	public static Block.Settings LanternSettings(int luminance) {
		return Block.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(createLightLevelFromLitBlockState(luminance)).nonOpaque();
	}
	protected BlockContainer MakeLantern(int luminance) { return MakeLantern(luminance, ItemSettings()); }
	public static BlockContainer MakeLantern(int luminance, Item.Settings settings) {
		return new BlockContainer(new LightableLanternBlock(LanternSettings(luminance)), settings);
	}
	protected BlockContainer MakeCampfire(int luminance, int fireDamage, MapColor mapColor, BlockSoundGroup sounds, boolean emitsParticles) {
		return MakeCampfire(luminance, fireDamage, mapColor, sounds, emitsParticles, ItemSettings());
	}
	public static BlockContainer MakeCampfire(int luminance, int fireDamage, MapColor mapColor, BlockSoundGroup sounds, boolean emitsParticles, Item.Settings settings) {
		return new BlockContainer(new CampfireBlock(emitsParticles, fireDamage, Block.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(sounds).luminance(createLightLevelFromLitBlockState(luminance)).nonOpaque()), settings);
	}
}
