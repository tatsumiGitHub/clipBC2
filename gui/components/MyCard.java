package gui.components;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;

public class MyCard implements Serializable {
    private JScrollPane sp;
    private ArrayList<JComponent> component_list;

    public MyCard(JScrollPane _sp, ArrayList<JComponent> _component_list) {
        sp = _sp;
        component_list = _component_list;
    }

    public JScrollPane getScrollPane() {
        return sp;
    }

    public ArrayList<JComponent> getComponentList() {
        return component_list;
    }

    public void setScrollPane(JScrollPane _sp) {
        sp = _sp;
    }

    public void setComponentList(ArrayList<JComponent> _components_list) {
        component_list = _components_list;
    }
}
