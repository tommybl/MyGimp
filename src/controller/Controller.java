/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.CareTaker;
import model.Memento;
import model.Model;
import model.Originator;
import view.ImagePanel;
import view.LayerPanel;
import view.MainView;
import view.MyFileFilter;
import view.MyPanel;
import view.MyTabbedPane;
import view.PrintImage;

/**
 *
 * @author Tommy
 */
public class Controller implements ActionListener, MouseListener, ChangeListener, WindowListener, KeyListener, ListSelectionListener, MouseMotionListener, MouseWheelListener {
    private MainView mainview;
    private Model model;
    private final JFileChooser fc;
    private boolean newproj = true;
    private JToggleButton btool = null;
    private Font textfont = new Font("Times New Roman", Font.PLAIN, 12);
    private Color col = Color.BLACK;
    private String textadd = "";
    private Originator originator = new Originator();
    private boolean moving = false;
    private boolean hasdraw = false;
    private boolean haspaint = false;
    private boolean hasclear = false;
    private boolean hasrect = false;
    private boolean hasoval = false;
    private boolean hasline = false;
    private boolean hasfill = false;
    private boolean hascut = false;
    private boolean hasselect = false;
    private boolean haspipette = false;
    private boolean hasstamp = false;
    private BufferedImage imgselect = null;
    private int prevperc = 100;
    
    public Controller(MainView mv) {
        mainview = mv;
        model = new Model(mainview);
        fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new MyFileFilter());
    }
    
    public void addObserver(Observer o) {
        model.addObserver(o);
    }
    
    public BufferedImage getImgSelect() {
        return imgselect;
    }
    
    public MainView getMainView() {
        return mainview;
    }
    
    public boolean getHasdraw() {
        return hasdraw;
    }
    
    public boolean getHasstamp() {
        return hasstamp;
    }
    
    public boolean getHaspaint() {
        return haspaint;
    }
    
    public boolean getHasclear() {
        return hasclear;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().getClass() == JMenuItem.class) {
            JMenuItem mi = (JMenuItem)e.getSource();
            JPopupMenu pm = (JPopupMenu)mi.getParent();
            JMenu menu = (JMenu)pm.getInvoker();
            boolean compress = false;
            if (mi == mainview.getMenuopen())
                try {
                this.openProject();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            else if (mi == mainview.getMIopenlayer())
                this.openLayer();
            else if (menu == mainview.getMenufilters()) {
                try {
                    model.performFilter(mi.getText());
                } catch (InstantiationException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (mi == mainview.getMItools()) {
                mainview.getToolsView().setVisible(true);
            }
            else if (mi == mainview.getMIlayers()) {
                mainview.getLaysView().setVisible(true);
                mainview.getLaysView().getTPlayshist().setSelectedIndex(0);
            }
            else if (mi == mainview.getMIhistory()) {
                mainview.getLaysView().setVisible(true);
                mainview.getLaysView().getTPlayshist().setSelectedIndex(1);
            }
            else if (mi == mainview.getMIsave()) {
                fc.setCurrentDirectory(new File(mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).getFilePath()));
                BufferedImage imgb = mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).getBuiltImage();
                BufferedImage imgsave = imgb;
                int ret = fc.showSaveDialog(mainview);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    String filename = f.getPath();
                    String name = f.getName();
                    String ext = "png";
                    if (filename.toLowerCase().endsWith(".png")) {
                        ext = "PNG";
                    }
                    else if (filename.toLowerCase().endsWith(".jpg")) {
                        ext = "JPG";
                        imgsave = new BufferedImage(imgb.getWidth(), imgb.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
                        Graphics2D g2d = imgsave.createGraphics();
                        g2d.drawImage(imgb, 0, 0, imgb.getWidth(), imgb.getHeight(), null);
                    }
                    else if (filename.toLowerCase().endsWith(".gif")) {
                        ext = "GIF";
                    }
                    else if (filename.toLowerCase().endsWith(".bmp")) {
                        ext = "BMP";
                        imgsave = new BufferedImage(imgb.getWidth(), imgb.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
                        Graphics2D g2d = imgsave.createGraphics();
                        g2d.drawImage(imgb, 0, 0, imgb.getWidth(), imgb.getHeight(), null);
                    }
                    else if (filename.toLowerCase().endsWith(".mypsd"))
                        ext = "MYPSD";
                    else if (filename.toLowerCase().endsWith(".mypsd.gz")) {
                        ext = "GZ";
                        compress = true;
                    }
                    else {
                        filename = filename + ".png";
                    }
                   
                    if (ext.compareTo("MYPSD") == 0) {
                        ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                        ImagePanel imgsav;
                        File file = new File(filename);
                        try {
                            ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(file));
                            oos.writeObject(img.getFileName());
                            oos.writeObject(img.getFilePath());
                            oos.writeObject(img.getZoom());
                            oos.writeObject(img.getImgW());
                            oos.writeObject(img.getImgH());
                            oos.writeObject(img.getNbLays());
                            oos.writeObject(img.getIndexSelect());
                            img.buildLayersPix();
                            oos.writeObject(img.getLayers());
                            oos.writeObject(img.getCareTaker().getStates().size());
                            for (Memento mem : img.getCareTaker().getStates()) {
                                oos.writeObject(mem.getSavedState());
                                oos.writeObject(mem.getIconPix());
                                imgsav = mem.getSavedImg();
                                oos.writeObject(imgsav.getFileName());
                                oos.writeObject(imgsav.getFilePath());
                                oos.writeObject(imgsav.getZoom());
                                oos.writeObject(imgsav.getImgW());
                                oos.writeObject(imgsav.getImgH());
                                oos.writeObject(imgsav.getNbLays());
                                oos.writeObject(imgsav.getIndexSelect());
                                oos.writeObject(imgsav.getLayers());
                            }
                            oos.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    else if (ext.compareTo("GZ") == 0) {
                        ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                        ImagePanel imgsav;
                        FileOutputStream fos;
                        GZIPOutputStream gos;
                        ObjectOutputStream oos;
                        try {
                            fos = new FileOutputStream(filename);
                            gos = new GZIPOutputStream(fos);
                            oos = new ObjectOutputStream(gos);
                            oos.writeObject(img.getFileName());
                            oos.writeObject(img.getFilePath());
                            oos.writeObject(img.getZoom());
                            oos.writeObject(img.getImgW());
                            oos.writeObject(img.getImgH());
                            oos.writeObject(img.getNbLays());
                            oos.writeObject(img.getIndexSelect());
                            img.buildLayersPix();
                            oos.writeObject(img.getLayers());
                            oos.writeObject(img.getCareTaker().getStates().size());
                            for (Memento mem : img.getCareTaker().getStates()) {
                                oos.writeObject(mem.getSavedState());
                                oos.writeObject(mem.getIconPix());
                                imgsav = mem.getSavedImg();
                                oos.writeObject(imgsav.getFileName());
                                oos.writeObject(imgsav.getFilePath());
                                oos.writeObject(imgsav.getZoom());
                                oos.writeObject(imgsav.getImgW());
                                oos.writeObject(imgsav.getImgH());
                                oos.writeObject(imgsav.getNbLays());
                                oos.writeObject(imgsav.getIndexSelect());
                                oos.writeObject(imgsav.getLayers());
                            }
                            oos.flush();
                            oos.close();
                            gos.close();
                            fos.close();
                        } catch(IOException ioe) {
                                ioe.printStackTrace();
                        }
                    }
                    else {
                        File outputf = new File(filename);
                        try {
                            ImageIO.write(imgsave, ext, outputf);
                        } catch (IOException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                    img.setFileName(name);
                    ((MyPanel)img.getParent()).getTitle().setText(name);
                    mainview.updateWindow();
                }
            }
            else if (mi == mainview.getMIcopy() && mainview.getTabs().getSelectedIndex() != -1) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                LayerPanel lay = img.getLayers().get(img.getIndexSelect());
                BufferedImage layimg = lay.getImage();
                int ix = (int)((float)img.getInitX()*100/img.getZoom())-lay.getLocalX();
                int fx = (int)((float)img.getCursorX()*100/img.getZoom())-lay.getLocalX();
                int iy = (int)((float)img.getInitY()*100/img.getZoom())-lay.getLocalY();
                int fy = (int)((float)img.getCursorY()*100/img.getZoom())-lay.getLocalY();
                if (ix > fx) {
                    int tmp = fx;
                    fx = ix;
                    ix = tmp;
                }
                if (iy > fy) {
                    int tmp = fy;
                    fy = iy;
                    iy = tmp;
                }
                if (fx > 0 && fy > 0 && ix < layimg.getWidth()-1 && iy < layimg.getHeight()-1) {
                    ix = (ix < 0) ? 0 : ix;
                    iy = (iy < 0) ? 0 : iy;
                    fx = (fx > layimg.getWidth()-1) ? layimg.getWidth()-1 : fx;
                    fy = (fy > layimg.getHeight()-1) ? layimg.getHeight()-1 : fy;
                    int w = Math.abs(fx-ix);
                    int h = Math.abs(fy-iy);
                    imgselect = new BufferedImage(w,h,BufferedImage.TYPE_4BYTE_ABGR);
                    for (int y = 0; y < h; y++) {
                        for (int x = 0; x < w; x++) {
                            imgselect.setRGB(x, y, layimg.getRGB(ix+x, iy+y));
                        }
                    }
                    img.setHasSelect(false);
                    img.repaint();
                    mainview.getToolsView().getLtoolPreview().setIcon(new ImageIcon(imgselect));
                }
            }else if (mi == mainview.getMIcut() && mainview.getTabs().getSelectedIndex() != -1) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                LayerPanel lay = img.getLayers().get(img.getIndexSelect());
                BufferedImage layimg = lay.getImage();
                int ix = (int)((float)img.getInitX()*100/img.getZoom())-lay.getLocalX();
                int fx = (int)((float)img.getCursorX()*100/img.getZoom())-lay.getLocalX();
                int iy = (int)((float)img.getInitY()*100/img.getZoom())-lay.getLocalY();
                int fy = (int)((float)img.getCursorY()*100/img.getZoom())-lay.getLocalY();
                if (ix > fx) {
                    int tmp = fx;
                    fx = ix;
                    ix = tmp;
                }
                if (iy > fy) {
                    int tmp = fy;
                    fy = iy;
                    iy = tmp;
                }
                if (fx > 0 && fy > 0 && ix < layimg.getWidth()-1 && iy < layimg.getHeight()-1) {
                    ix = (ix < 0) ? 0 : ix;
                    iy = (iy < 0) ? 0 : iy;
                    fx = (fx > layimg.getWidth()-1) ? layimg.getWidth()-1 : fx;
                    fy = (fy > layimg.getHeight()-1) ? layimg.getHeight()-1 : fy;
                    int w = Math.abs(fx-ix);
                    int h = Math.abs(fy-iy);
                    int argb = new Color(255,255,255,0).getRGB();
                    imgselect = new BufferedImage(w,h,BufferedImage.TYPE_4BYTE_ABGR);
                    for (int y = 0; y < h; y++) {
                        for (int x = 0; x < w; x++) {
                            imgselect.setRGB(x, y, layimg.getRGB(ix+x, iy+y));
                        }
                    }
                    for (int y = iy; y < iy+h; y++) {
                        for (int x = ix; x < ix+w; x++) {
                            layimg.setRGB(x, y, argb);
                        }
                    }
                    img.setHasSelect(false);
                    mainview.getToolsView().getLtoolPreview().setIcon(new ImageIcon(imgselect));
                    mainview.updateWindow();
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Cut from layer: "+lay.getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
            }
            else if (mi == mainview.getMIpaste() && mainview.getTabs().getSelectedIndex() != -1) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                BufferedImage layimg = new BufferedImage(imgselect.getWidth(), imgselect.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                layimg.createGraphics().drawImage(imgselect, 0, 0, null);
                img.newLayer(layimg);
                this.updateLayers();
                mainview.updateWindow();
                img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Paste new layer: "+img.getLayers().get(img.getLayers().size()-1).getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                this.updateHist();
                mainview.getTabs().grabFocus();
            }
            else if (mi == mainview.getMIselectall() && mainview.getTabs().getSelectedIndex() != -1) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                LayerPanel lay = img.getLayers().get(img.getIndexSelect());
                img.setInitX(0);
                img.setInitY(0);
                img.setCursorX(img.getWidth());
                img.setCursorY(img.getHeight());
                Graphics g = img.getGraphics();
                g.setColor(new Color(0,255,0,255));
                int ix = (int)((float)lay.getLocalX()*img.getZoom()/100);
                int iy = (int)((float)lay.getLocalY()*img.getZoom()/100);
                int iw = (int)((float)lay.getImage().getWidth()*img.getZoom()/100);
                int ih = (int)((float)lay.getImage().getHeight()*img.getZoom()/100);
                g.drawRect(ix, iy, iw+2, ih+2);
            }
            else if (mi == mainview.getMIrotation()) {
                String sangle = (new JOptionPane()).showInputDialog(null, "Enter rotation angle in degres", "Rotate image - MyPhotoshop", JOptionPane.QUESTION_MESSAGE);
                if (sangle != null && sangle.matches("-?[0-9]+(.[0-9]+)?")) {
                    ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                    img.setRotateProj(true);
                    img.setHasRot(true);
                    img.rotateExec(Float.valueOf(sangle));
                    img.setRotateProj(false);
                    img.setHasRot(false);
                    this.updateLayers();
                    mainview.updateWindow();
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Rotate image to "+sangle,img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
            }
            else if (mi == mainview.getMIlayRotate()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                img.setRotateProj(false);
                img.setHasRot(true);
                mainview.getRotView().setTitle("Rotate - " + img.getLayers().get(img.getIndexSelect()).getFileName());
                mainview.getRotView().setVisible(true);
                mainview.getRotView().getBreset().doClick();
            }
            else if (mi == mainview.getMIresize()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                img.setHasResize(true);
                img.setResizeProj(true);
                mainview.getResizeView().setTitle("Resize Image");
                mainview.getResizeView().setVisible(true);
                mainview.getResizeView().getBresizeReset().doClick();
            }
            else if (mi == mainview.getMIlayResize()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                img.setResizeProj(false);
                img.setHasResize(true);
                mainview.getResizeView().setTitle("Resize - " + img.getLayers().get(img.getIndexSelect()).getFileName());
                mainview.getResizeView().setVisible(true);
                mainview.getResizeView().getBresizeReset().doClick();
            }
            else if (mi == mainview.getMIclose())
                ((MyPanel)mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).getParent()).getBclose().doClick();
            else if (mi == mainview.getMIcloseall()) {
                while (mainview.getTabs().getSelectedIndex() != -1)
                    ((MyPanel)mainview.getProjects().get(0).getParent()).getBclose().doClick();
            }
            else if (mi == mainview.getMIquit()) {
                int option = (new JOptionPane()).showConfirmDialog(null, "Are you sure you want to quit the application?" + System.lineSeparator() + "Be sure to save all opened projects before leaving.", "Quit MyPhotoshop", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.OK_OPTION)
                    System.exit(0);
            }
            else if (mi == mainview.getMInew()) {
                newproj = true;
                mainview.getNewView().setVisible(true);
            }
            else if (mi == mainview.getMInewlayer()) {
                newproj = false;
                mainview.getNewView().setVisible(true);
            }
            else if (mi == mainview.getMIundo()) {
                int index = mainview.getLaysView().getLIhistory().getSelectedIndex();
                if (index > 0) {
                    mainview.getLaysView().getLIhistory().setSelectedIndex(index-1);
                    mainview.getTabs().grabFocus();
                }
            }
            else if (mi == mainview.getMIredo()) {
                int index = mainview.getLaysView().getLIhistory().getSelectedIndex();
                int size = mainview.getLaysView().getModelHistory().size();
                if (index < size-1) {
                    mainview.getLaysView().getLIhistory().setSelectedIndex(index+1);
                    mainview.getTabs().grabFocus();
                }
            }
            else if (mi == mainview.getMIopenurl()) {
                String urlname = (new JOptionPane()).showInputDialog(null, "Enter image url", "Open image from URL - MyPhotoshop", JOptionPane.QUESTION_MESSAGE);
                String[] splits = urlname.split("/");
                String filename = splits[splits.length-1];
                URL url = null;
                BufferedImage bufimg = null;
                try {
                    url = new URL(urlname);
                    try {
                        bufimg = ImageIO.read(url);
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                ImagePanel img = new ImagePanel(bufimg, filename, this);
                img.addMouseListener(this);
                img.addMouseMotionListener(this);
                img.addMouseWheelListener(this);
                JScrollPane sp = new JScrollPane();
                MyPanel pan = new MyPanel(img);
                pan.addMouseListener(this);
                pan.addMouseWheelListener(this);
                pan.setLayout(new GridBagLayout());
                pan.add(img);
                mainview.getProjects().add(img);
                sp.setViewportView(pan);
                mainview.addClosableTab(mainview.getTabs(), sp, pan, filename, MainView.CLOSE_TAB_ICON1);
                img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("New image: "+filename,img.getImagePanelCopy(),img.getBuiltImage()));
                this.updateHist();
                mainview.getTabs().grabFocus();
            }
            else if (mi == mainview.getMIlayerurl()) {
                String urlname = (new JOptionPane()).showInputDialog(null, "Enter image url", "Open image from URL - MyPhotoshop", JOptionPane.QUESTION_MESSAGE);
                String[] splits = urlname.split("/");
                String filename = splits[splits.length-1];
                URL url = null;
                BufferedImage bufimg = null;
                try {
                    url = new URL(urlname);
                    try {
                        bufimg = ImageIO.read(url);
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                img.newLayer(bufimg, filename);
                this.updateLayers();
                mainview.updateWindow();
                img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("New layer: "+filename,img.getImagePanelCopy(),img.getBuiltImage()));
                this.updateHist();
                mainview.getTabs().grabFocus();
            }
            else if (mi == mainview.getMIprint()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                new Thread(new PrintImage(img.getBuiltImage())).start();
            }
            else if (mi == mainview.getMIbalance()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                BufferedImage laybuf = img.getLayers().get(img.getIndexSelect()).getImage();
                BufferedImage imgcol = new BufferedImage(laybuf.getWidth(), laybuf.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                imgcol.createGraphics().drawImage(laybuf, 0, 0, null);
                img.setImgCol(imgcol);
                img.setHasBalance(true);
                mainview.getColorBalanceView().setVisible(true);
                mainview.getColorBalanceView().setTitle("Color balance "+img.getLayers().get(img.getIndexSelect()).getFileName());
                mainview.getColorBalanceView().getSLred().setValue(100);
                mainview.getColorBalanceView().getSLgreen().setValue(100);
                mainview.getColorBalanceView().getSLblue().setValue(100);
                mainview.getColorBalanceView().getSLalpha().setValue(100);
            }
            else if (mi == mainview.getMIbright()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                BufferedImage laybuf = img.getLayers().get(img.getIndexSelect()).getImage();
                BufferedImage imgcol = new BufferedImage(laybuf.getWidth(), laybuf.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                imgcol.createGraphics().drawImage(laybuf, 0, 0, null);
                img.setImgCol(imgcol);
                img.setHasBright(true);
                mainview.getBrightView().setVisible(true);
                mainview.getBrightView().setTitle("Brightness Contrast "+img.getLayers().get(img.getIndexSelect()).getFileName());
                mainview.getBrightView().getSLbright().setValue(100);
                mainview.getBrightView().getSLcontrast().setValue(100);
            }
        }
        else if (e.getSource().getClass() == JButton.class) {
            JButton but = (JButton)e.getSource();
            if (but == mainview.getStopfilter()) {
                int index = mainview.getTabs().getSelectedIndex();
                if (index != -1) {
                    Thread th = mainview.getProjects().get(index).getThread();
                    if (th != null) {
                        th.stop();
                        mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        mainview.getProjects().get(index).setThread(null);
                        mainview.getStopfilter().setEnabled(false);
                    }
                }
            }
            else if (but == mainview.getRotView().getBrotation()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                ((MyPanel)img.getParent()).clearRect();
                img.rotateExec((float)mainview.getRotView().getSpangle().getValue());
                mainview.getRotView().setVisible(false);
                img.setHasRot(false);
                //img.updateZoomImg();
                this.updateLayers();
                mainview.updateWindow();
                if (img.getRotateProj())
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Rotate image to "+(float)mainview.getRotView().getSpangle().getValue(),img.getImagePanelCopy(),img.getBuiltImage()));
                else
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Rotate "+img.getLayers().get(img.getIndexSelect()).getFileName()+" to "+(float)mainview.getRotView().getSpangle().getValue(),img.getImagePanelCopy(),img.getBuiltImage()));
                this.updateHist();
                mainview.getTabs().grabFocus();
            }
            else if (but == mainview.getRotView().getBreset()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                mainview.getRotView().getSpangle().setValue(new Float(0));
                try {
                    mainview.getRotView().getSpangle().commitEdit();
                } catch (ParseException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (but == mainview.getRotView().getBcancel()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                if (img.getRotateProj()) {
                    ((MyPanel)img.getParent()).clearRect();
                    img.setHasRot(false);
                    //img.updateZoomImg();
                    img.getParent().getParent().validate();
                    mainview.getRotView().setVisible(false);
                }
                else {
                    img.setHasRot(false);
                    //img.updateZoomImg();
                    img.repaint();
                    mainview.getRotView().setVisible(false);
                }
            }
            else if (but == mainview.getResizeView().getBresizeResize()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                img.resizeExec((int)mainview.getResizeView().getSPresizeWidth().getValue(),(int)mainview.getResizeView().getSPresizeHeight().getValue());
                mainview.getResizeView().setVisible(false);
                img.setHasResize(false);
                this.updateLayers();
                mainview.updateWindow();
                if (img.getResizeProj())
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Resize image to "+(int)mainview.getResizeView().getSPresizeWidth().getValue()+"x"+(int)mainview.getResizeView().getSPresizeHeight().getValue(),img.getImagePanelCopy(),img.getBuiltImage()));
                else
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Resize "+img.getLayers().get(img.getIndexSelect()).getFileName()+" to "+(int)mainview.getResizeView().getSPresizeWidth().getValue()+"x"+(int)mainview.getResizeView().getSPresizeHeight().getValue(),img.getImagePanelCopy(),img.getBuiltImage()));
                this.updateHist();
                mainview.getTabs().grabFocus();
            }
            else if (but == mainview.getResizeView().getBresizeReset()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                mainview.getResizeView().setBind(true);
                mainview.getResizeView().getBresizeBind().setIcon(new ImageIcon("src/images/bind.png"));
                if (img.getResizeProj()) {
                    mainview.getResizeView().getSPresizeWidth().setValue(img.getImgW());
                }
                else {
                    mainview.getResizeView().getSPresizeWidth().setValue(img.getLayers().get(img.getIndexSelect()).getImage().getWidth());
                }   
            }
            else if (but == mainview.getResizeView().getBresizeBind()) {
                model.sendNotification(e.getSource());
            }
            else if (but == mainview.getNewView().getBnewCancel()) {
                mainview.getNewView().setVisible(false);
            }
            else if (but == mainview.getNewView().getBnewCreate()) {
                int nw = (int)mainview.getNewView().getSpNewWidth().getValue();
                int nh = (int)mainview.getNewView().getSpNewHeight().getValue();
                Color col = mainview.getNewView().getCCcolor().getColor();
                BufferedImage bufimg = new BufferedImage(nw,nh,BufferedImage.TYPE_4BYTE_ABGR);
                model.fillImageColor(bufimg, col.getRGB());
                if (newproj)
                {
                    mainview.incNbnewproj();
                    ImagePanel img = new ImagePanel(bufimg, "newimage" + mainview.getNbnewproj(), this);
                    img.addMouseListener(this);
                    img.addMouseMotionListener(this);
                    img.addMouseWheelListener(this);
                    JScrollPane sp = new JScrollPane();
                    MyPanel pan = new MyPanel(img);
                    pan.addMouseListener(this);
                    pan.addMouseWheelListener(this);
                    pan.setLayout(new GridBagLayout());
                    pan.add(img);
                    mainview.getProjects().add(img);
                    sp.setViewportView(pan);
                    mainview.addClosableTab(mainview.getTabs(), sp, pan, "newimage" + mainview.getNbnewproj(), MainView.CLOSE_TAB_ICON1);
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("New image: "+"newimage" + mainview.getNbnewproj(),img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
                else {
                    ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                    img.newLayer(bufimg);
                    this.updateLayers();
                    mainview.updateWindow();
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("New layer: "+img.getLayers().get(img.getLayers().size()-1).getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
                mainview.getNewView().setVisible(false);
            }
            else if (but == mainview.getLaysView().getBlaysRemove() && mainview.getTabs().getSelectedIndex() != -1) {
                int index = mainview.getLaysView().getLIlayers().getSelectedIndex();
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                String layname = img.getLayers().get(index).getFileName();
                if (img.getLayers().size() > 1) {
                    img.getLayers().remove(index);
                    img.setIndexSelect(0);
                    this.updateLayers();
                    mainview.updateWindow();
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Remove layer: "+layname,img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
            }
            else if (but == mainview.getLaysView().getBlaysUp() && mainview.getTabs().getSelectedIndex() != -1) {
                int index = mainview.getLaysView().getLIlayers().getSelectedIndex();
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                if (index != 0) {
                    LayerPanel lp = img.getLayers().get(index);
                    img.getLayers().set(index, img.getLayers().get(index-1));
                    img.getLayers().set(index-1, lp);
                    img.setIndexSelect(index-1);
                    this.updateLayers();
                    mainview.updateWindow();
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Up layer: "+lp.getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
            }
            else if (but == mainview.getLaysView().getBlaysDown() && mainview.getTabs().getSelectedIndex() != -1) {
                int index = mainview.getLaysView().getLIlayers().getSelectedIndex();
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                if (index != img.getLayers().size()-1) {
                    LayerPanel lp = img.getLayers().get(index);
                    img.getLayers().set(index, img.getLayers().get(index+1));
                    img.getLayers().set(index+1, lp);
                    img.setIndexSelect(index+1);
                    this.updateLayers();
                    mainview.updateWindow();
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Down layer: "+lp.getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
            }
            else if (but == mainview.getLaysView().getBlaysMergeDown() && mainview.getTabs().getSelectedIndex() != -1) {
                int index = mainview.getLaysView().getLIlayers().getSelectedIndex();
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                if (index != img.getLayers().size()-1) {
                    LayerPanel lpup = img.getLayers().get(index);
                    BufferedImage bufup = lpup.getImage();
                    LayerPanel lpdown = img.getLayers().get(index+1);
                    BufferedImage bufdown = lpdown.getImage();
                    int nw = Math.max(bufup.getWidth(), bufdown.getWidth());
                    int nh = Math.max(bufup.getWidth(), bufdown.getWidth());
                    int nx = Math.min(lpup.getLocalX(), lpdown.getLocalX());
                    int ny = Math.min(lpup.getLocalY(), lpdown.getLocalY());
                    BufferedImage newbuf = new BufferedImage(nw,nh,BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D g2d = newbuf.createGraphics();
                    int imgx = (lpup.getLocalX() < lpdown.getLocalX()) ? 0 : lpup.getLocalX()-lpdown.getLocalX();
                    int imgy = (lpup.getLocalY() < lpdown.getLocalY()) ? 0 : lpup.getLocalY()-lpdown.getLocalY();
                    g2d.drawImage(bufup, imgx, imgy, bufup.getWidth(), bufup.getHeight(), null);
                    imgx = (lpdown.getLocalX() < lpup.getLocalX()) ? 0 : lpdown.getLocalX()-lpup.getLocalX();
                    imgy = (lpdown.getLocalY() < lpup.getLocalY()) ? 0 : lpdown.getLocalY()-lpup.getLocalY();
                    g2d.drawImage(bufdown, imgx, imgy, bufdown.getWidth(), bufdown.getHeight(), null);
                    lpup.setImage(newbuf);
                    lpup.setLocalX(nx);
                    lpup.setLocalY(ny);
                    img.getLayers().remove(index+1);
                    img.setIndexSelect(index);
                    this.updateLayers();
                    mainview.updateWindow();
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Merge down "+lpup.getFileName()+" to "+lpdown.getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
            }
            else if (but == mainview.getLaysView().getBlaysMergeUp() && mainview.getTabs().getSelectedIndex() != -1) {
                int index = mainview.getLaysView().getLIlayers().getSelectedIndex();
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                if (index != 0) {
                    LayerPanel lpdown = img.getLayers().get(index);
                    BufferedImage bufdown = lpdown.getImage();
                    LayerPanel lpup = img.getLayers().get(index-1);
                    BufferedImage bufup = lpup.getImage();
                    int nw = Math.max(bufup.getWidth(), bufdown.getWidth());
                    int nh = Math.max(bufup.getWidth(), bufdown.getWidth());
                    int nx = Math.min(lpup.getLocalX(), lpdown.getLocalX());
                    int ny = Math.min(lpup.getLocalY(), lpdown.getLocalY());
                    BufferedImage newbuf = new BufferedImage(nw,nh,BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D g2d = newbuf.createGraphics();
                    int imgx = (lpup.getLocalX() < lpdown.getLocalX()) ? 0 : lpup.getLocalX()-lpdown.getLocalX();
                    int imgy = (lpup.getLocalY() < lpdown.getLocalY()) ? 0 : lpup.getLocalY()-lpdown.getLocalY();
                    g2d.drawImage(bufup, imgx, imgy, bufup.getWidth(), bufup.getHeight(), null);
                    imgx = (lpdown.getLocalX() < lpup.getLocalX()) ? 0 : lpdown.getLocalX()-lpup.getLocalX();
                    imgy = (lpdown.getLocalY() < lpup.getLocalY()) ? 0 : lpdown.getLocalY()-lpup.getLocalY();
                    g2d.drawImage(bufdown, imgx, imgy, bufdown.getWidth(), bufdown.getHeight(), null);
                    lpup.setImage(newbuf);
                    lpup.setLocalX(nx);
                    lpup.setLocalY(ny);
                    img.getLayers().remove(index);
                    img.setIndexSelect(index-1);
                    this.updateLayers();
                    mainview.updateWindow();
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Merge up "+lpdown.getFileName()+" to "+lpup.getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
            }
            else if (but == mainview.getToolsView().getBtoolColor()) {
                mainview.getToolsView().getCcView().setVisible(true);
            }
            else if (but == mainview.getTextView().getBfontCreate()) {
                if (textadd.length() > 0) {
                    ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                    Canvas c = new Canvas();
                    FontMetrics fm = c.getFontMetrics(textfont);
                    int txtw = fm.stringWidth(textadd);
                    int txth = fm.getHeight();
                    BufferedImage layimg = new BufferedImage(txtw,txth,BufferedImage.TYPE_4BYTE_ABGR);
                    model.fillImageColor(layimg, new Color(255,255,255,0).getRGB());
                    Graphics2D g2d = layimg.createGraphics();
                    g2d.setColor(col);
                    g2d.setFont(textfont);
                    g2d.drawString(textadd, 0, txth-fm.getDescent());
                    mainview.getTextView().setVisible(false);
                    img.setHasText(false);
                    img.newLayer(layimg,     
                            img.getTextX()-2,img.getTextY()-txth+fm.getDescent()-2);
                    this.updateLayers();
                    mainview.updateWindow();
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("New text layer: "+textadd,img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
            }
            else if (but == mainview.getTextView().getBfontCancel()) {
                mainview.getTextView().setVisible(false);
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).setHasText(false);
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
            else if (but == mainview.getLaysView().getBlaysRename() && mainview.getTabs().getSelectedIndex() != -1) {
                String newname = (new JOptionPane()).showInputDialog(null, "Enter new layer name", "Change layer name - MyPhotoshop", JOptionPane.QUESTION_MESSAGE);
                if (newname != null) {
                    ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                    LayerPanel lay = img.getLayers().get(img.getIndexSelect());
                    String prevname = lay.getFileName();
                    lay.setFileName(newname);
                    this.updateLayers();
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Rename "+prevname+" to "+newname,img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
            }
            else if (but == mainview.getColorBalanceView().getBbalanceCancel()) {
                mainview.getColorBalanceView().setVisible(false);
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                img.setHasBalance(false);
                img.repaint();
            }
            else if (but == mainview.getBrightView().getBbrightCancel()) {
                mainview.getBrightView().setVisible(false);
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                img.setHasBright(false);
                img.repaint();
            }
            else if (but == mainview.getColorBalanceView().getBbalanceExec()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                LayerPanel lay = img.getLayers().get(img.getIndexSelect());
                BufferedImage laybuf = lay.getImage();
                BufferedImage imgcol = img.getImgCol();
                for (int i = 0; i < laybuf.getHeight(); i++) {
                    for (int j = 0; j < laybuf.getWidth(); j++) {
                        laybuf.setRGB(j, i, imgcol.getRGB(j, i));
                    }
                }
                mainview.getColorBalanceView().setVisible(false);
                img.setHasBalance(false);
                this.updateLayers();
                img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Balance color on "+lay.getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                this.updateHist();
                mainview.getTabs().grabFocus();
            }
            else if (but == mainview.getBrightView().getBbrightExec()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                LayerPanel lay = img.getLayers().get(img.getIndexSelect());
                BufferedImage laybuf = lay.getImage();
                BufferedImage imgcol = img.getImgCol();
                for (int i = 0; i < laybuf.getHeight(); i++) {
                    for (int j = 0; j < laybuf.getWidth(); j++) {
                        laybuf.setRGB(j, i, imgcol.getRGB(j, i));
                    }
                }
                mainview.getBrightView().setVisible(false);
                img.setHasBright(false);
                this.updateLayers();
                img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Brightness Contrast on "+lay.getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                this.updateHist();
                mainview.getTabs().grabFocus();
            }
        }
        else if (e.getSource().getClass() == JToggleButton.class) {
            JToggleButton but = (JToggleButton)e.getSource();
            if (but == mainview.getLaysView().getBlaysView()) {
                if (mainview.getTabs().getSelectedIndex() != -1) {
                    ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                    LayerPanel lay = img.getLayers().get(img.getIndexSelect());
                    if (lay.getIsShowed())
                        lay.setIsShowed(false);
                    else
                        lay.setIsShowed(true);
                    this.updateLayers();
                }
                else {
                    but.setSelected(false);
                }
            }
            if (but == mainview.getToolsView().getTBtoolText()) {
                if (but == btool) {
                    btool = null;
                    if (mainview.getTabs().getSelectedIndex() != -1) {
                        for (ImagePanel img : mainview.getProjects())
                            img.setHasText(false);
                        mainview.getTextView().setVisible(false);
                        mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
                    }
                }
                else {
                    if (btool != null)
                        btool.doClick();
                    btool = but;
                }
            }
            else if (but == mainview.getToolsView().getTBtoolMove()) {
                if (but == btool) {
                    btool = null;
                }
                else {
                    if (btool != null)
                        btool.doClick();
                    btool = but;
                }
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
            else if (but == mainview.getToolsView().getTBtoolDraw()) {
                if (but == btool) {
                    btool = null;
                    hasdraw = false;
                }
                else {
                    if (btool != null)
                        btool.doClick();
                    btool = but;
                    hasdraw = true;
                }
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
            else if (but == mainview.getToolsView().getTBtoolPaint()) {
                if (but == btool) {
                    btool = null;
                    haspaint = false;
                }
                else {
                    if (btool != null)
                        btool.doClick();
                    btool = but;
                    haspaint = true;
                }
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
            else if (but == mainview.getToolsView().getTBtoolClear()) {
                if (but == btool) {
                    btool = null;
                    hasclear = false;
                }
                else {
                    if (btool != null)
                        btool.doClick();
                    btool = but;
                    hasclear = true;
                }
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
            else if (but == mainview.getToolsView().getTBtoolRect()) {
                if (but == btool) {
                    btool = null;
                    hasrect = false;
                }
                else {
                    if (btool != null)
                        btool.doClick();
                    btool = but;
                    hasrect = true;
                }
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
            else if (but == mainview.getToolsView().getTBtoolOval()) {
                if (but == btool) {
                    btool = null;
                    hasoval = false;
                }
                else {
                    if (btool != null)
                        btool.doClick();
                    btool = but;
                    hasoval = true;
                }
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
            else if (but == mainview.getToolsView().getTBtoolLine()) {
                if (but == btool) {
                    btool = null;
                    hasline = false;
                }
                else {
                    if (btool != null)
                        btool.doClick();
                    btool = but;
                    hasline = true;
                }
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
            else if (but == mainview.getToolsView().getTBtoolFill()) {
                if (but == btool) {
                    btool = null;
                    hasfill = false;
                }
                else {
                    if (btool != null)
                        btool.doClick();
                    btool = but;
                    hasfill = true;
                }
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
            else if (but == mainview.getToolsView().getTBtoolCut()) {
                if (but == btool) {
                    btool = null;
                    hascut = false;
                }
                else {
                    if (btool != null)
                        btool.doClick();
                    btool = but;
                    hascut = true;
                }
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
            else if (but == mainview.getToolsView().getTBtoolSelect()) {
                if (but == btool) {
                    btool = null;
                    if (mainview.getTabs().getSelectedIndex() != -1) {
                        for (ImagePanel img : mainview.getProjects())
                            img.setHasSelect(false);
                    }
                    hasselect = false;
                }
                else {
                    if (btool != null)
                        btool.doClick();
                    btool = but;
                    hasselect = true;
                }
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
            else if (but == mainview.getToolsView().getTBtoolPipette()) {
                if (but == btool) {
                    btool = null;
                    haspipette = false;
                }
                else {
                    if (btool != null)
                        btool.doClick();
                    btool = but;
                    haspipette = true;
                }
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
            else if (but == mainview.getToolsView().getTBtoolStamp()) {
                if (but == btool) {
                    btool = null;
                    hasstamp = false;
                }
                else {
                    if (btool != null)
                        btool.doClick();
                    btool = but;
                    if (imgselect != null)
                        hasstamp = true;
                }
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
        }
        else if (e.getSource().getClass() == JComboBox.class) {
            JComboBox cb = (JComboBox)e.getSource();
            if (cb == mainview.getTextView().getCBfont() || cb == mainview.getTextView().getCBfontstyle()) {
                this.updateFont();
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
        }
    }
    
    private void openProject() throws IOException, ClassNotFoundException {
        if (mainview.getTabs().getSelectedIndex() != -1)
            fc.setCurrentDirectory(new File(mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).getFilePath()));
        int returnVal = fc.showOpenDialog(mainview);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (file.getName().toLowerCase().endsWith(".mypsd")) {
                ImagePanel img = new ImagePanel(this, new CareTaker());
                ImagePanel imgsav;
                String state;
                int[] iconpix;
                JScrollPane sp = new JScrollPane();
                MyPanel pan = new MyPanel(img);
                pan.addMouseListener(this);
                pan.addMouseWheelListener(this);
                pan.setLayout(new GridBagLayout());
                pan.add(img);
                mainview.getProjects().add(img);
                sp.setViewportView(pan);
                ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(file));
                img.setFileName((String)ois.readObject());
                img.setFilePath((String)ois.readObject());
                img.setZoom((float)ois.readObject());
                img.resetDims((int)ois.readObject(), (int)ois.readObject());
                img.setNbLays((int)ois.readObject());
                img.setIndexSelect((int)ois.readObject());
                img.setLayers((ArrayList<LayerPanel>)ois.readObject());
                int nbmems = (int)ois.readObject();
                for (int i = 0; i < nbmems; i++) {
                    state = (String)ois.readObject();
                    iconpix = (int[])ois.readObject();
                    imgsav = new ImagePanel(this, img.getCareTaker());
                    imgsav.setFileName((String)ois.readObject());
                    imgsav.setFilePath((String)ois.readObject());
                    imgsav.setZoom((float)ois.readObject());
                    imgsav.resetDims((int)ois.readObject(), (int)ois.readObject());
                    imgsav.setNbLays((int)ois.readObject());
                    imgsav.setIndexSelect((int)ois.readObject());
                    imgsav.setLayers((ArrayList<LayerPanel>)ois.readObject());
                    img.getCareTaker().addMemento(new Memento(state,imgsav,iconpix));
                }
                ois.close();
                img.buildLayersImg();
                mainview.addClosableTab(mainview.getTabs(), sp, pan, file.getName(), MainView.CLOSE_TAB_ICON1);
            }
            else if (file.getName().toLowerCase().endsWith(".mypsd.gz")) {
                FileInputStream fis;
		GZIPInputStream gis;
		ObjectInputStream ois;
                ImagePanel img = new ImagePanel(this, new CareTaker());
                ImagePanel imgsav;
                String state;
                int[] iconpix;
                JScrollPane sp = new JScrollPane();
                MyPanel pan = new MyPanel(img);
                pan.addMouseListener(this);
                pan.addMouseWheelListener(this);
                pan.setLayout(new GridBagLayout());
                pan.add(img);
                mainview.getProjects().add(img);
                sp.setViewportView(pan);
		try {
                    fis = new FileInputStream(file.getPath());
                    gis = new GZIPInputStream(fis);
                    ois = new ObjectInputStream(gis);
                    img.setFileName((String)ois.readObject());
                    img.setFilePath((String)ois.readObject());
                    img.setZoom((float)ois.readObject());
                    img.resetDims((int)ois.readObject(), (int)ois.readObject());
                    img.setNbLays((int)ois.readObject());
                    img.setIndexSelect((int)ois.readObject());
                    img.setLayers((ArrayList<LayerPanel>)ois.readObject());
                    int nbmems = (int)ois.readObject();
                    for (int i = 0; i < nbmems; i++) {
                        state = (String)ois.readObject();
                        iconpix = (int[])ois.readObject();
                        imgsav = new ImagePanel(this, img.getCareTaker());
                        imgsav.setFileName((String)ois.readObject());
                        imgsav.setFilePath((String)ois.readObject());
                        imgsav.setZoom((float)ois.readObject());
                        imgsav.resetDims((int)ois.readObject(), (int)ois.readObject());
                        imgsav.setNbLays((int)ois.readObject());
                        imgsav.setIndexSelect((int)ois.readObject());
                        imgsav.setLayers((ArrayList<LayerPanel>)ois.readObject());
                        img.getCareTaker().addMemento(new Memento(state,imgsav,iconpix));
                    }
                    ois.close();
                    gis.close();
                    fis.close();
                    img.buildLayersImg();
                    mainview.addClosableTab(mainview.getTabs(), sp, pan, file.getName(), MainView.CLOSE_TAB_ICON1);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
            }
            else {
                ImagePanel img = new ImagePanel(file, this);
                img.addMouseListener(this);
                img.addMouseMotionListener(this);
                img.addMouseWheelListener(this);
                JScrollPane sp = new JScrollPane();
                MyPanel pan = new MyPanel(img);
                pan.addMouseListener(this);
                pan.addMouseWheelListener(this);
                pan.setLayout(new GridBagLayout());
                pan.add(img);
                mainview.getProjects().add(img);
                sp.setViewportView(pan);
                mainview.addClosableTab(mainview.getTabs(), sp, pan, file.getName(), MainView.CLOSE_TAB_ICON1);
                img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Open image: "+file.getName(),img.getImagePanelCopy(),img.getBuiltImage()));
                this.updateHist();
            }
            mainview.getTabs().grabFocus();
            
            //This is where a real application would open the file.
            System.out.println("Opening: " + file.getAbsolutePath() + ".");
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }
    
    private void openLayer() {
        if (mainview.getTabs().getSelectedIndex() != -1)
            fc.setCurrentDirectory(new File(mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).getFilePath()));
        int returnVal = fc.showOpenDialog(mainview);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
            img.newLayer(file);
            mainview.getTabs().setSelectedIndex(mainview.getTabs().getSelectedIndex());
            this.updateLayers();
            mainview.updateWindow();
            img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("New layer: "+file.getName(),img.getImagePanelCopy(),img.getBuiltImage()));
            this.updateHist();
            mainview.getTabs().grabFocus();
            
            //This is where a real application would open the file.
            System.out.println("Opening: " + file.getAbsolutePath() + ".");
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }
    
    public void loadFilters() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        model.loadFilters();
    }
    
    public ConcurrentHashMap<String,Class> getClasses() {
        return model.getClasses();
    }
    
    public void setCol(Color c) {
        col = c;
        mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
    }
    
    public Color getCol() {
        return col;
    }
    
    public Font getTextFont() {
        return textfont;
    }
    
    public String getTextAdd() {
        return textadd;
    }
    
    public void setTextAdd(String s) {
        textadd = s;
    }
    
    public Originator getOriginator() {
        return originator;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource().getClass() == MyTabbedPane.class) {
            MyTabbedPane tp = (MyTabbedPane)e.getSource();
            if (tp == mainview.getTabs() && tp.getSelectedIndex() != -1) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                if (img.getThread() == null)
                    mainview.getStopfilter().setEnabled(false);
                else
                    mainview.getStopfilter().setEnabled(true);
                mainview.getRotView().setVisible(false);
                if (img.getRotateProj())
                    mainview.getRotView().setTitle("Rotate Image");
                else
                    mainview.getRotView().setTitle("Rotate - " + img.getLayers().get(img.getIndexSelect()).getFileName());
                if (img.getHasRot()) {
                    mainview.getRotView().setVisible(true);
                    try {
                        mainview.getRotView().getSpangle().commitEdit();
                    } catch (ParseException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                mainview.getResizeView().setVisible(false);
                if (img.getResizeProj())
                    mainview.getResizeView().setTitle("Resize Image");
                else
                    mainview.getResizeView().setTitle("Resize - " + img.getLayers().get(img.getIndexSelect()).getFileName());
                if (img.getHasResize()) {
                    mainview.getResizeView().setVisible(true);
                }
                this.updateLayers();
                this.updateHist();
                mainview.getTabs().grabFocus();
                
                mainview.setTitle("MyPhotoshop - " + img.getFileName() + " (" + img.getImgW() + "x" + img.getImgH() + ")");
                mainview.setIconImage(img.getBuiltImage());
            }
            else {
                mainview.getSpzoom().setValue(100);
            }
            mainview.updateWindow();
        }
        else if (e.getSource().getClass() == JSpinner.class) {
            JSpinner spin = (JSpinner)e.getSource();
            if (spin == mainview.getSpangle()) {
                model.sendNotification(e.getSource());
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).rotateSimule((float)spin.getValue());
            }
            else if (spin == mainview.getResizeView().getSPresizeWidth()) {
                model.sendNotification(e.getSource());
            }
            else if (spin == mainview.getResizeView().getSPresizeHeight()) {
                model.sendNotification(e.getSource());
            }
            else if (spin == mainview.getSpzoom()) {
                if (mainview.getTabs().getSelectedIndex() != -1) {
                    ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                    if (spin.getValue() != img.getZoom())
                        model.zoomImg(spin, img, (float)spin.getValue());
                }
            }
            else if (spin == mainview.getTextView().getSPfontsize()) {
                this.updateFont();
                mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
            }
        }
        else if (e.getSource().getClass() == JSlider.class) {
            JSlider slid = (JSlider)e.getSource();
            if (slid == mainview.getSlangle())
                model.sendNotification(e.getSource());
            else if (slid == mainview.getLaysView().getSLlaysAlpha()) {
                int perc = slid.getValue();
                mainview.getLaysView().getLlaysAlpha().setText(String.valueOf(perc));
                if (mainview.getTabs().getSelectedIndex() != -1 && perc != prevperc) {
                    ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                    LayerPanel lay = img.getLayers().get(img.getIndexSelect());
                    BufferedImage laybuf = lay.getImage();
                    int argb, a, r, g, b;
                    for (int i = 0; i < laybuf.getWidth(); i++)
                        for (int j = 0; j < laybuf.getHeight(); j++) {
                            argb = laybuf.getRGB(i,j);
                            a = (argb >> 24) & 0xff;
                            r = (argb >> 16) & 0xff;
                            g = (argb >> 8) & 0xff;
                            b = (argb) & 0xff;
                            a = (int)Math.round((double)a*(double)100/(double)prevperc);
                            if (a > 255)
                                a = 255;
                            if (a <= 0)
                                a = 1;
                            a = (int)Math.round((double)a*(double)perc/100);
                            if (a > 255)
                                a = 255;
                            if (a <= 0)
                                a = 1;
                            laybuf.setRGB(i, j, new Color(r,g,b,a).getRGB());
                        }
                    this.updateLayers();
                    mainview.updateWindow();
                    prevperc = perc;
                }
            }
            else if (slid == mainview.getColorBalanceView().getSLred()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                BufferedImage laybuf = img.getLayers().get(img.getIndexSelect()).getImage();
                BufferedImage imgcol = img.getImgCol();
                int perc = slid.getValue();
                mainview.getColorBalanceView().getLred().setText(String.valueOf(perc));
                if (mainview.getTabs().getSelectedIndex() != -1 && imgcol != null) {
                    int argb1 , argb2, a, r, g, b, nr;
                    for (int i = 0; i < laybuf.getWidth(); i++)
                        for (int j = 0; j < laybuf.getHeight(); j++) {
                            argb1 = laybuf.getRGB(i,j);
                            argb2 = imgcol.getRGB(i, j);
                            a = (argb2 >> 24) & 0xff;
                            r = (argb1 >> 16) & 0xff;
                            g = (argb2 >> 8) & 0xff;
                            b = (argb2) & 0xff;
                            nr = (int)Math.round((double)r*(double)perc/100);
                            nr = (nr > 255) ? 255 : nr;
                            imgcol.setRGB(i, j, new Color(nr,g,b,a).getRGB());
                        }
                    img.repaint();
                }
            }
            else if (slid == mainview.getColorBalanceView().getSLgreen()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                BufferedImage laybuf = img.getLayers().get(img.getIndexSelect()).getImage();
                BufferedImage imgcol = img.getImgCol();
                int perc = slid.getValue();
                mainview.getColorBalanceView().getLgreen().setText(String.valueOf(perc));
                if (mainview.getTabs().getSelectedIndex() != -1 && imgcol != null) {
                    int argb1, argb2, a, r, g, b, ng;
                    for (int i = 0; i < laybuf.getWidth(); i++)
                        for (int j = 0; j < laybuf.getHeight(); j++) {
                            argb1 = laybuf.getRGB(i,j);
                            argb2 = imgcol.getRGB(i, j);
                            a = (argb2 >> 24) & 0xff;
                            r = (argb2 >> 16) & 0xff;
                            g = (argb1 >> 8) & 0xff;
                            b = (argb2) & 0xff;
                            ng = (int)Math.round((double)g*(double)perc/100);
                            ng = (ng > 255) ? 255 : ng;
                            imgcol.setRGB(i, j, new Color(r,ng,b,a).getRGB());
                        }
                    img.repaint();
                }
            }
            else if (slid == mainview.getColorBalanceView().getSLblue()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                BufferedImage laybuf = img.getLayers().get(img.getIndexSelect()).getImage();
                BufferedImage imgcol = img.getImgCol();
                int perc = slid.getValue();
                mainview.getColorBalanceView().getLblue().setText(String.valueOf(perc));
                if (mainview.getTabs().getSelectedIndex() != -1 && imgcol != null) {
                    int argb1, argb2, a, r, g, b, nb;
                    for (int i = 0; i < laybuf.getWidth(); i++)
                        for (int j = 0; j < laybuf.getHeight(); j++) {
                            argb1 = laybuf.getRGB(i,j);
                            argb2 = imgcol.getRGB(i, j);
                            a = (argb2 >> 24) & 0xff;
                            r = (argb2 >> 16) & 0xff;
                            g = (argb2 >> 8) & 0xff;
                            b = (argb1) & 0xff;
                            nb = (int)Math.round((double)b*(double)perc/100);
                            nb = (nb > 255) ? 255 : nb;
                            imgcol.setRGB(i, j, new Color(r,g,nb,a).getRGB());
                        }
                    img.repaint();
                }
            }
            else if (slid == mainview.getColorBalanceView().getSLalpha()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                BufferedImage laybuf = img.getLayers().get(img.getIndexSelect()).getImage();
                BufferedImage imgcol = img.getImgCol();
                int perc = slid.getValue();
                mainview.getColorBalanceView().getLalpha().setText(String.valueOf(perc));
                if (mainview.getTabs().getSelectedIndex() != -1 && imgcol != null) {
                    int argb1, argb2, a, r, g, b, na;
                    for (int i = 0; i < laybuf.getWidth(); i++)
                        for (int j = 0; j < laybuf.getHeight(); j++) {
                            argb1 = laybuf.getRGB(i,j);
                            argb2 = imgcol.getRGB(i, j);
                            a = (argb1 >> 24) & 0xff;
                            r = (argb2 >> 16) & 0xff;
                            g = (argb2 >> 8) & 0xff;
                            b = (argb2) & 0xff;
                            na = (int)Math.round((double)a*(double)perc/100);
                            na = (na > 255) ? 255 : na;
                            imgcol.setRGB(i, j, new Color(r,g,b,na).getRGB());
                        }
                    img.repaint();
                }
            }
            else if (slid == mainview.getBrightView().getSLbright()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                BufferedImage laybuf = img.getLayers().get(img.getIndexSelect()).getImage();
                BufferedImage imgcol = img.getImgCol();
                int perc = slid.getValue();
                mainview.getBrightView().getLbright().setText(String.valueOf(perc));
                if (mainview.getTabs().getSelectedIndex() != -1 && imgcol != null) {
                    int argb, a, r, g, b, nr, ng, nb;
                    for (int i = 0; i < laybuf.getWidth(); i++)
                        for (int j = 0; j < laybuf.getHeight(); j++) {
                            argb = laybuf.getRGB(i,j);
                            a = (argb >> 24) & 0xff;
                            r = (argb >> 16) & 0xff;
                            g = (argb >> 8) & 0xff;
                            b = (argb) & 0xff;
                            nr = (int)Math.round((double)r*(double)perc/100);
                            nr = (nr > 255) ? 255 : nr;
                            ng = (int)Math.round((double)g*(double)perc/100);
                            ng = (ng > 255) ? 255 : ng;
                            nb = (int)Math.round((double)b*(double)perc/100);
                            nb = (nb > 255) ? 255 : nb;
                            imgcol.setRGB(i, j, new Color(nr,ng,nb,a).getRGB());
                        }
                    img.repaint();
                }
            }
            else if (slid == mainview.getBrightView().getSLcontrast()) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                BufferedImage laybuf = img.getLayers().get(img.getIndexSelect()).getImage();
                BufferedImage imgcol = img.getImgCol();
                int perc = slid.getValue();
                mainview.getBrightView().getLcontrast().setText(String.valueOf(perc));
                if (mainview.getTabs().getSelectedIndex() != -1 && imgcol != null) {
                    int argb, a, r, g, b, nr, ng, nb;
                    for (int i = 0; i < laybuf.getWidth(); i++)
                        for (int j = 0; j < laybuf.getHeight(); j++) {
                            argb = laybuf.getRGB(i,j);
                            a = (argb >> 24) & 0xff;
                            r = (argb >> 16) & 0xff;
                            g = (argb >> 8) & 0xff;
                            b = (argb) & 0xff;
                            nr = (int)Math.round((double)128+(double)(r-128)*(double)perc/100);
                            nr = (nr > 255) ? 255 : nr;
                            nr = (nr < 0) ? 0 : nr;
                            ng = (int)Math.round((double)128+(double)(g-128)*(double)perc/100);
                            ng = (ng > 255) ? 255 : ng;
                            ng = (ng < 0) ? 0 : ng;
                            nb = (int)Math.round((double)128+(double)(b-128)*(double)perc/100);
                            nb = (nb > 255) ? 255 : nb;
                            nb = (nb < 0) ? 0 : nb;
                            imgcol.setRGB(i, j, new Color(nr,ng,nb,a).getRGB());
                        }
                    img.repaint();
                }
            }
        }
    }
    
    public void updateFont() {
        String name = (String)mainview.getTextView().getCBfont().getSelectedItem();
        int style;
        int index = mainview.getTextView().getCBfontstyle().getSelectedIndex();
        if (index == 0)
            style = Font.PLAIN;
        else if (index == 1)
            style = Font.BOLD;
        else if (index == 2)
            style = Font.ITALIC;
        else
            style = Font.BOLD | Font.ITALIC;
        int size = (int)mainview.getTextView().getSPfontsize().getValue();
        textfont = new Font(name,style,size);
    }
    
    public void updateLayers() {
        ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
        DefaultListModel dlm = mainview.getLaysView().getModelLayers();
        dlm.removeAllElements();
        for (LayerPanel lp : img.getLayers()) {
            JLabel label = new JLabel(lp.getFileName()+" ("+lp.getImage().getWidth()+"x"+lp.getImage().getHeight()+")");
            label.setIcon(new ImageIcon(lp.getLayIcon()));
            dlm.addElement(label);
        }
        mainview.getLaysView().getLIlayers().setSelectedIndex(img.getIndexSelect());
        LayerPanel lay = img.getLayers().get(img.getIndexSelect());
        if (lay.getIsShowed())
            mainview.getLaysView().getBlaysView().setSelected(false);
        else
            mainview.getLaysView().getBlaysView().setSelected(true);
        img.repaint();
    }
    
    public void updateHist() {
        ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
        DefaultListModel dlm = mainview.getLaysView().getModelHistory();
        dlm.removeAllElements();
        for (Memento mem : img.getCareTaker().getStates()) {
            JLabel label = new JLabel(mem.getSavedState());
            label.setIcon(new ImageIcon(mem.getIcon()));
            dlm.addElement(label);
        }
        mainview.getSpzoom().setValue((img.getZoom()));
        img.updateZoomImg();
        mainview.getLaysView().getLIhistory().setSelectedIndex(dlm.getSize()-1);
        img.repaint();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (e.getSource() == mainview.getRotView()) {
            mainview.getRotView().getBcancel().doClick();
        }
        else if (e.getSource() == mainview.getTextView()) {
            mainview.getTextView().getBfontCancel().doClick();
        }
        else if (e.getSource() == mainview.getColorBalanceView()) {
            mainview.getColorBalanceView().getBbalanceCancel().doClick();
        }
        else if (e.getSource() == mainview.getBrightView()) {
            mainview.getBrightView().getBbrightCancel().doClick();
        }
        else if (e.getSource() == mainview.getNewView()) {
            mainview.getNewView().setVisible(false);
        }
        else if (e.getSource() == mainview.getResizeView()) {
            ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
            img.setHasResize(false);
            mainview.getResizeView().setVisible(false);
        }
        else if (e.getSource() == mainview) {
            int option = (new JOptionPane()).showConfirmDialog(null, "Are you sure you want to quit the application?" + System.lineSeparator() + "Be sure to save all opened projects before leaving.", "Quit MyPhotoshop", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION)
                System.exit(0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == mainview.getTabs() || e.getSource().getClass() == MyPanel.class || e.getSource().getClass() == ImagePanel.class) {
            mainview.getTabs().grabFocus();
        }
        if (e.getSource().getClass() == ImagePanel.class) {
            if (btool != null) {
                if (btool == mainview.getToolsView().getTBtoolText()) {
                    ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                    img.setHasText(true);
                    img.setTextX(e.getX());
                    img.setTextY(e.getY());
                    mainview.getTextView().getTFfonttext().setText("");
                    mainview.getTextView().setVisible(true);
                }
                else {
                    
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource().getClass() == ImagePanel.class) {
            ImagePanel img = (ImagePanel)e.getSource();
            LayerPanel lay = img.getLayers().get(img.getIndexSelect());
            img.setInitX(e.getX());
            img.setInitY(e.getY());
            img.setCursorX(e.getX());
            img.setCursorY(e.getY());
            if (btool == mainview.getToolsView().getTBtoolMove()) {
                lay.resetDim();
                img.setHasMove(true);
            }
            else if (hasdraw) {
                BufferedImage layimg = lay.getImage();
                int size = mainview.getToolsView().getSLtoolSize().getValue();
                Graphics2D g2d = layimg.createGraphics();
                g2d.setColor(col);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.fillRect((int)((float)e.getX()*100/img.getZoom())-size/2-lay.getLocalX()-1, (int)((float)e.getY()*100/img.getZoom())-size/2-lay.getLocalY()-1, size, size);
                img.repaint();
            }
            else if (haspaint) {
                BufferedImage layimg = lay.getImage();
                int size = mainview.getToolsView().getSLtoolSize().getValue();
                Graphics2D g2d = layimg.createGraphics();
                g2d.setColor(col);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.fillOval((int)((float)e.getX()*100/img.getZoom())-size/2-lay.getLocalX()-1, (int)((float)e.getY()*100/img.getZoom())-size/2-lay.getLocalY()-1, size, size);
                img.repaint();
            }
            else if (hasclear) {
                BufferedImage layimg = lay.getImage();
                int size = mainview.getToolsView().getSLtoolSize().getValue();
                int argb = new Color(255,255,255,0).getRGB();
                int initx = (int)((float)e.getX()*100/img.getZoom())-size/2-lay.getLocalX();
                int inity = (int)((float)e.getY()*100/img.getZoom())-size/2-lay.getLocalY();
                for (int i = inity; i < inity+size; i++) {
                    for (int j = initx; j < initx+size; j++) {
                        if (j >= 0 && j <= layimg.getWidth()-1 &&
                            i >= 0 && i <= layimg.getHeight()-1) {
                            layimg.setRGB(j, i, argb);
                        }
                    }
                }
                img.repaint();
            }
            else if (hasstamp) {
                BufferedImage layimg = lay.getImage();
                Graphics2D g2d = layimg.createGraphics();
                g2d.drawImage(imgselect, (int)((float)e.getX()*100/img.getZoom())-imgselect.getWidth()/2-lay.getLocalX()-1, (int)((float)e.getY()*100/img.getZoom())-imgselect.getHeight()/2-lay.getLocalY()-1, imgselect.getWidth(), imgselect.getHeight(), null);
                img.repaint();
            }
            else if (hasrect) {
                img.setHasRect(true);
            }
            else if (hasoval) {
                img.setHasOval(true);
            }
            else if (hasline) {
                img.setHasLine(true);
            }
            else if (hasfill) {
                int fillx = (int)((float)e.getX()*100/img.getZoom());
                int filly = (int)((float)e.getY()*100/img.getZoom());
                if (fillx >= lay.getLocalX() && fillx <= lay.getLocalX()+lay.getImage().getWidth() &&
                    filly >= lay.getLocalY() && filly <= lay.getLocalY()+lay.getImage().getHeight()) {
                    model.fillDetect(lay.getImage(), fillx-lay.getLocalX(), filly-lay.getLocalY(),lay.getImage().getRGB(fillx-lay.getLocalX(), filly-lay.getLocalY()), col.getRGB());
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Fill "+lay.getFileName()+" with color detect",img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
            }
            else if (hascut) {
                img.setHasCut(true);
            }
            else if (hasselect) {
                img.setHasSelect(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource().getClass() == ImagePanel.class) {
            ImagePanel img = (ImagePanel)e.getSource();
            LayerPanel lay = img.getLayers().get(img.getIndexSelect());
            if (btool == mainview.getToolsView().getTBtoolMove()) {
                img.setHasMove(false);
                if (img.getCursorX() != img.getInitX() || img.getCursorY() != img.getInitY()) {
                    lay.setLocalX(lay.getLocalX()+(int)((float)(img.getCursorX()-img.getInitX())*(float)100/img.getZoom()));
                    lay.setLocalY(lay.getLocalY()+(int)((float)(img.getCursorY()-img.getInitY())*(float)100/img.getZoom()));
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Move "+lay.getFileName()+" to "+lay.getLocalX()+","+lay.getLocalY(),img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
            }
            else if (hasdraw) {
                img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Draw on "+img.getLayers().get(img.getIndexSelect()).getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                this.updateHist();
                mainview.getTabs().grabFocus();
            }
            else if (haspaint) {
                img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Paint on "+img.getLayers().get(img.getIndexSelect()).getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                this.updateHist();
                mainview.getTabs().grabFocus();
            }
            else if (hasclear) {
                img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Clear on "+img.getLayers().get(img.getIndexSelect()).getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                this.updateHist();
                mainview.getTabs().grabFocus();
            }
            else if (hasstamp) {
                img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Stamp on "+img.getLayers().get(img.getIndexSelect()).getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                this.updateHist();
                mainview.getTabs().grabFocus();
            }
            else if (hasrect) {
                img.setHasRect(false);
                int ix = img.getInitX();
                int fx = img.getCursorX();
                int iy = img.getInitY();
                int fy = img.getCursorY();
                int sx = (ix >= fx) ? fx : ix;
                int sy = (iy >= fy) ? fy : iy;
                int sw = Math.abs(ix-fx);
                int sh = Math.abs(iy-fy);
                if (ix != fx && iy != fy) {
                    Graphics2D g2d = lay.getImage().createGraphics();
                    g2d.setColor(col);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g2d.fillRect((int)((float)sx*100/img.getZoom())-lay.getLocalX()-1, (int)((float)sy*100/img.getZoom())-lay.getLocalY()-1, (int)((float)sw*100/img.getZoom()), (int)((float)sh*100/img.getZoom()));
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Draw rect on "+img.getLayers().get(img.getIndexSelect()).getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
            }
            else if (hasoval) {
                img.setHasOval(false);
                int ix = img.getInitX();
                int fx = img.getCursorX();
                int iy = img.getInitY();
                int fy = img.getCursorY();
                int sx = (ix >= fx) ? fx : ix;
                int sy = (iy >= fy) ? fy : iy;
                int sw = Math.abs(ix-fx);
                int sh = Math.abs(iy-fy);
                if (ix != fx && iy != fy) {
                    Graphics2D g2d = lay.getImage().createGraphics();
                    g2d.setColor(col);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g2d.fillOval((int)((float)sx*100/img.getZoom())-lay.getLocalX()-1, (int)((float)sy*100/img.getZoom())-lay.getLocalY()-1, (int)((float)sw*100/img.getZoom()), (int)((float)sh*100/img.getZoom()));
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Draw oval on "+img.getLayers().get(img.getIndexSelect()).getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
            }
            else if (hasline) {
                img.setHasLine(false);
                int ix = (int)((float)img.getInitX()*100/img.getZoom())-lay.getLocalX();
                int fx = (int)((float)img.getCursorX()*100/img.getZoom())-lay.getLocalX();
                int iy = (int)((float)img.getInitY()*100/img.getZoom())-lay.getLocalY();
                int fy = (int)((float)img.getCursorY()*100/img.getZoom())-lay.getLocalY();
                if (ix != fx || iy != fy) {
                    Graphics2D g2d = lay.getImage().createGraphics();
                    g2d.setColor(col);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g2d.drawLine(ix-1, iy-1, fx-1, fy-1);
                    img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Draw line on "+img.getLayers().get(img.getIndexSelect()).getFileName(),img.getImagePanelCopy(),img.getBuiltImage()));
                    this.updateHist();
                    mainview.getTabs().grabFocus();
                }
            }
            else if (hascut) {
                img.setHasCut(false);
                img.cutImg();
                img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Cut image to "+img.getImgW()+"x"+img.getImgH(),img.getImagePanelCopy(),img.getBuiltImage()));
                this.updateHist();
                mainview.getTabs().grabFocus();
            }
            else if (hasselect) {
                if (img.getInitX() == img.getCursorX() || img.getInitY() == img.getCursorY()) {
                    img.setHasSelect(false);
                    img.repaint();
                }
            }
            else if (haspipette) {
                BufferedImage bufimg = img.getBuiltImage();
                int clickx = (int)((float)e.getX()*100/img.getZoom());
                int clicky = (int)((float)e.getY()*100/img.getZoom());
                int argb = bufimg.getRGB(clickx, clicky);
                col = new Color(argb,true);
                mainview.getToolsView().setColorChooser(col);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource().getClass() == ImagePanel.class) {
            ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
            if (mainview.getBstopfilter().isEnabled())
                img.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            if (btool == mainview.getToolsView().getTBtoolText())
                img.setCursor(new Cursor(Cursor.TEXT_CURSOR));
            else if (btool == mainview.getToolsView().getTBtoolMove())
                img.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            else if (hasrect || hasoval || hasline || hascut || hasselect)
                img.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            else if (hasfill || haspipette)
                img.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().getClass() == ImagePanel.class) {
                ((ImagePanel)e.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (e.getSource() == mainview.getTabs()) {
            if (mainview.getTabs().getSelectedIndex() != -1) {
                boolean goodkey = false;
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                switch(keyCode) { 
                    case KeyEvent.VK_UP:
                        img.getLayers().get(img.getIndexSelect()).decrLocalY();
                        goodkey = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        img.getLayers().get(img.getIndexSelect()).incrLocalY();
                        goodkey = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        img.getLayers().get(img.getIndexSelect()).decrLocalX();
                        goodkey = true;
                        break;
                    case KeyEvent.VK_RIGHT :
                        img.getLayers().get(img.getIndexSelect()).incrLocalX();
                        goodkey = true;
                        break;
                 }
                if (goodkey) {
                    moving = true;
                    if (img.getHasRot() && !img.getRotateProj())
                        img.updateRotLoc();
                    img.repaint();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == mainview.getTextView().getTFfonttext()) {
            textadd = mainview.getTextView().getTFfonttext().getText();
            mainview.getProjects().get(mainview.getTabs().getSelectedIndex()).repaint();
        }
        if (moving) {
            ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
            moving = false;
            img.getCareTaker().addMemento(mainview.getController().getOriginator().saveToMemento("Move "+img.getLayers().get(img.getIndexSelect()).getFileName()+" to "+img.getLayers().get(img.getIndexSelect()).getLocalX()+","+img.getLayers().get(img.getIndexSelect()).getLocalY(),img.getImagePanelCopy(),img.getBuiltImage()));
            this.updateHist();
            mainview.getTabs().grabFocus();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == mainview.getLaysView().getLIlayers()) {
            if (mainview.getTabs().getSelectedIndex() != -1) {
                ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
                if (mainview.getLaysView().getLIlayers().getSelectedIndex() != -1) {
                    img.setIndexSelect(mainview.getLaysView().getLIlayers().getSelectedIndex());
                    LayerPanel lay = img.getLayers().get(img.getIndexSelect());
                    if (lay.getIsShowed())
                        mainview.getLaysView().getBlaysView().setSelected(false);
                    else
                        mainview.getLaysView().getBlaysView().setSelected(true);
                    img.updateZoomImg();
                    img.getParent().validate();
                    img.getParent().repaint();
                    img.repaint();
                }
            }
        }
        if (e.getSource() == mainview.getLaysView().getLIhistory()) {
            if (mainview.getTabs().getSelectedIndex() != -1 && mainview.getLaysView().getModelHistory().size() > 1) {
                int itab = mainview.getTabs().getSelectedIndex();
                ImagePanel img = mainview.getProjects().get(itab);
                if (mainview.getLaysView().getLIhistory().getSelectedIndex() != -1) {
                    MyPanel mypan = (MyPanel)img.getParent();
                    mypan.remove(img);
                    int index = mainview.getLaysView().getLIhistory().getSelectedIndex();
                    originator.restoreFromMemento(img.getCareTaker().getMemento(index));
                    ImagePanel imgrest = originator.getImg();
                    ImagePanel newimg = imgrest.getImgSavedCopy();
                    //newimg.addMouseListener(this);
                    newimg.addMouseMotionListener(this);
                    newimg.addMouseWheelListener(this);
                    mainview.getProjects().set(itab, newimg);
                    newimg.setZoom((float)mainview.getSpzoom().getValue());
                    newimg.updateZoomImg();
                    mypan.add(newimg);
                    mypan.validate();
                    mypan.repaint();
                    newimg.repaint();
                    this.updateLayers();
                    mainview.updateWindow();
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource().getClass() == ImagePanel.class) {
            ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
            mainview.getLx().setText("x: "+(int)((float)e.getX()*100/img.getZoom()-2));
            mainview.getLy().setText("y: "+(int)((float)e.getY()*100/img.getZoom()-2));
            img.setCursorX(e.getX());
            img.setCursorY(e.getY());
            if (img.getHasMove()) {
                img.repaint();
            }
            if (hasdraw) {
                LayerPanel lay = img.getLayers().get(img.getIndexSelect());
                BufferedImage layimg = lay.getImage();
                int size = mainview.getToolsView().getSLtoolSize().getValue();
                Graphics2D g2d = layimg.createGraphics();
                g2d.setColor(col);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.fillRect((int)((float)e.getX()*100/img.getZoom())-size/2-lay.getLocalX()-1, (int)((float)e.getY()*100/img.getZoom())-size/2-lay.getLocalY()-1, size, size);
                img.repaint();
            }
            else if (haspaint) {
                LayerPanel lay = img.getLayers().get(img.getIndexSelect());
                BufferedImage layimg = lay.getImage();
                int size = mainview.getToolsView().getSLtoolSize().getValue();
                Graphics2D g2d = layimg.createGraphics();
                g2d.setColor(col);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.fillOval((int)((float)e.getX()*100/img.getZoom())-size/2-lay.getLocalX()-1, (int)((float)e.getY()*100/img.getZoom())-size/2-lay.getLocalY()-1, size, size);
                img.repaint();
            }
            else if (hasclear) {
                LayerPanel lay = img.getLayers().get(img.getIndexSelect());
                BufferedImage layimg = lay.getImage();
                int size = mainview.getToolsView().getSLtoolSize().getValue();
                int argb = new Color(255,255,255,0).getRGB();
                int initx = (int)((float)e.getX()*100/img.getZoom())-size/2-lay.getLocalX();
                int inity = (int)((float)e.getY()*100/img.getZoom())-size/2-lay.getLocalY();
                for (int i = inity; i < inity+size; i++) {
                    for (int j = initx; j < initx+size; j++) {
                        if (j >= 0 && j <= layimg.getWidth()-1 &&
                            i >= 0 && i <= layimg.getHeight()-1) {
                            layimg.setRGB(j, i, argb);
                        }
                    }
                }
                img.repaint();
            }
            else if (hasstamp) {
                LayerPanel lay = img.getLayers().get(img.getIndexSelect());
                BufferedImage layimg = lay.getImage();
                Graphics2D g2d = layimg.createGraphics();
                g2d.drawImage(imgselect, (int)((float)e.getX()*100/img.getZoom())-imgselect.getWidth()/2-lay.getLocalX()-1, (int)((float)e.getY()*100/img.getZoom())-imgselect.getHeight()/2-lay.getLocalY()-1, imgselect.getWidth(), imgselect.getHeight(), null);
                img.repaint();
            }
            else if (img.getHasRect() || img.getHasOval() || img.getHasLine() || img.getHasCut() || img.getHasSelect())
                img.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getSource().getClass() == ImagePanel.class) {
            ImagePanel img = mainview.getProjects().get(mainview.getTabs().getSelectedIndex());
            mainview.getLx().setText("x: "+(int)((float)e.getX()*100/img.getZoom()-2));
            mainview.getLy().setText("y: "+(int)((float)e.getY()*100/img.getZoom()-2));
            if (haspaint || hasdraw || hasclear || hasstamp) {
                img.setCursorX(e.getX());
                img.setCursorY(e.getY());
                img.repaint();
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int up = 1;
        int down = 2;
        int count = e.getWheelRotation();
        int direction = (count < 0) ? up : down;
        if (e.getSource().getClass() == ImagePanel.class && mainview.getTabs().getSelectedIndex() != -1) {
            if (direction == up) {
                mainview.getSpzoom().setValue((float)mainview.getSpzoom().getValue()+1);
            }
            else {
                mainview.getSpzoom().setValue((float)mainview.getSpzoom().getValue()-1);
            }
        }
        else if (e.getSource().getClass() == MyPanel.class && mainview.getTabs().getSelectedIndex() != -1) {
            MyPanel mypan = (MyPanel)e.getSource();
            JScrollPane sp = (JScrollPane)mypan.getParent().getParent();
            if (direction == up) {
                sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getValue()+10);
            }
            else {
                sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getValue()-10);
            }
        }
    }
}
