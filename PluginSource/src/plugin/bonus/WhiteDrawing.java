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
public class WhiteDrawing implements IPlugin {
    @Override
    public BufferedImage perform(BufferedImage img) 
    {
            BufferedImage imggrey = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            int w = img.getWidth();
            int h = img.getHeight();
            int[][] mat = new int[5][5];
            float[][] conv = 
            {{0,0,0,0,0},
             {0,-1,-1,-1,0},
             {0,-1,8,1,0},
             {0,-1,-1,-1,0},
             {0,0,0,0,0}};
            
            long moy;
            int a, argb, grey, r, g, b, newgrey;

            for (int i = 0; i < img.getWidth(); i++)
                for (int j = 0; j < img.getHeight(); j++) {
                    argb = img.getRGB(i,j);
                    a = (argb >> 24) & 0xff;
                    r = (argb >> 16) & 0xff;
                    g = (argb >> 8) & 0xff;
                    b = (argb) & 0xff;
                    grey = (int)(0.299 * (double)r + 0.587 * (double)g + 0.114 * (double)b);
                    imggrey.setRGB(i, j, new Color(grey,grey,grey,a).getRGB());
                }
            
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
                    
                    newgrey = 0;
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            grey = mat[i][j];
                            grey = (grey >> 16) & 0xff;
                            newgrey = (int)((float)newgrey + (float)grey*conv[i][j]);
                        }
                    }
                    newgrey = (newgrey > 255) ? 255 : newgrey;
                    newgrey = (newgrey < 0) ? 0 : newgrey;
                    a = (img.getRGB(x, y) >> 24) & 0xff;
                    res.setRGB(x, y, new Color(newgrey,newgrey,newgrey,a).getRGB());
                }
            
            return res;
    }

    @Override
    public String getName() 
    {
            return "White drawing";
    }
}
