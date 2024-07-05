package com.nemonotfound.nemosfireworkkeybinding;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import static com.nemonotfound.nemosfireworkkeybinding.NemosFireworkKeybinding.FIREWORK_KEYBINDING_PACKET_ID;
import static com.nemonotfound.nemosfireworkkeybinding.NemosFireworkKeybinding.MOD_ID;

public class NemosFireworkKeybindingClient implements ClientModInitializer {

    private static KeyBinding fireworkRocketKeyBinding;

    @Override
    public void onInitializeClient() {
        fireworkRocketKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key." + MOD_ID + ".firework",
                InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN,
                "category." + MOD_ID + ".nemos-firework-keybinding"));

        registerFireworkKeyPressedEventProducer();
    }

    private void registerFireworkKeyPressedEventProducer() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (fireworkRocketKeyBinding.wasPressed()) {
                ClientPlayNetworking.send(FIREWORK_KEYBINDING_PACKET_ID, PacketByteBufs.empty());
            }
        });
    }
}