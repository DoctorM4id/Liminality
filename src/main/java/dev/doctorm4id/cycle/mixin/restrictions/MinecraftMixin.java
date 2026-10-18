package dev.doctorm4id.cycle.mixin.restrictions;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.doctorm4id.cycle.Cycle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public class MinecraftMixin {

	@Unique
	@Nullable
	public LocalPlayer player;

	@WrapOperation(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V", ordinal = 1))
	private void cycle$replaceInventoryScreen(Minecraft instance, Screen screen, Operation<Void> original) {
/*		if (!Cycle.Companion.isPlayerAlive()) {
			original.call(instance,screen);
		}*/

		// custom screen.

		original.call(instance,screen);
	}
}
