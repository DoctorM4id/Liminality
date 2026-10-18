package dev.doctorm4id.cycle.mixin;

import dev.doctorm4id.cycle.content.ModContent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// CLIENT

@Mixin(ServerboundSetCarriedItemPacket.class)
public class ServerboundSetCarriedItemPacketMixin {

/*	@Inject(method = "<init>(I)V", at = @At("TAIL"))
	private void cycle$equipCooldown(int slot, CallbackInfo ci) {
		Minecraft minecraft = Minecraft.getInstance();

		if (minecraft.player == null || slot < 0 || slot >= 9) {
			return;
		}

		if (minecraft.player.getInventory().getItem(slot).is(ModContent.INSTANCE.getPISTOL()) && !minecraft.player.getCooldowns().isOnCooldown(ModContent.INSTANCE.getPISTOL())) {
			minecraft.player.getCooldowns().addCooldown(ModContent.INSTANCE.getPISTOL(), 8);
		}
	}*/
}
