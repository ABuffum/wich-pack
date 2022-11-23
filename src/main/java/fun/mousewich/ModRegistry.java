package fun.mousewich;

import fun.mousewich.dispenser.ModBoatDispenserBehavior;
import fun.mousewich.container.*;

import fun.mousewich.dispenser.HorseArmorDispenserBehavior;
import fun.mousewich.material.*;
import fun.mousewich.material.providers.*;
import fun.mousewich.material.providers.armor.BootsProvider;
import fun.mousewich.material.providers.armor.HelmetProvider;
import fun.mousewich.material.providers.armor.HorseArmorProvider;
import fun.mousewich.material.providers.armor.LeggingsProvider;
import fun.mousewich.material.providers.tool.*;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.*;

import static fun.mousewich.ModBase.*;

public class ModRegistry {
	public static Block Register(String path, Block block) { return Registry.register(Registry.BLOCK, ID(path), block); }
	public static Item Register(String path, Item item) { return Registry.register(Registry.ITEM, ID(path), item); }
	public static BlockContainer Register(String path, BlockContainer container) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, container.getBlock());
		Registry.register(Registry.ITEM, id, container.getItem());
		return container;
	}
	public static WallBlockContainer Register(String path, String wallPath, WallBlockContainer container) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, container.getBlock());
		Registry.register(Registry.BLOCK, ID(wallPath), container.getWallBlock());
		Registry.register(Registry.ITEM, id, container.getItem());
		return container;
	}
	public static TorchContainer Register(String path, String wallPath, TorchContainer container) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, container.getBlock());
		Registry.register(Registry.BLOCK, ID(wallPath), container.getWallBlock());
		Registry.register(Registry.ITEM, id, container.getItem());
		return container;
	}
	public static UnlitTorchContainer Register(String path, String wallPath, UnlitTorchContainer container) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, container.getBlock());
		Registry.register(Registry.BLOCK, ID(wallPath), container.getWallBlock());
		return container;
	}
	public static SignContainer Register(String path, String wallPath, SignContainer sign) {
		return (SignContainer)Register(path, wallPath, (WallBlockContainer)sign);
	}
	public static BoatContainer Register(String path, BoatContainer boat) {
		Register(path, boat.getItem());
		DispenserBlock.registerBehavior(boat.getItem(), new ModBoatDispenserBehavior(boat.getType()));
		return boat;
	}
	public static PottedBlockContainer Register(String path, String pottedPath, PottedBlockContainer potted) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, potted.getBlock());
		Registry.register(Registry.ITEM, id, potted.getItem());
		Registry.register(Registry.BLOCK, ID(pottedPath), potted.getPottedBlock());
		return potted;
	}
	public static <T extends Entity> EntityType<T> Register(String path, EntityType<T> entityType) {
		return Registry.register(Registry.ENTITY_TYPE, ID(path), entityType);
	}
	public static DefaultParticleType Register(String path, DefaultParticleType effect) {
		return Registry.register(Registry.PARTICLE_TYPE, ID(path), effect);
	}
	public static BaseMaterial Register(String name, BaseMaterial material) {
		boolean flammable = material.isFlammable();
		FlammableBlockRegistry FLAMMABLE = FlammableBlockRegistry.getDefaultInstance();
		FuelRegistry FUEL = FuelRegistry.INSTANCE;
		CompostingChanceRegistry COMPOST = CompostingChanceRegistry.INSTANCE;
//		if (material instanceof PottedProvider potted) Register(name, potted.getPotted());
//		else if (material instanceof Provider provider) Register(name, provider.get()); //PottedProvider registers the block for us
		//Crafting
//		if (material instanceof NuggetProvider nugget) Register(name + "_nugget", nugget.getNugget());
		//Tool
		if (material instanceof AxeProvider axe) Register(name + "_axe", axe.getAxe());
		if (material instanceof HoeProvider hoe) Register(name + "_hoe", hoe.getHoe());
		if (material instanceof PickaxeProvider pickaxe) Register(name + "_pickaxe", pickaxe.getPickaxe());
		if (material instanceof ShovelProvider shovel) Register(name + "_shovel", shovel.getShovel());
		if (material instanceof SwordProvider sword) Register(name + "_sword", sword.getSword());
		if (material instanceof KnifeProvider knife) Register(name + "_knife", knife.getKnife());
		if (material instanceof HelmetProvider helmet) Register(name + "_helmet", helmet.getHelmet());
		if (material instanceof ChestplateProvider chestplate) Register(name + "_chestplate", chestplate.getChestplate());
		if (material instanceof LeggingsProvider leggings) Register(name + "_leggings", leggings.getLeggings());
		if (material instanceof BootsProvider boots) Register(name + "_boots", boots.getBoots());
		if (material instanceof HorseArmorProvider horseArmorProvider){
			Item horseArmor = horseArmorProvider.getHorseArmor();
			Register(name + "_horse_armor", horseArmor);
			DispenserBlock.registerBehavior(horseArmor, new HorseArmorDispenserBehavior()::dispenseSilently);
		}
/*		if (material instanceof ShearsProvider shearsProvider) {
			Item shears = shearsProvider.getShears();
			Register(name + "_shears", shears);
			DispenserBlock.registerBehavior(shears, new ShearsDispenserBehavior());
		}*/
//		if (material instanceof BucketProvider bucketProvider) RegisterBuckets(name, bucketProvider);
		//Blocks
//		if (material instanceof TorchProvider torch) Register(name, torch.getTorch());
//		if (material instanceof OxidizableTorchProvider oxidizableTorch) Register(name, oxidizableTorch.getOxidizableTorch());
//		if (material instanceof SoulTorchProvider soulTorch) Register(name + "_soul", soulTorch.getSoulTorch());
//		if (material instanceof OxidizableSoulTorchProvider oxidizableSoulTorch) Register(name + "_soul", oxidizableSoulTorch.getOxidizableSoulTorch());
//		if (material instanceof EnderTorchProvider enderTorch) Register(name + "_ender", enderTorch.getEnderTorch());
//		if (material instanceof OxidizableEnderTorchProvider oxidizableEnderTorch) Register(name + "_ender", oxidizableEnderTorch.getOxidizableEnderTorch());
//		if (material instanceof CampfireProvider campfire) Register(name + "_campfire", campfire.getCampfire());
//		if (material instanceof SoulCampfireProvider soulCampfire) Register(name + "_soul_campfire", soulCampfire.getSoulCampfire());
//		if (material instanceof EnderCampfireProvider enderCampfire) Register(name + "_ender_campfire", enderCampfire.getEnderCampfire());
/*		if (material instanceof LanternProvider lantern) {
			Register(name + "_lantern", lantern.getLantern());
			Register("unlit_" + name + "_lantern", lantern.getUnlitLantern());
		}*/
//		if (material instanceof OxidizableLanternProvider oxidizableLantern) Register(name + "_lantern", oxidizableLantern.getOxidizableLantern());
/*		if (material instanceof SoulLanternProvider soulLantern) {
			Register(name + "_soul_lantern", soulLantern.getSoulLantern());
			Register("unlit_" + name + "_soul_lantern", soulLantern.getUnlitSoulLantern());
		}*/
//		if (material instanceof OxidizableSoulLanternProvider oxidizableSoulLantern) Register(name + "_soul_lantern", oxidizableSoulLantern.getOxidizableSoulLantern());
/*		if (material instanceof EnderLanternProvider enderLantern) {
			Register(name + "_ender_lantern", enderLantern.getEnderLantern());
			Register("unlit_" + name + "_ender_lantern", enderLantern.getUnlitEnderLantern());
		}*/
//		if (material instanceof OxidizableEnderLanternProvider oxidizableEnderLantern) Register(name + "_ender_lantern", oxidizableEnderLantern.getOxidizableEnderLantern());
//		if (material instanceof ChainProvider chain) Register(name + "_chain", chain.getChain());
//		if (material instanceof OxidizableChainProvider oxidizableChain) Register(name + "_chain", oxidizableChain.getOxidizableChain());
//		if (material instanceof BarsProvider bars) Register(name + "_bars", bars.getBars());
//		if (material instanceof OxidizableBarsProvider oxidizableBars) Register(name + "_bars", oxidizableBars.getOxidizableBars());
		if (material instanceof BlockProvider block) Register(name + "_block", block.getBlock());
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
/*		if (material instanceof LogProvider log) {
			BlockContainer pair = log.getLog();
			Register(name + "_log", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}*/
/*		if (material instanceof StrippedLogProvider strippedLog) {
			BlockContainer pair = strippedLog.getStrippedLog();
			Register(name + "_log", pair);
			Block strippedLogBlock = pair.getBlock();
			if (flammable) {
				FLAMMABLE.add(strippedLogBlock, 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}*/
/*		if (material instanceof WoodProvider wood) {
			BlockContainer pair = wood.getWood();
			Register(name + "_wood", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}*/
/*		if (material instanceof StrippedWoodProvider strippedWood) {
			BlockContainer pair = strippedWood.getStrippedWood();
			Register(name + "_wood", pair);
			Block strippedWoodBlock = pair.getBlock();
			if (flammable) {
				FLAMMABLE.add(strippedWoodBlock, 5, 5);
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
		if (material instanceof SlabProvider slab) {
			BlockContainer pair = slab.getSlab();
			Register(name + "_slab", pair);
			if (flammable) FLAMMABLE.add(pair.getBlock(), 5, 20);
		}
		if (material instanceof StairsProvider stairs) {
			BlockContainer pair = stairs.getStairs();
			Register(name + "_stairs", pair);
			if (flammable) FLAMMABLE.add(pair.getBlock(), 5, 20);
		}
//		if (material instanceof DoorProvider door) Register(name + "_door", door.getDoor());
//		if (material instanceof TrapdoorProvider trapdoor) Register(name + "_trapdoor", trapdoor.getTrapdoor());
//		if (material instanceof PressurePlateProvider pressurePlate) Register(name + "_pressure_plate", pressurePlate.getPressurePlate());
//		if (material instanceof ButtonProvider button) Register(name + "_button", button.getButton());
//		if (material instanceof OxidizableButtonProvider oxidizableButton) Register(name + "_button", oxidizableButton.getOxidizableButton());
/*		if (material instanceof FenceProvider fence) {
			BlockContainer pair = fence.getFence();
			Register(name + "_fence", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 20);
				FUEL.add(pair.getItem(), 300);
			}
		}*/
/*		if (material instanceof FenceGateProvider fenceGate) {
			BlockContainer pair = fenceGate.getFenceGate();
			Register(name + "_fence_gate", pair);
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
		if (material instanceof WallProvider wall) Register(name + "_wall", wall.getWall());
//		if (material instanceof OxidizableWallProvider oxidizableWall) Register(name + "_wall", oxidizableWall.getOxidizableWall());
//		if (material instanceof PaneProvider pane) Register(name + "_pane", pane.getPane());
//		if (material instanceof SmoothProvider smooth) Register("smooth_" + name, smooth.getSmooth());
//		if (material instanceof SmoothBlockProvider smoothBlock) Register("smooth_" + name + "_block", smoothBlock.getSmoothBlock());
//		if (material instanceof SmoothSlabProvider smoothSlab) Register("smooth_" + name + "_slab", smoothSlab.getSmoothSlab());
//		if (material instanceof SmoothStairsProvider smoothStairs) Register("smooth_" + name + "_stairs", smoothStairs.getSmoothStairs());
//		if (material instanceof SmoothWallProvider smoothWall) Register("smooth_" + name + "_wall", smoothWall.getSmoothWall());
		if (material instanceof CrystalBlockProvider crystalBlock) Register(name + "_crystal_block", crystalBlock.getCrystalBlock());
		if (material instanceof CrystalSlabProvider crystalSlab) Register(name + "_crystal_slab", crystalSlab.getCrystalSlab());
		if (material instanceof CrystalStairsProvider crystalStairs) Register(name + "_crystal_stairs", crystalStairs.getCrystalStairs());
		if (material instanceof CrystalWallProvider crystalWall) Register(name + "_crystal_wall", crystalWall.getCrystalWall());
//		if (material instanceof PackedProvider packed) Register("packed_" + name, packed.getPacked());
		if (material instanceof BricksProvider bricks) Register(name + "_bricks", bricks.getBricks());
//		if (material instanceof OxidizableBricksProvider oxidizableBricks) Register(name + "_bricks", oxidizableBricks.getOxidizableBricks());
		if (material instanceof BrickSlabProvider brickSlab) Register(name + "_brick_slab", brickSlab.getBrickSlab());
//		if (material instanceof OxidizableBrickSlabProvider oxidizableBrickSlab) Register(name + "_brick_slab", oxidizableBrickSlab.getOxidizableBrickSlab());
		if (material instanceof BrickStairsProvider brickStairs) Register(name + "_brick_stairs", brickStairs.getBrickStairs());
//		if (material instanceof OxidizableBrickStairsProvider oxidizableBrickStairs) Register(name + "_brick_stairs", oxidizableBrickStairs.getOxidizableBrickStairs());
		if (material instanceof BrickWallProvider brickWall) Register(name + "_brick_wall", brickWall.getBrickWall());
//		if (material instanceof OxidizableBrickWallProvider oxidizableBrickWall) Register(name + "_brick_wall", oxidizableBrickWall.getOxidizableBrickWall());
//		if (material instanceof CutProvider cut) Register("cut_" + name, cut.getCut());
//		if (material instanceof CutPillarProvider cutPillar) Register("cut_" + name + "_pillar", cutPillar.getCutPillar());
//		if (material instanceof OxidizableCutPillarProvider oxidizableCutPillar) Register("cut_" + name + "_pillar", oxidizableCutPillar.getOxidizableCutPillar());
//		if (material instanceof CutSlabProvider cutSlab) Register("cut_" + name + "_slab", cutSlab.getCutSlab());
//		if (material instanceof CutStairsProvider cutStairs) Register("cut_" + name + "_stairs", cutStairs.getCutStairs());
//		if (material instanceof CutWallProvider cutWall) Register("cut_" + name + "_wall", cutWall.getCutWall());
//		if (material instanceof OxidizableCutWallProvider oxidizableCutWall) Register("cut_" + name + "_wall", oxidizableCutWall.getOxidizableCutWall());
//		if (material instanceof PolishedProvider polished) Register("polished_" + name, polished.getPolished());
//		if (material instanceof PolishedSlabProvider polishedSlab) Register("polished_" + name + "_slab", polishedSlab.getPolishedSlab());
//		if (material instanceof PolishedStairsProvider polishedStairs) Register("polished_" + name + "_stairs", polishedStairs.getPolishedStairs());
//		if (material instanceof PolishedWallProvider polishedWall) Register("polished_" + name + "_wall", polishedWall.getPolishedWall());
//		if (material instanceof PolishedBricksProvider polishedBricks) Register("polished_" + name + "_bricks", polishedBricks.getPolishedBricks());
//		if (material instanceof PolishedBrickSlabProvider polishedBrickSlab) Register("polished_" + name + "_brick_slab", polishedBrickSlab.getPolishedBrickSlab());
//		if (material instanceof PolishedBrickStairsProvider polishedBrickStairs) Register("polished_" + name + "_brick_stairs", polishedBrickStairs.getPolishedBrickStairs());
//		if (material instanceof PolishedBrickWallProvider polishedBrickWall) Register("polished_" + name + "_brick_wall", polishedBrickWall.getPolishedBrickWall());
/*		if (material instanceof LadderProvider ladder) {
			BlockContainer pair = ladder.getLadder();
			Register(name + "_ladder", pair);
			if (flammable) FUEL.add(pair.getItem(), 300);
		}*/
//		if (material instanceof WoodcutterProvider woodcutter) Register(name + "_woodcutter", woodcutter.getWoodcutter());
//		if (material instanceof SignProvider sign) Register(name + "_sign", name + "_wall_sign", sign.getSign());
/*		if (material instanceof BoatProvider boatProvider) {
			HavenBoat boat = boatProvider.getBoat();
			Register(boat.TYPE.getName() + "_boat", boat.ITEM);
			DispenserBlock.registerBehavior(boat.ITEM, new HavenBoatDispenserBehavior(boat.TYPE));
		}*/
		return material;
	}
}
