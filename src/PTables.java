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
}
