package com.clemensu42.cutscenery.cutscenery;

import com.clemensu42.cutscenery.cutscenery.commands.PlayCommand;
import com.clemensu42.cutscenery.cutscenery.resourcemanagement.Resources;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.level.storage.LevelStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cutscenery implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("cutscenery");
    @Override
    public void onInitialize() {
        Resources.init();
        CutsceneManager.init();
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            PlayCommand.register(dispatcher);
        }));
    }
}
