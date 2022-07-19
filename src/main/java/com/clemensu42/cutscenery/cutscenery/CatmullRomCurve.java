package com.clemensu42.cutscenery.cutscenery;

import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import org.lwjgl.system.MathUtil;

public class CatmullRomCurve {

    public static Vec2f p0, p1, p2, p3;
    public static float alpha = 0.5f;

    public static Vec2f GetPoint(float t){
        float k0 = 0;
        float k1 = getKnotInterval(p0, p1);
        float k2 = getKnotInterval(p1, p2) + k1;
        float k3 = getKnotInterval(p2, p3) + k2;

        float u = Utilities.lerp(k1, k2, t);
        Vec2f A1 = remap(k0, k1, p0, p1, u);
        Vec2f A2 = remap(k1, k2, p1, p2, u);
        Vec2f A3 = remap(k2, k3, p2, p3, u);
        Vec2f B1 = remap(k0, k1, A1, A2, u);
        Vec2f B2 = remap(k1, k2, A2, A3, u);
        return remap(k1, k2, B1, B2, u);
    }

    static Vec2f remap(float a, float b, Vec2f c, Vec2f d, float u){
        return Utilities.lerp(c, d, (u - a) / (b - a));
    }

    static float getKnotInterval(Vec2f a, Vec2f b){
        Vec2f temp = new Vec2f(a.x - b.x, a.y - b.y);
        return (float)Math.pow(temp.dot(temp), 0.5f * alpha);
    }

    public void setPoints(Vec2f p0, Vec2f p1, Vec2f p2, Vec2f p3){
        CatmullRomCurve.p0 = p0;
        CatmullRomCurve.p1 = p1;
        CatmullRomCurve.p2 = p2;
        CatmullRomCurve.p3 = p3;
    }

    public static float getAlpha() {
        return alpha;
    }

    public static void setAlpha(float alpha) {
        CatmullRomCurve.alpha = alpha;
    }
}
