package org.ywzj.midi.network.message;

import net.minecraft.network.FriendlyByteBuf;

public class SPlayMusic extends CPlayMusic {

    public SPlayMusic(CPlayMusic cPlayMusic) {
        this.x = cPlayMusic.x;
        this.y = cPlayMusic.y;
        this.z = cPlayMusic.z;
        this.offset = cPlayMusic.offset;
        this.on = cPlayMusic.on;
        this.url = cPlayMusic.url;
        this.musicName = cPlayMusic.musicName;
        this.soundUuid = cPlayMusic.soundUuid;
        this.portable = cPlayMusic.portable;
        this.deviceUuid = cPlayMusic.deviceUuid;
        this.isDeviceCarried = cPlayMusic.isDeviceCarried;
    }

    public static SPlayMusic decode(FriendlyByteBuf buf) {
        return new SPlayMusic(CPlayMusic.decode(buf));
    }

}
