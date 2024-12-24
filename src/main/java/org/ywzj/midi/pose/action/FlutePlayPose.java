package org.ywzj.midi.pose.action;

import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.pose.PoseManager;

public class FlutePlayPose extends WoodwindPlayPose {

    public FlutePlayPose(LivingEntity player) {
        super(player);
        super.playPose = new PoseManager.PlayPose(1.5f,4.5f,-3f,-2f,1.2f,0f,null, 5.5f,null, -2.4f, 0.8f,0f);
    }

}
