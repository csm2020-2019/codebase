package Health_System_Monitoring;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

    public void prepareRDGUI() {

        Main_GUI.mainFrame.setVisible(false);

        mainFrame = new JFrame("RD application");
        mainFrame.setSize(500, 550);
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

        controlPanel.add(scrollPane);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int i;
                i = populatePatients.getSelectedRow();
                JOptionPane.showMessageDialog(null, "Patient ID is " + patientReferals[i][0]);
            }
        });
    }

    private void PopulatePatients() {
        int i = 30;
        patientReferals = new Object[i][4];
        for (i = 0; i < 30; i++) {
            patientReferals[i][0] = i;
            patientReferals[i][1] = "a";
            patientReferals[i][2] = "b";
            patientReferals[i][3] = "--->";
        }
        //scrollPane.updateUI();
    }

    private void ReferalsWindow2() {
        final JFrame frame = new JFrame("Scroll Pane Example");

        // Display the window.
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());

        JTextArea textArea = new JTextArea(20, 20);
        JScrollPane scrollableTextArea = new JScrollPane(textArea);

        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.getContentPane().add(scrollableTextArea);
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
