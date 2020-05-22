package com.fromLab.GUI.component;

import javax.swing.*;
import java.awt.*;

/**
 * @author wsh
 * @date 2019-12-26
 * The component for setting the progress
 */
public class TaskProgressSlider extends JSlider {

    public TaskProgressSlider() {
        super();
        initProgressSlider();
    }

    public TaskProgressSlider(Integer value) {
        super(0, 100, value);
        initProgressSlider();
    }

    private void initProgressSlider() {
        this.setMajorTickSpacing(10);
        this.setMinorTickSpacing(5);
        this.setPaintTicks(true);
        this.setPaintLabels(true);
    }
}
