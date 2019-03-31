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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RD_GUI {
    public static JFrame mainFrame;
    private JLabel headerLabel;
    private JPanel northPanel;
    private JPanel controlPanel;
    private JPanel southPanel, referalsWindowPanel;
    private JScrollPane scrollPane;
    private SpringLayout layout = new SpringLayout();
    private JButton button = new JButton();
    private Object[][] patientReferals;
    private List<Patient> patients  = new ArrayList<>();

    private int userId;

    public void prepareRDGUI() {

        Main_GUI.mainFrame.setVisible(false);

        //userId = Main_GUI.getCurrentUser().getUserId();

        if (userId == 0) {
            userId = 3;
        }

        mainFrame = new JFrame("RD application");
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
        BackButton();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ReferalsWindow();
            }
        });

        mainFrame.setLocation(Main_GUI.GetWindowPosition().x - 125, Main_GUI.GetWindowPosition().y - 165);
        mainFrame.add(northPanel, BorderLayout.NORTH);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        mainFrame.add(southPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    /**
     *
     */
    public void GoBackToMainGUI() {
        mainFrame.setVisible(false);
        Main_GUI.mainFrame.setVisible(true);
    }

    private void HeaderLabel() {
        headerLabel = new JLabel("", JLabel.CENTER);
        headerLabel.setText("Rehabilitation Doctor Menu");
        northPanel.add(headerLabel);
    }

    /**
     * Create GUI for back button
     */
    private void BackButton() {
        JButton BackButton = new JButton("Back");
        BackButton.setActionCommand("RD_Back");
        BackButton.addActionListener(new ButtonClickListener());
        southPanel.add(BackButton);
    }

    /**
     * @throws SQLException
     */
    public void PatientOpenButtonFunction(int patNum) throws SQLException {
        Patient_GUI patient_GUI = new Patient_GUI();
        patient_GUI.preparePatientGUI(patients.get(patNum), true);
    }

    private void ReferalsWindow() {
        PopulatePatients();
        String[] columns = {"User ID", "Forename", "Surname", "Search"};
        //Object[][] obj = new Object[][]{{"1", "a", "b", "button 1"}, {"2", "c", "d", "button 2"}, {"3", "e", "f", "button 3"},{"g", "h", "i", "button 3"}};
        JTable populatePatients = new JTable();
        populatePatients.setModel(new DefaultTableModel(patientReferals, columns) {
            /*
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            */
        });
        populatePatients.getColumn("Search").setCellRenderer(new ButtonRenderer());
        populatePatients.getColumn("Search").setCellEditor(new ButtonEditor(new JCheckBox()));

        scrollPane = new JScrollPane(populatePatients, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Dimension d = scrollPane.getPreferredSize();
        d.setSize(d.width, d.height * 0.95);
        scrollPane.setPreferredSize(d);

        controlPanel.add(scrollPane);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int i;
                i = populatePatients.getSelectedRow();
                try {
                    PatientOpenButtonFunction(i);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void PopulatePatients() {

        java.util.List<Integer> referrals = new ArrayList<>();
        //java.util.List<Patient> patients = new ArrayList<>();
        UserDao uDao = new UserDao();
        PatientDao pDao = new PatientDao();

        referrals = uDao.getReferralByRD(userId);

        int patientsNum = referrals.size();

        //System.out.println("Patient nums: " + patientsNum);

        patientReferals = new Object[patientsNum][4];

        for (int i = 0; i < patientsNum; i++) {
            Patient temp = pDao.searchPatientById(referrals.get(i)).get(0);
            patients.add(temp);

            patientReferals[i][0] = referrals.get(i);
            patientReferals[i][1] = patients.get(i).getPatientFirstName();
            patientReferals[i][2] = patients.get(i).getPatientLastName();
            patientReferals[i][3] = "--->";
        }
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
     * Action Listener that looks out for button presses in RD_GUI
     */
    class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            //GP_GUI gp_gui = new GP_GUI();
            //GP_Register_GUI gp_register_gui = new GP_Register_GUI();

            if (command.equals("Default")) {
                //Do nothing
            } else if (command.equals("RD_Back")) {
                GoBackToMainGUI();
            } else {
                System.out.println("No Input for button");
            }
        }
    }
}
