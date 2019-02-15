package Health_System_Monitoring;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class SC_GUI {
	public static JFrame mainFrame = new JFrame ("SCreen");;
    static JButton BackButton = new JButton("back");
    static JButton ScheduleButton = new JButton("Schedule");

	
	public static void prepareSCGUI() {
		Main_GUI.mainFrame.setVisible(false);
		//mainFrame = new JFrame ("SCreen");
		mainFrame.setSize(500, 500);
		mainFrame.setLayout(new FlowLayout());	
		mainFrame.getContentPane().add(ScheduleButton);
		mainFrame.getContentPane().add(BackButton);	
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
