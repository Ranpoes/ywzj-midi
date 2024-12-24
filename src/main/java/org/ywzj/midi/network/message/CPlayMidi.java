package org.ywzj.midi.network.message;

import net.minecraft.network.FriendlyByteBuf;

import java.nio.charset.StandardCharsets;

public class CPlayMidi {

    public String midFileNames;
    public String configs;
    public int conductorId;
    public short opCode;

    public CPlayMidi() {}

    public CPlayMidi(String midFileNames, String configs, int conductorId, short opCode) {
        this.midFileNames = midFileNames;
        this.configs = configs;
        this.conductorId = conductorId;
        this.opCode = opCode;
    }

    public static CPlayMidi decode(FriendlyByteBuf buf) {
        CPlayMidi data = new CPlayMidi();
        byte[] bytes = new byte[buf.readInt()];
        buf.readBytes(bytes);
        data.midFileNames = new String(bytes, StandardCharsets.UTF_8);
        bytes = new byte[buf.readInt()];
        buf.readBytes(bytes);
        data.configs = new String(bytes, StandardCharsets.UTF_8);
        data.conductorId = buf.readInt();
        data.opCode = buf.readShort();
        return data;
    }

    public void encode(FriendlyByteBuf buf) {
        byte[] bytes = midFileNames.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
        bytes = configs.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
        buf.writeInt(conductorId);
        buf.writeShort(opCode);
    }

}
