package com.clemensu42.cutscenery.cutscenery;

import net.minecraft.util.Identifier;

public class CutsceneryConstants {
    public static final String MODID = "cutscenery";
    public static final Identifier SEND_CLIENT_CUTSCENE_PACKET_ID = new Identifier(MODID, "send_client_cutscene_packet");
    public static final Identifier SEND_CLIENT_CUTSCENE_PACKET_RECEIVED_ID = new Identifier(MODID, "send_client_cutscene_packet_received");
    public static final Identifier SEND_CLIENT_START_CUTSCENE_ID = new Identifier(MODID, "send_client_start_cutscene_id");

    public static final Identifier KEYFRAME_CAMERA_TYPE = new Identifier(MODID, "camera");

    public static final Identifier CUTSCENE_ORIGIN_TYPE_RELATIVE = new Identifier(MODID, "origin_type/relative");
    public static final Identifier CUTSCENE_ORIGIN_TYPE_ABSOLUTE = new Identifier(MODID, "origin_type/absolute");

    public static final int CUTSCENE_FILE_VERSION = 1;
}
