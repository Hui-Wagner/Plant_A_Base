import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Set up Java Swing GUI
        JFrame frame = new JFrame("Planting A Base");
        frame.setSize(900, 550);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel TopPanel = new JPanel();
        TopPanel.setPreferredSize(new Dimension(145, 550));
        frame.add(TopPanel,BorderLayout.WEST);
        JPanel CenterPanel = new JPanel();

        frame.add(CenterPanel, BorderLayout.CENTER);

        // Add Plant Type label and Plant Type drop-down option on GUI
        JLabel labelPlantType = new JLabel("Plant Type:");
        TopPanel.add(labelPlantType);

        JComboBox<String> plantingType = new JComboBox<>(new String[]{"None","Trees", "Flowers", "Bushes", "Fruit", "Vegetables", "Succulent", "Vines"});
        TopPanel.add(plantingType);

        // Add Plant Month label and Plant Month drop-down option on GUI
        JLabel labelPlantMonth = new JLabel("Planting Month:");
        TopPanel.add(labelPlantMonth);

        JComboBox<String> plantingMonth = new JComboBox<>(new String[]{"None", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        TopPanel.add(plantingMonth);

        // Add Blooming Month label and Blooming Month drop-down option on GUI
        JLabel labelBloomMonth = new JLabel("Blooming Month:");
        TopPanel.add(labelBloomMonth);

        JComboBox<String> bloomingMonth = new JComboBox<>(new String[]{"None", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        TopPanel.add(bloomingMonth);

        // Add Harvesting Month label and Harvesting Month drop-down option on GUI
        JLabel labelHarvestMonth = new JLabel("Harvesting Month:");
        TopPanel.add(labelHarvestMonth);

        JComboBox<String> harvestingMonth = new JComboBox<>(new String[]{"None", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        TopPanel.add(harvestingMonth);

        // Add Soil Type label and Soil Type drop-down option on GUI
        JLabel labelSoilType = new JLabel("Soil Type:");
        TopPanel.add(labelSoilType);

        JComboBox<String> plantSoilType = new JComboBox<>(new String[]{"None", "Loamy", "Sandy", "Silty", "Peaty", "Chalky", "Clay"});
        TopPanel.add(plantSoilType);

        // Add Plant Temperature label and Plant Temperature drop-down option on GUI
        JLabel labelPlantTemp = new JLabel("Temperature:");
        TopPanel.add(labelPlantTemp);

        JComboBox<String> plantTemp = new JComboBox<>(new String[]{"None", "40F ~ 50F", "50F ~ 60F", "60F ~ 70F", "70F ~ 80F"});
        TopPanel.add(plantTemp);

        // Add Plant PH label and Plant PH drop-down option on GUI
        JLabel labelPlantPH = new JLabel("PH Value:");
        TopPanel.add(labelPlantPH);

        JComboBox<String> plantPH = new JComboBox<>(new String[]{"None", "5.0 ~ 5.5", "5.5 ~ 6.0", "6.0 ~ 6.5"});
        TopPanel.add(plantPH);

        //Add Sun Level label and Sun Level drop-down option on GUI
        JLabel labelSunLevel = new JLabel("Sun Level:");
        TopPanel.add(labelSunLevel);

        JComboBox<String> plantSunLevel = new JComboBox<>(new String[]{"None", "Avoid Sunlight", "Somewhat Sunlight", "Love Sunlight"});
        TopPanel.add(plantSunLevel);

        //Add Water Level label and Water Level drop-down option on GUI
        JLabel labelWaterLevel = new JLabel("Water Level:");
        TopPanel.add(labelWaterLevel);

        JComboBox<String> plantWaterLevel = new JComboBox<>(new String[]{"None", "Water Need High", "Water Need Medium", "Water Need Low"});
        TopPanel.add(plantWaterLevel);

        // Add Submit Button on GUI
        JButton btnSubmit = new JButton("Submit");
        TopPanel.add(btnSubmit);

        // Set Text Area for displaying the data that returns from database
        JTextArea txtResults = new JTextArea(30, 50);
        txtResults.setEditable(false);
        JScrollPane scrollResults = new JScrollPane(txtResults);
        CenterPanel.add(scrollResults);

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // If getSelectedIndex() returns 0, meaning it's on the default option "None"
                int plantType = plantingType.getSelectedIndex();
                int plantMonth = plantingMonth.getSelectedIndex();
                int bloomMonth = bloomingMonth.getSelectedIndex();
                int harvestMonth = harvestingMonth.getSelectedIndex();
                int soilType = plantSoilType.getSelectedIndex();
                int temp = plantTemp.getSelectedIndex();
                int PH =plantPH.getSelectedIndex();
                int sunLevel = plantSunLevel.getSelectedIndex();
                int waterLevel = plantWaterLevel.getSelectedIndex();



                //Connect to the database and building up the query by user's demand
                try (Connection conn = ConnectDB.connect()) {

                    // Build up the column selecting statement in the query string
                    List<String> selectedColumns = new ArrayList<>();

                    // Default columns selected are PBASIC.PID, PBASIC.PNames
                    selectedColumns.add("PBASIC.PID");
                    selectedColumns.add("PBASIC.PNames");

                    boolean hasPDETAILEDJoin = false;

                    if (plantType > 0) selectedColumns.add("PKINDS.PKind");

                    if (plantMonth > 0) selectedColumns.add("PACTIVITIES.Planting");
                    if (bloomMonth > 0) selectedColumns.add("PACTIVITIES.Blooming");
                    if (harvestMonth > 0) selectedColumns.add("PACTIVITIES.Harvesting");

                    if (soilType > 0) {selectedColumns.add("SKINDS.Soil_Type"); selectedColumns.add("SKINDS.Soil_Description");}

                    if (temp > 0) {selectedColumns.add("PDETAILED.Temperature");}

                    if (PH > 0) {selectedColumns.add("PDETAILED.PH");}

                    if (sunLevel > 0) selectedColumns.add("SunLevels.SunLevel");

                    if (waterLevel > 0) selectedColumns.add("WLEVELS.WLevel");


                    List<String> tables = Arrays.asList("patrishy_db.PBASIC");
                    Map<String, String> joinConditions = new LinkedHashMap<>();

                    Map<String, String> whereConditions = new HashMap<>();

                    // PKinds table
                    if(plantType != 0) {
                        joinConditions.put("patrishy_db.PKINDS", "PBASIC.PKind_ID = PKINDS.PKind_ID");
                        whereConditions.put("PBASIC.PKind_ID", "'" + plantType + "'");
                    }

                    // PActivities table
                    if (plantMonth != 0 || bloomMonth != 0 || harvestMonth != 0) {
                        joinConditions.put("patrishy_db.PACTIVITIES", "PBASIC.PID = PACTIVITIES.PID");
                        if (plantMonth > 0) whereConditions.put("PACTIVITIES.Planting", "'" + plantMonth + "'");
                        if (bloomMonth > 0) whereConditions.put("PACTIVITIES.Blooming", "'" + bloomMonth + "'");
                        if (harvestMonth > 0) whereConditions.put("PACTIVITIES.Harvesting", "'" + harvestMonth + "'");
                    }

                    // Skinds table
                    if (soilType != 0) {
                        joinConditions.put("patrishy_db.SKINDS", "PBASIC.Soil_ID = SKINDS.Soil_ID");
                        whereConditions.put("SKINDS.Soil_ID", "'" + soilType + "'");
                    }



                    // PDETAILED TABLE
                    if (temp != 0 || PH != 0 || sunLevel != 0 || waterLevel != 0) {
                        joinConditions.put("patrishy_db.PDETAILED", "PBASIC.PID = PDETAILED.PID");

                        if (temp > 0) {
                            int minTemp = 40 + (temp - 1) * 10;
                            int maxTemp = 50 + (temp - 1) * 10;
                            whereConditions.put("PDETAILED.Temperature", "BETWEEN " + minTemp + " AND " + maxTemp);
                        }

                        if (PH > 0) {
                            double minPH = 5.0 + (PH - 1) * 0.5;
                            double maxPH = 5.5 + (PH - 1) * 0.5;
                            whereConditions.put("PDETAILED.PH", "BETWEEN " + minPH + " AND " + maxPH);
                        }
                    }

                    if (sunLevel != 0) {
                        joinConditions.put("patrishy_db.SUNLEVELS", "PDETAILED.SunLevel_ID = SUNLEVELS.SunLevel_ID");
                        whereConditions.put("SUNLEVELS.SunLevel_ID", "'" + sunLevel + "'");
                    }

                    if (waterLevel != 0) {
                        joinConditions.put("patrishy_db.WLEVELS", "PDETAILED.WLevel_ID = WLEVELS.WLevel_ID");
                        whereConditions.put("WLEVELS.WLevel_ID", "'" + waterLevel + "'");
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

    //adjust column width to longest char length + 4
    //font size, font type
    //remove resizeable
    //
}








