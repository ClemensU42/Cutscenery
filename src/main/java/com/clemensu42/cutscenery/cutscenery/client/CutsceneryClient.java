package com.clemensu42.cutscenery.cutscenery.client;

import com.clemensu42.cutscenery.cutscenery.Cutscenery;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;

@Environment(EnvType.CLIENT)
public class CutsceneryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Keybinds.initializeKeybinds();
        ClientCutsceneManager.init();
    }
}
