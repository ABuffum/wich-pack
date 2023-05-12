package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.sound.ModSoundEvents;
import fun.mousewich.util.ItemUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CaveSpiderEntity.class)
public abstract class CaveSpiderEntityMixin extends SpiderEntity implements EntityWithBloodType {
	public CaveSpiderEntityMixin(EntityType<? extends CaveSpiderEntity> entityType, World world) { super(entityType, world); }

	@Override
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_CAVE_SPIDER_AMBIENT; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_CAVE_SPIDER_HURT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_CAVE_SPIDER_DEATH; }
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(ModSoundEvents.ENTITY_CAVE_SPIDER_STEP, 0.15f, 1.0f);
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.GLASS_BOTTLE) && !this.isBaby()) {
			player.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1.0f, 1.0f);
			ItemStack itemStack2 = ItemUtil.getConsumableRemainder(itemStack, player, ModBase.SPIDER_POISON_VIAL);
			player.setStackInHand(hand, itemStack2);
			return ActionResult.success(this.world.isClient);
		}
		return super.interactMob(player, hand);
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.CAVE_SPIDER_BLOOD_TYPE; }
}
