package org.ywzj.midi.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.ywzj.midi.gui.ScreenManager;

public class TubaItem extends Item {

    public TubaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            ScreenManager.openTubaScreen(player);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

}
