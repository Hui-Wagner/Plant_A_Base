import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Queries class provides utility methods for executing and building SQL queries.
 */
public class Queries {
    /**
     * Fetches and prints all records from the PBASIC table in the patrishy_db database.
     *
     * @param conn The database connection object.
     * @throws SQLException if there's an error during the SQL operation.
     */
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

    /**
     * Builds a SQL query string based on the provided parameters.
     *
     * @param selectedColumns The columns to be selected in the query.
     * @param tables The tables to be queried.
     * @param joinConditions The join conditions for the tables.
     * @param whereConditions The conditions for the WHERE clause.
     * @return The constructed SQL query string.
     */
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
                if (entry.getValue().contains("BETWEEN")) {
                    conditions.add(entry.getKey() + " " + entry.getValue());
                } else {
                    conditions.add(entry.getKey() + " = " + entry.getValue());
                }
            }
            query.append(String.join(" AND ", conditions));
        }

        System.out.println(query.toString());
        return query.toString();
    }

    /**
     * Executes a SQL query based on the provided parameters and returns the result as an InfiniteTable object.
     *
     * @param conn The database connection object.
     * @param selectedColumns The columns to be selected in the query.
     * @param tables The tables to be queried.
     * @param joinConditions The join conditions for the tables.
     * @param whereConditions The conditions for the WHERE clause.
     * @return An InfiniteTable object containing the result of the query.
     * @throws SQLException if there's an error during the SQL operation.
     */
    public static InfiniteTable executeQuery(Connection conn, List<String> selectedColumns, List<String> tables,
                                             Map<String, String> joinConditions,
                                             Map<String, String> whereConditions) throws SQLException {
        String query = buildQuery(selectedColumns, tables, joinConditions, whereConditions);
        InfiniteTable outTable = new InfiniteTable();
        try (Statement stmt = conn.createStatement()) {
            ResultSet results = stmt.executeQuery(query);
            ResultSetMetaData metadata = results.getMetaData();
            int columnCount = metadata.getColumnCount();

            // Collect column names
            ArrayList<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metadata.getColumnName(i);
                columnNames.add(columnName);
            }
            outTable.replaceColumnNames(columnNames);

            // Collect rows
            while (results.next()) {
                String[] currentRow = new String[columnNames.size()];
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = results.getString(i);
                    currentRow[i - 1] = columnValue;
                }
                outTable.addRow(currentRow);
            }
        }

        return outTable;
    }
}







