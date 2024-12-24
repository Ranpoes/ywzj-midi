package org.ywzj.midi.instrument.receiver;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.audio.NotePlayer;
import org.ywzj.midi.instrument.Instrument;
import org.ywzj.midi.pose.action.CymbalPlayPose;
import org.ywzj.midi.util.MidiUtils;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import java.util.UUID;

public class CymbalMidiReceiver extends MidiReceiver {

    private final CymbalPlayPose cymbalPlayPose;

    public CymbalMidiReceiver(Instrument instrument, LivingEntity player, Vec3 pos) {
        super(instrument, player, pos);
        this.cymbalPlayPose = new CymbalPlayPose(player);
    }

    @Override
    public void send(MidiMessage message, long timeStamp, int delay) {
        if (message instanceof ShortMessage shortMessage) {
            int command = shortMessage.getCommand();
            if (command == ShortMessage.NOTE_ON) {
                int note = shortMessage.getData1();
                int velocity = shortMessage.getData2();
                if (velocity == 0) {
                    UUID uuid = playedKeys.get(note);
                    if (uuid != null) {
                        NotePlayer.stopNote(uuid, player);
                        playedKeys.remove(note);
                    }
                    return;
                }
                cymbalPlayPose.hit();
                UUID uuid = UUID.randomUUID();
                playNote(MidiUtils.notationToNote("c1"), velocity, delay);
                playedKeys.put(note, uuid);
            } else if (command == ShortMessage.NOTE_OFF) {
                int note = shortMessage.getData1();
                UUID uuid = playedKeys.get(note);
                if (uuid != null) {
                    NotePlayer.stopNote(uuid, player);
                    playedKeys.remove(note);
                }
            }
        }
    }

    @Override
    public void stopPose() {

    }

}
