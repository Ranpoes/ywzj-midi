package org.ywzj.midi.instrument.player;

import net.minecraft.client.Minecraft;
import org.ywzj.midi.gui.screen.MidiInstrumentScreen;

public class InstrumentMidiPlayer extends MidiPlayer {

    private final MidiInstrumentScreen midiInstrumentScreen;

    public InstrumentMidiPlayer(MidiInstrumentScreen midiInstrumentScreen) {
        super(Minecraft.getInstance().player);
        this.midiInstrumentScreen = midiInstrumentScreen;
    }

    @Override
    public void handleStep() {}

    @Override
    public void endCallback() {
        midiInstrumentScreen.callbackPlayButton();
    }

}
