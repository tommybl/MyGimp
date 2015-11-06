/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JSpinner;
import plugin.IPlugin;
import view.ImagePanel;

/**
 *
 * @author Tommy
 */
public class Model extends Observable {
    private ConcurrentHashMap<String,Class> classes = null;
    
    public Model(Observer o) {
        this.addObserver(o);
    }
    
    public void sendNotification(Object o) {
        setChanged();
        notifyObservers(o);
    }
    
    public void loadFilters() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        classes = JarLoader.loadClasses();
        this.sendNotification(classes);
    }
    
    public ConcurrentHashMap<String,Class> getClasses() {
        return classes;
    }
    
    public void zoomImg(JSpinner sp, ImagePanel img, float zoom) {
        img.setZoom(zoom);
        img.updateZoomImg();
        this.sendNotification(sp);
    }
    
    public void performFilter(String name) throws InstantiationException, IllegalAccessException {
        Class c = classes.get(name);
        IPlugin plug = (IPlugin)c.newInstance();
        this.sendNotification(plug);
    }
    
    public void fillImageColor(BufferedImage img, int argb) {
        for (int i = 0; i < img.getWidth(); i++)
            for (int j = 0; j < img.getHeight(); j++)
                img.setRGB(i, j, argb);
    }
    
    public void fillDetect(BufferedImage bufimg, int fillx, int filly, int argb, int nargb) {
        LinkedList<Point> points = new LinkedList();
        if (fillx >= 0 && fillx <= bufimg.getWidth()-1 &&
            filly >= 0 && filly <= bufimg.getHeight()-1)
            points.add(new Point(fillx,filly));
        while (!points.isEmpty()) {
            Point p = points.removeFirst();
            if (p.getX() >= 0 && p.getX() <= bufimg.getWidth()-1 &&
                p.getY() >= 0 && p.getY() <= bufimg.getHeight()-1 &&
                bufimg.getRGB((int)p.getX(), (int)p.getY()) == argb) {
                bufimg.setRGB((int)p.getX(), (int)p.getY(), nargb);
                points.addLast(new Point((int)p.getX(),(int)p.getY()-1));
                points.addLast(new Point((int)p.getX(),(int)p.getY()+1));
                points.addLast(new Point((int)p.getX()+1,(int)p.getY()));
                points.addLast(new Point((int)p.getX()-1,(int)p.getY()));
            }
        }
    }
}
