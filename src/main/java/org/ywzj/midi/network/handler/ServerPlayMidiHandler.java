package org.ywzj.midi.network.handler;

import net.minecraftforge.network.NetworkEvent;
import org.ywzj.midi.entity.FakePlayerEntity;
import org.ywzj.midi.gui.screen.ServerMidiScreen;
import org.ywzj.midi.instrument.player.ServerMidiPlayer;
import org.ywzj.midi.network.message.CPlayMidi;

import java.util.function.Supplier;
public class ServerPlayMidiHandler {

    public static void onServerMessageReceived(CPlayMidi message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        if (ctx.getSender().level().getEntity(message.conductorId) instanceof FakePlayerEntity conductor) {
            if (conductor.serverMidiPlayer == null) {
                conductor.serverMidiPlayer = new ServerMidiPlayer(ctx.getSender(), conductor);
            }
            ServerMidiPlayer midiPlayer = conductor.serverMidiPlayer;
            if (message.opCode == ServerMidiScreen.OpCode.PLAY.value) {
                midiPlayer.init(message);
                midiPlayer.task(3);
            } else if (message.opCode == ServerMidiScreen.OpCode.STOP.value) {
                midiPlayer.stopPlay();
            } else if (message.opCode == ServerMidiScreen.OpCode.LOOP.value) {
                midiPlayer.setLoop();
            }
        }
     }

}
