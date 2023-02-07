package fun.mousewich.block.piglin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import fun.mousewich.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.UserCache;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;

public class PiglinHeadEntity extends BlockEntity {
	public static final String SKULL_OWNER_KEY = "SkullOwner";
	@Nullable
	private static UserCache userCache;
	@Nullable
	private static MinecraftSessionService sessionService;
	@Nullable
	private static Executor executor;
	@Nullable
	private GameProfile owner;
	private int poweredTicks;
	private boolean powered;

	public PiglinHeadEntity(BlockPos pos, BlockState state) {
		super(ModBase.PIGLIN_HEAD_BLOCK_ENTITY, pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, PiglinHeadEntity blockEntity) {
		if (world.isReceivingRedstonePower(pos)) {
			blockEntity.powered = true;
			++blockEntity.poweredTicks;
		}
		else blockEntity.powered = false;
	}
	public float getPoweredTicks(float tickDelta) {
		if (this.powered) return this.poweredTicks + tickDelta;
		return this.poweredTicks;
	}
}
