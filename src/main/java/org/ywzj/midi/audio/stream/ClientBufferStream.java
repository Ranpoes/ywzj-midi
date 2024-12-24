package org.ywzj.midi.audio.stream;

import net.minecraft.client.sounds.AudioStream;
import org.lwjgl.BufferUtils;
import org.ywzj.midi.audio.sound.ClientBufferMusicSound;

import javax.sound.sampled.AudioFormat;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ClientBufferStream implements AudioStream {

    private final ClientBufferMusicSound soundInstance;
    private final AudioFormat audioFormat;
    private int offset;
    private int count;
    private final ByteArrayOutputStream receiveBuffer;

    public ClientBufferStream(ClientBufferMusicSound soundInstance, float sampleRate, float frameRate) {
        audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sampleRate, 16,
                1, 2, frameRate, false);
        this.soundInstance = soundInstance;
        this.offset = 0;
        this.count = 0;
        this.receiveBuffer = new ByteArrayOutputStream();
    }

    public void writeBytes(byte[] bytes) {
        receiveBuffer.writeBytes(bytes);
    }

    @Override
    public AudioFormat getFormat() {
        return audioFormat;
    }

    @Override
    public ByteBuffer read(int size) {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(size);
        byte[] bytes = new byte[size];
        if (receiveBuffer.size() >= offset + size) {
            bytes = Arrays.copyOfRange(receiveBuffer.toByteArray(), offset, offset + size);
            offset += size;
        } else {
            count += 1;
            if (count == 10) {
                soundInstance.stop();
            }
        }
        byteBuffer.put(bytes);
        byteBuffer.flip();
        return byteBuffer;
    }

    @Override
    public void close() {
        soundInstance.stop();
    }

}
