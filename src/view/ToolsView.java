/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

/**
 *
 * @author Tommy
 */
public class ToolsView extends javax.swing.JDialog {
    private MainView mainview;
    private Controller control;
    private Color col = Color.BLACK;
    private BufferedImage colimg;
    private int textx;
    private int texty;
    private JColorChooser cc;
    private JDialog ccview;
  
    
    /**
     * Creates new form ToolsView
     */
    public ToolsView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Tools - MyPhotoshop");
        mainview = (MainView)parent;
        control = mainview.getController();
        cc = new JColorChooser(col);
        final JLabel previewLabel = new JLabel("I Love MyPhotoshop", JLabel.CENTER);
        previewLabel.setFont(new Font("New Times Roman", Font.BOLD, 50));
        previewLabel.setSize(previewLabel.getPreferredSize());
        previewLabel.setBorder(BorderFactory.createEmptyBorder(0,0,1,0));
        cc.setPreviewPanel(previewLabel);
        ActionListener okActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                col = cc.getColor();
                control.setCol(col);
                Graphics2D g2d = colimg.createGraphics();
                Color c1 = new Color(102,102,102,255);
                Color c2 = new Color(153,153,153,255);
                Color c3;
                g2d.setColor(c2);
                for (int i = 0; i < BtoolColor.getHeight(); i+=10) {
                    c3 = g2d.getColor();
                    for (int j = 0; j < BtoolColor.getWidth(); j+=10) {
                        g2d.fillRect(j, i, 10, 10);
                        if (g2d.getColor() == c2)
                            g2d.setColor(c1);
                        else
                            g2d.setColor(c2);
                    }
                    if (c3 == c1)
                        g2d.setColor(c2);
                    else
                        g2d.setColor(c1);
                }
                g2d.setColor(col);
                g2d.fillRect(0, 0, BtoolColor.getWidth(), BtoolColor.getHeight());
                BtoolColor.setIcon(new ImageIcon(colimg));
            }
        };
        ActionListener cancelActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            }
        };
        ccview = JColorChooser.createDialog(mainview, "Color Chooser - MyPhotoshop", true, cc, okActionListener, cancelActionListener);
        colimg = new BufferedImage(BtoolColor.getWidth(), BtoolColor.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = colimg.createGraphics();
        g2d.setColor(col);
        g2d.fillRect(0, 0, BtoolColor.getWidth(), BtoolColor.getHeight());
        BtoolColor.setIcon(new ImageIcon(colimg));
        BtoolColor.addActionListener(control);
        TBtoolText.setIcon(new ImageIcon("src/images/text.png"));
        TBtoolText.addActionListener(control);
        TBtoolHand.addActionListener(control);
        TBtoolHand.setIcon(new ImageIcon("src/images/move.png"));
        TBtoolDraw.addActionListener(control);
        TBtoolDraw.setIcon(new ImageIcon("src/images/draw.png"));
        TBtoolPaint.addActionListener(control);
        TBtoolPaint.setIcon(new ImageIcon("src/images/paint.png"));
        TBtoolClear.addActionListener(control);
        TBtoolClear.setIcon(new ImageIcon("src/images/clear.png"));
        TBtoolRect.addActionListener(control);
        TBtoolRect.setIcon(new ImageIcon("src/images/rect.png"));
        TBtoolOval.addActionListener(control);
        TBtoolOval.setIcon(new ImageIcon("src/images/oval.png"));
        TBtoolLine.addActionListener(control);
        TBtoolLine.setIcon(new ImageIcon("src/images/line.png"));
        TBtoolFill.addActionListener(control);
        TBtoolFill.setIcon(new ImageIcon("src/images/fill.png"));
        TBtoolCut.addActionListener(control);
        TBtoolCut.setIcon(new ImageIcon("src/images/cut.png"));
        TBtoolSelect.addActionListener(control);
        TBtoolSelect.setIcon(new ImageIcon("src/images/select.png"));
        TBtoolPipette.addActionListener(control);
        TBtoolPipette.setIcon(new ImageIcon("src/images/pipette.png"));
        TBtoolStamp.addActionListener(control);
        TBtoolStamp.setIcon(new ImageIcon("src/images/stamp.png"));
    }
    
    public JLabel getLtoolPreview() {
        return LtoolPreview;
    }
    
    public JToggleButton getTBtoolText() {
        return TBtoolText;
    }
    
    public JToggleButton getTBtoolStamp() {
        return TBtoolStamp;
    }
    
    public JToggleButton getTBtoolPipette() {
        return TBtoolPipette;
    }
    
    public JToggleButton getTBtoolSelect() {
        return TBtoolSelect;
    }
    
    public JToggleButton getTBtoolCut() {
        return TBtoolCut;
    }
    
    public JToggleButton getTBtoolFill() {
        return TBtoolFill;
    }
    
    public JToggleButton getTBtoolRect() {
        return TBtoolRect;
    }
    
    public JToggleButton getTBtoolOval() {
        return TBtoolOval;
    }
    
    public JToggleButton getTBtoolLine() {
        return TBtoolLine;
    }
    
    public JToggleButton getTBtoolMove() {
        return TBtoolHand;
    }
    
    public JToggleButton getTBtoolDraw() {
        return TBtoolDraw;
    }
    
    public JToggleButton getTBtoolPaint() {
        return TBtoolPaint;
    }
    
    public JToggleButton getTBtoolClear() {
        return TBtoolClear;
    }
    
    public JButton getBtoolColor() {
        return BtoolColor;
    }
    
    public JSlider getSLtoolSize() {
        return SLtoolSize;
    }
    
    public JDialog getCcView() {
        return ccview;
    }
    
    public Color getCol() {
        return col;
    }
    
    public void setColorChooser(Color ncol) {
        col = ncol;
        control.setCol(col);
        Graphics2D g2d = colimg.createGraphics();
        Color c1 = new Color(102,102,102,255);
        Color c2 = new Color(153,153,153,255);
        Color c3;
        g2d.setColor(c2);
        for (int i = 0; i < BtoolColor.getHeight(); i+=10) {
            c3 = g2d.getColor();
            for (int j = 0; j < BtoolColor.getWidth(); j+=10) {
                g2d.fillRect(j, i, 10, 10);
                if (g2d.getColor() == c2)
                    g2d.setColor(c1);
                else
                    g2d.setColor(c2);
            }
            if (c3 == c1)
                g2d.setColor(c2);
            else
                g2d.setColor(c1);
        }
        g2d.setColor(col);
        g2d.fillRect(0, 0, BtoolColor.getWidth(), BtoolColor.getHeight());
        BtoolColor.setIcon(new ImageIcon(colimg));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtoolColor = new javax.swing.JButton();
        TBtoolText = new javax.swing.JToggleButton();
        TBtoolHand = new javax.swing.JToggleButton();
        TBtoolSelect = new javax.swing.JToggleButton();
        TBtoolDraw = new javax.swing.JToggleButton();
        TBtoolPaint = new javax.swing.JToggleButton();
        TBtoolClear = new javax.swing.JToggleButton();
        SLtoolSize = new javax.swing.JSlider();
        LtoolSize = new javax.swing.JLabel();
        TBtoolRect = new javax.swing.JToggleButton();
        TBtoolOval = new javax.swing.JToggleButton();
        TBtoolLine = new javax.swing.JToggleButton();
        TBtoolFill = new javax.swing.JToggleButton();
        TBtoolCut = new javax.swing.JToggleButton();
        LtoolPreview = new view.MyPreviewLabel();
        TBtoolPipette = new javax.swing.JToggleButton();
        TBtoolStamp = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        TBtoolText.setMaximumSize(new java.awt.Dimension(45, 40));
        TBtoolText.setMinimumSize(new java.awt.Dimension(45, 40));
        TBtoolText.setPreferredSize(new java.awt.Dimension(45, 40));

        TBtoolHand.setMaximumSize(new java.awt.Dimension(45, 40));
        TBtoolHand.setMinimumSize(new java.awt.Dimension(45, 40));
        TBtoolHand.setPreferredSize(new java.awt.Dimension(45, 40));

        TBtoolSelect.setMaximumSize(new java.awt.Dimension(45, 40));
        TBtoolSelect.setMinimumSize(new java.awt.Dimension(45, 40));
        TBtoolSelect.setPreferredSize(new java.awt.Dimension(45, 40));

        TBtoolDraw.setMaximumSize(new java.awt.Dimension(45, 40));
        TBtoolDraw.setMinimumSize(new java.awt.Dimension(45, 40));
        TBtoolDraw.setPreferredSize(new java.awt.Dimension(45, 40));

        TBtoolPaint.setMaximumSize(new java.awt.Dimension(45, 40));
        TBtoolPaint.setMinimumSize(new java.awt.Dimension(45, 40));
        TBtoolPaint.setPreferredSize(new java.awt.Dimension(45, 40));

        TBtoolClear.setMaximumSize(new java.awt.Dimension(45, 40));
        TBtoolClear.setMinimumSize(new java.awt.Dimension(45, 40));
        TBtoolClear.setPreferredSize(new java.awt.Dimension(45, 40));

        SLtoolSize.setMinimum(2);
        SLtoolSize.setValue(10);
        SLtoolSize.setMaximumSize(new java.awt.Dimension(137, 26));
        SLtoolSize.setMinimumSize(new java.awt.Dimension(137, 26));
        SLtoolSize.setPreferredSize(new java.awt.Dimension(137, 26));
        SLtoolSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SLtoolSizeStateChanged(evt);
            }
        });

        LtoolSize.setFont(new java.awt.Font("Times New Roman", 0, 10)); // NOI18N
        LtoolSize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LtoolSize.setText("10");
        LtoolSize.setMaximumSize(new java.awt.Dimension(18, 26));
        LtoolSize.setMinimumSize(new java.awt.Dimension(18, 26));
        LtoolSize.setPreferredSize(new java.awt.Dimension(18, 26));

        TBtoolRect.setMaximumSize(new java.awt.Dimension(45, 40));
        TBtoolRect.setMinimumSize(new java.awt.Dimension(45, 40));
        TBtoolRect.setPreferredSize(new java.awt.Dimension(45, 40));

        TBtoolOval.setMaximumSize(new java.awt.Dimension(45, 40));
        TBtoolOval.setMinimumSize(new java.awt.Dimension(45, 40));
        TBtoolOval.setPreferredSize(new java.awt.Dimension(45, 40));

        TBtoolLine.setMaximumSize(new java.awt.Dimension(45, 40));
        TBtoolLine.setMinimumSize(new java.awt.Dimension(45, 40));
        TBtoolLine.setPreferredSize(new java.awt.Dimension(45, 40));

        TBtoolFill.setMaximumSize(new java.awt.Dimension(45, 40));
        TBtoolFill.setMinimumSize(new java.awt.Dimension(45, 40));
        TBtoolFill.setPreferredSize(new java.awt.Dimension(45, 40));

        TBtoolCut.setMaximumSize(new java.awt.Dimension(45, 40));
        TBtoolCut.setMinimumSize(new java.awt.Dimension(45, 40));
        TBtoolCut.setPreferredSize(new java.awt.Dimension(45, 40));

        LtoolPreview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        TBtoolPipette.setMaximumSize(new java.awt.Dimension(45, 40));
        TBtoolPipette.setMinimumSize(new java.awt.Dimension(45, 40));
        TBtoolPipette.setPreferredSize(new java.awt.Dimension(45, 40));

        TBtoolStamp.setMaximumSize(new java.awt.Dimension(45, 40));
        TBtoolStamp.setMinimumSize(new java.awt.Dimension(45, 40));
        TBtoolStamp.setPreferredSize(new java.awt.Dimension(45, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LtoolPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(SLtoolSize, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LtoolSize, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(BtoolColor, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(TBtoolFill, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TBtoolRect, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TBtoolDraw, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TBtoolHand, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TBtoolText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TBtoolPaint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TBtoolOval, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TBtoolCut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TBtoolSelect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TBtoolClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TBtoolLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TBtoolPipette, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(TBtoolStamp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TBtoolText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TBtoolSelect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TBtoolHand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TBtoolDraw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TBtoolPaint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TBtoolClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TBtoolRect, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TBtoolOval, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TBtoolLine, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(TBtoolFill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TBtoolCut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TBtoolPipette, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(TBtoolStamp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40)
                .addComponent(BtoolColor, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LtoolSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SLtoolSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LtoolPreview, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SLtoolSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SLtoolSizeStateChanged
        LtoolSize.setText(String.valueOf(SLtoolSize.getValue()));
    }//GEN-LAST:event_SLtoolSizeStateChanged

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
            java.util.logging.Logger.getLogger(ToolsView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ToolsView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ToolsView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ToolsView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ToolsView dialog = new ToolsView(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtoolColor;
    private view.MyPreviewLabel LtoolPreview;
    private javax.swing.JLabel LtoolSize;
    private javax.swing.JSlider SLtoolSize;
    private javax.swing.JToggleButton TBtoolClear;
    private javax.swing.JToggleButton TBtoolCut;
    private javax.swing.JToggleButton TBtoolDraw;
    private javax.swing.JToggleButton TBtoolFill;
    private javax.swing.JToggleButton TBtoolHand;
    private javax.swing.JToggleButton TBtoolLine;
    private javax.swing.JToggleButton TBtoolOval;
    private javax.swing.JToggleButton TBtoolPaint;
    private javax.swing.JToggleButton TBtoolPipette;
    private javax.swing.JToggleButton TBtoolRect;
    private javax.swing.JToggleButton TBtoolSelect;
    private javax.swing.JToggleButton TBtoolStamp;
    private javax.swing.JToggleButton TBtoolText;
    // End of variables declaration//GEN-END:variables
}
