package Health_System_Monitoring;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class GP_GUI {
    public static JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel searchLabel;
    private JPanel northPanel;
    private JPanel controlPanel;
    private JPanel southPanel;
    private JTextField patientSearchField;
    private JComboBox<String> formCreateComboBox;
    private SpringLayout layout = new SpringLayout();

    private HashMap<String,Integer> editableFormLookup;

    public void prepareGPGUI() {

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
        controlPanel = new JPanel();
        southPanel = new JPanel();

        controlPanel.setLayout(layout);

        HeaderLabel();
        RegisterPatientButton();
        SearchLabel();
        PatientSearchField();
        PatientSearchButton();
        FormComboBox();
        BackButton();

        mainFrame.setLocation(Main_GUI.GetWindowPosition().x -125, Main_GUI.GetWindowPosition().y -165);
        mainFrame.add(northPanel, BorderLayout.NORTH);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        mainFrame.add(southPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);

        if(Form_GUI.mainFrame == null)
        {
            Form_GUI.prepareFormGUI();
        }
        Form_GUI.mainFrame.setVisible(false);
    }

    /**
     *
     * @throws SQLException
     */
    public void PatientSearchButtonFunction() throws SQLException {
        String searchField = patientSearchField.getText();
        //String searchField = "Jones";
        System.out.println("Print off of search bar input: " + searchField);

        List<Patient> pat = Arrays.asList(new Patient[0]);
        PatientDao pDao = new PatientDao();
        pat = (List<Patient>) pDao.searchPatientByLastName(searchField);
        if (!searchField.isEmpty()) {
            if (!pat.isEmpty()) {
                System.out.println(pat);
                Patient_GUI patient_GUI = new Patient_GUI();
                patient_GUI.preparePatientGUI(pat.get(0));
            } else {
                System.out.println("Patient list is Empty");
            }
        } else {
            System.out.println("Search was Empty");
        }
    }



    public void GoBackToMainGUI() {
        mainFrame.setVisible(false);
        Main_GUI.mainFrame.setVisible(true);
    }

    private void HeaderLabel() {
        headerLabel = new JLabel("", JLabel.CENTER);
        headerLabel.setText("General Practitioner Menu");
        northPanel.add(headerLabel);
    }

    private void SearchLabel() {
        searchLabel = new JLabel("", JLabel.CENTER);
        searchLabel.setText("Search patients: ");
        controlPanel.add(searchLabel);

        layout.putConstraint(SpringLayout.NORTH, searchLabel, 55, SpringLayout.NORTH, controlPanel);
        layout.putConstraint(SpringLayout.WEST, searchLabel, 5, SpringLayout.WEST, controlPanel);
    }

    private void PatientSearchField() {
        patientSearchField = new JTextField("");
        patientSearchField.setPreferredSize(new Dimension(100, 25));
        controlPanel.add(patientSearchField);

        layout.putConstraint(SpringLayout.NORTH, patientSearchField, 50, SpringLayout.NORTH, controlPanel);
        layout.putConstraint(SpringLayout.WEST, patientSearchField, 100, SpringLayout.WEST, controlPanel);
    }

    private void UpdateFormsLookup()
    {
        FormDao dao = new FormJDBC();
        editableFormLookup = (HashMap<String,Integer>)dao.getFormsForGP(Main_GUI.getCurrentUser().getUserId());
    }

    private void FormComboBox() {
        UpdateFormsLookup();
        Set<String> names = editableFormLookup.keySet();
        String[] nameArray = names.toArray(new String[names.size()+1]);
        nameArray[names.size()] = "New Form...";
        formCreateComboBox = new JComboBox<String>(nameArray);

        formCreateComboBox.setVisible(true);
        controlPanel.add(formCreateComboBox);

        layout.putConstraint(SpringLayout.SOUTH, formCreateComboBox, 5, SpringLayout.SOUTH, controlPanel);

        // button to launch the selected form
        JButton gpButton = new JButton("Go");
        gpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = (String)formCreateComboBox.getSelectedItem();
                if(key == "New Form...")
                {
                    // generate a new empty form
                    Form_GUI.spawnEmptyForm();
                }
                else {
                    Integer value = editableFormLookup.getOrDefault(key, -1);
                    if (value < 0) {
                        // didn't find the form for some reason
                    } else {
                        // found the form ID we want to open, so open it
                        Form_GUI.setEditMode(true);
                        Form_GUI.openExistingForm(value);
                    }
                }
            }
        });

        controlPanel.add(gpButton);
        layout.putConstraint(SpringLayout.SOUTH, gpButton, 5, SpringLayout.SOUTH, controlPanel);
        layout.putConstraint(SpringLayout.WEST, gpButton, 5, SpringLayout.EAST, formCreateComboBox);

    }

    /**
     * Create GUI for back button
     */
    private void BackButton() {
        JButton BackButton = new JButton("Back");
        BackButton.setActionCommand("GP_Back");
        BackButton.addActionListener(new GP_GUI.ButtonClickListener());
        southPanel.add(BackButton);
    }

    /**
     * Create GUI for search button for patients
     */
    private void PatientSearchButton() {
        JButton PatientSearchButton = new JButton("Search");
        PatientSearchButton.setActionCommand("GP_Patient_Search");
        PatientSearchButton.addActionListener(new GP_GUI.ButtonClickListener());
        controlPanel.add(PatientSearchButton);

        layout.putConstraint(SpringLayout.NORTH, PatientSearchButton, 50, SpringLayout.NORTH, controlPanel);
        layout.putConstraint(SpringLayout.WEST, PatientSearchButton, 205, SpringLayout.WEST, controlPanel);
    }


    /**
     * Create GUI for register button
     */
    private void RegisterPatientButton() {
        JButton RegisterPatientButton = new JButton("Register Patient");
        RegisterPatientButton.setActionCommand("GP_Register");
        RegisterPatientButton.addActionListener(new GP_GUI.ButtonClickListener());
        controlPanel.add(RegisterPatientButton);

        layout.putConstraint(SpringLayout.NORTH, RegisterPatientButton, 5, SpringLayout.NORTH, controlPanel);
        layout.putConstraint(SpringLayout.WEST, RegisterPatientButton, 25, SpringLayout.WEST, controlPanel);
    }

    /**
     * Action Listener that looks out for button presses in GP_GUI
     */
    class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            //GP_GUI gp_gui = new GP_GUI();
            GP_Register_GUI gp_register_gui = new GP_Register_GUI();

            if (command.equals("Default")) {
                //Do nothing
            } else if (command.equals("GP_Back")) {
                GoBackToMainGUI();
            } else if (command.equals("GP_Patient_Search")) {
                try {
                    Main_GUI.SetWindowPosition(mainFrame.getLocation().x,mainFrame.getLocation().y);
                    PatientSearchButtonFunction();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } else if (command.equals("GP_Register")) {
                Main_GUI.SetWindowPosition(mainFrame.getLocation().x,mainFrame.getLocation().y);
                gp_register_gui.prepareGPGUI(true);
            }
        }
    }
}
