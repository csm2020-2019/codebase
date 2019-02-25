package Health_System_Monitoring;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

public class Patient_GUI {
    public static JFrame mainFrame, confirmFrame;
    private static JLabel headerLabel, searchLabel;
    private static JPanel northPanel, controlPanel, southPanel, successPanel, successSouthPanel, infoPanel, referPanel;
    private static Patient patient;
    private static JComboBox<String> referBox;
    private static List<User> rd_list;

    public static void preparePatientGUI(Patient pat) {
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

        patient = pat;

        HeaderLabel();
        ModifyRecordButton();
        DeleteRecordButton();
        PatientBackButton();
        NiceButton();
        PrescribeCheckBox();
        PatientInfoPanel();
        PatientInfoDisplay();
        PatientReferPanel();

        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(northPanel, BorderLayout.NORTH);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        mainFrame.add(southPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    private static void PatientInfoPanel() {
        infoPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 4);
        infoPanel.setLayout(flowLayout);
        infoPanel.setPreferredSize(new Dimension(300, 200));
        TitledBorder patientBorder = new TitledBorder("Patient Details");
        infoPanel.setBorder(patientBorder);
        controlPanel.add(infoPanel);
    }
    
    private static void PatientReferPanel() {
    	referPanel = new JPanel();
    	FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 4);
    	referPanel.setLayout(flowLayout);
    	
    	// first up, the combo box containing all RDs
    	database_driver d_driver = database_driver.getConnection();
    	if(rd_list == null) {
    		rd_list = new ArrayList<User>();
    	}
    	else
    	{
    		rd_list.clear();
    	}
    	rd_list = d_driver.getUsersByType("rd");
    	Vector<String> name_list = new Vector<String>(rd_list.size());
    	
    	for(User user : rd_list)
    	{
    		name_list.add(user.getUserFirstName() + " " + user.getUserLastName());
    	}
    	
    	referBox = new JComboBox<String>(name_list);
    	
    	// next up, the button to trigger referral
    	
    	JButton TriggerButton = new JButton("Refer");
    	TriggerButton.setActionCommand("Refer_Patient");
    	TriggerButton.addActionListener(new Main_GUI.ButtonClickListener());
    	
    	referPanel.add(referBox);
    	referPanel.add(TriggerButton);
    	
    	controlPanel.add(referPanel);
    }

    private static void PatientInfoDisplay() {
        JLabel NameLabel = new JLabel("", JLabel.CENTER);
        NameLabel.setText("Full name: " + patient.getPatientFirstName() + " " + patient.getPatientLastName());
        JLabel DoBLabel = new JLabel("", JLabel.CENTER);
        DoBLabel.setText("Date of Birth: " + patient.getPatientDob());
        JLabel AddressLabel = new JLabel("", JLabel.CENTER);
        AddressLabel.setText("Address: " + patient.getPatientAddress());
        JLabel HistoryLabel = new JLabel("", JLabel.CENTER);
        HistoryLabel.setText("Medical History: " + patient.getPatientMedicalHistory());
        JLabel DiagnosisLabel = new JLabel("", JLabel.CENTER);
        DiagnosisLabel.setText("Diagnosis: " + patient.getPatientDiagnosis());
        JLabel PrescriptionLabel = new JLabel("", JLabel.CENTER);
        PrescriptionLabel.setText("Prescription: " + patient.getPatientPrescriptions());

        infoPanel.add(NameLabel);
        infoPanel.add(DoBLabel);
        infoPanel.add(AddressLabel);
        infoPanel.add(HistoryLabel);
        infoPanel.add(DiagnosisLabel);
        infoPanel.add(PrescriptionLabel);
    }

    private static void PrescribeCheckBox() {
        JCheckBox PrescribeCheckBox = new JCheckBox("Prescribe to third party material");
        PrescribeCheckBox.setActionCommand("");
        PrescribeCheckBox.addActionListener(new Main_GUI.ButtonClickListener());
        controlPanel.add(PrescribeCheckBox);
    }

    public static void ReferPatient() {
    	int selected = referBox.getSelectedIndex();
    	// text box maps one-to-one with returned RD user list, which is stored in rd_list
    	
    	User rd = rd_list.get(selected);
    	User gp = Main_GUI.getCurrentUser();
    	
    	int rd_id = rd.getUserId();
    	int gp_id = gp.getUserId();
    	int patient_id = patient.getPatientUserId();
    	
    	database_driver d_driver = database_driver.getConnection();
    	boolean result = d_driver.addReferral(patient_id, gp_id, rd_id);
    }
    
    public static void ModifyRecordButtonFunction() {
        GP_Register_GUI bob = new GP_Register_GUI();
        bob.prepareModifyGPGUI(patient);
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
        JButton NiceButton = new JButton("Add Nice Test");
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

    public static void DeleteConfirmWindow() {
        confirmFrame = new JFrame("Confirm Deletion");
        confirmFrame.setSize(320, 200);
        confirmFrame.setLayout(new BorderLayout());
        confirmFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                confirmFrame.setVisible(false);
            }
        });

        successPanel = new JPanel();
        successSouthPanel = new JPanel();

        JLabel SuccessfulLabel = new JLabel("", JLabel.CENTER);
        SuccessfulLabel.setText("Are you sure you want to delete this record?");
        successPanel.add(SuccessfulLabel);

        DeleteOkayButton();
        DeleteCancelButton();

        confirmFrame.setLocationRelativeTo(null);
        confirmFrame.add(successPanel, BorderLayout.CENTER);
        confirmFrame.add(successSouthPanel, BorderLayout.SOUTH);
        confirmFrame.setVisible(true);
    }

    private static void DeleteOkayButton() {
        JButton NiceButton = new JButton("Okay");
        NiceButton.setActionCommand("Delete_Okay");
        NiceButton.addActionListener(new Main_GUI.ButtonClickListener());
        successSouthPanel.add(NiceButton);
    }

    private static void DeleteCancelButton() {
        JButton BackButton = new JButton("Cancel");
        BackButton.setActionCommand("Delete_Cancel");
        BackButton.addActionListener(new Main_GUI.ButtonClickListener());
        successSouthPanel.add(BackButton);
    }

    public static void DeleteOkayButtonFunction() throws SQLException {
        boolean acceptedCheck;

        database_driver d_driver = database_driver.getConnection();
        acceptedCheck = d_driver.deletePatientRecord(patient.getPatientId());

        if (acceptedCheck == true) {
            confirmFrame.setVisible(false);
            Patient_GUI.GoToGPGUI();
        }
    }

    public static void DeleteCancelButtonFunction() {
        confirmFrame.setVisible(false);
    }
}
