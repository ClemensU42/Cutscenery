package com.clemensu42.cutscenery.cutscenery;

import com.clemensu42.cutscenery.cutscenery.commands.PlayCommand;
import com.clemensu42.cutscenery.cutscenery.resourcemanagement.Resources;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cutscenery implements ModInitializer
{
	public static final Logger LOGGER = LoggerFactory.getLogger("cutscenery");

	@Override
	public void onInitialize()
	{
		Resources.init();
		CutsceneManager.init();
		CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) ->
				PlayCommand.register(dispatcher)));
	}
}
