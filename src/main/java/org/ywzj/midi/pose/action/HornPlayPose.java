package org.ywzj.midi.pose.action;

import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.pose.PoseManager;

public class HornPlayPose extends BrassPlayPose {

    public HornPlayPose(LivingEntity player) {
        super(player);
        super.playPose = new PoseManager.PlayPose(null,4f,-3f,-2f,0.6f,0f,null,3f,1.5f, -0.6f, 0.35f,0f);
    }

}
