import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // Set up Java Swing GUI
        JFrame frame = new JFrame("Planting A Base");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);

        // Add Plant Type label and Plant Type drop-down option on GUI
        JLabel labelPlantType = new JLabel("Plant Type:");
        panel.add(labelPlantType);

        JComboBox<String> plantingType = new JComboBox<>(new String[]{"None","Trees", "Flowers", "Bushes", "Fruit", "Vegetables", "Succulent", "Vines"});
        panel.add(plantingType);

        // Add Plant Month label and Plant Month drop-down option on GUI
        JLabel labelPlantMonth = new JLabel("Planting Month:");
        panel.add(labelPlantMonth);

        JComboBox<String> plantingMonth = new JComboBox<>(new String[]{"None", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        panel.add(plantingMonth);

        // Add Blooming Month label and Blooming Month drop-down option on GUI
        JLabel labelBloomMonth = new JLabel("Blooming Month:");
        panel.add(labelBloomMonth);

        JComboBox<String> bloomingMonth = new JComboBox<>(new String[]{"None", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        panel.add(bloomingMonth);

        // Add Harvesting Month label and Harvesting Month drop-down option on GUI
        JLabel labelHarvestMonth = new JLabel("Harvesting Month:");
        panel.add(labelHarvestMonth);

        JComboBox<String> harvestingMonth = new JComboBox<>(new String[]{"None", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        panel.add(harvestingMonth);

        // Add Soil Type label and Soil Type drop-down option on GUI
        JLabel labelSoilType = new JLabel("Soil Type:");
        panel.add(labelSoilType);

        JComboBox<String> plantSoilType = new JComboBox<>(new String[]{"None", "Loamy", "Sandy", "Silty", "Peaty", "Chalky", "Clay"});
        panel.add(plantSoilType);

        // Add Submit Button on GUI
        JButton btnSubmit = new JButton("Submit");
        panel.add(btnSubmit);

        // Add Text Area for displaying the data that returns from database
        JTextArea txtResults = new JTextArea(30, 50);
        txtResults.setEditable(false);
        JScrollPane scrollResults = new JScrollPane(txtResults);
        panel.add(scrollResults);

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // If getSelectedIndex() returns 0, meaning it's on the default option "None"
                int plantType = plantingType.getSelectedIndex();
                int plantMonth = plantingMonth.getSelectedIndex();
                int bloomMonth = bloomingMonth.getSelectedIndex();
                int harvestMonth = harvestingMonth.getSelectedIndex();
                int soilType = plantSoilType.getSelectedIndex();

                //Connect to the database and building up the query by user's demand
                try (Connection conn = ConnectDB.connect()) {

                    // Build up the column selecting statement in the query string
                    List<String> selectedColumns = new ArrayList<>();

                    // Default columns selected are PBASIC.PID, PBASIC.PNames
                    selectedColumns.add("PBASIC.PID");
                    selectedColumns.add("PBASIC.PNames");

                    if (plantType > 0) selectedColumns.add("PKINDS.PKind");

                    if (plantMonth > 0) selectedColumns.add("PACTIVITIES.Planting");
                    if (bloomMonth > 0) selectedColumns.add("PACTIVITIES.Blooming");
                    if (harvestMonth > 0) selectedColumns.add("PACTIVITIES.Harvesting");

                    if (soilType > 0) {selectedColumns.add("SKINDS.Soil_Type"); selectedColumns.add("SKINDS.Soil_Description");}


                    List<String> tables = Arrays.asList("patrishy_db.PBASIC");
                    Map<String, String> joinConditions = new HashMap<>();
                    Map<String, String> whereConditions = new HashMap<>();

                    if(plantType != 0) {
                        joinConditions.put("patrishy_db.PKINDS", "PBASIC.PKind_ID = PKINDS.PKind_ID");
                        whereConditions.put("PBASIC.PKind_ID", "'" + plantType + "'");
                    }


                    if (plantMonth != 0 || bloomMonth != 0 || harvestMonth != 0) {
                        joinConditions.put("patrishy_db.PACTIVITIES", "PBASIC.PID = PACTIVITIES.PID");
                        if (plantMonth > 0) whereConditions.put("PACTIVITIES.Planting", "'" + plantMonth + "'");
                        if (bloomMonth > 0) whereConditions.put("PACTIVITIES.Blooming", "'" + bloomMonth + "'");
                        if (harvestMonth > 0) whereConditions.put("PACTIVITIES.Harvesting", "'" + harvestMonth + "'");
                        whereConditions.put("PBASIC.PKind_ID", "'" + plantType + "'");
                    }


                    if (soilType != 0) {
                        joinConditions.put("patrishy_db.SKINDS", "PBASIC.Soil_ID = SKINDS.Soil_ID");
                        whereConditions.put("SKINDS.Soil_ID", "'" + soilType + "'");
                    }

                    String results = Queries.executeQuery(conn, selectedColumns, tables, joinConditions, whereConditions);
                    txtResults.setText(results);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    txtResults.setText("Failed to connect to the database.");
                }
            }
        });

        frame.setVisible(true);
    }
}








