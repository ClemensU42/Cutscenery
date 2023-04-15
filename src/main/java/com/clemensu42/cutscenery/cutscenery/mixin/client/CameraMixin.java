package com.clemensu42.cutscenery.cutscenery.mixin.client;

import com.clemensu42.cutscenery.cutscenery.Keyframes.CommonKeyframeInterface;
import com.clemensu42.cutscenery.cutscenery.client.CameraInterface;
import com.clemensu42.cutscenery.cutscenery.client.ClientCutsceneManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin implements CameraInterface, CommonKeyframeInterface
{

	@Shadow
	@Final
	private Vec3f diagonalPlane;
	@Shadow
	@Final
	private Vec3f verticalPlane;
	@Shadow
	@Final
	private Vec3f horizontalPlane;
	@Shadow
	@Final
	private Quaternion rotation;
	@Shadow
	private float pitch;
	@Shadow
	private float yaw;
	private boolean bobViewCopy;

	@Shadow
	public abstract float getYaw();

	@Shadow
	public abstract float getPitch();

	@Shadow
	public abstract Vec3d getPos();

	@Shadow
	protected abstract void setPos(Vec3d pos);


	@Inject(method = "update(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;ZZF)V", at = @At("TAIL"))
	private void updateInject(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci)
	{
		if (ClientCutsceneManager.camera == null) ClientCutsceneManager.camera = ((Camera) (Object) this);
		ClientCutsceneManager.tick(tickDelta);
	}

	@Override
	public void setFrozen(boolean frozen)
	{
		if (!frozen)
		{
			MinecraftClient.getInstance().options.getBobView().setValue(bobViewCopy);
			MinecraftClient.getInstance().options.setPerspective(Perspective.FIRST_PERSON);
			MinecraftClient.getInstance().options.hudHidden = false;
		}
		else
		{
			MinecraftClient.getInstance().options.setPerspective(Perspective.THIRD_PERSON_BACK);
			bobViewCopy = MinecraftClient.getInstance().options.getBobView().getValue();
			MinecraftClient.getInstance().options.getBobView().setValue(false);
			MinecraftClient.getInstance().options.hudHidden = true;
		}
	}

	@Override
	public void setRotation(float yaw, float pitch)
	{
		this.pitch = pitch;
		this.yaw = yaw;
		this.rotation.set(0.0f, 0.0f, 0.0f, 1.0f);
		this.rotation.hamiltonProduct(Vec3f.POSITIVE_Y.getDegreesQuaternion(-yaw));
		this.rotation.hamiltonProduct(Vec3f.POSITIVE_X.getDegreesQuaternion(pitch));
		this.horizontalPlane.set(0.0f, 0.0f, 1.0f);
		this.horizontalPlane.rotate(this.rotation);
		this.verticalPlane.set(0.0f, 1.0f, 0.0f);
		this.verticalPlane.rotate(this.rotation);
		this.diagonalPlane.set(1.0f, 0.0f, 0.0f);
		this.diagonalPlane.rotate(this.rotation);
	}

	@Override
	public void setPosition(Vec3d position)
	{
		setPos(position);
	}

	// Keyframe stuff

	@Override
	public Vec3d getObjectPosition()
	{
		return getPos();
	}

	@Override
	public void setObjectPosition(Vec3d position)
	{
		setPos(position);
	}

	@Override
	public void setObjectRotation(float pitch, float yaw, float roll)
	{
		setRotation(yaw, pitch);
	}

}
