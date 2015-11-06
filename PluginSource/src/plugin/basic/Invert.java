package plugin.basic;

import java.awt.Color;
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
public class Invert implements IPlugin {
    @Override
    public BufferedImage perform(BufferedImage img) 
    {
            BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            int argb, a, r, g, b;
            for (int i = 0; i < img.getWidth(); i++)
                    for (int j = 0; j < img.getHeight(); j++) {
                        argb = img.getRGB(i, j);
                        a = (argb >> 24) & 0xff;
                        r = (argb >> 16) & 0xff;
                        g = (argb >> 8) & 0xff;
                        b = (argb) & 0xff;
                        res.setRGB(i, j, new Color(255-r,255-g,255-b,a).getRGB());
                    }
            return res;
    }

    @Override
    public String getName() 
    {
            return "Invert";
    }
}
