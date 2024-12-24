package org.ywzj.midi.gui.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.gui.widget.CommonButton;
import org.ywzj.midi.instrument.Instrument;
import org.ywzj.midi.util.MidiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class PlayKeyScreen extends MidiInstrumentScreen {

    protected final List<CommonButton> keyButtons = new ArrayList<>();
    protected static ExecutorService delayActiveKeyPool = new ThreadPoolExecutor(24, 24, 0L,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(128),
            new ThreadPoolExecutor.AbortPolicy());

    public PlayKeyScreen(Instrument instrument, Vec3 pos, Component titleIn) {
        super(instrument, pos, titleIn);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256 && this.shouldCloseOnEsc()) {
            this.onClose();
            return true;
        }
        int keyIndex = MidiUtils.keyCodeToIndex(keyCode);
        if (keyIndex == -1) {
            return false;
        }
        if (keyIndex >= keyButtons.size()) {
            return false;
        }
        CommonButton keyButton = keyButtons.get(keyIndex);
        keyButton.active = false;
        keyButton.onPress();
        delayActiveKeyPool.submit(() -> {
            try {
                Thread.sleep(200);
            } catch (Exception ignore) {}
            keyButton.active = true;
        });
        return true;
    }

    @Override
    public void onClose() {
        needSave = true;
        super.onClose();
    }

}
