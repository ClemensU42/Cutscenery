package com.clemensu42.cutscenery.cutscenery.commands;

import com.clemensu42.cutscenery.cutscenery.CutsceneManager;
import com.clemensu42.cutscenery.cutscenery.resourcemanagement.Resources;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.CommandFunctionArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

public class PlayCommand {
    public static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = ((context, builder) -> CommandSource.suggestIdentifiers(Resources.CUTSCENE_JSON_OBJECTS.keySet(), builder));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        dispatcher.register((LiteralArgumentBuilder<ServerCommandSource>) CommandManager.literal("playCutscene")
                .requires(source -> source.hasPermissionLevel(2)).then(
                        CommandManager.argument("name", IdentifierArgumentType.identifier())
                                .suggests(SUGGESTION_PROVIDER).executes(
                                        context -> PlayCommand.execute(context.getSource(), IdentifierArgumentType.getIdentifier(context, "name"))
                                )
                ));
    }

    private static int execute(ServerCommandSource source, Identifier cutscene) {
        CutsceneManager.prepareCutscene(cutscene, source.getPlayer());
        return 0;
    }
}
