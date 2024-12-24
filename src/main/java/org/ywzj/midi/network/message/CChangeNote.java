package org.ywzj.midi.network.message;

import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public class CChangeNote {

    public float velScale;
    public UUID uuid;

    public CChangeNote() {}

    public CChangeNote(float velScale, UUID uuid) {
        this.velScale = velScale;
        this.uuid = uuid;
    }

    public static CChangeNote decode(FriendlyByteBuf buf) {
        CChangeNote data = new CChangeNote();
        data.velScale = buf.readFloat();
        data.uuid = buf.readUUID();
        return data;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(velScale);
        buf.writeUUID(uuid);
    }

}
