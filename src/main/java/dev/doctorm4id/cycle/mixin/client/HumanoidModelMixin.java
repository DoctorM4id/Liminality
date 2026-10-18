package dev.doctorm4id.cycle.mixin.client;

import dev.doctorm4id.cycle.content.ModContent;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends LivingEntity> {

	@Shadow
	@Final
	public ModelPart rightArm;

	@Shadow
	@Final
	public ModelPart head;

	@Shadow
	@Final
	public ModelPart leftArm;

	@Inject(method = "poseRightArm", at = @At("HEAD"), cancellable = true)
	private void cycle$customRightArm(T livingEntity, CallbackInfo ci) {
/*		if (!(livingEntity.getMainHandItem().getItem() instanceof AttackableWeapon item)) { return; }
		if (livingEntity.getMainArm() != HumanoidArm.RIGHT) return;

		ci.cancel();
		item.applyArmPose(livingEntity, rightArm, head, true);*/

/*		if (livingEntity.getMainArm() == HumanoidArm.RIGHT) {
			if (livingEntity.getMainHandItem().is(ModContent.INSTANCE.getKNIFE())) {
				ci.cancel();

				MixinHelper.INSTANCE.applyKnifePose(livingEntity, rightArm, head, true);
			}
		}*/
	}

/*	@Inject(method = "poseRightArm", at = @At("TAIL"))
	private void cycle$customRightArmTail(T livingEntity, CallbackInfo ci) {
		if (livingEntity.getMainArm() == HumanoidArm.RIGHT) {
			if (livingEntity.getMainHandItem().is(ModContent.INSTANCE.getPISTOL())) {

				MixinHelper.INSTANCE.applyGunPose(livingEntity, rightArm, head, true);
			}
		}
	}*/

	@Inject(method = "poseLeftArm", at = @At("HEAD"), cancellable = true)
	private void cycle$customLeftArm(T livingEntity, CallbackInfo ci) {
/*		if (!(livingEntity.getMainHandItem().getItem() instanceof AttackableWeapon item)) { return; }
		if (livingEntity.getMainArm() != HumanoidArm.LEFT) return;

		ci.cancel();
		item.applyArmPose(livingEntity, leftArm, head, false);*/

/*		if (livingEntity.getMainArm() == HumanoidArm.LEFT) {
			if (livingEntity.getMainHandItem().is(ModContent.INSTANCE.getKNIFE())) {
				ci.cancel();

				MixinHelper.INSTANCE.applyKnifePose(livingEntity, leftArm, head, false);
			}
		}*/
	}

/*	@Inject(method = "poseLeftArm", at = @At("TAIL"))
	private void cycle$customLeftArmTail(T livingEntity, CallbackInfo ci) {
		if (livingEntity.getMainArm() == HumanoidArm.LEFT) {
			if (livingEntity.getMainHandItem().is(ModContent.INSTANCE.getPISTOL())) {

				MixinHelper.INSTANCE.applyGunPose(livingEntity, leftArm, head, false);
			}
		}
	}*/
}
