package com.ucanesdetijuana.calculadorafisica.forms;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author José Alfredo Interián Polanco
 */
public class DescriptionEvent {
    private List<DescriptionListener> listeners;

    public DescriptionEvent() { listeners = new ArrayList<>(); }
    public void addDescListener (DescriptionListener listener) { listeners.add(listener); }
    public void fireEvent (String content) {
        listeners.forEach(t -> {
            t.updateContent(content);
        });
    }
}
