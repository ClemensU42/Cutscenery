package com.clemensu42.cutscenery.client.gui.editorscreens;

import imgui.ImGui;
import imgui.type.ImString;

public class EditorCreateNewCutscene {
    /**
     * Displays the "Create new Cutscene" menu
     * @return boolean - returns, whether the window should continue to be open (true), or be closed (false)
     */
    public static boolean DisplayEditorCreateNewCutscene(){
        boolean should_stay_open = true;
        if(ImGui.begin("Create new Cutscene")){
            ImString cutscene_name = new ImString();
            ImGui.inputText("cutscene_name", cutscene_name);

            if(ImGui.button("Create")){
                // TODO: add cutscene creation
                should_stay_open = false;
            }

            ImGui.sameLine();
            if(ImGui.button("Cancel")){
                should_stay_open = false;
            }

            ImGui.end();
        }
        return should_stay_open;
    }
}
