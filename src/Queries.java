import java.sql.*;

public class Queries {

    public static void getPBASIC(Connection conn) throws SQLException {
        String query = "SELECT * FROM patrishy_db.PBASIC";
        try (Statement stmt = conn.createStatement()) {
            ResultSet results = stmt.executeQuery(query);
            ResultSetMetaData metadata = results.getMetaData();
            int columnCount = metadata.getColumnCount();

            // Print column names
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metadata.getColumnName(i);
                System.out.print(columnName + "\t");
            }
            System.out.println(); // Print a new line after the column names

            // Print rows
            while (results.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = results.getString(i);
                    System.out.print(columnValue + ",");
                }
                System.out.println();
            }
        }
    }

}


