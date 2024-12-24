package org.ywzj.midi.all;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.ywzj.midi.YwzjMidi;
import org.ywzj.midi.audio.ServerSoundManager;
import org.ywzj.midi.item.MusicPlayerItem;

import java.util.UUID;

public class AllEvents {

    @Mod.EventBusSubscriber(modid = YwzjMidi.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class AllForgeEvents {

        @SubscribeEvent
        public static void onDrop(ItemTossEvent event) {
            ItemStack itemStack = event.getEntity().getItem();
            if (itemStack.getItem() instanceof MusicPlayerItem) {
                UUID musicPlayerUuid = MusicPlayerItem.getUUID(itemStack);
                if (musicPlayerUuid != null) {
                    ServerSoundManager.PLAY_ENTITIES.put(musicPlayerUuid, event.getEntity());
                }
            }
        }

        @SubscribeEvent
        public static void onPickup(EntityItemPickupEvent event) {
            ItemStack itemStack = event.getItem().getItem();
            if (itemStack.getItem() instanceof MusicPlayerItem) {
                UUID musicPlayerUuid = MusicPlayerItem.getUUID(itemStack);
                if (musicPlayerUuid != null) {
                    ServerSoundManager.PLAY_ENTITIES.put(musicPlayerUuid, event.getEntity());
                }
            }
        }

    }

    @Mod.EventBusSubscriber(modid = YwzjMidi.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class AllModEvents {

        @SubscribeEvent
        public static void entityAttributes(EntityAttributeCreationEvent event) {
            event.put(AllEntities.FAKE_PLAYER.get(), Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).build());
        }

    }

}
