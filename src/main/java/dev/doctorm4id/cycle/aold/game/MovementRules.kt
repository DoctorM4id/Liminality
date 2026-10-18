//package dev.doctorm4id.cycle.aold.game
//
//import dev.doctorm4id.cycle.Cycle
//import net.minecraft.resources.ResourceLocation
//import net.minecraft.server.MinecraftServer
//import net.minecraft.server.level.ServerPlayer
//import net.minecraft.world.entity.ai.attributes.AttributeInstance
//import net.minecraft.world.entity.ai.attributes.AttributeModifier
//import net.minecraft.world.entity.ai.attributes.Attributes
//
///**
// * Server-side movement rules. The server decides how fast each player may go and writes it into their
// * attributes as transient modifiers. Minecraft syncs a player's own attributes to their client, so the
// * client moves at the same speed and never gets rubber-banded, and a modified client can't go faster.
// *
// * Speeds come from [MatchManager.movementSpec], tuned in [MovementTuning]. Sprinting is blocked in
// * [dev.doctorm4id.cycle.mixin.LivingEntityMixin].
// */
//object MovementRules {
//
//	private val SPEED_ID: ResourceLocation = Cycle().idOf("role_speed")
//	private val SNEAK_ID: ResourceLocation = Cycle().idOf("role_crouch_speed")
//	private val JUMP_ID: ResourceLocation = Cycle().idOf("role_no_jump")
//
//	fun tick(server: MinecraftServer) {
//		for (player in server.playerList.players) {
//			apply(player, MatchManager.movementSpec(player.uuid))
//		}
//	}
//
//	private fun apply(player: ServerPlayer, spec: MovementSpec?) {
//		val speed = player.getAttribute(Attributes.MOVEMENT_SPEED)
//		val sneak = player.getAttribute(Attributes.SNEAKING_SPEED)
//		val jump = player.getAttribute(Attributes.JUMP_STRENGTH)
//
//		// Not in a role (or out of the trial): hand the player back to vanilla.
//		if (spec == null) {
//			speed?.removeModifier(SPEED_ID)
//			sneak?.removeModifier(SNEAK_ID)
//			jump?.removeModifier(JUMP_ID)
//			return
//		}
//
//		if (speed != null) {
//			val target = spec.runSpeed / MovementTuning.BLOCKS_PER_SECOND_PER_ATTRIBUTE_POINT
//			setModifier(speed, SPEED_ID, target - speed.baseValue, AttributeModifier.Operation.ADD_VALUE)
//		}
//
//		if (sneak != null) {
//			// Crouching multiplies movement by the sneaking-speed attribute (0.3 in vanilla).
//			val ratio = if (spec.runSpeed > 1.0E-6) (spec.crouchSpeed / spec.runSpeed).coerceIn(0.0, 1.0) else 1.0
//			setModifier(sneak, SNEAK_ID, ratio - sneak.baseValue, AttributeModifier.Operation.ADD_VALUE)
//		}
//
//		// Nobody jumps in this game. Vaulting will be its own action.
//		if (jump != null) {
//			setModifier(jump, JUMP_ID, -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
//		}
//	}
//
//	/** Only touches the attribute when the value actually changed, so it doesn't spam sync packets. */
//	private fun setModifier(
//		attribute: AttributeInstance,
//		id: ResourceLocation,
//		amount: Double,
//		operation: AttributeModifier.Operation
//	) {
//		val existing = attribute.getModifier(id)
//		if (existing != null && existing.amount() == amount && existing.operation() == operation) return
//
//		attribute.removeModifier(id)
//		attribute.addTransientModifier(AttributeModifier(id, amount, operation))
//	}
//}
