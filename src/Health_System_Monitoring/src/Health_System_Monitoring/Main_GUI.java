package Health_System_Monitoring;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Main_GUI implements KeyListener, ActionListener {
    public static JFrame mainFrame;
    private JLabel headerLabel1, headerLabel2;
    private JPanel controlPanel;
    private static Point windowsPosition;

    private static JTextField usernameTextField;

    private static JPasswordField passwordField;

    //private static int userId;
    //private static List<User> userInfo = null;
    private static User user;

    public Main_GUI() {
        mainFrame = new JFrame("Login");
        mainFrame.setSize(250, 175);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        headerLabel1 = new JLabel();
        headerLabel1.setText("Username: ");

        headerLabel2 = new JLabel();
        headerLabel2.setText("Password: ");

        usernameTextField = new JTextField("");
        usernameTextField.setPreferredSize(new Dimension(100, 25));
        usernameTextField.addKeyListener(this);

        passwordField = new JPasswordField("");
        passwordField.setPreferredSize(new Dimension(100, 25));
        passwordField.addKeyListener(this);

        controlPanel.add(headerLabel1);
        controlPanel.add(usernameTextField);
        controlPanel.add(headerLabel2);
        controlPanel.add(passwordField);

        LoginButton();
        BypassSCButton();
        BypassGPButton();
        GetCenterPoint();

        controlPanel.addKeyListener(this);

        mainFrame.setLocation(windowsPosition);
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            LoginFunction();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static User getCurrentUser() {
        return user;
    }

    private void LoginFunction() {
        String username = usernameTextField.getText();
        String userPassword = String.valueOf(passwordField.getPassword());

        UserDaoInterface uDao = (UserDao) new UserDao();

        user = uDao.checkCredentials(username, userPassword);

        if (user != null) {
            System.out.println(user);
            GP_GUI gp_GUI = new GP_GUI();
            gp_GUI.prepareGPGUI();
        } else {
            System.out.println("Incorrect password");
        }
    }

    /**
     * Creates GUI for login button
     */
    public void LoginButton() {
        JButton AddButton = new JButton("Login");
        AddButton.setActionCommand("Login");
        AddButton.addActionListener(this);
        controlPanel.add(AddButton);
    }

    //Temporary button that will be removed later on in development
    private void BypassSCButton() {
        JButton SCButton = new JButton("Bypass");
        SCButton.setActionCommand("SCBypass");
        SCButton.addActionListener(this);
        controlPanel.add(SCButton);
    }

    //Temporary button that will be removed later on in development
    private void BypassGPButton() {
        JButton GPButton = new JButton("Bypass GP");
        GPButton.setActionCommand("GPBypass");
        GPButton.addActionListener(this);
        controlPanel.add(GPButton);
    }

    public static void SetWindowPosition(double x, double y){
        Point pt = new Point((int)Math.round(x),(int)Math.round(y));
        windowsPosition = pt;
    }

    public static Point GetWindowPosition(){
        return windowsPosition;
    }

    private void GetCenterPoint(){
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        Point pt = new Point((screenWidth / 2) - 125, (screenHeight / 2) - 80);
        windowsPosition = pt;
    }

    /**
     * Action Listener that looks out for button presses in Main_GUI
     */

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        Patient_GUI patient_gui = new Patient_GUI();
        GP_Register_GUI gp_register_gui = new GP_Register_GUI();
        NICE_GUI nice_gui = new NICE_GUI();

        //get the text value from the username and pwd text field
        //converted to string type

        if (command.equals("Default")) {
            //Do Something?
        } else if (command.equals("Login")) {
            this.LoginFunction();
        } else if (command.equals("SCBypass")) {
            SC_GUI.prepareSCGUI();
        } else if (command.equals("GPBypass")) {
            SetWindowPosition(mainFrame.getLocation().x,mainFrame.getLocation().y);
            GP_GUI gp_GUI = new GP_GUI();
            gp_GUI.prepareGPGUI();
        } else if (command.equals("Nice_Back")) {
            nice_gui.GoToPatientGUI();
        } else {
            System.out.println("No Input for button");
        }
    }
}
