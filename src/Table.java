public class Table {

    public final int attributeCount = 6;
    private String[] columns = new String[attributeCount];

    public Table() {
        for (int i = 0; i < columns.length; i++) {
            columns[i] = "";
        }
    }

    public Table(String[] columns) {
        int size = columns.length;
        if(size > 4) size = 4;
        for (int i = 0; i < size; i++) {
            this.columns[i] = columns[i];
        }
    }
    //used to cooperate with TableView
    public Table(String column1, String column2, String column3, String column4, String column5, String column6) {
        columns[0] = column1;
        columns[1] = column2;
        columns[2] = column3;
        columns[3] = column4;
        columns[4] = column5;
        columns[5] = column6;
    }

    public String getColumn1() {
        return columns[0];
    }
    public String getColumn2() {
        return columns[1];
    }
    public String getColumn3() {
        return columns[2];
    }
    public String getColumn4() {
        return columns[3];
    }
    public String getColumn5() {
        return columns[4];
    }
    public String getColumn6() {
        return columns[5];
    }
}
