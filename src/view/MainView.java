/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.MenuElement;
import model.FilterThread;
import plugin.IPlugin;

/**
 *
 * @author Tommy
 */
public class MainView extends javax.swing.JFrame implements Observer {
    public static final Icon CLOSE_TAB_ICON1 = new ImageIcon("src/images/closeTabButton1.png");
    public static final Icon CLOSE_TAB_ICON2 = new ImageIcon("src/images/closeTabButton2.png");  
    private Controller control;
    private ArrayList<ImagePanel> projects = new ArrayList();
    private RotationView rotview = null;
    private NewView newview = null;
    private ResizeView resizeview = null;
    private LaysHistView laysview = null;
    private TextView textview = null;
    private ToolsView toolsview = null;
    private ColorBalanceView colbalview = null;
    private BrightnessView brightview = null;
    private int nbnewproj = 0;

    /**
     * Creates new form MainView
     */
    public MainView() {
        initComponents();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        control = new Controller(this);
        this.addWindowListener(control);
        TPtabs.addChangeListener(control);
        TPtabs.addMouseListener(control);
        TPtabs.addMouseWheelListener(control);
        InputMap im = TPtabs.getInputMap();
        KeyStroke left = KeyStroke.getKeyStroke("LEFT");
        KeyStroke right = KeyStroke.getKeyStroke("RIGHT");
        im.put(left, "none");
        im.put(right, "none");
        TPtabs.addKeyListener(control);
        MIopen.addActionListener(control);
        MIopenlayer.addActionListener(control);
        MInewlayer.addActionListener(control);
        MIclose.addActionListener(control);
        MIcloseall.addActionListener(control);
        MIquit.addActionListener(control);
        MInew.addActionListener(control);
        MIresize.addActionListener(control);
        MItools.addActionListener(control);
        MIlayers.addActionListener(control);
        Bstopfilter.addActionListener(control);
        MIrotation.addActionListener(control);
        MIlayRotate.addActionListener(control);
        MIlayResize.addActionListener(control);
        Spzoom.addChangeListener(control);
        MIhistory.addActionListener(control);
        MIundo.addActionListener(control);
        MIredo.addActionListener(control);
        MIsave.addActionListener(control);
        MIcopy.addActionListener(control);
        MIcut.addActionListener(control);
        MIpaste.addActionListener(control);
        MIopenurl.addActionListener(control);
        MIlayerurl.addActionListener(control);
        MIselectall.addActionListener(control);
        MIselectall.setEnabled(false);
        MIredo.setEnabled(false);
        MIundo.setEnabled(false);
        Lzoom.setEnabled(false);
        Spzoom.setEnabled(false);
        Lzoom2.setEnabled(false);
        Bstopfilter.setEnabled(false);
        MIsave.setEnabled(false);
        MIclose.setEnabled(false);
        MIcloseall.setEnabled(false);
        MIrotation.setEnabled(false);
        MIresize.setEnabled(false);
        MIopenlayer.setEnabled(false);
        MInewlayer.setEnabled(false);
        MIlayResize.setEnabled(false);
        MIlayRotate.setEnabled(false);
        MInew.setIcon(new ImageIcon("src/images/new.png"));
        MIopen.setIcon(new ImageIcon("src/images/open.png"));
        MIsave.setIcon(new ImageIcon("src/images/save.png"));
        MIclose.setIcon(new ImageIcon("src/images/close.png"));
        MIcloseall.setIcon(new ImageIcon("src/images/closeall.png"));
        MIquit.setIcon(new ImageIcon("src/images/quit.png"));
        MIrotation.setIcon(new ImageIcon("src/images/rotate.png"));
        MIresize.setIcon(new ImageIcon("src/images/resize.png"));
        MIopenlayer.setIcon(new ImageIcon("src/images/open.png"));
        MInewlayer.setIcon(new ImageIcon("src/images/layers.png"));
        MIlayers.setIcon(new ImageIcon("src/images/layers.png"));
        MItools.setIcon(new ImageIcon("src/images/tools.png"));
        MIlayRotate.setIcon(new ImageIcon("src/images/rotate.png"));
        MIlayResize.setIcon(new ImageIcon("src/images/resize.png"));
        MIhistory.setIcon(new ImageIcon("src/images/history.png"));
        MIundo.setIcon(new ImageIcon("src/images/undo.png"));
        MIredo.setIcon(new ImageIcon("src/images/redo.png"));
        MIcopy.setIcon(new ImageIcon("src/images/copy.png"));
        MIpaste.setIcon(new ImageIcon("src/images/paste.png"));
        MIcut.setIcon(new ImageIcon("src/images/cutbis.png"));
        MIopenurl.setIcon(new ImageIcon("src/images/url.png"));
        MIlayerurl.setIcon(new ImageIcon("src/images/url.png"));
        MIselectall.setIcon(new ImageIcon("src/images/selectall.png"));
        MIprint.setIcon(new ImageIcon("src/images/print.png"));
        MIbalance.setIcon(new ImageIcon("src/images/color.png"));
        MIbalance.addActionListener(control);
        MIbalance.setEnabled(false);
        MIbright.setIcon(new ImageIcon("src/images/brightness.png"));
        MIbright.addActionListener(control);
        MIbright.setEnabled(false);
        Lx.setText("x: 0");
        Ly.setText("y: 0");
        Lx.setEnabled(false);
        Ly.setEnabled(false);
        MIlayerurl.setEnabled(false);
        newview = new NewView(this,true);
        newview.addWindowListener(control);
        rotview = new RotationView(this,false);
        rotview.addWindowListener(control);
        resizeview = new ResizeView(this,false);
        resizeview.addWindowListener(control);
        laysview = new LaysHistView(this,false);
        laysview.addWindowListener(control);
        textview = new TextView(this,false);
        textview.addWindowListener(control);
        toolsview = new ToolsView(this,false);
        toolsview.addWindowListener(control);
        colbalview = new ColorBalanceView(this,true);
        colbalview.addWindowListener(control);
        brightview = new BrightnessView(this,true);
        brightview.addWindowListener(control);
        MIcopy.setEnabled(false);
        MIpaste.setEnabled(false);
        MIcut.setEnabled(false);
        laysview.setVisible(true);
        toolsview.setVisible(true);
        MIprint.setEnabled(false);
        MIprint.addActionListener(control);
        this.setTitle("MyPhotoshop - Tommy Lopes (lopes_t) - EPITA ING1 2016");
        this.setIconImage(new ImageIcon("src/images/icon.png").getImage());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Lzoom = new javax.swing.JLabel();
        Lzoom2 = new javax.swing.JLabel();
        Bstopfilter = new javax.swing.JButton();
        TPtabs = new view.MyTabbedPane();
        Spzoom = new javax.swing.JSpinner();
        Lx = new javax.swing.JLabel();
        Ly = new javax.swing.JLabel();
        MenuBar = new javax.swing.JMenuBar();
        Mfile = new javax.swing.JMenu();
        MInew = new javax.swing.JMenuItem();
        MInewlayer = new javax.swing.JMenuItem();
        MIopen = new javax.swing.JMenuItem();
        MIopenlayer = new javax.swing.JMenuItem();
        MIopenurl = new javax.swing.JMenuItem();
        MIlayerurl = new javax.swing.JMenuItem();
        MIsave = new javax.swing.JMenuItem();
        MIprint = new javax.swing.JMenuItem();
        MIclose = new javax.swing.JMenuItem();
        MIcloseall = new javax.swing.JMenuItem();
        MIquit = new javax.swing.JMenuItem();
        Medit = new javax.swing.JMenu();
        MIundo = new javax.swing.JMenuItem();
        MIredo = new javax.swing.JMenuItem();
        MIcopy = new javax.swing.JMenuItem();
        MIcut = new javax.swing.JMenuItem();
        MIpaste = new javax.swing.JMenuItem();
        MIselectall = new javax.swing.JMenuItem();
        Mimage = new javax.swing.JMenu();
        MIresize = new javax.swing.JMenuItem();
        MIrotation = new javax.swing.JMenuItem();
        Mlayer = new javax.swing.JMenu();
        MIlayResize = new javax.swing.JMenuItem();
        MIlayRotate = new javax.swing.JMenuItem();
        MIbalance = new javax.swing.JMenuItem();
        MIbright = new javax.swing.JMenuItem();
        Mfilters = new javax.swing.JMenu();
        Mwindows = new javax.swing.JMenu();
        MItools = new javax.swing.JMenuItem();
        MIlayers = new javax.swing.JMenuItem();
        MIhistory = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(400, 200));

        Lzoom.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        Lzoom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lzoom.setText("zoom");

        Lzoom2.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        Lzoom2.setText("%");

        Bstopfilter.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        Bstopfilter.setText("stop filter");
        Bstopfilter.setToolTipText("stop filter execution");

        Spzoom.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        Spzoom.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(100.0f), Float.valueOf(10.0f), Float.valueOf(10000.0f), Float.valueOf(1.0f)));

        Lx.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        Ly.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        MenuBar.setToolTipText("");
        MenuBar.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        Mfile.setMnemonic('F');
        Mfile.setText("File");
        Mfile.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        MInew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        MInew.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MInew.setText("New image");
        MInew.setToolTipText("create new project");
        Mfile.add(MInew);

        MInewlayer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        MInewlayer.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MInewlayer.setText("New layer");
        Mfile.add(MInewlayer);

        MIopen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        MIopen.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIopen.setText("Open");
        MIopen.setToolTipText("open existing image or project");
        Mfile.add(MIopen);

        MIopenlayer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        MIopenlayer.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIopenlayer.setText("Open as layer");
        Mfile.add(MIopenlayer);

        MIopenurl.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        MIopenurl.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIopenurl.setText("Open from URL");
        Mfile.add(MIopenurl);

        MIlayerurl.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        MIlayerurl.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIlayerurl.setText("Open layer from URL");
        Mfile.add(MIlayerurl);

        MIsave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        MIsave.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIsave.setText("Save");
        MIsave.setToolTipText("save current image or project");
        Mfile.add(MIsave);

        MIprint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        MIprint.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIprint.setText("Print");
        Mfile.add(MIprint);

        MIclose.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        MIclose.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIclose.setText("Close");
        MIclose.setToolTipText("close current project");
        Mfile.add(MIclose);

        MIcloseall.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        MIcloseall.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIcloseall.setText("Close all");
        MIcloseall.setToolTipText("close all projects");
        Mfile.add(MIcloseall);

        MIquit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        MIquit.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIquit.setText("Quit");
        MIquit.setToolTipText("quit all projects and application");
        MIquit.setVerifyInputWhenFocusTarget(false);
        Mfile.add(MIquit);
        MIquit.getAccessibleContext().setAccessibleDescription("");

        MenuBar.add(Mfile);

        Medit.setMnemonic('E');
        Medit.setText("Edit");
        Medit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Medit.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        MIundo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        MIundo.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIundo.setText("Undo");
        Medit.add(MIundo);

        MIredo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        MIredo.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIredo.setText("Redo");
        Medit.add(MIredo);

        MIcopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        MIcopy.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIcopy.setText("Copy");
        Medit.add(MIcopy);

        MIcut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        MIcut.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIcut.setText("Cut");
        Medit.add(MIcut);

        MIpaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        MIpaste.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIpaste.setText("Paste");
        Medit.add(MIpaste);

        MIselectall.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        MIselectall.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIselectall.setText("Select all");
        Medit.add(MIselectall);

        MenuBar.add(Medit);

        Mimage.setMnemonic('I');
        Mimage.setText("Image");
        Mimage.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        MIresize.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIresize.setText("Resize");
        Mimage.add(MIresize);

        MIrotation.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIrotation.setText("Rotate");
        Mimage.add(MIrotation);

        MenuBar.add(Mimage);

        Mlayer.setMnemonic('L');
        Mlayer.setText("Layer");
        Mlayer.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        MIlayResize.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIlayResize.setText("Resize");
        Mlayer.add(MIlayResize);

        MIlayRotate.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIlayRotate.setText("Rotate");
        Mlayer.add(MIlayRotate);

        MIbalance.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIbalance.setText("Color balance - Alpha");
        Mlayer.add(MIbalance);

        MIbright.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIbright.setText("Brightness - Contrast");
        Mlayer.add(MIbright);

        MenuBar.add(Mlayer);

        Mfilters.setMnemonic('t');
        Mfilters.setText("Filters");
        Mfilters.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MenuBar.add(Mfilters);

        Mwindows.setMnemonic('w');
        Mwindows.setText("Windows");
        Mwindows.setToolTipText("");
        Mwindows.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        MItools.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        MItools.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MItools.setText("Tools");
        Mwindows.add(MItools);

        MIlayers.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        MIlayers.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIlayers.setText("Layers");
        Mwindows.add(MIlayers);

        MIhistory.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        MIhistory.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        MIhistory.setText("History");
        Mwindows.add(MIhistory);

        MenuBar.add(Mwindows);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Lzoom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Spzoom, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Lzoom2)
                .addGap(35, 35, 35)
                .addComponent(Bstopfilter)
                .addGap(47, 47, 47)
                .addComponent(Lx, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Ly, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(518, Short.MAX_VALUE))
            .addComponent(TPtabs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(TPtabs, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Lzoom)
                    .addComponent(Spzoom)
                    .addComponent(Lzoom2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Bstopfilter, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Lx, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(Ly, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }
    
    public final void addClosableTab(final MyTabbedPane tabbedPane, final JComponent c, MyPanel pan, final String title,
                               final Icon icon) {
        if (tabbedPane.getSelectedIndex() == -1) {
            this.enableOnAdding();
        }
        tabbedPane.addTab(null, c);
        final int pos = tabbedPane.indexOfComponent(c);
        FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);
        final JPanel pnlTab = new JPanel(f);
        pnlTab.setOpaque(false);
        final JLabel lblTitle = new JLabel(title);
        final JButton btnClose = new JButton();
        btnClose.setOpaque(false);
        btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClose.setIcon(CLOSE_TAB_ICON1);
        btnClose.setBorder(null);
        btnClose.setFocusable(false);
        pnlTab.add(lblTitle);
        pnlTab.add(btnClose);
        pan.setBclose(btnClose);
        pan.setTitle(lblTitle);
        pnlTab.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        tabbedPane.setTabComponentAt(pos, pnlTab);
        
        ActionListener alistener = new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int option = (new JOptionPane()).showConfirmDialog(null, "Are you sure about closing the current project?" + System.lineSeparator() + "Be sure to save it before closing.", "Close project - MyPhotoshop", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION)
            {
                int index = tabbedPane.indexOfComponent(c);
                if (projects.get(index).getHasRot()) {
                    rotview.setVisible(false);
                    projects.get(index).setHasRot(false);
                }
                if (projects.get(index).getHasResize()) {
                    resizeview.setVisible(false);
                    projects.get(index).setHasResize(false);
                } 
                projects.remove(index);
                tabbedPane.remove(c);
                if (tabbedPane.getSelectedIndex() == -1) {
                    disableOnClosing();
                    laysview.getBlaysView().setSelected(false);
                    laysview.getModelHistory().clear();
                    laysview.getModelLayers().clear();
                    laysview.getLIhistory().repaint();
                    laysview.getLIlayers().repaint();
                }
            }
          }
        };
        
        MouseListener mlistener = new MouseListener() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnClose.setIcon(CLOSE_TAB_ICON2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnClose.setIcon(CLOSE_TAB_ICON1);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getComponent() == lblTitle)
                    tabbedPane.setSelectedComponent(c);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        };
        
        btnClose.addActionListener(alistener);
        btnClose.addMouseListener(mlistener);
        pnlTab.addMouseListener(mlistener);
        lblTitle.addMouseListener(mlistener);

        // Optionally bring the new tab to the front
        tabbedPane.setSelectedComponent(c);
    }
    
    public void updateWindow() {
        if (TPtabs.getSelectedIndex() == -1) {
            this.setTitle("MyPhotoshop - Tommy Lopes (lopes_t) - EPITA ING1 2016");
            this.setIconImage(new ImageIcon("src/images/icon.png").getImage());
        }
        else {
            ImagePanel img = projects.get(TPtabs.getSelectedIndex());
            this.setTitle("MyPhotoshop - " + img.getFileName() + " (" + img.getImgW() + "x" + img.getImgH() + ")");
            this.setIconImage(img.getBuiltImage());
        }  
    }
    
    public JLabel getLx() {
        return Lx;
    }
    
    public JLabel getLy() {
        return Ly;
    }
    
    public void loadFilters() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        control.loadFilters();
    }
    
    public JMenuItem getMIcopy() {
        return MIcopy;
    }
    
    public JMenuItem getMIbalance() {
        return MIbalance;
    }
    
    public JMenuItem getMIprint() {
        return MIprint;
    }
    
    public JMenuItem getMIopenurl() {
        return MIopenurl;
    }
    
    public JMenuItem getMIlayerurl() {
        return MIlayerurl;
    }
    
    public JMenuItem getMIselectall() {
        return MIselectall;
    }
    
    public JMenuItem getMIpaste() {
        return MIpaste;
    }
    
    public JMenuItem getMIcut() {
        return MIcut;
    }
    
    public JMenuItem getMenuopen() {
        return MIopen;
    }
    
    public JMenuItem getMIopenlayer() {
        return MIopenlayer;
    }
    
    public JMenuItem getMInewlayer() {
        return MInewlayer;
    }
    
    public JMenuItem getMIhistory() {
        return MIhistory;
    }
    
    public JMenuItem getMIlayers() {
        return MIlayers;
    }
    
    public JMenuItem getMItools() {
        return MItools;
    }
    
    public JMenu getMenufilters() {
        return Mfilters;
    }
    
    public MyTabbedPane getTabs() {
        return TPtabs;
    }
    
    public ArrayList<ImagePanel> getProjects() {
        return projects;
    }
    
    public JButton getStopfilter() {
        return Bstopfilter;
    }
    
    public Controller getController() {
        return control;
    }
    
    public JSpinner getSpangle() {
        return rotview.getSpangle();
    }
    
    public JSlider getSlangle() {
        return rotview.getSlangle();
    }
    
    public JSpinner getSpzoom() {
        return Spzoom;
    }
    
    public JLabel getLzoom() {
        return Lzoom;
    }
    
    public JMenuItem getMIrotation() {
        return MIrotation;
    }
    
    public JMenuItem getMIresize() {
        return MIresize;
    }
    
    public JMenuItem getMIbright() {
        return MIbright;
    }
    
    public JMenuItem getMIlayResize() {
        return MIlayResize;
    }
    
    public JMenuItem getMIlayRotate() {
        return MIlayRotate;
    }
    
    public void setRotView(RotationView rotv) {
        rotview = rotv;
    }
    
    public BrightnessView getBrightView() {
        return brightview;
    }
    
    public RotationView getRotView() {
        return rotview;
    }
    
    public ToolsView getToolsView() {
        return toolsview;
    }
    
    public void setResizeView(ResizeView resizev) {
        resizeview = resizev;
    }
    
    public ResizeView getResizeView() {
        return resizeview;
    }
    
    public ColorBalanceView getColorBalanceView() {
        return colbalview;
    }
    
    public JMenuItem getMInew() {
        return MInew;
    }
    
    public JMenuItem getMIclose() {
        return MIclose;
    }
    
    public JMenuItem getMIcloseall() {
        return MIcloseall;
    }
    
    public JMenuItem getMIquit() {
        return MIquit;
    }
    
    public JMenuItem getMIundo() {
        return MIundo;
    }
    
    public JMenuItem getMIredo() {
        return MIredo;
    }
    
    public JMenuItem getMIsave() {
        return MIsave;
    }
    
    public NewView getNewView() {
        return newview;
    }
    
    public int getNbnewproj() {
        return nbnewproj;
    }
    
    public void incNbnewproj() {
        nbnewproj++;
    }
    
    public LaysHistView getLaysView() {
        return laysview;
    }
    
    public TextView getTextView() {
        return textview;
    }
    
    public JButton getBstopfilter() {
        return Bstopfilter;
    }
    
    public void enableOnAdding() {
        Lzoom.setEnabled(true);
        Spzoom.setEnabled(true);
        Lzoom2.setEnabled(true);
        Lx.setEnabled(true);
        Ly.setEnabled(true);
        MenuElement me = Mfilters.getSubElements()[0];
        for (MenuElement m : me.getSubElements()) {
            ((JMenuItem)m).setEnabled(true);
        }
        MIsave.setEnabled(true);
        MIclose.setEnabled(true);
        MIcloseall.setEnabled(true);
        MIrotation.setEnabled(true);
        MIresize.setEnabled(true);
        MIopenlayer.setEnabled(true);
        MInewlayer.setEnabled(true);
        MIlayResize.setEnabled(true);
        MIlayRotate.setEnabled(true);
        MIredo.setEnabled(true);
        MIundo.setEnabled(true);
        MIcopy.setEnabled(true);
        MIpaste.setEnabled(true);
        MIcut.setEnabled(true);
        MIlayerurl.setEnabled(true);
        MIselectall.setEnabled(true);
        MIprint.setEnabled(true);
        MIbalance.setEnabled(true);
        MIbright.setEnabled(true);
        laysview.getSLlaysAlpha().setValue(100);
    }
    
    public void disableOnClosing() {
        Lzoom.setEnabled(false);
        Spzoom.setEnabled(false);
        Lzoom2.setEnabled(false);
        Lx.setEnabled(false);
        Ly.setEnabled(false);
        MenuElement me = Mfilters.getSubElements()[0];
        for (MenuElement m : me.getSubElements()) {
            ((JMenuItem)m).setEnabled(false);
        }
        MIsave.setEnabled(false);
        MIclose.setEnabled(false);
        MIcloseall.setEnabled(false);
        Bstopfilter.setEnabled(false);
        MIrotation.setEnabled(false);
        MIresize.setEnabled(false);
        MIopenlayer.setEnabled(false);
        MInewlayer.setEnabled(false);
        MIlayResize.setEnabled(false);
        MIlayRotate.setEnabled(false);
        MIredo.setEnabled(false);
        MIundo.setEnabled(false);
        MIcopy.setEnabled(false);
        MIpaste.setEnabled(false);
        MIcut.setEnabled(false);
        MIlayerurl.setEnabled(false);
        MIselectall.setEnabled(false);
        MIprint.setEnabled(false);
        MIbalance.setEnabled(false);
        MIbright.setEnabled(false);
        laysview.getSLlaysAlpha().setValue(100);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bstopfilter;
    private javax.swing.JLabel Lx;
    private javax.swing.JLabel Ly;
    private javax.swing.JLabel Lzoom;
    private javax.swing.JLabel Lzoom2;
    private javax.swing.JMenuItem MIbalance;
    private javax.swing.JMenuItem MIbright;
    private javax.swing.JMenuItem MIclose;
    private javax.swing.JMenuItem MIcloseall;
    private javax.swing.JMenuItem MIcopy;
    private javax.swing.JMenuItem MIcut;
    private javax.swing.JMenuItem MIhistory;
    private javax.swing.JMenuItem MIlayResize;
    private javax.swing.JMenuItem MIlayRotate;
    private javax.swing.JMenuItem MIlayers;
    private javax.swing.JMenuItem MIlayerurl;
    private javax.swing.JMenuItem MInew;
    private javax.swing.JMenuItem MInewlayer;
    private javax.swing.JMenuItem MIopen;
    private javax.swing.JMenuItem MIopenlayer;
    private javax.swing.JMenuItem MIopenurl;
    private javax.swing.JMenuItem MIpaste;
    private javax.swing.JMenuItem MIprint;
    private javax.swing.JMenuItem MIquit;
    private javax.swing.JMenuItem MIredo;
    private javax.swing.JMenuItem MIresize;
    private javax.swing.JMenuItem MIrotation;
    private javax.swing.JMenuItem MIsave;
    private javax.swing.JMenuItem MIselectall;
    private javax.swing.JMenuItem MItools;
    private javax.swing.JMenuItem MIundo;
    private javax.swing.JMenu Medit;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenu Mfile;
    private javax.swing.JMenu Mfilters;
    private javax.swing.JMenu Mimage;
    private javax.swing.JMenu Mlayer;
    private javax.swing.JMenu Mwindows;
    private javax.swing.JSpinner Spzoom;
    private view.MyTabbedPane TPtabs;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable obs, Object o) {
        JMenuItem mi = null;
        IPlugin plug = null;
        if (o == control.getClasses()) {
            for (Class c : control.getClasses().values()) {
                try {
                    plug = (IPlugin)c.newInstance();
                } catch (InstantiationException ex) {
                    Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
                }
                mi = new JMenuItem(plug.getName());
                mi.setFont(new Font("Times New Roman", 0, 12));
                mi.addActionListener(control);
                mi.setEnabled(false);
                Mfilters.add(mi);
            }
        }
        else if (o instanceof IPlugin) {
            plug = (IPlugin)o;
            if (TPtabs.getSelectedIndex() != -1) {
                ImagePanel img = projects.get(TPtabs.getSelectedIndex());
                Thread fth = new Thread(new FilterThread(img,plug,Bstopfilter,this));
                img.setThread(fth);
                fth.start();
            }
        }
        else if (o == Spzoom) {
            projects.get(TPtabs.getSelectedIndex()).getParent().getParent().validate();
        }
    }
}
