package fun.mousewich.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.*;

public class GoToSpawnCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		dispatcher.register(CommandManager.literal("gotospawn")
				.requires(source -> source.hasPermissionLevel(2))
				.executes(context -> execute(ImmutableList.of(context.getSource().getEntityOrThrow())))
				.then(CommandManager.argument("targets", EntityArgumentType.entities())
						.executes(context -> execute(EntityArgumentType.getEntities(context, "targets")))));
	}

	public static int execute(Collection<? extends Entity> targets) {
		for (Entity entity : targets) {
			if (entity instanceof ServerPlayerEntity player) GoToSpawn(player);
		}
		return targets.size();
	}

	public static void GoToSpawn(ServerPlayerEntity player) {
		ServerWorld world = player.server.getWorld(player.getSpawnPointDimension());
		BlockPos pos = player.getSpawnPointPosition();
		Optional<Vec3d> optional = world != null && pos != null ? PlayerEntity.findRespawnPosition(world, pos, player.getSpawnAngle(), player.isSpawnForced(), true) : Optional.empty();
		double x, y, z;
		if (optional.isPresent()) {
			Vec3d vec3d = optional.get();
			x = vec3d.getX();
			y = vec3d.getY();
			z = vec3d.getZ();
		}
		else {
			world = player.server.getOverworld();
			pos = world.getSpawnPos();
			x = pos.getX();
			y = pos.getY();
			z = pos.getZ();
			player.sendMessage(new TranslatableText("block.minecraft.spawn.not_valid"), false);
		}
		player.teleport(world, x, y, z, player.prevYaw, player.prevPitch);
		//EnumSet<PlayerPositionLookS2CPacket.Flag> set = EnumSet.noneOf(PlayerPositionLookS2CPacket.Flag.class);
		//set.add(PlayerPositionLookS2CPacket.Flag.X_ROT);
		//set.add(PlayerPositionLookS2CPacket.Flag.Y_ROT);
		//teleport(player, world, x, y, z, set, player.prevYaw, player.prevPitch);
	}

	private static void teleport(ServerPlayerEntity player, ServerWorld world, double x, double y, double z, Set<PlayerPositionLookS2CPacket.Flag> movementFlags, float yaw, float pitch) {
		BlockPos blockPos = new BlockPos(x, y, z);
		if (!World.isValid(blockPos)) return;
		float f = MathHelper.wrapDegrees(yaw);
		float g = MathHelper.wrapDegrees(pitch);
		ChunkPos chunkPos = new ChunkPos(new BlockPos(x, y, z));
		world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, chunkPos, 1, player.getId());
		player.stopRiding();
		if (player.isSleeping()) player.wakeUp(true, true);
		if (world == player.world) player.networkHandler.requestTeleport(x, y, z, f, g, movementFlags);
		else player.teleport(world, x, y, z, f, g);
		player.setHeadYaw(f);
		if (!player.isFallFlying()) {
			player.setVelocity(player.getVelocity().multiply(1.0, 0.0, 1.0));
			player.setOnGround(true);
		}
	}
}
