package com.ucanesdetijuana.calculadorafisica;

import com.formdev.flatlaf.FlatLightLaf;
import com.ucanesdetijuana.calculadorafisica.forms.Start;

/**
 *
 * @author Joså Alfredo Interián Polanco
 */
public class CalculadoraFisica {
    
    public static void main(String[] args) {
        FlatLightLaf.setup();
        Start s = Start.getInstance();
        s.setVisible(true);
    }
}
