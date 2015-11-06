/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;

/**
 *
 * @author Tommy
 */
public class MyPreviewLabel extends JLabel {
    @Override
    public void paintComponent(Graphics g) {
        Color c1 = new Color(102,102,102,255);
        Color c2 = new Color(153,153,153,255);
        Color c3;
        g.setColor(c2);
        for (int i = 0; i < this.getHeight(); i+=10) {
            c3 = g.getColor();
            for (int j = 0; j < this.getWidth(); j+=10) {
                g.fillRect(j, i, 10, 10);
                if (g.getColor() == c2)
                    g.setColor(c1);
                else
                    g.setColor(c2);
            }
            if (c3 == c1)
                g.setColor(c2);
            else
                g.setColor(c1);
        }
        super.paintComponent(g);
    }
}
