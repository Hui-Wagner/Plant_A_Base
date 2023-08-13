import java.util.List;
public class PTables {

    private List[] columnNames;
    private String[][] rows;

    public PTables(List[] columnNames, String[][] rows) {
        //length check
        if(columnNames.length == rows[0].length) {
            this.columnNames = columnNames;
            this.rows = rows;
        } else {
            System.out.println("Column name length doesn't match the row length");
        }
    }

    public List[] getColumnNames() {
        return columnNames;
    }

    public String[][] getRows() {
        return rows;
    }
}
