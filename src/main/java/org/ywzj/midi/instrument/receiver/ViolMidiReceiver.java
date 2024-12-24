package org.ywzj.midi.instrument.receiver;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.instrument.Instrument;
import org.ywzj.midi.pose.action.ViolPlayPose;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public abstract class ViolMidiReceiver extends MidiReceiver {

    private final ViolPlayPose violPlayPose;

    public ViolMidiReceiver(Instrument instrument, LivingEntity player, Vec3 pos) {
        super(instrument, player, pos);
        this.violPlayPose = getViolPlayPose(player);
    }

    public abstract ViolPlayPose getViolPlayPose(LivingEntity player);

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
                        violPlayPose.pause();
                    }
                    return;
                }
                playNote(variantId, note, velocity, delay);
                if (variantId == 0) {
                    violPlayPose.handle(timeStamp);
                } else {
                    violPlayPose.pizz();
                }
            } else if (command == ShortMessage.NOTE_OFF) {
                int note = shortMessage.getData1();
                stopNote(note);
                if (playedKeys.size() == 0) {
                    violPlayPose.pause();
                }
            } else if (command == ShortMessage.PROGRAM_CHANGE) {
                int program = shortMessage.getData1();
                if (program == 45) {
                    variantId = 1;
                } else {
                    variantId = 0;
                }
            } else if (command == ShortMessage.CONTROL_CHANGE) {
                commandChange(shortMessage.getData1(), shortMessage.getData2());
            }
        }
    }

    @Override
    public void stopPose() {
        violPlayPose.stop();
    }

}
