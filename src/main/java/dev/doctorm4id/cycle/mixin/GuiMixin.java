package dev.doctorm4id.cycle.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.doctorm4id.cycle.client.gui.CrosshairRenderer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Gui.class)
public class GuiMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@WrapMethod(method = "renderCrosshair")
	private void cycle$renderCrosshair(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Operation<Void> original) {
		LocalPlayer player = this.minecraft.player;

		assert player != null;
		CrosshairRenderer.INSTANCE.renderCrosshair(this.minecraft, player, guiGraphics, deltaTracker);
	}

	@WrapMethod(method = "renderExperienceLevel")
	private void cycle$fluffTheExperienceLevel(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Operation<Void> original) {

	}

	@WrapMethod(method = "renderExperienceBar")
	private void cycle$fluffTheExperienceBar(GuiGraphics guiGraphics, int i, Operation<Void> original) {

	}

	@WrapMethod(method = "renderTabList")
	private void cycle$fluffTheTabList(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Operation<Void> original) {

	}

/*	@WrapMethod(method = "renderHearts")
	private void cycle$fluffTheHearts(GuiGraphics guiGraphics, Player player, int i, int j, int k, int l, float f, int m, int n, int o, boolean bl, Operation<Void> original) {

	}

	@WrapMethod(method = "renderFood")
	private void cycle$fluffTheFood(GuiGraphics guiGraphics, Player player, int i, int j, Operation<Void> original) {

	}*/
}
