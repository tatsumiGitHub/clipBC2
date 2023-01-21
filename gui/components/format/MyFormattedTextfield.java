package gui.components.format;

import javax.swing.*;
import java.awt.*;
import java.text.*;
import javax.swing.text.*;

public class MyFormattedTextfield extends JFormattedTextField {

    public MyFormattedTextfield(int size, String fill, Color foreground_col, Color background_col) {
        this.setForeground(foreground_col);
        this.setBackground(background_col);
        this.setEditable(true);
        this.setFont(new Font(Font.DIALOG, Font.BOLD, size));
        MaskFormatter mf = null;
        try {
            mf = new MaskFormatter(fill);
            mf.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
