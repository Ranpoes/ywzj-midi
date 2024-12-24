package org.ywzj.midi.all;

import net.minecraft.world.InteractionHand;
import org.ywzj.midi.pose.PoseManager;

public class AllHoldPose {

    static {
        PoseManager.registerHoldPose(AllInstruments.CELLO,
                InteractionHand.OFF_HAND,
                new PoseManager.PlayPose(3.2f,null,1f,-1.6f,0f,0.1f,null,null,null,null,null,null));
        PoseManager.registerHoldPose(AllInstruments.DOUBLE_BASS,
                InteractionHand.OFF_HAND,
                new PoseManager.PlayPose(3.2f,null,1f,-2f,0f,0.1f,null,null,null,null,null,null));
        PoseManager.registerHoldPose(AllInstruments.FLUTE,
                InteractionHand.MAIN_HAND,
                new PoseManager.PlayPose(null,null,null,null,null,null,null,null,null,-0.6f,null,null));
        PoseManager.registerHoldPose(AllInstruments.BASSOON,
                InteractionHand.MAIN_HAND,
                new PoseManager.PlayPose(6f,4f,-0f,-0.8f,0.7f,0f,-6f,4f,null,-0.9f,-0.8f,0.3f));
        PoseManager.registerHoldPose(AllInstruments.HORN,
                InteractionHand.MAIN_HAND,
                new PoseManager.PlayPose(null,3f,-3f,-1f,0.6f,0f,null,4f,1.5f,-0.6f,0.2f,0.3f));
        PoseManager.registerHoldPose(AllInstruments.TRUMPET,
                InteractionHand.MAIN_HAND,
                new PoseManager.PlayPose(null,4f,null,-1.2f,0.5f,0f,null,4f,null,-1.2f,-0.5f,0f));
        PoseManager.registerHoldPose(AllInstruments.TROMBONE,
                InteractionHand.OFF_HAND,
                new PoseManager.PlayPose(null,4f,null,-1f,0.5f,0f,null,4f,null,-1f,-0.5f,0f));
        PoseManager.registerHoldPose(AllInstruments.TUBA,
                InteractionHand.MAIN_HAND,
                new PoseManager.PlayPose(null,null,null,-0.3f,null,0f,null,null,null,-1.0f,null,0f));
        PoseManager.registerHoldPose(AllInstruments.CYMBAL,
                InteractionHand.MAIN_HAND,
                new PoseManager.PlayPose(null,null,null,-1.0f,0f,0.02f,null,null,null,-1.0f,0f,-0.02f));
    }

    public static void preRegister() {}

}
