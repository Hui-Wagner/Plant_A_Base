import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.awt.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController {

//    @FXML
//    private TableView<TestTable> tableView;
//
//    @FXML
//    private TableColumn<TestTable, String> field1Column;
//
//    @FXML
//    private TableColumn<TestTable, Integer> field2Column;
//
//    @FXML
//    private TableColumn<TestTable, Integer> field3Column;

    @FXML
    private TextField nameInput;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //field1Column.setCellValueFactory(new PropertyValueFactory<TestTable, String>("field1"));
    }

    public void testButton(ActionEvent e) {
        System.out.println("Test test!");
        //load some kind of data
    }

    private void updateTable() {

    }
}
