package fun.mousewich.origins.powers;

import fun.mousewich.ModBase;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class ScareFoxesPower extends Power {
	public ScareFoxesPower(PowerType<?> type, LivingEntity entity) { super(type, entity); }

	public static PowerFactory<ScareFoxesPower> createFactory() {
		return new PowerFactory<ScareFoxesPower>(ModBase.ID("scare_foxes"), new SerializableData(),
				data -> ScareFoxesPower::new).allowCondition();
	}

	public static boolean Active(Entity entity) { return PowersUtil.Active(entity, ScareFoxesPower.class); }
}
