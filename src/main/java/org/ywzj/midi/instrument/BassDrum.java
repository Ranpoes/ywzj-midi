package org.ywzj.midi.instrument;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.instrument.receiver.BassDrumMidiReceiver;
import org.ywzj.midi.instrument.receiver.MidiReceiver;

public class BassDrum extends Instrument {

    public BassDrum(String name, boolean loop, boolean portable, String keyStart, String keyEnd) {
        super(name, loop, portable, keyStart, keyEnd);
    }

    @Override
    public MidiReceiver receiver(LivingEntity player, Vec3 pos) {
        return new BassDrumMidiReceiver(this, player, pos);
    }

}
