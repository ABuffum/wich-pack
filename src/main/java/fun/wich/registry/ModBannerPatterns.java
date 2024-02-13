package fun.wich.registry;

import fun.wich.gen.data.language.Lang;
import fun.wich.gen.data.language.Words;
import fun.wich.util.banners.ModBannerPattern;

import java.util.List;

import static fun.wich.ModBase.EN_US;
import static fun.wich.registry.ModRegistry.Register;

public class ModBannerPatterns {
	public static final ModBannerPattern CLUB = new ModBannerPattern("club", "club", true);
	public static final ModBannerPattern DIAMOND = new ModBannerPattern("diamond", "diamond", true);
	public static final ModBannerPattern HEART = new ModBannerPattern("heart", "heart", true);
	public static final ModBannerPattern SPADE = new ModBannerPattern("spade", "spade", true);

	public static void RegisterBannerPatterns() {
		List<String> translations = List.of(Lang.join(Words.Banner, Words.Pattern));
		Register("club_banner_pattern", CLUB.getPatternItem(), translations);
		Register("diamond_banner_pattern", DIAMOND.getPatternItem(), translations);
		Register("heart_banner_pattern", HEART.getPatternItem(), translations);
		Register("spade_banner_pattern", SPADE.getPatternItem(), translations);
	}
}
