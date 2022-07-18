package com.clemensu42.cutscenery.cutscenery.client;

import com.clemensu42.cutscenery.cutscenery.Cutscene;
import com.clemensu42.cutscenery.cutscenery.CutsceneryConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.Camera;

@Environment(EnvType.CLIENT)
public class ClientCutsceneManager {

    public static Camera camera;

    private static float timer;

    private static Cutscene cutscene;

    public static void init(){
        ClientPlayNetworking.registerGlobalReceiver(CutsceneryConstants.SEND_CLIENT_CUTSCENE_PACKET_ID, ((client, handler, buf, responseSender) -> {
            client.execute(() -> {
                cutscene.loadFromByteBuf(buf);
            });
        }));
    }

    public static void tick(float tickDelta) {

    }
}
