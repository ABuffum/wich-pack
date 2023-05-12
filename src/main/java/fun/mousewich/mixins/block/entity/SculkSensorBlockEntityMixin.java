package fun.mousewich.mixins.block.entity;

import fun.mousewich.block.sculk.ExtendedSculkEntity;
import fun.mousewich.event.ModVibrationListener;
import fun.mousewich.util.SculkUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SculkSensorBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import net.minecraft.world.event.listener.SculkSensorListener;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SculkSensorBlockEntity.class)
public abstract class SculkSensorBlockEntityMixin extends BlockEntity implements SculkSensorListener.Callback, ModVibrationListener.Callback, ExtendedSculkEntity {
	public SculkSensorBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		this.modListener = new ModVibrationListener(new BlockPositionSource(this.pos), ((SculkSensorBlock)state.getBlock()).getRange(), this, null, 0.0f, 0);
	}

	private ModVibrationListener modListener;
	public ModVibrationListener getModEventListener() {
		if (this.modListener == null) {
			this.modListener = new ModVibrationListener(new BlockPositionSource(this.pos), ((SculkSensorBlock)getCachedState().getBlock()).getRange(), this, null, 0.0f, 0);
		}
		return modListener;
	}
	@Shadow
	private int lastVibrationFrequency;

	@Override
	public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, BlockPos pos2) {
		if (this.isRemoved() || pos.equals(this.getPos()) && (event == GameEvent.BLOCK_DESTROY || event == GameEvent.BLOCK_PLACE)) return false;
		return SculkUtil.isInactive(this.getCachedState());
	}

	@Override
	public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, Entity sourceEntity, float distance) {
		BlockState blockState = this.getCachedState();
		if (SculkUtil.isInactive(blockState)) {
			this.lastVibrationFrequency = SculkUtil.FREQUENCIES.getInt(event);
			SculkUtil.setActive(entity, world, this.pos, blockState, SculkUtil.getPower(distance, listener.getRange()));
		}
	}

	@Override
	public boolean accepts(World world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity) {
		if (this.isRemoved() || pos.equals(this.getPos()) && (event == GameEvent.BLOCK_DESTROY || event == GameEvent.BLOCK_PLACE)) return false;
		return SculkUtil.isInactive(this.getCachedState());
	}

	@Override
	public void accept(World world, GameEventListener listener, GameEvent event, int distance) {
		BlockState blockState = this.getCachedState();
		if (SculkUtil.isInactive(blockState)) {
			this.lastVibrationFrequency = SculkUtil.FREQUENCIES.getInt(event);
			SculkUtil.setActive(null, world, this.pos, blockState, SculkUtil.getPower(distance, listener.getRange()));
		}
	}

	public void onListen() { this.markDirty(); }

	@Override
	public void setLastVibrationFrequency(int lastVibrationFrequency) {
		this.lastVibrationFrequency = lastVibrationFrequency;
	}
}
