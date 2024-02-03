package fun.wich.util.banners;

import net.minecraft.entity.player.PlayerEntity;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@FunctionalInterface
public interface ModBannerPatternLimitModifier {
	Event<ModBannerPatternLimitModifier> EVENT = EventFactory.createArrayBacked(
			ModBannerPatternLimitModifier.class,
			modifiers -> (currentLimit, player) -> {
				int limit = currentLimit;
				for (ModBannerPatternLimitModifier modifier : modifiers) {
					limit = modifier.computePatternLimit(limit, player);
				}
				return limit;
			}
	);

	int computePatternLimit(int currentLimit, PlayerEntity player);
}