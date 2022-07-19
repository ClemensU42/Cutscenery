package com.clemensu42.cutscenery.cutscenery;

import com.clemensu42.cutscenery.cutscenery.Keyframes.Keyframe;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class KeyframeCollection {
    public List<Keyframe> keyframes;

    public int currentKeyframe;

    public KeyframeCollection(){
        keyframes = new ArrayList<>();
        currentKeyframe = 0;
    }

    public Keyframe getCurrentKeyframe(){
        return keyframes.get(currentKeyframe);
    }

    public Keyframe getNextKeyframe(){
        return keyframes.get(currentKeyframe + 1);
    }

    public void update(float totalPassedTime, CommonKeyframeInterface target, Cutscene cutscene){
        if(getNextKeyframe().time >= totalPassedTime) currentKeyframe++;
        if(getCurrentKeyframe().time >= totalPassedTime) getCurrentKeyframe().update(totalPassedTime, target, cutscene, getNextKeyframe());
    }
}
