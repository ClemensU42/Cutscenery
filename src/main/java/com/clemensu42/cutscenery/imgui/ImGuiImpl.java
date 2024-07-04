package com.clemensu42.cutscenery.imgui;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.extension.implot.ImPlot;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;

public class ImGuiImpl {
    private final static ImGuiImplGlfw IM_GUI_IMPL_GLFW = new ImGuiImplGlfw();
    private final static ImGuiImplGl3 IM_GUI_IMPL_GL_3 = new ImGuiImplGl3();

    public static void create(final long handle){
        ImGui.createContext();
        ImPlot.createContext();

        final ImGuiIO data = ImGui.getIO();
        data.setIniFilename("cutscenery.ini");
        data.setFontGlobalScale(1F);

        data.setConfigFlags(ImGuiConfigFlags.DockingEnable | ImGuiConfigFlags.ViewportsEnable);

        IM_GUI_IMPL_GLFW.init(handle, true);
        IM_GUI_IMPL_GL_3.init();
    }

    public static void draw(final RenderInterface runnable){
        IM_GUI_IMPL_GLFW.newFrame();
        ImGui.newFrame();
        runnable.render(ImGui.getIO());
        ImGui.render();

        IM_GUI_IMPL_GL_3.renderDrawData(ImGui.getDrawData());
        if(ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)){
            final long pointer = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();

            GLFW.glfwMakeContextCurrent(pointer);
        }
    }
}
