package dev.doctorm4id.cycle.mixin.restrictions;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

	@ModifyReturnValue(method = "shouldShowName*", at = @At("RETURN"))
	private boolean cycle$hidePlayerNameTag(boolean original) {
		return false;
	}
}
