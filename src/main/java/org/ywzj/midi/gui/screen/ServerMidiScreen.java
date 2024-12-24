package org.ywzj.midi.gui.screen;

import com.google.gson.Gson;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.ywzj.midi.gui.widget.CommonButton;
import org.ywzj.midi.gui.widget.SelectionList;
import org.ywzj.midi.gui.widget.SelectionsButton;
import org.ywzj.midi.network.Channel;
import org.ywzj.midi.network.message.CPlayMidi;
import org.ywzj.midi.storage.ConductorConfig;
import org.ywzj.midi.storage.MidiFiles;
import org.ywzj.midi.util.ComponentUtils;
import org.ywzj.midi.util.FileTransfer;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServerMidiScreen extends Screen {

    private boolean firstRender = true;
    private LivingEntity conductor;
    protected List<SelectionList.Selection<Path>> midSelections = new ArrayList<>();
    protected List<SelectionList.Selection<Path>> configSelections = new ArrayList<>();
    protected SelectionsButton<Path> midSelectButton;
    protected SelectionsButton<Path> configSelectButton;

    public ServerMidiScreen(Component titleIn, LivingEntity conductor) {
        super(titleIn);
        this.conductor = conductor;
    }

    @Override
    protected void init() {
        List<Path> midPaths = MidiFiles.getMids();
        List<Path> configPaths = ConductorConfig.getConfigs();
        midSelections.clear();
        configSelections.clear();
        midPaths.forEach(midPath -> midSelections.add(new SelectionList.Selection<>(midPath, midPath.getFileName().toString())));
        configPaths.forEach(configPath -> configSelections.add(new SelectionList.Selection<>(configPath, configPath.getFileName().toString())));
        if (firstRender) {
            firstRender = false;
            midSelectButton = new SelectionsButton<>(width/2 - 40, height/2 - 80, 80, 20, ComponentUtils.translatable("ui.ywzj_midi.select_mid"), midSelections, null, this);
            configSelectButton = new SelectionsButton<>(width/2 - 40, height/2 - 50, 80, 20, ComponentUtils.translatable("ui.ywzj_midi.select_config"), configSelections, null, this);
        } else {
            midSelectButton.updateSelections(midSelections);
            configSelectButton.updateSelections(configSelections);
        }
        StringWidget suggestion = new StringWidget(width/2 - 40, height/2 - 20, 80, 10, ComponentUtils.translatable("info.ywzj_midi.suggestion_conduct_on_server"), this.font);
        CommonButton playOnServerButton = new CommonButton(width/2 - 70, height/2, 40, 20, ComponentUtils.translatable("ui.ywzj_midi.server_play"), (button) -> {
            if (midSelectButton.getValues().get(0) != null && configSelectButton.getValues().get(0) != null) {
                List<File> midFiles = midSelectButton.getValues().stream().map(Path::toFile).collect(Collectors.toList());
                List<File> configFiles = configSelectButton.getValues().stream().map(Path::toFile).collect(Collectors.toList());
                if (midFiles.size() != configFiles.size()) {
                    suggestion.setMessage(ComponentUtils.translatable("info.ywzj_midi.mid_config_not_match"));
                    return;
                }
                List<String> configs = new ArrayList<>();
                new Thread(() -> {
                    for (int index = 0; index < midFiles.size(); index += 1) {
                        File midFile = midFiles.get(index);
                        File configFile = configFiles.get(index);
                        if (midFile.exists() && configFile.exists()) {
                            try {
                                FileTransfer.upload(midFile, (short)0);
                                configs.add(Files.readString(configFile.toPath(), StandardCharsets.UTF_8));
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                    Gson gson = new Gson();
                    Channel.CHANNEL.sendToServer(new CPlayMidi(gson.toJson(midFiles.stream().map(File::getName).collect(Collectors.toList())),
                            gson.toJson(configs),
                            conductor.getId(),
                            OpCode.PLAY.value));
                }).start();
            }
        });
        CommonButton stopButton = new CommonButton(width/2 - 20, height/2, 40, 20, ComponentUtils.translatable("ui.ywzj_midi.server_stop"), (button) -> {
            Channel.CHANNEL.sendToServer(new CPlayMidi("",
                    "",
                    conductor.getId(),
                    OpCode.STOP.value));
        });
        CommonButton loopButton = new CommonButton(width/2 + 30, height/2, 40, 20, ComponentUtils.translatable("ui.ywzj_midi.server_loop"), (button) -> {
            Channel.CHANNEL.sendToServer(new CPlayMidi("",
                    "",
                    conductor.getId(),
                    OpCode.LOOP.value));
        });
        addRenderableWidget(midSelectButton);
        addRenderableWidget(configSelectButton);
        addRenderableWidget(suggestion);
        addRenderableWidget(playOnServerButton);
        addRenderableWidget(stopButton);
        addRenderableWidget(loopButton);
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

    public enum OpCode {

        PLAY((short)0),
        STOP((short)1),
        LOOP((short)2);

        public final short value;

        OpCode(short value) {
            this.value = value;
        }

    }

}
