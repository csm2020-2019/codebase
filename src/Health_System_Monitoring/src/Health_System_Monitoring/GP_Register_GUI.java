package Health_System_Monitoring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GP_Register_GUI {
    public static JFrame mainFrame;
    private static JPanel northPanel;
    private static JPanel controlPanel;
    private static JPanel southPanel;
    private static SpringLayout layout = new SpringLayout();

    public static void prepareGPGUI() {

        mainFrame = new JFrame("Register new patient");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        northPanel = new JPanel();
        controlPanel = new JPanel();
        southPanel = new JPanel();

        controlPanel.setLayout(layout);

        BackButton();
        SubmitButton();

        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(northPanel,BorderLayout.NORTH);
        mainFrame.add(controlPanel,BorderLayout.CENTER);
        mainFrame.add(southPanel,BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    public static void BackButtonFunction() {
        mainFrame.setVisible(false);
    }

    public static void SubmitButtonFunction() {
        System.out.println("Submit register patient form");
    }

    private static void BackButton() {
        JButton BackButton = new JButton("Back");
        BackButton.setActionCommand("GP_Register_Back");
        BackButton.addActionListener(new Main_GUI.ButtonClickListener());
        southPanel.add(BackButton);
    }

    private static void SubmitButton() {
        JButton BackButton = new JButton("Submit");
        BackButton.setActionCommand("GP_Register_Submit");
        BackButton.addActionListener(new Main_GUI.ButtonClickListener());
        southPanel.add(BackButton);
    }
}