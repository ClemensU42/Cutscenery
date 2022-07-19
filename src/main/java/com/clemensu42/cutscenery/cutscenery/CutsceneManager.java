package com.clemensu42.cutscenery.cutscenery;

import com.clemensu42.cutscenery.cutscenery.Keyframes.CommonKeyframeInterface;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class CutsceneManager {

    private static float timer;

    public static HashMap<Identifier, CommonKeyframeInterface> cutsceneTargets = new HashMap<>();

    private static Cutscene cutscene;

    public static boolean playing = false;

    public static void play(){

    }

    public static void init(){
        ServerPlayNetworking.registerGlobalReceiver(CutsceneryConstants.SEND_CLIENT_CUTSCENE_PACKET_RECEIVED_ID, ((server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                // TODO: send start message to all players of a certain cutscene once all have sent the received id
                ServerPlayNetworking.send(player, CutsceneryConstants.SEND_CLIENT_START_CUTSCENE_ID, PacketByteBufs.empty());
                play();
            });
        }));
    }

    // TODO: better player handling
    public static void prepareCutscene(Identifier identifier, ServerPlayerEntity player){
        cutscene = new Cutscene();
        cutscene.loadFromJson(identifier);
        PacketByteBuf packetByteBuf = cutscene.createClientByteBuf();
        ServerPlayNetworking.send(player, CutsceneryConstants.SEND_CLIENT_CUTSCENE_PACKET_ID, packetByteBuf);
    }

    public static void tick(float tickDelta){
        if(!playing) return;

        if(timer >= cutscene.playtime){
            playing = false;
            return;
        }

        cutscene.tick(timer);

        timer += tickDelta;
    }

}
