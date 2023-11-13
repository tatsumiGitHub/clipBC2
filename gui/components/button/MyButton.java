package gui.components.button;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.io.*;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.datatransfer.*;

import gui.components.*;
import gui.components.checkbox.MyCheckBox;
import gui.components.combobox.*;
import gui.components.label.*;
import gui.components.list.*;
import gui.components.panel.*;
import gui.components.scrollpane.*;
import gui.components.textarea.*;
import gui.components.textfield.*;
import system.*;

public class MyButton extends JButton implements ActionListener, clipBC2_Image {
	private static Deque<Integer> page_deque = new ArrayDeque<>();
	private static int win_width = 0;
	private static int win_height = 0;
	private static JButton BackButton = null;
	private static CardLayout BaseLayout = null;
	private static JPanel BasePanel = null;
	private static JPanel MainPanel = null;
	private static JLabel InfoLabel = null;
	private static int currentPanel_idx = 0;

	private boolean enable_texture = false;
	private Color default_background = new Color(250, 250, 250, 0);// not selecting
	private Color default_foreground = new Color(100, 100, 100, 250);// not selecting foreground color
	private Color selecting_background = new Color(65, 105, 225, 250);// selecting background color
	private Color selecting_foreground = new Color(250, 250, 250, 0);// selecting foreground color
	private int R = 20;

	private static ArrayList<MyCard> card_list = new ArrayList<>();
	private MyButton button = null;
	private MyComboBox combobox = null;
	private MyComboBox combobox_sub = null;
	private MyTextArea TextArea = null;
	private MyTextField TextField = null;
	private MyCheckBox checkbox = null;
	private MyList_Drag_Drop list_drag_drop = null;

	public MyButton(int size, String text, String cmd, Color col) {// Basis Button
		this.addActionListener(this);
		this.setActionCommand(cmd);
		this.setForeground(col);
		this.setText(text);
		this.setFont(new Font(Font.DIALOG, Font.BOLD, size));
		if (cmd.equals("del_button,")) {
			this.setEnabled(false);
		}
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
				this.setIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(home_img[0]))));
				this.setPressedIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(home_img[1]))));
				this.setRolloverIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(home_img[2]))));
				break;
			case 2:
				this.setIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(make_img[0]))));
				this.setPressedIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(make_img[1]))));
				this.setRolloverIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(make_img[2]))));
				break;
			case 3:
				this.setIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(delete_img[0]))));
				this.setPressedIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(delete_img[1]))));
				this.setRolloverIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(delete_img[2]))));
				break;
			case 4:
				this.setIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(close_img[0]))));
				this.setPressedIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(close_img[1]))));
				this.setRolloverIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(close_img[2]))));
				break;
			case 5:
				this.setIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(back_img[0]))));
				this.setPressedIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(back_img[1]))));
				this.setRolloverIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(back_img[2]))));
				this.setDisabledIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(back_img[3]))));
				break;
			case 6:
				this.setIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(sort_img[0]))));
				this.setPressedIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(sort_img[1]))));
				this.setRolloverIcon(getResizeIcon(new ImageIcon(Base64Image.decodedImage(sort_img[2]))));
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

	public static void setBaseCard(JPanel _BasePanel, CardLayout _BaseLayout, JPanel _MaiPanel, JLabel _InfoLabel,
			JButton _BackButton) {
		BasePanel = _BasePanel;
		BaseLayout = _BaseLayout;
		MainPanel = _MaiPanel;
		InfoLabel = _InfoLabel;
		BackButton = _BackButton;
	}

	public static void setCardList(ArrayList<MyCard> _card_list) {
		card_list = _card_list;
	}

	public static ArrayList<MyCard> getCardList() {
		return card_list;
	}

	public void setMyButton(MyButton _button) {
		button = _button;
	}

	public void setMyComboBox(MyComboBox _combobox) {
		combobox = _combobox;
	}

	public void setMyComboBox_Sub(MyComboBox _combobox_sub) {
		combobox_sub = _combobox_sub;
	}

	public void setMyCheckBox(MyCheckBox _checkbox) {
		checkbox = _checkbox;
	}

	public void setMyComponents(MyComboBox _combobox, MyTextArea _TextArea, MyTextField _TextField) {
		combobox = _combobox;
		TextArea = _TextArea;
		TextField = _TextField;
	}

	public void setMyList_Drag_drop(MyList_Drag_Drop _list_drag_drop) {
		list_drag_drop = _list_drag_drop;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		InfoLabel.setText(MyClipBoard.getClipBoard());
		int tmp, component_num;
		String str_tmp;
		String cmd = e.getActionCommand();
		GridBagConstraints gbc;
		if (cmd != null) {
			String[] token = cmd.split(",", 2);
			switch (token[0]) {
				case "close":
					currentPanel_idx = 0;
					System.exit(0);
					break;
				case "clipboard":
					MyClipBoard.setClipBoard(token[1]);
					InfoLabel.setText(MyClipBoard.getClipBoard());
					break;
				case "del_button":
					if (0 <= combobox.getSelectedIndex()) {
						int idx = 0;
						str_tmp = (String) combobox.getSelectedItem();
						System.out.println("Cardlist: " + ((JButton) card_list.get(currentPanel_idx).getComponentList().get(combobox.getSelectedIndex() + 2)).getText());
						System.out.println("JPanel  : " + ((JButton) card_list.get(currentPanel_idx).getComponentList().get(0).getComponent(combobox.getSelectedIndex() + 1)).getText());
						card_list.get(currentPanel_idx).getComponentList().get(0).remove(combobox.getSelectedIndex() + 1);
						card_list.get(currentPanel_idx).getComponentList().remove(combobox.getSelectedIndex() + 2);

						combobox.removeAllItems();
						for (JComponent c : card_list.get(currentPanel_idx).getComponentList()) {
							if (c instanceof JButton) {
								gbc = new GridBagConstraints();
								gbc.gridx = idx % 2;
								gbc.gridy = idx / 2 + 1;
								gbc.insets = new Insets(10, 10, 10, 10);
								((GridBagLayout) (card_list.get(currentPanel_idx).getComponentList().get(0)
										.getLayout())).setConstraints((JButton) c, gbc);
								combobox.addItem(((JButton) c).getText());
							}
							idx++;
						}
						ObjectIO.saveObject(".obj/object_list.dat", card_list);
						System.out.println("Info: Delete Button (" + currentPanel_idx + ", "
								+ combobox.getSelectedIndex() + ")");
						if (combobox.getSelectedIndex() != -1) {
							combobox.setSelectedIndex(0);
						}
						InfoLabel.setText("Delete Button (" + str_tmp + ")");
					}
					checkbox.setSelected(false);
					setEnabled(false);
					break;
				case "home":
					if (currentPanel_idx != 0) {
						page_deque.push(currentPanel_idx);
						BackButton.setEnabled(true);
					}
					card_list.get(currentPanel_idx).getScrollPane().setVisible(false);
					currentPanel_idx = 0;
					card_list.get(currentPanel_idx).getScrollPane().setVisible(true);
					BaseLayout.show(BasePanel, "MainPanel");
					break;
				case "load_button":
					tmp = combobox.getSelectedIndex();
					if (tmp != 0) {
						TextField.setText(
								((JButton) card_list.get(currentPanel_idx).getComponentList().get(tmp + 1)).getText());
						TextArea.setText(((JButton) card_list.get(currentPanel_idx).getComponentList().get(tmp + 1))
								.getActionCommand().split(",", 2)[1]);
						InfoLabel.setText("Load Button ("
								+ ((JButton) card_list.get(currentPanel_idx).getComponentList().get(tmp + 1)).getText()
								+ ")");
					} else {
						TextField.setText("");
						TextArea.setText("");
						InfoLabel.setText("Clear TextField and TextArea");
					}
					break;
				case "make_button":
					component_num = card_list.get(currentPanel_idx).getComponentList().size();
					switch (combobox.getSelectedIndex()) {
						case 0:
							try {
								if (!TextField.getText().trim().equals("") && !TextArea.getText().trim().equals("")) {
									MyButton button;
									button = new MyButton(20, TextField.getText(),
											"clipboard," + TextArea.getText(),
											new Color(6, 42, 120));
									button.enableTexture();

									gbc = new GridBagConstraints();

									tmp = combobox_sub.getSelectedIndex();
									if (checkbox.isSelected() && tmp != 0) {
										gbc.gridx = (tmp + 1) % 2;
										gbc.gridy = (tmp + 1) / 2;
										gbc.insets = new Insets(10, 10, 10, 10);
										((GridBagLayout) (card_list.get(currentPanel_idx).getComponentList().get(0)
												.getLayout())).setConstraints(button, gbc);
										card_list.get(currentPanel_idx).getComponentList().get(0).add(button, tmp + 1);
										card_list.get(currentPanel_idx).getComponentList().get(0).remove(tmp);
										card_list.get(currentPanel_idx).getComponentList().get(0).setComponentZOrder(button, tmp);
										card_list.get(currentPanel_idx).getComponentList().set(tmp + 1, button);
									} else {
										gbc.gridx = component_num % 2;
										gbc.gridy = component_num / 2;
										gbc.insets = new Insets(10, 10, 10, 10);
										((GridBagLayout) (card_list.get(currentPanel_idx).getComponentList().get(0)
												.getLayout())).setConstraints(button, gbc);
										card_list.get(currentPanel_idx).getComponentList().get(0).add(button);
										card_list.get(currentPanel_idx).getComponentList().add(button);
									}

									combobox_sub.removeAllItems();
									combobox_sub.addItem("New Button");
									for (JComponent c : card_list.get(currentPanel_idx).getComponentList()) {
										if (c instanceof JButton) {
											combobox_sub.addItem(((JButton) c).getText());
										}
									}
									ObjectIO.saveObject(".obj/object_list.dat", card_list);
									System.out.println("Info: Make New Button (" + currentPanel_idx + ", "
											+ card_list.get(currentPanel_idx).getComponentList().size() + ")");
									InfoLabel.setText("Make New Button (" + button.getText() + ")");
								} else {
									if (TextField.getText().trim().equals("")) {
										System.out.println("Info: No Text in Text Field");
									} else if (TextArea.getText().trim().equals("")) {
										System.out.println("Info: No Text in Text Area");
									}
								}
							} catch (NullPointerException err) {
								err.printStackTrace();
								JOptionPane.showMessageDialog(this, err.getMessage(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}
							break;
						case 1:
							try {
								if (!TextField.getText().trim().equals("")) {
									MyButton button;
									button = new MyButton(20, TextField.getText(),
											"mov_card," + (card_list.size()),
											new Color(6, 42, 120));
									button.enableTexture();

									gbc = new GridBagConstraints();

									tmp = combobox_sub.getSelectedIndex();
									if (checkbox.isSelected() && tmp != 0) {
										gbc.gridx = (tmp + 1) % 2;
										gbc.gridy = (tmp + 1) / 2;
										gbc.insets = new Insets(10, 10, 10, 10);
										((GridBagLayout) (card_list.get(currentPanel_idx).getComponentList().get(0)
												.getLayout())).setConstraints(button, gbc);
										card_list.get(currentPanel_idx).getComponentList().get(0).remove(tmp);
										card_list.get(currentPanel_idx).getComponentList().get(0).add(button, tmp);
										card_list.get(currentPanel_idx).getComponentList().set(tmp + 1, button);
									} else {
										gbc.gridx = component_num % 2;
										gbc.gridy = component_num / 2;
										gbc.insets = new Insets(10, 10, 10, 10);
										((GridBagLayout) (card_list.get(currentPanel_idx).getComponentList().get(0)
												.getLayout())).setConstraints(button, gbc);
										card_list.get(currentPanel_idx).getComponentList().get(0).add(button);
										card_list.get(currentPanel_idx).getComponentList().add(button);
									}

									ArrayList<JComponent> component_list = new ArrayList<>();
									JPanel card = new ChartPanel(0, new Color(200, 200, 200), 20, 60,
											win_width - 140,
											win_height / 2 - 40);
									card.setLayout(new GridBagLayout());
									MyLabel label_tmp = new MyLabel(20, TextField.getText(), new Color(6, 42, 120));
									card.add(label_tmp);
									gbc = new GridBagConstraints();
									gbc.gridx = 0;
									gbc.gridy = 0;
									gbc.gridwidth = 2;
									gbc.gridheight = 1;
									((GridBagLayout) (card.getLayout())).setConstraints(label_tmp, gbc);
									component_list.add(label_tmp);
									JScrollPane sp = new ChartScrollPane(card);
									component_list.add(0, card);
									sp.setVisible(false);
									card_list.add(new MyCard(sp, component_list));
									MainPanel.add(sp);

									combobox_sub.removeAllItems();
									combobox_sub.addItem("New Button");
									for (JComponent c : card_list.get(currentPanel_idx).getComponentList()) {
										if (c instanceof JButton) {
											combobox_sub.addItem(((JButton) c).getText());
										}
									}
									ObjectIO.saveObject(".obj/object_list.dat", card_list);
									System.out.println("Info: Make New Page (" + currentPanel_idx + ", "
											+ card_list.get(currentPanel_idx).getComponentList().size() + ")");
									InfoLabel.setText("Make New Button (" + button.getText() + ")");
								} else {
									if (TextField.getText().trim().equals("")) {
										System.out.println("Info: No Text in Text Field");
									}
								}
							} catch (NullPointerException err) {
								err.printStackTrace();
								JOptionPane.showMessageDialog(this, err.getMessage(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}
							break;
						default:
							System.out.println("Info: Undefined combobox[" + combobox.getSelectedIndex() + "] ("
									+ combobox.getSelectedItem() + ")");
							break;
					}
					checkbox.setSelected(false);
					break;
				case "mov_back":
					if (0 < page_deque.size()) {
						card_list.get(currentPanel_idx).getScrollPane().setVisible(false);
						currentPanel_idx = page_deque.pop();
						card_list.get(currentPanel_idx).getScrollPane().setVisible(true);
					}
					if (page_deque.size() < 1) {
						BackButton.setEnabled(false);
					}
					break;
				case "mov_card":
					try {
						page_deque.push(currentPanel_idx);
						BackButton.setEnabled(true);
						card_list.get(currentPanel_idx).getScrollPane().setVisible(false);
						currentPanel_idx = Integer.parseInt(token[1]);
						card_list.get(currentPanel_idx).getScrollPane().setVisible(true);
					} catch (NumberFormatException err) {
						err.printStackTrace();
						currentPanel_idx = 0;
					}
					break;
				case "mov_panel":
					BaseLayout.show(BasePanel, token[1]);
					switch (token[1]) {
						case "AddButtonPanel":
							combobox_sub.removeAllItems();
							combobox_sub.addItem("New Button");
							for (JComponent c : card_list.get(currentPanel_idx).getComponentList()) {
								if (c instanceof JButton) {
									combobox_sub.addItem(((JButton) c).getText());
								}
							}
							break;
						case "DelButtonPanel":
							combobox.removeAllItems();
							for (JComponent c : card_list.get(currentPanel_idx).getComponentList()) {
								if (c instanceof JButton) {
									combobox.addItem(((JButton) c).getText());
								}
							}
							break;
						case "SortButtonPanel":
							int i = 1;
							DefaultListModel model = new DefaultListModel();
							for (JComponent c : card_list.get(currentPanel_idx).getComponentList()) {
								if (c instanceof JButton) {
									model.addElement(String.format("%03d| %s", i, ((JButton) c).getText()));
									i++;
								}
							}
							if (model.getSize() == 0) {
								button.setEnabled(false);
							} else {
								button.setEnabled(true);
							}
							list_drag_drop.setModel(model);
							break;
					}
					break;
				case "sort_button":
					tmp = list_drag_drop.getModel().getSize();
					int[] sorts = new int[tmp];
					try {
						if (0 < tmp) {
							for (int i = 0; i < tmp; i++) {
								str_tmp = (String) list_drag_drop.getModel().getElementAt(i);
								sorts[i] = Integer.parseInt(str_tmp.split("\\|")[0]) + 1;
							}
						}
					} catch (NumberFormatException ex) {
						ex.printStackTrace();
						break;
					}
					card_list.get(currentPanel_idx).getComponentList().get(0).removeAll();
					gbc = new GridBagConstraints();
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.gridwidth = 2;
					gbc.gridheight = 1;
					((GridBagLayout) (card_list.get(currentPanel_idx).getComponentList().get(0)
												.getLayout())).setConstraints(card_list.get(currentPanel_idx).getComponentList().get(1), gbc);
					card_list.get(currentPanel_idx).getComponentList().get(0).add(card_list.get(currentPanel_idx).getComponentList().get(1));
					JComponent[] components = card_list.get(currentPanel_idx).getComponentList()
							.toArray(new JComponent[tmp]);
					gbc = new GridBagConstraints();
					for (int i = 0; i < tmp; i++) {
						gbc.gridx = i % 2;
						gbc.gridy = i / 2 + 1;
						gbc.insets = new Insets(10, 10, 10, 10);
						((GridBagLayout) (card_list.get(currentPanel_idx).getComponentList().get(0).getLayout()))
								.setConstraints(components[sorts[i]], gbc);
						card_list.get(currentPanel_idx).getComponentList().set(i + 2, components[sorts[i]]);
						card_list.get(currentPanel_idx).getComponentList().get(0).add(components[sorts[i]]);
					}

					int i = 1;
					DefaultListModel model = new DefaultListModel();
					for (JComponent c : card_list.get(currentPanel_idx).getComponentList()) {
						if (c instanceof JButton) {
							model.addElement(String.format("%03d| %s", i, ((JButton) c).getText()));
							i++;
						}
					}
					
					list_drag_drop.setModel(model);

					ObjectIO.saveObject(".obj/object_list.dat", card_list);
					System.out.println("sort buttons");
					InfoLabel.setText("sort buttons");

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

class MyClipBoard {
	public static String getClipBoard() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Clipboard clip = kit.getSystemClipboard();

		try {
			return (String) clip.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public static void setClipBoard(String str) {// copying words
		Toolkit kit = Toolkit.getDefaultToolkit();
		Clipboard clip = kit.getSystemClipboard();

		StringSelection ss = new StringSelection(str);
		clip.setContents(ss, ss);
	}
}