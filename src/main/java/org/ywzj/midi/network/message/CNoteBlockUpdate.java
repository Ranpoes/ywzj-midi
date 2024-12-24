package org.ywzj.midi.network.message;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

public class CNoteBlockUpdate {

    public int x;
    public int y;
    public int z;
    public int note;
    public boolean on;

    public CNoteBlockUpdate() {}

    public CNoteBlockUpdate(BlockPos pos, int note, boolean on) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.note = note;
        this.on = on;
    }

    public static CNoteBlockUpdate decode(FriendlyByteBuf buf) {
        CNoteBlockUpdate data = new CNoteBlockUpdate();
        data.x = buf.readInt();
        data.y = buf.readInt();
        data.z = buf.readInt();
        data.note = buf.readInt();
        data.on = buf.readBoolean();
        return data;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(note);
        buf.writeBoolean(on);
    }

}
