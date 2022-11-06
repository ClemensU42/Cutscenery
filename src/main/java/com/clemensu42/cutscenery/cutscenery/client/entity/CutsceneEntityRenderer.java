package com.clemensu42.cutscenery.cutscenery.client.entity;

import com.clemensu42.cutscenery.cutscenery.client.EntityModels;
import com.clemensu42.cutscenery.cutscenery.entity.CutsceneEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class CutsceneEntityRenderer extends EntityRenderer<CutsceneEntity>
{

	ModelPart modelPart = null;
	CutsceneEntityModel targetModel = null;
	String prevTargetMob = "";

	public CutsceneEntityRenderer(EntityRendererFactory.Context ctx)
	{
		super(ctx);
		targetModel = new CutsceneEntityModel(null);
	}


	@Override
	public void render(CutsceneEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light)
	{

		if (modelPart == null || !Objects.equals(entity.targetMob, prevTargetMob))
		{
			modelPart = EntityModels.MODELS.get(entity.targetMob);
			targetModel.setBase(modelPart);
			prevTargetMob = entity.targetMob;
		}

		matrices.push();

		Identifier textureID = this.getTexture(entity);
		RenderLayer renderLayer = this.targetModel.getLayer(textureID);
		if (renderLayer != null)
		{
			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
			this.targetModel.render(matrices, vertexConsumer, light, 0, 1.0f, 1.0f, 1.0f, 1.0f);
		}

		matrices.pop();
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}

	@Override
	public Identifier getTexture(CutsceneEntity entity)
	{
		return new Identifier("textures/entity/creeper/creeper.png");
		//return Identifier.tryParse(entity.targetMob);
	}
}
