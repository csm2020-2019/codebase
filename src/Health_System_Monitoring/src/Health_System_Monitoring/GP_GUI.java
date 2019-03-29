package Health_System_Monitoring;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

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
    private JLabel headerLabel, searchLabel;
    private JPanel northPanel, controlPanel, southPanel, searchPanel;
    private JScrollPane scrollPane;
    private JTextField patientSearchField;
    private JComboBox<String> formCreateComboBox;
    private JButton button = new JButton();
    private SpringLayout layout = new SpringLayout();
    private Object[][] patientSearch;
    private List<Patient> patients = Arrays.asList(new Patient[0]);

    private HashMap<String, Integer> editableFormLookup;

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
        searchPanel = new JPanel();

        controlPanel.setLayout(layout);

        PatientDao pDao = new PatientDao();
        patients = pDao.getAllPatientRecords();

        HeaderLabel();
        RegisterPatientButton();
        SearchLabel();
        PatientSearchField();
        PatientSearchButton();
        GPCreateFormButton();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ReferalsWindow();
            }
        });
        BackButton();

        mainFrame.setLocation(Main_GUI.GetWindowPosition().x - 125, Main_GUI.GetWindowPosition().y - 165);
        mainFrame.add(northPanel, BorderLayout.NORTH);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        mainFrame.add(southPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    /**
     * @throws SQLException
     */
    public void PatientSearchButtonFunction() throws SQLException {
        String searchField = patientSearchField.getText();
        System.out.println("Print off of search bar input: " + searchField);

        PatientDao pDao = new PatientDao();
        patients = (List<Patient>) pDao.searchPatientByLastName(searchField);
        if (!searchField.isEmpty()) {
            if (!patients.isEmpty()) {
                System.out.println(patients);
                //PopulatePatients();
                searchPanel.removeAll();
                ReferalsWindow();
                searchPanel.updateUI();
            } else {
                System.out.println("Patient list is Empty");
            }
        } else {
            System.out.println("Search was Empty");
        }
    }

    /**
     * @throws SQLException
     */
    public void PatientOpenButtonFunction(int patNum) throws SQLException {

        Patient_GUI patient_GUI = new Patient_GUI();
        patient_GUI.preparePatientGUI(patients.get(patNum));
        System.out.println(patients.get(patNum).getPatientFirstName());
    }

    private void PopulatePatients() {

        java.util.List<Integer> referrals = new ArrayList<>();
        UserDao uDao = new UserDao();

        //referrals = uDao.getReferralByRD(userId);

        //System.out.println(patients);

        for (int i = 0; i < patients.size(); i++) {
            referrals.add(patients.get(i).getPatientId());
        }

        int patientsNum = referrals.size();

        //System.out.println(patientsNum);
        //System.out.println(uDao.getReferralByPatientId(1));

        patientSearch = new Object[patientsNum][4];

        for (int i = 0; i < patientsNum; i++) {
            //System.out.println("Referral patient ID: " + referrals.get(i));
            //patients = pDao.searchPatientById(referrals.get(i));
            //System.out.println(patients.get(i));
            patientSearch[i][0] = referrals.get(i);
            patientSearch[i][1] = patients.get(i).getPatientFirstName();
            patientSearch[i][2] = patients.get(i).getPatientLastName();
            patientSearch[i][3] = "--->";
        }
    }

    private void ReferalsWindow() {
        PopulatePatients();
        String[] columns = {"User ID", "Forename", "Surname", "Search"};
        JTable populatePatients = new JTable();
        populatePatients.setModel(new DefaultTableModel(patientSearch, columns) {
            /*
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            */
        });
        populatePatients.getColumn("Search").setCellRenderer(new GP_GUI.ButtonRenderer());
        populatePatients.getColumn("Search").setCellEditor(new GP_GUI.ButtonEditor(new JCheckBox()));

        scrollPane = new JScrollPane(populatePatients, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        searchPanel.add(scrollPane);
        controlPanel.add(searchPanel);

        layout.putConstraint(SpringLayout.NORTH, searchPanel, 75, SpringLayout.NORTH, controlPanel);


        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int i;
                i = populatePatients.getSelectedRow();
                //JOptionPane.showMessageDialog(null, "Patient ID is " + patientSearch[i][0]);
                try {
                    PatientOpenButtonFunction(i);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
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

    private void UpdateFormsLookup() {
        FormDao dao = new FormJDBC();
        editableFormLookup = (HashMap<String, Integer>) dao.getFormsForGP(Main_GUI.getCurrentUser().getUserId());
    }

    private void FormComboBox() {
        UpdateFormsLookup();
        String[] names = (String[]) editableFormLookup.keySet().toArray();
        formCreateComboBox = new JComboBox<String>(names);

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

    public void GPCreateFormButton() {

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

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        private String label;

        public ButtonEditor(JCheckBox checkBox) {

            super(checkBox);

        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

            label = (value == null) ? "" : value.toString();
            button.setText(label);

            return button;

        }
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
                    Main_GUI.SetWindowPosition(mainFrame.getLocation().x, mainFrame.getLocation().y);
                    PatientSearchButtonFunction();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } else if (command.equals("GP_Register")) {
                Main_GUI.SetWindowPosition(mainFrame.getLocation().x, mainFrame.getLocation().y);
                gp_register_gui.prepareGPGUI(true);
            }
        }
    }
}
