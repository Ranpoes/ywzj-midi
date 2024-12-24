package org.ywzj.midi.network.message;

import net.minecraft.network.FriendlyByteBuf;

public class SChangeNote extends CChangeNote {

    public SChangeNote(CChangeNote cChangeNote) {
        this.velScale = cChangeNote.velScale;
        this.uuid = cChangeNote.uuid;
    }

    public static SChangeNote decode(FriendlyByteBuf buf) {
        return new SChangeNote(CChangeNote.decode(buf));
    }

}
