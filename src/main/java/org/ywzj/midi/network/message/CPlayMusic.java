package org.ywzj.midi.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class CPlayMusic extends MusicMessage {

    public int offset;
    public String url;

    public CPlayMusic() {}

    public CPlayMusic(UUID soundUuid) {
        this.x = -1;
        this.y = -1;
        this.z = -1;
        this.offset = 0;
        this.on = false;
        this.url = "";
        this.musicName = "";
        this.soundUuid = soundUuid;
        this.portable = false;
        this.deviceUuid = soundUuid;
        this.isDeviceCarried = false;
    }

    public CPlayMusic(Vec3 pos, int offset, String url, String musicName, UUID soundUuid, boolean portable, UUID deviceUuid, boolean isDeviceCarried) {
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
        this.offset = offset;
        this.on = true;
        this.url = url;
        this.musicName = musicName;
        this.soundUuid = soundUuid;
        this.portable = portable;
        this.deviceUuid = deviceUuid == null ? soundUuid : deviceUuid;
        this.isDeviceCarried = isDeviceCarried;
    }

    public static CPlayMusic decode(FriendlyByteBuf buf) {
        CPlayMusic data = new CPlayMusic();
        data.x = buf.readDouble();
        data.y = buf.readDouble();
        data.z = buf.readDouble();
        data.offset = buf.readInt();
        data.on = buf.readBoolean();
        byte[] bytes = new byte[buf.readInt()];
        buf.readBytes(bytes);
        data.url = new String(bytes, StandardCharsets.UTF_8);
        bytes = new byte[buf.readInt()];
        buf.readBytes(bytes);
        data.musicName = new String(bytes, StandardCharsets.UTF_8);
        data.soundUuid = buf.readUUID();
        data.portable = buf.readBoolean();
        data.deviceUuid = buf.readUUID();
        data.isDeviceCarried = buf.readBoolean();
        return data;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeInt(offset);
        buf.writeBoolean(on);
        byte[] bytes = url.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
        bytes = musicName.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
        buf.writeUUID(soundUuid);
        buf.writeBoolean(portable);
        buf.writeUUID(deviceUuid);
        buf.writeBoolean(isDeviceCarried);
    }

    public MusicMessage getServerMessage() {
        return new SPlayMusic(this);
    }

}
