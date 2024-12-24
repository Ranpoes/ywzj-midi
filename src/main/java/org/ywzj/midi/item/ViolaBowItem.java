package org.ywzj.midi.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.ywzj.midi.all.AllInstruments;
import org.ywzj.midi.all.AllItems;
import org.ywzj.midi.gui.ScreenManager;

public class ViolaBowItem extends Item {

    public ViolaBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (level.isClientSide) {
            if (player.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AllItems.ITEMS_LOOKUP.get(AllInstruments.VIOLA.getName()).get())) {
                ScreenManager.openViolaScreen(player);
            } else {
                player.sendSystemMessage(Component.translatable("info.ywzj_midi.need_viola"));
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

}
