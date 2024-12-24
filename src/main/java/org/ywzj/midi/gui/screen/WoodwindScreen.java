package org.ywzj.midi.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.audio.NotePlayer;
import org.ywzj.midi.instrument.Instrument;
import org.ywzj.midi.instrument.receiver.WoodwindMidiReceiver;
import org.ywzj.midi.pose.action.WoodwindPlayPose;
import org.ywzj.midi.util.MidiUtils;

import java.util.UUID;

public class WoodwindScreen extends PlaySustainScreen {

    private final WoodwindPlayPose woodwindPlayPose;

    public WoodwindScreen(Instrument instrument, Vec3 pos, Component titleIn, String keyStart, String keyEnd) {
        super(instrument, pos, titleIn, keyStart, keyEnd);
        this.woodwindPlayPose = ((WoodwindMidiReceiver) this.receiver).getWoodwindPlayPose(Minecraft.getInstance().player);
    }

    @Override
    protected void playNoteSwitch(String notation) {
        if (notes.containsKey(notation)) {
            NotePlayer.stopNote(notes.remove(notation), getMinecraft().player);
            woodwindPlayPose.stop();
        } else {
            UUID uuid = UUID.randomUUID();
            NotePlayer.playNote(uuid, Minecraft.getInstance().player.position(), instrument, variantSelectButton.getValue(), MidiUtils.notationToNote(notation), (float) Math.pow((double) velocitySlider.value / 127, 2), 0, getMinecraft().player);
            woodwindPlayPose.play();
            notes.put(notation, uuid);
        }
    }

}
