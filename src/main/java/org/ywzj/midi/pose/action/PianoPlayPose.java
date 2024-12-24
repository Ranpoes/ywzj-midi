package org.ywzj.midi.pose.action;

import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.pose.PoseManager;

import java.util.List;
import java.util.stream.Collectors;

public class PianoPlayPose {

    public static void handle(LivingEntity player, List<Integer> posePlayNotes) {
        posePlayNotes = posePlayNotes.stream().map(i -> i-=21).collect(Collectors.toList());
        double average = posePlayNotes.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        if (posePlayNotes.size() < 5) {
            if (average < 44) {
                PoseManager.publish(player, calHandPose(average, null));
            } else {
                PoseManager.publish(player, calHandPose(null, average));
            }
            return;
        }
        List<Integer> leftHandNotes = posePlayNotes.stream()
                .filter(number -> number <= average)
                .collect(Collectors.toList());
        List<Integer> rightHandNotes = posePlayNotes.stream()
                .filter(number -> number > average)
                .collect(Collectors.toList());
        double posLeftHand = leftHandNotes.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
        double posRightHand = rightHandNotes.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
        PoseManager.publish(player, calHandPose(posLeftHand, posRightHand));
    }

    private static PoseManager.PlayPose calHandPose(Double posLeftHand, Double posRightHand) {
        PoseManager.PlayPose pose = new PoseManager.PlayPose();
        pose.leftArmRotX = -1.1f;
        pose.rightArmRotX = -1.1f;
        pose.leftArmZ = -2.2f;
        pose.leftArmY = 3.5f;
        pose.rightArmZ = -2.2f;
        pose.rightArmY = 3.5f;
        if (posLeftHand != null) {
            float r = (float) ((33 - posLeftHand) / 33);
            pose.leftArmRotZ = -1.3f * r;
            pose.leftArmRotX = -1.1f + 0.4f * r;
            pose.leftArmZ = -2.2f - 2f * r;
        }
        if (posRightHand != null) {
            float r = (float) ((posRightHand - 55) / 33);
            pose.rightArmRotZ = 1.3f * r;
            pose.rightArmRotX = -1.1f + 0.4f * r;
            pose.rightArmZ = -2.2f - 2f * r;
        }
        return pose;
    }

}
