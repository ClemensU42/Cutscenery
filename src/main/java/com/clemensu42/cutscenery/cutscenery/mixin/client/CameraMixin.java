package com.clemensu42.cutscenery.cutscenery.mixin.client;

import com.clemensu42.cutscenery.cutscenery.client.Keybinds;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow public abstract void setPos(Vec3d pos);
    @Shadow public abstract void setRotation(float yaw, float pitch);
    @Shadow public abstract float getYaw();
    @Shadow public abstract float getPitch();

    @Shadow public abstract Vec3d getPos();

    private static Vec3d freezePosition;
    private static float freezePitch, freezeYaw;
    private static boolean frozen = false;

    @Inject(method = "update(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;ZZF)V", at = @At("TAIL"))
    private void updateInject(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci){
        if(frozen){
            if(Keybinds.freezeKeyBind.wasPressed()) frozen = false;
            setPos(freezePosition);
            setRotation(freezeYaw, freezePitch);
        } else {
            if(Keybinds.freezeKeyBind.wasPressed()) frozen = true;
            freezePosition = getPos();
            freezeYaw = getYaw();
            freezePitch = getPitch();
        }
    }
}
