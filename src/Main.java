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
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Planting A Base");

            ImageIcon icon = new ImageIcon("other/icon.png");
            Image iconImage = icon.getImage();
            frame.setIconImage(iconImage); // Set the icon for the title bar

            frame.setSize(1200, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            JPanel panel = new JPanel();

            JLabel lblPlantType = new JLabel("Plant Type:");
            panel.add(lblPlantType);

            JComboBox<String> cboPlantType = new JComboBox<>(new String[]{"Trees", "Flowers", "Bushes", "Fruit", "Vegetables", "Succulent", "Vines"});
            panel.add(cboPlantType);

            JLabel lblMonth = new JLabel("Planting Month:");
            panel.add(lblMonth);

            JComboBox<String> cboMonth = new JComboBox<>(new String[]{"None", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
            panel.add(cboMonth);

            JLabel lblBloomingMonth = new JLabel("Blooming Month:");
            panel.add(lblBloomingMonth);

            JComboBox<String> cboBloomingMonth = new JComboBox<>(new String[]{"None", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
            panel.add(cboBloomingMonth);

            JLabel lblHarvestingMonth = new JLabel("Harvesting Month:");
            panel.add(lblHarvestingMonth);

            JComboBox<String> cboHarvestingMonth = new JComboBox<>(new String[]{"None", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
            panel.add(cboHarvestingMonth);

            JButton btnSubmit = new JButton("Submit");
            panel.add(btnSubmit);

            JTextArea txtResults = new JTextArea(20, 40);
            txtResults.setEditable(false);
            JScrollPane scrollResults = new JScrollPane(txtResults);
            panel.add(scrollResults);

            frame.add(panel);

            btnSubmit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(() -> {
                        int plantChoice = cboPlantType.getSelectedIndex() + 1;
                        int month = cboMonth.getSelectedIndex();
                        int bloomingMonth = cboBloomingMonth.getSelectedIndex();
                        int harvestingMonth = cboHarvestingMonth.getSelectedIndex();

                        try (Connection conn = ConnectDB.connect()) {
                            List<String> selectedColumns = new ArrayList<>();
                            selectedColumns.add("pbasic.PNAMES");
                            if (month > 0) selectedColumns.add("PACTIVITIES.PLANTING");
                            if (bloomingMonth > 0) selectedColumns.add("PACTIVITIES.BLOOMING");
                            if (harvestingMonth > 0) selectedColumns.add("PACTIVITIES.HARVESTING");

                            List<String> tables = Arrays.asList("patrishy_db.pbasic");
                            Map<String, String> joinConditions = new HashMap<>();
                            joinConditions.put("patrishy_db.PACTIVITIES", "pbasic.PID = PACTIVITIES.PID");
                            Map<String, String> whereConditions = new HashMap<>();
                            if (month > 0) whereConditions.put("PACTIVITIES.PLANTING", "'" + month + "'");
                            if (bloomingMonth > 0) whereConditions.put("PACTIVITIES.BLOOMING", "'" + bloomingMonth + "'");
                            if (harvestingMonth > 0) whereConditions.put("PACTIVITIES.HARVESTING", "'" + harvestingMonth + "'");
                            whereConditions.put("pbasic.PKIND_ID", "'" + plantChoice + "'");

                            String results = Queries.executeQuery(conn, selectedColumns, tables, joinConditions, whereConditions);
                            txtResults.setText(results);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            txtResults.setText("Failed to connect to the database.");
                        }
                    });
                }
            });

            frame.setVisible(true);
        });
    }
}






