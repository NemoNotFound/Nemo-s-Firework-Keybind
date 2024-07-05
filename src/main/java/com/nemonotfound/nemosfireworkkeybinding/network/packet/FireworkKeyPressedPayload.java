package com.nemonotfound.nemosfireworkkeybinding.network.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static com.nemonotfound.nemosfireworkkeybinding.NemosFireworkKeybinding.MOD_ID;

public record FireworkKeyPressedPayload() implements CustomPayload {

    public static final CustomPayload.Id<FireworkKeyPressedPayload> ID = new CustomPayload.Id<>(Identifier.of(MOD_ID, "firework_keybinding"));
    public static final FireworkKeyPressedPayload INSTANCE = new FireworkKeyPressedPayload();
    public static final PacketCodec<RegistryByteBuf, FireworkKeyPressedPayload> CODEC = PacketCodec.unit(FireworkKeyPressedPayload.INSTANCE);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
