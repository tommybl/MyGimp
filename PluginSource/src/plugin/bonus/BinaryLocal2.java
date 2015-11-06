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
public class BinaryLocal2 implements IPlugin {
    @Override
    public BufferedImage perform(BufferedImage img) 
    {
            BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            int w = img.getWidth();
            int h = img.getHeight();
            int [][] mat = new int[5][5];
            long moy;
            int a;
            
            for (int x = 0; x < w; x++)
                for (int y = 0; y < h; y++) {
                    if ((x-2) >= 0 && (x+2) <= (w-1) && (y-2) >= 0 && (y+2) <= (h-1))
                    {
                        mat[0][0] = img.getRGB(x-2,y-2);
                        mat[0][1] = img.getRGB(x-1,y-2);
                        mat[0][2] = img.getRGB(x+0,y-2);
                        mat[0][3] = img.getRGB(x+1,y-2);
                        mat[0][4] = img.getRGB(x+2,y-2);

                        mat[1][0] = img.getRGB(x-2,y-1);
                        mat[1][1] = img.getRGB(x-1,y-1);
                        mat[1][2] = img.getRGB(x+0,y-1);
                        mat[1][3] = img.getRGB(x+1,y-1);
                        mat[1][4] = img.getRGB(x+2,y-1);

                        mat[2][0] = img.getRGB(x-2,y+0);
                        mat[2][1] = img.getRGB(x-1,y+0);
                        mat[2][2] = img.getRGB(x+0,y+0);
                        mat[2][3] = img.getRGB(x+1,y+0);
                        mat[2][4] = img.getRGB(x+2,y+0);

                        mat[3][0] = img.getRGB(x-2,y+1);
                        mat[3][1] = img.getRGB(x-1,y+1);
                        mat[3][2] = img.getRGB(x+0,y+1);
                        mat[3][3] = img.getRGB(x+1,y+1);
                        mat[3][4] = img.getRGB(x+2,y+1);

                        mat[4][0] = img.getRGB(x-2,y+2);
                        mat[4][1] = img.getRGB(x-1,y+2);
                        mat[4][2] = img.getRGB(x+0,y+2);
                        mat[4][3] = img.getRGB(x+1,y+2);
                        mat[4][4] = img.getRGB(x+2,y+2);
                    }
                    else if ((x-2) < 0 && (x+2) <= (w-1) && (y-2) < 0 && (y+2) <= (h-1))
                    {
                        mat[0][0] = img.getRGB(x+2,y+2);
                        mat[0][1] = img.getRGB(x+1,y+2);
                        mat[0][2] = img.getRGB(x+0,y+2);
                        mat[0][3] = img.getRGB(x+1,y+2);
                        mat[0][4] = img.getRGB(x+2,y+2);

                        mat[1][0] = img.getRGB(x+2,y+1);
                        mat[1][1] = img.getRGB(x+1,y+1);
                        mat[1][2] = img.getRGB(x+0,y+1);
                        mat[1][3] = img.getRGB(x+1,y+1);
                        mat[1][4] = img.getRGB(x+2,y+1);

                        mat[2][0] = img.getRGB(x+2,y+0);
                        mat[2][1] = img.getRGB(x+1,y+0);
                        mat[2][2] = img.getRGB(x+0,y+0);
                        mat[2][3] = img.getRGB(x+1,y+0);
                        mat[2][4] = img.getRGB(x+2,y+0);

                        mat[3][0] = img.getRGB(x+2,y+1);
                        mat[3][1] = img.getRGB(x+1,y+1);
                        mat[3][2] = img.getRGB(x+0,y+1);
                        mat[3][3] = img.getRGB(x+1,y+1);
                        mat[3][4] = img.getRGB(x+2,y+1);

                        mat[4][0] = img.getRGB(x+2,y+2);
                        mat[4][1] = img.getRGB(x+1,y+2);
                        mat[4][2] = img.getRGB(x+0,y+2);
                        mat[4][3] = img.getRGB(x+1,y+2);
                        mat[4][4] = img.getRGB(x+2,y+2);
                    }
                    else if ((x-2) >= 0 && (x+2) > (w-1) && (y-2) >= 0 && (y+2) > (h-1))
                    {
                        mat[0][0] = img.getRGB(x-2,y-2);
                        mat[0][1] = img.getRGB(x-1,y-2);
                        mat[0][2] = img.getRGB(x-0,y-2);
                        mat[0][3] = img.getRGB(x-1,y-2);
                        mat[0][4] = img.getRGB(x-2,y-2);

                        mat[1][0] = img.getRGB(x-2,y-1);
                        mat[1][1] = img.getRGB(x-1,y-1);
                        mat[1][2] = img.getRGB(x-0,y-1);
                        mat[1][3] = img.getRGB(x-1,y-1);
                        mat[1][4] = img.getRGB(x-2,y-1);

                        mat[2][0] = img.getRGB(x-2,y-0);
                        mat[2][1] = img.getRGB(x-1,y-0);
                        mat[2][2] = img.getRGB(x-0,y-0);
                        mat[2][3] = img.getRGB(x-1,y-0);
                        mat[2][4] = img.getRGB(x-2,y-0);

                        mat[3][0] = img.getRGB(x-2,y-1);
                        mat[3][1] = img.getRGB(x-1,y-1);
                        mat[3][2] = img.getRGB(x-0,y-1);
                        mat[3][3] = img.getRGB(x-1,y-1);
                        mat[3][4] = img.getRGB(x-2,y-1);

                        mat[4][0] = img.getRGB(x-2,y-2);
                        mat[4][1] = img.getRGB(x-1,y-2);
                        mat[4][2] = img.getRGB(x-0,y-2);
                        mat[4][3] = img.getRGB(x-1,y-2);
                        mat[4][4] = img.getRGB(x-2,y-2);
                    }
                    else if ((x-2) < 0 && (x+2) <= (w-1) && (y-2) >= 0 && (y+2) > (h-1))
                    {
                        mat[0][0] = img.getRGB(x+2,y-2);
                        mat[0][1] = img.getRGB(x+1,y-2);
                        mat[0][2] = img.getRGB(x+0,y-2);
                        mat[0][3] = img.getRGB(x+1,y-2);
                        mat[0][4] = img.getRGB(x+2,y-2);

                        mat[1][0] = img.getRGB(x+2,y-1);
                        mat[1][1] = img.getRGB(x+1,y-1);
                        mat[1][2] = img.getRGB(x+0,y-1);
                        mat[1][3] = img.getRGB(x+1,y-1);
                        mat[1][4] = img.getRGB(x+2,y-1);

                        mat[2][0] = img.getRGB(x+2,y-0);
                        mat[2][1] = img.getRGB(x+1,y-0);
                        mat[2][2] = img.getRGB(x+0,y-0);
                        mat[2][3] = img.getRGB(x+1,y-0);
                        mat[2][4] = img.getRGB(x+2,y-0);

                        mat[3][0] = img.getRGB(x+2,y-1);
                        mat[3][1] = img.getRGB(x+1,y-1);
                        mat[3][2] = img.getRGB(x+0,y-1);
                        mat[3][3] = img.getRGB(x+1,y-1);
                        mat[3][4] = img.getRGB(x+2,y-1);

                        mat[4][0] = img.getRGB(x+2,y-2);
                        mat[4][1] = img.getRGB(x+1,y-2);
                        mat[4][2] = img.getRGB(x+0,y-2);
                        mat[4][3] = img.getRGB(x+1,y-2);
                        mat[4][4] = img.getRGB(x+2,y-2);
                    }
                    else if ((x-2) >= 0 && (x+2) > (w-1) && (y-2) < 0 && (y+2) <= (h-1))
                    {
                        mat[0][0] = img.getRGB(x-2,y+2);
                        mat[0][1] = img.getRGB(x-1,y+2);
                        mat[0][2] = img.getRGB(x-0,y+2);
                        mat[0][3] = img.getRGB(x-1,y+2);
                        mat[0][4] = img.getRGB(x-2,y+2);

                        mat[1][0] = img.getRGB(x-2,y+1);
                        mat[1][1] = img.getRGB(x-1,y+1);
                        mat[1][2] = img.getRGB(x-0,y+1);
                        mat[1][3] = img.getRGB(x-1,y+1);
                        mat[1][4] = img.getRGB(x-2,y+1);

                        mat[2][0] = img.getRGB(x-2,y+0);
                        mat[2][1] = img.getRGB(x-1,y+0);
                        mat[2][2] = img.getRGB(x-0,y+0);
                        mat[2][3] = img.getRGB(x-1,y+0);
                        mat[2][4] = img.getRGB(x-2,y+0);

                        mat[3][0] = img.getRGB(x-2,y+1);
                        mat[3][1] = img.getRGB(x-1,y+1);
                        mat[3][2] = img.getRGB(x-0,y+1);
                        mat[3][3] = img.getRGB(x-1,y+1);
                        mat[3][4] = img.getRGB(x-2,y+1);

                        mat[4][0] = img.getRGB(x-2,y+2);
                        mat[4][1] = img.getRGB(x-1,y+2);
                        mat[4][2] = img.getRGB(x-0,y+2);
                        mat[4][3] = img.getRGB(x-1,y+2);
                        mat[4][4] = img.getRGB(x-2,y+2);
                    }
                    else if ((x-2) < 0 && (x+2) <= (w-1) && (y-2) >= 0 && (y+2) <= (h-1))
                    {
                        mat[0][0] = img.getRGB(x+2,y-2);
                        mat[0][1] = img.getRGB(x+1,y-2);
                        mat[0][2] = img.getRGB(x+0,y-2);
                        mat[0][3] = img.getRGB(x+1,y-2);
                        mat[0][4] = img.getRGB(x+2,y-2);

                        mat[1][0] = img.getRGB(x+2,y-1);
                        mat[1][1] = img.getRGB(x+1,y-1);
                        mat[1][2] = img.getRGB(x+0,y-1);
                        mat[1][3] = img.getRGB(x+1,y-1);
                        mat[1][4] = img.getRGB(x+2,y-1);

                        mat[2][0] = img.getRGB(x+2,y+0);
                        mat[2][1] = img.getRGB(x+1,y+0);
                        mat[2][2] = img.getRGB(x+0,y+0);
                        mat[2][3] = img.getRGB(x+1,y+0);
                        mat[2][4] = img.getRGB(x+2,y+0);

                        mat[3][0] = img.getRGB(x+2,y+1);
                        mat[3][1] = img.getRGB(x+1,y+1);
                        mat[3][2] = img.getRGB(x+0,y+1);
                        mat[3][3] = img.getRGB(x+1,y+1);
                        mat[3][4] = img.getRGB(x+2,y+1);

                        mat[4][0] = img.getRGB(x+2,y+2);
                        mat[4][1] = img.getRGB(x+1,y+2);
                        mat[4][2] = img.getRGB(x+0,y+2);
                        mat[4][3] = img.getRGB(x+1,y+2);
                        mat[4][4] = img.getRGB(x+2,y+2);
                    }
                    else if ((x-2) >= 0 && (x+2) > (w-1) && (y-2) >= 0 && (y+2) <= (h-1))
                    {
                        mat[0][0] = img.getRGB(x-2,y-2);
                        mat[0][1] = img.getRGB(x-1,y-2);
                        mat[0][2] = img.getRGB(x-0,y-2);
                        mat[0][3] = img.getRGB(x-1,y-2);
                        mat[0][4] = img.getRGB(x-2,y-2);

                        mat[1][0] = img.getRGB(x-2,y-1);
                        mat[1][1] = img.getRGB(x-1,y-1);
                        mat[1][2] = img.getRGB(x-0,y-1);
                        mat[1][3] = img.getRGB(x-1,y-1);
                        mat[1][4] = img.getRGB(x-2,y-1);

                        mat[2][0] = img.getRGB(x-2,y+0);
                        mat[2][1] = img.getRGB(x-1,y+0);
                        mat[2][2] = img.getRGB(x-0,y+0);
                        mat[2][3] = img.getRGB(x-1,y+0);
                        mat[2][4] = img.getRGB(x-2,y+0);

                        mat[3][0] = img.getRGB(x-2,y+1);
                        mat[3][1] = img.getRGB(x-1,y+1);
                        mat[3][2] = img.getRGB(x-0,y+1);
                        mat[3][3] = img.getRGB(x-1,y+1);
                        mat[3][4] = img.getRGB(x-2,y+1);

                        mat[4][0] = img.getRGB(x-2,y+2);
                        mat[4][1] = img.getRGB(x-1,y+2);
                        mat[4][2] = img.getRGB(x-0,y+2);
                        mat[4][3] = img.getRGB(x-1,y+2);
                        mat[4][4] = img.getRGB(x-2,y+2);
                    }
                    else if ((x-2) >= 0 && (x+2) <= (w-1) && (y-2) < 0 && (y+2) <= (h-1))
                    {
                        mat[0][0] = img.getRGB(x-2,y+2);
                        mat[0][1] = img.getRGB(x-1,y+2);
                        mat[0][2] = img.getRGB(x+0,y+2);
                        mat[0][3] = img.getRGB(x+1,y+2);
                        mat[0][4] = img.getRGB(x+2,y+2);

                        mat[1][0] = img.getRGB(x-2,y+1);
                        mat[1][1] = img.getRGB(x-1,y+1);
                        mat[1][2] = img.getRGB(x+0,y+1);
                        mat[1][3] = img.getRGB(x+1,y+1);
                        mat[1][4] = img.getRGB(x+2,y+1);

                        mat[2][0] = img.getRGB(x-2,y+0);
                        mat[2][1] = img.getRGB(x-1,y+0);
                        mat[2][2] = img.getRGB(x+0,y+0);
                        mat[2][3] = img.getRGB(x+1,y+0);
                        mat[2][4] = img.getRGB(x+2,y+0);

                        mat[3][0] = img.getRGB(x-2,y+1);
                        mat[3][1] = img.getRGB(x-1,y+1);
                        mat[3][2] = img.getRGB(x+0,y+1);
                        mat[3][3] = img.getRGB(x+1,y+1);
                        mat[3][4] = img.getRGB(x+2,y+1);

                        mat[4][0] = img.getRGB(x-2,y+2);
                        mat[4][1] = img.getRGB(x-1,y+2);
                        mat[4][2] = img.getRGB(x+0,y+2);
                        mat[4][3] = img.getRGB(x+1,y+2);
                        mat[4][4] = img.getRGB(x+2,y+2);
                    }
                    else
                    {
                        mat[0][0] = img.getRGB(x-2,y-2);
                        mat[0][1] = img.getRGB(x-1,y-2);
                        mat[0][2] = img.getRGB(x+0,y-2);
                        mat[0][3] = img.getRGB(x+1,y-2);
                        mat[0][4] = img.getRGB(x+2,y-2);

                        mat[1][0] = img.getRGB(x-2,y-1);
                        mat[1][1] = img.getRGB(x-1,y-1);
                        mat[1][2] = img.getRGB(x+0,y-1);
                        mat[1][3] = img.getRGB(x+1,y-1);
                        mat[1][4] = img.getRGB(x+2,y-1);

                        mat[2][0] = img.getRGB(x-2,y-0);
                        mat[2][1] = img.getRGB(x-1,y-0);
                        mat[2][2] = img.getRGB(x+0,y-0);
                        mat[2][3] = img.getRGB(x+1,y-0);
                        mat[2][4] = img.getRGB(x+2,y-0);

                        mat[3][0] = img.getRGB(x-2,y-1);
                        mat[3][1] = img.getRGB(x-1,y-1);
                        mat[3][2] = img.getRGB(x+0,y-1);
                        mat[3][3] = img.getRGB(x+1,y-1);
                        mat[3][4] = img.getRGB(x+2,y-1);

                        mat[4][0] = img.getRGB(x-2,y-2);
                        mat[4][1] = img.getRGB(x-1,y-2);
                        mat[4][2] = img.getRGB(x+0,y-2);
                        mat[4][3] = img.getRGB(x+1,y-2);
                        mat[4][4] = img.getRGB(x+2,y-2);
                    }
                    
                    moy = 0;
                    for (int i = 1; i < 4; i++)
                        for (int j = 1; j < 4; j++)
                            moy += mat[i][j];
                    moy = moy / 9;
                    
                    a = (img.getRGB(x, y) >> 24) & 0xff;
                    if (img.getRGB(x, y) > moy)
                        res.setRGB(x, y, new Color(255,255,255,a).getRGB());
                    else
                        res.setRGB(x, y, new Color(0,0,0,a).getRGB());
                }
            
            return res;
    }

    @Override
    public String getName() 
    {
            return "Binary local 3x3";
    }
}
