package org.kr1v.unlockedcamera.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import org.joml.Math;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin<T extends BipedEntityRenderState> extends EntityModel<T> implements ModelWithArms, ModelWithHead {
    protected BipedEntityModelMixin(ModelPart root) {
        super(root);
    }

    @Unique
    private static final float pi = Math.PI_f;
    /**
     * @author kr1v
     * @reason prevent arms going crazy when punching
     */
    @ModifyExpressionValue(method = "animateArms",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/model/ModelPart;pitch:F",
                    opcode = Opcodes.GETFIELD
            ))
    protected float Injected(float pitch) {
        float normalizedPitch = ((pitch + pi) % (2 * pi) + (2 * pi)) % (2 * pi) - pi;
        if (normalizedPitch < -pi/2) return -pi-normalizedPitch;
        else if (normalizedPitch > pi/2) return pi-normalizedPitch;
        return normalizedPitch;
    }

    // functions for extending stuff

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {

    }

    /**
     * Gets the head model part.
     *
     * @return the head
     */
    @Override
    public ModelPart getHead() {
        return null;
    }

}
