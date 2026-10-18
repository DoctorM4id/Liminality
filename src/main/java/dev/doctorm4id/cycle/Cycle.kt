package dev.doctorm4id.cycle

import dev.doctorm4id.cycle.content.ModContent
//import dev.doctorm4id.cycle.aold.game.GameFunctions
//import dev.doctorm4id.cycle.network.MatchSyncMsg
import dev.doctorm4id.cycle.platform.Platform
import org.slf4j.Logger
import org.slf4j.LoggerFactory

//? fabric {
import dev.doctorm4id.cycle.platform.fabric.FabricPlatform
import dev.doctorm4id.m4id.M4id
import dev.doctorm4id.m4id.registration.M4idRegistrar
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation

//? }

//? neoforge {
/*import dev.doctorm4id.cycle.platform.neoforge.NeoforgePlatform
*///? }

@SuppressWarnings("LoggingSimilarMessage")
class Cycle {

	companion object {
		const val MOD_ID: String = /*$ mod_id*/"cycle";
		const val MOD_VERSION: String =  /*$ mod_version*/"0.1.0";
		const val MOD_FRIENDLY_NAME: String =  /*$ mod_name*/"Cycle";

		@JvmField
		var LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

		private val PLATFORM: Platform = Cycle().createPlatformInstance()

/*		fun isPlayerAlive(): Boolean {
			return GameFunctions.isPlayerAlive(Minecraft.getInstance().player)
		}*/
	}

	fun onInitialize() {
		LOGGER.info( "Initializing {} on {}", MOD_ID, xplat().loader() )
		LOGGER.debug( "{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME )

		M4idRegistrar.doRegistrationsFor(ModContent)

		//M4id.Message.registerClientMessage(idOf("match_sync"), MatchSyncMsg.serializer())
	}

	fun onInitializeClient() {
		LOGGER.info("Initializing {} Client on {}", MOD_ID, xplat().loader())
		LOGGER.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME)
	}

	fun idOf(path: String): ResourceLocation {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path)
	}

	fun xplat(): Platform { return PLATFORM }

	private fun createPlatformInstance(): Platform {
		//? fabric {
		return FabricPlatform()
		//?} neoforge {
		/*return NeoforgePlatform()
		*///?}
	}
}
