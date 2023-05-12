package fun.mousewich.item.horn.goat;

import fun.mousewich.ModBase;
import fun.mousewich.event.ModGameEvent;
import fun.mousewich.item.horn.HornItem;
import fun.mousewich.util.CollectionUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class GoatHornItem extends HornItem {
	private static final String INSTRUMENT_KEY = "instrument";
	public GoatHornItem(Settings settings) { super(settings); }
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		GoatHornInstrument instrument = this.getInstrument(stack);
		if (instrument != null) {
			MutableText mutableText = new TranslatableText("instrument.minecraft." + instrument.getName());
			tooltip.add(mutableText.formatted(Formatting.GRAY));
		}
	}
	private GoatHornInstrument getInstrument(ItemStack stack) {
		NbtCompound nbtCompound = stack.getNbt();
		if (nbtCompound != null) return GoatHornInstruments.Get(nbtCompound.getString(INSTRUMENT_KEY));
		for (GoatHornInstrument instrument : GoatHornInstruments.INSTRUMENTS) return instrument;
		return null;
	}
	public static ItemStack getStackForInstrument(Item item, GoatHornInstrument instrument) {
		if (instrument == null) throw new IllegalStateException("Invalid instrument");
		ItemStack stack = new ItemStack(item);
		stack.getOrCreateNbt().putString(INSTRUMENT_KEY, instrument.getName());
		return stack;
	}

	public static void setRandomInstrumentFromRegular(ItemStack stack, Random random) {
		List<GoatHornInstrument> instruments = CollectionUtil.copyShuffled(GoatHornInstruments.INSTRUMENTS.toArray(new GoatHornInstrument[0]), random);
		setInstrument(stack, instruments.get(0));
	}
	private static void setInstrument(ItemStack stack, GoatHornInstrument instrument) {
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		nbtCompound.putString(INSTRUMENT_KEY, instrument.getName());
	}
	@Override
	public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
		if (this.isIn(group)) {
			for (GoatHornInstrument instrument : GoatHornInstruments.INSTRUMENTS) {
				stacks.add(getStackForInstrument(ModBase.GOAT_HORN, instrument));
			}
		}
	}
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		GoatHornInstrument i = this.getInstrument(itemStack);
		if (i != null) {
			user.setCurrentHand(hand);
			playSound(world, user, i);
			user.getItemCooldownManager().set(this, i.getUseDuration());
			return TypedActionResult.consume(itemStack);
		}
		return TypedActionResult.fail(itemStack);
	}
	@Override
	public int getMaxUseTime(ItemStack stack) {
		GoatHornInstrument i = getInstrument(stack);
		return i == null ? 0 : i.getUseDuration();
	}
	private static void playSound(World world, PlayerEntity player, GoatHornInstrument instrument) {
		SoundEvent soundEvent = instrument.getSoundEvent();
		float f = instrument.getRange() / 16.0f;
		world.playSoundFromEntity(player, player, soundEvent, SoundCategory.RECORDS, f, 1.0f);
		world.emitGameEvent(player, ModGameEvent.INSTRUMENT_PLAY, player.getBlockPos());
	}
}
