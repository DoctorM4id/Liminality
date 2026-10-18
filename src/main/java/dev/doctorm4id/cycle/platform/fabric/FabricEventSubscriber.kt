package dev.doctorm4id.cycle.platform.fabric

//? fabric {

import dev.doctorm4id.cycle.event.ExampleEventHandler
/*import dev.doctorm4id.cycle.aold.game.CycleCommands
import dev.doctorm4id.cycle.aold.game.MatchManager
import dev.doctorm4id.cycle.aold.game.MovementRules*/
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.server.level.ServerPlayer
import net.minecraft.tags.DamageTypeTags
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.LivingEntity

class FabricEventSubscriber {

	fun registerEvents() {
		//? != 1.20.1 {
		ServerLivingEntityEvents.AFTER_DAMAGE.register(ServerLivingEntityEvents.AfterDamage { entity: LivingEntity?, source: DamageSource?, baseDamage: Float, damageTaken: Float, blocked: Boolean ->
			if (entity is ServerPlayer && damageTaken > 0) {
				ExampleEventHandler().onPlayerHurt(entity)
			}
		})
		//?}

/*		ServerLivingEntityEvents.ALLOW_DAMAGE.register(ServerLivingEntityEvents.AllowDamage { entity, source, _ ->
			!(entity is ServerPlayer
				&& MatchManager.isPlaying(entity.uuid)
				&& !source.`is`(DamageTypeTags.BYPASSES_INVULNERABILITY))
		})

		ServerTickEvents.END_SERVER_TICK.register(ServerTickEvents.EndTick { server ->
			MatchManager.tick(server)
			MovementRules.tick(server)
		})

		ServerPlayConnectionEvents.JOIN.register(ServerPlayConnectionEvents.Join { handler, _, _ ->
			MatchManager.onPlayerJoin(handler.player)
		})

		ServerPlayConnectionEvents.DISCONNECT.register(ServerPlayConnectionEvents.Disconnect { handler, server ->
			MatchManager.onPlayerLeave(server, handler.player)
		})

		ServerLifecycleEvents.SERVER_STOPPED.register(ServerLifecycleEvents.ServerStopped {
			MatchManager.reset()
		})

		CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher, _, _ ->
			CycleCommands.register(dispatcher)
		})*/
	}
}

//? }
