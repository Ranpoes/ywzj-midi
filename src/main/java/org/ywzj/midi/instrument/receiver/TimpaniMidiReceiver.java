package org.ywzj.midi.instrument.receiver;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.instrument.Instrument;
import org.ywzj.midi.pose.action.TimpaniPlayPose;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public class TimpaniMidiReceiver extends MidiReceiver {

    private final TimpaniPlayPose timpaniPlayPose;

    public TimpaniMidiReceiver(Instrument instrument, LivingEntity player, Vec3 pos) {
        super(instrument, player, pos);
        this.timpaniPlayPose = new TimpaniPlayPose(player);
    }

    @Override
    public void send(MidiMessage message, long timeStamp, int delay) {
        if (message instanceof ShortMessage shortMessage) {
            int command = shortMessage.getCommand();
            if (command == ShortMessage.NOTE_ON) {
                int note = shortMessage.getData1();
                int velocity = shortMessage.getData2();
                if (velocity == 0) {
                    stopNote(note);
                    if (playedKeys.size() == 0) {
                        timpaniPlayPose.reset();
                    }
                    return;
                }
                timpaniPlayPose.hit(note);
                playNote(note, velocity, delay);
            } else if (command == ShortMessage.NOTE_OFF) {
                int note = shortMessage.getData1();
                stopNote(note);
                if (playedKeys.size() == 0) {
                    timpaniPlayPose.reset();
                }
            }
        }
    }

    @Override
    public void stopPose() {
        return;
    }

}
