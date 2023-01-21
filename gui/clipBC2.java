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

		///////////////////////////
		///// Base Components /////
		///////////////////////////
		JPanel BasePanel = new JPanel();
		JPanel OptionPanel = new JPanel();
		CardLayout BaseLayout = new CardLayout();

		MyPanel MainPanel = new MyPanel(0, gray);
		MyButton.setWindowSize(width, height);

		setTitle(title);
		this.setIconImage((new ImageIcon(Base64Image.decodedImage(app_img))).getImage());
		setBounds(150, 150, width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//////////////////////////////////
		///// Information Components /////
		//////////////////////////////////
		JPanel InformationPanel;
		JLabel InfoLabel;
		JLabel ClipBoardLabel;

		/////////////////////////////
		///// Option Components /////
		/////////////////////////////
		MyPanel OptionCard;
		ChartButton BackCommand;
		ChartButton HomeCommand;
		ChartButton MainCommand;
		ChartButton AddButtonCommand;
		ChartButton DelCommand;
		ChartButton CloseCommand;

		//////////////////////////
		///// tmp Components /////
		//////////////////////////
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
		InformationPanel = new JPanel();
		InformationPanel.setLayout(null);
		InformationPanel.setBounds(0, 0, 640, 25);
		InformationPanel.setPreferredSize(new Dimension(640, 25));
		InformationPanel.setBackground(blue);
		InfoLabel = new ChartLabel(15, "Clip Board: ", white, 20, 5, 640, 15);
		ClipBoardLabel = new ChartLabel(15, "", white, 120, 5, 640, 15);
		InformationPanel.add(InfoLabel);
		InformationPanel.add(ClipBoardLabel);

		////////////////////////
		///// Option Panel /////
		////////////////////////
		OptionCard = new MyPanel(2, blue);
		OptionCard.setLayout(null);
		OptionCard.setChart(0, 0, width, 50);

		BackCommand = new ChartButton(20, "back", "mov_back,", black, 60, 5, 40, 40);
		BackCommand.setupIcon(5);
		BackCommand.setEnabled(false);
		HomeCommand = new ChartButton(20, "home", "home,", black, 120, 5, 40, 40);
		HomeCommand.setupIcon(1);
		MainCommand = new ChartButton(20, "main", "mov_panel,MainPanel", black, 180, 5, 40, 40);
		MainCommand.setupIcon(0);
		AddButtonCommand = new ChartButton(20, "add", "mov_panel,AddButtonPanel", black, 240, 5, 40, 40);
		AddButtonCommand.setupIcon(2);
		DelCommand = new ChartButton(20, "del", "mov_panel,DelButtonPanel", black, 300, 5, 40, 40);
		DelCommand.setupIcon(3);
		CloseCommand = new ChartButton(20, "close", "close,", black, width - 140, 5, 40, 40);
		CloseCommand.setupIcon(4);
		MyButton.setBaseCard(BasePanel, BaseLayout, MainPanel, ClipBoardLabel, BackCommand);

		{
			OptionCard.add(BackCommand);
			OptionCard.add(MainCommand);
			OptionCard.add(AddButtonCommand);
			OptionCard.add(HomeCommand);
			OptionCard.add(DelCommand);
			OptionCard.add(CloseCommand);
		}

		//////////////////////
		///// Main Panel /////
		//////////////////////
		MainPanel.setLayout(null);
		label = new ChartLabel(40, "clipBC 2", blue,
				40, 0, 480, 60);
		MainPanel.add(label);
		//checkbox = new ChartCheckBox(15, "Output File", "output_file", black, white, null, width * 2 / 3, 10, 240, 40);
		//checkbox.setupIcon(1);
		//MainPanel.add(checkbox);

		/////////////////////
		///// Main Card /////
		/////////////////////
		ArrayList<MyCard> card_list = (ArrayList) ObjectIO.loadObject(".obj/object_list.dat");
		if (card_list == null) {
			card_list = new ArrayList<>();
			ArrayList<JComponent> component_list = new ArrayList<>();

			card = new ChartPanel(0, white_gray, 20, 60, width - 140, height / 2 - 40);
			card.setLayout(new GridBagLayout());
			sp = new ChartScrollPane(card);
			component_list.add(0, card);
			card_list.add(new MyCard(sp, component_list));
			ObjectIO.saveObject(".obj/object_list.dat", card_list);
		}
		MyButton.setCardList(card_list);
		for (MyCard c : card_list) {
			c.getScrollPane().setVisible(false);
			MainPanel.add(c.getScrollPane());
		}
		card_list.get(0).getScrollPane().setVisible(true);

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

		////////////////////////////
		///// del Button Panel /////
		////////////////////////////
		MyPanel DelButtonPanel = new MyPanel(0, gray);
		DelButtonPanel.setLayout(null);
		label = new ChartLabel(40, "Delete Button", blue, 40, 0, 480, 60);
		DelButtonPanel.add(label);
		label = new ChartLabel(20, "Button Name", blue, 60, 75, 200, 40);
		DelButtonPanel.add(label);
		items = new String[0];
		combobox = new ChartComboBox(20, items, width - 300, 80, 200, 30);
		DelCommand.setDelButton(combobox);
		DelButtonPanel.add(combobox);
		button = new ChartButton(20, "Delete", "del_button,", black,
				width - 300, 240, 200, 30);
		button.setDelButton(combobox);
		button.enableTexture();
		label = new ChartLabel(20, "Verification", blue, 60, 145, 200, 40);
		DelButtonPanel.add(label);
		checkbox = new ChartCheckBox(15, "I delete the button.", "delete_button", black, white, button, width - 290, 150, 240, 40);
		checkbox.setupIcon(1);
		DelButtonPanel.add(checkbox);
		DelButtonPanel.add(button);

		///////////////////////
		///// Base Layout /////
		///////////////////////
		BasePanel.setLayout(BaseLayout);
		BasePanel.add(MainPanel, "MainPanel");
		BasePanel.add(AddButtonPanel, "AddButtonPanel");
		BasePanel.add(DelButtonPanel, "DelButtonPanel");

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