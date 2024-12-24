package org.ywzj.midi.instrument.receiver;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.instrument.Instrument;
import org.ywzj.midi.pose.action.DoubleBassPlayPose;
import org.ywzj.midi.pose.action.ViolPlayPose;

public class DoubleBassMidiReceiver extends ViolMidiReceiver {

    public DoubleBassMidiReceiver(Instrument instrument, LivingEntity player, Vec3 pos) {
        super(instrument, player, pos);
    }

    @Override
    public ViolPlayPose getViolPlayPose(LivingEntity player) {
        return new DoubleBassPlayPose(player);
    }

}
