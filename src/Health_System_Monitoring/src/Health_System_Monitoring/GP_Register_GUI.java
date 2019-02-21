package Health_System_Monitoring;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class GP_Register_GUI {
    public static JFrame mainFrame;
    private static JPanel northPanel, controlPanel, southPanel, patientPanel, medicationPanel, infoPanel;

    private static JLabel firstNameLabel, lastNameLabel, addressLabel, dateOfBirthLabel, medicalHistoryLabel, patientDiagnosisLabel, patientPrescriptionsLabel;
    private static JTextField firstNameTextField, lastNameTextField, patientDiagnosisTextField;
    private static JTextArea addressTextArea, medicalHistoryTextArea, patientPrescriptionsTextArea;
    private static UtilDateModel dateOfBirthModel;
    private static JDatePanelImpl datePanel;
    private static JDatePickerImpl datePicker;

    private static String patient_first_name, patient_last_name, patient_address, patient_medical_history, patient_diagnosis, patient_prescriptions;
    private static java.sql.Date patient_dob;
    private static int userId = 1;


    public static void prepareGPGUI() {

        mainFrame = new JFrame("Register new patient");
        mainFrame.setSize(400, 510);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                mainFrame.setVisible(false);
            }
        });

        northPanel = new JPanel();
        controlPanel = new JPanel();
        southPanel = new JPanel();

        PatientPanel();
        MedicationPanel();
        InfoPanel();

        FirstNameLabel();
        FirstNameTextField();
        LastNameLabel();
        LastNameTextField();
        AddressLabel();
        AddressTextArea();
        DateOfBirthLabel();
        DateOfBirthField();
        PatientMedicalHistoryLabel();
        PatientMedicalHistoryTextArea();
        PatientDiagnosisLabel();
        PatientDiagnosisTextField();
        PatientPrescriptionsLabel();
        PatientPrescriptionsTextArea();

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
        patientPanel.setPreferredSize(new Dimension(300, 200));
        TitledBorder patientBorder = new TitledBorder("Patient Details");
        patientPanel.setBorder(patientBorder);
        controlPanel.add(patientPanel);
    }

    private static void MedicationPanel() {
        medicationPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 4);
        medicationPanel.setLayout(flowLayout);
        medicationPanel.setPreferredSize(new Dimension(300, 175));
        TitledBorder patientBorder = new TitledBorder("Medication Details");
        medicationPanel.setBorder(patientBorder);
        controlPanel.add(medicationPanel);
    }

    private static void InfoPanel() {
        infoPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 10, 4);
        infoPanel.setLayout(flowLayout);
        infoPanel.setPreferredSize(new Dimension(300, 30));
        TitledBorder patientBorder = new TitledBorder("");
        infoPanel.setBorder(patientBorder);
        controlPanel.add(infoPanel);
    }

    public static void BackButtonFunction() {
        mainFrame.setVisible(false);
    }

    public static void SubmitButtonFunction() throws SQLException {
        boolean acceptedCheck;

        GrabValues();
        System.out.println("Submitting - First name: " + patient_first_name + ", Last name: " +
                patient_last_name + ", Address: " + patient_address + ", Date of Birth: " + patient_dob + ", Medical History: "
                + patient_medical_history + ", Diagnosis: " + patient_diagnosis + ", Prescription: " + patient_prescriptions + ", User ID: " + userId);

        database_driver d_driver = database_driver.getConnection();
        d_driver.getConnection();
        acceptedCheck = d_driver.addNewPatientToDatabase(patient_dob, patient_first_name, patient_last_name, patient_address, patient_medical_history, patient_diagnosis, patient_prescriptions, userId);

        if (acceptedCheck == true) {
            mainFrame.setVisible(false);
            SubmitConfirmedWindow();
        } else {
            JLabel errorLabel = new JLabel();
            errorLabel.setText("Something was wrong with the submission");
            infoPanel.removeAll();
            infoPanel.add(errorLabel);
            infoPanel.updateUI();
        }
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

    private static void AddressTextArea() {
        addressTextArea = new JTextArea("");
        addressTextArea.setPreferredSize(new Dimension(150, 50));
        JScrollPane scrollPane = new JScrollPane(addressTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        addressTextArea.setLineWrap(true);
        patientPanel.add(scrollPane);
    }

    private static void PatientMedicalHistoryLabel() {
        medicalHistoryLabel = new JLabel();
        medicalHistoryLabel.setText("Medical History: ");
        medicationPanel.add(medicalHistoryLabel);
    }

    private static void PatientMedicalHistoryTextArea() {
        medicalHistoryTextArea = new JTextArea("");
        medicalHistoryTextArea.setPreferredSize(new Dimension(150, 50));
        JScrollPane scrollPane = new JScrollPane(medicalHistoryTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        medicalHistoryTextArea.setLineWrap(true);
        medicationPanel.add(scrollPane);
    }

    private static void PatientDiagnosisLabel() {
        patientDiagnosisLabel = new JLabel();
        patientDiagnosisLabel.setText("Diagnosis: ");
        medicationPanel.add(patientDiagnosisLabel);
    }

    private static void PatientDiagnosisTextField() {
        patientDiagnosisTextField = new JTextField("");
        patientDiagnosisTextField.setPreferredSize(new Dimension(150, 25));
        medicationPanel.add(patientDiagnosisTextField);
    }

    private static void PatientPrescriptionsLabel() {
        patientPrescriptionsLabel = new JLabel();
        patientPrescriptionsLabel.setText("Prescription: ");
        medicationPanel.add(patientPrescriptionsLabel);
    }

    private static void PatientPrescriptionsTextArea() {
        patientPrescriptionsTextArea = new JTextArea("");
        patientPrescriptionsTextArea.setPreferredSize(new Dimension(150, 50));
        JScrollPane scrollPane = new JScrollPane(patientPrescriptionsTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        patientPrescriptionsTextArea.setLineWrap(true);
        medicationPanel.add(scrollPane);
    }

    private static void DateOfBirthLabel() {
        dateOfBirthLabel = new JLabel();
        dateOfBirthLabel.setText("Date of Birth: ");
        patientPanel.add(dateOfBirthLabel);
    }

    private static void DateOfBirthField() {
        dateOfBirthModel = new UtilDateModel();

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePanel = new JDatePanelImpl(dateOfBirthModel, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        patientPanel.add(datePicker);
    }

    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }

    private static void GrabValues() {
        Date temp = (Date) datePicker.getModel().getValue();
        if (temp != null) {
            patient_dob = new java.sql.Date(temp.getTime());
        }
        patient_first_name = CheckStringEmpty(patient_first_name, firstNameTextField.getText());
        patient_last_name = CheckStringEmpty(patient_last_name, lastNameTextField.getText());
        patient_address = CheckStringEmpty(patient_address,addressTextArea.getText());
        patient_medical_history = CheckStringEmpty(patient_medical_history,medicalHistoryTextArea.getText());
        patient_diagnosis = CheckStringEmpty(patient_diagnosis,patientDiagnosisTextField.getText());
        patient_prescriptions = CheckStringEmpty(patient_prescriptions,patientPrescriptionsTextArea.getText());
    }

    private static String CheckStringEmpty(String x, String y) {
        if (y.length() > 0) {
            x = y;
        }
        return x;
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

    private static void SubmitConfirmedWindow() {
        mainFrame = new JFrame("Registration Successful");
        mainFrame.setSize(320, 200);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                mainFrame.setVisible(false);
            }
        });

        JPanel successPanel = new JPanel();
        JPanel successSouthPanel = new JPanel();

        JLabel SuccessfulLabel = new JLabel("", JLabel.CENTER);
        SuccessfulLabel.setText("Patient registration was submitted successfully");
        successPanel.add(SuccessfulLabel);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(successPanel, BorderLayout.CENTER);
        mainFrame.add(successSouthPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }
}