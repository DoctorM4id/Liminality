package dev.doctorm4id.cycle.mixin.client;

import dev.doctorm4id.cycle.content.ModContent;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

	@Inject(
			method = "getArmPose",
			at = @At("TAIL"),
			cancellable = true
	)
	private static void cycle$customArmPose(AbstractClientPlayer player, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
/*		if (player.getItemInHand(hand).is(ModContent.INSTANCE.getKNIFE())) {
			cir.setReturnValue(HumanoidModel.ArmPose.EMPTY);
		}*/
	}

}
