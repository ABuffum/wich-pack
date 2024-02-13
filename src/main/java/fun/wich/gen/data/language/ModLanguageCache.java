package fun.wich.gen.data.language;

import fun.wich.util.dye.ModDyeColor;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static fun.wich.gen.data.language.Words.*;

public abstract class ModLanguageCache {
	public final Map<String, String> TranslationKeys = new HashMap<>();

	public abstract String getLanguageCode();

	public abstract String Subtitle_Block_Hit(String name);
	public abstract String Subtitle_Block_Break(String name);
	public abstract String Subtitle_Block_Place(String name);
	public abstract String Subtitle_Block_Step(String name);

	public abstract String Subtitle_Boat_Paddle(String name);
	public abstract String Subtitle_Boat_Row(String name);

	public abstract String Subtitle_Entity_Big_Fall(String name);
	public abstract String Subtitle_Entity_Crash(String name);
	public abstract String Subtitle_Entity_Death(String name);
	public abstract String Subtitle_Entity_Small_Fall(String name);
	public abstract String Subtitle_Entity_Splash(String name);
	public abstract String Subtitle_Entity_Splash_High_Speed(String name);
	public abstract String Subtitle_Entity_Step(String name);
	public abstract String Subtitle_Entity_Swim(String name);

	public static String _add(String prefix, String postfix) { return (prefix + " " + postfix).trim(); }
	public static Pair<String, String> _apply(Function<String, String> func, Pair<String, String> pair) {
		return new Pair<>(func.apply(pair.getLeft()), func.apply(pair.getRight()));
	}

	public static class en_us extends ModLanguageCache {
		@Override
		public String getLanguageCode() { return "en_us"; }

		public String a(String word) { return _add(word, "a"); }
		public String agrees(String word) { return _add(word, "agrees"); }
		public String allays(String word) { return _add(word, "allays"); }
		public String and(String word) { return _add(word, "and"); }
		public String angrily(String word) { return _add(word, "angrily"); }
		public String attacks(String word) { return _add(word, "attacks"); }
		public String awkwardly(String word) { return _add(word, "awkwardly"); }
		public String bagged(String word) { return _add(word, "bagged"); }
		public String beats(String word) { return _add(word, "beats"); }
		public String blooms(String word) { return _add(word, "blooms"); }
		public String boot(String word) { return _add(word, "boot"); }
		public String breaking(String word) { return _add(word, "breaking"); }
		public String broken(String word) { return _add(word, "broken"); }
		public String bubbles(String word) { return _add(word, "bubbles"); }
		public String burned(String word) { return _add(word, "burned"); }
		public String cannonballs(String word) { return _add(word, "cannonballs"); }
		public String casts(String word) { return _add(word, "casts"); }
		public String cheers(String word) { return _add(word, "cheers"); }
		public String chimes(String word) { return _add(word, "chimes"); }
		public String chivalrously(String word) { return _add(word, "chivalrously"); }
		public String chortles(String word) { return _add(word, "chortles"); }
		public String clacking(String word) { return _add(word, "clacking"); }
		public String clangs(String word) { return _add(word, "clangs"); }
		public String click(String word) { return _add(word, "click"); }
		public String clicking(String word) { return _add(word, "clicking"); }
		public String closes(String word) { return _add(word, "closes"); }
		public String collected(String word) { return _add(word, "collected"); }
		public String completed(String word) { return _add(word, "completed"); }
		public String converts(String word) { return _add(word, "converts"); }
		public String cracks(String word) { return _add(word, "cracks"); }
		public String crashed(String word) { return _add(word, "crashed"); }
		public String crashes(String word) { return _add(word, "crashes"); }
		public String croaks(String word) { return _add(word, "croaks"); }
		public String crunches(String word) { return _add(word, "crunches"); }
		public String delights(String word) { return _add(word, "delights"); }
		public String deployed(String word) { return _add(word, "deployed"); }
		public String dies(String word) { return _add(word, "dies"); }
		public String digs(String word) { return _add(word, "digs"); }
		public String disagrees(String word) { return _add(word, "disagrees"); }
		public String down(String word) { return _add(word, "down"); }
		public String dropped(String word) { return _add(word, "dropped"); }
		public String drops(String word) { return _add(word, "drops"); }
		public String eats(String word) { return _add(word, "eats"); }
		public String empties(String word) { return _add(word, "empties"); }
		public String equips(String word) { return _add(word, "equips"); }
		public String explodes(String word) { return _add(word, "explodes"); }
		public String fell(String word) { return _add(word, "fell"); }
		public String fills(String word) { return _add(word, "fills"); }
		public String fizzes(String word) { return _add(word, "fizzes"); }
		public String flies(String word) { return _add(word, "flies"); }
		public String flops(String word) { return _add(word, "flops"); }
		public String footsteps(String word) { return _add(word, "footsteps"); }
		public String gallops(String word) { return _add(word, "gallops"); }
		public String glugs(String word) { return _add(word, "glugs"); }
		public String goos(String word) { return _add(word, "goos"); }
		public String groans(String word) { return _add(word, "groans"); }
		public String grunts(String word) { return _add(word, "grunts"); }
		public String hard(String word) { return _add(word, "hard"); }
		public String hatches(String word) { return _add(word, "hatches"); }
		public String heart(String word) { return _add(word, "heart"); }
		public String here(String word) { return _add(word, "here"); }
		public String hisses(String word) { return _add(word, "hisses"); }
		public String hit(String word) { return _add(word, "hit"); }
		public String hot(String word) { return _add(word, "hot"); }
		public String howls(String word) { return _add(word, "howls"); }
		public String hurts(String word) { return _add(word, "hurts"); }
		public String in(String word) { return _add(word, "in"); }
		public String into(String word) { return _add(word, "into"); }
		public String jingles(String word) { return _add(word, "jingles"); }
		public String juiced(String word) { return _add(word, "juiced"); }
		public String jumps(String word) { return _add(word, "jumps"); }
		public String lands(String word) { return _add(word, "lands"); }
		public String lays(String word) { return _add(word, "lays"); }
		public String lightly(String word) { return _add(word, "lightly"); }
		public String loaded(String word) { return _add(word, "loaded"); }
		public String loudly(String word) { return _add(word, "loudly"); }
		public String marching(String word) { return _add(word, "marching"); }
		public String medium(String word) { return _add(word, "medium"); }
		public String melts(String word) { return _add(word, "melts"); }
		public String moos(String word) { return _add(word, "moos"); }
		public String murmurs(String word) { return _add(word, "murmurs"); }
		public String notice(String word) { return _add(word, "notice"); }
		public String of(String word) { return _add(word, "of"); }
		public String on(String word) { return _add(word, "on"); }
		public String opens(String word) { return _add(word, "opens"); }
		public String paddles(String word) { return _add(word, "paddles"); }
		public String plays(String word) { return _add(word, "plays"); }
		public String porcelain(String word) { return _add(word, "porcelain"); }
		public String prances(String word) { return _add(word, "prances"); }
		public String prepares(String word) { return _add(word, "prepares"); }
		public String quietly(String word) { return _add(word, "quietly"); }
		public String rattles(String word) { return _add(word, "rattles"); }
		public String recovers(String word) { return _add(word, "recovers"); }
		public String released(String word) { return _add(word, "released"); }
		public String resonates(String word) { return _add(word, "resonates"); }
		public String saddled(String word) { return _add(word, "saddled"); }
		public String scents(String word) { return _add(word, "scents"); }
		public String scuttles(String word) { return _add(word, "scuttles"); }
		public String scooped(String word) { return _add(word, "scooped"); }
		public String searches(String word) { return _add(word, "searches"); }
		public String seed(String word) { return _add(word, "seed"); }
		public String seeks(String word) { return _add(word, "seeks"); }
		public String shatters(String word) { return _add(word, "shatters"); }
		public String shoots(String word) { return _add(word, "shoots"); }
		public String shrieks(String word) { return _add(word, "shrieks"); }
		public String sits(String word) { return _add(word, "sits"); }
		public String sizzles(String word) { return _add(word, "sizzles"); }
		public String sloshing(String word) { return _add(word, "sloshing"); }
		public String small(String word) { return _add(word, "small"); }
		public String sniffs(String word) { return _add(word, "sniffs"); }
		public String softly(String word) { return _add(word, "softly"); }
		public String spawn(String word) { return _add(word, "spawn"); }
		public String spell(String word) { return _add(word, "spell"); }
		public String spits(String word) { return _add(word, "spits"); }
		public String splashes(String word) { return _add(word, "splashes"); }
		public String splashing(String word) { return _add(word, "splashing"); }
		public String splats(String word) { return _add(word, "splats"); }
		public String sploshes(String word) { return _add(word, "sploshes"); }
		public String spreads(String word) { return _add(word, "spreads"); }
		public String squeezed(String word) { return _add(word, "squeezed"); }
		public String stands(String word) { return _add(word, "stands"); }
		public String starts(String word) { return _add(word, "starts"); }
		public String steps(String word) { return _add(word, "steps"); }
		public String stomps(String word) { return _add(word, "stomps"); }
		public String stops(String word) { return _add(word, "stops"); }
		public String summoning(String word) { return _add(word, "summoning"); }
		public String swims(String word) { return _add(word, "swims"); }
		public String takes(String word) { return _add(word, "takes"); }
		public String tapping(String word) { return _add(word, "tapping"); }
		public String tendrils(String word) { return _add(word, "tendrils"); }
		public String the(String word) { return _add(word, "the"); }
		public String to(String word) { return _add(word, "to"); }
		public String tosses(String word) { return _add(word, "tosses"); }
		public String trips(String word) { return _add(word, "trips"); }
		public String undignified(String word) { return _add(word, "undignified"); }
		public String up(String word) { return _add(word, "up"); }
		public String used(String word) { return _add(word, "used"); }
		public String wetly(String word) { return _add(word, "wetly"); }
		public String with() { return "with"; } public String with(String word) { return _add(word, with); }
		public String works(String word) { return _add(word, "works"); }
		public String yearns(String word) { return _add(word, "yearns"); }
		public String yeets(String word) { return _add(word, "yeets"); }

		public String Acacia(String word) { return _add(word, Acacia); }
		public String Active(String word) { return _add(word, Active); }
		public String Allium(String word) { return _add(word, Allium); }
		public String Amethyst(String word) { return _add(word, Amethyst); }
		public String Anatomy(String word) { return _add(word, Anatomy); }
		public String Anchor(String word) { return _add(word, Anchor); }
		public String Andesite(String word) { return _add(word, Andesite); }
		public String Anvil(String word) { return _add(word, Anvil); }
		public String Apple(String word) { return _add(word, Apple); }
		public String Armor() { return "Armor"; } public String Armor(String word) { return _add(word, Armor); }
		public String Arms() { return "Arms"; } public String Arms(String word) { return _add(word, Arms); }
		public String Arrow() { return "Arrow"; } public String Arrow(String word) { return _add(word, Arrow); }
		public String Ashen() { return "Ashen"; } public String Ashen(String word) { return _add(word, Ashen); }
		public String Aster() { return "Aster"; } public String Aster(String word) { return _add(word, Aster); }
		public String Attached() { return "Attached"; } public String Attached(String word) { return _add(word, Attached); }
		public String Axe() { return "Axe"; } public String Axe(String word) { return _add(word, Axe); }
		public String Axolotl() { return "Axolotl"; } public String Axolotl(String word) { return _add(word, Axolotl); }
		public String Azalea() { return "Azalea"; } public String Azalea(String word) { return _add(word, Azalea); }
		public String Azure() { return "Azure"; } public String Azure(String word) { return _add(word, Azure); }
		public String Baked() { return "Baked"; } public String Baked(String word) { return _add(word, Baked); }
		public String Bale() { return "Bale"; } public String Bale(String word) { return _add(word, Bale); }
		public String Ball() { return "Ball"; } public String Ball(String word) { return _add(word, Ball); }
		public String Bamboo() { return "Bamboo"; } public String Bamboo(String word) { return _add(word, Bamboo); }
		public String Bark(String word) { return _add(word, Bark); }
		public String Barrel() { return "Barrel"; } public String Barrel(String word) { return _add(word, Barrel); }
		public String Bars() { return "Bars"; } public String Bars(String word) { return _add(word, Bars); }
		public String Basalt() { return "Basalt"; } public String Basalt(String word) { return _add(word, Basalt); }
		public String Bat(String word) { return _add(word, Bat); }
		public String Bean() { return "Bean"; } public String Bean(String word) { return _add(word, Bean); }
		public String Beans() { return "Beans"; } public String Beans(String word) { return _add(word, Beans); }
		public String Bear(String word) { return _add(word, Bear); }
		public String Bed(String word) { return _add(word, Bed); }
		public String Beehive() { return "Beehive"; } public String Beehive(String word) { return _add(word, Beehive); }
		public String Beef() { return "Beef"; } public String Beef(String word) { return _add(word, Beef); }
		public String Beetroot() { return "Beetroot"; } public String Beetroot(String word) { return _add(word, Beetroot); }
		public String Beige() { return "Beige"; } public String Beige(String word) { return _add(word, Beige); }
		public String Berries() { return "Berries"; } public String Berries(String word) { return _add(word, Berries); }
		public String Berry() { return "Berry"; } public String Berry(String word) { return _add(word, Berry); }
		public String Birch() { return "Birch"; } public String Birch(String word) { return _add(word, Birch); }
		public String Black() { return "Black"; } public String Black(String word) { return _add(word, Black); }
		public String Blackstone() { return "Blackstone"; } public String Blackstone(String word) { return _add(word, Blackstone); }
		public String Blade() { return "Blade"; } public String Blade(String word) { return _add(word, Blade); }
		public String Blaze() { return "Blaze"; } public String Blaze(String word) { return _add(word, Blaze); }
		public String Bleeding() { return "Bleeding"; } public String Bleeding(String word) { return _add(word, Bleeding); }
		public String Blinding() { return "Blinding"; } public String Blinding(String word) { return _add(word, Blinding); }
		public String Block() { return "Block"; } public String Block(String word) { return _add(word, Block); }
		public String Blood() { return "Blood"; } public String Blood(String word) { return _add(word, Blood); }
		public String Blossom() { return "Blossom"; } public String Blossom(String word) { return _add(word, Blossom); }
		public String Blue() { return "Blue"; } public String Blue(String word) { return _add(word, Blue); }
		public String Bluet() { return "Bluet"; } public String Bluet(String word) { return _add(word, Bluet); }
		public String Boat() { return "Boat"; } public String Boat(String word) { return _add(word, Boat); }
		public Pair<String, String> _Boat(String word) { return new Pair<>(Boat(word), Boat(Chest(word))); }
		public String Bone() { return "Bone"; } public String Bone(String word) { return _add(word, Bone); }
		public String Book() { return "Book"; } public String Book(String word) { return _add(word, Book); }
		public String Bookshelf() { return "Bookshelf"; } public String Bookshelf(String word) { return _add(word, Bookshelf); }
		public String Boots() { return "Boots"; } public String Boots(String word) { return _add(word, Boots); }
		public String Bottle() { return "Bottle"; } public String Bottle(String word) { return _add(word, Bottle); }
		public String Bottled() { return "Bottled"; } public String Bottled(String word) { return _add(word, Bottled); }
		public String Bowl() { return "Bowl"; } public String Bowl(String word) { return _add(word, Bowl); }
		public String Box() { return "Box"; } public String Box(String word) { return _add(word, Box); }
		public String Bread() { return "Bread"; } public String Bread(String word) { return _add(word, Bread); }
		public String Breath() { return "Breath"; } public String Breath(String word) { return _add(word, Breath); }
		public String Brew() { return "Brew"; } public String Brew(String word) { return _add(word, Brew); }
		public String Brewer() { return "Brewer"; } public String Brewer(String word) { return _add(word, Brewer); }
		public String Brick() { return "Brick"; } public String Brick(String word) { return _add(word, Brick); }
		public String Bricks() { return "Bricks"; } public String Bricks(String word) { return _add(word, Bricks); }
		public String British() { return "British"; } public String British(String word) { return _add(word, British); }
		public String Broom() { return "Broom"; } public String Broom(String word) { return _add(word, Broom); }
		public String Bronzed() { return "Bronzed"; } public String Bronzed(String word) { return _add(word, Bronzed); }
		public String Brown() { return "Brown"; } public String Brown(String word) { return _add(word, Brown); }
		public String Brush() { return "Brush"; } public String Brush(String word) { return _add(word, Brush); }
		public String Brute(String word) { return _add(word, Brute); }
		public String Bucket() { return "Bucket"; } public String Bucket(String word) { return _add(word, Bucket); }
		public String Bud() { return "Bud"; } public String Bud(String word) { return _add(word, Bud); }
		public String Budding() { return "Budding"; } public String Budding(String word) { return _add(word, Budding); }
		public String Burgundy() { return "Burgundy"; } public String Burgundy(String word) { return _add(word, Burgundy); }
		public String Burn() { return "Burn"; } public String Burn(String word) { return _add(word, Burn); }
		public String Burnt() { return "Burnt"; } public String Burnt(String word) { return _add(word, Burnt); }
		public String Bush() { return "Bush"; } public String Bush(String word) { return _add(word, Bush); }
		public String Buttercup() { return "Buttercup"; } public String Buttercup(String word) { return _add(word, Buttercup); }
		public String Button() { return "Button"; } public String Button(String word) { return _add(word, Button); }
		public String Cabbage() { return "Cabbage"; } public String Cabbage(String word) { return _add(word, Cabbage); }
		public String Cactus() { return "Cactus"; } public String Cactus(String word) { return _add(word, Cactus); }
		public String Cake() { return "Cake"; } public String Cake(String word) { return _add(word, Cake); }
		public String Calcite() { return "Calcite"; } public String Calcite(String word) { return _add(word, Calcite); }
		public String Calibrated() { return "Calibrated"; } public String Calibrated(String word) { return _add(word, Calibrated); }
		public String Calico() { return "Calico"; } public String Calico(String word) { return _add(word, Calico); }
		public String Camel() { return "Camel"; } public String Camel(String word) { return _add(word, Camel); }
		public String Campfire() { return "Campfire"; } public String Campfire(String word) { return _add(word, Campfire); }
		public String Candle() { return "Candle"; } public String Candle(String word) { return _add(word, Candle); }
		public String Candy() { return "Candy"; } public String Candy(String word) { return _add(word, Candy); }
		public String Cane() { return "Cane"; } public String Cane(String word) { return _add(word, Cane); }
		public String Cap() { return "Cap"; } public String Cap(String word) { return _add(word, Cap); }
		public String Caramel() { return "Caramel"; } public String Caramel(String word) { return _add(word, Caramel); }
		public String Carnation(String word) { return _add(word, Carnation); }
		public String Carpet() { return "Carpet"; } public String Carpet(String word) { return _add(word, Carpet); }
		public String Carpeted() { return "Carpeted"; } public String Carpeted(String word) { return _add(word, Carpeted); }
		public String Carrot() { return "Carrot"; } public String Carrot(String word) { return _add(word, Carrot); }
		public String Carved() { return "Carved"; } public String Carved(String word) { return _add(word, Carved); }
		public String Cassia() { return "Cassia"; } public String Cassia(String word) { return _add(word, Cassia); }
		public String Cat() { return "Cat"; } public String Cat(String word) { return _add(word, Cat); }
		public String Catalyst() { return "Catalyst"; } public String Catalyst(String word) { return _add(word, Catalyst); }
		public String Cauldron() { return "Cauldron"; } public String Cauldron(String word) { return _add(word, Cauldron); }
		public String Cave() { return "Cave"; } public String Cave(String word) { return _add(word, Cave); }
		public String Chain() { return "Chain"; } public String Chain(String word) { return _add(word, Chain); }
		public String Charcoal() { return "Charcoal"; } public String Charcoal(String word) { return _add(word, Charcoal); }
		public String Charge() { return "Charge"; } public String Charge(String word) { return _add(word, Charge); }
		public String Charred() { return "Charred"; } public String Charred(String word) { return _add(word, Charred); }
		public String Cheese() { return "Cheese"; } public String Cheese(String word) { return _add(word, Cheese); }
		public String Cherry() { return "Cherry"; } public String Cherry(String word) { return _add(word, Cherry); }
		public String Chest() { return "Chest"; } public String Chest(String word) { return _add(word, Chest); }
		public String Chestplate() { return "Chestplate"; } public String Chestplate(String word) { return _add(word, Chestplate); }
		public String Chevon() { return "Chevon"; } public String Chevon(String word) { return _add(word, Chevon); }
		public String Chicken() { return "Chicken"; } public String Chicken(String word) { return _add(word, Chicken); }
		public String Chip() { return "Chip"; } public String Chip(String word) { return _add(word, Chip); }
		public String Chiseled() { return "Chiseled"; } public String Chiseled(String word) { return _add(word, Chiseled); }
		public String Chocolate(String word) { return _add(word, Chocolate); }
		public String Chorus(String word) { return _add(word, Chorus); }
		public String Chunk(String word) { return _add(word, Chunk); }
		public String Cider(String word) { return _add(word, Cider); }
		public String Cloud(String word) { return _add(word, Cloud); }
		public String Club(String word) { return _add(word, Club); }
		public String Cluster(String word) { return _add(word, Cluster); }
		public String Coal(String word) { return _add(word, Coal); }
		public String Cobbled(String word) { return _add(word, Cobbled); }
		public String Cobblestone(String word) { return _add(word, Cobblestone); }
		public String Cocoa(String word) { return _add(word, Cocoa); }
		public String Cod(String word) { return _add(word, Cod); }
		public String Coffee(String word) { return _add(word, Coffee); }
		public String Color(DyeColor color) {
			return switch (color) {
				case WHITE -> White; case ORANGE -> Orange; case MAGENTA -> Magenta; case LIGHT_BLUE -> Blue(Light);
				case YELLOW -> Yellow; case LIME -> Lime; case PINK -> Pink; case GRAY -> Gray;
				case LIGHT_GRAY -> Gray(Light); case CYAN -> Cyan; case PURPLE -> Purple; case BLUE -> Blue;
				case BROWN -> Brown; case GREEN -> Green; case RED -> Red; case BLACK -> Black;
			};
		}
		public String Color(DyeColor color, String word) { return _add(word, Color(color)); }
		public String Color(ModDyeColor color) {
			DyeColor dye = DyeColor.byId(color.getId());
			if (color.getId() == dye.getId()) return Color(dye);
			else if (color == ModDyeColor.BEIGE) return Beige;
			else if (color == ModDyeColor.BURGUNDY) return Burgundy;
			else if (color == ModDyeColor.LAVENDER) return Lavender;
			else if (color == ModDyeColor.MINT) return Mint;
			else return null;
		}
		public String Color(ModDyeColor color, String word) { return _add(word, Color(color)); }
		public String Compass() { return "Compass"; } public String Compass(String word) { return _add(word, Compass); }
		public String Components(String word) { return _add(word, Components); }
		public String Concrete() { return "Concrete"; } public String Concrete(String word) { return _add(word, Concrete); }
		public String Conduit() { return "Conduit"; }
		public String Confetti() { return "Confetti"; } public String Confetti(String word) { return _add(word, Confetti); }
		public String Cooked() { return "Cooked"; } public String Cooked(String word) { return _add(word, Cooked); }
		public String Cookie() { return "Cookie"; } public String Cookie(String word) { return _add(word, Cookie); }
		public String Cooled() { return "Cooled"; } public String Cooled(String word) { return _add(word, Cooled); }
		public String Copper() { return "Copper"; } public String Copper(String word) { return _add(word, Copper); }
		public String Coral() { return "Coral"; } public String Coral(String word) { return _add(word, Coral); }
		public String Corn() { return "Corn"; } public String Corn(String word) { return _add(word, Corn); }
		public String Cornflower() { return "Cornflower"; } public String Cornflower(String word) { return _add(word, Cornflower); }
		public String Cottage() { return "Cottage"; } public String Cottage(String word) { return _add(word, Cottage); }
		public String Cotton() { return "Cotton"; } public String Cotton(String word) { return _add(word, Cotton); }
		public String Cow() { return "Cow"; } public String Cow(String word) { return _add(word, Cow); }
		public String Cracked() { return "Cracked"; } public String Cracked(String word) { return _add(word, Cracked); }
		public String Crafting() { return "Crafting"; } public String Crafting(String word) { return _add(word, Crafting); }
		public String Cream() { return "Cream"; } public String Cream(String word) { return _add(word, Cream); }
		public String Creamy() { return "Creamy"; } public String Creamy(String word) { return _add(word, Creamy); }
		public String Creeper() { return "Creeper"; } public String Creeper(String word) { return _add(word, Creeper); }
		public String Crested() { return "Crested"; } public String Crested(String word) { return _add(word, Crested); }
		public String Crimson() { return "Crimson"; } public String Crimson(String word) { return _add(word, Crimson); }
		public String Crop() { return "Crop"; } public String Crop(String word) { return _add(word, Crop); }
		public String Crown() { return "Crown"; } public String Crown(String word) { return _add(word, Crown); }
		public String Crushing() { return "Crushing"; } public String Crushing(String word) { return _add(word, Crushing); }
		public String Crying() { return "Crying"; } public String Crying(String word) { return _add(word, Crying); }
		public String Crystal() { return "Crystal"; } public String Crystal(String word) { return _add(word, Crystal); }
		public String Cube() { return "Cube"; } public String Cube(String word) { return _add(word, Cube); }
		public String Cut() { return "Cut"; } public String Cut(String word) { return _add(word, Cut); }
		public String Cyan() { return "Cyan"; } public String Cyan(String word) { return _add(word, Cyan); }
		public String Dairy() { return "Dairy"; } public String Dairy(String word) { return _add(word, Dairy); }
		public String Daisy() { return "Daisy"; } public String Daisy(String word) { return _add(word, Daisy); }
		public String Dandelion() { return "Dandelion"; } public String Dandelion(String word) { return _add(word, Dandelion); }
		public String Danger() { return "Danger"; } public String Danger(String word) { return _add(word, Danger); }
		public String Dark() { return "Dark"; } public String Dark(String word) { return _add(word, Dark); }
		public String Death() { return "Death"; } public String Death(String word) { return _add(word, Death); }
		public String Debris(String word) { return _add(word, Debris); }
		public String Decoration() { return "Decoration"; }
		public String Deep() { return "Deep"; } public String Deep(String word) { return _add(word, Deep); }
		public String Deepslate() { return "Deepslate"; } public String Deepslate(String word) { return _add(word, Deepslate); }
		public String Dense() { return "Dense"; } public String Dense(String word) { return _add(word, Dense); }
		public String Detector(String word) { return _add(word, Detector); }
		public String Diamond() { return "Diamond"; } public String Diamond(String word) { return _add(word, Diamond); }
		public String Die() { return "Die"; } public String Die(String word) { return _add(word, Die); }
		public String Diorite() { return "Diorite"; } public String Diorite(String word) { return _add(word, Diorite); }
		public String Dirt() { return "Dirt"; } public String Dirt(String word) { return _add(word, Dirt); }
		public String Dirty() { return "Dirty"; } public String Dirty(String word) { return _add(word, Dirty); }
		public String Disc() { return "Disc"; } public String Disc(String word) { return _add(word, Disc); }
		public String Dispenser() { return "Dispenser"; } public String Dispenser(String word) { return _add(word, Dispenser); }
		public String Distilled() { return "Distilled"; } public String Distilled(String word) { return _add(word, Distilled); }
		public String Dogwood() { return "Dogwood"; } public String Dogwood(String word) { return _add(word, Dogwood); }
		public String Dolphin() { return "Dolphin"; } public String Dolphin(String word) { return _add(word, Dolphin); }
		public String Donkey() { return "Donkey"; } public String Donkey(String word) { return _add(word, Donkey); }
		public String Door() { return "Door"; } public String Door(String word) { return _add(word, Door); }
		public String Dragon() { return "Dragon"; } public String Dragon(String word) { return _add(word, Dragon); }
		public String Dried() { return "Dried"; } public String Dried(String word) { return _add(word, Dried); }
		public String Dripleaf(String word) { return _add(word, Dripleaf); }
		public String Dripstone() { return "Dripstone"; } public String Dripstone(String word) { return _add(word, Dripstone); }
		public String Dye(String word) { return _add(word, Dye); }
		public String Echo(String word) { return _add(word, Echo); }
		public String Egg(String word) { return _add(word, Egg); }
		public String Emerald(String word) { return _add(word, Emerald); }
		public String End() { return "End"; } public String End(String word) { return _add(word, End); }
		public String Ender() { return "Ender"; } public String Ender(String word) { return _add(word, Ender); }
		public String Enderman() { return "Enderman"; } public String Enderman(String word) { return _add(word, Enderman); }
		public String Endermite() { return "Endermite"; } public String Endermite(String word) { return _add(word, Endermite); }
		public String Equipment(String word) { return _add(word, Equipment); }
		public String Experience() { return "Experience"; } public String Experience(String word) { return _add(word, Experience); }
		public String Explorer() { return "Explorer"; } public String Explorer(String word) { return _add(word, Explorer); }
		public String Exposed() { return "Exposed"; } public String Exposed(String word) { return _add(word, Exposed); }
		public String Eye() { return "Eye"; } public String Eye(String word) { return _add(word, Eye); }
		public String Faceting() { return "Faceting"; } public String Faceting(String word) { return _add(word, Faceting); }
		public String Fan(String word) { return _add(word, Fan); }
		public String Fancy() { return "Fancy"; } public String Fancy(String word) { return _add(word, Fancy); }
		public String Fatigue(String word) { return _add(word, Fatigue); }
		public String Feather() { return "Feather"; } public String Feather(String word) { return _add(word, Feather); }
		public String Feathers() { return "Feathers"; } public String Feathers(String word) { return _add(word, Feathers); }
		public String Fence() { return "Fence"; } public String Fence(String word) { return _add(word, Fence); }
		public String Fern(String word) { return _add(word, Fern); }
		public String Fire(String word) { return _add(word, Fire); }
		public String Fireball(String word) { return _add(word, Fireball); }
		public String Firing() { return "Firing"; } public String Firing(String word) { return _add(word, Firing); }
		public String Fish() { return "Fish"; } public String Fish(String word) { return _add(word, Fish); }
		public String Flecked() { return "Flecked"; } public String Flecked(String word) { return _add(word, Flecked); }
		public String Fleece() { return "Fleece"; } public String Fleece(String word) { return _add(word, Fleece); }
		public String Flint() { return "Flint"; } public String Flint(String word) { return _add(word, Flint); }
		public Pair<String, String> _FlowerParts(String word) { return new Pair<>(Seeds(word), Petals(word)); }
		public String Flower() { return "Flower"; } public String Flower(String word) { return _add(word, Flower); }
		public String Flowering() { return "Flowering"; } public String Flowering(String word) { return _add(word, Flowering); }
		public String Flowers() { return "Flowers"; } public String Flowers(String word) { return _add(word, Flowers); }
		public String Flowing() { return "Flowing"; } public String Flowing(String word) { return _add(word, Flowing); }
		public String Fluid() { return "Fluid"; } public String Fluid(String word) { return _add(word, Fluid); }
		public String Footed() { return "Footed"; } public String Footed(String word) { return _add(word, Footed); }
		public String Fox(String word) { return _add(word, Fox); }
		public String Fragment() { return "Fragment"; } public String Fragment(String word) { return _add(word, Fragment); }
		public String Frame(String word) { return _add(word, Frame); }
		public String Frog(String word) { return _add(word, Frog); }
		public String Froglight() { return "Froglight"; } public String Froglight(String word) { return _add(word, Froglight); }
		public String Frogspawn() { return "Frogspawn"; } public String Frogspawn(String word) { return _add(word, Frogspawn); }
		public String Fruit(String word) { return _add(word, Fruit); }
		public String Fry(String word) { return _add(word, Fry); }
		public String Fungus(String word) { return _add(word, Fungus); }
		public String Furnace(String word) { return _add(word, Furnace); }
		public String Fuse(String word) { return _add(word, Fuse); }
		public String Fuzzy() { return "Fuzzy"; } public String Fuzzy(String word) { return _add(word, Fuzzy); }
		public String Gate() { return "Gate"; } public String Gate(String word) { return _add(word, Gate); }
		public String Gilded() { return "Gilded"; } public String Gilded(String word) { return _add(word, Gilded); }
		public String Glass() { return "Glass"; } public String Glass(String word) { return _add(word, Glass); }
		public String Glazed() { return "Glazed"; } public String Glazed(String word) { return _add(word, Glazed); }
		public String Glow(String word) { return _add(word, Glow); }
		public String Goat() { return "Goat"; } public String Goat(String word) { return _add(word, Goat); }
		public String Goggles() { return "Goggles"; } public String Goggles(String word) { return _add(word, Goggles); }
		public String Gold() { return "Gold"; } public String Gold(String word) { return _add(word, Gold); }
		public String Golden() { return "Golden"; } public String Golden(String word) { return _add(word, Golden); }
		public String Golem() { return "Golem"; } public String Golem(String word) { return _add(word, Golem); }
		public String Granite() { return "Granite"; } public String Granite(String word) { return _add(word, Granite); }
		public String Grape() { return "Grape"; } public String Grape(String word) { return _add(word, Grape); }
		public String Grapes() { return "Grapes"; } public String Grapes(String word) { return _add(word, Grapes); }
		public String Grappling() { return "Grappling"; } public String Grappling(String word) { return _add(word, Grappling); }
		public String Grass() { return "Grass"; } public String Grass(String word) { return _add(word, Grass); }
		public String Grate(String word) { return _add(word, Grate); }
		public String Gravel() { return "Gravel"; } public String Gravel(String word) { return _add(word, Gravel); }
		public String Gravity() { return "Gravity"; } public String Gravity(String word) { return _add(word, Gravity); }
		public String Gray(String word) { return _add(word, Gray); }
		public String Green(String word) { return _add(word, Green); }
		public String Grey(String word) { return _add(word, Grey); }
		public String Grilled(String word) { return _add(word, Grilled); }
		public String Grove(String word) { return _add(word, Grove); }
		public String Guardian(String word) { return _add(word, Guardian); }
		public String Hammer(String word) { return _add(word, Hammer); }
		public String Hanging(String word) { return _add(word, Hanging); }
		public Pair<String, String> _HangingSign(String word) { return _apply(x -> Sign(Hanging(x)), _Wall(word)); }
		public Pair<String, String> _HangingSign(String word, String withChain) { return _apply(x -> _add(Sign(Hanging(x)), withChain), _Wall(word)); }
		public String Hay() { return "Hay"; } public String Hay(String word) { return _add(word, Hay); }
		public String Head() { return "Head"; } public String Head(String word) { return _add(word, Head); }
		public Pair<String, String> _Head(String word) { return _apply(this::Head, _Wall(word)); }
		public String Heart(String word) { return _add(word, Heart); }
		public String Heavy(String word) { return _add(word, Heavy); }
		public String Hedgehog(String word) { return _add(word, Hedgehog); }
		public String Helmet(String word) { return _add(word, Helmet); }
		public String Hoe(String word) { return _add(word, Hoe); }
		public String Horn() { return "Horn"; } public String Horn(String word) { return _add(word, Horn); }
		public String Horse() { return "Horse"; } public String Horse(String word) { return _add(word, Horse); }
		public String Hotdog() { return "Hotdog"; } public String Hotdog(String word) { return _add(word, Hotdog); }
		public String Howl() { return "Howl"; } public String Howl(String word) { return _add(word, Howl); }
		public String Hybrid(String word) { return _add(word, Hybrid); }
		public String Hydrangea() { return "Hydrangea"; } public String Hydrangea(String word) { return _add(word, Hydrangea); }
		public String Hyphae() { return "Hyphae"; } public String Hyphae(String word) { return _add(word, Hyphae); }
		public String Ice() { return "Ice"; } public String Ice(String word) { return _add(word, Ice); }
		public String Indigo() { return "Indigo"; } public String Indigo(String word) { return _add(word, Indigo); }
		public String Inducing(String word) { return _add(word, Inducing); }
		public String Ingredient() { return "Ingredient"; } public String Ingredient(String word) { return _add(word, Ingredient); }
		public String Ingot() { return "Ingot"; } public String Ingot(String word) { return _add(word, Ingot); }
		public String Inky() { return "Inky"; } public String Inky(String word) { return _add(word, Inky); }
		public String Instantly() { return "Instantly"; } public String Instantly(String word) { return _add(word, Instantly); }
		public String Iron() { return "Iron"; } public String Iron(String word) { return _add(word, Iron); }
		public String JackOLantern() { return "Jack O' Lantern"; } public String JackOLantern(String word) { return _add(word, JackOLantern); }
		public String Javelin() { return "Javelin"; } public String Javelin(String word) { return _add(word, Javelin); }
		public String Jellie() { return "Jellie"; } public String Jellie(String word) { return _add(word, Jellie); }
		public String Jolly() { return "Jolly"; } public String Jolly(String word) { return _add(word, Jolly); }
		public String Juice() { return "Juice"; } public String Juice(String word) { return _add(word, Juice); }
		public String Juicer() { return "Juicer"; } public String Juicer(String word) { return _add(word, Juicer); }
		public String Jungle() { return "Jungle"; } public String Jungle(String word) { return _add(word, Jungle); }
		public String Keg() { return "Keg"; } public String Keg(String word) { return _add(word, Keg); }
		public String Kelp() { return "Kelp"; } public String Kelp(String word) { return _add(word, Kelp); }
		public String Key() { return "Key"; } public String Key(String word) { return _add(word, Key); }
		public String Knife() { return "Knife"; } public String Knife(String word) { return _add(word, Knife); }
		public String Ladder() { return "Ladder"; } public String Ladder(String word) { return _add(word, Ladder); }
		public String Ladle() { return "Ladle"; } public String Ladle(String word) { return _add(word, Ladle); }
		public String Lamp(String word) { return _add(word, Lamp); }
		public String Lantern() { return "Lantern"; } public String Lantern(String word) { return _add(word, Lantern); }
		public String Lapidary() { return "Lapidary"; } public String Lapidary(String word) { return _add(word, Lapidary); }
		public String Lapis() { return "Lapis"; } public String Lapis(String word) { return _add(word, Lapis); }
		public String Large() { return "Large"; } public String Large(String word) { return _add(word, Large); }
		public String Lava() { return "Lava"; } public String Lava(String word) { return _add(word, Lava); }
		public String Lavender() { return "Lavender"; } public String Lavender(String word) { return _add(word, Lavender); }
		public String Leather() { return "Leather"; } public String Leather(String word) { return _add(word, Leather); }
		public String Leaves() { return "Leaves"; } public String Leaves(String word) { return _add(word, Leaves); }
		public String Lectern() { return "Lectern"; } public String Lectern(String word) { return _add(word, Lectern); }
		public String Leggings() { return "Leggings"; } public String Leggings(String word) { return _add(word, Leggings); }
		public String Lichen() { return "Lichen"; } public String Lichen(String word) { return _add(word, Lichen); }
		public String Light() { return "Light"; } public String Light(String word) { return _add(word, Light); }
		public String Lightning() { return "Lightning"; } public String Lightning(String word) { return _add(word, Lightning); }
		public String Lilac() { return "Lilac"; } public String Lilac(String word) { return _add(word, Lilac); }
		public String Lily() { return "Lily"; } public String Lily(String word) { return _add(word, Lily); }
		public String Llama() { return "Llama"; } public String Llama(String word) { return _add(word, Llama); }
		public String Locket(String word) { return _add(word, Locket); }
		public String Log() { return "Log"; } public String Log(String word) { return _add(word, Log); }
		public String Lollipop() { return "Lollipop"; } public String Lollipop(String word) { return _add(word, Lollipop); }
		public String Long() { return "Long"; } public String Long(String word) { return _add(word, Long); }
		public String Lucy() { return "Lucy"; } public String Lucy(String word) { return _add(word, Lucy); }
		public String Magenta() { return "Magenta"; } public String Magenta(String word) { return _add(word, Magenta); }
		public String Magma() { return "Magma"; } public String Magma(String word) { return _add(word, Magma); }
		public String Mangrove() { return "Mangrove"; } public String Mangrove(String word) { return _add(word, Mangrove); }
		public String Marigold() { return "Marigold"; } public String Marigold(String word) { return _add(word, Marigold); }
		public String Martini(String word) { return _add(word, Martini); }
		public String Marshmallow() { return "Marshmallow"; } public String Marshmallow(String word) { return _add(word, Marshmallow); }
		public String Mask(String word) { return _add(word, Mask); }
		public String Medium() { return "Medium"; } public String Medium(String word) { return _add(word, Medium); }
		public String Melon() { return "Melon"; } public String Melon(String word) { return _add(word, Melon); }
		public String Metal() { return "Metal"; } public String Metal(String word) { return _add(word, Metal); }
		public String Midnight() { return "Midnight"; } public String Midnight(String word) { return _add(word, Midnight); }
		public String Milk() { return "Milk"; } public String Milk(String word) { return _add(word, Milk); }
		public String Milkshake() { return "Milkshake"; } public String Milkshake(String word) { return _add(word, Milkshake); }
		public String Minecart() { return "Minecart"; } public String Minecart(String word) { return _add(word, Minecart); }
		public String Miner() { return "Miner"; } public String Miner(String word) { return _add(word, Miner); }
		public String Mint() { return "Mint"; } public String Mint(String word) { return _add(word, Mint); }
		public String Mocha() { return "Mocha"; } public String Mocha(String word) { return _add(word, Mocha); }
		public String Moldy() { return "Moldy"; } public String Moldy(String word) { return _add(word, Moldy); }
		public String Mooblossom(String word) { return _add(word, Mooblossom); }
		public String Mooshroom(String word) { return _add(word, Mooshroom); }
		public String Mosaic() { return "Mosaic"; } public String Mosaic(String word) { return _add(word, Mosaic); }
		public String Moss() { return "Moss"; } public String Moss(String word) { return _add(word, Moss); }
		public String Mossy() { return "Mossy"; } public String Mossy(String word) { return _add(word, Mossy); }
		public String Mottled() { return "Mottled"; } public String Mottled(String word) { return _add(word, Mottled); }
		public String Mountaineer() { return "Mountaineer"; } public String Mountaineer(String word) { return _add(word, Mountaineer); }
		public String Mourner() { return "Mourner"; } public String Mourner(String word) { return _add(word, Mourner); }
		public String Mud() { return "Mud"; } public String Mud(String word) { return _add(word, Mud); }
		public String Muddy() { return "Muddy"; } public String Muddy(String word) { return _add(word, Muddy); }
		public String Mule() { return "Mule"; } public String Mule(String word) { return _add(word, Mule); }
		public String Mushroom() { return "Mushroom"; } public String Mushroom(String word) { return _add(word, Mushroom); }
		public String Music() { return "Music"; } public String Music(String word) { return _add(word, Music); }
		public String Mutton() { return "Mutton"; } public String Mutton(String word) { return _add(word, Mutton); }
		public String Mycelium() { return "Mycelium"; } public String Mycelium(String word) { return _add(word, Mycelium); }
		public String Nephal() { return "Nephal"; } public String Nephal(String word) { return _add(word, Nephal); }
		public String Nest(String word) { return _add(word, Nest); }
		public String Nether() { return "Nether"; } public String Nether(String word) { return _add(word, Nether); }
		public String Netherite() { return "Netherite"; } public String Netherite(String word) { return _add(word, Netherite); }
		public String Netherrack() { return "Netherrack"; } public String Netherrack(String word) { return _add(word, Netherrack); }
		public String Nethershroom() { return "Nethershroom"; } public String Nethershroom(String word) { return _add(word, Nethershroom); }
		public String Nog() { return "Nog"; } public String Nog(String word) { return _add(word, Nog); }
		public String Nose() { return "Nose"; } public String Nose(String word) { return _add(word, Nose); }
		public String Nugget() { return "Nugget"; } public String Nugget(String word) { return _add(word, Nugget); }
		public String Nylium() { return "Nylium"; } public String Nylium(String word) { return _add(word, Nylium); }
		public String Oak() { return "Oak"; } public String Oak(String word) { return _add(word, Oak); }
		public String Oakwood() { return "Oakwood"; } public String Oakwood(String word) { return _add(word, Oakwood); }
		public String Obsidian() { return "Obsidian"; } public String Obsidian(String word) { return _add(word, Obsidian); }
		public String Ocelot() { return "Ocelot"; } public String Ocelot(String word) { return _add(word, Ocelot); }
		public String Ochre() { return "Ochre"; } public String Ochre(String word) { return _add(word, Ochre); }
		public String Onion() { return "Onion"; } public String Onion(String word) { return _add(word, Onion); }
		public String Only(String word) { return _add(word, Only); }
		public String Orange() { return "Orange"; } public String Orange(String word) { return _add(word, Orange); }
		public String Ore() { return "Ore"; } public String Ore(String word) { return _add(word, Ore); }
		public String Orchid() { return "Orchid"; } public String Orchid(String word) { return _add(word, Orchid); }
		public String Oxeye() { return "Oxeye"; } public String Oxeye(String word) { return _add(word, Oxeye); }
		public String Oxidized() { return "Oxidized"; } public String Oxidized(String word) { return _add(word, Oxidized); }
		public String Packed() { return "Packed"; } public String Packed(String word) { return _add(word, Packed); }
		public String Pad(String word) { return _add(word, Pad); }
		public String Paddling(String word) { return _add(word, Paddling); }
		public String Paeonia() { return "Paeonia"; } public String Paeonia(String word) { return _add(word, Paeonia); }
		public String Pale() { return "Pale"; } public String Pale(String word) { return _add(word, Pale); }
		public String Panda() { return "Panda"; } public String Panda(String word) { return _add(word, Panda); }
		public String Pane() { return "Pane"; } public String Pane(String word) { return _add(word, Pane); }
		public String Parrot() { return "Parrot"; } public String Parrot(String word) { return _add(word, Parrot); }
		public String Patched() { return "Patched"; } public String Patched(String word) { return _add(word, Patched); }
		public String Path(String word) { return _add(word, Path); }
		public String Pearlescent() { return "Pearlescent"; } public String Pearlescent(String word) { return _add(word, Pearlescent); }
		public String Peony() { return "Peony"; } public String Peony(String word) { return _add(word, Peony); }
		public String Petal() { return "Petal"; } public String Petal(String word) { return _add(word, Petal); }
		public String Petals() { return "Petals"; } public String Petals(String word) { return _add(word, Petals); }
		public String Phantom() { return "Phantom"; } public String Phantom(String word) { return _add(word, Phantom); }
		public String Phantoms(String word) { return _add(word, Phantoms); }
		public String Pickaxe(String word) { return _add(word, Pickaxe); }
		public String Pickle(String word) { return _add(word, Pickle); }
		public String Pie(String word) { return _add(word, Pie); }
		public String Pig(String word) { return _add(word, Pig); }
		public String Piglin(String word) { return _add(word, Piglin); }
		public String Pillar() { return "Pillar"; } public String Pillar(String word) { return _add(word, Pillar); }
		public String Pink() { return "Pink"; } public String Pink(String word) { return _add(word, Pink); }
		public String Pinto() { return "Pinto"; } public String Pinto(String word) { return _add(word, Pinto); }
		public String Piranha() { return "Piranha"; } public String Piranha(String word) { return _add(word, Piranha); }
		public String Pitcher() { return "Pitcher"; } public String Pitcher(String word) { return _add(word, Pitcher); }
		public String Planks() { return "Planks"; } public String Planks(String word) { return _add(word, Planks); }
		public String Plant() { return "Plant"; } public String Plant(String word) { return _add(word, Plant); }
		public String Plate() { return "Plate"; } public String Plate(String word) { return _add(word, Plate); }
		public String Plenty() { return "Plenty"; } public String Plenty(String word) { return _add(word, Plenty); }
		public String Plushie() { return "Plushie"; } public String Plushie(String word) { return _add(word, Plushie); }
		public String Pod() { return "Pod"; } public String Pod(String word) { return _add(word, Pod); }
		public String Poison() { return "Poison"; } public String Poison(String word) { return _add(word, Poison); }
		public String Poisonous() { return "Poisonous"; } public String Poisonous(String word) { return _add(word, Poisonous); }
		public String Polar() { return "Polar"; } public String Polar(String word) { return _add(word, Polar); }
		public String Polished() { return "Polished"; } public String Polished(String word) { return _add(word, Polished); }
		public String Poppy() { return "Poppy"; } public String Poppy(String word) { return _add(word, Poppy); }
		public String Porkchop() { return "Porkchop"; } public String Porkchop(String word) { return _add(word, Porkchop); }
		public String Portal() { return "Portal"; } public String Portal(String word) { return _add(word, Portal); }
		public String Post() { return "Post"; } public String Post(String word) { return _add(word, Post); }
		public String Potato() { return "Potato"; } public String Potato(String word) { return _add(word, Potato); }
		public String Potion() { return "Potion"; } public String Potion(String word) { return _add(word, Potion); }
		public Pair<String, String> _Potted(String word) { return new Pair<>(word, _add(Potted, word)); }
		public String Pottery() { return "Pottery"; } public String Pottery(String word) { return _add(word, Pottery); }
		public String Powder() { return "Powder"; } public String Powder(String word) { return _add(word, Powder); }
		public String Pressure() { return "Pressure"; } public String Pressure(String word) { return _add(word, Pressure); }
		public String Prismarine() { return "Prismarine"; } public String Prismarine(String word) { return _add(word, Prismarine); }
		public String Prize() { return "Prize"; } public String Prize(String word) { return _add(word, Prize); }
		public String Propagule() { return "Propagule"; } public String Propagule(String word) { return _add(word, Propagule); }
		public String Pufferfish() { return "Pufferfish"; } public String Pufferfish(String word) { return _add(word, Pufferfish); }
		public String Pumice() { return "Pumice"; } public String Pumice(String word) { return _add(word, Pumice); }
		public String Pumpkin() { return "Pumpkin"; } public String Pumpkin(String word) { return _add(word, Pumpkin); }
		public String Purple() { return "Purple"; } public String Purple(String word) { return _add(word, Purple); }
		public String Purpur() { return "Purpur"; } public String Purpur(String word) { return _add(word, Purpur); }
		public String Put() { return "Put"; } public String Put(String word) { return _add(word, Put); }
		public String Quartz() { return "Quartz"; } public String Quartz(String word) { return _add(word, Quartz); }
		public String Rabbit() { return "Rabbit"; } public String Rabbit(String word) { return _add(word, Rabbit); }
		public String Raccoon() { return "Raccoon"; } public String Raccoon(String word) { return _add(word, Raccoon); }
		public String Raft(String word) { return _add(word, Raft); }
		public Pair<String, String> _Raft(String word) { return new Pair<>(Raft(word), Chest(with(Raft(word)))); }
		public String Ragdoll() { return "Ragdoll"; } public String Ragdoll(String word) { return _add(word, Ragdoll); }
		public String Rainbow() { return "Rainbow"; } public String Rainbow(String word) { return _add(word, Rainbow); }
		public String Ramen() { return "Ramen"; } public String Ramen(String word) { return _add(word, Ramen); }
		public String Ravager() { return "Ravager"; } public String Ravager(String word) { return _add(word, Ravager); }
		public String Raw() { return "Raw"; } public String Raw(String word) { return _add(word, Raw); }
		public String Recovery() { return "Recovery"; } public String Recovery(String word) { return _add(word, Recovery); }
		public String Red() { return "Red"; } public String Red(String word) { return _add(word, Red); }
		public String Redstone(String word) { return _add(word, Redstone); }
		public String Reinforced() { return "Reinforced"; } public String Reinforced(String word) { return _add(word, Reinforced); }
		public String Repulsion() { return "Repulsion"; } public String Repulsion(String word) { return _add(word, Repulsion); }
		public String Resistance(String word) { return _add(word, Resistance); }
		public String Roast() { return "Roast"; } public String Roast(String word) { return _add(word, Roast); }
		public String Rock() { return "Rock"; } public String Rock(String word) { return _add(word, Rock); }
		public String Rocky() { return "Rocky"; } public String Rocky(String word) { return _add(word, Rocky); }
		public String Rod() { return "Rod"; } public String Rod(String word) { return _add(word, Rod); }
		public String Roll() { return "Roll"; } public String Roll(String word) { return _add(word, Roll); }
		public String Roots() { return "Roots"; } public String Roots(String word) { return _add(word, Roots); }
		public String Rose() { return "Rose"; } public String Rose(String word) { return _add(word, Rose); }
		public String Rot(String word) { return _add(word, Rot); }
		public String Rotten() { return "Rotten"; } public String Rotten(String word) { return _add(word, Rotten); }
		public String Row() { return "Row"; } public String Row(String word) { return _add(word, Row); }
		public String Rowing(String word) { return _add(word, Rowing); }
		public String Royalty(String word) { return _add(word, Royalty); }
		public String Ruby() { return "Ruby"; } public String Ruby(String word) { return _add(word, Ruby); }
		public String Saddled() { return "Saddled"; } public String Saddled(String word) { return _add(word, Saddled); }
		public String Salmon() { return "Salmon"; } public String Salmon(String word) { return _add(word, Salmon); }
		public String Salve() { return "Salve"; } public String Salve(String word) { return _add(word, Salve); }
		public String Sand() { return "Sand"; } public String Sand(String word) { return _add(word, Sand); }
		public String Sandstone() { return "Sandstone"; } public String Sandstone(String word) { return _add(word, Sandstone); }
		public String Sandy() { return "Sandy"; } public String Sandy(String word) { return _add(word, Sandy); }
		public String Sap() { return "Sap"; } public String Sap(String word) { return _add(word, Sap); }
		public String Sapling() { return "Sapling"; } public String Sapling(String word) { return _add(word, Sapling); }
		public String Sapphire() { return "Sapphire"; } public String Sapphire(String word) { return _add(word, Sapphire); }
		public String Sculk() { return "Sculk"; } public String Sculk(String word) { return _add(word, Sculk); }
		public String Seed() { return "Seed"; } public String Seed(String word) { return _add(word, Seed); }
		public String Seeds() { return "Seeds"; } public String Seeds(String word) { return _add(word, Seeds); }
		public String Sensor() { return "Sensor"; } public String Sensor(String word) { return _add(word, Sensor); }
		public String Shale() { return "Shale"; } public String Shale(String word) { return _add(word, Shale); }
		public String Shard() { return "Shard"; } public String Shard(String word) { return _add(word, Shard); }
		public String Sharp() { return "Sharp"; }
		public String Sheaf() { return "Sheaf"; } public String Sheaf(String word) { return _add(word, Sheaf); }
		public String Sheared() { return "Sheared"; } public String Sheared(String word) { return _add(word, Sheared); }
		public String Shears() { return "Shears"; } public String Shears(String word) { return _add(word, Shears); }
		public String Sheep() { return "Sheep"; } public String Sheep(String word) { return _add(word, Sheep); }
		public String Shelter() { return "Shelter"; } public String Shelter(String word) { return _add(word, Shelter); }
		public String Sherd() { return "Sherd"; } public String Sherd(String word) { return _add(word, Sherd); }
		public String Shorthair() { return "Shorthair"; } public String Shorthair(String word) { return _add(word, Shorthair); }
		public String Shot(String word) { return _add(word, Shot); }
		public String Shovel() { return "Shovel"; } public String Shovel(String word) { return _add(word, Shovel); }
		public String Shrieker() { return "Shrieker"; } public String Shrieker(String word) { return _add(word, Shrieker); }
		public String Shroomlight() { return "Shroomlight"; } public String Shroomlight(String word) { return _add(word, Shroomlight); }
		public String Shulker() { return "Shulker"; } public String Shulker(String word) { return _add(word, Shulker); }
		public String Siamese() { return "Siamese"; } public String Siamese(String word) { return _add(word, Siamese); }
		public String Sign(String word) { return _add(word, Sign); }
		public Pair<Pair<String, String>, Pair<String, String>> _Sign(String word) { return new Pair<>(_apply(this::Sign, _Wall(word)), _HangingSign(word)); }
		public String Silverfish() { return "Silverfish"; } public String Silverfish(String word) { return _add(word, Silverfish); }
		public String Siphon(String word) { return _add(word, Siphon); }
		public String Skeleton(String word) { return _add(word, Skeleton); }
		public String Skewbald() { return "Skewbald"; } public String Skewbald(String word) { return _add(word, Skewbald); }
		public String Skull() { return "Skull"; } public String Skull(String word) { return _add(word, Skull); }
		public String Slab() { return "Slab"; } public String Slab(String word) { return _add(word, Slab); }
		public String Slime() { return "Slime"; } public String Slime(String word) { return _add(word, Slime); }
		public String Small() { return "Small"; } public String Small(String word) { return _add(word, Small); }
		public String Smooth() { return "Smooth"; } public String Smooth(String word) { return _add(word, Smooth); }
		public String Smoothie() { return "Smoothie"; } public String Smoothie(String word) { return _add(word, Smoothie); }
		public String Smithing() { return "Smithing"; } public String Smithing(String word) { return _add(word, Smithing); }
		public String Sneak(String word) { return _add(word, Sneak); }
		public String Sniffer() { return "Sniffer"; } public String Sniffer(String word) { return _add(word, Sniffer); }
		public String Snort() { return "Snort"; } public String Snort(String word) { return _add(word, Snort); }
		public String Snow() { return "Snow"; } public String Snow(String word) { return _add(word, Snow); }
		public String Snowball(String word) { return _add(word, Snowball); }
		public String Soil(String word) { return _add(word, Soil); }
		public String Sooty() { return "Sooty"; } public String Sooty(String word) { return _add(word, Sooty); }
		public String Soul() { return "Soul"; } public String Soul(String word) { return _add(word, Soul); }
		public String Soup() { return "Soup"; } public String Soup(String word) { return _add(word, Soup); }
		public String Spade(String word) { return _add(word, Spade); }
		public String Spawn() { return "Spawn"; } public String Spawn(String word) { return _add(word, Spawn); }
		public String Spider() { return "Spider"; } public String Spider(String word) { return _add(word, Spider); }
		public String Spoiled() { return "Spoiled"; } public String Spoiled(String word) { return _add(word, Spoiled); }
		public String Sponge(String word) { return _add(word, Sponge); }
		public String Spore() { return "Spore"; } public String Spore(String word) { return _add(word, Spore); }
		public String Spotted() { return "Spotted"; } public String Spotted(String word) { return _add(word, Spotted); }
		public String Sprouts(String word) { return _add(word, Sprouts); }
		public String Spruce() { return "Spruce"; } public String Spruce(String word) { return _add(word, Spruce); }
		public String Squid() { return "Squid"; } public String Squid(String word) { return _add(word, Squid); }
		public String Stained() { return "Stained"; } public String Stained(String word) { return _add(word, Stained); }
		public String Stairs() { return "Stairs"; } public String Stairs(String word) { return _add(word, Stairs); }
		public String Stand(String word) { return _add(word, Stand); }
		public String Statue() { return "Statue"; } public String Statue(String word) { return _add(word, Statue); }
		public String Stem() { return "Stem"; } public String Stem(String word) { return _add(word, Stem); }
		public String Stick() { return "Stick"; } public String Stick(String word) { return _add(word, Stick); }
		public String Sticky(String word) { return _add(word, Sticky); }
		public String Still() { return "Still"; } public String Still(String word) { return _add(word, Still); }
		public String Stir() { return "Stir"; } public String Stir(String word) { return _add(word, Stir); }
		public String Stone() { return "Stone"; } public String Stone(String word) { return _add(word, Stone); }
		public String Stormy() { return "Stormy"; } public String Stormy(String word) { return _add(word, Stormy); }
		public String Strawberry() { return "Strawberry"; } public String Strawberry(String word) { return _add(word, Strawberry); }
		public String Strider() { return "Strider"; } public String Strider(String word) { return _add(word, Strider); }
		public String Stripped() { return "Stripped"; } public String Stripped(String word) { return _add(word, Stripped); }
		public String Studded() { return "Studded"; } public String Studded(String word) { return _add(word, Studded); }
		public String Sugar() { return "Sugar"; } public String Sugar(String word) { return _add(word, Sugar); }
		public String Summoning() { return "Summoning"; } public String Summoning(String word) { return _add(word, Summoning); }
		public String Sunflower() { return "Sunflower"; } public String Sunflower(String word) { return _add(word, Sunflower); }
		public String Sunken(String word) { return _add(word, Sunken); }
		public String Sunset() { return "Sunset"; } public String Sunset(String word) { return _add(word, Sunset); }
		public String Suspicious() { return "Suspicious"; } public String Suspicious(String word) { return _add(word, Suspicious); }
		public String Swamp() { return "Swamp"; } public String Swamp(String word) { return _add(word, Swamp); }
		public String Sweet() { return "Sweet"; } public String Sweet(String word) { return _add(word, Sweet); }
		public String Sword() { return "Sword"; } public String Sword(String word) { return _add(word, Sword); }
		public String Syringe() { return "Syringe"; } public String Syringe(String word) { return _add(word, Syringe); }
		public String Syrup() { return "Syrup"; } public String Syrup(String word) { return _add(word, Syrup); }
		public String Tabby() { return "Tabby"; } public String Tabby(String word) { return _add(word, Tabby); }
		public String Table() { return "Table"; } public String Table(String word) { return _add(word, Table); }
		public String Tadpole() { return "Tadpole"; } public String Tadpole(String word) { return _add(word, Tadpole); }
		public String Tall() { return "Tall"; } public String Tall(String word) { return _add(word, Tall); }
		public String Target() { return "Target"; } public String Target(String word) { return _add(word, Target); }
		public String Temperate() { return "Temperate"; } public String Temperate(String word) { return _add(word, Temperate); }
		public String Template() { return "Template"; } public String Template(String word) { return _add(word, Template); }
		public String Terracotta() { return "Terracotta"; } public String Terracotta(String word) { return _add(word, Terracotta); }
		public String Throwable() { return "Throwable"; } public String Throwable(String word) { return _add(word, Throwable); }
		public String Thundering() { return "Thundering"; } public String Thundering(String word) { return _add(word, Thundering); }
		public String Tiki() { return "Tiki"; } public String Tiki(String word) { return _add(word, Tiki); }
		public String Tile() { return "Tile"; } public String Tile(String word) { return _add(word, Tile); }
		public String Tiles() { return "Tiles"; } public String Tiles(String word) { return _add(word, Tiles); }
		public String Tinted() { return "Tinted"; } public String Tinted(String word) { return _add(word, Tinted); }
		public String TNT() { return "TNT"; } public String TNT(String word) { return _add(word, TNT); }
		public String Tomato() { return "Tomato"; } public String Tomato(String word) { return _add(word, Tomato); }
		public String Torchflower() { return "Torchflower"; } public String Torchflower(String word) { return _add(word, Torchflower); }
		public String Torch() { return "Torch"; } public String Torch(String word) { return _add(word, Torch); }
		public Pair<String, String> _Torch(String word) { return _apply(this::Torch, _Wall(word)); }
		public String Trader() { return "Trader"; } public String Trader(String word) { return _add(word, Trader); }
		public String Trapdoor() { return "Trapdoor"; } public String Trapdoor(String word) { return _add(word, Trapdoor); }
		public String Trial() { return "Trial"; } public String Trial(String word) { return _add(word, Trial); }
		public String Trident() { return "Trident"; } public String Trident(String word) { return _add(word, Trident); }
		public String Trim(String word) { return _add(word, Trim); }
		public String Trimming() { return "Trimming"; } public String Trimming(String word) { return _add(word, Trimming); }
		public String Tropical() { return "Tropical"; } public String Tropical(String word) { return _add(word, Tropical); }
		public String Tuff() { return "Tuff"; } public String Tuff(String word) { return _add(word, Tuff); }
		public String Tulip() { return "Tulip"; } public String Tulip(String word) { return _add(word, Tulip); }
		public String Turf() { return "Turf"; } public String Turf(String word) { return _add(word, Turf); }
		public String Turtle() { return "Turtle"; } public String Turtle(String word) { return _add(word, Turtle); }
		public String Umbra() { return "Umbra"; } public String Umbra(String word) { return _add(word, Umbra); }
		public String Underwater() { return "Underwater"; } public String Underwater(String word) { return _add(word, Underwater); }
		public String Unlit() { return "Unlit"; } public String Unlit(String word) { return _add(word, Unlit); }
		public String Unreadable() { return "Unreadable"; } public String Unreadable(String word) { return _add(word, Unreadable); }
		public String Up() { return "Up"; } public String Up(String word) { return _add(word, Up); }
		public String Upgrade() { return "Upgrade"; } public String Upgrade(String word) { return _add(word, Upgrade); }
		public String Valley() { return "Valley"; } public String Valley(String word) { return _add(word, Valley); }
		public String Vampire() { return "Vampire"; } public String Vampire(String word) { return _add(word, Vampire); }
		public String Vanilla() { return "Vanilla"; } public String Vanilla(String word) { return _add(word, Vanilla); }
		public String Vein() { return "Vein"; } public String Vein(String word) { return _add(word, Vein); }
		public String Venom() { return "Venom"; } public String Venom(String word) { return _add(word, Venom); }
		public String Verdant() { return "Verdant"; } public String Verdant(String word) { return _add(word, Verdant); }
		public String Vex() { return "Vex"; } public String Vex(String word) { return _add(word, Vex); }
		public String Vial() { return "Vial"; } public String Vial(String word) { return _add(word, Vial); }
		public String Villager() { return "Villager"; } public String Villager(String word) { return _add(word, Villager); }
		public String Vine(String word) { return _add(word, Vine); }
		public String Vines() { return "Vines"; } public String Vines(String word) { return _add(word, Vines); }
		public String Wall() { return "Wall"; } public String Wall(String word) { return _add(word, Wall); }
		public Pair<String, String> _Wall(String word) { return new Pair<>(word, Wall(word)); }
		public String Warm() { return "Warm"; } public String Warm(String word) { return _add(word, Warm); }
		public String Warped() { return "Warped"; } public String Warped(String word) { return _add(word, Warped); }
		public String Wart() { return "Wart"; } public String Wart(String word) { return _add(word, Wart); }
		public String Water() { return "Water"; } public String Water(String word) { return _add(word, Water); }
		public String Wax() { return "Wax"; } public String Wax(String word) { return _add(word, Wax); }
		public String Waxed(String word) { return _add(word, Waxed); }
		public String Weathered() { return "Weathered"; } public String Weathered(String word) { return _add(word, Weathered); }
		public String Weighted() { return "Weighted"; } public String Weighted(String word) { return _add(word, Weighted); }
		public String White(String word) { return _add(word, White); }
		public String Wild() { return "Wild"; } public String Wild(String word) { return _add(word, Wild); }
		public String Wildfire() { return "Wildfire"; } public String Wildfire(String word) { return _add(word, Wildfire); }
		public String Wilted() { return "Wilted"; } public String Wilted(String word) { return _add(word, Wilted); }
		public String Wind() { return "Wind"; } public String Wind(String word) { return _add(word, Wind); }
		public String Wire(String word) { return _add(word, Wire); }
		public String Wither() { return "Wither"; } public String Wither(String word) { return _add(word, Wither); }
		public String Wolf() { return "Wolf"; } public String Wolf(String word) { return _add(word, Wolf); }
		public String Wood() { return "Wood"; } public String Wood(String word) { return _add(word, Wood); }
		public String Woodcutter() { return "Woodcutter"; } public String Woodcutter(String word) { return _add(word, Woodcutter); }
		public String Wool(String word) { return _add(word, Wool); }
		public String Wooly(String word) { return _add(word, Wooly); }
		public String Yellow(String word) { return _add(word, Yellow); }
		public String Zombie(String word) { return _add(word, Zombie); }

		public String Subtitle_Block_Step(String name) { return _add(on(Footsteps), name); }
		public String Subtitle_Block_Hit(String name) { return breaking(name); }
		public String Subtitle_Block_Break(String name) { return broken(name); }
		public String Subtitle_Block_Place(String name) { return Lang.join(name, placed); }

		public String Subtitle_Boat_Paddle(String name) { return Paddling(Boat(name)); }
		public String Subtitle_Boat_Row(String name) { return Rowing(Boat(name)); }

		public String Subtitle_Entity_Big_Fall(String name) { return fell(name); }
		public String Subtitle_Entity_Crash(String name) { return crashes(name); }
		public String Subtitle_Entity_Death(String name) { return dies(name); }
		public String Subtitle_Entity_Small_Fall(String name) { return trips(name); }
		public String Subtitle_Entity_Splash(String name) { return splashes(name); }
		public String Subtitle_Entity_Splash_High_Speed(String name) { return hard(splashes(name)); }
		public String Subtitle_Entity_Step(String name) { return steps(name); }
		public String Subtitle_Entity_Swim(String name) { return swims(name); }
	}
}
