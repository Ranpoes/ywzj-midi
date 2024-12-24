package org.ywzj.midi.gui.widget;

import net.minecraft.client.gui.components.AbstractSliderButton;
import org.ywzj.midi.util.ComponentUtils;

public class ValueSlider extends AbstractSliderButton {

    public String valueName;
    public int value;
    public int maxValue;
    private boolean updated;
    private ValueToText valueToText;

    public ValueSlider(int x, int y, int width, int height, String valueName, int value, int maxValue) {
        super(x, y, width, height, ComponentUtils.literal(valueName + ": " + value), (float) value / maxValue);
        this.valueName = valueName;
        this.value = value;
        this.maxValue = maxValue;
    }

    public ValueSlider(int x, int y, int width, int height, String valueName, int value, int maxValue, ValueToText valueToText) {
        super(x, y, width, height, ComponentUtils.literal(valueToText.valueToText(value)), (float) value / maxValue);
        this.valueName = valueName;
        this.value = value;
        this.maxValue = maxValue;
        this.valueToText = valueToText;
    }

    public void updatePos(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public void updateValue(double value) {
        super.value = value;
        this.applyValue();
        this.updateMessage();
    }

    public boolean isUpdated() {
        if (updated) {
            updated = false;
            return true;
        }
        return false;
    }

    @Override
    public void onDrag(double p_93591_, double p_93592_, double p_93593_, double p_93594_) {
        super.onDrag(p_93591_, p_93592_, p_93593_, p_93594_);
        updated = true;
    }

    @Override
    protected void updateMessage() {
        if (valueToText != null) {
            this.setMessage(ComponentUtils.literal(valueToText.valueToText(value)));
        } else {
            this.setMessage(ComponentUtils.literal(valueName + ": " + this.value));
        }
    }

    @Override
    protected void applyValue() {
        this.value = (int) (super.value * maxValue);
    }

    public interface ValueToText {
        String valueToText(int value);
    }

}
