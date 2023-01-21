package gui.components.textfield;

import java.awt.*;

public class ChartTextField extends MyTextField {
	public ChartTextField(int size, String text, Color foreground_col, Color background_col,
			int x, int y, int width, int height) {
		super(size, text, foreground_col, background_col);
		this.setBounds(x, y, width, height);
	}
}
