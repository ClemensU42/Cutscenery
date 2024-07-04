package com.clemensu42.cutscenery.networking;

import com.clemensu42.cutscenery.networking.payloads.OpenCutsceneEditorPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class CommonNetworkPayloads {
    public static void registerPayloads(){
        PayloadTypeRegistry.playS2C().register(OpenCutsceneEditorPayload.ID, OpenCutsceneEditorPayload.CODEC);
    }
}
