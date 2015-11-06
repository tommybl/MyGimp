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
public class Grayscale implements IPlugin {
    @Override
    public BufferedImage perform(BufferedImage img) 
    {
            BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            int argb, grey, r, g, b, a;

            for (int i = 0; i < img.getWidth(); i++)
                    for (int j = 0; j < img.getHeight(); j++) {
                        argb = img.getRGB(i,j);
                        a = (argb >> 24) & 0xff;
                        r = (argb >> 16) & 0xff;
                        g = (argb >> 8) & 0xff;
                        b = (argb) & 0xff;
                        grey = (int)(0.299 * (double)r + 0.587 * (double)g + 0.114 * (double)b);
                        res.setRGB(i, j, new Color(grey,grey,grey,a).getRGB());
                    }
            return res;
    }

    @Override
    public String getName() 
    {
            return "Grayscale";
    }
}
