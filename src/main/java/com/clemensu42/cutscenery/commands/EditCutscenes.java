package com.clemensu42.cutscenery.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

public class EditCutscenes {
    public static void registerCommand(){
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) ->
                dispatcher.register(CommandManager.literal("editCutscenes")
                                .requires(source -> source.hasPermissionLevel(4))
                        .executes(context -> {

                    context.getSource().sendMessage(Text.literal("NUH UH"));

                    return 1;
                }))));
    }
}
