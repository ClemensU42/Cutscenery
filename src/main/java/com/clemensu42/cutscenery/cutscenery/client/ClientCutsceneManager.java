package com.clemensu42.cutscenery.cutscenery.client;

import com.clemensu42.cutscenery.cutscenery.CommonKeyframeInterface;
import com.clemensu42.cutscenery.cutscenery.Cutscene;
import com.clemensu42.cutscenery.cutscenery.Cutscenery;
import com.clemensu42.cutscenery.cutscenery.CutsceneryConstants;
import com.clemensu42.cutscenery.cutscenery.mixin.client.MinecraftClientMixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.util.Identifier;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class ClientCutsceneManager {

    public static Camera camera = null;

    private static float timer;

    public static HashMap<Identifier, CommonKeyframeInterface> cutsceneTargets = new HashMap<>();

    private static Cutscene cutscene;

    public static boolean playing = false;

    public static void play(){
        if(!playing) {
            playing = true;
            ((CameraInterface)camera).setFrozen(true);
        }
    }

    public static void init(){
        ClientPlayNetworking.registerGlobalReceiver(CutsceneryConstants.SEND_CLIENT_CUTSCENE_PACKET_ID, ((client, handler, buf, responseSender) -> {
            buf.retain();
            cutscene = new Cutscene();
            cutscene.loadFromByteBuf(buf);
            cutsceneTargets.put(CutsceneryConstants.KEYFRAME_CAMERA_TYPE, (CommonKeyframeInterface)MinecraftClient.getInstance().gameRenderer.getCamera());
            ClientPlayNetworking.send(CutsceneryConstants.SEND_CLIENT_CUTSCENE_PACKET_RECEIVED_ID, PacketByteBufs.empty());
            // TODO: add proper origin support
            cutscene.originPosition = MinecraftClient.getInstance().player.getEyePos();
            buf.release();
        }));

        ClientPlayNetworking.registerGlobalReceiver(CutsceneryConstants.SEND_CLIENT_START_CUTSCENE_ID, ((client, handler, buf, responseSender) -> {
            client.execute(ClientCutsceneManager::play);
        }));
    }

    public static void tick(float tickDelta) {
        if(!playing) return;

        if(timer >= cutscene.playtime){
            playing = false;
            ((CameraInterface)camera).setFrozen(false);
            timer = 0;
            return;
        }
        cutscene.tick(timer);

        timer += 1f / (float)((MinecraftClientMixin)MinecraftClient.getInstance()).getCurrentFPS();
    }
}
