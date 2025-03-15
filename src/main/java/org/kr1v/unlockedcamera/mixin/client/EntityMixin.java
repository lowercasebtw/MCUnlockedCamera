package org.kr1v.unlockedcamera.mixin.client;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public float yaw;
    @Shadow public float pitch;

    /**
     * @author a
     * @reason a
     */
    @Overwrite
    public void setAngles(float yaw, float pitch) {
        ((Entity)(Object)this).setYaw(yaw % 360.0F);
        ((Entity)(Object)this).setPitch(pitch % 360.0F);
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
        ((Entity)(Object)this).setPitch(((Entity)(Object)this).getPitch() + f);
        ((Entity)(Object)this).setYaw(((Entity)(Object)this).getYaw() + g);
        ((Entity)(Object)this).prevPitch += f;
        ((Entity)(Object)this).prevYaw += g;
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

        if (pitch < -180) this.pitch = pitch + 360;
        else if (pitch > 180) this.pitch = pitch - 360;
        else this.pitch = pitch;
    }
}