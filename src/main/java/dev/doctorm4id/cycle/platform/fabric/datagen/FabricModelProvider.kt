package dev.doctorm4id.cycle.platform.fabric.datagen

//? fabric {

import dev.doctorm4id.cycle.Cycle
import dev.doctorm4id.cycle.content.ModContent
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.ModelTemplates

class FabricModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {

	override fun generateBlockStateModels(blockStateModelGenerator: BlockModelGenerators) {
		blockStateModelGenerator.createGenericCube(ModContent.CONCRETE)
		blockStateModelGenerator.createGenericCube(ModContent.CONCRETE_IDK)
	}

	override fun generateItemModels(itemModelGenerator: ItemModelGenerators?) {
		itemModelGenerator?.generateFlatItem(ModContent.KNIFE, ModelTemplates.FLAT_ITEM)
	}

	override fun getName(): String {

		return Cycle.MOD_FRIENDLY_NAME+" Model Provider"
	}
}

//? }
