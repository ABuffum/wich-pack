package fun.mousewich.entity;

import fun.mousewich.entity.frog.FrogVariant;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.PacketByteBuf;

import java.util.OptionalInt;

public class ModDataHandlers {

	public static final TrackedDataHandler<Long> LONG = new TrackedDataHandler<>(){
		@Override public void write(PacketByteBuf packetByteBuf, Long value) { packetByteBuf.writeVarLong(value); }
		@Override public Long read(PacketByteBuf packetByteBuf) { return packetByteBuf.readVarLong(); }
		@Override public Long copy(Long value) { return value; }
	};
	public static final TrackedDataHandler<FrogVariant> FROG_VARIANT = new TrackedDataHandler<>(){
		@Override public void write(PacketByteBuf packetByteBuf, FrogVariant variant) { packetByteBuf.writeEnumConstant(variant); }
		@Override public FrogVariant read(PacketByteBuf packetByteBuf) { return packetByteBuf.readEnumConstant(FrogVariant.class); }
		@Override public FrogVariant copy(FrogVariant variant) { return variant; }
	};
	public static final TrackedDataHandler<ModEntityPose> NEW_ENTITY_POSE = new TrackedDataHandler<>(){
		@Override public void write(PacketByteBuf packetByteBuf, ModEntityPose entityPose) { packetByteBuf.writeEnumConstant(entityPose); }
		@Override public ModEntityPose read(PacketByteBuf packetByteBuf) { return packetByteBuf.readEnumConstant(ModEntityPose.class); }
		@Override public ModEntityPose copy(ModEntityPose entityPose) { return entityPose; }
	};
	public static final TrackedDataHandler<OptionalInt> OPTIONAL_INT = new TrackedDataHandler<>() {
		@Override public void write(PacketByteBuf packetByteBuf, OptionalInt optionalInt) { packetByteBuf.writeVarInt(optionalInt.orElse(-1) + 1); }
		@Override public OptionalInt read(PacketByteBuf packetByteBuf) {
			int i = packetByteBuf.readVarInt();
			return i == 0 ? OptionalInt.empty() : OptionalInt.of(i - 1);
		}
		@Override public OptionalInt copy(OptionalInt optionalInt) { return optionalInt; }
	};

	public static void Initialize() {
		TrackedDataHandlerRegistry.register(LONG);
		TrackedDataHandlerRegistry.register(FROG_VARIANT);
		TrackedDataHandlerRegistry.register(NEW_ENTITY_POSE);
		TrackedDataHandlerRegistry.register(OPTIONAL_INT);
	}
}
