package com.clemensu42.cutscenery.cutscenery.Keyframes;

import com.clemensu42.cutscenery.cutscenery.CatmullRomCurve;
import com.clemensu42.cutscenery.cutscenery.Cutscene;
import com.clemensu42.cutscenery.cutscenery.KeyframeCollection;
import com.clemensu42.cutscenery.cutscenery.Utilities;
import net.minecraft.util.math.Vec3d;

public class Keyframe
{
	public float time;
	public Vec3d position;

	public float pitch, yaw, roll;

	public Vec3d getPosition(KeyframeCollection keyframeCollection, float currentTime)
	{
		float mappedTime = Utilities.map(time, keyframeCollection.getNextKeyframe().time, 0f, 1f, currentTime);
		//return Utilities.lerp(position, nextKeyFrame.position, mappedTime);
		return CatmullRomCurve.getPointOnSegment(keyframeCollection.currentPositionSegment, mappedTime);
	}

	public float getPitch(Keyframe nextKeyFrame, float currentTime)
	{
		float mappedTime = Utilities.map(time, nextKeyFrame.time, 0f, 1f, currentTime);
		return Utilities.lerp(pitch, nextKeyFrame.pitch, mappedTime);
	}

	public float getYaw(Keyframe nextKeyFrame, float currentTime)
	{
		float mappedTime = Utilities.map(time, nextKeyFrame.time, 0f, 1f, currentTime);
		return Utilities.lerp(yaw, nextKeyFrame.yaw, mappedTime);
	}

	public float getRoll(Keyframe nextKeyFrame, float currentTime)
	{
		float mappedTime = Utilities.map(time, nextKeyFrame.time, 0f, 1f, currentTime);
		return Utilities.lerp(roll, nextKeyFrame.roll, mappedTime);
	}

	public void update(float totalPassedTime, CommonKeyframeInterface target, Cutscene cutscene, KeyframeCollection keyframeCollection)
	{
		target.setObjectPosition(cutscene.translatePosition(getPosition(keyframeCollection, totalPassedTime)));
		target.setObjectRotation(
				getPitch(keyframeCollection.getNextKeyframe(), totalPassedTime),
				getYaw(keyframeCollection.getNextKeyframe(), totalPassedTime),
				getRoll(keyframeCollection.getNextKeyframe(), totalPassedTime));
	}
}
