package gui;

import java.util.*;
import javax.swing.*;
import java.awt.*;

import gui.components.*;
import gui.components.button.*;
import gui.components.checkbox.*;
import gui.components.combobox.*;
import gui.components.label.*;
import gui.components.panel.*;
import gui.components.scrollpane.*;
import gui.components.textarea.*;
import gui.components.textfield.*;
import system.*;

public class clipBC2 extends JFrame implements clipBC2_Image {

	public clipBC2(String title) {

		///////////////////////
		///// Screen Size /////
		///////////////////////
		final int width = 640;
		final int height = 480;

		/////////////////
		///// Color /////
		/////////////////
		Color blue = new Color(6, 42, 120);
		Color white_gray = new Color(200, 200, 200);
		Color gray = new Color(180, 180, 180);
		Color black = new Color(25, 25, 25);
		Color white = new Color(255, 255, 255);

		//////////////////////
		///// All JPanel /////
		//////////////////////
		JPanel BasePanel = new JPanel();
		JPanel OptionPanel = new JPanel();
		CardLayout BaseLayout = new CardLayout();
		MyButton.setWindowSize(width, height);
		MyButton.setBaseCard(BasePanel, BaseLayout);

		setTitle(title);
		this.setIconImage((new ImageIcon(Base64Image.decodedImage(app_img))).getImage());
		setBounds(150, 150, width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ChartButton button;
		ChartCheckBox checkbox;
		ChartComboBox combobox;
		ChartLabel label;
		ChartPanel card;
		ChartScrollPane sp;
		ChartTextArea textarea;
		ChartTextField textfield;
		////////////////////////////
		///// Information Card /////
		////////////////////////////
		JPanel InformationPanel = new JPanel();
		InformationPanel.setLayout(null);
		InformationPanel.setBounds(0, 0, 640, 25);
		InformationPanel.setPreferredSize(new Dimension(640, 25));
		InformationPanel.setBackground(blue);
		label = new ChartLabel(15, "Clip Board: ", white, 20, 5, 640, 15);
		InformationPanel.add(label);
		label = new ChartLabel(15, "Text", white, 120, 5, 640, 15);
		InformationPanel.add(label);

		//////////////////////
		///// Main Panel /////
		//////////////////////
		MyPanel MainPanel = new MyPanel(0, gray);
		MainPanel.setLayout(null);
		label = new ChartLabel(40, "clipBC 2", blue,
				40, 0, 480, 60);
		MainPanel.add(label);
		checkbox = new ChartCheckBox(15, "Output File", "output_file", black, white, null,
				width * 2 / 3, 10, 240, 40);
		checkbox.setupIcon(1);
		MainPanel.add(checkbox);
		/////////////////////
		///// Main Card /////
		/////////////////////
		ArrayList<MyCard> card_list = (ArrayList) ObjectIO.loadObject(".obj/object_list.dat");
		if (card_list == null) {
			card_list = new ArrayList<>();
			ArrayList<JComponent> component_list = new ArrayList<>();

			card = new ChartPanel(0, white_gray, 20, 60, width - 140, height / 2 - 40);
			card.setLayout(new BoxLayout(card, BoxLayout.PAGE_AXIS));
			/*
			 * /// card [benchmark button]
			 * button = new ChartButton(20, "benchmark", "mov_card,benchmark", blue, 20, 10,
			 * (width - 140) / 2 - 40, 30);
			 * button.enableTexture();
			 * component_list.add(button);
			 * /// card [gpgpu-sim button]
			 * button = new ChartButton(20, "gpgpu-sim", "mov_card,gpgpu-sim", blue, (width
			 * - 140) / 2 + 20, 10,
			 * (width - 140) / 2 - 40, 30);
			 * button.enableTexture();
			 * component_list.add(button);
			 * for (JComponent component : component_list) {
			 * card.add(component);
			 * }
			 */
			sp = new ChartScrollPane(card);
			component_list.add(0, card);
			card_list.add(new MyCard(sp, component_list));
			ObjectIO.saveObject(".obj/object_list.dat", card_list);
		}
		MyButton.setCardList(card_list);
		MainPanel.add(card_list.get(0).getScrollPane());

		////////////////////////////
		///// add Button Panel /////
		////////////////////////////
		MyPanel AddButtonPanel = new MyPanel(0, gray);
		AddButtonPanel.setLayout(null);
		label = new ChartLabel(40, "Add Button", blue, 40, 0, 480, 60);
		AddButtonPanel.add(label);
		label = new ChartLabel(20, "Button Name", blue, 60, 65, 200, 40);
		AddButtonPanel.add(label);
		textfield = new ChartTextField(20, "", black, white,
				width - 400, 70, 300, 30);
		AddButtonPanel.add(textfield);
		label = new ChartLabel(20, "Text Area", blue, 60, 115, 200, 40);
		AddButtonPanel.add(label);
		textarea = new ChartTextArea(15, "", black, white, width - 400, 120, 300, 50);
		sp = new ChartScrollPane(textarea);
		AddButtonPanel.add(sp);
		label = new ChartLabel(20, "Button Type", blue, 60, 205, 200, 40);
		AddButtonPanel.add(label);
		String[] items = { "Clip Board", "New Page" };
		combobox = new ChartComboBox(20, items, width - 300, 210, 200, 30);
		AddButtonPanel.add(combobox);
		button = new ChartButton(20, "Make", "make_button,", black,
				width - 200, 260, 100, 30);
		button.setAddButton(combobox, textarea, textfield);
		button.enableTexture();
		AddButtonPanel.add(button);

		///////////////////////
		///// Option Card /////
		///////////////////////
		MyPanel OptionCard = new MyPanel(2, blue);
		OptionCard.setLayout(null);
		OptionCard.setChart(0, 0, width, 50);

		ChartButton MainCommand = new ChartButton(20, "main", "mov_panel,MainPanel", black, 60, 5, 40, 40);
		MainCommand.setupIcon(0);
		ChartButton AddButtonCommand = new ChartButton(20, "add", "mov_panel,AddButtonPanel", black, 120, 5, 40, 40);
		AddButtonCommand.setupIcon(0);
		ChartButton CloseCommand = new ChartButton(20, "close", "close,", black, width - 140, 5, 40, 40);
		CloseCommand.setupIcon(1);

		{
			OptionCard.add(MainCommand);
			OptionCard.add(AddButtonCommand);
			OptionCard.add(CloseCommand);
		}

		///////////////////////
		///// Base Layout /////
		///////////////////////
		BasePanel.setLayout(BaseLayout);
		BasePanel.add(MainPanel, "MainPanel");
		BasePanel.add(AddButtonPanel, "AddButtonPanel");

		/////////////////////////
		///// Option Layout /////
		/////////////////////////
		OptionPanel.add(OptionCard, "OptionCard");

		getContentPane().add(InformationPanel, BorderLayout.NORTH);
		getContentPane().add(BasePanel, BorderLayout.CENTER);
		getContentPane().add(OptionPanel, BorderLayout.SOUTH);
		this.setResizable(false);
	}
}