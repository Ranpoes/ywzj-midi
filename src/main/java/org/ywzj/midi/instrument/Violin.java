package org.ywzj.midi.instrument;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.instrument.receiver.MidiReceiver;
import org.ywzj.midi.instrument.receiver.ViolinMidiReceiver;

public class Violin extends Instrument {

    public Violin(String name, boolean loop, boolean portable, String keyStart, String keyEnd) {
        super(name, loop, portable, keyStart, keyEnd);
    }

    @Override
    public MidiReceiver receiver(LivingEntity player, Vec3 pos) {
        return new ViolinMidiReceiver(this, player, pos);
    }

}
