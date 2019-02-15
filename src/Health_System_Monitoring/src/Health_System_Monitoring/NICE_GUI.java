package Health_System_Monitoring;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


/**
 * @author - Nick Maslin
 * @desc - GUI for NICE test
 */

public class NICE_GUI {
    public static JFrame mainFrame;
    private static JPanel welcomePanel, controlPanel, bmiPanel, bpPanel, smokingPanel, hbaPanel, urinaryPanel, serumPanel, cholPanel, eyePanel, footPanel;
    private static JLabel welcomeLbl;


    public static void prepareNiceGUI() {
        GP_GUI.mainFrame.setVisible(false);

        mainFrame = new JFrame("NICE Test");
        mainFrame.setSize(600, 550);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        welcomePanel = new JPanel();
        welcomePanel.setLayout(new FlowLayout());

        welcomeLbl = new JLabel("NICE Test");

        welcomePanel.add(welcomeLbl);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // BMI Panel
        bmiPanel = new JPanel();
        bmiPanel.setLayout(new FlowLayout());
        bmiPanel.setPreferredSize(new Dimension(150, 140));
        TitledBorder bmiBorder = new TitledBorder("BMI");
        setBorderTitle(bmiBorder);
        bmiPanel.setBorder(bmiBorder);

        JLabel heightLbl = new JLabel("Height: ");
        JTextField heightTxt = new JTextField(3);
        JLabel cmLbl = new JLabel("cm");
        bmiPanel.add(heightLbl);
        bmiPanel.add(heightTxt);
        bmiPanel.add(cmLbl);

        JLabel weightLbl = new JLabel("Weight: ");
        JTextField weightTxt = new JTextField(4);
        JLabel kgLbl = new JLabel("kg");
        bmiPanel.add(weightLbl);
        bmiPanel.add(weightTxt);
        bmiPanel.add(kgLbl);

        JLabel ageLbl = new JLabel("Age: ");
        JTextField ageTxt = new JTextField(3);
        JLabel yearLbl = new JLabel("years");
        bmiPanel.add(ageLbl);
        bmiPanel.add(ageTxt);
        bmiPanel.add(yearLbl);

        JLabel genderLbl = new JLabel("Gender: ");
        String[] gender = {"", "Male", "Female"};
        JComboBox genderList = new JComboBox(gender);
        bmiPanel.add(genderLbl);
        bmiPanel.add(genderList);

        controlPanel.add(bmiPanel);

        // Blood Pressure Panel
        bpPanel = new JPanel();
        bpPanel.setLayout(new FlowLayout());
        bpPanel.setPreferredSize(new Dimension(200, 150));
        TitledBorder bpBorder = new TitledBorder("Blood Pressure");
        setBorderTitle(bpBorder);
        bpPanel.setBorder(bpBorder);

        JCheckBox kidneyDam = new JCheckBox("Kidney Damage");
        JCheckBox eyeDam = new JCheckBox("Eye Damage");
        JCheckBox cercDam = new JCheckBox("Cercbrovascluar Damage");
        
        JTextField systolicTxt = new JTextField(3);
        JLabel slashLbl = new JLabel("/");
        JTextField diastolicLbl = new JTextField(3);
        JLabel mmhgLbl = new JLabel("mmHg");

        bpPanel.add(kidneyDam);
        bpPanel.add(eyeDam);
        bpPanel.add(cercDam);
        bpPanel.add(systolicTxt);
        bpPanel.add(slashLbl);
        bpPanel.add(diastolicLbl);
        bpPanel.add(mmhgLbl);
        controlPanel.add(bpPanel);

        // Smoking Panel
        smokingPanel = new JPanel();
        smokingPanel.setLayout(new FlowLayout());
        TitledBorder smokingBorder = new TitledBorder("Smoking Status");
        setBorderTitle(smokingBorder);
        smokingPanel.setBorder(smokingBorder);
        String[] smokingStatus = {"", "Smoker", "Non-Smoker"};
        JComboBox smokingList = new JComboBox(smokingStatus);

        smokingPanel.add(smokingList);
        controlPanel.add(smokingPanel);

        // HBA Panel
        hbaPanel = new JPanel();
        hbaPanel.setLayout(new FlowLayout());
        hbaPanel.setPreferredSize(new Dimension(200, 50));
        TitledBorder hbaBorder = new TitledBorder("Glycosylated Haemoglobin (HbA)");
        setBorderTitle(hbaBorder);
        hbaPanel.setBorder(hbaBorder);

        JTextField hbaTxt = new JTextField(3);
        JLabel hbaLbl = new JLabel("HbA1C");

        hbaPanel.add(hbaTxt);
        hbaPanel.add(hbaLbl);
        controlPanel.add(hbaPanel);

        // Urinary Panel
        urinaryPanel = new JPanel();
        urinaryPanel.setLayout(new FlowLayout());
        TitledBorder urinaryBorder = new TitledBorder("Urinary Albumin");
        setBorderTitle(urinaryBorder);
        urinaryPanel.setBorder(urinaryBorder);

        JTextField acrTxt = new JTextField(3);
        JLabel acrLbl = new JLabel("mg/mmol");

        urinaryPanel.add(acrTxt);
        urinaryPanel.add(acrLbl);
        controlPanel.add(urinaryPanel);

        // Serum Panel
        serumPanel = new JPanel();
        serumPanel.setLayout(new FlowLayout());
        serumPanel.setPreferredSize(new Dimension(125, 75));
        TitledBorder serumBorder = new TitledBorder("Serum Creatine");
        setBorderTitle(serumBorder);
        serumPanel.setBorder(serumBorder);

        JTextField micromolTxt = new JTextField(3);
        JLabel micromolLbl = new JLabel("micromol/L");
        JTextField egfrTxt = new JTextField(2);
        JLabel egfrLbl = new JLabel("eGFR");

        serumPanel.add(micromolTxt);
        serumPanel.add(micromolLbl);
        serumPanel.add(egfrTxt);
        serumPanel.add(egfrLbl);
        controlPanel.add(serumPanel);

        // Cholesterol Panel
        cholPanel = new JPanel();
        cholPanel.setLayout(new FlowLayout());
        cholPanel.setPreferredSize(new Dimension(200, 100));
        TitledBorder cholBorder = new TitledBorder("Cholestrerol");
        setBorderTitle(cholBorder);
        cholPanel.setBorder(cholBorder);

        JLabel tcLbl = new JLabel("Total Cholesterol: ");
        JTextField tcTxt = new JTextField(3);
        JLabel lipoLbl = new JLabel("Lipoproteins: ");
        JTextField lipoTxt = new JTextField(3);
        JLabel mmolLbl = new JLabel("mmol/L");

        cholPanel.add(tcLbl);
        cholPanel.add(tcTxt);
        cholPanel.add(lipoLbl);
        cholPanel.add(lipoTxt);
        cholPanel.add(mmolLbl);
        controlPanel.add(cholPanel);

        // Eye Examination Panel
        eyePanel = new JPanel();
        eyePanel.setLayout(new FlowLayout());
        eyePanel.setPreferredSize(new Dimension(250, 125));
        TitledBorder eyeBorder = new TitledBorder("Eye Examination");
        setBorderTitle(eyeBorder);
        eyePanel.setBorder(eyeBorder);

        JCheckBox visionBox = new JCheckBox("Sudden loss of vision");
        JCheckBox retinaBox = new JCheckBox("Pre-retinal or vitreous haemoerhage");
        JCheckBox detatchBox = new JCheckBox("Retinal detatchment");
        JCheckBox rubeosisBox = new JCheckBox("Rubeosis");

        eyePanel.add(visionBox);
        eyePanel.add(retinaBox);
        eyePanel.add(detatchBox);
        eyePanel.add(rubeosisBox);
        controlPanel.add(eyePanel);

        // Foot Examination Panel
        footPanel = new JPanel();
        footPanel.setLayout(new FlowLayout());
        TitledBorder footBorder = new TitledBorder("Foot Examination");
        setBorderTitle(footBorder);
        footPanel.setBorder(footBorder);

        JCheckBox senseBox = new JCheckBox("Lack of sensation");
        JCheckBox deformBox = new JCheckBox("Deformity");
        JCheckBox pulseBox = new JCheckBox("Palpatation of foot pulse");
        JCheckBox shoeBox = new JCheckBox("Inappropriate footwear");

        footPanel.add(senseBox);
        footPanel.add(deformBox);
        footPanel.add(pulseBox);
        footPanel.add(shoeBox);
        controlPanel.add(footPanel);

        // Submit button
        JButton submitBtn = new JButton("Submit");
        controlPanel.add(submitBtn);

        NiceBackButton();

        mainFrame.add(welcomePanel, BorderLayout.NORTH);
        mainFrame.add(controlPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    public static void NiceBackButton() {
        JButton niceBackBtn = new JButton("Back");
        niceBackBtn.setActionCommand("Nice_Back");
        niceBackBtn.addActionListener(new Main_GUI.ButtonClickListener());
        controlPanel.add(niceBackBtn);
    }

    public static void setBorderTitle(TitledBorder border) {
        border.setTitleJustification(TitledBorder.LEFT);
        border.setTitlePosition(TitledBorder.TOP);
    }

    public static void GoToPatientGUI() {
        mainFrame.setVisible(false);
        Patient_GUI.mainFrame.setVisible(true);
    }
}
