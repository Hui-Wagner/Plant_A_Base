import java.util.List;
public class PTables {

    private String[] columnNames;
    private List<String[]> rows;

    private boolean isEmpty = true;

    public  PTables() {}
    public PTables(String[] columnNames, List<String[]> rows) {
        //length check
        if(columnNames.length == rows.get(0).length) {
            this.columnNames = columnNames;
            this.rows = rows;
            this.isEmpty = false;
        } else {
            System.out.println("Column name length doesn't match the row length");
        }
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public List<String[]> getRows() {
        return rows;
    }

    public boolean getIsEmpty() {
        return isEmpty;
    }

    @Override
    public String toString() {
        if(isEmpty) {
            return "empty table";
        }
        String output = "Table output \n";
        output+= rowToString(columnNames);
        for (int i = 0; i < rows.size(); i++) {
            output+= "\n";
            output+= rowToString(rows.get(i));
        }
        return output;
    }

    private String rowToString(String[] theRow) {
        String output = "";
        for (int i = 0; i < theRow.length; i++) {
            output +=  theRow[i] + "\t";
        }
        return output;
    }
}
