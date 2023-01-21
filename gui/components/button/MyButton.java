package gui.components.button;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import gui.components.*;
import gui.components.combobox.*;
import gui.components.panel.*;
import gui.components.scrollpane.*;
import gui.components.textarea.*;
import gui.components.textfield.*;
import system.*;

public class MyButton extends JButton implements ActionListener, clipBC2_Image {
	private static int win_width = 0;
	private static int win_height = 0;
	private static JPanel BasePanel = null;
	private static CardLayout BaseLayout = null;
	private static int currentPanel_idx = 0;

	private boolean enable_texture = false;
	private Color default_background = new Color(250, 250, 250, 0);// not selecting
	private Color default_foreground = new Color(100, 100, 100, 250);// not selecting foreground color
	private Color selecting_background = new Color(102, 204, 255, 250);// selecting background color
	private Color selecting_foreground = new Color(250, 250, 250, 0);// selecting foreground color
	private int R = 20;

	private static ArrayList<MyCard> card_list = new ArrayList<>();
	private MyComboBox ButtonType = null;
	private MyTextArea TextArea = null;
	private MyTextField TextField = null;

	public MyButton(int size, String text, String cmd, Color col) {// Basis Button
		this.addActionListener(this);
		this.setActionCommand(cmd);
		this.setForeground(col);
		this.setText(text);
		this.setFont(new Font(Font.DIALOG, Font.BOLD, size));
		if (cmd.equals("execute")) {
			this.setEnabled(false);
		}
		currentPanel_idx = 0;
	}

	public void enableTexture() {
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setOpaque(false);
		enable_texture = true;
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
		int w = width_button;
		int h = height_button;
		if (width_img < height_img) {
			w = width_button * (width_img / height_img);
		} else if (height_img < width_img) {
			h = height_button * (height_img / width_img);
		}
		this.setSize(new DimensionUIResource(w, h));

		Image image = img_icon.getImage();
		Image new_img = image.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		return new ImageIcon(new_img);
	}

	public void setupIcon(int n) {
		this.setText("");
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setOpaque(false);

		switch (n) {
			case 1:
				this.setIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(close_img[0]))));
				this.setPressedIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(close_img[1]))));
				this.setRolloverIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(close_img[2]))));
				break;
			default:
				this.setIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(default_img[0]))));
				this.setPressedIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(default_img[1]))));
				this.setRolloverIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(default_img[2]))));
				break;
		}
	}

	public void setTexture(Color default_background, Color default_foreground,
			Color selecting_background, Color selecting_foreground) {
		if (default_background != null) {
			this.default_background = default_background;
		}
		if (default_foreground != null) {
			this.default_foreground = default_foreground;
		}
		if (selecting_background != null) {
			this.selecting_background = selecting_background;
		}
		if (selecting_foreground != null) {
			this.selecting_foreground = selecting_foreground;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		if (enable_texture == true) {
			int x = 0;
			int y = 0;
			int w = getWidth();
			int h = getHeight();
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Shape area = new RoundRectangle2D.Double(x, y, w - 1, h - 1, R, R);
			Color ssc = default_background;
			Color bgc = default_foreground;
			ButtonModel m = getModel();
			if (m.isPressed()) {
				ssc = selecting_foreground;
				bgc = selecting_background;
			} else if (m.isRollover()) {
				ssc = selecting_background;
				bgc = selecting_foreground;
			}
			g2.setPaint(new GradientPaint(x, y, ssc, x, y + h, bgc, true));
			g2.fill(area);
			g2.setPaint(default_foreground);
			g2.draw(area);
			g2.dispose();
		}
		super.paintComponent(g);
	}

	@Override
	public JToolTip createToolTip() {
		return new MyToolTip();
	}

	@Override
	public Point getToolTipLocation(MouseEvent e) {
		Point po = e.getPoint();
		po.x = -this.getWidth() / 2;
		po.y = -this.getHeight() - 3;
		return po;
	}

	public static void setWindowSize(int _win_width, int _win_height) {
		win_width = _win_width;
		win_height = _win_height;
	}

	public static void setBaseCard(JPanel _BasePanel, CardLayout _BaseLayout) {
		BasePanel = _BasePanel;
		BaseLayout = _BaseLayout;
	}

	public static void setCardList(ArrayList<MyCard> _card_list) {
		card_list = _card_list;
	}

	public static ArrayList<MyCard> getCardList() {
		return card_list;
	}

	public void setAddButton(MyComboBox _ButtonType, MyTextArea _TextArea, MyTextField _TextField) {
		ButtonType = _ButtonType;
		TextArea = _TextArea;
		TextField = _TextField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd != null) {
			String[] token = cmd.split(",", 2);
			switch (token[0]) {
				case "close":
					System.exit(0);
					break;
				case "make_button":
					if (ButtonType.getSelectedItem().equals("Clip Board") && TextField.getText() != null
							&& TextArea.getText() != null) {
						if (!TextField.getText().trim().equals("")) {
							if (!TextArea.getText().trim().equals("")) {
								int component_num = card_list.get(currentPanel_idx).getComponentList().size();

								MyButton button;
								button = new MyButton(20, TextField.getText(), "mov_card,gpgpu-sim", new Color(6, 42, 120));
								button.enableTexture();
								card_list.get(currentPanel_idx).getComponentList().add(button);
								
								GridBagConstraints gbc = new GridBagConstraints();

								gbc.gridx = (component_num % 2 == 1) ? 20 : (win_width - 140) / 2 + 20;
								gbc.gridy = 10 + ((component_num - 1) / 2) * 40;
								gbc.gridwidth = (win_width - 140) / 2 - 40;
								gbc.gridheight = 30;
								gbc.weightx = 1.0d;
								gbc.fill = GridBagConstraints.NONE;
								gbc.insets = new Insets(10, 10, 10, 10);
								((GridBagLayout) (card_list.get(currentPanel_idx).getComponentList().get(0).getLayout())).setConstraints(button, gbc);
								card_list.get(currentPanel_idx).getComponentList().get(0).add(button);
								ObjectIO.saveObject(".obj/object_list.dat", card_list);
							} else {
								System.out.println("Info: No Text in Text Field");
							}
						} else {
							System.out.println("Info: No Text in Text Field");
						}
					}
					break;
				case "mov_panel":
					BaseLayout.show(BasePanel, token[1]);
					break;
				default:
					System.out.println("Undefined signal");
			}
		}
	}
}

class MyToolTip extends JToolTip {
	private static final int TRI_HEIGHT = 4;
	private static final int round = 10;
	private HierarchyListener listener;

	@Override
	public void updateUI() {
		removeHierarchyListener(listener);
		super.updateUI();
		listener = e -> {
			Component c = e.getComponent();
			if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0
					&& c.isShowing()) {
				Window w = SwingUtilities.getWindowAncestor(c);
				if (w instanceof JWindow) {
					((JWindow) w).setBackground(new Color(0x0, true));
				}
			}
		};
		addHierarchyListener(listener);
		setOpaque(false);
		setForeground(Color.WHITE);
		setBackground(new Color(0xC8_00_00_00, true));
		setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		d.width = d.width + round / 2;
		d.height = d.height + round / 2;
		return d;
	}

	@Override
	public void addNotify() {
		super.addNotify();
		Component parent = this.getParent();
		if (parent != null) {
			if (parent instanceof JComponent) {
				JComponent jparent = (JComponent) parent;
				jparent.setOpaque(true);
			}
		}
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Shape s = makeBalloonShape();
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fill(s);
		g2.setColor(getBackground());
		g2.draw(s);
		g2.dispose();
		super.paintComponent(g);
	}

	private Shape makeBalloonShape() {
		Insets i = getInsets();
		int w = getWidth() + 10;
		int h = getHeight() + 10;
		Polygon triangle = new Polygon();
		triangle.addPoint(i.left + 2 * TRI_HEIGHT, 0);
		triangle.addPoint(i.left + TRI_HEIGHT, TRI_HEIGHT);
		triangle.addPoint(i.left + 3 * TRI_HEIGHT, TRI_HEIGHT);
		Area area = new Area(new RoundRectangle2D.Float(0, TRI_HEIGHT,
				w - round, h - i.bottom - round,
				round, round));
		area.add(new Area(triangle));
		return area;
	}

	@Override
	public JToolTip createToolTip() {
		JToolTip tip = new MyToolTip();
		tip.updateUI();
		tip.setComponent(this);
		return tip;
	}
}
