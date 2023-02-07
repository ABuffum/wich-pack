package fun.mousewich.effect;

import fun.mousewich.damage.ModDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class BleedingEffect extends StatusEffect {
	public BleedingEffect() { super(StatusEffectCategory.HARMFUL, 0xFF0000); }
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		int k = 40 >> amplifier;
		if (k > 0) return duration % k == 0;
		else return true;
	}
	@Override
	public void applyUpdateEffect(LivingEntity entity, int i) { entity.damage(ModDamageSource.BLEEDING,1); }
}
