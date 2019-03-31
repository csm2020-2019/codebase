package Health_System_Monitoring;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.*;

public class Printer implements Printable {

    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        g.drawString("Hello World", 100, 100);

        return PAGE_EXISTS;
    }

    public void actionPerformed(ActionEvent e) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if(ok) {
            try {
                job.print();
            } catch (PrinterException ex){
                System.out.println(ex);
            }
        }
    }
}
