package com.clemensu42.cutscenery.client.gui.editorscreens;

import com.clemensu42.cutscenery.client.CutsceneryClient;
import com.clemensu42.cutscenery.imgui.ImGuiImpl;
import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;

public class EditorToolbar {

    public static final int TOOLBAR_HEIGHT = 32;

    public static void renderEditorToolbar(){
        ImGuiViewport viewport = ImGui.getMainViewport();
        ImGui.setNextWindowPos(viewport.getPosX(), viewport.getPosY());
        ImGui.setNextWindowSize(viewport.getSizeX(), TOOLBAR_HEIGHT);
        ImGui.setNextWindowViewport(viewport.getID());

        int windowFlags =
                ImGuiWindowFlags.NoDocking
                | ImGuiWindowFlags.NoTitleBar
                | ImGuiWindowFlags.NoResize
                | ImGuiWindowFlags.NoMove
                | ImGuiWindowFlags.NoScrollbar
                | ImGuiWindowFlags.NoSavedSettings;
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0);
        ImGui.begin("TOOLBAR", windowFlags);
        ImGui.popStyleVar();

        if(ImGui.button("Exit Editor"))
            CutsceneryClient.isClientInEditor = false;

        ImGui.end();

    }
}
