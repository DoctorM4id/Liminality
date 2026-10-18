//package dev.doctorm4id.cycle.aold.game
//
//import dev.doctorm4id.cycle.network.MatchSyncMsg
//import net.minecraft.network.chat.Component
//import net.minecraft.server.MinecraftServer
//import net.minecraft.server.level.ServerPlayer
//import java.util.UUID
//import kotlin.collections.iterator
//
///**
// * Server-side source of truth for the match: who plays which role, what shape each survivor is in,
// * and which phase the match is in. Clients never decide any of this, they only receive snapshots.
// *
// * Only touch this from the server thread. Client code reads [dev.doctorm4id.cycle.client.ClientMatchState] instead.
// */
//object MatchManager {
//
//	private class Entry(
//		var role: Role,
//		var health: HealthState = HealthState.HEALTHY,
//		/** Server ticks of hit haste left. Only ever set by [applyHit]. */
//		var hasteTicks: Int = 0
//	)
//
//	private val roster = LinkedHashMap<UUID, Entry>()
//
//	var phase: MatchPhase = MatchPhase.LOBBY
//		private set
//
//	private var ticksLeft = 0
//
//	// ---- queries ----
//
//	fun snapshotOf(uuid: UUID): PlayerSnapshot {
//		val entry = roster[uuid] ?: return PlayerSnapshot(phase, Role.NONE, HealthState.HEALTHY)
//		return PlayerSnapshot(phase, entry.role, entry.health)
//	}
//
//	/** In a running match, playing, and still in the trial. */
//	fun isPlaying(uuid: UUID): Boolean = snapshotOf(uuid).isPlaying()
//
//	private fun count(role: Role): Int = roster.values.count { it.role == role }
//
//	/**
//	 * How fast this player may move right now, or null if they have no active role and should move like vanilla.
//	 * [MovementRules] applies the result to the player's attributes every tick.
//	 */
//	fun movementSpec(uuid: UUID): MovementSpec? {
//		val entry = roster[uuid] ?: return null
//		if (!entry.role.isPlayable || !entry.health.isInPlay) return null
//
//		if (entry.role == Role.KILLER) {
//			val carrying = roster.values.any { it.role == Role.SURVIVOR && it.health == HealthState.CARRIED }
//			val speed = MovementTuning.killerSpeed * (if (carrying) MovementTuning.killerCarryFactor else 1.0)
//			// Killers can't crouch, so crouching gives them no speed change.
//			return MovementSpec(speed, speed)
//		}
//
//		return when (entry.health) {
//			HealthState.DOWNED -> MovementSpec(MovementTuning.survivorCrawlSpeed, MovementTuning.survivorCrawlSpeed)
//			// Carried and hooked survivors are held in place.
//			HealthState.CARRIED, HealthState.HOOKED -> MovementSpec(0.0, 0.0)
//			else -> {
//				val hasted = entry.hasteTicks > 0
//				val run = MovementTuning.survivorSpeed * (if (hasted) 1.0 + MovementTuning.hitHasteBonus else 1.0)
//				MovementSpec(run, minOf(MovementTuning.survivorCrouchSpeed, run))
//			}
//		}
//	}
//
//	fun describe(server: MinecraftServer): List<String> {
//		val lines = mutableListOf("Phase: ${phase.name.lowercase()}")
//		if (roster.isEmpty()) lines += "Nobody has a role yet."
//		for ((uuid, entry) in roster) {
//			val name = server.playerList.getPlayer(uuid)?.scoreboardName ?: uuid.toString()
//			val health = if (entry.role == Role.SURVIVOR) " (${entry.health.name.lowercase()})" else ""
//			lines += "$name: ${entry.role.name.lowercase()}$health"
//		}
//		return lines
//	}
//
//	// ---- roster and match control (return an error message, or null on success) ----
//
//	fun assignRole(server: MinecraftServer, player: ServerPlayer, role: Role): String? {
//		if (phase == MatchPhase.STARTING) return "Roles can't change during the countdown."
//
//		val alreadyKiller = roster[player.uuid]?.role == Role.KILLER
//		if (role == Role.KILLER && !alreadyKiller && count(Role.KILLER) >= GameConfig.MAX_KILLERS) {
//			return "There can only be ${GameConfig.MAX_KILLERS} killer(s)."
//		}
//
//		if (role == Role.NONE) roster.remove(player.uuid) else roster[player.uuid] = Entry(role)
//
//		syncAll(server)
//		checkEndConditions(server)
//		return null
//	}
//
//	fun start(server: MinecraftServer): String? {
//		if (phase != MatchPhase.LOBBY) return "A match is already running."
//		if (count(Role.KILLER) < 1) return "Assign a killer first."
//		if (count(Role.SURVIVOR) < GameConfig.MIN_SURVIVORS) {
//			return "Need at least ${GameConfig.MIN_SURVIVORS} survivor(s)."
//		}
//
//		roster.values.forEach { it.health = HealthState.HEALTHY; it.hasteTicks = 0 }
//		phase = MatchPhase.STARTING
//		ticksLeft = GameConfig.START_COUNTDOWN_TICKS
//		syncAll(server)
//		return null
//	}
//
//	fun stop(server: MinecraftServer): String? {
//		if (phase == MatchPhase.LOBBY) return "No match is running."
//		announce(server, "The match was stopped.", actionBar = false)
//		returnToLobby(server)
//		return null
//	}
//
//	// ---- survivor health ----
//
//	/**
//	 * The one code path a killer's attack will use. Healthy becomes injured, injured becomes downed,
//	 * anything else is unaffected. Returns the new state, or null if nothing changed.
//	 */
//	fun applyHit(server: MinecraftServer, victim: ServerPlayer): HealthState? {
//		if (phase != MatchPhase.ACTIVE && phase != MatchPhase.ENDGAME) return null
//		val entry = roster[victim.uuid] ?: return null
//		if (entry.role != Role.SURVIVOR) return null
//
//		val next = when (entry.health) {
//			HealthState.HEALTHY -> HealthState.INJURED
//			HealthState.INJURED -> HealthState.DOWNED
//			else -> return null
//		}
//		setHealthState(server, victim, next)
//
//		// Being hit (but not downed) gives a short speed burst so the survivor can get away.
//		if (next == HealthState.INJURED) {
//			entry.hasteTicks = (MovementTuning.hitHasteSeconds * 20).toInt()
//		}
//		return next
//	}
//
//	/** Single mutation point for survivor health. Returns false if the player isn't a survivor in the roster. */
//	fun setHealthState(server: MinecraftServer, player: ServerPlayer, state: HealthState): Boolean {
//		val entry = roster[player.uuid] ?: return false
//		if (entry.role != Role.SURVIVOR) return false
//
//		entry.health = state
//		// Haste only makes sense for a survivor who is up and running.
//		if (state != HealthState.HEALTHY && state != HealthState.INJURED) entry.hasteTicks = 0
//		syncAll(server)
//		checkEndConditions(server)
//		return true
//	}
//
//	// ---- per-tick and lifecycle ----
//
//	fun tick(server: MinecraftServer) {
//		for (entry in roster.values) {
//			if (entry.hasteTicks > 0) entry.hasteTicks--
//		}
//
//		when (phase) {
//			MatchPhase.STARTING -> {
//				ticksLeft--
//				if (ticksLeft <= 0) {
//					phase = MatchPhase.ACTIVE
//					announce(server, "The trial begins.", actionBar = true)
//					syncAll(server)
//					checkEndConditions(server)
//				} else if (ticksLeft % 20 == 0) {
//					announce(server, "Starting in ${ticksLeft / 20}", actionBar = true)
//				}
//			}
//			MatchPhase.ENDED -> {
//				ticksLeft--
//				if (ticksLeft <= 0) returnToLobby(server)
//			}
//			else -> {}
//		}
//	}
//
//	fun onPlayerJoin(player: ServerPlayer) {
//		sync(player)
//	}
//
//	/** A survivor who leaves mid-match counts as dead. A killer who leaves ends the trial. */
//	fun onPlayerLeave(server: MinecraftServer, player: ServerPlayer) {
//		val entry = roster[player.uuid] ?: return
//
//		if (!phase.isRunning) {
//			roster.remove(player.uuid)
//			return
//		}
//
//		if (entry.role == Role.SURVIVOR) entry.health = HealthState.DEAD else roster.remove(player.uuid)
//		syncAll(server)
//		checkEndConditions(server)
//	}
//
//	/** Called when the server shuts down so state doesn't leak into the next world (singleplayer reuses the JVM). */
//	fun reset() {
//		roster.clear()
//		phase = MatchPhase.LOBBY
//		ticksLeft = 0
//	}
//
//	// ---- internals ----
//
//	private fun checkEndConditions(server: MinecraftServer) {
//		if (phase != MatchPhase.ACTIVE && phase != MatchPhase.ENDGAME) return
//
//		if (count(Role.KILLER) == 0) {
//			endMatch(server, "The killer left the trial.")
//			return
//		}
//
//		val survivors = roster.values.filter { it.role == Role.SURVIVOR }
//		if (survivors.none { it.health.isInPlay }) {
//			val escaped = survivors.count { it.health == HealthState.ESCAPED }
//			endMatch(
//				server,
//				if (escaped == 0) "No one escaped." else "$escaped of ${survivors.size} survivor(s) escaped."
//			)
//		}
//	}
//
//	private fun endMatch(server: MinecraftServer, message: String) {
//		phase = MatchPhase.ENDED
//		ticksLeft = GameConfig.END_SCREEN_TICKS
//		announce(server, message, actionBar = false)
//		syncAll(server)
//	}
//
//	private fun returnToLobby(server: MinecraftServer) {
//		phase = MatchPhase.LOBBY
//		ticksLeft = 0
//		roster.values.forEach { it.health = HealthState.HEALTHY; it.hasteTicks = 0 }
//		syncAll(server)
//	}
//
//	private fun announce(server: MinecraftServer, text: String, actionBar: Boolean) {
//		val message = Component.literal(text)
//		for (uuid in roster.keys) {
//			server.playerList.getPlayer(uuid)?.sendSystemMessage(message, actionBar)
//		}
//	}
//
//	private fun sync(player: ServerPlayer) {
//		MatchSyncMsg(snapshotOf(player.uuid)).sendToClient(player)
//	}
//
//	private fun syncAll(server: MinecraftServer) {
//		for (player in server.playerList.players) sync(player)
//	}
//}
