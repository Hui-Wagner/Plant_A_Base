import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A table with almost any amount of rows and column names
 * Mainly used to translate query results, which can be a table of any size
 * to Object[][] that JTable object can use
 */
public class InfiniteTable {
    /**
     * the names of each column in the table
     */
    String[] columnNames;
    /**
     * the rows of the table
     */
    List<String[]> rows = new ArrayList<>();

    /**
     * empty starter table;
     */
    InfiniteTable() {
        columnNames = new String[]{"No Columns Given"};
    }

    /**
     * @param columnNames the names of each column in the table
     */
    InfiniteTable(String[] columnNames) {
        this.columnNames = columnNames;
    }

    /**
     * @param CNames replaces existing columnsNames with CNames
     */
    public void replaceColumnNames(ArrayList<String> CNames) {
        this.columnNames = new String[CNames.size()];
        for (int i = 0; i < CNames.size(); i++) {
            this.columnNames[i] = CNames.get(i);
        }
    }

    /**
     * @param row a new row to be added to the table
     */
    public void addRow(String[] row) {
        if(row.length != columnNames.length) System.out.println("added row is of incorrect length. Expect error");
        rows.add(row);
    }

    /**
     * @return the names of each column
     */
    public String[] getColumnNames() {
        return columnNames;
    }

    /**
     * @return the rows found in the table as Object[][] for JTable
     */
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

    /**
     * @return gets the data model for table models
     */
    public DefaultTableModel getModel() {
        DefaultTableModel model = new DefaultTableModel(getRows(),getColumnNames());
        return model;
    }
}
