import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class ButtonsAndDropDowns {
    private static final String[] LIST_MONTHS = new String[]{"None", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private static final String[] LIST_PLANT_TYPES = new String[]{"None","Trees", "Flowers", "Bushes", "Fruit", "Vegetables", "Succulent", "Vines"};
    private static final String[] LIST_SOIL_TYPES = new String[]{"None", "Loamy", "Sandy", "Silty", "Peaty", "Chalky", "Clay"};
    private static final String[] LIST_TEMP = new String[]{"None", "40F ~ 50F", "50F ~ 60F", "60F ~ 70F", "70F ~ 80F"};
    private static final String[] LIST_PH = new String[]{"None", "5.0 ~ 5.5", "5.5 ~ 6.0", "6.0 ~ 6.5"};
    private static final String[] LIST_SUN_LEVEL = new String[]{"None", "Avoid Sunlight", "Somewhat Sunlight", "Love Sunlight"};
    private static final String[] LIST_WATER_LEVEL = new String[]{"None", "Water Need High", "Water Need Medium", "Water Need Low"};
    public static void makeDropDowns(JPanel panel, GUI theGUI) {
        // Add Plant Type label and Plant Type drop-down option on GUI
        JComboBox<String> plantingType = new JComboBox<>(LIST_PLANT_TYPES);
        panel.add(addLabelDropDown("Plant Type:", plantingType));

        // Add Plant Month label and Plant Month drop-down option on GUI
        JComboBox<String> plantingMonth = new JComboBox<>(LIST_MONTHS);
        panel.add(addLabelDropDown("Planting Month:", plantingMonth));

        // Add Blooming Month label and Blooming Month drop-down option on GUI
        JComboBox<String> bloomingMonth = new JComboBox<>(LIST_MONTHS);
        panel.add(addLabelDropDown("Blooming Month:",bloomingMonth));

        // Add Harvesting Month label and Harvesting Month drop-down option on GUI
        JComboBox<String> harvestingMonth = new JComboBox<>(LIST_MONTHS);
        panel.add(addLabelDropDown("Harvesting Month:",harvestingMonth));

        // Add Soil Type label and Soil Type drop-down option on GUI
        JComboBox<String> plantSoilType = new JComboBox<>(LIST_SOIL_TYPES);
        panel.add(addLabelDropDown("Soil Type:",plantSoilType));

        // Add Plant Temperature label and Plant Temperature drop-down option on GUI
        JComboBox<String> plantTemp = new JComboBox<>(LIST_TEMP);
        panel.add(addLabelDropDown("Temperature:",plantTemp));

        // Add Plant PH label and Plant PH drop-down option on GUI
        JComboBox<String> plantPH = new JComboBox<>(LIST_PH);
        panel.add(addLabelDropDown("PH Value:", plantPH));

        //Add Sun Level label and Sun Level drop-down option on GUI
        JComboBox<String> plantSunLevel = new JComboBox<>(LIST_SUN_LEVEL);
        panel.add(addLabelDropDown("Sun Level:", plantSunLevel));

        //Add Water Level label and Water Level drop-down option on GUI
        JComboBox<String> plantWaterLevel = new JComboBox<>(LIST_WATER_LEVEL);
        panel.add(addLabelDropDown("Water Level:", plantWaterLevel));

        // Add Submit Button on GUI
        JPanel btnPanel = new JPanel(new BorderLayout());
        JButton btnSubmit = new JButton("Submit");

        btnPanel.add(btnSubmit);
        btnPanel.setSize(new Dimension(140,40));
        panel.add(btnPanel);

        // Set Display Table for displaying the data that returns from database
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

                    // If plant type is chosen, add the column statement into the query
                    if (plantType > 0) selectedColumns.add("PKINDS.PKind");

                    // If planting month, blooming month, or harvesting month  is chosen, add the column statement into the query
                    if (plantMonth > 0) selectedColumns.add("PACTIVITIES.Planting");
                    if (bloomMonth > 0) selectedColumns.add("PACTIVITIES.Blooming");
                    if (harvestMonth > 0) selectedColumns.add("PACTIVITIES.Harvesting");

                    // If soil type, temp, PH, sunLevel, or waterLevel is chosen, add the column statement into the query
                    if (soilType > 0) {selectedColumns.add("SKINDS.Soil_Type"); selectedColumns.add("SKINDS.Soil_Description");}
                    if (temp > 0) {selectedColumns.add("PDETAILED.Temperature");}
                    if (PH > 0) {selectedColumns.add("PDETAILED.PH");}
                    if (sunLevel > 0) selectedColumns.add("SunLevels.SunLevel");
                    if (waterLevel > 0) selectedColumns.add("WLEVELS.WLevel");

                    // Add table PBASIC
                    List<String> tables = Arrays.asList("patrishy_db.PBASIC");
                    Map<String, String> joinConditions = new LinkedHashMap<>();

                    Map<String, String> whereConditions = new HashMap<>();

                    // Combine PKinds table with PBASIC table and add join statement into the query
                    if(plantType != 0) {
                        joinConditions.put("patrishy_db.PKINDS", "PBASIC.PKind_ID = PKINDS.PKind_ID");
                        whereConditions.put("PBASIC.PKind_ID", "'" + plantType + "'");
                    }

                    // Combine PActivities table with PBASIC table and add join statement into the query
                    if (plantMonth != 0 || bloomMonth != 0 || harvestMonth != 0) {
                        joinConditions.put("patrishy_db.PACTIVITIES", "PBASIC.PID = PACTIVITIES.PID");
                        if (plantMonth > 0) whereConditions.put("PACTIVITIES.Planting", "'" + plantMonth + "'");
                        if (bloomMonth > 0) whereConditions.put("PACTIVITIES.Blooming", "'" + bloomMonth + "'");
                        if (harvestMonth > 0) whereConditions.put("PACTIVITIES.Harvesting", "'" + harvestMonth + "'");
                    }

                    // // Combine SKINDS table with PBASIC table and add join statement into the query
                    if (soilType != 0) {
                        joinConditions.put("patrishy_db.SKINDS", "PBASIC.Soil_ID = SKINDS.Soil_ID");
                        whereConditions.put("SKINDS.Soil_ID", "'" + soilType + "'");
                    }

                    // Combine PDETAILED TABLE with PBASIC table and add join statement into the query
                    if (temp != 0 || PH != 0 || sunLevel != 0 || waterLevel != 0) {
                        joinConditions.put("patrishy_db.PDETAILED", "PBASIC.PID = PDETAILED.PID");

                        // Convert the selection number from drop-down menu to min and max temperature to define the range and add into query
                        if (temp > 0) {
                            int minTemp = 40 + (temp - 1) * 10;
                            int maxTemp = 50 + (temp - 1) * 10;
                            whereConditions.put("PDETAILED.Temperature", "BETWEEN " + minTemp + " AND " + maxTemp);
                        }

                        // Convert the selection number from drop-down menu to min and max PH value to define the range and add into query
                        if (PH > 0) {
                            double minPH = 5.0 + (PH - 1) * 0.5;
                            double maxPH = 5.5 + (PH - 1) * 0.5;
                            whereConditions.put("PDETAILED.PH", "BETWEEN " + minPH + " AND " + maxPH);
                        }
                    }

                    // Combine SUNLEVELS TABLE with PBASIC table and add join statement into the query
                    if (sunLevel != 0) {
                        joinConditions.put("patrishy_db.SUNLEVELS", "PDETAILED.SunLevel_ID = SUNLEVELS.SunLevel_ID");
                        whereConditions.put("SUNLEVELS.SunLevel_ID", "'" + sunLevel + "'");
                    }

                    // Combine WLEVELS TABLE with PBASIC table and add join statement into the query
                    if (waterLevel != 0) {
                        joinConditions.put("patrishy_db.WLEVELS", "PDETAILED.WLevel_ID = WLEVELS.WLevel_ID");
                        whereConditions.put("WLEVELS.WLevel_ID", "'" + waterLevel + "'");
                    }

                    InfiniteTable theTable = Queries.executeQuery(conn, selectedColumns, tables, joinConditions, whereConditions);
                    theGUI.updateTable(theTable.getModel());

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Does the repetitive action for each drop down menu
     * @param label the label for the dropDown jComboBox
     * @param jComboBox the dropDown menu
     * @return Panel with both the label and dropDown menu
     */
    private static JPanel addLabelDropDown(String label, JComboBox jComboBox) {
        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.add(new JLabel(label), BorderLayout.NORTH);
        jComboBox.setPreferredSize(new Dimension(140,20));
        jPanel.add(jComboBox, BorderLayout.CENTER);
        return jPanel;
    }
}
