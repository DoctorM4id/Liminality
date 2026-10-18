//package dev.doctorm4id.cycle.network
//
//import dev.doctorm4id.cycle.client.ClientMatchState
//import dev.doctorm4id.cycle.aold.game.HealthState
//import dev.doctorm4id.cycle.aold.game.MatchPhase
//import dev.doctorm4id.cycle.aold.game.PlayerSnapshot
//import dev.doctorm4id.cycle.aold.game.Role
//import dev.doctorm4id.m4id.message.M4idMsg
//import kotlinx.serialization.Serializable
//
///**
// * Server to client: "here is your role, your health state and the match phase".
// * Fields are plain ints (enum ordinals) so the message doesn't depend on how the encoder treats enums.
// */
//@Serializable
//class MatchSyncMsg(val phase: Int, val role: Int, val health: Int) : M4idMsg() {
//
//	constructor(snapshot: PlayerSnapshot) : this(
//		snapshot.phase.ordinal,
//		snapshot.role.ordinal,
//		snapshot.health.ordinal
//	)
//
//	override fun onClientReceived() {
//		ClientMatchState.update(
//			PlayerSnapshot(
//				MatchPhase.entries.getOrElse(phase) { MatchPhase.LOBBY },
//				Role.entries.getOrElse(role) { Role.NONE },
//				HealthState.entries.getOrElse(health) { HealthState.HEALTHY }
//			)
//		)
//	}
//}
