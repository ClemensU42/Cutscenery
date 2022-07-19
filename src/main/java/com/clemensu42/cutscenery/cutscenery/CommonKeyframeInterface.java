package com.clemensu42.cutscenery.cutscenery;

import net.minecraft.util.math.Vec3d;

public interface CommonKeyframeInterface {
    public abstract void setObjectPosition(Vec3d position);
    public abstract Vec3d getObjectPosition();

    public abstract void setPitch(float pitch);
    public abstract void setYaw(float yaw);
    public abstract void setRoll(float roll);

    public abstract float getPitch();
    public abstract float getYaw();
    public abstract float getRoll();
}
