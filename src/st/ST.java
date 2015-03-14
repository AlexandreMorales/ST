package st;

import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;

public class ST extends javax.swing.JFrame {

    Map<String, Line.Info> mapaMicrofones;
    Map<Double, String> mapaFrequencias;
    TargetDataLine targetDataLine;
    private static final double[] FREQUENCIES = {146.83, 138.59, 130.81, 123.47, 116.54, 110.00, 103.83, 98.00, 92.50, 87.31, 82.41, 77.78};
    private static final String[] NAME = {"D", "C#", "C", "B", "A#", "A", "G#", "G", "F#", "F", "E", "D#"};

    public ST() {
        initComponents();
        this.setLocationRelativeTo(null);
        carregarMicrofones();
        setUp(mapaMicrofones.get((String) cbMicrofones.getSelectedItem()));
    }

    public void setUp(Line.Info lineInfo) {
        try {
            float sampleRate = 44100;
            int sampleSizeInBits = 16;
            int channels = 1;
            boolean signed = true;
            boolean bigEndian = false;
            AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);

            targetDataLine.open(format, (int) sampleRate);
            targetDataLine.start();

            byte[] buffer = new byte[2 * 1200];
            int[] a = new int[buffer.length / 2];

            int n = -1;
            while ((n = targetDataLine.read(buffer, 0, buffer.length)) > 0) {
                for (int i = 0; i < n; i += 2) {
                    int value = (short) ((buffer[i] & 0xFF) | ((buffer[i + 1] & 0xFF) << 8));
                    a[i >> 1] = value;
                }
                double prevDiff = 0;
                double prevDx = 0;
                double maxDiff = 0;
                int sampleLen = 0;
                int len = a.length / 2;
                for (int i = 0; i < len; i++) {
                    double diff = 0;
                    for (int j = 0; j < len; j++) {
                        diff += Math.abs(a[j] - a[i + j]);
                    }
                    double dx = prevDiff - diff;
                    if (dx < 0 && prevDx > 0) {
                        if (diff < (0.1 * maxDiff)) {
                            if (sampleLen == 0) {
                                sampleLen = i - 1;
                            }
                        }
                    }
                    prevDx = dx;
                    prevDiff = diff;
                    maxDiff = Math.max(diff, maxDiff);
                }
                if (sampleLen > 0) {
                    double frequency = (format.getSampleRate() / sampleLen);
                    
                    /*freqLabel.setText(String.format("%.2fhz", frequency));
                    frequency = normaliseFreq(frequency);
                    int note = closestNote(frequency);
                    matchLabel.setText(NAME[note]);
                    System.out.println(NAME[note]);
                    prevLabel.setText(NAME[note - 1]);
                    nextLabel.setText(NAME[note + 1]);

                    int value = 0;
                    double matchFreq = FREQUENCIES[note];
                    if (frequency < matchFreq) {
                        double prevFreq = FREQUENCIES[note + 1];
                        value = (int) (-FREQ_RANGE * (frequency - matchFreq) / (prevFreq - matchFreq));
                    } else {
                        double nextFreq = FREQUENCIES[note - 1];
                        value = (int) (FREQ_RANGE * (frequency - matchFreq) / (nextFreq - matchFreq));
                    }
                    freqSlider.setValue(value);
                } else {
                    matchLabel.setText("--");
                    prevLabel.setText("--");
                    nextLabel.setText("--");
                    freqSlider.setValue(0);
                    freqLabel.setText("--");
                }
                prevLabel.setSize(prevLabel.getPreferredSize());
                nextLabel.setSize(nextLabel.getPreferredSize());
                matchLabel.setSize(matchLabel.getPreferredSize());

                freqSlider.repaint();
                freqLabel.repaint();*/
                }
                try {
                    Thread.sleep(250);
                } catch (Exception e) {
                }
            }

        } catch (LineUnavailableException lue) {
            JOptionPane.showMessageDialog(null, lue.getMessage());
        }
    }
    
    private void criaFreq(){
        mapaFrequencias = new HashMap <Double, String>();
        for (int i = 0; i < 5; i++) {
            for (int j = NAME.length-1; j >0; j--) {
                mapaFrequencias.put(FREQUENCIES[j]*(i^2), NAME[j]);
            }
        }
    }
    
    private static int closestNote(double hz) {
        double minDist = Double.MAX_VALUE;
        int minFreq = -1;
        for (int i = 0; i < FREQUENCIES.length; i++) {
            double dist = Math.abs(FREQUENCIES[i] - hz);
            if (dist < minDist) {
                minDist = dist;
                minFreq = i;
            }
        }
        return minFreq;
    }

    public void carregarMicrofones() {
        cbMicrofones.setModel(new javax.swing.DefaultComboBoxModel(new String[]{}));
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
        HashMap<String, Line.Info> out = new HashMap<String, Line.Info>();
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbMicrofones, 0, 122, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbMicrofones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbMicrofonesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMicrofonesActionPerformed
        setUp(mapaMicrofones.get((String) cbMicrofones.getSelectedItem()));
    }//GEN-LAST:event_cbMicrofonesActionPerformed

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
    private javax.swing.JTextArea telaSaida;
    // End of variables declaration//GEN-END:variables
}
