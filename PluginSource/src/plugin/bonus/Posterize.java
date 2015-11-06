package plugin.bonus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import plugin.IPlugin;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tommy
 */
public class Posterize implements IPlugin, ActionListener, ChangeListener {
    private PosterizeView pv;
    private BufferedImage img;
    private BufferedImage res;
    private boolean keep = true;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pv.getBvalid()) {
            pv.setVisible(false);
            keep = false;
        }
        else if (e.getSource() == pv.getBcancel()) {
            pv.setVisible(false);
            res = img;
            keep = false;
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == pv.getSLlevel() && pv.getLlevel() != null) {
            int level = pv.getSLlevel().getValue();
            pv.getLlevel().setText(String.valueOf(level));
            performPreview(level);
        }
    }
    
    public void performPreview(int level) {
        float nbareas = 256/(float)level;
        float nbvalues = 255/(float)(level-1);
        int argb, a, r, g, b, ra, ga, ba, rn, gn, bn;
        
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                argb = img.getRGB(x,y);
                a = (argb >> 24) & 0xff;
                r = (argb >> 16) & 0xff;
                g = (argb >> 8) & 0xff;
                b = (argb) & 0xff;
                
                ra = (int)((float)r/nbareas);
                rn = (int)(nbvalues*(float)ra);
                
                ga = (int)((float)g/nbareas);
                gn = (int)(nbvalues*(float)ga);
                
                ba = (int)((float)b/nbareas);
                bn = (int)(nbvalues*(float)ba);
                
                res.setRGB(x, y, new Color(rn,gn,bn,a).getRGB());
            }
        }
        
        pv.getLpreview().setIcon(new ImageIcon(res));
    }
    
    static public class MyLabel extends JLabel {
        @Override
        public void paintComponent(Graphics g) {
            Color c1 = new Color(102,102,102,255);
            Color c2 = new Color(153,153,153,255);
            Color c3;
            g.setColor(c2);
            for (int i = 0; i < this.getHeight(); i+=10) {
                c3 = g.getColor();
                for (int j = 0; j < this.getWidth(); j+=10) {
                    g.fillRect(j, i, 10, 10);
                    if (g.getColor() == c2)
                        g.setColor(c1);
                    else
                        g.setColor(c2);
                }
                if (c3 == c1)
                    g.setColor(c2);
                else
                    g.setColor(c1);
            }
            super.paintComponent(g);
        }
    }
    
    static public class PosterizeView extends JDialog {
        private JSlider SLlevel;
        private JLabel Llevel = null;
        private MyLabel Lpreview;
        private JButton Bvalid;
        private JButton Bcancel;
        
        public JSlider getSLlevel() {
            return SLlevel;
        }
        
        public void setSLlevel(JSlider sl) {
            SLlevel = sl;
        }
        
        public JLabel getLlevel() {
            return Llevel;
        }
        
        public void setLlevel(JLabel l) {
            Llevel = l;
        }
        
        public MyLabel getLpreview() {
            return Lpreview;
        }
        
        public void setLpreview(MyLabel l) {
            Lpreview = l;
        }
        
        public JButton getBvalid() {
            return Bvalid;
        }
        
        public void setBvalid(JButton but) {
            Bvalid = but;
        }
        
        public JButton getBcancel() {
            return Bcancel;
        }
        
        public void setBcancel(JButton but) {
            Bcancel = but;
        }
    }
    
    @Override
    public BufferedImage perform(BufferedImage imge) 
    {
        img = imge;
        res = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        res.createGraphics().drawImage(img, 0, 0, null);
        pv = new PosterizeView();
        pv.setTitle("Posterize - MyPhotoshop");
        JPanel pan = new JPanel();
        pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
        JPanel pan1 = new JPanel();
        pan1.setLayout(new BoxLayout(pan1, BoxLayout.LINE_AXIS));
        JPanel pan2 = new JPanel();
        pan2.setLayout(new BoxLayout(pan2, BoxLayout.LINE_AXIS));
        JPanel pan3 = new JPanel();
        pan3.setLayout(new BoxLayout(pan3, BoxLayout.LINE_AXIS));
        JSlider SLlevel = new JSlider();
        SLlevel.addChangeListener(this);
        pv.setSLlevel(SLlevel);
        SLlevel.setMinimum(2);
        SLlevel.setMaximum(255);
        SLlevel.setValue(255);
        SLlevel.setMaximumSize(new Dimension(400,20));
        JLabel Llevel = new JLabel("255");
        pv.setLlevel(Llevel);
        Llevel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        Llevel.setHorizontalAlignment(JLabel.CENTER);
        pan1.add(SLlevel);
        pan1.add(Box.createRigidArea(new Dimension(5,0)));
        pan1.add(Llevel);
        MyLabel Lpreview = new MyLabel();
        pv.setLpreview(Lpreview);
        Lpreview.setIcon(new ImageIcon(res));
        Lpreview.setHorizontalAlignment(JLabel.CENTER);
        Lpreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pan2.add(Lpreview);
        JButton Bvalid = new JButton("Execute");
        JButton Bcancel = new JButton("Cancel");
        Bvalid.addActionListener(this);
        Bcancel.addActionListener(this);
        pv.setBvalid(Bvalid);
        pv.setBcancel(Bcancel);
        Bvalid.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        Bcancel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        pan3.add(Bvalid);
        pan3.add(Box.createRigidArea(new Dimension(10,0)));
        pan3.add(Bcancel);
        pan.add(Box.createRigidArea(new Dimension(0,10)));
        pan.add(pan1);
        pan.add(Box.createRigidArea(new Dimension(0,10)));
        pan.add(pan2);
        pan.add(Box.createRigidArea(new Dimension(0,10)));
        pan.add(pan3);
        pan.add(Box.createRigidArea(new Dimension(0,10)));
        pv.add(pan);
        pv.setMinimumSize(new Dimension(600,400));
        pv.setVisible(true);
        
        while (keep) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(Posterize.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return res;
    }
    
    @Override
    public String getName() 
    {
            return "Posterize";
    }
}
