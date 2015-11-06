/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.image.BufferedImage;
import view.ImagePanel;

/**
 *
 * @author Tommy
 */
public class Originator {
    private String state;
    private ImagePanel img;
    
    public Memento saveToMemento(String st, ImagePanel imgsave, BufferedImage imgicon) {
        return new Memento(st, imgsave, imgicon); 
    }
    
    public void restoreFromMemento(Memento m) {
        state = m.getSavedState(); 
        img = m.getSavedImg();
    }
    
    public String getState() {
        return state;
    }
    
    public ImagePanel getImg() {
        return img;
    }
}
