package gui.components.scrollpane;

import java.awt.*;
import javax.swing.*;

public class ChartScrollPane extends JScrollPane {
	public ChartScrollPane(JComponent component) {
		super(component);
		this.setBounds(component.getX(), component.getY(), component.getWidth() + 20, component.getHeight() + 20);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}
}