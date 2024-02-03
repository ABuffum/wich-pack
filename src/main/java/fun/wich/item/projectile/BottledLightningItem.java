package fun.wich.item.projectile;

import fun.wich.entity.projectile.BottledLightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BottledLightningItem extends Item {
	public BottledLightningItem(Settings settings) { super(settings); }

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.NEUTRAL, 0.5F, 1F);
		if (!world.isClient) {
			BottledLightningEntity lightningEntity = new BottledLightningEntity(world, user);
			lightningEntity.setItem(itemStack);
			lightningEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0F, 1.5F, 0F);
			world.spawnEntity(lightningEntity);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		if (!user.getAbilities().creativeMode) itemStack.decrement(1);
		return TypedActionResult.success(itemStack, world.isClient());
	}
}