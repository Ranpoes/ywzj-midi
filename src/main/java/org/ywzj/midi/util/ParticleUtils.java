package org.ywzj.midi.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ParticleUtils {

    public static void addNoteParticle(Vec3 pos) {
        Level level = Minecraft.getInstance().player.level();
        if (level != null) {
            RandomSource random = level.random;
            level.addParticle(ParticleTypes.NOTE,
                    pos.x - 2f + 4 * random.nextDouble(),
                    pos.y + random.nextDouble() + 2,
                    pos.z - 2f + 4 * random.nextDouble(),
                    random.nextGaussian(), random.nextGaussian(), random.nextInt(3));
        }
    }

}
