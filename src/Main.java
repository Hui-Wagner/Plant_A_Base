import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = ConnectDB.connect()) {
            System.out.println("Connected to the database!");

            System.out.println("Before you planting...");
            System.out.println("What kinds of plant you want to plant in your backyard?");
            System.out.println("1, Trees\n" +
                               "2, Flowers\n" +
                               "3, Bushes\n" +
                               "4, Fruit'\n" +
                               "5, Vegetables'\n" +
                               "6, Succulent'\n" +
                               "7, Vines'\n ");
            int plantChoice = Integer.parseInt(scanner.nextLine());

            switch (plantChoice) {
                case 1:
                    System.out.println("You choose to plant Trees");
                    break;
                case 2:
                    System.out.println("You choose to plant Flowers");
                    break;
                case 3:
                    System.out.println("You choose to plant Bushes");
                    break;
                case 4:
                    System.out.println("You choose to plant Fruit");
                    break;
                case 5:
                    System.out.println("You choose to plant Vegetables");
                    break;
                case 6:
                    System.out.println("You choose to plant Succulent");
                    break;
                case 7:
                    System.out.println("You choose to plant Vines");
                    break;
            }

            System.out.println("What months you are planning to plant? Type 1~12");
            String plantMonths = scanner.nextLine();
            int month = Integer.parseInt(plantMonths);

            switch (month) {
                case 1:
                    System.out.println("You are planning to plant in January");
                    break;
                case 2:
                    System.out.println("You are planning to plant in February");
                    break;
                case 3:
                    System.out.println("You are planning to plant in March");
                    break;
                case 4:
                    System.out.println("You are planning to plant in April");
                    break;
                case 5:
                    System.out.println("You are planning to plant in May");
                    break;
                case 6:
                    System.out.println("You are planning to plant in June");
                    break;
                case 7:
                    System.out.println("You are planning to plant in July");
                    break;
                case 8:
                    System.out.println("You are planning to plant in August");
                    break;
                case 9:
                    System.out.println("You are planning to plant in September");
                    break;
                case 10:
                    System.out.println("You are planning to plant in October");
                    break;
                case 11:
                    System.out.println("You are planning to plant in November");
                    break;
                case 12:
                    System.out.println("You are planning to plant in December");
                    break;
            }
            scanner.close();

// Define the columns you want to select
            List<String> selectedColumns = Arrays.asList("pbasic.PNAMES", "PACTIVITIES.PLANTING");

// Define the tables you want to query
            List<String> tables = Arrays.asList("patrishy_db.pbasic");

// Define any join conditions (if needed)
            Map<String, String> joinConditions = new HashMap<>();
            joinConditions.put("patrishy_db.PACTIVITIES", "pbasic.PID = PACTIVITIES.PID");


// Define where conditions based on user's input
            Map<String, String> whereConditions = new HashMap<>();
            whereConditions.put("PACTIVITIES.PLANTING", "'" + month + "'");
            whereConditions.put("pbasic.PKIND_ID", "'" + plantChoice + "'");


// Execute the query using the defined components
            Queries.executeQuery(conn, selectedColumns, tables, joinConditions, whereConditions);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
    }
}


