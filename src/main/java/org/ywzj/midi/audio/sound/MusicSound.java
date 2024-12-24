package org.ywzj.midi.audio.sound;

import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.util.ParticleUtils;

public class MusicSound extends SimpleSoundInstance implements TickableSoundInstance {

    private boolean isPlaying;
    private boolean isKilled;
    private Long tickCount;

    public MusicSound(SoundEvent event, float volume, float pitch, RandomSource source, Vec3 pos) {
        super(event, SoundSource.RECORDS, volume, pitch, source, pos.x, pos.y, pos.z);
        this.isPlaying = true;
        this.tickCount = 0L;
    }

    public void stop() {
        isPlaying = false;
    }

    public void kill() {
        stop();
        isKilled = true;
    }

    @Override
    public boolean isStopped() {
        return !isPlaying;
    }

    public boolean isKilled() {
        return isKilled;
    }

    @Override
    public void tick() {
        if (tickCount % 2 == 0) {
            ParticleUtils.addNoteParticle(new Vec3(x, y, z));
        }
        tickCount += 1;
    }

}
