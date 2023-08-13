import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable{

    private Queries queryHandler;

    @FXML
    private TableView<PTables> tableView;

    @FXML
    private TableColumn<PTables, Integer> PID;

    @FXML
    private TableColumn<PTables, String> PNames;

    @FXML
    private TableColumn<PTables, Integer> PKind_ID;

    @FXML
    private TableColumn<PTables, Integer> Soil_ID;

    @FXML
    private TextField nameInput;

    @FXML
    private ChoiceBox<String> myPlantTypeChoiceBox;

    @FXML
    private ChoiceBox<String> myMonthChoiceBox;

    private String[] plantTypes = {"Trees", "Flowers", "Bushes", "Fruit", "Vegetables", "Succulent", "Vines"};

    private String[] months = {"1","2","3","4","5","6","7","8","9","10","11","12"};

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //tables
//        PID.setCellValueFactory(new PropertyValueFactory<PBasic, Integer>("PID"));
//        PNames.setCellValueFactory(new PropertyValueFactory<PBasic, String>("PName"));
//        PKind_ID.setCellValueFactory(new PropertyValueFactory<PBasic, Integer>("PKind_ID"));
//        Soil_ID.setCellValueFactory(new PropertyValueFactory<PBasic, Integer>("Soil_ID"));

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

//    public TableController (Queries queryHandler) {
//        this.queryHandler = queryHandler;
//    }

    //retrieves query
    public void testButton(ActionEvent e) {
        //processing inputs
        //stop if any are null
        int plantTypeNum = fetchPlantType();
        int monthNum = fetchMonth();
        if(plantTypeNum == -1 || monthNum == -1) {
            System.out.println("Process failed, please make all choices in the query");
        } else {
            PTables theTable = QueryHandler.makeQuery(plantTypeNum,monthNum);
            System.out.println("table received. isEmpty: " + theTable.getIsEmpty());
            System.out.println(theTable.toString());
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

//    private void fetchTable() throws SQLException{
//        String query = "SELECT * FROM patrishy_db.PBASIC";
//        ResultSet resultSet = queryHandler.executeQuery(query);
//
//        //tries to make a connection again
//        ResultSetMetaData metadata = resultSet.getMetaData();
//        int columnCount = metadata.getColumnCount();
//
//        // Print column names
//        for (int i = 1; i <= columnCount; i++) {
//            String columnName = metadata.getColumnName(i);
//            System.out.print(columnName + "\t");
//        }
//        System.out.println(); // Print a new line after the column names
//
//        ObservableList<PBasic> pBasics = tableView.getItems();
//
//        // Print rows
//        while (resultSet.next()) {
//
//            //hold data
//            int PID = 0;
//            String PName = "";
//            int PKind_ID = 0;
//            int Soil_ID = 0;
//
//            for (int i = 1; i <= columnCount; i++) {
//                String columnValue = resultSet.getString(i);
//                System.out.print(columnValue + ",");
//
//                switch (columnCount) {
//                    case 1: PID = Integer.parseInt(columnValue);
//                        break;
//                    case 2: PName = columnValue;
//                        break;
//                    case 3: PKind_ID = Integer.parseInt(columnValue);
//                        break;
//                    case 4: Soil_ID = Integer.parseInt(columnValue);
//                        break;
//                }
//
//                //store into local tables
//                PBasic pbasic = new PBasic(PID,PName,PKind_ID,Soil_ID);
//                pBasics.add(pbasic);
//                tableView.setItems(pBasics);
//            }
//
//            System.out.println();
//        }
//
//    }

//    public void addToTable(PBasic pbasic) {
//        ObservableList<PBasic> customers = tableView.getItems();
//        customers.add(pbasic);
//        tableView.setItems(customers);
//    }

    private void updateUI() {

    }
}
