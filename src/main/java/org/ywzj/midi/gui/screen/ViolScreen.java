package org.ywzj.midi.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.audio.NotePlayer;
import org.ywzj.midi.instrument.Instrument;
import org.ywzj.midi.instrument.receiver.ViolMidiReceiver;
import org.ywzj.midi.pose.action.ViolPlayPose;
import org.ywzj.midi.util.MidiUtils;

import java.util.UUID;

public class ViolScreen extends PlaySustainScreen {

    private final ViolPlayPose violPlayPose;

    public ViolScreen(Instrument instrument, Vec3 pos, Component titleIn, String keyStart, String keyEnd) {
        super(instrument, pos, titleIn, keyStart, keyEnd);
        this.violPlayPose = ((ViolMidiReceiver) this.receiver).getViolPlayPose(Minecraft.getInstance().player);
    }

    protected void playNoteSwitch(String notation) {
        if (notes.containsKey(notation)) {
            NotePlayer.stopNote(notes.remove(notation), getMinecraft().player);
            violPlayPose.stop();
        } else {
            UUID uuid = UUID.randomUUID();
            int variantId = variantSelectButton.getValue();
            NotePlayer.playNote(uuid, Minecraft.getInstance().player.position(), instrument, variantId, MidiUtils.notationToNote(notation), (float) Math.pow((double) velocitySlider.value / 127, 2), 0, getMinecraft().player);
            if (instrument.getVariant(variantId).isLoop()) {
                violPlayPose.loopPullPush();
                notes.put(notation, uuid);
            } else {
                violPlayPose.pizz();
            }
        }
    }

}
