package plugin.bonus;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import plugin.IPlugin;

public class VeryLong implements IPlugin {

	@Override
	public BufferedImage perform(BufferedImage img) 
	{
		BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		int argb, a, r, g, b;
                
		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++)
                        {
                            argb = img.getRGB(i, j);
                            a = (argb >> 24) & 0xff;
                            r = Math.abs(new Random().nextInt()%256);
                            g = Math.abs(new Random().nextInt()%256);
                            b = Math.abs(new Random().nextInt()%256);
                            res.setRGB(i, j, new Color(r, g, b, a).getRGB());
                            try 
                            {
                                    Thread.sleep(1);
                            } catch (InterruptedException e)
                            {
                                    e.printStackTrace();
                            }
                        }
		
		return res;
	}

	@Override
	public String getName() 
	{
		return "Very long";
	}

}
