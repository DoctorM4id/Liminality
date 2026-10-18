package dev.doctorm4id.cycle.mixin.restrictions;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.doctorm4id.cycle.Cycle;
import net.minecraft.client.CameraType;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Options.class)
public abstract class GameOptionsMixin {

	@Shadow
	public abstract CameraType getCameraType();

	@ModifyReturnValue(method = "getCameraType", at = @At("RETURN"))
	public CameraType cycle$getCameraType(CameraType original) {
/*		if (Cycle.Companion.isPlayerAlive()) {
			return CameraType.THIRD_PERSON_BACK;
		}*/

		return original;
	}

	@ModifyReturnValue(method = "gamma", at = @At("RETURN"))
    public OptionInstance<Double> cycle$getGamma(OptionInstance<Double> original) {
        original.set(0d);
        return original;
    }
}
