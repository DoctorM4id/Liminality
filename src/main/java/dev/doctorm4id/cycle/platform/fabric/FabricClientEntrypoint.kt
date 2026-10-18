package dev.doctorm4id.cycle.platform.fabric

//? fabric {

import dev.doctorm4id.cycle.Cycle
//import dev.doctorm4id.cycle.client.ClientMatchState
//? <= 1.20.1 {
/*import dev.doctorm4id.cycle.core.ModRenderLayers
*///? }
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents

@Entrypoint("client")
class FabricClientEntrypoint : ClientModInitializer {

	override fun onInitializeClient() {
		Cycle().onInitializeClient()

/*		ClientPlayConnectionEvents.DISCONNECT.register(ClientPlayConnectionEvents.Disconnect { _, _ ->
			ClientMatchState.clear()
		})*/
	}
}

//? }
