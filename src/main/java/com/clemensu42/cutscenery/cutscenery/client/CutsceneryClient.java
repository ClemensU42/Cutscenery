package com.clemensu42.cutscenery.cutscenery.client;

import com.clemensu42.cutscenery.cutscenery.Cutscenery;
import com.clemensu42.cutscenery.cutscenery.client.entity.CutsceneEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class CutsceneryClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		Keybinds.initializeKeybinds();
		ClientCutsceneManager.init();

		EntityRendererRegistry.register(Cutscenery.CUTSCENE_ENTITY, CutsceneEntityRenderer::new);
	}
}
