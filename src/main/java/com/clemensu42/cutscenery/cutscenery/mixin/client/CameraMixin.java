package com.clemensu42.cutscenery.cutscenery.mixin.client;

import com.clemensu42.cutscenery.cutscenery.client.CameraInterface;
import com.clemensu42.cutscenery.cutscenery.client.Keybinds;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
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
public abstract class CameraMixin implements CameraInterface {

    @Shadow
    protected abstract void setPos(Vec3d pos);
    @Shadow public abstract float getYaw();
    @Shadow public abstract float getPitch();

    @Shadow public abstract Vec3d getPos();

    @Shadow @Final private Vec3f diagonalPlane;
    @Shadow @Final private Vec3f verticalPlane;
    @Shadow @Final private Vec3f horizontalPlane;
    @Shadow @Final private Quaternion rotation;
    @Shadow private float pitch;
    @Shadow private float yaw;
    private static Vec3d freezePosition;
    private static float freezePitch, freezeYaw;
    private static boolean frozen = false;

    private boolean bobViewCopy;

    @Inject(method = "update(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;ZZF)V", at = @At("TAIL"))
    private void updateInject(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci){
        if(frozen){
            if(Keybinds.freezeKeyBind.wasPressed()) {
                frozen = false;
                MinecraftClient.getInstance().options.getBobView().setValue(bobViewCopy);
                MinecraftClient.getInstance().options.setPerspective(Perspective.FIRST_PERSON);
                MinecraftClient.getInstance().options.hudHidden = false;
            }
            setPos(freezePosition);
            setRotation(freezeYaw, freezePitch);
        } else {
            if(Keybinds.freezeKeyBind.wasPressed()) {
                frozen = true;
                MinecraftClient.getInstance().options.setPerspective(Perspective.THIRD_PERSON_BACK);
                bobViewCopy = MinecraftClient.getInstance().options.getBobView().getValue();
                MinecraftClient.getInstance().options.getBobView().setValue(false);
                MinecraftClient.getInstance().options.hudHidden = true;
            }
            freezePosition = getPos();
            freezeYaw = getYaw();
            freezePitch = getPitch();
        }
    }

    @Override
    public void setRotation(float yaw, float pitch) {
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
    public void setPosition(Vec3d position){
        setPos(position);
    }
}
