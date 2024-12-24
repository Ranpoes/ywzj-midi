package org.ywzj.midi.instrument.receiver;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.audio.NotePlayer;
import org.ywzj.midi.instrument.Instrument;
import org.ywzj.midi.pose.action.BassDrumPlayPose;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import java.util.UUID;

public class BassDrumMidiReceiver extends MidiReceiver {

    private final BassDrumPlayPose bassDrumPlayPose;

    public BassDrumMidiReceiver(Instrument instrument, LivingEntity player, Vec3 pos) {
        super(instrument, player, pos);
        this.bassDrumPlayPose = new BassDrumPlayPose(player);
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
                    if (playedKeys.size() == 0) {
                        bassDrumPlayPose.reset();
                    }
                    return;
                }
                bassDrumPlayPose.hit();
                UUID uuid = UUID.randomUUID();
                NotePlayer.playNote(uuid, portable ? player.position() : pos, instrument, variantId, toNote(velocity), 1f, delay, player);
                playedKeys.put(note, uuid);
            } else if (command == ShortMessage.NOTE_OFF) {
                int note = shortMessage.getData1();
                UUID uuid = playedKeys.get(note);
                if (uuid != null) {
                    NotePlayer.stopNote(uuid, player);
                    playedKeys.remove(note);
                }
                if (playedKeys.size() == 0) {
                    bassDrumPlayPose.reset();
                }
            }
        }
    }

    private int toNote(int velocity) {
        return switch (velocity / 25) {
            case 0, 1 -> 29;
            case 2 -> 31;
            case 3 -> 33;
            case 4 -> 35;
            case 5 -> 36;
            default -> 36;
        };
    }

    @Override
    public void stopPose() {
        return;
    }

}
