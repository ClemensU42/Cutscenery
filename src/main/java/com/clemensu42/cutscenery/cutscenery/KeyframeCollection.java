package com.clemensu42.cutscenery.cutscenery;

import com.clemensu42.cutscenery.cutscenery.Keyframes.CommonKeyframeInterface;
import com.clemensu42.cutscenery.cutscenery.Keyframes.Keyframe;

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
        if(getNextKeyframe().time <= totalPassedTime) {
            currentKeyframe++;
            Cutscenery.LOGGER.info("Current Keyframe: " + currentKeyframe);
        }
        //if(getCurrentKeyframe().time >= totalPassedTime)
        getCurrentKeyframe().update(totalPassedTime, target, cutscene, getNextKeyframe());
    }
}
