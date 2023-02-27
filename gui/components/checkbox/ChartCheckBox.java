package gui.components.checkbox;

import java.awt.*;

public class ChartCheckBox extends MyCheckBox {
    public ChartCheckBox(int size, String text, String cmd, Color foreground_col, Color background_col,
            int x, int y, int width, int height) {
        super(size, text, cmd, foreground_col, background_col);
        this.setBounds(x, y, width, height);
    }
}
