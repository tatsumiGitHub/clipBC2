package gui.components.list;

import java.awt.*;

public class ChartList_Drag_Drop extends MyList_Drag_Drop {

	public ChartList_Drag_Drop(int size, int x, int y, int width, int height) {
		super();
		this.setFont(new Font(Font.DIALOG, Font.BOLD, size));
		this.setBounds(x, y, width, height);
    }
}
