package com.clemensu42.cutscenery.cutscenery.mixin.client;

import com.clemensu42.cutscenery.cutscenery.client.EntityModels;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRendererFactory.Context.class)
public class EntityRendererFactoryContextMixin
{
	@Shadow
	@Final
	private EntityModelLoader modelLoader;

	@Inject(at = @At("TAIL"), method = "<init>(" +
			"Lnet/minecraft/client/render/entity/EntityRenderDispatcher;" +
			"Lnet/minecraft/client/render/item/ItemRenderer;" +
			"Lnet/minecraft/client/render/block/BlockRenderManager;" +
			"Lnet/minecraft/client/render/item/HeldItemRenderer;" +
			"Lnet/minecraft/resource/ResourceManager;" +
			"Lnet/minecraft/client/render/entity/model/EntityModelLoader;" +
			"Lnet/minecraft/client/font/TextRenderer;)V")
	private void init(CallbackInfo info)
	{
		for (var entry : ((EntityModelLoaderMixin) modelLoader).getModelParts().entrySet())
		{
			EntityModels.MODELS.put(entry.getKey().getId().toString(), entry.getValue().createModel());
		}
	}
}
