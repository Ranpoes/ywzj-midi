package org.ywzj.midi.pose.action;

import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.pose.PoseManager;

public class CelloPlayPose extends ViolPlayPose {

    private long lastTimeStamp = 0;

    public CelloPlayPose(LivingEntity player) {
        super(player);
    }

    @Override
    public void getPullPushPoses() {
        for (float rot = -0.25f; rot <= 0.25f; rot += 0.025) {
            double bias = (rot + 0.25) / 0.5;
            pullPushPoses.add(new PoseManager.PlayPose(null,null,null,null,null,null,null,5f,(float) (-3f + bias * 4.5f),(float) (-1.1f - bias * 0.05),rot,0f));
        }
    }

    @Override
    public void getPizzPoses() {
        for (float rot = 0f; rot <= 0.24f; rot += 0.04f) {
            pizzPoses.add(new PoseManager.PlayPose(null,null,2.3f,null,null,null,null,5f,-3.5f,-1.3f - rot,-0.8f,0f));
        }
    }

    @Override
    public void handle(long timeStamp) {
        if (lastTimeStamp == 0 || timeStamp < lastTimeStamp) {
            loopPullPush();
        }
        if (pause) {
            pause = false;
        }
        if (timeStamp - lastTimeStamp > 4000) {
            resetPose();
        }
        lastTimeStamp = timeStamp;
    }

}
