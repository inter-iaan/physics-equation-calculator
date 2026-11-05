package com.ucanesdetijuana.calculadorafisica;

import com.formdev.flatlaf.FlatLightLaf;
import com.ucanesdetijuana.calculadorafisica.core.convertions.ConverterFactory;
import com.ucanesdetijuana.calculadorafisica.core.convertions.LengthUnit;
import com.ucanesdetijuana.calculadorafisica.core.convertions.UnitConverter;
import com.ucanesdetijuana.calculadorafisica.forms.Start;

/**
 *
 * @author Joså Alfredo Interián Polanco
 */
public class CalculadoraFisica {
    public static void main(String[] args) {        
//        FlatLightLaf.setup();        
//        Start s = new Start();
//        s.setVisible(true);

        System.out.println(UnitConverter.convert(10, LengthUnit.METRO, LengthUnit.KILOMETRO) + LengthUnit.KILOMETRO.getSymbol());
    }
}
