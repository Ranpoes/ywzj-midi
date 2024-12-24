package org.ywzj.midi.instrument;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.instrument.receiver.ClavichordReceiver;
import org.ywzj.midi.instrument.receiver.MidiReceiver;

public class GrandPiano extends Instrument {

    public GrandPiano(String name, boolean loop, boolean portable, String keyStart, String keyEnd) {
        super(name, loop, portable, keyStart, keyEnd);
    }

    @Override
    public MidiReceiver receiver(LivingEntity player, Vec3 pos) {
        return new ClavichordReceiver(this, player, pos);
    }

}
