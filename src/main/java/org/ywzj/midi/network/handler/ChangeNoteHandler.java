package org.ywzj.midi.network.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import org.ywzj.midi.audio.NotePlayer;
import org.ywzj.midi.network.Channel;
import org.ywzj.midi.network.message.CChangeNote;
import org.ywzj.midi.network.message.SChangeNote;

import java.util.function.Supplier;

public class ChangeNoteHandler {

    public static void onClientMessageReceived(CChangeNote message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.setPacketHandled(true);
        ctx.enqueueWork(() -> broadcastNote(ctxSupplier.get().getSender(), message, true));
    }

    public static void broadcastNote(LivingEntity sender, CChangeNote message, boolean fromClient) {
        for (ServerPlayer player : sender.level().getServer().getPlayerList().getPlayers()) {
            if (fromClient && player.equals(sender)) {
                continue;
            }
            Channel.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SChangeNote(message));
        }
    }

    public static void onServerMessageReceived(SChangeNote message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.setPacketHandled(true);
        ctx.enqueueWork(() -> processServerMessage(ctxSupplier.get().getSender(), message));
    }

    private static void processServerMessage(ServerPlayer sender, SChangeNote message) {
        NotePlayer.changeClientNote(message.uuid, message.velScale);
    }

}
