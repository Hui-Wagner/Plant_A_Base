import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static final String URL = "jdbc:mysql://98.59.242.200:3306/patrishy_db";
    private static final String USER = "Patrishy";
    private static final String PASSWORD = "DogBeePlay#01";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


