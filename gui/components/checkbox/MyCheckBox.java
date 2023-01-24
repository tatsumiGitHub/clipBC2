package gui.components.checkbox;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import gui.components.*;

public class MyCheckBox extends JCheckBox implements ActionListener, clipBC2_Image {
	private JButton button;

	public MyCheckBox(int size, String text, String cmd, Color foreground_col, Color background_col) {
		this.setText(text);
		this.setActionCommand(cmd);
		this.addActionListener(this);
		this.setForeground(foreground_col);
		this.setBackground(background_col);
		this.setFont(new Font(Font.DIALOG, Font.BOLD, size));
	}

	public void setRelatedButton(JButton button) {
		this.button = button;
	}

	public ImageIcon getResizeIcon(ImageIcon img_icon) {
		int width_button = this.getWidth();
		int height_button = this.getHeight();
		int width_img = img_icon.getIconWidth();
		int height_img = img_icon.getIconHeight();

		if (width_button < height_button) {
			height_button = width_button;
		} else if (height_button < width_button) {
			width_button = height_button;
		}
		int w = width_button / 2;
		int h = height_button / 2;

		if (width_img < height_img) {
			w = width_button * (width_img / height_img);
		} else if (height_img < width_img) {
			h = height_button * (height_img / width_img);
		}
		Image image = img_icon.getImage();
		Image new_img = image.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		return new ImageIcon(new_img);
	}

	public void setupIcon(int mode) {
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setOpaque(false);

		switch (mode) {
			case 1:
				this.setIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(checkbox_img[0]))));
				this.setPressedIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(checkbox_img[1]))));
				this.setRolloverIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(checkbox_img[2]))));
				this.setSelectedIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(checkbox_img[3]))));
				break;
			default:
				this.setIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(default_img[0]))));
				this.setPressedIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(default_img[1]))));
				this.setRolloverIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(default_img[2]))));
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd != null) {
			switch (cmd) {
				case "delete_button":
					if (this.isSelected()) {
						button.setEnabled(true);
					} else {
						button.setEnabled(false);
					}
					break;
			}
		}
	}
}
