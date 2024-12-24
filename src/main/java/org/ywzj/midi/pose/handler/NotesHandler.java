package org.ywzj.midi.pose.handler;

import org.ywzj.midi.instrument.Instrument;

import java.util.List;
import java.util.UUID;

public abstract class NotesHandler {

    public abstract Instrument getInstrument();

    public abstract void handle(UUID playerUuid, List<Integer> notes);

}
