package org.ywzj.midi.all;

import org.ywzj.midi.pose.PoseManager;
import org.ywzj.midi.pose.handler.BassDrumHandler;
import org.ywzj.midi.pose.handler.TimpaniHitHandler;
import org.ywzj.midi.pose.handler.TromboneSlideHandler;

public class AllNotesHandler {

    static {
        PoseManager.registerNotesHandler(new TromboneSlideHandler());
        PoseManager.registerNotesHandler(new TimpaniHitHandler());
        PoseManager.registerNotesHandler(new BassDrumHandler());
    }

    public static void preRegister() {}

}
