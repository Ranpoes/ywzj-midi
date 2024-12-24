package org.ywzj.midi.network.handler;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import org.ywzj.midi.entity.FakePlayerEntity;
import org.ywzj.midi.network.message.CFakePlayerUpdate;

import java.util.function.Supplier;

public class FakePlayerDataHandler {

    public static void onServerMessageReceived(CFakePlayerUpdate message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        Entity entity = ctx.getSender().level().getEntity(message.id);
        if (entity instanceof FakePlayerEntity fakePlayerEntity) {
            fakePlayerEntity.getEntityData().set(FakePlayerEntity.SKIN_URL, message.skinUrl);
            fakePlayerEntity.getEntityData().set(FakePlayerEntity.NAME, message.name);
            fakePlayerEntity.getEntityData().set(FakePlayerEntity.IS_SITTING, message.isSitting);
        }
    }

}
