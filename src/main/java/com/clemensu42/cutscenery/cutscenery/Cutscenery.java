package com.clemensu42.cutscenery.cutscenery;

import com.clemensu42.cutscenery.cutscenery.commands.PlayCommand;
import com.clemensu42.cutscenery.cutscenery.entity.CutsceneEntity;
import com.clemensu42.cutscenery.cutscenery.resourcemanagement.Resources;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cutscenery implements ModInitializer
{
	public static final Logger LOGGER = LoggerFactory.getLogger("cutscenery");

	public static final EntityType<CutsceneEntity> CUTSCENE_ENTITY = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(CutsceneryConstants.MODID, "cutscene_entity"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, CutsceneEntity::new).build()
	);

	@Override
	public void onInitialize()
	{
		Resources.init();

		CutsceneManager.init();

		CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) ->
				PlayCommand.register(dispatcher)));


	}
}
