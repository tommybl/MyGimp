/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JOptionPane;

/**
 *
 * @author Tommy
 */
public class PrintImage implements Runnable {

    private BufferedImage img;

    public PrintImage(BufferedImage image) {
        img = image;
    }

    @Override
    public void run() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new ImagePrintable(pj,img));
        if (pj.printDialog()) {
            try {
                pj.print();
            } catch (PrinterException prt) {
                (new JOptionPane()).showMessageDialog(null, "Couldnt print the image on the selected printer", "Printing Error - MyPhotoshop", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class ImagePrintable implements Printable {
        private double x;
        private double y;
        private double w;
        private int orient;
        private BufferedImage img;

        public ImagePrintable(PrinterJob printJob, BufferedImage image) {
            PageFormat pageFormat = printJob.defaultPage();
            x = pageFormat.getImageableX();
            y = pageFormat.getImageableY();
            w = pageFormat.getImageableWidth();
            orient = pageFormat.getOrientation();
            img = image;
        }

        @Override
        public int print(Graphics g, PageFormat pf, int pi)
                throws PrinterException {
            if (pi == 0) {
                int pw = 0;
                int ph = 0;
                if (orient == PageFormat.PORTRAIT) {
                    pw = (int)Math.min(w, (double)img.getWidth());
                    ph = pw*img.getHeight()/img.getWidth();
                } else {
                    ph = (int)Math.min(w, (double)img.getHeight());
                    pw = ph*img.getWidth()/img.getHeight();
                }
                g.drawImage(img, (int)x, (int)y, pw, ph, null);
                return PAGE_EXISTS;
            } else {
                return NO_SUCH_PAGE;
            }
        }
    }
}
