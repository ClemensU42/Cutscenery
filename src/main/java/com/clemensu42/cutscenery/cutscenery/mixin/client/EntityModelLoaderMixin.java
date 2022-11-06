package com.clemensu42.cutscenery.cutscenery.mixin.client;

import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(EntityModelLoader.class)
public interface EntityModelLoaderMixin
{
	@Accessor("modelParts")
	public Map<EntityModelLayer, TexturedModelData> getModelParts();
}
