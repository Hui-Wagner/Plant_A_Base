import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class TableGUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Queries queryHandler = new Queries();

        TableController controller = new TableController(queryHandler);

        // Load your FXML file and set the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start.fxml"));
        loader.setController(controller);

        Parent root = loader.load();
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(String.valueOf(getClass().getResource("application.css")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void TableGUI(String[] args) {
        launch(args);
    }
}
