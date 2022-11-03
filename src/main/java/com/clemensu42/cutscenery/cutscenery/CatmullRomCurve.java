package com.clemensu42.cutscenery.cutscenery;

import net.minecraft.util.math.Vec3d;

public class CatmullRomCurve
{
	public static Segment calculateSegment(Vec3d p0, Vec3d p1, Vec3d p2, Vec3d p3, float alpha, float tension)
	{
		float t01 = (float) Math.pow(p0.distanceTo(p1), alpha);
		float t12 = (float) Math.pow(p1.distanceTo(p2), alpha);
		float t23 = (float) Math.pow(p2.distanceTo(p3), alpha);

		Vec3d m1 = new Vec3d(
				(1.0f - tension) * (p2.x - p1.x + t12 * ((p1.x - p0.x) / t01 - (p2.x - p0.x) / (t01 + t12))),
				(1.0f - tension) * (p2.y - p1.y + t12 * ((p1.y - p0.y) / t01 - (p2.y - p0.y) / (t01 + t12))),
				(1.0f - tension) * (p2.z - p1.z + t12 * ((p1.z - p0.z) / t01 - (p2.z - p0.z) / (t01 + t12)))
		);

		Vec3d m2 = new Vec3d(
				(1.0f - tension) * (p2.x - p1.x + t12 * ((p3.x - p2.x) / t23 - (p3.x - p1.x) / (t12 + t23))),
				(1.0f - tension) * (p2.y - p1.y + t12 * ((p3.y - p2.y) / t23 - (p3.y - p1.y) / (t12 + t23))),
				(1.0f - tension) * (p2.z - p1.z + t12 * ((p3.z - p2.z) / t23 - (p3.z - p1.z) / (t12 + t23)))
		);

		Segment segment = new Segment();
		segment.a = (p1.subtract(p2)).multiply(2.0f).add(m1).add(m2);
		segment.b = (p1.subtract(p2)).multiply(-3.0f).subtract(m1).subtract(m1).subtract(m2);
		segment.c = m1;
		segment.d = p1;

		return segment;
	}

	public static Vec3d getPointOnSegment(Segment segment, float t)
	{
		return segment.a.multiply(t * t * t).add(segment.b.multiply(t * t)).add(segment.c.multiply(t)).add(segment.d);
	}

	public static class Segment
	{
		public Vec3d a, b, c, d;
	}
}
