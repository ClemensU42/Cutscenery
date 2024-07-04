package com.clemensu42.cutscenery.client;

import com.clemensu42.cutscenery.networking.ClientNetworkReceivers;
import net.fabricmc.api.ClientModInitializer;

public class CutsceneryClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        ClientNetworkReceivers.registerReceivers();
    }
}
