package gui.components.combobox;

import javax.swing.*;

import java.awt.*;

public class MyComboBox extends JComboBox<String> {

    public MyComboBox(int size, String[] items) {
		super(items);
        this.setFont(new Font(Font.DIALOG, Font.BOLD, size));
    }
}
