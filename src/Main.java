import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class Main {

    private static final String[] LIST_MONTHS = new String[]{"None", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private static final String[] LIST_PLANT_TYPES = new String[]{"None","Trees", "Flowers", "Bushes", "Fruit", "Vegetables", "Succulent", "Vines"};
    private static final String[] LIST_SOIL_TYPES = new String[]{"None", "Loamy", "Sandy", "Silty", "Peaty", "Chalky", "Clay"};
    private static final String[] LIST_TEMP = new String[]{"None", "40F ~ 50F", "50F ~ 60F", "60F ~ 70F", "70F ~ 80F"};
    private static final String[] LIST_PH = new String[]{"None", "5.0 ~ 5.5", "5.5 ~ 6.0", "6.0 ~ 6.5"};
    private static final String[] LIST_SUN_LEVEL = new String[]{"None", "Avoid Sunlight", "Somewhat Sunlight", "Love Sunlight"};
    private static final String[] LIST_WATER_LEVEL = new String[]{"None", "Water Need High", "Water Need Medium", "Water Need Low"};

    private JFrame frame;
    private JButton submitButton = new JButton("submit");
    private JTable displayTable;

    public static void main(String[] args) {
        new Main();
    }

    Main() {
        // Set up Java Swing GUI
        frame = new JFrame("Planting A Base");
        frame.setSize(700, 550);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon logo = new ImageIcon("/Plant_A_Base/src/Icon.png");
        frame.setIconImage(logo.getImage());

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(145, 550));
        frame.add(leftPanel,BorderLayout.WEST);
        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(650,550));
        frame.add(centerPanel, BorderLayout.CENTER);

        // Add Plant Type label and Plant Type drop-down option on GUI
        JPanel plantTypePanel = new JPanel(new BorderLayout());
        JLabel labelPlantType = new JLabel();
        JComboBox<String> plantingType = new JComboBox<>(LIST_PLANT_TYPES);
        addLabelDropDown(plantTypePanel, "Plant Type:", plantingType);
        leftPanel.add(plantTypePanel);

        // Add Plant Month label and Plant Month drop-down option on GUI
        JPanel plantMonthPanel = new JPanel(new BorderLayout());
        JComboBox<String> plantingMonth = new JComboBox<>(LIST_MONTHS);

        addLabelDropDown(plantMonthPanel, "Planting Month:", plantingMonth);
        leftPanel.add(plantMonthPanel);

        // Add Blooming Month label and Blooming Month drop-down option on GUI

        JPanel bloomMonthPanel = new JPanel(new BorderLayout());
        JComboBox<String> bloomingMonth = new JComboBox<>(LIST_MONTHS);
        addLabelDropDown(bloomMonthPanel,"Blooming Month:",bloomingMonth);
        leftPanel.add(bloomMonthPanel);

        // Add Harvesting Month label and Harvesting Month drop-down option on GUI
        JPanel harvestingMonthPanel = new JPanel(new BorderLayout());
        JComboBox<String> harvestingMonth = new JComboBox<>(LIST_MONTHS);
        addLabelDropDown(harvestingMonthPanel,"Harvesting Month:",harvestingMonth);
        leftPanel.add(harvestingMonthPanel);

        // Add Soil Type label and Soil Type drop-down option on GUI
        JPanel plantSoilPanel = new JPanel(new BorderLayout());
        JComboBox<String> plantSoilType = new JComboBox<>(LIST_SOIL_TYPES);
        addLabelDropDown(plantSoilPanel,"Soil Type:",plantSoilType);
        leftPanel.add(plantSoilPanel);

        // Add Plant Temperature label and Plant Temperature drop-down option on GUI
        JPanel plantTempPanel = new JPanel(new BorderLayout());
        JComboBox<String> plantTemp = new JComboBox<>(LIST_TEMP);
        addLabelDropDown(plantTempPanel, "Temperature:",plantTemp);
        leftPanel.add(plantTempPanel);

        // Add Plant PH label and Plant PH drop-down option on GUI
        JPanel plantPHPanel = new JPanel(new BorderLayout());
        JComboBox<String> plantPH = new JComboBox<>(LIST_PH);
        addLabelDropDown(plantPHPanel, "PH Value:", plantPH);
        leftPanel.add(plantPHPanel);

        //Add Sun Level label and Sun Level drop-down option on GUI
        JPanel plantSunLevelPanel = new JPanel(new BorderLayout());
        JComboBox<String> plantSunLevel = new JComboBox<>(LIST_SUN_LEVEL);
        addLabelDropDown(plantSunLevelPanel, "Sun Level:", plantSunLevel);
        leftPanel.add(plantSunLevelPanel);

        //Add Water Level label and Water Level drop-down option on GUI
        JPanel plantWaterLevelPanel = new JPanel(new BorderLayout());
        JComboBox<String> plantWaterLevel = new JComboBox<>(LIST_WATER_LEVEL);
        addLabelDropDown(plantWaterLevelPanel, "Water Level:", plantWaterLevel);
        leftPanel.add(plantWaterLevelPanel);

        // Add Submit Button on GUI
        JPanel btnPanel = new JPanel(new BorderLayout());
        JButton btnSubmit = new JButton("Submit");
        btnPanel.add(btnSubmit);
        btnPanel.setPreferredSize(new Dimension(110,20));
        leftPanel.add(btnPanel);

        // Set Text Area for displaying the data that returns from database
        JTextArea txtResults = new JTextArea(30, 50);
        txtResults.setEditable(false);
        displayTable = new JTable();
        JScrollPane scrollResults = new JScrollPane(displayTable);
        centerPanel.add(scrollResults);

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

                    InfiniteTable theTable = Queries.executeQueryOO(conn, selectedColumns, tables, joinConditions, whereConditions);
                    displayTable.setModel(theTable.getModel());
                    scrollResults.repaint();
                    scrollResults.revalidate();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        frame.pack();
        frame.setVisible(true);
    }

    private static void addLabelDropDown(JPanel jPanel, String label, JComboBox jComboBox) {
        JLabel label1 = new JLabel(label);
        jPanel.add(label1, BorderLayout.NORTH);
        jComboBox.setPreferredSize(new Dimension(110,20));
        jPanel.add(jComboBox, BorderLayout.CENTER);
    }
    //adjust column width to longest char length + 4
    //font size, font type
    //remove resizeable
    //
}








