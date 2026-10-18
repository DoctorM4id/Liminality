package dev.doctorm4id.cycle.content

import dev.doctorm4id.cycle.Cycle
import dev.doctorm4id.m4id.registration.M4idAutoRegistrar
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour

object ModContent : M4idAutoRegistrar {

/*	val REACTOR = "reactor" forBlock { ReactorBlock(BlockBehaviour.Properties.of()
		.noOcclusion()
	) }

	val REACTOR_ENTITY by "reactor-be".forBlockEntity(REACTOR, ::ReactorBlockEntity)


	val KNIFE by "knife" forItem { KnifeItem(Item.Properties()
		.stacksTo(1)
	) }

	val PISTOL by "pistol" forItem { PistolItem(Item.Properties()
		.stacksTo(1)
	) }

	val PLAYER_BODY by "player_body" forEntityType {
		EntityType.Builder
			.of(::PlayerBodyEntity, MobCategory.MISC)
			.sized(1f, 0.25f)
			.clientTrackingRange(128)
			.noSummon()
			.build() }*/

	val KNIFE by "knife" forItem { Item(Item.Properties()
		.stacksTo(1)
	) }

	val CONCRETE by "concrete" forBlock { Block(BlockBehaviour.Properties.of()) }

	val CONCRETE_IDK by "concrete_idk" forBlock { Block(BlockBehaviour.Properties.of()) }

	override fun getId(): String = Cycle.MOD_ID

}
