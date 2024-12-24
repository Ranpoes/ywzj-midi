package org.ywzj.midi.instrument;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.instrument.receiver.CelloMidiReceiver;
import org.ywzj.midi.instrument.receiver.MidiReceiver;

public class Cello extends Instrument {

    public Cello(String name, boolean loop, boolean portable, String keyStart, String keyEnd) {
        super(name, loop, portable, keyStart, keyEnd);
    }

    @Override
    public MidiReceiver receiver(LivingEntity player, Vec3 pos) {
        return new CelloMidiReceiver(this, player, pos);
    }

}
