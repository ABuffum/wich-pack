package fun.wich.item;

import fun.wich.util.ItemUtil;
import fun.wich.util.OxidationScale;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Optional;
import java.util.Random;

import net.minecraft.block.Oxidizable.OxidationLevel;

public interface OxidizableItem {
	static ItemStack degrade(ItemStack stack) {
		Optional<Item> degraded = OxidationScale.getIncreasedItem(stack.getItem());
		if (degraded.isPresent()) return ItemUtil.swapItem(stack, degraded.get());
		else return ItemStack.EMPTY;
	}
	static ItemStack tryDegrade(ItemStack stack, Random random) {
		ItemStack degraded = ItemStack.EMPTY;
		if (stack.getItem() instanceof OxidizableItem oxidizable) {
			OxidationLevel level = oxidizable.getDegradationLevel();
			if (stack.isDamageable()) {
				float damage = 1 - (stack.getDamage() / (float) stack.getMaxDamage());
				if (level == OxidationLevel.UNAFFECTED) {
					if (damage <= 0.75f) degraded = degrade(stack);
				}
				else if (level == OxidationLevel.EXPOSED) {
					if (damage <= 0.5f) degraded = degrade(stack);
				}
				else if (level == OxidationLevel.WEATHERED) {
					if (damage <= 0.25f) degraded = degrade(stack);
				}
			}
			else {
				float multiplier = level == OxidationLevel.UNAFFECTED ? 0.75F : 1F;
				if (random.nextFloat() < multiplier) degraded = degrade(stack);
			}
		}
		return degraded;
	}
	static void tryDegradeEquipment(LivingEntity entity, EquipmentSlot slot, Random random) {
		ItemStack degraded = tryDegrade(entity.getEquippedStack(slot), random);
		if (!degraded.isEmpty()) entity.equipStack(slot, degraded);
	}
	static void tryDegradeItems(LivingEntity entity, Random random) {
		for (EquipmentSlot slot : EquipmentSlot.values()) tryDegradeEquipment(entity, slot, random);
	}
	static ItemStack tryDegradeInWater(ItemStack stack, Random random) {
		if (stack.getItem() instanceof OxidizableItem oxidizable) {
			OxidationLevel level = oxidizable.getDegradationLevel();
			float multiplier = 1;
			if (level == OxidationLevel.EXPOSED) multiplier = 0.75f;
			else if (level == OxidationLevel.WEATHERED) multiplier = 0.5f;
			else if (level == OxidationLevel.OXIDIZED) return ItemStack.EMPTY;
			if (random.nextFloat() < multiplier / 4000f) return degrade(stack);
		}
		return ItemStack.EMPTY;
	}
	static void tryDegradeEquipmentInWater(LivingEntity entity, EquipmentSlot slot, Random random) {
		ItemStack degraded = tryDegradeInWater(entity.getEquippedStack(slot), random);
		if (!degraded.isEmpty()) entity.equipStack(slot, degraded);
	}
	static void tryDegradeArmorInWater(LivingEntity entity, Random random) {
		tryDegradeEquipmentInWater(entity, EquipmentSlot.HEAD, random);
		tryDegradeEquipmentInWater(entity, EquipmentSlot.CHEST, random);
		tryDegradeEquipmentInWater(entity, EquipmentSlot.LEGS, random);
		tryDegradeEquipmentInWater(entity, EquipmentSlot.FEET, random);
	}
	static ItemStack deoxidize(ItemStack stack) {
		Optional<Item> deoxidized = OxidationScale.getDecreasedItem(stack.getItem());
		if (deoxidized.isPresent()) return ItemUtil.swapItem(stack, deoxidized.get());
		else return ItemStack.EMPTY;
	}
	static ItemStack tryDeoxidize(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof OxidizableItem oxidizable) {
			OxidationLevel level = oxidizable.getDegradationLevel();
			if (level != OxidationLevel.UNAFFECTED) return deoxidize(stack);
		}
		return ItemStack.EMPTY;
	}
	static void tryDeoxidizeEquipment(LivingEntity entity, EquipmentSlot slot) {
		ItemStack deoxidized = tryDeoxidize(entity.getEquippedStack(slot));
		if (!deoxidized.isEmpty()) entity.equipStack(slot, deoxidized);
	}
	static void tryDeoxidizeItems(LivingEntity entity) {
		for (EquipmentSlot slot : EquipmentSlot.values()) tryDeoxidizeEquipment(entity, slot);
	}

	OxidationLevel getDegradationLevel();
	Optional<Item> getDegradationResult();
}
