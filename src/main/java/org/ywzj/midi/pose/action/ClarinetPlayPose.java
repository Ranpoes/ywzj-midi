package org.ywzj.midi.pose.action;

import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.pose.PoseManager;

public class ClarinetPlayPose extends WoodwindPlayPose {

    public ClarinetPlayPose(LivingEntity player) {
        super(player);
        super.playPose = new PoseManager.PlayPose(null,null,null,-1.2f,0.5f,0f,null,null,null, -1.2f, -0.5f,0f);
    }

}
