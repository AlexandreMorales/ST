/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package st;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ALEXANDRE
 */
public class Notas {    
    private Map<Double, String> mapaFrequencias;
    private static final double[] FREQUENCIES = {146.83, 138.59, 130.81, 123.47, 116.54, 110.00, 103.83, 98.00, 92.50, 87.31, 82.41, 77.78};
    private static final String[] NAME = {"D", "C#", "C", "B", "A#", "A", "G#", "G", "F#", "F", "E", "D#"};

    public Notas() {
        criaFreq();
    }    
    
    private void criaFreq() {
        mapaFrequencias = new HashMap<Double, String>();
        for (int i = 0; i < 5; i++) {
            for (int j = NAME.length - 1; j > 0; j--) {
                mapaFrequencias.put(FREQUENCIES[j] * (i ^ 2), NAME[j]);
            }
        }
    }

    public String notaMaisProxima(double hz) {
        double distanciaMinima = Double.MAX_VALUE;
        double distancia = 0;
        String nota = "";
        for (double freq : mapaFrequencias.keySet()) {
            distancia = Math.abs(freq - hz);
            if (distancia < distanciaMinima) {
                distanciaMinima = distancia;
                nota = mapaFrequencias.get(freq);
            }
        }
        return nota;
    }
}
