//package dev.doctorm4id.cycle.aold.game
//
//import kotlin.reflect.KMutableProperty0
//
///**
// * Movement numbers, in blocks per second (vanilla walking is about 4.3, sprinting about 5.6).
// *
// * These are plain variables on purpose: `/cycle tune <name> <value>` changes them live on the server,
// * so you can tweak how the game feels without restarting. Live changes are not saved, so once you like a
// * value, copy it in here.
// *
// * The defaults are rough guesses based on the real game's proportions, meant to be tuned by feel.
// */
//object MovementTuning {
//
//	/** Survivor run speed. The real game has no sprint, you simply run. */
//	var survivorSpeed = 4.0
//
//	/** Survivor crouch speed (absolute, not a fraction of run speed). */
//	var survivorCrouchSpeed = 2.0
//
//	/** Survivor crawl speed while downed. */
//	var survivorCrawlSpeed = 0.7
//
//	/** Killer speed, about 115% of a survivor. Killers cannot crouch. */
//	var killerSpeed = 4.6
//
//	/** Killer speed multiplier while carrying a survivor. */
//	var killerCarryFactor = 0.92
//
//	/** Extra survivor speed right after being hit (0.5 means +50%). */
//	var hitHasteBonus = 0.5
//
//	/** How long that burst lasts. */
//	var hitHasteSeconds = 2.0
//
//	/** Everything above by name, for the tune command. */
//	val all: Map<String, KMutableProperty0<Double>> = linkedMapOf(
//		"survivorSpeed" to ::survivorSpeed,
//		"survivorCrouchSpeed" to ::survivorCrouchSpeed,
//		"survivorCrawlSpeed" to ::survivorCrawlSpeed,
//		"killerSpeed" to ::killerSpeed,
//		"killerCarryFactor" to ::killerCarryFactor,
//		"hitHasteBonus" to ::hitHasteBonus,
//		"hitHasteSeconds" to ::hitHasteSeconds
//	)
//
//	/**
//	 * Vanilla ground speed is 0.1 on the movement speed attribute, which works out to about 4.317 blocks per second.
//	 * Used to turn the blocks-per-second numbers above into attribute values.
//	 */
//	const val BLOCKS_PER_SECOND_PER_ATTRIBUTE_POINT = 43.17
//}
