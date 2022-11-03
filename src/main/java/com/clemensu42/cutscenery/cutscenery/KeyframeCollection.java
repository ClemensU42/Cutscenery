package com.clemensu42.cutscenery.cutscenery;

import com.clemensu42.cutscenery.cutscenery.Keyframes.CommonKeyframeInterface;
import com.clemensu42.cutscenery.cutscenery.Keyframes.Keyframe;

import java.util.ArrayList;
import java.util.List;

public class KeyframeCollection
{
	public List<Keyframe> keyframes;

	public int currentKeyframe;
	public CatmullRomCurve.Segment currentPositionSegment;

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
		return keyframes.get((currentKeyframe + 1) % keyframes.size());
	}

	public Keyframe getSecondNextKeyframe()
	{
		return keyframes.get((currentKeyframe + 2) % keyframes.size());
	}

	public Keyframe getLastKeyframe()
	{
		int i = currentKeyframe - 1;
		i = i < 0 ? keyframes.size() - 1 : i;
		return keyframes.get(i);
	}

	public void update(float totalPassedTime, CommonKeyframeInterface target, Cutscene cutscene)
	{
		if (currentPositionSegment == null)
		{
			currentPositionSegment = CatmullRomCurve.calculateSegment(
					getLastKeyframe().position,
					getCurrentKeyframe().position,
					getNextKeyframe().position,
					getSecondNextKeyframe().position,
					0.5f,
					0.0f
			);
			keyframes.get(0).position.add(0.0001, 0, 0);
			currentPositionSegment = CatmullRomCurve.calculateSegment(
					getLastKeyframe().position,
					getCurrentKeyframe().position,
					getNextKeyframe().position,
					getSecondNextKeyframe().position,
					0.5f,
					0.0f
			);
		}

		if (getNextKeyframe().time <= totalPassedTime)
		{
			currentKeyframe++;
			currentPositionSegment = CatmullRomCurve.calculateSegment(
					getLastKeyframe().position,
					getCurrentKeyframe().position,
					getNextKeyframe().position,
					getSecondNextKeyframe().position,
					0.5f,
					0.0f
			);
		}
		getCurrentKeyframe().update(totalPassedTime, target, cutscene, this);
	}
}
