package org.ywzj.midi.network.message;

import net.minecraft.network.FriendlyByteBuf;

public class SSyncMusic extends CSyncMusic {

    public SSyncMusic(CSyncMusic cSyncMusic) {
        this.x = cSyncMusic.x;
        this.y = cSyncMusic.y;
        this.z = cSyncMusic.z;
        this.sampleRate = cSyncMusic.sampleRate;
        this.frameRate = cSyncMusic.frameRate;
        this.on = cSyncMusic.on;
        this.musicName = cSyncMusic.musicName;
        this.soundUuid = cSyncMusic.soundUuid;
        this.portable = cSyncMusic.portable;
        this.deviceUuid = cSyncMusic.deviceUuid;
        this.isDeviceCarried = cSyncMusic.isDeviceCarried;
        this.bytes = cSyncMusic.bytes;
    }

    public static SSyncMusic decode(FriendlyByteBuf buf) {
        return new SSyncMusic(CSyncMusic.decode(buf));
    }

}
