package dev.doctorm4id.cycle.mixin;

import dev.doctorm4id.cycle.content.ModContent;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerMixin {
/*	@Inject(method = "handleSetCarriedItem", at = @At("TAIL"))
	private void cycle$pistolEquipCooldown(ServerboundSetCarriedItemPacket packet, CallbackInfo ci) {
		ServerGamePacketListenerImpl connection = (ServerGamePacketListenerImpl) (Object) this;
		ServerPlayer player = connection.player;

		int slot = packet.getSlot();
		if (slot < 0 || slot >= 9) {
			return;
		}

		if (player.getInventory().getItem(slot).is(ModContent.INSTANCE.getPISTOL()) && !player.getCooldowns().isOnCooldown(ModContent.INSTANCE.getPISTOL())) {
			player.getCooldowns().addCooldown(ModContent.INSTANCE.getPISTOL(), 8);
		}
	}*/
}
