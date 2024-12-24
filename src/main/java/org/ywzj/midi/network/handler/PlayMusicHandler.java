package org.ywzj.midi.network.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.ywzj.midi.audio.ClientPlayerInstance;
import org.ywzj.midi.audio.MusicPlayer;
import org.ywzj.midi.audio.ServerSoundManager;
import org.ywzj.midi.network.message.CPlayMusic;
import org.ywzj.midi.network.message.SPlayMusic;

import java.util.function.Supplier;

public class PlayMusicHandler {

    public static void onClientMessageReceived(CPlayMusic message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.setPacketHandled(true);
        ctx.enqueueWork(() -> processClientMessage(ctxSupplier.get().getSender(), message));
    }

    private static void processClientMessage(ServerPlayer sender, CPlayMusic message) {
        ServerSoundManager.dispatch(sender, message);
    }

    public static void onServerMessageReceived(SPlayMusic message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.setPacketHandled(true);
        ctx.enqueueWork(() -> processServerMessage(ctxSupplier.get().getSender(), message));
    }

    private static void processServerMessage(ServerPlayer sender, SPlayMusic message) {
        if (message.on) {
            try {
                ClientPlayerInstance playerInstance = new ClientPlayerInstance();
                playerInstance.soundUuid = message.soundUuid;
                playerInstance.deviceUuid = message.soundUuid;
                playerInstance.portable = message.portable;
                MusicPlayer.playClientNetStream(playerInstance, new Vec3(message.x, message.y, message.z), message.url, message.musicName, message.offset, false);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            MusicPlayer.stopStream(message.soundUuid);
        }
    }

}
