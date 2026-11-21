package com.ucanesdetijuana.calculadorafisica;

import com.ucanesdetijuana.calculadorafisica.core.logic.Ecuation;
import com.ucanesdetijuana.calculadorafisica.forms.EcuationViewModel;
import com.ucanesdetijuana.calculadorafisica.modules.LentesConcavos;
import com.ucanesdetijuana.calculadorafisica.modules.MRU;
import com.ucanesdetijuana.calculadorafisica.modules.SLNewton;
import com.ucanesdetijuana.calculadorafisica.modules.Snell;
import java.util.ArrayList;

/**
 * Guarda las instancia de los modelos directamente en la memoria de ejecución
 * @author José Alfredo Interián Polanco
 */
public class EcuationHardCoded {
    private static final ArrayList<EcuationViewModel> models = new ArrayList<>();

    static {
        models.add(new EcuationViewModel(new MRU()));
        models.add(new EcuationViewModel(new Snell()));
        models.add(new EcuationViewModel(new SLNewton()));
        models.add(new EcuationViewModel(new LentesConcavos()));
    }
    
    public static int parseIndex (Ecuation ec) throws Exception {
        return switch (ec) {
            case MRU _ -> 0;
            case Snell _ -> 1;
            case SLNewton _ -> 2;
            case LentesConcavos _ -> 3;
            default -> throw new Exception("No se puede acceder a ese tipo de Ecuación.");
        };
    }

    public static ArrayList<EcuationViewModel> getModels() { return models; }
}
