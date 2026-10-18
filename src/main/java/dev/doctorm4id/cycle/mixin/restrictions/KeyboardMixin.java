package dev.doctorm4id.cycle.mixin.restrictions;

import dev.doctorm4id.cycle.Cycle;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyboardHandler.class)
public class KeyboardMixin {

	@Inject(method = "handleDebugKeys(I)Z", at = @At("HEAD"), cancellable = true)
	private void cycle$disableF3(int key, CallbackInfoReturnable<Boolean> cir) {
/*		if (Cycle.Companion.isPlayerAlive()) {
			assert Minecraft.getInstance().player != null;

			if (!Minecraft.getInstance().player.hasPermissions(2)) {
				cir.setReturnValue(true);
			}
		}*/
	}
}
