package org.kr1v.unlockedcamera.client.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracked;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin implements DataTracked, EntityLike {
    @Shadow
    public abstract void setYaw(float yaw);
    @Shadow
    public abstract float getYaw();
    @Shadow
    public abstract float getPitch();
    @Shadow
    public abstract Entity getVehicle();
    @Shadow public float pitch;
    @Shadow public float prevYaw;
    @Shadow public float prevPitch;

    /**
     * @author kr1v
     * @reason prevent pitch from clamping
     */
    @Overwrite
    public void setAngles(float yaw, float pitch) {
        float normalizedPitch = ((pitch + 180) % 360 + 360) % 360 - 180;
        this.setYaw(yaw%360);
        this.setPitch(normalizedPitch);
        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
    }
    /**
     * @author kr1v
     * @reason invert horizontal mouse movement if upside down
     */
    @Overwrite
    public void changeLookDirection(double cursorDeltaX, double cursorDeltaY) {
        float f = (float)cursorDeltaY * 0.15F;
        float g = (float)cursorDeltaX * 0.15F;
        float normalizedPitch = ((pitch % 360) + 360) % 360;
        if (normalizedPitch > 90 && normalizedPitch < 270) {
            this.setYaw(this.getYaw() - g);
            this.prevYaw -= g;
        }
        else {
            this.setYaw(this.getYaw() + g);
            this.prevYaw += g;
        }
        this.setPitch(this.getPitch() + f);
        this.prevPitch += f;
        if (this.getVehicle() != null) {
            this.getVehicle().onPassengerLookAround((Entity)(Object)this);
        }
    }

    /**
     * @author kr1v
     * @reason prevent pitch from clamping
     */
    @Overwrite
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}