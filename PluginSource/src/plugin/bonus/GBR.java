package plugin.bonus;

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
public class GBR implements IPlugin {

    @Override
    public BufferedImage perform(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        int argb, grey, r, g, b, a, nr, ng, nb;

        for (int i = 0; i < img.getWidth(); i++)
            for (int j = 0; j < img.getHeight(); j++) {
                argb = img.getRGB(i,j);
                a = (argb >> 24) & 0xff;
                r = (argb >> 16) & 0xff;
                g = (argb >> 8) & 0xff;
                b = (argb) & 0xff;
                nr = g;
                ng = b;
                nb = r;
                res.setRGB(i, j, new Color(nr,ng,nb,a).getRGB());
            }
        return res;
    }

    @Override
    public String getName() {
        return "GBR";
    }
    
}
