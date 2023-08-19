import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The PlantABase application is a database with different kinds of plants mainly
 * for gardening applications. Find the plant that's suites your needs today!
 */
public class Main implements ActionListener {

    /**
     * The Username
     */
    private final static String USERNAME = "PlantLover";
    /**
     * The Password
     */
    private final static String PASSWORD = "Washington";
    /**
     * The frame for login
     */
    private JFrame frame = new JFrame();

    /**
     * The login button
     */
    private JButton loginButton = new JButton("Login");
    /**
     * The username field
     */
    private JTextField usernameField = new JTextField();

    /**
     * The password field
     */
    private JPasswordField passwordField = new JPasswordField();

    /**
     * The login launch page of Application
     */
    public Main() {
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(setUpLogo(), BorderLayout.CENTER);


        frame.add(setUpLowerPanel(),BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }
    /**
     * Sets up the logo in a panel for the launch page
     * @return JPanel, the logo panel
     */
    private JPanel setUpLogo() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // Load and display the logo
        ImageIcon logoIcon = new ImageIcon("other/Logo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add some spacin
        return panel;
    }

    /**
     * Sets up the lower panel of the launch page
     * @return JPanel, the lower panel
     */
    private JPanel setUpLowerPanel() {
        JPanel panel = new JPanel(new GridLayout(2,1));
        JPanel buttonPanel = new JPanel(new GridLayout(1,3));

        loginButton.setPreferredSize(new Dimension(50,20));
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        buttonPanel.add(new JLabel());
        buttonPanel.add(loginButton);
        buttonPanel.add(new JLabel());

        panel.add(setUpLogInPanel());
        panel.add(buttonPanel);
        return panel;
    }
    /**
     * The log in panel the user interacts with
     * @return JPanel
     */
    private JPanel setUpLogInPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        return panel;
    }

    public static void main(String[] args) {
        new Main();
    }

    /**
     * compares the input username and password with the one stored
     * if correct, close current frame and opens database GUI
     * else tell the User that it's incorrect
     */
    private void loginAction() {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        if(user.equals(USERNAME) && pass.equals(PASSWORD)) {
            frame.dispose();
            new GUI();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * All button related actions
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton) {
            loginAction();
        }
    }
}