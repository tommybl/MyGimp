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
public class Blury implements IPlugin {
    @Override
    public BufferedImage perform(BufferedImage img) 
    {
            BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            int w = img.getWidth();
            int h = img.getHeight();
            int[][] mat = new int[5][5];
            float[][] conv = 
            {{1,1,1,1,1},
             {1,1,1,1,1},
             {1,1,1,1,1},
             {1,1,1,1,1},
             {1,1,1,1,1}};
            
            long moy;
            int a, argb, grey, r, g, b, nargb, nr, ng, nb;
            
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
                    
                    nr = 0;
                    ng = 0;
                    nb = 0;
                    a = (img.getRGB(x, y) >> 24) & 0xff;
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            r = (mat[i][j] >> 16) & 0xff;
                            g = (mat[i][j] >> 8) & 0xff;
                            b = (mat[i][j]) & 0xff;
                            nr = (int)((float)nr + (float)r*conv[i][j]);
                            ng = (int)((float)ng + (float)g*conv[i][j]);
                            nb = (int)((float)nb + (float)b*conv[i][j]);
                        }
                    }
                    
                    nr = nr/25;
                    ng = ng/25;
                    nb = nb/25;
                    res.setRGB(x, y, new Color(nr,ng,nb,a).getRGB());
                }
            
            return res;
    }

    @Override
    public String getName() 
    {
            return "Blury";
    }
}
