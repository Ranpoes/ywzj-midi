package org.ywzj.midi.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class CSyncMusic extends MusicMessage {

    public float sampleRate;
    public float frameRate;
    public byte[] bytes;

    public CSyncMusic() {}

    public CSyncMusic(UUID soundUuid) {
        this.x = -1;
        this.y = -1;
        this.z = -1;
        this.sampleRate = -1;
        this.frameRate = -1;
        this.on = false;
        this.musicName = "";
        this.soundUuid = soundUuid;
        this.portable = false;
        this.deviceUuid = soundUuid;
        this.isDeviceCarried = false;
        this.bytes = new byte[1];
    }

    public CSyncMusic(Vec3 pos, float sampleRate, float frameRate, String musicName, UUID soundUuid, boolean portable, UUID deviceUuid, boolean isDeviceCarried, byte[] bytes) {
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
        this.sampleRate = sampleRate;
        this.frameRate = frameRate;
        this.on = true;
        this.musicName = musicName;
        this.soundUuid = soundUuid;
        this.portable = portable;
        this.deviceUuid = deviceUuid == null ? soundUuid : deviceUuid;
        this.isDeviceCarried = isDeviceCarried;
        this.bytes = bytes;
    }

    public static CSyncMusic decode(FriendlyByteBuf buf) {
        CSyncMusic data = new CSyncMusic();
        data.x = buf.readDouble();
        data.y = buf.readDouble();
        data.z = buf.readDouble();
        data.sampleRate = buf.readFloat();
        data.frameRate = buf.readFloat();
        data.on = buf.readBoolean();
        int size = buf.readInt();
        byte[] musicNameBytes = new byte[size];
        buf.readBytes(musicNameBytes);
        data.musicName = new String(musicNameBytes, StandardCharsets.UTF_8);
        data.soundUuid = buf.readUUID();
        data.portable = buf.readBoolean();
        data.deviceUuid = buf.readUUID();
        data.isDeviceCarried = buf.readBoolean();
        size = buf.readInt();
        byte[] bytes = new byte[size];
        buf.readBytes(bytes);
        data.bytes = bytes;
        return data;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeFloat(sampleRate);
        buf.writeFloat(frameRate);
        buf.writeBoolean(on);
        byte[] musicNameBytes = musicName.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(musicNameBytes.length);
        buf.writeBytes(musicNameBytes);
        buf.writeUUID(soundUuid);
        buf.writeBoolean(portable);
        buf.writeUUID(deviceUuid);
        buf.writeBoolean(isDeviceCarried);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }

    public MusicMessage getServerMessage() {
        return new SSyncMusic(this);
    }

}
