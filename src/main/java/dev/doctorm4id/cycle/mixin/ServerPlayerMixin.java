package dev.doctorm4id.cycle.mixin;

//import dev.doctorm4id.cycle.aold.game.MatchManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

	/**
	 * Players in a running match never die the vanilla way (survivor health is a state machine instead).
	 * This is a safety net for damage that gets past the damage event, such as void or /kill.
	 * Outside a match, death behaves normally.
	 */
	@Inject(method = "die", at = @At("HEAD"), cancellable = true)
	private void cycle$replaceDeath(DamageSource damageSource, CallbackInfo ci) {
		ServerPlayer victim = (ServerPlayer) (Object) this;

		//if (!MatchManager.INSTANCE.isPlaying(victim.getUUID())) return;

		victim.setHealth(victim.getMaxHealth());
		victim.deathTime = 0;

		ci.cancel();
	}

}
