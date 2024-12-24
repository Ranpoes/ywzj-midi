package org.ywzj.midi.instrument.receiver;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.instrument.Instrument;
import org.ywzj.midi.pose.action.ViolPlayPose;
import org.ywzj.midi.pose.action.ViolaPlayPose;

public class ViolaMidiReceiver extends ViolMidiReceiver {

    public ViolaMidiReceiver(Instrument instrument, LivingEntity player, Vec3 pos) {
        super(instrument, player, pos);
    }

    @Override
    public ViolPlayPose getViolPlayPose(LivingEntity player) {
        return new ViolaPlayPose(player);
    }

}
