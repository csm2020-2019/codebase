package Health_System_Monitoring.Health_System_Monitoring;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Main_GUI {
    public static JFrame mainFrame;
    private JLabel headerLabel1;
    private JLabel headerLabel2;
    private JPanel controlPanel;
    private static JTextField textField1;
    private static JPasswordField passwordField1;

    public Main_GUI() {
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Login");
        mainFrame.setSize(250, 200);
        //mainFrame.setLayout(new GridBagLayout());
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

        textField1 = new JTextField("");
        textField1.setPreferredSize(new Dimension(100, 25));

        passwordField1 = new JPasswordField("");
        passwordField1.setPreferredSize(new Dimension(100, 25));

        controlPanel.add(headerLabel1);
        controlPanel.add(textField1);
        controlPanel.add(headerLabel2);
        controlPanel.add(passwordField1);
        LoginButton();
        BypassButton();

        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    static class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Default")) {
                //Do Something?
            } else if (command.equals("Login")) {
                LoginCheckCredentials();
            } else if (command.equals("Bypass")) {
                GP_GUI.prepareAddGPGUI();
            } else if (command.equals("Back")) {
                //System.out.println("Add");
                GP_GUI.GoBackToMainGUI();
            } else {
                System.out.println("No Input for button");
            }
        }
    }

    //WILL BE MODIFIED LATER TO CHECK DATABASE FOR USERS - TO BE DONE
    private static void LoginCheckCredentials() {
        String login = textField1.getText();

        if (login.equals("admin")) {
            System.out.println("Username is correct");
            LoginCheckUserPassword();
        } else {
            System.out.println("Username is incorrect");
        }
    }

    //Will look up the user and compare the typed in password to one in database - TO BE DONE
    private static void LoginCheckUserPassword() {
        char[] password = passwordField1.getPassword();
        String tempPassword = "default";
        char[] tempCharPassword = tempPassword.toCharArray();

        if (Arrays.equals(password, tempCharPassword)) {
            System.out.println("Password is correct");
            GP_GUI.prepareAddGPGUI();
        } else {
            System.out.println("Password is incorrect");
        }
    }

    private void LoginButton() {
        JButton AddButton = new JButton("Login");
        AddButton.setActionCommand("Login");
        AddButton.addActionListener((ActionListener) new ButtonClickListener());
        controlPanel.add(AddButton);
    }

    //Temporary button that will be removed later on in development
    private void BypassButton() {
        JButton AddButton = new JButton("Bypass");
        AddButton.setActionCommand("Bypass");
        AddButton.addActionListener((ActionListener) new ButtonClickListener());
        controlPanel.add(AddButton);
    }
}
