//package dev.doctorm4id.cycle.client
//
//import dev.doctorm4id.cycle.aold.game.PlayerSnapshot
//import dev.doctorm4id.cycle.aold.game.Role
//
///** What this client last heard from the server about its own role and the match. Display and input only, never authority. */
//object ClientMatchState {
//
//	@Volatile
//	var snapshot: PlayerSnapshot = PlayerSnapshot.NONE
//		private set
//
//	fun update(snapshot: PlayerSnapshot) {
//		this.snapshot = snapshot
//	}
//
//	/** This client is the killer and still in the trial (any phase). Used to block crouching. */
//	fun isActiveKiller(): Boolean = snapshot.role == Role.KILLER && snapshot.hasActiveRole()
//
//	/** Called on disconnect so a role from one world never carries into the next. */
//	fun clear() {
//		snapshot = PlayerSnapshot.NONE
//	}
//}
