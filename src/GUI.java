import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GUI {
    private static GUI instance;
    private JFrame frame;
    private JButton submitButton = new JButton("Submit");
    private JScrollPane scrollResults;
    private JTable displayTable;
    public GUI() {
        // Set up Java Swing GUI
        instance = this;
        frame = setUpFrame();
        frame.add(setUpLeftPanel(),BorderLayout.WEST);
        frame.add(setUpCenterPanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
    private JFrame setUpFrame() {
        JFrame theFrame = new JFrame("Planting A Base");
        theFrame.setSize(1500, 500);
        theFrame.setLayout(new BorderLayout());
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("other/icon.png");
        theFrame.setIconImage(icon.getImage());
        return theFrame;
    }
    private JPanel setUpCenterPanel() {
        JPanel centerPanel = new JPanel();
        displayTable = new JTable();
        scrollResults = new JScrollPane(displayTable);
        centerPanel.add(scrollResults);
        return centerPanel;
    }
    private JPanel setUpLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(300, 400));
        leftPanel.add(setUpLogo());
        ButtonsAndDropDowns.makeDropDowns(leftPanel);
        return leftPanel;
    }
    private JLabel setUpLogo(){
        ImageIcon logo = new ImageIcon("other/logo.png");
        Image logoResized = logo.getImage().getScaledInstance(300,146, Image.SCALE_SMOOTH);
        logo = new ImageIcon(logoResized);
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logo);
        return logoLabel;
    }
    public static GUI getInstance() {
        if(instance == null) {
            instance = new GUI();
        }
        return instance;
    }
    public void updateTable(DefaultTableModel model) {
        displayTable.setModel(model);
        scrollResults.repaint();
        scrollResults.revalidate();
    }
}
