package org.ywzj.midi.gui.screen;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.ywzj.midi.entity.FakePlayerEntity;
import org.ywzj.midi.util.ComponentUtils;

import static org.ywzj.midi.entity.FakePlayerEntity.*;

public class FakePlayerScreen extends Screen {

    private boolean firstRender = true;
    public EditBox nameBox;
    public EditBox urlBox;
    private final FakePlayerEntity fakePlayerEntity;

    public FakePlayerScreen(Component component, FakePlayerEntity fakePlayerEntity) {
        super(component);
        this.fakePlayerEntity = fakePlayerEntity;
    }

    @Override
    protected void init() {
        if (firstRender) {
            firstRender = false;
            nameBox = new EditBox(font, width/2 - 180/2, height/2 - 60, 180, 20, ComponentUtils.literal("name"));
            nameBox.setMaxLength(256);
            nameBox.setValue(fakePlayerEntity.getEntityData().get(NAME));
            nameBox.setResponder((box) -> nameBox.setSuggestion(""));
            if (nameBox.getValue().length() == 0) {
                nameBox.setSuggestion("AA775");
            }
            urlBox = new EditBox(font, width/2 - 180/2, height/2 - 30, 180, 20, ComponentUtils.literal("url"));
            urlBox.setMaxLength(256);
            urlBox.setValue(fakePlayerEntity.getEntityData().get(SKIN_URL));
            urlBox.setResponder((box) -> urlBox.setSuggestion(""));
            if (urlBox.getValue().length() == 0) {
                urlBox.setSuggestion("https://abc.com/xxx/x.png");
            }
        }
        this.addRenderableWidget(nameBox);
        this.addRenderableWidget(urlBox);
    }

    @Override
    public void onClose() {
        super.onClose();
        fakePlayerEntity.getEntityData().set(FakePlayerEntity.NAME, nameBox.getValue().length() == 0 ? DEFAULT_NAME : nameBox.getValue());
        fakePlayerEntity.getEntityData().set(SKIN_URL, urlBox.getValue());
        fakePlayerEntity.updateClientToServer();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
