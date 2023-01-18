package gui.components.panel;

import java.awt.*;

public class ChartPanel extends MyPanel {
	public ChartPanel(int option, Color col,
			int x, int y, int width, int height) {
		super(option, col);
		this.setBounds(x, y, width, height);
	}
}