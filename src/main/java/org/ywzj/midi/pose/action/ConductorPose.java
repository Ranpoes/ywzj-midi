package org.ywzj.midi.pose.action;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.pose.PoseManager;

import java.util.ArrayList;
import java.util.List;

public class ConductorPose {

    public LivingEntity player;
    public final List<PoseManager.PlayPose> poses = new ArrayList<>();
    private int step = 0;
    public boolean loop = false;
    public boolean pause = false;
    private int velocity = 0;
    private double bpm = 0;
    private final float leftArmBaseRotX = -0.8f;
    private LivingEntity lookAtPlayer;

    public ConductorPose(LivingEntity player) {
        this.player = player;
        for (int i = 0; i < 12; i++) {
            poses.add(new PoseManager.PlayPose(null,null,null, leftArmBaseRotX,null,null,null,null, null, (float) (-0.8f - 0.05 * i), null,0f));
        }
        for (int i = 0; i < 12; i++) {
            poses.add(new PoseManager.PlayPose(null,null,null, leftArmBaseRotX,null,null,null,null, null, (float) (-1.4f + 0.05 * i), null,0f));
        }
        for (int i = 0; i < 12; i++) {
            poses.add(new PoseManager.PlayPose(null,null,null, leftArmBaseRotX,null,null,null,null, null, -0.9f, (float) (0.03 * i), null));
        }
        for (int i = 0; i < 12; i++) {
            poses.add(new PoseManager.PlayPose(null,null,null, leftArmBaseRotX,null,null,null,null, null, -0.9f, (float) (0.36 - 0.03 * i), null));
        }
    }

    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }

    public void setLookAtPlayer(LivingEntity lookAtPlayer) {
        this.lookAtPlayer = lookAtPlayer;
    }

    public void loop() {
        loop = true;
        new Thread(() -> {
            while (loop) {
                for (int i = 0; i < poses.size(); i++) {
                    try {
                        Thread.sleep((long) (60000 / bpm / 24));
                    } catch (Exception ignore) {}
                    if (!pause) {
                        PoseManager.publish(player, getNextPose());
                    }
                }
            }
        }).start();
    }

    public void pause(boolean isPause) {
        pause = isPause;
    }

    public void stop() {
        loop = false;
    }

    private synchronized PoseManager.PlayPose getNextPose() {
        if (lookAtPlayer != null) {
            player.lookAt(EntityAnchorArgument.Anchor.EYES, EntityAnchorArgument.Anchor.EYES.apply(lookAtPlayer));
        }
        step = (step + 1) % poses.size();
        PoseManager.PlayPose pose = poses.get(step);
        pose.leftArmRotX = leftArmBaseRotX - ((float) velocity / 127 * 1);
        return poses.get(step);
    }

}
