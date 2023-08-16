import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TableController implements Initializable{

    @FXML
    private TableView<Table> tableView = new TableView<Table>();
    @FXML
    private TableColumn<Table, String> column1;
    @FXML
    private TableColumn<Table, String> column2;
    @FXML
    private TableColumn<Table, String> column3;
    @FXML
    private TableColumn<Table, String> column4;
    @FXML
    private TableColumn<Table, String> column5;
    @FXML
    private TableColumn<Table, String> column6;

    @FXML
    private ChoiceBox<String> myPlantTypeChoiceBox;

    @FXML
    private ChoiceBox<String> myMonthChoiceBox;

    private String[] plantTypes = {"Trees", "Flowers", "Bushes", "Fruit", "Vegetables", "Succulent", "Vines"};

    private String[] months = {"1","2","3","4","5","6","7","8","9","10","11","12"};

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //tables
        column1.setCellValueFactory(new PropertyValueFactory<Table, String>(""));
        column2.setCellValueFactory(new PropertyValueFactory<Table, String>(""));
        column3.setCellValueFactory(new PropertyValueFactory<Table, String>(""));
        column4.setCellValueFactory(new PropertyValueFactory<Table, String>(""));
//        column5.setCellValueFactory(new PropertyValueFactory<Table, String>(""));
//        column6.setCellValueFactory(new PropertyValueFactory<Table, String>(""));

        //add all plant types
        myPlantTypeChoiceBox.getItems().addAll(plantTypes);
        myPlantTypeChoiceBox.setOnAction(this::updatePlantType);

        myMonthChoiceBox.getItems().addAll(months);
        myMonthChoiceBox.setOnAction(this::updateMonth);
    }

    private void updatePlantType(ActionEvent event) {
        String myPlantType = myPlantTypeChoiceBox.getValue();
        System.out.println(myPlantType);
    }

    private void updateMonth(ActionEvent event) {
        String myMonth = myMonthChoiceBox.getValue();
        System.out.println(myMonth);
    }

    //retrieves query
    public void submitQuery(ActionEvent e) {
        //processing inputs
        //stop if any are null
        int plantTypeNum = fetchPlantType();
        int monthNum = fetchMonth();
        if(plantTypeNum == -1 || monthNum == -1) {
            System.out.println("Process failed, please make all choices in the query");
        } else {
            PTables thePTable = QueryHandler.makeQuery(plantTypeNum,monthNum);
            System.out.println("table received. isEmpty: " + thePTable.getIsEmpty());
            System.out.println(thePTable.toString());
            updateUI(thePTable);
        }
    }

    private int fetchPlantType() {
        int plantTypeNum = -1;
        String myPlantType = myPlantTypeChoiceBox.getValue();
        if (myPlantType != null) {
            //continue
            for (int i = 0; i < plantTypes.length; i++) {
                if(myPlantType.equals(plantTypes[i])) plantTypeNum = i + 1;
            }
            System.out.println(plantTypeNum + " " + myPlantType);
        } else {
            System.out.println("Plant Type has not been selected");
        }
        return plantTypeNum;
    }

    private int fetchMonth() {
        int monthNum = -1;
        String month = myMonthChoiceBox.getValue();
        if(month != null) {
            monthNum = Integer.parseInt(month);
        } else {
            System.out.println("month has not been selected");
        }
        System.out.println(monthNum);
        return monthNum;
    }


    private void updateUI(PTables inputTable) {
        System.out.println("updating UI");
        //rename columns
        String[] CNames = inputTable.getColumnNames();
        //for each, up to four *can be adjusted later
        int size = 4;
        if(size > CNames.length) {
            size = CNames.length;
        }
        for (int i = 0; i < size; i++) {
            switch (i) {
                case 0: column1 = new TableColumn<>(CNames[0]);
                    break;
                case 1: column2 = new TableColumn<>(CNames[0]);
                    break;
                case 2: column3 = new TableColumn<>(CNames[0]);
                    break;
                case 3: column4 = new TableColumn<>(CNames[0]);
                    break;
                case 4: column5 = new TableColumn<>(CNames[0]);
                    break;
                case 5: column6 = new TableColumn<>(CNames[0]);
                    break;
            }
        }

        //digest PTables to add rows
        List<String[]> rows = inputTable.getRows();
        ObservableList<Table> allTables = tableView.getItems();
        //each row
        for (int i = 0; i < rows.size(); i++) {
            Table currentRow = new Table(rows.get(i));
            allTables.add(currentRow);
        }
        tableView.setItems(allTables);
    }
}
