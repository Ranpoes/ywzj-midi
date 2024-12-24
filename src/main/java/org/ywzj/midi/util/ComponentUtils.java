package org.ywzj.midi.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.awt.*;
import java.awt.font.FontRenderContext;

public class ComponentUtils {

    public static final Component EMPTY = ComponentUtils.literal("");

    public static String getLimitedString(String s, int width) {
        Font font = new Font("Arial", Font.PLAIN, 12);
        FontRenderContext frc = new FontRenderContext(null, true, true);
        for (int i = 0; i < s.length(); i++) {
            if (font.getStringBounds(s.substring(0,i), frc).getWidth() > width) {
                return s.substring(0, i-1) + "...";
            }
        }
        return s;
    }

    public static MutableComponent literal(String s) {
        return Component.literal(s);
    }

    public static MutableComponent translatable(String s) {
        return Component.translatable(s);
    }

}
