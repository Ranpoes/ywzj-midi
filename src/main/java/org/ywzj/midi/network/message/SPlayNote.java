package org.ywzj.midi.network.message;

import net.minecraft.network.FriendlyByteBuf;

public class SPlayNote extends CPlayNote {

    public SPlayNote(CPlayNote cPlayNote) {
        this.x = cPlayNote.x;
        this.y = cPlayNote.y;
        this.z = cPlayNote.z;
        this.instrumentId = cPlayNote.instrumentId;
        this.variantId = cPlayNote.variantId;
        this.note = cPlayNote.note;
        this.velocity = cPlayNote.velocity;
        this.delay = cPlayNote.delay;
        this.on = cPlayNote.on;
        this.uuid = cPlayNote.uuid;
    }

    public static SPlayNote decode(FriendlyByteBuf buf) {
        return new SPlayNote(CPlayNote.decode(buf));
    }

}
