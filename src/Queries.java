import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Queries {

    // When user click the button, for example to choose planting season (in PActivities),
    // generate the text by clicked the button, then use that text and saved into query string.

    // Need a buildQuery method to build query each time by the user
    // Need an executeQuery method to executeQuery

    // Then later can be used to display on the interface

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

    public static String buildQuery(List<String> selectedColumns, List<String> tables,
                                    Map<String, String> joinConditions,
                                    Map<String, String> whereConditions) {
        StringBuilder query = new StringBuilder("SELECT ");

        // Add selected columns
        query.append(String.join(", ", selectedColumns));

        // Add tables
        query.append(" FROM ");
        query.append(String.join(", ", tables));

        // Add join conditions if any
        if (!joinConditions.isEmpty()) {
            for (Map.Entry<String, String> entry : joinConditions.entrySet()) {
                query.append(" JOIN ");
                query.append(entry.getKey());
                query.append(" ON ");
                query.append(entry.getValue());
            }
        }

        // Add where conditions if any
        if (!whereConditions.isEmpty()) {
            query.append(" WHERE ");
            List<String> conditions = new ArrayList<>();
            for (Map.Entry<String, String> entry : whereConditions.entrySet()) {
                conditions.add(entry.getKey() + " = " + entry.getValue());
            }
            query.append(String.join(" AND ", conditions));
        }

        return query.toString();
    }


    public static String executeQuery(Connection conn, List<String> selectedColumns, List<String> tables,
                                      Map<String, String> joinConditions,
                                      Map<String, String> whereConditions) throws SQLException {
        String query = buildQuery(selectedColumns, tables, joinConditions, whereConditions);
        StringBuilder resultString = new StringBuilder();
        try (Statement stmt = conn.createStatement()) {
            ResultSet results = stmt.executeQuery(query);

            // Print column names
            resultString.append(String.join("\t", selectedColumns)).append("\n");

            // Print rows
            while (results.next()) {
                for (String column : selectedColumns) {
                    resultString.append(results.getString(column)).append("\t");
                }
                resultString.append("\n");
            }
        }
        return resultString.toString();
    }
    }






