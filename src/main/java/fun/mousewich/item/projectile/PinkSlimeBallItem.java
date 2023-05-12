package fun.mousewich.item.projectile;

import fun.mousewich.entity.projectile.PinkSlimeBallEntity;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PinkSlimeBallItem extends Item {
	public PinkSlimeBallItem(Item.Settings settings) { super(settings); }
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSoundEvents.ENTITY_PINK_SLIME_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
		if (!world.isClient) {
			PinkSlimeBallEntity projectile = new PinkSlimeBallEntity(world, user);
			projectile.setItem(itemStack);
			projectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
			world.spawnEntity(projectile);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		if (!user.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}
		return TypedActionResult.success(itemStack, world.isClient());
	}
}
