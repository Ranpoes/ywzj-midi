package org.ywzj.midi;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.ywzj.midi.all.*;
import org.ywzj.midi.network.Channel;
import org.ywzj.midi.render.renderer.FakePlayerRenderer;
import org.ywzj.midi.render.renderer.SeatRenderer;

@Mod(YwzjMidi.MODID)
public class YwzjMidi
{

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "ywzj_midi";
    public static final String PROTOCOL = "1.6";
    public static final String CHANNEL = "ywzj_midi_channel";

    public YwzjMidi()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        AllInstruments.preRegister();
        AllHoldPose.preRegister();
        AllNotesHandler.preRegister();
        AllConfigs.register(ModLoadingContext.get());
        AllBlockEntities.register(eventBus);
        AllEntities.register(eventBus);
        AllTabs.register(eventBus);
        register(eventBus, MODID);
        eventBus.register(Channel.class);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
    }

    public static void register(IEventBus eventBus, String namespace) {
        AllItems.register(eventBus, namespace);
        AllBlocks.register(eventBus, namespace);
        AllSounds.register(eventBus, namespace);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> EntityRenderers.register(AllEntities.SEAT.get(), SeatRenderer::new));
        event.enqueueWork(() -> EntityRenderers.register(AllEntities.FAKE_PLAYER.get(), FakePlayerRenderer::new));
    }

}
