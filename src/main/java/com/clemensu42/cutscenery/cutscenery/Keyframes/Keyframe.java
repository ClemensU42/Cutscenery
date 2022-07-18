package com.clemensu42.cutscenery.cutscenery.Keyframes;

import com.clemensu42.cutscenery.cutscenery.Utilities;
import net.fabricmc.api.EnvType;
import net.minecraft.util.math.Vec3d;

public class Keyframe {
    public float time;
    public Vec3d position;

    public float pitch, yaw, roll;

    public Vec3d getPosition(Keyframe nextKeyFrame, float currentTime) {
        float mappedTime = Utilities.map(time, nextKeyFrame.time, 0f, 1f, currentTime);
        return Utilities.lerp(position, nextKeyFrame.position, mappedTime);
    }

    public float getPitch(Keyframe nextKeyFrame, float currentTime){
        float mappedTime = Utilities.map(time, nextKeyFrame.time, 0f, 1f, currentTime);
        return Utilities.lerp(pitch, nextKeyFrame.pitch, mappedTime);
    }

    public float getYaw(Keyframe nextKeyFrame, float currentTime){
        float mappedTime = Utilities.map(time, nextKeyFrame.time, 0f, 1f, currentTime);
        return Utilities.lerp(yaw, nextKeyFrame.yaw, mappedTime);
    }

    public float getRoll(Keyframe nextKeyFrame, float currentTime){
        float mappedTime = Utilities.map(time, nextKeyFrame.time, 0f, 1f, currentTime);
        return Utilities.lerp(roll, nextKeyFrame.roll, mappedTime);
    }

    public EnvType getEnviroment(){
        return EnvType.SERVER;
    }
}
