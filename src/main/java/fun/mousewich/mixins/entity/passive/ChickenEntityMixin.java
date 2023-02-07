package fun.mousewich.mixins.entity.passive;

import fun.mousewich.gen.data.tag.ModItemTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity {
	protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="initGoals", at = @At("HEAD"))
	protected void initGoals(CallbackInfo ci) {
		if (getClass().equals(ChickenEntity.class) || getClass().equals(ChickenEntityMixin.class)) {
			this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, ChickenEntity.class));
			this.goalSelector.add(3, new TemptGoal(this, 1.0D, Ingredient.fromTag(ModItemTags.SEEDS), false));
		}
	}

	@Inject(method="isBreedingItem", at = @At("HEAD"), cancellable = true)
	public void isBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.isIn(ModItemTags.SEEDS)) cir.setReturnValue(true);
	}
}
