package Health_System_Monitoring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Patient_GUI {
    public static JFrame mainFrame;
    private static JLabel headerLabel;
    private static JLabel searchLabel;
    private static JPanel northPanel;
    private static JPanel controlPanel;
    private static JPanel southPanel;

    public static void preparePatientGUI() {
        GP_GUI.mainFrame.setVisible(false);

        mainFrame = new JFrame("GP application");
        mainFrame.setSize(500, 500);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        northPanel = new JPanel();
        //westPanel = new JPanel();
        controlPanel = new JPanel();
        southPanel = new JPanel();

        HeaderLabel();
        AddRecordButton();
        ModifyRecordButton();
        DeleteRecordButton();
        PatientBackButton();
        NiceButton();

        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(northPanel,BorderLayout.NORTH);
        mainFrame.add(controlPanel,BorderLayout.CENTER);
        mainFrame.add(southPanel,BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    private static void AddRecordButton() {
        JButton NiceButton = new JButton("Add Record");
        NiceButton.setActionCommand("Add_Record");
        NiceButton.addActionListener(new Main_GUI.ButtonClickListener());
        controlPanel.add(NiceButton);
    }

    private static void ModifyRecordButton() {
        JButton NiceButton = new JButton("Modify Record");
        NiceButton.setActionCommand("Modify_Record");
        NiceButton.addActionListener(new Main_GUI.ButtonClickListener());
        controlPanel.add(NiceButton);
    }

    private static void DeleteRecordButton() {
        JButton NiceButton = new JButton("Delete Record");
        NiceButton.setActionCommand("Delete_Record");
        NiceButton.addActionListener(new Main_GUI.ButtonClickListener());
        controlPanel.add(NiceButton);
    }

    private static void HeaderLabel() {
        headerLabel = new JLabel("", JLabel.CENTER);
        headerLabel.setText("Patient Record Overview");
        northPanel.add(headerLabel);
    }

    private static void NiceButton() {
        JButton NiceButton = new JButton("Nice Test");
        NiceButton.setActionCommand("Nice");
        NiceButton.addActionListener(new Main_GUI.ButtonClickListener());
        controlPanel.add(NiceButton);
    }

    private static void PatientBackButton() {
        JButton BackButton = new JButton("Back");
        BackButton.setActionCommand("Patient_Back");
        BackButton.addActionListener(new Main_GUI.ButtonClickListener());
        southPanel.add(BackButton);
    }

    public static void GoToNiceGUI() {
        mainFrame.setVisible(false);
        NICE_GUI.mainFrame.setVisible(true);
    }

    public static void GoToGPGUI() {
        mainFrame.setVisible(false);
        GP_GUI.mainFrame.setVisible(true);
    }
}
