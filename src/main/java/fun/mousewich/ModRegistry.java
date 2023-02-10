package fun.mousewich;

import com.google.common.collect.ImmutableList;
import fun.mousewich.container.*;

import fun.mousewich.mixins.world.BuiltinBiomesInvoker;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.PowerFactorySupplier;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

import static fun.mousewich.ModBase.*;

public class ModRegistry {
	public static Block Register(String path, Block block) { return Registry.register(Registry.BLOCK, ID(path), block); }
	public static Item Register(String path, Item item) { return Registry.register(Registry.ITEM, ID(path), item); }
	public static BlockContainer Register(String path, BlockContainer container) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, container.asBlock());
		Registry.register(Registry.ITEM, id, container.asItem());
		return container;
	}
	public static WallBlockContainer Register(String path, String wallPath, WallBlockContainer container) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, container.asBlock());
		Registry.register(Registry.BLOCK, ID(wallPath), container.getWallBlock());
		Registry.register(Registry.ITEM, id, container.asItem());
		return container;
	}
	public static TorchContainer Register(String path, String wallPath, TorchContainer container) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, container.asBlock());
		Registry.register(Registry.BLOCK, ID(wallPath), container.getWallBlock());
		Registry.register(Registry.ITEM, id, container.asItem());
		return container;
	}
	public static UnlitTorchContainer Register(String path, String wallPath, UnlitTorchContainer container) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, container.asBlock());
		Registry.register(Registry.BLOCK, ID(wallPath), container.getWallBlock());
		return container;
	}
	public static SignContainer Register(String name, SignContainer sign) {
		Register(name + "_sign", name + "_wall_sign", (WallBlockContainer)sign);
		Register(name + "_hanging_sign", name + "_wall_hanging_sign", sign.getHanging());
		return sign;
	}
	public static BoatContainer Register(String path, String chestPath, BoatContainer boat) {
		Register(path, boat.asItem());
		Register(chestPath, boat.getChestBoat());
		return boat;
	}
	public static PottedBlockContainer Register(String path, String pottedPath, PottedBlockContainer potted) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, potted.asBlock());
		Registry.register(Registry.ITEM, id, potted.asItem());
		Registry.register(Registry.BLOCK, ID(pottedPath), potted.getPottedBlock());
		return potted;
	}
	public static <T extends Entity> EntityType<T> Register(String path, EntityType<T> entityType) {
		return Registry.register(Registry.ENTITY_TYPE, ID(path), entityType);
	}
	public static <T extends BlockEntity> BlockEntityType<T> Register(String path, BlockEntityType<T> entity) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, ID(path), entity);
	}
	public static <T extends BlockEntity> void Register(String path, BlockContainer container, BlockEntityType<T> entity) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, container.asBlock());
		Registry.register(Registry.ITEM, id, container.asItem());
		Registry.register(Registry.BLOCK_ENTITY_TYPE, id, entity);
	}
	public static <T extends ParticleEffect> ParticleType<T> Register(String path, ParticleType<T> particleType) {
		return Registry.register(Registry.PARTICLE_TYPE, ID(path), particleType);
	}
	public static <T extends FeatureConfig> Feature<T> Register(String path, Feature<T> feature) {
		return Registry.register(Registry.FEATURE, ID(path), feature);
	}
	public static <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<FC, ?>> Register(String id, ConfiguredFeature<FC, F> feature) {
		return BuiltinRegistries.method_40360(BuiltinRegistries.CONFIGURED_FEATURE, id, feature);
	}
	public static RegistryEntry<PlacedFeature> Register(String id, RegistryEntry<? extends ConfiguredFeature<?, ?>> registryEntry, List<PlacementModifier> modifiers) {
		return PlacedFeatures.register(ID(id).toString(), registryEntry, modifiers);
	}
	public static RegistryEntry<PlacedFeature> Register(String id, RegistryEntry<? extends ConfiguredFeature<?, ?>> registryEntry, PlacementModifier ... modifiers) {
		return PlacedFeatures.register(ID(id).toString(), registryEntry, List.of(modifiers));
	}
	public static RegistryEntry<StructureProcessorList> RegisterStructureProcessorList(String id, ImmutableList<StructureProcessor> processorList) {
		StructureProcessorList structureProcessorList = new StructureProcessorList(processorList);
		return BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_PROCESSOR_LIST, ID(id), structureProcessorList);
	}


	public static DefaultParticleType Register(String path, DefaultParticleType effect) {
		return Registry.register(Registry.PARTICLE_TYPE, ID(path), effect);
	}
	public static StatusEffect Register(String path, StatusEffect effect) {
		return Registry.register(Registry.STATUS_EFFECT, ID(path), effect);
	}
	public static Enchantment Register(String path, Enchantment enchantment) {
		return Registry.register(Registry.ENCHANTMENT, ID(path), enchantment);
	}
	public static Biome Register(RegistryKey<Biome> key, Biome biome) {
		BuiltinBiomesInvoker.Register(key, biome);
		return biome;
	}
	public static <T extends Recipe<?>> RecipeType<T> RegisterRecipeType(String path) {
		return Registry.register(Registry.RECIPE_TYPE, ID(path), new RecipeType<T>() { public String toString() { return path; } });
	}
	public static <T extends Recipe<?>> RecipeSerializer<T> Register(String path, RecipeSerializer<T> serializer) {
		return Registry.register(Registry.RECIPE_SERIALIZER, ID(path), serializer);
	}
	public static <T extends Power> void Register(PowerFactory<T> powerFactory) { Registry.register(ApoliRegistries.POWER_FACTORY, powerFactory.getSerializerId(), powerFactory); }
	public static <T extends Power> void Register(PowerFactorySupplier<T> factorySupplier) { Register(factorySupplier.createFactory()); }

	public static void Register(String path, PottedBlockContainer potted) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, potted.asBlock());
		Registry.register(Registry.ITEM, id, potted.asItem());
		String registryName = path.startsWith("minecraft:") ? ("minecraft:potted_" + path.substring("minecraft:".length())) : ("potted_" + path);
		Registry.register(Registry.BLOCK, ID(registryName), potted.getPottedBlock());
	}

	public static ModFactory Register(String name, ModFactory material) {
		FlammableBlockRegistry FLAMMABLE = FlammableBlockRegistry.getDefaultInstance();
		FuelRegistry FUEL = FuelRegistry.INSTANCE;
		CompostingChanceRegistry COMPOST = CompostingChanceRegistry.INSTANCE;
//		if (material instanceof PottedProvider potted) Register(name, potted.getPotted());
//		else if (material instanceof Provider provider) Register(name, provider.get()); //PottedProvider registers the block for us
		//Crafting
		//Tool
/*		if (material instanceof ShearsProvider shearsProvider) {
			Item shears = shearsProvider.getShears();
			Register(name + "_shears", shears);
			DispenserBlock.registerBehavior(shears, new ShearsDispenserBehavior());
		}*/
/*		if (material instanceof BaleProvider bail) {
			BlockContainer pair = bail.getBale();
			Register(name + "_bale", pair);
			COMPOST.add(pair.getItem(), 0.85F);
			if (flammable) FLAMMABLE.add(pair.getBlock(), 60, 20);
		}*/
/*		if (material instanceof BundleProvider bundle) {
			BlockContainer pair = bundle.getBundle();
			Register(name + "_bundle", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}*/
/*		if (material instanceof StrippedBundleProvider strippedBundle) {
			BlockContainer pair = strippedBundle.getStrippedBundle();
			Register(name + "_bundle", pair);
			Block strippedBundleBlock = pair.getBlock();
			if (flammable) {
				FLAMMABLE.add(strippedBundleBlock, 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}*/
/*		if (material instanceof StemProvider stem) {
			BlockContainer pair = stem.getStem();
			Register(name + "_stem", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}*/
/*		if (material instanceof StrippedStemProvider strippedStem) {
			BlockContainer pair = strippedStem.getStrippedStem();
			Register("stripped_" + name + "_stem", pair);
			Block strippedStemBlock = pair.getBlock();
			if (flammable) {
				FLAMMABLE.add(strippedStemBlock, 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}*/
/*		if (material instanceof HyphaeProvider hyphae) {
			BlockContainer pair = hyphae.getHyphae();
			Register(name + "_hyphae", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}*/
/*		if (material instanceof StrippedHyphaeProvider strippedHyphae) {
			BlockContainer pair = strippedHyphae.getStrippedHyphae();
			Register("stripped_" + name + "_hyphae", pair);
			Block strippedHyphaeBlock = pair.getBlock();
			if (flammable) {
				FLAMMABLE.add(strippedHyphaeBlock, 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}*/
/*		if (material instanceof LeavesProvider leaves) {
			BlockContainer pair = leaves.getLeaves();
			Register(name + "_leaves", pair);
			COMPOST.add(pair.getItem(), 0.3f);
			if (flammable) FLAMMABLE.add(pair.getBlock(), 30, 60);
		}*/
/*		if (material instanceof WartBlockProvider wartBlock){
			BlockContainer pair = wartBlock.getWartBlock();
			Register(name + "_wart_block", pair);
			COMPOST.add(pair.getItem(), 0.85f);
		}*/
/*		if (material instanceof SaplingProvider saplingProvider) {
			SaplingContainer sapling = saplingProvider.getSapling();
			Register(name + "_sapling", sapling);
			COMPOST.add(sapling.ITEM, 0.3f);
		}*/
/*		if (material instanceof FungusProvider fungusProvider) {
			FungusContainer fungus = fungusProvider.getFungus();
			Register(name + "_fungus", fungus);
			COMPOST.add(fungus.ITEM, 0.65f);
		}*/
/*		if (material instanceof PropaguleProvider propaguleProvider) {
			PottedBlockContainer propagule = propaguleProvider.getPropagule();
			Register(name + "_propagule", propagule);
			COMPOST.add(propagule.ITEM, 0.3f);
		}*/
/*		if (material instanceof PlanksProvider planks) {
			BlockContainer pair = planks.getPlanks();
			Register(name + "_planks", pair);
			if (flammable) FLAMMABLE.add(pair.getBlock(), 5, 20);
		}*/
/*		if (material instanceof FenceProvider fence) {
			BlockContainer pair = fence.getFence();
			Register(name + "_fence", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 20);
				FUEL.add(pair.getItem(), 300);
			}
		}*/
/*		if (material instanceof BookshelfProvider bookshelf) {
			BlockContainer pair = bookshelf.getBookshelf();
			Register(name + "_bookshelf", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 30, 20);
				FUEL.add(pair.getItem(), 300);
			}
		}*/
/*		if (material instanceof ChiseledBookshelfProvider chiseledBookshelf) {
			BlockContainer pair = chiseledBookshelf.getChiseledBookshelf();
			Register(name + "_chiseled_bookshelf", pair);
			if (flammable) FUEL.add(pair.getItem(), 300);
		}*/
/*		if (material instanceof RowProvider row) {
			BlockContainer pair = row.getRow();
			Register(name + "_row", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 20);
				FUEL.add(pair.getItem(), 300);
			}
		}*/
/*		if (material instanceof LadderProvider ladder) {
			BlockContainer pair = ladder.getLadder();
			Register(name + "_ladder", pair);
			if (flammable) FUEL.add(pair.getItem(), 300);
		}*/
//		if (material instanceof WoodcutterProvider woodcutter) Register(name + "_woodcutter", woodcutter.getWoodcutter());
		return material;
	}
}
