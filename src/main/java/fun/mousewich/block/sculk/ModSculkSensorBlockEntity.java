package fun.mousewich.block.sculk;

import fun.mousewich.ModBase;
import fun.mousewich.event.ModVibrationListener;
import fun.mousewich.util.SculkUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
public class ModSculkSensorBlockEntity extends BlockEntity implements ModVibrationListener.Callback {
	private final ModVibrationListener listener;
	private int lastVibrationFrequency;

	public ModSculkSensorBlockEntity(BlockPos pos, BlockState state) {
		super(ModBase.SCULK_SENSOR_ENTITY, pos, state);
		this.listener = new ModVibrationListener(new BlockPositionSource(this.pos), ((ModSculkSensorBlock)state.getBlock()).getRange(), this, null, 0.0f, 0);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.lastVibrationFrequency = nbt.getInt("last_vibration_frequency");
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putInt("last_vibration_frequency", this.lastVibrationFrequency);
	}

	public ModVibrationListener getEventListener() { return this.listener; }

	public int getLastVibrationFrequency() { return this.lastVibrationFrequency; }

	@Override
	public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, BlockPos pos2) {
		if (this.isRemoved() || pos.equals(this.getPos()) && (event == GameEvent.BLOCK_DESTROY || event == GameEvent.BLOCK_PLACE)) return false;
		return SculkUtils.isInactive(this.getCachedState());
	}

	@Override
	public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, Entity sourceEntity, float distance) {
		BlockState blockState = this.getCachedState();
		if (SculkUtils.isInactive(blockState)) {
			this.lastVibrationFrequency = SculkUtils.FREQUENCIES.getInt(event);
			SculkUtils.setActive(entity, world, this.pos, blockState, SculkUtils.getPower(distance, listener.getRange()));
		}
	}

	public void onListen() { this.markDirty(); }

	public void setLastVibrationFrequency(int lastVibrationFrequency) {
		this.lastVibrationFrequency = lastVibrationFrequency;
	}
}
