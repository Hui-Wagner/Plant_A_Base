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

                //hold data
                int PID = 0;
                String PName = "";
                int PKind_ID = 0;
                int Soil_ID = 0;

                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = results.getString(i);
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
                }

                System.out.println();
            }
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        ResultSet resultSet = null;
        try (Statement stmt = ConnectDB.connect().createStatement()) {
            System.out.println("Connected to the database!");
            resultSet = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }

        return resultSet;
    }
}


