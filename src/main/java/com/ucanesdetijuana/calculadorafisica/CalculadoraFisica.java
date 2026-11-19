package com.ucanesdetijuana.calculadorafisica;

import com.formdev.flatlaf.FlatLightLaf;
import com.ucanesdetijuana.calculadorafisica.forms.EcuationViewModel;
import com.ucanesdetijuana.calculadorafisica.forms.Start;
import com.ucanesdetijuana.calculadorafisica.modules.MRU;
import com.ucanesdetijuana.calculadorafisica.modules.Snell;
import java.util.ArrayList;

/**
 *
 * @author Joså Alfredo Interián Polanco
 */
public class CalculadoraFisica {
    public static void main(String[] args) {
        ArrayList<EcuationViewModel> models = new ArrayList<>();
        models.add(new EcuationViewModel(new MRU()));
        models.add(new EcuationViewModel(new Snell()));
        
        FlatLightLaf.setup();        
        Start s = new Start(models);
        s.setVisible(true);
    }
}
