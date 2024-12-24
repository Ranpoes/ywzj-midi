package org.ywzj.midi.all;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ywzj.midi.YwzjMidi;
import org.ywzj.midi.block.MusicStandBlock;
import org.ywzj.midi.block.PianoBenchBlock;
import org.ywzj.midi.block.SpeakerBlock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class AllBlocks {

    public static final ConcurrentHashMap<String, DeferredRegister<Block>> BLOCKS = new ConcurrentHashMap<>();

    public static final RegistryObject<Block> MUSIC_STAND_BLOCK = registerBlock("music_stand_block", () -> new MusicStandBlock(BlockBehaviour.Properties.of().strength(1f)));
    public static final RegistryObject<Block> PIANO_BENCH_BLOCK = registerBlock("piano_bench_block", () -> new PianoBenchBlock(BlockBehaviour.Properties.of().strength(1f)));
    public static final RegistryObject<Block> SPEAKER_BLOCK = registerBlock("speaker_block", () -> new SpeakerBlock(BlockBehaviour.Properties.of().strength(1f)));

    public static <T extends Block> RegistryObject<Block> registerBlock(String name, Supplier<T> block) {
        return registerBlock(YwzjMidi.MODID, name, block);
    }

    public static <T extends Block> RegistryObject<Block> registerBlock(String namespace, String name, Supplier<T> block) {
        DeferredRegister<Block> blockDeferredRegister = BLOCKS.computeIfAbsent(namespace, k -> DeferredRegister.create(ForgeRegistries.BLOCKS, namespace));
        RegistryObject<Block> toReturn = blockDeferredRegister.register(name, block);
        registerBlockItem(namespace, name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String namespace, String name, RegistryObject<T> block) {
        DeferredRegister<Item> itemDeferredRegister = AllItems.ITEMS.computeIfAbsent(namespace, k -> DeferredRegister.create(ForgeRegistries.ITEMS, namespace));
        RegistryObject<Item> registryObject = itemDeferredRegister.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        AllTabs.TAB_ITEMS.add(registryObject);
        AllItems.ITEMS_LOOKUP.put(name, registryObject);
        return registryObject;
    }

    public static void register(IEventBus eventBus, String namespace) {
        if (BLOCKS.get(namespace) != null) {
            BLOCKS.get(namespace).register(eventBus);
        }
    }

}
