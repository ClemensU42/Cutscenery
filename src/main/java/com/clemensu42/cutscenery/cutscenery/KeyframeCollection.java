package com.clemensu42.cutscenery.cutscenery;

import com.clemensu42.cutscenery.cutscenery.Keyframes.CommonKeyframeInterface;
import com.clemensu42.cutscenery.cutscenery.Keyframes.Keyframe;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class KeyframeCollection
{
	public List<Keyframe> keyframes;

	public int currentKeyframe;

	public KeyframeCollection()
	{
		keyframes = new ArrayList<>();
		currentKeyframe = 1;
	}

	public Keyframe getCurrentKeyframe()
	{
		return keyframes.get(currentKeyframe);
	}

	public Keyframe getNextKeyframe()
	{
		return keyframes.get(currentKeyframe + 1);
	}

	public Keyframe getSecondNextKeyframe()
	{
		return keyframes.get(MathHelper.clamp(currentKeyframe + 2, 0, keyframes.size() - 1));
	}

	public Keyframe getLastKeyframe()
	{
		return keyframes.get(MathHelper.clamp(currentKeyframe - 1, 0, keyframes.size() - 1));
	}

	public void update(float totalPassedTime, CommonKeyframeInterface target, Cutscene cutscene)
	{
		if (getNextKeyframe().time <= totalPassedTime)
		{
			currentKeyframe++;
		}
		getCurrentKeyframe().update(totalPassedTime, target, cutscene, this);
	}
}
