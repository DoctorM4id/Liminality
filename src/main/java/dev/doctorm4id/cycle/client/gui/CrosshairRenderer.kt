package dev.doctorm4id.cycle.client.gui

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import dev.doctorm4id.cycle.Cycle
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

object CrosshairRenderer {

	private val CROSSHAIR: ResourceLocation = Cycle().idOf("hud/crosshair")
	private val CROSSHAIR_TARGET: ResourceLocation = Cycle().idOf("hud/crosshair_target")
	private val CROSSHAIR_COOLDOWN: ResourceLocation = Cycle().idOf("hud/crosshair_cooldown")

	private val KNIFE_BACKGROUND: ResourceLocation = Cycle().idOf("hud/knife_background")
	private val KNIFE_TARGET: ResourceLocation = Cycle().idOf("hud/knife_attack")
	private val KNIFE_PROGRESS: ResourceLocation = Cycle().idOf("hud/knife_progress")

	private val PISTOL_BACKGROUND: ResourceLocation = Cycle().idOf("hud/pistol_background")
	private val PISTOL_PROGRESS: ResourceLocation = Cycle().idOf("hud/pistol_progress")
	private val PISTOL_ATTACK: ResourceLocation = Cycle().idOf("hud/pistol_attack")

	fun renderCrosshair(client: Minecraft, player: Player, context: GuiGraphics, tick: DeltaTracker) {

		context.pose().pushPose()
		context.pose().translate( context.guiWidth() / 2.0f, context.guiHeight() / 2.0f, 0.0f )

		RenderSystem.defaultBlendFunc()
		RenderSystem.disableBlend()

		var target = false

		val mainHandStack: ItemStack = player.mainHandItem

/*		if (mainHandStack.`is`(ModContent.KNIFE)) {

			val progress = (KnifeItem.getChargeProgress(player, mainHandStack) / 8.0f).coerceIn(0.0f, 1.0f)

			if (KnifeItem.serverAcquireKnifeTarget(player) is EntityHitResult && !player.cooldowns.isOnCooldown(mainHandStack.item) && player.isUsingItem && KnifeItem.isReadyToAttack(player, mainHandStack)) {
				target = true

				context.blitSprite(
					KNIFE_TARGET,
					-5,
					5,
					10,
					7
				)
			} else {
				context.blitSprite(
					KNIFE_BACKGROUND,
					-5,
					5,
					10,
					7
				)

				if (player.isUsingItem) {
					context.blitSprite(
						KNIFE_PROGRESS,
						10,
						7,
						0,
						0,
						-5,
						5,
						(progress * 10).toInt(),
						7
					)
				}
			}

		} else if (mainHandStack.`is`(ModContent.PISTOL)) {

			val partialTick = Minecraft.getInstance().timer.getGameTimeDeltaPartialTick(true)

			val cooldown = player.cooldowns.getCooldownPercent(ModContent.PISTOL, partialTick)
			val progress = (1.0F - cooldown).coerceIn(0.0f, 1.0f)

			if (PistolItem.serverAcquireGunTarget(player) is EntityHitResult && !player.cooldowns.isOnCooldown(mainHandStack.item)) {
				target = true

				context.blitSprite(
					PISTOL_ATTACK,
					-5,
					5,
					10,
					7
				)
			} else {
				context.blitSprite(
					PISTOL_BACKGROUND,
					-5,
					5,
					10,
					7
				)

				context.blitSprite(
					PISTOL_PROGRESS,
					10,
					7,
					0,
					0,
					-5,
					5,
					(progress * 10).toInt(),
					7
				)
			}
		}*/

		context.pose().pushPose()
		context.pose().translate( -1.5f, -1.5f, 0.0f )

		RenderSystem.enableBlend()
		RenderSystem.blendFuncSeparate(
			GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR,
			GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR,
			GlStateManager.SourceFactor.ONE,
			GlStateManager.DestFactor.ZERO,
		)

		if (player.cooldowns.isOnCooldown(mainHandStack.item)) {
			context.blitSprite(
				CROSSHAIR_COOLDOWN,
				0,
				0,
				7,
				7
			)
		} else if (target) {
			context.blitSprite(
				CROSSHAIR_TARGET,
				0,
				0,
				7,
				7
			)
		} else {
			context.blitSprite(
				CROSSHAIR,
				0,
				0,
				7,
				7
			)
		}

		context.pose().popPose()
		context.pose().popPose()

		RenderSystem.defaultBlendFunc()
		RenderSystem.disableBlend()
	}


}
