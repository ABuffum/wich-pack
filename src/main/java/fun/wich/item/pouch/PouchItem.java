package fun.wich.item.pouch;

import fun.wich.ModBase;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class PouchItem extends Item {
	protected final SoundEvent emptyingSound;
	public PouchItem(Settings settings) { this(ModSoundEvents.ITEM_POUCH_EMPTY, settings); }
	public PouchItem(SoundEvent emptyingSound, Settings settings) {
		super(settings);
		this.emptyingSound = emptyingSound;
	}
	public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
		if (!player.getAbilities().creativeMode) return new ItemStack(ModBase.POUCH);
		return stack;
	}
	public void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos) { }
	protected void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos, ItemStack stack) {
		world.playSound(null, pos, this.emptyingSound, SoundCategory.NEUTRAL, 1.0f, 1.0f);
	}
}
