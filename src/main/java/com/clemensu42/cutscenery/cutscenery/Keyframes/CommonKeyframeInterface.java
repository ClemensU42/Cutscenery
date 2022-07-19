package com.clemensu42.cutscenery.cutscenery.Keyframes;

import net.minecraft.util.math.Vec3d;

public interface CommonKeyframeInterface {
    public abstract void setObjectPosition(Vec3d position);
    public abstract Vec3d getObjectPosition();

    public abstract void setObjectRotation(float pitch, float yaw, float roll);

    public abstract float getPitch();
    public abstract float getYaw();
    public abstract float getRoll();
}
