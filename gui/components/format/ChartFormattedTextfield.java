package gui.components.format;

import java.awt.*;

public class ChartFormattedTextfield extends MyFormattedTextfield {
    public ChartFormattedTextfield(int size, String fill, Color foreground_col, Color background_col,
			int x, int y, int width, int height) {
		super(size, fill, foreground_col, background_col);
		this.setBounds(x, y, width, height);
	}
}
