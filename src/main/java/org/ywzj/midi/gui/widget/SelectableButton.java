package org.ywzj.midi.gui.widget;

import org.ywzj.midi.util.ComponentUtils;

import java.util.Collections;
import java.util.List;

public class SelectableButton<T> extends CommonButton {

    private final LinkedSelections<T> selections;
    private final boolean lockWidth;
    private int middleX;
    private final int firstWidth;

    public SelectableButton(int middleX, int y, int width, int height, LinkedSelections<T> selections) {
        super(middleX, y, width, height, ComponentUtils.literal(selections.get().name), (button) -> {});
        this.selections = selections;
        this.lockWidth = width != -1;
        this.middleX = middleX;
        this.firstWidth = width <= 0 ? selections.get().name.length() * 5 + 15 : width;
        updateWidth();
    }

    @Override
    public void updatePos(int x, int y) {
        middleX = x;
        updateWidth();
        this.setY(y);
    }

    private void updateWidth() {
        if (lockWidth) {
            this.setX(middleX - firstWidth/2);
            this.setWidth(firstWidth);
        } else {
            int newWidth = selections.get().name.length() * 5 + 15;
            this.setX(middleX - newWidth/2);
            this.setWidth(newWidth);
        }
    }

    public T getValue() {
        return selections.get().value;
    }

    public boolean setValue(T value) {
        int step = 0;
        while (!selections.get().value.equals(value) && step < selections.values.size()) {
            selections.next();
            step += 1;
        }
        if (step >= selections.values.size()) {
            return false;
        } else {
            String name = selections.get().name;
            updateWidth();
            setMessage(ComponentUtils.literal(name));
            return true;
        }
    }

    @Override
    public void onPress() {
        String name = selections.next().name;
        updateWidth();
        this.setMessage(ComponentUtils.literal(name));
    }

    public static class LinkedSelections<T> {

        private List<Selection<T>> values;
        private int select = 0;

        public LinkedSelections(List<Selection<T>> values) {
            this.values = values.size() == 0 ? Collections.singletonList(new Selection<>(null, "    ")) : values;
        }

        public LinkedSelections(LinkedSelections<T> selections) {
            this.values = selections.values.size() == 0 ? Collections.singletonList(new Selection<>(null, "    ")) : selections.values;
        }

        public Selection<T> get() {
            return values.get(select);
        }

        public Selection<T> next() {
            select = (select + 1) % values.size();
            return values.get(select);
        }

        public void update(List<Selection<T>> values) {
            boolean findFlag = false;
            for (int select = 0; select < values.size(); select++) {
                if (values.get(select).value.equals(this.values.get(this.select).value)) {
                    this.select = select;
                    findFlag = true;
                    break;
                }
            }
            if (!findFlag) {
                this.select = 0;
            }
            this.values = values.size() == 0 ? Collections.singletonList(new Selection<>(null, "    ")) : values;
        }

    }

    public static class Selection<T> {

        public T value;
        public String name;

        public Selection(T value, String name) {
            this.value = value;
            this.name = name;
        }

    }

}
