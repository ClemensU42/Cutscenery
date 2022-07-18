package com.clemensu42.cutscenery.cutscenery.client;

import net.minecraft.util.math.Vec3d;

public interface CameraInterface {
    public abstract void setPosition(Vec3d position);
    public abstract void setRotation(float pitch, float yaw);
}
