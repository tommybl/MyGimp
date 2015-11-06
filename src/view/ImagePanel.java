package view;

import controller.Controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import model.CareTaker;

/**
 * We give you this class to help you display images.
 * You are free to use it or not, to modify it.
 */
public class ImagePanel extends JPanel implements Serializable
{
	private static final long serialVersionUID = -314171089120047242L;
	private String fileName;
        private String filePath;
        private ArrayList<LayerPanel> layers;
        private transient BufferedImage imgrot = null;
        private transient BufferedImage imgcol = null;
        private transient int rotx;
        private transient int roty;
        private transient int textx;
        private transient int texty;
        private float zoom;
        private transient Thread th = null;
        private transient boolean hasrot = false;
        private transient boolean hasresize = false;
        private transient boolean hastext = false;
        private transient boolean newproj = true;
        private transient boolean resizeproj = true;
        private transient boolean rotateproj = true;
        private transient boolean hasmove = false;
        private transient boolean hasrect = false;
        private transient boolean hasoval = false;
        private transient boolean hasline = false;
        private transient boolean hascut = false;
        private transient boolean hasselect = false;
        private transient boolean hasbalance = false;
        private transient boolean hasbright = false;
        private int imgw;
        private int imgh;
        private int nblays = 0;
        private int indexselect = 0;
        private transient Controller control;
        private CareTaker caretaker;
        private transient int cursorx = 0;
        private transient int cursory = 0;
        private transient int initx = 0;
        private transient int inity = 0;
        
        public boolean getHasBalance() {
            return hasbalance;
        }
        
        public void setHasBalance(boolean b) {
            hasbalance = b;
        }
        
        public boolean getHasBright() {
            return hasbright;
        }
        
        public void setHasBright(boolean b) {
            hasbright = b;
        }
        
        public BufferedImage getImgCol() {
            return imgcol;
        }
        
        public void setImgCol(BufferedImage nimg) {
            imgcol = nimg;
        }
        
        public ImagePanel(Controller ctr, CareTaker ct) {
            control = ctr;
            caretaker = ct;
        }
        
        public void resetSelectLay() {
            indexselect = 0;
        }
        
        public void buildLayersPix() {
            for (LayerPanel lp : layers) {
                lp.ImageToPixels();
            }
        }
        
        public void buildLayersImg() {
            for (LayerPanel lp : layers) {
                lp.PixelsToImage();
            }
        }
        
        public void setHasSelect(boolean b) {
            hasselect = b;
        }
        
        public boolean getHasSelect() {
            return hasselect;
        }
        
        public boolean getHasMove() {
            return hasmove;
        }
        
        public void setHasMove(boolean b) {
            hasmove = b;
        }
        
        public boolean getHasCut() {
            return hascut;
        }
        
        public void setHasCut(boolean b) {
            hascut = b;
        }
        
        public boolean getHasRect() {
            return hasrect;
        }
        
        public void setHasRect(boolean b) {
            hasrect = b;
        }
        
        public boolean getHasOval() {
            return hasoval;
        }
        
        public void setHasOval(boolean b) {
            hasoval = b;
        }
        
        public boolean getHasLine() {
            return hasline;
        }
        
        public void setHasLine(boolean b) {
            hasline = b;
        }
        
        public int getCursorX() {
            return cursorx;
        }
        
        public void setCursorX(int curx) {
            cursorx = curx;
        }
        
        public int getCursorY() {
            return cursory;
        }
        
        public void setCursorY(int cury) {
            cursory = cury;
        }
        
        public int getInitX() {
            return initx;
        }
        
        public void setInitX(int curx) {
            initx = curx;
        }
        
        public int getInitY() {
            return inity;
        }
        
        public void setInitY(int cury) {
            inity = cury;
        }
        
        
        public int getIndexSelect() {
            return indexselect;
        }
        
        public void setIndexSelect(int i) {
            indexselect = i;
        }
        
	/**
	 * Create the ImagePanel
	 *
	 * @param image: image to display
	 * @param name: name of the image
	 */
	public ImagePanel(BufferedImage img, String name, Controller ctr)
	{
            control = ctr;
            caretaker = new CareTaker();
            layers = new ArrayList();
            fileName = name;
            filePath = name;
            imgw = img.getWidth();
            imgh = img.getHeight();
            LayerPanel lay = new LayerPanel(img, fileName, 0, 0);
            layers.add(lay);
            zoom = 100;
            this.setPreferredSize(new Dimension(imgw+4, imgh+4));
            this.setSize(new Dimension(imgw+4, imgh+4));
            this.setOpaque(false);
            this.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 2, 3, 3, false));
	}

	/**
	 * Create the ImagePanel
	 *
	 * @param file: image to display
	 */
	public ImagePanel(File file, Controller ctr)
	{
            control = ctr;
            caretaker = new CareTaker();
            BufferedImage img = null;
            try
            {
                img = ImageIO.read(file);
                fileName = file.getName();
                filePath = file.getPath();
            } catch (IOException e)
            {
                    e.printStackTrace();
            }
            layers = new ArrayList();
            imgw = img.getWidth();
            imgh = img.getHeight();
            BufferedImage image = new BufferedImage(imgw, imgh, BufferedImage.TYPE_4BYTE_ABGR);
            image.getGraphics().drawImage(img, 0, 0, null);
            LayerPanel lay = new LayerPanel(image, file.getName(), 0, 0);
            layers.add(lay);
            zoom = 100;
            this.setPreferredSize(new Dimension(imgw+4, imgh+4));
            this.setSize(new Dimension(imgw+4, imgh+4));
            this.setOpaque(false);
            this.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 2, 3, 3, false));
	}
        
        public ImagePanel getImagePanelCopy() {
            ImagePanel imgcpy = new ImagePanel(control, caretaker);
            imgcpy.addMouseListener(control);
            imgcpy.addMouseWheelListener(control);
            imgcpy.setFileName(fileName);
            imgcpy.setFilePath(filePath);
            imgcpy.setCopyLayers(layers);
            imgcpy.resetSelectLay();
            imgcpy.setZoom(zoom);
            imgcpy.resetDims(imgw, imgh);
            imgcpy.setNbLays(nblays);
            imgcpy.setIndexSelect(indexselect);
            imgcpy.setInitX(initx);
            imgcpy.setInitY(inity);
            imgcpy.setCursorX(cursorx);
            imgcpy.setCursorY(cursory);
            imgcpy.setPreferredSize(new Dimension(imgw+4, imgh+4));
            imgcpy.setSize(new Dimension(imgw+4, imgh+4));
            imgcpy.setOpaque(false);
            imgcpy.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 2, 3, 3, false));
            return imgcpy;
        }
        
        public ImagePanel getImgSavedCopy() {
            ImagePanel imgcpy = new ImagePanel(control, caretaker);
            imgcpy.addMouseListener(control);
            imgcpy.addMouseWheelListener(control);
            imgcpy.setFileName(fileName);
            imgcpy.setFilePath(filePath);
            imgcpy.setSavedLayers(layers);
            imgcpy.resetSelectLay();
            imgcpy.setZoom(zoom);
            imgcpy.resetDims(imgw, imgh);
            imgcpy.setNbLays(nblays);
            imgcpy.setIndexSelect(indexselect);
            imgcpy.setInitX(initx);
            imgcpy.setInitY(inity);
            imgcpy.setCursorX(cursorx);
            imgcpy.setCursorY(cursory);
            imgcpy.setPreferredSize(new Dimension(imgw+4, imgh+4));
            imgcpy.setSize(new Dimension(imgw+4, imgh+4));
            imgcpy.setOpaque(false);
            imgcpy.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 2, 3, 3, false));
            return imgcpy;
        }
        
        public void setCopyLayers(ArrayList<LayerPanel> lays) {
            layers = new ArrayList();
            for (LayerPanel lp : lays)
                layers.add(lp.getLayerPanelCopy());
        }
        
        public void setSavedLayers(ArrayList<LayerPanel> lays) {
            layers = new ArrayList();
            for (LayerPanel lp : lays)
                layers.add(lp.getLaySavedCopy());
        }
        
        public int getNbLays() {
            return nblays;
        }
        
        public CareTaker getCareTaker() {
            return caretaker;
        }
        
        public void setNbLays(int nb) {
            nblays = nb;
        }
        
        public void resetDims(int rw, int rh) {
            imgw = rw;
            imgh = rh;
        }
        
        public void setFileName(String s) {
            fileName = s;
        }
        
        public void setFilePath(String s) {
            filePath = s;
        }
        
        public void newLayer(File file) {
            BufferedImage img = null;
            try
            {
                img = ImageIO.read(file);
            } catch (IOException e)
            {
                    e.printStackTrace();
            }
            BufferedImage layimg = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
            layimg.getGraphics().drawImage(img, 0, 0, null);
            LayerPanel lay = new LayerPanel(layimg, file.getName(), imgw/2-img.getWidth()/2, imgh/2-img.getHeight()/2);
            indexselect = layers.size();
            layers.add(lay);
        }
        
        public void newLayer(BufferedImage img) {
            nblays++;
            LayerPanel lay = new LayerPanel(img, "newlayer"+nblays, imgw/2-img.getWidth()/2, imgh/2-img.getHeight()/2);
            indexselect = layers.size();
            layers.add(lay);
        }
        
        public void newLayer(BufferedImage img, String layname) {
            LayerPanel lay = new LayerPanel(img, layname, imgw/2-img.getWidth()/2, imgh/2-img.getHeight()/2);
            indexselect = layers.size();
            layers.add(lay);
        }
        
        public void newLayer(BufferedImage img, int locx, int locy) {
            nblays++;
            LayerPanel lay = new LayerPanel(img, "newlayer"+nblays, locx, locy);
            indexselect = layers.size();
            layers.add(lay);
        }
        
        public void rotateSimule(float ang) {
            if (rotateproj) {
                /*double rot = Math.toRadians(ang);
                double s = Math.abs(Math.sin(rot));
                double c = Math.abs(Math.cos(rot));
                int w = image.getWidth();
                int h = image.getHeight();
                int nw = (int)Math.floor(w*c+h*s);
                int nh = (int)Math.floor(h*c+w*s);
                imgrot = new BufferedImage(nw, nh, image.getType());
                int r = 255;
                int g = 255;
                int b = 255;
                int a = 0;
                int col = (a << 24) | (r << 16) | (g << 8) | b;
                for (int i = 0; i < nw; i++)
                    for (int j = 0; j < nh; j++)
                        imgrot.setRGB(i, j, col);
                Graphics2D g2d = imgrot.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.translate((nw-w)/2, (nh-h)/2);
                g2d.rotate(rot, w/2, h/2);
                g2d.drawRenderedImage(image, null);
                g2d.dispose();
                this.setPreferredSize(new Dimension((int)((float)imgrot.getWidth()*zoom/100)+4, (int)((float)imgrot.getHeight()*zoom/100)+4));
                this.setSize(new Dimension((int)((float)imgrot.getWidth()*zoom/100)+4, (int)((float)imgrot.getHeight()*zoom/100)+4));*/
            }
            else {
                double rot = Math.toRadians(ang);
                double s = Math.abs(Math.sin(rot));
                double c = Math.abs(Math.cos(rot));
                int w = layers.get(indexselect).getImage().getWidth();
                int h = layers.get(indexselect).getImage().getHeight();
                int nw = (int)Math.floor(w*c+h*s);
                int nh = (int)Math.floor(h*c+w*s);
                imgrot = new BufferedImage(nw, nh, BufferedImage.TYPE_4BYTE_ABGR);
                int r = 255;
                int g = 255;
                int b = 255;
                int a = 0;
                int col = (a << 24) | (r << 16) | (g << 8) | b;
                for (int i = 0; i < nw; i++)
                    for (int j = 0; j < nh; j++)
                        imgrot.setRGB(i, j, col);
                Graphics2D g2d = imgrot.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.translate((nw-w)/2, (nh-h)/2);
                g2d.rotate(rot, w/2, h/2);
                g2d.drawRenderedImage(layers.get(indexselect).getImage(), null);
                rotx = layers.get(indexselect).getLocalX()-(nw-w)/2;
                roty = layers.get(indexselect).getLocalY()-(nh-h)/2;
                g2d.dispose();
            }

            this.repaint();
        }
        
        public void rotateExec(float ang) {
            if (rotateproj) {
                double rot = Math.toRadians(ang);
                double s = Math.abs(Math.sin(rot));
                double c = Math.abs(Math.cos(rot));
                int w, h, nw, nh, argb;
                BufferedImage laybuf;
                Graphics2D g2d;
                argb = new Color(255,255,255,0).getRGB();
                nw = (int)Math.floor(imgw*c+imgh*s);
                nh = (int)Math.floor(imgh*c+imgw*s);
                imgw = nw;
                imgh = nh;
                
                for (LayerPanel lp : layers) {
                    laybuf = lp.getImage();
                    w = laybuf.getWidth();
                    h = laybuf.getHeight();
                    nw = (int)Math.floor(w*c+h*s);
                    nh = (int)Math.floor(h*c+w*s);
                    imgrot = new BufferedImage(nw, nh, BufferedImage.TYPE_4BYTE_ABGR);
                    for (int i = 0; i < nw; i++)
                        for (int j = 0; j < nh; j++)
                            imgrot.setRGB(i, j, argb);
                    g2d = imgrot.createGraphics();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g2d.translate((nw-w)/2, (nh-h)/2);
                    g2d.rotate(rot, w/2, h/2);
                    g2d.drawRenderedImage(laybuf, null);
                    g2d.dispose();
                    lp.setImage(imgrot);
                }
                this.updateZoomImg();
                this.getParent().getParent().validate();
            }
            else {
                double rot = Math.toRadians(ang);
                double s = Math.abs(Math.sin(rot));
                double c = Math.abs(Math.cos(rot));
                int w = layers.get(indexselect).getImage().getWidth();
                int h = layers.get(indexselect).getImage().getHeight();
                int nw = (int)Math.floor(w*c+h*s);
                int nh = (int)Math.floor(h*c+w*s);
                imgrot = new BufferedImage(nw, nh, BufferedImage.TYPE_4BYTE_ABGR);
                int r = 255;
                int g = 255;
                int b = 255;
                int a = 0;
                int col = (a << 24) | (r << 16) | (g << 8) | b;
                for (int i = 0; i < nw; i++)
                    for (int j = 0; j < nh; j++)
                        imgrot.setRGB(i, j, col);
                Graphics2D g2d = imgrot.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.translate((nw-w)/2, (nh-h)/2);
                g2d.rotate(rot, w/2, h/2);
                g2d.drawRenderedImage(layers.get(indexselect).getImage(), null);
                g2d.dispose();
                layers.get(indexselect).setLocalX(layers.get(indexselect).getLocalX()-(nw-w)/2);
                layers.get(indexselect).setLocalY(layers.get(indexselect).getLocalY()-(nh-h)/2);
                layers.get(indexselect).setImage(imgrot);
            }
            this.updateZoomImg();
            this.getParent().getParent().validate();
        }
        
        public void resizeExec(int nw, int nh) {
            if (resizeproj) {
                double ratiow = (double)nw/(double)imgw;
                double ratioh = (double)nh/(double)imgh;
                imgw = nw;
                imgh = nh;
                int nx, lw;
                int ny, lh;
                BufferedImage nimg;
                Graphics2D g2d;
                for (LayerPanel lp : layers) {
                    lw = lp.getImage().getWidth();
                    lh = lp.getImage().getHeight();
                    nw = (int)((double)lw*ratiow);
                    nh = (int)((double)lh*ratioh);
                    lp.setLocalX((int)((double)lp.getLocalX()*ratiow));
                    lp.setLocalY((int)((double)lp.getLocalY()*ratioh));
                    nimg = new BufferedImage(nw, nh, BufferedImage.TYPE_4BYTE_ABGR);
                    g2d = nimg.createGraphics();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g2d.drawImage(lp.getImage(),0,0, nw, nh, null);
                    g2d.dispose();
                    lp.setImage(nimg);
                }
                this.updateZoomImg();
                this.getParent().getParent().validate();
            }
            else {
                BufferedImage nimg = new BufferedImage(nw, nh, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D g2d = nimg.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.drawImage(layers.get(indexselect).getImage(),0,0, nw, nh, null);
                g2d.dispose();
                layers.get(indexselect).setImage(nimg);
                this.updateZoomImg();
                this.getParent().getParent().validate();
            }
        }
        
        public void updateZoomImg() {
            /*if (hasrot) {
                this.setPreferredSize(new Dimension((int)((float)imgrot.getWidth()*zoom/100)+4, (int)((float)imgrot.getHeight()*zoom/100)+4));
                this.setSize(new Dimension((int)((float)imgrot.getWidth()*zoom/100)+4, (int)((float)imgrot.getHeight()*zoom/100)+4));
            }
            else
            {*/
                this.setPreferredSize(new Dimension((int)((float)imgw*zoom/100)+4, (int)((float)imgh*zoom/100)+4));
                this.setSize(new Dimension((int)((float)imgw*zoom/100)+4, (int)((float)imgh*zoom/100)+4));
           // }
        }
        
        public void updateRotLoc() {
            int w = layers.get(indexselect).getImage().getWidth();
            int h = layers.get(indexselect).getImage().getHeight();
            int nw = imgrot.getWidth();
            int nh = imgrot.getHeight();
            rotx = layers.get(indexselect).getLocalX()-(nw-w)/2;
            roty = layers.get(indexselect).getLocalY()-(nh-h)/2;
        }

	/**
	 * Create the bufferImage after deserialization.
	 */
	/*public void buildImage()
	{
		image = new BufferedImage(width, height, imageType);
		image.setRGB(0, 0, width, height, pixels, 0, width);
	}*/

	@Override
	public void paintComponent(Graphics g)
	{
            super.paintComponent(g);
            int size = (int)((float)control.getMainView().getToolsView().getSLtoolSize().getValue()*zoom/100);
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

            if (hasrot && false) {

            }
            else {
                if (layers.get(indexselect) != null) {
                    for (LayerPanel lp : layers) {
                        if (lp == layers.get(indexselect)) {
                            if (lp.getIsShowed()) {
                                if (imgrot != null && hasrot) {
                                    g.drawImage(imgrot, (int)((float)rotx*zoom/100)+2, (int)((float)roty*zoom/100)+2, (int)((float)imgrot.getWidth()*zoom/100), (int)((float)imgrot.getHeight()*zoom/100), null);
                                    g.setColor(Color.BLACK);
                                    g.drawRect((int)((float)rotx*zoom/100), (int)((float)roty*zoom/100), (int)((float)imgrot.getWidth()*zoom/100)+3, (int)((float)imgrot.getHeight()*zoom/100)+3);
                                    g.drawRect((int)((float)rotx*zoom/100)+1, (int)((float)roty*zoom/100)+1, (int)((float)imgrot.getWidth()*zoom/100)+1, (int)((float)imgrot.getHeight()*zoom/100)+1);
                                }
                                else if (imgcol != null && (hasbalance || hasbright)) {
                                    g.drawImage(imgcol, (int)((float)lp.getLocalX()*zoom/100)+2, (int)((float)lp.getLocalY()*zoom/100)+2, (int)((float)imgcol.getWidth()*zoom/100), (int)((float)imgcol.getHeight()*zoom/100), null);
                                }
                                else {
                                    if (hasmove)
                                        g.drawImage(lp.getImage(), (int)((float)lp.getLocalX()*zoom/100)+cursorx-initx+2, (int)((float)lp.getLocalY()*zoom/100)+cursory-inity+2, (int)((float)lp.getImage().getWidth()*zoom/100), (int)((float)lp.getImage().getHeight()*zoom/100), null);
                                    else
                                        g.drawImage(lp.getImage(), (int)((float)lp.getLocalX()*zoom/100)+2, (int)((float)lp.getLocalY()*zoom/100)+2, (int)((float)lp.getImage().getWidth()*zoom/100), (int)((float)lp.getImage().getHeight()*zoom/100), null);
                                }
                            }
                        }
                        else {
                            if (lp.getIsShowed())
                                g.drawImage(lp.getImage(), (int)((float)lp.getLocalX()*zoom/100)+2, (int)((float)lp.getLocalY()*zoom/100)+2, (int)((float)lp.getImage().getWidth()*zoom/100), (int)((float)lp.getImage().getHeight()*zoom/100), null);
                        }
                    }

                    g.setColor(Color.RED);
                    g.drawRect((int)((float)layers.get(indexselect).getLocalX()*zoom/100), (int)((float)layers.get(indexselect).getLocalY()*zoom/100), (int)((float)layers.get(indexselect).getImage().getWidth()*zoom/100)+3, (int)((float)layers.get(indexselect).getImage().getHeight()*zoom/100)+3);
                    g.drawRect((int)((float)layers.get(indexselect).getLocalX()*zoom/100)+1, (int)((float)layers.get(indexselect).getLocalY()*zoom/100)+1, (int)((float)layers.get(indexselect).getImage().getWidth()*zoom/100)+1, (int)((float)layers.get(indexselect).getImage().getHeight()*zoom/100)+1); 
                    
                    if (hastext) {
                        g.setColor(control.getCol());
                        g.setFont(control.getTextFont());
                        g.drawString(control.getTextAdd(), textx, texty);
                    }
                    
                    g.setColor(Color.BLACK);
                    if (control.getHaspaint()) {
                        g.drawOval(cursorx-size/2, cursory-size/2, size, size);
                    }
                    else if (control.getHasdraw() || control.getHasclear()) {
                        g.drawRect(cursorx-size/2, cursory-size/2, size, size);
                    }
                    else if (control.getHasstamp()) {
                        g.drawRect(cursorx-(int)((float)control.getImgSelect().getWidth()/2*zoom/100), cursory-(int)((float)control.getImgSelect().getHeight()/2*zoom/100), (int)((float)control.getImgSelect().getWidth()*zoom/100), (int)((float)control.getImgSelect().getHeight()*zoom/100));
                    }
                    else if (hasrect) {
                        g.setColor(control.getCol());
                        int ix = (initx >= cursorx) ? cursorx : initx;
                        int iy = (inity >= cursory) ? cursory : inity;
                        g.fillRect(ix, iy, Math.abs(initx-cursorx), Math.abs(inity-cursory));
                    }
                    else if (hasoval) {
                        g.setColor(control.getCol());
                        int ix = (initx >= cursorx) ? cursorx : initx;
                        int iy = (inity >= cursory) ? cursory : inity;
                        g.fillOval(ix, iy, Math.abs(initx-cursorx), Math.abs(inity-cursory));
                    }
                    else if (hasline) {
                        g.setColor(control.getCol());
                        g.drawLine(initx, inity, cursorx, cursory);
                    }
                    else if (hascut || hasselect) {
                        g.setColor(Color.BLACK);
                        int ix = (initx >= cursorx) ? cursorx : initx;
                        int iy = (inity >= cursory) ? cursory : inity;
                        g.drawRect(ix, iy, Math.abs(initx-cursorx), Math.abs(inity-cursory));
                    }
                }
            }
	}

	public String getFileName()
	{
		return fileName;
	}
        
        public String getFilePath() {
            return filePath;
        }

        public void setThread(Thread thr) {
            th = thr;
        }
        
        public Thread getThread() {
            return th;
        }
        
        public float getZoom() {
            return zoom;
        }
        
        public void setZoom(float z) {
            zoom = z;
        }
        
        public void setHasRot(boolean hasr) {
            hasrot = hasr;
            /*if (hasrot)
                imgrot = image;*/
        }
        
        public boolean getHasResize() {
            return hasresize;
        }
        
        public void setHasResize(boolean hasr) {
            hasresize = hasr;
        }
        
        public void setHasText(boolean b) {
            hastext = b;
        }
        
        public boolean getHasText() {
            return hastext;
        }
        
        public boolean getHasRot() {
            return hasrot;
        }
        
        public int getImgW() {
            return imgw;
        }
        
        public int getImgH() {
            return imgh;
        }
        
        public ArrayList<LayerPanel> getLayers() {
            return layers;
        }
        
        public boolean getResizeProj() {
            return resizeproj;
        }
        
        public boolean getRotateProj() {
            return rotateproj;
        }
        
        
        public void setResizeProj(boolean b) {
            resizeproj = b;
        }
        
        public void setRotateProj(boolean b) {
            rotateproj = b;
        }
        
        public void setTextX(int tx) {
            textx = tx;
        }
        
        public void setTextY(int ty) {
            texty = ty;
        }
        
        public int getTextX() {
            return textx;
        }
        
        public int getTextY() {
            return texty;
        }
        
        public void setLayers(ArrayList<LayerPanel> lays) {
            layers = lays;
        }
        
        public BufferedImage getBuiltImage() {
            BufferedImage img = new BufferedImage(imgw,imgh,BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g2d = img.createGraphics();
            for (LayerPanel lp : layers) {
                g2d.drawImage(lp.getImage(), lp.getLocalX(), lp.getLocalY(), lp.getImage().getWidth(), lp.getImage().getHeight(), null);
            }
            return img;
        }
        
        public void cutImg() {
            int ix = (int)((float)initx*100/zoom);
            int fx = (int)((float)cursorx*100/zoom);
            fx = (fx > imgw-1) ? imgw-1 : fx;
            fx = (fx < 0) ? 0 : fx;
            int iy = (int)((float)inity*100/zoom);
            int fy = (int)((float)cursory*100/zoom);
            fy = (fy > imgh-1) ? imgh-1 : fy;
            fy = (fy < 0) ? 0 : fy;
            int sx = (ix >= fx) ? fx : ix;
            int sy = (iy >= fy) ? fy : iy;
            imgw = Math.abs(ix-fx);
            imgh = Math.abs(iy-fy);
            this.setPreferredSize(new Dimension(imgw+4, imgh+4));
            this.setSize(new Dimension(imgw+4, imgh+4));
            for (LayerPanel lp : layers) {
                lp.setLocalX(lp.getLocalX()-sx);
                lp.setLocalY(lp.getLocalY()-sy);
            }
        }
}
