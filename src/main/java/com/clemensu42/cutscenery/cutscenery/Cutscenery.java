package com.clemensu42.cutscenery.cutscenery;

import com.clemensu42.cutscenery.cutscenery.resourcemanagement.Resources;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.level.storage.LevelStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cutscenery implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("cutscenery");
    @Override
    public void onInitialize() {
        Resources.init();

    }
}
