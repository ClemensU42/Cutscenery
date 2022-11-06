package com.clemensu42.cutscenery.cutscenery.client.entity;

import com.clemensu42.cutscenery.cutscenery.entity.CutsceneEntity;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class CutsceneEntityModel extends EntityModel<CutsceneEntity>
{

	private ModelPart base;

	public CutsceneEntityModel(ModelPart src)
	{
		this.base = src;
	}

	public static TexturedModelData getTexturedModelData()
	{
		ModelData modelData = new ModelData();
		return TexturedModelData.of(modelData, 64, 32);
	}

	public void setBase(ModelPart src)
	{
		this.base = src;
	}

	@Override
	public void setAngles(CutsceneEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
	{

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha)
	{
		ImmutableList.of(this.base).forEach((modelPart ->
		{
			modelPart.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		}));
	}
}
