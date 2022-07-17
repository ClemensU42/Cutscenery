package com.clemensu42.cutscenery.cutscenery.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class Keybinds {
    public static KeyBinding freezeKeyBind;

    public static void initializeKeybinds(){
        freezeKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.cutscenery.freeze",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.cutscenery.test"
        ));
    }
}
