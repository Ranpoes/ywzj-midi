package org.ywzj.midi.network.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.ywzj.midi.audio.MusicPlayer;
import org.ywzj.midi.audio.ServerSoundManager;
import org.ywzj.midi.network.message.CSyncMusic;
import org.ywzj.midi.network.message.SSyncMusic;

import java.util.function.Supplier;

public class SyncMusicHandler {

    public static void onClientMessageReceived(CSyncMusic message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.setPacketHandled(true);
        ctx.enqueueWork(() -> processClientMessage(ctxSupplier.get().getSender(), message));
    }

    private static void processClientMessage(ServerPlayer sender, CSyncMusic message) {
        ServerSoundManager.dispatch(sender, message);
    }

    public static void onServerMessageReceived(SSyncMusic message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.setPacketHandled(true);
        ctx.enqueueWork(() -> processServerMessage(ctxSupplier.get().getSender(), message));
    }

    private static void processServerMessage(ServerPlayer sender, SSyncMusic message) {
        if (message.on) {
            MusicPlayer.playOtherClientStream(message.soundUuid, new Vec3(message.x, message.y, message.z), message.sampleRate, message.frameRate, message.musicName, message.bytes);
        } else {
            MusicPlayer.stopStream(message.soundUuid);
        }
    }

}
