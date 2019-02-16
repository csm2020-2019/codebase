package Health_System_Monitoring;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SC_GUI {
	public static JFrame mainFrame = new JFrame ("SCreen");;
	private static JPanel TimesPanel = new JPanel();
	private static JLabel conText = new JLabel("set open and closing times");
	private static JPanel panel = new JPanel();
	private static JLabel monLabel = new JLabel("monday: ");
	private static JLabel tueLabel = new JLabel("tuesday: ");
	private static JLabel wedLabel = new JLabel("wednesday: ");
	private static JLabel thuLabel = new JLabel("thursday: ");
	private static JLabel friLabel = new JLabel("friday: ");
	private static JLabel satLabel = new JLabel("saturday: ");
	private static JLabel sunLabel = new JLabel("sunday: ");
	private static JTextField monOpen = new JTextField(5);
	private static JTextField monclose = new JTextField(5);
	private static JTextField tueOpen = new JTextField(5);
	private static JTextField tueclose = new JTextField(5);
	private static JTextField wedOpen = new JTextField(5);
	private static JTextField wedclose = new JTextField(5);
	private static JTextField thuOpen = new JTextField(5);
	private static JTextField thuclose = new JTextField(5);
	private static JTextField friOpen = new JTextField(5);
	private static JTextField friclose = new JTextField(5);
	private static JTextField satOpen = new JTextField(5);
	private static JTextField satclose = new JTextField(5);
	private static JTextField sunOpen = new JTextField(5);
	private static JTextField sunclose = new JTextField(5);
	private static JPanel BtnPanel = new JPanel();
	private static JButton BackButton = new JButton("back");
	private static JButton ScheduleButton = new JButton("set times");

	
	public static void prepareSCGUI() {
		Main_GUI.mainFrame.setVisible(false);
		mainFrame = new JFrame ("SCreen");
		mainFrame.setSize(500, 500);
		mainFrame.setLayout(new FlowLayout());	
		TimesPanel.setLayout(new BorderLayout());	
		panel.setLayout(new GridLayout(7,3));
		panel.add(monLabel);
		panel.add(monOpen);
		panel.add(monclose);
		panel.add(tueLabel);
		panel.add(tueOpen);
		panel.add(tueclose);
		panel.add(wedLabel);
		panel.add(wedOpen);
		panel.add(wedclose);
		panel.add(thuLabel);
		panel.add(thuOpen);
		panel.add(thuclose);
		panel.add(friLabel);
		panel.add(friOpen);
		panel.add(friclose);
		panel.add(satLabel);
		panel.add(satOpen);
		panel.add(satclose);
		panel.add(sunLabel);
		panel.add(sunOpen);
		panel.add(sunclose);
		BtnPanel.add(ScheduleButton);
		BtnPanel.add(BackButton);
		TimesPanel.add(conText,BorderLayout.NORTH);
		TimesPanel.add(panel, BorderLayout.CENTER);
		TimesPanel.add(BtnPanel, BorderLayout.SOUTH);
		mainFrame.getContentPane().add(TimesPanel);
		mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
		mainFrame.setVisible(true);
		btnListen();
	}
	
	static void btnListen() {
		BackButton.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	    		mainFrame.setVisible(false);
	            Main_GUI.mainFrame.setVisible(true);
	    	        }  
	    	    });
		ScheduleButton.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	    		mainFrame.setVisible(false);
	        	//do a scheduling thing
	    	        }
	    	    });
	}
}
