package org.ywzj.midi.instrument.receiver;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.instrument.Instrument;
import org.ywzj.midi.pose.action.BrassPlayPose;
import org.ywzj.midi.pose.action.TrumpetPlayPose;

public class TrumpetMidiReceiver extends BrassMidiReceiver {

    public TrumpetMidiReceiver(Instrument instrument, LivingEntity player, Vec3 pos) {
        super(instrument, player, pos);
    }

    @Override
    public BrassPlayPose getBrassPlayPose(LivingEntity player) {
        return new TrumpetPlayPose(player);
    }

}
