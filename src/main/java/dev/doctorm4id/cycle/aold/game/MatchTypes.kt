//package dev.doctorm4id.cycle.aold.game
//
///** What a player is doing in the current match. [NONE] means "not part of the match". */
//enum class Role {
//	NONE, SURVIVOR, KILLER, SPECTATOR;
//
//	/** True for roles that actually play the trial (as opposed to watching it). */
//	val isPlayable: Boolean
//		get() = this == SURVIVOR || this == KILLER
//}
//
///** A survivor's condition. The killer is always [HEALTHY] and never changes state. */
//enum class HealthState {
//	HEALTHY, INJURED, DOWNED, CARRIED, HOOKED, DEAD, ESCAPED;
//
//	/** Still inside the trial (neither dead nor escaped). */
//	val isInPlay: Boolean
//		get() = this != DEAD && this != ESCAPED
//}
//
//enum class MatchPhase {
//	LOBBY, STARTING, ACTIVE, ENDGAME, ENDED;
//
//	val isRunning: Boolean
//		get() = this == STARTING || this == ACTIVE || this == ENDGAME
//}
//
///** How fast one player is allowed to move right now, in blocks per second. */
//data class MovementSpec(val runSpeed: Double, val crouchSpeed: Double)
//
///**
// * Everything one client needs to know about itself. Server builds it, [dev.doctorm4id.cycle.network.MatchSyncMsg]
// * carries it, and [dev.doctorm4id.cycle.client.ClientMatchState] stores it.
// */
//data class PlayerSnapshot(val phase: MatchPhase, val role: Role, val health: HealthState) {
//
//	/** In a running match, on the playing side, and still in the trial. */
//	fun isPlaying(): Boolean = phase.isRunning && role.isPlayable && health.isInPlay
//
//	/**
//	 * Has a playable role and is still in the trial, in any phase. Movement rules (no sprint, no jump, role speeds)
//	 * follow this instead of [isPlaying], so you can test movement in the lobby just by assigning a role.
//	 */
//	fun hasActiveRole(): Boolean = role.isPlayable && health.isInPlay
//
//	companion object {
//		val NONE = PlayerSnapshot(MatchPhase.LOBBY, Role.NONE, HealthState.HEALTHY)
//	}
//}
