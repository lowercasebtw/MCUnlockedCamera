package org.kr1v.unlockedcamera.client.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.kr1v.unlockedcamera.client.UnlockedCameraConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    /**
     * Overwrites the travel method to adjust movement when swimming with inverted pitch.
     * @author a
     * @reason a
     */
    @Overwrite
    public void travel(Vec3d movementInput) {
        if (this.hasVehicle()) {
            super.travel(movementInput);
        } else {
            if (this.isSwimming()) {
                if ((pitch > 90.0F || pitch < -90.0F) && UnlockedCameraConfigManager.getConfig().shouldInvertMovementSwimming) {
                    movementInput = movementInput.multiply(-1, 1, -1);
                }
                double d = getRotationVector().y;
                double e = d < -0.2 ? 0.085 : 0.06;
                if (d <= 0.0 || this.jumping ||
                        !this.getWorld().getFluidState(BlockPos.ofFloored(this.getX(), this.getY() + 0.9, this.getZ())).isEmpty()) {
                    Vec3d vec3d = this.getVelocity();
                    this.setVelocity(vec3d.add(0.0, (d - vec3d.y) * e, 0.0));
                }
            }
            float pitch = this.getPitch();
            if ((pitch > 90.0F || pitch < -90.0F) && UnlockedCameraConfigManager.getConfig().shouldInvertMovement) {
                movementInput = movementInput.multiply(-1, 1, -1);
            }

            if (((PlayerEntity)(Object)this).getAbilities().flying) {
                double d = this.getVelocity().y;
                super.travel(movementInput);
                this.setVelocity(this.getVelocity().withAxis(Direction.Axis.Y, d * 0.6));
            } else {
                super.travel(movementInput);
            }
        }
    }
}
