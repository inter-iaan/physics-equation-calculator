package com.ucanesdetijuana.calculadorafisica.forms;

import com.ucanesdetijuana.calculadorafisica.core.logic.Ecuation;
import java.util.ArrayList;
import java.util.List;

/**
 * Almacena y procesa a los oyentes y disparadores cuando se desea calcular el valor de una variable
 * @author José Alfredo Interián Polanco
 */
public class CalculateEvent {
    List<CalculateListener> listeners = new ArrayList();
    
    public void addListener (CalculateListener l) {
        listeners.add(l);
    }
    
    public void fireContent (List<String> latex, Ecuation ec) {
        for (CalculateListener listener : listeners) {
            listener.onCalculate(latex, ec);
        }
    }
}
