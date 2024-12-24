package org.ywzj.midi.instrument;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.instrument.receiver.MidiReceiver;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Instrument {

    private static final AtomicInteger indexGenerator = new AtomicInteger(0);
    protected final Integer index;
    protected final String name;
    protected final boolean loop;
    protected final boolean portable;
    protected final String keyStart;
    protected final String keyEnd;
    protected final LinkedHashMap<Integer, Variant> variants = new LinkedHashMap<>();

    public Instrument(String name, boolean loop, boolean portable, String keyStart, String keyEnd) {
        this.index = indexGenerator.addAndGet(1);
        this.name = name;
        this.loop = loop;
        this.portable = portable;
        this.keyStart = keyStart;
        this.keyEnd = keyEnd;
        this.variants.put(0, new Variant(0, "raw", loop, keyStart, keyEnd));
    }

    public abstract MidiReceiver receiver(LivingEntity player, Vec3 pos);

    public Instrument extra(String name, boolean loop) {
        return extra(name, loop, keyStart, keyEnd);
    }

    public Instrument extra(String name, boolean loop, String keyStart, String keyEnd) {
        Integer index = variants.keySet().size();
        Variant variant = new Variant(index, name, loop, keyStart, keyEnd);
        variants.put(index, variant);
        return this;
    }

    public String getName() {
        return name;
    }

    public Integer getIndex() {
        return index;
    }

    public Boolean isLoop() {
        return loop;
    }

    public Boolean isPortable() {
        return portable;
    }

    public String getKeyStart() {
        return keyStart;
    }

    public String getKeyEnd() {
        return keyEnd;
    }

    public Variant getVariant(int variantId) {
        if (variantId > variants.size()) {
            return variants.get(0);
        }
        return variants.get(variantId);
    }

    public Collection<Variant> getAllVariants() {
        return variants.values();
    }

    public static class Variant {

        private final Integer index;
        private final String name;
        private final boolean loop;
        private final String keyStart;
        private final String keyEnd;

        public Variant(Integer index, String name, boolean loop, String keyStart, String keyEnd) {
            this.index = index;
            this.name = name;
            this.loop = loop;
            this.keyStart = keyStart;
            this.keyEnd = keyEnd;
        }

        public Integer getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }

        public boolean isLoop() {
            return loop;
        }

        public String getKeyStart() {
            return keyStart;
        }

        public String getKeyEnd() {
            return keyEnd;
        }

    }

}
