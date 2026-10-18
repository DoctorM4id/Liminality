package dev.doctorm4id.cycle.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	/**
	 * Players with a role can't sprint, on either side. This covers the sprint key, double-tapping forward,
	 * and a client that asks the server to sprint. Survivors and the killer just run at their role speed.
	 */
	@Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
	private void cycle$noSprinting(boolean sprinting, CallbackInfo ci) {
		if (!sprinting) return;
		if (!((Object) this instanceof Player player)) return;

		//if (GameFunctions.INSTANCE.hasActiveRole(player)) ci.cancel();
	}
}
