package org.ywzj.midi.pose.action;

import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.pose.PoseManager;

public class BrassPlayPose {

    protected final LivingEntity player;
    public PoseManager.PlayPose playPose;

    public BrassPlayPose(LivingEntity player) {
        this.player = player;
    }

    public void play(int note) {
        PoseManager.publish(player, playPose);
    }

    public void stop() {
        PoseManager.clearCache(player);
    }

}
