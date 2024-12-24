package org.ywzj.midi.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import org.ywzj.midi.block.AABlock;
import org.ywzj.midi.gui.screen.AABlockScreen;
import org.ywzj.midi.instrument.receiver.MidiReceiver;
import org.ywzj.midi.network.message.CNoteBlockUpdate;

public class AABlockEntity extends NoteBlockEntity {

    public MidiReceiver receiver;
    public AABlockScreen aaBlockScreen;

    public AABlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(pWorldPosition, pBlockState);
    }

    public void closeReceiver() {
        if (receiver != null) {
            receiver.close();
            receiver = null;
        }
    }

    @Override
    public void setRemoved() {
        closeReceiver();
        super.setRemoved();
    }

    @Override
    public void serverHandleData(ServerPlayer sender, CNoteBlockUpdate message, BlockPos pos) {
        sender.level().setBlockAndUpdate(pos, this.getBlockState().setValue(AABlock.POWERING, message.on));
    }

}
