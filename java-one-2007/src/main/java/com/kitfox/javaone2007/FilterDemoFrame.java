/*
 * FilterDemoFrame.java
 *
 * Created on April 23, 2007, 1:32 AM
 */

package com.kitfox.javaone2007;

import java.awt.Color;

/**
 *
 * @author  kitfox
 */
public class FilterDemoFrame extends javax.swing.JFrame
{
    /** Creates new form FilterDemoFrame */
    public FilterDemoFrame()
    {
        initComponents();
        
        FilteredButton but1 = new FilteredButton();
        but1.setText("Button 1");
        but1.setBounds(20, 40, 100, 40);
        but1.setBackground(new Color(204, 102, 255));
        
        getContentPane().add(but1);
        
        setSize(640, 480);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        jButton1 = new javax.swing.JButton();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jButton1.setText("jButton1");
        getContentPane().add(jButton1);
        jButton1.setBounds(220, 210, 75, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new FilterDemoFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
    
}
