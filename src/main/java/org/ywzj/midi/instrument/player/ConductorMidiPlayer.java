package org.ywzj.midi.instrument.player;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.gui.screen.ConductorScreen;
import org.ywzj.midi.gui.widget.ValueSlider;
import org.ywzj.midi.pose.action.ConductorPose;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class ConductorMidiPlayer extends MidiPlayer {

    private final ConductorScreen conductorScreen;
    protected final ConductorPose conductorPose = new ConductorPose(Minecraft.getInstance().player);

    public ConductorMidiPlayer(ConductorScreen conductorScreen) {
        super(Minecraft.getInstance().player);
        this.conductorScreen = conductorScreen;
    }

    @Override
    public void handleStep() {
        handlePose();
        updateProcessBar();
        updateVolume();
    }

    @Override
    public void endCallback() {
        conductorPose.stop();
        conductorScreen.callbackPlayButton();
    }

    private void handlePose() {
        if (!conductorScreen.needConductor) {
            conductorPose.setLookAtPlayer(null);
            return;
        }
        channelPlayingNotes.keySet().stream().filter(channel -> conductorScreen.channelFilterButton.getValues().contains(channel.instrumentName)).forEach(channelPlayingNotes::remove);
        if (channelPlayingNotes.size() > 1) {
            conductorPose.pause(false);
            if (!conductorPose.loop) {
                conductorPose.loop();
            }
            conductorPose.setBpm(bpm);
            AtomicReference<LivingEntity> lookAtPlayer = new AtomicReference<>();
            int maxVol = 0;
            for (Map.Entry<MidiPlayer.Channel, HashMap<Integer, Integer>> channelHashMapEntry : channelPlayingNotes.entrySet()) {
                Optional<Integer> vol = channelHashMapEntry.getValue().values().stream().max(Integer::compare);
                if (vol.isPresent()) {
                    if (vol.get() > maxVol) {
                        maxVol = vol.get();
                        channelHashMapEntry.getKey().getReceivers().values().stream().findAny().ifPresent(midiReceiver -> lookAtPlayer.set(midiReceiver.getPlayer()));
                    }
                }
            }
            conductorPose.setVelocity(maxVol);
            if (!conductorPose.player.equals(lookAtPlayer.get())) {
                conductorPose.setLookAtPlayer(lookAtPlayer.get());
            }
        } else {
            conductorPose.pause(true);
        }
    }

    private void updateProcessBar() {
        ValueSlider progressBar = conductorScreen.progressBar;
        if (progressBar == null) {
            return;
        }
        if (progressBar.isUpdated()) {
            step = (int) (allEvents.size() * ((float) progressBar.value / progressBar.maxValue));
            long targetTick = allEvents.get(step).getTick();
            for (MidiPlayer.Channel channel : channels) {
                channel.set(targetTick);
            }
            for (long lookupTick = targetTick; lookupTick >= 0; lookupTick -= 1) {
                if (msPerTickChanges.get(lookupTick) != null) {
                    msPerTick = msPerTickChanges.get(lookupTick);
                    break;
                }
            }
            lastTick = targetTick;
        } else if (step % 10 == 0) {
            progressBar.updateValue((double) step / allEvents.size());
        }
    }

    public void updateVolume() {
        conductorScreen.volumeSliders.stream()
                .filter(ValueSlider::isUpdated)
                .forEach(volumeSlider -> conductorScreen.volumeSliderToChannel.get(volumeSlider).volume((float) volumeSlider.value / volumeSlider.maxValue));
    }

}
