package plugin.bonus;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
public class Rotate180 implements IPlugin {
    @Override
    public BufferedImage perform(BufferedImage img) 
    {
        BufferedImage res;
        AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
        tx.translate(-img.getWidth(null), -img.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        res = op.filter(img, null);

        return res;
    }

    @Override
    public String getName() 
    {
            return "Rotate 180";
    }
}
