package org.ywzj.midi.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.ywzj.midi.util.ComponentUtils;

import java.util.List;

public class SelectionListButton<T> extends CommonButton {

    private final SelectionListScreen selectionListScreen;
    private final Component defaultComponent;
    private final boolean lockWidth;
    private int middleX;
    private final int firstWidth;

    public SelectionListButton(int middleX, int y, int width, int height, Component component, List<SelectionList.Selection<T>> values, Screen lastScreen, Component suggestion) {
        super(middleX, y, width, height, component, (button) -> {});
        this.defaultComponent = component;
        this.selectionListScreen = new SelectionListScreen(ComponentUtils.EMPTY, values, lastScreen, suggestion);
        this.lockWidth = width != -1;
        this.middleX = middleX;
        this.firstWidth = width <= 0 ? defaultComponent.getString().length() * 8 + 15 : width;
        updateWidth();
    }

    @Override
    public void updatePos(int x, int y) {
        middleX = x;
        updateWidth();
        this.setY(y);
    }

    private void updateWidth() {
        if (lockWidth || this.getName() == null) {
            this.setX(middleX - firstWidth/2);
            this.setWidth(firstWidth);
        } else {
            int newWidth = this.getName().length() * 5 + 15;
            this.setX(middleX - newWidth/2);
            this.setWidth(newWidth);
        }
    }

    public T getValue() {
        SelectionList<T>.SelectionEntry entry = selectionListScreen.selectionList.getSelected();
        if (entry != null) {
            return entry.getValue().value;
        }
        return null;
    }

    public String getName() {
        SelectionList<T>.SelectionEntry entry = selectionListScreen.selectionList.getSelected();
        if (entry != null) {
            return entry.getValue().name;
        }
        return null;
    }

    public void setValue(T value) {
        selectionListScreen.selectionList.findAndSelect(value);
        updateMessage();
    }

    public void updateSelections(List<SelectionList.Selection<T>> values) {
        selectionListScreen.selectionList.update(values, selectionListScreen.width, selectionListScreen.height, 0, selectionListScreen.height - 30);
    }

    private void updateMessage() {
        if (getName() != null) {
            this.setMessage(ComponentUtils.literal(SelectionListButton.this.getName()));
        } else {
            this.setMessage(defaultComponent);
        }
        updateWidth();
    }

    @Override
    public void onPress() {
        Minecraft.getInstance().setScreen(selectionListScreen);
    }

    public class SelectionListScreen extends Screen {

        private final Screen lastScreen;
        private final Component suggestion;
        private final SelectionList<T> selectionList;

        protected SelectionListScreen(Component component, List<SelectionList.Selection<T>> values, Screen lastScreen, Component suggestion) {
            super(component);
            this.lastScreen = lastScreen;
            this.suggestion = suggestion;
            this.selectionList = new SelectionList<>(values, this, lastScreen.width, lastScreen.height, 0, lastScreen.height - 30, () -> {});
        }

        @Override
        protected void init() {
            selectionList.update(null, this.width, this.height, 0, this.height - 30);
            addRenderableWidget(selectionList);
            if (selectionList.values.size() == 0) {
                addRenderableWidget(new StringWidget(lastScreen.width/2 - 40, lastScreen.height/2 - 40, 80, 10, ComponentUtils.translatable("info.ywzj_midi.no_selectable_value"), this.font));
                addRenderableWidget(new StringWidget(lastScreen.width/2 - 40, lastScreen.height/2 - 30, 80, 10, suggestion, this.font));
            }
        }

        @Override
        public boolean isPauseScreen() {
            return false;
        }

        @Override
        public void onClose() {
            Minecraft.getInstance().setScreen(lastScreen);
            SelectionListButton.this.updateMessage();
        }

    }

}
