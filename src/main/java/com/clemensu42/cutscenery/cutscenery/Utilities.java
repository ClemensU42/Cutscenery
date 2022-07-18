package com.clemensu42.cutscenery.cutscenery;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vec3i;

public class Utilities {
    public static Vec3d lerp(Vec3d from, Vec3d to, float i){
        return to.multiply(i).add(from.multiply(1f - i));
    }

    public static float lerp(float from, float to, float i){
        return to * i + from * (1f - i);
    }

    public static float map(float currentMin, float currentMax, float targetMin, float targetMax, float val){
        float currentDiff = currentMax - currentMin;
        float targetDiff = targetMax - targetMin;
        return ((val - currentMin) / currentDiff) * targetDiff + targetMin;
    }
}
