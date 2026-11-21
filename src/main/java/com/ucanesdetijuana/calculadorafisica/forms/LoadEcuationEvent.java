package com.ucanesdetijuana.calculadorafisica.forms;

import com.ucanesdetijuana.calculadorafisica.core.logic.Ecuation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfre
 */
public class LoadEcuationEvent {
    List<LoadListener> listeners = new ArrayList();
    
    public void addListener (LoadListener l) {
        listeners.add(l);
    }
    
    public void fireContent (Ecuation ec) {
        for (LoadListener listener : listeners) {
            listener.load(ec);
        }
    }
}
