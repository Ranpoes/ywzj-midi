package org.ywzj.midi.network.message;

import net.minecraft.network.FriendlyByteBuf;
import org.ywzj.midi.pose.PoseManager;

import java.util.UUID;

public class CPoseData {

    public Float leftArmX;
    public Float leftArmY;
    public Float leftArmZ;
    public Float leftArmRotX;
    public Float leftArmRotY;
    public Float leftArmRotZ;
    public Float rightArmX;
    public Float rightArmY;
    public Float rightArmZ;
    public Float rightArmRotX;
    public Float rightArmRotY;
    public Float rightArmRotZ;
    public int instrumentId;
    public int notesLength;
    public int[] notes;
    public UUID playerUuid;

    public CPoseData() {};
    
    public CPoseData(UUID playerUuid, PoseManager.PlayPose pose) {
        this.leftArmX = getValue(pose.leftArmX);
        this.leftArmY = getValue(pose.leftArmY);
        this.leftArmZ = getValue(pose.leftArmZ);
        this.leftArmRotX = getValue(pose.leftArmRotX);
        this.leftArmRotY = getValue(pose.leftArmRotY);
        this.leftArmRotZ = getValue(pose.leftArmRotZ);
        this.rightArmX = getValue(pose.rightArmX);
        this.rightArmY = getValue(pose.rightArmY);
        this.rightArmZ = getValue(pose.rightArmZ);
        this.rightArmRotX = getValue(pose.rightArmRotX);
        this.rightArmRotY = getValue(pose.rightArmRotY);
        this.rightArmRotZ = getValue(pose.rightArmRotZ);
        this.instrumentId = pose.instrumentId;
        this.notesLength = pose.notes.size();
        this.notes = pose.notes.stream().mapToInt(Integer::intValue).toArray();
        this.playerUuid = playerUuid;
    }

    public static CPoseData decode(FriendlyByteBuf buf) {
        CPoseData data = new CPoseData();
        data.leftArmX = buf.readFloat();
        data.leftArmY = buf.readFloat();
        data.leftArmZ = buf.readFloat();
        data.leftArmRotX = buf.readFloat();
        data.leftArmRotY = buf.readFloat();
        data.leftArmRotZ = buf.readFloat();
        data.rightArmX = buf.readFloat();
        data.rightArmY = buf.readFloat();
        data.rightArmZ = buf.readFloat();
        data.rightArmRotX = buf.readFloat();
        data.rightArmRotY = buf.readFloat();
        data.rightArmRotZ = buf.readFloat();
        data.instrumentId = buf.readInt();
        data.notesLength = buf.readInt();
        data.notes = new int[data.notesLength];
        for (int i = 0; i < data.notesLength; i++) {
            data.notes[i] = buf.readInt();
        }
        data.playerUuid = buf.readUUID();
        return data;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(leftArmX);
        buf.writeFloat(leftArmY);
        buf.writeFloat(leftArmZ);
        buf.writeFloat(leftArmRotX);
        buf.writeFloat(leftArmRotY);
        buf.writeFloat(leftArmRotZ);
        buf.writeFloat(rightArmX);
        buf.writeFloat(rightArmY);
        buf.writeFloat(rightArmZ);
        buf.writeFloat(rightArmRotX);
        buf.writeFloat(rightArmRotY);
        buf.writeFloat(rightArmRotZ);
        buf.writeInt(instrumentId);
        buf.writeInt(notesLength);
        for (int i = 0; i < notesLength; i++) {
            buf.writeInt(notes[i]);
        }
        buf.writeUUID(playerUuid);
    }

    protected static Float getValue(Float v) {
        if (v == null) {
            return -9f;
        }
        if (v == -9f) {
            return null;
        }
        return v;
    }

}
