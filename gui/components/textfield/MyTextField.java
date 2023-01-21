package gui.components.textfield;

import javax.swing.*;

import java.awt.*;

public class MyTextField extends JTextField {
	public MyTextField(int size, String text, Color foreground_col, Color background_col) {
		this.setText(text);
		this.setForeground(foreground_col);
		this.setBackground(background_col);
		this.setEditable(true);
		this.setFont(new Font(Font.DIALOG, Font.BOLD, size));
	}
}
