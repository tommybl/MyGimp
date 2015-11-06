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
public class Binary implements IPlugin {
    @Override
    public BufferedImage perform(BufferedImage img) 
    {
            BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            long moy = 0;
            long nb = 0;
            
            for (int i = 0; i < img.getWidth(); i++)
                for (int j = 0; j < img.getHeight(); j++) {
                    moy += img.getRGB(i, j);
                    nb++;
                }
            
            moy = moy / nb;
            int a;

            for (int i = 0; i < img.getWidth(); i++)
                for (int j = 0; j < img.getHeight(); j++) {
                    a = (img.getRGB(i, j) >> 24) & 0xff;
                    if (img.getRGB(i, j) > moy)
                        res.setRGB(i, j, new Color(255,255,255,a).getRGB());
                    else
                        res.setRGB(i, j, new Color(0,0,0,a).getRGB());
                }
            return res;
    }

    @Override
    public String getName() 
    {
            return "Binary";
    }
}
