package org.ywzj.midi.gui.screen;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.ywzj.midi.audio.ClientPlayerInstance;
import org.ywzj.midi.gui.widget.CommonButton;
import org.ywzj.midi.gui.widget.SelectionList;
import org.ywzj.midi.storage.MidiFiles;
import org.ywzj.midi.util.ComponentUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class MusicPlayerScreen extends Screen {

    private boolean firstRender = true;
    private final ClientPlayerInstance clientPlayerInstance;
    private final Vec3 pos;
    private Button playLocalButton;
    private Button playNetButton;
    private Button playRoundButton;
    private EditBox musicUrlBox;
    private EditBox musicNameBox;
    private SelectionList<Path> musicNameList;
    private List<SelectionList.Selection<Path>> musicPaths = new ArrayList<>();
    private final Lock uiLock = new ReentrantLock();

    public MusicPlayerScreen(Vec3 pos, Component titleIn, ClientPlayerInstance clientPlayerInstance) {
        super(titleIn);
        this.pos = pos;
        this.clientPlayerInstance = clientPlayerInstance;
        clientPlayerInstance.setScreen(this);
    }

    private synchronized void playLocal() {
        new Thread(() -> {
            if (!uiLock.tryLock()) {
                return;
            }
            try {
                if (clientPlayerInstance.isPlaying()) {
                    clientPlayerInstance.stop();
                    playLocalButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.play_local_music"));
                    playNetButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.play_net_music"));
                    clientPlayerInstance.stopSyncRound();
                    playRoundButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.play_round"));
                } else {
                    if (musicNameList.getSelected() != null) {
                        Path path = musicNameList.getSelected().getValue().value;
                        boolean result = clientPlayerInstance.playSync(pos, "file:///" + path, path.getFileName().toString());
                        if (result) {
                            playLocalButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.playing"));
                            playNetButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.playing"));
                        }
                    }
                }
            } catch (Exception ignore) {}
            finally {
                uiLock.unlock();
            }
        }).start();
    }

    private synchronized void playRound() {
        new Thread(() -> {
            if (!uiLock.tryLock()) {
                return;
            }
            try {
                if (clientPlayerInstance.isPlaying()) {
                    clientPlayerInstance.stopSyncRound();
                    playRoundButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.play_round"));
                    playLocalButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.play_local_music"));
                    playNetButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.play_net_music"));
                } else {
                    if (musicPaths.size() == 0) {
                        return;
                    }
                    clientPlayerInstance.playSyncRound(pos, musicPaths.stream().map(pathSelection -> pathSelection.value).collect(Collectors.toList()), musicNameList.getSelected() == null ? null : musicNameList.getSelected().getValue().value);
                    playRoundButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.playing"));
                    playNetButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.playing"));
                    playLocalButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.playing"));
                }
            } catch (Exception ignore) {}
            finally {
                uiLock.unlock();
            }
        }).start();
    }

    private void playNet() {
        new Thread(() -> {
            if (!uiLock.tryLock()) {
                return;
            }
            try {
                if (clientPlayerInstance.isPlaying()) {
                    clientPlayerInstance.stop();
                    playNetButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.play_net_music"));
                    playLocalButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.play_local_music"));
                    clientPlayerInstance.stopSyncRound();
                    playRoundButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.play_round"));
                } else {
                    boolean result = clientPlayerInstance.playNet(
                            pos,
                            musicUrlBox.getValue(),
                            musicNameBox.getValue().isEmpty() ? ComponentUtils.translatable("ui.ywzj_midi.net_music").getString() : musicNameBox.getValue()
                    );
                    if (result) {
                        playNetButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.playing"));
                        playLocalButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.playing"));
                    } else {
                        musicUrlBox.setValue("");
                        musicUrlBox.setSuggestion(ComponentUtils.translatable("ui.ywzj_midi.wrong_url").getString());
                    }
                }
            } catch (Exception ignore) {}
            finally {
                uiLock.unlock();
            }
        }).start();
    }

    @Override
    protected void init() {
        playNetButton = new CommonButton(width/2 - 90/2, height/2 - 90, 90, 20,
                ComponentUtils.translatable(clientPlayerInstance.isPlaying() ? "ui.ywzj_midi.playing" : "ui.ywzj_midi.play_net_music"),
                (button) -> playNet()
        );
        musicUrlBox = new EditBox(font, width/2 - 100, height/2 - 60, 200, 20, ComponentUtils.literal("编辑地址"));
        musicUrlBox.setMaxLength(256);
        musicUrlBox.setResponder((box) -> musicUrlBox.setSuggestion(""));
        musicNameBox = new EditBox(font, width/2 + 100 + 20, height/2 - 60, 50, 20, ComponentUtils.literal("编辑名称"));
        musicNameBox.setMaxLength(256);
        musicNameBox.setResponder((box) -> musicNameBox.setSuggestion(""));
        musicNameBox.setSuggestion(ComponentUtils.translatable("ui.ywzj_midi.music_name").getString());
        playLocalButton = new CommonButton(width/2 - 90/2, height/2 - 30, 90, 20,
                ComponentUtils.translatable(clientPlayerInstance.isPlaying() ? "ui.ywzj_midi.playing" : "ui.ywzj_midi.play_local_music"),
                (button) -> playLocal()
        );
        musicPaths.clear();
        MidiFiles.getMusics().forEach(path -> musicPaths.add(new SelectionList.Selection<>(path, path.getFileName().toString())));
        if (firstRender) {
            firstRender = false;
            musicNameList = new SelectionList<>(musicPaths, this, MusicPlayerScreen.this.width, MusicPlayerScreen.this.height, MusicPlayerScreen.this.height/2 - 5, MusicPlayerScreen.this.height/2 + 100, this::playLocal);
        } else {
            musicNameList.update(musicPaths, MusicPlayerScreen.this.width, MusicPlayerScreen.this.height, MusicPlayerScreen.this.height/2 - 5, MusicPlayerScreen.this.height/2 + 100);
        }
        playRoundButton = new CommonButton(width / 2 + 50, height / 2 - 30, 40, 20, ComponentUtils.translatable("ui.ywzj_midi.play_round"), (button) -> playRound());
        if (clientPlayerInstance.isPlaying() && !clientPlayerInstance.portable) {
            String playingInfo = clientPlayerInstance.playerName + ComponentUtils.translatable("ui.ywzj_midi.is_now_playing").getString() + clientPlayerInstance.musicName;
            musicUrlBox.setSuggestion(ComponentUtils.getLimitedString(playingInfo, 200));
        } else {
            musicUrlBox.setSuggestion("https://abc.com/xxx/x.mp3");
        }
        if (musicPaths.size() == 0) {
            addRenderableWidget(new StringWidget(this.width/2 - 40, this.height/2 + 10, 80, 10, ComponentUtils.translatable("info.ywzj_midi.no_selectable_value"), this.font));
            addRenderableWidget(new StringWidget(this.width/2 - 40, this.height/2 + 20, 80, 10, ComponentUtils.translatable("info.ywzj_midi.suggestion_music"), this.font));
        }
        addRenderableWidget(playNetButton);
        addRenderableWidget(musicUrlBox);
        addRenderableWidget(musicNameBox);
        addRenderableWidget(playLocalButton);
        addRenderableWidget(musicNameList);
        addRenderableWidget(playRoundButton);
    }

    public void callbackPlayEnd() {
        playNetButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.play_net_music"));
        playLocalButton.setMessage(ComponentUtils.translatable("ui.ywzj_midi.play_local_music"));
    }

    public void callbackPlayNext(Path nextPath) {
        musicNameList.findAndSelect(nextPath);
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean keyPressed = super.keyPressed(keyCode, scanCode, modifiers);
        if (keyPressed || getFocused() != null)
            return keyPressed;
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
        if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey)) {
            this.onClose();
            return true;
        }
        return false;
    }

}
