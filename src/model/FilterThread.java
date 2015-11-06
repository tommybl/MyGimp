/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import plugin.IPlugin;
import view.ImagePanel;
import view.MainView;

/**
 *
 * @author Tommy
 */
public class FilterThread implements Runnable {
    private ImagePanel img;
    private IPlugin plug;
    private JButton but;
    private MainView mainview;

    public FilterThread(ImagePanel im, IPlugin pl, JButton b, MainView mv) {
        img = im;
        plug = pl;
        but = b;
        mainview = mv;
    }
    
    @Override
    public void run() {
        but.setEnabled(true);
        img.getParent().setCursor(new Cursor(Cursor.WAIT_CURSOR));
        BufferedImage newimg = plug.perform(img.getLayers().get(img.getIndexSelect()).getImage());
        img.setPreferredSize(new Dimension(newimg.getWidth() + 4, newimg.getHeight() + 4));
        img.setSize(new Dimension(newimg.getWidth() + 4, newimg.getHeight() + 4));
        img.getLayers().get(img.getIndexSelect()).setImage(newimg);
        img.updateZoomImg();
        img.repaint();
        img.getParent().getParent().validate();
        img.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        img.setThread(null);
        but.setEnabled(false);
        img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Apply filter "+plug.getName()+ " on "+img.getLayers().get(img.getIndexSelect()).getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
        mainview.getController().updateHist();
        mainview.getController().updateLayers();
        mainview.updateWindow();
    }
    
}
