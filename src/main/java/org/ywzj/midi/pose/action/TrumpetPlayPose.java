package org.ywzj.midi.pose.action;

import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.pose.PoseManager;

public class TrumpetPlayPose extends BrassPlayPose {

    public TrumpetPlayPose(LivingEntity player) {
        super(player);
        super.playPose = new PoseManager.PlayPose(null,4f,null,-1.8f,0.5f,0f,null,4f,null, -1.8f, -0.5f,0f);
    }

}
