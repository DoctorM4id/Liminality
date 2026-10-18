//package dev.doctorm4id.cycle.aold.game
//
//import com.mojang.brigadier.CommandDispatcher
//import com.mojang.brigadier.arguments.DoubleArgumentType
//import com.mojang.brigadier.arguments.StringArgumentType
//import com.mojang.brigadier.context.CommandContext
//import net.minecraft.commands.CommandSourceStack
//import net.minecraft.commands.Commands
//import net.minecraft.commands.SharedSuggestionProvider
//import net.minecraft.commands.arguments.EntityArgument
//import net.minecraft.network.chat.Component
//
///**
// * Operator commands for testing the match flow. Everything here goes through [MatchManager],
// * the same entry points the real gameplay will use.
// *
// *   /cycle role <players> <none|survivor|killer|spectator>
// *   /cycle start | stop | status
// *   /cycle state <players> <healthy|injured|downed|carried|hooked|dead|escaped>
// *   /cycle hit <players>
// *   /cycle tune [name] [value]    (live movement tuning, see [MovementTuning])
// */
//object CycleCommands {
//
//	fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
//		val role = Commands.literal("role")
//		val roleTargets = Commands.argument("targets", EntityArgument.players())
//		for (r in Role.entries) {
//			roleTargets.then(Commands.literal(r.name.lowercase()).executes { ctx -> setRole(ctx, r) })
//		}
//		role.then(roleTargets)
//
//		val state = Commands.literal("state")
//		val stateTargets = Commands.argument("targets", EntityArgument.players())
//		for (h in HealthState.entries) {
//			stateTargets.then(Commands.literal(h.name.lowercase()).executes { ctx -> setState(ctx, h) })
//		}
//		state.then(stateTargets)
//
//		val hit = Commands.literal("hit")
//			.then(Commands.argument("targets", EntityArgument.players()).executes { ctx -> hit(ctx) })
//
//		val tune = Commands.literal("tune")
//			.executes { ctx -> listTuning(ctx) }
//			.then(
//				Commands.argument("name", StringArgumentType.word())
//					.suggests { _, builder -> SharedSuggestionProvider.suggest(MovementTuning.all.keys, builder) }
//					.executes { ctx -> showTuning(ctx) }
//					.then(
//						Commands.argument("value", DoubleArgumentType.doubleArg(0.0, 100.0))
//							.executes { ctx -> setTuning(ctx) }
//					)
//			)
//
//		dispatcher.register(
//			Commands.literal("cycle")
//				.requires { it.hasPermission(2) }
//				.then(role)
//				.then(state)
//				.then(hit)
//				.then(tune)
//				.then(Commands.literal("start").executes { ctx -> report(ctx, MatchManager.start(ctx.source.server), "Match starting.") })
//				.then(Commands.literal("stop").executes { ctx -> report(ctx, MatchManager.stop(ctx.source.server), "Match stopped.") })
//				.then(Commands.literal("status").executes { ctx -> status(ctx) })
//		)
//	}
//
//	private fun setRole(ctx: CommandContext<CommandSourceStack>, role: Role): Int {
//		val source = ctx.source
//		var changed = 0
//		for (player in EntityArgument.getPlayers(ctx, "targets")) {
//			val error = MatchManager.assignRole(source.server, player, role)
//			if (error != null) source.sendFailure(Component.literal(error)) else changed++
//		}
//		if (changed > 0) {
//			source.sendSuccess({ Component.literal("Set $changed player(s) to ${role.name.lowercase()}.") }, true)
//		}
//		return changed
//	}
//
//	private fun setState(ctx: CommandContext<CommandSourceStack>, state: HealthState): Int {
//		val source = ctx.source
//		var changed = 0
//		for (player in EntityArgument.getPlayers(ctx, "targets")) {
//			if (MatchManager.setHealthState(source.server, player, state)) {
//				changed++
//			} else {
//				source.sendFailure(Component.literal("${player.scoreboardName} is not a survivor in the match."))
//			}
//		}
//		if (changed > 0) {
//			source.sendSuccess({ Component.literal("Set $changed survivor(s) to ${state.name.lowercase()}.") }, true)
//		}
//		return changed
//	}
//
//	private fun hit(ctx: CommandContext<CommandSourceStack>): Int {
//		val source = ctx.source
//		var changed = 0
//		for (player in EntityArgument.getPlayers(ctx, "targets")) {
//			val next = MatchManager.applyHit(source.server, player)
//			if (next != null) {
//				changed++
//				source.sendSuccess({ Component.literal("${player.scoreboardName} is now ${next.name.lowercase()}.") }, true)
//			} else {
//				source.sendFailure(Component.literal("${player.scoreboardName} can't be hit right now."))
//			}
//		}
//		return changed
//	}
//
//	private fun listTuning(ctx: CommandContext<CommandSourceStack>): Int {
//		val lines = MovementTuning.all.map { (name, property) -> "$name = ${property.get()}" }
//		ctx.source.sendSuccess({ Component.literal(lines.joinToString("\n")) }, false)
//		return MovementTuning.all.size
//	}
//
//	private fun showTuning(ctx: CommandContext<CommandSourceStack>): Int {
//		val name = StringArgumentType.getString(ctx, "name")
//		val property = MovementTuning.all[name]
//		if (property == null) {
//			ctx.source.sendFailure(Component.literal("Unknown setting '$name'. Run /cycle tune to see them all."))
//			return 0
//		}
//		ctx.source.sendSuccess({ Component.literal("$name = ${property.get()}") }, false)
//		return 1
//	}
//
//	private fun setTuning(ctx: CommandContext<CommandSourceStack>): Int {
//		val name = StringArgumentType.getString(ctx, "name")
//		val property = MovementTuning.all[name]
//		if (property == null) {
//			ctx.source.sendFailure(Component.literal("Unknown setting '$name'. Run /cycle tune to see them all."))
//			return 0
//		}
//		val value = DoubleArgumentType.getDouble(ctx, "value")
//		property.set(value)
//		ctx.source.sendSuccess(
//			{ Component.literal("$name = $value (live only, copy it into MovementTuning.kt to keep it)") },
//			true
//		)
//		return 1
//	}
//
//	private fun status(ctx: CommandContext<CommandSourceStack>): Int {
//		val lines = MatchManager.describe(ctx.source.server)
//		ctx.source.sendSuccess({ Component.literal(lines.joinToString("\n")) }, false)
//		return 1
//	}
//
//	private fun report(ctx: CommandContext<CommandSourceStack>, error: String?, success: String): Int {
//		if (error != null) {
//			ctx.source.sendFailure(Component.literal(error))
//			return 0
//		}
//		ctx.source.sendSuccess({ Component.literal(success) }, true)
//		return 1
//	}
//}
