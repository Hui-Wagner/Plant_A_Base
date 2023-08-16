import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InfiniteTable {
    String[] columnNames;
    List<String[]> rows = new ArrayList<>();
    InfiniteTable() {
        columnNames = new String[]{"No Name"};
    }
    InfiniteTable(String[] columnNames) {
        this.columnNames = columnNames;
    }
    public void replaceColumnNames(ArrayList<String> CNames) {
        this.columnNames = new String[CNames.size()];
        for (int i = 0; i < CNames.size(); i++) {
            this.columnNames[i] = CNames.get(i);
        }
    }

    public void addRow(String[] row) {
        if(row.length != columnNames.length) System.out.println("added row is of incorrect length. Expect error");
        rows.add(row);
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public Object[][] getRows() {
        if(rows.size() == 0) return new String[][]{{"No Values Found"}};

        Object[][] objectRows = new Object[rows.size()][rows.get(0).length];
        for (int i = 0; i < rows.size(); i++) {
            String[] currentRow = rows.get(i);
            for (int j = 0; j < currentRow.length; j++) {
                objectRows[i][j] = currentRow[j];
            }
        }
        return objectRows;
    }

    public DefaultTableModel getModel() {
        DefaultTableModel model = new DefaultTableModel(getRows(),getColumnNames());
        return model;
    }
}
