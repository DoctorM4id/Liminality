package dev.doctorm4id.cycle.mixin.restrictions;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.doctorm4id.cycle.Cycle;
//import dev.doctorm4id.cycle.client.ClientMatchState;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KeyMapping.class)
public class KeyBindingMixin {

	@Unique
	private boolean shouldSuppressKey() {
		Options opts = Minecraft.getInstance().options;
		KeyMapping self = (KeyMapping) (Object) this;

		//if (self == opts.keyShift && ClientMatchState.INSTANCE.isActiveKiller()) return true;

/*		if (Cycle.Companion.isPlayerAlive()) {
			if (self == opts.keyChat || self == opts.keyCommand) {
				LocalPlayer player = Minecraft.getInstance().player;
				return player == null || !player.hasPermissions(2);
			}

			return self == opts.keySwapOffhand ||
				self == opts.keyJump ||
				self == opts.keyTogglePerspective ||
				self == opts.keyDrop ||
				self == opts.keyAdvancements;
		}*/
		return false;
	}

	@ModifyReturnValue(method = "consumeClick", at = @At("RETURN"))
	private boolean cycle$restrainWasPressedKeys(boolean original) {
		if (this.shouldSuppressKey()) return false;
		else return original;
	}

	@ModifyReturnValue(method = "isDown", at = @At("RETURN"))
	private boolean cycle$restrainIsPressedKeys(boolean original) {
		if (this.shouldSuppressKey()) return false;
		else return original;
	}

	@ModifyReturnValue(method = "matches", at = @At("RETURN"))
	private boolean cycle$restrainMatchesKey(boolean original) {
		if (this.shouldSuppressKey()) return false;
		else return original;
	}

}
