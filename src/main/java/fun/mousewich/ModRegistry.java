package fun.mousewich;

import com.google.common.collect.ImmutableList;
import fun.mousewich.container.*;

import fun.mousewich.entity.projectile.ModArrowEntity;
import fun.mousewich.item.projectile.ModArrowItem;
import fun.mousewich.mixins.world.BuiltinBiomesInvoker;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.PowerFactorySupplier;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.Pair;
import net.minecraft.util.Util;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;
import java.util.stream.Stream;

import static fun.mousewich.ModBase.*;

public class ModRegistry {
	public static <T, S> List<T>getLeft(Pair<T, S>[] pairs) { return getLeft(List.of(pairs)); }
	public static <T, S> List<T>getLeft(List<Pair<T, S>> pairs) { return getLeft(pairs.stream()); }
	public static <T, S> List<T>getLeft(Stream<Pair<T, S>> pairs) { return pairs.map(Pair::getLeft).toList(); }
	public static <T, S> List<S>getRight(Pair<T, S>[] pairs) { return getRight(List.of(pairs)); }
	public static <T, S> List<S>getRight(List<Pair<T, S>> pairs) { return getRight(pairs.stream()); }
	public static <T, S> List<S>getRight(Stream<Pair<T, S>> pairs) { return pairs.map(Pair::getRight).toList(); }

	public static Block Register(String path, Block value, List<String> translations) {
		int length = translations.size();
		Identifier id = ID(path);
		for (int i = 0; i < LANGUAGE_CACHES.length; i++) {
			if (length <= i) throw new RuntimeException("Missing translation for Language: " + LANGUAGE_CACHES[i].getLanguageCode() + " & Block: " + id);
			LANGUAGE_CACHES[i].TranslationKeys.put(Util.createTranslationKey("block", ID(path)), translations.get(i));
		}
		return Registry.register(Registry.BLOCK, id, value);
	}
	public static Item Register(String path, Item value, List<String> translations) {
		int length = translations.size();
		Identifier id = ID(path);
		for (int i = 0; i < LANGUAGE_CACHES.length; i++) {
			if (length <= i) throw new RuntimeException("Missing translation for Language: " + LANGUAGE_CACHES[i].getLanguageCode() + " & Item: " + id);
			LANGUAGE_CACHES[i].TranslationKeys.put(Util.createTranslationKey("item", ID(path)), translations.get(i));
		}
		return Registry.register(Registry.ITEM, id, value);
	}
	public static BlockContainer Register(String path, BlockContainer value, List<String> translations) {
		Register(path, value.asBlock(), translations);
		Register(path, value.asItem(), translations);
		return value;
	}
	public static IWallBlockItemContainer Register(String path, String wallPath, IWallBlockItemContainer value, List<Pair<String, String>> translations) {
		List<String> left = getLeft(translations);
		Register(path, value.asBlock(), left);
		Register(path, value.asItem(), left);
		Register(wallPath, value.getWallBlock(), getRight(translations));
		return value;
	}
	public static UnlitTorchContainer Register(String path, String wallPath, UnlitTorchContainer value, List<Pair<String, String>> translations) {
		Register(path, value.asBlock(), getLeft(translations));
		Register(wallPath, value.getWallBlock(), getRight(translations));
		return value;
	}
	public static SignContainer Register(String name, SignContainer value, List<Pair<Pair<String, String>, Pair<String, String>>> translations) {
		Register(name + "_sign", name + "_wall_sign", value, getLeft(translations));
		Register(name + "_hanging_sign", name + "_wall_hanging_sign", value.getHanging(), getRight(translations));
		return value;
	}
	public static BoatContainer Register(String name, BoatContainer value, List<Pair<String, String>> translations) { return Register(name, "boat", value, translations); }
	public static BoatContainer Register(String name, String type, BoatContainer value, List<Pair<String, String>> translations) {
		Register(name + "_" + type, value.asItem(), getLeft(translations));
		Register(name + "_chest_" + type, value.getChestBoat(), getRight(translations));
		return value;
	}
	public static PottedBlockContainer Register(String path, PottedBlockContainer value, List<Pair<String, String>> translations) {
		List<String> left = getLeft(translations);
		Register(path, value.asBlock(), left);
		Register(path, value.asItem(), left);
		Identifier id = ID(path);
		Register(new Identifier(id.getNamespace(), "potted_" + id.getPath()).toString(), value.getPottedBlock(), getRight(translations));
		return value;
	}
	public static <T extends Entity> EntityType<T> Register(String path, EntityType<T> value, List<String> translations) {
		int length = translations.size();
		Identifier id = ID(path);
		for (int i = 0; i < LANGUAGE_CACHES.length; i++) {
			if (length <= i) throw new RuntimeException("Missing translation for Language: " + LANGUAGE_CACHES[i].getLanguageCode() + " & Entity Type: " + id);
			LANGUAGE_CACHES[i].TranslationKeys.put(Util.createTranslationKey("entity", ID(path)), translations.get(i));
		}
		return Registry.register(Registry.ENTITY_TYPE, id, value);
	}
	public static <T extends BlockEntity> BlockEntityType<T> Register(String path, BlockEntityType<T> value) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, ID(path), value);
	}
	public static <T extends BlockEntity> void Register(String path, BlockContainer container, BlockEntityType<T> type, List<String> translations) {
		Register(path, container, translations);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, ID(path), type);
	}
	public static <T extends ParticleEffect> ParticleType<T> Register(String path, ParticleType<T> value) {
		return Registry.register(Registry.PARTICLE_TYPE, ID(path), value);
	}
	public static <T extends FeatureConfig> Feature<T> Register(String path, Feature<T> value) {
		return Registry.register(Registry.FEATURE, ID(path), value);
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
	public static StatusEffect Register(String path, StatusEffect value, List<String> translations) {
		int length = translations.size();
		Identifier id = ID(path);
		if (length == 0) throw new RuntimeException("Must provide at least one translation for Status Effect: " + id);
		ModBase.EN_US.TranslationKeys.put(Util.createTranslationKey("effect", id), translations.get(0));
		return Registry.register(Registry.STATUS_EFFECT, id, value);
	}
	public static Enchantment Register(String path, Enchantment value, List<String> translations) {
		int length = translations.size();
		Identifier id = ID(path);
		if (length == 0) throw new RuntimeException("Must provide at least one translation for Enchantment: " + id);
		ModBase.EN_US.TranslationKeys.put(Util.createTranslationKey("enchantment", id), translations.get(0));
		return Registry.register(Registry.ENCHANTMENT, id, value);
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
	public static Identifier Register(String path, StatFormatter formatter) { return Register(ID(path), formatter); }
	public static Identifier Register(Identifier identifier, StatFormatter formatter) {
		Registry.register(Registry.CUSTOM_STAT, identifier, identifier);
		Stats.CUSTOM.getOrCreateStat(identifier, formatter);
		return identifier;
	}
	public static LootFunctionType Register(String id, JsonSerializer<? extends LootFunction> jsonSerializer) {
		return Registry.register(Registry.LOOT_FUNCTION_TYPE, ID(id), new LootFunctionType(jsonSerializer));
	}
	public static LootFunctionType Register(String id, LootFunctionType lootFunctionType) {
		return Registry.register(Registry.LOOT_FUNCTION_TYPE, ID(id), lootFunctionType);
	}
	public static ArrowContainer Register(String id, ArrowContainer arrow, List<String> translations) {
		Register(id, arrow.asItem(), translations);
		Register(id, arrow.getEntityType(), translations);
		DispenserBlock.registerBehavior(arrow, new ProjectileDispenserBehavior(){
			@Override
			protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
				ModArrowEntity arrow = ((ModArrowItem)stack.getItem()).createArrow(world, position.getX(), position.getY(), position.getZ());
				arrow.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
				return arrow;
			}
		});
		return arrow;
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
