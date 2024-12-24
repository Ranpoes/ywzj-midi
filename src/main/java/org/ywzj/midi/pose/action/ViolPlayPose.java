package org.ywzj.midi.pose.action;

import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.pose.PoseManager;

import java.util.ArrayList;
import java.util.List;

public abstract class ViolPlayPose {

    private final LivingEntity player;
    private int step = 0;
    private boolean isPull = false;
    public boolean loop = true;
    public boolean pause = false;
    public final List<PoseManager.PlayPose> pullPushPoses = new ArrayList<>();
    public final List<PoseManager.PlayPose> pizzPoses = new ArrayList<>();

    public ViolPlayPose(LivingEntity player) {
        this.player = player;
        getPullPushPoses();
        getPizzPoses();
    }

    public abstract void handle(long timeStamp);

    public void loopPullPush() {
        loop = true;
        new Thread(() -> {
            while (loop) {
                for (int i = 0; i < pullPushPoses.size() * 2; i += 1) {
                    try {
                        Thread.sleep(50);
                    } catch (Exception ignore) {}
                    if (!pause) {
                        PoseManager.publish(player, getNextPose());
                    }
                }
            }
        }).start();
    }

    public void pizz() {
        new Thread(() -> {
            for (PoseManager.PlayPose pose : pizzPoses) {
                PoseManager.publish(player, pose);
            }
        }).start();
    }

    public void pause() {
        pause = true;
    }

    public void stop() {
        loop = false;
    }

    public abstract void getPullPushPoses();

    public abstract void getPizzPoses();

    private synchronized PoseManager.PlayPose getNextPose() {
        if (step == pullPushPoses.size() - 1) {
            isPull = false;
        } else if (step == 0) {
            isPull = true;
        }
        step += isPull ? 1 : -1;
        return pullPushPoses.get(step);
    }

    public void resetPose() {
        step = 0;
        isPull = true;
    }

}
