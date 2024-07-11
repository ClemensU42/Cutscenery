package com.clemensu42.cutscenery.mixin.client.gui;

import com.clemensu42.cutscenery.client.CutsceneryClient;
import com.clemensu42.cutscenery.client.gui.editorscreens.EditorToolbar;
import com.clemensu42.cutscenery.imgui.ImGuiImpl;
import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LayeredDrawer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Shadow
    private final LayeredDrawer layeredDrawer = new LayeredDrawer();

    @Inject(method = "<init>(Lnet/minecraft/client/MinecraftClient;)V", at = @At("TAIL"))
    private void constructorInject(MinecraftClient client, CallbackInfo ci){
        layeredDrawer.addLayer(this::renderCutsceneEditorOverlay);
    }

    @Unique
    public void renderCutsceneEditorOverlay(DrawContext drawContext, RenderTickCounter renderTickCounter){
        if(!CutsceneryClient.isClientInEditor) return;

        ImGuiImpl.draw(io -> {
            /*ImGuiViewport viewport = ImGui.getMainViewport();
            ImGui.setNextWindowPos(viewport.getPosX(), viewport.getPosY() + EditorToolbar.TOOLBAR_HEIGHT);
            ImGui.setNextWindowSize(viewport.getSizeX(), viewport.getSizeY() - EditorToolbar.TOOLBAR_HEIGHT);
            ImGui.setNextWindowViewport(viewport.getID());

            int window_flags =
                    ImGuiWindowFlags.MenuBar | ImGuiWindowFlags.NoDocking
                    | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse
                    | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove
                    | ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;

            ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0.0f, 0.0f);
            ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
            ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);
            ImGui.begin("Master Dockspace", window_flags);
            int dockmain = ImGui.getID("")*/

            EditorToolbar.renderEditorToolbar();

        });
    }
}
