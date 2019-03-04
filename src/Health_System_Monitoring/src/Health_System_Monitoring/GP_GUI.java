package Health_System_Monitoring;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class GP_GUI {
    public static JFrame mainFrame;
    private static JLabel headerLabel;
    private static JLabel searchLabel;
    private static JPanel northPanel;
    //private static JPanel westPanel;
    private static JPanel controlPanel;
    private static JPanel southPanel;
    private static JTextField patientSearchField;
    private static SpringLayout layout = new SpringLayout();
    
    private static patientDao patientDao;

    public static void prepareGPGUI() {

        Main_GUI.mainFrame.setVisible(false);

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

        //westPanel.setLayout(layout);
        controlPanel.setLayout(layout);

        HeaderLabel();
        RegisterPatientButton();
        SearchLabel();
        PatientSearchField();
        PatientSearchButton();
        BackButton();
        
        patientDao = new patientDao();

        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(northPanel, BorderLayout.NORTH);
        //mainFrame.add(westPanel,BorderLayout.WEST);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        mainFrame.add(southPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    public static void PatientSearchButtonFunction() throws SQLException {
        String searchField = patientSearchField.getText();
        //String searchField = "Jones";
        System.out.println("Print off of search bar input: " + searchField);

        List<Patient> pat = Arrays.asList(new Patient[0]);
        pat = (List<Patient>) patientDao.searchPatient(searchField);
        if(!searchField.isEmpty()){
            if (!pat.isEmpty()) {
                System.out.println(pat);
                Patient_GUI.preparePatientGUI(pat.get(0));
            } else {
                System.out.println("Patient list is Empty");
            }
        } else {
            System.out.println("Search was Empty");
        }
    }

    private static void BackButton() {
        JButton BackButton = new JButton("Back");
        BackButton.setActionCommand("Back");
        BackButton.addActionListener(new Main_GUI.ButtonClickListener());
        southPanel.add(BackButton);
    }

    public static void GoBackToMainGUI() {
        mainFrame.setVisible(false);
        Main_GUI.mainFrame.setVisible(true);
    }

    private static void HeaderLabel() {
        headerLabel = new JLabel("", JLabel.CENTER);
        headerLabel.setText("General Practitioner Menu");
        northPanel.add(headerLabel);
    }

    private static void PatientSearchField() {
        patientSearchField = new JTextField("");
        patientSearchField.setPreferredSize(new Dimension(100, 25));
        controlPanel.add(patientSearchField);

        layout.putConstraint(SpringLayout.NORTH, patientSearchField, 50, SpringLayout.NORTH, controlPanel);
        layout.putConstraint(SpringLayout.WEST, patientSearchField, 100, SpringLayout.WEST, controlPanel);
    }

    private static void PatientSearchButton() {
        JButton PatientSearchButton = new JButton("Search");
        PatientSearchButton.setActionCommand("GP_Patient_Search");
        PatientSearchButton.addActionListener(new Main_GUI.ButtonClickListener());
        controlPanel.add(PatientSearchButton);

        layout.putConstraint(SpringLayout.NORTH, PatientSearchButton, 50, SpringLayout.NORTH, controlPanel);
        layout.putConstraint(SpringLayout.WEST, PatientSearchButton, 205, SpringLayout.WEST, controlPanel);
    }

    private static void RegisterPatientButton() {
        JButton RegisterPatientButton = new JButton("Register Patient");
        RegisterPatientButton.setActionCommand("GP_Register");
        RegisterPatientButton.addActionListener(new Main_GUI.ButtonClickListener());
        controlPanel.add(RegisterPatientButton);

        layout.putConstraint(SpringLayout.NORTH, RegisterPatientButton, 5, SpringLayout.NORTH, controlPanel);
        layout.putConstraint(SpringLayout.WEST, RegisterPatientButton, 25, SpringLayout.WEST, controlPanel);
    }

    private static void SearchLabel() {
        searchLabel = new JLabel("", JLabel.CENTER);
        searchLabel.setText("Search patients: ");
        controlPanel.add(searchLabel);

        layout.putConstraint(SpringLayout.NORTH, searchLabel, 55, SpringLayout.NORTH, controlPanel);
        layout.putConstraint(SpringLayout.WEST, searchLabel, 5, SpringLayout.WEST, controlPanel);
    }
}
