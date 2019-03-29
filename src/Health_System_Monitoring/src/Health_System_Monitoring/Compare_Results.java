package Health_System_Monitoring;

import javax.swing.*;

public class Compare_Results extends JOptionPane {

    private static JRadioButton weightBtn, bpBtn, haemoglobinBtn, urinaryBtn, serumBtn, cholesterolBtn;
    private static JPanel panel;

    public static void DisplayPanel() {
        JLabel message = new JLabel("Select which graph to display:");

        weightBtn = new JRadioButton("Weight");
        bpBtn = new JRadioButton("Blood Pressure");
        haemoglobinBtn = new JRadioButton("Haemoglobin");
        urinaryBtn = new JRadioButton("Urinary Albumin");
        serumBtn = new JRadioButton("Serum Creatine");
        cholesterolBtn = new JRadioButton("Cholesterol");

        panel = new JPanel();
        panel.add(message);
        panel.add(weightBtn);
        panel.add(bpBtn);
        panel.add(haemoglobinBtn);
        panel.add(urinaryBtn);
        panel.add(serumBtn);
        panel.add(cholesterolBtn);

        ButtonGroup group = new ButtonGroup();
        group.add(weightBtn);
        group.add(bpBtn);
        group.add(haemoglobinBtn);
        group.add(urinaryBtn);
        group.add(serumBtn);
        group.add(cholesterolBtn);

        JOptionPane.showMessageDialog(null, panel);
    }
}
