package fun.mousewich.origins.powers;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class PowersUtil {
	public static <T extends Power> boolean Active(Entity entity, Class<T> powerClass) {
		return entity instanceof LivingEntity && PowerHolderComponent.hasPower(entity, powerClass, Power::isActive);
	}
}
