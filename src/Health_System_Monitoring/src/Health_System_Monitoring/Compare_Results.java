package Health_System_Monitoring;

import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;

public class Compare_Results {

    private static JRadioButton weightBtn, bpBtn, haemoglobinBtn, urinaryBtn, serumBtn, cholesterolBtn;
    private static JPanel panel, north, center;

    public static void DisplayPanel() {
        JLabel message = new JLabel("Select which graph to display:");

        weightBtn = new JRadioButton("Weight");
        bpBtn = new JRadioButton("Blood Pressure");
        haemoglobinBtn = new JRadioButton("Haemoglobin");
        urinaryBtn = new JRadioButton("Urinary Albumin");
        serumBtn = new JRadioButton("Serum Creatine");
        cholesterolBtn = new JRadioButton("Cholesterol");

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        north = new JPanel();
        center = new JPanel();

        north.add(message);
        center.add(weightBtn);
        center.add(bpBtn);
        center.add(haemoglobinBtn);
        center.add(urinaryBtn);
        center.add(serumBtn);
        center.add(cholesterolBtn);

        panel.add(north, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);

        ButtonGroup group = new ButtonGroup();
        group.add(weightBtn);
        group.add(bpBtn);
        group.add(haemoglobinBtn);
        group.add(urinaryBtn);
        group.add(serumBtn);
        group.add(cholesterolBtn);

        JOptionPane.showMessageDialog(null, panel);

        if (weightBtn.isSelected()) {
            System.out.println("weight");
            result_graph graph = new result_graph("Weight Chart", "Patients Weight Over Time", "Date", "kg");
            graph.pack();
            RefineryUtilities.centerFrameOnScreen(graph);
            graph.setVisible(true);
        } else if (bpBtn.isSelected()){
            System.out.println("blood pressure");
            result_graph graph = new result_graph("Blood Pressure Chart", "Patients Blood Pressure Over Time", "Date", "mmHg");
            graph.pack();
            RefineryUtilities.centerFrameOnScreen(graph);
            graph.setVisible(true);
        } else if (haemoglobinBtn.isSelected()) {
            System.out.println("haemoglobin");
            result_graph graph = new result_graph("Haemoglobin Chart", "Patients Haemoglobin Count Over Time", "Date", "% HbA1c");
            graph.pack();
            RefineryUtilities.centerFrameOnScreen(graph);
            graph.setVisible(true);
        } else if (urinaryBtn.isSelected()) {
            System.out.println("urinary");
            result_graph graph = new result_graph("Urinary Albumin Chart", "Patients Urinary Albumin Over Time", "Date", "mg/mmol");
            graph.pack();
            RefineryUtilities.centerFrameOnScreen(graph);
            graph.setVisible(true);
        } else if (serumBtn.isSelected()) {
            System.out.println("serum creatine");
            result_graph graph = new result_graph("Serum Creatine Chart", "Patients Serum Creatine Over Time", "Date", "micromol/L");
            graph.pack();
            RefineryUtilities.centerFrameOnScreen(graph);
            graph.setVisible(true);
        } else if (cholesterolBtn.isSelected()){
            System.out.println("cholesterol");
            result_graph graph = new result_graph("Cholesterol Chart", "Patients Cholesterol Over Time", "Date", "Total Cholesterol");
            graph.pack();
            RefineryUtilities.centerFrameOnScreen(graph);
            graph.setVisible(true);
        }
    }
}
