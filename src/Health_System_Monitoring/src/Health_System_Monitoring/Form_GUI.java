package Health_System_Monitoring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Form_GUI {
    public static JFrame mainFrame;
    private static JPanel titlepanel = new JPanel();
    private static JButton editbutton = new JButton("edit");
    private static JPanel editingpanel = new JPanel();
    private static JScrollPane contentpanel = new JScrollPane();
    private static Vector<JButton> editButtonz = new Vector<JButton>();

    public static void prepareSCGUI() {
        JLabel titlelabel = new JLabel("title ");
        titlepanel.add(titlelabel);
        titlepanel.add(editbutton);

        for (FormType ft : FormType.values()) {
            if(ft!=FormType.FT_ERROR){
                String var = ft.toString();
                 JButton nButton = new JButton(var);
                 editButtonz.add(nButton);
                editingpanel.add(nButton);
                nButton.setActionCommand(var);
                nButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        FormType f=FormType.fromString(e.getActionCommand().toLowerCase());
                        insertItem(f);
                }
        });
            }
        }

        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(titlepanel,BorderLayout.NORTH);
        mainFrame.add(editingpanel,BorderLayout.EAST);
        mainFrame.add(contentpanel,BorderLayout.CENTER);
        mainFrame.setVisible(true);

    }



    public static void insertItem(FormType f){
        
    }
}
