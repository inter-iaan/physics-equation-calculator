package com.ucanesdetijuana.calculadorafisica.forms;

import java.util.ArrayList;
import java.util.List;

/**
 * Evento que se dispara cuando el contenido de un archivo es modificado.
 * @author José Alfredo Interián Polanco
 */
public class EditedEvent {
    List<EditedListener> listeners = new ArrayList<>();
    
    public void addEditedListener (EditedListener l) { listeners.add(l); }
    
    public void fire (boolean isEdited) {
        for (EditedListener l : listeners) {
            l.onEdited(isEdited);
        }
    }
}
