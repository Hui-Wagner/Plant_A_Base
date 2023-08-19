import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class provides functionality to connect to a MySQL database.
 */
public class ConnectDB {

    private static final String URL = "jdbc:mysql://98.59.242.200:3306/patrishy_db";

    private static final String USER = "Patrishy";

    private static final String PASSWORD = "DogBeePlay#01";

    /**
     * Establishes and returns a connection to the database using the provided URL, username, and password.
     *
     * @return Connection to the database.
     * @throws SQLException if there's an error while connecting.
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}



