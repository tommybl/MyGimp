/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JTabbedPane;

/**
 *
 * @author Tommy
 */
public class MyTabbedPane extends JTabbedPane {
    private BufferedImage background = null;
    
    public MyTabbedPane() {
        File file = new File("src/images/background.png");
        try {
            background = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        BufferedImage img = new BufferedImage(this.getWidth()-4, this.getHeight()-4, background.getType());
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(background, 0, 0, this.getWidth()-4, this.getHeight()-4, null);
        g2d.setComposite(AlphaComposite.Src);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.dispose();
        g.drawImage(img, 2, 2, null);
        super.paintComponent(g);
    }
}
