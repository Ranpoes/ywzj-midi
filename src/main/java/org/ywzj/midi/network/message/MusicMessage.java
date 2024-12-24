package org.ywzj.midi.network.message;

import java.util.UUID;

public abstract class MusicMessage {

    public double x;
    public double y;
    public double z;
    public boolean on;
    public String musicName;
    public UUID soundUuid;
    public boolean portable;
    public UUID deviceUuid;
    public boolean isDeviceCarried;

    abstract public MusicMessage getServerMessage();

}
