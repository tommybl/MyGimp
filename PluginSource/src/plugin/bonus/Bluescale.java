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
public class Bluescale implements IPlugin {

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
                
                nr = (int)(0.393*(float)r+0.349*(float)g+0.272*(float)b);
                nr = (nr > 255) ? 255 : nr;
                
                ng = (int)(0.189*(float)r+0.168*(float)g+0.131*(float)b);
                ng = (ng > 255) ? 255 : ng;
                
                
                nb = (int)(0.769*(float)r+0.686*(float)g+0.534*(float)b);
                nb = (nb > 255) ? 255 : nb;
                res.setRGB(i, j, new Color(nr,ng,nb,a).getRGB());
            }
        return res;
    }

    @Override
    public String getName() {
        return "Bluescale";
    }
    
}
