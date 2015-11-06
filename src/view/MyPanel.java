/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Tommy
 */
public class MyPanel extends JPanel {
    private boolean hasrect = false;
    private JButton Bclose;
    private JLabel title;
    private ImagePanel img;
    
    public MyPanel(ImagePanel imgp) {
        img = imgp;
    }
    
    public void setRect() {
        hasrect = true;
    }
    
    public void clearRect() {
        hasrect = false;
        this.repaint();
    }
    
    public void setBclose(JButton but) {
        Bclose = but;
    }
    
    public JButton getBclose() {
        return Bclose;
    }
    
    public JLabel getTitle() {
        return title;
    }
    
    public void setTitle(JLabel label) {
        title = label;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        /*if (hasrect) {
            int w = (int)((float)img.getImage().getWidth()*img.getZoom()/100)+4;
            int h = (int)((float)img.getImage().getHeight()*img.getZoom()/100)+4;
            int x = this.getWidth()/2 - w/2;
            int y = this.getHeight()/2 - h/2;
            g.drawRect(x, y, w, h);
            g.drawRect(x+1, y+1, w-2, h-2);
        }*/
    }
}
