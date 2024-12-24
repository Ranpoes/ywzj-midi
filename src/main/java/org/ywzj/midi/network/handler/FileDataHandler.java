package org.ywzj.midi.network.handler;

import net.minecraftforge.network.NetworkEvent;
import org.ywzj.midi.network.message.CFileData;
import org.ywzj.midi.util.FileTransfer;

import java.util.function.Supplier;

public class FileDataHandler {

    public static void onServerMessageReceived(CFileData message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        FileTransfer.receive(ctx.getSender(), message);
    }

}
