package org.ywzj.midi.pose.action;

import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.pose.PoseManager;

import java.util.ArrayList;
import java.util.List;

public class CymbalPlayPose {

    private final LivingEntity player;
    public final List<PoseManager.PlayPose> strikePoses = new ArrayList<>();

    public CymbalPlayPose(LivingEntity player) {
        this.player = player;
        PoseManager.PlayPose basePose = new PoseManager.PlayPose(null,null,null,-1.0f,0f,0.02f,null,null,null,-1.0f,0f,-0.02f);
        for (int i = 0; i < 5; i++) {
            basePose.leftArmRotY += 0.06f;
            basePose.rightArmRotY -= 0.06f;
            strikePoses.add(new PoseManager.PlayPose(basePose));
        }
    }

    public void hit() {
        for (int i = 0; i <= 4; i++) {
            PoseManager.publish(player, strikePoses.get(i));
        }
        for (int i = 3; i >= 0; i--) {
            PoseManager.publish(player, strikePoses.get(i));
        }
    }

}
