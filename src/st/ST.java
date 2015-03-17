package st;

import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;

public class ST extends javax.swing.JFrame {

    Map<String, Line.Info> mapaMicrofones;
    Notas notas;
    Audio audio;
    
    public ST() {
        initComponents();
        this.setLocationRelativeTo(null);
        audio = new Audio();
        notas = new Notas();
        carregarMicrofones();
    }

    public void testaMic() {
        TargetDataLine targetDataLine = audio.getTargetDataLine();
        AudioFormat format = audio.getFormat();
        try {
            targetDataLine.open(format, (int) audio.getSampleRate());
            targetDataLine.start();
        } catch (LineUnavailableException lue) {
            JOptionPane.showMessageDialog(null, lue.getMessage());
        }
        byte[] buffer = new byte[2 * 1200];

        int n = -1;
        while ((n = targetDataLine.read(buffer, 0, buffer.length)) > 0) {
            telaSaida.setText(String.format("%.2fhz", format.getSampleRate()));
            try {
                Thread.sleep(250);
            } catch (Exception e) {
            }
            targetDataLine.stop();
        }
    }

    public void carregarMicrofones() {
        cbMicrofones.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Selecione..."}));
        try {
            mapaMicrofones = listarMicrofones();
            for (String microfone : mapaMicrofones.keySet()) {
                cbMicrofones.addItem(microfone);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static Map<String, Line.Info> listarMicrofones() {
        Map<String, Line.Info> out = new HashMap<String, Line.Info>();
        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        for (Mixer.Info info : mixerInfos) {
            Mixer m = AudioSystem.getMixer(info);
            Line.Info[] lineInfos = m.getTargetLineInfo();
            if (lineInfos.length >= 1 && lineInfos[0].getLineClass().equals(TargetDataLine.class)) {
                out.put(info.getName(), lineInfos[0]);
            }
        }
        return out;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        telaSaida = new javax.swing.JTextArea();
        cbMicrofones = new javax.swing.JComboBox();
        jbTeste = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        telaSaida.setColumns(20);
        telaSaida.setRows(5);
        jScrollPane1.setViewportView(telaSaida);

        cbMicrofones.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione..." }));
        cbMicrofones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMicrofonesActionPerformed(evt);
            }
        });

        jbTeste.setText("Teste");
        jbTeste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTesteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbMicrofones, 0, 122, Short.MAX_VALUE)
                    .addComponent(jbTeste))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbMicrofones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 235, Short.MAX_VALUE)
                        .addComponent(jbTeste))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbMicrofonesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMicrofonesActionPerformed
        audio.setTargetDataLine(mapaMicrofones.get((String) cbMicrofones.getSelectedItem()));
    }//GEN-LAST:event_cbMicrofonesActionPerformed

    private void jbTesteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTesteActionPerformed
        testaMic();
    }//GEN-LAST:event_jbTesteActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ST.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ST.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ST.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ST.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ST().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbMicrofones;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbTeste;
    private javax.swing.JTextArea telaSaida;
    // End of variables declaration//GEN-END:variables
}
