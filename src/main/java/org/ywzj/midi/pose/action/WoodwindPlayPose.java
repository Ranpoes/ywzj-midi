package org.ywzj.midi.pose.action;

import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.pose.PoseManager;

public abstract class WoodwindPlayPose {

    private final LivingEntity player;
    public PoseManager.PlayPose playPose;

    public WoodwindPlayPose(LivingEntity player) {
        this.player = player;
    }

    public void play() {
        PoseManager.publish(player, playPose);
    }

    public void stop() {
        PoseManager.clearCache(player);
    }

}
