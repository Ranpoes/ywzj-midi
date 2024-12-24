package org.ywzj.midi.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.ywzj.midi.all.AllInstruments;
import org.ywzj.midi.blockentity.AABlockEntity;
import org.ywzj.midi.blockentity.PianoBlockEntity;
import org.ywzj.midi.blockentity.TimpaniBlockEntity;
import org.ywzj.midi.entity.FakePlayerEntity;
import org.ywzj.midi.gui.screen.*;
import org.ywzj.midi.instrument.Instrument;
import org.ywzj.midi.util.ComponentUtils;
import org.ywzj.midi.util.MathUtils;

import java.util.HashMap;
import java.util.UUID;

import static org.ywzj.midi.entity.FakePlayerEntity.DEFAULT_NAME;

@OnlyIn(Dist.CLIENT)
public class ScreenManager {

    private static final ConductorScreen CONDUCTOR_SCREEN = new ConductorScreen(ComponentUtils.literal("指挥"));
    private static ViolScreen violinScreen;
    private static ViolScreen violaScreen;
    private static ViolScreen celloScreen;
    private static ViolScreen doubleBassScreen;
    private static WoodwindScreen oboeScreen;
    private static WoodwindScreen clarinetScreen;
    private static WoodwindScreen fluteScreen;
    private static WoodwindScreen bassoonScreen;
    private static BrassScreen hornScreen;
    private static BrassScreen trumpetScreen;
    private static BrassScreen tromboneScreen;
    private static BrassScreen tubaScreen;
    private static final HashMap<UUID, ServerMidiScreen> fakePlayerConductorScreens = new HashMap<>();

    public static void openBatonScreen() {
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(CONDUCTOR_SCREEN));
    }

    public static void openPianoScreen(BlockPos pos, Instrument instrument, PianoBlockEntity pianoBlockEntity) {
        if (!checkDistance(pos, 3)) {
            return;
        }
        if (pianoBlockEntity.clavichordScreen == null) {
            pianoBlockEntity.clavichordScreen = new ClavichordScreen(instrument, pos, ComponentUtils.literal("钢琴"), "c4", "b6");
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(pianoBlockEntity.clavichordScreen));
    }

    public static void openViolinScreen(Player player) {
        if (violinScreen == null) {
            violinScreen = new ViolScreen(AllInstruments.VIOLIN, player.position(), ComponentUtils.literal("小提琴"), "c4", "b6");
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(violinScreen));
    }

    public static void openViolaScreen(Player player) {
        if (violaScreen == null) {
            violaScreen = new ViolScreen(AllInstruments.VIOLA, player.position(), ComponentUtils.literal("中提琴"), "c3", "b5");
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(violaScreen));
    }

    public static void openCelloScreen(Player player) {
        if (celloScreen == null) {
            celloScreen = new ViolScreen(AllInstruments.CELLO, player.position(), ComponentUtils.literal("大提琴"), "c3", "b5");
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(celloScreen));
    }

    public static void openDoubleBassScreen(Player player) {
        if (doubleBassScreen == null) {
            doubleBassScreen = new ViolScreen(AllInstruments.DOUBLE_BASS, player.position(), ComponentUtils.literal("低音提琴"), "c1", "b3");
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(doubleBassScreen));
    }

    public static void openOboeScreen(Player player) {
        if (oboeScreen == null) {
            oboeScreen = new WoodwindScreen(AllInstruments.OBOE, player.position(), ComponentUtils.literal("双簧管"), "c4", "b6");
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(oboeScreen));
    }

    public static void openClarinetScreen(Player player) {
        if (clarinetScreen == null) {
            clarinetScreen = new WoodwindScreen(AllInstruments.CLARINET, player.position(), ComponentUtils.literal("单簧管"), "c4", "b6");
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(clarinetScreen));
    }

    public static void openFluteScreen(Player player) {
        if (fluteScreen == null) {
            fluteScreen = new WoodwindScreen(AllInstruments.FLUTE, player.position(), ComponentUtils.literal("长笛"), "c4", "b6");
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(fluteScreen));
    }

    public static void openBassoonScreen(Player player) {
        if (bassoonScreen == null) {
            bassoonScreen = new WoodwindScreen(AllInstruments.BASSOON, player.position(), ComponentUtils.literal("巴松管"), "c3", "b5");
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(bassoonScreen));
    }

    public static void openHornScreen(Player player) {
        if (hornScreen == null) {
            hornScreen = new BrassScreen(AllInstruments.HORN, player.position(), ComponentUtils.literal("圆号"), "c3", "b5");
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(hornScreen));
    }

    public static void openTrumpetScreen(Player player) {
        if (trumpetScreen == null) {
            trumpetScreen = new BrassScreen(AllInstruments.TRUMPET, player.position(), ComponentUtils.literal("小号"), "c4", "b6");
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(trumpetScreen));
    }

    public static void openTromboneScreen(Player player) {
        if (tromboneScreen == null) {
            tromboneScreen = new BrassScreen(AllInstruments.TROMBONE, player.position(), ComponentUtils.literal("长号"), "c3", "b5");
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(tromboneScreen));
    }

    public static void openTubaScreen(Player player) {
        if (tubaScreen == null) {
            tubaScreen = new BrassScreen(AllInstruments.TUBA, player.position(), ComponentUtils.literal("大号"), "c2", "b4");
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(tubaScreen));
    }

    public static void openSpeakerScreen(BlockPos pos, MusicPlayerScreen musicPlayerScreen) {
        if (!checkDistance(pos, 2)) {
            return;
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(musicPlayerScreen));
    }

    public static void openMusicPlayerScreen(MusicPlayerScreen musicPlayerScreen) {
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(musicPlayerScreen));
    }

    public static void openAABlockScreen(BlockPos pos, AABlockEntity aaBlockEntity) {
        if (aaBlockEntity.aaBlockScreen == null) {
            aaBlockEntity.aaBlockScreen = new AABlockScreen(pos, ComponentUtils.literal("AA775"), aaBlockEntity);
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(aaBlockEntity.aaBlockScreen));
    }

    public static void openTimpaniScreen(BlockPos pos, TimpaniBlockEntity timpaniBlockEntity) {
        if (timpaniBlockEntity.timpaniScreen == null) {
            timpaniBlockEntity.timpaniScreen = new TimpaniScreen(pos, timpaniBlockEntity);
        }
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(timpaniBlockEntity.timpaniScreen));
    }

    public static void openFakePlayerScreen(FakePlayerEntity fakePlayerEntity) {
        FakePlayerScreen fakePlayerScreen = new FakePlayerScreen(ComponentUtils.literal(DEFAULT_NAME), fakePlayerEntity);
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(fakePlayerScreen));
    }

    public static void openFakePlayerConductorScreen(FakePlayerEntity fakePlayerEntity) {
        ServerMidiScreen serverMidiScreen = fakePlayerConductorScreens.computeIfAbsent(fakePlayerEntity.getUUID(), k -> new ServerMidiScreen(ComponentUtils.literal("指挥"), fakePlayerEntity));
        Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(serverMidiScreen));
    }

    private static boolean checkDistance(BlockPos pos, int distance) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (MathUtils.distance(player.getX(), player.getY(), player.getZ(), pos.getX(), pos.getY(), pos.getZ()) > distance) {
                player.sendSystemMessage(ComponentUtils.translatable("info.ywzj_midi.warn_2"));
                return false;
            }
        }
        return true;
    }

}
