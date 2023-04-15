package com.clemensu42.cutscenery.cutscenery.Keyframes;

import com.clemensu42.cutscenery.cutscenery.CatmullRomCurve;
import com.clemensu42.cutscenery.cutscenery.Cutscene;
import com.clemensu42.cutscenery.cutscenery.KeyframeCollection;
import com.clemensu42.cutscenery.cutscenery.Utilities;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Keyframe
{
	public float time;
	public Vec3d position;

	public float pitch, yaw, roll;

	public CameraLookAtMode cameraLookAtMode = CameraLookAtMode.NONE;
	public Vec3d cameraLookAtPoint;

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
		Vec3d position = getPosition(keyframeCollection, totalPassedTime);
		target.setObjectPosition(cutscene.translatePosition(position));

		if(cameraLookAtMode == CameraLookAtMode.NONE)
			target.setObjectRotation(
					getPitch(keyframeCollection.getNextKeyframe(), totalPassedTime),
					getYaw(keyframeCollection.getNextKeyframe(), totalPassedTime),
					getRoll(keyframeCollection.getNextKeyframe(), totalPassedTime));
		else{
			Vec3d targetPoint = Vec3d.ZERO;
			if(cameraLookAtMode == CameraLookAtMode.FOLLOW_POINT){
				targetPoint = cameraLookAtPoint;
			} // TODO: implement player following //! set targetPoint as player position

			Vec3d diff = targetPoint.subtract(position);
			double yaw = MathHelper.atan2(diff.getZ(), diff.getX()) * MathHelper.DEGREES_PER_RADIAN - 90.0;
			double pitch = 0;
			target.setObjectRotation((float) pitch, (float) yaw, 0);
		}
	}

	public enum CameraLookAtMode{
		NONE,
		FOLLOW_PLAYER,
		FOLLOW_POINT
	}
}
