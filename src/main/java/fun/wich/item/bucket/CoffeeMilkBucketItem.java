package fun.wich.item.bucket;

import fun.wich.util.MilkUtil;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class CoffeeMilkBucketItem extends ModMilkBucketItem {
	public CoffeeMilkBucketItem(Settings settings, BucketProvider bucketProvider) { super(settings, bucketProvider); }

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof ServerPlayerEntity serverPlayerEntity) {
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}
		if (user instanceof PlayerEntity && !((PlayerEntity)user).getAbilities().creativeMode) stack.decrement(1);
		if (!world.isClient) MilkUtil.ApplyMilk(world, user, true);
		return stack.isEmpty() ? new ItemStack(getBucketProvider().getBucket()) : stack;
	}
}
