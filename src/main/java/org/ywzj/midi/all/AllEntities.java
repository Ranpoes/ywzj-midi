package org.ywzj.midi.all;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ywzj.midi.YwzjMidi;
import org.ywzj.midi.entity.FakePlayerEntity;
import org.ywzj.midi.entity.SeatEntity;

public class AllEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, YwzjMidi.MODID);

    public static final RegistryObject<EntityType<FakePlayerEntity>> FAKE_PLAYER = ENTITIES.register("fake_player",
            () -> EntityType.Builder.of(FakePlayerEntity::new, MobCategory.CREATURE).sized(0.8f, 1.9f)
                    .clientTrackingRange(4)
                    .build("fake_player"));

    public static final RegistryObject<EntityType<SeatEntity>> SEAT = registerEntities("seat", EntityType.Builder.<SeatEntity>of((type, world) ->
            new SeatEntity(world), MobCategory.MISC)
            .sized(0.0F, 0.0F)
            .setCustomClientFactory((spawnEntity, world) -> new SeatEntity(world)));

    private static <T extends Entity> RegistryObject<EntityType<T>> registerEntities(String name, EntityType.Builder<T> builder)
    {
        return ENTITIES.register(name, () -> builder.build(name));
    }

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }

}
