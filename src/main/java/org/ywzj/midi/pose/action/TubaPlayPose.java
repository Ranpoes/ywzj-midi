package org.ywzj.midi.pose.action;

import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.pose.PoseManager;

public class TubaPlayPose extends BrassPlayPose {

    public TubaPlayPose(LivingEntity player) {
        super(player);
        super.playPose = new PoseManager.PlayPose(null,null,null,-0.4f,null,0f,null,null,null, -1.1f, null,0f);
    }

}
