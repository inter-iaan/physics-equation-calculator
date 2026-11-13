package com.ucanesdetijuana.calculadorafisica.core.convertions;

/**
 *
 * @author José Alfredo Interián Polanco
 * Comportamiento general de las unidades con respecto a su contenido.
 */
public interface UnitMetric {
    /**
     * Obtiene el nombre completo de la unidad
     * Ejemplo: Metro
     * @return Nombre de la unidad
     */
    String getName();
    /**
     * Obtiene únicamente su representación corta
     * Ejempo: m
     * @return Símbolo de la unidad
     */
    String getSymbol();
    
    ConverterFactory.MagnitudeType getMagnitudeType();
    
    /**
     * Convierte el valor dado a la unidad base de la magnitud
     * Ejemplo: 10 km devuelve 10,000
     * @param value Valor en cualquier escala
     * @return Valor en la base
     */
    double toBase(double value);
    /**
     * Convierte el valor desde su base a cualquier otra escala
     * Ejemplo: 1000m a 1km
     * @param unitValue Valor en su base. 
     * @return Valor con respecto a su escala.
     */
    double fromBaseTo (double unitValue);
}
