package org.ywzj.midi.instrument.receiver;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.instrument.Instrument;
import org.ywzj.midi.pose.action.BrassPlayPose;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public abstract class BrassMidiReceiver extends MidiReceiver {

    private final BrassPlayPose brassPlayPose;

    public BrassMidiReceiver(Instrument instrument, LivingEntity player, Vec3 pos) {
        super(instrument, player, pos);
        this.brassPlayPose = getBrassPlayPose(player);
    }

    public abstract BrassPlayPose getBrassPlayPose(LivingEntity player);

    @Override
    public void send(MidiMessage message, long timeStamp, int delay) {
        if (message instanceof ShortMessage shortMessage) {
            int command = shortMessage.getCommand();
            if (command == ShortMessage.NOTE_ON) {
                int note = shortMessage.getData1();
                int velocity = shortMessage.getData2();
                if (velocity == 0) {
                    stopNote(note);
                    return;
                }
                playNote(note, velocity, delay);
                brassPlayPose.play(note);
            } else if (command == ShortMessage.NOTE_OFF) {
                int note = shortMessage.getData1();
                stopNote(note);
            }
        }
    }

    @Override
    public void stopPose() {
        brassPlayPose.stop();
    }

}
