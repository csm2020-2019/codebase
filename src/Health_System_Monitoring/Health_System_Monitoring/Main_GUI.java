package Health_System_Monitoring.Health_System_Monitoring;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

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

	    public Main_GUI() {
	        prepareGUI();
	    }

	    public void prepareGUI() {
	        mainFrame = new JFrame("Login");
	        mainFrame.setSize(250, 150);
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
			BypassButton();

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

	        		if(db_connect.checkCredentials(username, userPassword)) {
		                GP_GUI.prepareAddGPGUI();
	        		}
	        		else System.out.println("Incorrect password");
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



	    public void LoginButton() {
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
