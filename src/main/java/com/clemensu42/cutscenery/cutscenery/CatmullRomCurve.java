package com.clemensu42.cutscenery.cutscenery;

import net.minecraft.util.math.Vec3d;

public class CatmullRomCurve
{
	public static Vec3d GetPoint(float t, float alpha, Vec3d p0, Vec3d p1, Vec3d p2, Vec3d p3)
	{
		float k0 = 0;
		float k1 = getKnotInterval(p0, p1, alpha);
		float k2 = getKnotInterval(p1, p2, alpha) + k1;
		float k3 = getKnotInterval(p2, p3, alpha) + k2;

		float u = Utilities.lerp(k1, k2, t);
		Vec3d A1 = remap(k0, k1, p0, p1, u);
		Vec3d A2 = remap(k1, k2, p1, p2, u);
		Vec3d A3 = remap(k2, k3, p2, p3, u);
		Vec3d B1 = remap(k0, k1, A1, A2, u);
		Vec3d B2 = remap(k1, k2, A2, A3, u);
		return remap(k1, k2, B1, B2, u);
	}

	static Vec3d remap(float a, float b, Vec3d c, Vec3d d, float u)
	{
		return Utilities.lerp(c, d, (u - a) / (b - a));
	}

	static float getKnotInterval(Vec3d a, Vec3d b, float alpha)
	{
		Vec3d temp = a.subtract(b);
		return (float) Math.pow(temp.dotProduct(temp), alpha);
	}
}
