package gui;

import javax.swing.*;
import java.awt.*;

import gui.components.*;
import gui.components.button.*;
import gui.components.checkbox.*;
import gui.components.label.*;
import gui.components.panel.*;
import gui.components.scrollpane.*;

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
		JPanel cardPanel = new JPanel();
		JPanel OptionPanel = new JPanel();
		CardLayout CardLayout = new CardLayout();
		CardLayout OptionLayout = new CardLayout();

		setTitle(title);
		this.setIconImage((new ImageIcon(Base64Image.decodedImage(app_img))).getImage());
		setBounds(150, 150, width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		////////////////////////////
		///// Information Card /////
		////////////////////////////
		JPanel InformationPanel = new JPanel();
		InformationPanel.setLayout(null);
		InformationPanel.setBounds(0, 0, 640, 25);
		InformationPanel.setPreferredSize(new Dimension(640, 25));
		InformationPanel.setBackground(blue);
		ChartLabel InformationLabel = new ChartLabel(15, "Clip Board: ", white, 20, 5, 640, 15);
		InformationPanel.add(InformationLabel);
		ChartLabel FilePathLabel = new ChartLabel(15, "Text", white, 120, 5, 640, 15);
		InformationPanel.add(FilePathLabel);

		/////////////////////
		///// Main Card /////
		/////////////////////
		MyPanel MainPanel = new MyPanel(0, gray);
		ChartLabel TitleLabel = new ChartLabel(60, "clipBC 2", blue,
				40, 0, 480, 80);
		ChartCheckBox OutputFileCheckBox = new ChartCheckBox(15, "Output File", "output_file", black, white, null,
				width / 2 + 20, 20, 400, 40);
		OutputFileCheckBox.setupIcon(1);
		ChartPanel card;
		card = new ChartPanel(0, white_gray, 60, 100, 520, 240);
		ChartButton button = new ChartButton(30, "benchmark", "BenchmarkCard", blue,
				20, 20, 475, 40);
		button.enableTexture();
		card.add(button);
		ChartScrollPane MainCard = new ChartScrollPane(card);
		card = new ChartPanel(0, white_gray, 60, 100, 520, 240);
		button = new ChartButton(30, "benchmark", "BenchmarkCard", blue,
				20, 20, 475, 40);
		card.add(button);
		{
			MainPanel.add(TitleLabel);
			MainPanel.add(OutputFileCheckBox);
			MainPanel.add(MainCard, "MainCard");
		}

		///////////////////////
		///// Option Card /////
		///////////////////////
		MyPanel OptionCard = new MyPanel(2, blue);
		OptionCard.setChart(0, height - 50, width, 50);

		ChartButton CloseCommand = new ChartButton(20, "close", "close", black,
				width - 100, 5, 100, 40);
		CloseCommand.setupIcon(4);

		{
			OptionCard.add(CloseCommand);
		}

		///////////////////////
		///// Card Layout /////
		///////////////////////
		cardPanel.setLayout(CardLayout);
		cardPanel.add(MainPanel, "MainPanel");

		/////////////////////////
		///// Option Layout /////
		/////////////////////////
		OptionPanel.setLayout(OptionLayout);
		OptionPanel.add(OptionCard, "OptionCard");

		getContentPane().add(InformationPanel, BorderLayout.NORTH);
		getContentPane().add(cardPanel, BorderLayout.CENTER);
		getContentPane().add(OptionPanel, BorderLayout.SOUTH);
	}
}
