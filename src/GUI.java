import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class GUI extends JFrame implements ActionListener{
    private static final String[] LIST_MONTHS = new String[]{"None", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private static final String[] LIST_PLANT_TYPES = new String[]{"None","Trees", "Flowers", "Bushes", "Fruit", "Vegetables", "Succulent", "Vines"};
    private static final String[] LIST_SOIL_TYPES = new String[]{"None", "Loamy", "Sandy", "Silty", "Peaty", "Chalky", "Clay"};
    private static final String[] LIST_TEMP = new String[]{"None", "40F ~ 50F", "50F ~ 60F", "60F ~ 70F", "70F ~ 80F"};
    private static final String[] LIST_PH = new String[]{"None", "5.0 ~ 5.5", "5.5 ~ 6.0", "6.0 ~ 6.5"};
    private static final String[] LIST_SUN_LEVEL = new String[]{"None", "Avoid Sunlight", "Somewhat Sunlight", "Love Sunlight"};
    private static final String[] LIST_WATER_LEVEL = new String[]{"None", "Water Need High", "Water Need Medium", "Water Need Low"};
    private JComboBox<String> plantingType = new JComboBox<>(LIST_PLANT_TYPES);
    private JComboBox<String> plantingMonth = new JComboBox<>(LIST_MONTHS);
    private JComboBox<String> bloomingMonth = new JComboBox<>(LIST_MONTHS);
    private JComboBox<String> harvestingMonth = new JComboBox<>(LIST_MONTHS);
    private JComboBox<String> plantSoilType = new JComboBox<>(LIST_SOIL_TYPES);
    private JComboBox<String> plantTemp = new JComboBox<>(LIST_TEMP);
    private JComboBox<String> plantPH = new JComboBox<>(LIST_PH);
    private JComboBox<String> plantSunLevel = new JComboBox<>(LIST_SUN_LEVEL);
    private JComboBox<String> plantWaterLevel = new JComboBox<>(LIST_WATER_LEVEL);
    private JFrame frame;
    private JButton submitButton = new JButton("Submit");
    private JTable displayTable;
    private JScrollPane scrollResults;

    public GUI() {
        // Set up Java Swing GUI
        frame = setUpFrame();
        frame.add(setUpLeftPanel(),BorderLayout.WEST);
        frame.add(setUpCenterPanel(), BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    private JFrame setUpFrame() {
        JFrame frame = new JFrame("Planting A Base");
        frame.setSize(1500, 500);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("other/icon.png");
        frame.setIconImage(icon.getImage());
        return frame;
    }
    private JPanel setUpCenterPanel() {
        JPanel panel = new JPanel();
        // Set Display Table for displaying the data that returns from database
        displayTable = new JTable();
        scrollResults = new JScrollPane(displayTable);
        panel.add(scrollResults);
        return panel;
    }
    private JPanel setUpLeftPanel(){
        JPanel thePanel = new JPanel();
        thePanel.setPreferredSize(new Dimension(300, 400));
        thePanel.add(setUpLogo());

        // Add Plant Type label and Plant Type drop-down option on GUI
        thePanel.add(addLabelDropDown("Plant Type:", plantingType));

        // Add Plant Month label and Plant Month drop-down option on GUI
        thePanel.add(addLabelDropDown("Planting Month:", plantingMonth));

        // Add Blooming Month label and Blooming Month drop-down option on GUI
        thePanel.add(addLabelDropDown("Blooming Month:",bloomingMonth));

        // Add Harvesting Month label and Harvesting Month drop-down option on GUI
        thePanel.add(addLabelDropDown("Harvesting Month:",harvestingMonth));

        // Add Soil Type label and Soil Type drop-down option on GUI
        thePanel.add(addLabelDropDown("Soil Type:",plantSoilType));

        // Add Plant Temperature label and Plant Temperature drop-down option on GUI
        thePanel.add(addLabelDropDown("Temperature:",plantTemp));

        // Add Plant PH label and Plant PH drop-down option on GUI
        thePanel.add(addLabelDropDown("PH Value:", plantPH));

        //Add Sun Level label and Sun Level drop-down option on GUI
        thePanel.add(addLabelDropDown( "Sun Level:", plantSunLevel));

        //Add Water Level label and Water Level drop-down option on GUI
        thePanel.add(addLabelDropDown("Water Level:", plantWaterLevel));

        // Add Submit Button on GUI


        thePanel.add(setUpButtonsPanel());

        return thePanel;
    }

    private JPanel setUpButtonsPanel() {
        JPanel btnPanel = new JPanel(new BorderLayout());
        submitButton.addActionListener(this);
        submitButton.setFocusable(false);
        btnPanel.setSize(new Dimension(140,40));
        btnPanel.add(submitButton);
        return btnPanel;
    }

    private JLabel setUpLogo() {
        ImageIcon logo = new ImageIcon("other/logo.png");
        Image logoResized = logo.getImage().getScaledInstance(300,146, Image.SCALE_SMOOTH);
        logo = new ImageIcon(logoResized);
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logo);
        return logoLabel;
    }
    public void makeQuery() {
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
            java.util.List<String> selectedColumns = new ArrayList<>();

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
            displayTable.setModel(theTable.getModel());
            scrollResults.repaint();
            scrollResults.revalidate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Does the repetitive action for each drop down menu
     * @param label the label for the dropDown jComboBox
     * @param jComboBox the dropDown menu
     * @return the JPanel containing both the label and dropdown box
     */
    private static JPanel addLabelDropDown(String label, JComboBox jComboBox) {
        JPanel jPanel = new JPanel(new BorderLayout());
        JLabel label1 = new JLabel(label);
        jPanel.add(label1, BorderLayout.NORTH);
        jComboBox.setPreferredSize(new Dimension(140,20));
        jPanel.add(jComboBox, BorderLayout.CENTER);
        return jPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Button is pushed");
        if(e.getSource() == submitButton) {
            makeQuery();
        }
    }
    //adjust column width to longest char length + 4
    //font size, font type
    //remove resizeable
    //
}
