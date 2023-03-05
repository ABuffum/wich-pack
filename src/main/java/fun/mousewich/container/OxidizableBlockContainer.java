package fun.mousewich.container;

import fun.mousewich.util.OxidationScale;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.function.Function;

import static net.minecraft.block.Oxidizable.OxidationLevel;

public class OxidizableBlockContainer implements IContainer {
	private final BlockContainer unaffected;
	public BlockContainer getUnaffected() { return unaffected; }
	private final BlockContainer exposed;
	public BlockContainer getExposed() { return exposed; }
	private final BlockContainer weathered;
	public BlockContainer getWeathered() { return weathered; }
	private final BlockContainer oxidized;
	public BlockContainer getOxidized() { return oxidized; }
	private final WaxedBlockContainer waxed_unaffected;
	public WaxedBlockContainer getWaxedUnaffected() { return waxed_unaffected; }
	private final WaxedBlockContainer waxed_exposed;
	public WaxedBlockContainer getWaxedExposed() { return waxed_exposed; }
	private final WaxedBlockContainer waxed_weathered;
	public WaxedBlockContainer getWaxedWeathered() { return waxed_weathered; }
	private final WaxedBlockContainer waxed_oxidized;
	public WaxedBlockContainer getWaxedOxidized() { return waxed_oxidized; }

	public static interface OxidizableBlockSupplier {
		public Block get(OxidationLevel level, AbstractBlock.Settings settings);
	}

	public OxidizableBlockContainer(OxidizableBlockSupplier oxidizable, Function<AbstractBlock.Settings, Block> waxed, OxidationScale.BlockSettingsSupplier settings, Item.Settings itemSettings) {
		unaffected = new BlockContainer(oxidizable.get(OxidationLevel.UNAFFECTED, settings.get(OxidationLevel.UNAFFECTED)), itemSettings);
		exposed = new BlockContainer(oxidizable.get(OxidationLevel.EXPOSED, settings.get(OxidationLevel.EXPOSED)), itemSettings);
		weathered = new BlockContainer(oxidizable.get(OxidationLevel.WEATHERED, settings.get(OxidationLevel.WEATHERED)), itemSettings);
		oxidized = new BlockContainer(oxidizable.get(OxidationLevel.OXIDIZED, settings.get(OxidationLevel.OXIDIZED)), itemSettings);
		waxed_unaffected = new WaxedBlockContainer(unaffected, waxed.apply(settings.get(OxidationLevel.UNAFFECTED)), itemSettings);
		waxed_exposed = new WaxedBlockContainer(exposed, waxed.apply(settings.get(OxidationLevel.EXPOSED)), itemSettings);
		waxed_weathered = new WaxedBlockContainer(weathered, waxed.apply(settings.get(OxidationLevel.WEATHERED)), itemSettings);
		waxed_oxidized = new WaxedBlockContainer(oxidized, waxed.apply(settings.get(OxidationLevel.OXIDIZED)), itemSettings);
	}

	public OxidizableBlockContainer(OxidizableBlockSupplier oxidizable, Function<AbstractBlock.Settings, Block> waxed, AbstractBlock.Settings settings, Item.Settings itemSettings) {
		unaffected = new BlockContainer(oxidizable.get(OxidationLevel.UNAFFECTED, settings), itemSettings);
		exposed = new BlockContainer(oxidizable.get(OxidationLevel.EXPOSED, settings), itemSettings);
		weathered = new BlockContainer(oxidizable.get(OxidationLevel.WEATHERED, settings), itemSettings);
		oxidized = new BlockContainer(oxidizable.get(OxidationLevel.OXIDIZED, settings), itemSettings);
		waxed_unaffected = new WaxedBlockContainer(unaffected, waxed.apply(settings), itemSettings);
		waxed_exposed = new WaxedBlockContainer(exposed, waxed.apply(settings), itemSettings);
		waxed_weathered = new WaxedBlockContainer(weathered, waxed.apply(settings), itemSettings);
		waxed_oxidized = new WaxedBlockContainer(oxidized, waxed.apply(settings), itemSettings);
	}

	private OxidizableBlockContainer(BlockContainer u, BlockContainer e, BlockContainer w, BlockContainer o, Block wu, Block we, Block ww, Block wo, Item.Settings itemSettings) {
		unaffected = u;
		exposed = e;
		weathered = w;
		oxidized = o;
		waxed_unaffected = new WaxedBlockContainer(u, wu, itemSettings);
		waxed_exposed = new WaxedBlockContainer(e, we, itemSettings);
		waxed_weathered = new WaxedBlockContainer(w, ww, itemSettings);
		waxed_oxidized = new WaxedBlockContainer(o, wo, itemSettings);
	}

	public boolean contains(Block block) {
		return unaffected.contains(block) || exposed.contains(block) || weathered.contains(block) || oxidized.contains(block)
				|| waxed_unaffected.contains(block) || waxed_exposed.contains(block) || waxed_weathered.contains(block) || waxed_oxidized.contains(block);
	}
	public boolean contains(Item item) {
		return unaffected.contains(item) || exposed.contains(item) || weathered.contains(item) || oxidized.contains(item)
				|| waxed_unaffected.contains(item) || waxed_exposed.contains(item) || waxed_weathered.contains(item) || waxed_oxidized.contains(item);
	}
}
