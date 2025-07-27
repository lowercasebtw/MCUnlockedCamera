package org.kr1v.unlockedcamera.client.mixins;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.kr1v.unlockedcamera.client.UnlockedCameraConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    /**
     * @author kr1v
     * @reason implement swimming upside down movement and looking upside down movement
     */
    @ModifyVariable(method = "travel(Lnet/minecraft/util/math/Vec3d;)V", at = @At("HEAD"), argsOnly = true)
    public Vec3d unlockedCamera$invertMovement(Vec3d movementInput) {
        if (UnlockedCameraConfigManager.getConfig().enabled) {
            PlayerEntity thiz = (PlayerEntity) (Object) this;
            float normalizedPitch = ((thiz.getPitch() + 180) % 360 + 360) % 360 - 180;
            if ((normalizedPitch > 90.0F || normalizedPitch < -90.0F) &&
                    ((UnlockedCameraConfigManager.getConfig().shouldInvertMovementSwimming && thiz.isSwimming()) ||
                            UnlockedCameraConfigManager.getConfig().shouldInvertMovement)) {
                movementInput = movementInput.multiply(-1, 1, -1);
            }
        }

        return movementInput;
    }
}
