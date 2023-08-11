import java.sql.Connection;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {
        try (Connection conn = ConnectDB.connect()) {
            System.out.println("Connected to the database!");

            Queries.getPBASIC(conn);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
    }
}

