package com.ucanesdetijuana.calculadorafisica.forms;

import com.ucanesdetijuana.calculadorafisica.core.logic.Ecuation;
import java.util.List;

/**
 *
 * Establece el método con las acciones que los oyentes realizaran
 * @author José Alfredo Interián Polanco
 */
@FunctionalInterface
public interface CalculateListener {
    /**
     * Envía las cadenas de texto para renderizar un contenido LaTeX postcálculo.
     * @param latex Pasos que se siguieron para calcular el contenido.
     */
    public void onCalculate(List<String> latex);
}
