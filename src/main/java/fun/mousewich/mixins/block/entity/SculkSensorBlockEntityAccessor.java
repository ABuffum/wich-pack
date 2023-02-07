package fun.mousewich.mixins.block.entity;

import net.minecraft.block.entity.SculkSensorBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SculkSensorBlockEntity.class)
public interface SculkSensorBlockEntityAccessor {
	@Accessor("lastVibrationFrequency")
	public int getLastVibrationFrequency();
	@Accessor("lastVibrationFrequency")
	public void setLastVibrationFrequency(int value);
}
