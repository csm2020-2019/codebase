package Health_System_Monitoring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Main_GUI {

    public static JFrame mainFrame;
    private JLabel headerLabel1;
    private JLabel headerLabel2;
    private JPanel controlPanel;
    private JTextField textField1;
    private JPasswordField passwordfield1;

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

        textField1 = new JTextField("");
        textField1.setPreferredSize(new Dimension(100, 25));

        passwordfield1 = new JPasswordField("");
        passwordfield1.setPreferredSize(new Dimension(100, 25));

        controlPanel.add(headerLabel1);
        controlPanel.add(textField1);
        controlPanel.add(headerLabel2);
        controlPanel.add(passwordfield1);
        LoginButton();

        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    static class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Default")) {
                //Do Something?
            } else if (command.equals("Login")) {
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
        AddButton.addActionListener(new ButtonClickListener());
        controlPanel.add(AddButton);
    }

    public static void main(String[] args) throws IOException {

        Main_GUI GUI = new Main_GUI();

    }
}