package com.clemensu42.cutscenery.cutscenery;

import com.clemensu42.cutscenery.cutscenery.client.EntityModels;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

import java.util.HashMap;

public class CutsceneryPreLaunch implements PreLaunchEntrypoint
{
	@Override
	public void onPreLaunch()
	{
		EntityModels.MODELS = new HashMap<>();
	}
}
