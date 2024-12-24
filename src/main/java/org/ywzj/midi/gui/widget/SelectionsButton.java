package org.ywzj.midi.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.ywzj.midi.util.ComponentUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SelectionsButton<T> extends CommonButton {

    private final SelectionsScreen<T> selectionsScreen;

    public SelectionsButton(int x, int y, int width, int height, Component component, List<SelectionList.Selection<T>> selections, List<T> data, Screen lastScreen) {
        super(x, y, width, height, component, (button) -> {});
        this.selectionsScreen = new SelectionsScreen<>(component, selections, data, lastScreen);
    }

    public SelectionsButton(int x, int y, int width, int height, Component component, List<SelectionList.Selection<T>> selections, List<T> data, T defaultSingleData, Screen lastScreen) {
        super(x, y, width, height, component, (button) -> {});
        this.selectionsScreen = new SelectionsScreen<>(component, selections, data, defaultSingleData, lastScreen);
    }

    public void setValues(List<T> values) {
        this.selectionsScreen.data = values;
        this.selectionsScreen.selectionButtons.clear();
    }

    public List<T> getValues() {
        return selectionsScreen.data == null ? Collections.singletonList(selectionsScreen.defaultSingleData) : selectionsScreen.data;
    }

    public void updateSelections(List<SelectionList.Selection<T>> values) {
        selectionsScreen.selectionButtons.forEach(button -> button.updateSelections(values));
    }

    @Override
    public void onPress() {
        Minecraft.getInstance().setScreen(selectionsScreen);
    }

    public static class SelectionsScreen<T> extends Screen {

        private List<T> data;
        private T defaultSingleData;
        private final Screen lastScreen;
        private final List<SelectionList.Selection<T>> selections;
        private final List<SelectionListButton<T>> selectionButtons = new ArrayList<>();
        private CommonButton delButton;
        private CommonButton addButton;
        private int count;
        private int x;
        private int y;

        protected SelectionsScreen(Component titleIn, List<SelectionList.Selection<T>> selections, List<T> data, Screen lastScreen) {
            super(titleIn);
            this.selections = selections;
            this.data = data;
            this.lastScreen = lastScreen;
        }

        protected SelectionsScreen(Component titleIn, List<SelectionList.Selection<T>> selections, List<T> data, T defaultSingleData, Screen lastScreen) {
            super(titleIn);
            this.selections = selections;
            this.data = data;
            this.defaultSingleData = defaultSingleData;
            this.lastScreen = lastScreen;
        }

        @Override
        protected void init() {
            count = 0;
            x = width/2 - 250;
            y = height/2 - 120;
            delButton = new CommonButton(x + 75, y - 5, 10, 10, ComponentUtils.literal("-"), (button) -> delSelection());
            addButton = new CommonButton(x + 90, y - 5, 10, 10, ComponentUtils.literal("+"), (button) -> addSelection(null));
            if (selectionButtons.size() != 0) {
                data = selectionButtons.stream().map(SelectionListButton::getValue).collect(Collectors.toList());
                selectionButtons.clear();
                data.forEach(this::addSelection);
            } else {
                if (data == null || data.size() == 0) {
                    SelectionListButton<T> selectionListButton = new SelectionListButton<>(x + 80, y, 60, 20, ComponentUtils.literal("NA"), selections, this, ComponentUtils.EMPTY);
                    if (defaultSingleData != null) {
                        selectionListButton.setValue(defaultSingleData);
                    }
                    selectionButtons.add(selectionListButton);
                    addRenderableWidget(selectionListButton);
                    count += 1;
                    y += 30;
                    addButton.setY(addButton.getY() + 30);
                    delButton.setY(delButton.getY() + 30);
                } else {
                    data.forEach(this::addSelection);
                }
            }
            addRenderableWidget(delButton);
            addRenderableWidget(addButton);
        }

        private void delSelection() {
            if (count <= 1) {
                return;
            }
            removeWidget(selectionButtons.get(selectionButtons.size() - 1));
            selectionButtons.remove(selectionButtons.size() - 1);
            count -= 1;
            if (count % 7 == 6) {
                x -= 70;
                y = height/2 - 120 + 6 * 30;
            } else {
                y -= 30;
            }
            delButton.setY(delButton.getY() - 30);
            addButton.setY(addButton.getY() - 30);
            if (count % 7 == 0) {
                int newY = height/2 - 120 + 7 * 30;
                delButton.setX(delButton.getX() - 70);
                delButton.setY(newY);
                addButton.setX(addButton.getX() - 70);
                addButton.setY(newY);
            }
        }

        private void addSelection(T value) {
            if (count >= 42) {
                return;
            }
            SelectionListButton<T> newSelectionButton = new SelectionListButton<>(x + 80, y, 60, 20, ComponentUtils.literal("NA"), selections, this, ComponentUtils.EMPTY);
            if (value != null) {
                newSelectionButton.setValue(value);
            }
            selectionButtons.add(newSelectionButton);
            addRenderableWidget(newSelectionButton);
            count += 1;
            if (count % 7 == 0) {
                x += 70;
                y = height/2 - 120;
            } else {
                y += 30;
            }
            addButton.setY(addButton.getY() + 30);
            delButton.setY(delButton.getY() + 30);
            if (count != 1 && count % 7 == 1) {
                addButton.setX(addButton.getX() + 70);
                addButton.setY(y);
                delButton.setX(delButton.getX() + 70);
                delButton.setY(y);
            }
        }

        @Override
        public boolean isPauseScreen() {
            return false;
        }

        @Override
        public void onClose() {
            Minecraft.getInstance().setScreen(lastScreen);
        }

    }

}
