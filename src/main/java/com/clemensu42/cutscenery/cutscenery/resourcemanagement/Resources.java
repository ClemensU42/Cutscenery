package com.clemensu42.cutscenery.cutscenery.resourcemanagement;

import com.clemensu42.cutscenery.cutscenery.Cutscenery;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class Resources {

    public static HashMap<Identifier, JsonObject> CUTSCENE_JSON_OBJECTS = new HashMap<>();
    public static void init(){
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return new Identifier("cutscenery", "cutscenery_resources");
            }

            @Override
            public void reload(ResourceManager manager) {
                CUTSCENE_JSON_OBJECTS.clear();
                Map<Identifier, Resource> map = manager.findResources("cutscenes", path -> path.getPath().endsWith(".json"));
                for(Identifier id : map.keySet()) {
                    try(BufferedReader reader = map.get(id).getReader()) {
                        JsonElement jsonElement = JsonParser.parseReader(reader);
                        JsonObject object = jsonElement.getAsJsonObject();
                        CUTSCENE_JSON_OBJECTS.put(id, object);
                        Cutscenery.LOGGER.info(id.toString());
                    } catch(Exception e){
                        Cutscenery.LOGGER.error("Error occured while loading resource json " + id.toString(), e);
                    }
                }
            }
        });
    }
}
