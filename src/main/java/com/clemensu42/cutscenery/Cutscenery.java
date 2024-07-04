package com.clemensu42.cutscenery;

import com.clemensu42.cutscenery.commands.EditCutscenes;
import net.fabricmc.api.ModInitializer;

public class Cutscenery implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        EditCutscenes.registerCommand();
    }
}
