/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import view.ImagePanel;

/**
 *
 * @author Tommy
 */
public class Memento implements Serializable {
    private String state;
    private ImagePanel img;
    private int[] iconpix;

   public Memento(String st, ImagePanel imgsave, BufferedImage imgicon) {
        state = st;
        img = imgsave;

        BufferedImage ico = new BufferedImage(50,50,BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = ico.createGraphics();       
        Color c1 = new Color(102,102,102,255);
        Color c2 = new Color(153,153,153,255);
        Color c3;
        g2d.setColor(c2);
        for (int i = 0; i < 50; i+=10) {
            c3 = g2d.getColor();
            for (int j = 0; j < 50; j+=10) {
                g2d.fillRect(j, i, 10, 10);
                if (g2d.getColor() == c2)
                    g2d.setColor(c1);
                else
                    g2d.setColor(c2);
            }
            if (c3 == c1)
                g2d.setColor(c2);
            else
                g2d.setColor(c1);
        }  
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawImage(imgicon,0,0, 50, 50, null);
        g2d.dispose();
       iconpix = ico.getRGB(0, 0, 50, 50, null, 0, 50);
   }
   
   public Memento(String st, ImagePanel imgsave, int[] pix) {
        state = st;
        img = imgsave;
        iconpix = pix;
   }
   
   public String getSavedState() {
       return state;
   }
   
   public ImagePanel getSavedImg() {
       return img;
   }
   
   public int[] getIconPix() {
       return iconpix;
   }
   
   public BufferedImage getIcon() {
       BufferedImage ico = new BufferedImage(50,50,BufferedImage.TYPE_4BYTE_ABGR);
       ico.setRGB(0, 0, 50, 50, iconpix, 0, 50);
       return ico;
   }
}
