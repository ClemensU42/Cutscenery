package com.clemensu42.cutscenery.networking.payloads;

import com.clemensu42.cutscenery.networking.NetworkingIDs;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record OpenCutsceneEditorPayload(int dummy) implements CustomPayload {
    public static final Id<OpenCutsceneEditorPayload> ID = new Id<>(NetworkingIDs.OPEN_CUTSCENE_EDITOR_ID);
    public static final PacketCodec<RegistryByteBuf, OpenCutsceneEditorPayload> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, OpenCutsceneEditorPayload::dummy, OpenCutsceneEditorPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
