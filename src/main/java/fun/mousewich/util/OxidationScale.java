package fun.mousewich.util;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import fun.mousewich.container.BlockContainer;
import fun.mousewich.container.OxidizableBlockContainer;
import fun.mousewich.container.OxidizableTorchContainer;
import fun.mousewich.container.TorchContainer;
import net.minecraft.block.*;

import java.util.*;
import java.util.function.Supplier;

import static java.util.Map.entry;

public class OxidationScale {
	protected final Block unaffected;
	public Block getUnaffected() { return unaffected; }
	protected final Block exposed;
	public Block getExposed() { return exposed; }
	protected final Block weathered;
	public Block getWeathered() { return weathered; }
	protected final Block oxidized;
	public Block getOxidized() { return oxidized; }

	public OxidationScale(Block unaffected, Block exposed, Block weathered, Block oxidized) {
		this.unaffected = unaffected;
		this.exposed = exposed;
		this.weathered = weathered;
		this.oxidized = oxidized;
	}


	public static final Set<OxidationScale> OXIDIZABLE_BLOCKS = new HashSet<OxidationScale>();

	public static final OxidationScale COPPER_BLOCK = Register(Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER);
	public static final OxidationScale CUT_COPPER = Register(Blocks.CUT_COPPER, Blocks.EXPOSED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER);
	public static final OxidationScale CUT_COPPER_SLAB = Register(Blocks.CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER_SLAB);
	public static final OxidationScale CUT_COPPER_STAIRS = Register(Blocks.CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER_STAIRS);

	public static MapColor getMapColor(Oxidizable.OxidationLevel level) {
		if (level == Oxidizable.OxidationLevel.OXIDIZED) return MapColor.TEAL;
		else if (level == Oxidizable.OxidationLevel.WEATHERED) return MapColor.DARK_AQUA;
		else if (level == Oxidizable.OxidationLevel.EXPOSED) return MapColor.TERRACOTTA_LIGHT_GRAY;
		else return MapColor.ORANGE;
	}

	public static interface BlockSettingsSupplier {
		public AbstractBlock.Settings get(Oxidizable.OxidationLevel level);
	}

	public static final Map<Block, Block> WAXED_BLOCKS = new HashMap<Block, Block>(Map.<Block, Block>ofEntries(
			entry(Blocks.COPPER_BLOCK, Blocks.WAXED_COPPER_BLOCK), entry(Blocks.EXPOSED_COPPER, Blocks.WAXED_EXPOSED_COPPER),
			entry(Blocks.WEATHERED_COPPER, Blocks.WAXED_WEATHERED_COPPER), entry(Blocks.OXIDIZED_COPPER, Blocks.WAXED_OXIDIZED_COPPER),
			entry(Blocks.CUT_COPPER, Blocks.WAXED_CUT_COPPER), entry(Blocks.EXPOSED_CUT_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER),
			entry(Blocks.WEATHERED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER), entry(Blocks.OXIDIZED_CUT_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER),
			entry(Blocks.CUT_COPPER_SLAB, Blocks.WAXED_CUT_COPPER_SLAB), entry(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB),
			entry(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB), entry(Blocks.OXIDIZED_CUT_COPPER_SLAB, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB),
			entry(Blocks.CUT_COPPER_STAIRS, Blocks.WAXED_CUT_COPPER_STAIRS), entry(Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS),
			entry(Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS), entry(Blocks.OXIDIZED_CUT_COPPER_STAIRS, Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS)
	));

	public static void Register(OxidizableTorchContainer container) {
		Register(container.getUnaffected(), container.getExposed(), container.getWeathered(), container.getOxidized());
	}
	public static OxidationScale Register(OxidizableBlockContainer container) {
		return Register(container.getUnaffected().getBlock(), container.getExposed().getBlock(), container.getWeathered().getBlock(), container.getOxidized().getBlock());
	}
	public static OxidationScale Register(BlockContainer unaffected, BlockContainer exposed, BlockContainer weathered, BlockContainer oxidized) {
		return Register(unaffected.getBlock(), exposed.getBlock(), weathered.getBlock(), oxidized.getBlock());
	}
	public static void Register(TorchContainer unaffected, TorchContainer exposed, TorchContainer weathered, TorchContainer oxidized) {
		Register(unaffected.getBlock(), exposed.getBlock(), weathered.getBlock(), oxidized.getBlock());
		Register(unaffected.getWallBlock(), exposed.getWallBlock(), weathered.getWallBlock(), oxidized.getWallBlock());
	}
	public static OxidationScale Register(Block unaffected, Block exposed, Block weathered, Block oxidized) {
		return Register(new OxidationScale(unaffected, exposed, weathered, oxidized));
	}

	public static OxidationScale Register(OxidationScale scale) {
		if (scale != null) OXIDIZABLE_BLOCKS.add(scale);
		return scale;
	}

	public static Optional<Block> getDecreasedOxidationBlock(Block block) {
		return Optional.ofNullable(OxidationLevelDecreases().get().get(block));
	}

	public static Block getUnaffectedOxidationBlock(Block block) {
		Block block2 = block;
		for(Block block3 = OxidationLevelDecreases().get().get(block); block3 != null; block3 = OxidationLevelDecreases().get().get(block3)) {
			block2 = block3;
		}
		return block2;
	}

	public static Optional<BlockState> getDecreasedOxidationState(BlockState state) {
		return getDecreasedOxidationBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}

	public static Optional<Block> getIncreasedOxidationBlock(Block block) {
		return Optional.ofNullable(OxidationLevelIncreases().get().get(block));
	}

	public static BlockState getUnaffectedOxidationState(BlockState state) {
		return getUnaffectedOxidationBlock(state.getBlock()).getStateWithProperties(state);
	}

	private static Supplier<BiMap<Block, Block>> OxidationLevelIncreases() {
		return Suppliers.memoize(() -> {
			BiMap<Block, Block> map = HashBiMap.create();
			for(OxidationScale scale : OXIDIZABLE_BLOCKS) {
				map.put(scale.unaffected, scale.exposed);
				map.put(scale.exposed, scale.weathered);
				map.put(scale.weathered, scale.oxidized);
			}
			return map;
		});
	}
	private static Supplier<BiMap<Block, Block>> OxidationLevelDecreases() {
		return Suppliers.memoize(() -> OxidationLevelIncreases().get().inverse());
	}

	public static Optional<BlockState> getWaxedState(BlockState state) {
		return Optional.ofNullable(UnwaxedToWaxedBlocks().get().get(state.getBlock())).map((block) -> block.getStateWithProperties(state));
	}

	private static Supplier<BiMap<Block, Block>> UnwaxedToWaxedBlocks() {
		return Suppliers.memoize(() -> {
			BiMap<Block, Block> map = HashBiMap.create();
			for(Block unwaxed : WAXED_BLOCKS.keySet()) {
				map.put(unwaxed, WAXED_BLOCKS.get(unwaxed));
			}
			return map;
		});
	}
	public static Supplier<BiMap<Block, Block>> WaxedToUnwaxedBlocks() {
		return Suppliers.memoize(() -> UnwaxedToWaxedBlocks().get().inverse());
	}
}
