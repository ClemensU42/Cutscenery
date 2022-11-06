package com.clemensu42.cutscenery.cutscenery.mixin.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(EntityRenderers.class)
public interface EntityRenderersMixin
{
	@Accessor("RENDERER_FACTORIES")
	public static Map<EntityType<?>, EntityRendererFactory<?>> getRendererFactories()
	{
		throw new AssertionError();
	}
}
