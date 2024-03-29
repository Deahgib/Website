package doscales;

import javax.swing.JOptionPane;
/**
 * @author Louis Bennette
 * Written in NetBeans
 */
public class ScalerGUI extends javax.swing.JFrame {

    /**
     * Creates new form ScalerGUI
     */
    public ScalerGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label = new javax.swing.JLabel();
        scaleButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        fieldA = new javax.swing.JTextField();
        fieldB = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Do Scales!");

        label.setText("Set the first values to be scalled.");

        scaleButton.setText("Set Scale");
        scaleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleButtonActionPerformed(evt);
            }
        });

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        fieldA.setText("0");
        fieldA.setToolTipText("");

        fieldB.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldA)
                            .addComponent(fieldB))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scaleButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(resetButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label)
                        .addGap(0, 184, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scaleButton)
                    .addComponent(fieldA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetButton)
                    .addComponent(fieldB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private double a,b;
    private boolean setScale = true;
    private Scale scale;
    
    private void scaleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleButtonActionPerformed
        // TODO add your handling code here:
        
        
        try {
            a = Double.parseDouble(this.fieldA.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "First variable is not valid", "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            b = Double.parseDouble(this.fieldB.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Second variable is not valid", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        if(a == 0 || b == 0){
            JOptionPane.showMessageDialog(this, "\"0\" Is not valid.\nHint: 0,x will scale to 0,y", "Error", JOptionPane.ERROR_MESSAGE);
            reset();
            return;
        }
  
        if(setScale){
            scale = new Scale(a,b);
            setScale = false;
            scaleButton.setText("Find Scale!");
            label.setText("Now change one variable to find to other scale equivalent.");
        }
        else{
            if(!scale.sameAsRefA(a) && !scale.sameAsRefB(b)){
                JOptionPane.showMessageDialog(this, "You seem to have changed both variables. To find the scale equivalent please only change one.", "Error", JOptionPane.ERROR_MESSAGE);
                fieldA.setText("" + scale.getRefA());
                fieldB.setText("" + scale.getRefB());
                
            }
            else if(scale.sameAsRefA(a)){
                a = (b / scale.getRefB()) * scale.getRefA();
                fieldA.setText("" + a);
                fieldB.setText("" + b);
                scale.setReference(a, b);
            }
            else if(scale.sameAsRefB(b)){
                b = (a / scale.getRefA()) * scale.getRefB();
                fieldA.setText("" + a);
                fieldB.setText("" + b);
                scale.setReference(a, b);
            }
        }
    }//GEN-LAST:event_scaleButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_resetButtonActionPerformed

    public void reset(){
        setScale = true;
        scaleButton.setText("Set Scale");
        fieldA.setText("");
        fieldB.setText("");
        label.setText("Set the first values to be scalled");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ScalerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ScalerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ScalerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ScalerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ScalerGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField fieldA;
    private javax.swing.JTextField fieldB;
    private javax.swing.JLabel label;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton scaleButton;
    // End of variables declaration//GEN-END:variables
}
