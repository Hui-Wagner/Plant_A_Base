import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TableController {

    private Queries queryHandler;

    @FXML
    private TableView<PBasic> tableView;

    @FXML
    private TableColumn<PBasic, Integer> PID;

    @FXML
    private TableColumn<PBasic, String> PNames;

    @FXML
    private TableColumn<PBasic, Integer> PKind_ID;

    @FXML
    private TableColumn<PBasic, Integer> Soil_ID;

    @FXML
    private TextField nameInput;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PID.setCellValueFactory(new PropertyValueFactory<PBasic, Integer>("PID"));
        PNames.setCellValueFactory(new PropertyValueFactory<PBasic, String>("PName"));
        PKind_ID.setCellValueFactory(new PropertyValueFactory<PBasic, Integer>("PKind_ID"));
        Soil_ID.setCellValueFactory(new PropertyValueFactory<PBasic, Integer>("Soil_ID"));
    }

    public TableController (Queries queryHandler) {
        this.queryHandler = queryHandler;
    }

    //display PBasic
    public void testButton(ActionEvent e) {
        System.out.println("Test test!");
        //load some kind of data
        try {
            fetchTable();
        } catch (SQLException exception) {
            System.out.println("unable to make connection " + exception);
        }

    }

    private void fetchTable() throws SQLException{
        String query = "SELECT * FROM patrishy_db.PBASIC";
        ResultSet resultSet = queryHandler.executeQuery(query);

        //tries to make a connection again
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();

        // Print column names
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metadata.getColumnName(i);
            System.out.print(columnName + "\t");
        }
        System.out.println(); // Print a new line after the column names

        ObservableList<PBasic> pBasics = tableView.getItems();

        // Print rows
        while (resultSet.next()) {

            //hold data
            int PID = 0;
            String PName = "";
            int PKind_ID = 0;
            int Soil_ID = 0;

            for (int i = 1; i <= columnCount; i++) {
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + ",");

                switch (columnCount) {
                    case 1: PID = Integer.parseInt(columnValue);
                        break;
                    case 2: PName = columnValue;
                        break;
                    case 3: PKind_ID = Integer.parseInt(columnValue);
                        break;
                    case 4: Soil_ID = Integer.parseInt(columnValue);
                        break;
                }

                //store into local tables
                PBasic pbasic = new PBasic(PID,PName,PKind_ID,Soil_ID);
                pBasics.add(pbasic);
                tableView.setItems(pBasics);
            }

            System.out.println();
        }

    }

    public void addToTable(PBasic pbasic) {
        ObservableList<PBasic> customers = tableView.getItems();
        customers.add(pbasic);
        tableView.setItems(customers);
    }

    private void updateUI() {

    }
}
