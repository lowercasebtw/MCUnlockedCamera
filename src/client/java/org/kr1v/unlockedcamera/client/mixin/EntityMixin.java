package org.kr1v.unlockedcamera.client.mixin;

import net.minecraft.entity.Entity;
import org.kr1v.unlockedcamera.client.UnlockedCameraConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public float pitch;

    /**
     * @author a
     * @reason a
     */
    @Overwrite
    public void setAngles(float yaw, float pitch) {
        ((Entity)(Object)this).setYaw(yaw);
        ((Entity)(Object)this).setPitch(pitch);
        ((Entity)(Object)this).prevYaw = ((Entity)(Object)this).getYaw();
        ((Entity)(Object)this).prevPitch = ((Entity)(Object)this).getPitch();
    }
    /**
     * @author a
     * @reason a
     */
    @Overwrite
    public void changeLookDirection(double cursorDeltaX, double cursorDeltaY) {
        float f = (float)cursorDeltaY * 0.15F;
        float g = (float)cursorDeltaX * 0.15F;
        if ((pitch < -90 || pitch > 90) && UnlockedCameraConfigManager.getConfig().shouldInvertMouse) ((Entity)(Object)this).setYaw(((Entity)(Object)this).getYaw() - g);
        else ((Entity)(Object)this).setYaw(((Entity)(Object)this).getYaw() + g);
        if ((pitch < -90 || pitch > 90) && UnlockedCameraConfigManager.getConfig().shouldInvertMouse) ((Entity)(Object)this).prevYaw -= g;
        else ((Entity)(Object)this).prevYaw += g;
        ((Entity)(Object)this).setPitch(((Entity)(Object)this).getPitch() + f);
        ((Entity)(Object)this).prevPitch += f;
        if (((Entity) (Object) this).getVehicle() != null) {
            ((Entity) (Object) this).getVehicle().onPassengerLookAround(((Entity)(Object)this));
        }
    }

    /**
     * @author kr1v
     * @reason prevent pitch from clamping
     */
    @Overwrite
    public void setPitch(float pitch) {

        /*if (pitch < -180) this.pitch = pitch + 360;
        else if (pitch > 180) this.pitch = pitch - 360;
        else */this.pitch = pitch;
    }
}