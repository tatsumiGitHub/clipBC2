package gui.components.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.io.IOException;

import javax.swing.*;

import java.awt.datatransfer.*;

public class MyList_Drag_Drop<T> extends JList<T> {
  @Override
  public void updateUI() {
    setSelectionBackground(null);
    setCellRenderer(null);
    super.updateUI();
    getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    setDropMode(DropMode.INSERT);
    setDragEnabled(true);
    setTransferHandler(new ListItemTransferHandler());
  }
}

class ListItemTransferHandler extends TransferHandler {
  protected static final DataFlavor FLAVOR = new DataFlavor(List.class, "List of items");
  private final List<Integer> indices = new ArrayList<>();
  private int addIndex = -1;
  private int addCount;

  @Override
  protected Transferable createTransferable(JComponent c) {
    JList<?> source = (JList<?>) c;
    for (int i : source.getSelectedIndices()) {
      indices.add(i);
    }
    List<?> selectedValues = source.getSelectedValuesList();
    return new Transferable() {
      @Override
      public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { FLAVOR };
      }

      @Override
      public boolean isDataFlavorSupported(DataFlavor flavor) {
        return Objects.equals(FLAVOR, flavor);
      }

      @Override
      public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (isDataFlavorSupported(flavor)) {
          return selectedValues;
        } else {
          throw new UnsupportedFlavorException(flavor);
        }
      }
    };
  }

  @Override
  public boolean canImport(TransferHandler.TransferSupport info) {
    return info.isDataFlavorSupported(FLAVOR);
  }

  @Override
  public int getSourceActions(JComponent c) {
    return COPY_OR_MOVE;
  }

  private static int getIndex(TransferHandler.TransferSupport info) {
    JList<?> target = (JList<?>) info.getComponent();
    int index;
    if (info.isDrop()) {
      TransferHandler.DropLocation tdl = info.getDropLocation();
      if (tdl instanceof JList.DropLocation) {
        index = ((JList.DropLocation) tdl).getIndex();
      } else {
        index = target.getSelectedIndex();
      }
    } else {
      index = target.getSelectedIndex();
    }
    DefaultListModel<?> listModel = (DefaultListModel<?>) target.getModel();
    int max = listModel.getSize();
    index = index < 0 ? max : index;
    index = Math.min(index, max);
    return index;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean importData(TransferHandler.TransferSupport info) {
    JList<?> target = (JList<?>) info.getComponent();
    DefaultListModel<Object> listModel = (DefaultListModel<Object>) target.getModel();
    int index = getIndex(info);
    addIndex = index;
    try {
      List<?> values = (List<?>) info.getTransferable().getTransferData(FLAVOR);
      for (Object o : values) {
        int i = index++;
        listModel.add(i, o);
        target.addSelectionInterval(i, i);
      }
      addCount = info.isDrop() ? values.size() : 0;
      return true;
    } catch (UnsupportedFlavorException | IOException e) {
      return false;
    }
  }

  @Override
  public boolean importData(JComponent comp, Transferable t) {
    return importData(new TransferHandler.TransferSupport(comp, t));
  }

  @Override
  protected void exportDone(JComponent c, Transferable data, int action) {
    cleanup(c, action == MOVE);
  }

  private void cleanup(JComponent c, boolean remove) {
    if (remove && !indices.isEmpty()) {
      if (addCount > 0) {
        for (int i = 0; i < indices.size(); i++) {
          if (indices.get(i) >= addIndex) {
            indices.set(i, indices.get(i) + addCount);
          }
        }
      }
      JList<?> src = (JList<?>) c;
      DefaultListModel<?> model = (DefaultListModel<?>) src.getModel();
      for (int i = indices.size() - 1; i >= 0; i--) {
        model.remove(indices.get(i));
      }
    }
    indices.clear();
    addCount = 0;
    addIndex = -1;
  }
}