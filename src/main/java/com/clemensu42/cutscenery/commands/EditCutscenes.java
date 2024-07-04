package com.clemensu42.cutscenery.commands;

import com.clemensu42.cutscenery.networking.payloads.OpenCutsceneEditorPayload;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.command.CommandManager;

public class EditCutscenes {
    public static void registerCommand(){
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) ->
                dispatcher.register(CommandManager.literal("editCutscenes")
                        .requires(source -> source.hasPermissionLevel(4))
                        .executes(context -> {
                            if (context.getSource().getPlayer() != null)
                                ServerPlayNetworking.send(context.getSource().getPlayer(), new OpenCutsceneEditorPayload(1));
                            return 1;
                        }))));
    }
}
