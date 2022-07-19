package com.clemensu42.cutscenery.cutscenery;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class Utilities {
    public static Vec3d lerp(Vec3d from, Vec3d to, float i){
        return from.add((to.subtract(from)).multiply(i));
    }

    public static Vec2f lerp(Vec2f from, Vec2f to, float i){
        Vec2f temp0 = new Vec2f(to.x - from.x, to.y - from.y);
        return from.add(temp0).multiply(i);
    }

    public static float lerp(float from, float to, float i){
        return from + (to - from) * i;
    }

    public static float map(float currentMin, float currentMax, float targetMin, float targetMax, float val){
        float currentDiff = currentMax - currentMin;
        float targetDiff = targetMax - targetMin;
        return ((val - currentMin) / currentDiff) * targetDiff + targetMin;
    }
}
