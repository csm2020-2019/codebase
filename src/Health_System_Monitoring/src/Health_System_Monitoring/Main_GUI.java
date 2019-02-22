package Health_System_Monitoring;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

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
    
    private static JTextField usernameTextField;
    
    private static JPasswordField passwordField;

    
    private static int userId;
    private static List<User> userInfo = null;


    public Main_GUI() {
        prepareGUI();
    }

    
    public void prepareGUI() {
        mainFrame = new JFrame("Login");
        mainFrame.setSize(250, 175);
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

        usernameTextField = new JTextField("");
        usernameTextField.setPreferredSize(new Dimension(100, 25));


        passwordField = new JPasswordField("");
        passwordField.setPreferredSize(new Dimension(100, 25));

        controlPanel.add(headerLabel1);
        controlPanel.add(usernameTextField);
        controlPanel.add(headerLabel2);
        controlPanel.add(passwordField);

        LoginButton();
        BypassSCButton();
        BypassGPButton();

        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    static class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            //get the text value from the username and pwd text field
            //converted to string type
            String username = usernameTextField.getText().toString();
            String userPassword = String.valueOf(passwordField.getPassword());


            if (command.equals("Default")) {
                //Do Something?
            } else if (command.equals("Login")) {

                database_driver db_connect = database_driver.getConnection();

                try {
                    userInfo = db_connect.checkCredentials(username, userPassword);

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                if(!userInfo.isEmpty()){
                    for(User user : userInfo){
                        System.out.println(user);
                    }
                    GP_GUI.prepareGPGUI();
                }
                else {
                    System.out.println("Incorrect password");
                }


//                if (userId != -1) {
//                    //for debug purpose
//                    System.out.println("User id is: " + userId);
//
//                } else System.out.println("Incorrect password");

            } else if (command.equals("SCBypass")) {
                SC_GUI.prepareSCGUI();
            } else if (command.equals("GPBypass")) {
            	GP_GUI.prepareGPGUI();
            } else if (command.equals("Back")) {
                GP_GUI.GoBackToMainGUI();
            } else if (command.equals("Nice")) {
                NICE_GUI.prepareNiceGUI();
            } else if (command.equals("GP_Register")) {
                GP_Register_GUI.prepareGPGUI();
            } else if (command.equals("GP_Register_Back")) {
                GP_Register_GUI.BackButtonFunction();
            } else if (command.equals("GP_Register_Submit")) {
                try {
                    GP_Register_GUI.SubmitButtonFunction();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } else if (command.equals("GP_Patient_Search")) {
                GP_GUI.PatientSearchButtonFunction();
                Patient_GUI.preparePatientGUI();
            } else if (command.equals("Patient_Back")) {
                Patient_GUI.GoToGPGUI();
            } else if (command.equals("Nice_Back")) {
                NICE_GUI.GoToPatientGUI();
            } else {
                System.out.println("No Input for button");
            }
        }
    }


    
    public void LoginButton() {
        JButton AddButton = new JButton("Login");
        AddButton.setActionCommand("Login");
        AddButton.addActionListener((ActionListener) new ButtonClickListener());
        controlPanel.add(AddButton);
    }

    
    //Temporary button that will be removed later on in development
    private void BypassSCButton() {
        JButton SCButton = new JButton("Bypass");
        SCButton.setActionCommand("SCBypass");
        SCButton.addActionListener((ActionListener) new ButtonClickListener());
        controlPanel.add(SCButton);
    }
    
    private void BypassGPButton() {
    	JButton GPButton = new JButton("Bypass GP");
    	GPButton.setActionCommand("GPBypass");
    	GPButton.addActionListener((ActionListener) new ButtonClickListener());
    	controlPanel.add(GPButton);
    }
}
