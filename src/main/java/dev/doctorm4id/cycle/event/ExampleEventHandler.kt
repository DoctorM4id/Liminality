package dev.doctorm4id.cycle.event

import dev.doctorm4id.cycle.Cycle
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import java.util.Objects

class ExampleEventHandler {

	fun onPlayerHurt(player: ServerPlayer) {
		//? if > 1.20.1 {
		// MinecraftServer.pvp is private... only here to test ATs/AWs
		//? if < 26.1.2
		val pvp = Objects.requireNonNull<MinecraftServer>(player.getServer()).pvp
		//? if >= 26.1.2
		//boolean pvp = Objects.requireNonNull(player.level()).isPvpAllowed();
		if (pvp) {
			Cycle.LOGGER.info("{} took damage. PVP is allowed.", player.displayName)
		} else {
			Cycle.LOGGER.info("{} took damage. PVP is disallowed.", player.displayName)
		}
		//?}
	}
}
