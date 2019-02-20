package Health_System_Monitoring;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

public class GP_Register_GUI {
    public static JFrame mainFrame;
    private static JPanel northPanel, controlPanel, southPanel, patientPanel;

    private static JLabel firstNameLabel, lastNameLabel, addressLabel;
    private static JTextField firstNameTextField, lastNameTextField;
    private static JTextArea addressTextArea;

    private static String patient_first_name, patient_last_name, patient_address, patient_medical_history, patient_diagnosis, patient_prescriptions;
    private static Date patient_dob;
    private static int userId;


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

        PatientPanel();
        FirstNameLabel();
        FirstNameTextField();
        LastNameLabel();
        LastNameTextField();
        AddressLabel();
        AddressTextField();
        BackButton();
        SubmitButton();

        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(northPanel, BorderLayout.NORTH);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        mainFrame.add(southPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    private static void PatientPanel() {
        patientPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 4);
        patientPanel.setLayout(flowLayout);
        patientPanel.setPreferredSize(new Dimension(300, 180));
        TitledBorder patientBorder = new TitledBorder("Patient Details");
        patientPanel.setBorder(patientBorder);
        controlPanel.add(patientPanel);
    }

    public static void BackButtonFunction() {
        mainFrame.setVisible(false);
    }

    public static void SubmitButtonFunction() {
        System.out.println("Submit register patient form");
    }

    private static void FirstNameLabel() {
        firstNameLabel = new JLabel();
        firstNameLabel.setText("First name: ");
        patientPanel.add(firstNameLabel);
    }

    private static void FirstNameTextField() {
        firstNameTextField = new JTextField("");
        firstNameTextField.setPreferredSize(new Dimension(150, 25));
        patientPanel.add(firstNameTextField);
    }

    private static void LastNameLabel() {
        lastNameLabel = new JLabel();
        lastNameLabel.setText("Last name: ");
        patientPanel.add(lastNameLabel);
    }

    private static void LastNameTextField() {
        lastNameTextField = new JTextField("");
        lastNameTextField.setPreferredSize(new Dimension(150, 25));
        patientPanel.add(lastNameTextField);
    }

    private static void AddressLabel() {
        addressLabel = new JLabel();
        addressLabel.setText("Address: ");
        patientPanel.add(addressLabel);
    }

    private static void AddressTextField() {
        addressTextArea = new JTextArea("");
        //addressTextField.setRows(2);
        addressTextArea.setPreferredSize(new Dimension(150, 50));
        JScrollPane scrollPane = new JScrollPane(addressTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        addressTextArea.setLineWrap(true);
        //patientPanel.add(addressTextArea);
        patientPanel.add(scrollPane);
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