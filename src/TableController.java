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
            System.out.printf("unable to make connection");
        }

    }

    private void fetchTable() throws SQLException{
        String query = "SELECT * FROM patrishy_db.PBASIC";
        queryHandler.executeQuery(query);
    }

    public void addToTable(PBasic pbasic) {
        ObservableList<PBasic> customers = tableView.getItems();
        customers.add(pbasic);
        tableView.setItems(customers);
    }

    private void updateUI() {

    }
}
