package org.ywzj.midi.pose.action;

import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.pose.PoseManager;

public class ViolaPlayPose extends ViolPlayPose {

    private long lastTimeStamp = 0;

    public ViolaPlayPose(LivingEntity player) {
        super(player);
    }

    @Override
    public void getPullPushPoses() {
        for (float rot = -0.8f; rot <= -0.3f; rot += 0.025) {
            double bias = (rot + 0.8) / 0.5;
            pullPushPoses.add(new PoseManager.PlayPose(null,null,null,-1.2f,null,-0.8f,(float) (-4f - bias * 2),null,(float) (-1f + bias * 2),(float) (-1.67f + bias * 0.25),rot,0f));
        }
    }

    @Override
    public void getPizzPoses() {
        for (float rot = 0f; rot <= 0.24f; rot += 0.04f) {
            pizzPoses.add(new PoseManager.PlayPose(null,null,null,-1.2f,null,-0.8f,-4f,null,-1f,-1.9f - rot,-0.9f,0f));
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
