package org.ywzj.midi.pose.action;

import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.pose.PoseManager;

public class BassoonPlayPose extends WoodwindPlayPose {

    public BassoonPlayPose(LivingEntity player) {
        super(player);
        super.playPose = new PoseManager.PlayPose(5f,4f,-0f,-1.2f,0.7f,0f,-6f,4f,null, -0.6f, -0.2f,0f);
    }

}
