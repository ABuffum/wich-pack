package fun.mousewich.origins.powers;

import fun.mousewich.ModBase;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class BreatheInWaterPower extends Power {
	public BreatheInWaterPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }
	public static PowerFactory<BreatheInWaterPower> createFactory() {
		return new PowerFactory<BreatheInWaterPower>(ModBase.ID("breathe_in_water"), new SerializableData(),
				data -> BreatheInWaterPower::new).allowCondition();
	}
}
