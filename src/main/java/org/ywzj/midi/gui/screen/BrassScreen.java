package org.ywzj.midi.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.audio.NotePlayer;
import org.ywzj.midi.instrument.Instrument;
import org.ywzj.midi.instrument.receiver.BrassMidiReceiver;
import org.ywzj.midi.pose.action.BrassPlayPose;
import org.ywzj.midi.util.MidiUtils;

import java.util.UUID;

public class BrassScreen extends PlaySustainScreen {

    private final BrassPlayPose brassPlayPose;

    public BrassScreen(Instrument instrument, Vec3 pos, Component titleIn, String keyStart, String keyEnd) {
        super(instrument, pos, titleIn, keyStart, keyEnd);
        this.brassPlayPose = ((BrassMidiReceiver) this.receiver).getBrassPlayPose(Minecraft.getInstance().player);
    }

    @Override
    protected void playNoteSwitch(String notation) {
        if (notes.containsKey(notation)) {
            NotePlayer.stopNote(notes.remove(notation), getMinecraft().player);
            brassPlayPose.stop();
        } else {
            UUID uuid = UUID.randomUUID();
            int note = MidiUtils.notationToNote(notation);
            NotePlayer.playNote(uuid, Minecraft.getInstance().player.position(), instrument, variantSelectButton.getValue(), note, (float) Math.pow((double) velocitySlider.value / 127, 2), 0, getMinecraft().player);
            brassPlayPose.play(note);
            notes.put(notation, uuid);
        }
    }

}
