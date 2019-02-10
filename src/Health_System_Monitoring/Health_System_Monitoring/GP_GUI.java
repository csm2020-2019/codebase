package Health_System_Monitoring.Health_System_Monitoring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GP_GUI {
    public static JFrame mainFrame;
    private static JLabel headerLabel1;
    private static JPanel controlPanel;

    public static void prepareAddGPGUI() {

        Main_GUI.mainFrame.setVisible(false);

        mainFrame = new JFrame("GP application");
        mainFrame.setSize(500, 500);
        mainFrame.setLayout(new GridLayout());
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        headerLabel1 = new JLabel("", JLabel.CENTER);
        headerLabel1.setText("Application stuff");



        controlPanel.add(headerLabel1);
        BackButton();

        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    public static void BackButton() {
        JButton BackButton = new JButton("Back");
        BackButton.setActionCommand("Back");
        BackButton.addActionListener(new Main_GUI.ButtonClickListener());
        controlPanel.add(BackButton);
    }

    public static void GoBackToMainGUI() {
        mainFrame.setVisible(false);
        Main_GUI.mainFrame.setVisible(true);
    }
}
