package com.clemensu42.cutscenery;

import com.clemensu42.cutscenery.commands.EditCutscenes;
import com.clemensu42.cutscenery.networking.CommonNetworkPayloads;
import net.fabricmc.api.ModInitializer;

public class Cutscenery implements ModInitializer {
    public static final String MODID = "cutscenery";
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        EditCutscenes.registerCommand();
        CommonNetworkPayloads.registerPayloads();
    }
}
