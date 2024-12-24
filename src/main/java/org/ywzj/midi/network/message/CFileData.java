package org.ywzj.midi.network.message;

import net.minecraft.network.FriendlyByteBuf;

import java.nio.charset.StandardCharsets;

public class CFileData {

    public boolean eof;
    public byte[] bytes;
    public short type;
    public String fileName;

    public CFileData() {}

    public CFileData(boolean eof, byte[] bytes, short type, String fileName) {
        this.eof = eof;
        this.bytes = bytes;
        this.type = type;
        this.fileName = fileName;
        if (bytes.length > 32763) throw new RuntimeException("packet size > 32K");
    }

    public static CFileData decode(FriendlyByteBuf buf) {
        CFileData data = new CFileData();
        data.eof = buf.readBoolean();
        data.bytes = new byte[buf.readInt()];
        buf.readBytes(data.bytes);
        data.type = buf.readShort();
        byte[] bytes = new byte[buf.readInt()];
        buf.readBytes(bytes);
        data.fileName = new String(bytes, StandardCharsets.UTF_8);
        return data;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(eof);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
        buf.writeShort(type);
        byte[] bytes = fileName.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }

}
