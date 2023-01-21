package gui.components.combobox;

public class ChartComboBox extends MyComboBox {

    public ChartComboBox(int size, String[] items,
	int x, int y, int width, int height) {
		super(size, items);
		this.setBounds(x, y, width, height);
    }
}
