package com.clemensu42.cutscenery.cutscenery;

import com.clemensu42.cutscenery.cutscenery.Keyframes.Keyframe;
import com.clemensu42.cutscenery.cutscenery.client.ClientCutsceneManager;
import com.clemensu42.cutscenery.cutscenery.resourcemanagement.Resources;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;

public class Cutscene {
    public HashMap<Identifier, KeyframeCollection> serverKeyframes = new HashMap<>();
    public HashMap<Identifier, KeyframeCollection> clientKeyframes = new HashMap<>();

    public float playtime;
    public Vec3d originPosition = Vec3d.ZERO;
    public String originType;

    private EnvType environment;

    public boolean loadFromJson(Identifier id){
        // Cutscene Json files are ONLY read from the logical server
        environment = EnvType.SERVER;
        JsonObject jsonObject = Resources.CUTSCENE_JSON_OBJECTS.get(id);

        if(jsonObject.get("file_version").getAsInt() != CutsceneryConstants.CUTSCENE_FILE_VERSION){
            Cutscenery.LOGGER.error("file version of file " + id.getPath() + " is not up to date!");
            return false;
        }

        originType = jsonObject.get("origin").getAsString();
        playtime = jsonObject.get("playtime").getAsFloat();
        JsonArray jsonKeyframes = jsonObject.get("keyframes").getAsJsonArray();

        for(int i = 0; i < jsonKeyframes.size(); i++){
            JsonObject currentKeyframeJson = jsonKeyframes.get(i).getAsJsonObject();
            Keyframe keyframe = new Keyframe();
            Identifier type = new Identifier(currentKeyframeJson.get("type").getAsString());
            if(currentKeyframeJson.has("position")) {
                JsonObject positionJson = currentKeyframeJson.get("position").getAsJsonObject();
                keyframe.position = new Vec3d(
                        positionJson.get("x").getAsFloat(),
                        positionJson.get("y").getAsFloat(),
                        positionJson.get("z").getAsFloat()
                );
            }
            keyframe.time = currentKeyframeJson.get("time").getAsFloat();
            String environment = currentKeyframeJson.get("environment").getAsString();
            if(environment.equals("client")){
                if(!clientKeyframes.containsKey(type)){
                    clientKeyframes.put(type, new KeyframeCollection());
                }

                clientKeyframes.get(type).keyframes.add(keyframe);

            } else if (environment.equals("server")){
                if(!serverKeyframes.containsKey(type)){
                    serverKeyframes.put(type, new KeyframeCollection());
                }

                serverKeyframes.get(type).keyframes.add(keyframe);

            } else {
                Cutscenery.LOGGER.error("Environment " + environment + " is not a valid environment in " + id.getPath());
            }
        }

        Comparator<Keyframe> comparator = (k1, k2) -> Float.compare(k2.time, k1.time);

        for(KeyframeCollection keyframeCollection : clientKeyframes.values()) {
            keyframeCollection.keyframes.sort(comparator);
        }

        for(KeyframeCollection keyframeCollection : serverKeyframes.values()){
            keyframeCollection.keyframes.sort(comparator);
        }

        return true;
    }

    public PacketByteBuf createClientByteBuf(){
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeFloat(playtime);

        buffer.writeDouble(originPosition.getX());
        buffer.writeDouble(originPosition.getY());
        buffer.writeDouble(originPosition.getZ());

        buffer.writeString(originType);

        Iterator<Identifier> identifiers = clientKeyframes.keySet().iterator();
        List<KeyframeCollection> keyframes = clientKeyframes.values().stream().toList();

        buffer.writeInt(clientKeyframes.size());
        for(int i = 0; i < clientKeyframes.size(); i++){
            buffer.writeIdentifier(identifiers.next());
            buffer.writeInt(keyframes.get(i).keyframes.size());
            for(int j = 0; j < keyframes.get(i).keyframes.size(); j++){
                Keyframe keyframe = keyframes.get(i).keyframes.get(j);
                buffer.writeFloat(keyframe.time);

                buffer.writeDouble(keyframe.position.getX());
                buffer.writeDouble(keyframe.position.getY());
                buffer.writeDouble(keyframe.position.getZ());

                buffer.writeFloat(keyframe.pitch);
                buffer.writeFloat(keyframe.yaw);
                buffer.writeFloat(keyframe.roll);
            }
        }

        return buffer;
    }

    public void loadFromByteBuf(PacketByteBuf buffer){
        // Cutscene PacketByteBufs are ONLY received by client
        environment = EnvType.CLIENT;

        playtime = buffer.readFloat();

        originPosition = new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());

        originType = buffer.readString();

        clientKeyframes = new HashMap<>();
        int entryAmount = buffer.readInt();
        for(int i = 0; i < entryAmount; i++){
            Identifier id = buffer.readIdentifier();
            int keyframeAmount = buffer.readInt();
            List<Keyframe> keyframes = new ArrayList<>();
            for(int j = 0; j < keyframeAmount; j++){
                Keyframe keyframe = new Keyframe();
                keyframe.time = buffer.readFloat();

                keyframe.position = new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());

                keyframe.pitch = buffer.readFloat();
                keyframe.yaw = buffer.readFloat();
                keyframe.roll = buffer.readFloat();
                keyframes.add(keyframe);
            }
            KeyframeCollection keyframeCollection = new KeyframeCollection();
            keyframeCollection.keyframes = keyframes;
            clientKeyframes.put(id, keyframeCollection);
        }
    }

    public void tick(float totalPassedTime) {
        if (environment == EnvType.CLIENT){
            for(Identifier key : clientKeyframes.keySet()){
                clientKeyframes.get(key).update(totalPassedTime, ClientCutsceneManager.cutsceneTargets.get(key), this);
            }
        }
        else {
            /*for(Identifier key : serverKeyframes.keySet())

            }*/
            throw new NotImplementedException();
        }
    }

    public Vec3d translatePosition(Vec3d originalPosition){
        if(originType.equals(CutsceneryConstants.CUTSCENE_ORIGIN_TYPE_ABSOLUTE.getPath()))
            return originalPosition;
        else if(originType.equals(CutsceneryConstants.CUTSCENE_ORIGIN_TYPE_RELATIVE.getPath()))
            return originalPosition.add(originPosition);
        return Vec3d.ZERO;
    }
}
