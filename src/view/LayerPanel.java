/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.JPanel;

/**
 *
 * @author Tommy
 */
public class LayerPanel extends JPanel implements Serializable {
    private int localx;
    private int localy;
    private int localw;
    private int localh;
    private transient BufferedImage image;
    private int[] pixels;
    private String fileName;
    private boolean isshowed = true;
    
    public LayerPanel(BufferedImage img, String n, int lx, int ly) {
        image = img;
        fileName = n;
        localx = lx;
        localy = ly;
    }
    
    public void resetDim() {
        localw = image.getWidth();
        localh = image.getHeight();
    }
    
    public void setIsShowed(boolean b) {
        isshowed = b;
    }
    
    public boolean getIsShowed() {
        return isshowed;
    }
    
    public LayerPanel getLayerPanelCopy() {
        LayerPanel laycpy = new LayerPanel(image,fileName,localx,localy);
        laycpy.setIsShowed(isshowed);
        laycpy.ImageToPixels();
        return laycpy;
    }
    
    public LayerPanel getLaySavedCopy() {
        BufferedImage layimg = new BufferedImage(localw, localh, BufferedImage.TYPE_4BYTE_ABGR);
        layimg.setRGB(0, 0, localw, localh, pixels, 0, localw);
        LayerPanel laycpy = new LayerPanel(layimg,fileName,localx,localy);
        laycpy.setIsShowed(isshowed);
        return laycpy;
    }
    
    public void ImageToPixels() {
        localw = image.getWidth();
        localh = image.getHeight();
        pixels = image.getRGB(0, 0, localw, localh, null, 0, localw);
    }
    
    public void PixelsToImage() {
        image = new BufferedImage(localw, localh, BufferedImage.TYPE_4BYTE_ABGR);
        image.setRGB(0, 0, localw, localh, pixels, 0, localw);
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public void setImage(BufferedImage img) {
        image = img;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String n) {
        fileName = n;
    }
    
    public int getLocalX() {
        return localx;
    }
    
    public void setLocalX(int lx) {
        localx = lx;
    }
    
    public int getLocalY() {
        return localy;
    }
    
    public void setLocalY(int ly) {
        localy = ly;
    }
    
    public void incrLocalX() {
        localx++;
    }
    
    public void decrLocalX() {
        localx--;
    }
    
    public void incrLocalY() {
        localy++;
    }
    
    public void decrLocalY() {
        localy--;
    }
    
    public int getLocalW() {
        return localw;
    }
    
    public int getLocalH() {
        return localh;
    }
    
    public BufferedImage getLayIcon() {
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
        g2d.drawImage(image,0,0, 50, 50, null);
        g2d.dispose();
        return ico;
    }
}
