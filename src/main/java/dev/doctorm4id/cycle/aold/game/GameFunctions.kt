//package dev.doctorm4id.cycle.aold.game
//
//import dev.doctorm4id.cycle.client.ClientMatchState
//import net.minecraft.world.entity.player.Player
//
//object GameFunctions {
//
//	/**
//	 * True while this player is actively playing a running match, meaning not in the lobby,
//	 * not spectating, and not dead or escaped. Works on both sides: the client reads its synced
//	 * snapshot, the server reads the match manager.
//	 */
//	fun isPlayerAlive(player: Player?): Boolean {
//		if (player == null) return false
//		val snapshot = if (player.level().isClientSide) {
//			ClientMatchState.snapshot
//		} else {
//			MatchManager.snapshotOf(player.uuid)
//		}
//		return snapshot.isPlaying()
//	}
//
//	/**
//	 * True if this player has a playable role and is still in the trial, in any phase. Movement rules follow
//	 * this (see [PlayerSnapshot.hasActiveRole]). Only meaningful for the local player on the client, since the
//	 * client only knows its own role.
//	 */
//	fun hasActiveRole(player: Player?): Boolean {
//		if (player == null) return false
//		val snapshot = if (player.level().isClientSide) {
//			ClientMatchState.snapshot
//		} else {
//			MatchManager.snapshotOf(player.uuid)
//		}
//		return snapshot.hasActiveRole()
//	}
//}
