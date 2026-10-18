package dev.doctorm4id.cycle.platform.fabric

//? fabric {

import dev.doctorm4id.cycle.Cycle
import dev.doctorm4id.cycle.content.ModContent
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry

@Entrypoint("main")
class FabricEntrypoint : ModInitializer {

	override fun onInitialize() {
		Cycle().onInitialize()
		FabricEventSubscriber().registerEvents()

/*		FabricDefaultAttributeRegistry.register(
			ModContent.PLAYER_BODY,
			PlayerBodyEntity.createAttributes()
		)*/
	}
}

//? }
