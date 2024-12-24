package org.ywzj.midi.network.message;

import net.minecraft.network.FriendlyByteBuf;

public class CFakePlayerUpdate {

    public String name;
    public String skinUrl;
    public boolean isSitting;
    public int id;

    public CFakePlayerUpdate() {}

    public CFakePlayerUpdate(String name, String skinUrl, boolean isSitting, int id) {
        this.name = name;
        this.skinUrl = skinUrl;
        this.isSitting = isSitting;
        this.id = id;
    }

    public static CFakePlayerUpdate decode(FriendlyByteBuf buf) {
        CFakePlayerUpdate data = new CFakePlayerUpdate();
        data.name = buf.readUtf();
        data.skinUrl = buf.readUtf();
        data.isSitting = buf.readBoolean();
        data.id = buf.readInt();
        return data;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(name);
        buf.writeUtf(skinUrl);
        buf.writeBoolean(isSitting);
        buf.writeInt(id);
    }

}
