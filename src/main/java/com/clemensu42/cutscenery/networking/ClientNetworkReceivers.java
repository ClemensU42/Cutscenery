package com.clemensu42.cutscenery.networking;

import com.clemensu42.cutscenery.client.screen.CutsceneEditScreen;
import com.clemensu42.cutscenery.networking.payloads.OpenCutsceneEditorPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientNetworkReceivers {
    public static void registerReceivers(){
        ClientPlayNetworking.registerGlobalReceiver(OpenCutsceneEditorPayload.ID, ((payload, context) ->
                context.client().execute(() ->
                        context.client().setScreenAndRender(new CutsceneEditScreen())
                )
        ));
    }
}
