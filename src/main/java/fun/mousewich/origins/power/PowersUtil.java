package fun.mousewich.origins.power;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import net.minecraft.entity.Entity;

public class PowersUtil {
	public static <T extends Power> boolean Active(Entity entity, Class<T> powerClass) {
		return PowerHolderComponent.hasPower(entity, powerClass, Power::isActive);
	}
}
