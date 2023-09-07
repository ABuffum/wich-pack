package fun.mousewich.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Collection;

public class IgniteCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		dispatcher.register(CommandManager.literal("ignite")
				.requires(source -> source.hasPermissionLevel(2))
				.executes((context) -> execute(ImmutableList.of(context.getSource().getEntityOrThrow()), 10))
				.then(CommandManager.argument("targets", EntityArgumentType.entities())
						.executes((context) -> execute(EntityArgumentType.getEntities(context, "targets"), 10))
						.then((CommandManager.argument("seconds", IntegerArgumentType.integer(1, 1000000))
								.executes(context -> execute(EntityArgumentType.getEntities(context, "targets"), IntegerArgumentType.getInteger(context, "seconds")))))));
	}

	private static int execute(Collection<? extends Entity> targets, int duration) {
		for (Entity entity : targets) {
			if (!entity.isFireImmune()) entity.setOnFireFor(duration);
		}
		return targets.size();
	}
}
