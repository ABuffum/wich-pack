package fun.mousewich.gen.data.language;

import net.minecraft.util.DyeColor;
import net.minecraft.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
	public abstract String Subtitle_Entity_SplashHighSpeed(String name);
	public abstract String Subtitle_Entity_Step(String name);
	public abstract String Subtitle_Entity_Swim(String name);

	public static class en_us extends ModLanguageCache {
		@Override
		public String getLanguageCode() { return "en_us"; }
		public String Literal(String word) { return word; }

		private String _add(String prefix, String postfix) { return (prefix + " " + postfix).trim(); }
		private Pair<String, String> _apply(Function<String, String> func, Pair<String, String> pair) {
			return new Pair<>(func.apply(pair.getLeft()), func.apply(pair.getRight()));
		}

		public String advances(String word) { return _add(word, "advances"); }
		public String allays(String word) { return _add(word, "allays"); }
		public String angrily(String word) { return _add(word, "angrily"); }
		public String applied(String word) { return _add(word, "applied"); }
		public String approaches(String word) { return _add(word, "approaches"); }
		public String awkwardly(String word) { return _add(word, "awkwardly"); }
		public String bagged(String word) { return _add(word, "bagged"); }
		public String beats(String word) { return _add(word, "beats"); }
		public String blooms(String word) { return _add(word, "blooms"); }
		public String book(String word) { return _add(word, "book"); }
		public String booms(String word) { return _add(word, "booms"); }
		public String boot(String word) { return _add(word, "boot"); }
		public String breaking(String word) { return _add(word, "breaking"); }
		public String broken(String word) { return _add(word, "broken"); }
		public String bubbles(String word) { return _add(word, "bubbles"); }
		public String cannonballs(String word) { return _add(word, "cannonballs"); }
		public String charges(String word) { return _add(word, "charges"); }
		public String chimes(String word) { return _add(word, "chimes"); }
		public String chivalrously(String word) { return _add(word, "chivalrously"); }
		public String chortles(String word) { return _add(word, "chortles"); }
		public String clacking(String word) { return _add(word, "clacking"); }
		public String click(String word) { return _add(word, "click"); }
		public String clicking(String word) { return _add(word, "clicking"); }
		public String close(String word) { return _add(word, "close"); }
		public String collected(String word) { return _add(word, "collected"); }
		public String crashes(String word) { return _add(word, "crashes"); }
		public String croaks(String word) { return _add(word, "croaks"); }
		public String crunches(String word) { return _add(word, "crunches"); }
		public String delights(String word) { return _add(word, "delights"); }
		public String deployed(String word) { return _add(word, "deployed"); }
		public String dies(String word) { return _add(word, "dies"); }
		public String digs(String word) { return _add(word, "digs"); }
		public String down(String word) { return _add(word, "down"); }
		public String draws(String word) { return _add(word, "draws"); }
		public String drops(String word) { return _add(word, "drops"); }
		public String eats(String word) { return _add(word, "eats"); }
		public String emerges(String word) { return _add(word, "emerges"); }
		public String empties(String word) { return _add(word, "empties"); }
		public String equips(String word) { return _add(word, "equips"); }
		public String explodes(String word) { return _add(word, "explodes"); }
		public String fell(String word) { return _add(word, "fell"); }
		public String fills(String word) { return _add(word, "fills"); }
		public String fizzes(String word) { return _add(word, "fizzes"); }
		public String flops(String word) { return _add(word, "flops"); }
		public String footsteps(String word) { return _add(word, "footsteps"); }
		public String gallops(String word) { return _add(word, "gallops"); }
		public String glugs(String word) { return _add(word, "glugs"); }
		public String groans(String word) { return _add(word, "groans"); }
		public String grunts(String word) { return _add(word, "grunts"); }
		public String hard(String word) { return _add(word, "hard"); }
		public String heart(String word) { return _add(word, "heart"); }
		public String hisses(String word) { return _add(word, "hisses"); }
		public String hit(String word) { return _add(word, "hit"); }
		public String hot(String word) { return _add(word, "hot"); }
		public String howls(String word) { return _add(word, "howls"); }
		public String hurts(String word) { return _add(word, "hurts"); }
		public String in(String word) { return _add(word, "in"); }
		public String injected(String word) { return _add(word, "injected"); }
		public String juiced(String word) { return _add(word, "juiced"); }
		public String jumps(String word) { return _add(word, "jumps"); }
		public String lands(String word) { return _add(word, "lands"); }
		public String lays(String word) { return _add(word, "lays"); }
		public String lightly(String word) { return _add(word, "lightly"); }
		public String loaded(String word) { return _add(word, "loaded"); }
		public String loudly(String word) { return _add(word, "loudly"); }
		public String marching(String word) { return _add(word, "marching"); }
		public String moos(String word) { return _add(word, "moos"); }
		public String medium(String word) { return _add(word, "medium"); }
		public String notice(String word) { return _add(word, "notice"); }
		public String of(String word) { return _add(word, "of"); }
		public String on(String word) { return _add(word, "on"); }
		public String paddles(String word) { return _add(word, "paddles"); }
		public String placed(String word) { return _add(word, "placed"); }
		public String plays(String word) { return _add(word, "plays"); }
		public String porcelain(String word) { return _add(word, "porcelain"); }
		public String quietly(String word) { return _add(word, "quietly"); }
		public String rages(String word) { return _add(word, "rages"); }
		public String recovers(String word) { return _add(word, "recovers"); }
		public String released(String word) { return _add(word, "released"); }
		public String roars(String word) { return _add(word, "roars"); }
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
		public String splashes(String word) { return _add(word, "splashes"); }
		public String splashes_hard(String word) { return hard(splashes(word)); }
		public String splashing(String word) { return _add(word, "splashing"); }
		public String splats(String word) { return _add(word, "splats"); }
		public String sploshes(String word) { return _add(word, "sploshes"); }
		public String spreads(String word) { return _add(word, "spreads"); }
		public String squelches(String word) { return _add(word, "squelches"); }
		public String stands(String word) { return _add(word, "stands"); }
		public String starts(String word) { return _add(word, "starts"); }
		public String steps(String word) { return _add(word, "steps"); }
		public String stomping(String word) { return _add(word, "stomping"); }
		public String stops(String word) { return _add(word, "stops"); }
		public String swims(String word) { return _add(word, "swims"); }
		public String taken(String word) { return _add(word, "taken"); }
		public String takes(String word) { return _add(word, "takes"); }
		public String tapping(String word) { return _add(word, "tapping"); }
		public String tendrils(String word) { return _add(word, "tendrils"); }
		public String tosses(String word) { return _add(word, "tosses"); }
		public String trips(String word) { return _add(word, "trips"); }
		public String undignified(String word) { return _add(word, "undignified"); }
		public String up(String word) { return _add(word, "up"); }
		public String used(String word) { return _add(word, "used"); }
		public String whines(String word) { return _add(word, "whines"); }
		public String with(String word) { return _add(word, "with"); }
		public String withChest(String word) { return Chest(with(word)); }
		public String yearns(String word) { return _add(word, "yearns"); }
		public String yeets(String word) { return _add(word, "yeets"); }

		public String Acacia() { return "Acacia"; } public String Acacia(String word) { return _add(word, Acacia()); }
		public String Aggressive() { return "Aggressive"; } public String Aggressive(String word) { return _add(word, Aggressive()); }
		public String Allay() { return "Allay"; } public String Allay(String word) { return _add(word, Allay()); }
		public String Allium() { return "Allium"; } public String Allium(String word) { return _add(word, Allium()); }
		public String Aloof() { return "Aloof"; } public String Aloof(String word) { return _add(word, Aloof()); }
		public String Amaranth() { return "Amaranth"; } public String Amaranth(String word) { return _add(word, Amaranth()); }
		public String Amethyst() { return "Amethyst"; } public String Amethyst(String word) { return _add(word, Amethyst()); }
		public String AmethystArrow() { return Arrow(Amethyst()); } public String AmethystArrow(String word) { return Arrow(Amethyst(word)); }
		public String AmethystBud() { return Bud(Amethyst()); } public String AmethystBud(String word) { return Bud(Amethyst(word)); }
		public String AmethystBricks() { return Bricks(Amethyst()); } public String AmethystBricks(String word) { return Bricks(Amethyst(word)); }
		public String AmethystCluster() { return Cluster(Amethyst()); } public String AmethystCluster(String word) { return Cluster(Amethyst(word)); }
		public String AmethystSlab() { return Slab(Amethyst()); } public String AmethystSlab(String word) { return Slab(Amethyst(word)); }
		public String AmethystStairs() { return Stairs(Amethyst()); } public String AmethystStairs(String word) { return Stairs(Amethyst(word)); }
		public String AmethystWall() { return Wall(Amethyst()); } public String AmethystWall(String word) { return Wall(Amethyst(word)); }
		public String Anchor() { return "Anchor"; } public String Anchor(String word) { return _add(word, Anchor()); }
		public String Ancient() { return "Ancient"; } public String Ancient(String word) { return _add(word, Ancient()); }
		public String AncientDebris() { return Debris(Ancient()); } public String AncientDebris(String word) { return Debris(Ancient(word)); }
		public String Andesite() { return "Andesite"; } public String Andesite(String word) { return _add(word, Andesite()); }
		public String Anvil() { return "Anvil"; } public String Anvil(String word) { return _add(word, Anvil()); }
		public String Armor() { return "Armor"; } public String Armor(String word) { return _add(word, Armor()); }
		public String ArmorStand() { return Stand(Armor()); } public String ArmorStand(String word) { return Stand(Armor(word)); }
		public String Arrow() { return "Arrow"; } public String Arrow(String word) { return _add(word, Arrow()); }
		public String Aster() { return "Aster"; } public String Aster(String word) { return _add(word, Aster()); }
		public String Attached() { return "Attached"; } public String Attached(String word) { return _add(word, Attached()); }
		public String AttachedStem() { return Stem(Attached()); } public String AttachedStem(String word) { return Stem(Attached(word)); }
		public String Axe() { return "Axe"; } public String Axe(String word) { return _add(word, Axe()); }
		public String Axolotl() { return "Axolotl"; } public String Axolotl(String word) { return _add(word, Axolotl()); }
		public String Azalea() { return "Azalea"; } public String Azalea(String word) { return _add(word, Azalea()); }
		public String AzaleaLeaves() { return Leaves(Azalea()); } public String AzaleaLeaves(String word) { return Leaves(Azalea(word)); }
		public String Bamboo() { return "Bamboo"; } public String Bamboo(String word) { return _add(word, Bamboo()); }
		public String BambooDoor() { return Door(Bamboo()); }
		public String BambooFence() { return Fence(Bamboo()); }
		public String BambooFenceGate() { return FenceGate(Bamboo()); }
		public String BambooLadder() { return Ladder(Bamboo()); }
		public String BambooLectern() { return Lectern(Bamboo()); }
		public String BambooMosaic() { return Mosaic(Bamboo()); }
		public String BambooSapling() { return Sapling(Bamboo()); }
		public String BambooTrapdoor() { return Trapdoor(Bamboo()); }
		public String BambooWood() { return Wood(Bamboo()); }
		public String BambooWoodcutter() { return Woodcutter(Bamboo()); }
		public String Banner() { return "Banner"; }
		public String Banner(String word) { return _add(word, Banner()); }
		public String Bark() { return "Bark"; } public String Bark(String word) { return _add(word, Bark()); }
		public String Barrel() { return "Barrel"; } public String Barrel(String word) { return _add(word, Barrel()); }
		public String Bars() { return "Bars"; } public String Bars(String word) { return _add(word, Bars()); }
		public String Basalt() { return "Basalt"; }
		public String Bat() { return "Bat"; }
		public String Beacon() { return "Beacon"; }
		public String Bear() { return "Bear"; } public String Bear(String word) { return _add(word, Bear()); }
		public String Bed() { return "Bed"; } public String Bed(String word) { return _add(word, Bed()); }
		public String Bedrock() { return "Bedrock"; }
		public String Bee() { return "Bee"; }
		public String BeeNest() { return Nest(Bee()); }
		public String Beehive() { return "Beehive"; }
		public String Beige() { return "Beige"; } public String Beige(String word) { return _add(word, Beige()); }
		public String Bell() { return "Bell"; }
		public String Berry() { return "Berry"; }
		public String Berry(String word) { return _add(word, Berry()); }
		public String Big() { return "Big"; } public String Big(String word) { return _add(word, Big()); }
		public String BigDripleaf() { return Dripleaf(Big()); } public String BigDripleaf(String word) { return Dripleaf(Big(word)); }
		public String Birch() { return "Birch"; } public String Birch(String word) { return _add(word, Birch()); }
		public String Black() { return "Black"; } public String Black(String word) { return _add(word, Black()); }
		public String Blast() { return "Blast"; } public String Blast(String word) { return _add(word, Blast()); }
		public String BlastFurnace() { return Furnace(Blast()); } public String BlastFurnace(String word) { return Furnace(Blast(word)); }
		public String Blackstone() { return "Blackstone"; } public String Blackstone(String word) { return _add(word, Blackstone()); }
		public String Bleeding() { return "Bleeding"; } public String Bleeding(String word) { return _add(word, Bleeding()); }
		public String BleedingObsidian() { return Obsidian(Bleeding()); } public String BleedingObsidian(String word) { return Obsidian(Bleeding(word)); }
		public String Blindness() { return "Blindness"; } public String Blindness(String word) { return _add(word, Blindness()); }
		public String Block() { return "Block"; } public String Block(String word) { return _add(word, Block()); }
		public String Blossom() { return "Blossom"; } public String Blossom(String word) { return _add(word, Blossom()); }
		public String Blue() { return "Blue"; } public String Blue(String word) { return _add(word, Blue()); }
		public String BlueRose() { return Rose(Blue()); } public String BlueRose(String word) { return Rose(Blue(word)); }
		public String BlueShroomlight() { return Shroomlight(Blue()); } public String BlueShroomlight(String word) { return Shroomlight(Blue(word)); }
		public String Boat() { return "Boat"; } public String Boat(String word) { return _add(word, Boat()); }
		public Pair<String, String> _Boat(String word) { return new Pair<>(Boat(word), ChestBoat(word)); }
		public String Bone() { return "Bone"; } public String Bone(String word) { return _add(word, Bone()); }
		public String BoneLadder() { return Ladder(Bone()); }
		public String Book() { return "Book"; } public String Book(String word) { return _add(word, Book()); }
		public String Bookshelf() { return "Bookshelf"; } public String Bookshelf(String word) { return _add(word, Bookshelf()); }
		public String Boots() { return "Boots"; } public String Boots(String word) { return _add(word, Boots()); }
		public String Bottle() { return "Bottle"; } public String Bottle(String word) { return _add(word, Bottle()); }
		public String Box() { return "Box"; } public String Box(String word) { return _add(word, Box()); }
		public String Brewing() { return "Brewing"; } public String Brewing(String word) { return _add(word, Brewing()); }
		public String BrewingStand() { return Stand(Brewing()); } public String BrewingStand(String word) { return Stand(Brewing(word)); }
		public String Brick() { return "Brick"; } public String Brick(String word) { return _add(word, Brick()); }
		public String Bricks() { return "Bricks"; } public String Bricks(String word) { return _add(word, Bricks()); }
		public String BrickSlab(String word) { return Slab(Brick(word)); }
		public String BrickStairs(String word) { return Stairs(Brick(word)); }
		public String BrickWall(String word) { return Wall(Brick(word)); }
		public String Brown() { return "Brown"; } public String Brown(String word) { return _add(word, Brown()); }
		public String Bucket() { return "Bucket"; } public String Bucket(String word) { return _add(word, Bucket()); }
		public String BucketOf() { return of(Bucket()); } public String BucketOf(String word) { return of(Bucket(word)); }
		public String Bud() { return "Bud"; } public String Bud(String word) { return _add(word, Bud()); }
		public String Budding() { return "Budding"; } public String Budding(String word) { return _add(word, Budding()); }
		public String BuddingEcho() { return Echo(Budding()); }
		public String Bush(String word) { return _add(word, "Bush"); }
		public String Buttercup() { return "Buttercup"; }
		public String Button(String word) { return _add(word, "Button"); }
		public String Cactus() { return "Cactus"; }
		public String Cake() { return "Cake"; }
		public String Cake(String flavor) { return _add(flavor, Cake()); }
		public String Calcite() { return "Calcite"; }
		public String Camel() { return "Camel"; }
		public String Campfire(String word) { return _add(word, "Campfire"); }
		public String Candle() { return "Candle"; }
		public String Candle(String word) { return _add(word, Candle()); }
		public String CandleCake(String candle) { return _add(with(Cake()), candle); }
		public String CandleCake(String flavor, String candle) { return _add(flavor, CandleCake(candle)); }
		public String Cane() { return "Cane"; } public String Cane(String word) { return _add(word, Cane()); }
		public String Carpet() { return "Carpet"; } public String Carpet(String word) { return _add(word, Carpet()); }
		public String Cartography() { return "Cartography"; } public String Cartography(String word) { return _add(word, Cartography()); }
		public String CartographyTable() { return Table(Cartography()); } public String CartographyTable(String word) { return Table(Cartography(word)); }
		public String Cat() { return "Cat"; } public String Cat(String word) { return _add(word, Cat()); }
		public String Catalyst() { return "Catalyst"; } public String Catalyst(String word) { return _add(word, Catalyst()); }
		public String Catalyzing() { return "Catalyzing"; } public String Catalyzing(String word) { return _add(word, Catalyzing()); }
		public String Cauldron() { return "Cauldron"; } public String Cauldron(String word) { return _add(word, Cauldron()); }
		public String Cave() { return "Cave"; } public String Cave(String word) { return _add(word, Cave()); }
		public String CaveSpider() { return Spider(Cave()); } public String CaveSpider(String word) { return Spider(Cave(word)); }
		public String CaveVines() { return Vines(Cave()); } public String CaveVines(String word) { return Vines(Cave(word)); }
		public String Chain() { return "Chain"; } public String Chain(String word) { return _add(word, Chain()); }
		public String Charcoal() { return "Charcoal"; } public String Charcoal(String word) { return _add(word, Charcoal()); }
		public String Charred() { return "Charred"; } public String Charred(String word) { return _add(word, Charred()); }
		public String Cherry() { return "Cherry"; } public String Cherry(String word) { return _add(word, Cherry()); }
		public String CherryDoor() { return Door(Cherry()); } public String CherryDoor(String word) { return Door(Cherry(word)); }
		public String CherryFence() { return Fence(Cherry()); } public String CherryFence(String word) { return Fence(Cherry(word)); }
		public String CherryFenceGate() { return FenceGate(Cherry()); } public String CherryFenceGate(String word) { return FenceGate(Cherry(word)); }
		public String CherryLeaves() { return Leaves(Cherry()); } public String CherryLeaves(String word) { return Leaves(Cherry(word)); }
		public String CherryLadder() { return Ladder(Cherry()); } public String CherryLadder(String word) { return Ladder(Cherry(word)); }
		public String CherryLectern() { return Lectern(Cherry()); } public String CherryLectern(String word) { return Lectern(Cherry(word)); }
		public String CherrySapling() { return Sapling(Cherry()); } public String CherrySapling(String word) { return Sapling(Cherry(word)); }
		public String CherryTrapdoor() { return Trapdoor(Cherry()); } public String CherryTrapdoor(String word) { return Trapdoor(Cherry(word)); }
		public String CherryWood() { return Wood(Cherry()); } public String CherryWood(String word) { return Wood(Cherry(word)); }
		public String CherryWoodcutter() { return Woodcutter(Cherry()); } public String CherryWoodcutter(String word) { return Woodcutter(Cherry(word)); }
		public String CherryWoodHangingSign() { return HangingSign(CherryWood()); }
		public String Chest() { return "Chest"; } public String Chest(String word) { return _add(word, Chest()); }
		public String Chestplate() { return "Chestplate"; } public String Chestplate(String word) { return _add(word, Chestplate()); }
		public String ChestBoat() { return withChest(Boat()); } public String ChestBoat(String word) { return _add(word, ChestBoat()); }
		public String Chevon() { return "Chevon"; } public String Chevon(String word) { return _add(word, Chevon()); }
		public String Chicken() { return "Chicken"; } public String Chicken(String word) { return _add(word, Chicken()); }
		public String Chiseled() { return "Chiseled"; } public String Chiseled(String word) { return _add(word, Chiseled()); }
		public String Chorus() { return "Chorus"; } public String Chorus(String word) { return _add(word, Chorus()); }
		public String ChorusArrow() { return Arrow(Chorus()); } public String ChorusArrow(String word) { return Arrow(Chorus(word)); }
		public String ChorusPlant() { return Plant(Chorus()); } public String ChorusPlant(String word) { return Plant(Chorus(word)); }
		public String Chum() { return "Chum"; } public String Chum(String word) { return _add(word, Chum()); }
		public String Chunkeater() { return "Chunkeater"; } public String Chunkeater(String word) { return _add(word, Chunkeater()); }
		public String Clay() { return "Clay"; } public String Clay(String word) { return _add(word, Clay()); }
		public String Cluster() { return "Cluster"; } public String Cluster(String word) { return _add(word, Cluster()); }
		public String Clumsy() { return "Clumsy"; } public String Clumsy(String word) { return _add(word, Clumsy()); }
		public String Coal() { return "Coal"; } public String Coal(String word) { return _add(word, Coal()); }
		public String CoalOre() { return Ore(Coal()); } public String CoalOre(String word) { return Ore(Coal(word)); }
		public String Coarse() { return "Coarse"; } public String Coarse(String word) { return _add(word, Coarse()); }
		public String CoarseDirt() { return Dirt(Coarse()); }
		public String Cobbled() { return "Cobbled"; } public String Cobbled(String word) { return _add(word, Cobbled()); }
		public String Cobblestone() { return "Cobblestone"; } public String Cobblestone(String word) { return _add(word, Cobblestone()); }
		public String CobbledDeepslate() { return Deepslate(Cobbled()); }
		public String Cobweb() { return "Cobweb"; } public String Cobweb(String word) { return _add(word, Cobweb()); }
		public String Cocoa() { return "Cocoa"; } public String Cocoa(String word) { return _add(word, Cocoa()); }
		public String Cod() { return "Cod"; } public String Cod(String word) { return _add(word, Cod()); }
		public String Color(DyeColor color) {
			return switch (color) {
				case WHITE -> White(); case ORANGE -> Orange(); case MAGENTA -> Magenta(); case LIGHT_BLUE -> LightBlue();
				case YELLOW -> Yellow(); case LIME -> Lime(); case PINK -> Pink(); case GRAY -> Gray();
				case LIGHT_GRAY -> LightGray(); case CYAN -> Cyan(); case PURPLE -> Purple(); case BLUE -> Blue();
				case BROWN -> Brown(); case GREEN -> Green(); case RED -> Red(); case BLACK -> Black();
			};
		}
		public String Color_(DyeColor color, String word) { return _add(Color(color), word); }
		public String Color_Carpet(DyeColor color, String word) { return Color_(color, Carpet(word)); }
		public String Color_Slab(DyeColor color, String word) { return Color_(color, Slab(word)); }
		public String Components() { return "Components"; } public String Components(String word) { return _add(word, Components()); }
		public String Composter() { return "Composter"; } public String Composter(String word) { return _add(word, Composter()); }
		public String Concrete() { return "Concrete"; } public String Concrete(String word) { return _add(word, Concrete()); }
		public String ConcretePowder() { return Powder(Concrete()); } public String ConcretePowder(String word) { return Powder(Concrete(word)); }
		public String Conduit() { return "Conduit"; } public String Conduit(String word) { return _add(word, Conduit()); }
		public String Confident() { return "Confident"; } public String Confident(String word) { return _add(word, Confident()); }
		public String Cooked() { return "Cooked"; } public String Cooked(String word) { return _add(word, Cooked()); }
		public String Copper() { return "Copper"; } public String Copper(String word) { return _add(word, Copper()); }
		public String CopperOre() { return Ore(Copper()); } public String CopperOre(String word) { return Ore(Copper(word)); }
		public String Coral() { return "Coral"; } public String Coral(String word) { return _add(word, Coral()); }
		public String CoralFan() { return Fan(Coral()); }
		public String Cow() { return "Cow"; }
		public String Cracked() { return "Cracked"; } public String Cracked(String word) { return _add(word, Cracked()); } public String Cracked_(String word) { return _add(Cracked(), word); }
		public String Cracked_Bricks(String word) { return Cracked_(Bricks(word)); }
		public String Crafting() { return "Crafting"; }
		public String CraftingTable() { return Table(Crafting()); }
		public String Creeper() { return "Creeper"; } public String Creeper(String word) { return _add(word, Creeper()); }
		public String Crop() { return "Crop"; } public String Crop(String word) { return _add(word, Crop()); }
		public String Crying() { return "Crying"; } public String Crying(String word) { return _add(word, Crying()); }
		public String CryingObsidian() { return Obsidian(Crying()); }
		public String Crystal() { return "Crystal"; } public String Crystal(String word) { return _add(word, Crystal()); }
		public String CrystalBlock(String word) { return Block(Crystal(word)); }
		public String CrystalSlab(String word) { return Slab(Crystal(word)); }
		public String CrystalStairs(String word) { return Stairs(Crystal(word)); }
		public String CrystalWall(String word) { return Wall(Crystal(word)); }
		public String Crimson() { return "Crimson"; } public String Crimson(String word) { return _add(word, Crimson()); }
		public String Cut() { return "Cut"; } public String Cut(String word) { return _add(word, Cut()); } public String Cut_(String word) { return _add(Cut(), word); }
		public String Cut_Pillar(String word) { return Cut_(Pillar(word)); }
		public String Cut_Slab(String word) { return Cut_(Slab(word)); }
		public String Cut_Stairs(String word) { return Cut_(Stairs(word)); }
		public String Cut_Wall(String word) { return Cut_(Wall(word)); }
		public String Cyan() { return "Cyan"; } public String Cyan(String word) { return _add(word, Cyan()); }
		public String Daisy() { return "Daisy"; } public String Daisy(String word) { return _add(word, Daisy()); }
		public String Dark() { return "Dark"; } public String Dark(String word) { return _add(word, Dark()); }
		public String DarkPrismarine() { return Prismarine(Dark()); } public String DarkPrismarine(String word) { return Prismarine(Dark(word)); }
		public String DarkOak() { return Oak(Dark()); } public String DarkOak(String word) { return Oak(Dark(word)); }
		public String Darkness() { return "Darkness"; } public String Darkness(String word) { return _add(word, Darkness()); }
		public String Daylight() { return "Daylight"; } public String Daylight(String word) { return _add(word, Daylight()); }
		public String DaylightDetector() { return Detector(Daylight()); } public String DaylightDetector(String word) { return Detector(Daylight(word)); }
		public String Dead() { return "Dead"; } public String Dead(String word) { return _add(word, Dead()); }
		public String DeadCoral() { return Coral(Dead()); } public String DeadCoral(String word) { return Coral(Dead(word)); }
		public String Debris() { return "Debris"; } public String Debris(String word) { return _add(word, Debris()); }
		public String Deepslate() { return "Deepslate"; } public String Deepslate(String word) { return _add(word, Deepslate()); }
		public String DeepslateCoalOre() { return CoalOre(Deepslate()); }
		public String DeepslateCopperOre() { return CopperOre(Deepslate()); }
		public String DeepslateDiamondOre() { return DiamondOre(Deepslate()); }
		public String DeepslateEmeraldOre() { return EmeraldOre(Deepslate()); }
		public String DeepslateGoldOre() { return GoldOre(Deepslate()); }
		public String DeepslateIronOre() { return IronOre(Deepslate()); }
		public String DeepslateLapisOre() { return LapisOre(Deepslate()); }
		public String DeepslateRedstoneOre() { return RedstoneOre(Deepslate()); }
		public String Detector() { return "Detector"; } public String Detector(String word) { return _add(word, Detector()); }
		public String Devouring() { return "Devouring"; } public String Devouring(String word) { return _add(word, Devouring()); }
		public String Diamond() { return "Diamond"; } public String Diamond(String word) { return _add(word, Diamond()); }
		public String DiamondOre() { return Ore(Diamond()); } public String DiamondOre(String word) { return Ore(Diamond(word)); }
		public String Diorite() { return "Diorite"; }
		public String Dirt() { return "Dirt"; }
		public String Dirt(String word) { return _add(word, Dirt()); }
		public String DirtPath() { return Path(Dirt()); }
		public String Disc() { return "Disc"; }
		public String Disc(String word) { return _add(word, Disc()); }
		public String Dispenser() { return "Dispenser"; }
		public String Donkey() { return "Donkey"; }
		public String Door() { return "Door"; }
		public String Door(String word) { return _add(word, Door()); }
		public String DoorClangs() { return _add(Door(), "clangs"); }
		public String Dragon() { return "Dragon"; }
		public String DragonEgg() { return Egg(Dragon()); }
		public String DragonFireball() { return Fireball(Dragon()); }
		public String DragonHead() { return Head(Dragon()); }
		public String Dried() { return "Dried"; } public String Dried(String word) { return _add(word, Dried()); }
		public String DriedKelp() { return Kelp(Dried()); } public String DriedKelp(String word) { return Kelp(Dried(word)); }
		public String Dripleaf() { return "Dripleaf"; } public String Dripleaf(String word) { return _add(word, Dripleaf()); }
		public String Dripstone() { return "Dripstone"; } public String Dripstone(String word) { return _add(word, Dripstone()); }
		public String Dropper() { return "Dropper"; } public String Dropper(String word) { return _add(word, Dropper()); }
		public String Echo() { return "Echo"; } public String Echo(String word) { return _add(word, Echo()); }
		public String EchoBud() { return Bud(Echo()); } public String EchoBud(String word) { return Bud(Echo(word)); }
		public String EchoCluster() { return Cluster(Echo()); } public String EchoCluster(String word) { return Cluster(Echo(word)); }
		public String EchoCrystal() { return Crystal(Echo()); } public String EchoCrystal(String word) { return Crystal(Echo(word)); }
		public String Egg() { return "Egg"; } public String Egg(String word) { return _add(word, Egg()); }
		public String Elder() { return "Elder"; } public String Elder(String word) { return _add(word, Elder()); }
		public String ElderGuardian() { return Guardian(Elder()); } public String ElderGuardian(String word) { return Guardian(Elder(word)); }
		public String Emerald() { return "Emerald"; } public String Emerald(String word) { return _add(word, Emerald()); }
		public String EmeraldOre() { return Ore(Emerald()); } public String EmeraldOre(String word) { return Ore(Emerald(word)); }
		public String Enchanted() { return "Enchanted"; } public String Enchanted(String word) { return _add(word, Enchanted()); }
		public String Enchanting() { return "Enchanting"; } public String Enchanting(String word) { return _add(word, Enchanting()); }
		public String EnchantingTable() { return Table(Enchanting()); } public String EnchantingTable(String word) { return Table(Enchanting(word)); }
		public String End() { return "End"; } public String End(String word) { return _add(word, End()); }
		public String EndPortal() { return Portal(End()); } public String EndPortal(String word) { return Portal(End(word)); }
		public String EndPortalFrame() { return Frame(EndPortal()); } public String EndPortalFrame(String word) { return Frame(EndPortal(word)); }
		public String EndRod() { return Rod(End()); } public String EndRod(String word) { return Rod(End(word)); }
		public String Ender() { return "Ender"; } public String Ender(String word) { return _add(word, Ender()); }
		public String EnderChest() { return Chest(Ender()); } public String EnderChest(String word) { return Chest(Ender(word)); }
		public String EnderLantern() { return Lantern(Ender()); } public String EnderLantern(String word) { return Lantern(Ender(word)); }
		public Pair<String, String> EnderTorch() { return _Torch(Ender()); } public Pair<String, String> EnderTorch(String word) { return _Torch(Ender(word)); }
		public String Endermite() { return "Endermite"; } public String Endermite(String word) { return _add(word, Endermite()); }
		public String Excited() { return "Excited"; } public String Excited(String word) { return _add(word, Excited()); }
		public String Exposed() { return "Exposed"; } public String Exposed(String word) { return _add(word, Exposed()); }
		public String ExposedCopper() { return Copper(Exposed()); } public String ExposedCopper(String word) { return Copper(Exposed(word)); }
		public String Fan() { return "Fan"; } public String Fan(String word) { return _add(word, Fan()); }
		public String Farmland() { return "Farmland"; } public String Farmland(String word) { return _add(word, Farmland()); }
		public String Feather() { return "Feather"; } public String Feather(String word) { return _add(word, Feather()); }
		public String Feathers() { return "Feathers"; } public String Feathers(String word) { return _add(word, Feathers()); }
		public String Fence() { return "Fence"; } public String Fence(String word) { return _add(word, Fence()); }
		public String FenceGate() { return Gate(Fence()); } public String FenceGate(String word) { return Gate(Fence(word)); }
		public String Fire() { return "Fire"; } public String Fire(String word) { return _add(word, Fire()); }
		public String Fireball() { return "Fireball"; } public String Fireball(String word) { return _add(word, Fireball()); }
		public String Fish() { return "Fish"; } public String Fish(String word) { return _add(word, Fish()); }
		public String Flashbanged() { return "Flashbanged"; } public String Flashbanged(String word) { return _add(word, Flashbanged()); }
		public String Fleece() { return "Fleece"; } public String Fleece(String word) { return _add(word, Fleece()); }
		public String Fletching() { return "Fletching"; } public String Fletching(String word) { return _add(word, Fletching()); }
		public String FletchingTable() { return Table(Fletching()); }
		public String Flint() { return "Flint"; } public String Flint(String word) { return _add(word, Flint()); }
		public String Flower() { return "Flower"; } public String Flower(String word) { return _add(word, Flower()); }
		public String Flowering() { return "Flowering"; } public String Flowering(String word) { return _add(word, Flowering()); }
		public String FloweringAzalea() { return Azalea(Flowering()); } public String FloweringAzalea(String word) { return Azalea(Flowering(word)); }
		public String FloweringAzaleaLeaves() { return Leaves(FloweringAzalea()); } public String FloweringAzaleaLeaves(String word) { return Leaves(FloweringAzalea(word)); }
		public String Flowers() { return "Flowers"; } public String Flowers(String word) { return _add(word, Flowers()); }
		public String Footsteps() { return "Footsteps"; } public String Footsteps(String word) { return _add(word, Footsteps()); }
		public String Fox() { return "Creeper"; } public String Fox(String word) { return _add(word, Fox()); }
		public String Fragment(String word) { return _add(word, "Fragment"); }
		public String Frame() { return "Frame"; } public String Frame(String word) { return _add(word, Frame()); }
		public String Frog() { return "Frog"; }
		public String Froglight() { return "Froglight"; } public String Froglight(String word) { return _add(word, Froglight()); }
		public String Frogspawn() { return "Frogspawn"; }
		public String Fungus() { return "Fungus"; }
		public String Furnace() { return "Furnace"; } public String Furnace(String word) { return _add(word, Furnace()); }
		public String Gate(String word) { return _add(word, "Gate"); }
		public String Gilded() { return "Gilded"; } public String Gilded(String word) { return _add(word, Gilded()); }
		public String GildedBlackstone() { return Blackstone(Gilded()); } public String GildedBlackstone(String word) { return Blackstone(Gilded(word)); }
		public String Glass() { return "Glass"; }
		public String Glass(String word) { return _add(word, Glass()); }
		public String GlassPane() { return Pane(Glass()); } public String GlassPane(String word) { return Pane(Glass(word)); }
		public String GlassSlab(String word) { return Slab(Glass(word)); }
		public String GlassTrapdoor(String word) { return Trapdoor(Glass(word)); }
		public String GlazedTerracotta() { return Terracotta("Glazed"); } public String GlazedTerracotta(String word) { return _add(word, GlazedTerracotta()); }
		public String Glow() { return "Glow"; } public String Glow(String word) { return _add(word, Glow()); }
		public String GlowLichen() { return Lichen(Glow()); }
		public String GlowSquid() { return Squid(Glow()); }
		public String Glowstone() { return "Glowstone"; }
		public String Goat() { return "Goat"; }
		public String GoatHorn() { return Horn(Goat()); }
		public String Goggles() { return "Goggles"; } public String Goggles(String word) { return _add(word, Goggles()); }
		public String Gold() { return "Gold"; } public String Gold(String word) { return _add(word, Gold()); }
		public String GoldOre() { return Ore(Gold()); } public String GoldOre(String word) { return Ore(Gold(word)); }
		public String Golem() { return "Golem"; } public String Golem(String word) { return _add(word, Golem()); }
		public String Granite() { return "Granite"; } public String Granite(String word) { return _add(word, Granite()); }
		public String Grass() { return "Grass"; } public String Grass(String word) { return _add(word, Grass()); }
		public String Gravel() { return "Gravel"; } public String Gravel(String word) { return _add(word, Gravel()); }
		public String Gray() { return "Gray"; } public String Gray(String word) { return _add(word, Gray()); }
		public String Green() { return "Green"; } public String Green(String word) { return _add(word, Green()); }
		public String Greg() { return "Greg"; } public String Greg(String word) { return _add(word, Greg()); }
		public String Grindstone() { return "Grindstone"; } public String Grindstone(String word) { return _add(word, Grindstone()); }
		public String Guardian() { return "Guardian"; } public String Guardian(String word) { return _add(word, Guardian()); }
		public String Hanging() { return "Hanging"; } public String Hanging(String word) { return _add(word, Hanging()); }
		public String HangingRoots() { return Roots(Hanging()); } public String HangingRoots(String word) { return Roots(Hanging(word)); }
		public String HangingSign() { return Sign(Hanging()); } public String HangingSign(String word) { return Sign(Hanging(word)); }
		public Pair<String, String> _HangingSign(String word) { return _apply(this::HangingSign, _Wall(word)); }
		public String Hay() { return "Hay"; } public String Hay(String word) { return _add(word, Hay()); }
		public String Helmet() { return "Helmet"; } public String Helmet(String word) { return _add(word, Helmet()); }
		public String Head() { return "Head"; } public String Head(String word) { return _add(word, Head()); }
		public Pair<String, String> _Head(String word) { return _apply(this::Head, _Wall(word)); }
		public String Hedge() { return "Hedge"; } public String Hedge(String word) { return _add(word, Hedge()); }
		public String Hedgehog() { return "Hedgehog"; } public String Hedgehog(String word) { return _add(word, Hedgehog()); }
		public String Heels() { return "Heels"; } public String Heels(String word) { return _add(word, Heels()); }
		public String Hoe(String word) { return _add(word, "Hoe"); }
		public String Honey() { return "Honey"; }
		public String Honeycomb() { return "Honeycomb"; }
		public String Hopper() { return "Hopper"; }
		public String Horn() { return "Horn"; }
		public String Horn(String word) { return _add(word, Horn()); }
		public String Horse() { return "Horse"; } public String Horse(String word) { return _add(word, Horse()); }
		public String HorseArmor(String word) { return Armor(Horse(word)); }
		public String Husk() { return "Husk"; } public String Husk(String word) { return _add(word, Husk()); }
		public String Hydrangea() { return "Hydrangea"; } public String Hydrangea(String word) { return _add(word, Hydrangea()); }
		public String Ice() { return "Ice"; } public String Ice(String word) { return _add(word, Ice()); }
		public String Indigo() { return "Indigo"; } public String Indigo(String word) { return _add(word, Indigo()); }
		public String Infested() { return "Infested"; } public String Infested(String word) { return _add(word, Infested()); }
		public String InfestedCobblestone() { return Cobblestone(Infested()); } public String InfestedCobblestone(String word) { return Cobblestone(Infested(word)); }
		public String InfestedDeepslate() { return Deepslate(Infested()); } public String InfestedDeepslate(String word) { return Deepslate(Infested(word)); }
		public String InfestedStone() { return Stone(Infested()); } public String InfestedStone(String word) { return Stone(Infested(word)); }
		public String Iron() { return "Iron"; } public String Iron(String word) { return _add(word, Iron()); }
		public String IronBars() { return Bars(Iron()); } public String IronBars(String word) { return Bars(Iron(word)); }
		public String IronDoor() { return Door(Iron()); } public String IronDoor(String word) { return Door(Iron(word)); }
		public String IronGolem() { return Golem(Iron()); } public String IronGolem(String word) { return Golem(Iron(word)); }
		public String IronOre() { return Ore(Iron()); } public String IronOre(String word) { return Ore(Iron(word)); }
		public String IronTrapdoor() { return Trapdoor(Iron()); } public String IronTrapdoor(String word) { return Trapdoor(Iron(word)); }
		public String Juicer() { return "Juicer"; } public String Juicer(String word) { return _add(word, Juicer()); }
		public String Jukebox() { return "Jukebox"; } public String Jukebox(String word) { return _add(word, Jukebox()); }
		public String Jumping() { return "Jumping"; } public String Jumping(String word) { return _add(word, Jumping()); }
		public String JumpingSpider() { return Spider(Jumping()); } public String JumpingSpider(String word) { return Spider(Jumping(word)); }
		public String Jungle() { return "Jungle"; } public String Jungle(String word) { return _add(word, Jungle()); }
		public String Kelp() { return "Kelp"; } public String Kelp(String word) { return _add(word, Kelp()); }
		public String Knife() { return "Knife"; } public String Knife(String word) { return _add(word, Knife()); }
		public String Ladder() { return "Ladder"; } public String Ladder(String word) { return _add(word, Ladder()); }
		public String Lamp() { return "Lamp"; } public String Lamp(String word) { return _add(word, Lamp()); }
		public String Lantern() { return "Lantern"; } public String Lantern(String word) { return _add(word, Lantern()); }
		public String Lapis() { return "Lapis"; } public String Lapis(String word) { return _add(word, Lapis()); }
		public String LapisOre() { return Ore(Lapis()); } public String LapisOre(String word) { return Ore(Lapis(word)); }
		public String Large() { return "Large"; } public String Large(String word) { return _add(word, Large()); }
		public String Lava() { return "Lava"; } public String Lava(String word) { return _add(word, Lava()); }
		public String Lavender() { return "Lavender"; } public String Lavender(String word) { return _add(word, Lavender()); }
		public String Leather() { return "Leather"; } public String Leather(String word) { return _add(word, Leather()); }
		public String Leaves() { return "Leaves"; } public String Leaves(String word) { return _add(word, Leaves()); }
		public String Lectern() { return "Lectern"; } public String Lectern(String word) { return _add(word, Lectern()); }
		public String Leggings() { return "Leggings"; } public String Leggings(String word) { return _add(word, Leggings()); }
		public String Lichen() { return "Lichen"; } public String Lichen(String word) { return _add(word, Lichen()); }
		public String Light() { return "Light"; } public String Light(String word) { return _add(word, Light()); }
		public String LightBlue() { return Blue(Light()); } public String LightBlue(String word) { return _add(word, LightBlue()); }
		public String LightGray() { return Gray(Light()); } public String LightGray(String word) { return _add(word, LightGray()); }
		public String Lily() { return "Lily"; } public String Lily(String word) { return _add(word, Lily()); }
		public String LilyPad() { return Pad(Lily()); } public String LilyPad(String word) { return Pad(Lily(word)); }
		public String Lime() { return "Lime"; } public String Lime(String word) { return _add(word, Lime()); }
		public String Lodestone() { return "Lodestone"; } public String Lodestone(String word) { return _add(word, Lodestone()); }
		public String Log() { return "Log"; } public String Log(String word) { return _add(word, Log()); }
		public String Loom() { return "Loom"; } public String Loom(String word) { return _add(word, Loom()); }
		public String Loud() { return "Loud"; } public String Loud(String word) { return _add(word, Loud()); }
		public String Magenta() { return "Magenta"; } public String Magenta(String word) { return _add(word, Magenta()); }
		public String Magma() { return "Magma"; } public String Magma(String word) { return _add(word, Magma()); }
		public String Mangrove() { return "Mangrove"; } public String Mangrove(String word) { return _add(word, Mangrove()); }
		public String MangroveRoots() { return Roots(Mangrove()); } public String MangroveRoots(String word) { return Roots(Mangrove(word)); }
		public String Marigold() { return "Marigold"; } public String Marigold(String word) { return _add(word, Marigold()); }
		public String Medium() { return "Medium"; } public String Medium(String word) { return _add(word, Medium()); }
		public String Melon() { return "Melon"; } public String Melon(String word) { return _add(word, Melon()); }
		public String Metal() { return "Metal"; } public String Metal(String word) { return _add(word, Metal()); }
		public String Mooshroom() { return "Mooshroom"; } public String Mooshroom(String word) { return _add(word, Mooshroom()); }
		public String Mosaic() { return "Mosaic"; } public String Mosaic(String word) { return _add(word, Mosaic()); }
		public String Moss() { return "Moss"; } public String Moss(String word) { return _add(word, Mud()); }
		public String MossBed() { return Bed(Moss()); } public String MossBed(String word) { return MossBed(Moss(word)); }
		public String MossCarpet() { return Carpet(Moss()); } public String MossCarpet(String word) { return Carpet(Moss(word)); }
		public String Mud() { return "Mud"; } public String Mud(String word) { return _add(word, Mud()); }
		public String MudBricks() { return Bricks(Mud()); } public String MudBricks(String word) { return Bricks(Mud(word)); }
		public String Muddy() { return "Muddy"; } public String Muddy(String word) { return _add(word, Muddy()); }
		public String MuddyMangroveRoots() { return MangroveRoots(Muddy()); } public String MuddyMangroveRoots(String word) { return MangroveRoots(Muddy(word)); }
		public String Mushroom() { return "Mushroom"; } public String Mushroom(String word) { return _add(word, Mushroom()); }
		public String MushroomPlant() { return Plant(Mushroom()); }
		public String Music() { return "Music"; } public String Music(String word) { return _add(word, Music()); }
		public String MusicDisc() { return Disc(Music()); } public String MusicDisc(String word) { return Disc(Music(word)); }
		public String Mule() { return "Mule"; } public String Mule(String word) { return _add(word, Mule()); }
		public String Mycelium() { return "Mycelium"; } public String Mycelium(String word) { return _add(word, Mycelium()); }
		public String Nest() { return "Nest"; } public String Nest(String word) { return _add(word, Nest()); }
		public String Nether() { return "Nether"; } public String Nether(String word) { return _add(word, Nether()); }
		public String NetherBark() { return Bark(Nether()); }
		public String NetherBricks() { return Bricks(Nether()); }
		public String NetherBrickFence() { return Fence(Brick(Nether())); }
		public String NetherGoldOre() { return GoldOre(Nether()); }
		public String NetherOre() { return Ore(Nether()); }
		public String NetherSprouts() { return Sprouts(Nether()); }
		public String NetherStem() { return Stem(Nether()); }
		public String NetherWart() { return Wart(Nether()); }
		public String NetherWood() { return Wood(Nether()); }
		public String NetherWoodDoor() { return Door(NetherWood()); }
		public String NetherWoodFence() { return Fence(NetherWood()); }
		public String NetherWoodFenceGate() { return FenceGate(NetherWood()); }
		public String NetherWoodLadder() { return Ladder(NetherWood()); }
		public String NetherWoodTrapdoor() { return Trapdoor(NetherWood()); }
		public String Netherite() { return "Netherite"; } public String Netherite(String word) { return _add(word, Netherite()); }
		public String NetheriteBars() { return Bars(Netherite()); }
		public String NetheriteChain() { return Chain(Netherite()); }
		public String Netherrack() { return "Netherrack"; }
		public String NetherrackGoldOre() { return GoldOre(Netherrack()); }
		public String NetherrackQuartzOre() { return QuartzOre(Netherrack()); }
		public String Note() { return "Note"; } public String Note(String word) { return _add(word, Note()); }
		public String NoteBlock() { return Block(Note()); } public String NoteBlock(String word) { return Block(Note(word)); }
		public String Nugget() { return "Nugget"; } public String Nugget(String word) { return _add(word, Nugget()); }
		public String Nylium() { return "Nylium"; } public String Nylium(String word) { return _add(word, Nylium()); }
		public String Oak() { return "Oak"; } public String Oak(String word) { return _add(word, Oak()); }
		public String Observer() { return "Observer"; } public String Observer(String word) { return _add(word, Observer()); }
		public String Obsidian() { return "Obsidian"; } public String Obsidian(String word) { return _add(word, Obsidian()); }
		public String Ocelot() { return "Ocelot"; } public String Ocelot(String word) { return _add(word, Ocelot()); }
		public String Ochre() { return "Ochre"; } public String Ochre(String word) { return _add(word, Ochre()); }
		public String Orange() { return "Orange"; } public String Orange(String word) { return _add(word, Orange()); }
		public String Ore() { return "Ore"; } public String Ore(String word) { return _add(word, Ore()); }
		public String Orchid() { return "Orchid"; } public String Orchid(String word) { return _add(word, Orchid()); }
		public String Oxidized() { return "Oxidized"; } public String Oxidized(String word) { return _add(word, Oxidized()); }
		public String OxidizedCopper() { return Copper(Oxidized()); } public String OxidizedCopper(String word) { return Copper(Oxidized(word)); }
		public String Paced() { return "Paced"; } public String Paced(String word) { return _add(word, Paced()); }
		public String Packed() { return "Packed"; } public String Packed(String word) { return _add(word, Packed()); }
		public String PackedMud() { return Mud(Packed()); } public String PackedMud(String word) { return Mud(Packed(word)); }
		public String Pad() { return "Pad"; } public String Pad(String word) { return _add(word, Pad()); }
		public String Paddling() { return "Paddling"; } public String Paddling(String word) { return _add(word, Paddling()); }
		public String Paeonia() { return "Paeonia"; } public String Paeonia(String word) { return _add(word, Paeonia()); }
		public String Pane() { return "Pane"; } public String Pane(String word) { return _add(word, Pane()); }
		public String Parrot() { return "Parrot"; } public String Parrot(String word) { return _add(word, Parrot()); }
		public String Path() { return "Path"; } public String Path(String word) { return _add(word, Path()); }
		public String Pearlescent() { return "Pearlescent"; } public String Pearlescent(String word) { return _add(word, Pearlescent()); }
		public String Petals() { return "Petals"; } public String Petals(String word) { return _add(word, Petals()); }
		public String Pickaxe() { return "Pickaxe"; } public String Pickaxe(String word) { return _add(word, Pickaxe()); }
		public String Pickle() { return "Pickle"; } public String Pickle(String word) { return _add(word, Pickle()); }
		public String Pig() { return "Pig"; } public String Pig(String word) { return _add(word, Pig()); }
		public String Piglin() { return "Piglin"; } public String Piglin(String word) { return _add(word, Piglin()); }
		public String Pillar(String word) { return _add(word, "Pillar"); }
		public String Pink() { return "Pink"; } public String Pink(String word) { return _add(word, Pink()); }
		public String PinkPetals() { return Petals(Pink()); }
		public String PinkAllium() { return Allium(Pink()); }
		public String PinkDaisy() { return Daisy(Pink()); }
		public String Piston() { return "Piston"; } public String Piston(String word) { return _add(word, Piston()); }
		public String Planks(String word) { return _add(word, "Planks"); }
		public String Plant() { return "Plant"; } public String Plant(String word) { return _add(word, Plant()); }
		public String Plate() { return "Plate"; } public String Plate(String word) { return _add(word, Plate()); }
		public String Podzol() { return "Podzol"; } public String Podzol(String word) { return _add(word, Podzol()); }
		public String Pointed() { return "Pointed"; } public String Pointed(String word) { return _add(word, Pointed()); }
		public String PointedDripstone() { return Dripstone(Pointed()); }
		public String Polar() { return "Polar"; } public String Polar(String word) { return _add(word, Polar()); }
		public String PolarBear() { return Bear(Polar()); } public String PolarBear(String word) { return Bear(Polar(word)); }
		public String Polished() { return "Polished"; } public String Polished(String word) { return _add(word, Polished()); } public String Polished_(String word) { return _add(Polished(), word); }
		public String Polished_Wall(String word) { return Polished_(Wall(word)); }
		public String PolishedDeepslate() { return Deepslate(Polished()); } public String PolishedDeepslate(String word) { return Deepslate(Polished(word)); }
		public String PolishedGildedBlackstone() { return GildedBlackstone(Polished()); } public String PolishedGildedBlackstone(String word) { return GildedBlackstone(Polished(word)); }
		public String Porcelain() { return "Porcelain"; } public String Porcelain(String word) { return _add(word, Porcelain()); }
		public String Portal() { return "Portal"; } public String Portal(String word) { return _add(word, Portal()); }
		public String Pot() { return "Pot"; } public String Pot(String word) { return _add(word, Pot()); }
		public String Potted() { return "Potted"; } public String Potted(String word) { return _add(word, Potted()); } public String Potted_(String word) { return _add(Potted(), word); }
		public Pair<String, String> _Potted(String word) { return new Pair<>(word, Potted_(word)); }
		public String PottedPlant() { return Plant(Potted()); }
		public Pair<String, String> PottedPropagule(String word) { return _Potted(Propagule(word)); }
		public Pair<String, String> PottedSapling(String word) { return _Potted(Sapling(word)); }
		public String Pouch() { return "Pouch"; } public String Pouch(String word) { return _add(word, Pouch()); }
		public String PouchOf() { return of(Pouch()); } public String PouchOf(String word) { return of(Pouch(word)); }
		public String Powder() { return "Powder"; } public String Powder(String word) { return _add(word, Powder()); }
		public String PowderSnow() { return Snow(Powder()); }
		public String Pressure() { return "Pressure"; } public String Pressure(String word) { return _add(word, Pressure()); }
		public String PressurePlate() { return Plate(Pressure()); } public String PressurePlate(String word) { return Plate(Pressure(word)); }
		public String Prismarine() { return "Prismarine"; } public String Prismarine(String word) { return _add(word, Prismarine()); }
		public String Propagule() { return "Propagule"; } public String Propagule(String word) { return _add(word, Propagule()); }
		public String Pufferfish() { return "Pufferfish"; } public String Pufferfish(String word) { return _add(word, Pufferfish()); }
		public String Pumpkin() { return "Pumpkin"; } public String Pumpkin(String word) { return _add(word, Pumpkin()); }
		public String Purple() { return "Purple"; } public String Purple(String word) { return _add(word, Purple()); }
		public String Purpur() { return "Purpur"; } public String Purpur(String word) { return _add(word, Purpur()); }
		public String Quartz() { return "Quartz"; } public String Quartz(String word) { return _add(word, Quartz()); }
		public String QuartzOre() { return Ore(Quartz()); } public String QuartzOre(String word) { return Ore(Quartz(word)); }
		public String Rabbit() { return "Rabbit"; } public String Rabbit(String word) { return _add(word, Rabbit()); }
		public String Rainbow() { return "Rainbow"; } public String Rainbow(String word) { return _add(word, Rainbow()); }
		public String Rainbow_(String word) { return _add(Rainbow(), word); }
		public String Rainbow_Carpet() { return Rainbow_(Carpet()); } public String Rainbow_Carpet(String word) { return Rainbow_(Carpet(word)); }
		public String Rainbow_Slab(String word) { return Rainbow_(Slab(word)); }
		public String Raft() { return "Raft"; } public String Raft(String word) { return _add(word, Raft()); }
		public Pair<String, String> _Raft(String word) { return new Pair<>(Raft(word), withChest(Raft(word))); }
		public String Rail() { return "Rail"; } public String Rail(String word) { return _add(word, Rail()); }
		public String Raw() { return "Raw"; } public String Raw(String word) { return _add(word, Raw()); }
		public String RawCopper() { return Copper(Raw()); } public String RawCopper(String word) { return Copper(Raw(word)); }
		public String RawGold() { return Gold(Raw()); } public String RawGold(String word) { return Gold(Raw(word)); }
		public String RawIron() { return Iron(Raw()); } public String RawIron(String word) { return Iron(Raw(word)); }
		public String Red() { return "Red"; } public String Red(String word) { return _add(word, Red()); }
		public String RedSandstone() { return Sandstone(Red()); } public String RedSandstone(String word) { return Sandstone(Red(word)); }
		public String Redstone() { return "Redstone"; } public String Redstone(String word) { return _add(word, Redstone()); }
		public String RedstoneComponents() { return Components(Redstone()); } public String RedstoneComponents(String word) { return Components(Redstone(word)); }
		public String RedstoneLamp() { return Lamp(Redstone()); } public String RedstoneLamp(String word) { return Lamp(Redstone(word)); }
		public String RedstoneOre() { return Ore(Redstone()); } public String RedstoneOre(String word) { return Ore(Redstone(word)); }
		public String RedstoneWire() { return Wire(Redstone()); } public String RedstoneWire(String word) { return Wire(Redstone(word)); }
		public String Reinforced() { return "Reinforced"; } public String Reinforced(String word) { return _add(word, Reinforced()); }
		public String ReinforcedDeepslate() { return Deepslate(Reinforced()); } public String ReinforcedDeepslate(String word) { return Deepslate(Reinforced(word)); }
		public String Respawn() { return "Respawn"; } public String Respawn(String word) { return _add(word, Respawn()); }
		public String RespawnAnchor() { return Anchor(Respawn()); } public String RespawnAnchor(String word) { return Anchor(Respawn(word)); }
		public String Rhythmic() { return "Rhythmic"; } public String Rhythmic(String word) { return _add(word, Rhythmic()); }
		public String Rod() { return "Rod"; } public String Rod(String word) { return _add(word, Rod()); }
		public String Rooted() { return "Rooted"; } public String Rooted(String word) { return _add(word, Rooted()); }
		public String RootedDirt() { return Dirt(Rooted()); } public String RootedDirt(String word) { return Dirt(Rooted(word)); }
		public String Roots() { return "Roots"; } public String Roots(String word) { return _add(word, Roots()); }
		public String Rose() { return "Rose"; } public String Rose(String word) { return _add(word, Rose()); }
		public String Row() { return "Row"; } public String Row(String word) { return _add(word, Row()); }
		public String Rowing() { return "Rowing"; } public String Rowing(String word) { return _add(word, Rowing()); }
		public String Saddle() { return "Saddle"; } public String Saddle(String word) { return _add(word, Saddle()); }
		public String Salmon() { return "Salmon"; } public String Salmon(String word) { return _add(word, Salmon()); }
		public String Salve() { return "Salve"; } public String Salve(String word) { return _add(word, Salve()); }
		public String Sand() { return "Sand"; } public String Sand(String word) { return _add(word, Sand()); }
		public String Sandstone() { return "Sandstone"; } public String Sandstone(String word) { return _add(word, Sandstone()); }
		public String Sapling() { return "Sapling"; } public String Sapling(String word) { return _add(word, Sapling()); }
		public String Scaffolding() { return "Scaffolding"; } public String Scaffolding(String word) { return _add(word, Scaffolding()); }
		public String Sculk() { return "Sculk"; } public String Sculk(String word) { return _add(word, Sculk()); }
		public String SculkCatalyst() { return Catalyst(Sculk()); } public String SculkCatalyst(String word) { return Catalyst(Sculk(word)); }
		public String SculkSensor() { return Sensor(Sculk()); } public String SculkSensor(String word) { return Sensor(Sculk(word)); }
		public String SculkShrieker() { return Shrieker(Sculk()); } public String SculkShrieker(String word) { return Shrieker(Sculk(word)); }
		public String SculkVein() { return Vein(Sculk()); } public String SculkVein(String word) { return Vein(Sculk(word)); }
		public String SculkStone() { return Stone(Sculk()); } public String SculkStone(String word) { return Stone(Sculk(word)); }
		public String SculkTurf() { return Turf(Sculk()); } public String SculkTurf(String word) { return Turf(Sculk(word)); }
		public String Sea() { return "Sea"; } public String Sea(String word) { return _add(word, Sea()); }
		public String SeaLantern() { return Lantern(Sea()); } public String SeaLantern(String word) { return Lantern(Sea(word)); }
		public String SeaPickle() { return Pickle(Sea()); } public String SeaPickle(String word) { return Pickle(Sea(word)); }
		public String Seagrass() { return "Seagrass"; } public String Seagrass(String word) { return _add(word, Seagrass()); }
		public String Seed() { return "Seed"; } public String Seed(String word) { return _add(word, Seed()); }
		public String Seeds() { return "Seeds"; } public String Seeds(String word) { return _add(word, Seeds()); }
		public String Sensor() { return "Sensor"; } public String Sensor(String word) { return _add(word, Sensor()); }
		public String Serrated() { return "Serrated"; } public String Serrated(String word) { return _add(word, Serrated()); }
		public String Shard() { return "Shard"; } public String Shard(String word) { return _add(word, Shard()); }
		public String Sharp() { return "Sharp"; } public String Sharp(String word) { return _add(word, Sharp()); }
		public String Sheep() { return "Sheep"; } public String Sheep(String word) { return _add(word, Sheep()); }
		public String Shovel() { return "Shovel"; } public String Shovel(String word) { return _add(word, Shovel()); }
		public String Shrieker() { return "Shrieker"; } public String Shrieker(String word) { return _add(word, Shrieker()); }
		public String Shroomlight() { return "Shroomlight"; } public String Shroomlight(String word) { return _add(word, Shroomlight()); }
		public String Shulker() { return "Shulker"; } public String Shulker(String word) { return _add(word, Shulker()); }
		public String ShulkerBox() { return Box(Shulker()); } public String ShulkerBox(String word) { return Box(Shulker(word)); }
		public String Sign() { return "Sign"; } public String Sign(String word) { return _add(word, Sign()); }
		public Pair<Pair<String, String>, Pair<String, String>> _Sign(String word) { return new Pair<>(_apply(this::Sign, _Wall(word)), _HangingSign(word)); }
		public String Silverfish() { return "Silverfish"; }
		public String Skeleton() { return "Skeleton"; }
		public String Skeleton(String word) { return _add(word, Skeleton()); }
		public String SkeletonHorse() { return Horse(Skeleton()); }
		public String Skull() { return "Skull"; }
		public String Slab(String word) { return _add(word, "Slab"); }
		public String Slime() { return "Slime"; }
		public String Small() { return "Small"; } public String Small(String word) { return _add(word, Small()); }
		public String SmallDripleaf() { return Dripleaf(Small()); }
		public String Smoker() { return "Smoker"; }
		public String Smooth() { return "Smooth"; }
		public String Smooth_(String word) { return _add(Smooth(), word); }
		public String Smooth_Slab(String word) { return Smooth_(Slab(word)); }
		public String Smooth_Stairs(String word) { return Smooth_(Stairs(word)); }
		public String Smooth_Wall(String word) { return Smooth_(Wall(word)); }
		public String SmoothBasalt() { return Smooth_(Basalt()); }
		public String Smithing() { return "Smithing"; } public String Smithing(String word) { return _add(word, Smithing()); }
		public String SmithingTable() { return Table(Smithing()); } public String SmithingTable(String word) { return Table(Smithing(word)); }
		public String SmithingTemplate() { return Template(Smithing()); } public String SmithingTemplate(String word) { return Template(Smithing(word)); }
		public String Sniffer() { return "Sniffer"; } public String Sniffer(String word) { return _add(word, Sniffer()); }
		public String Snow() { return "Snow"; } public String Snow(String word) { return _add(word, Snow()); }
		public String SnowGolem() { return Golem(Snow()); } public String SnowGolem(String word) { return Golem(Snow(word)); }
		public String Soft() { return "Soft"; } public String Soft(String word) { return _add(word, Soft()); }
		public String Soil() { return "Soil"; } public String Soil(String word) { return _add(word, Soil()); }
		public String Someone() { return "Someone"; } public String Someone(String word) { return _add(word, Someone()); }
		public String Something() { return "Something"; } public String Something(String word) { return _add(word, Something()); }
		public String SomethingSticky() { return Sticky(Something()); } public String SomethingSticky(String word) { return Sticky(Something(word)); }
		public String Soul() { return "Soul"; } public String Soul(String word) { return _add(word, Soul()); }
		public String SoulLantern() { return Lantern(Soul()); } public String SoulLantern(String word) { return Lantern(Soul(word)); }
		public String SoulSand() { return Sand(Soul()); } public String SoulSand(String word) { return Sand(Soul(word)); }
		public String SoulSoil() { return Soil(Soul()); } public String SoulSoil(String word) { return Soil(Soul(word)); }
		public Pair<String, String> SoulTorch(String word) { return _Torch(Soul(word)); }
		public String Spawn() { return "Spawn"; } public String Spawn(String word) { return _add(word, Spawn()); }
		public String SpawnEgg() { return Egg(Spawn()); } public String SpawnEgg(String word) { return Egg(Spawn(word)); }
		public String Spawner() { return "Spawner"; } public String Spawner(String word) { return _add(word, Spawner()); }
		public String Spider() { return "Spider"; } public String Spider(String word) { return _add(word, Spider()); }
		public String Sponge() { return "Sponge"; } public String Sponge(String word) { return _add(word, Sponge()); }
		public String Spore() { return "Spore"; } public String Spore(String word) { return _add(word, Spore()); }
		public String SporeBlossom() { return Blossom(Spore()); } public String SporeBlossom(String word) { return Blossom(Spore(word)); }
		public String Sprouts() { return "Sprouts"; } public String Sprouts(String word) { return _add(word, Sprouts()); }
		public String Spruce() { return "Spruce"; } public String Spruce(String word) { return _add(word, Spruce()); }
		public String Squid() { return "Squid"; } public String Squid(String word) { return _add(word, Squid()); }
		public String Stairs() { return "Stairs"; } public String Stairs(String word) { return _add(word, Stairs()); }
		public String Stand() { return "Stand"; } public String Stand(String word) { return _add(word, Stand()); }
		public String Stem() { return "Stem"; } public String Stem(String word) { return _add(word, Stem()); }
		public String Sticky() { return "Sticky"; } public String Sticky(String word) { return _add(word, Sticky()); }
		public String StickyPiston() { return Piston(Sticky()); } public String StickyPiston(String word) { return Piston(Sticky(word)); }
		public String Stone() { return "Stone"; } public String Stone(String word) { return _add(word, Stone()); }
		public String StoneCoalOre() { return CoalOre(Stone()); } public String StoneCoalOre(String word) { return CoalOre(Stone(word)); }
		public String StoneCopperOre() { return CopperOre(Stone()); } public String StoneCopperOre(String word) { return CopperOre(Stone(word)); }
		public String StoneDiamondOre() { return DiamondOre(Stone()); } public String StoneDiamondOre(String word) { return DiamondOre(Stone(word)); }
		public String StoneEmeraldOre() { return EmeraldOre(Stone()); } public String StoneEmeraldOre(String word) { return EmeraldOre(Stone(word)); }
		public String StoneGoldOre() { return GoldOre(Stone()); } public String StoneGoldOre(String word) { return GoldOre(Stone(word)); }
		public String StoneIronOre() { return IronOre(Stone()); } public String StoneIronOre(String word) { return IronOre(Stone(word)); }
		public String StoneLapisOre() { return LapisOre(Stone()); } public String StoneLapisOre(String word) { return LapisOre(Stone(word)); }
		public String StoneRedstoneOre() { return RedstoneOre(Stone()); } public String StoneRedstoneOre(String word) { return RedstoneOre(Stone(word)); }
		public String Stonecutter() { return "Stonecutter"; } public String Stonecutter(String word) { return _add(word, Stonecutter()); }
		public String Stray() { return "Stray"; } public String Stray(String word) { return _add(word, Stray()); }
		public String Strider() { return "Strider"; } public String Strider(String word) { return _add(word, Strider()); }
		public String Stripped() { return "Stripped"; } public String Stripped(String word) { return _add(word, Stripped()); }
		public String Studded() { return "Studded"; } public String Studded(String word) { return _add(word, Studded()); }
		public String StuddedLeather() { return Leather(Studded()); } public String StuddedLeather(String word) { return Leather(Studded(word)); }
		public String Sugar() { return "Sugar"; } public String Sugar(String word) { return _add(word, Sugar()); }
		public String SugarCane() { return Cane(Sugar()); } public String SugarCane(String word) { return Cane(Sugar(word)); }
		public String Sweet() { return "Sweet"; } public String Sweet(String word) { return _add(word, Sweet()); }
		public String SweetBerry() { return Berry(Sweet()); } public String SweetBerry(String word) { return Berry(Sweet(word)); }
		public String SweetBerryBush() { return Bush(SweetBerry()); } public String SweetBerryBush(String word) { return Bush(SweetBerry(word)); }
		public String Sword() { return "Sword"; } public String Sword(String word) { return _add(word, Sword()); }
		public String Syringe() { return "Syringe"; } public String Syringe(String word) { return _add(word, Syringe()); }
		public String Table() { return "Table"; } public String Table(String word) { return _add(word, Table()); }
		public String Tadpole() { return "Tadpole"; } public String Tadpole(String word) { return _add(word, Tadpole()); }
		public String Tall() { return "Tall"; } public String Tall(String word) { return _add(word, Tall()); } public String Tall_(String word) { return _add(Tall(), word); }
		public String Target() { return "Target"; } public String Target(String word) { return _add(word, Target()); }
		public String Template() { return "Template"; } public String Template(String word) { return _add(word, Template()); }
		public String Terracotta() { return "Terracotta"; } public String Terracotta(String word) { return _add(word, Terracotta()); }
		public String Tiles() { return "Tiles"; } public String Tiles(String word) { return _add(word, Tiles()); }
		public String Tinted() { return "Tinted"; } public String Tinted(String word) { return _add(word, Tinted()); } public String Tinted_(String word) { return _add(Tinted(), word); }
		public String TintedGlass() { return Glass(Tinted()); }
		public String TintedGoggles() { return Goggles(Tinted()); }
		public String TNT() { return "TNT"; } public String TNT(String word) { return _add(word, TNT()); }
		public String Torchflower() { return "Torchflower"; } public String Torchflower(String word) { return _add(word, Torchflower()); }
		public String Torch() { return "Torch"; } public String Torch(String word) { return _add(word, Torch()); }
		public Pair<String, String> _Torch(String word) { return _apply(this::Torch, _Wall(word)); }
		public String Trader() { return "Trader"; } public String Trader(String word) { return _add(word, Trader()); }
		public String Trapdoor() { return "Trapdoor"; } public String Trapdoor(String word) { return _add(word, Trapdoor()); }
		public String Trimming() { return "Trimming"; } public String Trimming(String word) { return _add(word, Trimming()); }
		public String TrimmingTable() { return Table(Trimming()); } public String TrimmingTable(String word) { return Table(Trimming(word)); }
		public String Tripwire() { return "Tripwire"; } public String Tripwire(String word) { return _add(word, Tripwire()); }
		public String Tropical() { return "Tropical"; } public String Tropical(String word) { return _add(word, Tropical()); }
		public String TropicalFish() { return Fish(Tropical()); } public String TropicalFish(String word) { return Fish(Tropical(word)); }
		public String Tuff() { return "Tuff"; } public String Tuff(String word) { return _add(word, Tuff()); }
		public String Tulip() { return "Tulip"; } public String Tulip(String word) { return _add(word, Tulip()); }
		public String Turf() { return "Turf"; } public String Turf(String word) { return _add(word, Turf()); }
		public String Turtle() { return "Turtle"; } public String Turtle(String word) { return _add(word, Turtle()); }
		public String TurtleEgg() { return Egg(Turtle()); } public String TurtleEgg(String word) { return Egg(Turtle(word)); }
		public String Underwater() { return "Underwater"; } public String Underwater(String word) { return _add(word, Underwater()); } public String Underwater_(String word) { return _add(Underwater(), word); }
		public Pair<String, String> Underwater_Torch() { return _Torch(Underwater()); } public Pair<String, String> Underwater_Torch(String word) { return _Torch(Underwater_(word)); }
		public String Undignified() { return "Undignified"; } public String Undignified(String word) { return _add(word, Undignified()); }
		public String Unlit() { return "Unlit"; } public String Unlit(String word) { return _add(word, Unlit()); }
		public String Upgrade() { return "Upgrade"; } public String Upgrade(String word) { return _add(word, Upgrade()); }
		public String Vein() { return "Vein"; } public String Vein(String word) { return _add(word, Vein()); }
		public String Verdant() { return "Verdant"; } public String Verdant(String word) { return _add(word, Verdant()); }
		public String Villager() { return "Villager"; } public String Villager(String word) { return _add(word, Villager()); }
		public String Vine() { return "Vine"; } public String Vine(String word) { return _add(word, Vine()); }
		public String Vines() { return "Vines"; } public String Vines(String word) { return _add(word, Vines()); }
		public String Violent() { return "Violent"; } public String Violent(String word) { return _add(word, Violent()); }
		public String Wall() { return "Wall"; } public String Wall(String word) { return _add(word, Wall()); }
		public Pair<String, String> _Wall(String word) { return new Pair<>(word, Wall(word)); }
		public String Wandering() { return "Wandering"; } public String Wandering(String word) { return _add(word, Wandering()); }
		public String WanderingTrader() { return Trader(Wandering()); } public String WanderingTrader(String word) { return Trader(Wandering(word)); }
		public String Warden() { return "Warden"; } public String Warden(String word) { return _add(word, Warden()); }
		public String Wardens() { return "Wardens"; } public String Wardens(String word) { return _add(word, Wardens()); }
		public String Warped() { return "Warped"; } public String Warped(String word) { return _add(word, Warped()); }
		public String Wart() { return "Wart"; } public String Wart(String word) { return _add(word, Wart()); }
		public String WartBlock() { return Block(Wart()); } public String WartBlock(String word) { return Block(Wart(word)); }
		public String Water() { return "Water"; } public String Water(String word) { return _add(word, Water()); }
		public String Wax() { return "Wax"; } public String Wax(String word) { return _add(word, Wax()); }
		public String Waxed() { return "Waxed"; } public String Waxed(String word) { return _add(word, Waxed()); }
		public String WaxedCopper() { return Copper(Waxed()); } public String WaxedCopper(String word) { return Copper(Waxed(word)); }
		public String WaxedExposedCopper() { return ExposedCopper(Waxed()); } public String WaxedExposedCopper(String word) { return ExposedCopper(Waxed(word)); }
		public String WaxedOxidizedCopper() { return OxidizedCopper(Waxed()); } public String WaxedOxidizedCopper(String word) { return OxidizedCopper(Waxed(word)); }
		public String WaxedWeatheredCopper() { return OxidizedCopper(Waxed()); } public String WaxedWeatheredCopper(String word) { return WeatheredCopper(Waxed(word)); }
		public String Weathered() { return "Weathered"; } public String Weathered(String word) { return _add(word, Weathered()); }
		public String WeatheredCopper() { return Copper(Weathered()); } public String WeatheredCopper(String word) { return Copper(Weathered(word)); }
		public String Weeping() { return "Weeping"; } public String Weeping(String word) { return _add(word, Weeping()); }
		public String WeepingVines() { return Vines(Weeping()); } public String WeepingVines(String word) { return Vines(Weeping(word)); }
		public String Weighted() { return "Weighted"; } public String Weighted(String word) { return _add(word, Weighted()); }
		public String WeightedPressurePlate() { return PressurePlate(Weighted()); } public String WeightedPressurePlate(String word) { return PressurePlate(Weighted(word)); }
		public String Wet() { return "Wet"; } public String Wet(String word) { return _add(word, Wet()); }
		public String WetGrass() { return Grass(Wet()); } public String WetGrass(String word) { return Grass(Wet(word)); }
		public String WetSponge() { return Sponge(Wet()); } public String WetSponge(String word) { return Sponge(Wet(word)); }
		public String White() { return "White"; } public String White(String word) { return _add(word, White()); }
		public String Wine() { return "Wine"; } public String Wine(String word) { return _add(word, Wine()); }
		public String Wire() { return "Wire"; } public String Wire(String word) { return _add(word, Wire()); }
		public String Wither() { return "Wither"; } public String Wither(String word) { return _add(word, Wither()); }
		public String WitherRose() { return Rose(Wither()); } public String WitherRose(String word) { return Rose(Wither(word)); }
		public String WitherSkeleton() { return Skeleton(Wither()); } public String WitherSkeleton(String word) { return Skeleton(Wither(word)); }
		public String Wolf() { return "Wolf"; } public String Wolf(String word) { return _add(word, Wolf()); }
		public String Wood() { return "Wood"; } public String Wood(String word) { return _add(word, Wood()); }
		public String WoodDoor() { return Door(Wood()); } public String WoodDoor(String word) { return Door(Wood(word)); }
		public String WoodFence() { return Fence(Wood()); } public String WoodFence(String word) { return Fence(Wood(word)); }
		public String WoodFenceGate() { return FenceGate(Wood()); } public String WoodFenceGate(String word) { return FenceGate(Wood(word)); }
		public String WoodTrapdoor() { return Trapdoor(Wood()); } public String WoodTrapdoor(String word) { return Trapdoor(Wood(word)); }
		public String Woodcutter() { return "Woodcutter"; } public String Woodcutter(String word) { return _add(word, Woodcutter()); }
		public String Wool() { return "Wool"; } public String Wool(String word) { return _add(word, Wool()); }
		public String Yellow() { return "Yellow"; } public String Yellow(String word) { return _add(word, Yellow()); }
		public String Zombie() { return "Zombie"; } public String Zombie(String word) { return _add(word, Zombie()); }
		public String ZombieHorse() { return Horse(Zombie()); } public String ZombieHorse(String word) { return Horse(Zombie(word)); }
		public String ZombieVillager() { return Villager(Zombie()); } public String ZombieVillager(String word) { return Villager(Zombie(word)); }
		public String Zombified() { return "Zombified"; } public String Zombified(String word) { return _add(word, Zombified()); }
		public String ZombifiedPigling() { return Piglin(Zombified()); } public String ZombifiedPigling(String word) { return Piglin(Zombified(word)); }

		public String Subtitle_Block_Step(String name) { return _add(on(Footsteps()), name); }
		public String Subtitle_Block_Hit(String name) { return breaking(name); }
		public String Subtitle_Block_Break(String name) { return broken(name); }
		public String Subtitle_Block_Place(String name) { return placed(name); }

		public String Subtitle_Boat_Paddle(String name) { return Paddling(Boat(name)); }
		public String Subtitle_Boat_Row(String name) { return Rowing(Boat(name)); }

		public String Subtitle_Entity_Big_Fall(String name) { return fell(name); };
		public String Subtitle_Entity_Crash(String name) { return crashes(name); };
		public String Subtitle_Entity_Death(String name) { return dies(name); };
		public String Subtitle_Entity_Small_Fall(String name) { return trips(name); };
		public String Subtitle_Entity_Splash(String name) { return splashes(name); };
		public String Subtitle_Entity_SplashHighSpeed(String name) { return splashes_hard(name); };
		public String Subtitle_Entity_Step(String name) { return steps(name); };
		public String Subtitle_Entity_Swim(String name) { return swims(name); };
	}
}
