package org.ywzj.midi.instrument.receiver;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.instrument.Instrument;
import org.ywzj.midi.pose.action.OboePlayPose;
import org.ywzj.midi.pose.action.WoodwindPlayPose;

public class OboeMidiReceiver extends WoodwindMidiReceiver {

    public OboeMidiReceiver(Instrument instrument, LivingEntity player, Vec3 pos) {
        super(instrument, player, pos);
    }

    @Override
    public WoodwindPlayPose getWoodwindPlayPose(LivingEntity player) {
        return new OboePlayPose(player);
    }

}
