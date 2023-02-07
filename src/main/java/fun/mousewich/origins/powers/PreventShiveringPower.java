package fun.mousewich.origins.powers;

import fun.mousewich.ModBase;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class PreventShiveringPower extends Power {
	public PreventShiveringPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }

	public static PowerFactory<?> createFactory() {
		return new PowerFactory<>(ModBase.ID("prevent_shivering"), new SerializableData(), data -> PreventShiveringPower::new).allowCondition();
	}
}
