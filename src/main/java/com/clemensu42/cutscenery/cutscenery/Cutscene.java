package com.clemensu42.cutscenery.cutscenery;

import com.clemensu42.cutscenery.cutscenery.Keyframes.Keyframe;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class Cutscene {
    public HashMap<Identifier, List<Keyframe>> serverKeyframes = new HashMap<>();
    public HashMap<Identifier, List<Keyframe>> clientKeyframes = new HashMap<>();
    public float playtime;
    public Vec3d originPosition;
    public Identifier originType;


    public PacketByteBuf createClientByteBuf(){
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeFloat(playtime);

        buffer.writeDouble(originPosition.getX());
        buffer.writeDouble(originPosition.getY());
        buffer.writeDouble(originPosition.getZ());

        buffer.writeIdentifier(originType);

        Iterator<Identifier> identifiers = clientKeyframes.keySet().iterator();
        List<List<Keyframe>> keyframes = clientKeyframes.values().stream().toList();

        buffer.writeInt(clientKeyframes.size());
        for(int i = 0; i < clientKeyframes.size(); i++){
            buffer.writeIdentifier(identifiers.next());
            buffer.writeInt(keyframes.get(i).size());
            for(int j = 0; j < keyframes.get(i).size(); j++){
                Keyframe keyframe = keyframes.get(i).get(j);
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
        playtime = buffer.readFloat();

        originPosition = new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());

        originType = buffer.readIdentifier();

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
            clientKeyframes.put(id, keyframes);
        }
    }

}
