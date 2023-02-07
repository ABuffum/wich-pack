package fun.mousewich.entity.ai.sensor;

import fun.mousewich.ModBase;
import fun.mousewich.mixins.entity.ai.SensorTypeInvoker;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class ModSensorType <U extends Sensor<?>> {
	public final Identifier identifier;
	public final Supplier<U> factory;
	private SensorType<U> sensorType;
	public ModSensorType(String id, Supplier<U> factory) {
		this.identifier = ModBase.ID(id);
		this.factory = factory;
	}
	public SensorType<U> get() {
		if (sensorType == null) Initialize();
		return sensorType;
	}
	public void Initialize() {
		if (sensorType != null) return;
		sensorType = SensorTypeInvoker.Register(identifier.toString(), factory);
	}
}