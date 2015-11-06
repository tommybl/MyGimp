package plugin.basic;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import plugin.IPlugin;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tommy
 */
public class RotateLeft implements IPlugin {
    @Override
    public BufferedImage perform(BufferedImage img) 
    {
        double rot = 90;
        double s = Math.sin(rot/(double)180*Math.PI);
        double c = Math.cos(rot/(double)180*Math.PI);
        int w = img.getWidth();
        int h = img.getHeight();
        int nw = Math.abs((int)Math.floor(w*c+h*s));
        int nh = Math.abs((int)Math.floor(h*c+w*s));
        float x0 = w/2;
        float y0 = h/2;
        BufferedImage res = new BufferedImage(nw, nh, img.getType());
        for (int i = 0; i < nw; i++)
            for (int j = 0; j < nh; j++) {
                int x = (int)Math.round(x0+((float)i-x0)*c-((float)j-y0)*s);
                int y = (int)Math.round(y0+((float)i-x0)*s-((float)j-y0)*c);
                if (x >= 0 && x < w && y >=0 && y < h)
                    res.setRGB(i, j, img.getRGB(x, y));
            }
      
        return res;
    }

    @Override
    public String getName() 
    {
            return "Rotate left";
    }
}
