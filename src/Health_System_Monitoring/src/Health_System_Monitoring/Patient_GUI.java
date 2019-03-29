package Health_System_Monitoring;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

public class Patient_GUI {
    public static JFrame mainFrame, confirmFrame;
    private JLabel headerLabel;
    private JPanel northPanel, controlPanel, southPanel, successPanel, successSouthPanel, infoPanel, referPanel;
    private JButton triggerButton;
    private Patient patient;
    private JComboBox<String> referBox;
    private List<User> rd_list;

    public void preparePatientGUI(Patient pat) {
        Main_GUI.SetWindowPosition(GP_GUI.mainFrame.getLocation().x, GP_GUI.mainFrame.getLocation().y);
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
        controlPanel = new JPanel();
        southPanel = new JPanel();

        patient = pat;

        HeaderLabel();
        ModifyRecordButton();
        DeleteRecordButton();
        PatientBackButton();
        AddNiceButton();
<<<<<<< HEAD
        ResultsButton();
        PrescribeCheckBox();
=======
>>>>>>> master
        PatientInfoPanel();
        PatientInfoDisplay();
        PatientReferPanel();

        mainFrame.setLocation(Main_GUI.GetWindowPosition());
        mainFrame.add(northPanel, BorderLayout.NORTH);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        mainFrame.add(southPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    private void PatientInfoPanel() {
        infoPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 4);
        infoPanel.setLayout(flowLayout);
        infoPanel.setPreferredSize(new Dimension(400, 200));
        TitledBorder patientBorder = new TitledBorder("Patient Details");
        infoPanel.setBorder(patientBorder);
        controlPanel.add(infoPanel);
    }

    private void PatientReferPanel() {
        referPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 4);
        referPanel.setLayout(flowLayout);

    	// first check to see if we're already referred

        UserDaoInterface userDao = UserDao.getDAO();

        int rd_id = userDao.getReferralByPatientId(patient.getPatientId());
        if(rd_id !=-1) {
            // we have a referral, so show that
            User rd = userDao.getUserById(rd_id);

            JLabel referred = new JLabel (rd.getUserFirstName() + " " + rd.getUserLastName());
            referPanel.add(referred);
        }
        else {
            // first up, the combo box containing all RDs

            if (rd_list == null) {
                rd_list = new ArrayList<User>();
            } else {
                rd_list.clear();
            }
            rd_list = userDao.getUserByType("rd");
            Vector<String> name_list = new Vector<String>(rd_list.size());

        for (User user : rd_list) {
            name_list.add(user.getUserFirstName() + " " + user.getUserLastName());
        }

        referBox = new JComboBox<String>(name_list);

        // next up, the button to trigger referral

            triggerButton = new JButton("Refer");
            triggerButton.setActionCommand("Refer_Patient");
            triggerButton.addActionListener(new Patient_GUI.ButtonClickListener());

            referPanel.add(referBox);
            referPanel.add(triggerButton);
        }
        TitledBorder referBorder = new TitledBorder("Referrals");
        referPanel.setBorder(referBorder);
    	controlPanel.add(referPanel);
    }

    private void PatientInfoDisplay() {
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

    public void ReferPatient() {
        int selected = referBox.getSelectedIndex();
        // text box maps one-to-one with returned RD user list, which is stored in rd_list

        User rd = rd_list.get(selected);
        User gp = Main_GUI.getCurrentUser();

        int rd_id = rd.getUserId();
        int gp_id = gp.getUserId();
        int patient_id = patient.getPatientId();

        UserDao userDao = new UserDao();
        boolean result = userDao.addReferral(patient_id, gp_id, rd_id);


    	referBox.setEditable(false);
    	referBox.setEnabled(false);
        triggerButton.setEnabled(false);
        triggerButton.setText("Referred");
    }

    public void ModifyRecordButtonFunction() {
        GP_Register_GUI bob = new GP_Register_GUI();
        bob.prepareModifyGPGUI(patient);
    }

    private void HeaderLabel() {
        headerLabel = new JLabel("", JLabel.CENTER);
        headerLabel.setText("Patient Record Overview");
        northPanel.add(headerLabel);
    }

    public void GoToGPGUI() {
        mainFrame.setVisible(false);
        GP_GUI.mainFrame.setLocation(Main_GUI.GetWindowPosition());
        GP_GUI.mainFrame.setVisible(true);
    }

    public void DeleteConfirmWindow() {
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

    public void DeleteOkayButtonFunction() throws SQLException {
        boolean acceptedCheck;

        PatientDao pDao = (PatientDao) new PatientDao();
        acceptedCheck = pDao.deletePatientRecord(patient.getPatientId());

        if (acceptedCheck == true) {
            confirmFrame.setVisible(false);
            Patient_GUI patient_GUI = new Patient_GUI();
            patient_GUI.GoToGPGUI();
        }
    }

    public void DeleteCancelButtonFunction() {
        confirmFrame.setVisible(false);
    }

    /**
     * Create GUI for
     */
    private void ModifyRecordButton() {
        JButton NiceButton = new JButton("Modify Record");
        NiceButton.setActionCommand("Patient_Modify_Record");
        NiceButton.addActionListener(new Patient_GUI.ButtonClickListener());
        controlPanel.add(NiceButton);
    }

    /**
     * Create GUI for record delete button
     */
    private void DeleteRecordButton() {
        JButton NiceButton = new JButton("Delete Record");
        NiceButton.setActionCommand("Patient_Delete_Record");
        NiceButton.addActionListener(new Patient_GUI.ButtonClickListener());
        controlPanel.add(NiceButton);
    }

    /**
     * Create GUI for add NICE test button
     */
    private void AddNiceButton() {
        JButton NiceButton = new JButton("Add Nice Test");
        NiceButton.setActionCommand("Patient_Nice");
        NiceButton.addActionListener(new Patient_GUI.ButtonClickListener());
        controlPanel.add(NiceButton);
    }

    /**
     * Create graph of test results
     */
    private void ResultsButton() {
        JButton ResultsButton = new JButton ("Compare Results");
        ResultsButton.setActionCommand("Results_Graph");
        ResultsButton.addActionListener(new Patient_GUI.ButtonClickListener());
        controlPanel.add(ResultsButton);
    }

    /**
     * Create GUI for back button
     */
    private void PatientBackButton() {
        JButton BackButton = new JButton("Back");
        BackButton.setActionCommand("Patient_Back");
        BackButton.addActionListener(new Patient_GUI.ButtonClickListener());
        southPanel.add(BackButton);
    }

    /**
     * Create GUI for okay button in delete confirmation window
     */
    private void DeleteOkayButton() {
        JButton NiceButton = new JButton("Okay");
        NiceButton.setActionCommand("Patient_Delete_Okay");
        NiceButton.addActionListener(new Patient_GUI.ButtonClickListener());
        successSouthPanel.add(NiceButton);
    }

    /**
     * Create GUI for cancel button in delete confirmation window
     */
    private void DeleteCancelButton() {
        JButton BackButton = new JButton("Cancel");
        BackButton.setActionCommand("Patient_Delete_Cancel");
        BackButton.addActionListener(new Patient_GUI.ButtonClickListener());
        successSouthPanel.add(BackButton);
    }

<<<<<<< HEAD
    /*
    check if check box is checked
    Checkbox - PrescribeCheckBox
    if checked, set patient to prescribe third party materials
     */
    public void itemStateChanged(ItemEvent e) {

        if (e.getStateChange() == ItemEvent.SELECTED) {
            PatientDao pDao = new PatientDao();
            patient.setPatient_email_prescription(true);
            pDao.setPatientThirdPartyPrescription(patient);

            System.out.println("Patient prescribed");
        } else {
            PatientDao pDao = new PatientDao();
            patient.setPatient_email_prescription(false);
            pDao.setPatientThirdPartyPrescription(patient);

            System.out.println("Patient not prescribed");
        }
    }

    private void CompareResults() {
        String[] values = {"Weight", "Blood Pressure", "Haemoglobin", "Urinary Albumin", "Serum Creatine", "eGFR", "Cholesterol"};

        JOptionPane.showInputDialog(mainFrame, "Which results would you like to display?", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "None");


    }

=======
>>>>>>> master
    /**
     * Action Listener that looks out for button presses in Patient_GUI
     */
    class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            GP_GUI gp_gui = new GP_GUI();
            GP_Register_GUI gp_register_gui = new GP_Register_GUI();
            //Patient_GUI patient_gui = new Patient_GUI();
            NICE_GUI nice_gui = new NICE_GUI();

            if (command.equals("Default")) {
                //Do nothing
            } else if (command.equals("Patient_Back")) {
                Main_GUI.SetWindowPosition(mainFrame.getLocation().x, mainFrame.getLocation().y);
                GoToGPGUI();
            } else if (command.equals("Patient_Delete_Cancel")) {
                DeleteCancelButtonFunction();
            } else if (command.equals("Patient_Delete_Okay")) {
                try {
                    DeleteOkayButtonFunction();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } else if (command.equals("Patient_Delete_Record")) {
                DeleteConfirmWindow();
            } else if (command.equals("Patient_Modify_Record")) {
                Main_GUI.SetWindowPosition(mainFrame.getLocation().x, mainFrame.getLocation().y);
                ModifyRecordButtonFunction();
            } else if (command.equals("Patient_Nice")) {
                nice_gui.prepareNiceGUI();
            } else if (command.equals("Refer_Patient")) {
                ReferPatient();
            } else if (command.equals("Results_Graph")) {
                CompareResults();
            }
        }
    }
}