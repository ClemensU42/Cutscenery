package com.clemensu42.cutscenery.client.screen;

import com.clemensu42.cutscenery.imgui.ImGuiImpl;
import imgui.ImGui;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class CutsceneEditScreen extends Screen {
    public CutsceneEditScreen() {
        super(Text.literal(""));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        ImGuiImpl.draw(io -> {
            if(ImGui.begin("Hello World")){
                ImGui.end();
            }
        });
    }
}
