/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package st;

import java.util.Map;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;

/**
 *
 * @author ALEXANDRE
 */
public class Audio {
    private TargetDataLine targetDataLine;
    private AudioFormat format;
    private float sampleRate = 44100;
    private int sampleSizeInBits = 16;
    private int channels = 1;
    private boolean signed = true;
    private boolean bigEndian = false;

    public AudioFormat getFormat() {
        return format;
    }

    public float getSampleRate() {
        return sampleRate;
    }

    public TargetDataLine getTargetDataLine() {
        return targetDataLine;
    }
    
    public void setTargetDataLine(Line.Info dataLineInfo) {
        try {
            if (dataLineInfo == null) {
                dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
            }
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
        } catch (LineUnavailableException lue) {
            JOptionPane.showMessageDialog(null, lue.getMessage());
        }
    }
    
    public Audio(){
        format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        setTargetDataLine(null);
    }    
    
}
